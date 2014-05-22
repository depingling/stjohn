/*
 * CustAcctMgtInvoiceLogic.java
 *
 * Created on July 1, 2005, 2:24 PM
 */

package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
//import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
//import com.cleanwise.service.api.value.PurchaseOrderDataVector;
import com.cleanwise.service.api.value.PurchaseOrderStatusCriteriaData;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataViewVector;
import com.cleanwise.view.forms.CustAcctMgtInvoiceDetailForm;
import com.cleanwise.view.forms.CustAcctMgtInvoiceSearchForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.pdf.PdfInvoiceDist;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import java.util.Date;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.view.utils.DisplayListSort;

import com.cleanwise.service.api.value.InvoiceDistView;
import com.cleanwise.service.api.value.InvoiceDistViewVector;
import com.cleanwise.service.api.value.InvoiceNetworkServiceData;
import com.cleanwise.service.api.value.InvoiceNetworkServiceDataVector;
import com.cleanwise.service.api.value.AssetData;
import java.util.Comparator;
import com.cleanwise.view.utils.pdf.PdfOrderGuide;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.service.api.value.StoreData;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.forms.UserShopForm;
import java.util.Properties;
import java.io.FileInputStream;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.view.utils.ReportRequest;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.view.utils.pdf.PdfCatalogProduct;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.utils.ContactUsInfo;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.CurrencyData;
import java.util.Locale;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.view.utils.pdf.PdfInvoiceDetail;
import org.apache.log4j.*;

/**
 *Encapsulats the logic regarding a vendor invoice in the system forthe store admin portal.
 * @author bstevens
 */
public class CustAcctMgtInvoiceLogic {
    private static final Logger log = Logger.getLogger(CustAcctMgtInvoiceLogic.class);

  public static final BigDecimal ZERO = new BigDecimal(0);
  private static SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");

  public static void init(HttpServletRequest request, ActionForm form) throws Exception {
     HttpSession session = request.getSession();
     APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
     if (null == factory) {
         throw new Exception("Without APIAccess.");
     }

     ListService listServiceEjb = factory.getListServiceAPI();

     if (session.getAttribute("PurchaseOrder.status.vector") == null) {
         RefCdDataVector statusv =
         listServiceEjb.getRefCodesCollection("PURCHASE_ORDER_STATUS_CD");
         session.setAttribute("PurchaseOrder.status.vector", statusv);
     }

     if (session.getAttribute("Open.line.status.vector") == null) {
         RefCdDataVector statusv =
         listServiceEjb.getRefCodesCollection("OPEN_LINE_STATUS_CD");
         session.setAttribute("Open.line.status.vector", statusv);
     }

     if (session.getAttribute("OrderProperty.ShipStatus.vector") == null){
             CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

         RefCdDataVector statusv =
         listServiceEjb.getRefCodesCollection("ORDER_PROPERTY_SHIP_STATUS");

         if(appUser.getUser().getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR)
                     && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MODIFY_ORDER_ITEM_QTY)){

             RefCdData newFunc = RefCdData.createValue();
             newFunc.setRefCd("ORDER_PROPERTY_SHIP_STATUS");
             newFunc.setShortDesc(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE);
             newFunc.setValue(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE);

             statusv.add(newFunc);
         }
         session.setAttribute("OrderProperty.ShipStatus.vector", statusv);
     }

  }

  /**
   *Uses the defined "error" status codes to serach the invoice sytem
   */
  /**
   *Performs  search against the po subsystem for invoices
   */
  public static ActionMessages searchInvoiceDist(HttpServletRequest request, ActionForm form) 
  throws Exception {
    ActionMessages updateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    CustAcctMgtInvoiceSearchForm sForm = (CustAcctMgtInvoiceSearchForm) form;
    PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();
    boolean criteriaSet = populateInvoiceDistSearchCriteria(sForm, updateErrors, searchCriteria, session);
    //if something was specified search for it.
    if (!criteriaSet) {
      updateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.invalidSearchCritera"));
    }

    if (updateErrors.size() > 0) {
      return updateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
/*
    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    PurchaseOrderStatusDescDataViewVector poStatusDescData =
      purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria);
    sForm.setResultList(poStatusDescData);
*/
    Order orderEjb = factory.getOrderAPI();
  /* params list
            Date pInvoiceSearchStartDate,
            Date pInvoiceSearchEndDate,
            String pDateSearchType,
            String pErpPoNum,
            String pInvoiceNum,
            String pVoucherNumber,
            String pOptionalInvoiceStatusCd
   */
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
    int storeId = appUser.getUserStore().getStoreId();
    
    String invStatus = null;
    List invStatusL = searchCriteria.getInvoiceDistStatusList();
    if(invStatusL!=null && !invStatusL.isEmpty()) {
        invStatus = (String) invStatusL.get(0);
    }

    InvoiceDistDataVector invoiceSearchData  = orderEjb.getInvoiceDistCollectionForStore(
                                                    searchCriteria.getInvoiceDistDateRangeBegin(),
                                                    searchCriteria.getInvoiceDistDateRangeEnd(),
                                                    null,
                                                    searchCriteria.getErpPONum(),
                                                    searchCriteria.getInvoiceDistNum(), null, 
                                                    invStatus,
                                                    new Integer(storeId) );
    sForm.setResultList(invoiceSearchData);

    return updateErrors;
  }


  /**
   *Populates the search criteria
   */
  private static boolean populateInvoiceDistSearchCriteria(CustAcctMgtInvoiceSearchForm sForm, ActionMessages updateErrors,
    PurchaseOrderStatusCriteriaData searchCriteria, HttpSession session)
  {

    boolean criteriaSet = false;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");


     CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
     if(!RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(appUser.getUser().getUserTypeCd()) && 
        !RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
         searchCriteria.setStoreIdVector(appUser.getUserStoreAsIdVector());
     }

     if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(appUser.getUser().getUserTypeCd())){
         searchCriteria.setUserId(appUser.getUserId());
     }
     if( Utility.isSet(sForm.getOutboundPoNum())) {
          criteriaSet = true;
          searchCriteria.setOutboundPoNum(sForm.getOutboundPoNum().trim());
      }
 /*     if( Utility.isSet(sForm.getErpPONum())) {
        criteriaSet = true;
        searchCriteria.setOutboundPoNum(sForm.getErpPONum().trim());
      } */
      if( Utility.isSet(sForm.getInvoiceDistDateRangeBegin())) {
          criteriaSet = true;
          Date begDate =  new Date();
          try {
              begDate  = simpleDateFormat.parse(sForm.getInvoiceDistDateRangeBegin().trim());
          }
          catch (Exception e) {
              begDate = null;
              updateErrors.add("posearch", new ActionError("variable.date.format.error","Dist Invoice Begin Date Range"));
          }
          searchCriteria.setInvoiceDistDateRangeBegin(begDate);
      }
      if( Utility.isSet(sForm.getInvoiceDistDateRangeEnd())) {
          criteriaSet = true;
          Date endDate =  new Date();
          try {
              endDate  = simpleDateFormat.parse(sForm.getInvoiceDistDateRangeEnd().trim());
          }
          catch (Exception e) {
              updateErrors.add("posearch", new ActionError("variable.date.format.error","Dist Invoice End Date Range"));
              endDate = null;
          }
          searchCriteria.setInvoiceDistDateRangeEnd(endDate);
      }

    if( Utility.isSet(sForm.getErpPONum())) {
           criteriaSet = true;
           searchCriteria.setErpPONum(sForm.getErpPONum().trim());
    }

    if( Utility.isSet(sForm.getInvoiceDistNum())) {
          criteriaSet = true;
          searchCriteria.setInvoiceDistNum(sForm.getInvoiceDistNum().trim());
        }

     if(Utility.isSet(sForm.getSearchStatus())) {
         ArrayList statusAL = new ArrayList();
         statusAL.add(sForm.getSearchStatus());
         searchCriteria.setInvoiceDistStatusList(statusAL);
         criteriaSet = true;
     }
    searchCriteria.setInvoiceSearch(true);
    return criteriaSet;
  }


   /**
   * Expects either a invoiceId or a poId.
   * sets the nextInvoiceInList and prevInvoiceInList properties of the detail form,
   * and will return the PurchaseOrderStatusDescDataView from the list in the search form.
   */

  private static PurchaseOrderStatusDescDataView getInvoiceFromListAndInitNextAndPrev(CustAcctMgtInvoiceDetailForm
          detailForm, HttpSession session, int invoiceId, int poId) {

      CustAcctMgtInvoiceSearchForm searchForm = getCustAcctMgtInvoiceSearchForm(session);
      if (searchForm == null || invoiceId == 0) {
          detailForm.setNextInvoiceInList(0);
          detailForm.setPrevInvoiceInList(0);
          return null;
      }

      List invoiceList = searchForm.getResultList();
      Iterator it = invoiceList.iterator();
      int prevId = 0;
      int nextId = 0;
      PurchaseOrderStatusDescDataView desc = null;

      boolean foundFl = false;
      while (it.hasNext()) {
          desc = (PurchaseOrderStatusDescDataView) it.next();
          if (invoiceId != 0) {
              //use invoice searching
              if(desc != null && desc.getInvoiceDist() != null && desc.getInvoiceDist().getInvoiceDistId() == invoiceId){
                  foundFl = true;
              }
          } else if (poId != 0) {
              //use po searching
              if (desc.getPurchaseOrderData().getPurchaseOrderId() == poId) {
                  foundFl = true;
              }
          }

          if (foundFl) {
              while (it.hasNext()) {
                  //find the next valid invoice id
                  PurchaseOrderStatusDescDataView next = (PurchaseOrderStatusDescDataView) it.next();
                  if (next.getInvoiceDist() != null) {
                      nextId = next.getInvoiceDist().getInvoiceDistId();
                      break; //inner iterator
                  }
              }
              break; //outer iteration
          } else {
              if (desc.getInvoiceDist() != null) {
                  prevId = desc.getInvoiceDist().getInvoiceDistId();
                  desc = null;
              }
          }
      }

      if (desc == null) {
          //if we did not find it in the list set the vars up to 0.
          //this prevents wiered next and previous values if we linked
          //in from some other part of the system
          nextId = 0;
          prevId = 0;
      }
      detailForm.setNextInvoiceInList(nextId);
      detailForm.setPrevInvoiceInList(prevId);
      return desc;
  }
 /*
  public static ActionErrors sort(HttpServletRequest request, CustAcctMgtInvoiceSearchForm pForm) throws Exception {
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();

     ArrayList resultList = (ArrayList) pForm.getResultList();
     if (resultList == null) {
         return ae;
     }
     String sortField = request.getParameter("sortField");
     DisplayListSort.sort(resultList, sortField);
     return ae;
 }

 public static ActionErrors sort(HttpServletRequest request, CustAcctMgtInvoiceDetailForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String sortField = (request.getParameter("sortField") == null ) ? "linenumber" : request.getParameter("sortField");

    InvoiceDistDetailDataVector resultList = (InvoiceDistDetailDataVector) pForm.getInvoiceDetail();
    if (resultList == null) {
        return ae;
    }
    DisplayListSort.sort(resultList, sortField);
    return ae;
}
*/

 public static InvoiceDistView getInvoiceDistView(HttpSession session, int pInvoiceId ) throws Exception {

     APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
     if (null == factory) {
       throw new Exception("Without APIAccess.");
     }

     Order orderEjb = factory.getOrderAPI();
     InvoiceDistView view = new InvoiceDistView();

     InvoiceDistData header  = orderEjb.getInvoiceDist(pInvoiceId);
     InvoiceDistDetailDataVector items = (InvoiceDistDetailDataVector)orderEjb.getInvoiceDistDetailCollection(pInvoiceId);
     OrderPropertyDataVector propLines = (OrderPropertyDataVector)orderEjb.getOrderPropertyCollectionByInvoiceDist(pInvoiceId, RefCodeNames.ORDER_PROPERTY_TYPE_CD.NETWORK_INVOICE);

     view.setInvoiceHeader(header);
     view.setInvoiceLines(items);
     view.setOrderProperties(propLines);
     return view;
   }

  /**
   *Fetches the detail for an invoice based off the passed in optInvoiceDistId.  If that is not supplied the request parameter "id" is used.
   */
  public static ActionMessages fetchInvoiceDistDetail(HttpServletRequest request, ActionForm form, int optInvoiceDistId) throws
    Exception {
    ActionMessages updateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    CustAcctMgtInvoiceDetailForm sForm = (CustAcctMgtInvoiceDetailForm) form;
    int invoiceId = optInvoiceDistId;
    if (invoiceId == 0) {
      try {
        invoiceId = Integer.parseInt(request.getParameter("id"));
      } catch (Exception e) {}
    }
    if (invoiceId == 0) {
      updateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
      return updateErrors;
    }

//    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
//    if (null == factory) {
//      throw new Exception("Without APIAccess.");
//    }

 //   PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();

    //init previous next ids and the invoice if it was in teh search list
//    InvoiceDistView desc = getInvoiceFromListAndInitNextAndPrev(sForm, session, invoiceId, 0);
    InvoiceDistView desc = null;
    //if we didn't find it in the list fetch it
    if (desc == null) {
      desc = getInvoiceDistView(session, invoiceId);
    }

    if (desc != null){
       sForm.setInvoiceView(desc);

       OrderPropertyDataVector propLines = desc.getOrderProperties();

      // Invoice Result Header population
      if (desc.getInvoiceHeader() != null){
        InvoiceDistData invoiceHeader = desc.getInvoiceHeader();

        sForm.setInvoiceNumber(invoiceHeader.getInvoiceNum());
        sForm.setInvoiceDate(invoiceHeader.getInvoiceDate());
        sForm.setInvoiceType(invoiceHeader.getInvoiceStatusCd());

        sForm.setPoNumber(invoiceHeader.getErpPoNum());

        if (invoiceHeader.getSalesTax() != null){
          sForm.setTaxAmount(String.valueOf(invoiceHeader.getSalesTax()));
        }
        if (invoiceHeader.getFreight() != null ){
          sForm.setFreightCharges(String.valueOf(invoiceHeader.getFreight()));
        }
      }
      if (propLines != null){
        for (Iterator iter = propLines.iterator(); iter.hasNext(); ) {
          OrderPropertyData opD = (OrderPropertyData) iter.next();
          Integer detailIdI = new Integer(opD.getInvoiceDistDetailId());
          if (detailIdI.intValue() == 0) {
            if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.ACCOUNT_NAME)) {
              sForm.setAccountName( opD.getValue());
            }
            else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.ACCOUNT_NUMBER)) {
              sForm.setAccountNumber( opD.getValue());
            }
            else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.DISTRIBUTION_CENTER_NAME)) {
              sForm.setDistributionCenterName( opD.getValue());
            }
            else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.DISTRIBUTION_CENTER_NO)) {
              sForm.setDistributionCenterNo(  opD.getValue());
            }
          }
        }
      }
      // Invoice Result Lunes population
      InvoiceNetworkServiceDataVector lineList = new InvoiceNetworkServiceDataVector();
      if (desc.getInvoiceLines()!= null) {
        InvoiceDistDetailDataVector invoiceLines = desc.getInvoiceLines();


        for (Iterator iter = invoiceLines.iterator(); iter.hasNext(); ) {
          InvoiceNetworkServiceData line = new InvoiceNetworkServiceData();

          InvoiceDistDetailData invD = (InvoiceDistDetailData) iter.next();

          line.setLineNumber(String.valueOf(invD.getDistLineNumber()));
          line.setQuantity(String.valueOf(invD.getDistItemQtyReceived()));
          if (invD.getDistItemUom() != null){
            line.setQuantityUnitOfMeasure(String.valueOf(invD.getDistItemUom()));
          }
          if (invD.getItemReceivedCost() != null ){
            line.setUnitPrice(String.valueOf(invD.getItemReceivedCost()));
          }
          if (invD.getLineTotal() != null ){
            line.setExtendedPrice(String.valueOf(invD.getLineTotal()));
          }
          line.setProductNumber(invD.getInvoiceDistSkuNum());
          line.setProductName(invD.getDistItemShortDesc());
          line.setPack(invD.getDistItemPack());
          if (propLines != null){
           for (Iterator iter1 = propLines.iterator(); iter1.hasNext(); ) {
             OrderPropertyData opD = (OrderPropertyData) iter1.next();
             if (invD.getInvoiceDistDetailId() == opD.getInvoiceDistDetailId() ){
               if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.PACK_SIZE)) {
                 line.setPackSize( opD.getValue());
               } else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.BRAND)) {
                 line.setBrand(  opD.getValue());
               } else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.MANUFACTURER_NAME)) {
                 line.setManufacturerName(  opD.getValue());
               } else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.MANUFACTURER_PRODUCT_NO)) {
                 line.setManufacturerProductNo(  opD.getValue());
               } else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.UPC)) {
                 line.setUpc(  opD.getValue());
               } else if (opD.getShortDesc().equals(RefCodeNames.NETWORK_PROP_SHORT_DESC.DISCOUNTS)) {
                 line.setDiscounts( opD.getValue());
               }
              }
           }
         }
         lineList.add(line);
        }
        sort(lineList, "lineNumber");
        sForm.setResultList(lineList);

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Order orderEjb = factory.getOrderAPI();
        OrderPropertyDataVector notes = getNetworkInvoiceNotesForDisplay(invoiceId, 0, orderEjb);
        sForm.setOrderNotes(notes);

      }
    }
    return updateErrors;
  }

  /**
   * Gets appropriate notes for display for the invoice display screen
   */
  public static OrderPropertyDataVector getInvoiceDistNotesForDisplay(int invoiceId, int orderId, Order orderEjb) throws
    RemoteException, DataNotFoundException {
    //set the order property notes
    OrderPropertyDataVector opdv = orderEjb.getOrderPropertyCollectionByInvoiceDist(invoiceId,
      RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
    if (opdv == null) {
      opdv = new OrderPropertyDataVector();
    }
    if (orderId > 0) {
      //opdv.addAll(orderEjb.getOrderPropertyCollection(desc.getOrderData().getOrderId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE));
      opdv.addAll(orderEjb.getOrderPropertyCollection(orderId, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS));
    }
    sortAndRemoveDupsFromOrderPropertyDataVector(opdv);
    return opdv;
  }

  public static OrderPropertyDataVector getNetworkInvoiceNotesForDisplay(int invoiceId, int orderId, Order orderEjb) throws
      RemoteException, DataNotFoundException {
    OrderPropertyDataVector opdv = orderEjb.getOrderPropertyCollectionByInvoiceDist(invoiceId,
        RefCodeNames.ORDER_PROPERTY_TYPE_CD.NETWORK_INVOICE_NOTE);
    if (opdv == null) {
      opdv = new OrderPropertyDataVector();
    }
    if (orderId > 0) {
      opdv.addAll(orderEjb.getOrderPropertyCollection(orderId, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS));
    }
    sortAndRemoveDupsFromOrderPropertyDataVector(opdv);
    return opdv;
  }

  private static void sortAndRemoveDupsFromOrderPropertyDataVector(OrderPropertyDataVector dv) {
    Collections.sort(dv, ClwComparatorFactory.getOrderPropertyIdComparator());
    Iterator it = dv.iterator();
    HashSet found = new HashSet();
    while (it.hasNext()) {
      OrderPropertyData opd = (OrderPropertyData) it.next();
      Integer id = new Integer(opd.getOrderPropertyId());
      if (found.contains(id)) {
        it.remove();
      } else {
        found.add(id);
      }
    }
  }


   /**
   *Returns the freight handlers for this order
   */
  public static FreightHandlerView getFreightHandler(OrderItemDataVector orderItems) {
    APIAccess factory = null;
    Iterator it = orderItems.iterator();
    while (null != it && it.hasNext()) {
      OrderItemData oid = (OrderItemData) it.next();
      if (oid != null) {
        if (oid.getFreightHandlerId() > 0) {
          int fhid = oid.getFreightHandlerId();
          try {
            if (factory == null) {
              factory = new APIAccess();
            }
            Distributor distBean = factory.getDistributorAPI();
            return distBean.getFreightHandlerById(fhid);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  /**
   *Verifies that this user can see this invoice based off the store id and the user type code.
   */
  private static boolean verifyStoreAuthorization(HttpSession session, ActionForm form) {
    CustAcctMgtInvoiceDetailForm sForm = (CustAcctMgtInvoiceDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = 0;

    if (sForm.getInvoiceView() != null) {
      if (sForm.getInvoiceView().getInvoiceHeader() != null) {
        storeId = sForm.getInvoiceView().getInvoiceHeader().getStoreId();
      }
    }

    if (appUser.getUserStore().getStoreId() == storeId ||
        RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      return true;
    }
    return false;
  }

  /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
   private static ArrayList mkExcelBlankLine() {
       ArrayList r = new ArrayList();
       r.add(" ");
       r.add(" ");
       return r;
   }

   private static ArrayList mkExcelNVP(String name, String val) {
       ArrayList r = new ArrayList();
       r.add(name);
       r.add(val);
       return r;
    }
    private static boolean isShowValue(HttpServletRequest request, String pParamName) {
      String value = request.getParameter(pParamName);
      if (Utility.isSet(value) && !Utility.isTrue(value)) {
        return false;
      }
      return true;
    }

    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  items   Description of the Parameter
     *@return         Description of the Return Value
     */
    private static GenericReportResultView generateExcelItems(HttpServletRequest
        request,  List items,  CustAcctMgtInvoiceDetailForm sForm) {

     GenericReportResultView xlog = GenericReportResultView.createValue();

     try {
         String itemsStr = ClwI18nUtil.getMessage(request,"report.tab.Items",null);
         xlog.setName(itemsStr);

         Locale lLocale = request.getLocale();
         java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance(lLocale);

         ArrayList r = null;

         ArrayList  restable = new ArrayList();

         for (int i = 0; null != items && i < items.size(); i++) {
             r = new ArrayList();
             InvoiceNetworkServiceData pItm = (InvoiceNetworkServiceData)items.get(i);
             r.add(sForm.getInvoiceNumber());
             r.add(sForm.getInvoiceDate());
             r.add(sForm.getInvoiceType());

             r.add(sForm.getDistributionCenterNo());
             r.add(sForm.getDistributionCenterName());
             r.add(sForm.getAccountNumber());
             r.add(sForm.getAccountName());
             r.add(sForm.getPoNumber());
             r.add(sForm.getTaxAmount());
             r.add(sForm.getFreightCharges());

             r.add(pItm.getLineNumber());
             r.add(pItm.getQuantity());
             r.add(pItm.getQuantityUnitOfMeasure());
             r.add(pItm.getUnitPrice());
             r.add(pItm.getExtendedPrice());
             r.add(pItm.getProductNumber());
             r.add(pItm.getProductName());
             r.add(pItm.getPack());
             r.add(pItm.getPackSize());
             r.add(pItm.getManufacturerName());
             r.add(pItm.getManufacturerProductNo());
             r.add(pItm.getUpc());
             r.add(pItm.getDiscounts());
             restable.add(r);
             r = null;
         }

         xlog.setTable(restable);

         GenericReportColumnViewVector itemHeader =  new GenericReportColumnViewVector();

         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.invoiceNumber",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.invoiceDate",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.invoiceType",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.distributionCenterNo",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.distributionCenterName",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.accountNumber",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.accountName",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.poNumber",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.taxAmount",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.freightCharges",null),0,255,"VARCHAR2"));

         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.lineNumber",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.quantity",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.quantityUOM",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.unitPrice",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.extendedPrice",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.productNumber",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.productName",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.pack",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.packSize",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.manufName",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.manufNum",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.upc",null),0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"invoice.text.discounts",null),0,255,"VARCHAR2"));

/*
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Number",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Date",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Type",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distribution Center No",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distribution Center Name",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Number",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Number",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Tax Amount",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Freight Charges",0,255,"VARCHAR2"));


         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Line #",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Quantity",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Quantity Unit Of Measure",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Unit Price",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Extended Price",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor Num",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Product Name",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack Size",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manuf Name",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manuf Num",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UPC",0,255,"VARCHAR2"));
         itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Discounts",0,255,"VARCHAR2"));
 */

         xlog.setHeader(itemHeader);
         xlog.setColumnCount(itemHeader.size());
     } catch (Exception e) {
         e.printStackTrace();
     }
     return xlog;
 }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  private static GenericReportColumnView makeExcelCell(String s) {
      return ReportingUtils.createGenericReportColumnView
              ("java.lang.String", s, 0, 255, "VARCHAR2");
  }

  public static ActionErrors printDetail(HttpServletResponse response,
          HttpServletRequest request,
          ActionForm form,
          String userReq
          )
          throws Exception {
    return printDetail(response,request,form,userReq,null);
  }

  /**
   *  <code>print</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@param  response       Description of the Parameter
   *@param  userReq        Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  if an error occurs
   */

  public static ActionErrors printDetail(HttpServletResponse response,
          HttpServletRequest request,
          ActionForm form,
          String userReq,
          MessageResources mr
          )
          throws Exception {

      HttpSession session = request.getSession();
      CustAcctMgtInvoiceDetailForm sForm = (CustAcctMgtInvoiceDetailForm) form;
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

      if (null == factory) {
          throw new Exception("Without APIAccess.");
      }

      //List items = sForm.getCartItems();
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      if (userReq.startsWith("excel")) {
        printDetailToExcel(response,request, sForm,  userReq, out,  mr);

      } else {
        printDetailToPdf(response, request, sForm,   out,  mr);
      }

      response.setContentLength(out.size());
      out.writeTo(response.getOutputStream());
      response.flushBuffer();
      response.getOutputStream().close();
      return new ActionErrors();
    }

    public static void printDetailToExcel(HttpServletResponse response,
            HttpServletRequest request,
            CustAcctMgtInvoiceDetailForm sForm,
            String userReq,
            ByteArrayOutputStream out,
            MessageResources mr
            )
            throws Exception {

      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser)session.getAttribute("ApplicationUser");
      List items = sForm.getResultList();

      response.setContentType("application/x-excel");
      String browser = (String)  request.getHeader("User-Agent");
      boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
      if (!isMSIE6){
    	  response.setHeader("extension", "xls");  	  
      }
      if(isMSIE6){
  		response.setHeader("Pragma", "public");
  		response.setHeader("Expires", "0");
  		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
  		response.setHeader("Cache-Control", "public"); 
  	
      }
      response.setHeader("Content-disposition", "attachment; filename=" +userReq + "InvoiceDetail.xls");
      SiteData sd = appUser.getSite();
      GenericReportResultViewVector excel_og =  new GenericReportResultViewVector();
      excel_og.add(generateExcelItems(request,  items, sForm));
//      excel_og.add(generateExcelHeader(request, appUser, sForm));

      try {
        if(mr!=null) {
          SessionTool st = new SessionTool(request);
          String dateFmt = ClwI18nUtil.getCountryDateFormat(request);
          ReportRequest rr = new ReportRequest(st.getUserData(),mr,dateFmt);
          ReportWritter.writeExcelReportMulti(excel_og, out, rr);
        } else {
          ReportWritter.writeExcelReportMulti(excel_og, out);
        }
      } catch (Exception e) {
          e.printStackTrace();
      }


  }
  public static void printDetailToPdf(HttpServletResponse response,
           HttpServletRequest request,
           CustAcctMgtInvoiceDetailForm sForm,
           ByteArrayOutputStream out,
           MessageResources mr
          ) throws Exception {
     HttpSession session = request.getSession();
     String imgPath = ClwCustomizer.getCustomizeImgElement(request,"pages.logo1.image");

     String catalogLocaleCd = (String) session.getAttribute(Constants.CATALOG_LOCALE);
     if(catalogLocaleCd==null) catalogLocaleCd = "en_US";

     CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute("ApplicationUser");
     StoreData storeD = appUser.getUserStore();


     List items = sForm.getResultList();

     //sets the content type so the browser knows this is a pdf
     response.setContentType("application/pdf");
     String browser = (String)  request.getHeader("User-Agent");
     boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
     if (!isMSIE6){
   	  	response.setHeader("extension", "pdf");
   	  	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
     }
     int decimals = -1;
     Integer catalogDecimalsI = (Integer) session.getAttribute(Constants.CATALOG_DECIMALS);
     if (catalogDecimalsI!=null) decimals = catalogDecimalsI.intValue();

     ClwI18nUtil formatter = ClwI18nUtil.getInstance(request,catalogLocaleCd, decimals);
     PdfInvoiceDetail pdf = null;
     Properties configProps = new Properties();
     String fileName = ClwCustomizer.getAbsFilePath(request,"config.txt");
     if(Utility.isSet(fileName)) {
         FileInputStream configIS = new FileInputStream(fileName);
         configProps.load(configIS);
         String pdfClass = configProps.getProperty("PdfInvoiceDetail");
         if(Utility.isSet(pdfClass)) {
             try {
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pdfClass);
                pdf = (PdfInvoiceDetail) clazz.newInstance();
             } catch (Exception exc) {
               log.info("!!!!ATTENTION failed creating PdfOrderGuide using class name: "+pdfClass);
             }
         }
     }
    if(pdf==null) pdf = new PdfInvoiceDetail();
    pdf.init(request, catalogLocaleCd);
//    pdf.constructPdf(invoiceToRender, pdfout);
    pdf.generatePdf(sForm, items,  storeD, out, imgPath);

   }

  /**
   *Creates a printable version of the distributor invoice
   */
  public static ActionMessages printInvoiceDist(HttpServletResponse response, HttpServletRequest request, ActionForm form) throws
    Exception {
    ActionMessages updateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    CustAcctMgtInvoiceDetailForm sForm = (CustAcctMgtInvoiceDetailForm) form;

    if (!verifyStoreAuthorization(session, form)) {
      return updateErrors;
    }

    DistributorData dist;
    /** @todo Print
    if (sForm.getInvoice().getInvoiceDist().getBusEntityId() > 0) {
      dist = SessionTool.getDistributorData(request, sForm.getInvoice().getInvoiceDist().getBusEntityId());
    } else {
      dist = null;
    }
    */
    //sets the content type so the browser knows this is a pdf
    response.setContentType("application/pdf");
    response.setHeader("extension", "pdf");
    response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");


    ByteArrayOutputStream pdfout = new ByteArrayOutputStream();

    PdfInvoiceDist pdf = new PdfInvoiceDist();
    /** @todo  */
    //pdf.constructPdf(sForm.getInvoice(), sForm.getInvoiceItems(), sForm.getNotes(), dist, pdfout);

    response.setContentLength(pdfout.size());
    pdfout.writeTo(response.getOutputStream());
    response.flushBuffer();
    response.getOutputStream().close();
    return updateErrors;
  }


  /**
   *Creates a printable version of the distributor invoice
   */
  public static ActionMessages printInvoiceDistList(HttpServletResponse response, HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionMessages updateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    CustAcctMgtInvoiceSearchForm sForm = (CustAcctMgtInvoiceSearchForm) form;

    if (sForm.getResultList() == null || sForm.getResultList().isEmpty()) {
      updateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nothing.to.print"));
      return updateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    Distributor distEjb = factory.getDistributorAPI();
    Order orderEjb = factory.getOrderAPI();

    PdfInvoiceDist pdfInv = new PdfInvoiceDist();
    ArrayList invoiceToRender = new ArrayList();
    HashMap distMap = new HashMap();
    Iterator it = sForm.getResultList().iterator();
    while (it.hasNext()) {
      PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) it.next();
      OrderItemDescDataVector itms = purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(po.getInvoiceDist().
        getInvoiceDistId(), 0);
      int orderId = 0;
      if (po.getOrderData() != null) {
        orderId = po.getOrderData().getOrderId();
      }
      Integer distKey = new Integer(po.getInvoiceDist().getBusEntityId());
      DistributorData dist;
      if (po.getInvoiceDist().getBusEntityId() == 0) {
        dist = null;
      } else {
        if (distMap.containsKey(distKey)) {
          dist = (DistributorData) distMap.get(distKey);
        } else {
          dist = distEjb.getDistributor(po.getInvoiceDist().getBusEntityId());
          distMap.put(distKey, dist);
        }
      }
      OrderPropertyDataVector notes = CustAcctMgtInvoiceLogic.getInvoiceDistNotesForDisplay(po.getInvoiceDist().
        getInvoiceDistId(), orderId, orderEjb);
      invoiceToRender.add(pdfInv.createPdfInvoiceDistRequest(po, itms, notes, dist));
    }

    ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
    pdfInv.constructPdf(invoiceToRender, pdfout);
    //sets the content type so the browser knows this is a pdf
    response.setContentType("application/pdf");
	response.setHeader("extension", "pdf");
    response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");

    response.setContentLength(pdfout.size());
    pdfout.writeTo(response.getOutputStream());
    response.flushBuffer();
    response.getOutputStream().close();
    return updateErrors;
  }


  private static CustAcctMgtInvoiceSearchForm getCustAcctMgtInvoiceSearchForm(HttpSession session) {
    return (CustAcctMgtInvoiceSearchForm) session.getAttribute("CUST_ACCT_MGT_INVOICE_SEARCH_FORM");
  }

  /**
   * Fetches the next invoice from the list of invoices that the user had previously searched on
   */
  public static ActionMessages getNextInvoice(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages updateErrors = new ActionMessages();
    CustAcctMgtInvoiceDetailForm dForm = (CustAcctMgtInvoiceDetailForm) form;
    if (dForm.getNextInvoiceInList() == 0) {
      updateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invoice.no.next"));
      return updateErrors;
    }

    updateErrors = CustAcctMgtInvoiceLogic.fetchInvoiceDistDetail(request, form, dForm.getNextInvoiceInList());
    if (!updateErrors.isEmpty()) {
      return updateErrors;
    }
    return updateErrors;
  }

  /**
   * Fetches the previous invoice from the list of invoices that the user had previously searched on
   */
  public static ActionMessages getPrevInvoice(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages updateErrors = new ActionMessages();
    CustAcctMgtInvoiceDetailForm dForm = (CustAcctMgtInvoiceDetailForm) form;
    if (dForm.getPrevInvoiceInList() == 0) {
      updateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invoice.no.prev"));
      return updateErrors;
    }
    updateErrors = CustAcctMgtInvoiceLogic.fetchInvoiceDistDetail(request, form, dForm.getPrevInvoiceInList());
    if (!updateErrors.isEmpty()) {
      return updateErrors;
    }
    return updateErrors;
  }


    /**
     * Sort for resultList
     */

     public static ActionErrors sortItems (HttpServletRequest request, ActionForm pForm) throws Exception
     {
       HttpSession session = request.getSession();
       ActionErrors ae = new ActionErrors();
       String sortField = ((CustAcctMgtInvoiceDetailForm)pForm).getSortField();
       InvoiceNetworkServiceDataVector resultList = ((CustAcctMgtInvoiceDetailForm)pForm).getResultList();

       sort(resultList, sortField);
       return ae;
     }


     public static void sort(InvoiceNetworkServiceDataVector resultList, String pSortField) throws Exception {
        sort( resultList, pSortField, true);
     }

     public static void sort( InvoiceNetworkServiceDataVector resultList, String pSortField, boolean pAscFl) throws Exception {
         boolean _ascFl = true;
         if (pSortField == null) {
             return;
         }

         if (pSortField.equals("lineNumber")) {
             Collections.sort(resultList, LINE_NUMBER_COMARATOR);
         } else if (pSortField.equals("quantity")) {
             Collections.sort(resultList, QUANTITY_COMARATOR);
         } else if (pSortField.equals("quantityUnitOfMeasure")) {
             Collections.sort(resultList, QUANTITY_UOM_COMARATOR);
         } else if (pSortField.equals("productNumber")) {
             Collections.sort(resultList, PRODUCT_NUMBER_COMARATOR);
         } else if (pSortField.equals("productName")) {
               Collections.sort(resultList, PRODUCT_NAME_COMARATOR);
         } else if (pSortField.equals("manufacturerName")) {
             Collections.sort(resultList, MANUFACTURER_NAME_COMARATOR);
         } else if (pSortField.equals("manufacturerProductNo")) {
             Collections.sort(resultList, MANUFACTURER_PRODUCT_NO_COMARATOR);
         } else if (pSortField.equals("upc")) {
             Collections.sort(resultList, UPC_COMARATOR);

         }
    }

    static final Comparator  LINE_NUMBER_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        Integer name1 = new Integer(((InvoiceNetworkServiceData)o1).getLineNumber());
        Integer name2 = new Integer(((InvoiceNetworkServiceData)o2).getLineNumber());
        return name1.compareTo(name2);
    } };
    static final Comparator  QUANTITY_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        Integer name1 = new Integer(((InvoiceNetworkServiceData)o1).getQuantity());
        Integer name2 = new Integer(((InvoiceNetworkServiceData)o2).getQuantity());
        return name1.compareTo(name2);
    } };
    static final Comparator  QUANTITY_UOM_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        String name1 = ((InvoiceNetworkServiceData)o1).getQuantityUnitOfMeasure();
        String name2 = ((InvoiceNetworkServiceData)o2).getQuantityUnitOfMeasure();
        return name1.compareTo(name2);
    } };
    static final Comparator  PRODUCT_NUMBER_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        String name1 = ((InvoiceNetworkServiceData)o1).getProductNumber();
        String name2 = ((InvoiceNetworkServiceData)o2).getProductNumber();
        return name1.compareTo(name2);
    } };
    static final Comparator  PRODUCT_NAME_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        String name1 = ((InvoiceNetworkServiceData)o1).getProductName();
        String name2 = ((InvoiceNetworkServiceData)o2).getProductName();
        return name1.compareTo(name2);
    } };
    static final Comparator  MANUFACTURER_NAME_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        String name1 = ((InvoiceNetworkServiceData)o1).getManufacturerName();
        String name2 =((InvoiceNetworkServiceData)o2).getManufacturerName();
        return name1.compareTo(name2);
    } };
    static final Comparator  MANUFACTURER_PRODUCT_NO_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        String name1 = ((InvoiceNetworkServiceData)o1).getManufacturerProductNo();
        String name2 = ((InvoiceNetworkServiceData)o2).getManufacturerProductNo();
        return name1.compareTo(name2);
    } };
    static final Comparator  UPC_COMARATOR = new Comparator() {
    public  int compare(Object o1, Object o2)
    {
        String name1 = ((InvoiceNetworkServiceData)o1).getUpc();
        String name2 = ((InvoiceNetworkServiceData)o2).getUpc();
        return name1.compareTo(name2);
    } };


    public static ActionMessages updateInvoice(HttpServletRequest request, ActionForm form) throws Exception {
        ActionMessages updateErrors = new ActionMessages();
        HttpSession session = request.getSession();
        CustAcctMgtInvoiceDetailForm sForm = (CustAcctMgtInvoiceDetailForm) form;
        InvoiceDistView invoice = sForm.getInvoiceView();
        InvoiceDistData header = invoice.getInvoiceHeader();
        // update invoice status
        header.setInvoiceStatusCd(sForm.getInvoiceType());

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
          throw new Exception("Without APIAccess.");
        }
        Order orderEjb = factory.getOrderAPI();

        header = orderEjb.updateInvoiceDist(header);
        invoice.setInvoiceHeader(header);
        sForm.setInvoiceView(invoice);
        return updateErrors;
    }
}
