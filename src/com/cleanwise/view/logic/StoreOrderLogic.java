package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Note;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.StoreOrder;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineMessageException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.CustomerOrderChangeRequestData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InvoiceCustCritView;
import com.cleanwise.service.api.value.InvoiceCustViewVector;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.NoteData;
import com.cleanwise.service.api.value.NoteJoinView;
import com.cleanwise.service.api.value.NoteJoinViewVector;
import com.cleanwise.service.api.value.NoteTextData;
import com.cleanwise.service.api.value.NoteTextDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemChangeRequestData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemJoinDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ReplacedOrderItemView;
import com.cleanwise.service.api.value.ReplacedOrderItemViewVector;
import com.cleanwise.service.api.value.ReplacedOrderView;
import com.cleanwise.service.api.value.ReplacedOrderViewVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.StoreOrderChangeRequestData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.StoreOrderDetailForm;
import com.cleanwise.view.forms.StoreOrderItemDetailForm;
import com.cleanwise.view.forms.StoreOrderItemSearchForm;
import com.cleanwise.view.forms.StoreOrderNoteForm;
import com.cleanwise.view.forms.StoreOrderSearchForm;
import com.cleanwise.view.forms.StoreOrderTemporaryPoForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CumulativeSummary;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.ValidateActionMessage;
import com.cleanwise.view.utils.pdf.PdfTemporaryPO;

/**
 * <code>OrderOpLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class StoreOrderLogic
{
    private static final Logger log = Logger.getLogger(StoreOrderLogic.class);

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request, ActionForm form)
                     throws Exception
    {
      StoreOrderSearchForm searchForm = (StoreOrderSearchForm)form;
      if (searchForm == null) {
      }
      if (searchForm != null) {
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        if (searchForm.getStoreId() != 0) {
          if (searchForm.getStoreId() != appUser.getUserStore().getStoreId()) {
            searchForm.setResultList(null);
          }
        }
        searchForm.setStoreId(appUser.getUserStore().getStoreId());
      }
        initConstantList(request);
        //searchAll(request, form);
        return;
    }

    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors search(HttpServletRequest request,
                                      ActionForm form)
                               throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        StoreOrderSearchForm sForm = (StoreOrderSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        StoreOrder orderEjb = factory.getStoreOrderAPI();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);




        if (sForm.getOrderDateRangeBegin().trim().length() < 1 &&
            sForm.getOrderDateRangeEnd().trim().length() < 1)
        {
            lUpdateErrors.add("ordersearch",
                              new ActionError("variable.empty.error",
                                              "Order Date Range"));

            return lUpdateErrors;
        }

        if (sForm.getAccountId().trim().length() > 0)
        {
            searchCriteria.setAccountId(sForm.getAccountId().trim());
        }

        String accountIdList = sForm.getAccountIdList();
        if (Utility.isSet(accountIdList)) {
           IdVector accountIdV = Utility.parseIdStringToVector(accountIdList,",");
           if(accountIdV.size()>0) {
             searchCriteria.setAccountIdVector(accountIdV);
           }
        }

        if (sForm.getDistributorId().trim().length() > 0)
        {
            searchCriteria.setDistributorId(sForm.getDistributorId().trim());
        }

        String distributorIdList = sForm.getDistributorIdList();
        if (Utility.isSet(distributorIdList)) {
           IdVector distributorIdV = Utility.parseIdStringToVector(distributorIdList,",");
           if(distributorIdV.size()>0) {
             searchCriteria.setDistributorIdVector(distributorIdV);
           }
        }

        if (sForm.getErpOrderNum().trim().length() > 0)
        {
            searchCriteria.setErpOrderNum(sForm.getErpOrderNum().trim());
        }

        if (sForm.getErpPONum().trim().length() > 0)
        {
            searchCriteria.setErpPONum(sForm.getErpPONum().trim());
        }
        if (sForm.getOutboundPoNum().trim().length() > 0)
        {
            searchCriteria.setOutboundPONum(sForm.getOutboundPoNum().trim());
        }
        if (sForm.getWebOrderConfirmationNum().trim().length() > 0)
        {
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }

        if (sForm.getOrderDateRangeBegin().trim().length() > 0)
        {
            searchCriteria.setOrderDateRangeBegin(sForm.getOrderDateRangeBegin().trim());
        }

        if (sForm.getOrderDateRangeEnd().trim().length() > 0)
        {
            searchCriteria.setOrderDateRangeEnd(sForm.getOrderDateRangeEnd().trim());
        }

        if (sForm.getCustPONum().trim().length() > 0)
        {
            searchCriteria.setCustPONum(sForm.getCustPONum().trim());
        }

        if (sForm.getRefOrderNum().trim().length() > 0)
        {
            searchCriteria.setRefOrderNum(sForm.getRefOrderNum().trim());
        }

        if (sForm.getSiteId().trim().length() > 0)
        {
            searchCriteria.setSiteId(sForm.getSiteId().trim());
        }

//        String siteIdList = sForm.getSiteIdList();
//        if (Utility.isSet(siteIdList)) {
//           IdVector siteIdV = Utility.parseIdStringToVector(siteIdList,",");
//           if(siteIdV.size()>0) {
//             searchCriteria..setSiteIdVector(siteIdV);
//           }
//        }
//
        if (sForm.getSiteZipCode().trim().length() > 0)
        {
            searchCriteria.setSiteZipCode(sForm.getSiteZipCode().trim());
        }

        if (!"".equals(sForm.getOrderStatus()))
        {
            searchCriteria.setOrderStatus(sForm.getOrderStatus());
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            searchCriteria.setStoreIdVector(appUser.getUserStoreAsIdVector());
        }

        if (sForm.getReferenceCode().trim().length() > 0)
        {
            searchCriteria.setReferenceCode(sForm.getReferenceCode().trim());
        }

        if (sForm.getShipFromId().trim().length() > 0)
        {
            searchCriteria.setShipFromId(sForm.getShipFromId().trim());
        }

        if (sForm.getPlacedBy().trim().length() > 0)
        {
            searchCriteria.setPlacedBy(sForm.getPlacedBy().trim());
        }

        if (!"".equals(sForm.getMethod()))
        {
            searchCriteria.setMethod(sForm.getMethod());
        }

        if (sForm.getInvoiceDistNum().trim().length() > 0)
        {
            searchCriteria.setInvoiceDistNum(sForm.getInvoiceDistNum().trim());
        }

        if (sForm.getInvoiceCustNum().trim().length() > 0)
        {
            searchCriteria.setInvoiceCustNum(sForm.getInvoiceCustNum().trim());
        }

        if (sForm.getWorkflowId().trim().length() > 0)
        {
            searchCriteria.setWorkflowId(sForm.getWorkflowId().trim());
        }

        // search order must have userId or/and userTypeCd
        String userId = (String)session.getAttribute(Constants.USER_ID);
        String userType = (String)session.getAttribute(Constants.USER_TYPE);

        if (null == userId)
            userId = new String("");

        if (null == userType)
            userType = new String("");

        searchCriteria.setUserId(userId);
        searchCriteria.setUserTypeCd(userType);

        OrderStatusDescDataVector orderStatus = new OrderStatusDescDataVector();
        orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria);
        sForm.setResultList(orderStatus);

        initConstantList(request);
        return lUpdateErrors;
    }

    /**
     * <code>searchAll</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request, ActionForm form)
                          throws Exception
    {

        HttpSession session = request.getSession();
        StoreOrderSearchForm sForm = (StoreOrderSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        /*
        OrderOp orderEjb   = factory.getOrderOpAPI();

        OrderOpDataVector orders = new OrderOpDataVector();
        orders  = orderEjb.getAllOrderOps();

        sForm.setResultList(orders);
         */
    }

    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request, ActionForm form)
                     throws Exception
    {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        StoreOrderSearchForm sForm = (StoreOrderSearchForm)form;

        if (sForm == null)
        {

            return;
        }

        OrderStatusDescDataVector orders = (OrderStatusDescDataVector)sForm.getResultList();

        if (orders == null)
        {

            return;
        }

        String sortField = request.getParameter("sortField");
        DisplayListSort.sortAdmin(request,orders, sortField);
    }

    /**
     *  <code>sortItems</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItems(HttpServletRequest request, ActionForm form)
                          throws Exception
    {

        HttpSession session = request.getSession();
        StoreOrderDetailForm sForm = (StoreOrderDetailForm)session.getAttribute(
                                          "STORE_ORDER_DETAIL_FORM");

        if (sForm == null)
        {

            // not expecting this, but nothing to do if it is
            return;
        }

        OrderItemDescDataVector orderItemDescList = (OrderItemDescDataVector)sForm.getOrderItemDescList();
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(orderItemDescList, sortField);
    }

    /**
     *  <code>sortItemList</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItemList(HttpServletRequest request,
                                    ActionForm form)
                             throws Exception
    {

        HttpSession session = request.getSession();
        StoreOrderItemSearchForm sForm = (StoreOrderItemSearchForm)session.getAttribute(
                                              "STORE_ORDER_ITEM_SEARCH_FORM");

        if (sForm == null)
        {

            // not expecting this, but nothing to do if it is
            return;
        }

        OrderItemDescDataVector orderItemDescList = (OrderItemDescDataVector)sForm.getOrderItemDescList();
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(orderItemDescList, sortField);
    }

    /**
     *  <code>sortItemDetails</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItemDetails(HttpServletRequest request,
                                       ActionForm form)
                                throws Exception
    {

        HttpSession session = request.getSession();
        StoreOrderItemDetailForm sForm = (StoreOrderItemDetailForm)session.getAttribute(
                                              "STORE_ORDER_ITEM_DETAIL_FORM");

        if (sForm == null)
        {

            // not expecting this, but nothing to do if it is
            return;
        }

        /*
        OrderItemDetailDataVector orderItemDetailList =
        (OrderItemDetailDataVector)sForm.getOrderItemDetailList();

        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(orderItemDetailList, sortField);
         */
    }

    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
    public static void initConstantList(HttpServletRequest request)
                                 throws Exception
    {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("Order.status.vector"))
        {

            RefCdDataVector statusv = listServiceEjb.getRefCodesCollection(
                                              "ORDER_STATUS_CD");
            session.setAttribute("Order.status.vector", statusv);
        }

        if (null == session.getAttribute("Method.type.vector"))
        {

            RefCdDataVector typev = listServiceEjb.getRefCodesCollection(
                                            "ORDER_SOURCE_CD");
            session.setAttribute("Method.type.vector", typev);
        }

        if (null == session.getAttribute("ItemDetail.action.vector"))
        {

            RefCdDataVector actionv = listServiceEjb.getRefCodesCollection(
                                              "ORDER_ITEM_DETAIL_ACTION_CD");
            session.setAttribute("ItemDetail.action.vector", actionv);
        }
    }

    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors getOrderStatusDetail(HttpServletRequest request,
                                            ActionForm form,
                                            String orderStatusId)
                                     throws Exception
    {
    	ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

try {
        Order orderEjb = factory.getOrderAPI();
        User userEjb = factory.getUserAPI();
        Note noteEjb = factory.getNoteAPI();
        ItemInformation itemInfEjb= factory.getItemInformationAPI();
        PropertyService propServEjb = factory.getPropertyServiceAPI();
        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == orderStatusId || "".equals(orderStatusId))
        {
            orderStatusId = (String)session.getAttribute("OrderStatus.id");
        }

        session.setAttribute("OrderStatus.id", orderStatusId);

        OrderStatusDescData orderStatusDetail = orderEjb.getOrderStatusDesc(Integer.parseInt(orderStatusId));
        boolean unauthrizedAccess = false;

        if(appUser.isaCustomer() || appUser.isaMSB()){
            if(!factory.getUserAPI().isSiteOfUser(orderStatusDetail.getOrderDetail().getSiteId(),appUser.getUser().getUserId())){
            	unauthrizedAccess = true;
            }
        }

        OrderData orderD = orderStatusDetail.getOrderDetail();

        int storeId = orderD.getStoreId();
        IdVector stores = appUser.getUserStoreAsIdVector();
        if (!stores.contains(new Integer(storeId))){
        	unauthrizedAccess = true;
        }

        if (unauthrizedAccess){
        	Object[] param = new Object[2];
            param[0] = appUser.getUser().getUserId()+"";
            param[1] = orderStatusId;
            String errorMess = ClwI18nUtil.getMessage(request, "unauthorized.order.access", param);
        	lUpdateErrors.add("orderdetail", new ActionError("error.simpleGenericError", errorMess));
            return lUpdateErrors;
        }
        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        detailForm.setShowDistNoteFl(false);
        try {
          String showDistNoteS = propServEjb.getBusEntityProperty(storeId,
                RefCodeNames.PROPERTY_TYPE_CD.SHOW_DISTR_NOTES_TO_CUSTOMER);
          if("true".equalsIgnoreCase(showDistNoteS)) {
            detailForm.setShowDistNoteFl(true);
          }
        } catch(DataNotFoundException exc) {
          //property is not defined
        } catch(Exception exc) {
          exc.printStackTrace();
        }

        /*Get only the notes from the vector for the selected order
        getOrderPropertyVec takes care of making sure we only get notes,
        and only the notes for the proper order
        */
        OrderPropertyDataVector orderPropertyDetailVec =
            orderEjb.getOrderPropertyVec(Integer.parseInt(orderStatusId));
        OrderPropertyData orderPropertyDetail = OrderPropertyData.createValue();

        if (null != orderPropertyDetailVec &&
            0 < orderPropertyDetailVec.size())
        {

            //We want to see the notes in order of thier being written
            DisplayListSort.sort(orderPropertyDetailVec, "propertyId");

            //set the OrderPropertyDetail to the latest object
            int lastItem = orderPropertyDetailVec.size() - 1;
            orderPropertyDetail = (OrderPropertyData)orderPropertyDetailVec.get(
                                          lastItem);
        }

        // rebill order
        OrderPropertyDataVector rebillProps = orderEjb.getOrderPropertyCollection(orderD.getOrderId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
        if (rebillProps != null && rebillProps.size() > 0) {
            OrderPropertyData rebillProp = (OrderPropertyData)rebillProps.get(0);
            if (Utility.isTrue(rebillProp.getValue())) {
                detailForm.setReBillOrder(true);
            }
        }


        OrderItemDescDataVector itemStatusDescV =
            orderEjb.getOrderItemDescCollection(Integer.parseInt(orderStatusId));

        DisplayListSort.sort(itemStatusDescV, "orderLineNum");

        //depending on the store type we may have to resort the items
        if(appUser.getUserStore() != null && appUser.getUserStore().getStoreType()!= null && RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(appUser.getUserStore().getStoreType().getValue())){
            //sort the items by distributor and po number.
            DisplayListSort.sort(itemStatusDescV,"erpPoNum");
        }

        detailForm.setOrderStatusDetail(orderStatusDetail);
        detailForm.setOrderPropertyList(orderPropertyDetailVec);
        detailForm.setOrderPropertyDetail(orderPropertyDetail);
        detailForm.setOrderItemDescList(itemStatusDescV);
        detailForm.setOrderStatus(orderStatusDetail.getOrderDetail().getOrderStatusCd());
        detailForm.setCustomerOrderNotes(
            orderEjb.getOrderPropertyCollection(Integer.parseInt(orderStatusId),
            RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS));

        detailForm.setShowCancelButton(true);
        for (int i = 0; null != itemStatusDescV && i < itemStatusDescV.size(); i++) {
          OrderItemDescData oid = (OrderItemDescData) itemStatusDescV.get(i);
          if(appUser != null && RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue())){
            if (oid.getInvoiceCustDetailList().size() > 0) {
              detailForm.setShowCancelButton(false);
            }
          } else {
            if (oid.getInvoiceDistDetailList().size() > 0) {
              detailForm.setShowCancelButton(false);
            }
          }
        }

        //set the placed by informaion
        try{
            UserData usr = userEjb.getUserByName(orderStatusDetail.getOrderDetail().getAddBy(), orderStatusDetail.getOrderDetail().getStoreId());
            detailForm.setOrderPlacedBy(usr.getFirstName() + " " +usr.getLastName());
        }catch(Exception e){
            if(!(e instanceof DataNotFoundException)){
                e.printStackTrace();
            }
            detailForm.setOrderPlacedBy("");
        }

        // set order total amounts
        if (null != orderStatusDetail)
        {
          setUiOrderAmounts(orderStatusDetail.getOrderDetail(), detailForm, orderStatusDetail);
          setUiWorkflowIndList(orderStatusDetail.getOrderDetail().getWorkflowInd(), detailForm);
          setUiOrderStatusList(orderStatusDetail.getOrderDetail().getOrderStatusCd(), detailForm, listServiceEjb);
          setUiOrderBudgetCd(orderStatusDetail.getOrderDetail().getOrderBudgetTypeCd(), detailForm);
          setOrderItemStatusList(detailForm, listServiceEjb);
          setPoItemStatusList(detailForm, listServiceEjb);
          setSimpleServiceFlag(detailForm,orderStatusDetail.getOrderItemList(),itemInfEjb);
        } else {
          detailForm.setTotalAmount(0);
        }

        CumulativeSummary sum = new CumulativeSummary(itemStatusDescV);
        detailForm.setAcceptedNum(sum.getAcceptedNum());
        detailForm.setBackorderedNum(sum.getBackorderedNum());
        detailForm.setInvoicedNum(sum.getInvoicedNum());
        detailForm.setLastDate(sum.getLastDate());
        detailForm.setOrderedNum(sum.getOrderedNum());
        detailForm.setReturnedNum(sum.getReturnedNum());
        detailForm.setShippedNum(sum.getShippedNum());
        detailForm.setSubstitutedNum(sum.getSubstitutedNum());


        detailForm.setNewContractIdS("");
        detailForm.setNewOrderDate("");

        //initialize the comstants lists for states and contries
        initConstantList(request);

        //get deliver dates
        Distributor distEjb = factory.getDistributorAPI();
        AddressData orderAddress = orderStatusDetail.getSiteAddress();
        String zipCode = null;
        if(orderAddress!=null){
            zipCode = orderAddress.getPostalCode();
        }

        if(zipCode!=null) {
            zipCode = zipCode.trim();
            if((orderAddress.getCountryCd()!=null)&&(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(orderAddress.getCountryCd()))){
                if (zipCode.length() > 5) {
                    zipCode = zipCode.substring(0, 5);
                }
            }
        }

        if(RefCodeNames.WORKFLOW_IND_CD.PROCESSED.equals(orderStatusDetail.getOrderDetail().getWorkflowInd()) ||
        RefCodeNames.WORKFLOW_IND_CD.SKIP.equals(orderStatusDetail.getOrderDetail().getWorkflowInd())){
            detailForm.setProcessCustomerWorkflow(false);
        }else{
            detailForm.setProcessCustomerWorkflow(true);
        }

        Date orderDate = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
        Date orderTime = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
        Hashtable deliveryDays = new Hashtable();
        Date nullDate = new Date();
        nullDate.setTime(0);
        deliveryDays.put("NULLTEST", nullDate);
        int siteId = detailForm.getOrderStatusDetail().getSiteAddress().getBusEntityId();
        if(orderDate!=null && orderTime!=null && zipCode!=null) {
          for(int ii=0; ii<itemStatusDescV.size();ii++) {
            OrderItemDescData oidD = (OrderItemDescData) itemStatusDescV.get(ii);
            oidD.setItemQuantityRecvdS(Integer.toString(oidD.getOrderItem().getTotalQuantityReceived()));
            if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(
                    oidD.getOrderItem().getOrderItemStatusCd())) {
              continue;
            }
            String distErpNum = oidD.getOrderItem().getDistErpNum();
            if(distErpNum==null) {
              continue;
            }
            Date deliveryDate = null;
            if(deliveryDays.containsKey(distErpNum)) {
              deliveryDate = (Date) deliveryDays.get(distErpNum);
            } else {
              if(siteId!=0) {
                deliveryDate = distEjb.getDeliveryDate(0, distErpNum, siteId, orderDate,orderTime, zipCode);
              }
              if(deliveryDate!=null) {
                deliveryDays.put(distErpNum, deliveryDate);
              } else {
                deliveryDays.put(distErpNum, nullDate);
              }
            }
            if(deliveryDate!=null && !deliveryDate.equals(nullDate)) {
              oidD.setDeliveryDate(deliveryDate);
            }

          }
        }

        NoteJoinViewVector noteJoinVwV = noteEjb.getSiteCrcNotes(siteId);
        detailForm.setSiteNotes(noteJoinVwV);

        InvoiceCustViewVector invInfo = null;
        InvoiceCustCritView crit = InvoiceCustCritView.createValue();
        crit.setWebOrderNum(Integer.parseInt(orderStatusDetail.getOrderDetail().getOrderNum()));
        invInfo = orderEjb.getInvoiceCustViewCollection(crit,true);
        detailForm.setInvoiceList(invInfo);
        detailForm.setSiteId((orderD.getSiteId() > 0) ? "" + orderD.getSiteId() : "");
	}
	catch (Exception e) { e.printStackTrace();
	}
	saveCheckBoxesState(form);
	return lUpdateErrors;
    }

    private static void setSimpleServiceFlag(StoreOrderDetailForm detailForm,OrderItemDataVector itms, ItemInformation itemInfoEjb) throws RemoteException {
        IdVector itemIds = new IdVector();
        IdVector assetIds = new IdVector();
        for (int i = 0, len = itms.size(); i < len; i++) {
            itemIds.add(new Integer(((OrderItemData) itms.get(i)).getItemId()));
            assetIds.add(new Integer(((OrderItemData) itms.get(i)).getAssetId()));
        }

        ItemDataVector itemDV = itemInfoEjb.getItemCollection(itemIds);
        detailForm.setSimpleServiceFlag(PipelineUtil.isSimpleServiceOrder(itemDV));
    }


    /**
     * <code>getOrderItemDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param OrderItemId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void getOrderItemDetail(HttpServletRequest request,
                                          ActionForm form, String OrderItemId)
                                   throws Exception
    {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();

        if (null == OrderItemId || "".equals(OrderItemId))
        {
            OrderItemId = (String)session.getAttribute("OrderItem.id");
        }

        OrderItemDescData orderItemDescD = orderEjb.getOrderItemDescByItem(Integer.parseInt(
                                                                                   OrderItemId));
        InvoiceDistDataVector invoiceDistList = orderEjb.getInvoiceDistCollectionByItem(Integer.parseInt(
                                                                                                OrderItemId));
        StoreOrderItemDetailForm detailForm = (StoreOrderItemDetailForm)form;
        detailForm.setOrderItemDesc(orderItemDescD);
        detailForm.setInvoiceDistList(invoiceDistList);

        int orderedNum = 0;
        int acceptedNum = 0;
        int backorderedNum = 0;
        int substitutedNum = 0;
        int shippedNum = 0;
        int invoicedNum = 0;
        Date lastDate = null;

        if (null != orderItemDescD)
        {
            orderedNum = orderItemDescD.getOrderItem().getTotalQuantityOrdered();
        }

        OrderItemActionDataVector itemActions = orderItemDescD.getOrderItemActionList();

        if (null != itemActions)
        {

            for (int j = 0; j < itemActions.size(); j++)
            {

                OrderItemActionData itemActionD = (OrderItemActionData)itemActions.get(
                                                          j);

                if (null == lastDate)
                {
                    lastDate = itemActionD.getActionDate();
                }
                else
                {

                    if (lastDate.before(itemActionD.getActionDate()))
                    {
                        lastDate = itemActionD.getActionDate();
                    }
                }

                String actionCd = itemActionD.getActionCd();

                if ("Accepted".equals(actionCd))
                {
                    acceptedNum += itemActionD.getQuantity();
                }
                else if ("Substituted".equals(actionCd))
                {
                    substitutedNum += itemActionD.getQuantity();
                }
                else if ("Invoiced".equals(actionCd))
                {
                    invoicedNum += itemActionD.getQuantity();
                }
                else if ("Shipped".equals(actionCd))
                {
                    shippedNum += itemActionD.getQuantity();
                }
            } // end of loop of itemActions
        } //end of if null != itemActions

        if (0 == shippedNum)
        {
            backorderedNum = 0;
        }
        else
        {
            backorderedNum = orderedNum - shippedNum;
        }

        //backorderedNum = orderedNum - shippedNum;
        detailForm.setAcceptedNum(acceptedNum);
        detailForm.setSubstitutedNum(substitutedNum);
        detailForm.setShippedNum(shippedNum);
        detailForm.setInvoicedNum(invoicedNum);
        detailForm.setBackorderedNum(backorderedNum);
        detailForm.setLastDate(lastDate);
    }

    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void getOrderNotes(HttpServletRequest request,
                                     ActionForm form, String orderId,
                                     String orderItemId, String noteId) throws Exception {
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        throw new Exception("Without APIAccess.");
      }

      Order orderEjb = factory.getOrderAPI();
      orderId = getOrderId(orderId, session);
      OrderPropertyDataVector orderNotes = null;
      if (null != orderItemId && !"".equals(orderItemId)) {
        orderNotes = orderEjb.getOrderItemPropertyCollection(Integer.parseInt(orderItemId),
                                                             RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
      } else if (null != orderId && !"".equals(orderId)) {
        orderNotes = orderEjb.getOrderNotes(Integer.parseInt(orderId));
      }

      if (orderNotes == null) {
        orderNotes = new OrderPropertyDataVector();
      }
      OrderPropertyData note = OrderPropertyData.createValue();

      StoreOrderNoteForm orderNoteForm = (StoreOrderNoteForm) form;
      if (null != noteId && !"".equals(noteId)) {
        note = orderEjb.getOrderProperty(Integer.parseInt(noteId), RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        if (null != note) {
          orderNoteForm.setOrderPropertyDetail(note);
        }
      }

      orderNoteForm.setOrderPropertyList(orderNotes);
    }

    /**
     *
     */
    private static void setCustomerOrderNotes(StoreOrderDetailForm form)
       throws Exception{
        OrderPropertyDataVector custOrderNotes = new OrderPropertyDataVector();
        Iterator it = form.getOrderPropertyList().iterator();
        while(it.hasNext()){
            OrderPropertyData op = (OrderPropertyData) it.next();
            if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS.equals(op.getOrderPropertyTypeCd())){
                custOrderNotes.add(op);
            }
        }
        form.setCustomerOrderNotes(custOrderNotes);
    }


    private static String getOrderId(String orderId, HttpSession session) {
      if (null == orderId || "".equals(orderId)) {
        StoreOrderDetailForm orderDetailForm = (StoreOrderDetailForm) session.getAttribute("STORE_ORDER_DETAIL_FORM");
        if (orderDetailForm != null && orderDetailForm.getOrderStatusDetail() != null &&
            orderDetailForm.getOrderStatusDetail().getOrderDetail() != null) {
          orderId = String.valueOf(orderDetailForm.getOrderStatusDetail().getOrderDetail().getOrderId());
        } else {
          orderId = (String) session.getAttribute("OrderStatus.id");
        }
      }
      return orderId;
    }

    /**
     * <code>AddOrderNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void addOrderNote(HttpServletRequest request,
                                    ActionForm form, String orderId,
                                    String orderItemId, String noteType) throws Exception {

      StoreOrderNoteForm dForm = (StoreOrderNoteForm) form;
      HttpSession session = request.getSession();
      dForm = new StoreOrderNoteForm();

      if (null == noteType || "".equals(noteType)) {
        noteType = new String("order");
      }

      dForm.setOrderNoteType(noteType);
      if ("order".equals(noteType)) {
        if (null == orderId || "".equals(orderId)) {
          StoreOrderDetailForm orderDetailForm = (StoreOrderDetailForm) session.getAttribute("STORE_ORDER_DETAIL_FORM");

          if (null != orderDetailForm) {
            orderId = String.valueOf(orderDetailForm.getOrderStatusDetail().getOrderDetail().getOrderId());
          } else {
            orderId = (String) session.getAttribute("OrderStatus.id");
          }
        }
        dForm.setOrderId(orderId);
      } else if ("item".equals(noteType)) {
        dForm.setOrderItemId(orderItemId);
      }

      session.setAttribute("STORE_ORDER_NOTE_FORM", dForm);
    }

    /**
     * <code>saveNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors saveNote(HttpServletRequest request,
                                        ActionForm form) throws Exception {

      ActionErrors lUpdateErrors = new ActionErrors();
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        throw new Exception("Without APIAccess.");
      }

      StoreOrderNoteForm sForm = (StoreOrderNoteForm) form;
      session.setAttribute("STORE_ORDER_NOTE_FORM", sForm);

      if (sForm != null) {
        if (sForm.getOrderPropertyDetail().getValue().length() == 0) {
          lUpdateErrors.add("notes",
                            new ActionError("variable.empty.error", "Notes"));
        } else if (sForm.getOrderPropertyDetail().getValue().length() >= 2000) {
          lUpdateErrors.add("notes",
                            new ActionError("error.simpleGenericError",
                                            new String("The note is too large, please limit each note to 2000 characters")));
        }
      }

      if (lUpdateErrors.size() > 0) {
        // Report the errors to allow for edits.
        return lUpdateErrors;
      }

      String noteType = sForm.getOrderNoteType();

      if (null == noteType || "".equals(noteType)) {
        noteType = new String("order");
      }

      Order orderEjb = factory.getOrderAPI();
      OrderPropertyData OPD = OrderPropertyData.createValue();

      if ("order".equals(noteType)) {
        String orderId = sForm.getOrderId();
        OPD.setOrderId(Integer.parseInt(orderId));
      } else if ("item".equals(noteType)) {
        String orderItemId = sForm.getOrderItemId();
        OPD.setOrderItemId(Integer.parseInt(orderItemId));
      }

      if (sForm.getOrderPropertyDetail().getValue().length() > 250) {
        OPD.setShortDesc(sForm.getOrderPropertyDetail().getValue().substring(0, 250));
      } else {
        OPD.setShortDesc(sForm.getOrderPropertyDetail().getValue());
      }

      OPD.setValue(sForm.getOrderPropertyDetail().getValue());
      OPD.setOrderPropertyStatusCd("ACTIVE");
      OPD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
      OPD.setAddBy(sForm.getOrderPropertyDetail().getAddBy());
      OPD.setModBy(sForm.getOrderPropertyDetail().getAddBy());
      orderEjb.addNote(OPD);

      return lUpdateErrors;
    }

    public static ActionErrors updateCustomerNote(HttpServletRequest request,
    ActionForm form)throws Exception{
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

        if (null == factory){
            throw new Exception("Without APIAccess.");
        }

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        if(appUser == null){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,
                                  new ActionError("simple.generic.error", "Could not find user"));
        }
        StoreOrderDetailForm sForm = (StoreOrderDetailForm)form;
        session.setAttribute("STORE_ORDER_DETAIL_FORM", sForm);
        if (sForm != null){
            if (!Utility.isSet(sForm.getCustomerComment())){
                lUpdateErrors.add("customerComment",
                                  new ActionError("variable.empty.error", "comments"));
            }else if (sForm.getCustomerComment().length() >= 250){
                lUpdateErrors.add("customerComment",
                                  new ActionError("variable.to.large.error", "comments"));
            }
        }

        if (!(lUpdateErrors.size() == 0)){
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        int orderStatusId = sForm.getOrderStatusDetail().getOrderDetail().getOrderId();
        Order orderEjb = factory.getOrderAPI();
        OrderPropertyData OPD = OrderPropertyData.createValue();
        OPD.setOrderId(orderStatusId);
        OPD.setValue(sForm.getCustomerComment());
        OPD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
        OPD.setOrderPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        OPD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);

        OPD.setAddBy(appUser.getUserName());
        OPD.setModBy(appUser.getUserName());
        OPD = orderEjb.addNote(OPD);


        if(lUpdateErrors.size() == 0){
            if(sForm.getCustomerOrderNotes() == null){
                sForm.setCustomerOrderNotes(new OrderPropertyDataVector());
            }
            sForm.getCustomerOrderNotes().add(0,OPD);
            sForm.setCustomerComment("");
        }
        return lUpdateErrors;
    }

    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors updateNote(HttpServletRequest request,
                                          ActionForm form)
                                   throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        StoreOrderDetailForm sForm = (StoreOrderDetailForm)form;
        session.setAttribute("STORE_ORDER_DETAIL_FORM", sForm);

        if (sForm != null)
        {

            if (sForm.getOrderPropertyDetail().getValue().length() == 0)
            {
                lUpdateErrors.add("notes",
                                  new ActionError("variable.empty.error",
                                                  "Notes"));
            }
            else if (sForm.getOrderPropertyDetail().getValue().length() >= 2000)
            {
                lUpdateErrors.add("notes",
                                  new ActionError("error.simpleGenericError",
                                                  new String("The note is too large, please keep it in 2000 characters")));
            }
        }

        if (lUpdateErrors.size() > 0)
        {

            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        int orderStatusId = sForm.getOrderStatusDetail().getOrderDetail().getOrderId();
        Order orderEjb = factory.getOrderAPI();
        OrderData orderStatusDetail = orderEjb.getOrderStatus(orderStatusId);
        OrderPropertyData OPD = OrderPropertyData.createValue();
        OPD.setOrderId(orderStatusId);

        if (sForm.getOrderPropertyDetail().getValue().length() > 250)
        {
            OPD.setShortDesc(sForm.getOrderPropertyDetail().getValue().substring(
                                     0, 250));
        }
        else
        {
            OPD.setShortDesc(sForm.getOrderPropertyDetail().getValue());
        }

        OPD.setValue(sForm.getOrderPropertyDetail().getValue());
        OPD.setOrderPropertyStatusCd("ACTIVE");
        OPD.setOrderPropertyTypeCd("ORDER_NOTE");
        OPD.setAddBy(sForm.getOrderPropertyDetail().getAddBy());
        OPD.setModBy(sForm.getOrderPropertyDetail().getAddBy());

        //OPD.setAddDate();
        //OPD.setModDate();
        orderEjb.addNote(OPD);

        return lUpdateErrors;
    }

    /**
     * <code>getOrderItemList</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void getOrderItemList(HttpServletRequest request,
                                        ActionForm form, String orderId,
                                        String erpPoNum)
                                 throws Exception
    {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        OrderStatusDescData orderStatusDetail = null;

        try
        {
            orderStatusDetail = orderEjb.getOrderStatusDesc(Integer.parseInt(
                                                                    orderId));
        }
        catch (Exception e)
        {
            throw e;
        }

        OrderItemDescDataVector itemStatusDescV = new OrderItemDescDataVector();

        try
        {
            itemStatusDescV = orderEjb.getOrderItemDescCollection(Integer.parseInt(
                                                                          orderId),
                                                                  erpPoNum);
        }
        catch (Exception e)
        {
            throw e;
        }

        StoreOrderItemSearchForm detailForm = (StoreOrderItemSearchForm)form;
        detailForm.setOrderDesc(orderStatusDetail);
        detailForm.setOrderItemDescList(itemStatusDescV);
        detailForm.setOrderId(orderId);
        detailForm.setErpPoNum(erpPoNum);
    }


    /**
     * <code>moveSiteContract</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors moveSiteContract(HttpServletRequest request,
                                                ActionForm form)
                                         throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              "Can't find application user information in session"));

            return lUpdateErrors;
        }
        else
        {

            //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));

                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // only order with PENDING_APPROVAL status can be updated
        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail()
          .getOrderStatusCd();

        if ( isOrderStatusValid(orderStatusCd, detailForm.getFullControlFl()) == false )
        {
            lUpdateErrors.add("orderdetail",
                new ActionError("error.simpleGenericError",
                new String("Current order status is " + orderStatusCd))
                );

            return lUpdateErrors;
        }

        // Get a reference to the admin facade.
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
        Catalog catalogEjb = factory.getCatalogAPI();
        Contract contractEjb = factory.getContractAPI();
        String userName = (String)session.getAttribute(Constants.USER_NAME);
        OrderData orderD = detailForm.getOrderStatusDetail().getOrderDetail();
        int siteId = detailForm.getOrderStatusDetail().getSiteAddress().getBusEntityId();
        CatalogData sitecatD = null;

        try
        {
            sitecatD = catalogInfoEjb.getSiteCatalog(siteId);
        }
        catch (Exception e)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Error occured when trying to find this site's catalog, " + e.toString())));

            return lUpdateErrors;
        }

        if (null == sitecatD)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find this site's catalog")));

            return lUpdateErrors;
        }

        String contractIdS = detailForm.getNewContractIdS().trim();
        int contractId = detailForm.getOrderStatusDetail().getOrderDetail().getContractId();
        int accountId = detailForm.getOrderStatusDetail().getAccountId();
        boolean contractChangedFlag = false;

        if (null != contractIdS && !"".equals(contractIdS))        {

            // see if we can parse the inputed contract id
            try
            {
                contractId = Integer.parseInt(contractIdS.trim());
            }
            catch (Exception e)
            {
                lUpdateErrors.add
                    ("orderdetail",
                     new ActionError("error.simpleGenericError",
                                     "No contract available [contractId="
                                     + contractIdS +"]"));

                return lUpdateErrors;
            }

            // see if we can get the contract data according to the contract id
            ContractData contractD = null;

            try
            {
                contractD = contractEjb.getContractByAccount(contractId,
                                                             accountId);
            }
            catch (Exception e)
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  new String("Can't find contract[contractId=" + contractId +
                                                                     "] or this contract belongs to another account")));

                return lUpdateErrors;
            }

            // set the order account erp num as the new one
            if (null != contractD)    {

                if (sitecatD.getCatalogId() != contractD.getCatalogId())
                {

                    try           {
                        catalogEjb.removeCatalogSiteAssoc(0, siteId, userName);
                        catalogEjb.addCatalogAssoc(contractD.getCatalogId(),
                                                   siteId,
                                                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
                    }
                    catch (Exception e)
                        {
                            lUpdateErrors.add("orderdetail",
                                              new ActionError(
                                                              "error.simpleGenericError",
                                                              new String("Can't update site to use this contract[contractId=" + contractId +
                                                                         "]")));

                            return lUpdateErrors;
                        }
                }
            }
            else
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  new String("Can't find contract[contractId=" + contractId +
                                                                     "] or this contract belongs to another account")));

                return lUpdateErrors;
            }
        }

        return lUpdateErrors;
    }

    public static ActionErrors approvePendingApprovalOrder
      (HttpServletRequest request,
       ActionForm form,
       String orderStatusId)
      throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        OrderData orderD = detailForm.getOrderStatusDetail().getOrderDetail();
        String orderTypeCd = orderD.getOrderTypeCd();
        String newOrderStatusCd =
        (RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderTypeCd))?
                 RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION:
                 RefCodeNames.ORDER_STATUS_CD.ORDERED;
        lUpdateErrors = changePendingApprovalOrderStatus
              (request, form, orderStatusId, newOrderStatusCd);
        return lUpdateErrors;
    }

    /**
     * <code>changePendingApprovalOrderStatus</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors changePendingApprovalOrderStatus
      (HttpServletRequest request,
       ActionForm form,
       String orderStatusId,
       String pNewOrderStatusCd)
      throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));

            return lUpdateErrors;
        }
        else
        {

            //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permission"));

                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail().getOrderStatusCd();

        boolean orderStatusValidFl = isOrderStatusValid(orderStatusCd,detailForm.getFullControlFl());

        if (!orderStatusValidFl)
        {
            lUpdateErrors.add("orderdetail",
                new ActionError("error.simpleGenericError",
                new String("Current order status is " + orderStatusCd))
                );

            return lUpdateErrors;
        }

        // Get a reference to the admin facade.
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
            IntegrationServices integrationEjb =  factory.getIntegrationServicesAPI();

        String userName = (String)session.getAttribute(Constants.USER_NAME);
        OrderData orderD = detailForm.getOrderStatusDetail().getOrderDetail();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

        //process handling charges
        if(!(RefCodeNames.ORDER_STATUS_CD.REJECTED.equals(pNewOrderStatusCd))) {
          int contractId = orderD.getContractId();
          OrderItemDescDataVector itemDescDV =  detailForm.getOrderItemDescList();
          OrderItemDataVector orderItemDV = new OrderItemDataVector();
          for(int ii=0; ii<itemDescDV.size(); ii++) {
            OrderItemDescData orderItemDescD = (OrderItemDescData) itemDescDV.get(ii);
            OrderItemData orderItemD = orderItemDescD.getOrderItem();
            orderItemDV.add(orderItemD);
          }
          ActionErrors ae =
            setHandlingCharges(request,detailForm, shoppingServEjb, orderD, orderItemDV, contractId);
          if(ae.size()>0) {
            return ae;
          }
          if(detailForm.getHandlingChangedFlag()) {
            orderD.setTotalMiscCost(detailForm.getTotalMiscCost());
            orderD.setTotalFreightCost(detailForm.getTotalFreightCost());
          }
        }
        if(!RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(pNewOrderStatusCd)) {
          orderD.setOrderStatusCd(pNewOrderStatusCd);
        }
        if(detailForm.isProcessCustomerWorkflow()){

            if(!RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS.equals(orderD.getWorkflowInd())){
                orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS);
            }
        }else{
            if(!RefCodeNames.WORKFLOW_IND_CD.SKIP.equals(orderD.getWorkflowInd())){
                orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.SKIP);
            }
        }

        // update the order info
        if (true == orderD.isDirty())
        {

            // Update the db with any changes made.
          orderEjb.updateOrder(orderD);
            if ( detailForm.isBypassOrderRouting() ) {
                // flag this order for no routing.
                orderEjb.setMetaAttribute
                    (orderD.getOrderId(),
                     Order.BYPASS_ORDER_ROUTING,
                     "TRUE",
                     userName);
            }

            ProcessOrderResultData ores =
                integrationEjb.reprocessOrderRequest
                (orderD.getOrderId(),
                 pNewOrderStatusCd,
                 userName);

     OrderData resOrderD = orderEjb.getOrderStatus(orderD.getOrderId());
     orderD.setWorkflowInd(resOrderD.getWorkflowInd());

            if(!(RefCodeNames.ORDER_STATUS_CD.REJECTED.equals
                 (pNewOrderStatusCd))) {
                // This is the final status for the order
                // after reprocessing.
                orderD.setOrderStatusCd(ores.getOrderStatusCd());
            }
            else {
                orderD.setOrderStatusCd(pNewOrderStatusCd);
            }

            orderD.setModBy(userName);
            // Update the final status of the orders.
            orderEjb.updateOrder(orderD);

            if (  ores.getOrderStatusCd().equals
                  (RefCodeNames.ORDER_STATUS_CD.ORDERED) ||
                  ores.getOrderStatusCd().equals
                  (RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
                  ores.getOrderStatusCd().equals
                  (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE) ||
                  ores.getOrderStatusCd().equals
                  (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
                  pNewOrderStatusCd.equals
                  (RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW) ||
                  pNewOrderStatusCd.equals
                  (RefCodeNames.ORDER_STATUS_CD.REJECTED)  ) {

            }
            else {
                lUpdateErrors.add
                    ("orderdetail",
                     new ActionError("error.simpleGenericError",
                                     ores));
            }
        }

        return lUpdateErrors;
    }

    /**
     * <code>checkContractForPendingApprovalOrder</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors checkContractForPendingApprovalOrder(HttpServletRequest request,
                                                                    ActionForm form,
                                                                    String orderStatusId)
        throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));

            return lUpdateErrors;
        }
        else
        {

            //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));

                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // only order with PENDING_APPROVAL status can be updated
        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail()
          .getOrderStatusCd();

        if ( isOrderStatusValid(orderStatusCd,detailForm.getFullControlFl()) == false )
        {
            lUpdateErrors.add("orderdetail",
                new ActionError("error.simpleGenericError",
                new String("Current order status is " + orderStatusCd))
                );

            return lUpdateErrors;
        }

        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        // set the new contract info according to the contract id inputted
        lUpdateErrors = checkContractForOrder(request, form, orderStatusId);

        if (0 < lUpdateErrors.size())
        {

            return lUpdateErrors;
        }

        return lUpdateErrors;
    }

    /**
     * <code>checkContractForOrder</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors checkContractForOrder(HttpServletRequest request,
                                                     ActionForm form,
                                                     String orderStatusIdS)
                                              throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // Get a reference to the admin facade.
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Contract contractEjb = factory.getContractAPI();

        // check the new contract info according to the contract id inputted
        String contractIdS = detailForm.getNewContractIdS().trim();
        int contractId = detailForm.getOrderStatusDetail().getOrderDetail().getContractId();
        int accountId = detailForm.getOrderStatusDetail().getAccountId();

        if (null != contractIdS && !"".equals(contractIdS))
        {

            // see if we can parse the inputed contract id
            try
            {
                contractId = Integer.parseInt(contractIdS);
            }
            catch (Exception e)
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  new String("The Contract Id is not a valid amount value")));

                return lUpdateErrors;
            }

            // see if we can get the contract data according to the contract id
            ContractData contractD = null;

            try
            {
                contractD = contractEjb.getContractByAccount(contractId,
                                                             accountId);
            }
            catch (Exception e)
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  new String("Error occurs when trying to find contract[contractId=" + contractId +
                                                                     "] for this account")));

                return lUpdateErrors;
            }

            if (null != contractD)
            {

                ContractItemDataVector contractItemV = new ContractItemDataVector();
                contractItemV = contractEjb.getItems(contractD.getContractId());

                if (null != contractItemV && 0 < contractItemV.size())
                {

                    OrderItemDescDataVector orderItemDescV =
                            detailForm.getOrderItemDescList();

                    if (null != orderItemDescV &&
                        0 < orderItemDescV.size())
                    {

                        String errorMessage = new String("");

                        for (int i = 0; i < orderItemDescV.size(); i++)
                        {

                            OrderItemDescData orderItemDescD =
                                    (OrderItemDescData)orderItemDescV.get(i);
                            boolean matchItemFlag = false;

                            for (int j = 0; j < contractItemV.size(); j++)
                            {

                                ContractItemData contractItemD =
                                        (ContractItemData)contractItemV.get(j);

                                if (null != orderItemDescD &&
                                    null != contractItemD &&
                                    contractItemD.getItemId() == orderItemDescD
                                              .getOrderItem().getItemId())
                                {
                                    matchItemFlag = true;

                                    break;
                                }
                            }

                            if (false == matchItemFlag)   {
                                errorMessage += " Item[" + i +
                                    "] [item info= " +
                                    orderItemDescD +
                                    "] -- is not in the contract  ";
                            }
                        }

                        if (!"".equals(errorMessage))
                        {
                            lUpdateErrors.add("orderdetail",
                                              new ActionError(
                                                      "error.simpleGenericError",
                                                      errorMessage));

                            return lUpdateErrors;
                        }
                    }
                }
                else
                {
                    lUpdateErrors.add("orderdetail",
                                      new ActionError(
                                              "error.simpleGenericError",
                                              new String("Can't find contract items for this contract[contractId=" + contractId +
                                                                 "]")));

                    return lUpdateErrors;
                }
            }
            else
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  new String("Can't find contract[contractId=" + contractId +
                                                                     "] or this contract belongs to another account")));

                return lUpdateErrors;
            }
        }

        return lUpdateErrors;
    }

    /**
     * <code>cancelSelectedOrderItems</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors OLDcancelSelectedOrderItems(HttpServletRequest request,
                                                        ActionForm form,
                                                        String orderStatusId)
                                                 throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));

            return lUpdateErrors;
        }
        else
        {

            //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));

                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // only order with PENDING_APPROVAL status can be updated
        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail()
          .getOrderStatusCd();
/*
        if ( isOrderStatusValid(orderStatusCd) == false )
        {
            lUpdateErrors.add("orderdetail",
                new ActionError("error.simpleGenericError",
                new String("Current order status is " + orderStatusCd))
                );

            return lUpdateErrors;
        }
*/
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }
//        StoreOrder sorderEjb = factory.getOrderAPI();

        Order orderEjb = factory.getOrderAPI();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
        String[] selectedItems = detailForm.getSelectItems();

        if (0 != selectedItems.length) {
          int[] delOrderItemIds = new int[selectedItems.length];
          for (int ii = 0; ii < selectedItems.length; ii++) {
            String orderItemIdS = selectedItems[ii];
            delOrderItemIds[ii] = Integer.parseInt(orderItemIdS );
          }
          OrderStatusDescData orderStatusDescD = detailForm.getOrderStatusDetail();
          OrderData orderD = orderStatusDescD.getOrderDetail();
          OrderItemDataVector orderItemDV = orderStatusDescD.getOrderItemList();
          OrderItemDataVector nonDelItemDV = new OrderItemDataVector();
          IdVector selectedItemIdV = new IdVector();
          BigDecimal totalPrice = new BigDecimal(0);
          for(Iterator iter=orderItemDV.iterator(); iter.hasNext();) {
            OrderItemData orderItemD = (OrderItemData) iter.next();
            int orderItemId = orderItemD.getOrderItemId();
            boolean delFl = false;
            for (int i = 0; i < delOrderItemIds.length; i++) {
              if(orderItemId==delOrderItemIds[i]) {
                selectedItemIdV.add(new Integer(orderItemId));
                delFl = true;
                break;
              }
            }
            if(!delFl) {
              String itemStatus = orderItemD.getOrderItemStatusCd();
              if(!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)){
                nonDelItemDV.add(orderItemD);
                BigDecimal amount = orderItemD.getCustContractPrice();
                if(amount!=null) {
                  int qty = orderItemD.getTotalQuantityOrdered();
                  BigDecimal price = amount.multiply(new BigDecimal(qty));
                  price = price.setScale(2,BigDecimal.ROUND_HALF_UP);
                  totalPrice = totalPrice.add(price);

                }
              }
            }
          }
          totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
          orderD.setOriginalAmount(totalPrice);
          orderD.setTotalPrice(totalPrice);

            if (null != selectedItemIdV && 0 < selectedItemIdV.size())
            {
              lUpdateErrors = setHandlingCharges(request, detailForm,
                  shoppingServEjb,orderD, nonDelItemDV, orderD.getContractId());
              if(lUpdateErrors.size()>0) {
                return lUpdateErrors;
              }
              orderD.setTotalFreightCost(detailForm.getTotalFreightCost());
              orderD.setTotalMiscCost(detailForm.getTotalMiscCost());


                try
                {
                    String userName = appUser.getUser().getUserName();
                    orderEjb.cancelOrderItems(orderD,selectedItemIdV, userName, false);
                }
                catch (Exception e)
                {
                }
            }
            setUiOrderAmounts(orderD, detailForm, null);

        }

        return lUpdateErrors;
    }

    /**
     * <code>cancelSelectedOrderItems</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors cancelSelectedOrderItems(HttpServletRequest request,
                                                        ActionForm form,
                                                        String orderStatusId)
        throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        if (null == appUser) {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));
            return lUpdateErrors;
        } else {
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));
                return lUpdateErrors;
            }
        }
        StoreOrderChangeRequestData changeD = StoreOrderChangeRequestData.createValue();
        changeD.setUserName(appUser.getUser().getUserName());
        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;

        String[] selectedItems = detailForm.getSelectItems();
        if (0 != selectedItems.length) {
          int[] delOrderItemIds = new int[selectedItems.length];
          for (int ii = 0; ii < selectedItems.length; ii++) {
            String orderItemIdS = selectedItems[ii];
            delOrderItemIds[ii] = Integer.parseInt(orderItemIdS );
          }
          OrderStatusDescData orderStatusDescD = detailForm.getOrderStatusDetail();
          // order information
          OrderData orderD = orderStatusDescD.getOrderDetail();
          changeD.setOrderData(orderD);
          OrderItemDataVector orderItemDV = orderStatusDescD.getOrderItemList();
          // order items information
          changeD.setOrderItems(orderItemDV);
          IdVector selectedItemIdV = new IdVector();

          for(Iterator iter=orderItemDV.iterator(); iter.hasNext();) {
            OrderItemData orderItemD = (OrderItemData) iter.next();
            int orderItemId = orderItemD.getOrderItemId();
            for (int i = 0; i < delOrderItemIds.length; i++) {
              if(orderItemId==delOrderItemIds[i]) {
                selectedItemIdV.add(new Integer(orderItemId));
                break;
              }
            }
          }
          changeD.setDelOrderItems(selectedItemIdV);
          //Get handling from the form
          String freightCostS = detailForm.getTotalFreightCostS();
          BigDecimal pageFreight = new BigDecimal(0);
          if (freightCostS != null) {
            try {
              pageFreight = CurrencyFormat.parse(freightCostS.trim());
            } catch (Exception pe){
              lUpdateErrors.add("exceptiondetail",
                                new ActionError("error.invalidNumberAmount", "Freight"));
              log.info("invalid freight string=" + freightCostS);
              return lUpdateErrors;
            }
            // total freight cost
            changeD.setTotalFreightCost(pageFreight);
          }
          String miscCostS = detailForm.getTotalMiscCostS();
          BigDecimal pageMisc = new BigDecimal(0);
          if (miscCostS != null) {
            try {
              pageMisc = CurrencyFormat.parse(miscCostS);
            } catch (Exception pe){
              lUpdateErrors.add("exceptiondetail",
                                new ActionError("error.invalidNumberAmount", "Handling"));
              log.info("invalid misc cost string=" + miscCostS);
              return lUpdateErrors;
            }
            // total freight cost
            changeD.setTotalMiscCost(pageMisc);
          }


            String smallOrderFeeStr = detailForm.getSmallOrderFeeStr();
            BigDecimal pageSmallOrderFee;
            if (Utility.isSet(smallOrderFeeStr)) {
                try {
                    pageSmallOrderFee = CurrencyFormat.parse(smallOrderFeeStr);
                } catch (Exception pe) {
                    lUpdateErrors.add("exceptiondetail", new ActionError("error.invalidNumberAmount", "Small Order Fee"));
                    return lUpdateErrors;
                }
                changeD.setSmallOrderFeeAmt(pageSmallOrderFee);
            }

            String fuelSurchargeStr = detailForm.getFuelSurchargeStr();
            BigDecimal pageFuelSurcharge;
            if (Utility.isSet(fuelSurchargeStr)) {
                try {
                    pageFuelSurcharge = CurrencyFormat.parse(fuelSurchargeStr);
                } catch (Exception pe) {
                    lUpdateErrors.add("exceptiondetail", new ActionError("error.invalidNumberAmount", "Fuel Surcharge"));
                    return lUpdateErrors;
                }
                changeD.setFuelSurchargeAmt(pageFuelSurcharge);
            }

            String discountStr = detailForm.getDiscountStr();
            BigDecimal pageDiscount = null;
            if (Utility.isSet(discountStr)) {
                try {
                    pageDiscount = CurrencyFormat.parse(discountStr);
                } catch (Exception pe) {
                    lUpdateErrors.add("exceptiondetail", new ActionError("error.invalidNumberAmount", "Discount"));
                    return lUpdateErrors;
                }
                changeD.setDiscountAmt(pageDiscount);
            }

           changeD.setOrderMeta(detailForm.getOrderStatusDetail().getOrderMetaData());

       APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
       if (null == factory) {
           throw new Exception("Without APIAccess.");
       }
       StoreOrder storeOrderEjb = factory.getStoreOrderAPI();
       if (null != selectedItemIdV && 0 < selectedItemIdV.size()) {
         try {
           orderD = storeOrderEjb.cancelOrderItems(changeD);
         } catch (Exception e) {
           log.info("Can't cancel selected order items. Order id="+orderD.getOrderId());
         }
       }
       setUiOrderAmounts(orderD, detailForm, null);
     }

  return lUpdateErrors;
}




    /**
     * <code>addOrderItems</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors addOrderItems(HttpServletRequest request,
                                             ActionForm form,
                                             String orderStatusId)
                                      throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);
        String userName = (String)session.getAttribute(Constants.USER_NAME);

        if (null == appUser)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));

            return lUpdateErrors;
        }
        else
        {

            //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));

                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // only order with PENDING_APPROVAL status can be updated
        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail()
          .getOrderStatusCd();

//        if ( isOrderStatusValid(orderStatusCd,detailForm.getFullControlFl()) == false )
//        {
//            lUpdateErrors.add("orderdetail",
//                new ActionError("error.simpleGenericError",
//                new String("Current order status is " + orderStatusCd))
//                );
//
//            return lUpdateErrors;
//        }

        OrderItemDescDataVector orderItemDescList = detailForm.getOrderItemDescList();

        if (null == orderItemDescList)
        {
            orderItemDescList = new OrderItemDescDataVector();
        }

        // get the maxium lineNum
        int maxLineNum = 0;
        int maxCustLine = 0;
        String saleType = null;
        for (int i = 0; i < orderItemDescList.size(); i++)
        {

            OrderItemDescData orderItemDescD = (OrderItemDescData)orderItemDescList.get(i);
            saleType = orderItemDescD.getOrderItem().getSaleTypeCd();
            if (maxLineNum < orderItemDescD.getOrderItem().getOrderLineNum()){
                maxLineNum = orderItemDescD.getOrderItem().getOrderLineNum();
            }
            if (maxCustLine < orderItemDescD.getOrderItem().getCustLineNum()){
                maxCustLine = orderItemDescD.getOrderItem().getCustLineNum();
            }
        }

        OrderItemDescData newOrderItemDescD = OrderItemDescData.createValue();
        OrderItemData newOrderItemD = OrderItemData.createValue();
        newOrderItemD.setOrderId(orderId);
        newOrderItemDescD.setAssetInfo(AssetData.createValue());
        newOrderItemDescD.setItemInfo(ItemData.createValue());
        newOrderItemD.setOrderLineNum(maxLineNum + 1);
        newOrderItemD.setCustLineNum(maxCustLine + 1);
        newOrderItemD.setSaleTypeCd(saleType);
        newOrderItemD.setTotalQuantityOrdered(1);
        newOrderItemD.setAddBy(userName);
        newOrderItemDescD.setOrderItem(newOrderItemD);
        orderItemDescList.add(newOrderItemDescD);

        detailForm.setOrderItemDescList(orderItemDescList);

        return lUpdateErrors;
    }

    /**
     * <code>updatePendingApprovalOrder</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
/*    public static ActionErrors OLDupdatePendingApprovalOrder2(HttpServletRequest request,
                                                           ActionForm form,
                                                           String orderStatusId)
        throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                                                    Constants.APP_USER);

        if (null == appUser)	    {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));

            return lUpdateErrors;
        }


        //String userRole = appUser.getUser().getWorkflowRoleCd();
        String userType = appUser.getUser().getUserTypeCd();
        if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
            !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
            !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))   {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              "Current user does not have enough permissions"));

            return lUpdateErrors;
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // only order with PENDING_APPROVAL status can be updated
        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail()
            .getOrderStatusCd();

        if ( isOrderStatusValid(orderStatusCd,detailForm.getFullControlFl()) == false ){
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Current order status is " + orderStatusCd))
                              );

            return lUpdateErrors;
        }

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory)    {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        Account accountEjb = factory.getAccountAPI();
        //       Site siteEjb = factory.getSiteAPI();
        Distributor distEjb = factory.getDistributorAPI();
        CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
        Contract contractEjb = factory.getContractAPI();
        IntegrationServices integrationEjb = factory.getIntegrationServicesAPI();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
        String userName = (String)session.getAttribute(Constants.USER_NAME);
        OrderData orderD = detailForm.getOrderStatusDetail().getOrderDetail();
        boolean consolidatedOrderFl =
            (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd()))?
            true:false;
        int accountId = detailForm.getOrderStatusDetail().getAccountId();

        // set the new contract info according to the contract id inputted
        lUpdateErrors = checkContractForOrder2(request, form, orderStatusId);
        if (0 < lUpdateErrors.size())	    {
            return lUpdateErrors;
        }

        // set the new orderItem info according to input

        // New Order Date
        if (!"".equals(detailForm.getNewOrderDate())) {
          DateFormat mDateFormat = (new SimpleDateFormat("mm/dd/yyyy"));
          try{
            orderD.setRevisedOrderDate(mDateFormat.parse(detailForm.getNewOrderDate()));
            orderD.setRevisedOrderTime(mDateFormat.parse(detailForm.getNewOrderDate()));
          } catch (Exception e)   {
              lUpdateErrors.add
                  ("orderdetail",
                   new ActionError("error.simpleGenericError",
                                   "Invalid new order date string=["
                                   + detailForm.getNewOrderDate() +  "]. "));

              return lUpdateErrors;
          }
        }

        boolean contractChangedFlag = false;
        String contractIdS = detailForm.getNewContractIdS();
        int contractId = orderD.getContractId();
        int catalogId = 0;

        if (null != contractIdS
            && contractIdS.trim().length() > 0
            && !"".equals(contractIdS))	    {
            contractChangedFlag = true;

            // see if we can parse the inputed contract id
            try		    {
                contractId = Integer.parseInt(contractIdS);
            } catch (Exception e)   {
                lUpdateErrors.add
                    ("orderdetail",
                     new ActionError("error.simpleGenericError",
                                     "Invalid contract id string=["
                                     + contractIdS +  "]. "));

                return lUpdateErrors;
            }
        }

        // see if we can get the contract data according to the contract id
        ContractData contractD = null;

        try	    {
            contractD = contractEjb.getContractByAccount(contractId, accountId);
        }        catch (Exception e)	    {
            lUpdateErrors.add
                ("orderdetail",
                 new ActionError("error.simpleGenericError",
                                 "Can't find contract[contractId="
                                 + contractId +  "]. "));

            return lUpdateErrors;
        }

        // set the order account erp num as the new one
        if (null != contractD)	    {
            if (orderD.getContractId() != contractD.getContractId())   {
                orderD.setContractId(contractD.getContractId());
            }

            catalogId = contractD.getCatalogId();
        }        else	    {
            lUpdateErrors.add
                ("orderdetail",
                 new ActionError("error.simpleGenericError",
                                 "Can't get contract for contractId=" +
                                 contractId +  "] "));

            return lUpdateErrors;
        }

        *//*
        // update the order info
        if (true == orderD.isDirty())
        {
        orderD.setModBy(userName);
        orderEjb.updateOrder(orderD);
        }
        *//*
        orderD.setContractId(contractId);
        OrderItemDescDataVector orderItemDescList =
            detailForm.getOrderItemDescList();

        OrderItemDataVector changedOrderItemList = new OrderItemDataVector();
        boolean updateItemsFlag = false;

        OrderItemDataVector orderItemDV = new OrderItemDataVector();
        ReplacedOrderItemViewVector modReplOrderItems =
            new ReplacedOrderItemViewVector();
        if (null != orderItemDescList && 0 < orderItemDescList.size())	    {

            // get the all contractItems' information
            // and the all contract distributors
            ContractItemPriceViewVector cItemPriceViewV = new ContractItemPriceViewVector();
            BusEntityDataVector contractDistV = new BusEntityDataVector();

            try		    {
                contractDistV = contractEjb.getContractDistCollection(orderD.getContractId());
            }	catch (Exception e)    {
                contractDistV = new BusEntityDataVector();
            }

            if (contractChangedFlag){
                IdVector orderItemIdV = new IdVector();

                // get the orderItems' itemIds
                for (int i = 0; i < orderItemDescList.size(); i++){

                    OrderItemDescData orderItemDescD = (OrderItemDescData)orderItemDescList.get(i);
                    if (null != orderItemDescD)	    {

                        OrderItemData orderItemD = orderItemDescD.getOrderItem();
                        String itemIdS = orderItemDescD.getItemIdS();
                        if (null == itemIdS
                            || itemIdS.trim().length() == 0) {
                            String paramName = "orderItemDesc[" + i
                                + "].itemIdS";
                            itemIdS = request.getParameter(paramName);
                        }

                        int itemId = orderItemD.getItemId();
                        orderItemIdV.add(new Integer(itemId));

                        if (null != itemIdS
                            && itemIdS.trim().length() > 0
                            && !"".equals(itemIdS)){
                            // see if we can parse the inputed item id
                            try				{
                                itemId = Integer.parseInt(itemIdS);
                                orderItemD.setItemId(itemId);
                                orderItemDescD.setItemIdS(itemIdS);

                            }    catch (Exception e)	{
                                lUpdateErrors.add
                                    ("orderdetail", new ActionError
                                     ("error.simpleGenericError",
                                      "Invalid Item Id[" + i + "]="+itemIdS));

                                return lUpdateErrors;
                            }
                        }
                    }
                }

                // get the contract info and dist id for theorderitems
                try	{
                    cItemPriceViewV = contractEjb.getItemDistributorIdCollection(orderD.getContractId(),orderItemIdV);
                }    catch (Exception e)	{
                    cItemPriceViewV = new ContractItemPriceViewVector();

                }

                if (null == cItemPriceViewV || 0 == cItemPriceViewV.size())    {
                    lUpdateErrors.add("orderdetail",
                                      new ActionError
                                      ("error.simpleGenericError",
                                       new String("Can't find the order items in this contract[contractId=" + contractId +
                                                  "]")));

                    return lUpdateErrors;
                }
            }

            // Begin to change each ordeItemD
            for (int i = 0; i < orderItemDescList.size(); i++)	{

                OrderItemDescData orderItemDescD =
                    (OrderItemDescData)orderItemDescList.get(i);

                if (null == orderItemDescD) {
                    continue;
                }

                OrderItemData orderItemD = orderItemDescD.getOrderItem();
                int origItemId = orderItemD.getItemId();
                boolean orderItemChangedFlag = false;

                // if contractId was changed, we need update
                // the distCost and customerPrice for all items
                // and set the dist info
                if (true == contractChangedFlag)                    {
                    orderItemChangedFlag = true;

                    boolean findContractItemFlag = false;
                    boolean findContractDistFlag = false;

                    for (int j = 0; j < cItemPriceViewV.size(); j++){

                        ContractItemPriceView cItemPriceViewD =
                            (ContractItemPriceView)cItemPriceViewV.get(j);

                        if (null != cItemPriceViewD &&
                            orderItemD.getItemId() == cItemPriceViewD.getItemId())               {
                            findContractItemFlag = true;

                            orderItemD.setDistItemCost(cItemPriceViewD.getDistCost());
                            orderItemD.setCustContractPrice(cItemPriceViewD.getPrice());
                            orderItemDescD.setDistId(cItemPriceViewD.getDistId());
                            orderItemDescD.setDistName("");
                            orderItemD.setDistItemPack(cItemPriceViewD.getItemPack());

                            //sets distributor related info
                            ProductData prod = catalogInfoEjb.getCatalogClwProduct(catalogId,orderItemD.getItemId(),0,0,SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
                            //XXX this is not what should go here, but it is what goes here for regular orders,
                            //and it should not be different
                            orderItemD.setDistItemShortDesc(prod.getCatalogDistributorName());
                            orderItemD.setDistItemSkuNum(prod.getDistributorSku(cItemPriceViewD.getDistId()));
                            orderItemD.setDistItemUom(prod.getDistributorUom(cItemPriceViewD.getDistId()));


                            if (null != contractDistV && 0 < contractDistV.size())          {

                                for (int k = 0;k < contractDistV.size();k++){

                                    BusEntityData distD =
                                        (BusEntityData)contractDistV.get(k);

                                    if (distD.getBusEntityId() == cItemPriceViewD.getDistId()){
                                        findContractDistFlag = true;
                                        orderItemD.setDistErpNum(distD.getErpNum());
                                        orderItemDescD.setDistName(distD.getShortDesc());
                                        break;
                                    }

                                    if (false == findContractDistFlag){
                                        orderItemD.setDistErpNum(null);
                                    }
                                }
                            }
                            else                                {
                                orderItemD.setDistErpNum(null);
                            }

                            break;
                        }
                    }

                    if (false == findContractItemFlag)                        {
                        orderItemD.setDistItemCost(null);
                        orderItemD.setCustContractPrice(null);
                        orderItemD.setDistErpNum(null);
                        orderItemDescD.setDistId(0);
                        orderItemDescD.setDistName("");
                    }
                }

                int orgDistId = orderItemDescD.getDistId();
                String itemIdS = orderItemDescD.getItemIdS();
                int itemId = orderItemDescD.getOrderItem().getItemId();
                String itemSkuNumS = orderItemDescD.getItemSkuNumS();
                boolean itemInputedFlag = false;

                if ( Utility.isSet(itemIdS)  &&
                     itemIdS.trim().length() > 0 ){
                    itemInputedFlag = true;

                    // see if we can parse the inputed item id
                    try         {
                        itemId = Integer.parseInt(itemIdS);
                    }
                    catch (Exception e)                     {
                        lUpdateErrors.add
                            ("orderdetail",
                             new ActionError
                             ("error.simpleGenericError",
                              "Invalid Item Id[" + i +
                              "]="+itemIdS));

                        return lUpdateErrors;
                    }

                    // set the distributor info according to the contract
                    // because the new item maybe has different distributor
                    orgDistId = contractEjb.getItemDistributorId(contractId, itemId);

                    if (0 != orgDistId)                        {
                        orderItemDescD.setDistId(orgDistId);
                        orderItemDescD.setDistName("");

                        if (null != contractDistV &&
                            0 < contractDistV.size()){

                            boolean findContractDistFlag = true;
                            for (int k = 0; k < contractDistV.size(); k++){

                                BusEntityData distD = (BusEntityData)contractDistV.get(k);

                                if (distD.getBusEntityId() == orgDistId){
                                    findContractDistFlag = true;
                                    orderItemD.setDistErpNum(distD.getErpNum());
                                    orderItemDescD.setDistName(distD.getShortDesc());

                                    break;
                                }

                                if (false == findContractDistFlag){
                                    orderItemD.setDistErpNum(null);
                                }
                            }
                        }
                        else                            {
                            orderItemD.setDistErpNum(null);
                        }
                    }
                    else                        {
                        orderItemDescD.setDistId(0);
                        orderItemDescD.setDistName("");
                        orderItemD.setDistErpNum(null);
                    }

                    // fetch the distItemCost and custContractPrice
                    ContractItemDataVector contractItemV =
                        contractEjb.getContractItemCollectionByItem(contractId, itemId);

                    if (null != contractItemV && 0 < contractItemV.size()){

                        ContractItemData contractItemD =
                            (ContractItemData)contractItemV.get(0);

                        if (null != contractItemD)                   {
                            orderItemD.setDistItemCost(contractItemD.getDistCost());
                            orderItemD.setCustContractPrice(contractItemD.getAmount());
                        }
                        else                 {
                            orderItemD.setDistItemCost(null);
                            orderItemD.setCustContractPrice(null);
                        }
                    }
                }

                // determine if we want to fetch the productD
                ProductData productD = null;
                boolean distInputedFlag = false;

                if (null != orderItemDescD.getDistIdS() &&
                    !"".equals(orderItemDescD.getDistIdS()))        {
                    distInputedFlag = true;
                }

                if (true == contractChangedFlag ||
                    true == itemInputedFlag || true == distInputedFlag) {

                    // see if we can get the item data according to the item id
                    if (null == itemSkuNumS || "".equals(itemSkuNumS)){
                        itemSkuNumS = String.valueOf(orderItemD.getItemSkuNum());
                    }

                    try       {
                        productD = catalogInfoEjb.getCatalogClwProduct(catalogId, itemId,0,0,SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
                    }        catch (Exception e)          {
                        lUpdateErrors.add
                            ("orderdetail",
                             new ActionError("error.badItem",
                                             "cwSku " + itemSkuNumS +
                                             " [itemId=" +
                                             itemId +
                                             ", catalogId=" +
                                             catalogId +"] 1"));

                        return lUpdateErrors;
                    }

                    if (null == productD)           {
                        lUpdateErrors.add
                            ("orderdetail",
                             new ActionError("error.badItem",
                                             "cwSku " + itemSkuNumS +
                                             " [itemId=" +
                                             itemId +
                                             ", catalogId=" +
                                             catalogId +"] 2"));

                        return lUpdateErrors;
                    }

                    if (null == orderItemD){
                        orderItemD = OrderItemData.createValue();
                    }

                    if (true == itemInputedFlag)               {
                        orderItemChangedFlag = true;
                        orderItemD.setItemId(itemId);
                    }

                    if (true == contractChangedFlag ||
                        true == itemInputedFlag)               {
                        orderItemD.setItemId(productD.getProductId());
                        orderItemD.setItemSkuNum(productD.getSkuNum());
                        orderItemD.setItemShortDesc(productD.getShortDesc());
                        orderItemD.setItemUom(productD.getUom());
                        orderItemD.setItemPack(productD.getPack());
                        orderItemD.setItemSize(productD.getSize());
                        orderItemD.setItemCost(null);
                        if(itemInputedFlag){
                            orderItemD.setCustItemSkuNum(productD.getCatalogSkuNum());
                        }
                        orderItemD.setCustItemShortDesc(productD.getCatalogProductShortDesc());
                        orderItemD.setCustItemUom(productD.getUom());
                        orderItemD.setCustItemPack(productD.getPack());
                        //CUST_CONTRACT_PRICE      NUMBER (15,3)
                        //CUST_CONTRACT_ID         NUMBER (38)
                        //CUST_CONTRACT_SHORT_DESC VARCHAR (30)
                        //COST_CENTER_ID           NUMBER (38)
                        orderItemD.setManuItemSkuNum(productD.getManufacturerSku());
                        orderItemD.setManuItemMsrp(new BigDecimal(productD.getListPrice()));
                        orderItemD.setManuItemUpcNum(productD.getUpc());
                        orderItemD.setManuPackUpcNum(productD.getPkgUpc());
                        orderItemD.setManuItemShortDesc(productD.getManufacturerName());
                        orderItemD.setModBy(userName);


                        if (0 != orgDistId)                           {
                            orderItemD.setDistItemShortDesc(productD.getCatalogDistributorName());
                            orderItemD.setDistItemSkuNum
                                (productD.getDistributorSku
                                 (orgDistId));
                            orderItemD.setDistItemUom
                                (productD.getDistributorUom
                                 (orgDistId));
                            orderItemD.setDistItemPack
                                (productD.getDistributorPack
                                 (orgDistId));
                        }
                        else                                {
                            orderItemD.setDistItemSkuNum(null);
                            orderItemD.setDistItemUom(null);
                            orderItemD.setDistItemPack(null);
                        }
                    }
                }

                String distIdS = orderItemDescD.getDistIdS();
                String distName = orderItemDescD.getNewDistName();
                orderItemDescD.setDistIdS("");
                orderItemDescD.setNewDistName("");

                if (null != distIdS && !"".equals(distIdS))         {

                    // see if we can parse the inputed dist id
                    int distId = 0;

                    try                        {
                        distId = Integer.parseInt(distIdS);
                    }                        catch (Exception e)            {
                        lUpdateErrors.add
                            ("orderdetail",
                             new ActionError
                             ("error.badDist",
                              "[distName=" + distName +
                              "]" + "Dist Id[" + i + "]"));

                        return lUpdateErrors;
                    }

                    // see if we can get the dist data according to the dist id
                    DistributorData distD = null;

                    try                        {
                        distD = distEjb.getDistributor(distId);
                    }       catch (Exception e)      {
                        lUpdateErrors.add("orderdetail",
                                          new ActionError("error.badDist",
                                                          "[distName=" + distName +
                                                          "]"));

                        return lUpdateErrors;
                    }

                    if (null != distD)                  {

                        if (null == orderItemD ||
                            distD.getBusEntity().getBusEntityId() != orgDistId){
                            orderItemChangedFlag = true;

                            if (null == orderItemD){
                                orderItemD = OrderItemData.createValue();
                                orderItemD.setOrderId(orderD.getOrderId());
                            }

                            orderItemD.setDistErpNum(distD.getBusEntity().getErpNum());
                            orderItemD.setDistItemShortDesc(distD.getBusEntity().getShortDesc());
                            orderItemDescD.setDistId(distD.getBusEntity().getBusEntityId());
                            orderItemDescD.setDistName(distD.getBusEntity().getShortDesc());

                            IdVector itemV = new IdVector();
                            itemV.add(new Integer(itemId));
                            DistItemViewVector  diVwV =
                                distEjb.getDistItems(distD.getBusEntity().getBusEntityId(),itemV);
                            if(diVwV.size()>0) {
                                DistItemView diVw = (DistItemView) diVwV.get(0);
                                orderItemD.setDistItemSkuNum(diVw.getDistItemSku());
                                orderItemD.setDistItemUom(diVw.getDistItemUom());
                                orderItemD.setDistItemPack(diVw.getDistItemPack());
                            }
                            *//*
                              if (null != productD)
                              {
                              orderItemD.setDistItemSkuNum(productD.getDistributorSku(
                              distId));
                              orderItemD.setDistItemUom(productD.getDistributorUom(
                              distId));
                              orderItemD.setDistItemPack(productD.getDistributorPack(
                              distId));
                              }
                            *//*
                        }
                    }
                    else	    {
                        lUpdateErrors.add("orderdetail",
                                          new ActionError("error.badDist",
                                                          "[distName=" + distName +
                                                          "]"));

                        return lUpdateErrors;
                    }
                } // end if ! "".equals(distIdS)

                // set the cust cost for this item
                String itemCostS = orderItemDescD.getCwCostS().trim();

                if (null != itemCostS && !"".equals(itemCostS))        {

                    // check that it is a valid amount format
                    BigDecimal itemCost = null;

                    try                        {
                        itemCost = CurrencyFormat.parse(itemCostS);
                        itemCost = itemCost.setScale(2,BigDecimal.ROUND_HALF_UP);
                    }    catch (Exception pe)             {
                        lUpdateErrors.add("orderdetail",
                                          new ActionError(
                                                          "error.invalidNumberAmount",
                                                          "Cw Cost[" + i +
                                                          "]"));

                        return lUpdateErrors;
                    }

                    if (null != itemCost)           {

                        if (null != orderItemD.getDistItemCost())            {

                            if (0.001 < java.lang.Math.abs(orderItemD.getDistItemCost().subtract
                                                           (itemCost).doubleValue())) {
                                orderItemD.setDistItemCost(itemCost);
                                orderItemChangedFlag = true;
                            }
                        }
                        else {
                            orderItemD.setDistItemCost(itemCost);
                            orderItemChangedFlag = true;
                        }
                    }
                    else                    {
                        lUpdateErrors.add("orderdetail",
                                          new ActionError(
                                                          "error.invalidNumberAmount",
                                                          "Cw Cost [" + i +
                                                          "]"));

                        return lUpdateErrors;
                    }
                } // end of dealing inputed cost

                // set the itemQuantity for this item
                if(!consolidatedOrderFl) {
                    String itemQuantityS = orderItemDescD.getItemQuantityS().trim();

                    if (null != itemQuantityS && !"".equals(itemQuantityS))    {

                        // check that it is a valid amount format
                        int itemQuantity = 0;

                        try                          {
                            itemQuantity = Integer.parseInt(itemQuantityS);
                        }     catch (Exception pe)             {
                            lUpdateErrors.add("orderdetail",
                                              new ActionError(
                                                              "error.invalidNumberAmount",
                                                              "Item Quantity[" + i +
                                                              "]"));

                            return lUpdateErrors;
                        }

                        if (orderItemD.getTotalQuantityOrdered() != itemQuantity){
                            orderItemD.setTotalQuantityOrdered(itemQuantity);
                            orderItemChangedFlag = true;
                        }
                    } // end of dealing inputed item quantity
                } else {
                    //Consolidated order
                    int newQty = 0;
                    try {
                        ReplacedOrderViewVector roVwV =
                            detailForm.getOrderStatusDetail().getReplacedOrders();
                        newQty = checkReplacedItemQtyChange(modReplOrderItems, origItemId, orderItemD, roVwV);
                    } catch(Exception exc) {
                        String errorMess = exc.getMessage();
                        lUpdateErrors.add("error",
                                          new ActionError("error.simpleGenericError", errorMess));
                        return lUpdateErrors;
                    }
                    if(newQty != orderItemD.getTotalQuantityOrdered()) {
                        orderItemD.setTotalQuantityOrdered(newQty);
                        orderItemChangedFlag = true;
                    }
                }

                if (true == orderItemChangedFlag)
                    {
                        changedOrderItemList.add(orderItemD);
                    }
                orderItemDV.add(orderItemD);

            } //end of i-loop for orderItemDescList

            if (null != changedOrderItemList &&
                0 < changedOrderItemList.size())	{
                updateItemsFlag =true;
            }
        } // end if null != orderItemDescList

        // process handling charges
        ActionErrors ae =
            setHandlingCharges(request, detailForm, shoppingServEjb, orderD, orderItemDV, contractId);
        if(ae.size()>0) {
            return ae;
        }
        if(detailForm.getHandlingChangedFlag()) {
            orderD.setTotalMiscCost(detailForm.getTotalMiscCost());
            orderD.setTotalFreightCost(detailForm.getTotalFreightCost());
        }

        //Calculate order amount
        BigDecimal totalPrice = new BigDecimal(0);
        for(Iterator iter=orderItemDV.iterator(); iter.hasNext();){
            OrderItemData oiD = (OrderItemData)  iter.next();
            BigDecimal lineAmt = oiD.getCustContractPrice();
            if(lineAmt!=null) {
                BigDecimal qtyBD = new BigDecimal(oiD.getTotalQuantityOrdered());
                lineAmt = lineAmt.multiply(qtyBD);
                lineAmt = lineAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalPrice = totalPrice.add(lineAmt);
            }
        }
        totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal origTotalPrice = orderD.getOriginalAmount();
        if(origTotalPrice==null || !origTotalPrice.equals(totalPrice)) {
            orderD.setTotalPrice(totalPrice);
            orderD.setOriginalAmount(totalPrice);
        }

        BigDecimal totalTaxAmount = TaxUtilAvalara.recalculateItemTaxAmount(orderItemDV);
        orderD.setTotalTaxCost(totalTaxAmount);

        if(!RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(orderD.getWorkflowInd())){

        if(detailForm.isProcessCustomerWorkflow()){
            if(!RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS.equals(orderD.getWorkflowInd())){
                orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS);
            }
        }else{
            if(!RefCodeNames.WORKFLOW_IND_CD.SKIP.equals(orderD.getWorkflowInd())){
                orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.SKIP);
            }
        }
        }
        // update the order info
        if (true == orderD.isDirty())	    {
            orderD.setModBy(userName);
            orderEjb.updateOrder(orderD);
        }

        setUiOrderAmounts(orderD, detailForm, null);

        if(updateItemsFlag == true)  {
            OrderItemDataVector updatedItems = null;
            if(consolidatedOrderFl) {
                updatedItems = orderEjb.updateOrderItemCollection
                    (changedOrderItemList, modReplOrderItems, userName);
            } else {
                updatedItems = orderEjb.updateOrderItemCollection
                    (changedOrderItemList);
            }

            if ( null != lUpdateErrors && 0 == lUpdateErrors.size() ) {
                lUpdateErrors = checkOrderItems(updatedItems);
            }
        }

        return lUpdateErrors;
    }*/

    public static ActionErrors updateOrder(HttpServletRequest request,
                                                           ActionForm form)
        throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        if (null == appUser)	    {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));
            return lUpdateErrors;
        }
        String userType = appUser.getUser().getUserTypeCd();
        if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
            !RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType)&&
            !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
            !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))   {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              "Current user does not have enough permissions"));
            return lUpdateErrors;
        }


        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        String orderStatus = detailForm.getOrderStatusDetail().getOrderDetail().getOrderStatusCd();
        if (RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatus)
                || RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderStatus)
                || RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS.equals(orderStatus)
                || RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM.equals(orderStatus))
        {
          lUpdateErrors.add("exceptiondetail",
                              new ActionError("error.simpleGenericError", "Can not modify order with status: "+orderStatus));
          return lUpdateErrors;
        }

        StoreOrderChangeRequestData changeD = StoreOrderChangeRequestData.createValue();
        String userName = (String)session.getAttribute(Constants.USER_NAME);
        changeD.setUserName(userName);

        //Get handling from the form
        String freightCostS = detailForm.getTotalFreightCostS();
        BigDecimal pageFreight = new BigDecimal(0);
        if (freightCostS != null) {
          try {
            pageFreight = CurrencyFormat.parse(freightCostS.trim());
          } catch (Exception pe){
            lUpdateErrors.add("exceptiondetail",
                              new ActionError("error.invalidNumberAmount", "Freight"));
            return lUpdateErrors;
          }
          // total freight cost
          changeD.setTotalFreightCost(pageFreight);
        }
        String miscCostS = detailForm.getTotalMiscCostS();
        BigDecimal pageMisc = new BigDecimal(0);
        if (miscCostS != null) {
          try {
            pageMisc = CurrencyFormat.parse(miscCostS);
          } catch (Exception pe){
            lUpdateErrors.add("exceptiondetail",
                              new ActionError("error.invalidNumberAmount", "Handling"));
            return lUpdateErrors;
          }
          // total freight cost
          changeD.setTotalMiscCost(pageMisc);
        }

        String smallOrderFeeStr = detailForm.getSmallOrderFeeStr();
        BigDecimal pageSmallOrderFee;
        if (Utility.isSet(smallOrderFeeStr)) {
            try {
                pageSmallOrderFee = CurrencyFormat.parse(smallOrderFeeStr);
            } catch (Exception pe) {
                lUpdateErrors.add("exceptiondetail", new ActionError("error.invalidNumberAmount", "Small Order Fee"));
                return lUpdateErrors;
            }
            changeD.setSmallOrderFeeAmt(pageSmallOrderFee);
        }

        String fuelSurchargeStr = detailForm.getFuelSurchargeStr();
        BigDecimal pageFuelSurcharge;
        if (Utility.isSet(fuelSurchargeStr)) {
            try {
                pageFuelSurcharge = CurrencyFormat.parse(fuelSurchargeStr);
            } catch (Exception pe) {
                lUpdateErrors.add("exceptiondetail", new ActionError("error.invalidNumberAmount", "Fuel Surcharge"));
                return lUpdateErrors;
            }
            changeD.setFuelSurchargeAmt(pageFuelSurcharge);
        }

        String discountStr = detailForm.getDiscountStr();
        BigDecimal pageDiscount = null;
        if (Utility.isSet(discountStr)) {
            try {
                pageDiscount = CurrencyFormat.parse(discountStr);
            } catch (Exception pe) {
                lUpdateErrors.add("exceptiondetail", new ActionError("error.invalidNumberAmount", "Discount"));
                return lUpdateErrors;
            }
            changeD.setDiscountAmt(pageDiscount);
        }

        String totalTaxCostStr=detailForm.getTotalTaxCostStr();
        try {
            double tax = Double.parseDouble(totalTaxCostStr);
            detailForm.setTotalTaxCost(tax);
        } catch (NumberFormatException e) {
            lUpdateErrors.add("exceptiondetail",
                                        new ActionError("error.invalidNumberAmount", "Tax"));
           detailForm.setTotalTaxCostStr(String.valueOf(detailForm.getTotalTaxCost()));
           return lUpdateErrors;

        }

        BigDecimal totalTaxCostBD = new BigDecimal(detailForm.getTotalTaxCost());
        detailForm.getOrderStatusDetail().getOrderDetail().setTotalTaxCost(totalTaxCostBD.setScale(2,BigDecimal.ROUND_HALF_UP));

        OrderData oldOrderD = (OrderData)detailForm.getOrderStatusDetail().getOrderDetail().clone();
        changeD.setOldOrderData(oldOrderD);
        OrderItemDataVector oldOrderItemV = (OrderItemDataVector)detailForm.getOrderStatusDetail().getOrderItemList().clone();
        changeD.setOldOrderItems(oldOrderItemV);
        OrderData orderD = detailForm.getOrderStatusDetail().getOrderDetail();

        // Cancel selected items
        IdVector selectedItemIdV = new IdVector();
        OrderItemDescDataVector orderItemDescDV = detailForm.getOrderItemDescList();
        Iterator iter = orderItemDescDV.iterator();
        while (iter.hasNext()) {
          OrderItemDescData itemDescD = (OrderItemDescData) iter.next();
          if (RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemDescD.getItemStatus()) &&
              itemDescD.getOrderItem().getOrderItemId() != 0 &&
              !RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemDescD.getOrderItem().getOrderItemStatusCd())) {
             selectedItemIdV.add(new Integer(itemDescD.getOrderItem().getOrderItemId()));
          }
        }
        changeD.setDelOrderItems(selectedItemIdV);


//        OrderItemDataVector orderItemV = (OrderItemDataVector)detailForm.getOrderStatusDetail().getOrderItemList();
//        String[] selectedItems = detailForm.getSelectItems();
//        if (0 != selectedItems.length) {
//          int[] delOrderItemIds = new int[selectedItems.length];
//          for (int ii = 0; ii < selectedItems.length; ii++) {
//            String orderItemIdS = selectedItems[ii];
//            delOrderItemIds[ii] = Integer.parseInt(orderItemIdS);
//          }
//          IdVector selectedItemIdV = new IdVector();
//          for (Iterator iter = orderItemV.iterator(); iter.hasNext(); ) {
//            OrderItemData orderItemD = (OrderItemData) iter.next();
//            int orderItemId = orderItemD.getOrderItemId();
//            for (int i = 0; i < delOrderItemIds.length; i++) {
//              if (orderItemId == delOrderItemIds[i]) {
//                selectedItemIdV.add(new Integer(orderItemId));
//                break;
//              }
//            }
//          }
//          changeD.setDelOrderItems(selectedItemIdV);
//        }
        // apply budget
        if (!detailForm.getApplyBudget()) {
          orderD.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
        } else {
          orderD.setOrderBudgetTypeCd(null);
        }
        // rebill order
        changeD.setRebillOrder(detailForm.getReBillOrder());

        // Workflow Indicator
        orderD.setWorkflowInd(detailForm.getWorkflowInd());
        // Status
        orderD.setOrderStatusCd(detailForm.getOrderStatus());
        orderD.setModBy(userName);

        changeD.setOrderItemsDesc(orderItemDescDV);
        if (!"".equals(detailForm.getNewOrderDate())) {
          DateFormat mDateFormat = (new SimpleDateFormat("MM/dd/yyyy"));
          try{
            changeD.setNewOrderDate(mDateFormat.parse(detailForm.getNewOrderDate()));
          } catch (Exception e)   {
              lUpdateErrors.add
                  ("orderdetail",
                   new ActionError("error.simpleGenericError",
                                   "Invalid new order date string=["
                                   + detailForm.getNewOrderDate() +  "]. "));

              return lUpdateErrors;
          }
        }
        changeD.setOrderData(orderD);
        changeD.setOrderMeta(detailForm.getOrderStatusDetail().getOrderMetaData());

        // ejb
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory)    {
            throw new Exception("Without APIAccess.");
        }
        StoreOrder storeOrderEjb = factory.getStoreOrderAPI();
        try {
            int newSiteId = Utility.parseInt(detailForm.getSiteId());
            if (Utility.isSet(detailForm.getSiteId()) == true) {
                if (newSiteId > 0) {
                    changeD.setNewSiteId(newSiteId);
                    List<String> errors = storeOrderEjb.validateSite(changeD);
                    if (errors != null && errors.size() > 0) {
                        for (String error : errors) {
                            lUpdateErrors.add("orderdetail", new ActionError(
                                    "error.simpleGenericError", error));
                        }
                        return lUpdateErrors;
                    }
                    storeOrderEjb.changeSite(changeD);
                } else {
                    lUpdateErrors.add("orderdetail", new ActionError(
                            "error.simpleGenericError", "Incorrect Site Id!"));
                    return lUpdateErrors;
                }
            } else {
            	lUpdateErrors.add("orderdetail", new ActionError(
                        "error.simpleGenericError", "Empty Site Id!"));
                return lUpdateErrors;
            }


            OrderData orderData = changeD.getOrderData();
            if (orderData != null && RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderData.getOrderStatusCd())) {
                storeOrderEjb.processPipelineSyncAsync(orderData);
            } else {
                storeOrderEjb.updateOrder(changeD);
            }
        } catch (Exception ex) {
          ex.printStackTrace();
          EJBException ee = (EJBException) ex.getCause();
          if (ee != null && (ee.getCause() instanceof PipelineMessageException)){
            PipelineMessageException pex = (PipelineMessageException)ee.getCause();
            lUpdateErrors.add("orderdetail",
                              new ActionError(pex.getCode(), pex.getMessage()));
          } else {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              ex.getMessage()));

          }
          return lUpdateErrors;
        }

        setUiOrderAmounts(orderD, detailForm, null);

        return lUpdateErrors;
    }


    public static ActionErrors checkOrderItems
        (OrderItemDataVector pOrderItems )
        throws Exception    {

        ActionErrors oiErrors = new ActionErrors();
        for ( int i = 0; i < pOrderItems.size(); i++ ) {
            OrderItemData oid = (OrderItemData)pOrderItems.get(i);
            if ( null != oid.getOrderItemStatusCd() &&
                 oid.getOrderItemStatusCd().equals
                 (RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED  ) ) {
                continue;
            }

            String t = "CwSku=" + oid.getItemSkuNum();
            String errm = "";
            if ( oid.getCustItemSkuNum() != null &&
                 oid.getCustItemSkuNum().length() > 0 ) {
                t += " CustomerSkuNum=" + oid.getCustItemSkuNum();
            }
            if ( oid.getDistItemSkuNum() == null ||
                 oid.getDistItemSkuNum().length() == 0 ) {
                errm += " Dist sku is missing. ";
            }
            if ( oid.getDistItemCost() == null ) {
                errm += " Dist cost is missing. ";
            }
            if ( oid.getDistItemUom() == null ||
                 oid.getDistItemUom().length() == 0 ) {
                errm += " Dist uom is missing. ";
            }
            if ( oid.getDistItemPack() == null ||
                 oid.getDistItemPack().length() == 0 ) {
                errm += " Dist pack is missing. ";
            }

            if ( oid.getItemUom() == null ||
                 oid.getItemUom().length() == 0 ) {
                errm += " Item uom is missing. ";
            }
            if ( oid.getItemPack() == null ||
                 oid.getItemPack().length() == 0 ) {
                errm += " Item pack is missing. ";
            }
            if ( errm.length() > 0 ) {
                oiErrors.add
                    ("item info missing",
                     new ActionError("error.simpleGenericError",
                                     t + "  " + errm)
                     );

            }
        }
        return oiErrors;
    }

    public static int checkReplacedItemQtyChange(
            ReplacedOrderItemViewVector pModifiedReplOrderItems,
            int pOrigItemId,
            OrderItemData pOrderItem,
            ReplacedOrderViewVector pReplOrders )
    throws Exception
    {
      //Consolidated order
      boolean orderItemQtyChangedFl = false;
      int sumQty = 0;
      for(Iterator iter = pReplOrders.iterator(); iter.hasNext();) {
        ReplacedOrderView roVw = (ReplacedOrderView) iter.next();
        ReplacedOrderItemViewVector roiVwV = roVw.getItems();
        for(Iterator iter1=roiVwV.iterator(); iter1.hasNext();) {
           ReplacedOrderItemView roiVw =
                    (ReplacedOrderItemView) iter1.next();
           int iId = roiVw.getItemId();
           if(iId!=pOrigItemId) {
             continue;
           }
           String quantityS = roiVw.getQuantityS();
           int newQty = 0;
           try {
             newQty = Integer.parseInt(quantityS);
           } catch (Exception exc) {
             String errorMess = "Wrong sku quantity format. Sku ="+
                 roiVw.getItemSkuNum()+ " Quantity = "+
                 quantityS;
             throw new Exception (errorMess);
           }
           if(newQty<0) {
             String errorMess = "Wrong  sku  quantity. Sku = "+
                 roiVw.getItemSkuNum()+ " Quantity = "+
                 quantityS;
             throw new Exception (errorMess);
           }
           if(newQty!=roiVw.getQuantity()) {
             roiVw.setQuantity(newQty);
             pModifiedReplOrderItems.add(roiVw);
           }
           sumQty += newQty;
           break;
        }
      }
      return sumQty;
    }

    /**
     * <codecheckContractForPendingApprovalOrder2</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors checkContractForPendingApprovalOrder2(HttpServletRequest request,
                                                                     ActionForm form,
                                                                     String orderStatusId)
        throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Can't find application user information in session")));

            return lUpdateErrors;
        }
        else
        {

            //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));
                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // only order with PENDING_APPROVAL status can be updated
        String orderStatusCd = detailForm.getOrderStatusDetail().getOrderDetail()
          .getOrderStatusCd();

        if ( isOrderStatusValid(orderStatusCd,detailForm.getFullControlFl()) == false )
        {
            lUpdateErrors.add("orderdetail",
                new ActionError("error.simpleGenericError",
                new String("Current order status is " + orderStatusCd))
                );

            return lUpdateErrors;
        }

        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        // set the new contract info according to the contract id inputted
        lUpdateErrors = checkContractForOrder2(request, form, orderStatusId);

        if (0 < lUpdateErrors.size())
        {

            return lUpdateErrors;
        }

        return lUpdateErrors;
    }

    /**
     * <code>checkContractForOrder2</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors checkContractForOrder2
        (HttpServletRequest request,
         ActionForm form,
         String orderStatusIdS)
        throws Exception    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        // Get a reference to the admin facade.
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)	    {
            throw new Exception("Without APIAccess.");
        }

        Contract contractEjb = factory.getContractAPI();

        // check the new contract info according to the contract id inputted
        String contractIdS = detailForm.getNewContractIdS().trim();
        int contractId = detailForm.getOrderStatusDetail().getOrderDetail().getContractId();
        int accountId = detailForm.getOrderStatusDetail().getAccountId();

        if (null != contractIdS && !"".equals(contractIdS)) {

            // see if we can parse the inputed contract id
            try		{
                contractId = Integer.parseInt(contractIdS);
            }
            catch (Exception e)		{
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  new String("The Contract Id is not a valid amount value")));

                return lUpdateErrors;
            }
        }

        // see if we can get the contract data according to the contract id
        ContractData contractD = null;

        try        {
            contractD = contractEjb.getContractByAccount(contractId, accountId);
        }
        catch (Exception e)        {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              new String("Error occurs when trying to find contract[contractId=" + contractId +
                                                         "] for this account")));

            return lUpdateErrors;
        }

        if (null == contractD)  {
            lUpdateErrors.add
                ("orderdetail",
                 new ActionError("error.simpleGenericError",
                                 "Can't find contract[contractId=" +
                                 contractId +
                                 "] "));

            return lUpdateErrors;
        }


        ContractItemDataVector contractItemV = new ContractItemDataVector();
        contractItemV = contractEjb.getItems(contractD.getContractId());

        if (null != contractItemV && 0 < contractItemV.size())     {

            OrderItemDescDataVector orderItemDescV = detailForm.getOrderItemDescList();

            if (null != orderItemDescV && 0 < orderItemDescV.size())   {

                String errorMessage = "";

                for (int i = 0; i < orderItemDescV.size(); i++){

                    OrderItemDescData orderItemDescD = (OrderItemDescData)orderItemDescV.get(i);

                    if ( orderItemDescD.getOrderItem() != null &&
                         orderItemDescD.getOrderItem().getOrderItemStatusCd() != null &&
                         orderItemDescD.getOrderItem().getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED  ) ) {
                        log.info("item: " + orderItemDescD +
                                           " was cancelled" );
                        continue;
                    }

                    // if the item is inputed, see if the
                    // inputed one is matched
                    // otherwise match the existing one
                    String itemIdS = orderItemDescD.getItemIdS();
                    int itemId = orderItemDescD.getOrderItem().getItemId();
                    String itemSkuNumS = String.valueOf(orderItemDescD.getOrderItem().getItemSkuNum());
                    if ( null == itemIdS || itemIdS.length() == 0 ) {
                        String paramName = "orderItemDesc[" + i
                            + "].itemIdS";
                        itemIdS = request.getParameter(paramName);
                        orderItemDescD.setItemIdS(itemIdS);
                    }

                    if (null != itemIdS && !"".equals(itemIdS)){

                        // see if we can parse the inputed item id
                        itemSkuNumS = orderItemDescD.getItemSkuNumS();

                        try      {
                            itemId = Integer.parseInt(itemIdS.trim());
                        }
                        catch (Exception e)     {
                            lUpdateErrors.add
                                ("orderdetail",
                                 new ActionError
                                 ("error.simpleGenericError",
                                  "Item Id[" + i + "]" +
                                  " itemIdS=" + itemIdS
                                  ));

                            return lUpdateErrors;
                        }
                    }

                    boolean matchItemFlag = false;

                    for (int j = 0; j < contractItemV.size(); j++) {

                        ContractItemData contractItemD =
                            (ContractItemData)contractItemV.get(j);

                        if (null != orderItemDescD &&
                            null != contractItemD &&
                            contractItemD.getItemId() == itemId){
                            matchItemFlag = true;
                            break;
                        }
                    }

                    if (false == matchItemFlag)           {
                        errorMessage += "Item[" + i +
                            "] CwSku " +
                            itemSkuNumS +
                            "[item info=" +
                            orderItemDescD +
                            "]- is not in the contract";
                    }
                }

                if (!"".equals(errorMessage)){
                    lUpdateErrors.add
                        ("orderdetail",
                         new ActionError("error.simpleGenericError",
                                         errorMessage));
                    return lUpdateErrors;
                }
            }
        }
        else            {
            lUpdateErrors.add
                ("orderdetail",
                 new ActionError
                 ("error.simpleGenericError",
                  "Can't find contract items for this contract[contractId="
                  + contractId +  "]"  ));

            return lUpdateErrors;
        }

        return lUpdateErrors;
    }

    /**
     * <code>initTempPo</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void initTempPo(HttpServletRequest request, ActionForm form,
                                  String orderStatusId)
                           throws Exception
    {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        Distributor distEjb = factory.getDistributorAPI();

        if (null == orderStatusId || "".equals(orderStatusId))
        {
            orderStatusId = (String)session.getAttribute("OrderStatus.id");
        }

        session.setAttribute("OrderStatus.id", orderStatusId);

        OrderStatusDescData orderStatusDetail = orderEjb.getOrderStatusDesc(Integer.parseInt(
                                                                                    orderStatusId));
        OrderItemDescDataVector itemStatusDescV = orderEjb.getOrderItemDescCollection(Integer.parseInt(
                                                                                              orderStatusId),
                                                                                      null);
        StoreOrderTemporaryPoForm detailForm = (StoreOrderTemporaryPoForm)form;
        detailForm.setOrderStatusDetail(orderStatusDetail);
        detailForm.setOrderItemDescList(itemStatusDescV);
        if (null != itemStatusDescV)
        {
            DistributorDataVector distList = new DistributorDataVector();
            IdVector idV = new IdVector();

            for (int i = 0; i < itemStatusDescV.size(); i++)
            {

                OrderItemDescData itemDescD = (OrderItemDescData)itemStatusDescV.get(i);
                idV.add(new Integer(itemDescD.getDistId()));
            }
            distList = distEjb.getDistributorCollectionByIdList(idV);
            detailForm.setDistList(distList);
        }

        detailForm.setDistIdS(new String(""));
    }

    /**
     * <code>setTempPoDist</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void setTempPoDist(HttpServletRequest request,
                                     ActionForm form)
                              throws Exception
    {

        StoreOrderTemporaryPoForm detailForm = (StoreOrderTemporaryPoForm)form;
        OrderStatusDescData orderStatusDetail = detailForm.getOrderStatusDetail();
        OrderItemDescDataVector itemStatusDescV = detailForm.getOrderItemDescList();
        String distIdS = detailForm.getDistIdS();
        DistributorDataVector distList = detailForm.getDistList();
        DistributorData distributorD = null;

        if (null != distIdS && !"".equals(distIdS.trim()))
        {

            for (int i = 0; i < distList.size(); i++)
            {

                DistributorData distD = (DistributorData)distList.get(i);

                if (String.valueOf(distD.getBusEntity().getBusEntityId()).equals(
                            distIdS))
                {
                    distributorD = distD;

                    break;
                }
            }
        }

        OrderItemDescDataVector orderItemDescV = detailForm.getOrderItemDescList();
        OrderItemDataVector distOrderItemList = new OrderItemDataVector();

        if (null == distributorD)
        {
            distributorD = DistributorData.createValue();

            if (null != orderItemDescV)
            {

                for (int i = 0; i < orderItemDescV.size(); i++)
                {
                    distOrderItemList.add(((OrderItemDescData)orderItemDescV.get(
                                                   i)).getOrderItem());
                }
            }
        }
        else
        {

            String distErpNum = distributorD.getBusEntity().getErpNum();

            if (null != orderItemDescV)
            {

                for (int i = 0; i < orderItemDescV.size(); i++)
                {

                    OrderItemData orderItemD = ((OrderItemDescData)orderItemDescV.get(
                                                        i)).getOrderItem();

                    if (distErpNum.equals(orderItemD.getDistErpNum()))
                    {
                        distOrderItemList.add(((OrderItemDescData)orderItemDescV.get(
                                                       i)).getOrderItem());
                    }
                }
            }
        }

        detailForm.setDistributorData(distributorD);
        detailForm.setDistOrderItemList(distOrderItemList);
    }

    /**
     * <code>printTemporaryPo</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors printTemporaryPo(HttpServletResponse response,
                                                HttpServletRequest request,
                                                ActionForm form)
                                         throws Exception
    {

        HttpSession session = request.getSession();
        StoreOrderTemporaryPoForm sForm = (StoreOrderTemporaryPoForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Store storeEjb = factory.getStoreAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        ItemInformation itemInfoEjb=factory.getItemInformationAPI();
        Asset assetEjb=factory.getAssetAPI();
        HashMap assetInfo=null;

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = null;
        if(sForm.getOrderStatusDetail().getOrderDetail().getStoreId() > 0){
            storeD = factory.getStoreAPI().getStore((sForm.getOrderStatusDetail().getOrderDetail().getStoreId()));
        }
        if(storeD == null){
            storeD = appUser.getUserStore();
        }
        PdfTemporaryPO pdf = new PdfTemporaryPO();
        String imgPath = ClwCustomizer.getCustomizeImgElement(request,"pages.store.logo1.image");

        //sets the content type so the browser knows this is a pdf
        response.setContentType("application/pdf");
        String browser = (String)  request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
      	  	response.setHeader("extension", "pdf");
      	  	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
        }

        //gets the references to the objects our po needs
        OrderData ord = sForm.getOrderStatusDetail().getOrderDetail();
        OrderItemDataVector itms = sForm.getDistOrderItemList();

        IdVector itemIds=new IdVector();
        IdVector assetIds=new IdVector();
        for(int i=0,len=itms.size();i<len;i++)
        {
         itemIds.add(new Integer(((OrderItemData)itms.get(i)).getItemId()));
         assetIds.add(new Integer(((OrderItemData)itms.get(i)).getAssetId()));
        }

        ItemDataVector itemDV=itemInfoEjb.getItemCollection(itemIds);
        boolean isSimpleServiceOrder = PipelineUtil.isSimpleServiceOrder(itemDV);
        if(PipelineUtil.isSimpleServiceOrder(itemDV))
        {
          assetInfo=getAssetInfo(assetEjb,assetIds);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (isSimpleServiceOrder) {
            pdf.constructPdfPO(sForm.getDistributorData(),
                    ord, sForm.getOrderStatusDetail().getShipTo(),
                    itms, sForm.getOrderStatusDetail().getAccountName(),
                    storeD, assetInfo, isSimpleServiceOrder, out,
                    imgPath);
        } else {
            pdf.constructPdfPO(sForm.getDistributorData(),
                    ord,
                    sForm.getOrderStatusDetail().getShipTo(),
                    itms,
                    sForm.getOrderStatusDetail().getAccountName(),
                    storeD, out,
                    imgPath);
        }
        response.setContentLength(out.size());
        out.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return new ActionErrors();
    }

    public static HashMap getAssetInfo(Asset assetEjb, IdVector assetIds) {
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        HashMap assetInfo = null;
        criteria.setAssetIds(assetIds);
        try {
            AssetDataVector assetDV = assetEjb.getAssetDataByCriteria(criteria);
            assetInfo = new HashMap();
            Iterator it = assetDV.iterator();
            while (it.hasNext()) {
                AssetData assetData = (AssetData) it.next();
                if (!assetInfo.containsKey(String.valueOf(assetData.getAssetId()))) {
                    assetInfo.put(String.valueOf(assetData.getAssetId()), assetData);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return assetInfo;
    }

    public static ActionErrors reorder(HttpServletRequest request,
                                       ActionForm pform)
                                throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreOrderDetailForm sForm = (StoreOrderDetailForm)session.getAttribute(
                                          "STORE_ORDER_DETAIL_FORM");

        if (sForm == null)
        {

            // not expecting this, but nothing to ae if it is
            System.err.println(" No form available.");

            return ae;
        }

        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            ae.add("orderdetail",
                   new ActionError("error.simpleGenericError",
                                   new String("No user information available 1.3")));

            return ae;
        }

        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
        OrderStatusDescData odetail = sForm.getOrderStatusDetail();
        int orderSiteId = odetail.getSiteAddress().getBusEntityId();

        LogOnLogic.siteShop(request, orderSiteId);

        Integer catalogId = (Integer)session.getAttribute(Constants.CATALOG_ID);

        if (null == catalogId)
        {
            throw new Exception("No catalog id defined in the session.");
        }

        Integer contractId = (Integer)session.getAttribute(
                                     Constants.CONTRACT_ID);

        if (null == contractId)
        {
            throw new Exception("No contract id defined in the session.");
        }

        OrderItemDataVector orderItems = odetail.getOrderItemList();

        // Set the cart to hold the items contained in the order.
        ShoppingCartData scd = new ShoppingCartData();
        scd.setUser(appUser.getUser());
        scd.setSite(appUser.getSite());

        StoreData store = appUser.getUserStore();
        if (store == null)
        {
            ae.add("error",
                   new ActionError("error.systemError",
                                   "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null)
        {
            ae.add("error",
                   new ActionError("error.systemError",
                                   "No store type information was loaded"));

            return ae;
        }

        List items = new LinkedList();

        try
        {

            for (int itemIdx = 0; itemIdx < orderItems.size(); itemIdx++)
            {

                OrderItemData oi = (OrderItemData)orderItems.get(itemIdx);
                items.add(new Integer(oi.getItemId()));
            }
        }
        catch (Exception e)
        {
            log.info("ShoppingCartData init exception: " + e);
        }

        ShoppingCartItemDataVector itemV = null;

        try
        {
            //Jd begin
            SiteData site = appUser.getSite();
            if("jd".equals(ClwCustomizer.getStoreDir())) {
            itemV = shoppingServEjb.prepareJdShoppingItems(site.getPriceCode(),
                                                         catalogId.intValue(),
                                                         contractId.intValue(),
                                                         items,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            } else {
            //Jd end
                itemV = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), site, catalogId.intValue(),
                                                         contractId.intValue(),
                                                         items,SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
          if(!appUser.getShowWholeCatalog())
            for(int ii=0; ii<itemV.size(); ii++) {
              ShoppingCartItemData sciD = (ShoppingCartItemData)itemV.get(ii);
              if(!sciD.getContractFlag()){
                 itemV.remove(ii);
              }
            }
            //Jd begin
            }
            //Jd end
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            ae.add("error",
                   new ActionError("error.systemError", "Shopping error."));

            return ae;
        }

        ArrayList scdwarnings = new ArrayList();

        for (int itemIdx = 0; itemIdx < orderItems.size(); itemIdx++)
        {

            OrderItemData oi = (OrderItemData)orderItems.get(itemIdx);
            int thisItemId = oi.getItemId();
            int thisQty = oi.getTotalQuantityOrdered();
            boolean itemAdded = false;

            for (int scidx = 0; scidx < itemV.size(); scidx++)
            {

                ShoppingCartItemData sciD = (ShoppingCartItemData)itemV.get(
                                                    scidx);

                if (thisItemId == sciD.getProduct().getItemData().getItemId())
                {
                    sciD.setQuantity(thisQty);
                    scd.addItem(sciD);
                    itemAdded = true;
                    if(!sciD.getContractFlag()) {
                      int cwSkuNum = sciD.getProduct().getItemData().getSkuNum();
                      String name = sciD.getProduct().getItemData().getShortDesc();
                       String wmsg = "Not contract item: CW.Sku=" + cwSkuNum +
                       " Desc=" + name;
                       scdwarnings.add(wmsg);
                    }
                }
            }

            if (itemAdded == false)
            {

                String wmsg = "Dropped item: CW.Sku=" + oi.getItemSkuNum() +
                " Desc=" + oi.getItemShortDesc();
                scdwarnings.add(wmsg);
            }
        }

        scd.setPrevOrderData(odetail.getOrderDetail());
        scd.setWarningMessages(scdwarnings);
        session.setAttribute(Constants.SHOPPING_CART, scd);
        ae = ShoppingCartLogic.saveShoppingCart(session);

        ShoppingCartData savedCart = ShopTool.getCurrentShoppingCart(session);
        savedCart.setWarningMessages(scdwarnings);

        return ae;
    }

    public static ActionErrors reorder2(HttpServletRequest request)
                                throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        OrderJoinData order = (OrderJoinData)session.getAttribute("order");

        if (order == null)
        {
            System.err.println(" No order available.");
            return ae;
        }

        OrderItemJoinDataVector orderItems = order.getOrderJoinItems();
        if (orderItems == null)
        {
            System.err.println(" No order items available.");
            return ae;
        }

        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);

        if (null == appUser)
        {
            ae.add("orderdetail",
                   new ActionError
                   ("error.simpleGenericError",
                    new String("No user information available 1.3")));

            return ae;
        }

        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
        Site siteEjb = factory.getSiteAPI();

        // Get the site for the new order from the previous order.
        SiteData sd = siteEjb.getSite(order.getOrder().getSiteId(),0, false, SessionTool.getCategoryToCostCenterView(session, order.getOrder().getSiteId()));
        int orderSiteId = sd.getBusEntity().getBusEntityId();
        LogOnLogic.siteShop(request, orderSiteId);

        Integer catalogId = (Integer)session.getAttribute(Constants.CATALOG_ID);

        if (null == catalogId)
        {
            throw new Exception("No catalog id defined in the session.");
        }

        Integer contractId = (Integer)session.getAttribute(
                                     Constants.CONTRACT_ID);

        if (null == contractId)
        {
            throw new Exception("No contract id defined in the session.");
        }

        // Set the cart to hold the items contained in the order.
        ShoppingCartData scd = new ShoppingCartData();
        scd.setUser(appUser.getUser());
        scd.setSite(appUser.getSite());

        StoreData store = appUser.getUserStore();

        if (store == null)
        {
            ae.add("error",
                   new ActionError("error.systemError",
                                   "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null)
        {
            ae.add("error",
                   new ActionError("error.systemError",
                                   "No store type information was loaded"));

            return ae;
        }

        List items = new LinkedList();

        try
        {
            for (int itemIdx = 0; itemIdx < orderItems.size(); itemIdx++)
            {
                OrderItemJoinData oi = (OrderItemJoinData)orderItems.get(itemIdx);
                items.add(new Integer(oi.getOrderItem().getItemId()));
            }
        }
        catch (Exception e)
        {
            log.info("ShoppingCartData init exception: " + e);
        }

        ShoppingCartItemDataVector itemV = null;
        SiteData site = appUser.getSite();
        try
        {
            log.info("---------  prepare shopping for: " +
                               " store type: " + storeTypeProperty.getValue() +
                               " cat id: " + catalogId +
                               " contract id: " + contractId +
                               " for items: " + items );

            //Jd begin

            if("jd".equals(ClwCustomizer.getStoreDir())) {
            itemV = shoppingServEjb.prepareJdShoppingItems(site.getPriceCode(),
                                                         catalogId.intValue(),
                                                         contractId.intValue(),
                                                         items,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            } else {
            //Jd end
                itemV = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), site,
                                                         catalogId.intValue(),
                                                         contractId.intValue(),
                                                         items,SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
            //Jd begin
            }
            //Jd end
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            ae.add("error",
                   new ActionError("error.systemError", "Shopping error."));

            return ae;
        }

        ArrayList scdwarnings = new ArrayList();

        for (int itemIdx = 0; itemIdx < orderItems.size(); itemIdx++)
        {

            OrderItemJoinData oi = (OrderItemJoinData)orderItems.get(itemIdx);
            int thisItemId = oi.getOrderItem().getItemId();
            int thisQty      = oi.getOrderItem().getTotalQuantityOrdered(),
                thisParValue = oi.getOrderItem().getInventoryParValue();
            String thisOnHandSetting = oi.getOrderItem().getInventoryQtyOnHand();

            boolean itemAdded = false;

            for (int scidx = 0; scidx < itemV.size(); scidx++)
            {
                ShoppingCartItemData sciD = (ShoppingCartItemData)itemV.get(scidx);
                if (sciD.isSameItem(thisItemId))
                {
                    sciD.setQuantity(thisQty);

                    if ( thisOnHandSetting != null )
                    {
                        thisOnHandSetting = thisOnHandSetting.trim();
                        sciD.setIsaInventoryItem(true);
                        int q = 0;
                        try {
                            q = Integer.parseInt(thisOnHandSetting);
                        }
                        catch(Exception e) {
                            log.info
                                ("int parse failed on "
                                 + " thisOnHandSetting=" +
                                 thisOnHandSetting);
                        }
                        sciD.setInventoryQtyOnHandString(thisOnHandSetting);
                        sciD.setInventoryQtyOnHand(q);
                        sciD.setInventoryQtyIsSet(true);
                        sciD.setInventoryParValue(thisParValue);
                    }
                    //scd.addItem(sciD);
                    itemAdded = true;
                    if(!sciD.getContractFlag()) {
                      int cwSkuNum = sciD.getProduct().getItemData().getSkuNum();
                      String name = sciD.getProduct().getItemData().getShortDesc();
                       String wmsg = "Not contract item: CW.Sku=" + cwSkuNum +
                       " Desc=" + name;
                       scdwarnings.add(wmsg);
                    }
                }
            }

            if (itemAdded == false)
            {

                String wmsg = "Dropped item: CW.Sku=" +
                    oi.getOrderItem().getItemSkuNum() +
                    " Desc=" + oi.getOrderItem().getItemShortDesc();
                scdwarnings.add(wmsg);
            }
        }

        ValidateActionMessage am = ShopTool.addItemsToCart(request, scd, itemV);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            scdwarnings.addAll(am.getWarnings());
        }

        scd.setWarningMessages(scdwarnings);
        order.getOrder().setTotalMiscCost(new java.math.BigDecimal(0));
        order.getOrder().setTotalFreightCost(new java.math.BigDecimal(0));
        scd.setPrevOrderData(order.getOrder());
        if(!site.hasModernInventoryShopping()) {
        UserShopLogic.saveInventoryOnHandValues
            (request, orderSiteId, scd.getItems(), false);
        }
        session.setAttribute(Constants.SHOPPING_CART, scd);
        ae = ShoppingCartLogic.saveShoppingCart(session);

        ShoppingCartData savedCart = ShopTool.getCurrentShoppingCart(session);
        savedCart.setWarningMessages(scdwarnings);

        if( order.getRushOrderCharge() != null ) {
            // Carry over the previous rush charge.
            savedCart.setPrevRushCharge(order.getRushOrderCharge());
        }
        return ae;
    }

    public static boolean
        isOrderStatusValid( String pOrdStatus, boolean pFullControlFl ) {

        if (null == pOrdStatus) {
            return false;
        }
        if (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(pOrdStatus)  ||
        RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(pOrdStatus)  ||
            RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(pOrdStatus)  ||
            RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED.equals(pOrdStatus)
            ) {
            return true;
        }
        if( pFullControlFl && (
            RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.DUPLICATED.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.INVOICED.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED.equals(pOrdStatus) ||
            RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED.equals(pOrdStatus)))
        {
            return true;
        }

        return false;
    }

    //--------------------------------------------------------------------
    private static ActionErrors setHandlingCharges(HttpServletRequest request,
       StoreOrderDetailForm pForm,
       ShoppingServices pShoppingServEjb,
       OrderData pOrder,
       OrderItemDataVector pOrderItems,
       int pContractId
       )
    {
       boolean handlingChangedFlag = false;
       ActionErrors lUpdateErrors = new ActionErrors();

       int handlingChoise = pForm.getHandlingChoise();
       if( handlingChoise!=2 && handlingChoise!=3) handlingChoise = 1;
       int siteId = pForm.getOrderStatusDetail().getSiteAddress().getBusEntityId();




       //Get handling from the form
        String freightCostS = pForm.getTotalFreightCostS();
        BigDecimal pageFreight = new BigDecimal(0);
        if(freightCostS!=null) {
          try {
              pageFreight = CurrencyFormat.parse(freightCostS.trim());
          } catch (Exception pe){
              lUpdateErrors.add("exceptiondetail",
                                new ActionError("error.invalidNumberAmount", "Freight"));
              return lUpdateErrors;
          }
        }

        String miscCostS = pForm.getTotalMiscCostS();
        BigDecimal pageMisc = new BigDecimal(0);
        if(miscCostS!=null) {
          try {
              pageMisc = CurrencyFormat.parse(miscCostS);
          } catch (Exception pe){
              lUpdateErrors.add("exceptiondetail",
                                new ActionError("error.invalidNumberAmount", "Handling"));
              return lUpdateErrors;
          }
        }


     BigDecimal calcFreight = null;
     BigDecimal calcMisc = null;
     if(handlingChoise!=3) {
       //Calculate handling
       OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();
       for (int ii = 0; ii < pOrderItems.size(); ii++) {
          OrderItemData orderItemD = (OrderItemData)pOrderItems.get(ii);
          if (null == orderItemD) continue;
          if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(orderItemD.getOrderItemStatusCd())){
            continue;
          }
          OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
          int itemId = orderItemD.getItemId();
          frItem.setItemId(itemId);
          BigDecimal price = orderItemD.getCustContractPrice();
          frItem.setPrice(price);
          int qty = orderItemD.getTotalQuantityOrdered();
          frItem.setQty(qty);
          frItem.setWeight(new BigDecimal(0));
          frItems.add(frItem);
       }
       OrderHandlingView frOrder = OrderHandlingView.createValue();
       frOrder.setTotalHandling(new BigDecimal(0));
       frOrder.setTotalFreight(new BigDecimal(0));
       frOrder.setContractId(pContractId);

       int accountId = pOrder.getAccountId();
       frOrder.setAccountId(accountId);
       frOrder.setSiteId(siteId);
       frOrder.setAmount(pOrder.getTotalPrice());
       BigDecimal weight = pOrder.getGrossWeight();
       if (weight==null) weight = new BigDecimal(0);
       frOrder.setWeight(weight);
       frOrder.setItems(frItems);

       try
        {
          frOrder = pShoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
          calcFreight = frOrder.getTotalFreight().setScale(2,BigDecimal.ROUND_HALF_UP);
          calcMisc = frOrder.getTotalHandling().setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        catch (Exception exc)
        {
            lUpdateErrors.add("error",new ActionError("error.systemError",exc.getMessage()));
            exc.printStackTrace();
            return lUpdateErrors;
        }

      }
        BigDecimal freight = null;
        BigDecimal misc = null;

        switch(handlingChoise) {
          case 1:
            freight = pageFreight;
            misc = pageMisc;
            BigDecimal pageHandlingSum = pageFreight.add(pageMisc).abs();
            BigDecimal calcHandlingSum = calcFreight.add(calcMisc).abs();
            if(pageHandlingSum.subtract(calcHandlingSum).abs().doubleValue()>0.005) {
               String errorMess = "Freight and handling order amount don't "+
                                    "match to calulated amount. Order amount: "+
               pageHandlingSum.setScale(2,BigDecimal.ROUND_HALF_UP)+
               " Calculated amount: "+
               calcHandlingSum.setScale(2,BigDecimal.ROUND_HALF_UP);
               lUpdateErrors.add("error",
                  new ActionError("error.simpleGenericError",errorMess));
            }
            break;
          case 2:
            freight = calcFreight;
            misc = calcMisc;
            break;
          case 3:
            freight = pageFreight;
            misc = pageMisc;
            break;
        }

        freight = freight.setScale(2,BigDecimal.ROUND_HALF_UP);
        misc = misc.setScale(2,BigDecimal.ROUND_HALF_UP);

        //Update order if necessary
        BigDecimal orderFreight = (pOrder.getTotalFreightCost()==null)?
           (new BigDecimal(0)):pOrder.getTotalFreightCost().abs();
        BigDecimal orderMisc = (pOrder.getTotalMiscCost()==null)?
           (new BigDecimal(0)):pOrder.getTotalMiscCost().abs();

        if(orderFreight.subtract(freight).abs().doubleValue()>0.005 ||
           orderMisc.subtract(misc).abs().doubleValue()>0.005)
        {
          handlingChangedFlag = true;
          pForm.setHandlingChangedFlag(handlingChangedFlag);
          pForm.setTotalFreightCost(freight);
          pForm.setTotalFreightCostS(freight.toString());

          pForm.setTotalMiscCost(misc);
          pForm.setTotalMiscCostS(misc.toString());
        }

        //Update detail form
      return lUpdateErrors;
    }
    //--------------------------------------------------------------------------
    /**
     * <code>cancelOrder</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors cancelOrder(HttpServletRequest request,
                                                        ActionForm form,
                                                        String orderStatusId)
                                                 throws Exception
    {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        if (null == appUser) {
            lUpdateErrors.add("orderdetail",
               new ActionError("error.simpleGenericError",
               new String("Can't find application user information in session")));
            return lUpdateErrors;
        } else {
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));
                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        OrderStatusDescData orderStatusDetail = detailForm.getOrderStatusDetail();
        int orderId = orderStatusDetail.getOrderDetail().getOrderId();


//        // only order with PENDING_APPROVAL status can be updated
//        if ( orderStatusDetail.getAllowModifFl() == false ) {
//            lUpdateErrors.add("orderdetail",
//                new ActionError("error.simpleGenericError",
//                new String("Current order couldnot be modified "))
//                );
//            return lUpdateErrors;
//        }


        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        StoreOrder orderEjb = factory.getStoreOrderAPI();
        try
        {
          String userName = appUser.getUser().getUserName();
          orderEjb.cancelOrder(orderId, userName);
        } catch (Exception ex) {
          EJBException ee = (EJBException) ex.getCause();
          Exception ne = (Exception) ee.getCause();
          if (ne instanceof PipelineMessageException) {
            PipelineMessageException pex = (PipelineMessageException)ne;
            lUpdateErrors.add("orderdetail",
                              new ActionError(pex.getCode(), pex.getMessage()));
          } else {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              ex.getMessage()));

          }
          return lUpdateErrors;
        }
        return lUpdateErrors;
    }

    /**
     * <code>updateCostQty</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors updateCostQty(HttpServletRequest request,
                                                           ActionForm form,
                                                           String orderStatusId)
                                                    throws Exception
    {
        try {
            return updateCostQty_p(request, form, orderStatusId);
        } catch (Exception e) {
            ActionErrors lUpdateErrors = new ActionErrors();
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              "Trying to update and order: " + e.getMessage()
                                              )
                              );
            return lUpdateErrors;
        }
    }

    private static ActionErrors updateCostQty_p(HttpServletRequest request,
                                                           ActionForm form,
                                                           String orderStatusId)
                                                    throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc with ORDER_APPROVER role can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);
        if (null == appUser) {
          lUpdateErrors.add("orderdetail",
                    new ActionError("error.simpleGenericError",
                    new String("Can't find application user information in session")));
            return lUpdateErrors;
        } else {
          //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)&&
                !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));
                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();

        String orderStatusCd =
           detailForm.getOrderStatusDetail().getOrderDetail().getOrderStatusCd();

              if (!(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(orderStatusCd) ||
              RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO.equals(orderStatusCd) ||
              detailForm.getFullControlFl())) {

               lUpdateErrors.add("orderdetail",
                new ActionError("error.simpleGenericError",
                new String("Current order status is " + orderStatusCd))
                );

            return lUpdateErrors;
        }

        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();

        String userName = (String)session.getAttribute(Constants.USER_NAME);
        OrderData orderD = detailForm.getOrderStatusDetail().getOrderDetail();
        int accountId = detailForm.getOrderStatusDetail().getAccountId();

        int contractId = orderD.getContractId();
        int catalogId = 0;

        OrderItemDescDataVector orderItemDescList = detailForm.getOrderItemDescList();
        OrderItemDataVector changedOrderItemList = new OrderItemDataVector();
        boolean updateItemsFlag = false;
        boolean consolidatedOrderFl =
         (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd()))?
         true:false;
        ReplacedOrderItemViewVector modReplOrderItemVwV =
                                            new ReplacedOrderItemViewVector();
        ReplacedOrderViewVector replOrderVwV =
                          detailForm.getOrderStatusDetail().getReplacedOrders();

        if (null != orderItemDescList && 0 < orderItemDescList.size())
        {
           // Begin to change each ordeItemD
           OrderPropertyDataVector orderPropertyDV = new OrderPropertyDataVector();
           for (int ii = 0; ii<orderItemDescList.size(); ii++)
           {
              OrderItemDescData orderItemDescD =
                      (OrderItemDescData)orderItemDescList.get(ii);
              if (null==orderItemDescD) continue;

              boolean changeFl = false;
              OrderItemData orderItemD = orderItemDescD.getOrderItem();
              int qtyNew = 0;
              int qty = orderItemD.getTotalQuantityOrdered();
              if(!consolidatedOrderFl) {
                String qtyS = orderItemDescD.getItemQuantityS();
                if(qtyS!=null && qtyS.trim().length()>0) {
                  try {
                    qtyNew = Integer.parseInt(qtyS);
                  } catch(Exception exc) {
                     lUpdateErrors.add("error",
                       new ActionError("error.simpleGenericError",
                         "Wrong item quantity format: "+qtyS));
                     return lUpdateErrors;
                  }
                } else {
                  qtyNew = qty;
                }
              } else {
                //Consolidated order
                try {
                 qtyNew = checkReplacedItemQtyChange(modReplOrderItemVwV,
                                                orderItemD.getItemId(),
                                                orderItemD, replOrderVwV);
                } catch(Exception exc) {
                  String errorMess = exc.getMessage();
                  lUpdateErrors.add("error",
                      new ActionError("error.simpleGenericError", errorMess));
                  return lUpdateErrors;
                }
              }
              if(qtyNew!=qty){
                if(qtyNew==0) {
                  lUpdateErrors.add("error",
                   new ActionError("error.simpleGenericError",
                      "Quntity could not be 0. Delete line instead"));
                   return lUpdateErrors;
                }
                int qtyChng = qty-qtyNew;
                //Generate order property
                OrderPropertyData opD = OrderPropertyData.createValue();
                opD.setValue(""+qtyChng);
                opD.setShortDesc("Quantity changed");
                opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.QUANTITY_UPDATE);
                opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                opD.setOrderItemId(orderItemD.getOrderItemId());
                opD.setOrderId(orderId);
                opD.setModBy(userName);
                opD.setAddBy(userName);
                orderPropertyDV.add(opD);

                //************Should all be moved to pipeline************
                BigDecimal distQuantity;
                BigDecimal uomMult = orderItemD.getDistUomConvMultiplier();
                if(uomMult == null){
                  uomMult = new BigDecimal(1);
                }
                distQuantity = uomMult.multiply(new BigDecimal(qtyNew));
                orderItemD.setDistItemQuantity(distQuantity.intValue());
                //************END Should all be moved to pipeline************

                orderItemD.setTotalQuantityOrdered(qtyNew);
                orderItemD.setAckStatusCd(null);
                changeFl = true;
              }
              BigDecimal costNewBD = null;
              BigDecimal costBD = orderItemD.getDistItemCost();
              String costS = orderItemDescD.getCwCostS();
              if(costS!=null && costS.trim().length()>0) {
                 try {
                   costNewBD = new BigDecimal(costS);
                 }catch(Exception exc) {
                   lUpdateErrors.add("error",
                      new ActionError("error.simpleGenericError",
                         "Wrong item cost format: "+costS));
                    return lUpdateErrors;
                 }
                 //************Should all be moved to pipeline************
                  BigDecimal distUomConvCost;
                  BigDecimal uomMult = orderItemD.getDistUomConvMultiplier();
                  if(uomMult == null){
                      uomMult = new BigDecimal(1);
                  }
                  uomMult = uomMult.setScale(2,BigDecimal.ROUND_HALF_UP);
                 try{
                  distUomConvCost = costNewBD.setScale(2).divide(uomMult,BigDecimal.ROUND_UNNECESSARY);
                 }catch(ArithmeticException e){
                     lUpdateErrors.add("error",
                     new ActionError("error.simpleGenericError",
                       "Item Cost ("+costS+" not divisable by : "+orderItemD.getDistUomConvMultiplier()));
                     return lUpdateErrors;
                 }
                 orderItemD.setDistUomConvCost(distUomConvCost);
                 //************END Should all be moved to pipeline************

                 orderItemD.setDistItemCost(costNewBD);
                 changeFl = true;
              }
              if(changeFl) {
                changedOrderItemList.add(orderItemD);
              }
        } // end if null != orderItemDescList

        //Set order price
        OrderItemDataVector orderItemDV = new OrderItemDataVector();
        BigDecimal totalPrice = new BigDecimal(0);
        for(Iterator iter=orderItemDescList.iterator(); iter.hasNext();) {
           OrderItemDescData orderItemDescD = (OrderItemDescData)iter.next();
           if (null==orderItemDescD) continue;
           OrderItemData orderItemD = orderItemDescD.getOrderItem();
           orderItemDV.add(orderItemD);
           String itemStatus = orderItemD.getOrderItemStatusCd();
           if(!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)){
              BigDecimal amount = orderItemD.getCustContractPrice();
              if(amount!=null) {
                int qty = orderItemD.getTotalQuantityOrdered();
                amount = amount.multiply(new BigDecimal(qty));
                amount = amount.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalPrice = totalPrice.add(amount);
              }
           }
        }
        totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
        orderD.setTotalPrice(totalPrice);
        orderD.setOriginalAmount(totalPrice);

        BigDecimal totalTaxAmount = TaxUtilAvalara.recalculateItemTaxAmount(orderItemDV);
        orderD.setTotalTaxCost(totalTaxAmount);

        //Set freight
        lUpdateErrors = setHandlingCharges(request, detailForm,
             factory.getShoppingServicesAPI(),orderD, orderItemDV, contractId);
        if(lUpdateErrors.size()>0) {
           return lUpdateErrors;
        }
        orderD.setTotalFreightCost(detailForm.getTotalFreightCost());
        orderD.setTotalMiscCost(detailForm.getTotalMiscCost());

        if(changedOrderItemList.size()>0) {
          orderEjb.updateOrderSentToErp(orderD, changedOrderItemList,
                                 orderPropertyDV, modReplOrderItemVwV, userName);
    //public void updateOrderSentToErp(OrderData pOrder,
    //                                 OrderItemDataVector pOrderItemDV,
    //                                 OrderPropertyDataVector pOrderPropertyDV,
    //                                 ReplacedOrderItemViewVector pReplacedOrderItemVwV,
    //                                 String pUser)
          detailForm.setOrderItemDescList(orderEjb.getOrderItemDescCollection(orderId,null));
        }

        setUiOrderAmounts(orderD, detailForm, null);

     }
     return lUpdateErrors;
   }

    /**
     *Updates an order for the receiving system. It update the order items and marks the order as having been received
     */
    public static ActionErrors  updateReceivedItems(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        return updateOrderCustomer(request,form,true);
    }

        /**
     *Updates an order from the shopping portal (runtime).  Indicate if the customer
     *is updating the qty recieved as this is a seperate button.
     */
    private static ActionErrors  updateOrderCustomer(HttpServletRequest request,
            ActionForm form,boolean doReceived)
            throws Exception {
        //find me
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        String user = ((CleanwiseUser)session.getAttribute(Constants.APP_USER)).getUserName();

        if (null == factory){
            throw new Exception("Without APIAccess.");
        }
        CustomerOrderChangeRequestData changeReq = CustomerOrderChangeRequestData.createValue();
        changeReq.setDoNotChangeOrderStatusCd(true);

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        changeReq.setOrderData(detailForm.getOrderStatusDetail().getOrderDetail());
        if(doReceived){
                changeReq.setMarkReceived(true);
        }

        if(detailForm.getOrderItemDescList() == null){
            return ae;
        }

        ArrayList itemChanges = new ArrayList();


        //validate the entered string values
        Iterator it = detailForm.getOrderItemDescList().iterator();
        while(it.hasNext()){
                boolean somethingChanged = false;

            OrderItemDescData desc = (OrderItemDescData) it.next();
            OrderItemChangeRequestData itmChgReq = new OrderItemChangeRequestData(desc.getOrderItem());
            if(doReceived){
                somethingChanged = true; //add them all, as 0's are significant
                int recQtyI = 0;
                    String recQty = desc.getItemQuantityRecvdS();
                    if(recQty != null){
                        recQty = recQty.trim();
                    }
                    if(Utility.isSet(recQty)){
                        try{
                            recQtyI = Integer.parseInt(recQty);
                        }catch(Exception e){
                            ae.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.invalidNumber","Received Qty"));
                        }
                        desc.setItemQuantityRecvdS(recQty);
                    }else{
                        desc.setItemQuantityRecvdS("0");
                    }
                    itmChgReq.setNewTotalQuantityReceived(new Integer(recQtyI));
            }
            if(somethingChanged){
                itemChanges.add(itmChgReq);
            }
        }

        changeReq.setOrderItemChangeRequests(itemChanges);
        changeReq.setUserName(user);
        //if any validation errors occured return
        if(ae != null && ae.size() > 0){
            return ae;
        }

        factory.getIntegrationServicesAPI().webProcessOrderChangeRequest(changeReq);
        return ae;
        /*
        ActionErrors ae = updateOrderItemsCustomer(request, form);
        if(ae.size() == 0){
            OrderOpDetailForm detailForm = (OrderOpDetailForm)form;
            int oid = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

            if (null == factory){
                throw new Exception("Without APIAccess.");
            }
            Order orderEjb = factory.getOrderAPI();
            OrderPropertyDataVector topdv = orderEjb.getOrderPropertyCollection(oid, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
            OrderPropertyData opd=null;
            if(topdv.size() > 0){
                opd = (OrderPropertyData) topdv.get(0);
            }


            String user = ((CleanwiseUser)session.getAttribute(Constants.APP_USER)).getUserName();

            //OrderPropertyData opd = Utility.getOrderProperty(detailForm.getOrderPropertyList(),RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
            if(opd == null){
                opd = OrderPropertyData.createValue();
                opd.setAddBy(user);
                opd.setModBy(user);
                opd.setOrderId(oid);
                opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
                opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
            }
            opd.setValue(Boolean.TRUE.toString());

            orderEjb.addNote(opd);
        }
        return ae;
        */
    }





   public static ActionErrors saveSiteNoteLine(HttpServletRequest request,
                                                           ActionForm form)
   throws Exception
   {
     StoreOrderDetailForm detailForm = (StoreOrderDetailForm) form;
     HttpSession session = request.getSession();
     ActionErrors ae = new ActionErrors();
     String noteIdS = request.getParameter("noteId");
     int noteId = Integer.parseInt(noteIdS);
     String lineText = request.getParameter("siteNoteLine");
     if(lineText==null || lineText.trim().length()==0) {
        ae.add("error",
           new ActionError("error.simpleGenericError",
              "Empty Note Line Found"));
         return ae;
     }
     APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
     Note noteEjb = factory.getNoteAPI();
    ///////////////////////////////////////////////////////////////////
     boolean foundFl=false;
     NoteJoinViewVector njVwV = detailForm.getSiteNotes();
     for(int ii= 0; ii<njVwV.size(); ii++){
       NoteJoinView njVw = (NoteJoinView) njVwV.get(ii);
       NoteData nD = njVw.getNote();
       int nId = nD.getNoteId();
       if(nId==noteId) {
         foundFl = true;
         NoteJoinView njVwInterface = NoteJoinView.createValue();
         njVwInterface.setNote(nD);
         NoteTextDataVector ntDV = new NoteTextDataVector();
         NoteTextData ntD = NoteTextData.createValue();
         ntDV.add(ntD);
         njVwInterface.setNoteText(ntDV);
         ntD.setNoteId(noteId);
         ntD.setNoteText(lineText);
         CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
         UserData userD = appUser.getUser();
         String userName = userD.getUserName();
         ntD.setUserFirstName(userD.getFirstName());
         ntD.setUserLastName(userD.getLastName());
         ntD.setPageNum(0);
         ntD.setSeqNum(0);
         njVwInterface = noteEjb.saveNote(njVwInterface,userName,true);
         njVwV.remove(ii);
         njVwV.add(ii, njVwInterface);
         break;
       }
     }

     if(!foundFl){
       String mess = "Site note not found";
       ae.add("error",new ActionError("error.simpleGenericError",mess));
       return ae;
     }

     return ae;
   }

   public static ActionErrors addSiteNote(HttpServletRequest request,
                                                           ActionForm form)
   throws Exception
   {
     StoreOrderDetailForm detailForm = (StoreOrderDetailForm) form;
     HttpSession session = request.getSession();
     ActionErrors ae = new ActionErrors();
     String siteIdS = request.getParameter("siteId");
     int siteId = Integer.parseInt(siteIdS);
     String title = request.getParameter("siteNoteTitle");
     if(title==null || title.trim().length()==0) {
        ae.add("error",
           new ActionError("error.simpleGenericError",
              "No title provided"));
         return ae;
     }
     String lineText = request.getParameter("siteNoteLine");
     if(lineText==null || lineText.trim().length()==0) {
        ae.add("error",
           new ActionError("error.simpleGenericError",
              "Empty Note Line Found"));
         return ae;
     }
     OrderStatusDescData orderStatusDescD = detailForm.getOrderStatusDetail();
     OrderData orderD = orderStatusDescD.getOrderDetail();
     int orderSiteId = orderD.getSiteId();
     if(orderSiteId!=siteId) {
        ae.add("error",
           new ActionError("error.simpleGenericError",
              "Wrong site number. Probaly the page has expired"));
         return ae;
     }
     APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
     Note noteEjb = factory.getNoteAPI();
     int topicId = 0;
     PropertyDataVector topicDV =
         noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.SITE_NOTE_TOPIC);
     String topicName = "CRC Site Notes";
     boolean foundFl = false;
     for(Iterator iter = topicDV.iterator(); iter.hasNext();) {
       PropertyData pD = (PropertyData) iter.next();
       if(topicName.equals(pD.getValue())) {
             topicId = pD.getPropertyId();
             foundFl = true;
       }
     }
     if(!foundFl) {
      String mess = "Site notes are not configured";
      ae.add("error",new ActionError("error.simpleGenericError",mess));
      return ae;
     }
     CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
     UserData userD = appUser.getUser();
     String userName = userD.getUserName();
     NoteJoinView njVwInterface = NoteJoinView.createValue();
     NoteData nD = NoteData.createValue();
     njVwInterface.setNote(nD);
     nD.setBusEntityId(siteId);
     nD.setNoteTypeCd(RefCodeNames.NOTE_TYPE_CD.SITE_NOTE);
     nD.setPropertyId(topicId);
     nD.setTitle(title);
     NoteTextDataVector ntDV = new NoteTextDataVector();
     NoteTextData ntD = NoteTextData.createValue();
     ntDV.add(ntD);
     njVwInterface.setNoteText(ntDV);
     ntD.setNoteText(lineText);
     ntD.setUserFirstName(userD.getFirstName());
     ntD.setUserLastName(userD.getLastName());
     ntD.setPageNum(0);
     ntD.setSeqNum(0);
     njVwInterface = noteEjb.saveNote(njVwInterface,userName,true);

     NoteJoinViewVector njVwV = detailForm.getSiteNotes();
     if(njVwV==null) njVwV = new NoteJoinViewVector();
     njVwV.add(0,njVwInterface);
     detailForm.setSiteNotes(njVwV);

     return ae;
   }

    private static void setUiOrderAmounts(OrderData pOrder,
                                          StoreOrderDetailForm pForm,
                                          OrderStatusDescData orderDesc) {

        BigDecimal subTotal = pOrder.getTotalPrice();
        if (subTotal == null) subTotal = new BigDecimal(0);
        pForm.setSubTotal(subTotal.doubleValue());

        BigDecimal freight = pOrder.getTotalFreightCost();
        if (freight == null) freight = new BigDecimal(0);
        pForm.setTotalFreightCostS(freight.toString());
        pForm.setTotalFreightCost(freight);

        BigDecimal misc = pOrder.getTotalMiscCost();
        if (misc == null) misc = new BigDecimal(0);
        pForm.setTotalMiscCostS(misc.toString());
        pForm.setTotalMiscCost(misc);

        BigDecimal tax = pOrder.getTotalTaxCost();
        if (tax == null) tax = new BigDecimal(0);
        pForm.setTotalTaxCost(tax.doubleValue());
        pForm.setTotalTaxCostStr(tax.toString());

        if (orderDesc == null) {

            pForm.setRushOrderCharge(null);
            pForm.setSmallOrderFeeAmt(null);
            pForm.setFuelSurchargeAmt(null);
            pForm.setFuelSurchargeStr(null);
            pForm.setSmallOrderFeeStr(null);
            pForm.setDiscountAmt(null);
            pForm.setDiscountStr(null);

        } else {

            pForm.setRushOrderCharge(orderDesc.getRushOrderCharge());

            OrderMetaData oSmallOrderFee = orderDesc.getMetaObject(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
            if (oSmallOrderFee != null) {
                pForm.setSmallOrderFeeAmt(new BigDecimal(oSmallOrderFee.getValue()));
                pForm.setSmallOrderFeeStr(oSmallOrderFee.getValue());
            } else {
                pForm.setSmallOrderFeeAmt(null);
                pForm.setSmallOrderFeeStr(null);
            }

            OrderMetaData oFuelSurcharge = orderDesc.getMetaObject(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
            if (oFuelSurcharge != null) {
                pForm.setFuelSurchargeAmt(new BigDecimal(oFuelSurcharge.getValue()));
                pForm.setFuelSurchargeStr(oFuelSurcharge.getValue());
            } else {
                pForm.setFuelSurchargeAmt(null);
                pForm.setFuelSurchargeStr(null);
            }

            OrderMetaData oDiscount = orderDesc.getMetaObject(RefCodeNames.CHARGE_CD.DISCOUNT);
            if (oDiscount != null && oDiscount.getValue() != null) {
            	String dVal = oDiscount.getValue();
            	if(dVal.startsWith("-")){
            		dVal = dVal.replaceAll("-", "");
            	}
                pForm.setDiscountAmt(new BigDecimal(dVal));
                pForm.setDiscountStr(oDiscount.getValue());
            } else {
                pForm.setDiscountAmt(null);
                pForm.setDiscountStr(null);
            }
        }

        pForm.calculateTotalAmount();
    }

   private static void setUiWorkflowIndList(String pWorkflowInd,
                                         StoreOrderDetailForm pForm) {
     Vector options = new Vector();
     if (pWorkflowInd == null || "".equals(pWorkflowInd)) {
       options.add(new LabelValueBean("Empty", ""));
     } else {
       options.add(new LabelValueBean(pWorkflowInd, pWorkflowInd));
     }
     if (!RefCodeNames.WORKFLOW_IND_CD.SKIP.equals(pWorkflowInd)) {
       options.add(new LabelValueBean(RefCodeNames.WORKFLOW_IND_CD.SKIP,
                                      RefCodeNames.WORKFLOW_IND_CD.SKIP));
     }
     if (!RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS.equals(pWorkflowInd)) {
       options.add(new LabelValueBean(RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS,
                                      RefCodeNames.WORKFLOW_IND_CD.TO_PROCESS));
     }
     pForm.setWorkflowIndList(options);
   }

   private static void setUiOrderStatusList(String pOrderStatus,
                                         StoreOrderDetailForm pForm,
                                         ListService listServiceEjb) throws Exception{
     Vector options = new Vector();
     if (pOrderStatus == null || "".equals(pOrderStatus)) {
       options.add(new LabelValueBean("Empty", ""));
     } else {
       options.add(new LabelValueBean(pOrderStatus, pOrderStatus));
     }
     RefCdDataVector statusv = listServiceEjb.getRefCodesCollection("ORDER_STATUS_CD");
     for (int i = 0; i < statusv.size(); i++) {
       RefCdData refD = (RefCdData)statusv.get(i);
       if (!refD.getShortDesc().equals(pOrderStatus)) {
         options.add(new LabelValueBean(refD.getValue(),refD.getValue()));
       }
     }
     pForm.setOrderStatusList(options);
   }

   private static void setUiOrderBudgetCd(String pOrderBudgetCd,
                                         StoreOrderDetailForm pForm) {
     if (!RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(pOrderBudgetCd)) {
       pForm.setApplyBudget(true);
     } else {
       pForm.setApplyBudget(false);
     }
   }

   private static void setOrderItemStatusList(StoreOrderDetailForm pForm,
                                                ListService listServiceEjb) throws Exception{
     Vector options = new Vector();
     options.add(new LabelValueBean("", ""));
     options.add(new LabelValueBean("Ordered", "Ordered"));
     RefCdDataVector statusv = listServiceEjb.getRefCodesCollection("ORDER_ITEM_STATUS_CD");
     for (int i = 0; i < statusv.size(); i++) {
       RefCdData refD = (RefCdData)statusv.get(i);
       options.add(new LabelValueBean(refD.getValue(),refD.getValue()));
     }
     pForm.setOrderItemStatusList(options);
   }

   private static void setPoItemStatusList(StoreOrderDetailForm pForm,
                                                ListService listServiceEjb) throws Exception{
     Vector options = new Vector();
     options.add(new LabelValueBean("", ""));
     RefCdDataVector statusv = listServiceEjb.getRefCodesCollection("PURCHASE_ORDER_STATUS_CD");
     for (int i = 0; i < statusv.size(); i++) {
       RefCdData refD = (RefCdData)statusv.get(i);
       options.add(new LabelValueBean(refD.getValue(),refD.getValue()));
     }
     pForm.setPoItemStatusList(options);
   }

    public static ActionErrors dontSendToDistributor(HttpServletRequest request,
                                                           ActionForm form,
                                                           String orderStatusId)
                                                    throws Exception
    {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        // only crc manager can do this update
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);
        if (null == appUser) {
          lUpdateErrors.add("orderdetail",
                    new ActionError("error.simpleGenericError",
                    new String("Can't find application user information in session")));
            return lUpdateErrors;
        } else {
          //String userRole = appUser.getUser().getWorkflowRoleCd();
            String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType))
            {
                lUpdateErrors.add("orderdetail",
                                  new ActionError("error.simpleGenericError",
                                                  "Current user does not have enough permissions"));
                return lUpdateErrors;
            }
        }

        StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
        int orderId = detailForm.getOrderStatusDetail().getOrderDetail().getOrderId();


        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }


        Order orderEjb = factory.getOrderAPI();
        String userName = (String)session.getAttribute(Constants.USER_NAME);
        OrderPropertyDataVector orderPropertyDV = new OrderPropertyDataVector();


        //Generate order properties
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setValue("true");
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setOrderId(orderId);
        opD.setModBy(userName);
        opD.setAddBy(userName);
        orderPropertyDV.add(opD);

        opD = OrderPropertyData.createValue();
        opD.setValue("Order was set to billing only.");
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setOrderId(orderId);
        opD.setModBy(userName);
        opD.setAddBy(userName);
        orderPropertyDV.add(opD);

        orderEjb.makeOrderNoFulfillment(orderPropertyDV);

    return lUpdateErrors;
   }

    public static ActionErrors updateShipping(HttpServletRequest request,
                                              ActionForm form)
        throws Exception
    {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        try {
            String id = request.getParameter("id"),
                for_disterpnum = request.getParameter("disterpnum"),
                tofhid = request.getParameter("freightHandler")
                ;
            APIAccess factory = (APIAccess)session.getAttribute
                (Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("System acceess error (2006-10-2)");
            }

            Order orderEjb = factory.getOrderAPI();
            String userName = (String)session.getAttribute(Constants.USER_NAME);
            String resp =
                orderEjb.updateOrderShippingForDist( Integer.parseInt(id),
                                                     Integer.parseInt(tofhid),
                                                     for_disterpnum,
                                                     userName );
            StoreOrderDetailForm detailForm = (StoreOrderDetailForm)form;
            detailForm.setResultMessage(resp);

        } catch (Exception e) {
            lUpdateErrors.add("orderdetail",
                              new ActionError("error.simpleGenericError",
                                              "Trying to update and order: " + e.getMessage()
                                              )
                              );
        }
        return lUpdateErrors;
    }

    public static ActionErrors fillOrderAccountFields(ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      StoreOrderSearchForm pForm = (StoreOrderSearchForm)form;
      pForm.setAccountIdList(Utility.toCommaSting(Utility.toIdVector(pForm.getAccountFilter())));
      return ae;
    }

    public static ActionErrors fillOrderDistributorFields(ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      StoreOrderSearchForm pForm = (StoreOrderSearchForm)form;
      pForm.setDistributorIdList(Utility.toCommaSting(Utility.toIdVector(pForm.getDistributorFilter())));
      return ae;
    }

    public static ActionErrors fillOrderSiteFields(ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      StoreOrderSearchForm pForm = (StoreOrderSearchForm)form;
      pForm.setSiteIdList(Utility.toCommaSting(Utility.toIdVector(pForm.getSiteFilter())));

      IdVector ids = Utility.toIdVector(pForm.getSiteFilter());
      if (ids.size() > 0) {
        Iterator it = ids.iterator();
        StringBuffer ret = new StringBuffer();
        ret.append(it.next());
        pForm.setSiteId(ret.toString());
      } else {
        pForm.setSiteId("");
      }
      return ae;
    }

    public static ActionErrors fillPlacedByFields(ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      StoreOrderSearchForm pForm = (StoreOrderSearchForm)form;
      StringBuffer sb = new StringBuffer();
      Iterator<UserData> filterIterator = pForm.getUserFilter().iterator();
      while(filterIterator.hasNext()){
    	  sb.append(filterIterator.next().getUserName().trim());
    	  if(filterIterator.hasNext()) sb.append(",");
      }
      pForm.setPlacedBy(sb.toString());
      
      return ae;
    }

   public static void saveCheckBoxesState(ActionForm form){
      StoreOrderDetailForm pForm = (StoreOrderDetailForm)form;
      boolean[] reSaleCopy = null;
      boolean[] taxExemptCopy = null;
      boolean applyBudgetCopy =pForm.getApplyBudget();
      boolean reBillOrderCopy = pForm.getReBillOrder();
      if (pForm.getOrderItemDescList() != null){
        reSaleCopy = new boolean[pForm.getOrderItemDescList().size()];
        taxExemptCopy = new boolean[pForm.getOrderItemDescList().size()];

        for (int i = 0; i < pForm.getOrderItemDescList().size(); i++) {
          OrderItemDescData oid = (OrderItemDescData) pForm.getOrderItemDescList().get(i);
          reSaleCopy[i] = oid.getReSale();
          taxExemptCopy[i] = oid.getTaxExempt();
        }
      }
      pForm.setReBillOrderCopy(reBillOrderCopy);
      pForm.setApplyBudgetCopy(applyBudgetCopy);
      pForm.setReSaleCopy(reSaleCopy);
      pForm.setTaxExemptCopy(taxExemptCopy);
    }

    public static void restoreCheckBoxesState(ActionForm form){
      StoreOrderDetailForm pForm = (StoreOrderDetailForm)form;
      pForm.setApplyBudget(pForm.getApplyBudgetCopy());
      pForm.setReBillOrder(pForm.getReBillOrderCopy());
      boolean[] reSaleCopy = pForm.getReSaleCopy();
      boolean[] taxExemptCopy = pForm.getTaxExemptCopy();
      if (pForm.getOrderItemDescList() != null ){
        for (int i = 0; i < pForm.getOrderItemDescList().size(); i++) {
          OrderItemDescData oid = (OrderItemDescData) pForm.getOrderItemDescList().get(i);
          if (reSaleCopy.length > 0){
            oid.setReSale(reSaleCopy[i]);
          }
          if (taxExemptCopy.length >0 ){
            oid.setTaxExempt(taxExemptCopy[i]);
          }
        }
      }

    }

}
