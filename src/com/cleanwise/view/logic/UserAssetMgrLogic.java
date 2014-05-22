package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.AssetDataAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Warranty;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.BreadCrumbNavigator;
import com.cleanwise.view.utils.BreadCrumbNavigator.BreadCrumbContainer;
import com.cleanwise.view.utils.BreadCrumbNavigator.BreadCrumbItem;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.StringUtils;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import com.cleanwise.service.api.dao.AssetMasterAssocDataAccess;
import com.cleanwise.service.api.dao.AssetAssocDataAccess;
import org.apache.log4j.*;

/**
 * Title:        UserAssetMgrLogic
 * Description:  logic manager for the asset processing.
 * Purpose:      execute operation for the asset processing
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         02.01.2007
 * Time:         10:00:58
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class UserAssetMgrLogic {
    private static final Logger log = Logger.getLogger(UserAssetMgrLogic.class);

    public static final String className = "UserAssetMgrLogic";

    public static final String USER_ASSET_PROFILE_MGR_FORM  = "USER_ASSET_PROFILE_MGR_FORM";
    public static final String USER_ASSET_MGR_FORM          = "USER_ASSET_MGR_FORM";
    public static final String USER_ASSET_CONTENT_MGR_FORM  = "USER_ASSET_CONTENT_MGR_FORM";

      public static ActionErrors search(HttpServletRequest request, UserAssetMgrForm form) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, form);
        if (ae.size() > 0) return ae;

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();

        try {
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            Asset assetEJB = factory.getAssetAPI();
            Site siteEJB = factory.getSiteAPI();
            AssetSearchCriteria criteria = new AssetSearchCriteria();
            IdVector siteIds = null;
            int categoryId = 0;

            criteria.setUserAuthorizedForAssetWOViewAllForStore(
            		appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_WO_VIEW_ALL_FOR_STORE));
            criteria.setUserId(appUser.getUserId());
            criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());

            criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            criteria.setSearchNumberTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            criteria.setSearchManufSkuTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            criteria.setSearchModelTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);

            if (Utility.isSet(form.getSearchField())) {
                criteria.setAssetName(form.getSearchField());
            }
            criteria.setAssetTypeCd(form.getAssetType());
            //criteria.setOrderBy(new PairView(AssetDataAccess.SHORT_DESC, Boolean.TRUE));

            String assetType = criteria.getAssetTypeCd();
            if (!RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetType)) {
                if (Utility.isSet(form.getSearchManufSku())) {
                    criteria.setManufSku(form.getSearchManufSku());
                }
                int manufId = Integer.parseInt(form.getSearchManufacturerId());
                if (manufId > 0) {
                    criteria.setManufId(manufId);
                }
                if (Utility.isSet(form.getSearchModel())) {
                    criteria.setModelNumber(form.getSearchModel());
                }
                categoryId = Integer.parseInt(form.getSearchCategoryId());
                if (categoryId > 0) {
                    criteria.setParentIds(Utility.toIdVector(categoryId));
                }

                if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetType)) {
                    if (Utility.isSet(form.getSearchSerial())) {
                        criteria.setSerialNumber(form.getSearchSerial());
                    }
                    if (Utility.isSet(form.getSearchNumber())) {
                        criteria.setAssetNumber(form.getSearchNumber());
                    }
                    AccountDataVector filterAccounts = form.getFilterAccounts();
                    IdVector accountIds = null;
                    if (filterAccounts != null && filterAccounts.size() > 0) {
                        accountIds = new IdVector();
                        Iterator it = filterAccounts.iterator();
                        AccountData account;
                        while (it.hasNext()) {
                            account = (AccountData)it.next();
                            accountIds.add(Integer.valueOf(account.getAccountId()));
                        }
                        criteria.setAccountIds(accountIds);
                    }
                    if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
                        HashMap assocCds = new HashMap();
                        assocCds.put(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
                        criteria.setAssetAssocCds(assocCds);
                    }
                } else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                    if (Utility.isSet(form.getSearchNumber())) {
                        criteria.setAssetNumber(form.getSearchNumber());
                    }
                    criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
                    criteria.setSearchNumberTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
                }
            }

            if (form.getShowInactive()) {
                criteria.setShowInactive(true);
            }

            criteria.setIgnoreCase(true);
            criteria.setStoreIds(appUser.getUserStoreAsIdVector());

            //if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            //if (form.getCurrentSiteOnlyFl()) {
            //    IdVector siteIds = new IdVector();
            //    siteIds.add(new Integer(appUser.getSite().getSiteId()));
            //    criteria.setSiteIds(siteIds);
            //}

            //} else {

            //    HashMap assocCds = new HashMap();
            //    assocCds.put(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
            //    criteria.setAssetAssocCds(assocCds);

            //    criteria.setUserId(appUser.getUserId());
            //    criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());//

            //    IdVector siteIds = new IdVector();
            //    siteIds.add(new Integer(appUser.getSite().getSiteId()));
            //    criteria.setSiteIds(siteIds);
            //}

            try {
                AssetViewVector assetVV = assetEJB.getAssetViewVector(criteria);
                DisplayListSort.sort(assetVV, "assetname");
                if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetType)) {
                    form.setAssetSearchResult(assetVV);
                } else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                    form.setMasterAssetSearchResult(assetVV);
                } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetType)) {
                    form.setCategoryAssetSearchResult(assetVV);
                }
            } catch (DataNotFoundException e) {
                if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetType)) {
                    form.setAssetSearchResult(new AssetViewVector());
                } else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                    form.setMasterAssetSearchResult(new AssetViewVector());
                } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetType)) {
                    form.setCategoryAssetSearchResult(new AssetViewVector());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resetSessionAttributes(request, form);
            throw new Exception(e);
        }
        return ae;
    }

    public static ActionErrors loadMasterAsset(HttpServletRequest request, UserAssetMgrForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Store storeEJB = factory.getStoreAPI();
        try{
        	storeEJB.loadMasterAsset("tmp_tcsload", appUser);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	ae.add("loadMasterAsset", new ActionError(e.getClass().getName() + ", " + e.getMessage()));
        }
    	return ae;
    }

    public static ActionErrors loadPhysicalAsset(HttpServletRequest request, UserAssetMgrForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Store storeEJB = factory.getStoreAPI();

        try {
            storeEJB.loadPhysicalAsset("tmp_tcs_paload", appUser);
        } catch(Exception e) {
            e.printStackTrace();
            try {
                String err = StringUtils.prepareUIMessage(request, e);
                ae.add("loadPhysicalAsset", new ActionError("error.simpleGenericError", err));
            } catch (Exception e1) {
                ae.add("loadPhysicalAsset", new ActionError("error.simpleGenericError", e.toString()));
            }
        }
    	return ae;
    }

    public static ActionErrors storePortalSearch(HttpServletRequest request, UserAssetMgrForm form) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, form);
        if (ae.size() > 0) return ae;

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        try {
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            Asset assetEJB = factory.getAssetAPI();
            AssetSearchCriteria criteria = new AssetSearchCriteria();
            int categoryId = 0;

            criteria.setUserAuthorizedForAssetWOViewAllForStore(true);
            criteria.setUserId(appUser.getUserId());
            criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());

            if (Utility.isSet(form.getSearchField())) {
                criteria.setAssetName(form.getSearchField());
            }
            criteria.setAssetTypeCd(form.getAssetType());
            criteria.setSearchNameTypeCd(form.getNameSearchType());// for MASTER_ASSET AND CATEGORY

            String assetType = criteria.getAssetTypeCd();
            if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                if (Utility.isSet(form.getSearchManufSku())) {
                    criteria.setManufSku(form.getSearchManufSku());
                }
                int manufId = Integer.parseInt(form.getSearchManufacturerId());
                if (manufId > 0) {
                    criteria.setManufId(manufId);
                }
                if (Utility.isSet(form.getSearchModel())) {
                    criteria.setModelNumber(form.getSearchModel());
                }
                categoryId = Integer.parseInt(form.getSearchCategoryId());
                if (categoryId > 0) {
                    criteria.setParentIds(Utility.toIdVector(categoryId));
                }
                if (Utility.isSet(form.getSearchNumber())) {
                    criteria.setAssetNumber(form.getSearchNumber());
                }
                criteria.setSearchModelTypeCd(form.getModelSearchType());
                criteria.setSearchManufSkuTypeCd(form.getManufSkuSearchType());
                criteria.setSearchNumberTypeCd(form.getIdSearchType());
            }

            if (form.getShowInactive()) {
                criteria.setShowInactive(true);
            }

            criteria.setIgnoreCase(true);
            criteria.setStoreIds(appUser.getUserStoreAsIdVector());

            try {
                AssetViewVector assetVV = assetEJB.getAssetViewVector(criteria);
                DisplayListSort.sort(assetVV, "assetname");
                if (form.getMatchSearch()) {
                    form.setAssetMatchSearchResult(assetVV);
                } else {
                    if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                        form.setMasterAssetSearchResult(assetVV);
                    } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetType)) {
                        form.setCategoryAssetSearchResult(assetVV);
                    }
                }
            } catch (DataNotFoundException e) {
                if (form.getMatchSearch()) {
                    form.setAssetMatchSearchResult(new AssetViewVector());
                } else {
                    if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                        form.setMasterAssetSearchResult(new AssetViewVector());
                    } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetType)) {
                        form.setCategoryAssetSearchResult(new AssetViewVector());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resetSessionAttributes(request, form);
            throw new Exception(e);
        }
        return ae;
    }

    public static ActionErrors chooseAssetToMatch(HttpServletRequest request, UserAssetMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        String assetToMatchStr = request.getParameter("masterAssetIndex");

        if (Utility.isSet(assetToMatchStr)) {
            if (form.getStagedAssetSearchResult() != null) {
                try {
                    AssetView assetToMatch = (AssetView)form.getStagedAssetSearchResult().get(Integer.valueOf(assetToMatchStr));
                    form.setStagedAssetMatch(assetToMatch);
                } catch (NumberFormatException e) {
                    form.setStagedAssetMatch(null);
                }
            }
        }
        return ae;
    }

    public static ActionErrors searchStaged(HttpServletRequest request, UserAssetMgrForm form) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, form);
        if (ae.size() > 0) return ae;

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        try {
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            Asset assetEJB = factory.getAssetAPI();
            AssetSearchCriteria criteria = new AssetSearchCriteria();

            //criteria.setUserAuthorizedForAssetWOViewAllForStore(true);
            //criteria.setUserId(appUser.getUserId());
            //criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());

            if (Utility.isSet(form.getSearchField())) {
                criteria.setAssetName(form.getSearchField());
            }
            criteria.setAssetTypeCd(form.getAssetType());

            criteria.setSearchNameTypeCd(form.getNameSearchType());// for MASTER_ASSET AND CATEGORY
            //criteria.setStagedSearchType(form.getStagedSearchType());

            String assetType = criteria.getAssetTypeCd();
            if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                if (Utility.isSet(form.getSearchManufSku())) {
                    criteria.setManufSku(form.getSearchManufSku());
                }
                if (Utility.isSet(form.getSearchModel())) {
                    criteria.setModelNumber(form.getSearchModel());
                }
                if (Utility.isSet(form.getSearchManufacturerName())) {
                    criteria.setManufName(form.getSearchManufacturerName());
                }
                if (Utility.isSet(form.getSearchCategoryName())) {
                    criteria.setCategoryName(form.getSearchCategoryName());
                }
                if (Utility.isSet(form.getSearchNumber())) {
                    criteria.setAssetNumber(form.getSearchNumber());
                }
                criteria.setSearchModelTypeCd(form.getModelSearchType());
                criteria.setSearchManufSkuTypeCd(form.getManufSkuSearchType());
                criteria.setSearchNumberTypeCd(form.getIdSearchType());
            }

            if (form.getShowInactive()) {
                criteria.setShowInactive(true);
            }
            criteria.setIgnoreCase(true);
            criteria.setStoreIds(appUser.getUserStoreAsIdVector());

            try {
                AssetViewVector stagedVV = (AssetViewVector)assetEJB.getStagedAssetVector(criteria);
                DisplayListSort.sort(stagedVV, "assetname");
                if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                    form.setStagedAssetSearchResult(stagedVV);
                }
            } catch (DataNotFoundException e) {
                if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) {
                    form.setStagedAssetSearchResult(new AssetViewVector());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resetSessionAttributes(request, form);
            throw new Exception(e);
        }
        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();
        session.setAttribute(USER_ASSET_MGR_FORM, new UserAssetMgrForm());
    }

    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (form == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }
  /*      if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }*/
        return ae;

    }



    public static ActionErrors init(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if(pForm ==null || !pForm.init){
            pForm = new UserAssetMgrForm();
            //pForm.setNameSearchType(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            pForm.setAssetType(RefCodeNames.ASSET_TYPE_CD.ASSET);
            pForm.setSearchFieldType(UserAssetMgrForm.SEARCH_FIELD_TYPE.ASSET_NAME);
            pForm.init();
        }

        ae.add(checkRequest(request, pForm));
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = new APIAccess();
        ListService listServiceEJB = factory.getListServiceAPI();
        Manufacturer manufEJB = factory.getManufacturerAPI();
        Asset assetEJB = factory.getAssetAPI();

        session.setAttribute(Constants.BREAD_CRUMB_NAVIGATOR,null);

        RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("ASSET_STATUS_CD");
        session.setAttribute("Asset.status.vector", statusCds);

        RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("ASSET_TYPE_CD");
        session.setAttribute("Asset.type.vector", typeCds);

        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
        IdVector storeIdV = new IdVector();
        storeIdV.add(new Integer(appUser.getUserStore().getStoreId()));
        besc.setStoreBusEntityIds(storeIdV);
        besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        besc.setSearchForInactive(false);

        BusEntityDataVector manuf = manufEJB.getManufacturerBusEntitiesByCriteria(besc);
        DisplayListSort.sort(manuf,"short_desc");

        PairViewVector pairs = new PairViewVector();
        BusEntityData mD1 = null;
        for (int i = 0; i < manuf.size(); i++) {
            mD1 = (BusEntityData) manuf.get(i);
            pairs.add(new PairView(new Integer(mD1.getBusEntityId()),  mD1.getShortDesc()));
        }
        session.setAttribute("Store.manufacturer.vector", pairs);

        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(appUser.getUserStore().getStoreId()));
        criteria.setStoreIds(storeIds);

        AssetDataVector categories = assetEJB.getAssetDataByCriteria(criteria);
        DisplayListSort.sort(categories, "short_desc");

        pairs = new PairViewVector();
        AssetData assetD = null;
        for (int i = 0; i < categories.size(); i++) {
            assetD = (AssetData) categories.get(i);
            pairs.add(new PairView(new Integer(assetD.getAssetId()), assetD.getShortDesc()));
        }
        session.setAttribute("Store.category.vector", pairs);

        if (pForm.getFormVars() == null) {
            pForm.setFormVars(new HashMap());
        }
        pForm.getFormVars().put("accountSearchType", "userAccounts");

        session.setAttribute(USER_ASSET_MGR_FORM, pForm);
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        UserAssetProfileMgrForm newForm = new UserAssetProfileMgrForm();

        APIAccess factory = new APIAccess();

        Manufacturer manufEJB = factory.getManufacturerAPI();
        Asset assetEJB = factory.getAssetAPI();
        WorkOrder woEjb = factory.getWorkOrderAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        Account accountEjb = factory.getAccountAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        AssetDetailView assetDetailView = new AssetDetailView(
                new AssetDetailData(AssetData.createValue(),null,null,null,null,null,null,null,null),
                new AssetAssocDataVector(),
                new BusEntityDataVector(),
                new BusEntityDataVector(),
                new WarrantyDataVector(),
                new WorkOrderDataVector(), new AssetContentViewVector(),
                null,new ItemDataVector());

        if (appUser.getSite() != null) {
            AccountData siteAccount = accountEjb.getAccountForSite(appUser.getSite().getSiteId());
            newForm.setUserAssignedAssetNumber(checkUserAssignedAssetNumber(siteAccount));
        } else {
            newForm.setUserAssignedAssetNumber(false);
        }

        uploadDetailData(request, woEjb, warrantyEjb, assetEJB, assetDetailView, appUser, newForm);

        if (appUser != null) {
            setStoreManufPairs(manufEJB, newForm, appUser.getUserStore().getStoreId());
            setStoreCategories(assetEJB, newForm, appUser.getUserStore().getStoreId());
            if (appUser.getSite() != null) {
                newForm.setSiteId(appUser.getSite().getSiteId());
            }
        }
        ae.add(checkRequest(request, newForm));


        session.setAttribute(USER_ASSET_PROFILE_MGR_FORM, newForm);
        return ae;
    }


    public static ActionErrors createNewAsset(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        ae = init(request, (UserAssetProfileMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        UserAssetProfileMgrForm detForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);
        if (detForm == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

        detForm.getAssetData().setAssetTypeCd(pForm.getAssetType());

        session.setAttribute(USER_ASSET_PROFILE_MGR_FORM, detForm);

        return ae;
    }

    public static ActionErrors createNotMatchedAsset(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();

        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        AssetView notMatchedAsset = pForm.getStagedAssetMatch();
        if (notMatchedAsset != null) {
        	APIAccess factory = new APIAccess();
        	Store storeEJB = factory.getStoreAPI();
        	try {
        		storeEJB.loadMasterAsset(notMatchedAsset.getAssetId(), appUser);
        	}
        	catch(RemoteException e)
        	{
        		ae.add("Create", new ActionError("Create Master Asset From Staged Asset Failed"));
        	}
        }
        else {
        	ae.add("Create", new ActionError("Internal Error. There is no selected Master Asset"));
        }
        return ae;
    }

    public static ActionErrors createAllNotMatchedAssets(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();

        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

    	APIAccess factory = new APIAccess();
    	Store storeEJB = factory.getStoreAPI();
    	try {
    		storeEJB.loadMasterAssetFromStaged(appUser);
    	}
    	catch(RemoteException e)
    	{
    		ae.add("Create", new ActionError("Create Master Asset From Staged Asset Failed"));
    	}
        return ae;

    }

    private static void createNotMatchedAssets(Asset assetEJB,
                                               Collection assetsToCreate,
                                               int storeId,
                                               String userName) throws Exception {

        Iterator it = assetsToCreate.iterator();
        AssetData assetToCreate = null;
        AssetView notMatchedAsset = null;
        while (it.hasNext()) {
            notMatchedAsset = (AssetView)it.next();

            assetToCreate = AssetData.createValue();
            assetToCreate.setAssetId(0);
            assetToCreate.setAssetNum(notMatchedAsset.getAssetNumber());
            assetToCreate.setParentId(notMatchedAsset.getParentId());
            assetToCreate.setManufId(notMatchedAsset.getManufId());
            assetToCreate.setManufName(notMatchedAsset.getManufName());
            assetToCreate.setManufSku(notMatchedAsset.getManufSku());
            assetToCreate.setManufTypeCd(notMatchedAsset.getManufType());
            assetToCreate.setAssetTypeCd(notMatchedAsset.getAssetTypeCd());
            assetToCreate.setShortDesc(notMatchedAsset.getAssetName());
            assetToCreate.setSerialNum(notMatchedAsset.getSerialNumber());
            assetToCreate.setModelNumber(notMatchedAsset.getModelNumber());
            assetToCreate.setStatusCd(RefCodeNames.ASSET_STATUS_CD.ACTIVE);

            assetToCreate = assetEJB.updateAssetData(assetToCreate, userName);

            //int assetId = assetToCreate.getAssetId();
            //if (assetId > 0) {
            //    AssetAssocDataVector assetAssocDV = new AssetAssocDataVector();
            //    AssetAssocData assetAssocD = AssetAssocData.createValue();
            //    assetAssocD.setAssetAssocCd(RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
            //    assetAssocD.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
            //    assetAssocD.setAssetId(assetId);
            //    assetAssocD.setBusEntityId(storeId);
            //    assetAssocDV.add(assetAssocD);

            //    assetEJB.updateAssetAssocDataVector(assetId, assetAssocDV, userName);
            //}

            // make the corresponding staged asset in the staged assets table inactive
            //StagedAssetData stagedAsset = assetEJB.getStagedAssetData(notMatchedAsset.getAssetId());
            //if (stagedAsset != null) {
            //    stagedAsset.setssetStatusCd(RefCodeNames.ASSET_STATUS_CD.INACTIVE);
            //    assetEJB.updateStagedAssetData(stagedAsset, userName);
            //}

            // make the corresponding staged asset association in staged asset assoc table inactive
            //AssetSearchCriteria crit = new AssetSearchCriteria();
            //crit.setAssetId(notMatchedAsset.getAssetId());
            //crit.setAssocTypeCd(RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
            //crit.setStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);

            //StagedAssetAssocDataVector stagedAssocV = assetEJB.getStagedAssetAssocDataVector(crit);
            //StagedAssetAssocData stagedAssoc;
            //if (!stagedAssocV.isEmpty()) {
            //    stagedAssoc = (StagedAssetAssocData)stagedAssocV.get(0);
            //    stagedAssoc.setAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.INACTIVE);
            //    assetEJB.updateStagedAssetAssocData(stagedAssoc, userName);
            //}
        }
    }

    public static ActionErrors unmatchMatchedAsset(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();

        String selectedAssetIndexStr = request.getParameter("masterAssetIndex");

        int selectedAssetIndex = -1;
        StagedAssetData matchedAsset = null;
        AssetView selectedAsset = null;
        if (selectedAssetIndexStr != null) {
            try {
                selectedAssetIndex = Integer.parseInt(selectedAssetIndexStr);
                if (pForm.getStagedAssetSearchResult() != null) {
                    selectedAsset = (AssetView)pForm.getStagedAssetSearchResult().get(selectedAssetIndex);
                    if (selectedAsset != null) {
                        matchedAsset = assetEJB.getStagedAssetData(selectedAsset.getAssetId());
                    }
                }
            } catch (NumberFormatException e) {}
        }

        if (matchedAsset != null) {
            // remove the matched staged asset from the list of the matched staged assets
            //pForm.getStagedAssetSearchResult().remove(selectedAssetIndex);

            selectedAsset.setMatchedAssetId(0);
            matchedAsset.setMatchedAssetId(0);
            assetEJB.updateStagedAssetData(matchedAsset, appUser.getUserName());
        }

        return ae;
    }

    public static ActionErrors matchStagedAsset(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        String assetIdStr = request.getParameter("selectedAssetId");
        int assetId = Integer.parseInt(assetIdStr);

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        AssetView notMatchedAsset = pForm.getStagedAssetMatch();
        if (notMatchedAsset != null) {
        	APIAccess factory = new APIAccess();
        	Store storeEJB = factory.getStoreAPI();
        	try {
                    // remove the staged asset from the list of the unmatched staged assets
                    pForm.getStagedAssetSearchResult().remove(notMatchedAsset);
        		final int staged_asset_id = notMatchedAsset.getAssetId();
				storeEJB.loadMasterAsset(staged_asset_id, assetId, appUser);
        	}
        	catch(RemoteException e)
        	{
        		ae.add("Select", new ActionError("Matching Staged Asset with existing one Failed"));
        	}
        }
        else {
        	log.info("Internal Error. There is no selected Master Asset");
        	ae.add("Select", new ActionError("Internal Error. There is no selected Master Asset"));
        }
        return ae;

    }

    public static ActionErrors createNewManagedAsset(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        ae = init(request, (UserAssetProfileMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        UserAssetProfileMgrForm detForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);
        if (detForm == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

        detForm.getAssetData().setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);
        session.setAttribute(USER_ASSET_PROFILE_MGR_FORM, detForm);

        return ae;
    }

    public static ActionErrors setStoreCategories(Asset assetEjb, UserAssetProfileMgrForm form, int storeId) throws Exception {
        ActionErrors ae = new ActionErrors();
        AssetDataVector categories = new AssetDataVector();
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(storeId));
        criteria.setStoreIds(storeIds);
        AssetDataVector parentAssets = null;

        parentAssets = assetEjb.getAssetDataByCriteria(criteria);
        Iterator it = parentAssets.iterator();
        while (it.hasNext()) {
            AssetData assetData = (AssetData) it.next();
            categories.add(assetData);
        }
        form.setAssetCategories(categories);
        return ae;
    }

    private static ActionErrors setStoreManufPairs(Manufacturer manufEjb, UserAssetProfileMgrForm form, int storeId) throws Exception {

        ActionErrors ae = new ActionErrors();
        PairViewVector pairs = new PairViewVector();
        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
        IdVector storeIdAsV = new IdVector();
        storeIdAsV.add(new Integer(storeId));
        besc.setStoreBusEntityIds(storeIdAsV);

        BusEntityDataVector manuf = manufEjb.getManufacturerBusEntitiesByCriteria(besc);
        DisplayListSort.sort(manuf,"short_desc");

        for (int i = 0; i < manuf.size(); i++) {
            BusEntityData mD1 = (BusEntityData) manuf.get(i);
            pairs.add(new PairView(new Integer(mD1.getBusEntityId()),  mD1.getShortDesc()));
        }

        form.setManufIdNamePairs(pairs);
        return ae;
    }


    private static AssetContentDetailView getAssetImage(Asset assetEjb, AssetContentViewVector contents) throws Exception {

        AssetContentViewVector assetImgContents = new AssetContentViewVector();

        if (contents != null) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                AssetContentView contentV = ((AssetContentView) it.next());
                if (RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE.equals(contentV.getAssetContentData().getTypeCd())) {
                    assetImgContents.add(contentV);
                }
            }
        }
        if (!assetImgContents.isEmpty()) {
            int assetContentId = ((AssetContentView) assetImgContents.get(0)).getAssetContentData().getAssetContentId();
            return assetEjb.getAssetContentDetails(assetContentId);
        } else {
            return null;
        }
    }

    public static ActionErrors sort(HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        AssetViewVector assets = null;
        if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(pForm.getAssetType())) {
            assets = (AssetViewVector) pForm.getAssetSearchResult();
        } else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(pForm.getAssetType())) {
            assets = (AssetViewVector) pForm.getMasterAssetSearchResult();
        } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(pForm.getAssetType())) {
            assets = (AssetViewVector) pForm.getCategoryAssetSearchResult();
        }
        if (assets == null) {
            return ae;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(assets, sortField);
        return ae;
    }

    public static ActionErrors sortMatch (HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        AssetViewVector assets = (AssetViewVector) pForm.getAssetMatchSearchResult();

        if (assets != null) {
            String sortField = request.getParameter("sortField");
            DisplayListSort.sort(assets, sortField);
        }
        return ae;
    }

    public static ActionErrors sortStaged (HttpServletRequest request, UserAssetMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        AssetViewVector assets = (AssetViewVector) pForm.getStagedAssetSearchResult();

        if (assets != null) {
            String sortField = request.getParameter("sortField");
            DisplayListSort.sort(assets, sortField);
        }
        return ae;
    }

    public static ActionErrors getAssetProfile(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        int assetIdInt = getAssetIdFromRequest(request, pForm);

        ae = init(request, pForm);
        if (ae.size() > 0) return ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;


        UserAssetProfileMgrForm sForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        Asset assetEjb  = factory.getAssetAPI();
        WorkOrder woEjb = factory.getWorkOrderAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        Site siteEjb = factory.getSiteAPI();
        Account accountEjb = factory.getAccountAPI();

        int siteId = getSiteIdfromRequest(request, pForm);

        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetId(assetIdInt);
        IdVector siteIds = new IdVector();
        siteIds.add(new Integer(siteId));
        //if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
        //    criteria.setSiteIds(siteIds);
        //    criteria.setUserId(appUser.getUserId());
        //    criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());
        //}

        criteria.setShowInactive(true);
        try {

            AssetDetailViewVector assetDetailVV = assetEjb.getAssetDetailViewVector(criteria);
            if (assetDetailVV.size() > 1) {
                throw new Exception("Multiple asset detail data");
            }

            AssetDetailView assetDetailViewData = ((AssetDetailView) assetDetailVV.get(0));

            AddressData assetLocationData = null;
            assetLocationData = assetDetailViewData.getLocation();
            //if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR) &&
            //       !matchAssetLocation(assetLocationData, appUser.getSite().getSiteAddress())) {
            //    throw new Exception("Asset location  does not match site address");
            //}

            uploadDetailData(request, woEjb, warrantyEjb, assetEjb, assetDetailViewData, appUser, sForm);

            if (appUser.getSite() != null) {
                sForm.setSiteId(appUser.getSite().getSiteId());
            }

            LocateStoreSiteLogic.initSearch(request, sForm);
            sForm.getLocateStoreSiteForm().setLocateSiteFl(true);
            session.setAttribute(USER_ASSET_PROFILE_MGR_FORM, sForm);

        } catch (DataNotFoundException e) {
            e.printStackTrace();
            String errorMess = "Asset detail not found.";
            throw new Exception(errorMess);
        }

        return ae;
    }

    public static ActionErrors getStoreMasterAssetProfile(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        int assetIdInt = getAssetIdFromRequest(request, pForm);

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;

        //UserAssetProfileMgrForm sForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Asset assetEjb  = factory.getAssetAPI();
        WorkOrder woEjb = factory.getWorkOrderAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        Manufacturer manufacturerEjb = factory.getManufacturerAPI();

        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetId(assetIdInt);
        criteria.setShowInactive(true);
        try {
            AssetDetailViewVector assetDetailVV = assetEjb.getAssetDetailViewVector(criteria);
            if (assetDetailVV.size() > 1) {
                log.info("UserAssetMgrLogic.getStoreMasterAssetProfile() - WARNING - Multiple asset detail data");
            }
            AssetDetailView assetDetailViewData = ((AssetDetailView) assetDetailVV.get(0));
            uploadDetailData(request, woEjb, warrantyEjb, assetEjb, assetDetailViewData, appUser, pForm);

            if (appUser != null) {
                setStoreManufPairs(manufacturerEjb, pForm, appUser.getUserStore().getStoreId());
                setStoreCategories(assetEjb, pForm, appUser.getUserStore().getStoreId());
            }
        } catch (DataNotFoundException e) {
            log.info("UserAssetMgrLogic.getStoreMasterAssetProfile(): ERROR - Asset detail not found. assetId: " + assetIdInt);
        }

        return ae;
    }

    private static void uploadDetailData(HttpServletRequest request,
                                         WorkOrder woEjb,
                                         Warranty warrantyEjb,
                                         Asset assetEjb,
                                         AssetDetailView assetDetailView,
                                         CleanwiseUser appUser,
                                         UserAssetProfileMgrForm form) throws Exception {

        StoreData storeD = appUser.getUserStore();
        if (storeD != null) {
            String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),
                                RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
            form.setShowLinkedStores(Utility.isTrue(isParentStore, false));
        }
        form.setAssetData(assetDetailView.getAssetDetailData().getAssetData());
        form.setAssetWarrantyAssoc(assetDetailView.getAssetWarrantyAssoc());

        WorkOrderDataVector workOrders = assetDetailView.getAssetWorkOrderAssoc();
        //contains work order total cost for asset
        //key is workOrderId,object is BigDecimal actual total cost
        int assetId = assetDetailView.getAssetDetailData().getAssetData().getAssetId();
        //HashMap woTotalCostMap = woEjb.getWoTotalCostMapForAsset(Utility.toIdVector(getNotCancelled(workOrders)), assetId);
        //get 0.00 total sum for cancelled work orders
        HashMap woTotalCostMap = woEjb.getWoTotalCostMapForAsset(workOrders, assetId);
        form.setWorkOrderActualCostMap(woTotalCostMap);
        form.setWorkOrderTotalCost(WorkOrderUtil.costSum(woTotalCostMap));
        form.setAssetWorkOrderAssoc(workOrders);
        form.setLinkedStores(assetEjb.getMasterAssetLinkedStores(assetId));
        form.setIsEditable(assetEjb.canEditMasterAsset(assetId, appUser.getUserStore().getStoreId()));
        //form.setIsEditable(false);

        form.setContents(assetDetailView.getContents());
        form.setAssetLocationData(assetDetailView.getLocation());
        form.setAssociations(assetDetailView.getAssocDataVector());

        AssetDetailData assetDetail = assetDetailView.getAssetDetailData();

        form.setLongDesc(assetDetail.getLongDesc());
        form.setCustomDesc(assetDetail.getCustomDesc());
        form.setInactiveReason(assetDetail.getInactiveReason());
        form.setAcquisitionDate(assetDetail.getAcquisitionDate());
        form.setDateInService(assetDetail.getDateInService());
        form.setAcquisitionCost(assetDetail.getAcquisitionCost());
        form.setDateLastHMR(assetDetail.getDateLastHMR());
        form.setLastHMR(assetDetail.getLastHMR());

        form.setAcquisitionDate(convertDateValueForRead(Locale.US, request, form.getAcquisitionDate()));
        form.setDateInService(convertDateValueForRead(Locale.US, request, form.getDateInService()));
        form.setDateLastHMR(convertDateValueForRead(Locale.US, request, form.getDateLastHMR()));

        form.setAssetId(assetDetailView.getAssetDetailData().getAssetData().getAssetId());

        int masterAssetIdInt = 0;
        String assetIdStr = request.getParameter("masterAssetId");
        try {
            masterAssetIdInt = Integer.parseInt(assetIdStr);
        } catch (NumberFormatException e) {
            masterAssetIdInt = form.getAssetData().getMasterAssetId();
        }

        //set AssetNumber

        // make a link to the Master Asset
        AssetDetailData masterAssetDD = null;
        AssetContentViewVector currentAssetContentVV = assetDetailView.getContents();
        if (masterAssetIdInt > 0) {
            IdVector storeIds = new IdVector();
            storeIds.add(Integer.valueOf(appUser.getUserStore().getStoreId()));

            masterAssetDD = assetEjb.getAssetDetailData(masterAssetIdInt, storeIds, null);

            if (masterAssetDD != null) {
                AssetData newAssetData = form.getAssetData();
                AssetData masterAssetData = masterAssetDD.getAssetData();

                newAssetData.setShortDesc(masterAssetData.getShortDesc());
                newAssetData.setParentId(masterAssetData.getParentId());
                newAssetData.setManufId(masterAssetData.getManufId());
                newAssetData.setManufName(masterAssetData.getManufName());
                newAssetData.setManufSku(masterAssetData.getManufSku());
                newAssetData.setModelNumber(masterAssetData.getModelNumber());
                newAssetData.setMasterAssetId(masterAssetData.getAssetId());

                String longDescription = masterAssetDD.getLongDesc().getValue();
                form.getLongDesc().setValue(longDescription);
                currentAssetContentVV = assetEjb.getAssetContents(masterAssetData.getAssetId());
            }
        }

        AssetContentDetailView assetMainImg = getAssetImage(assetEjb, currentAssetContentVV);
        form.setMainAssetImage(assetMainImg);
        if (assetMainImg != null && assetMainImg.getContent() != null) {
            String tempFileName = IOUtilities.convertToTempFile(assetMainImg.getContent().getData(), assetMainImg.getContent().getPath(), "Asset");
            form.setMainAssetImageName(tempFileName);
        } else {
            form.setMainAssetImageName("");
        }

        WarrantyDataVector result = warrantyEjb.getWarrantiesForStore(appUser.getUserStore().getStoreId());
        form.setStoreWarranties(result);

        result = form.getAssetWarrantyAssoc();
        int size = result.size();
        String[] warrantyIds = new String[size];
        WarrantyData currWarranty;
        for(int i = 0; i < size; i++) {
            currWarranty = (WarrantyData)result.get(i);
            warrantyIds[i] = String.valueOf(currWarranty.getWarrantyId());
        }
        form.setAssetWarrantyIds(warrantyIds);
    }

    private static WorkOrderDataVector getNotCancelled(WorkOrderDataVector worOrders) {
        WorkOrderDataVector result = null;
        if(worOrders!=null){
            result  = new WorkOrderDataVector();
            Iterator it = worOrders.iterator();
            while(it.hasNext()){
                WorkOrderData workOrder = (WorkOrderData) it.next();
                if(!RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(workOrder.getStatusCd())){
                    result.add(workOrder);
                }
            }
        }
        return result;
    }

    public static boolean matchAssetLocation(AddressData assetLocationData, AddressData siteAddress) {
        if (assetLocationData != null && siteAddress != null) {

            String assetLocA1 = assetLocationData.getAddress1();
            String assetLocA2 = assetLocationData.getAddress2() == null ? "" : assetLocationData.getAddress2();
            String assetLocA3 = assetLocationData.getAddress3() == null ? "" : assetLocationData.getAddress3();
            String assetLocA4 = assetLocationData.getAddress4() == null ? "" : assetLocationData.getAddress4();

            String siteA1 = siteAddress.getAddress1();
            String siteA2 = siteAddress.getAddress2() == null ? "" : siteAddress.getAddress2();
            String siteA3 = siteAddress.getAddress3() == null ? "" : siteAddress.getAddress3();
            String siteA4 = siteAddress.getAddress4() == null ? "" : siteAddress.getAddress4();

            return Utility.stringMatch(assetLocationData.getCity(), siteAddress.getCity(), 40)
                    && Utility.stringMatch(assetLocationData.getStateProvinceCd(), siteAddress.getStateProvinceCd(), 2)
                    && Utility.stringMatch(assetLocA1, siteA1, 40)
                    && assetLocA2.equals(siteA2)
                    && assetLocA3.equals(siteA3)
                    && assetLocA4.equals(siteA4);
        }
        return false;

    }

    private static int getSiteIdfromRequest(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {
        String siteIdStr = request.getParameter("siteId");
        int siteIdInt = -1;
        try {
            siteIdInt = Integer.parseInt(siteIdStr);
        } catch (NumberFormatException e) {
            if (siteIdStr == null && pForm != null) {
                siteIdInt = pForm.getSitetId();
            }
        }
        return siteIdInt;
    }

    private static int getAssetIdFromRequest(HttpServletRequest request, UserAssetProfileMgrForm form) throws Exception {

        String assetIdStr = request.getParameter("assetId");
        int assetIdInt = -1;
        try {
            assetIdInt = Integer.parseInt(assetIdStr);
        }
        catch (NumberFormatException e) {
            if (assetIdStr == null && form.getAssetData() != null) {
                assetIdInt = form.getAssetData().getAssetId();
            }
        }
        return assetIdInt;
    }

    public static AssetAssocDataVector getActiveAssocDataVector(AssetAssocDataVector assetAssocDV) {
        AssetAssocDataVector result = new AssetAssocDataVector();
        if (assetAssocDV != null) {
            Iterator it = assetAssocDV.iterator();
            while (it.hasNext()) {
                AssetAssocData assetAssoc = (AssetAssocData) it.next();
                if (RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE.equals(assetAssoc.getAssetAssocStatusCd())) {
                    result.add(assetAssoc);
                }
            }
        }
        return result;
    }


    public static ActionErrors init(HttpServletRequest request, UserAssetContentMgrForm pForm) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        pForm = new UserAssetContentMgrForm();
        session.setAttribute(USER_ASSET_CONTENT_MGR_FORM, pForm);
        UserAssetProfileMgrForm detailForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);

        ae=checkRequest(request, detailForm);
        ae.add(checkRequest(request, pForm));
        if(ae.size()>0){
            return ae;
        }

        pForm.setAsset(detailForm.getAssetData());
        session.setAttribute(USER_ASSET_CONTENT_MGR_FORM, pForm);

        return ae;
    }


    public static ActionErrors getContentDetail(HttpServletRequest request, UserAssetContentMgrForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();
        int assetContentIdInt = getAssetContentIdFromRequest(request,pForm);

        ae=init(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        UserAssetProfileMgrForm detailForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);
        pForm = (UserAssetContentMgrForm) session.getAttribute(USER_ASSET_CONTENT_MGR_FORM);

        AssetContentView content = findAssetContent(detailForm.getContents(), assetContentIdInt);

        if (content == null ) {
            throw new Exception("Can't find content.AssetContentId:" + assetContentIdInt);
        }
        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();

        AssetContentDetailView contentDetails = assetEjb.getAssetContentDetails(assetContentIdInt);

        uploadContentDetailData(request,contentDetails,pForm);


        session.setAttribute(USER_ASSET_CONTENT_MGR_FORM, pForm);
        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, UserAssetContentMgrForm pForm) { }

    private static void uploadContentDetailData(HttpServletRequest request,AssetContentDetailView content, UserAssetContentMgrForm form) {

        form.setAssetId(content.getAssetContentData().getAssetId());
        form.setAssetContentId(content.getAssetContentData().getAssetContentId());
        form.setUrl(content.getAssetContentData().getUrl());
        form.setAssetAddBy(content.getAssetContentData().getAddBy());
        form.setAssetModBy(content.getAssetContentData().getModBy());
        form.setAssetAddDate(content.getAssetContentData().getAddDate());
        form.setAssetModDate(content.getAssetContentData().getModDate());

        form.setContentId(content.getContent().getContentId());
        form.setBusEntityId(content.getContent().getBusEntityId());
        form.setItemId(content.getContent().getItemId());
        form.setContentStatusCd(Utility.strNN(content.getContent().getContentStatusCd()));
        form.setContentTypeCd(Utility.strNN(content.getContent().getContentTypeCd()));
        form.setContentUsageCd(Utility.strNN(content.getContent().getContentUsageCd()));
        form.setLanguageCd(Utility.strNN(content.getContent().getLanguageCd()));
        form.setLocaleCd(Utility.strNN(content.getContent().getLocaleCd()));
        form.setLongDesc(Utility.strNN(content.getContent().getLongDesc()));
        form.setShortDesc(Utility.strNN(content.getContent().getShortDesc()));
        form.setSourceCd(Utility.strNN(content.getContent().getSourceCd()));
        form.setTypeCd(Utility.strNN(content.getAssetContentData().getTypeCd()));

        if (content.getContent().getEffDate() != null) {
            try {
                form.setEffDate(ClwI18nUtil.formatDateInp(request,content.getContent().getEffDate()));
            } catch (Exception e) {
                form.setEffDate(content.getContent().getEffDate().toString());
            }
        } else {
            form.setEffDate("");
        }

        if (content.getContent().getExpDate() != null) {
            try {
                form.setExpDate(ClwI18nUtil.formatDateInp(request,content.getContent().getExpDate()));
            } catch (Exception e) {
                form.setExpDate(content.getContent().getExpDate().toString());
            }
        } else {
            form.setExpDate("");
        }

        form.setData(content.getContent().getData());
        form.setPath(Utility.strNN(content.getContent().getPath()));
        form.setVersion(String.valueOf(content.getContent().getVersion()));

        if(content.getContent().getData()!=null){
            form.setContentSize(String.valueOf(content.getContent().getData().length)+" bytes");
        }

        form.setContentAddBy(content.getContent().getAddBy());
        form.setContentModBy(content.getContent().getModBy());
        form.setContentAddDate(content.getContent().getAddDate());
        form.setContentModDate(content.getContent().getModDate());
    }

    private static AssetContentView findAssetContent(AssetContentViewVector contents, int assetContentIdInt) {
        if (contents != null) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                AssetContentView content = ((AssetContentView) it.next());
                if (content != null &&
                        content.getAssetContentData()!=null &&
                        content.getAssetContentData().getAssetContentId()==assetContentIdInt) {
                    return content;
                }
            }
        }
        return null;
    }

    private static int getAssetContentIdFromRequest(HttpServletRequest request, UserAssetContentMgrForm pForm) throws Exception {

        String assetContentIdStr = request.getParameter("assetContentId");
        int assetContentIdInt = -1;
        try {
            assetContentIdInt = Integer.parseInt(assetContentIdStr);
        } catch (NumberFormatException e) {
            if (pForm.getAsset() != null) {
                return assetContentIdInt = pForm.getAssetContentId();
            }
        }
        return assetContentIdInt;
    }

    public static ActionErrors creteNewContent(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();
        ae = init(request, (UserAssetContentMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        UserAssetContentMgrForm form = (UserAssetContentMgrForm) session.getAttribute(USER_ASSET_CONTENT_MGR_FORM);
        UserAssetProfileMgrForm detailForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);

        form.setAsset(detailForm.getAssetData());
        AssetContentDetailView content = new AssetContentDetailView(ContentDetailView.createValue(), AssetContentData.createValue());
        content.getAssetContentData().setAssetId(form.getAsset().getAssetId());

        uploadContentDetailData(request,content, form);

        session.setAttribute(USER_ASSET_CONTENT_MGR_FORM, form);

        return ae;
    }


    private static void refreshFormData(HttpServletRequest request, UserAssetContentMgrForm pForm) throws Exception {
        HttpSession session = request.getSession();
        try {
            UserAssetProfileMgrForm detForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);

            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();

            AssetContentViewVector contents    = assetEJB.getAssetContents(detForm.getAssetData().getAssetId());

            detForm.setContents(contents);
            session.setAttribute(USER_ASSET_PROFILE_MGR_FORM,detForm);

        } catch (Exception e) {
            throw new Exception("Detail data can't be refreshed");
        }

    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserAssetContentMgrForm form) {
        ActionErrors ae = new ActionErrors();

        if (!Utility.isSet(form.getShortDesc())) {
            ae.add("ShortDesc", new ActionError("variable.empty.error", "Description"));
        }

        try {
            Integer.parseInt(form.getVersion());
        } catch (NumberFormatException e) {
            ae.add("Version", new ActionError("error.simpleGenericError", "Invalid Number"));
        }

        if (Utility.isSet(form.getExpDate())) {
            try {
                ClwI18nUtil.parseDateInp(request,form.getExpDate());
            } catch (ParseException e) {
                e.printStackTrace();
                ae.add("ExpDate", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        if (Utility.isSet(form.getEffDate())) {
            try {
                ClwI18nUtil.parseDateInp(request,form.getEffDate());
            } catch (ParseException e) {
                e.printStackTrace();
                ae.add("EffDate", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        if (Utility.isSet(form.getLongDesc())) {
            if(form.getLongDesc().length() > 255) {
                String fieldName = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assocDocs.longDesc");
                    if (fieldName == null) {
                        fieldName = "Long Description";
                    }
                    ae.add("Long Description", new ActionError("variable.long.error", fieldName));
            }
        }
        if (form.getUploadNewFile() != null) {
            if (Utility.isSet(form.getUploadNewFile().getFileName())) {
                try {
                    byte[] fileData = form.getUploadNewFile().getFileData();
                    if (fileData.length == 0) {
                        ae.add("File not exists", new ActionError("error.fileCanNotBeFound", form.getUploadNewFile().getFileName()));
                        return ae;
                    }
                } catch (Exception e) {
                    ae.add("File not exists", new ActionError("error.fileCanNotBeFound", form.getUploadNewFile().getFileName()));
                    return ae;
                }
            }
        }
        return ae;
    }

    public static ActionErrors updateContentData(HttpServletRequest request, UserAssetContentMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkFormAttribute(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();

        AssetContentDetailView assetContent = AssetContentDetailView.createValue();
        assetContent = loadContentDetailData(request,assetContent, pForm);
        assetContent = assetEjb.updateAssetContent(assetContent, appUser.getUser());
        refreshFormData(request, pForm);
        uploadContentDetailData(request,assetContent, pForm);
        doParentChildStoreSynchronization(appUser, assetContent.getAssetContentData().getAssetId());
        return getContentDetail(request, pForm);

    }

    private static AssetContentDetailView loadContentDetailData(HttpServletRequest request,AssetContentDetailView assetContent, UserAssetContentMgrForm form) throws Exception {

        AssetContentData assetContentData = AssetContentData.createValue();
        assetContentData.setAssetId(form.getAssetId());
        assetContentData.setAssetContentId(form.getAssetContentId());
        assetContentData.setUrl(form.getUrl());
        assetContentData.setAddBy(form.getAssetAddBy());
        assetContentData.setModBy(form.getAssetModBy());
        assetContentData.setAddDate(form.getAssetAddDate());
        assetContentData.setModDate(form.getAssetModDate());
        if (!Utility.isSet(form.getTypeCd())) {
            assetContentData.setTypeCd(RefCodeNames.ASSET_CONTENT_TYPE_CD.SPEC);
        } else {
            assetContentData.setTypeCd(form.getTypeCd());
        }
        ContentDetailView content = ContentDetailView.createValue();
        content.setContentId(form.getContentId());
        content.setBusEntityId(form.getBusEntityId());
        content.setItemId(form.getItemId());

        if(!Utility.isSet(form.getContentStatusCd())){
            content.setContentStatusCd(RefCodeNames.CONTENT_STATUS_CD.ACTIVE);
        } else{
            content.setContentStatusCd(form.getContentStatusCd());
        }

        if(!Utility.isSet(form.getLanguageCd())){
            content.setLanguageCd("x");
        } else{
            content.setLanguageCd(form.getLanguageCd());
        }

        if(!Utility.isSet(form.getLocaleCd())){
            content.setLocaleCd("x");
        } else{
            content.setLocaleCd(form.getLocaleCd());
        }

        if(!Utility.isSet(form.getContentUsageCd())){
            content.setContentUsageCd("N/A");
        } else{
            content.setContentUsageCd(form.getContentUsageCd());
        }

        if(!Utility.isSet(form.getSourceCd())){
            content.setSourceCd("N/A");
        } else{
            content.setSourceCd(form.getSourceCd());
        }

        content.setLongDesc(form.getLongDesc());
        content.setShortDesc(form.getShortDesc());
        content.setAddBy(form.getContentAddBy());
        content.setModBy(form.getContentModBy());
        content.setAddDate(form.getContentAddDate());
        content.setModDate(form.getContentModDate());

        if (form.getUploadNewFile() != null && Utility.isSet(form.getUploadNewFile().getFileName())) {
            content.setPath(form.getUploadNewFile().getFileName());
            content.setData(form.getUploadNewFile().getFileData());
            content.setContentTypeCd(form.getUploadNewFile().getContentType());
        } else {
            if (form.getData() != null) {
                content.setPath(form.getPath());
                content.setData(form.getData());
                if(!Utility.isSet(form.getContentTypeCd())){
                    content.setContentTypeCd("N/A");
                } else{
                    content.setContentTypeCd(form.getContentTypeCd());
                }

            } else {
                content.setData(new byte[0]);
                content.setPath("");
                content.setContentTypeCd("N/A");
            }
        }

        if (Utility.isSet(form.getExpDate())) {
            try {
                content.setExpDate(ClwI18nUtil.parseDateInp(request,form.getExpDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                content.setExpDate(null);
            }
        } else {
            content.setExpDate(null);
        }

        if (Utility.isSet(form.getEffDate())) {
            try {
                content.setEffDate(ClwI18nUtil.parseDateInp(request,form.getEffDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                content.setEffDate(null);
            }
        } else {
            content.setEffDate(null);
        }

        content.setVersion(Integer.parseInt(form.getVersion()));

        assetContent = new AssetContentDetailView(content, assetContentData);
        return assetContent;
    }

    public static ActionErrors readDocument(HttpServletRequest request, HttpServletResponse response, UserAssetContentMgrForm pForm) throws Exception {
        ActionErrors ae = getContentDetail(request,pForm);
        if(ae.size()>0) {
            return ae;
        }

        pForm = (UserAssetContentMgrForm) request.getSession().getAttribute(USER_ASSET_CONTENT_MGR_FORM);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(pForm.getData());
        response.setContentType(pForm.getContentTypeCd());
        response.setContentLength(out.size());
        out.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return ae;
    }


    public static ActionErrors removeAssetContent(HttpServletRequest request, UserAssetContentMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();

        int acId = pForm.getAssetContentId();
        int cId = pForm.getContentId();
        boolean removeFlag = assetEjb.removeAssetContent(acId, cId);
        if (removeFlag) {
            refreshFormData(request, pForm);
            ae = creteNewContent(request, pForm);
        }

        return ae;
    }



    public static ActionErrors saveAsset(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        ae = checkFormAttribute(request, appUser, pForm, assetEJB);
        if (ae.size() > 0) {
            return ae;
        }

        AssetDetailData assetDetail = AssetDetailData.createValue();
        int assetId = pForm.getAssetData().getAssetId();

        loadDetailData(request,assetDetail, pForm);

        try {
            assetDetail = assetEJB.updateAssetDetailData(assetDetail, appUser.getUser().getUserName());
        } catch (Exception e) {
            return ae;
        }

        if (!pForm.getUserAssignedAssetNumber()) {
            AssetData assetD = assetDetail.getAssetData();
            String assetNum = assetD.getAssetNum();
            int i = 0;
            StringBuffer buf = new StringBuffer(assetNum);
            while (isDuplicateAssetNumber(request,
                                          assetEJB,
                                          assetD.getAssetId(),
                                          buf.toString(),
                                          appUser.getUserAccount().getAccountId()) > 0) {
                buf.setLength(0);
                buf.append(assetNum);
                buf.append("_");
                i++;
                buf.append(i);
            }
            if (i > 0) {
                assetD.setAssetNum(buf.toString());
                assetEJB.updateAssetData(assetD, appUser.getUser().getUserName());
            }
        }

        if (assetId == 0 && assetDetail.getAssetData().getAssetId() > 0) {
            addAssociationWithStore(pForm, assetDetail, appUser.getUserStore().getStoreId());
        }

        if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetDetail.getAssetData().getAssetTypeCd())
                && (assetId == 0 && assetDetail.getAssetData().getAssetId() > 0)){
            addAssociationWithSite(pForm,appUser.getSite().getSiteId());
        }
        /* updates Assoc Data */
        if (assetDetail.getAssetData().getAssetId() > 0) {
            AssetAssocDataVector result = assetEJB.updateAssetAssocDataVector(assetDetail.getAssetData().getAssetId(),
                    pForm.getAssociations(),
                    appUser.getUser().getUserName());
        }

        if (pForm.getImageFile() != null) {
            setNewAssetImage(assetEJB, assetDetail.getAssetData(), pForm.getImageFile(), pForm.getMainAssetImage(), appUser.getUser());
        }

        pForm.setAssetData(assetDetail.getAssetData());
        pForm.setAssetId(assetDetail.getAssetData().getAssetId());

        ae = getAssetProfile(request, pForm);

        //-------------------------------------------------------------------------------------------------
        // correct BreadCrumb
        BreadCrumbNavigator breadCrambNav = (BreadCrumbNavigator) request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR);
        //String fileSeparator = System.getProperty("file.separator");
        if (breadCrambNav != null) {
            BreadCrumbItem parent = breadCrambNav.getItem(breadCrambNav.getCursor());
            String name = parent.getName();
            if ("userAssetProfile".equals(name) || "t_userAssetProfile".equals(name)) {
                String href = parent.getHref();
                if ((href != null) && (href.indexOf("userAssets.do?action=") > -1)) {
                    int index = href.lastIndexOf("/");
                    String head = "";
                    if (index > -1) {
                        head = href.substring(0, index);
                    }
                    StringBuffer assetHref = new StringBuffer(head);
                        assetHref.append("/");
                        assetHref.append("userAssetProfile.do?action=assetdetail&assetId=");
                        assetHref.append(pForm.getAssetId());
                        assetHref.append("&siteId=");
                        assetHref.append(pForm.getSitetId());
                    String tail = "";
                    index = href.lastIndexOf("&");
                    if (index > -1) {
                        tail = href.substring(index, href.length());
                    }
                        assetHref.append(tail);
                    parent.setHref(assetHref.toString());
                }
            }
        }
        //-------------------------------------------------------------------------------------------------

        return ae;

    }

    public static ActionErrors saveAssetWarrantyConfig(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();

        int currentAssetId = pForm.getAssetData().getAssetId();
        IdVector assetIds = warrantyEjb.getAssetWarrantyIdOnly(currentAssetId);
        warrantyEjb.removeAssetWarranty(assetIds);

        AssetWarrantyData assetWarrantyData;
        String[] assetWarrantyIds = pForm.getAssetWarrantyIds();
        int warrantyId;
        for (int i = 0; i < assetWarrantyIds.length; i++) {
            warrantyId = Integer.parseInt(assetWarrantyIds[i]);
            assetWarrantyData = AssetWarrantyData.createValue();
            assetWarrantyData.setAssetWarrantyId(0);
            assetWarrantyData.setAssetId(currentAssetId);
            assetWarrantyData.setWarrantyId(warrantyId);
            assetWarrantyData.setAddBy(appUser.getUserName());
            assetWarrantyData.setModBy(appUser.getUserName());
            assetWarrantyData.setAddDate(new Date());
            assetWarrantyData.setModDate(new Date());
            warrantyEjb.insertAssetWarranty(assetWarrantyData);
        }

       ae = getAssetProfile(request, pForm);

       return ae;
    }

    public static ActionErrors saveMasterAsset(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();

        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();
        Manufacturer manufacturerEJB = factory.getManufacturerAPI();

        ae = checkMasterAssetFormAttribute(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        //check uniquety in the current store
        int assetId = Integer.valueOf(pForm.getAssetData().getAssetId());
        AssetSearchCriteria crit = new AssetSearchCriteria();
        // get current manuf name
        String currentManufName = "";
        int currentManufId = pForm.getAssetData().getManufId();
        PairView manufPair;
        for (int i = 0; i < pForm.getManufIdNamePairs().size(); i++) {
            manufPair = (PairView)pForm.getManufIdNamePairs().get(i);
            if (currentManufId == ((Integer)manufPair.getObject1()).intValue()) {
                currentManufName = (String)manufPair.getObject2();
                break;
            }
        }
        String [] otherNamesArray = null;
        BusEntityDataVector manufs = manufacturerEJB.getManufacturerByName(currentManufName, storeId);
        if (!manufs.isEmpty()) {
            int manufId = ((BusEntityData)manufs.get(0)).getBusEntityId();

            PropertyDataVector props = manufacturerEJB.getManufacturerProps(manufId);
            if (!props.isEmpty()) {
                String otherNames = Utility.getPropertyValue(props, RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
                if (Utility.isSet(otherNames)) {
                    otherNamesArray = otherNames.split("\r\n");
                }
            }
        }
        crit = new AssetSearchCriteria();
        if (otherNamesArray != null) {
            crit.setManufOtherNames(otherNamesArray);
        }
        crit.setManufName(currentManufName);
        crit.setManufSku(pForm.getAssetData().getManufSku());
        crit.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET);
        IdVector store = new IdVector();
        store.add(Integer.valueOf(storeId));
        crit.setStoreIds(store);
        IdVector assetIds = assetEJB.checkAssetStoreUnique(crit);
        if (!assetIds.isEmpty() && !assetIds.contains(assetId)) {
            String mess = "";
            if (assetId == 0) {
                mess = ClwI18nUtil.getMessageOrNull(request, "error.store.assetManuf.notUnique");
            } else {
                mess = ClwI18nUtil.getMessageOrNull(request, "error.store.assetManuf.notUniqueUpdate");
            }
            ae.add("AssetManufNotUnique", new ActionError("error.simpleError", mess));
            return ae;
        }

        AssetDetailData assetDetail = AssetDetailData.createValue();

        loadDetailData(request, assetDetail, pForm);

        HashMap changes = getMasterAssetChangedFields(assetId, assetEJB, pForm);

        try {
            assetDetail = assetEJB.updateAssetDetailData(assetDetail, appUser.getUser().getUserName());
        } catch (Exception e) {
            return ae;
        }

        if (assetId == 0 && assetDetail.getAssetData().getAssetId() > 0) {
            addAssociationWithStore(pForm, assetDetail, appUser.getUserStore().getStoreId());
        }

        /* updates Assoc Data */
        if (assetDetail.getAssetData().getAssetId() > 0) {
            AssetAssocDataVector result = assetEJB.updateAssetAssocDataVector(assetDetail.getAssetData().getAssetId(),
                    pForm.getAssociations(),
                    appUser.getUser().getUserName());
        }

        if (pForm.getImageFile() != null) {
            setNewAssetImage(assetEJB, assetDetail.getAssetData(), pForm.getImageFile(), pForm.getMainAssetImage(), appUser.getUser());
        }

        //update all the changed fields in the linked assets
        if (!changes.isEmpty()) {
            //get all linked assets
            crit = new AssetSearchCriteria();
            crit.setMasterAssetId(assetId);
            crit.setShowInactive(true);
            AssetDataVector linkedAssets = assetEJB.getAssetDataByCriteria(crit);

            Iterator it = linkedAssets.iterator();
            AssetData assetD;
            Object value;
            boolean updateAsset;
            while (it.hasNext()) {
                updateAsset = false;
                assetD = (AssetData) it.next();
                if ((value = changes.get(AssetDataAccess.SHORT_DESC)) != null) {
                    assetD.setShortDesc((String)value);
                    updateAsset = true;
                }
                if ((value = changes.get(AssetDataAccess.PARENT_ID)) != null) {
                    assetD.setParentId(((Integer)value).intValue());
                    updateAsset = true;
                }
                if ((value = changes.get(AssetDataAccess.MANUF_ID)) != null) {
                    assetD.setManufId(((Integer)value).intValue());
                    updateAsset = true;
                }
                if ((value = changes.get(AssetDataAccess.MANUF_SKU)) != null) {
                    assetD.setManufSku((String)value);
                    updateAsset = true;
                }
                if ((value = changes.get(AssetDataAccess.MODEL_NUMBER)) != null) {
                    assetD.setModelNumber((String)value);
                    updateAsset = true;
                }
                if ((value = changes.get(AssetDataAccess.STATUS_CD)) != null) {
                    assetD.setStatusCd((String)value);
                    updateAsset = true;
                }
                if (updateAsset) {
                    assetEJB.updateAssetData(assetD, appUser.getUserName());
                }
                //--------------------------------------------------------------
                if ((value = changes.get(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC)) != null) {
                    AssetDetailData assetDD = assetEJB.getAssetDetailData(assetD.getAssetId(), null, null);
                    AssetPropertyData longDesc = assetDD.getLongDesc();
                    longDesc.setValue((String)value);
                    assetEJB.updateAssetPropertyData(longDesc, appUser.getUserName());
                }
                //--------------------------------------------------------------
                if ((value = changes.get(RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE)) != null) {
                    setNewAssetImage(assetEJB, assetD, (FormFile)value, null, appUser.getUser());
                }
            }
        }

        pForm.setAssetData(assetDetail.getAssetData());
        pForm.setAssetId(assetDetail.getAssetData().getAssetId());

        ae = getAssetProfile(request, pForm);

        return ae;
    }

    public static ActionErrors saveStoreMasterAsset(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {
        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = appUser.getUserStore().getStoreId();

        ae = checkUserRight(request, appUser);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();
        Manufacturer manufacturerEJB = factory.getManufacturerAPI();

        ae = checkStoreMasterAssetFormAttribute(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        //check uniquety in the current store
        int assetId = Integer.valueOf(pForm.getAssetData().getAssetId());
        AssetSearchCriteria crit = new AssetSearchCriteria();
        // get current manuf name
        String currentManufName = "";
        int currentManufId = pForm.getAssetData().getManufId();
        PairView manufPair;
        for (int i = 0; i < pForm.getManufIdNamePairs().size(); i++) {
            manufPair = (PairView)pForm.getManufIdNamePairs().get(i);
            if (currentManufId == ((Integer)manufPair.getObject1()).intValue()) {
                currentManufName = (String)manufPair.getObject2();
                break;
            }
        }
        String [] otherNamesArray = null;
        BusEntityDataVector manufs = manufacturerEJB.getManufacturerByName(currentManufName, storeId);
        if (!manufs.isEmpty()) {
            int manufId = ((BusEntityData)manufs.get(0)).getBusEntityId();

            PropertyDataVector props = manufacturerEJB.getManufacturerProps(manufId);
            if (!props.isEmpty()) {
                String otherNames = Utility.getPropertyValue(props, RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
                if (Utility.isSet(otherNames)) {
                    otherNamesArray = otherNames.split("\r\n");
                }
            }
        }
        crit = new AssetSearchCriteria();
        if (otherNamesArray != null) {
            crit.setManufOtherNames(otherNamesArray);
        }
        crit.setManufName(currentManufName);
        crit.setManufSku(pForm.getAssetData().getManufSku());
        crit.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET);
        IdVector store = new IdVector();
        store.add(Integer.valueOf(storeId));
        crit.setStoreIds(store);
        IdVector assetIds = assetEJB.checkAssetStoreUnique(crit);
        if (!assetIds.isEmpty() && !assetIds.contains(assetId)) {
            String mess = "";
            if (assetId == 0) {
                mess = ClwI18nUtil.getMessageOrNull(request, "error.store.assetManuf.notUnique");
            } else {
                mess = ClwI18nUtil.getMessageOrNull(request, "error.store.assetManuf.notUniqueUpdate");
            }
            ae.add("AssetManufNotUnique", new ActionError("error.simpleError", mess));
            return ae;
        }

        if (storeD != null) {
            String parentStoreIdStr = Utility.getPropertyValue(storeD.getMiscProperties(),
                                        RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
            if (Utility.isSet(parentStoreIdStr)) {
                int parentStoreId = 0;
                try {
                    parentStoreId = Integer.parseInt(parentStoreIdStr);
                } catch (NumberFormatException e) { }

                if (parentStoreId > 0) {
                    //get manuf other names
                    otherNamesArray = null;
                    manufs = manufacturerEJB.getManufacturerByName(currentManufName, parentStoreId);
                    if (!manufs.isEmpty()) {
                        int manufId = ((BusEntityData)manufs.get(0)).getBusEntityId();

                        PropertyDataVector props = manufacturerEJB.getManufacturerProps(manufId);
                        if (!props.isEmpty()) {
                            String otherNames = Utility.getPropertyValue(props, RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
                            if (Utility.isSet(otherNames)) {
                                otherNamesArray = otherNames.split("\r\n");
                            }
                        }
                    }
                    crit = new AssetSearchCriteria();
                    if (otherNamesArray != null) {
                        crit.setManufOtherNames(otherNamesArray);
                    }
                    crit.setManufName(currentManufName);
                    crit.setManufSku(pForm.getAssetData().getManufSku());
                    crit.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET);
                    store = new IdVector();
                    store.add(Integer.valueOf(parentStoreId));
                    crit.setStoreIds(store);
                    // get the same Master Assets from the Parent Store
                    assetIds = assetEJB.checkAssetStoreUnique(crit);
                    if (!assetIds.isEmpty()) { // there are the same Master Assets in the Parent Store
                        String mess = "";
                        if (assetId == 0) {
                            mess = ClwI18nUtil.getMessageOrNull(request, "error.store.assetManuf.ParentMasterAssetExsist");
                        } else {
                            mess = ClwI18nUtil.getMessageOrNull(request, "error.store.assetManuf.ParentMasterAssetNotUniqueUpdate");
                        }
                        ae.add("AssetManufNotUnique", new ActionError("error.simpleError", mess));
                        return ae;
                    }
                }
            }
        }

        AssetDetailData assetDetail = AssetDetailData.createValue();

        loadDetailData(request, assetDetail, pForm);

        try {
            assetDetail = assetEJB.updateAssetDetailData(assetDetail, appUser.getUser().getUserName());
        } catch (Exception e) {
            return ae;
        }

        if (assetId == 0 && assetDetail.getAssetData().getAssetId() > 0) {
            addAssociationWithStore(pForm, assetDetail, appUser.getUserStore().getStoreId());
        }

        /* updates Assoc Data */
        if (assetDetail.getAssetData().getAssetId() > 0) {
            assetEJB.updateAssetAssocDataVector(assetDetail.getAssetData().getAssetId(),
                                                pForm.getAssociations(),
                                                appUser.getUser().getUserName());
        }

        if (pForm.getImageFile() != null) {
            if (Utility.isSet(pForm.getImageFile().getFileName())) {
                setNewAssetImage(assetEJB, assetDetail.getAssetData(), pForm.getImageFile(), pForm.getMainAssetImage(), appUser.getUser());
            }
        }

    	int masertAssetId = assetDetail.getAssetData().getAssetId();
        doParentChildStoreSynchronization(appUser, masertAssetId);


        pForm.setAssetData(assetDetail.getAssetData());
        pForm.setAssetId(assetDetail.getAssetData().getAssetId());

        ae = getAssetProfile(request, pForm);

        return ae;
    }

	private static void doParentChildStoreSynchronization(
			CleanwiseUser appUser, int assetId)
			throws APIServiceAccessException, NamingException, RemoteException {
		boolean isParentStore = Boolean.parseBoolean(Utility
				.getPropertyValue(appUser.getUserStore()
						.getMiscProperties(),
						RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE));
        if(isParentStore) {
			Store storeEJB =  new APIAccess().getStoreAPI();
			storeEJB.synchronizeMasterAsset(
					appUser.getUserStore().getStoreId(),
					assetId, appUser);
		}
	}

    private static HashMap getMasterAssetChangedFields (int masterAssetId,
                                                        Asset assetEJB,
                                                        UserAssetProfileMgrForm pForm) throws Exception {
        HashMap changedFields = new HashMap();

        if (masterAssetId > 0) {
            AssetDetailData assetDD = assetEJB.getAssetDetailData(masterAssetId, null, null);
            AssetContentViewVector assetCVV = assetEJB.getAssetContents(masterAssetId);
            AssetContentDetailView assetMainImg = getAssetImage(assetEJB, assetCVV);
            if (assetDD != null) {
                AssetData assetD = assetDD.getAssetData();
                AssetData assetFormD = pForm.getAssetData();

                if (!assetFormD.getShortDesc().equals(assetD.getShortDesc())) {
                    changedFields.put(AssetDataAccess.SHORT_DESC, assetFormD.getShortDesc());
                }
                if (assetFormD.getParentId() != assetD.getParentId()) {
                    changedFields.put(AssetDataAccess.PARENT_ID, Integer.valueOf(assetFormD.getParentId()));
                }
                if (assetFormD.getManufId() != assetD.getManufId()) {
                    changedFields.put(AssetDataAccess.MANUF_ID, Integer.valueOf(assetFormD.getManufId()));
                }
                if (!assetFormD.getManufSku().equals(assetD.getManufSku())) {
                    changedFields.put(AssetDataAccess.MANUF_SKU, assetFormD.getManufSku());
                }
                if (!assetFormD.getModelNumber().equals(assetD.getModelNumber())) {
                    changedFields.put(AssetDataAccess.MODEL_NUMBER, assetFormD.getModelNumber());
                }
                if (!assetFormD.getStatusCd().equals(assetD.getStatusCd())) {
                    changedFields.put(AssetDataAccess.STATUS_CD, assetFormD.getStatusCd());
                }
                if (pForm.getLongDesc() != null) {
                    if (assetDD.getLongDesc() != null) {
                        if (!pForm.getLongDesc().getValue().equals(assetDD.getLongDesc().getValue())) {
                            changedFields.put(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC, pForm.getLongDesc().getValue());
                        }
                    } else {
                        changedFields.put(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC, pForm.getLongDesc().getValue());
                    }
                }
                ContentDetailView imageContent = null;
                if (assetMainImg != null) {
                    imageContent = assetMainImg.getContent();
                }
                FormFile imageFormFile = pForm.getImageFile();
                if (imageFormFile != null && imageContent != null) {
                    if (!imageFormFile.getFileName().equals(imageContent.getPath()) ||
                        !imageFormFile.getContentType().equals(imageContent.getContentTypeCd())) {
                        changedFields.put(RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE, imageFormFile);
                    }
                } else if ((imageFormFile == null && imageContent != null) ||
                           (imageFormFile != null && imageContent == null)) {
                    changedFields.put(RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE, imageFormFile);
                }
            }
        }
        return changedFields;
    }

    public static ActionErrors saveAssetCategory(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = checkUserRight(request,appUser);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();

        String categoryShortDesc = pForm.getAssetData().getShortDesc().trim();
        int assetId = pForm.getAssetData().getAssetId();

        String field;
        if (!Utility.isSet(categoryShortDesc)) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assetCategoryName");
            if (field == null) {
                field = "Category Name";
            }
            ae.add("Category Name", new ActionError("variable.empty.error", field));
        } else if(categoryShortDesc.length() > 255) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assetCategoryName");
            if (field == null) {
                field = "Category Name";
            }
            ae.add("Category Name", new ActionError("variable.long.error", field));
 /* TBD (TCS Bug 4059 will be deferred)
          } else if (isDuplicateCategory(assetEJB, assetId, categoryShortDesc, appUser.getUserStore().getStoreId() ) > 0){
          field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assetCategoryName");
          if (field == null) {
              field = "Category Name";
          }
          ae.add("Category Name", new ActionError("userAssets.store.error.DuplicateAssetCategory", categoryShortDesc));
 */
        }
        if (ae.size() > 0) {
            return ae;
        }

        AssetDetailData assetDetail = AssetDetailData.createValue();


        loadDetailData(request, assetDetail, pForm);

        try {
            assetDetail = assetEJB.updateAssetDetailData(assetDetail, appUser.getUser().getUserName());
        } catch (Exception e) {
            return ae;
        }

        if (assetId == 0 && assetDetail.getAssetData().getAssetId() > 0) {
            addAssociationWithStore(pForm, assetDetail, appUser.getUserStore().getStoreId());
        }

        /* updates Assoc Data */
        if (assetDetail.getAssetData().getAssetId() > 0) {
            AssetAssocDataVector result = assetEJB.updateAssetAssocDataVector(assetDetail.getAssetData().getAssetId(),
                    pForm.getAssociations(),
                    appUser.getUser().getUserName());
        }

        pForm.setAssetData(assetDetail.getAssetData());
        pForm.setAssetId(assetDetail.getAssetData().getAssetId());

        //ae = getAssetProfile(request, pForm);

        if (assetId > 0) {
			try {
				doParentChildStoreSynchronization(appUser, assetId);
			} catch (Exception e) {
				ae.add("error", new ActionError("error.systemError",
						"Parent-Child store synchronization faild."));
			}
		}


        return ae;
    }

    public static ActionErrors deleteAssetCategory(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

         ActionErrors ae;
         ae = checkRequest(request, pForm);
         if (ae.size() > 0) {
             return ae;
         }

         HttpSession session = request.getSession();
         CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

         ae = checkUserRight(request,appUser);
         if (ae.size() > 0) {
             return ae;
         }
         String categoryShortDesc = pForm.getAssetData().getShortDesc().trim();

         APIAccess factory = new APIAccess();
         Asset assetEJB = factory.getAssetAPI();

         StoreData storeD = appUser.getUserStore();
         String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
         boolean parentStoreFl = Utility.isTrue(isParentStore, false);

         int categoryAssetId = pForm.getAssetData().getAssetId();  // category

         IdVector categoryChildAssetIdV = null;
         try {
           if (parentStoreFl){
             categoryChildAssetIdV  = assetEJB.getParentOrChildMasterAssetIds(categoryAssetId, AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID);
           }
           ae = checkCategoryNotInUse(assetEJB, categoryAssetId, categoryChildAssetIdV);
           if (ae.size() > 0) {
             return ae;
           }

           removeAssetDetailDataForCategory(assetEJB, categoryAssetId, categoryChildAssetIdV, appUser.getUser().getUserName());

          } catch (Exception e) {
             return ae;
         }

 //        pForm.setAssetData(null);
         ae = init(request, (UserAssetProfileMgrForm) null);
         if (ae.size() > 0) {
           return ae;
         }

         UserAssetProfileMgrForm detForm = (UserAssetProfileMgrForm) session.getAttribute(USER_ASSET_PROFILE_MGR_FORM);
         if (detForm == null) {
           ae.add("error", new ActionError("error.systemError", "Form not initialized"));
           return ae;
         }

         detForm.getAssetData().setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);

         session.setAttribute(USER_ASSET_PROFILE_MGR_FORM, detForm);


 //        pForm.setAssetId(0);

         //ae = getAssetProfile(request, pForm);

         return ae;
     }

    private static ActionErrors checkUserRight(HttpServletRequest request, CleanwiseUser appUser) {
        ActionErrors ae = new ActionErrors();

        if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            String errorMess = ClwI18nUtil.getMessageOrNull(request, "shop.errors.notAuthorizedForAssetManagement");
            if (!Utility.isSet(errorMess)) {
                errorMess = "Access is denied to asset management";
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        return  ae;
    }

    private static void addAssociationWithSite(UserAssetProfileMgrForm pForm, int siteId) {
        if (!vectorExistAssoc(pForm.getAssetData().getAssetId(),
                siteId, pForm.getAssociations(),
                RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE)) {
            //remove all  site associations
            removeAssetSiteAssociation(pForm.getAssociations());
            //create new assoc and  move asset to new assoc location
            AssetAssocData newAssetAssocData = createAssocObject(pForm.getAssetData().getAssetId(), siteId, RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
            AssetAssocDataVector curAssoc = pForm.getAssociations();
            if (curAssoc == null) {
                curAssoc = new AssetAssocDataVector();
            }
            curAssoc.add(newAssetAssocData);
            pForm.setAssociations(curAssoc);
        }
    }

    private static void removeAssetSiteAssociation(AssetAssocDataVector associations) {
        AssetAssocData result = null;
        if (associations != null) {
            Iterator it = associations.iterator();
            while (it.hasNext()) {
                AssetAssocData assetAssoc = (AssetAssocData) it.next();
                if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE.equals(assetAssoc.getAssetAssocCd())) {
                    it.remove();
                }
            }
        }
    }

    private static void addAssociationWithStore(UserAssetProfileMgrForm pForm, AssetDetailData assetDetailData, int storeId) throws Exception {

        AssetAssocData assetAssocData = AssetAssocData.createValue();
        assetAssocData.setAssetAssocCd(RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
        assetAssocData.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
        assetAssocData.setAssetId(assetDetailData.getAssetData().getAssetId());
        assetAssocData.setBusEntityId(storeId);
        pForm.getAssociations().add(assetAssocData);

    }

    private static void loadDetailData(HttpServletRequest request,
                                       AssetDetailData assetDetail,
                                       UserAssetProfileMgrForm pForm) {

        String manufName = null;
        if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(pForm.getAssetData().getAssetTypeCd())) {

            manufName = getManufNameById(pForm.getAssetData().getManufId(), pForm.getManufIdNamePairs());
            pForm.getAssetData().setManufName(manufName);
            pForm.getAssetData().setManufTypeCd(RefCodeNames.ASSET_MANUF_TYPE_CD.STORE);

            assetDetail.setAssetData(pForm.getAssetData());
            assetDetail.setLongDesc(pForm.getLongDesc());
            assetDetail.setCustomDesc(pForm.getCustomDesc());
            assetDetail.setInactiveReason(pForm.getInactiveReason());
            assetDetail.setAcquisitionDate(convertDateValueForWrite(request, Locale.US, pForm.getAcquisitionDate()));
            assetDetail.setDateInService(convertDateValueForWrite(request, Locale.US, pForm.getDateInService()));
            assetDetail.setAcquisitionCost(pForm.getAcquisitionCost());
            assetDetail.setDateLastHMR(convertDateValueForWrite(request, Locale.US, pForm.getDateLastHMR()));
            assetDetail.setLastHMR(pForm.getLastHMR());

        }else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(pForm.getAssetData().getAssetTypeCd())) {
            manufName = getManufNameById(pForm.getAssetData().getManufId(), pForm.getManufIdNamePairs());
            pForm.getAssetData().setManufName(manufName);
            pForm.getAssetData().setManufTypeCd(RefCodeNames.ASSET_MANUF_TYPE_CD.STORE);

            assetDetail.setAssetData(pForm.getAssetData());
            assetDetail.setLongDesc(pForm.getLongDesc());
        } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(pForm.getAssetData().getAssetTypeCd())) {
            assetDetail.setAssetData(pForm.getAssetData());
        }
    }

    private static String getManufNameById(int selectedManufId, PairViewVector pairs) {
        if (pairs != null) {
            Iterator it = pairs.iterator();
            while (it.hasNext()) {
                PairView pair = (PairView) it.next();
                if (((Integer) pair.getObject1()).intValue() == selectedManufId)
                    return (String) pair.getObject2();
            }
        }
        return "";
    }

    private static ActionErrors checkMasterAssetFormAttribute(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        AssetData assetData = pForm.getAssetData();

        String field;
        if (!Utility.isSet(assetData.getShortDesc())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.assetName");
            if (field == null) {
                field = "Asset Name";
            }
            ae.add("Asset Name", new ActionError("variable.empty.error", field));
        } else if(assetData.getShortDesc().length() > 255) {
            field = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.assetName");
            if (field == null) {
                field = "Asset Name";
            }
            ae.add("Asset Name", new ActionError("variable.long.error", field));
        }

        if (!Utility.isSet(pForm.getLongDesc().getValue())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.longDescription");
            if (field == null) {
                field = "Long Description";
            }
            ae.add("Long Description", new ActionError("variable.empty.error", field));
        }

        if (pForm.getImageFile() != null) {
            if (Utility.isSet(pForm.getImageFile().getFileName())) {
                byte[] fileData = pForm.getImageFile().getFileData();
                if (fileData.length == 0) {
                    ae.add("File not exists", new ActionError("error.fileCanNotBeFound", pForm.getImageFile().getFileName()));
                }
                String imageFileName = pForm.getImageFile().getFileName().trim().toLowerCase();
                if (!(imageFileName.endsWith(".jpeg") ||
                      imageFileName.endsWith(".jpg")  ||
                      imageFileName.endsWith(".gif")  ||
                      imageFileName.endsWith(".png"))) {
                    ae.add("Image File Type", new ActionError("error.wrongImageFileType"));
                }
            }
        }

        if (assetData.getManufId() < 1) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.manufacturer");
            if (field == null) {
                field = "Manufacturer";
            }
            ae.add("Manufacturer", new ActionError("variable.empty.error", field));
        }

        if (!Utility.isSet(assetData.getManufSku())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.manufacturerSku");
            if (field == null) {
                field = "Manufacturer Sku";
            }
            ae.add("Manufacturer Sku", new ActionError("variable.empty.error", field));
        }

        if(Utility.isSet(assetData.getModelNumber()) && assetData.getModelNumber().length() > 255){
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.modelNumber");
            if (field == null) {
                field = "Model";
            }
            ae.add("Model", new ActionError("variable.long.error", field));
        }

        if (!Utility.isSet(assetData.getStatusCd())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status");
            if (field == null) {
                field = "Status";
            }
            ae.add("Status", new ActionError("variable.empty.error", field));
        }

        return ae;
    }

    private static ActionErrors checkStoreMasterAssetFormAttribute(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        AssetData assetData = pForm.getAssetData();

        String field;
        if (!Utility.isSet(assetData.getShortDesc())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.masterAssetName");
            if (field == null) {
                field = "Master Asset Name";
            }
            ae.add("Master Asset Name", new ActionError("variable.empty.error", field));
        } else if(assetData.getShortDesc().length() > 255) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.masterAssetName");
            if (field == null) {
                field = "Master Asset Name";
            }
            ae.add("Master Asset Name", new ActionError("variable.long.error", field));
        }

        if (!Utility.isSet(pForm.getLongDesc().getValue())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assocDocs.description");
            if (field == null) {
                field = "Description";
            }
            ae.add("Long Description", new ActionError("variable.empty.error", field));
        }

        if (pForm.getImageFile() != null) {
            if (Utility.isSet(pForm.getImageFile().getFileName())) {
                byte[] fileData = pForm.getImageFile().getFileData();
                if (fileData.length == 0) {
                    ae.add("File not exists", new ActionError("error.fileCanNotBeFound", pForm.getImageFile().getFileName()));
                }
                String imageFileName = pForm.getImageFile().getFileName().trim().toLowerCase();
                if (!(imageFileName.endsWith(".jpeg") ||
                      imageFileName.endsWith(".jpg")  ||
                      imageFileName.endsWith(".gif")  ||
                      imageFileName.endsWith(".png"))) {
                    ae.add("Image File Type", new ActionError("error.wrongImageFileType"));
                }
            }
        }

        if (assetData.getManufId() < 1) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.manufacturer");
            if (field == null) {
                field = "Manufacturer";
            }
            ae.add("Manufacturer", new ActionError("variable.empty.error", field));
        }

        if (!Utility.isSet(assetData.getManufSku())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.manufacturerSku");
            if (field == null) {
                field = "Manufacturer Sku";
            }
            ae.add("Manufacturer Sku", new ActionError("variable.empty.error", field));
        }

        if(Utility.isSet(assetData.getModelNumber()) && assetData.getModelNumber().length() > 255){
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.modelNumber");
            if (field == null) {
                field = "Model";
            }
            ae.add("Model", new ActionError("variable.long.error", field));
        }

        if (!Utility.isSet(assetData.getStatusCd())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status");
            if (field == null) {
                field = "Status";
            }
            ae.add("Status", new ActionError("variable.empty.error", field));
        }

        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request,
                                                   CleanwiseUser appUser,
                                                   UserAssetProfileMgrForm pForm,
                                                   Asset assetEjb) throws Exception {

        ActionErrors ae = new ActionErrors();
        AssetPropertyData longDesc = pForm.getLongDesc();
        AssetData assetData = pForm.getAssetData();
        String dateFormat = ClwI18nUtil.getCountryDateFormat(request);
        SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, ClwI18nUtil.getUserLocale(request));
        Date date1970 = new Date(0);
        Date tmpDate;
        if (dateFormat == null) {
            dateFormat = "MM/dd/yyyy";
        }

        if (assetData == null || longDesc == null) {
            throw new Exception("check is failed.Some data is null");
        }

        String field;
        if (!Utility.isSet(assetData.getShortDesc())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.assetName");
            if (field == null) {
                field = "Asset Name";
            }
            ae.add("Asset Name", new ActionError("variable.empty.error", field));
        } else if(assetData.getShortDesc().length() > 255) {
            field = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.assetName");
            if (field == null) {
                field = "Asset Name";
            }
            ae.add("Asset Name", new ActionError("variable.long.error", field));
        }

        if (pForm.getImageFile() != null) {
            if (Utility.isSet(pForm.getImageFile().getFileName())) {
                String imageFileName = pForm.getImageFile().getFileName().trim().toLowerCase();
                if (!(imageFileName.endsWith(".jpeg") ||
                      imageFileName.endsWith(".jpg")  ||
                      imageFileName.endsWith(".gif")  ||
                      imageFileName.endsWith(".png"))) {
                    ae.add("Image File Type", new ActionError("error.wrongImageFileType"));
                }
                byte[] fileData = pForm.getImageFile().getFileData();
                if (fileData.length == 0) {
                    ae.add("File not exists", new ActionError("error.badFile", pForm.getImageFile().getFileName()));
                }
            }
        }

        if(Utility.isSet(assetData.getSerialNum()) && assetData.getSerialNum().length() > 255){
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.serialnumber");
            if (field == null) {
                field = "Serial Number";
            }
            ae.add("Serial Number", new ActionError("variable.long.error", field));
        }

        if(Utility.isSet(assetData.getModelNumber()) && assetData.getModelNumber().length() > 255){
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.modelNumber");
            if (field == null) {
                field = "Model";
            }
            ae.add("Model", new ActionError("variable.long.error", field));
        }

        if (!Utility.isSet(assetData.getStatusCd())) {
            if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetData.getAssetTypeCd())) {
                assetData.setStatusCd(RefCodeNames.ASSET_STATUS_CD.ACTIVE);
            } else {
                field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status");
                if (field == null) {
                    field = "Status";
                }
                ae.add("Status", new ActionError("variable.empty.error", field));
            }
        }

        if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetData.getAssetTypeCd())) {

            if (!Utility.isSet(pForm.getLongDesc().getValue())) {
                field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.longDescription");
                if (field == null) {
                    field = "Long Description";
                }
                ae.add("Long Description", new ActionError("variable.empty.error", field));
            }

            if (pForm.getUserAssignedAssetNumber()) {
                if (!Utility.isSet(pForm.getAssetData().getAssetNum())) {
                    field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.assetnumber");
                    if (field == null) {
                        field = "Asset Number";
                    }
                    ae.add("Asset Number", new ActionError("variable.empty.error", field));
                } else {
                    int duplicate_code;
                    if ((duplicate_code = isDuplicateAssetNumber(request,
                                                                assetEjb,
                                                                pForm.getAssetData().getAssetId(),
                                                                pForm.getAssetData().getAssetNum(),
                                                                appUser.getUserAccount().getAccountId())) > 0) {
                        if (duplicate_code == 1) {
                            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.assetnumber");
                            if (field == null) {
                                field = "Asset Number";
                            }
                            ae.add("Asset Number", new ActionError("variable.unique.error", field));
                        } else if (duplicate_code == 2) {
                            String asset = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.asset");
                            if (asset == null) {
                                asset = "Asset";
                            }
                            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.assetnumber");
                            if (field == null) {
                                field = "Asset Number";
                            }
                            ae.add("Multiple Asset Number", new ActionError("error.multiple.equal.present", new Object[]{asset, field}));
                        }
                    }
                }
            }

            if (!Utility.isSet(assetData.getManufSku())) {
                field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.manufacturerSku");
                if (field == null) {
                    field = "Manufacturer Sku";
                }
                ae.add("Manufacturer Sku", new ActionError("variable.empty.error", field));
            }

            if (pForm.getAcquisitionCost() != null && Utility.isSet(pForm.getAcquisitionCost().getValue())) {
                try {
                    Double.parseDouble(pForm.getAcquisitionCost().getValue());
                } catch (NumberFormatException e) {
                    field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.acquisitionprice");
                    if (field == null) {
                        field = "Acquisition Price";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "shop.errors.wrongNumberFormat", new Object[]{field, pForm.getAcquisitionCost().getValue()}, true);
                    ae.add("Acquisition Price", new ActionError("error.simpleError", mess));
                }
            }

            if (pForm.getAcquisitionDate() != null && Utility.isSet(pForm.getAcquisitionDate().getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, pForm.getAcquisitionDate().getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    pForm.getAcquisitionDate().setValue(sdFormat.format(tmpDate));
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.acquisitiondate");
                    if (property == null) {
                        property = "Acquisition Date";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, pForm.getAcquisitionDate().getValue()}, true);
                    ae.add("acquisitionDate", new ActionError("error.simpleError", mess));
                }
            }

            if (pForm.getDateInService() != null && Utility.isSet(pForm.getDateInService().getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, pForm.getDateInService().getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    pForm.getDateInService().setValue(sdFormat.format(tmpDate));
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.dateInService");
                    if (property == null) {
                        property = "Date In Service";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, pForm.getDateInService().getValue()}, true);
                    ae.add("dateInService", new ActionError("error.simpleError", mess));
                }
            }

            if (pForm.getDateLastHMR() != null && Utility.isSet(pForm.getDateLastHMR().getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, pForm.getDateLastHMR().getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    pForm.getDateLastHMR().setValue(sdFormat.format(tmpDate));
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.dateLastHMR");
                    if (property == null) {
                        property = "Date Last Hour Meter Reading";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, pForm.getDateLastHMR().getValue()}, true);
                    ae.add("dateLastHMR", new ActionError("error.simpleError", mess));
                }
            }

            if (pForm.getLastHMR() != null && Utility.isSet(pForm.getLastHMR().getValue())) {
                try {
                    Integer.parseInt(pForm.getLastHMR().getValue());
                } catch (NumberFormatException e) {
                    ae.add("Last Hour Meter Reading", new ActionError("shop.errors.wrongNumberFormat", pForm.getLastHMR().getValue()));
                }
            }
        }

        return ae;
    }

    private static AssetContentDetailView setNewAssetImage(Asset assetEJB,
                                                           AssetData asset,
                                                           FormFile imageFile,
                                                           AssetContentDetailView mainAssetImage,
                                                           UserData user) throws Exception {
        if (mainAssetImage == null) {
            mainAssetImage = createAssetImage(asset, user);
            if (imageFile != null && Utility.isSet(imageFile.getFileName())) {
                mainAssetImage.getContent().setPath(imageFile.getFileName());
                mainAssetImage.getContent().setData(imageFile.getFileData());
                mainAssetImage.getContent().setContentTypeCd(imageFile.getContentType());

                mainAssetImage = assetEJB.updateAssetContent(mainAssetImage, user);
            }
        } else {
            if (imageFile != null && Utility.isSet(imageFile.getFileName())) {
                mainAssetImage.getContent().setPath(imageFile.getFileName());
                mainAssetImage.getContent().setData(imageFile.getFileData());
                mainAssetImage.getContent().setContentTypeCd(imageFile.getContentType());

                mainAssetImage = assetEJB.updateAssetContent(mainAssetImage, user);
            }
        }
        return mainAssetImage;
    }

    private static AssetContentDetailView createAssetImage(AssetData asset, UserData user) throws Exception {

        AssetContentData assetContentData = AssetContentData.createValue();

        assetContentData.setAssetId(asset.getAssetId());
        assetContentData.setAssetContentId(0);
        assetContentData.setUrl("");
        assetContentData.setAddBy(user.getUserName());
        assetContentData.setModBy(user.getUserName());
        assetContentData.setAddDate(null);
        assetContentData.setModDate(null);
        assetContentData.setTypeCd(RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE);

        ContentDetailView content = ContentDetailView.createValue();
        content.setContentId(0);
        content.setBusEntityId(0);
        content.setItemId(0);

        content.setContentStatusCd(RefCodeNames.CONTENT_STATUS_CD.ACTIVE);
        content.setLanguageCd("x");
        content.setLocaleCd("x");
        content.setContentUsageCd("N/A");
        content.setSourceCd("N/A");
        content.setLongDesc(null);
        content.setShortDesc("Asset Image");
        content.setAddBy(user.getUserName());
        content.setModBy(user.getUserName());
        content.setAddDate(null);
        content.setModDate(null);

        content.setEffDate(null);
        content.setExpDate(null);
        content.setVersion(1);

        return new AssetContentDetailView(content, assetContentData);
    }

    public static ActionErrors locateSite(HttpServletRequest request,
                                          UserAssetProfileMgrForm pForm,
                                          HttpServletResponse response) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "AssetViewVector", null);
        Element root = doc.getDocumentElement();
        int count = 0;
        LocateStoreSiteForm locateForm = pForm.getLocateStoreSiteForm();
        if (locateForm != null) {
            SiteViewVector sites = locateForm.getSites();
            if (sites != null) {

                Iterator it = sites.iterator();
                while (it.hasNext()) {
                    SiteView siteView = (SiteView) it.next();

                    Element siteNode = doc.createElement("Site");
                    siteNode.setAttribute("Id", String.valueOf(siteView.getId()));

                    Element siteDetail;
                    siteDetail = doc.createElement("Name");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getName())));
                    siteNode.appendChild(siteDetail);

                    siteDetail = doc.createElement("Rank");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getTargetFacilityRank())));
                    siteNode.appendChild(siteDetail);

                    siteDetail = doc.createElement("AccountName");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getAccountName())));
                    siteNode.appendChild(siteDetail);

                    siteDetail = doc.createElement("StreetAddress");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getAddress())));
                    siteNode.appendChild(siteDetail);

                    siteDetail = doc.createElement("City");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getCity())));
                    siteNode.appendChild(siteDetail);

                    siteDetail = doc.createElement("State");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getState())));
                    siteNode.appendChild(siteDetail);

                    siteDetail = doc.createElement("Status");
                    siteDetail.appendChild(doc.createTextNode(String.valueOf(siteView.getStatus())));
                    siteNode.appendChild(siteDetail);

                    root.appendChild(siteNode);
                    count++;
                }
            }

        }

        root.setAttribute("size", String.valueOf(count));

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(root);

        return ae;
    }

    public static ActionErrors updateLocation(HttpServletRequest request,
                                              UserAssetProfileMgrForm pForm,
                                              HttpServletResponse response) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        Asset assetEJB =  APIAccess.getAPIAccess().getAssetAPI();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }


        SiteViewVector newAsscocSites = pForm.getLocateStoreSiteForm().getSitesToReturn();
        if (newAsscocSites != null && !newAsscocSites.isEmpty()) {
            SiteView siteView = (SiteView) newAsscocSites.get(0);
        	if(isDuplicateAssetNumber(request, assetEJB,
        			pForm.getAssetData().getAssetId(), pForm.getAssetData().getAssetNum(),
        			siteView.getAccountId()) > 0) {

        		String errorMessage = ClwI18nUtil.getMessage(request, "userAssets.shop.error.DublicateAssetMumber",
                		new Object[] {pForm.getAssetData().getAssetNum()});

                Document doc = docBuilder.getDOMImplementation().createDocument("", "Error", null);
                Element root = doc.getDocumentElement();
                root.appendChild(doc.createTextNode(errorMessage));

                response.setContentType("application/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setCharacterEncoding("UTF-8");
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(root);

                session.setAttribute(USER_ASSET_PROFILE_MGR_FORM,pForm);
        		return ae;
        	}
            addAssociationWithSite(pForm,siteView.getId());
        }

        AddressData newAssetLocation=null;
        /* updates Assoc Data */
        if (pForm.getAssetData().getAssetId() > 0) {
            AssetAssocDataVector newAssoc = assetEJB.updateAssetAssocDataVector(pForm.getAssetData().getAssetId(),
                    pForm.getAssociations(),
                    appUser.getUser().getUserName());
            newAssetLocation = assetEJB.getAssetLocation(pForm.getAssetData().getAssetId());
            pForm.setAssociations(newAssoc);
            pForm.setAssetLocationData(newAssetLocation);
        }

        Element responseXml=null;
        Document doc = docBuilder.getDOMImplementation().createDocument("", "Address", null);
        if(newAssetLocation!=null){
            responseXml = newAssetLocation.toXml(doc);
        }

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(responseXml);

        session.setAttribute(USER_ASSET_PROFILE_MGR_FORM,pForm);


        return ae;
    }

    private static AssetAssocData createAssocObject(int assetId, int id, String assocCd) {
        AssetAssocData assetAssocData = AssetAssocData.createValue();
        assetAssocData.setBusEntityId(id);
        assetAssocData.setAssetId(assetId);
        assetAssocData.setAssetAssocCd(assocCd);
        assetAssocData.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
        return assetAssocData;
    }

    private static boolean vectorExistAssoc(int assetId, int busEntityId, AssetAssocDataVector vector, String assocCd) {
        if (assetId > 0
                && busEntityId > 0
                && vector != null) {
            Iterator it = vector.iterator();
            while (it.hasNext()) {
                AssetAssocData assetAssoc = (AssetAssocData) it.next();
                if (assetAssoc.getAssetId() == assetId
                        && assetAssoc.getBusEntityId() == busEntityId
                        && assocCd.equals(assetAssoc.getAssetAssocCd())) {
                    return true;
                }
            }
        }
        return false;
    }


    public static ActionErrors createNewWarranty(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {
        ActionErrors ae;
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }
        APIAccess factory = new APIAccess();
        Manufacturer manufEjb = factory.getManufacturerAPI();
        HttpSession session = request.getSession();
        UserWarrantyMgrLogic.createNewWarranty(request,null);
        UserWarrantyDetailMgrForm warrantyDetailForm = (UserWarrantyDetailMgrForm) session.getAttribute(UserWarrantyMgrLogic.USER_WARRANTY_DETAIL_MGR_FORM);

        if(warrantyDetailForm==null){
            ae.add("error",new ActionError("error.simpleGenericError","Creating warranty is failed"));
            return ae;
        }
        IdVector assetIds = new  IdVector();
        assetIds.add(new Integer(pForm.getAssetData().getAssetId()));
        UserWarrantyMgrLogic.addNewAssetWarrantyToForm(warrantyDetailForm,assetIds);

        ManufacturerData manuf = manufEjb.getManufacturer(pForm.getAssetData().getManufId());

        warrantyDetailForm.setWarrantyProvider(manuf.getBusEntity());
        String warrantyProviderId;
        if (warrantyDetailForm.getWarrantyProvider() != null) {
            warrantyProviderId = String.valueOf(warrantyDetailForm.getWarrantyProvider().getBusEntityId());
            warrantyDetailForm.setWarrantyProviderId(warrantyProviderId);
        }

        warrantyDetailForm.managementSource=UserAssetMgrLogic.className;

        session.setAttribute(UserWarrantyMgrLogic.USER_WARRANTY_DETAIL_MGR_FORM,warrantyDetailForm);
        return ae;
    }



    public static ActionErrors createNewWorkOrder(HttpServletRequest request, UserAssetProfileMgrForm pForm) throws Exception {

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        // create work order
        UserWorkOrderMgrLogic.createNewWorkOrder(request,null);

        UserWorkOrderDetailMgrForm woDetailForm = (UserWorkOrderDetailMgrForm) session.getAttribute(UserWorkOrderMgrLogic.USER_WORK_ORDER_DETAIL_MGR_FORM);
        if(woDetailForm==null){
            ae.add("error",new ActionError("error.simpleGenericError","Creating work order is failed"));
            return ae;
        }
        //set work order type and updates session
        woDetailForm.getWorkOrderDetail().getWorkOrder().setTypeCd(RefCodeNames.WORK_ORDER_TYPE_CD.EQUIPMENT);
        UserWorkOrderMgrLogic.uploadDetailData(request, woDetailForm.getWorkOrderDetail(),woDetailForm);
        woDetailForm.setActiveAsset(pForm.getAssetData());
        woDetailForm.setActiveAssetIdStr(String.valueOf(pForm.getAssetData().getAssetId()));

        WarrantyDataVector warranties = new WarrantyDataVector();

        WorkOrder wrkejb = APIAccess.getAPIAccess().getWorkOrderAPI();
        IdVector assetIds = new IdVector();
        assetIds.add(new Integer(woDetailForm.getActiveAsset().getAssetId()));
        warranties = wrkejb.getWorkOrderWarrantiesForAssets(assetIds,RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);
        woDetailForm.setWarrantyForActiveAsset(warranties);

        woDetailForm.managementSource = UserAssetMgrLogic.className;

        session.setAttribute(UserWorkOrderMgrLogic.USER_WORK_ORDER_DETAIL_MGR_FORM, woDetailForm);

        return ae;
    }

    private static AssetPropertyData convertDateValueForWrite(HttpServletRequest request,
                                                              Locale locale,
                                                              AssetPropertyData dateProperty) {
        if (dateProperty != null) {
            dateProperty.setValue(ClwI18nUtil.convertDateToLocale(request, locale, dateProperty.getValue()));
        }
        return dateProperty;
    }

    private static AssetPropertyData convertDateValueForRead(Locale locale,
                                                             HttpServletRequest request,
                                                             AssetPropertyData dateProperty) {
        if (dateProperty != null) {
            dateProperty.setValue(ClwI18nUtil.convertDateToRequest(locale, request, dateProperty.getValue()));
        }
        return dateProperty;
    }

    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }

    public static ItemDataVector getServicesByIds(ItemDataVector assetServiceAssoc, IdVector serviceIdsInSiteCatalog) {
        return  new ItemDataVector();
    }

    private static boolean checkUserAssignedAssetNumber(AccountData ad) throws Exception {
        boolean result = false;
        if (ad != null) {
            String property = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER);
            result = Utility.isTrue(property, false);
        }
        return result;
    }

    private static int isDuplicateAssetNumber(HttpServletRequest request,
                                                  Asset assetEjb,
                                                  int assetId,
                                                  String assetNumber,
                                                  int accountId) throws RemoteException {
        int duplicate = 0;

        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetNumber(assetNumber);
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);
        //criteria.setStoreIds(assetStoreAsIdVector);
        criteria.setSearchNumberTypeCd(RefCodeNames.SEARCH_TYPE_CD.EQUALS);
        criteria.setIgnoreCase(true);
        criteria.setShowInactive(true);
        IdVector accountIds = new IdVector();
        accountIds.add(Integer.valueOf(accountId));
        criteria.setAccountIds(accountIds);
        AssetDataVector result = assetEjb.getAssetDataByCriteria(criteria);
        if (assetId > 0) {
            if (result.size() >= 1) {
                if (((AssetData) result.get(0)).getAssetId() != assetId) {
                    duplicate = 1;
                }
                if (result.size() > 1) {
                    duplicate = 2;
                }
            }
        } else if (result.size() > 0) {
            duplicate = 1;
        }
    return duplicate;
    }

    private static ActionErrors checkCategoryNotInUse(Asset assetEjb, int categoryAssetId, IdVector childCategoryAssetIds) throws Exception {
     ActionErrors ae = new ActionErrors();
     IdVector categoryToDeleteIds = new IdVector();
     categoryToDeleteIds.add(new Integer(categoryAssetId));
     if (childCategoryAssetIds != null) {
       categoryToDeleteIds.addAll(childCategoryAssetIds);
     }
     DBCriteria dbc = new DBCriteria();
    dbc.addOneOf(AssetDataAccess.PARENT_ID, categoryToDeleteIds );
     ArrayList errs = assetEjb.checkAssetsInUse(categoryToDeleteIds);
     if ( errs != null &&  errs.size() > 0){
       String firstN = (errs.size() >10) ? " First 10 ::" : "";
       ae.add("error",new ActionError("error.simpleGenericError", (String)errs.get(0)+ firstN));
       for (int i = 1; i < errs.size() && i <= 10; i++) {
        ae.add("error",new ActionError("error.simpleGenericError", (String)errs.get(i)));
       }
     }
     return ae;
   }
   private static void removeAssetDetailDataForCategory(Asset assetEjb, int assetId, IdVector linkedAssetIds, String userName) throws Exception{
     IdVector toDeleteCategoryIds = new IdVector();
     toDeleteCategoryIds.add(new Integer(assetId));
     if (linkedAssetIds != null) {
       toDeleteCategoryIds.addAll(linkedAssetIds);
     }

     // remove from clw_asset_master_assoc
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID, assetId);
     int n = assetEjb.removeAssetMasterAssoc( dbc);

     // remove all category associations with parent and child stores
     dbc = new DBCriteria();
     dbc.addOneOf(AssetAssocDataAccess.ASSET_ID, toDeleteCategoryIds );
     dbc.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
     String sql = AssetAssocDataAccess.getSqlSelectIdOnly(AssetAssocDataAccess.ASSET_ASSOC_ID, dbc);
     dbc = new DBCriteria();
     dbc.addOneOf(AssetAssocDataAccess.ASSET_ASSOC_ID, sql);
     n = assetEjb.removeAssetAssoc(dbc);

     //
//     removeMasterAssociation(pForm, categoryAssetId);
//     removeAssociationWithStore(pForm, categoryAssetId, appUser.getUserStore().getStoreId());
     n = assetEjb.removeAssetDetailDataForCategory(toDeleteCategoryIds);

   }
   private static int isDuplicateCategory(    Asset assetEjb,
                                              int assetId,
                                              String category,
                                              int storeId) throws RemoteException {
     AssetSearchCriteria criteria = new AssetSearchCriteria();
     criteria.setAssetName(category);
     criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
     criteria.setSearchNumberTypeCd(RefCodeNames.SEARCH_TYPE_CD.EQUALS);
     criteria.setIgnoreCase(true);
     criteria.setShowInactive(true);
     IdVector storeIds = new IdVector();
     storeIds.add(Integer.valueOf(storeId));
     criteria.setStoreIds(storeIds);
     AssetDataVector result = assetEjb.getAssetDataByCriteria(criteria);
     if (assetId > 0){
       if (result.size()==1 && ((AssetData)result.get(0)).getAssetId()==assetId ){
         return 0;// find itself
       }
     }
     return result.size();
   }

}
