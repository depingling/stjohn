package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Warranty;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.*;

/**
 * Title:        UserWarrantyMgrLogic
 * Description:  logic manager for the warranty processing
 * Purpose:      execute operation for the warranty processing
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.09.2007
 * Time:         19:32:22
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class UserWarrantyMgrLogic {
    private static final Logger log = Logger.getLogger(UserWarrantyMgrLogic.class);

    public static final String USER_WARRANTY_MGR_FORM               = "USER_WARRANTY_MGR_FORM";
    public static final String USER_WARRANTY_DETAIL_MGR_FORM        = "USER_WARRANTY_DETAIL_MGR_FORM";
    public static final String USER_WARRANTY_CONFIG_MGR_FORM        = "USER_WARRANTY_CONFIG_MGR_FORM";
    public static final String USER_WARRANTY_CONFIG_DETAIL_MGR_FORM = "USER_WARRANTY_CONFIG_DETAIL_MGR_FORM";
    public static final String USER_WARRANTY_NOTE_MGR_FORM          = "USER_WARRANTY_NOTE_MGR_FORM";
    public static final String USER_WARRANTY_CONTENT_MGR_FORM       = "USER_WARRANTY_CONTENT_MGR_FORM";

    public static String className = "UserWarrantyMgrLogic";

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    static final Comparator ASSET_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((AssetView) o1).getAssetId();
            int id2 = ((AssetView) o2).getAssetId();
            return id2 - id1;
        }
    };

    public static ActionErrors search(HttpServletRequest request, UserWarrantyMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantySimpleSearchCriteria criteria = new WarrantySimpleSearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            //add data to criteria
            if (Utility.isSet(pForm.getSearchField())) {
                if (UserWarrantyMgrForm.ASSET_NUMBER.equals(pForm.getSearchFieldType())) {
                    criteria.setAssetNumber(pForm.getSearchField());
                    criteria.setSearchType(pForm.getSearchType());
                } else if (UserWarrantyMgrForm.ASSET_NAME.equals(pForm.getSearchFieldType())) {
                    criteria.setAssetName(pForm.getSearchField());
                    criteria.setSearchType(pForm.getSearchType());
                } else if (UserWarrantyMgrForm.WARRANTY_PROVIDER.equals(pForm.getSearchFieldType())) {
                    criteria.setWarrantyProvider(pForm.getSearchField());
                    criteria.setSearchType(pForm.getSearchType());
                } else if (UserWarrantyMgrForm.SERVICE_PROVIDER.equals(pForm.getSearchFieldType())) {
                    criteria.setServiceProvider(pForm.getSearchField());
                    criteria.setSearchType(pForm.getSearchType());
                } else {
                    criteria.setWarrantyName(pForm.getSearchField());
                    criteria.setSearchType(pForm.getSearchType());
                }
            }

            IdVector storeIds = new IdVector();
            int storeId = appUser.getUserStore().getStoreId();
            storeIds.add(new Integer(storeId));
            criteria.setStoreIds(storeIds);

            WarrantyViewVector result = warrantyEjb.getWarrantyViewCollection(criteria);

            pForm.setSearchResult(result);
            session.setAttribute(USER_WARRANTY_MGR_FORM, pForm);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, UserWarrantyDetailMgrForm form) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        form = new UserWarrantyDetailMgrForm();

        try {

            APIAccess factory = new APIAccess();

            Warranty warrantyEjb = factory.getWarrantyAPI();
            ListService listServiceEJB = factory.getListServiceAPI();

            ae=checkRequest(request, form);
            if (ae.size() > 0) {
                resetSessionAttributes(request, form);
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            BusEntityDataVector wProviders = warrantyEjb.getWarrantyProvidersForStore(appUser.getUserStore().getStoreId());
            DisplayListSort.sort(wProviders,"short_desc");
            HashMap warrantyProvidersMap = toMapByBusEntityId(wProviders);
            session.setAttribute("Warranty.provider.vector", wProviders);
            form.setAllWarrantyProvidersMap(warrantyProvidersMap);

            //BusEntityDataVector sProviders = warrantyEjb.getServiceProvidersForStore(appUser.getUserStore().getStoreId());
            BusEntityDataVector sProviders = warrantyEjb.getServiceProvidersForAccount(appUser.getUserAccount().getAccountId());
            DisplayListSort.sort(sProviders ,"short_desc");   
            HashMap serviceProvidersMap = toMapByBusEntityId(sProviders);
            session.setAttribute("Warranty.serviceProvider.vector", sProviders);
            form.setAllServiceProvidersMap(serviceProvidersMap);

            if (isEmptySessionCd(session, "Warranty.status.vector")) {
                RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WARRANTY_STATUS_CD");
                session.setAttribute("Warranty.status.vector", statusCds);
            }

            if (isEmptySessionCd(session, "Warranty.type.vector")) {
                RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("WARRANTY_TYPE_CD");
                session.setAttribute("Warranty.type.vector", typeCds);
            }

            if (isEmptySessionCd(session, "Warranty.duration.type.vector")) {
                RefCdDataVector durationTypeCds = listServiceEJB.getRefCodesCollection("WARRANTY_DURATION_TYPE_CD");
                session.setAttribute("Warranty.duration.type.vector", durationTypeCds);
            }

            if (isEmptySessionCd(session, "Coverage.cds.vector")) {
                RefCdDataVector covCds = listServiceEJB.getRefCodesCollection("COVERAGE_IND_CD");
                session.setAttribute("Coverage.cds.vector", covCds);
            }

            if (isEmptySessionCd(session, "Content.status.vector")) {
                RefCdDataVector wContentStatusCds = listServiceEJB.getRefCodesCollection("CONTENT_STATUS_CD");
                session.setAttribute("Content.status.vector", wContentStatusCds);
            }

            if (isEmptySessionCd(session, "Warranty.note.type.cds")) {
                RefCdDataVector warrantyNoteTypeCds = listServiceEJB.getRefCodesCollection("WARRANTY_NOTE_TYPE_CD");
                session.setAttribute("Warranty.note.type.cds", warrantyNoteTypeCds);
            }

            if (isEmptySessionCd(session, "Warranty.documents.locale.vector")) {
                RefCdDataVector warrantyDocsLocaleCds = listServiceEJB.getRefCodesCollection("LOCALE_CD");
                session.setAttribute("Warranty.documents.locale.vector", warrantyDocsLocaleCds);
            }
            session.setAttribute(USER_WARRANTY_DETAIL_MGR_FORM, form);

        } catch (Exception e) {
            e.printStackTrace();
            resetSessionAttributes(request, form);
            throw new Exception(e.getMessage());
        }

        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, UserWarrantyDetailMgrForm pForm) {

        HttpSession session = request.getSession();
        session.setAttribute("Warranty.provider.vector", new BusEntityDataVector());
        session.setAttribute("Warranty.status.vector", new RefCdDataVector());
        session.setAttribute("Warranty.type.vector", new RefCdDataVector());
        session.setAttribute("Coverage.cds.vector", new RefCdDataVector());
        session.setAttribute("Warranty.note.type.cds", new RefCdDataVector());
        session.setAttribute("Content.status.vector", new RefCdDataVector());
        session.setAttribute("Warranty.documents.locale.vector", new RefCdDataVector());
        session.setAttribute("Warranty.duration.type.vector", new RefCdDataVector());

        pForm = new UserWarrantyDetailMgrForm();
        session.setAttribute(USER_WARRANTY_DETAIL_MGR_FORM, pForm);

    }

    private static boolean isEmptySessionCd(HttpSession session, String s) {
        if (session != null) {
            if (session.getAttribute(s) != null) {
                if (session.getAttribute(s) instanceof List) {
                    return ((List) session.getAttribute(s)).isEmpty();
                }
            }
        }
        return true;
    }

    private static HashMap toMapByBusEntityId(BusEntityDataVector providers) {
        HashMap providersMap = new HashMap();
        if (providers != null) {
            Iterator it = providers.iterator();
            while (it.hasNext()) {
                BusEntityData provider = (BusEntityData) it.next();
                providersMap.put(new Integer(provider.getBusEntityId()), provider);
            }
        }
        return providersMap;
    }

    public static ActionErrors init(HttpServletRequest request, UserWarrantyMgrForm pForm) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae;

        if(pForm ==null || !pForm.init){
            pForm = new UserWarrantyMgrForm();
            pForm.setSearchType(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            pForm.setSearchFieldType(UserWarrantyMgrForm.WARRANTY_NAME);
            pForm.init();
        }

        session.setAttribute(Constants.BREAD_CRUMB_NAVIGATOR,null);

        session.setAttribute(USER_WARRANTY_MGR_FORM, pForm);

        ae=checkRequest(request, pForm);
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (form == null) {
            throw new Exception("Form not initialized");
        }

        if (factory == null) {
            throw new Exception("No Ejb access");
        }

        if (appUser == null) {
            throw new Exception("No user info");
        }

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            throw new Exception("Unauthorized access");
        }

        return ae;
    }

    public static ActionErrors sort(HttpServletRequest request, UserWarrantyMgrForm userAssetMgrForm) {
        return null;
    }

    public static ActionErrors getDetail(HttpServletRequest request, UserWarrantyDetailMgrForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        int warrantyId = getWarrantyIdFromRequest(request, pForm);

        ae = init(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        Warranty warrantyEjb = APIAccess.getAPIAccess().getWarrantyAPI();
        WarrantyDetailView detail = warrantyEjb.getWarrantyDetailView(warrantyId);
        uploadDetailData(detail, pForm);
        session.setAttribute(USER_WARRANTY_DETAIL_MGR_FORM, pForm);
        return ae;
    }

    private static void uploadDetailData(WarrantyDetailView warrantyDetail, UserWarrantyDetailMgrForm pForm) throws Exception {

        pForm.setWarrantyData(warrantyDetail.getWarrantyData());
        pForm.setWarrantyProvider(getWarrantyProvider(warrantyDetail.getWarrantyAssocViewVector()));
        pForm.setServiceProvider(getServiceProvider(warrantyDetail.getWarrantyAssocViewVector()));
        pForm.setContents(warrantyDetail.getContents());
        pForm.setWarrantyAssocViewVector(warrantyDetail.getWarrantyAssocViewVector());
        pForm.setStatusHistory(warrantyDetail.getStatusHistory());
        pForm.setWarrantyNotes(warrantyDetail.getWarrantyNotes());
        pForm.setAssetWarrantyViewVector(warrantyDetail.getAssetWarrantyViewVector());
        pForm.setAssetWarrantyCategoryAssoc(getAssetCategoriesForAsset(warrantyDetail.getAssetWarrantyViewVector()));
        pForm.setWorkOrderItemWarrantyViewVector(warrantyDetail.getWorkOrderItemWarrantyViewVector());

        //add editing fields
        if (pForm.getWarrantyData() != null) {

            WarrantyData warrantyData = pForm.getWarrantyData();

            String warrantyName = Utility.strNN(warrantyData.getShortDesc());
            String warrantyNumber = Utility.strNN(warrantyData.getWarrantyNumber());
            String typeCd = Utility.strNN(warrantyData.getTypeCd());
            String statusCd = Utility.strNN(warrantyData.getStatusCd());
            String longDesc = Utility.strNN(warrantyData.getLongDesc());
            String businessName = Utility.strNN(warrantyData.getBusinessName());

            String duration;
            if (warrantyData.getDuration() > 0) {
                duration = String.valueOf(warrantyData.getDuration());
            } else {
                duration = "";
            }

            if (Utility.isSet(warrantyData.getDurationTypeCd())) {
                pForm.setDurationTypeCd(warrantyData.getDurationTypeCd());
            } else {
                pForm.setDurationTypeCd(RefCodeNames.WARRANTY_DURATION_TYPE_CD.MONTHS);
            }

            String cost;
            if (warrantyData.getCost() != null) {
                cost = warrantyData.getCost().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            } else {
                cost = "";
            }

            String warrantyProviderId;
            if (pForm.getWarrantyProvider() != null) {
                warrantyProviderId = String.valueOf(pForm.getWarrantyProvider().getBusEntityId());
            } else {
                warrantyProviderId = "0";
            }

            String serviceProviderId;
            if (pForm.getServiceProvider() != null) {
                serviceProviderId = String.valueOf(pForm.getServiceProvider().getBusEntityId());
            } else {
                serviceProviderId = "0";
            }

            pForm.setWarrantyName(warrantyName);
            pForm.setWarrantyNumber(warrantyNumber);
            pForm.setTypeCd(typeCd);
            pForm.setStatusCd(statusCd);
            pForm.setLongDesc(longDesc);
            pForm.setBusinessName(businessName);
            pForm.setCost(cost);
            pForm.setWarrantyProviderId(warrantyProviderId);
            pForm.setServiceProviderId(serviceProviderId);
            pForm.setDuration(duration);
        }

    }

    private static AssetDataVector getAssetCategoriesForAsset(AssetWarrantyViewVector assetWarrantyViewVector) throws Exception {

        IdVector assetIds = new IdVector();
        AssetDataVector assetCat = new AssetDataVector();
        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();
        if (assetWarrantyViewVector != null && !assetWarrantyViewVector.isEmpty()) {

            Iterator it = assetWarrantyViewVector.iterator();
            while (it.hasNext()) {
                AssetWarrantyView assetWarrView = (AssetWarrantyView) it.next();
                if (assetWarrView.getAssetView().getParentId() > 0) {
                    assetIds.add(new Integer(assetWarrView.getAssetView().getParentId()));
                }
            }
            if (!assetIds.isEmpty()) {
                AssetSearchCriteria criteria = new AssetSearchCriteria();
                criteria.setAssetIds(assetIds);
                criteria.setShowInactive(true);
                criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
                assetCat = assetEjb.getAssetDataByCriteria(criteria);
            }
        }
        return assetCat;
    }

    private static BusEntityData getWarrantyProvider(WarrantyAssocViewVector warrantyAssocVV) {

        BusEntityData provider = BusEntityData.createValue();

        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocDetail = (WarrantyAssocView) it.next();
                if (assocDetail.getBusEntity() != null) {
                    if (RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER.equals(assocDetail.getBusEntity().getBusEntityTypeCd())) {
                        provider = assocDetail.getBusEntity();
                        break;
                    }
                }
            }

        }
        return provider;
    }

    private static BusEntityData getServiceProvider(WarrantyAssocViewVector warrantyAssocVV) {

        BusEntityData provider = BusEntityData.createValue();

        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocDetail = (WarrantyAssocView) it.next();
                if (assocDetail.getBusEntity() != null) {
                    if (RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(assocDetail.getBusEntity().getBusEntityTypeCd())) {
                        provider = assocDetail.getBusEntity();
                        break;
                    }
                }
            }

        }
        return provider;
    }

    private static int getWarrantyIdFromRequest(HttpServletRequest request, UserWarrantyDetailMgrForm form) throws Exception {

        String warrantyIdStr = request.getParameter("warrantyId");
        int warrantyIdInt = -1;
        try {
            warrantyIdInt = Integer.parseInt(warrantyIdStr);
        } catch (NumberFormatException e) {
            if (warrantyIdStr == null && form.getWarrantyData() != null) {
                warrantyIdInt = form.getWarrantyData().getWarrantyId();
            }
        }
        return warrantyIdInt;
    }

    public static ActionErrors updateWarranty(HttpServletRequest request, UserWarrantyDetailMgrForm pForm) throws Exception {

        ActionErrors ae ;
        HttpSession session = request.getSession();
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            String errorMess = ClwI18nUtil.getMessageOrNull(request, "shop.errors.notAuthorizedForAssetManagement");
            if (!Utility.isSet(errorMess)) {
                errorMess = "Access is denied to asset management";
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ae = checkFormAttribute(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantyDetailView warrantyDetail = WarrantyDetailView.createValue();
            loadDetailData(warrantyDetail, pForm);
            WarrantyAssocDataVector warrantyAssoc = getWarrantyAssocDataVector(warrantyDetail.getWarrantyAssocViewVector());

            WarrantyData warrantyData = warrantyEjb.updateWarranty(warrantyDetail.getWarrantyData(), warrantyAssoc, appUser.getUser());


            if(pForm.managementSource.equals(UserAssetMgrLogic.className) && warrantyData.getWarrantyId()>0){
                warrantyEjb.updateAssetWarrantyView(warrantyData.getWarrantyId(), pForm.getAssetWarrantyViewVector(), appUser.getUser());
                pForm.resetManagementSource();
            }

            pForm.setWarrantyData(warrantyData);
            return getDetail(request, pForm);
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static IdVector getNewlyAssetIds(AssetWarrantyViewVector assetWarrantyViewVector) {
        IdVector newlyAssetIds = new IdVector();
        if(assetWarrantyViewVector!=null && !assetWarrantyViewVector.isEmpty()){
            Iterator it =  assetWarrantyViewVector.iterator();
            while(it.hasNext()){
                AssetWarrantyView aw = (AssetWarrantyView) it.next();
                if(aw.getAssetWarrantyData().getWarrantyId()==0
                        && aw.getAssetWarrantyData().getAssetId()>0){
                    if(vectorNotExistThisAsset(assetWarrantyViewVector,aw.getAssetWarrantyData().getAssetId()))
                        newlyAssetIds.add(new Integer(aw.getAssetWarrantyData().getAssetId()));
                }
            }
        }
        return  newlyAssetIds;
    }

    private static boolean vectorNotExistThisAsset(AssetWarrantyViewVector assetWarrantyViewVector,int assetId) {
        if(assetWarrantyViewVector!=null && !assetWarrantyViewVector.isEmpty()){
            Iterator it =  assetWarrantyViewVector.iterator();
            while(it.hasNext()){
                AssetWarrantyView aw = (AssetWarrantyView) it.next();
                if(aw.getAssetWarrantyData().getAssetId()== assetId){
                    return false;
                }
            }
        }
        return true;
    }

    public static ActionErrors createNewWarranty(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = init(request, (UserWarrantyDetailMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);

        ae = checkRequest(request, detailForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            String errorMess = ClwI18nUtil.getMessageOrNull(request, "shop.errors.notAuthorizedForAssetManagement");
            if (!Utility.isSet(errorMess)) {
                errorMess = "Access is denied to asset management";
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        WarrantyDetailView warrantyDetail = WarrantyDetailView.createValue();

        WarrantyAssocData assocData = WarrantyAssocData.createValue();
        assocData.setBusEntityId(appUser.getUserStore().getStoreId());
        assocData.setWarrantyAssocCd(RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_STORE);
        assocData.setWarrantyAssocStatusCd(RefCodeNames.WARRANTY_ASSOC_STATUS_CD.ACTIVE);

        WarrantyAssocView assocV = WarrantyAssocView.createValue();
        WarrantyAssocViewVector assocViewVector = new WarrantyAssocViewVector();
        assocV.setBusEntity(appUser.getUserStore().getBusEntity());
        assocV.setWarrantyAssoc(assocData);
        assocViewVector.add(assocV);

        warrantyDetail.setWarrantyData(WarrantyData.createValue());
        warrantyDetail.setWarrantyAssocViewVector(assocViewVector);

        uploadDetailData(warrantyDetail, detailForm);

        session.setAttribute(USER_WARRANTY_DETAIL_MGR_FORM, detailForm);

        return ae;
    }


    private static void loadDetailData(WarrantyDetailView warrantyDetail, UserWarrantyDetailMgrForm pForm) {
        WarrantyData warrantyData = pForm.getWarrantyData();

        warrantyData.setShortDesc(pForm.getWarrantyName());
        warrantyData.setWarrantyNumber(pForm.getWarrantyNumber());
        warrantyData.setLongDesc(pForm.getLongDesc());
        warrantyData.setTypeCd(pForm.getTypeCd());
        warrantyData.setStatusCd(pForm.getStatusCd());
        warrantyData.setBusinessName(pForm.getBusinessName());
        warrantyData.setDuration(Integer.parseInt(pForm.getDuration()));

        if (Utility.isSet(pForm.getCost())) {
            double cost = Double.parseDouble(pForm.getCost());
            warrantyData.setCost(new BigDecimal(cost).setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            warrantyData.setCost(null);
        }

        if (Utility.isSet(pForm.getDurationTypeCd())) {
            warrantyData.setDurationTypeCd(pForm.getDurationTypeCd());
        } else {
            warrantyData.setDurationTypeCd(RefCodeNames.WARRANTY_DURATION_TYPE_CD.MONTHS);
        }

        BusEntityData warrantyProvider = (BusEntityData) pForm.getAllWarrantyProvidersMap().get(new Integer(pForm.getWarrantyProviderId()));
        setWarrantyProviderToAssoc(warrantyProvider, pForm.getWarrantyAssocViewVector(), warrantyData);

        BusEntityData serviceProvider = (BusEntityData) pForm.getAllServiceProvidersMap().get(new Integer(pForm.getServiceProviderId()));
        setServiceProviderToAssoc(serviceProvider, pForm.getWarrantyAssocViewVector(), warrantyData);

        warrantyDetail.setWarrantyData(warrantyData);
        warrantyDetail.setWarrantyAssocViewVector(pForm.getWarrantyAssocViewVector());
        warrantyDetail.setContents(pForm.getContents());
        warrantyDetail.setStatusHistory(pForm.getStatusHistory());
        warrantyDetail.setWarrantyNotes(pForm.getWarrantyNotes());
        warrantyDetail.setAssetWarrantyViewVector(pForm.getAssetWarrantyViewVector());
        warrantyDetail.setWorkOrderItemWarrantyViewVector(pForm.getWorkOrderItemWarrantyViewVector());


    }

    public static WarrantyAssocViewVector setWarrantyProviderToAssoc(BusEntityData provider, WarrantyAssocViewVector warrantyAssoc, WarrantyData warrantyData) {
        if (warrantyAssoc == null) {
            warrantyAssoc = new WarrantyAssocViewVector();
            WarrantyAssocView assocV = createWarrantyProviderAssocView(provider, warrantyData);
            if (assocV != null) {
                warrantyAssoc.add(assocV);
            }
        } else {
            warrantyAssoc = setNewAssocWarrantyProvider(warrantyAssoc, provider, warrantyData);
        }
        return warrantyAssoc;
    }

    private static WarrantyAssocViewVector setServiceProviderToAssoc(BusEntityData provider, WarrantyAssocViewVector warrantyAssoc, WarrantyData warrantyData) {
        if (warrantyAssoc == null) {
            warrantyAssoc = new WarrantyAssocViewVector();
            WarrantyAssocView assocV = createServiceProviderAssocView(provider, warrantyData);
            if (assocV != null) {
                warrantyAssoc.add(assocV);
            }
        } else {
            warrantyAssoc = setNewAssocServiceProvider(warrantyAssoc, provider, warrantyData);
        }
        return warrantyAssoc;
    }

    private static WarrantyAssocViewVector setNewAssocServiceProvider(WarrantyAssocViewVector warrantyAssocVV, BusEntityData provider, WarrantyData warrantyData) {
        boolean isSet = false;
        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            //find and replace provider if provider exist
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocDetail = (WarrantyAssocView) it.next();
                if (assocDetail.getBusEntity() != null) {
                    if (RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(assocDetail.getBusEntity().getBusEntityTypeCd())) {
                        assocDetail.setBusEntity(provider);
                        assocDetail.getWarrantyAssoc().setBusEntityId(provider.getBusEntityId());
                        isSet = true;
                    }
                }
            }
            //if provider not exist create new assoc
            if (!isSet && provider != null) {
                WarrantyAssocView assocV = createServiceProviderAssocView(provider, warrantyData);
                if (assocV != null) {
                    warrantyAssocVV.add(assocV);
                }
            }
        }
        return warrantyAssocVV;
    }

    private static WarrantyAssocView createServiceProviderAssocView(BusEntityData provider, WarrantyData warrantyData) {

        if (provider != null && warrantyData != null && provider.getBusEntityId() > 0) {
            WarrantyAssocView assocV = WarrantyAssocView.createValue();
            WarrantyAssocData assoc = WarrantyAssocData.createValue();
            assoc.setBusEntityId(provider.getBusEntityId());
            assoc.setWarrantyId(warrantyData.getWarrantyId());
            assoc.setWarrantyAssocCd(RefCodeNames.WARRANTY_ASSOC_CD.SERVICE_PROVIDER);
            assoc.setWarrantyAssocStatusCd(RefCodeNames.WARRANTY_ASSOC_STATUS_CD.ACTIVE);
            assocV.setBusEntity(provider);
            assocV.setWarrantyAssoc(assoc);
            return assocV;
        }
        return null;
    }

    private static WarrantyAssocViewVector setNewAssocWarrantyProvider(WarrantyAssocViewVector warrantyAssocVV, BusEntityData provider, WarrantyData warrantyData) {
        boolean isSet = false;
        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            //find and replace provider if provider exist
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocDetail = (WarrantyAssocView) it.next();
                if (assocDetail.getBusEntity() != null) {
                    if (RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER.equals(assocDetail.getBusEntity().getBusEntityTypeCd())) {
                        assocDetail.setBusEntity(provider);
                        assocDetail.getWarrantyAssoc().setBusEntityId(provider.getBusEntityId());
                        isSet = true;
                    }
                }
            }
            //if provider not exist create new assoc
            if (!isSet && provider != null) {
                WarrantyAssocView assocV = createWarrantyProviderAssocView(provider, warrantyData);
                if (assocV != null) {
                    warrantyAssocVV.add(assocV);
                }
            }
        }
        return warrantyAssocVV;
    }

    private static WarrantyAssocView createWarrantyProviderAssocView(BusEntityData provider, WarrantyData warrantyData) {

        if (provider != null && warrantyData != null && provider.getBusEntityId() > 0) {
            WarrantyAssocView assocV = WarrantyAssocView.createValue();
            WarrantyAssocData assoc = WarrantyAssocData.createValue();
            assoc.setBusEntityId(provider.getBusEntityId());
            assoc.setWarrantyId(warrantyData.getWarrantyId());
            assoc.setWarrantyAssocCd(RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_PROVIDER);
            assoc.setWarrantyAssocStatusCd(RefCodeNames.WARRANTY_ASSOC_STATUS_CD.ACTIVE);
            assocV.setBusEntity(provider);
            assocV.setWarrantyAssoc(assoc);
            return assocV;
        }
        return null;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWarrantyDetailMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        WarrantyData warrantyData = pForm.getWarrantyData();

        if (warrantyData == null) {
            throw new Exception("Warranty Data is null");
        }

        if (!Utility.isSet(pForm.getWarrantyName())) {
            ae.add("WarrantyName", new ActionError("variable.empty.error", "Warranty Name"));
        }

        if (!Utility.isSet(pForm.getWarrantyNumber())) {
            ae.add("WarrantyNumber", new ActionError("variable.empty.error", "Warranty Number"));
        }

        if (!Utility.isSet(pForm.getTypeCd())) {
            ae.add("Type", new ActionError("variable.empty.error", "Type"));
        }

        if (!Utility.isSet(pForm.getStatusCd())) {
            ae.add("Status", new ActionError("variable.empty.error", "Status"));
        }

        if (Utility.isSet(pForm.getCost())) {
            try {
                double cost = Double.parseDouble(pForm.getCost());
                warrantyData.setCost(new BigDecimal(cost).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(warrantyData.getCost().compareTo(new BigDecimal(0)) < 0)
                {
                    ae.add("Warranty Cost", new ActionError("error.wrongNegativeValue", "Warranty Cost", warrantyData.getCost()));
                }
            } catch (NumberFormatException e) {
                ae.add("Warranty Cost", new ActionError("error.wrongNumberFormat", "Warranty Cost", pForm.getCost()));
                e.printStackTrace();
            }
        } else {
            warrantyData.setCost(null);
        }

        if (!Utility.isSet(pForm.getDuration())) {
            ae.add("Duration", new ActionError("variable.empty.error", "Duration"));
        } else {
            try {
                int duration = Integer.parseInt(pForm.getDuration());
                if(duration < 0)
                {
                    ae.add("Duration", new ActionError("error.wrongNegativeValue", "Duration", duration));
                }
                
            } catch (NumberFormatException e) {
                ae.add("Duration", new ActionError("error.wrongNumberFormat", "Duration", pForm.getDuration()));
            }

        }

        if (!Utility.isSet(pForm.getWarrantyProviderId())) {
            ae.add("Warranty Provider", new ActionError("variable.empty.error", "Warranty Provider"));
        } else {
            try {
                int val = Integer.parseInt(pForm.getWarrantyProviderId());
                BusEntityData provider = (BusEntityData) pForm.getAllWarrantyProvidersMap().get(new Integer(val));
                if (provider == null) {
                    throw new Exception("Can't set this Warranty Provider");
                }
            } catch (NumberFormatException e) {
                ae.add("Warranty Provider", new ActionError("error.simpleGenericError", e.getMessage()));
                e.printStackTrace();
            }
        }

        if (Utility.isSet(pForm.getWarrantyProviderId()))
        {
            try {
                int val = Integer.parseInt(pForm.getServiceProviderId());
                BusEntityData provider = (BusEntityData) pForm.getAllServiceProvidersMap().get(new Integer(val));
                if (provider == null) {
                    throw new Exception("Can't set this Service Provider");
                }
            } catch (NumberFormatException e) {
                ae.add("Service Provider", new ActionError("variable.empty.error", "Service Provider"));
                e.printStackTrace();
            }
        }

        return ae;
    }

    private static WarrantyAssocDataVector getWarrantyAssocDataVector(WarrantyAssocViewVector warrantyAssocVV) {
        WarrantyAssocDataVector result = new WarrantyAssocDataVector();
        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocV = ((WarrantyAssocView) it.next());
                if (assocV != null) {
                    result.add(assocV.getWarrantyAssoc());
                }
            }
        }
        return result;
    }


    public static ActionErrors init(HttpServletRequest request, UserWarrantyConfigMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        if (detailForm != null) {

            pForm = new UserWarrantyConfigMgrForm();
            pForm.setWarrantyData(detailForm.getWarrantyData());
            pForm.setAllOnlyConfigs(detailForm.getAssetWarrantyViewVector());
            session.setAttribute(USER_WARRANTY_CONFIG_MGR_FORM, pForm);

            session.setAttribute("Warranty.asset.category.vector", new AssetDataVector());

            ae.add(checkRequest(request, pForm));
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = new APIAccess();
            Asset assetEjb = factory.getAssetAPI();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            IdVector storeIds = new IdVector();
            storeIds.add(new Integer(appUser.getUserStore().getStoreId()));

            if (detailForm.getAllWarrantyProvidersMap() != null) {
                try {
                    int warProviderId = Integer.parseInt(detailForm.getWarrantyProviderId());
                    pForm.setFilterWarrantyProvider((BusEntityData) detailForm.getAllWarrantyProvidersMap().get(new Integer(warProviderId)));
                } catch (NumberFormatException e) {
                    log.info("init => WARNING:" + e.getMessage());
                }
            }

            AssetDataVector assetCategories = getAssetCategories(assetEjb, storeIds);
            session.setAttribute("Warranty.asset.category.vector", assetCategories);

            session.setAttribute(USER_WARRANTY_CONFIG_MGR_FORM, pForm);

        } else {
            ae.add("error", new ActionError("error.simpleGenericError", "Can't build config info."));
        }

        return ae;
    }


    private static AssetDataVector getAssetCategories(Asset assetEjb, IdVector storeIds) throws Exception {
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setStoreIds(storeIds);
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
        criteria.setShowInactive(true);
        AssetDataVector assetCategories = assetEjb.getAssetDataByCriteria(criteria);
        return assetCategories;
    }

    public static ActionErrors getWarrantyConfiguration(HttpServletRequest request, UserWarrantyConfigMgrForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        int assetCatId = -1;
        String assetCatIdStr = request.getParameter("assetCategoryId");
        if (!Utility.isSet(assetCatIdStr)) {
            try {
                assetCatId = Integer.parseInt(pForm.getActiveAssetCategoryId());
            } catch (NumberFormatException e) {
            }
        } else {
            try {
                assetCatId = Integer.parseInt(assetCatIdStr);
            } catch (NumberFormatException e) {
            }
        }


        boolean manufFilter;
        String manufFilterStr = request.getParameter("manufFilter");
        if (!Utility.isSet(manufFilterStr)) {
            manufFilter = pForm.getManufFilter();
        } else {
            manufFilter = Utility.isTrue(request.getParameter("manufFilter"), true);
        }

        ae = init(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        pForm = (UserWarrantyConfigMgrForm) session.getAttribute(USER_WARRANTY_CONFIG_MGR_FORM);

        pForm.setManufFilter(manufFilter);
        pForm.setActiveAssetCategoryId(String.valueOf(assetCatId));

        if (pForm.getWarrantyData() != null && pForm.getWarrantyData().getWarrantyId() > 0) {
            try {
                if (assetCatId > -1) {
                    SelectableObjects selectedObjects = getAssetWarrantyConfigObject(appUser, pForm, manufFilter, assetCatId);
                    pForm.setConfigResults(selectedObjects);
                }

            } catch (Exception e) {
                e.printStackTrace();
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        } else {
            ae.add("WarrantyId", new ActionError("variable.empty.error", "WarrantyId"));
        }
        return ae;
    }

    public static SelectableObjects getAssetWarrantyConfigObject(CleanwiseUser appUser,
                                                                 UserWarrantyConfigMgrForm form,
                                                                 boolean manufFilter,
                                                                 int assetCatId) throws Exception {

        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();

        AssetSearchCriteria criteria = new AssetSearchCriteria();
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(appUser.getUserStore().getStoreId()));

        criteria.setStoreIds(storeIds);
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);
        criteria.setShowInactive(true);

        if (assetCatId > 0) {
            criteria.setParentId(assetCatId);
        }

        if (form.getFilterWarrantyProvider() != null && manufFilter) {
            criteria.setManufName(Utility.strNN(form.getFilterWarrantyProvider().getShortDesc()));
        }

        AssetViewVector configDV = null;
        AssetViewVector all = null;

        configDV = filterAsset(form.getAllOnlyConfigs(), criteria.getManufName(), criteria.getParentId());
        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {

            try {
                all = assetEjb.getAssetViewVector(criteria);
            } catch (DataNotFoundException e) {
                log.info(e.getMessage());
                all = new AssetViewVector();
            }
        } else {
            all = configDV;
        }

        return new SelectableObjects(configDV, all, ASSET_ID_COMPARE);

    }

    private static AssetViewVector filterAsset(AssetWarrantyViewVector assetWarrantyViewVector, String manufName, int catId) {
        AssetViewVector assetCollection = new AssetViewVector();
        if (assetWarrantyViewVector != null) {
            Iterator it = assetWarrantyViewVector.iterator();
            while (it.hasNext()) {
                AssetView asset = ((AssetWarrantyView) it.next()).getAssetView();
                if (Utility.isSet(manufName) && catId > 0) {
                    if (manufName.equals(asset.getManufName()) && asset.getParentId() == catId) {
                        assetCollection.add(asset);
                    }
                } else if (catId > 0 && !Utility.isSet(manufName)) {
                    if (asset.getParentId() == catId) {
                        assetCollection.add(asset);
                    }
                } else if (Utility.isSet(manufName) && catId <= 0) {
                    if (manufName.equals(asset.getManufName())) {
                        assetCollection.add(asset);
                    }
                } else {
                    assetCollection.add(asset);
                }
            }
        }
        return assetCollection;
    }


    public static ActionErrors getAssetWarrantyDetails(HttpServletRequest request, UserWarrantyConfigDetailMgrForm awcForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        UserWarrantyConfigMgrForm form = (UserWarrantyConfigMgrForm) session.getAttribute(USER_WARRANTY_CONFIG_MGR_FORM);

        try {

            int assetIdInt      = getIdFromRequest(request, "assetId");
            int assetWarrantyId = getAssetWarrantyIdFromRequest(request, awcForm);

            ae.add(init(request, form));
            if (ae.size() > 0) {
                return ae;
            }

            form = (UserWarrantyConfigMgrForm) session.getAttribute(USER_WARRANTY_CONFIG_MGR_FORM);
            ae.add(init(request, awcForm));
            if (ae.size() > 0) {
                return ae;
            }

            awcForm = (UserWarrantyConfigDetailMgrForm) session.getAttribute(USER_WARRANTY_CONFIG_DETAIL_MGR_FORM);
            AssetWarrantyView awview = null;

            if (assetWarrantyId > 0) {
                awview = filterAssetWarantyViewByAssetWarrantyId(assetWarrantyId, form.getAllOnlyConfigs());
            } else if (assetIdInt > 0) {
                awview = filterAssetWarantyViewByAssetId(assetIdInt, form.getAllOnlyConfigs());
            }

            if (awview == null) {
                throw new Exception("Can't build detail data for assetId=" + assetIdInt);
            }

            awcForm.setEditAssetWarranty(awview);

        } catch (Exception e) {
            try {
                e.printStackTrace();
                resetSessionAttributes(request, form);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }

        session.setAttribute(USER_WARRANTY_CONFIG_DETAIL_MGR_FORM, awcForm);
        session.setAttribute(USER_WARRANTY_CONFIG_MGR_FORM, form);

        return ae;
    }

    private static AssetWarrantyView filterAssetWarantyViewByAssetId(int assetIdInt, List awViewVector) {

        if (awViewVector != null) {
            Iterator it = awViewVector.iterator();
            while (it.hasNext()) {
                AssetWarrantyView awView = ((AssetWarrantyView) it.next());
                if (awView.getAssetWarrantyData() != null) {
                    if (assetIdInt == awView.getAssetWarrantyData().getAssetId()) {
                        return awView;
                    }
                }
            }
        }
        return null;
    }

    private static AssetWarrantyView filterAssetWarantyViewByAssetWarrantyId(int awIdInt, List awViewVector) {

        if (awViewVector != null) {
            Iterator it = awViewVector.iterator();
            while (it.hasNext()) {
                AssetWarrantyView awView = ((AssetWarrantyView) it.next());
                if (awView.getAssetWarrantyData() != null) {
                    if (awIdInt == awView.getAssetWarrantyData().getAssetWarrantyId()) {
                        return awView;
                    }
                }
            }
        }
        return null;
    }

    private static void resetSessionAttributes(HttpServletRequest request, UserWarrantyConfigMgrForm form) {
    }

    private static ActionMessages init(HttpServletRequest request, UserWarrantyConfigDetailMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        if (detailForm != null) {
            form = new UserWarrantyConfigDetailMgrForm();
            form.setWarrantyData(detailForm.getWarrantyData());
            session.setAttribute(USER_WARRANTY_CONFIG_DETAIL_MGR_FORM, form);
            ae.add(checkRequest(request, form));

        } else {
            throw new Exception("Can't build config info.");
        }

        return ae;
    }

    private static int getIdFromRequest(HttpServletRequest request, String idName) {
        String idStr = request.getParameter(idName);
        int idInt = -1;
        if (idStr != null) {
            try {
                idInt = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                log.info("getIdFromRequest => exc:" + e.getMessage());
            }
        }
        return idInt;
    }

    private static int getAssetWarrantyIdFromRequest(HttpServletRequest request, UserWarrantyConfigDetailMgrForm form) throws Exception {
        String assetWarrantyIdStr = request.getParameter("assetWarrantyId");
        int assetWarrantyIdInt = -1;

        try {
            assetWarrantyIdInt = Integer.parseInt(assetWarrantyIdStr);
        } catch (NumberFormatException e) {
            if (assetWarrantyIdStr == null
                    && form != null
                    && form.getWarrantyData() != null
                    && form.getEditAssetWarranty() != null
                    && form.getEditAssetWarranty().getAssetWarrantyData() != null) {
                assetWarrantyIdInt = form.getEditAssetWarranty().getAssetWarrantyData().getAssetWarrantyId();
            }
        }

        return assetWarrantyIdInt;
    }

    public static ActionErrors updateAssetWarrantyConfig(HttpServletRequest request, UserWarrantyConfigMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            String errorMess = ClwI18nUtil.getMessageOrNull(request, "shop.errors.notAuthorizedForAssetManagement");
            if (!Utility.isSet(errorMess)) {
                errorMess = "Access is denied to asset management";
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            if (pForm.getConfigResults() != null) {
                List newlySelected = pForm.getConfigResults().getNewlySelected();
                List deselected = pForm.getConfigResults().getDeselected();
                IdVector newlyIds = getAssetIds(newlySelected);
                IdVector deselectedIds = getAssetWarrantyIds(deselected, pForm.getAllOnlyConfigs());
                int warrantyId = pForm.getWarrantyData().getWarrantyId();
                if (warrantyId>0&&((deselectedIds != null && !deselectedIds.isEmpty()) || (newlyIds != null && !newlyIds.isEmpty()))) {
                    addNewAssetWarrantyToForm(detailForm,newlyIds);
                    removeAssetWarrantyFromForm(detailForm,deselectedIds);
                    warrantyEjb.updateAssetWarrantyView(warrantyId, detailForm.getAssetWarrantyViewVector(), appUser.getUser());
                    refreshFormData(request, pForm);
                    ae = getWarrantyConfiguration(request, pForm);
                } else {
                    addNewAssetWarrantyToForm(detailForm,newlyIds);
                    removeAssetWarrantyFromForm(detailForm,deselectedIds);
                }
            }
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }


    private static void removeAssetWarrantyFromForm(UserWarrantyDetailMgrForm detailForm, List deselectedIds) {
        if(deselectedIds!=null && !deselectedIds.isEmpty()){
            AssetWarrantyViewVector currentlyAssetWarranty = detailForm.getAssetWarrantyViewVector();
            if(currentlyAssetWarranty!=null && !currentlyAssetWarranty.isEmpty()) {
                Iterator deselecteIt =  deselectedIds.iterator();
                while(deselecteIt.hasNext()){
                    Iterator currenltyIt = currentlyAssetWarranty.iterator();
                    Integer desId = (Integer)deselecteIt.next();
                    while(currenltyIt.hasNext()){
                        AssetWarrantyView currAssetWarranty = (AssetWarrantyView)currenltyIt.next();
                        if(currAssetWarranty.getAssetWarrantyData().getAssetWarrantyId()==desId.intValue()) {
                            currenltyIt.remove();
                        }
                    }
                }
            }
        }
    }

    public static void addNewAssetWarrantyToForm(UserWarrantyDetailMgrForm detailForm,IdVector newlyIds) throws Exception {
        if(newlyIds!=null && !newlyIds.isEmpty()){
            AssetWarrantyViewVector currentlyAssetWarranty = detailForm.getAssetWarrantyViewVector();
            if(currentlyAssetWarranty==null) {
                currentlyAssetWarranty = new AssetWarrantyViewVector();
            }
            Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
            AssetSearchCriteria criteria = new AssetSearchCriteria();
            criteria.setAssetIds(newlyIds);
            AssetViewVector assetVV=assetEjb.getAssetViewVector(criteria);
            if(!assetVV.isEmpty()){
                Iterator assetIt = assetVV.iterator();
                while(assetIt.hasNext()){
                    AssetView assetV = (AssetView) assetIt.next();
                    AssetWarrantyData assetWarrantyData =  AssetWarrantyData.createValue();
                    assetWarrantyData.setAssetId(assetV.getAssetId());
                    AssetWarrantyView newAssetWarranty = new AssetWarrantyView(assetWarrantyData,assetV,new WarrantyNoteDataVector());
                    currentlyAssetWarranty.add(newAssetWarranty);
                }
            }

            detailForm.setAssetWarrantyViewVector(currentlyAssetWarranty);
        }
    }


    private static IdVector getAssetIds(List assetCollection) {
        IdVector ids = new IdVector();
        if (assetCollection != null) {
            Iterator it = assetCollection.iterator();
            while (it.hasNext()) {
                AssetView asset = (AssetView) it.next();
                ids.add(new Integer(asset.getAssetId()));
            }
        }
        return ids;
    }

    private static IdVector getAssetWarrantyIds(List assetDataVector, AssetWarrantyViewVector allOnlyConfigs) {

        IdVector awIds = new IdVector();

        if (assetDataVector != null) {
            Iterator it = assetDataVector.iterator();
            if (allOnlyConfigs != null) {
                while (it.hasNext()) {
                    AssetView assetV = (AssetView) it.next();
                    Iterator it2 = allOnlyConfigs.iterator();
                    while (it2.hasNext()) {
                        AssetWarrantyView awData = (AssetWarrantyView) it2.next();
                        if (assetV.getAssetId() == awData.getAssetView().getAssetId()) {
                            awIds.add(new Integer(awData.getAssetWarrantyData().getAssetWarrantyId()));
                            it.remove();
                            break;
                        }
                    }
                }
            }
        }
        return awIds;
    }

    private static ActionErrors refreshFormData(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        UserWarrantyDetailMgrForm detForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        ActionErrors ae = getDetail(request, detForm);
        if (ae.size() > 0) {
            throw new Exception("Detail data can't be refreshed");
        }
        return ae;
    }

    public static ActionErrors getNoteDetail(HttpServletRequest request, UserWarrantyNoteMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int warrantyNoteId     = getWarrantyNoteIdFromRequest(request, form);

        ae.add(init(request, form));
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        form = (UserWarrantyNoteMgrForm) session.getAttribute(USER_WARRANTY_NOTE_MGR_FORM);

        WarrantyNoteData note = warrantyEjb.getWarrantyNote(warrantyNoteId);

        form.setNoteDetail(note);

        session.setAttribute(USER_WARRANTY_NOTE_MGR_FORM, form);
        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, UserWarrantyNoteMgrForm form) {

    }

    private static int getWarrantyNoteIdFromRequest(HttpServletRequest request, UserWarrantyNoteMgrForm form) throws Exception {
        String warrantyNoteIdStr = request.getParameter("warrantyNoteId");
        int warrantyNoteIdInt = -1;
        try {
            warrantyNoteIdInt = Integer.parseInt(warrantyNoteIdStr);
        } catch (NumberFormatException e) {
            if (form.getWarrantyData() != null
                    && form.getNoteDetail() != null) {
                warrantyNoteIdInt = form.getNoteDetail().getWarrantyNoteId();
            }
        }
        return warrantyNoteIdInt;
    }

    public static ActionErrors init(HttpServletRequest request, UserWarrantyNoteMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        if (detailForm != null) {
            form = new UserWarrantyNoteMgrForm();
            form.setWarrantyData(detailForm.getWarrantyData());
            form.setWarrantyNoteTypeCd("");
            session.setAttribute(USER_WARRANTY_NOTE_MGR_FORM, form);
            ae.add(checkRequest(request, form));
        } else {
            ae.add("error", new ActionError("error.simpleGenericError", "Can't build note info."));
        }
        return ae;
    }

    public static ActionErrors removeWarrantyNote(HttpServletRequest request, UserWarrantyNoteMgrForm form) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            int warrantyNoteId = getWarrantyNoteIdFromRequest(request, form);
            warrantyEjb.removeWarrantyNote(warrantyNoteId);
            form.setNoteDetail(WarrantyNoteData.createValue());
            refreshFormData(request, form);
            ae=init(request, form);
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }


    public static ActionErrors updateWarrantyNote(HttpServletRequest request, UserWarrantyNoteMgrForm form) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            WarrantyNoteData note = form.getNoteDetail();
            note = warrantyEjb.updateWarrantyNote(note, appUser.getUser());
            refreshFormData(request, form);
            form.setNoteDetail(note);
            return getNoteDetail(request, form);

        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWarrantyNoteMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        if (form.getNoteDetail() == null) {
            throw new Exception("Note can't be null");
        }

        if (!Utility.isSet(form.getNoteDetail().getNote())) {
            ae.add("Note", new ActionError("variable.empty.error", "Note"));
        }

        if (!Utility.isSet(form.getNoteDetail().getShortDesc())) {
            ae.add("Description", new ActionError("variable.empty.error", "Description"));
        }

        //if (!Utility.isSet(form.getNoteDetail().getTypeCd())) {
        //    ae.add("TypeCode", new ActionError("variable.empty.error", "Type Code"));
        //}

        if (form.getWarrantyData().getWarrantyId() <= 0 || form.getNoteDetail().getWarrantyId() <= 0) {
            throw new Exception("WarrantyId = 0");
        }


        return ae;
    }

    public static ActionErrors createNewWarrantyNote(HttpServletRequest request, ActionForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init(request, (UserWarrantyNoteMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        UserWarrantyNoteMgrForm form = (UserWarrantyNoteMgrForm) session.getAttribute(USER_WARRANTY_NOTE_MGR_FORM);
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);

        form.setWarrantyData(detailForm.getWarrantyData());

        int warrantyId = getIdFromRequest(request, "warrantyId");
        int assetWarrantyId = getIdFromRequest(request, "assetWarrantyId");
        if (warrantyId > 0 && assetWarrantyId > 0) {
            WarrantyNoteData note = WarrantyNoteData.createValue();
            note.setWarrantyId(warrantyId);
            note.setAssetWarrantyId(assetWarrantyId);
            form.setNoteDetail(note);
        } else if (warrantyId > 0) {
            WarrantyNoteData note = WarrantyNoteData.createValue();
            note.setWarrantyId(warrantyId);
            form.setNoteDetail(note);
        } else if (assetWarrantyId > 0) {
            WarrantyNoteData note = WarrantyNoteData.createValue();
            note.setAssetWarrantyId(assetWarrantyId);
            note.setWarrantyId(detailForm.getWarrantyData().getWarrantyId());
            form.setNoteDetail(note);
        }else {
            throw new Exception("Warranty Note can't be created");
        }

        session.setAttribute(USER_WARRANTY_NOTE_MGR_FORM, form);
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, UserWarrantyContentMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        if (detailForm != null) {
            form = new UserWarrantyContentMgrForm();
            form.setWarrantyData(detailForm.getWarrantyData());

            ae = checkRequest(request, form);
            if (ae.size() > 0) {
                return ae;
            }

            session.setAttribute(USER_WARRANTY_CONTENT_MGR_FORM, form);
        }

        return ae;
    }


    public static ActionErrors getContentDetail(HttpServletRequest request, UserWarrantyContentMgrForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int warrantyContentIdInt = getWarrantyContentIdFromRequest(request, form);

        ae.add(init(request, form));
        if (ae.size() > 0) {
            return ae;
        }

        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        form = (UserWarrantyContentMgrForm) session.getAttribute(USER_WARRANTY_CONTENT_MGR_FORM);

        WarrantyContentView content = findWarrantyContent(detailForm.getContents(), warrantyContentIdInt);

        if (content == null) {
            throw new Exception("Can't find content.WarrantyContentId:" + warrantyContentIdInt);
        }

        APIAccess factory = new APIAccess();
        Warranty warrantyEjb = factory.getWarrantyAPI();

        WarrantyContentDetailView contentDetails = warrantyEjb.getWarrantyContentDetails(warrantyContentIdInt);

        uploadContentDetailData(request,contentDetails, form);


        session.setAttribute(USER_WARRANTY_CONTENT_MGR_FORM, form);
        return ae;
    }

    private static void uploadContentDetailData(HttpServletRequest request,
                                                WarrantyContentDetailView content,
                                                UserWarrantyContentMgrForm form) {

        form.setWarrantyId(content.getWarrantyContentData().getWarrantyId());
        form.setWarrantyContentId(content.getWarrantyContentData().getWarrantyContentId());
        form.setUrl(content.getWarrantyContentData().getUrl());
        form.setWarrantyAddBy(content.getWarrantyContentData().getAddBy());
        form.setWarrantyModBy(content.getWarrantyContentData().getModBy());
        form.setWarrantyAddDate(content.getWarrantyContentData().getAddDate());
        form.setWarrantyModDate(content.getWarrantyContentData().getModDate());

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

        if (content.getContent().getData() != null) {
            form.setContentSize(String.valueOf(content.getContent().getData().length) + " bytes");
        }

        form.setContentAddBy(content.getContent().getAddBy());
        form.setContentModBy(content.getContent().getModBy());
        form.setContentAddDate(content.getContent().getAddDate());
        form.setContentModDate(content.getContent().getModDate());

    }


    private static void resetSessionAttributes(HttpServletRequest request, UserWarrantyContentMgrForm form) {
    }

    public static ActionErrors findContent(HttpServletRequest request, UserWarrantyContentMgrForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        ae.add(init(request, form));
        if (ae.size() > 0) {
            return ae;
        }

        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);
        form.setFindResult(detailForm.getContents());

        session.setAttribute(USER_WARRANTY_CONTENT_MGR_FORM, form);
        return ae;
    }

    private static WarrantyContentView findWarrantyContent(WarrantyContentViewVector contents, int warrantyContentIdInt) {
        if (contents != null) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                WarrantyContentView content = ((WarrantyContentView) it.next());
                if (content != null &&
                        content.getWarrantyContentData() != null &&
                        content.getWarrantyContentData().getWarrantyContentId() == warrantyContentIdInt) {
                    return content;
                }
            }
        }
        return null;
    }


    public static ActionErrors createNewContent(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init(request, (UserWarrantyContentMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        UserWarrantyContentMgrForm form = (UserWarrantyContentMgrForm) session.getAttribute(USER_WARRANTY_CONTENT_MGR_FORM);
        UserWarrantyDetailMgrForm detailForm = (UserWarrantyDetailMgrForm) session.getAttribute(USER_WARRANTY_DETAIL_MGR_FORM);

        form.setWarrantyData(detailForm.getWarrantyData());
        WarrantyContentDetailView content = new WarrantyContentDetailView(ContentDetailView.createValue(), WarrantyContentData.createValue());
        content.getWarrantyContentData().setWarrantyId(form.getWarrantyData().getWarrantyId());

        uploadContentDetailData(request,content, form);

        session.setAttribute(USER_WARRANTY_CONTENT_MGR_FORM, form);
        return ae;
    }


    private static int getWarrantyContentIdFromRequest(HttpServletRequest request, UserWarrantyContentMgrForm form) {
        int warrantyContentId = -1;
        warrantyContentId = getIdFromRequest(request, "warrantyContentId");
        if (warrantyContentId < 0 &&
                form != null &&
                form.getWarrantyContentId() > 0) {
            warrantyContentId = form.getWarrantyContentId();
        }

        return warrantyContentId;
    }


    public static ActionErrors updateContentData(HttpServletRequest request, UserWarrantyContentMgrForm form) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            String errorMess = ClwI18nUtil.getMessageOrNull(request, "shop.errors.notAuthorizedForAssetManagement");
            if (!Utility.isSet(errorMess)) {
                errorMess = "Access is denied to asset management";
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantyContentDetailView warrantyContent = WarrantyContentDetailView.createValue();
            warrantyContent = loadContentDetailData(request,warrantyContent, form);
            warrantyContent = warrantyEjb.updateWarrantyContent(warrantyContent, appUser.getUser());
            refreshFormData(request, form);
            uploadContentDetailData(request,warrantyContent, form);
            return getContentDetail(request, form);

        } catch (Exception e) {
            try {
                error(e.getMessage(), e);
                resetSessionAttributes(request, form);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    private static WarrantyContentDetailView loadContentDetailData(HttpServletRequest request,
                                                                   WarrantyContentDetailView warrantyContent,
                                                                   UserWarrantyContentMgrForm form) throws IOException {

        WarrantyContentData warrantyContentData = WarrantyContentData.createValue();
        warrantyContentData.setWarrantyId(form.getWarrantyId());
        warrantyContentData.setWarrantyContentId(form.getWarrantyContentId());
        warrantyContentData.setUrl(form.getUrl());
        warrantyContentData.setAddBy(form.getWarrantyAddBy());
        warrantyContentData.setModBy(form.getWarrantyModBy());
        warrantyContentData.setAddDate(form.getWarrantyAddDate());
        warrantyContentData.setModDate(form.getWarrantyModDate());

        ContentDetailView content = ContentDetailView.createValue();
        content.setContentId(form.getContentId());
        content.setBusEntityId(form.getBusEntityId());
        content.setItemId(form.getItemId());

        if (!Utility.isSet(form.getContentStatusCd())) {
            content.setContentStatusCd(RefCodeNames.CONTENT_STATUS_CD.ACTIVE);
        } else {
            content.setContentStatusCd(form.getContentStatusCd());
        }

        if (!Utility.isSet(form.getLanguageCd())) {
            content.setLanguageCd("x");
        } else {
            content.setLanguageCd(form.getLanguageCd());
        }

        if (!Utility.isSet(form.getLocaleCd())) {
            content.setLocaleCd("x");
        } else {
            content.setLocaleCd(form.getLocaleCd());
        }

        if (!Utility.isSet(form.getContentUsageCd())) {
            content.setContentUsageCd("N/A");
        } else {
            content.setContentUsageCd(form.getContentUsageCd());
        }

        if (!Utility.isSet(form.getSourceCd())) {
            content.setSourceCd("N/A");
        } else {
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
                if (!Utility.isSet(form.getContentTypeCd())) {
                    content.setContentTypeCd("N/A");
                } else {
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

        warrantyContent = new WarrantyContentDetailView(content, warrantyContentData);
        return warrantyContent;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWarrantyContentMgrForm form) {

        ActionErrors ae = new ActionErrors();

        if (!Utility.isSet(form.getShortDesc())) {
            ae.add("ShortDesc", new ActionError("variable.empty.error", "Short Description"));
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
        return ae;
    }

    public static ActionErrors removeWarrantyContent(HttpServletRequest request, UserWarrantyContentMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
            String errorMess = ClwI18nUtil.getMessageOrNull(request, "shop.errors.notAuthorizedForAssetManagement");
            if (!Utility.isSet(errorMess)) {
                errorMess = "Access is denied to asset management";
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            int wcId = pForm.getWarrantyContentId();
            int cId = pForm.getContentId();
            boolean removeFlag = warrantyEjb.removeWarrantyContent(wcId, cId);
            if (removeFlag) {
                refreshFormData(request, pForm);
                ae = createNewContent(request, pForm);
            }


        } catch (Exception e) {
            error(e.getMessage(), e);
            resetSessionAttributes(request, pForm);
            throw new Exception(e.getMessage());
        }
        return ae;
    }

    public static ActionErrors readDocument(HttpServletRequest request, HttpServletResponse response, UserWarrantyContentMgrForm form) throws Exception {

        ActionErrors ae = getContentDetail(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        form = (UserWarrantyContentMgrForm) request.getSession().getAttribute(USER_WARRANTY_CONTENT_MGR_FORM);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(form.getData());
        response.setContentType(form.getContentTypeCd());
        response.setContentLength(out.size());
        out.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return ae;

    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {
        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }
}
