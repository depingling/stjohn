package com.cleanwise.view.logic;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;

/**
 *  <code>StoreFhMgrLogic</code> implements the logic needed to manipulate
 *  purchasing site records.
 *
 *@author     dbelyakov
 *@created    11/19/2007
 */
public class StoreFhMgrLogic {

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initFormVectors(HttpServletRequest request)
        throws Exception {

        HttpSession session = request.getSession();

        // Cache the lists needed for Sites.
        APIAccess factory = new APIAccess();

        ListService lsvc = factory.getListServiceAPI();
    
    	if (session.getAttribute("Dist.status.vector") == null) {
    	    RefCdDataVector statusv =
                    lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
    	    session.setAttribute("Dist.status.vector", statusv);
    	}
    
    	if (session.getAttribute("countries.vector") == null) {
    	    Country countryBean = factory.getCountryAPI();
            CountryDataVector countriesv = countryBean.getAllCountries();
            session.setAttribute("countries.vector", countriesv);
    	}
    
    	if (session.getAttribute("Dist.type.vector") == null) {
    	    RefCdDataVector dtypev =
                    lsvc.getRefCodesCollection("DISTRIBUTOR_TYPE_CD");
    	    session.setAttribute("Dist.type.vector", dtypev);
    	}
    
        if (session.getAttribute("RECEIVING_SYSTEM_INVOICE_CD") == null) {
    	    RefCdDataVector refcds =
                    lsvc.getRefCodesCollection("RECEIVING_SYSTEM_INVOICE_CD");
    	    session.setAttribute("RECEIVING_SYSTEM_INVOICE_CD", refcds);
    	}
    }
    
    /**
     *  <code>init</code> method.     
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        initFormVectors(request);
        return;
    }


    /**
     *  <code>searchFreightHandlers</code>
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void searchFreightHandlers(HttpServletRequest request, ActionForm form) throws Exception {

        getFreightHandlers(request, form);
        
    }

    public static ActionErrors getFreightHandlers(HttpServletRequest request,ActionForm form)
     {
         HttpSession session = request.getSession();
         session.removeAttribute("freight_handler.vector");
         request.removeAttribute("freight_handler.vector");
         ActionErrors ae = new ActionErrors();
         LocateStoreFhForm sForm = (LocateStoreFhForm) form;
         FreightHandlerViewVector fhv = null;
         try {
             APIAccess factory = new APIAccess();
             Distributor distBean = factory.getDistributorAPI();


             if(!Utility.isSet(sForm.getSearchType())){
                 ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("variable.empty.error","Search Type"));
             }
             if(!Utility.isSet(sForm.getSearchField())){
                 ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("variable.empty.error","Search Field"));
             }

             BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
             CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
             if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                 crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
             }
	     if(sForm.getSearchType().equals("id")){
                try{
                    int lId = Integer.parseInt(sForm.getSearchField());
                    crit.setSearchId(sForm.getSearchField());
                }catch(NumberFormatException e){
                    ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Search Field"));                    
                }
             }else if(sForm.getSearchType().equals("nameBegins")){
                 crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                 crit.setSearchName(sForm.getSearchField());
             }else if(sForm.getSearchType().equals("nameContains")){
                 crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                 crit.setSearchName(sForm.getSearchField());
             }

	     crit.setSearchForInactive(sForm.getShowInactiveFl());
             crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
             fhv = distBean.getFreightHandlersByCriteria(crit);

             if(fhv == null){
                 fhv = new FreightHandlerViewVector();
             }

             session.setAttribute("freight_handler.vector", fhv);
         }
         catch (Exception e) {
             ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError",e.getMessage()));
             e.printStackTrace();
         }
         return ae;
    }

    public static ActionErrors getAllFreightHandlers(HttpServletRequest request, ActionForm form)
     {
         HttpSession session = request.getSession();
         session.removeAttribute("freight_handler.vector");
         request.removeAttribute("freight_handler.vector");
         ActionErrors ae = new ActionErrors();
         try {
             APIAccess factory = new APIAccess();
             Distributor distBean = factory.getDistributorAPI();
             BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
             CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
             if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                 crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
             }
             crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
             FreightHandlerViewVector fhv = distBean.getFreightHandlersByCriteria(crit);

             if (fhv == null) { 
		fhv = new FreightHandlerViewVector(); 
             }
             session.setAttribute("freight_handler.vector", fhv);
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         return ae;
    }

    public static ActionErrors createNewFreightHandler (HttpServletRequest request, ActionForm form)
     {
         HttpSession session = request.getSession();
         ActionErrors ae = new ActionErrors();

	     try {
    	     initFormVectors(request);
    	     FhMgrDetailForm sForm = new FhMgrDetailForm();
    	     sForm.setId("0");
    	     sForm.setName("");
    	     session.setAttribute("FH_DETAIL_FORM", sForm);

	    } catch (Exception e ) {
	      ae.add("distributor",
		   new ActionError
		   ("error.simpleGenericError", "error:" + e.getMessage()));
	 }
         return ae;
    }

    public static ActionErrors saveFreightHandler (HttpServletRequest request, ActionForm form) {
        FhMgrDetailForm sForm = (FhMgrDetailForm)form;
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
            initFormVectors(request);
            FreightHandlerViewVector fhv = (FreightHandlerViewVector)session.getAttribute("freight_handler.vector");
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();
            int storeId = 0;

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
             if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                storeId = appUser.getUserStore().getStoreId();
             }else{
                try{
                    storeId = Integer.parseInt(sForm.getStoreId());
                }catch(NumberFormatException e){
                    ae.add("storeId", new ActionError("error.invalidAmount","storeId"));
                }
             }
             
            if(!Utility.isSet(sForm.getStatusCd())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'Status'"));
            }
            if(!Utility.isSet(sForm.getName())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'Name'"));
            }
            if(!Utility.isSet(sForm.getStreetAddr1())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'Street Address 1'"));
            }
            if(!Utility.isSet(sForm.getCity())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'City'"));
            }
            if(!Utility.isSet(sForm.getCountry())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'Country'"));
            }
            if(!Utility.isSet(sForm.getStateOrProv())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'State/Province'"));
            }
            if(!Utility.isSet(sForm.getPostalCode())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'Zip/Postal Code'"));
            }
            if(!Utility.isSet(sForm.getAcceptFreightOnInvoice())) {
                ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("variable.empty.error", "'Accept Freight On Invoice'"));
            }
            if (ae.size() > 0) {
                return ae;
            }
            
            int fhid = Integer.parseInt(sForm.getId());
            if (fhv == null) {
              fhv = new FreightHandlerViewVector();
              session.setAttribute("freight_handler.vector", fhv);
            }
              
            if(fhid == 0){
                FreightHandlerView fh = FreightHandlerView.createValue();
                fhv.add(fh);
                fh.setBusEntityData(BusEntityData.createValue());
                fh.getBusEntityData().setAddBy(appUser.getUserName());
                fh.getBusEntityData().setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.FREIGHT_HANDLER);
                fh.getBusEntityData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
                fh.getBusEntityData().setLocaleCd(appUser.getUserStore().getBusEntity().getLocaleCd());
            }
            Iterator itr = fhv.iterator();
            while (itr.hasNext()) {
                FreightHandlerView fh = (FreightHandlerView) itr.next();
                if ( fh.getBusEntityData().getBusEntityId() == fhid ) {
                    fh.getBusEntityData().setShortDesc(sForm.getName());
                    fh.getBusEntityData().setBusEntityStatusCd(sForm.getStatusCd());
                    if(fh.getPrimaryAddress() == null){
                        fh.setPrimaryAddress(AddressData.createValue());
                    }
                    fh.getPrimaryAddress().setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                    fh.getPrimaryAddress().setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
                    fh.getPrimaryAddress().setAddress1(sForm.getStreetAddr1());
                    fh.getPrimaryAddress().setAddress2(sForm.getStreetAddr2());
                    fh.getPrimaryAddress().setAddress3(sForm.getStreetAddr3());
                    fh.getPrimaryAddress().setAddress4(sForm.getStreetAddr4());
                    fh.getPrimaryAddress().setCity(sForm.getCity());
                    fh.getPrimaryAddress().setPostalCode(sForm.getPostalCode());
                    fh.getPrimaryAddress().setCountryCd(sForm.getCountry());
                    fh.getPrimaryAddress().setStateProvinceCd(sForm.getStateOrProv());
                    fh.setEdiRoutingCd(sForm.getEdiRoutingCd());
		             fh.setAcceptFreightOnInvoice
			         (sForm.getAcceptFreightOnInvoice());
                    if(fh.getStoreId() == 0){
                        fh.setStoreId(storeId);
                    }
                    fh = distBean.saveFreightHandler(fh);
                    sForm.setId(fh.getBusEntityData().getBusEntityId());
                    fetchFreightHandlerDetail(request, form);
                    //remove old and add new one to make sure our lists in sync
                    itr.remove();
                }
            }
        } catch (Exception e ) {
            e.printStackTrace();
            ae.add("distributor",
                    new ActionError
                    ("error.simpleGenericError", "error:" + e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors fetchFreightHandlerDetail (HttpServletRequest request, ActionForm form)
     {
         HttpSession session = request.getSession();
         ActionErrors ae = new ActionErrors();
	     try {
    	     initFormVectors(request);
    	     String rid = request.getParameter("searchField");
    	     if ( rid == null ) {
    		 ae.add("distributor",
    			new ActionError
    			("error.simpleGenericError", "error: id missing"));
    		 return ae;
    	     }
    	     int fhid = Integer.parseInt(rid);
    	     FreightHandlerViewVector fhv = (FreightHandlerViewVector)	session.getAttribute("freight_handler.vector");
    	     if ( null == fhv) {
    		 ae.add("distributor",
    			new ActionError
    			("error.simpleGenericError", "error: no freight handlers"));
    		 return ae;
    	     }
    	     Iterator itr = fhv.iterator();
    	     while (itr.hasNext()) {
    		     FreightHandlerView fh = (FreightHandlerView) itr.next();
                     if ( fh.getBusEntityData().getBusEntityId() == fhid ) {
    		     FhMgrDetailForm sForm = (FhMgrDetailForm) form;
    		     sForm.setId(rid);
    		     sForm.setName(fh.getBusEntityData().getShortDesc());
    		     sForm.setStatusCd(fh.getBusEntityData().getBusEntityStatusCd());
    		     sForm.setStreetAddr1(fh.getPrimaryAddress().getAddress1());
    		     sForm.setStreetAddr2(fh.getPrimaryAddress().getAddress2());
    		     sForm.setStreetAddr3(fh.getPrimaryAddress().getAddress3());
    		     sForm.setStreetAddr4(fh.getPrimaryAddress().getAddress4());
    		     sForm.setCity(fh.getPrimaryAddress().getCity());
    		     sForm.setPostalCode(fh.getPrimaryAddress().getPostalCode());
    		     sForm.setCountry(fh.getPrimaryAddress().getCountryCd());
    		     sForm.setStateOrProv(fh.getPrimaryAddress().getStateProvinceCd());
    		     sForm.setEdiRoutingCd(fh.getEdiRoutingCd());
                         sForm.setEdiRoutingCd(fh.getEdiRoutingCd());
    		     sForm.setAcceptFreightOnInvoice
    			(fh.getAcceptFreightOnInvoice());
    
                         sForm.setStoreId(Integer.toString(fh.getStoreId()));
    		     return ae;
    		 }
	     }

	 }
	 catch (Exception e ) {
	    ae.add("distributor",
		   new ActionError
		   ("error.simpleGenericError", "error:" + e.getMessage()));
	 }
         return ae;
    }
    
}

