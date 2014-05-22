package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Warranty;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.rmi.RemoteException;
import org.apache.log4j.*;

/**
 * Title:        StoreWarrantyMgrLogic
 * Description:  Logic manager for the warranty processing
 * Purpose:      Executes operation for the warranty processing
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         27.09.2007
 * Time:         17:13:27
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class StoreWarrantyMgrLogic {
    private static final Logger log = Logger.getLogger(StoreWarrantyMgrLogic.class);

    private static final String STORE_WARRANTY_DETAIL_FORM              = "STORE_WARRANTY_DETAIL_FORM";
    private static final String STORE_WARRANTY_CONFIG_FORM              = "STORE_WARRANTY_CONFIG_FORM" ;
    private static final String STORE_WARRANTY_MGR_FORM                 = "STORE_WARRANTY_MGR_FORM";
    private static final String STORE_WARRANTY_NOTE_MGR_FORM            = "STORE_WARRANTY_NOTE_MGR_FORM";
    private static final String STORE_WARRANTY_CONTENT_MGR_FORM         = "STORE_WARRANTY_CONTENT_MGR_FORM";
    private static final String STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM = "STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM";
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    static final Comparator ASSET_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2)
        {
            int id1 = ((AssetView)o1).getAssetId();
            int id2 = ((AssetView)o2).getAssetId();
            return id2 - id1;
        }
    };

    public static ActionErrors init(HttpServletRequest request, StoreWarrantyMgrForm form) {
        HttpSession session=request.getSession();
        ActionErrors ae=new ActionErrors();
        form=new StoreWarrantyMgrForm();
        form.setSearchFieldType(StoreWarrantyMgrForm.WARRANTY_NAME);
        form.setSearchType(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
        session.setAttribute(STORE_WARRANTY_MGR_FORM,form);
        ae.add(checkRequest(request,form));
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, StoreWarrantyDetailForm form) {

        HttpSession session = request.getSession();
        ActionErrors ae     = new ActionErrors();
        form                = new StoreWarrantyDetailForm();

        try {

            APIAccess factory = new APIAccess();

            Warranty warrantyEjb       = factory.getWarrantyAPI();
            ListService listServiceEJB = factory.getListServiceAPI();

            session.setAttribute("Warranty.provider.vector",new BusEntityDataVector());
            session.setAttribute("Warranty.status.vector",new RefCdDataVector());
            session.setAttribute("Warranty.type.vector",new RefCdDataVector());
            session.setAttribute("Coverage.cds.vector",new RefCdDataVector());
            session.setAttribute("Warranty.note.type.cds",new RefCdDataVector());
            session.setAttribute("Content.status.vector",new RefCdDataVector());
            session.setAttribute("Warranty.documents.locale.vector",new RefCdDataVector());
            session.setAttribute("Warranty.duration.type.vector",new RefCdDataVector());


            ae.add(checkRequest(request,form));
            if(ae.size()>0){
                resetSessionAttributes(request,form);
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            BusEntityDataVector wProviders = warrantyEjb.getWarrantyProvidersForStore(appUser.getUserStore().getStoreId());
            DisplayListSort.sort(wProviders,"short_desc");
            HashMap warrantyProvidersMap = toMapByBusEntityId(wProviders);
            session.setAttribute("Warranty.provider.vector",wProviders);
            form.setAllWarrantyProvidersMap(warrantyProvidersMap);

            BusEntityDataVector sProviders = warrantyEjb.getServiceProvidersForStore(appUser.getUserStore().getStoreId());
            DisplayListSort.sort(sProviders,"short_desc");
            HashMap serviceProvidersMap = toMapByBusEntityId(sProviders);
            session.setAttribute("Warranty.serviceProvider.vector",sProviders);
            form.setAllServiceProvidersMap(serviceProvidersMap);

            RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WARRANTY_STATUS_CD");
            session.setAttribute("Warranty.status.vector",statusCds);

            RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("WARRANTY_TYPE_CD");
            session.setAttribute("Warranty.type.vector",typeCds);

            RefCdDataVector durationTypeCds = listServiceEJB.getRefCodesCollection("WARRANTY_DURATION_TYPE_CD");
            session.setAttribute("Warranty.duration.type.vector",durationTypeCds);

            RefCdDataVector covCds = listServiceEJB.getRefCodesCollection("COVERAGE_IND_CD");
            session.setAttribute("Coverage.cds.vector",covCds);


            RefCdDataVector wContentStatusCds = listServiceEJB.getRefCodesCollection("CONTENT_STATUS_CD");
            session.setAttribute("Content.status.vector",wContentStatusCds);

            RefCdDataVector warrantyNoteTypeCds = listServiceEJB.getRefCodesCollection("WARRANTY_NOTE_TYPE_CD");
            session.setAttribute("Warranty.note.type.cds",warrantyNoteTypeCds);

            RefCdDataVector warrantyDocsLocaleCds = listServiceEJB.getRefCodesCollection("LOCALE_CD");
            session.setAttribute("Warranty.documents.locale.vector",warrantyDocsLocaleCds);

            session.setAttribute(STORE_WARRANTY_DETAIL_FORM,form);

        } catch (Exception e) {
            e.printStackTrace();
            resetSessionAttributes(request,form);
            ae.add("error",new ActionError("error.simpleGenericError",e.getMessage()));
        }

        return ae;
    }

    public static ActionErrors search(HttpServletRequest request, StoreWarrantyMgrForm form) {

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        try {
            APIAccess factory    = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantySimpleSearchCriteria criteria = new WarrantySimpleSearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            //add data to criteria
            if (Utility.isSet(form.getSearchField())) {
                if (StoreWarrantyMgrForm.ASSET_NUMBER.equals(form.getSearchFieldType())) {
                    criteria.setAssetNumber(form.getSearchField());
                    criteria.setSearchType(form.getSearchType());
                } else if (StoreWarrantyMgrForm.ASSET_NAME.equals(form.getSearchFieldType())) {
                    criteria.setAssetName(form.getSearchField());
                    criteria.setSearchType(form.getSearchType());
                } else if (StoreWarrantyMgrForm.WARRANTY_ID.equals(form.getSearchFieldType())) {
                    int id = 0;
                    try {
                        id = Integer.parseInt(form.getSearchField());
                    } catch (NumberFormatException e) {
                        ae.add("Warranty", new ActionError("error.invalidNumber", form.getSearchField()));
                        return ae;
                    }
                    criteria.setWarrantyId(id);
                } else if (StoreWarrantyMgrForm.WARRANTY_PROVIDER.equals(form.getSearchFieldType())){
                    criteria.setWarrantyProvider(form.getSearchField());
                    criteria.setSearchType(form.getSearchType());
                } else if (StoreWarrantyMgrForm.SERVICE_PROVIDER.equals(form.getSearchFieldType())){
                    criteria.setServiceProvider(form.getSearchField());
                    criteria.setSearchType(form.getSearchType());
                }
                else {
                    criteria.setWarrantyName(form.getSearchField());
                    criteria.setSearchType(form.getSearchType());
                }
            }

            criteria.setStoreIds(appUser.getUserStoreAsIdVector());

            WarrantyViewVector result = warrantyEjb.getWarrantyViewCollection(criteria);

            form.setSearchResult(result);
            session.setAttribute(STORE_WARRANTY_MGR_FORM, form);

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

    private static ActionErrors checkRequest(HttpServletRequest request, StoreWarrantyDetailForm form) {

        ActionErrors ae       = new ActionErrors();
        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if(form==null) {
            ae.add("error",new ActionError("error.simpleGenericError","Form not initialized"));
            return ae;
        }

        if(appUser==null){
            ae.add("error",new ActionError("error.simpleGenericError","No user info"));
            return ae;
        }

        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreWarrantyMgrForm form) {

        ActionErrors ae       = new ActionErrors();
        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if(form==null) {
            ae.add("error",new ActionError("error.simpleGenericError","Form not initialized"));
            return ae;
        }

        if(appUser==null){
            ae.add("error",new ActionError("error.simpleGenericError","No user info"));
            return ae;
        }

        return ae;
    }

    private static ActionErrors resetSessionAttributes(HttpServletRequest request, StoreWarrantyDetailForm pForm) {

        HttpSession session = request.getSession();
        ActionErrors ae     = new ActionErrors();

        try {
            session.setAttribute("Warranty.type.vector",new RefCdDataVector());
            session.setAttribute("Warranty.provider.vector",new BusEntityDataVector());
            session.setAttribute("Warranty.status.vector",new RefCdDataVector());
            session.setAttribute("Coverage.cds.vector",new RefCdDataVector());
            session.setAttribute("Warranty.note.type.cds",new RefCdDataVector());
            session.setAttribute("Content.status.vector",new RefCdDataVector());
            session.setAttribute("Warranty.documents.locale.vector",new RefCdDataVector());
            session.setAttribute("Warranty.duration.type.vector",new RefCdDataVector());

            pForm = new StoreWarrantyDetailForm();
            session.setAttribute(STORE_WARRANTY_DETAIL_FORM,pForm);

        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
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

    public static ActionErrors createNewWarranty(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = init(request, (StoreWarrantyDetailForm) null);
        if(ae.size()>0) {
            return ae;
        }

        StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);

        ae=checkRequest(request,detailForm);
        if(ae.size()>0){
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

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

        uploadDetailData(warrantyDetail,detailForm);

        session.setAttribute(STORE_WARRANTY_DETAIL_FORM,detailForm);

        return ae;
    }

    private static void uploadDetailData(WarrantyDetailView warrantyDetail, StoreWarrantyDetailForm pForm) throws Exception {

        pForm.setWarrantyData(warrantyDetail.getWarrantyData());
        pForm.setWarrantyProvider(getWarrantyProvider(warrantyDetail.getWarrantyAssocViewVector()));
        pForm.setServiceProvider(getServiceProvider(warrantyDetail.getWarrantyAssocViewVector()));
        pForm.setContents(warrantyDetail.getContents());
        pForm.setWarrantyAssocViewVector(warrantyDetail.getWarrantyAssocViewVector());
        pForm.setStatusHistory(warrantyDetail.getStatusHistory());
        pForm.setWarrantyNotes(warrantyDetail.getWarrantyNotes());
        pForm.setAssetWarrantyViewVector(warrantyDetail.getAssetWarrantyViewVector());
        pForm.setAssetWarrantyCategoryAssoc(getAssetCategoryFor(warrantyDetail.getAssetWarrantyViewVector()));
        pForm.setWorkOrderItemWarrantyViewVector(warrantyDetail.getWorkOrderItemWarrantyViewVector());

        //add editing fields
        if(pForm.getWarrantyData()!=null){

            WarrantyData warrantyData = pForm.getWarrantyData();

            String warrantyName   = Utility.strNN(warrantyData.getShortDesc());
            String warrantyNumber = Utility.strNN(warrantyData.getWarrantyNumber());
            String typeCd         = Utility.strNN(warrantyData.getTypeCd());
            String statusCd       = Utility.strNN(warrantyData.getStatusCd());
            String longDesc       = Utility.strNN(warrantyData.getLongDesc());
            String businessName   = Utility.strNN(warrantyData.getBusinessName());

            String duration;
            if(warrantyData.getDuration()>0){
                duration = String.valueOf(warrantyData.getDuration());
            }else{
                duration="";
            }

            if(Utility.isSet(warrantyData.getDurationTypeCd())){
                pForm.setDurationTypeCd(warrantyData.getDurationTypeCd());
            } else{
                pForm.setDurationTypeCd(RefCodeNames.WARRANTY_DURATION_TYPE_CD.MONTHS);
            }

            String cost;
            if(warrantyData.getCost()!=null){
                cost= warrantyData.getCost().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }else{
                cost="";
            }

            String warrantyProviderId;
            if(pForm.getWarrantyProvider()!=null){
                warrantyProviderId = String.valueOf(pForm.getWarrantyProvider().getBusEntityId());
            } else{
                warrantyProviderId="0";
            }

            String serviceProviderId;
            if(pForm.getServiceProvider()!=null){
                serviceProviderId = String.valueOf(pForm.getServiceProvider().getBusEntityId());
            } else{
                serviceProviderId="0";
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

    private static AssetDataVector getAssetCategoryFor(AssetWarrantyViewVector assetWarrantyViewVector) throws Exception {

        IdVector assetIds = new IdVector();
        AssetDataVector assetCat = new AssetDataVector();
        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();
        if (assetWarrantyViewVector != null && !assetWarrantyViewVector.isEmpty()) {

            Iterator it = assetWarrantyViewVector.iterator();
            while (it.hasNext()) {
                AssetWarrantyView assetWarrView = (AssetWarrantyView) it.next();
                if (assetWarrView.getAssetView().getParentId() > 0 ) {
                    assetIds.add(new Integer(assetWarrView.getAssetView().getParentId()));
                }
            }
            if (!assetIds.isEmpty()) {
                AssetSearchCriteria criteria = new AssetSearchCriteria();
                criteria.setAssetIds(assetIds);
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

    public static ActionErrors getDetail(HttpServletRequest request, ActionForm form) {

        ActionErrors ae     = new ActionErrors();
        HttpSession session = request.getSession();

        StoreWarrantyDetailForm sForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);

        try {
            int warrantyIdInt = getWarrantyIdFromRequest(request, sForm);

            ae.add(init(request, sForm));
            if(ae.size()>0){
                return ae;
            }

            sForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);

            APIAccess factory = new APIAccess();
            Warranty warrantyEJB = factory.getWarrantyAPI();

            WarrantyDetailView warrantyDetail = warrantyEJB.getWarrantyDetailView(warrantyIdInt);

            uploadDetailData(warrantyDetail, sForm);

        } catch (Exception e) {
            try {
                e.printStackTrace();
                resetSessionAttributes(request, sForm);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        session.setAttribute(STORE_WARRANTY_DETAIL_FORM, sForm);
        return ae;
    }

    /**
     * This method searches warranty_id in (HttpServletRequest)request
     * or (StoreWarrantyDetailForm )pForm and returnes this a  value
     *
     * @param pForm   StoreWarrantyDetailForm
     * @param request HttpServletRequest
     * @return warrantyId
     * @throws Exception exception
     */
    public static int getWarrantyIdFromRequest(HttpServletRequest request, StoreWarrantyDetailForm pForm) throws Exception {

        String warrantyIdStr = request.getParameter("warrantyId");
        int warrantyIdInt = -1;
        if (warrantyIdStr == null && pForm.getWarrantyData() != null) {
            warrantyIdInt = pForm.getWarrantyData().getWarrantyId();
        } else {
            try {
                warrantyIdInt = Integer.parseInt(warrantyIdStr);
            }
            catch (NumberFormatException e) {
                throw new Exception(e.getMessage());
            }
        }
        return warrantyIdInt;
    }

    public static ActionErrors updateWarranty(HttpServletRequest request, StoreWarrantyDetailForm pForm) {

        ActionErrors ae = new ActionErrors();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae=checkFormAttribute(request,pForm);
        if(ae.size()>0) {
            return ae;
        }

        HttpSession session =request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantyDetailView warrantyDetail = WarrantyDetailView.createValue();
            loadDetailData(warrantyDetail,pForm);
            WarrantyAssocDataVector warrantyAssoc = getWarrantyAssocDataVector(warrantyDetail.getWarrantyAssocViewVector());

            WarrantyData warrantyData = warrantyEjb.updateWarranty(warrantyDetail.getWarrantyData(),warrantyAssoc,appUser.getUser());

            pForm.setWarrantyData(warrantyData);
            return getDetail(request,pForm);
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static void loadDetailData(WarrantyDetailView warrantyDetail, StoreWarrantyDetailForm pForm) throws Exception {

        WarrantyData warrantyData = pForm.getWarrantyData();

        warrantyData.setShortDesc(pForm.getWarrantyName());
        warrantyData.setWarrantyNumber(pForm.getWarrantyNumber());
        warrantyData.setLongDesc(pForm.getLongDesc());
        warrantyData.setTypeCd(pForm.getTypeCd());
        warrantyData.setStatusCd(pForm.getStatusCd());
        warrantyData.setBusinessName(pForm.getBusinessName());
        warrantyData.setDuration(Integer.parseInt(pForm.getDuration()));

        if(Utility.isSet(pForm.getCost())){
            double cost = Double.parseDouble(pForm.getCost());
            warrantyData.setCost(new BigDecimal(cost).setScale(2,BigDecimal.ROUND_HALF_UP));
        } else{
            warrantyData.setCost(null);
        }

        if(Utility.isSet(pForm.getDurationTypeCd())){
            warrantyData.setDurationTypeCd(pForm.getDurationTypeCd());
        } else{
            warrantyData.setDurationTypeCd(RefCodeNames.WARRANTY_DURATION_TYPE_CD.MONTHS);
        }

        BusEntityData warrantyProvider = (BusEntityData) pForm.getAllWarrantyProvidersMap().get(new Integer(pForm.getWarrantyProviderId()));
        setWarrantyProviderToAssoc(warrantyProvider,pForm.getWarrantyAssocViewVector(),warrantyData);

        BusEntityData serviceProvider = (BusEntityData) pForm.getAllServiceProvidersMap().get(new Integer(pForm.getServiceProviderId()));
        setServiceProviderToAssoc(serviceProvider,pForm.getWarrantyAssocViewVector(),warrantyData);

        warrantyDetail.setWarrantyData(warrantyData);
        warrantyDetail.setWarrantyAssocViewVector(pForm.getWarrantyAssocViewVector());
        warrantyDetail.setContents(pForm.getContents());
        warrantyDetail.setStatusHistory(pForm.getStatusHistory());
        warrantyDetail.setWarrantyNotes(pForm.getWarrantyNotes());
        warrantyDetail.setAssetWarrantyViewVector(pForm.getAssetWarrantyViewVector());
        warrantyDetail.setWorkOrderItemWarrantyViewVector(pForm.getWorkOrderItemWarrantyViewVector());



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

    private static ActionErrors checkFormAttribute(HttpServletRequest request, StoreWarrantyDetailForm pForm) {

        ActionErrors ae = new ActionErrors();
        WarrantyData warrantyData = pForm.getWarrantyData();

        if (warrantyData == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "Warranty Data is null"));
            return ae;
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
            } catch (NumberFormatException e) {
                ae.add("Cost", new ActionError("variable.empty.error", "Cost"));
                e.printStackTrace();
            }
        } else {
            warrantyData.setCost(null);
        }

        if (!Utility.isSet(pForm.getDuration())) {
            ae.add("Duration", new ActionError("variable.empty.error", "Duration"));
        } else {
            try {
                Integer.parseInt(pForm.getDuration());
            } catch (NumberFormatException e) {
                ae.add("Duration", new ActionError("error.simpleGenericError", e.getMessage()));
            }

        }

        if (!Utility.isSet(pForm.getWarrantyProviderId())) {
            ae.add("Warranty Provider", new ActionError("variable.empty.error", "Warranty Provider"));
        } else {
            try {
                int val = Integer.parseInt(pForm.getWarrantyProviderId());
                BusEntityData provider = (BusEntityData) pForm.getAllWarrantyProvidersMap().get(new Integer(val));
                if (provider == null) {
                    ae.add("Warranty Provider", new ActionError("error.simpleGenericError", "Can't set this Warranty Provider"));
                }
            } catch (NumberFormatException e) {
                ae.add("Warranty Provider", new ActionError("error.simpleGenericError", e.getMessage()));
                e.printStackTrace();
            }
        }


        return ae;
    }

    private static WarrantyAssocViewVector setWarrantyProviderToAssoc(BusEntityData provider, WarrantyAssocViewVector warrantyAssoc, WarrantyData warrantyData) {
        if(warrantyAssoc==null){
            warrantyAssoc=new WarrantyAssocViewVector();
            WarrantyAssocView assocV = createWarrantyProviderAssocView(provider, warrantyData);
            if(assocV!=null){
                warrantyAssoc.add(assocV);
            }
        } else{
            warrantyAssoc=setNewAssocWarrantyProvider(warrantyAssoc,provider,warrantyData);
        }
        return warrantyAssoc;
    }

    private static WarrantyAssocViewVector setNewAssocWarrantyProvider(WarrantyAssocViewVector warrantyAssocVV, BusEntityData provider,WarrantyData warrantyData) {
        boolean isSet=false;
        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            //find and replace provider if provider exist
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocDetail = (WarrantyAssocView) it.next();
                if (assocDetail.getBusEntity() != null) {
                    if (RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER.equals(assocDetail.getBusEntity().getBusEntityTypeCd())) {
                        assocDetail.setBusEntity(provider);
                        assocDetail.getWarrantyAssoc().setBusEntityId(provider.getBusEntityId());
                        isSet=true;
                    }
                }
            }
            //if provider not exist create new assoc
            if(!isSet && provider!=null){
                WarrantyAssocView assocV = createWarrantyProviderAssocView(provider, warrantyData);
                if(assocV!=null){
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


    private static WarrantyAssocViewVector setServiceProviderToAssoc(BusEntityData provider, WarrantyAssocViewVector warrantyAssoc, WarrantyData warrantyData) {
        if(warrantyAssoc==null){
            warrantyAssoc=new WarrantyAssocViewVector();
            WarrantyAssocView assocV = createServiceProviderAssocView(provider, warrantyData);
            if(assocV!=null){
                warrantyAssoc.add(assocV);
            }
        } else{
            warrantyAssoc=setNewAssocServiceProvider(warrantyAssoc,provider,warrantyData);
        }
        return warrantyAssoc;
    }

    private static WarrantyAssocViewVector setNewAssocServiceProvider(WarrantyAssocViewVector warrantyAssocVV, BusEntityData provider,WarrantyData warrantyData) {
        boolean isSet=false;
        if (warrantyAssocVV != null && !warrantyAssocVV.isEmpty()) {
            //find and replace provider if provider exist
            Iterator it = warrantyAssocVV.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocDetail = (WarrantyAssocView) it.next();
                if (assocDetail.getBusEntity() != null) {
                    if (RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(assocDetail.getBusEntity().getBusEntityTypeCd())) {
                        assocDetail.setBusEntity(provider);
                        assocDetail.getWarrantyAssoc().setBusEntityId(provider.getBusEntityId());
                        isSet=true;
                    }
                }
            }
            //if provider not exist create new assoc
            if(!isSet && provider!=null){
                WarrantyAssocView assocV = createServiceProviderAssocView(provider, warrantyData);
                if(assocV!=null){
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


    public static ActionErrors init(HttpServletRequest request, StoreWarrantyConfigForm form) {

        ActionErrors ae=new ActionErrors();
        try{
            HttpSession session=request.getSession();
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            if(detailForm!=null)
            {
                StoreWarrantyConfigForm pForm=new  StoreWarrantyConfigForm();
                pForm.setWarrantyData(detailForm.getWarrantyData());
                pForm.setAllOnlyConfigs(detailForm.getAssetWarrantyViewVector());
                session.setAttribute(STORE_WARRANTY_CONFIG_FORM,pForm);

                session.setAttribute("Warranty.asset.category.vector",new AssetDataVector());

                ae.add(checkRequest(request,pForm));
                if(ae.size()>0){
                    return ae;
                }

                APIAccess factory = new APIAccess();
                Asset assetEjb    = factory.getAssetAPI();
                CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                IdVector storeIds = new IdVector();
                storeIds.add(new Integer(appUser.getUserStore().getStoreId()));

                if(detailForm.getAllWarrantyProvidersMap()!=null){
                    try{
                        int warProviderId = Integer.parseInt(detailForm.getWarrantyProviderId());
                        pForm.setFilterWarrantyProvider((BusEntityData) detailForm.getAllWarrantyProvidersMap().get(new Integer(warProviderId)));
                    }catch(NumberFormatException e){
                        log.info("init => WARNING:"+e.getMessage());
                    }
                }

                AssetDataVector assetCategories=getAssetCategories(assetEjb,storeIds);
                session.setAttribute("Warranty.asset.category.vector",assetCategories);

                session.setAttribute(STORE_WARRANTY_CONFIG_FORM,pForm);

            }   else  {
                ae.add("error",new ActionError("error.simpleGenericError","Can't build config info."));
            }
        } catch (Exception e) {
            ae.add("error",new ActionError("error.simpleGenericError",e.getMessage()));
        }

        return ae;
    }

    private static AssetDataVector getAssetCategories(Asset assetEjb, IdVector storeIds) throws Exception {
        AssetDataVector assetCategories = new AssetDataVector();
        AssetSearchCriteria criteria  = new AssetSearchCriteria();

        criteria.setStoreIds(storeIds);
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
        assetCategories = assetEjb.getAssetDataByCriteria(criteria);
        return assetCategories;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreWarrantyConfigForm form) {

        ActionErrors ae       = new ActionErrors();
        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if(form==null) {
            ae.add("error",new ActionError("error.simpleGenericError","Form not initialized"));
            return ae;
        }

        if(form.getWarrantyData()==null){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not initialized"));
            return ae;
        }

        if(appUser==null){
            ae.add("error",new ActionError("error.simpleGenericError","No user info"));
            return ae;
        }

        StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
        if(detailForm==null){
            ae.add("error",new ActionError("error.simpleGenericError","Detail form not found"));
            return ae;
        }

        if(!form.getWarrantyData().equals(detailForm.getWarrantyData())){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not synchronized"));
            return ae;
        }

        return ae;
    }

    public static ActionErrors getWarrantyConfiguration(HttpServletRequest request, StoreWarrantyConfigForm form) {
        ActionErrors ae;
        HttpSession session = request.getSession();

        int assetCatId=-1;
        String assetCatIdStr = request.getParameter("assetCategoryId");
        if(!Utility.isSet(assetCatIdStr)){
            try {
                assetCatId=Integer.parseInt(form.getActiveAssetCategoryId());
            } catch (NumberFormatException e) {}
        }else
        { try {
            assetCatId=Integer.parseInt(assetCatIdStr);
        } catch (NumberFormatException e) {}
        }


        boolean manufFilter;
        String manufFilterStr = request.getParameter("manufFilter");
        if(!Utility.isSet(manufFilterStr)){
            manufFilter=form.getManufFilter();
        }else{
            manufFilter = Utility.isTrue(request.getParameter("manufFilter"),true);
        }

        ae=init(request,form);
        if(ae.size()>0){
            return ae;
        }

        form = (StoreWarrantyConfigForm) session.getAttribute(STORE_WARRANTY_CONFIG_FORM);

        form.setManufFilter(manufFilter);
        form.setActiveAssetCategoryId(String.valueOf(assetCatId));

        if (form.getWarrantyData()!=null && form.getWarrantyData().getWarrantyId()>0) {

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            try {
                if(assetCatId>-1){
                    SelectableObjects selectedObjects = getAssetWarrantyConfigOject(appUser, form, manufFilter, assetCatId);
                    form.setConfigResults(selectedObjects);
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

    public static ActionErrors createNewAssetWarrantyLink(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae=init(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        form = (StoreAssetWarrantyConfigDetailForm) session.getAttribute(STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM);
        try {
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            form.setWarrantyData(detailForm.getWarrantyData());
            form.setEditAssetWarranty(new AssetWarrantyView(AssetWarrantyData.createValue(),AssetView.createValue(),new WarrantyNoteDataVector()));
            session.setAttribute(STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM,form);
        } catch (Exception e) {
            try {
                session.setAttribute(STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM,new StoreAssetWarrantyConfigDetailForm());
                e.printStackTrace();
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) {
        ActionErrors ae=new ActionErrors();
        try{
            HttpSession session=request.getSession();
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            if(detailForm!=null)
            {
                form=new StoreAssetWarrantyConfigDetailForm();
                form.setWarrantyData(detailForm.getWarrantyData());
                session.setAttribute(STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM,form);
                ae.add(checkRequest(request,form));

            }   else  {
                ae.add("error",new ActionError("error.simpleGenericError","Can't build config info."));
            }
        } catch (Exception e) {
            ae.add("error",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return ae;
    }

    private static int getAssetWarrantyIdFromRequest(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) throws Exception {
        String assetWarrantyIdStr = request.getParameter("assetWarrantyId");
        int assetWarrantyIdInt = -1;
        if (assetWarrantyIdStr == null
                && form != null
                && form.getWarrantyData() != null
                && form.getEditAssetWarranty() != null
                && form.getEditAssetWarranty().getAssetWarrantyData() != null) {
            assetWarrantyIdInt = form.getEditAssetWarranty().getAssetWarrantyData().getAssetWarrantyId();
        } else {
            try {
                assetWarrantyIdInt = Integer.parseInt(assetWarrantyIdStr);
            }
            catch (NumberFormatException e) {}
        }
        return assetWarrantyIdInt;
    }

    public static ActionErrors getAssetWarrantyDetails(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm awcForm) {

        ActionErrors ae     = new ActionErrors();
        HttpSession session = request.getSession();
        StoreWarrantyConfigForm form = (StoreWarrantyConfigForm) session.getAttribute(STORE_WARRANTY_CONFIG_FORM);

        try {

            int assetIdInt      = getIdFromRequest(request,"assetId");
            int assetWarrantyId = getAssetWarrantyIdFromRequest(request,awcForm);

            ae.add(init(request,form));
            if(ae.size()>0){
                return ae;
            }

            form = (StoreWarrantyConfigForm) session.getAttribute(STORE_WARRANTY_CONFIG_FORM);
            ae.add(init(request,awcForm));
            if(ae.size()>0){
                return ae;
            }

            awcForm = (StoreAssetWarrantyConfigDetailForm) session.getAttribute(STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM);
            AssetWarrantyView awview =null;

            if(assetWarrantyId>0){
                awview= filterAssetWarantyViewByAssetWarrantyId(assetWarrantyId,form.getAllOnlyConfigs());
            } else if (assetIdInt>0) {
                awview= filterAssetWarantyViewByAssetId(assetIdInt,form.getAllOnlyConfigs());
            }

            if(awview==null){
                throw new Exception("Can't build detail data for assetId="+assetIdInt);
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
        session.setAttribute(STORE_ASSET_WARRANTY_CONFIG_DETAIL_FORM, awcForm);
        session.setAttribute(STORE_WARRANTY_CONFIG_FORM, form);

        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, StoreWarrantyConfigForm form) {

    }


    private static AssetWarrantyView filterAssetWarantyViewByAssetId(int assetIdInt, List awViewVector) {

        if(awViewVector!=null){
            Iterator it =  awViewVector.iterator();
            while(it.hasNext()){
                AssetWarrantyView awView = ((AssetWarrantyView) it.next());
                if(awView.getAssetWarrantyData()!=null){
                    if(assetIdInt==awView.getAssetWarrantyData().getAssetId()){
                        return awView;
                    }
                }
            }
        }
        return null;
    }

    private static AssetWarrantyView filterAssetWarantyViewByAssetWarrantyId(int awIdInt, List awViewVector) {

        if(awViewVector!=null){
            Iterator it =  awViewVector.iterator();
            while(it.hasNext()){
                AssetWarrantyView awView = ((AssetWarrantyView) it.next());
                if(awView.getAssetWarrantyData()!=null){
                    if(awIdInt==awView.getAssetWarrantyData().getAssetWarrantyId()){
                        return awView;
                    }
                }
            }
        }
        return null;
    }
    public static ActionErrors updateAssetWarrantyLink(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) {

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int warrantyId = -1;
        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            Asset assetEjb = factory.getAssetAPI();
            IdVector storeIds = new IdVector();
            storeIds.add(new Integer(appUser.getUserStore().getStoreId()));
            AssetSearchCriteria crit = new AssetSearchCriteria();

            if (Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetNumber())) {
                crit.setAssetNumber(form.getEditAssetWarranty().getAssetView().getAssetNumber());
            }
            if (Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetName())) {
                crit.setAssetName(form.getEditAssetWarranty().getAssetView().getAssetName());
            }

            crit.setStoreIds(storeIds);

            try {
                AssetViewVector assetVV = assetEjb.getAssetViewVector(crit);
                if (assetVV.size() > 1) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Multilple asset."));
                    return ae;
                }
                form.getEditAssetWarranty().setAssetView((AssetView) assetVV.get(0));
            } catch (RemoteException e) {
                ae.add("error", new ActionError("error.simpleGenericError", "Asset can't be found."));
                return ae;
            } catch (DataNotFoundException e) {
                ae.add("error", new ActionError("error.simpleGenericError", "Asset not found."));
                return ae;
            }

            AssetWarrantyData awData = prepareAssetWarrantyData(form);
            awData = warrantyEjb.updateAssetWarranty(awData, appUser.getUser());
            form.getEditAssetWarranty().setAssetWarrantyData(awData);
            refreshFormData(request, form);
            return getAssetWarrantyDetails(request,form);

        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) {
        ActionErrors ae = new ActionErrors();

        if (form.getEditAssetWarranty() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "AssetWarrantyView can't be null"));
            return ae;
        }

        if (form.getEditAssetWarranty().getAssetView() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "AssetView can't be null"));
            return ae;
        }

        if (form.getEditAssetWarranty().getAssetWarrantyData() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "AssetWarrantyData can't be null"));
            return ae;
        }

        if (!Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetNumber()) &&
                !Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetName())) {

            if (Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetNumber()) &&
                    !Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetName())) {
                ae.add("AssetName", new ActionError("variable.empty.error", "Asset Name"));
            }

            if (!Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetNumber()) &&
                    Utility.isSet(form.getEditAssetWarranty().getAssetView().getAssetName())) {
                ae.add("AssetNum", new ActionError("variable.empty.error", "Asset Number"));
            }
        }

        if (form.getWarrantyData().getWarrantyId() <= 0) {
            ae.add("AssetNum", new ActionError("variable.empty.error", "WarrantyId must be greater than 0"));
        }

        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) {

        ActionErrors ae       = new ActionErrors();
        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if(form==null) {
            ae.add("error",new ActionError("error.simpleGenericError","Form not initialized"));
            return ae;
        }

        if(form.getWarrantyData()==null){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not initialized"));
            return ae;
        }

        if(appUser==null){
            ae.add("error",new ActionError("error.simpleGenericError","No user info"));
            return ae;
        }

        StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
        if(detailForm==null){
            ae.add("error",new ActionError("error.simpleGenericError","Detail form not found"));
            return ae;
        }

        if(!form.getWarrantyData().equals(detailForm.getWarrantyData())){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not synchronized"));
            return ae;
        }

        return ae;
    }

    private static ActionErrors refreshFormData(HttpServletRequest request,ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        StoreWarrantyDetailForm detForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
        ActionErrors ae = getDetail(request, detForm);
        if(ae.size()>0){
            throw new Exception("Detail data can't be refreshed");
        }
        return ae;
    }

    private static AssetWarrantyData prepareAssetWarrantyData(StoreAssetWarrantyConfigDetailForm form) {
        AssetWarrantyData awData =form.getEditAssetWarranty().getAssetWarrantyData();
        awData.setAssetId(form.getEditAssetWarranty().getAssetView().getAssetId());
        awData.setModDate(form.getEditAssetWarranty().getAssetWarrantyData().getModDate());
        awData.setAddDate(form.getEditAssetWarranty().getAssetWarrantyData().getAddDate());
        awData.setAddBy(form.getEditAssetWarranty().getAssetWarrantyData().getAddBy());
        awData.setAddBy(form.getEditAssetWarranty().getAssetWarrantyData().getModBy());         
        awData.setWarrantyId(form.getWarrantyData().getWarrantyId());
        return awData;
    }

    public static ActionErrors removeAssetWarrantyLink(HttpServletRequest request, StoreAssetWarrantyConfigDetailForm form) {

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            AssetWarrantyData awData = prepareAssetWarrantyData(form);

            warrantyEjb.removeAssetWarranty(awData.getAssetWarrantyId());

            form.setEditAssetWarranty(new AssetWarrantyView(AssetWarrantyData.createValue(),AssetView.createValue(),new WarrantyNoteDataVector()));
            refreshFormData(request, form);
            return createNewAssetWarrantyLink(request,form);
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {
        ActionErrors ae=new ActionErrors();
        try{
            HttpSession session=request.getSession();
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            if(detailForm!=null)
            {
                form=new  StoreWarrantyNoteMgrForm();
                form.setWarrantyData(detailForm.getWarrantyData());
                form.setWarrantyNoteTypeCd("");
                session.setAttribute(STORE_WARRANTY_NOTE_MGR_FORM,form);
                ae.add(checkRequest(request,form));

            }   else  {
                ae.add("error",new ActionError("error.simpleGenericError","Can't build config info."));
            }
        } catch (Exception e) {
            ae.add("error",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {

        ActionErrors ae       = new ActionErrors();
        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if(form==null) {
            ae.add("error",new ActionError("error.simpleGenericError","Form not initialized"));
            return ae;
        }

        if(form.getWarrantyData()==null){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not initialized"));
            return ae;
        }

        if(appUser==null){
            ae.add("error",new ActionError("error.simpleGenericError","No user info"));
            return ae;
        }

        StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
        if(detailForm==null){
            ae.add("error",new ActionError("error.simpleGenericError","Detail form not found"));
            return ae;
        }

        if(!form.getWarrantyData().equals(detailForm.getWarrantyData())){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not synchronized"));
            return ae;
        }

        return ae;
    }

    public static ActionErrors noteSearch(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {
        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        try {
            APIAccess factory    = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            int warrantyId=form.getWarrantyData().getWarrantyId();

            String noteName=null;
            String noteType=null;
            //add data to criteria

            if (Utility.isSet(form.getSearchField())) {
                noteName= form.getSearchField();
            }

            if(Utility.isSet(form.getWarrantyNoteTypeCd())){
                noteType=form.getWarrantyNoteTypeCd();
            }

            ///WarrantyNoteDataVector result = warrantyEjb.get(0,warrantyId,0,noteName,null,noteType);

            //form.setSearchResult(result);
            session.setAttribute(STORE_WARRANTY_NOTE_MGR_FORM,form);

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

    public static ActionErrors getNoteDetail(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {
        ActionErrors ae     = new ActionErrors();
        HttpSession session = request.getSession();
        try {
            int assetWarrantyIdInt = getIdFromRequest(request,"assetWarrantyId");
            int warrantyId         = getIdFromRequest(request,"warrantyId");
            int warrantyNoteId     = getWarrantyNoteIdFromRequest(request,form);
            ae.add(init(request, form));
            if(ae.size()>0){
                return ae;
            }

            APIAccess factory    = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            form = (StoreWarrantyNoteMgrForm) session.getAttribute(STORE_WARRANTY_NOTE_MGR_FORM);

            WarrantyNoteData note = warrantyEjb.getWarrantyNote(warrantyNoteId);

            form.setNoteDetail(note);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                resetSessionAttributes(request, form);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        session.setAttribute(STORE_WARRANTY_NOTE_MGR_FORM, form);
        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {}

    private static int getIdFromRequest(HttpServletRequest request,String idName) {
        String idStr = request.getParameter(idName);
        int idInt = -1;
        if (idStr != null) {
            try {
                idInt = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                log.info("getIdFromRequest => exc:"+e.getMessage());
            }
        }
        return idInt;
    }

    public static ActionErrors updateWarrantyNote(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            WarrantyNoteData note = form.getNoteDetail();
            note=warrantyEjb.updateWarrantyNote(note,appUser.getUser());
            refreshFormData(request, form);
            form.setNoteDetail(note);
            return getNoteDetail(request,form);

        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {

        ActionErrors ae = new ActionErrors();

        if (form.getNoteDetail() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "Note can't be null"));
            return ae;
        }

        if (!Utility.isSet(form.getNoteDetail().getNote())) {
            ae.add("Note", new ActionError("variable.empty.error", "Note"));
        }

        if (!Utility.isSet(form.getNoteDetail().getShortDesc())) {
            ae.add("ShortDesc", new ActionError("variable.empty.error", "Short Desc"));
        }

        if (!Utility.isSet(form.getNoteDetail().getTypeCd())) {
            ae.add("TypeCode", new ActionError("variable.empty.error", "Type Code"));
        }

        if (form.getWarrantyData().getWarrantyId() <= 0) {
            ae.add("WarrantyData", new ActionError("variable.empty.error", "WarrantyId must be greater than 0"));
        }

        if (form.getNoteDetail().getWarrantyId() <= 0) {
            ae.add("NoteData", new ActionError("variable.empty.error", "WarrantyId must be greater than 0"));
        }

        return ae;
    }


    public static ActionErrors removeWarrantyNote(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            int warrantyNoteId=getWarrantyNoteIdFromRequest(request,form);
            warrantyEjb.removeWarrantyNote(warrantyNoteId);
            form.setNoteDetail(WarrantyNoteData.createValue());
            refreshFormData(request, form);
            init(request,form);
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors createNewWarrantyNote(HttpServletRequest request, StoreWarrantyNoteMgrForm form) {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        form = (StoreWarrantyNoteMgrForm) session.getAttribute(STORE_WARRANTY_NOTE_MGR_FORM);
        try {
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);

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
            } else {
                throw new Exception("Warranty Note can't be created");
            }
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        session.setAttribute(STORE_WARRANTY_NOTE_MGR_FORM, form);
        return ae;
    }

    private static int getWarrantyNoteIdFromRequest(HttpServletRequest request, StoreWarrantyNoteMgrForm form) throws Exception {
        String warrantyNoteIdStr = request.getParameter("warrantyNoteId");
        int warrantyNoteIdInt = -1;
        if (form.getWarrantyData() != null
                && form.getNoteDetail()!=null) {
            warrantyNoteIdInt = form.getNoteDetail().getWarrantyNoteId();
        } else {
            try {
                warrantyNoteIdInt = Integer.parseInt(warrantyNoteIdStr);
            } catch (NumberFormatException e) {
            }
        }
        return warrantyNoteIdInt;
    }

    public static ActionErrors getContentDetail(HttpServletRequest request, StoreWarrantyContentMgrForm form) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        try {
            int warrantyContentIdInt = getWarrantyContentIdFromRequest(request,form);

            ae.add(init(request, form));
            if (ae.size() > 0) {
                return ae;
            }

            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            form = (StoreWarrantyContentMgrForm) session.getAttribute(STORE_WARRANTY_CONTENT_MGR_FORM);

            WarrantyContentView content = findWarrantyContent(detailForm.getContents(), warrantyContentIdInt);

            if (content == null ) {
                throw new Exception("Can't find content.WarrantyContentId:" + warrantyContentIdInt);
            }
            if(content.getContent()==null||content.getWarrantyContentData()==null){
                throw new Exception("Not correct data.WarrantyContentId:" + warrantyContentIdInt);
            }

            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantyContentDetailView contentDetails = warrantyEjb.getWarrantyContentDetails(warrantyContentIdInt);

            uploadContentDetailData(contentDetails,form);

        } catch (Exception e) {
            try {
                e.printStackTrace();
                resetSessionAttributes(request, form);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        session.setAttribute(STORE_WARRANTY_CONTENT_MGR_FORM, form);
        return ae;
    }

    private static int getWarrantyContentIdFromRequest(HttpServletRequest request, StoreWarrantyContentMgrForm form) {
        int warrantyContentId = -1;
        if (form != null && form.getWarrantyContentId() > 0) {
            warrantyContentId = form.getWarrantyContentId();
        } else {
            warrantyContentId = getIdFromRequest(request, "warrantyContentId");
        }
        return warrantyContentId;
    }


    private static void resetSessionAttributes(HttpServletRequest request, StoreWarrantyContentMgrForm form) { }

    private static void uploadContentDetailData(WarrantyContentDetailView content, StoreWarrantyContentMgrForm form) {

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
                form.setEffDate(sdf.format(content.getContent().getEffDate()));
            } catch (Exception e) {
                form.setEffDate(content.getContent().getEffDate().toString());
            }
        } else {
            form.setEffDate("");
        }

        if (content.getContent().getExpDate() != null) {
            try {
                form.setExpDate(sdf.format(content.getContent().getExpDate()));
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

    public static ActionErrors init(HttpServletRequest request, StoreWarrantyContentMgrForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            if (detailForm != null) {
                form = new StoreWarrantyContentMgrForm();
                form.setWarrantyData(detailForm.getWarrantyData());
                ae.add(checkRequest(request, form));
                if(ae.size()>0){
                    return ae;
                }
                session.setAttribute(STORE_WARRANTY_CONTENT_MGR_FORM, form);
            }
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreWarrantyContentMgrForm form) {

        ActionErrors ae       = new ActionErrors();
        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if(form==null) {
            ae.add("error",new ActionError("error.simpleGenericError","Form not initialized"));
            return ae;
        }

        if(form.getWarrantyData()==null){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not initialized"));
            return ae;
        }

        if(appUser==null){
            ae.add("error",new ActionError("error.simpleGenericError","No user info"));
            return ae;
        }

        StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
        if(detailForm==null){
            ae.add("error",new ActionError("error.simpleGenericError","Detail form not found"));
            return ae;
        }

        if(!form.getWarrantyData().equals(detailForm.getWarrantyData())){
            ae.add("error",new ActionError("error.simpleGenericError","Warranty data not synchronized"));
            return ae;
        }

        return ae;
    }

    public static ActionErrors findContent(HttpServletRequest request, StoreWarrantyContentMgrForm form){

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        try {

            ae.add(init(request, form));
            if (ae.size() > 0) {
                return ae;
            }

            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);
            form.setFindResult(detailForm.getContents());
        } catch (Exception e) {
            try {
                e.printStackTrace();
                resetSessionAttributes(request, form);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        session.setAttribute(STORE_WARRANTY_CONTENT_MGR_FORM, form);
        return ae;
    }

    private static WarrantyContentView findWarrantyContent(WarrantyContentViewVector contents, int warrantyContentIdInt) {
        if (contents != null) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                WarrantyContentView content = ((WarrantyContentView) it.next());
                if (content != null &&
                        content.getWarrantyContentData()!=null &&
                        content.getWarrantyContentData().getWarrantyContentId()==warrantyContentIdInt) {
                    return content;
                }
            }
        }
        return null;
    }

    public static ActionErrors updateContentData(HttpServletRequest request, StoreWarrantyContentMgrForm form) {
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            WarrantyContentDetailView warrantyContent = WarrantyContentDetailView.createValue();
            warrantyContent = loadContentDetailData(warrantyContent, form);
            warrantyContent = warrantyEjb.updateWarrantyContent(warrantyContent, appUser.getUser());
            refreshFormData(request, form);
            uploadContentDetailData(warrantyContent, form);
            return getContentDetail(request, form);

        } catch (Exception e) {
            try {
                error(e.getMessage(),e);
                resetSessionAttributes(request, form);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    private static WarrantyContentDetailView loadContentDetailData(WarrantyContentDetailView warrantyContent, StoreWarrantyContentMgrForm form) throws IOException {

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
                content.setExpDate(sdf.parse(form.getExpDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                content.setExpDate(null);
            }
        } else {
            content.setExpDate(null);
        }

        if (Utility.isSet(form.getEffDate())) {
            try {
                content.setEffDate(sdf.parse(form.getEffDate()));
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

    private static ActionErrors checkFormAttribute(HttpServletRequest request, StoreWarrantyContentMgrForm form) {

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
                sdf.parse(form.getExpDate());
            } catch (ParseException e) {
                e.printStackTrace();
                ae.add("ExpDate", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        if (Utility.isSet(form.getEffDate())) {
            try {
                sdf.parse(form.getEffDate());
            } catch (ParseException e) {
                e.printStackTrace();
                ae.add("EffDate", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    public static ActionErrors creteNewContent(HttpServletRequest request, StoreWarrantyContentMgrForm form) {

        ActionErrors ae;
        HttpSession session = request.getSession();
        ae = init(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        form = (StoreWarrantyContentMgrForm) session.getAttribute(STORE_WARRANTY_CONTENT_MGR_FORM);
        try {
            StoreWarrantyDetailForm detailForm = (StoreWarrantyDetailForm) session.getAttribute(STORE_WARRANTY_DETAIL_FORM);

            form.setWarrantyData(detailForm.getWarrantyData());
            WarrantyContentDetailView content = new WarrantyContentDetailView(ContentDetailView.createValue(), WarrantyContentData.createValue());
            content.getWarrantyContentData().setWarrantyId(form.getWarrantyData().getWarrantyId());

            uploadContentDetailData(content, form);

        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        session.setAttribute(STORE_WARRANTY_CONTENT_MGR_FORM, form);
        return ae;
    }

    public static ActionErrors readDocument(HttpServletRequest request, HttpServletResponse response, StoreWarrantyContentMgrForm form) throws Exception {

        ActionErrors ae = getContentDetail(request,form);
        if(ae.size()>0) {
            return ae;
        }

        form = (StoreWarrantyContentMgrForm) request.getSession().getAttribute(STORE_WARRANTY_CONTENT_MGR_FORM);

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
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e){

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }


    public static ActionErrors updateAssetWarrantyConfig(HttpServletRequest request, StoreWarrantyConfigForm pForm) {

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();
            if (pForm.getConfigResults() != null) {
                List newlySelected = pForm.getConfigResults().getNewlySelected();
                List deselected = pForm.getConfigResults().getDeselected();
                IdVector newlyIds = getAssetIds(newlySelected);
                IdVector deselectedIds = getAssetWarrantyIds(deselected, pForm.getAllOnlyConfigs());
                int warrantyId = pForm.getWarrantyData().getWarrantyId();
                if ((deselectedIds != null && !deselectedIds.isEmpty()) || (newlyIds != null && !newlyIds.isEmpty())) {
                   // warrantyEjb.addAndRemoveAssetWarrantyLinks(warrantyId, newlyIds, deselectedIds, appUser.getUser());
                    refreshFormData(request, pForm);
                    ae = getWarrantyConfiguration(request, pForm);
                }
            }
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
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

    public static ActionErrors removeWarrantyContent(HttpServletRequest request, StoreWarrantyContentMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Warranty warrantyEjb = factory.getWarrantyAPI();

            int wcId = pForm.getWarrantyContentId();
            int cId   = pForm.getContentId();
            boolean removeFlag = warrantyEjb.removeWarrantyContent(wcId,cId);
            if(removeFlag){
                refreshFormData(request,pForm);
            }

            return init(request, pForm);

        } catch (Exception e) {
            error(e.getMessage(), e);
            resetSessionAttributes(request,pForm);
            throw new Exception(e.getMessage());
        }
    }


    private static IdVector getAssetIds(List assetCollection) {
        IdVector ids = new IdVector();

        if(assetCollection!=null){
            Iterator it=assetCollection.iterator();
            while(it.hasNext()){
                AssetView asset = (AssetView)it.next();
                ids.add(new Integer(asset.getAssetId()));
            }
        }
        return  ids;
    }

    public static SelectableObjects getAssetWarrantyConfigOject(CleanwiseUser appUser, StoreWarrantyConfigForm form, boolean manufFilter, int assetCatId) throws Exception {

        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();

        AssetSearchCriteria criteria = new AssetSearchCriteria();
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(appUser.getUserStore().getStoreId()));


        criteria.setStoreIds(storeIds);
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);

        if (assetCatId>0) {
            criteria.setParentId(assetCatId);
        }

        if (form.getFilterWarrantyProvider() != null && manufFilter) {
            criteria.setManufName(Utility.strNN(form.getFilterWarrantyProvider().getShortDesc()));
        }

        AssetViewVector configDV = null;
        AssetViewVector all = null;

        try {
            all = assetEjb.getAssetViewVector(criteria);
        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            all = new AssetViewVector();
        }

        configDV = filterAsset(form.getAllOnlyConfigs(), criteria.getManufName(), criteria.getParentId());

        return new SelectableObjects(configDV, all, ASSET_ID_COMPARE);

    }
}
