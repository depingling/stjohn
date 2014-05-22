package com.cleanwise.view.logic;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.forms.LocateStoreServiceForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.CatalogInformation;


import java.util.Iterator;


/**
 * Title:        LocateStoreServiceLogic
 * Description:  Logic class for the  service search.
 * Purpose:      service search
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         10.01.2007
 * Time:         19:13:45
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class LocateStoreServiceLogic {

     private static final String className = "LocateStoreServiceLogic";
    /**
     * Action processing
     * @param request HttpServletRequest
     * @param baseForm base form
     * @param action  action
     * @return  errors
     * @throws Exception exception
     */
    public static ActionErrors processAction(HttpServletRequest request,
                                             StorePortalForm baseForm,
                                             String action)  throws Exception
    {
        LocateStoreServiceForm pForm = baseForm.getLocateStoreServiceForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session  = request.getSession();
            if("initSearch".equals(action)) {
                ae = initSearch(request,baseForm);
                return ae;
            }
            int myLevel = pForm.getLevel()+1;
            pForm.setServiceToReturn(null);
            if("Cancel".equals(action)) {
                ae = returnNoValue(request,pForm);
            } else if("Search".equals(action)) {
                ae = search(request,pForm);
            } else if("Return Selected".equals(action)) {
                ae = returnSelected(request,pForm);
            }
            return ae;
        }finally {
            if(pForm!=null)  pForm.reset(null, null);
        }
    }


    /**
     * Init data of form bean
     * @param request   HttpServletRequest
     * @param baseForm  form bean
     * @return   errors
     * @throws Exception  exceptions
     */
    public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();

        if(storeD==null) {
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }

        LocateStoreServiceForm pForm = baseForm.getLocateStoreServiceForm();
        if(pForm==null) {
            pForm = new LocateStoreServiceForm();
            pForm.setLevel(1);
            baseForm.setLocateStoreServiceForm(pForm);
        }

        String searchType = pForm.getSearchType();
        if(searchType==null || searchType.trim().length()==0) searchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        pForm.setSearchType(searchType);

        ServiceViewVector serviceViewVector=pForm.getService();

        if(serviceViewVector==null) {
            pForm.setService(new ServiceViewVector());
        }
        pForm.setServiceToReturn(null);
        pForm.setSearchStoreId(-1);

        return ae;
    }



    public static ActionErrors returnNoValue(HttpServletRequest request,LocateStoreServiceForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        pForm.setServiceToReturn(new ServiceViewVector());
        return ae;
    }


    public static ActionErrors search(HttpServletRequest request, LocateStoreServiceForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            String searchField = pForm.getSearchField();
            String fieldSearchType = pForm.getSearchType();
            if (fieldSearchType.equals("id") && searchField.length() > 0) {

                Integer.parseInt(searchField);
            }
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
            StoreData storeD = appUser.getUserStore();
            if (storeD == null) {
                ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
                return ae;
            }
            int storeId = storeD.getBusEntity().getBusEntityId();

            CatalogDataVector catalogDV =
                    catalogInfEjb.getCatalogsCollectionByBusEntity(storeId, RefCodeNames.CATALOG_TYPE_CD.STORE);

            CatalogData cD = null;
            for (Iterator iter = catalogDV.iterator(); iter.hasNext();) {
                cD = (CatalogData) iter.next();
                if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
                    break;
                }
            }
            if (cD == null) {
                ae.add("error", new ActionError("error.simpleGenericError", "No store catalog found"));
                return ae;
            }
            ServiceSearchCriteria crit=new ServiceSearchCriteria();
            // selected catalogs
            if (pForm.getSearchInSelectedCatalogs()) {
                String selectedCatalogs = pForm.getSelectedCatalogList();
                crit.setCatalogIds(Utility.parseIdStringToVector(selectedCatalogs,","));
            }

            crit.setCategory(pForm.getCategoryTempl());
            crit.setServiceName(pForm.getSearchField());
            crit.setSearchNameTypeCd(pForm.getSearchType());
            crit.setStoreId(storeId);
            crit.setServiceShowInactive(pForm.getShowInactiveFl());

            ServiceViewVector serviceVV = search(crit);
            pForm.setService(serviceVV);
            pForm.setSearchStoreId(storeId);

        } catch (NumberFormatException e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.invalidNumber", "Search Field"));
            return ae;
        }
        catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionMessage("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    /**
     * Returns an Service view vector based off the specified search criteria
     * @return Service view vector
     * @throws Exception exception
     * @param crit ServiceSearchCriteria
     */
    static public ServiceViewVector search(ServiceSearchCriteria crit) throws Exception{

      APIAccess factory = new APIAccess();
      ActionErrors ae = new ActionErrors();
      CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

      return catalogInfEjb.getServicesViewVector(crit);

    }
    public static ActionErrors returnSelected(HttpServletRequest request,
                                              LocateStoreServiceForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int[] selected = pForm.getSelected();
        ServiceViewVector  serviceVV = pForm.getService();
        ServiceViewVector retVV = new ServiceViewVector();
        for(Iterator iter=serviceVV.iterator(); iter.hasNext();) {
            ServiceView serviceView = (ServiceView) iter.next();
            for(int ii=0; ii<selected.length; ii++) {
                if(serviceView.getServiceId()==selected[ii]) {
                    retVV.add(serviceView);
                }
            }
        }

        pForm.setServiceToReturn(retVV);
        return ae;
    }



    public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
        HttpSession session = request.getSession();
        LocateStoreServiceForm serviceForm = pForm.getLocateStoreServiceForm();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(serviceForm.getName()),serviceForm.getProperty(),null);
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        LocateStoreServiceForm sForm = pForm.getLocateStoreServiceForm();
        ServiceViewVector serviceVV = sForm.getServiceToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(sForm.getName()),sForm.getProperty(),serviceVV);
        sForm.setLocateServiceFl(false);
        return ae;
    }

    protected void finalize() throws Throwable {

        super.finalize();
    }

}

