package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import com.cleanwise.view.forms.ContractMgrDetailForm;
import com.cleanwise.view.forms.ContractMgrSearchForm;
import com.cleanwise.view.utils.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 * <code>ContractMgrLogic</code> implements the logic needed to
 * manipulate contract records.
 *
 * @author durval
 */
public class ContractMgrLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request, ActionForm form)
	throws Exception {

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
    public static void search(HttpServletRequest request, ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
        ContractMgrSearchForm sForm = (ContractMgrSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData userStore = appUser.getUserStore();
        Contract contractEjb = factory.getContractAPI();
        String searchField = sForm.getSearchField();
        String searchType = sForm.getSearchType();
        boolean containsFlag = sForm.isContainsFlag();
        ContractDescDataVector contracts = new ContractDescDataVector();
        //List contractList = new LinkedList();

        if (null != searchType && "id".equals(searchType) && 
            null != searchField && !"".equals(searchField)) {
            //contractList = new LinkedList();

            ContractDescData contractData = contractEjb.getContractDesc(Integer.parseInt(
											 searchField),userStore.getStoreId());

            if (null != contractData) {
                contracts.add(contractData);
            }
        } else if (null != searchType && "nameContains".equals(searchType) && 
                   null != searchField && !"".equals(searchField)) {
            contracts = contractEjb.getContractDescByName(searchField, userStore.getStoreId(),
                                                          Contract.CONTAINS_IGNORE_CASE);
            //contractList = new LinkedList();

            //for(int i = 0; i < contracts.size(); i++) {
            //    contractList.add((ContractData)contracts.elementAt(i));
            //}
            //contractList = (List)contracts;
        } else if (null != searchType && "nameBegins".equals(searchType) && 
                   null != searchField && !"".equals(searchField)) {
            contracts = contractEjb.getContractDescByName(searchField, userStore.getStoreId(),
                                                          Contract.BEGINS_WITH_IGNORE_CASE);
            //contractList = new LinkedList();

            //for(int i = 0; i < contracts.size(); i++) {
            //    contractList.add((ContractData)contracts.elementAt(i));
            //}
            //contractList = (List)contracts;
        } else {
        	contracts = contractEjb.getContractDescsByStore(userStore.getStoreId());
        	//contracts = contractEjb.getAllContractDescs();
            //contractList = new LinkedList();

            //for(int i = 0; i < contracts.size(); i++) {
            //    contractList.add((ContractData)contracts.elementAt(i));
            //}
            //contractList = (List)contracts;
        }

        String catalogIdS = request.getParameter("catalogId");

        if (null != catalogIdS && !"".equals(catalogIdS)) {

            ContractDescDataVector newContractList = new ContractDescDataVector();

            for (int i = 0; i < contracts.size(); i++) {

                int catalogId = Integer.parseInt(catalogIdS);
                ContractDescData contractD = (ContractDescData)contracts.get(
									     i);

                if (catalogId == contractD.getCatalogId()) {
                    newContractList.add(contractD);
                }
            }

            contracts = newContractList;
        }

        ContractDescDataVector finalList = new ContractDescDataVector();
        for (int i = 0; i < contracts.size(); i++) {

            ContractDescData contractD = (ContractDescData)contracts.get(i);

            if ( ! contractD.getStatus().equals(
						RefCodeNames.CONTRACT_STATUS_CD.DELETED)
		 ) {

                // Only display contracts which have not been
                // deleted.  (request from paula and andy)
                finalList.add(contractD);
            }
        }

        sForm.setResultList(finalList);
        sForm.setSearchField(searchField);
        sForm.setSearchType(searchType);
    }

    /**
     * <code>searchAll</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request, ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
        ContractMgrSearchForm sForm = (ContractMgrSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData userStore = appUser.getUserStore();
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Contract contractEjb = factory.getContractAPI();
        ContractDescDataVector contracts = new ContractDescDataVector();
        List contractList = new LinkedList();
      //  contracts = contractEjb.getAllContractDescs();
        contracts = contractEjb.getContractDescsByStore(userStore.getStoreId());
        contractList = new LinkedList();
        contractList = (List)contracts;

        String catalogIdS = request.getParameter("catalogId");

        if (null != catalogIdS && !"".equals(catalogIdS)) {

            List newContractList = new LinkedList();

            for (int i = 0; i < contractList.size(); i++) {

                int catalogId = Integer.parseInt(catalogIdS);
                ContractDescData contractD = (ContractDescData)contractList.get(
										i);

                if (catalogId == contractD.getCatalogId()) {
                    newContractList.add(contractD);
                }
            }

            contractList = newContractList;
        }

        sForm.setResultList(contractList);
    }

    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request, ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        ContractMgrSearchForm sForm = (ContractMgrSearchForm)form;

        if (sForm == null) {
            return;
        }

	try {
	    ContractDescDataVector contracts = (ContractDescDataVector)sForm.getResultList();

	    if (contracts == null) {
		return;
	    }

	    String sortField = request.getParameter("sortField");
	    DisplayListSort.sort(contracts, sortField);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     *  <code>sortItems</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItems(HttpServletRequest request, ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
        ContractMgrDetailForm sForm = (ContractMgrDetailForm)session.getAttribute(
										  "CONTRACT_DETAIL_FORM");

        if (sForm == null) {

            // not expecting this, but nothing to do if it is
            return;
        }

        ContractItemDescDataVector contractItems = (ContractItemDescDataVector)sForm.getItemsDetailCollection();
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(contractItems, sortField);
        initCostInputArrays(request, form);
    }

    /**
     *  <code>sortCatalogItems</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortCatalogItems(HttpServletRequest request, 
                                        ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
        ContractMgrDetailForm sForm = (ContractMgrDetailForm)session.getAttribute(
										  "CONTRACT_DETAIL_FORM");

        if (sForm == null) {
            return;
        }

        ContractItemDescDataVector catalogItems = (ContractItemDescDataVector)sForm.getCatalogItemsCollection();
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(catalogItems, sortField);

        // this is request scoped, need to set
        request.setAttribute("CONTRACT_DETAIL_FORM", sForm);
    }

    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
    public static void initConstantList(HttpServletRequest request)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("Contract.status.vector")) {

            RefCdDataVector statusv = 
		listServiceEjb.getRefCodesCollection
		("CONTRACT_STATUS_CD");
            session.setAttribute("Contract.status.vector", statusv);
        }

        FreightTable freightTableEjb = factory.getFreightTableAPI();
        FreightTableDataVector freightTableList = freightTableEjb.getAllFreightTables();
        session.setAttribute("FreightTable.vector", freightTableList);
    }

    /**
     * <code>AddContract</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void addContract(HttpServletRequest request, ActionForm form)
	throws Exception {

        ContractMgrDetailForm contract = (ContractMgrDetailForm)form;
        contract = new ContractMgrDetailForm();

        HttpSession session = request.getSession();
        session.setAttribute("CONTRACT_DETAIL_FORM", contract);
        session.removeAttribute("Contract.id");

        //initialize the comstants lists for states and contries
        initConstantList(request);
    }

    /**
     * <code>editContract</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param contractId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void editContract(HttpServletRequest request, 
                                    ActionForm form, String contractId)
	throws Exception {
Date dateSt = new Date();        

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Contract contractEjb = factory.getContractAPI();

        if (null == contractId || "".equals(contractId)) {
            contractId = (String)session.getAttribute("Contract.id");
        }
Date dateReqSt = new Date();        
        ContractData detail = contractEjb.getContract(Integer.parseInt(
								       contractId));
        ContractMgrDetailForm detailForm = (ContractMgrDetailForm)form;
        detailForm.setDetail(detail);

        if (0 == detail.getFreightTableId()) {
            detailForm.setFreightTableId("");
            detailForm.setCurrentFreightTable(null);
            detailForm.setCurrentFreightTableCriteria(null);
        } else {
            detailForm.setFreightTableId(String.valueOf(detail.getFreightTableId()));

            FreightTable freightTableEjb = factory.getFreightTableAPI();
            FreightTableData currentFreightTable = freightTableEjb.getFreightTable(detail.getFreightTableId());
            detailForm.setCurrentFreightTable(currentFreightTable);

            FreightTableCriteriaDescDataVector criterias = freightTableEjb.getAllFreightTableCriteriaDescs(detail.getFreightTableId());
            detailForm.setCurrentFreightTableCriteria(criterias);
        }

        session.setAttribute("Contract.id", contractId);

        CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
        int catalogId = detail.getCatalogId();
dateReqSt = new Date();
        CatalogData catalogData = catalogInfoEjb.getCatalog(catalogId);
        detailForm.setCatalogId(""+catalogId);
        detailForm.setCatalogName(catalogData.getShortDesc());
dateReqSt = new Date();
        BusEntityDataVector storeV = catalogInfoEjb.getStoreCollection(
								       catalogId);

        if (storeV.size() > 0) {

            BusEntityData storeD = (BusEntityData)storeV.get(0);
            detailForm.setStoreId(storeD.getBusEntityId());
            detailForm.setStoreName(storeD.getShortDesc());
        } else {
            detailForm.setStoreId(0);
            detailForm.setStoreName("");
        }

        loadContractItems(detailForm,contractEjb, Integer.parseInt(contractId));

        //initialize the comstants lists for states and countries
dateReqSt = new Date();
        initConstantList(request);
dateReqSt = new Date();
        initCostInputArrays(request, form);
    }
    //----------------------------------------------------------------
    private static void loadContractItems (ContractMgrDetailForm detailForm, 
       Contract contractEjb, int contractId)
      throws Exception 
    {
Date dateReqSt = new Date();        
        ContractItemDescDataVector contractItemsAll = 
           contractEjb.getContractItemsDesc(contractId,false);

        ContractItemDescDataVector contractItemsDesc = 
           new ContractItemDescDataVector();
        ContractItemDescDataVector nonCatalogItems = 
           new ContractItemDescDataVector();

        for(int ii=0; ii<contractItemsAll.size(); ii++) {
           ContractItemDescData cidD = (ContractItemDescData) contractItemsAll.get(ii);
           if(cidD.getCatalogId()==0) {
             nonCatalogItems.add(cidD);
           } else {
             contractItemsDesc.add(cidD);
           }
        }
dateReqSt = new Date();                
        DisplayListSort.sort(contractItemsDesc, "cwSKU");
        detailForm.setItemsDetailCollection(contractItemsDesc);
        DisplayListSort.sort(nonCatalogItems, "cwSKU");
        detailForm.setNonCatalogItems(nonCatalogItems);
    }

 private static BigDecimal calcDuration(java.util.Date pFinish, java.util.Date pStart) {
   long duration = pFinish.getTime()-pStart.getTime();
   BigDecimal durBD = new BigDecimal(duration);
   durBD = durBD.multiply(new BigDecimal(.001));
   durBD.setScale(2,BigDecimal.ROUND_HALF_UP);
   return durBD;
}
    
    /**
     * <code>saveContract</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors saveContract(HttpServletRequest request, 
                                            ActionForm form)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        ContractMgrDetailForm detailForm = (ContractMgrDetailForm)form;
        int contractId = detailForm.getDetail().getContractId();
        boolean reloadFl = false;
        // Get a reference to the admin facade.
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Contract contractEjb = factory.getContractAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        String freightTableId = detailForm.getFreightTableId();
        ContractData detail = detailForm.getDetail();

        if (null == freightTableId || "".equals(freightTableId)) {
            detail.setFreightTableId(0);
        } else {
            detail.setFreightTableId(Integer.parseInt(freightTableId));
        }

        if (0 != detail.getContractId()) {
            detail.setRefContractNum("0");
            detail.setLocaleCd("en_US");
            int catalogIdOld = detail.getCatalogId();
            int catalogIdNew = 0;
            try {
              catalogIdNew = Integer.parseInt(detailForm.getCatalogId());
            }catch(Exception exc) {}
            if(catalogIdNew<=0) {
              String mess = "Wrong catalog id: "+detailForm.getCatalogId();
              lUpdateErrors.add("error", new ActionError("error.simpleGenericError",mess));
              return lUpdateErrors;
            }
            if(catalogIdNew!=catalogIdOld) {
              try {
              CatalogData catalogD = catalogInfEjb.getCatalog(catalogIdNew);
              } catch(DataNotFoundException exc) {
                String mess = "Catalog does not exist: "+catalogIdNew;
                lUpdateErrors.add("error", new ActionError("error.simpleGenericError",mess));
                return lUpdateErrors;
              }
              detail.setCatalogId(catalogIdNew);
              reloadFl = true;
            }
            contractEjb.addContract(detail);
        } else {

            String createFrom = detailForm.getCreateFrom();

            if (null == detail.getContractTypeCd() || 
                "".equals(detail.getContractTypeCd())) {
                detail.setContractTypeCd("UNKNOWN");
            }

            detail.setRefContractNum("0");
            detail.setLocaleCd("en_US");

            ContractData newContract = ContractData.createValue();

            if ("Contract".equals(createFrom)) {

                String parentContractId = detailForm.getParentContractId();
                ContractData parentContract = contractEjb.getContract(Integer.parseInt(
										       parentContractId));

                if (null == parentContract) {
                    lUpdateErrors.add("contract", 
                                      new ActionError("contract.bad.contract"));

                    return lUpdateErrors;
                }

                ContractData parentContractData = contractEjb.getContract(Integer.parseInt(
											   parentContractId));
                detail.setCatalogId(parentContractData.getCatalogId());
                newContract = contractEjb.createFromContract(detail, 
                                                             Integer.parseInt(
									      parentContractId));
            } else {
                detail.setCatalogId(Integer.parseInt(detailForm.getCatalogId()));

                CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
                CatalogData parentCatalog = null;

                try {
                    parentCatalog = catalogInfoEjb.getCatalog(Integer.parseInt(detailForm.getCatalogId()));
                } catch (DataNotFoundException exc) {
                    parentCatalog = null;
                }

                if (null == parentCatalog) {
                    lUpdateErrors.add("contract", 
                                      new ActionError("contract.bad.catalog"));

                    return lUpdateErrors;
                }

                newContract = contractEjb.createFromCatalog(detail);
            }

            session.setAttribute("Contract.id", 
                                 String.valueOf(newContract.getContractId()));
        }
        if(reloadFl) {
          loadContractItems(detailForm,contractEjb, contractId);
        }
        detailForm.setCatalogId(""+detail.getCatalogId());
        return lUpdateErrors;
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors updateItems(HttpServletRequest request, 
                                           ActionForm form)
	throws Exception {
        ContractMgrDetailForm pForm = (ContractMgrDetailForm)form;
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        ActionErrors ae = new ActionErrors();
        ContractData contractD = pForm.getDetail();
        int contactId = contractD.getContractId();
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        String locale = contractD.getLocaleCd();
        
        if (factory == null) {
            ae.add("error", 
                   new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        Contract contractEjb = null;
        com.cleanwise.service.api.session.Currency currencyEjb = null;

        try {
            contractEjb = factory.getContractAPI();
        } catch (APIServiceAccessException exc) {
            ae.add("error", 
                   new ActionError("error.systemError", 
                                   "No Contract Ejb access"));

            return ae;
        }
        
        try {
        	currencyEjb = factory.getCurrencyAPI();
        } catch (APIServiceAccessException exc) {
            ae.add("error", 
                   new ActionError("error.systemError", 
                                   "No Currency Ejb access"));

            return ae;
        }
        
        int decimalPlaces = currencyEjb.getDecimalPlacesForLocale(locale);

        //
        ArrayList contractItemDescL = pForm.getItemsDetailCollection();

        if (contractItemDescL == null) {

            return ae;
        }

        String[] distCosts = pForm.getDistCosts();
        String[] distBaseCosts = pForm.getDistBaseCosts();
        String[] amounts = pForm.getAmounts();
        String[] inputIds = pForm.getInputIds();
        LinkedList itemsToSave = new LinkedList();

        if (inputIds.length < contractItemDescL.size()) {
            ae.add("error", 
                   new ActionError("error.simpleGenericError", 
                                   "Submited information does not match stored items. Probably old page was used"));

            return ae;
        }

        for (int ii = 0; ii < contractItemDescL.size(); ii++) {

            ContractItemDescData cidD = (ContractItemDescData)contractItemDescL.get(
										    ii);
            String inputId = "" + cidD.getContractItemId();

            if (inputIds[ii] == null || inputIds[ii].trim().length() == 0) {

                continue;
            }

            if (!inputId.equals(inputIds[ii])) {
                ae.add("error", 
                       new ActionError("error.simpleGenericError", 
                                       "Submited information does not match stored items. Probably old page was used"));

                return ae;
            }

            String distCostS = distCosts[ii];
            BigDecimal distCost = null;
            try {
                distCost = new BigDecimal(distCostS);
            } catch (Exception exc) {}

            if (distCost == null) {
                ae.add("error", 
                       new ActionError("error.simpleGenericError", 
                                       "Wrong distributor cost format: " + distCostS + 
				       " Item id: " + cidD.getItemId() + 
				       " Contract: " + 
				       contractD.getShortDesc()));
            }
            
            //check allowed number of digits after decimal pt for locale
            if(distCost.scale()>decimalPlaces){
            	String errorMess = "The dist cost for item id "+cidD.getItemId()+" has too many decimal points.  " +
    	  		"It can only have "+decimalPlaces+" decimal points for this currency";
            	ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }

            String distBaseCostS = distBaseCosts[ii];
            BigDecimal distBaseCost = null;
            try {
                distBaseCost = new BigDecimal(distBaseCostS);
            } catch (Exception exc) {}

            if (distBaseCost == null) {
                ae.add("error", 
                       new ActionError("error.simpleGenericError", 
                                       "Wrong distributor base cost format: " + distBaseCostS + 
				       " Item id: " + cidD.getItemId() + 
				       " Contract: " + 
				       contractD.getShortDesc()));
            }
            
            //check allowed number of digits after decimal pt for locale
            if(distBaseCost.scale()>decimalPlaces){
            	String errorMess = "The dist base cost for item id "+cidD.getItemId()+" has too many decimal points.  " +
    	  		"It can only have "+decimalPlaces+" decimal points for this currency";
            	ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
            
            String amountS = amounts[ii];
            BigDecimal amount = null;

            try {
                amount = new BigDecimal(amountS);
            } catch (Exception exc) {
            }

            if (amount == null) {
                ae.add("error", 
                       new ActionError("error.simpleGenericError", 
                                       "Wrong amount format: " + amountS + 
				       " Item id: " + cidD.getItemId() + 
				       " Contract: " + 
				       contractD.getShortDesc()));
            }
            
            //check allowed number of digits after decimal pt for locale
            if(amount.scale()>decimalPlaces){
            	String errorMess = "The amount for item id "+cidD.getItemId()+" has too many decimal points.  " +
    	  		"It can only have "+decimalPlaces+" decimal points for this currency";
            	ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }

            if (ae.size() == 0 && amount != null && distCost != null && 
                (!amount.equals(cidD.getAmount()) || 
		 !distCost.equals(cidD.getDistributorCost()) ||
                 !distBaseCost.equals(cidD.getDistributorBaseCost()))) {
                cidD.setAmount(amount);
                cidD.setDistributorCost(distCost);
                cidD.setDistributorBaseCost(distBaseCost);
                itemsToSave.add(cidD);
            }
        }

        if (ae.size() > 0) {

            return ae;
        }

        for (int ii = 0; ii < itemsToSave.size(); ii++) {

            try {

                ContractItemDescData cidD = (ContractItemDescData)itemsToSave.get(
										  ii);
                contractEjb.updateItem(cidD, user);
            } catch (java.rmi.RemoteException exc) {
                ae.add("error", 
                       new ActionError("error.systemError", 
                                       "Error update contract item: " + exc.getMessage()));

                return ae;
            }
        }

        pForm.setItemsDetailCollection(contractItemDescL);
        initCostInputArrays(request, pForm);

        return ae;

        /////////////////////////////////////

        /*
        
	try {
	ContractData fcd = null;
	ContractMgrDetailForm sForm =
	(ContractMgrDetailForm)form;
        
	if (sForm != null) {
	fcd = sForm.getDetail();
	}
        
	if (fcd == null || lErrors.size() > 0) {
	// Report the errors to allow for edits.
	return lErrors;
	}
        
	String cuser = (String) session.getAttribute(Constants.USER_NAME);
	APIAccess factory = new APIAccess();
	Contract contractEjb = factory.getContractAPI();
        
	int id = fcd.getContractId();
        
	// Get the updated quantities.
	ArrayList idv = sForm.getItemsDetailCollection();
	ArrayList idvorig = sForm.getItemList();
        
	for (int j = 0; j < idv.size(); j++) {
	ContractItemDescData newo = (ContractItemDescData) idv.get(j);
	if (newo == null) continue;
        
	for (int j2 = 0; j2 < idvorig.size(); j2++) {
	ContractItemData oldo = (ContractItemData) idvorig.get(j2);
	if ( oldo == null ) continue;
        
	if (newo.getContractItemId() == oldo.getContractItemId() ) {
        
	boolean makeupdate = false;
        
	java.math.BigDecimal oldAmount = oldo.getAmount();
	if ( null == oldAmount ) {
	oldAmount = new java.math.BigDecimal(0);
	}
        
	if (newo.getAmount().compareTo(oldAmount) != 0 ) {
	oldo.setAmount(newo.getAmount());
	makeupdate = true;
	}
        
	java.math.BigDecimal oldDistCost = oldo.getDistCost();
	if ( null == oldDistCost ) {
	oldDistCost = new java.math.BigDecimal(0);
	}
        
	if (newo.getDistributorCost().compareTo
	(oldDistCost) != 0 ) {
	oldo.setDistCost(newo.getDistributorCost());
	makeupdate = true;
	}
        
	if ( makeupdate ) {
	contractEjb.updateItem
	(oldo.getContractItemId(), oldo, cuser);
	}
	}
        
	}  // j2 for loop (inner loop)
        
	} // j for loop (outer loop)
        
	}
	catch (Exception e) {
	lErrors.add("contract",
	new ActionError("Update failed. " + e));
	}
        */
    }

    /**
     * Describe <code>findItems</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors findItems(HttpServletRequest request, 
                                         ActionForm form)
	throws Exception {
        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        ContractData fcd = null;
        ContractMgrDetailForm sForm = (ContractMgrDetailForm)session.getAttribute(
										  "CONTRACT_DETAIL_FORM");

        if (sForm != null) {
            fcd = sForm.getDetail();
        }

        if (fcd == null || lErrors.size() > 0) {

            // Report the errors to allow for edits.
            return lErrors;
        }

        String cuser = (String)session.getAttribute(Constants.USER_NAME);
        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();
        int id = fcd.getContractId();

        ContractItemDescDataVector catalogItems = contractEjb.getCatalogItems(
									      id);
        sForm.setCatalogItemsCollection(catalogItems);
        request.setAttribute("CONTRACT_DETAIL_FORM", sForm);

        return lErrors;
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors removeItems(HttpServletRequest request, 
                                           ActionForm form)
	throws Exception {

        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        ContractMgrDetailForm sForm = (ContractMgrDetailForm)form;
        ContractData fcd = null;

        if (sForm == null) {
            sForm = (ContractMgrDetailForm)session.getAttribute(
								"CONTRACT_DETAIL_FORM");
        }

        if (sForm != null) {
            fcd = sForm.getDetail();
        }

        if (fcd == null || lErrors.size() > 0) {

            // Report the errors to allow for edits.
            return lErrors;
        }

        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();
        String[] itemstorm = sForm.getSelectItems();
        int contractId = fcd.getContractId();

        for (int i = 0; i < itemstorm.length; i++) {
            contractEjb.removeItem(Integer.parseInt(itemstorm[i]));
        }

        //OrderGuideInfoData ogd = ogBean.getOrderGuideInfo(id);
        //sForm.setOrderGuideInfoData(ogd);
        //session.setAttribute("ORDER_GUIDES_DETAIL_FORM", sForm);
        return lErrors;
    }

 ////////////////
    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors removeNonCatalogItems(HttpServletRequest request, 
                                           ActionForm form)
	throws Exception {

        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        ContractMgrDetailForm sForm = (ContractMgrDetailForm)form;
        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();

        String[] itemToDelA = sForm.getSelectItems();
        if(itemToDelA==null || itemToDelA.length==0) {
          lErrors.add("error", new ActionError("error.simpleGenericError",
                   "Nothing Selected"));
          return lErrors;
        }

        ContractItemDescDataVector cidDV = sForm.getNonCatalogItems();
        for(int ii=0; ii<cidDV.size(); ii++) {
          ContractItemDescData cidD = (ContractItemDescData) cidDV.get(ii);
          int contractItemId = cidD.getContractItemId();
          for(int jj=0; jj<itemToDelA.length; jj++){
            try {
              int idToDel = Integer.parseInt(itemToDelA[jj]);
              if(idToDel==contractItemId) {
                contractEjb.removeItem(idToDel);
                cidDV.remove(ii);
                ii--;
                break;
              }
            }catch(Exception exc) {
              lErrors.add("error"+ii, new ActionError("error.simpleGenericError",
                   "Wrong contract item id format"+itemToDelA[jj]));
            }
          }
        }
        return lErrors;
    }
    //////////////////
    /**
     * Describe <code>addItems</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors addItems(HttpServletRequest request, 
                                        ActionForm form)
	throws Exception {

        ActionErrors lErrors = new ActionErrors();
        HttpSession session = request.getSession();
        ContractMgrDetailForm sForm = (ContractMgrDetailForm)form;
        ContractData fcd = null;

        if (sForm == null) {
            sForm = (ContractMgrDetailForm)session.getAttribute(
								"CONTRACT_DETAIL_FORM");
        }

        if (sForm != null) {
            fcd = sForm.getDetail();
        }

        if (fcd == null || lErrors.size() > 0) {

            // Report the errors to allow for edits.
            return lErrors;
        }

        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();
        String[] itemstoadd = sForm.getSelectItems();
        int id = fcd.getContractId();

        for (int i = 0; i < itemstoadd.length; i++) {
            contractEjb.addItem(id, Integer.parseInt(itemstoadd[i]));
        }

        return lErrors;
    }

    //----------------------------------------------------------------------------
    private static void initCostInputArrays(HttpServletRequest request, 
                                            ActionForm form) {

        ContractMgrDetailForm pForm = (ContractMgrDetailForm)form;
        List itemDescL = pForm.getItemsDetailCollection();

        if (itemDescL == null || itemDescL.size() == 0) {
            pForm.setInputIds(new String[0]);
            pForm.setDistCosts(new String[0]);
            pForm.setDistBaseCosts(new String[0]);
            pForm.setAmounts(new String[0]);
        } else {

            int size = itemDescL.size();
            String[] inputIds = new String[size];
            String[] distCosts = new String[size];
            String[] distBaseCosts = new String[size];
            String[] amounts = new String[size];

            for (int ii = 0; ii < size; ii++) {
                inputIds[ii] = "";

                ContractItemDescData cidD = (ContractItemDescData)itemDescL.get(
										ii);
                distCosts[ii] = cidD.getDistributorCost().toString();
                distBaseCosts[ii] = cidD.getDistributorBaseCost().toString();
                amounts[ii] = cidD.getAmount().toString();
            }

            pForm.setInputIds(inputIds);
            pForm.setDistCosts(distCosts);
            pForm.setDistBaseCosts(distBaseCosts);
            pForm.setAmounts(amounts);
        }
    }

    /**
     * Describe <code>removeContract</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors removeContract(HttpServletRequest request, 
                                              ActionForm form)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        ContractMgrDetailForm detailForm = (ContractMgrDetailForm)form;
        int contractId = detailForm.getDetail().getContractId();

        // Get a reference to the admin facade.
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(
							    Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        try {

            Contract contractEjb = factory.getContractAPI();
            ContractData detail = detailForm.getDetail();

            if (0 != detail.getContractId()) {
                detail.setRefContractNum("0");
                detail.setLocaleCd("en_US");
                contractEjb.removeContract(detail);
            }
        } catch (Exception e) {
            lUpdateErrors.add("error", 
                              new ActionError("error.systemError", 
                                              "Contract not deleted: " + e));
        }

        return lUpdateErrors;
    }
    //-----------------------------------------------------------------------------------
    public static ActionErrors verifyItems(HttpServletRequest request, 
                                           ActionForm form)
      throws Exception {
      ActionErrors ae = new ActionErrors();
      ContractMgrDetailForm detailForm = (ContractMgrDetailForm)form;
      int contractId = detailForm.getDetail().getContractId();

      // Get a reference to the admin facade.
      HttpSession session = request.getSession();
      Contract contractEjb = null;
      try {
        APIAccess factory = 
               (APIAccess)session.getAttribute(Constants.APIACCESS);
        contractEjb = factory.getContractAPI();
      } catch(Exception exc) {
         throw new Exception("No API access. "+exc.getMessage());
      }
      try {
        IdVector extraItems = contractEjb.getNonCatalogItems(contractId);
        if(extraItems.size()>0) {
          //ContractItemDescDataVector contractItems = (ContractItemDescDataVector)sForm.getItemsDetailCollection();
          String[] itemsToSelect = new String[extraItems.size()];
          for(int ii=0; ii<extraItems.size(); ii++) {
            Integer idI = (Integer) extraItems.get(ii);
            itemsToSelect[ii]=idI.toString();
          }
          detailForm.setSelectItems(itemsToSelect);
          
        }
        
      } catch(Exception exc) {
        String msg = exc.getMessage();
        ae.add("error", new ActionError("error.simpleGenericError",msg));
      }
      return ae;
    }
}
