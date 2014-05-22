/*
 * LocateStoreCatalogLogic.java
 *
 * Created on May 5, 2005, 3:02 PM
 */
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import com.cleanwise.view.forms.LocateStoreCatalogForm;
import com.cleanwise.view.logic.LocateStoreAccountLogic;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreCatalogLogic {

    public static ActionErrors processAction(HttpServletRequest request, StorePortalForm baseForm, String action)
            throws Exception
    {
        LocateStoreCatalogForm pForm = null;
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        boolean  isUseDefaultFilter=true;
        if("initSearch".equals(action)) {
            ae = initSearch(request, baseForm);
            return ae;
        }

        pForm = baseForm.getLocateStoreCatalogForm();
        pForm.setCatalogsToReturn(null);
        pForm.setLocateAccountFl(false);
        int myLevel = pForm.getLevel()+1;
        try {
            //Process account search actions
            String submitFormIdent = request.getParameter("jspSubmitIdent");
            int ind = -1;
            if(submitFormIdent!=null) {
                for(int ii=0; ii<myLevel; ii++) {
                    ind = submitFormIdent.indexOf("#",ind+1);
                    if(ind<0) break;
                }
            }
            if(ind>=0 &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM,ind)>=0) {
                ae = LocateStoreAccountLogic.processAction(request,pForm, action);
                isUseDefaultFilter=false;
                if(ae.size()>0) {
                    pForm.setLocateAccountFl(true);
                    return ae;
                }
                if("Return Selected".equals(action)) {
                    action = "accountSearchDone";
                } else if("Cancel".equals(action)) {
                    pForm.getLocateStoreAccountForm().setLocateAccountFl(false);
                    action = "do nothing";
                } else {
                    pForm.setLocateAccountFl(true);
                    return ae;
                }
            }
            //Set default filter if you set params in jsp
            String dF = request.getParameter("defaultFilter");
            if("Search".equals(action)&&
                    (dF!=null&&dF.length()>0)&&isUseDefaultFilter)
            {
                setDefaultAccountFilter(request,baseForm,dF);
            }

            if("Cancel".equals(action)) {
                ae = returnNoValue(request,pForm);
            } else if("Search".equals(action)) {
                ae = search(request,pForm);
            } else if("Return Selected".equals(action)) {
                ae = returnSelected(request,pForm);
            } else if("Locate Account".equals(action)) {
                ae = LocateStoreAccountLogic.processAction(request,pForm,"initSearch");
                if(ae.size()==0) {
                    pForm.getLocateStoreAccountForm().setLocateAccountFl(true);
                }
            } else if("Clear Account Filter".equals(action)) {
                ae = clearAccountFilter(request,pForm);
            } else if("accountSearchDone".equals(action)) {
                ae = LocateStoreAccountLogic.setFilter(request,pForm);
            }

            return ae;
        }finally {
            if(pForm!=null)  pForm.reset(null, null);
        }
    }

    private static ActionErrors setDefaultAccountFilter(HttpServletRequest request, StorePortalForm pForm,String dFilter) {
        ActionErrors ae=new ActionErrors();
        try {


            LocateStoreAccountLogic.processAction(request,pForm,"initSearch") ;
            LocateStoreAccountForm lForm = pForm.getLocateStoreAccountForm();
            lForm.setName(request.getParameter("locateStoreCatalogForm.name"));
            lForm.setProperty("locateStoreCatalogForm.accountFilter");
            if(lForm==null){ae.add("error",new ActionError("error.simpleGenericError","No LocateStoreAccountForm"));
                return ae;
            }
            lForm.setSearchField(dFilter);
            lForm.setSearchType("id");
            ae= LocateStoreAccountLogic.search(request,lForm);
            int []f=new int[]{new Integer(dFilter).intValue()};
            if(ae.size()>0) return ae;

            lForm.setSelected(f);
            LocateStoreAccountLogic.returnSelected(request,lForm);
            LocateStoreAccountLogic.setFilter(request, pForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ae;
    }


    public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();

        APIAccess factory = new APIAccess();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        if(storeD==null) {
            ae.add("error",new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }

        LocateStoreCatalogForm pForm = baseForm.getLocateStoreCatalogForm();
        if(pForm==null) {
            pForm = new LocateStoreCatalogForm();
            baseForm.setLocateStoreCatalogForm(pForm);
        }
        //baseForm.setEmbeddedForm(pForm);

        String searchCatalogType = pForm.getSearchCatalogType();
        if(searchCatalogType==null || searchCatalogType.trim().length()==0) {
            searchCatalogType = "catalogNameContains";
        }
        pForm.setSearchCatalogType(searchCatalogType);


        CatalogDataVector catalogDV = pForm.getCatalogsSelected();
        if(catalogDV==null) {
            pForm.setCatalogsSelected(new CatalogDataVector());
        }
        pForm.setCatalogsToReturn(null);
        return ae;
    }


    public static ActionErrors returnNoValue(HttpServletRequest request,
                                             LocateStoreCatalogForm pForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        pForm.setCatalogsToReturn(null);
        return ae;
    }

    public static ActionErrors search(HttpServletRequest request,
                                      LocateStoreCatalogForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();

        Catalog catalogEjb   = factory.getCatalogAPI();
        CatalogInformation catalogInformationEjb   = factory.getCatalogInformationAPI();

        EntitySearchCriteria crit = new EntitySearchCriteria();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        ArrayList types = new ArrayList();
        String typeFilter = pForm.getCatalogTypeFilter();
        if(!Utility.isSet(typeFilter)) {
            types.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
            types.add(RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
            types.add(RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
            types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        }else {
            types.add(typeFilter);
        }
        crit.setSearchTypeCds(types);

        String searchField = pForm.getSearchCatalogField();
        String searchType = pForm.getSearchCatalogType();
        boolean showInactiveFl = pForm.getShowInactiveCatalogFl();

        CatalogDataVector catalogList = new CatalogDataVector();

        if(Utility.isSet(searchField)) {
            if(searchType.equalsIgnoreCase("catalogId")) {
                try{
                    int catalogId = Integer.parseInt(searchField);
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                    ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Search Field"));
                    return ae;
                }
                crit.setSearchId(searchField);
            } else {
                int matchType=(searchType.equalsIgnoreCase("catalogNameContains"))?
                        crit.NAME_CONTAINS:crit.NAME_STARTS_WITH;
                crit.setSearchNameType(matchType);
                crit.setSearchName(searchField);
            }
        }
        if(!showInactiveFl) {
            LinkedList statusLL = new LinkedList();
            statusLL.add(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
            statusLL.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);
            crit.setSearchStatusCdList(statusLL);
        }

        //Set account
        IdVector accountIdV = new IdVector();
        AccountDataVector accountDV = pForm.getAccountFilter();

        if(accountDV!=null) {
            for(Iterator iter=accountDV.iterator(); iter.hasNext();) {
                AccountData aD = (AccountData) iter.next();
                int accountId = aD.getBusEntity().getBusEntityId();
                accountIdV.add(new Integer(accountId));
            }
        }
        crit.setAccountBusEntityIds(accountIdV);
        catalogList = catalogInformationEjb.getCatalogsByCrit(crit);

        pForm.setCatalogsSelected(catalogList);
        return ae;
    }


    public static ActionErrors clearAccountFilter(HttpServletRequest request,
                                                  LocateStoreCatalogForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        pForm.setAccountFilter(null);
        return ae;
    }


    public static ActionErrors returnSelected(HttpServletRequest request,
                                              LocateStoreCatalogForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int[] selected = pForm.getSelectedCatalogIds();
        CatalogDataVector cDV = pForm.getCatalogsSelected();
        CatalogDataVector retCDV = new CatalogDataVector();
        for(Iterator iter=cDV.iterator(); iter.hasNext();) {
            CatalogData cD = (CatalogData) iter.next();
            for(int ii=0; ii<selected.length; ii++) {
                if(cD.getCatalogId()==selected[ii]) {
                    retCDV.add(cD);
                }
            }
        }
        pForm.setCatalogsToReturn(retCDV);
        return ae;
    }

    public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
        HttpSession session = request.getSession();
        LocateStoreCatalogForm form = pForm.getLocateStoreCatalogForm();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),null);
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        LocateStoreCatalogForm form = pForm.getLocateStoreCatalogForm();
        Object o = form.getCatalogsToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),o);
        form.setLocateCatalogFl(false);
        return ae;
    }

    public static ActionErrors setFilterProgrammatically(HttpServletRequest request, 
        StorePortalForm baseForm, CatalogDataVector data, String formName, 
        String dataPropertyName) throws Exception {
        LocateStoreCatalogForm locateForm = baseForm.getLocateStoreCatalogForm();
        if (locateForm == null) {
            locateForm = new LocateStoreCatalogForm();
        }
        if (locateForm.getName() == null) {
            locateForm.setName(formName);
        }
        if (locateForm.getProperty() == null) {
            locateForm.setProperty(dataPropertyName);
        }
        if (data == null || data.size() == 0) {
            locateForm.setCatalogsToReturn(null);
        } else {
            locateForm.setCatalogsToReturn(data);
        }
        baseForm.setLocateStoreCatalogForm(locateForm);
        return new ActionErrors();
    }

}
