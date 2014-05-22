/*
 * StoreVendorInvoiceLogic.java
 *
 * Created on July 1, 2005, 2:24 PM
 */

package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.avalara.avatax.services.tax.ArrayOfMessage;
import com.avalara.avatax.services.tax.GetTaxResult;
import com.avalara.avatax.services.tax.Message;
import com.avalara.avatax.services.tax.SeverityLevel;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DistributorInvoiceFreightTool;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntityTerrViewVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;
import com.cleanwise.service.api.value.PurchaseOrderStatusCriteriaData;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TaxQueryResponse;
import com.cleanwise.view.forms.StoreVendorInvoiceDetailForm;
import com.cleanwise.view.forms.StoreVendorInvoiceSearchForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.custom.GCAViewLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.pdf.PdfInvoiceDist;

/**
 *Encapsulates the logic regarding a Vendor Invoice in the system for the Store Admin portal.
 * @author bstevens
 */
public class StoreVendorInvoiceLogic {

        private static final Logger log = Logger.getLogger(StoreVendorInvoiceLogic.class);

  public static final BigDecimal ZERO = new BigDecimal(0);
  private static SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");

  public static void init(HttpServletRequest request, ActionForm form) throws Exception {
    initConstantList(request);
  }

  private static void initConstantList(HttpServletRequest request) throws Exception {
    PurchaseOrderOpLogic.initConstantList(request);
  }

  /**
   *Uses the defined "error" status codes to search the invoice system
   */
  public static ActionMessages searchInvoiceDistException(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceSearchForm sForm = (StoreVendorInvoiceSearchForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();
    populateInvoiceDistSearchCriteria(sForm, lUpdateErrors, searchCriteria, request);
    List exL = searchCriteria.getInvoiceDistExcludeStatusList();
    exL.add(RefCodeNames.INVOICE_STATUS_CD.CANCELLED);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.PENDING);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP);
    exL.add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    Group groupEjb = factory.getGroupAPI();
    
    //restrict store_admin user to invoices of accts configured 
    String isRestrictAcctInvoices = "false";
    IdVector accountIdVector = new IdVector();
    
    if(appUser.getUser().getUserTypeCd().equalsIgnoreCase(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)){
		//check if restrictAcctInvoices flag is set
		Properties props = appUser.getUserProperties();
		isRestrictAcctInvoices = props.getProperty(RefCodeNames.PROPERTY_TYPE_CD.RESTRICT_ACCT_INVOICES);
		
		if(isRestrictAcctInvoices != null && isRestrictAcctInvoices.equalsIgnoreCase("true")){
			//get all accounts configured to user grp 
			
			int userId = appUser.getUserId();
			IdVector locAccts = searchCriteria.getAccountIdVector();
			
			GroupDataVector gdv = groupEjb.getUserGroupsByType(userId,RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
			Iterator it = gdv.iterator();
			while(it.hasNext()){
				int grpId = ((GroupData)it.next()).getGroupId();
				
				BusEntityDataVector beDV = groupEjb.getBusEntitysForGroup(grpId, 
						RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
				for(int i=0; i<beDV.size(); i++){
					BusEntityData bed = (BusEntityData)beDV.get(i);
					Integer acctId = new Integer(bed.getBusEntityId());
					
					if(locAccts!=null && locAccts.size()>0){
						if(locAccts.contains(acctId)){
							accountIdVector.add(acctId);
						}
					}else{
						accountIdVector.add(acctId);
					}
				}
			}	

			if(accountIdVector == null || accountIdVector.size()<=0){
				accountIdVector.add(new Integer(0));
			}
			searchCriteria.setAccountIdVector(accountIdVector);
		}	
	}
    
    //Get all invoices that do not have an account
    PurchaseOrderStatusCriteriaData searchCriteria2 = null;
    
    if(isRestrictAcctInvoices !=null && isRestrictAcctInvoices.equalsIgnoreCase("true")){   	
    	
    	searchCriteria2 = PurchaseOrderStatusCriteriaData.createValue();
    	boolean criteriaSet2 = populateInvoiceDistSearchCriteria(sForm, lUpdateErrors, searchCriteria2, request);
    	searchCriteria2.setOrderId("null");
    	searchCriteria2.setStoreIdVector(appUser.getUserStoreAsIdVector());
    	searchCriteria2.setInvoiceDistExcludeStatusList(exL);
    }
    
    PurchaseOrderStatusDescDataViewVector poStatusDescData =
      purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria);
    
    if(searchCriteria2!=null){
    	PurchaseOrderStatusDescDataViewVector poStatusDescDataNoAcct= 
    		purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria2);
		
    	Iterator it = poStatusDescDataNoAcct.iterator();
    	while(it.hasNext()){
    		poStatusDescData.add((PurchaseOrderStatusDescDataView)it.next());
    	}
    }
    
    if(poStatusDescData.size() > PurchaseOrderStatusCriteriaData.MAX_SEARCH_RESULTS){
    	PurchaseOrderStatusDescDataViewVector poStatList2 = new PurchaseOrderStatusDescDataViewVector();
    	for(int i=0; i<PurchaseOrderStatusCriteriaData.MAX_SEARCH_RESULTS; i++){
    		poStatList2.add((PurchaseOrderStatusDescDataView)poStatusDescData.get(i));
    	}
    	sForm.setResultList(poStatList2);   	
    }else{
    	sForm.setResultList(poStatusDescData);
    }
    sForm.setResultCount(poStatusDescData.size());
    
    return lUpdateErrors;
  }


  /**
   *Performs  search against the po subsystem for invoices
   */
  public static ActionMessages searchInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceSearchForm sForm = (StoreVendorInvoiceSearchForm) form;
    PurchaseOrderStatusCriteriaData searchCriteria = PurchaseOrderStatusCriteriaData.createValue();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    boolean criteriaSet = populateInvoiceDistSearchCriteria(sForm, lUpdateErrors, searchCriteria, request);
    //if something was specified search for it.
    if (!criteriaSet) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.invalidSearchCritera"));
    }

    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    Group groupEjb = factory.getGroupAPI();
    PurchaseOrderStatusDescDataViewVector poStatusDescData= new PurchaseOrderStatusDescDataViewVector();
    PurchaseOrderStatusDescDataViewVector poStatusDescDataNoAcct= new PurchaseOrderStatusDescDataViewVector();
    
    /*** new piece of code ***/
    if (sForm.getInvoiceStatus() != null && !sForm.getInvoiceStatus().equals("")
    		&& !sForm.getInvoiceStatus().equals("-Select-")) {
    	searchCriteria.setInvoiceStatus(sForm.getInvoiceStatus());
    }
    
    //restrict store_admin user to invoices of accts configured
    
    String isRestrictAcctInvoices = "false";
    IdVector accountIdVector = new IdVector();
    
    if(appUser.getUser().getUserTypeCd().equalsIgnoreCase(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)){
		//check if restrictAcctInvoices flag is set
		Properties props = appUser.getUserProperties();
		isRestrictAcctInvoices = props.getProperty(RefCodeNames.PROPERTY_TYPE_CD.RESTRICT_ACCT_INVOICES);
		
		if(isRestrictAcctInvoices!=null && isRestrictAcctInvoices.equalsIgnoreCase("true")){
			//get all accounts configured to user grp 
			
			int userId = appUser.getUserId();
			IdVector locAccts = searchCriteria.getAccountIdVector();
			
			GroupDataVector gdv = groupEjb.getUserGroupsByType(userId,RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
			Iterator it = gdv.iterator();
			while(it.hasNext()){
				int grpId = ((GroupData)it.next()).getGroupId();
				
				BusEntityDataVector beDV = groupEjb.getBusEntitysForGroup(grpId, 
						RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
				for(int i=0; i<beDV.size(); i++){
					BusEntityData bed = (BusEntityData)beDV.get(i);
					Integer acctId = new Integer(bed.getBusEntityId());
					
					if(locAccts!=null && locAccts.size()>0){
						if(locAccts.contains(acctId)){
							accountIdVector.add(acctId);
						}
					}else{
						accountIdVector.add(acctId);
					}
				}
			}	

			if(accountIdVector == null || accountIdVector.size()<=0){
				accountIdVector.add(new Integer(0));
			}
			searchCriteria.setAccountIdVector(accountIdVector);
		}
	
	}

    //Get all invoices that do not have an account
    PurchaseOrderStatusCriteriaData searchCriteria2 = null;
    
    if(isRestrictAcctInvoices!=null && isRestrictAcctInvoices.equalsIgnoreCase("true")){   	
    	
    	searchCriteria2 = PurchaseOrderStatusCriteriaData.createValue();
    	boolean criteriaSet2 = populateInvoiceDistSearchCriteria(sForm, lUpdateErrors, searchCriteria2, request);
    	searchCriteria2.setOrderId("null");
    	searchCriteria2.setStoreIdVector(appUser.getUserStoreAsIdVector());
    	if (sForm.getInvoiceStatus() != null && !sForm.getInvoiceStatus().equals("")
    			&& !sForm.getInvoiceStatus().equals("-Select-")) {
    		searchCriteria2.setInvoiceStatus(sForm.getInvoiceStatus());
    	}
    }
    
    /*************************/
    String action = request.getParameter("action");
    if(action.equals(Constants.SEARCH_POS)){
    	poStatusDescData =
    		purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria,false);
    	
    	if(searchCriteria2!=null){
    		poStatusDescDataNoAcct = 
    			purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria2);
    		
    		Iterator it = poStatusDescDataNoAcct.iterator();
    		while(it.hasNext()){
    			poStatusDescData.add((PurchaseOrderStatusDescDataView)it.next());
    		}
    	}
    	
    }else{   
    	poStatusDescData =
    		purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria);  
    	 
    	if(searchCriteria2!=null){
    		poStatusDescDataNoAcct = 
    			purchaseOrderEjb.getPurchaseOrderStatusDescCollection(searchCriteria2);
    		
    		Iterator it = poStatusDescDataNoAcct.iterator();
    		while(it.hasNext()){
    			poStatusDescData.add((PurchaseOrderStatusDescDataView)it.next());
    		}
    	}
    }

    if(poStatusDescData.size() > PurchaseOrderStatusCriteriaData.MAX_SEARCH_RESULTS){
    	PurchaseOrderStatusDescDataViewVector poStatList2 = new PurchaseOrderStatusDescDataViewVector();
    	for(int i=0; i<PurchaseOrderStatusCriteriaData.MAX_SEARCH_RESULTS; i++){
    		poStatList2.add((PurchaseOrderStatusDescDataView)poStatusDescData.get(i));
    	}
    	sForm.setResultList(poStatList2);   	
    }else{
    	sForm.setResultList(poStatusDescData);
    }
    sForm.setResultCount(poStatusDescData.size());

    return lUpdateErrors;
  }


  /**
   *Populates the search criteria
   */
  private static boolean populateInvoiceDistSearchCriteria(StoreVendorInvoiceSearchForm sForm, ActionMessages lUpdateErrors,
    PurchaseOrderStatusCriteriaData searchCriteria, HttpServletRequest request) {

	boolean critSet = PurchaseOrderOpLogic.populateSearchCriteria(sForm, lUpdateErrors, searchCriteria, null, request, null);
    //XXX Not sure if we should restrict the status codes.  For now do not.
    if (sForm.getAccountFilter() != null && !sForm.getAccountFilter().isEmpty()) {
      critSet = true;
      searchCriteria.setAccountIdVector(Utility.toIdVector(sForm.getAccountFilter()));
    }
    if (sForm.getDistributorFilter() != null && !sForm.getDistributorFilter().isEmpty()) {
      critSet = true;
      searchCriteria.setDistributorIds(Utility.toIdVector(sForm.getDistributorFilter()));
    }
    searchCriteria.setInvoiceSearch(true);
    return critSet;
  }

  /**
   *initializes the form from the quick po lookup displayed on the screen
   */
  public static ActionMessages initNewInvoiceDistFromFormPo(HttpServletRequest request, ActionForm form) throws Exception {

      ActionMessages lUpdateErrors = new ActionMessages();

      PurchaseOrderData po = getPurchaseOrderDataFromLookup(lUpdateErrors, request, form);
      if (lUpdateErrors != null && lUpdateErrors.size() > 0) {
          return lUpdateErrors;
      }

      return initNewDataForLookUpPO(po.getPurchaseOrderId(), request, form);
  }


  /**
   *initializes the form from the quick po lookup displayed on the screen
   */
  private static PurchaseOrderData getPurchaseOrderDataFromLookup(ActionMessages lUpdateErrors, HttpServletRequest request,
    ActionForm form) throws Exception {
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    PurchaseOrderStatusCriteriaData crit = new PurchaseOrderStatusCriteriaData();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (!Utility.isSet(sForm.getNewErpPoNum())) {
      lUpdateErrors.add("newErpPoNum", new ActionError("variable.empty.error", "Lookup Po Number"));
      return null;
    }
    crit.setStoreIdVector(appUser.getUserStoreAsIdVector());
    crit.setErpPONum(sForm.getNewErpPoNum());
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PurchaseOrderDataVector pos = factory.getPurchaseOrderAPI().getPurchaseOrderCollection(crit);
    if (pos.size() == 0) {
      lUpdateErrors.add("newErpPoNum", new ActionError("invoice.couldNotFindPo", Utility.encodeForHTML(sForm.getNewErpPoNum())));
      return null;
    } else if (pos.size() > 1) {
      lUpdateErrors.add("newErpPoNum", new ActionError("invoice.invoice.foundMultiplePos", sForm.getNewErpPoNum()));
      return null;
    } else {
      PurchaseOrderData po = (PurchaseOrderData) pos.get(0);
      return po;
    }
  }

  /**
   *initializes the form from the search of po screen
   */
  public static ActionMessages initNewInvoiceDistFromId(HttpServletRequest request, ActionForm form) throws Exception {
    int purchaseOrderId = 0;
    try {
      purchaseOrderId = Integer.parseInt(request.getParameter("id"));
    } catch (Exception e) {}
    if (purchaseOrderId == 0) {
      ActionMessages lUpdateErrors = new ActionMessages();
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
      return lUpdateErrors;
    }
    return initNewInvoiceDist(purchaseOrderId, request, form);
  }

    /**
     *Initializes data for new PO
     */
    private static ActionMessages initNewDataForLookUpPO(int purchaseOrderId, HttpServletRequest request, ActionForm form) throws
            Exception {

        ActionMessages lUpdateErrors = new ActionMessages();
        HttpSession session = request.getSession();
        StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            throw new Exception("Without APIAccess.");
        }

        PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
        PurchaseOrderStatusDescDataView desc;
        PropertyService propertyEjb = factory.getPropertyServiceAPI();

        desc = sForm.getInvoice();
        desc.setOrderData(purchaseOrderEjb.getPurchaseOrderStatusDesc(purchaseOrderId).getOrderData());
        desc.setPurchaseOrderData(purchaseOrderEjb.getPurchaseOrderStatusDesc(purchaseOrderId).getPurchaseOrderData());

        sForm.setInvoiceItems(purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(0, purchaseOrderId));

        setInvoiceDistValuesToUI(sForm, session, request);

        //setup the minimum order amt for the distrbutor
        if (desc.getDistributorBusEntityData() != null) {

            sForm.setInvoice(desc);

            try {
                  String minAmt = propertyEjb.getBusEntityProperty(desc.getDistributorBusEntityData().getBusEntityId(),
                          RefCodeNames.PROPERTY_TYPE_CD.MINIMUM_ORDER_AMOUNT);
                  if (Utility.isSet(minAmt)) {
                      sForm.setVendorMinimumOrder(toBigDecimal(minAmt));
                  }
              } catch (Exception e) {

              }
          }

        //set the order property notes
        sForm.setNotes(new OrderPropertyDataVector());

        clearFormVariables(sForm);

        return lUpdateErrors;
    }

  /**
   *Initializes a form for entering in a new invoice
   */
  private static ActionMessages initNewInvoiceDist(int purchaseOrderId, HttpServletRequest request, ActionForm form) throws
          Exception {

      ActionMessages lUpdateErrors = new ActionMessages();
      HttpSession session = request.getSession();
      StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;

      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (factory == null) {
          throw new Exception("Without APIAccess.");
      }

      PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
      PropertyService propertyEjb = factory.getPropertyServiceAPI();

      PurchaseOrderStatusDescDataView desc;

      desc = getInvoiceFromListAndInitNextAndPrev(sForm, session, 0, purchaseOrderId);
      desc = purchaseOrderEjb.getPurchaseOrderStatusDesc(purchaseOrderId);

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

      //make sure we are properly authorized to view this invoice.
      if (appUser.getUserStore().getStoreId() == desc.getOrderData().getStoreId()) {
          sForm.setInvoice(desc);
          //get the items
          sForm.setInvoiceItems(purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(0, purchaseOrderId));
          setInvoiceDistValuesToUI(sForm, session, request);

          //setup the minimum order amt for the distrbutor
          if (desc.getDistributorBusEntityData() != null) {
              try {
                  String minAmt = propertyEjb.getBusEntityProperty(desc.getDistributorBusEntityData().getBusEntityId(),
                          RefCodeNames.PROPERTY_TYPE_CD.MINIMUM_ORDER_AMOUNT);
                  if (Utility.isSet(minAmt)) {
                      sForm.setVendorMinimumOrder(toBigDecimal(minAmt));
                  }
              } catch (Exception e) {

              }
          }

          //set the order property notes
          sForm.setNotes(new OrderPropertyDataVector());
      }

      clearFormVariables(sForm);
      return lUpdateErrors;
  }


  /**
   * Syncs up the search list with the supplied invoice using invoice ids.
   */
  private static void replaceInvoiceInList(HttpSession session, PurchaseOrderStatusDescDataView invoice) {
    StoreVendorInvoiceSearchForm searchForm = getStoreVendorInvoiceSearchForm(session);
    if (searchForm == null) {
      return;
    }
    List invoiceList = searchForm.getResultList();
    Iterator it = invoiceList.iterator();
    int ct = 0;
    while (it.hasNext()) {
      PurchaseOrderStatusDescDataView desc = (PurchaseOrderStatusDescDataView) it.next();
      if(desc != null && desc.getInvoiceDist() != null){
          if (desc.getInvoiceDist().getInvoiceDistId() == invoice.getInvoiceDist().getInvoiceDistId()) {
            searchForm.getResultList().set(ct, invoice);
            return;
          }
      }
      ct++;
    }
    //not found add it at the end
    searchForm.getResultList().add(invoice);
  }

  /**
   * Expects either a invoiceId or a poId.
   * sets the nextInvoiceInList and prevInvoiceInList properties of the detail form,
   * and will return the PurchaseOrderStatusDescDataView from the list in the search form.
   */
  private static PurchaseOrderStatusDescDataView getInvoiceFromListAndInitNextAndPrev(StoreVendorInvoiceDetailForm
          detailForm, HttpSession session, int invoiceId, int poId) {

      StoreVendorInvoiceSearchForm searchForm = getStoreVendorInvoiceSearchForm(session);
      if (searchForm == null || invoiceId == 0) {
          detailForm.setNextInvoiceInList(0);
          detailForm.setPrevInvoiceInList(0);
          return null;
      }

      List invoiceList = searchForm.getResultList();
      log.info("***********SVC: invoiceList = " + invoiceList);
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


  /**
   *Fetches the detail for an invoice based off the passed in optInvoiceDistId.  If that is not supplied the request parameter "id" is used.
   */
  public static ActionMessages fetchInvoiceDistDetail(HttpServletRequest request, ActionForm form, int optInvoiceDistId) throws
    Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    int invoiceId = optInvoiceDistId;
    if (invoiceId == 0) {
      try {
        invoiceId = Integer.parseInt(request.getParameter("id"));
      } catch (Exception e) {}
    }
    if (invoiceId == 0) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
      return lUpdateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    PropertyService propertyEjb = factory.getPropertyServiceAPI();
    Order orderEjb = factory.getOrderAPI();

    //init previous next ids and the invoice if it was in the search list
    PurchaseOrderStatusDescDataView desc = getInvoiceFromListAndInitNextAndPrev(sForm, session, invoiceId, 0);

    //if we didn't find it in the list fetch it
    if (desc == null) {
      desc = purchaseOrderEjb.getDistributorInvoiceDesc(invoiceId);
    }

    String recInvSysCd = null;
    if (desc != null && desc.getDistributorBusEntityData() != null &&
        desc.getDistributorBusEntityData().getBusEntityId() > 0) {
      try {
        recInvSysCd = propertyEjb.getBusEntityProperty(desc.getDistributorBusEntityData().getBusEntityId(),
          RefCodeNames.PROPERTY_TYPE_CD.RECEIVING_SYSTEM_INVOICE_CD);
      } catch (Exception e) {
        log.info("Caught exception getting receiving system cd for distributor");
        recInvSysCd = null;
      }
    }
    sForm.setReceivingSystemInvoiceCd(recInvSysCd);

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    //make sure we are properly authorized to view this invoice.
    if (desc.getInvoiceDist() != null && appUser.getUserStore().getStoreId() == desc.getInvoiceDist().getStoreId()) {
      sForm.setInvoice(desc);
      //get the items, if the poid is avaliable supply it, otherwise just the invoice dist detail items will be populated
      //in the underling OrderItemDescView object
      if (desc.getPurchaseOrderData() != null) {
        sForm.setInvoiceItems(purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(invoiceId,
          desc.getPurchaseOrderData().getPurchaseOrderId()));
      } else {
        sForm.setInvoiceItems(purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(invoiceId, 0));
      }
      refreshObjectsInvoiceDist(request, sForm);

      if (desc.getDistributorBusEntityData() != null) {

        //setup the minimum order amt for the distrbutor
        try {
          String minAmt = propertyEjb.getBusEntityProperty(desc.getDistributorBusEntityData().getBusEntityId(),
            RefCodeNames.PROPERTY_TYPE_CD.MINIMUM_ORDER_AMOUNT);
          if (Utility.isSet(minAmt)) {
            sForm.setVendorMinimumOrder(toBigDecimal(minAmt));
          }
        } catch (Exception e) {}

          //setup the rights of invoice editing for the distributor
          try {
              String doNotAllowInvoiceEdits = propertyEjb.getBusEntityProperty(desc.getDistributorBusEntityData().getBusEntityId(), RefCodeNames.PROPERTY_TYPE_CD.DO_NOT_ALLOW_INVOICE_EDITS);
              sForm.setDoNotAllowInvoiceEdits(Utility.isTrue(doNotAllowInvoiceEdits));
          } catch (Exception e) {
              sForm.setDoNotAllowInvoiceEdits(false);
          }
      }

      
      int orderId = 0;
      if (desc.getOrderData() != null) {
        orderId = desc.getOrderData().getOrderId();
        OrderPropertyDataVector opdv = getInvoiceDistNotesForDisplay(invoiceId, orderId, orderEjb);
        sForm.setNotes(opdv);
      } else {
        OrderPropertyDataVector opdv = getInvoiceDistNotesForDisplay(invoiceId, 0, orderEjb);
        sForm.setNotes(opdv);
      }
      
      Account accountEjb = factory.getAccountAPI();
      if(sForm.getInvoice() != null && sForm.getInvoice().getAccountBusEntityData() != null) {
    	sForm.setCostCenterDataVector(accountEjb.getAllCostCenters(
				  sForm.getInvoice().getAccountBusEntityData().getBusEntityId(), Account.ORDER_BY_ID));
      }
      
      sForm.setRequireErpAccountNumber(false);
      Store storeBean = factory.getStoreAPI();
      StoreData dd = storeBean.getStore(desc.getInvoiceDist().getStoreId());
      PropertyDataVector prop = dd.getMiscProperties();
      for (Iterator iterator = prop.iterator(); iterator.hasNext();) {
    	  PropertyData propertyData = (PropertyData) iterator.next();
    	  if(propertyData.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER))
    	  {
    		  sForm.setRequireErpAccountNumber(Utility.isTrue(propertyData.getValue()));
    	  }
      }
      
      Distributor distributorBean = factory.getDistributorAPI();
      EmailData rejectedInvEmailData = null;
      try{
      	rejectedInvEmailData = distributorBean.getRejectedInvEmail(sForm.getInvoice().getDistributorBusEntityData().getBusEntityId());
      }catch(Exception e){
      	log.error("Error getting rejectedInvEmailData " + e);
      }
      String rejectedInvEmail = "";
      if(rejectedInvEmailData != null){
      	rejectedInvEmail = rejectedInvEmailData.getEmailAddress();
      }
      sForm.setRejectedInvEmail(rejectedInvEmail);
      clearFormVariables(sForm);

    }
    int invoiceNumLines = sForm.getInvoiceItems().size();
    log.info("invoiceNumLines = " + invoiceNumLines);
    return lUpdateErrors;
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
     * Updates the estimated sales tax based off the invoice subtotal
     */
    public static void updateCalculatedSalesTax(ActionMessages ae, HttpServletRequest request, ActionForm form) throws
            Exception {

        try {

            StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
            if (sForm == null) {
                throw new Exception("Form is null");
            }

            /*** new piece of code for Avatax: Begin ***/
           
            BigDecimal taxesForInvoice = ZERO;
            InvoiceDistData idd = sForm.getInvoice().getInvoiceDist(); // returns empty Object (no data)
            log.info("updateCalculatedSalesTax InvoiceDistData = " + idd);
            
            boolean originAddress = true;
            boolean shippingAddress = true;
            AddressData oad = AddressData.createValue();
            AddressData sad = AddressData.createValue(); 
            PurchaseOrderStatusDescDataView posddv = sForm.getInvoice();
            int distributorId = 0;
            if (posddv != null) {
            	OrderAddressData osad = posddv.getShipToAddress(); //Order Shipping Address            	
                log.info("updateCalculatedSalesTax Order Shipping Address = " + osad);
                if (osad != null) {
                	
                	//populate AddressData Object for Shipping Address
                	sad = AddressData.createValue();  
                	
                    sad.setAddress1(osad.getAddress1());     
                    sad.setAddress2(osad.getAddress2());   
                    sad.setAddress3(osad.getAddress3());   
                    sad.setAddress4(osad.getAddress4());   
                    sad.setCity(osad.getCity());
                    sad.setStateProvinceCd(osad.getStateProvinceCd());
                    sad.setPostalCode(osad.getPostalCode());
                    sad.setCountryCd(osad.getCountryCd());
                    	
            	    BusEntityData bed = posddv.getDistributorBusEntityData(); //Distributor Business Entity Data
                    log.info("updateCalculatedSalesTax Distributor Business Entity Data = " + bed);
                    if (bed != null) {
                        HttpSession session = request.getSession();
                        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
                        if (null == factory) {
                          throw new Exception("Without APIAccess.");
                        }
                        Distributor distributorEjb = factory.getDistributorAPI();
                        
                        distributorId = bed.getBusEntityId();
         	            log.info("updateCalculatedSalesTax Distributor Id = " + distributorId);
        	   
        	            //find Origin Address for ALL Vendor Invoice Items (It is only ONE Origin Address for ALL Vendor Invoice Items)
            	        DistributorData dd = null;
            	        try {
            	            dd = distributorEjb.getDistributor(distributorId);
            	        } catch (DataNotFoundException e) {
            	    	    originAddress = false;
                            e.printStackTrace();
            	        }
            	        oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)                      
                    } else { // bed = null
                        log.info("updateCalculatedSalesTax Distributor Business Entity Data = null");
                        originAddress = false;
                    }
                } else {
                    log.info("updateCalculatedSalesTax Order Shipping Address = null");
                	shippingAddress = false;
                }
            } else { // posdvv = null
            	log.info("updateCalculatedSalesTax PurchaseOrderStatusDescDataView Object = null");
            	shippingAddress = false;
            }
            if (originAddress == true && shippingAddress == true ) { //Origin and Shipping Addresses exist
                  taxesForInvoice = TaxUtilAvalara.calculateInvoiceItemTaxes(sForm.getInvoiceItems(), oad, sad);
            } else {
            	  log.info("Either Origin Address or Shipping Address or both for this Vendor Invoice are not defined. Sales Tax will not be calculated!");
                  throw new Exception("Either Origin Address or Shipping Address or both for this Vendor Invoice are not defined. Sales Tax will not be calculated!");
            }
            
            /*** new piece of code for Avatax: End ***/
            
            sForm.setCalculatedSalesTax(taxesForInvoice);

        } catch (Exception e) {
            e.printStackTrace();
            ae.add("calculatedSalesTax",
                    new ActionError("error.simpleGenericError", "Sales Tax Calculation Error: " + e.getMessage()));
        }

    }


  /**
   *Updates the estimated sales tax based off the invoice subtotal
   */
  public static void updateCalculatedTotal(ActionMessages ae,
                                           HttpServletRequest request,
                                           ActionForm form) throws Exception {
    try {
      StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      //String totalROProp = appUser.getUserProperties().getProperty(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, "");
      ///if ( totalROProp.equalsIgnoreCase("true") || totalROProp.equalsIgnoreCase("on") ) {
      BigDecimal subTotal = setInvoiceDistValuesFromUI(ae, sForm);

      BigDecimal freight = toBigDecimal(sForm.getTotalFreightCostS());
      BigDecimal misc = toBigDecimal(sForm.getTotalMiscChargesS());
      BigDecimal tax = toBigDecimal(sForm.getTotalTaxCostS());

      BigDecimal calcTotal = subTotal.add(freight);
      calcTotal = calcTotal.add(tax);
      calcTotal = calcTotal.add(misc);

      sForm.setCalculatedTotal(calcTotal);
      //}
    } catch (Exception e) {
      e.printStackTrace();
      ae.add("calculatedTotal",
             new ActionError("error.simpleGenericError",
                             "Total Calculation Error: " + e.getMessage()));
    }
  }


  /**
   *Returns the taxable subtotal for the invoice in the form.  If an item matches to an order item it compares the taxable
   *status of the order item.  If not it assumes that the item is taxable.
   */
  private static BigDecimal getTaxableSubTotalAndSetLineTax(StoreVendorInvoiceDetailForm pForm, TaxQueryResponse pTaxResp) throws Exception {
    BigDecimal subTotal = ZERO;
    Iterator it = pForm.getInvoiceItems().iterator();
    while (it.hasNext()) {
      OrderItemDescData itm = (OrderItemDescData) it.next();
      if (itm.getOrderItem() == null || (!itm.getTaxExempt()&&!itm.getReSale())) {
        BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(itm.getWorkingInvoiceDistDetailData());
        amt = amt.multiply(new BigDecimal(itm.getWorkingInvoiceDistDetailData().getDistItemQuantity()));
        subTotal = Utility.addAmt(subTotal, amt);
        
        ///////////////////////////// Avatax new code: Begin
        
        InvoiceDistData idd = pForm.getInvoice().getInvoiceDist();
        log.info("SVC getTaxableSubTotalAndSetLineTax InvoiceDistData = " + idd);
        BigDecimal calcTax = ZERO;
        
        AddressData originAddress = AddressData.createValue();
        AddressData shippingAddress = AddressData.createValue();
        
 	    //find the Origin Address and the Shipping Address for the Items of the Vendor Invoice
        if (null != idd) {                    	   
        	shippingAddress.setAddress1(idd.getShipToAddress1());     
        	shippingAddress.setAddress2(idd.getShipToAddress2());   
        	shippingAddress.setAddress3(idd.getShipToAddress3());   
        	shippingAddress.setAddress4(idd.getShipToAddress4());   
        	shippingAddress.setCity(idd.getShipFromCity());
        	shippingAddress.setStateProvinceCd(idd.getShipFromState());
        	shippingAddress.setPostalCode(idd.getShipFromPostalCode());
        	shippingAddress.setCountryCd(idd.getShipFromCountry());
        	
        	originAddress.setAddress1(idd.getShipFromAddress1());
        	originAddress.setAddress2(idd.getShipFromAddress2());
        	originAddress.setAddress3(idd.getShipFromAddress3());
        	originAddress.setAddress4(idd.getShipFromAddress4());
        	originAddress.setCity(idd.getShipFromCity());
        	originAddress.setStateProvinceCd(idd.getShipFromState());
        	originAddress.setPostalCode(idd.getShipFromPostalCode());
        	originAddress.setCountryCd(idd.getShipFromCountry());
        }	   
        
        GetTaxResult getTaxResult = TaxUtilAvalara.calculateAvatax(originAddress, shippingAddress, amt);
        if (getTaxResult.getResultCode() == SeverityLevel.Success)
        {
            //log.info("DocCode: " + getTaxRequest.getDocCode());
            // DocId is generated by AvaTax
            log.info("DocId: " + getTaxResult.getDocId());
            log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
            log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
            calcTax = getTaxResult.getTotalTax();
            calcTax = calcTax.setScale(2, BigDecimal.ROUND_HALF_UP);
            log.info("getTaxableSubTotalAndSetLineTax(): AvaTax calculated Invoice tax successfully!");
        }
        else
        {
     	    printMessages(getTaxResult.getMessages());
            log.info("AvaTax Invoice tax calculation failed!");
            throw new Exception("StoreVendorInvoiceLogic::getTaxableSubTotalAndSetLineTax => AvaTax Invoice tax calculation failed!");                              
        }
        
        ///////////////////////////// Avatax new code: End
        
        //itm.setCalculatedSalesTax(pTaxResp.calculateTax(amt)); //old code
        itm.setCalculatedSalesTax(calcTax); //Avatax new code
      } else {
        itm.setCalculatedSalesTax(ZERO);
      }
    }
    return subTotal;
  }

  protected static void printMessages(ArrayOfMessage messages)
  {
      for (int ii = 0; ii < messages.size(); ii++)
      {
          Message message = messages.getMessage(ii);
          log.info(message.getSeverity().toString() + " " + ii + ": " + message.getSummary());
      }

  }

  private static void setInvoiceDistValuesToUIDetail(PurchaseOrderStatusDescDataView pInvoice, OrderItemDescData oItm,
    HttpSession session) {
    //create the invoice dist detail object
    if (oItm.getWorkingInvoiceDistDetailData() == null) {
      //return;
      oItm.setWorkingInvoiceDistDetailData(InvoiceDistDetailData.createValue());
    }

    //create the adjusted cost
    oItm.setActualCost(Utility.getBestCostFromInvoiceDistDetail(oItm.getWorkingInvoiceDistDetailData()));
    oItm.setCwCostS(oItm.getActualCost().toString());

    //set the qty
    if (oItm.getWorkingInvoiceDistDetailData().getDistItemQuantity() == 0) {
      if (oItm.getWorkingInvoiceDistDetailData().getInvoiceDistDetailId() == 0) {
        oItm.setItemQuantityS("");
        oItm.setCwCostS("");
      } else {
        oItm.setItemQuantityS("0");
        oItm.setCwCostS("0");
      }
      oItm.setActualQty(0);
    } else {
      oItm.setActualQty(oItm.getWorkingInvoiceDistDetailData().getDistItemQuantity());
      oItm.setItemQuantityS(Integer.toString(oItm.getActualQty()));
    }

    if (oItm.getOrderItem() != null) {
      oItm.setOrderItemIdS(Integer.toString(oItm.getOrderItem().getOrderItemId()));
    } else {
      oItm.setOrderItemIdS("");
    }

    InvoiceDistDetailData inv = oItm.getWorkingInvoiceDistDetailData();
    OrderItemData oitm = oItm.getOrderItem();
    //pre populate some of the info for the dist item.  This will in turn update that when it is sent to the db
    //and allow the user some idea of what they are in fact matching.
    boolean keepInvData = true;
    if (oitm != null) {
      if (!Utility.isSet(inv.getDistItemSkuNum()) || !keepInvData) {
        inv.setDistItemSkuNum(oitm.getDistItemSkuNum());
      }
      if (!Utility.isSet(inv.getDistItemShortDesc()) || !keepInvData) {
        inv.setDistItemShortDesc(oitm.getItemShortDesc());
      }
      if (!Utility.isSet(inv.getDistItemUom()) || !keepInvData) {
        inv.setDistItemUom(oitm.getDistItemUom());
      }
      if (!Utility.isSet(inv.getDistItemPack()) || !keepInvData) {
        inv.setDistItemPack(oitm.getDistItemPack());
      }
      if (!Utility.isSet(inv.getErpAccountCode()) || !keepInvData) {
        if (!pInvoice.getInvoiceApproved()) {
          if (oitm.getCostCenterId() > 0) {
            CostCenterData ccd = (CostCenterData) session.getAttribute("cached.cost.center." + oitm.getCostCenterId());
            if (ccd == null) {
              try {
                APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
                ccd = factory.getAccountAPI().getCostCenter(oitm.getCostCenterId(), 0);
                session.setAttribute("cached.cost.center." + oitm.getCostCenterId(), ccd);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            //will only be null if there was an error retribeving the cost center.
            if (ccd != null) {
              inv.setErpAccountCode(ccd.getShortDesc());
            }
          }
        }
      }
    }
  }

  private static void setInvoiceDistValuesToUILeaveUserValues(StoreVendorInvoiceDetailForm sForm) {
    Iterator it = sForm.getInvoiceItems().iterator();
    while (it.hasNext()) {
      OrderItemDescData oItm = (OrderItemDescData) it.next();
      oItm.setActualCost(Utility.getBestCostFromInvoiceDistDetail(oItm.getWorkingInvoiceDistDetailData()));
      oItm.setActualQty(oItm.getWorkingInvoiceDistDetailData().getDistItemQuantity());
    }
  }

  /**
   *Initializes the form with some default values and sets null values to an appropiate value.
   */
  private static void setInvoiceDistValuesToUI(StoreVendorInvoiceDetailForm sForm, HttpSession session,
                                               HttpServletRequest request) throws Exception {

    if (sForm.getInvoiceItems() == null) {
      sForm.setInvoiceItems(new OrderItemDescDataVector());
    }
    if (sForm.getInvoice() == null) {
      sForm.setInvoice(PurchaseOrderStatusDescDataView.createValue());
    }
    if (sForm.getInvoice().getInvoiceDist() == null) {
      sForm.getInvoice().setInvoiceDist(InvoiceDistData.createValue());
    }
    ArrayList validPoItems = new ArrayList();
    BigDecimal calcRecTotal = ZERO;
    Iterator it = sForm.getInvoiceItems().iterator();
    while (it.hasNext()) {
      OrderItemDescData oItm = (OrderItemDescData) it.next();

      setInvoiceDistValuesToUIDetail(sForm.getInvoice(), oItm, session);
      if (oItm.getOrderItem() != null &&
          (oItm.getWorkingInvoiceDistDetailData() == null ||
           oItm.getWorkingInvoiceDistDetailData().getDistItemQuantity() == 0)) {
        FormArrayElement ele = new FormArrayElement(oItm.getOrderItem().getDistItemSkuNum(),
          Integer.toString(oItm.getOrderItem().getOrderItemId()));
        validPoItems.add(ele);
      }
      if (oItm.getWorkingInvoiceDistDetailData() != null) {
        InvoiceDistDetailData wi = oItm.getWorkingInvoiceDistDetailData();
        BigDecimal cost = wi.getItemReceivedCost();
        if (!Utility.isSet(cost)) {
          cost = wi.getItemCost();
        }
        if (cost == null) {
          cost = wi.getAdjustedCost();
        }
        if (cost == null) {
          cost = ZERO;
        }
        cost = cost.multiply(new BigDecimal(wi.getDistItemQuantity()));
        calcRecTotal = calcRecTotal.add(cost);
      }
    }
    session.setAttribute("store.vendor.invoice.detail.validPoItems", validPoItems);
    
    /*** fetch a list of possible ERP Account Number(s) for Purchase Order Items (SVC) ***/
    PurchaseOrderStatusDescDataView desc;
    desc = sForm.getInvoice();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    ArrayList DistErpAccountList = purchaseOrderEjb.fetchErpAccountNum(desc.getInvoiceDist().getInvoiceDistId());
    log.info("*******************SVC: DistErpAccountList = " + DistErpAccountList);
    session.setAttribute("store.vendor.invoice.detail.DistErpAccountList", DistErpAccountList);
    /***************************************************************************/
    
    //init some of the base form variables used as temporary storage of String values before coverson
    //to appropriate type (int, BigDeciml, Date, etc.)

    //invoiceDate
    if (sForm.getInvoice().getInvoiceDist().getInvoiceDate() != null) {
      sForm.setInvoiceDateS(ClwI18nUtil.formatDateInp(request, sForm.getInvoice().getInvoiceDist().getInvoiceDate()));
    } else {
      sForm.setInvoiceDateS("");
    }

    //freight
    if (sForm.getInvoice().getInvoiceDist().getFreight() != null) {
      sForm.setTotalFreightCostS(sForm.getInvoice().getInvoiceDist().getFreight().toString());
    } else {
      sForm.setTotalFreightCostS("");
    }

    //misc charges
    if (sForm.getInvoice().getInvoiceDist().getMiscCharges() != null) {
      sForm.setTotalMiscChargesS(sForm.getInvoice().getInvoiceDist().getMiscCharges().toString());
    } else {
      sForm.setTotalMiscChargesS("");
    }

    //tax
    if (sForm.getInvoice().getInvoiceDist().getSalesTax() != null) {
      sForm.setTotalTaxCostS(sForm.getInvoice().getInvoiceDist().getSalesTax().toString());
    } else {
      sForm.setTotalTaxCostS("");
    }

  //Discount
    if (sForm.getInvoice().getInvoiceDist().getDiscounts() != null) {
      sForm.setTotalDiscountS(sForm.getInvoice().getInvoiceDist().getDiscounts().toString());
    } else {
      sForm.setTotalDiscountS("");
    }
    
    //calculated recieved subtotal
    if (calcRecTotal != null) {
      sForm.setSubTotalReceivedCost(calcRecTotal);
      calcRecTotal = Utility.addAmt(calcRecTotal, sForm.getInvoice().getVendorRequestedFreight());
      calcRecTotal = Utility.addAmt(calcRecTotal, sForm.getInvoice().getVendorRequestedMiscCharges());
      calcRecTotal = Utility.addAmt(calcRecTotal, sForm.getInvoice().getVendorRequestedTax());
      calcRecTotal = Utility.addAmt(calcRecTotal, sForm.getInvoice().getVendorRequestedDiscount());
      sForm.setTotalReceivedCost(calcRecTotal);
    } else {
      sForm.setTotalReceivedCost(ZERO);
      sForm.setSubTotalReceivedCost(ZERO);
    }

    GCAViewLogic.setInvoiceDistValuesToUILeaveUserValues(request, sForm);

    //total is left intentiaonally balnk as this is just a human check, make sur what is entered should in fact match the
    //total.
  }

  /**
   *Initializes a new invoice based off a purchase order, or completely blank not referencing anything else in the system
   */
  public static ActionMessages initNewInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    int poId = 0;
    try {
      poId = Integer.parseInt(request.getParameter("id"));
    } catch (Exception e) {}
    if (poId == 0) {
      PurchaseOrderStatusDescDataView desc = PurchaseOrderStatusDescDataView.createValue();
      desc.setInvoiceDist(InvoiceDistData.createValue());
      sForm.setInvoiceItems(new OrderItemDescDataVector());
    } else {
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        throw new Exception("Without APIAccess.");
      }
      PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
      PurchaseOrderStatusDescDataView desc = purchaseOrderEjb.getPurchaseOrderStatusDesc(poId);
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      //make sure we are properly authorized to view this invoice.
      if (appUser.getUserStore().getStoreId() == desc.getOrderData().getStoreId()) {
        desc.setInvoiceDist(InvoiceDistData.createValue());
        sForm.setInvoice(desc);
        sForm.setInvoiceItems(purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(0, poId));
      }
    }
    clearFormVariables(sForm);
    return lUpdateErrors;
  }


  private static void clearFormVariables(StoreVendorInvoiceDetailForm sForm) {
//    	reset cached propeties that are needed for saving the invoice
    if (sForm.getDistributor() == null || sForm.getInvoice() == null ||
        sForm.getDistributor().getBusEntity() == null ||
        sForm.getInvoice().getDistributorBusEntityData() == null ||
        sForm.getDistributor().getBusEntity().getBusEntityId() !=
        sForm.getInvoice().getDistributorBusEntityData().getBusEntityId()) {
      sForm.setDistributor(null);
      sForm.setDistributorTerritory(null);
    }
    sForm.setExistingIvoices(null);
    sForm.setLineToDelete(null);
    sForm.setTotalAmountS("");
    sForm.setOrderRoutedWarningCount(0);
    sForm.setMinimumOrderWarningCount(0);
    sForm.setFrieghtOnBackorderedWarningCount(0);
    sForm.setFrieghtOnFreeTerritoryWarningCount(0);
    sForm.setFrieghtOverMaxCountWarningCount(0);
    sForm.setFreightOverOrderFreightForDistWarningCount(0);
  }

  /**
   *Saves the distributor invoice and the distributor invoice detail that are contained in the form.
   */
  public static ActionMessages saveInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    try {

      HttpSession session = request.getSession();
      StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        throw new Exception("Without APIAccess.");
      }

      if(sForm.getInvoice()!=null && sForm.getInvoice().getInvoiceDist()!=null){
         sForm.getInvoice().getInvoiceDist().setModBy(appUser.getUserName());
      }

      lUpdateErrors = GCAViewLogic.saveInvoiceDist(request, form);
      if (lUpdateErrors != null && lUpdateErrors.size() > 0) {
        return lUpdateErrors;
      }

      lUpdateErrors = new ActionMessages();
      if (!verifyStoreAuthorization(session, form)) {
        lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
        return lUpdateErrors;
      }

      // Bug 1927 - Part #2 of the ERP Account Number code 
      PurchaseOrder poBean = factory.getPurchaseOrderAPI();
      
      int storeId0 = 0; // initialize to 0
      int invoiceDistId0 = sForm.getInvoice().getInvoiceDist().getInvoiceDistId();
      if (invoiceDistId0 > 0) {
          storeId0 = poBean.getBusEntityIdOffInvoiceDistId(invoiceDistId0);
      } else {
          storeId0 = sForm.getInvoice().getOrderData().getStoreId();
      }
      boolean requireErpAccountNumber = false; 
      if (storeId0 != 0) { // invoice for this store is valid and not "broken"             
        PropertyService propBean = factory.getPropertyServiceAPI();
        PropertyDataVector pdv0 = propBean.getProperties(null, storeId0, RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER);       
        if ( !pdv0.isEmpty() && pdv0 != null) { // we found the record in the clw_property table with the property ERP_ACCOUNT_NUMBER      	
           //get the value of the property "ERP_ACCOUNT_NUMBER"
           PropertyData pd0 = (PropertyData) pdv0.get(0);
           String clwValue0;
           clwValue0 = pd0.getValue(); // find out, does the business entity require alphanumeric code
                                       // to be entered in each line item of the invoice 
           if (clwValue0.equals("true")) { // business entity requires alphanumeric code
                                           // to be entered in each line item of the invoice 
        	   requireErpAccountNumber = true;
           }
        } 
      }
      Iterator iterator = sForm.getInvoiceItems().iterator();
      while (iterator.hasNext()) {
    	  OrderItemDescData orderItemDescData = (OrderItemDescData) iterator.next();
          String erpAcctCd = orderItemDescData.getWorkingInvoiceDistDetailData().getErpAccountCode();

          if (!isInvoiceItemEligibleForSaving(orderItemDescData)) {
        	  continue;
          }          
          //if ((erpAcctCd == null) || (erpAcctCd.equals("")) && sForm.isRequireErpAccountNumber()) { // ErpAcctCd (ERP ACCT on the screen) is empty and Store RequireErpAccountNumber flag is true
          if ((erpAcctCd == null) || (erpAcctCd.equals("")) && requireErpAccountNumber) { // ErpAcctCd (ERP ACCT on the screen) is empty and Store RequireErpAccountNumber flag is true 
             lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.simpleGenericError","Invoice was not updated. Missing required(for this store) ERP Account Number in invoice line."));
             return lUpdateErrors;
          }
          /*** commented because:
           * 1) GCA Store has its own ERP Account Codes; it means that this check does not work for ALL stores
          if ( !isErpAcctCdCorrect(erpAcctCd, sForm.getCostCenterDataVector())) { // ErpAcctCd (ERP ACCT on the screen) is not empty and it is not in the list of allowable values 
    		 lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.simpleGenericError","Invoice was not updated. Incorrect ERP Account Number=" + erpAcctCd + " in invoice line"));
             return lUpdateErrors;
          }
          ***/
      }
      // End of Bug 1927
      
      /************** new by SVC ***/
      
      PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
      Order orderEjb = factory.getOrderAPI();      
      
      int storeId = 0; // initialize to 0
      int invoiceDistId = sForm.getInvoice().getInvoiceDist().getInvoiceDistId();
      log.info("*****************SVC: invoiceDistId  = " + invoiceDistId);
      if (invoiceDistId > 0) {
          storeId = poEjb.getBusEntityIdOffInvoiceDistId(invoiceDistId);
      } else {
          storeId = sForm.getInvoice().getOrderData().getStoreId();
      }
      log.info("*****************SVC: storeId  = " + storeId);
      if (storeId != 0) { // invoice for this store is valid and not "broken"
        //busEntityId = poEjb.getBusEntityIdOffInvoiceDistId(sForm.getInvoice().getInvoiceDist().getInvoiceDistId()); 
      
      
        String propertyTypeCode = RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER;
        PropertyService propEjb = factory.getPropertyServiceAPI();
        //select clw_value from clw_property where trim(short_desc)='ERP_ACCOUNT_NUMBER' and <bus_entity_id = busEntityId>;
        PropertyDataVector pdv = propEjb.getProperties(null, storeId, RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER); 
      
        log.info("*****************SVC: pdv = " + pdv);
      
        if ( !pdv.isEmpty() && pdv != null) { // we found the record in the clw_property table with the property ERP_ACCOUNT_NUMBER
      	
           //get the value of the property ERP_ACCOUNT_NUMBER
           PropertyData pd = (PropertyData) pdv.get(0);
           String clwValue;
           clwValue = pd.getValue(); // find out, does the business entity require alphanumeric code
                                          // to be entered in each line item of the invoice 
           log.info("*****************SVC: clwValue = " + clwValue);

           if (clwValue.equals("true")) { // business entity requires alphanumeric code
                                        // to be entered in each line item of the invoice 
      	   
      	       log.info("*****************SVC: business entity requires Alphanumeric code for ERP Account number");
      	      
      	   
      	       //OrderItemDataVector = Utility.toOrderItemDataVector(sForm.getInvoiceItems());
      	       
      	       // loop through all the PO items
      	       Iterator it = sForm.getInvoiceItems().iterator();
      	       while (it.hasNext()) {
      	          OrderItemDescData desc = (OrderItemDescData) it.next();
 	              String erpAcctCd = desc.getWorkingInvoiceDistDetailData().getErpAccountCode();
 	              Integer distItemQtyReceived = desc.getWorkingInvoiceDistDetailData().getDistItemQtyReceived(); //is it int ? value of invoice quantity
 	              java.math.BigDecimal recCst = desc.getWorkingInvoiceDistDetailData().getItemReceivedCost(); // value of invoice cost
 	            
 	              log.info("*****************SVC: erpAcctCd = " + erpAcctCd);
 	              log.info("*****************SVC: distItemQtyReceived = " + distItemQtyReceived);
 	              log.info("*****************SVC: recCst = " + recCst);
 	            
 	              if ((erpAcctCd == null) || (erpAcctCd.equals("")) && recCst != null && distItemQtyReceived != null) { // ErpAcctCd (ERP ACCT on the screen) is empty and the order was NOT invoiced
 	            	 
 	            	 log.info("*********SVC: missing ERP Accout number in invoice line"); 
 	            	 // Should not save updated invoice in the Database
 	                 
 	                 // if Invoice has a status code other than "PENDING_REVIEW", 
 	                 // set the status code of the Invoice to "PENDING_REVIEW" 
 	                 
 	                 //String invoiceStatusCd = sForm.getInvoice().getInvoiceDist().getInvoiceDistId();
 	                 InvoiceDistData inv = sForm.getInvoice().getInvoiceDist();
 	                 String invoiceStatusCd = inv.getInvoiceStatusCd();
 	                 
 	                 log.info("*********SVC: invoiceStatusCd = " + invoiceStatusCd); 
 	                 
 	                 if (invoiceStatusCd.trim().equals(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW)){ // status code of the Invoice is "PENDING_REVIEW"
 	                	    //do nothing
 	                 } else {   	                	 
 	                	    //set invoice status code to "PENDING_REVIEW"
 	                	    //update status code of the Invoice in the DB    	                	 
 	                	    PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();        	     
 	        	            purchaseOrderEjb.updateInvoiceStatusCd(invoiceDistId, invoiceStatusCd.trim() );
 	                 }  
 	                 log.info("*********SVC: write a Note with an error message to the CLW_ORDER_PROPERTY table");
 	        	           
 	                 //write a Note with an error message to the CLW_ORDER_PROPERTY table
 	        	     OrderPropertyData pData = null;  	        	      	        		     	        		    	   
 	        	     OrderPropertyData orderProperty = OrderPropertyData.createValue();
 	        		 	    
 	        	     //setting the values of a single record to be saved in the DB table   	        		        
 	        	     int orderId = sForm.getInvoice().getOrderData().getOrderId();
 	        		 //int invoiceDistId = sForm.getInvoice().getInvoiceDist().getInvoiceDistId();
 	        			    
 	        		 log.info("order_id for the SQL stmt = " + orderId);
 	        		     
 	        		 orderProperty.setOrderId( orderId ); // set order_id value
 	        		 orderProperty.setInvoiceDistId(invoiceDistId); // set Invoice Distributor Id value
 	        		 orderProperty.setShortDesc("Invoice Error");// set short_desc value
 	        		 clwValue = "Missing ERP Acct Number in invoice line"; // construct clw_value
 	        		 orderProperty.setValue(clwValue); // set clw_value value
 	        		 orderProperty.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE); // set order_property_type_cd value
 	        		 orderProperty.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE); //set order_property_status_cd value
 	        		 try { 
 	        		           pData = orderEjb.addNote(orderProperty);
 	        		 } catch (DataNotFoundException e) {
 	        		           log.error("Note for the Invoice Distributor Id = " + invoiceDistId + " was not inserted in the clw_order_property table.");   
 	        		           e.printStackTrace();
 	        		 }
 	        		        
 	        		 log.info("pData = " + pData);

 	        		 lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.simpleGenericError","Invoice was not updated. Missing ERP Account Number in invoice line"));
 	                 
 	                 return lUpdateErrors;
 	              } // if ((erpAcctCd == null)
 	              else {
 	            	  continue;
 	              }
      	    } // while (it.hasNext())
      	    
      	    //return lUpdateErrors;
      	    
         }	// if (clwValue.equals("true"))
      } // if ( !pdv.isEmpty() )
        //return lUpdateErrors;  // ???
      } // if (storeId != 0) 
      
      
      /*** end of new code written by SVC ***/
      
     
      //Set the string values!
      setInvoiceDistValuesFromUI(lUpdateErrors, sForm);
      //set the date
      sForm.getInvoice().getInvoiceDist().setInvoiceDate(ClwI18nUtil.parseDateInp(request, sForm.getInvoiceDateS()));
      //set the frieght
      BigDecimal freight = toBigDecimal(sForm.getTotalFreightCostS());
      sForm.getInvoice().getInvoiceDist().setFreight(freight);
      //set the misc charges
      BigDecimal misc = toBigDecimal(sForm.getTotalMiscChargesS());
      sForm.getInvoice().getInvoiceDist().setMiscCharges(misc);
      //set the discount
      BigDecimal discount = toBigDecimal(sForm.getTotalDiscountS());
      sForm.getInvoice().getInvoiceDist().setDiscounts(discount);
      
      //set the tax
      BigDecimal tax = toBigDecimal(sForm.getTotalTaxCostS());
      sForm.getInvoice().getInvoiceDist().setSalesTax(tax);

      BigDecimal calcTotal = sForm.getInvoice().getInvoiceDist().getSubTotal().add(freight);
      calcTotal = calcTotal.add(tax);
      calcTotal = calcTotal.add(misc);

      // if user has not property,
      //double check the entered total to make sure that it matches the summed total (+/- .02 this is an arbitrarily chosen value)
      String totalROProp = appUser.getUserProperties().getProperty(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, "");
      if (totalROProp.equals("") || totalROProp.equalsIgnoreCase("false") || totalROProp.equalsIgnoreCase("off")) {
        BigDecimal enteredTotal = toBigDecimal(sForm.getTotalAmountS());
        if (calcTotal.subtract(enteredTotal).abs().compareTo(new BigDecimal(.02)) > 0) {
          lUpdateErrors.add("totalAmountS",
                            new ActionError("invoice.totalAmoutnMismatch",
                                            calcTotal.toString()));
        }
      }
      if (lUpdateErrors.size() > 0) {
        return lUpdateErrors;
      }

      //Based off the user type this should be one of a couple status codes.      
      //PurchaseOrder poEjb = factory.getPurchaseOrderAPI(); // Commented by SVC, because I moved it up 
      //Order orderEjb = factory.getOrderAPI(); // Commented by SVC, because I moved it up

      InvoiceDistData invoice = sForm.getInvoice().getInvoiceDist();

      String erpPoNum = sForm.getInvoice().getInvoiceDist().getErpPoNum();
      //lazy load some things that are needed for saving only
      if (sForm.getDistributorTerritory() == null) {
        if (invoice.getShipToPostalCode() == null) {
          sForm.setDistributorTerritory(new BusEntityTerrViewVector());
        } else {
          java.util.Hashtable terrCond = new java.util.Hashtable();
          terrCond.put("postalCode", invoice.getShipToPostalCode());
          sForm.setDistributorTerritory(factory.getDistributorAPI().getDistributorZipCodes(sForm.getInvoice().
            getDistributorBusEntityData().getBusEntityId(), terrCond));
        }
      }
      if (sForm.getDistributor() == null && sForm.getInvoice().getDistributorBusEntityData() != null) {
        sForm.setDistributor(factory.getDistributorAPI().getDistributor(sForm.getInvoice().getDistributorBusEntityData().
          getBusEntityId()));
      }
      if (sForm.getExistingIvoices() == null) {
        InvoiceDistDataVector existingIvoices = orderEjb.getInvoiceDistCollection(null, null, null, erpPoNum, null, null, null);
        sForm.setExistingIvoices(existingIvoices);
      }
      InvoiceDistDataVector existingIvoices = sForm.getExistingIvoices();
      BusEntityTerrViewVector distTerr = sForm.getDistributorTerritory();

      BigDecimal poItemTotals;
      if (sForm.getInvoice().getPurchaseOrderData() != null) {
        poItemTotals = sForm.getInvoice().getPurchaseOrderData().getLineItemTotal();
      } else {
        poItemTotals = ZERO;
      }
      OrderItemDataVector orderItems = Utility.toOrderItemDataVector(sForm.getInvoiceItems());
      FreightHandlerView fhv = getFreightHandler(orderItems);
      DistributorInvoiceFreightTool frtTool =
        new DistributorInvoiceFreightTool(poItemTotals,
                                          orderItems, invoice, sForm.getDistributor(), fhv, existingIvoices, distTerr,
                                          sForm.getInvoice().getOrderFreightDataVector());
      if (frtTool.isFreightHandlerFreightNotAllowedWithFreight() && sForm.getOrderRoutedWarningCount() == 0) {
        sForm.setOrderRoutedWarningCount(sForm.getOrderRoutedWarningCount() + 1);
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("invoice.shippedViaFreightHandlerWithFrieght"));
      }
      if (frtTool.isOverMinimumOrderWithFrieght() && sForm.getMinimumOrderWarningCount() == 0) {
        sForm.setMinimumOrderWarningCount(sForm.getMinimumOrderWarningCount() + 1);
        BigDecimal minimumOrderAmount = sForm.getDistributor().getMinimumOrderAmount();
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE,
                          new ActionError("invoice.overMinimumOrderWithFrieght", poItemTotals, minimumOrderAmount));
      }
      if (frtTool.isBackOrderWithFreight() && sForm.getFrieghtOnBackorderedWarningCount() == 0) {
        sForm.setFrieghtOnBackorderedWarningCount(sForm.getFrieghtOnBackorderedWarningCount() + 1);
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("invoice.backOrderWithFreight"));
      }
      if (frtTool.isFreeTerritoryWithFreight() && sForm.getFrieghtOnFreeTerritoryWarningCount() == 0) {
        sForm.setFrieghtOnFreeTerritoryWarningCount(sForm.getFrieghtOnFreeTerritoryWarningCount() + 1);
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("invoice.freeTerritoryWithFreight"));
      }
      if (frtTool.isFreightOverMax() && sForm.getFrieghtOverMaxCountWarningCount() == 0) {
        sForm.setFrieghtOverMaxCountWarningCount(sForm.getFrieghtOverMaxCountWarningCount() + 1);
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("invoice.freightOverMax"));
      }
      if (frtTool.isFreightOverOrderFreightForDist() && sForm.getFreightOverOrderFreightForDistWarningCount() == 0) {
        sForm.setFreightOverOrderFreightForDistWarningCount(sForm.getFreightOverOrderFreightForDistWarningCount() + 1);
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("invoice.freightOverOrderFreightForDist"));
      }
      //Check to see if the invoice is an empty invoice (no freight, tax, misc charges etc)
      if (ZERO.equals(calcTotal)) {
        lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("invoice.emptyInvoice"));
      }
      if (lUpdateErrors.size() != 0) {
        return lUpdateErrors;
      }
      int updatedInvoiceDistId = sForm.getInvoice().getInvoiceDist().getInvoiceDistId();
      {
        //set the ship from data based off the distributor
        if (!Utility.isSet(invoice.getShipFromAddress1()) && !Utility.isSet(invoice.getShipFromCity())) {
          if(sForm.getDistributor() != null){
              AddressData shipFrom = sForm.getDistributor().getPrimaryAddress();
              invoice.setShipFromAddress1(shipFrom.getAddress1());
              invoice.setShipFromAddress2(shipFrom.getAddress2());
              invoice.setShipFromAddress3(shipFrom.getAddress3());
              invoice.setShipFromAddress4(shipFrom.getAddress4());
              invoice.setShipFromCity(shipFrom.getCity());
              invoice.setShipFromCountry(shipFrom.getCountryCd());
              invoice.setShipFromPostalCode(shipFrom.getPostalCode());
              invoice.setShipFromState(shipFrom.getStateProvinceCd());
              invoice.setShipFromName(sForm.getDistributor().getBusEntity().getShortDesc());
          }
        }
        //set the source code of this invoice
        if (invoice.getInvoiceDistId() == 0) {
          invoice.setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.WEB);
        }
        sForm.getInvoice().setInvoiceDistDetailList(toInvoiceDistDetailDataVector(invoice, sForm.getInvoiceItems()));

        /*******SVC: before here I added new piece of code; now I moved it up ***/

        
        if (appUser.isaAdmin()) {
          updatedInvoiceDistId = poEjb.processInvoiceDistUpdate(sForm.getInvoice(), appUser.getUserName(), true);
        } else {
          updatedInvoiceDistId = poEjb.processInvoiceDistUpdate(sForm.getInvoice(), appUser.getUserName(), false);
        }
      }

      PurchaseOrderStatusDescDataView updatedInvoiceDist = poEjb.getDistributorInvoiceDesc(updatedInvoiceDistId);
      replaceInvoiceInList(session, updatedInvoiceDist);

      //try and match up the form items with the items returned from the update.
      //if(!matchDisributorItems(invItms,sForm.getInvoiceItems())){
      fetchInvoiceDistDetail(request, form, updatedInvoiceDistId);
      //}
    } catch (DuplicateNameException e) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.duplicate.invoice"));
      return lUpdateErrors;
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        lUpdateErrors.add("error", new ActionError("error.simpleGenericError", mess));
        return lUpdateErrors;
      } else {
        throw exc;
      }
    }
    return lUpdateErrors;

  }

  private static boolean isErpAcctCdCorrect(String erpAcctCd,
        CostCenterDataVector costCenterDataVector) {
	if(erpAcctCd == null || erpAcctCd.equals("")) {
		return true;
	}
	
	for (Iterator iterator = costCenterDataVector.iterator(); iterator.hasNext();) {
		CostCenterData costCenterData = (CostCenterData) iterator.next();
		if(costCenterData.getShortDesc().equals(erpAcctCd))	{
			return true;
		}
    }
	return false;
}

/**
   *Matches the distributor items to their order itme desc data.  returns true if all the data matched.  False otherwise.  This
   *method modifies the passsed in OrderItemDescDataVector with the passed in InvoiceDistDataVector
   */
  /*private static boolean matchDisributorItems(InvoiceDistDetailDataVector invItms, OrderItemDescDataVector oItms){
      Iterator it = invItms.iterator();
      while(it.hasNext()){
          InvoiceDistDetailData invItm = (InvoiceDistDetailData) it.next();
          Iterator oIt = oItms.iterator();
          boolean removed = false;
          while(oIt.hasNext()){
              OrderItemDescData oItm = (OrderItemDescData) oIt.next();
              if(oItm.getOrderItem() != null && oItm.getOrderItem().getOrderItemId() == invItm.getOrderItemId()){
                  oItm.setWorkingInvoiceDistDetailData(invItm);
                  if(!removed){
                      it.remove();
                      removed = true;
                  }
              }
          }
      }

      return invItms.isEmpty();
       }*/

  private static OrderItemData getOrderItemData(OrderItemDescDataVector oItms, int orderItemId) {
    OrderItemDescData oItm = getOrderItemDescDataForOrderItemId(oItms, orderItemId);
    if (oItm != null) {
      return oItm.getOrderItem();
    }
    return null;
  }

  private static OrderItemDescData getOrderItemDescDataForOrderItemId(OrderItemDescDataVector oItms, int orderItemId) {
    Iterator it = oItms.iterator();
    while (it.hasNext()) {
      OrderItemDescData oItm = (OrderItemDescData) it.next();
      if (oItm.getOrderItem() != null && oItm.getOrderItem().getOrderItemId() == orderItemId) {
        return oItm;
      }
    }
    return null;
  }

  /**
   *Converts a Sting to a BigDecimal accounting for spaces.  Assumes that null checking has already occured, will return 0 if null value.
   */
  private static BigDecimal toBigDecimal(String val) {
    if (!Utility.isSet(val)) {
      return ZERO;
    }
    val = val.trim();
    return new BigDecimal(val);
  }

  /**
   *Converts a Sting to an int accounting for spaces.  Assumes that null checking has already occured, will return 0 if null value.
   */
  private static int toInt(String val) {
    if (!Utility.isSet(val)) {
      return 0;
    }
    val = val.trim();
    return Integer.parseInt(val);
  }


  /**
   *Converts the string values used for web entry into the apropriate data type (int, BigDecimal etc.)
   *Assumes validation has already taken place and that all of the values are valid (nulls treated as 0).
   */
  private static BigDecimal setInvoiceDistValuesFromUI(ActionMessages pErrors, StoreVendorInvoiceDetailForm sForm) {
    PurchaseOrderData po = null;
    if(sForm.getInvoice() != null && sForm.getInvoice().getPurchaseOrderData() != null){
        sForm.getInvoice().getPurchaseOrderData();
    }
    //InvoiceDistData masInv = sForm.gsetetInvoice().getInvoiceDist();
    if (sForm.getDistributor() != null) {
      if (po != null && Utility.isSet(po.getDistErpNum()) &&
          !sForm.getDistributor().getBusEntity().getErpNum().equals(po.getDistErpNum())) {
        log.info("------------------------------>Reasigned distributor? " + po.getDistErpNum() + "!=" +
                           sForm.getDistributor().getBusEntity().getErpNum());
        pErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.cannot.reassign.dist"));
      }
    }

    OrderItemDescDataVector lOrderItemDescDataVector = sForm.getInvoiceItems();

    BigDecimal subTotal = new BigDecimal(0.00);
    if(lOrderItemDescDataVector != null){
        Iterator it = lOrderItemDescDataVector.iterator();
        while (it.hasNext()) {
          OrderItemDescData oI = (OrderItemDescData) it.next();
          InvoiceDistDetailData inv = oI.getWorkingInvoiceDistDetailData();
          if (inv == null) {
            inv = InvoiceDistDetailData.createValue();
            oI.setWorkingInvoiceDistDetailData(inv);
          }
          if (oI.getOrderItem() != null) {
            inv.setOrderItemId(oI.getOrderItem().getOrderItemId());
            inv.setErpPoRefLineNum(oI.getOrderItem().getErpPoRefLineNum());
          }
          int qty = 0;
          if (Utility.isSet(oI.getItemQuantityS())) {
            qty = toInt(oI.getItemQuantityS());
          }
          if (inv.getInvoiceDistDetailId() == 0) {
            inv.setDistItemQtyReceived(qty);
          }

          inv.setItemQuantity(qty);
          inv.setDistItemQuantity(qty);

          BigDecimal cost = ZERO;
          if (!Utility.isSet(oI.getCwCostS())) {
            if (qty != 0) {
              pErrors.add("cwCostS", new ActionError("invoice.costNotEntered"));
            }
          } else {
            cost = toBigDecimal(oI.getCwCostS());
            inv.setAdjustedCost(cost);
            if (inv.getItemReceivedCost() == null) {
              inv.setItemReceivedCost(cost);
            }
            if (inv.getItemCost() == null) {
              inv.setItemCost(cost);
            }
          }

          BigDecimal lineTot = cost.multiply(new BigDecimal(qty));
          inv.setLineTotal(lineTot);
          subTotal = subTotal.add(lineTot);

          OrderItemData oitm = oI.getOrderItem();
          int oid = 0;
          if (oitm != null) {
            oid = oitm.getOrderItemId();
          }
          if (inv.getOrderItemId() > 0 && inv.getOrderItemId() != oid) {
            oitm = getOrderItemData(lOrderItemDescDataVector, oid);
          }
          oid = inv.getOrderItemId();
          //fresh insertion we are going to set some of the values based off the order item data if it is not set and
          //the order item data is in fact present

          if (inv.getInvoiceDistDetailId() == 0 && oitm != null) {
            inv.setItemPack(oitm.getItemPack());
            inv.setItemUom(oitm.getItemUom());
            inv.setItemShortDesc(oitm.getItemShortDesc());
          }

          //some things we want to overide regardless of if this is a fresh insertio or not.
          if (oitm != null) {
            inv.setErpPoLineNum(oitm.getErpPoLineNum());
            inv.setItemSkuNum(oitm.getItemSkuNum());
          }
        }
    }
    if(sForm.getInvoice() != null && sForm.getInvoice().getInvoiceDist() != null){
        sForm.getInvoice().getInvoiceDist().setSubTotal(subTotal);
    }
    return subTotal;
  }

  /**
   *Converts a OrderItemDescDataVector to a InvoiceDistDetailDataVector using the workingInvoiceDistDetailData property as the source data and
   *sets the invoice dist id in the converted items
   */
  private static InvoiceDistDetailDataVector toInvoiceDistDetailDataVector(InvoiceDistData pInvoice,
    OrderItemDescDataVector pOrderItemDescDataVector) {
    InvoiceDistDetailDataVector retVal = new InvoiceDistDetailDataVector();
    Iterator it = pOrderItemDescDataVector.iterator();
    while (it.hasNext()) {
      OrderItemDescData oI = (OrderItemDescData) it.next();
      //if there is a qty or this is an update
      if (isInvoiceItemEligibleForSaving(oI)) {
        oI.getWorkingInvoiceDistDetailData().setInvoiceDistId(pInvoice.getInvoiceDistId());
        if (oI.getOrderItem() == null) {
          oI.getWorkingInvoiceDistDetailData().setOrderItemId(0);
          oI.getWorkingInvoiceDistDetailData().setErpPoRefLineNum(0);
        } else {
          oI.getWorkingInvoiceDistDetailData().setOrderItemId(oI.getOrderItem().getOrderItemId());
          oI.getWorkingInvoiceDistDetailData().setErpPoRefLineNum(oI.getOrderItem().getErpPoRefLineNum());
        }
        retVal.add(oI.getWorkingInvoiceDistDetailData());
      }
    }
    return retVal;
  }

private static boolean isInvoiceItemEligibleForSaving(
        OrderItemDescData orderItemDescData) {
	return orderItemDescData.getWorkingInvoiceDistDetailData().getItemQuantity() > 0 ||
          orderItemDescData.getWorkingInvoiceDistDetailData().getInvoiceDistDetailId() > 0;
}

  /**
   *Looks at throws objects in the form and refreshes the data when a new line is select.  I.e. the user added a line and then told us
   *that is should be matched against some other line.  This version leaves whatever the user entered unaltered.
   */
  public static ActionMessages refreshObjectsInvoiceDistLeaveUserEntry(HttpServletRequest request, ActionForm form) throws
    Exception {
    return refreshObjectsInvoiceDist(request, form, true);
  }

  /**
   *Looks at throws objects in the form and refreshes the data when a new line is select.  I.e. the user added a line and then told us
   *that is should be matched against some other line.
   */
  public static ActionMessages refreshObjectsInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    return refreshObjectsInvoiceDist(request, form, false);
  }

  /**
   *Looks at throws objects in the form and refreshes the data when a new line is select.  I.e. the user added a line and then told us
   *that is should be matched against some other line.  based on teh leaveUserEntry param leaves whatever the user entered unaltered.
   */
  private static ActionMessages refreshObjectsInvoiceDist(HttpServletRequest request, ActionForm form,
    boolean leaveUserEntry) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    if (!verifyStoreAuthorization(session, form)) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
      return lUpdateErrors;
    }
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (sForm.getInvoice().getDistributorBusEntityData() != null &&
        sForm.getInvoice().getInvoiceDist().getBusEntityId() !=
        sForm.getInvoice().getDistributorBusEntityData().getBusEntityId()) {
      if (!Utility.isSet(sForm.getInvoice().getInvoiceDist().getErpSystemCd())) {
        //if (appUser.getUserStore().getStoreId() == sForm.getInvoice().getInvoiceDist().getInvoiceDistId()) {
    	  if (appUser.getUserStore().getStoreId() == sForm.getInvoice().getInvoiceDist().getStoreId()) {
    	  sForm.getInvoice().getInvoiceDist().setErpSystemCd(appUser.getUserStore().getErpSystemCode());
        } else {
          throw new Exception("Error mismatch ids A");
        }
      }
    }
    sForm.setRemitToAddresses(null);
    Iterator it = sForm.getInvoiceItems().iterator();
    while (it.hasNext()) {
      OrderItemDescData desc = (OrderItemDescData) it.next();
      if (Utility.isSet(desc.getOrderItemIdS())) {
        int oid = Integer.parseInt(desc.getOrderItemIdS());
        if (oid > 0 || desc.getOrderItem() == null || oid != desc.getOrderItem().getOrderItemId()) {
          OrderItemDescData newDesc = getOrderItemDescDataForOrderItemId(sForm.getInvoiceItems(), oid);
          if (newDesc != null && newDesc != desc) { //check for null and the case where I found myself (desc and newDesc are referencing the same object)
            if (newDesc.getWorkingInvoiceDistDetailData() == null) {
              newDesc.setWorkingInvoiceDistDetailData(desc.getWorkingInvoiceDistDetailData());
              newDesc.setItemQuantityS(desc.getItemQuantityS());
              newDesc.setCwCostS(desc.getCwCostS());
              if (desc.getOrderItem() == null || desc.getOrderItem().getOrderItemId() == 0) {
                it.remove();
              }
            } else {
              String erpAcctCd1 = desc.getWorkingInvoiceDistDetailData().getErpAccountCode();
              String erpAcctCd2 = newDesc.getWorkingInvoiceDistDetailData().getErpAccountCode();
              String erpAcctToUse = null;
              if (Utility.isSet(erpAcctCd1) && Utility.isSet(erpAcctCd2)) {
                //both are valid so just pick one to use
                erpAcctToUse = erpAcctCd1;
              } else if (Utility.isSet(erpAcctCd1) && !Utility.isSet(erpAcctCd2)) {
                erpAcctToUse = erpAcctCd1;
              } else {
                //either erpAcctCd2 is set and erpAcctCd1 is not, or both are empty
                erpAcctToUse = erpAcctCd2;
              }
              desc.getWorkingInvoiceDistDetailData().setErpAccountCode(erpAcctToUse);
              newDesc.getWorkingInvoiceDistDetailData().setErpAccountCode(erpAcctToUse);

              int q1 = toInt(newDesc.getItemQuantityS());
              int q2 = toInt(desc.getItemQuantityS());
              int q = q1 + q2;
              if (newDesc.getWorkingInvoiceDistDetailData().getInvoiceDistId() == 0) {
                //combine quantities
                desc.getWorkingInvoiceDistDetailData().setDistItemQuantity(q);
                combineInvoiceDistData(desc.getWorkingInvoiceDistDetailData(), newDesc.getWorkingInvoiceDistDetailData(), true);
                newDesc.setWorkingInvoiceDistDetailData(desc.getWorkingInvoiceDistDetailData());
                newDesc.setCwCostS(desc.getCwCostS());
                newDesc.setItemQuantityS(Integer.toString(q));
                desc.setWorkingInvoiceDistDetailData(null);
              } else if (desc.getWorkingInvoiceDistDetailData().getInvoiceDistId() == 0) {
                combineInvoiceDistData(newDesc.getWorkingInvoiceDistDetailData(), desc.getWorkingInvoiceDistDetailData(), true);
                newDesc.getWorkingInvoiceDistDetailData().setDistItemQuantity(q);
                desc.setWorkingInvoiceDistDetailData(null);
                //will deal with it later in the loop
              } else { //both ids are set
                //this is a problem.  There are multiple invoices that are all "valid" and have real db entries.
                desc.setOrderItem(newDesc.getOrderItem());
              }
              if (desc.getOrderItem() == null || desc.getOrderItem().getOrderItemId() == 0) {
                it.remove();
              }
            }

          }
        }
      }
    }
    if (!leaveUserEntry) {
      setInvoiceDistValuesToUI(sForm, session, request);
    } else {
      setInvoiceDistValuesToUILeaveUserValues(sForm);
    }
    updateCalculatedSalesTax(lUpdateErrors, request, sForm);
    return lUpdateErrors;
  }

  /**
   *Combines 2 invoice dist data objects.  The copyTo will recieve the data from copyFrom.  If the onlyWhenEmpty flag is set
   *the data is only copied when the data in copyTo is empty
   */
  private static void combineInvoiceDistData(InvoiceDistDetailData copyTo, InvoiceDistDetailData copyFrom,
                                             boolean onlyWhenEmpty) {
    if (!Utility.isSet(copyTo.getDistItemSkuNum()) || !onlyWhenEmpty) {
      copyTo.setDistItemSkuNum(copyFrom.getDistItemSkuNum());
    }
    if (!Utility.isSet(copyTo.getDistItemShortDesc()) || !onlyWhenEmpty) {
      copyTo.setDistItemShortDesc(copyFrom.getItemShortDesc());
    }
    if (!Utility.isSet(copyTo.getDistItemUom()) || !onlyWhenEmpty) {
      copyTo.setDistItemUom(copyFrom.getDistItemUom());
    }
    if (!Utility.isSet(copyTo.getDistItemPack()) || !onlyWhenEmpty) {
      copyTo.setDistItemPack(copyFrom.getDistItemPack());
    }
    if (copyTo.getAdjustedCost() == null || copyTo.getAdjustedCost().compareTo(ZERO) == 0 || !onlyWhenEmpty) {
      copyTo.setAdjustedCost(copyFrom.getAdjustedCost());
    }
  }

  /**
   *Reassignes the invoice from one purchase order to anouther.
   */
  public static ActionMessages reAssignInvoiceDistToPo(HttpServletRequest request, ActionForm form) throws Exception {

      ActionMessages lUpdateErrors = new ActionMessages();
      HttpSession session = request.getSession();
      StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
      InvoiceDistData inv = sForm.getInvoice().getInvoiceDist();
      PurchaseOrderData newPo = getPurchaseOrderDataFromLookup(lUpdateErrors, request, form);

      if (lUpdateErrors.size() > 0) {
          return lUpdateErrors;
      }

      if (newPo.getStoreId() != inv.getStoreId()) {
          //UI should prevent this
          throw new Exception("Error mismatch ids");
      }

      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
          throw new Exception("Without APIAccess.");
      }

      PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      poEjb.reAssignInvoicePo(inv, newPo, appUser.getUserName());
      //refresh the UI as lots can change when reassigning pos
      lUpdateErrors = fetchInvoiceDistDetail(request, form, inv.getInvoiceDistId());

      return lUpdateErrors;
  }


  /**
   *Deletes a line from the invoice.  This is easy if it was one we just added otherwise it needs to have it's status updated in the db when the
   *user later clicks on the save button.
   */
  public static ActionMessages deleteLineFromInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    int idx = 0;
    try {
      idx = Integer.parseInt(sForm.getLineToDelete());
    } catch (Exception e) {
      lUpdateErrors = new ActionMessages();
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
      return lUpdateErrors;
    }
    if (idx > sForm.getInvoiceItems().size()) {
      lUpdateErrors = new ActionMessages();
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.badRequest2"));
      return lUpdateErrors;
    }
    OrderItemDescData desc = (OrderItemDescData) sForm.getInvoiceItems().get(idx);
    if ((desc.getWorkingInvoiceDistDetailData() == null ||
         desc.getWorkingInvoiceDistDetailData().getInvoiceDistDetailId() == 0) &&
        (desc.getOrderItem() == null || desc.getOrderItem().getOrderItemId() == 0)) {
      sForm.getInvoiceItems().remove(idx);
    } else if (desc.getWorkingInvoiceDistDetailData() == null ||
               desc.getWorkingInvoiceDistDetailData().getInvoiceDistDetailId() == 0) {
      desc.setWorkingInvoiceDistDetailData(InvoiceDistDetailData.createValue());
      setInvoiceDistValuesToUIDetail(sForm.getInvoice(), desc, session);
    } else {
      desc.getWorkingInvoiceDistDetailData().setDistItemQuantity(0);
      setInvoiceDistValuesToUIDetail(sForm.getInvoice(), desc, session);
    }
    //set values so that validation of form works without having to enter values for these lines
    if (!Utility.isSet(desc.getWorkingInvoiceDistDetailData().getDistItemPack())) {
      desc.getWorkingInvoiceDistDetailData().setDistItemPack("0");
    }
    return lUpdateErrors;
  }

  /**
   *Adds a new line onto this invoice.
   */
  public static ActionMessages addLineToInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    OrderItemDescData newLine = OrderItemDescData.createValue();
    newLine.setWorkingInvoiceDistDetailData(InvoiceDistDetailData.createValue());
    setInvoiceDistValuesToUIDetail(sForm.getInvoice(), newLine, session);
    sForm.getInvoiceItems().add(newLine);
    return lUpdateErrors;
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
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = 0;
    if (sForm.getInvoice() != null) {
      if (sForm.getInvoice().getInvoiceDist() != null) {
        storeId = sForm.getInvoice().getInvoiceDist().getStoreId();
      }
      if (storeId == 0 && sForm.getInvoice().getOrderData() != null) {
        storeId = sForm.getInvoice().getOrderData().getStoreId();
      }
    }
    if (appUser.getUserStore().getStoreId() == storeId ||
        RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      return true;
    }
    return false;
  }


  /**
   *Adds a note to this invoice and updates the database accordingly;
   */
  public static ActionMessages addInvoiceDistNote(HttpServletRequest request, ActionForm form, boolean requiered) throws
    Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (sForm.getInvoice().getInvoiceDist() == null || sForm.getInvoice().getInvoiceDist().getInvoiceDistId() == 0) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.msg.saveFirst"));
    }
    if (requiered && !Utility.isSet(sForm.getNewNote())) {
     lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "Note"));
    }
    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }

    if (!Utility.isSet(sForm.getNewNote())) {
      //nothing to do
      return lUpdateErrors;
    }
    OrderPropertyData aNote = OrderPropertyData.createValue();
    aNote.setValue(sForm.getNewNote());
    aNote.setAddBy(appUser.getUserName());
    aNote.setModBy(appUser.getUserName());
    aNote.setInvoiceDistId(sForm.getInvoice().getInvoiceDist().getInvoiceDistId());
    if (sForm.getInvoice().getOrderData() != null) {
      aNote.setOrderId(sForm.getInvoice().getOrderData().getOrderId());
    } else {
      //shouldn't be much help, but ue this value just in case
      aNote.setOrderId(sForm.getInvoice().getInvoiceDist().getOrderId());
    }
    aNote.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
    aNote.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
    aNote.setShortDesc("Invoice Matching Note");

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    aNote = factory.getOrderAPI().addNote(aNote);
    sForm.getNotes().add(aNote);
    sForm.setNewNote(null);
    return lUpdateErrors;
  }

  /**
   *Rejects this invoice.  Removes any item actions and any order modifications.  Only can happen for
   *certain invoice status codes.
   */
  public static ActionMessages rejectInvoiceDist(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    String newNote = sForm.getNewNote();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    if (!sForm.isUpdateableInvoiceStatus() &&
        !RefCodeNames.INVOICE_STATUS_CD.REJECTED.equals(sForm.getInvoice().getInvoiceDist().getInvoiceStatusCd())) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.error.wrongStatus"));
    }
    if (sForm.getInvoice().getInvoiceDist() == null || sForm.getInvoice().getInvoiceDist().getInvoiceDistId() == 0) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.msg.saveFirst"));
    }
    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }
    lUpdateErrors = addInvoiceDistNote(request, form, true);
    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    InvoiceDistData newInv = factory.getPurchaseOrderAPI().rejectInvoice(sForm.getInvoice().getInvoiceDist(),
      appUser.getUserName());
 
    //String fromEmailAddress = appUser.getUserStore().getDefaultEmail().getEmailAddress() ;
    Store storeBean = factory.getStoreAPI();
    StoreData storeData = storeBean.getStore(sForm.getInvoice().getInvoiceDist().getStoreId());
    String fromEmailAddress = storeData.getDefaultEmail().getEmailAddress();
    
    if(!Utility.isSet(fromEmailAddress)){
    	fromEmailAddress = System.getProperty("mail.from");
    }
    if(!Utility.isSet(fromEmailAddress)){
    	fromEmailAddress = Constants.EMAIL_DEFAULT_FROM_ADDRESS;
    }
    String toEmailAddressStr = sForm.getRejectedInvEmail();
    
    if (Utility.isSet(toEmailAddressStr)) {
    	EmailClient emailClientEjb = factory.getEmailClientAPI();
      PurchaseOrderData poData = sForm.getInvoice().getPurchaseOrderData();
	    String outboundPO = "";
	    if(poData != null){
	    	outboundPO = poData.getOutboundPoNum();
	    }
	    Date invDate = newInv.getInvoiceDate();
	    String localeCd = sForm.getInvoice().getDistributorBusEntityData().getLocaleCd();    
	    if (localeCd == null || localeCd.equals("") || "unk".equals(localeCd)){
	      localeCd = "en_US";
	    }
	    String invDateStr ="";
	    try{
	    	invDateStr = ClwI18nUtil.formatDate(localeCd, invDate, DateFormat.SHORT);
	    }catch(Exception e){
	    	log.error("Error formatting Inventory date " + e);
	    }
	    String lSubject =  appUser.getUserStore().getBusEntity().getShortDesc() + " - INVOICE REJECTED.";
	    String lMsg= "Invoice Number:     " + Utility.strNN(newInv.getInvoiceNum()) + "\n";
	    lMsg= lMsg + "Invoice Date:       " + Utility.strNN(invDateStr) + "\n";
	    lMsg= lMsg + "Outbound PO Number: " + Utility.strNN(outboundPO) + "\n";
	    lMsg= lMsg + "Invoice Note:       " + Utility.strNN(newNote) + "\n";
	    
	   //Not doing any email validation here, since it is done while user set the email using UI. 
      String[] toEmailAddresList = toEmailAddressStr.trim().split(",");
      for (String toEmailAddres : toEmailAddresList) {
      	if(Utility.isSet(toEmailAddres)){
          emailClientEjb.send(toEmailAddres.trim(),
          		fromEmailAddress,
              lSubject, lMsg,
              Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, appUser.getUserId());
          }
      	}
    }
    
    sForm.getInvoice().setInvoiceDist(newInv);
    return lUpdateErrors;
  }

  /**
   *Markes an invoice as being manually resolved.  This means that the invoice is valid and has been fixed seperatly in
   *whatever erp system is being used.  Esentially this simply removes it as an exception invoice and will ot send it on
   *to the erp or worry about any errors that it contains.
   */
  public static ActionMessages updateInvoiceDistToManuallyResolved(HttpServletRequest request, ActionForm form) throws
    Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    if (!sForm.isUpdateableInvoiceStatus() &&
        !RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE.equals(sForm.getInvoice().getInvoiceDist().getInvoiceStatusCd())) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.error.wrongStatus"));
    }
    if (sForm.getInvoice().getInvoiceDist() == null || sForm.getInvoice().getInvoiceDist().getInvoiceDistId() == 0) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.msg.saveFirst"));
    }

    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }
    lUpdateErrors = addInvoiceDistNote(request, form, true);
    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    sForm.getInvoice().getInvoiceDist().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE);
    sForm.getInvoice().getInvoiceDist().setModBy(appUser.getUserName());
    InvoiceDistData newInv = factory.getPurchaseOrderAPI().addUpdateInvoiceDistData(sForm.getInvoice().getInvoiceDist());
    sForm.getInvoice().setInvoiceDist(newInv);
    return lUpdateErrors;
  }


  /**
   *Markes an invoice as being manually resolved.  This means that the invoice is valid and has been fixed seperatly in
   *whatever erp system is being used.  Esentially this simply removes it as an exception invoice and will ot send it on
   *to the erp or worry about any errors that it contains.
   */
  public static ActionMessages updateInvoiceDistToDistShipped(HttpServletRequest request, ActionForm form) throws
    Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    if (!RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE.equals(sForm.getInvoice().getInvoiceDist().getInvoiceStatusCd()) &&
		!RefCodeNames.INVOICE_STATUS_CD.REJECTED.equals(sForm.getInvoice().getInvoiceDist().getInvoiceStatusCd())
    		) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.error.wrongStatus"));
    }
    if (sForm.getInvoice().getInvoiceDist() == null || sForm.getInvoice().getInvoiceDist().getInvoiceDistId() == 0) {
      lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("invoice.msg.saveFirst"));
    }

    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }
    lUpdateErrors = addInvoiceDistNote(request, form, true);
    if (lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    sForm.getInvoice().getInvoiceDist().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
    sForm.getInvoice().getInvoiceDist().setModBy(appUser.getUserName());
    InvoiceDistData newInv = factory.getPurchaseOrderAPI().addUpdateInvoiceDistData(sForm.getInvoice().getInvoiceDist());
    sForm.getInvoice().setInvoiceDist(newInv);
    return lUpdateErrors;
  }


  /**
   *Creates a printable version of the distributor invoice
   */
  public static ActionMessages printInvoiceDist(HttpServletResponse response, HttpServletRequest request, ActionForm form) throws
    Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceDetailForm sForm = (StoreVendorInvoiceDetailForm) form;

    if (!verifyStoreAuthorization(session, form)) {
      return lUpdateErrors;
    }

    DistributorData dist;
    if (sForm.getInvoice().getInvoiceDist().getBusEntityId() > 0) {
      dist = SessionTool.getDistributorData(request, sForm.getInvoice().getInvoiceDist().getBusEntityId());
    } else {
      dist = null;
    }

    //sets the content type so the browser knows this is a pdf
    response.setContentType("application/pdf");
    String browser = (String)  request.getHeader("User-Agent");
    boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
    if (!isMSIE6){
  	  	response.setHeader("extension", "pdf");
  	  	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
    }
    
    ByteArrayOutputStream pdfout = new ByteArrayOutputStream();

    PdfInvoiceDist pdf = new PdfInvoiceDist();
    pdf.constructPdf(sForm.getInvoice(), sForm.getInvoiceItems(), sForm.getNotes(), dist, pdfout);

    response.setContentLength(pdfout.size());
    pdfout.writeTo(response.getOutputStream());
    response.flushBuffer();
    response.getOutputStream().close();
    return lUpdateErrors;
  }


  /**
   *Creates a printable version of the distributor invoice
   */
  public static ActionMessages printInvoiceDistList(HttpServletResponse response, HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    HttpSession session = request.getSession();
    StoreVendorInvoiceSearchForm sForm = (StoreVendorInvoiceSearchForm) form;

    if (sForm.getResultList() == null || sForm.getResultList().isEmpty()) {
      lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nothing.to.print"));
      return lUpdateErrors;
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
      OrderPropertyDataVector notes = StoreVendorInvoiceLogic.getInvoiceDistNotesForDisplay(po.getInvoiceDist().
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
    return lUpdateErrors;
  }


  private static StoreVendorInvoiceSearchForm getStoreVendorInvoiceSearchForm(HttpSession session) {
    return (StoreVendorInvoiceSearchForm) session.getAttribute("STORE_VEN_INVOICE_SEARCH_FORM");
  }

  /**
   * Fetches the next invoice from the list of invoices that the user had previously searched on
   */
  public static ActionMessages getNextInvoice(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    StoreVendorInvoiceDetailForm dForm = (StoreVendorInvoiceDetailForm) form;
    if (dForm.getNextInvoiceInList() == 0) {
      lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invoice.no.next"));
      return lUpdateErrors;
    }
    lUpdateErrors = StoreVendorInvoiceLogic.fetchInvoiceDistDetail(request, form, dForm.getNextInvoiceInList());
    if (!lUpdateErrors.isEmpty()) {
      return lUpdateErrors;
    }
    StoreVendorInvoiceLogic.updateCalculatedSalesTax(lUpdateErrors, request, form);
    StoreVendorInvoiceLogic.updateCalculatedTotal(lUpdateErrors, request, form);
    return lUpdateErrors;
  }

  /**
   * Fetches the previous invoice from the list of invoices that the user had previously searched on
   */
  public static ActionMessages getPrevInvoice(HttpServletRequest request, ActionForm form) throws Exception {
    ActionMessages lUpdateErrors = new ActionMessages();
    StoreVendorInvoiceDetailForm dForm = (StoreVendorInvoiceDetailForm) form;
    if (dForm.getPrevInvoiceInList() == 0) {
      lUpdateErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invoice.no.prev"));
      return lUpdateErrors;
    }
    lUpdateErrors = StoreVendorInvoiceLogic.fetchInvoiceDistDetail(request, form, dForm.getPrevInvoiceInList());
    if (!lUpdateErrors.isEmpty()) {
      return lUpdateErrors;
    }
    StoreVendorInvoiceLogic.updateCalculatedSalesTax(lUpdateErrors, request, form);
    StoreVendorInvoiceLogic.updateCalculatedTotal(lUpdateErrors, request, form);
    return lUpdateErrors;
  }


    public static void setPoScreenAttr(ActionForm form,boolean val) {
        StoreVendorInvoiceDetailForm detForm = (StoreVendorInvoiceDetailForm) form;
        if(detForm!=null){
            detForm.setPoScreen(val);
        }
    }
    
    // find BusEntityId on the basis of the InvoiceDistId
    public static int getBusEntityIdOffInvoiceDistId(int pInvoiceDistId) {
    	return 0;
    }
}
