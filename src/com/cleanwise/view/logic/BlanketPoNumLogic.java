/*
 * BlanketPoNumLogic.java
 *
 * Created on February 9, 2005, 12:42 PM
 */

package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CustomerRequestPoNumber;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.BlanketPoNumData;
import com.cleanwise.service.api.value.BlanketPoNumDescData;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.forms.BlanketPoNumForm;
import com.cleanwise.view.forms.BlanketPoNumSearchForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SelectableObjects;
import java.util.List;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import com.cleanwise.view.utils.SessionTool;

/**
 *Encapsulates all of the logic necessary to manage the Blanke Pos
 * @author bstevens
 */
public class BlanketPoNumLogic {

    /**
     *initializes the configuration
     */
    public static void initConfig(HttpServletRequest request,ActionForm pForm) throws Exception{
        BlanketPoNumForm sForm = (BlanketPoNumForm) pForm;
        sForm.setResults(null);
    }

    /**
     *Initializes some constant values used in the display
     */
    public static void initConstants(HttpServletRequest request)throws Exception{
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();

        if (session.getAttribute("simple.status") == null) {
            RefCdDataVector statusv =
            listServiceEjb.getRefCodesCollection("SIMPLE_STATUS_CD");
            session.setAttribute("simple.status", statusv);
        }

        if (session.getAttribute("blanket.req.po.num.type.cd") == null) {
            RefCdDataVector statusv =
            listServiceEjb.getRefCodesCollection("BLANKET_PO_NUM_TYPE_CD");
            session.setAttribute("blanket.req.po.num.type.cd", statusv);
        }
    }

    /**
     *Preforms a search based off the values in the form and populates the results
     */
    public static ActionErrors search(HttpServletRequest request,
            ActionForm pForm) throws Exception{
        BlanketPoNumSearchForm sForm = (BlanketPoNumSearchForm) pForm;
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        CustomerRequestPoNumber custPoEJB = factory.getCustomerRequestPoNumberAPI();
        EntitySearchCriteria crit = new EntitySearchCriteria();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        }
        if("id".equals(sForm.getSearchType())){
            try{
                //make sure it is an int
                Integer.parseInt(sForm.getSearchField());
                crit.setSearchId(sForm.getSearchField());
            }catch(Exception e){
                lUpdateErrors.add("searchField",new ActionError("error.invalidNumber","search"));
            }
        }else if("nameBegins".equals(sForm.getSearchType())){
            crit.setSearchName(sForm.getSearchField());
            crit.setSearchNameType(EntitySearchCriteria.NAME_STARTS_WITH);
        }else if("nameContains".equals(sForm.getSearchType())){
            crit.setSearchName(sForm.getSearchField());
            crit.setSearchNameType(EntitySearchCriteria.NAME_CONTAINS);
        }
        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        sForm.setResults(custPoEJB.search(crit));
        return lUpdateErrors;
    }



    /**
     *Populates the form with all the BlanketPoNums
     */
    public static ActionErrors viewAll(HttpServletRequest request,
            ActionForm pForm) throws Exception{
        BlanketPoNumSearchForm sForm = (BlanketPoNumSearchForm) pForm;
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        CustomerRequestPoNumber custPoEJB = factory.getCustomerRequestPoNumberAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            EntitySearchCriteria crit = new EntitySearchCriteria();
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            sForm.setResults(custPoEJB.search(crit));
        }else{
            sForm.setResults(custPoEJB.viewAll());
        }
        return lUpdateErrors;
    }

    /**
     *Initialiazes the form for configuring/creating a new BlanketPoNum
     */
    public static ActionErrors initNew(HttpServletRequest request,ActionForm pForm) throws Exception{
        BlanketPoNumForm sForm = (BlanketPoNumForm) pForm;
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        BlanketPoNumDescData bpo = new BlanketPoNumDescData();
        bpo.init();
        sForm.setCurrentRelease("0");
        sForm.setBlanketPoNumDescData(bpo);
        return lUpdateErrors;
    }

    /**
     *Saves the BlanketPoNum contained in the form, preforms some validation on the data entry
     */
    public static ActionErrors save(HttpServletRequest request,ActionForm pForm) throws Exception{
        BlanketPoNumForm sForm = (BlanketPoNumForm) pForm;
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        //validate that object
        BlanketPoNumDescData desc = sForm.getBlanketPoNumDescData();
        BlanketPoNumData data = desc.getBlanketPoNumData();
        if(!Utility.isSet(data.getPoNumber())){
            lUpdateErrors.add("BlanketPoNumDescData.BlanketPoNumData.poNumber",new ActionError("variable.empty.error","PO Number"));
        }
        if(Utility.isSet(sForm.getCurrentRelease())){
            try{
                data.setCurrentRelease(Integer.parseInt(sForm.getCurrentRelease()));
            }catch(NumberFormatException e){
                lUpdateErrors.add("currentRelease",new ActionError("error.invalidNumber","id"));
            }
        }
        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        CustomerRequestPoNumber custPoEJB = factory.getCustomerRequestPoNumberAPI();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        try{
            sForm.setBlanketPoNumDescData(
                custPoEJB.addUpdate(sForm.getBlanketPoNumDescData(),appUser.getUserName()));
        }catch(Exception e){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return lUpdateErrors;
    }

    /**
     *Retrieves the BlanketPoNum detail for the id specified in the request under the id parameter
     */
    public static ActionErrors getDetail(HttpServletRequest request,ActionForm pForm) throws Exception{
        BlanketPoNumForm sForm = (BlanketPoNumForm) pForm;
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        CustomerRequestPoNumber custPoEJB = factory.getCustomerRequestPoNumberAPI();
        String idS = request.getParameter("id");
        int id = 0;
        try{
            id = Integer.parseInt(idS);
        }catch(Exception e){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","id"));
        }
        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }
        try{
            sForm.setBlanketPoNumDescData(custPoEJB.getDetail(id));
        }catch(DataNotFoundException e){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError","id not valid"));
        }
        //translate any of the desc datas object into it's forms counterparts
        sForm.setCurrentRelease(Integer.toString(sForm.getBlanketPoNumDescData().getBlanketPoNumData().getCurrentRelease()));
        return lUpdateErrors;
    }


    /**
	 *Searches the configuration based off the form search type (account, site, stores) and the serach criteria
	 */
	public static ActionErrors searchConfig(HttpServletRequest request,ActionForm pForm) throws Exception{
	    BlanketPoNumForm sForm = (BlanketPoNumForm) pForm;
	    sForm.setResults(null);
	    HttpSession session = request.getSession();
	    ActionErrors lUpdateErrors = new ActionErrors();
	    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	    if (null == factory) {
	        throw new Exception("Without APIAccess.");
	    }
	    CustomerRequestPoNumber custPoEJB = factory.getCustomerRequestPoNumberAPI();

	    BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
	    crit.setSearchName(sForm.getSearchField());
	    if("nameBegins".equals(sForm.getSearchType())){
	        crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
	    }else if("nameContains".equals(sForm.getSearchType())){
	        crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
	    }
	    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	    if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
	        crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
	    }

	    int poId = sForm.getBlanketPoNumDescData().getBlanketPoNumData().getBlanketPoNumId();

	    //preform the search
	    List allOptions;
	    List selectedOptions;
	    sForm.setResultsType(sForm.getConfigType());
	    if(sForm.getConfigType().equals("Site")){
	        //restrict results to those associated with the accounts of this blanket po
	        BusEntityDataVector accounts = custPoEJB.searchAccountAssociations(null, poId);
	        if(accounts != null && !accounts.isEmpty()){
	            crit.setParentBusEntityIds(Utility.toIdVector(accounts));
	            Site siteEJB = factory.getSiteAPI();

	            allOptions = siteEJB.getSitesByCriteria(crit);
	            selectedOptions = custPoEJB.searchSiteAssociations(crit, poId);
	            //anything that should be checked but was filtered out of the
	            //crit grab and add to the list.
	            IdVector toFetch = getIdsNotInSecondList(Utility.toIdVector(selectedOptions),Utility.toIdVector(allOptions));

                int oneOfSiteId =(toFetch!= null)? ((Integer)toFetch.get(0)).intValue() : 0 ;
	            allOptions.addAll(siteEJB.getSiteCollection(toFetch,SessionTool.getCategoryToCostCenterView(session, oneOfSiteId )));
	        }else{
	            return lUpdateErrors;
	        }
	    }else if(sForm.getConfigType().equals("Account")){
	        //restrict results to those associated with the stores of this blanket po
	        BusEntityDataVector stores = custPoEJB.searchStoreAssociations(null, poId);
	        if(stores != null && !stores.isEmpty()){
	            crit.setParentBusEntityIds(Utility.toIdVector(stores));
	            Account accountEJB = factory.getAccountAPI();
	            //AccountDataVector accounts = accountEJB.getSitesByCriteria(crit);
	            //allOptions = accountEJB.getAccountsByCriteria(crit);
	            Account accountBean = factory.getAccountAPI();
	            AccountSearchResultViewVector acntSrchRsltVctr = null;
	            //CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	            String storeIdsStr = null;
	            if (!appUser.isaSystemAdmin()) {
	        		IdVector storesIds = appUser.getUserStoreAsIdVector();
	        		storeIdsStr = storesIds.toCommaString(storesIds);
	        	}
	            allOptions = accountBean.search(storeIdsStr,
	            										sForm.getSearchField(),
	            										sForm.getSearchType(),
	            										"0", true);
	            selectedOptions = custPoEJB.searchAccountAssociations(crit, poId);
	            //anything that should be checked but was filtered out of the
	            //crit grab and add to the list.
	            IdVector toFetch = getIdsNotInSecondList(Utility.toIdVector(selectedOptions),Utility.toIdVector(allOptions));
	            allOptions.addAll(accountEJB.getAccountsAsSrchResultVector(toFetch));
	        }else{
	            return lUpdateErrors;
	        }
	    }else if(sForm.getConfigType().equals("Store")){
	        Store storeEJB = factory.getStoreAPI();
	        //StoreDataVector stores = storeEJB.getSitesByCriteria(crit);
	        allOptions = storeEJB.getStoresByCriteria(crit);
	        selectedOptions = custPoEJB.searchStoreAssociations(crit, poId);
	    }else{
	        lUpdateErrors.add("configType", new ActionError("error.simpleGenericError","Bad Config Type"));
	        return lUpdateErrors;
	    }
	    //create the selecatble object
	    SelectableObjects so = new SelectableObjects(selectedOptions,allOptions,ClwComparatorFactory.getBusEntityComparator());
	    sForm.setResults(so);
	    return lUpdateErrors;
	}

    private static IdVector getIdsNotInSecondList(IdVector l1, IdVector l2){
        IdVector toReturn = new IdVector();
        Iterator it = l1.iterator();
        while(it.hasNext()){
            Object o = it.next();
            if(!l2.contains(o)){
                toReturn.add(o);
            }
        }
        return toReturn;
    }

    /**
     *Saves the configuration setup.  attaches and unattaches from accounts, stores and sites
     */
    public static ActionErrors saveConfig(HttpServletRequest request,ActionForm pForm) throws Exception{
        BlanketPoNumForm sForm = (BlanketPoNumForm) pForm;
        HttpSession session = request.getSession();
        ActionErrors lUpdateErrors = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        CustomerRequestPoNumber custPoEJB = factory.getCustomerRequestPoNumberAPI();


        List newSel = sForm.getResults().getNewlySelected();
        if("Sites".equals(sForm.getConfigType())){
            Iterator it = newSel.iterator();
            while(it.hasNext()){
                SiteData sd = (SiteData) it.next();
                if(sd.getBlanketPoNum() != null && sd.getBlanketPoNum().getBlanketPoNumId() != 0){
                    lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.siteMustHaveOnlyOnePO",sd.getBusEntity().getShortDesc()));
                }
            }
        }

        if(lUpdateErrors.size() > 0){
            return lUpdateErrors;
        }

        IdVector nl = Utility.toIdVector(newSel);
        IdVector dl = Utility.toIdVector(sForm.getResults().getDeselected());
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        int id = sForm.getBlanketPoNumDescData().getBlanketPoNumData().getBlanketPoNumId();
        custPoEJB.addBusEntityAssociations(id,nl,appUser.getUserName());
        custPoEJB.removeBusEntityAssociations(id,dl);
        sForm.getResults().resetState();
        return lUpdateErrors;
    }






}
