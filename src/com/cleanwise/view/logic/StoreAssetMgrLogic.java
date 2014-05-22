package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.AssetDataAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AssetAssocData;
import com.cleanwise.service.api.value.AssetAssocDataVector;
import com.cleanwise.service.api.value.AssetContentData;
import com.cleanwise.service.api.value.AssetContentDetailView;
import com.cleanwise.service.api.value.AssetContentView;
import com.cleanwise.service.api.value.AssetContentViewVector;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.AssetDetailData;
import com.cleanwise.service.api.value.AssetDetailView;
import com.cleanwise.service.api.value.AssetDetailViewVector;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.AssetView;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.ContentDetailView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.WarrantyDataVector;
import com.cleanwise.service.api.value.WorkOrderDataVector;
import com.cleanwise.view.forms.StoreAssetConfigForm;
import com.cleanwise.view.forms.StoreAssetContentMgrForm;
import com.cleanwise.view.forms.StoreAssetDetailForm;
import com.cleanwise.view.forms.StoreAssetMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.StringUtils;
import org.apache.log4j.*;

/**
 * Title:        StoreAssetMgrLogic
 * Description:  logic manager for the asset processing.
 * Purpose:      executes operation for the asset processing
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         9:41:37
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreAssetMgrLogic {
    private static final Logger log = Logger.getLogger(StoreAssetMgrLogic.class);

    static final Comparator BUS_ENTITY_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((BusEntityData) o1).getBusEntityId();
            int id2 = ((BusEntityData) o2).getBusEntityId();
            return id2 - id1;
        }
    };

    private static final String STORE_ASSET_DETAIL_FORM = "STORE_ASSET_DETAIL_FORM";
    private static final String STORE_ASSET_CONFIG_FORM = "STORE_ASSET_CONFIG_FORM";
    private static final String STORE_ASSET_MGR_FORM    = "STORE_ASSET_MGR_FORM";
    private static final String STORE_ASSET_CONTENT_MGR_FORM = "STORE_ASSET_CONTENT_MGR_FORM";

    public static final String ASSET_IMG_ROOT_DIR = "assetimg";

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static ActionErrors init(HttpServletRequest request, StoreAssetMgrForm pForm) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        pForm = new StoreAssetMgrForm();
        session.setAttribute(STORE_ASSET_MGR_FORM, pForm);
        ae.add(checkRequest(request, pForm));
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, StoreAssetConfigForm form) {

        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            StoreAssetDetailForm detailForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);
            if (detailForm != null) {
                StoreAssetConfigForm pForm = new StoreAssetConfigForm();

                pForm.setAssetId(detailForm.getAssetData().getAssetId());
                pForm.setAssocType(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                pForm.setAssetType(detailForm.getAssetData().getAssetTypeCd());
                pForm.setAssetDetailView(AssetDetailView.createValue());

                ArrayList arrayAssocType = new ArrayList();
                arrayAssocType.add(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);

                session.setAttribute("Asset.assoc.type.vector", arrayAssocType);
                session.setAttribute(STORE_ASSET_CONFIG_FORM, pForm);

                ae.add(checkRequest(request, pForm));

            } else {
                ae.add("error", new ActionError("error.simpleGenericError", "Can't build config info."));
            }
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors getAssetConfiguration(HttpServletRequest request, StoreAssetConfigForm pForm) {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0){
            return ae;
        }

        if (pForm.getAssetId() > 0) {

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            try {
                APIAccess factory = new APIAccess();
                Asset assetEJB = factory.getAssetAPI();

                BusEntitySearchCriteria busCrit = new BusEntitySearchCriteria();
                busCrit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
                busCrit.setSearchId(pForm.getSiteSearchField());

                AssetSearchCriteria criteria = new AssetSearchCriteria();
                criteria.setAssetId(pForm.getAssetId());
                criteria.setAssocTypeCd(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                criteria.setStoreIds(appUser.getUserStoreAsIdVector());
                if (Utility.isSet(pForm.getSiteSearchField())) {
                    criteria.setSiteIds(Utility.parseIdStringToVector(pForm.getSiteSearchField(), ","));
                }

                AssetDetailViewVector detailVV = null;
                try {
                    detailVV = assetEJB.getAssetDetailViewVector(criteria);
                } catch (DataNotFoundException e) {
                    log.info(e.getMessage());

                }
                BusEntityDataVector siteAssocConfigOnly = new BusEntityDataVector();

                if (detailVV != null && detailVV.size() > 0) {
                    if (detailVV.size() > 1) {
                        ae.add("error", new ActionError("erro.simpleGenericError", "Multiple Asset.AssetId : " + pForm.getAssetId()));
                        return ae;
                    }
                    pForm.setAssetDetailView(((AssetDetailView) detailVV.get(0)));
                    //  busEntityConfigOnly = ((AssetDetailView) config.get(0)).getAssociations();
                    siteAssocConfigOnly = ((AssetDetailView) (detailVV.get(0))).getAssetSiteAssoc();
                }
                BusEntityDataVector all = null;
                if (!pForm.getShowConfiguredOnlyFl()) {
                    all = assetEJB.getBusEntityByCriteria(busCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                } else {
                    all = (BusEntityDataVector) siteAssocConfigOnly.clone();
                }

                try {
                    int locSiteId = assetEJB.getAssetLocationSiteId(pForm.getAssetId());
                    pForm.setAssetLocation(locSiteId);
                }
                catch (DataNotFoundException e) {
                    pForm.setAssetLocation(-1);
                }

                SelectableObjects selectedObjects = new SelectableObjects(siteAssocConfigOnly, all, BUS_ENTITY_ID_COMPARE);
                pForm.setConfigResults(selectedObjects);
            } catch (Exception e) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));

            }
        } else {
            ae.add("AssetId", new ActionError("variable.empty.error", "AssetId"));
        }
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, StoreAssetDetailForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreAssetDetailForm pForm = new StoreAssetDetailForm();

        if (form != null) {
            pForm.setAssetId(form.getAssetId());
        }
        try {
            APIAccess factory = new APIAccess();

            ListService listServiceEJB = factory.getListServiceAPI();
            Manufacturer manufEJB      = factory.getManufacturerAPI();
            Asset assetEJB             = factory.getAssetAPI();

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("ASSET_STATUS_CD");
            session.setAttribute("Asset.status.vector", statusCds);

            RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("ASSET_TYPE_CD");
            session.setAttribute("Asset.type.vector", typeCds);

            try {
                pForm.setAssetImageDir(getAssetImageDir());
            } catch (Exception e) {
               log.info("init => WARNING:"+e.getMessage());
            }

            AssetDetailData assetDetail = AssetDetailData.createValue();

            uploadDetailData(assetDetail,new WarrantyDataVector(),new WorkOrderDataVector(),new AssetContentViewVector(),pForm);

            if (appUser != null) {
                setStoreManufPairs(manufEJB, pForm, appUser.getUserStore().getStoreId());
                setParentIdNamePairs(assetEJB, pForm, appUser.getUserStore().getStoreId());
            }
            ae.add(checkRequest(request, pForm));
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }

        session.setAttribute(STORE_ASSET_DETAIL_FORM, pForm);
        return ae;
    }

    private static File getAssetImageDir() {
        String dir = System.getProperty("webdeploy") + "/en/" + ASSET_IMG_ROOT_DIR + "/";
        File file = new File(dir);
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }

    public static ActionErrors search(HttpServletRequest request, ActionForm form) {

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0){
            return ae;
        }

        HttpSession session = request.getSession();
        StoreAssetMgrForm pForm = (StoreAssetMgrForm) form;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        try {
            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();
            AssetSearchCriteria criteria = new AssetSearchCriteria();
            if (RefCodeNames.SEARCH_TYPE_CD.ID.equals(pForm.getSearchType())) {
                int id = 0;
                try {
                    id = Integer.parseInt(pForm.getSearchField());
                } catch (NumberFormatException e) {
                    ae.add("Asset", new ActionError("error.invalidNumber", pForm.getSearchField()));
                    return ae;
                }
                criteria.setAssetId(id);
            } else if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(pForm.getSearchType())) {
                String searchFld = Utility.isSet(pForm.getSearchField()) ? pForm.getSearchField() : "%";
                criteria.setAssetName(searchFld);
                criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.BEGINS);
            } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(pForm.getSearchType())) {
                String searchFld = Utility.isSet(pForm.getSearchField()) ? pForm.getSearchField() : "%";
                criteria.setAssetName(searchFld);
                criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            }

            criteria.setShowInactive(pForm.getShowInactiveFl());
            criteria.setUserId(appUser.getUser().getUserId());
            criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());
            criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);

            if (Utility.isSet(pForm.getSiteSearchField())) {
                criteria.setSiteIds(Utility.parseIdStringToVector(pForm.getSiteSearchField(), ","));
            }
            if (Utility.isSet(pForm.getStoreSearchField())) {
                criteria.setStoreIds(Utility.parseIdStringToVector(pForm.getStoreSearchField(), ","));
            } else {
                criteria.setStoreIds(appUser.getUserStoreAsIdVector());
            }
            AssetViewVector assetVV = assetEJB.getAssetViewVector(criteria);
            pForm.setMainSearchResult(assetVV);
        } catch (Exception e) {
            try {
                resetSessionAttributes(request, pForm);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (form == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "Form not initialized"));
            return ae;
        }
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }
        if (form instanceof StoreAssetMgrForm) ae = checkRequest(request, (StoreAssetMgrForm) form);
        else if (form instanceof StoreAssetDetailForm) ae = checkRequest(request, (StoreAssetDetailForm) form);
        else if (form instanceof StoreAssetConfigForm) ae = checkRequest(request, (StoreAssetConfigForm) form);
        else if (form instanceof StoreAssetContentMgrForm) ae = checkRequest(request, (StoreAssetContentMgrForm) form);

        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreAssetMgrForm form) {
        ActionErrors ae = new ActionErrors();
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreAssetContentMgrForm form) {
        ActionErrors ae = new ActionErrors();
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreAssetDetailForm form) {
        ActionErrors ae = new ActionErrors();
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, StoreAssetConfigForm form) {
        ActionErrors ae = new ActionErrors();
        if (!RefCodeNames.ASSET_TYPE_CD.ASSET.equals(form.getAssetType())) {
            ae.add("error", new ActionError("error.simpleGenericError", "Method is not support this asset type"));
            resetSessionAttributes(request, form);
        }
        return ae;
    }

    private static ActionErrors setStoreManufPairs(Manufacturer manufEjb, StoreAssetDetailForm pForm, int storeId) throws Exception {

        ActionErrors ae = new ActionErrors();
        PairViewVector pairs = new PairViewVector();
        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
        IdVector storeIdAsV = new IdVector();
        storeIdAsV.add(new Integer(storeId));
        besc.setStoreBusEntityIds(storeIdAsV);

        ManufacturerDataVector manufDV = manufEjb.getManufacturerByCriteria(besc);
        if (manufDV.size() > 1) {
            Object[] manufA = manufDV.toArray();
            for (int ii = 0; ii < manufA.length - 1; ii++) {
                boolean exitFl = true;
                for (int jj = 0; jj < manufA.length - ii - 1; jj++) {
                    ManufacturerData mD1 = (ManufacturerData) manufA[jj];
                    ManufacturerData mD2 = (ManufacturerData) manufA[jj + 1];
                    int comp = mD1.getBusEntity().getShortDesc().compareToIgnoreCase(
                            mD2.
                                    getBusEntity().getShortDesc());
                    if (comp > 0) {
                        manufA[jj] = mD2;
                        manufA[jj + 1] = mD1;
                        exitFl = false;
                    }
                }
                if (exitFl) break;
            }
            for (int ii = 0; ii < manufA.length; ii++) {
                ManufacturerData mD1 = (ManufacturerData) manufA[ii];
                pairs.add(new PairView(new Integer(mD1.getBusEntity().getBusEntityId()),
                        mD1.getBusEntity().getShortDesc()));

            }
        }

        pForm.setManufIdNamePairs(pairs);
        return ae;
    }


    public static ActionErrors setParentIdNamePairs(Asset assetEjb, StoreAssetDetailForm pForm, int storeId) throws Exception {
        ActionErrors ae = new ActionErrors();
        PairViewVector pairs = new PairViewVector();
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(storeId));
        criteria.setStoreIds(storeIds);
        criteria.setOrderBy(new PairView(AssetDataAccess.SHORT_DESC, Boolean.TRUE));
        AssetDataVector parentAssets = null;

        parentAssets = assetEjb.getAssetDataByCriteria(criteria);

        Iterator it = parentAssets.iterator();
        while (it.hasNext()) {
            AssetData assetData = (AssetData) it.next();
            pairs.add(new PairView(new Integer(assetData.getAssetId()), assetData.getShortDesc()));
        }
        pForm.setParentIdNamePairs(pairs);
        return ae;
    }

    /**
     * This method searches asset_id in (HttpServletRequest)request
     * or (StoreAssetDetailForm )pForm and returnes this a  value
     *
     * @param pForm   StoreAssetDetailForm
     * @param request HttpServletRequest
     * @return staged_assetId
     * @throws Exception exception
     */
    public static int getAssetIDfromRequest(HttpServletRequest request, StoreAssetDetailForm pForm) throws Exception {

        String assetIdStr = request.getParameter("assetId");
        int assetIdInt = -1;
        if (assetIdStr == null && pForm.getAssetData() != null) {
            assetIdInt = pForm.getAssetId();
        } else {
            try {
                assetIdInt = Integer.parseInt(assetIdStr);
            }
            catch (NumberFormatException e) {
                throw new Exception(e.getMessage());
            }
        }
        return assetIdInt;
    }

    public static ActionErrors getDetail(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        StoreAssetDetailForm sForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);

        int assetIdInt = getAssetIDfromRequest(request, (StoreAssetDetailForm) form);

        ae.add(init(request, sForm));
        if(ae.size()>0){
            return ae;
        }

        sForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);

        try {

            IdVector storeIds = appUser.getUserStoreAsIdVector();
            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();

            AssetDetailData assetDetail        = assetEJB.getAssetDetailData(assetIdInt, storeIds, null);
            WarrantyDataVector assetWarrAssoc  = assetEJB.getAssetWarrantyAssoc(assetIdInt);
            WorkOrderDataVector workOrderAssoc = assetEJB.getAssetWorkOrderAssoc(assetIdInt);
            AssetContentViewVector contents    = assetEJB.getAssetContents(assetIdInt);

            DisplayListSort.sort(contents, "contentdescription");
            uploadDetailData(assetDetail,assetWarrAssoc,workOrderAssoc,contents,sForm);


        } catch (Exception e) {
            try {
                resetSessionAttributes(request, sForm);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));

            }
        }
        session.setAttribute(STORE_ASSET_DETAIL_FORM, sForm);
        return ae;
    }


    private static ActionErrors resetSessionAttributes(HttpServletRequest request, StoreAssetMgrForm pForm) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
            pForm = new StoreAssetMgrForm();
            session.setAttribute(STORE_ASSET_MGR_FORM, pForm);
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors resetSessionAttributes(HttpServletRequest request, StoreAssetDetailForm pForm) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
            session.setAttribute("Asset.status.vector", new RefCdDataVector());
            pForm.setImageFile(null);
            AssetDetailData assetDetail = AssetDetailData.createValue();
            uploadDetailData(assetDetail,new WarrantyDataVector(),new WorkOrderDataVector(),new AssetContentViewVector(),pForm);
            session.setAttribute(STORE_ASSET_DETAIL_FORM, pForm);

        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static ActionErrors resetSessionAttributes(HttpServletRequest request, StoreAssetConfigForm pForm) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
            session.setAttribute("Asset.assoc.type.vector", new RefCdDataVector());
            session.setAttribute(STORE_ASSET_CONFIG_FORM, new StoreAssetConfigForm());

        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors createNewAsset(HttpServletRequest request, ActionForm pForm) {
        HttpSession session = request.getSession();
        ActionErrors ae = init(request, (StoreAssetDetailForm) null);
        if (ae.size() > 0) return ae;
        StoreAssetDetailForm detailForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);

        ae = checkRequest(request, detailForm);
        if (ae.size() > 0) return ae;

        detailForm.getAssetData().setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);
        return ae;
    }

    public static ActionErrors checkFormAttribute(HttpServletRequest request, StoreAssetDetailForm pForm, Asset assetEjb) throws DataNotFoundException,
                                                                                                                                 RemoteException,
                                                                                                                                 FileNotFoundException,
                                                                                                                                 IOException {

        ActionErrors ae = new ActionErrors();
        AssetData assetData = pForm.getAssetData();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
        if (assetData == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "Check is failed. Some data is null."));
        }

        String dateFormat = ClwI18nUtil.getCountryDateFormat(request);
        SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, ClwI18nUtil.getUserLocale(request));
        Date date1970 = new Date(0);
        Date tmpDate;
        if (dateFormat == null) {
            dateFormat = "MM/dd/yyyy";
        }

        String field = "";
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
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assetName");
            if (field == null) {
                field = "Asset Name";
            }
        } else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetData.getAssetTypeCd())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.masterAssetName");
            if (field == null) {
                field = "Master Asset Name";
            }
        } else if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetData.getAssetTypeCd())) {
            field = ClwI18nUtil.getMessageOrNull(request, "userAssets.text.assetCategoryName");
            if (field == null) {
                field = "Category Name";
            }
        }
        if (!Utility.isSet(assetData.getShortDesc())) {
            ae.add("Asset Name", new ActionError("variable.empty.error", field));
        } else if(assetData.getShortDesc().length() > 255) {
            ae.add("Asset Name", new ActionError("variable.long.error", field));
        }

        if (!RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(assetData.getAssetTypeCd())) {
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
        }

        if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetData.getAssetTypeCd())) {
            if(Utility.isSet(assetData.getSerialNum()) && assetData.getSerialNum().length() > 255){
                field = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.serialnumber");
                if (field == null) {
                    field = "Serial Number";
                }
                ae.add("Serial Number", new ActionError("variable.long.error", field));
            }

            if (Utility.isSet(pForm.getAssetData().getAssetNum())) {
                AssetSearchCriteria criteria = new AssetSearchCriteria();
                criteria.setAssetNumber(pForm.getAssetData().getAssetNum());
				IdVector storeIdV = new IdVector();
				storeIdV.add(storeId);
				criteria.setStoreIds(storeIdV);
                AssetDataVector result = assetEjb.getAssetDataByCriteria(criteria);
                if (pForm.getAssetData().getAssetId() > 0) {
                    if (result.size() > 0 && result.size() == 1) {
                        if (((AssetData) result.get(0)).getAssetId() != pForm.getAssetData().getAssetId()) {
                            ae.add("Asset Number", new ActionError("error.simpleGenericError", "Asset Number must be unique."));
                        }
                    } else if (result.size() > 1) {
                        ae.add("Asset Number", new ActionError("error.simpleGenericError", "Multiple Asset Number."));
                    }
                } else if (result.size() > 0) {
                    ae.add("Asset Number", new ActionError("error.simpleGenericError", "Asset Number must be unique."));
                }
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

    public static ActionErrors saveAsset(HttpServletRequest request, StoreAssetDetailForm pForm) {

        ActionErrors ae = new ActionErrors();

        try {
            ae = checkRequest(request, pForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();

            ae = checkFormAttribute(request, pForm, assetEJB);
            if (ae.size() > 0){
                return ae;
            }

            int assetId = -1;

            AssetDetailData assetDetail = AssetDetailData.createValue();
            assetId = pForm.getAssetData().getAssetId();
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            loadDetailData(assetDetail, pForm);
            /* updates Assoc Data */
            try {
                assetDetail = assetEJB.updateAssetDetailData(assetDetail, appUser.getUser().getUserName());

            } catch (Exception e) {
                return ae;
            }

            if (assetId == 0 && assetDetail.getAssetData().getAssetId() > 0) {
                setAssociationWithStore(assetEJB, assetDetail, appUser.getUserStore().getStoreId(), appUser.getUser().getUserName());
            }

             if(pForm.getImageFile()!=null){
                    setNewAssetImage(assetEJB,assetDetail.getAssetData(),pForm.getImageFile(),pForm.getMainAssetImage(),appUser.getUser());
             }


            pForm.setAssetData(assetDetail.getAssetData());
            pForm.setAssetId(assetDetail.getAssetData().getAssetId());
            return getDetail(request,pForm);
        } catch (Exception e) {
            ae.add(processExceptionOfSave(0, e, null));
        }
        return ae;
    }

    private static AssetContentDetailView setNewAssetImage(Asset assetEJB,
                                                           AssetData asset,
                                                           FormFile imageFile,
                                                           AssetContentDetailView mainAssetImage,
                                                           UserData user) throws Exception {
        if(mainAssetImage==null){
            mainAssetImage= createAssetImage(asset,user);
            if (imageFile != null && Utility.isSet(imageFile.getFileName())) {
                mainAssetImage.getContent().setPath(imageFile.getFileName());
                mainAssetImage.getContent().setData(imageFile.getFileData());
                mainAssetImage.getContent().setContentTypeCd(imageFile.getContentType());

            mainAssetImage = assetEJB.updateAssetContent(mainAssetImage, user);
            }
        } else{
            if (imageFile != null && Utility.isSet(imageFile.getFileName())) {
                mainAssetImage.getContent().setPath(imageFile.getFileName());
                mainAssetImage.getContent().setData(imageFile.getFileData());
                mainAssetImage.getContent().setContentTypeCd(imageFile.getContentType());

            mainAssetImage = assetEJB.updateAssetContent(mainAssetImage, user);
            }
        }
        return mainAssetImage;
    }

    private static void setAssociationWithStore(Asset assetEJB, AssetDetailData assetDetailData, int storeId, String userName) throws RemoteException {

        AssetAssocData assetAssocData = AssetAssocData.createValue();
        assetAssocData.setAssetAssocCd(RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
        assetAssocData.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
        assetAssocData.setAssetId(assetDetailData.getAssetData().getAssetId());
        assetAssocData.setBusEntityId(storeId);
        AssetAssocDataVector assetAssocDV = new AssetAssocDataVector();
        assetAssocDV.add(assetAssocData);
        assetEJB.updateAssetAssocDataVector(assetDetailData.getAssetData().getAssetId(),assetAssocDV, userName);
    }

    private static void loadDetailData(AssetDetailData assetDetail, StoreAssetDetailForm pForm) {

        String manufName = null;
        if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(pForm.getAssetData().getAssetTypeCd())) {

            manufName = getManufNameById(pForm.getAssetData().getManufId(), pForm.getManufIdNamePairs());
            pForm.getAssetData().setManufName(manufName);
            pForm.getAssetData().setManufTypeCd(RefCodeNames.ASSET_MANUF_TYPE_CD.STORE);

            assetDetail.setAssetData(pForm.getAssetData());
            assetDetail.setLongDesc(pForm.getLongDesc());
            assetDetail.setInactiveReason(pForm.getInactiveReason());
            assetDetail.setAcquisitionDate(pForm.getAcquisitionDate());
            assetDetail.setDateInService(pForm.getDateInService());
            assetDetail.setAcquisitionCost(pForm.getAcquisitionCost());
            assetDetail.setDateLastHMR(pForm.getDateLastHMR());
            assetDetail.setLastHMR(pForm.getLastHMR());

        } else if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(pForm.getAssetData().getAssetTypeCd())) {
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

    private static void uploadDetailData(AssetDetailData assetDetail,
                                         WarrantyDataVector assetWarrAssoc,
                                         WorkOrderDataVector assetWorkOrderAssoc,
                                         AssetContentViewVector contents,
                                         StoreAssetDetailForm pForm) throws Exception {

        pForm.setAssetData(assetDetail.getAssetData());

        pForm.setAssetWarrantyAssoc(assetWarrAssoc);
        pForm.setAssetWorkOrderAssoc(assetWorkOrderAssoc);
        pForm.setContents(contents);
        pForm.setLongDesc(assetDetail.getLongDesc());

        AssetContentDetailView assetMainImg = getMainImage(APIAccess.getAPIAccess().getAssetAPI(),contents);
        pForm.setImagePath("");
        pForm.setMainAssetImage(assetMainImg);
        if(assetMainImg!=null&&assetMainImg.getContent()!=null){
            try {
                File imgTempFile=File.createTempFile("Asset",getFileExt(assetMainImg.getContent().getPath()),pForm.getAssetImageDir());
                FileOutputStream fos= new FileOutputStream(imgTempFile);
                fos.write(assetMainImg.getContent().getData());
                fos.flush();
                fos.close();
                //pForm.setImagePath(imgTempFile.getPath());
				pForm.setImagePath(imgTempFile.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pForm.setInactiveReason(assetDetail.getInactiveReason());
        pForm.setAcquisitionDate(assetDetail.getAcquisitionDate());
        pForm.setDateInService(assetDetail.getDateInService());
        pForm.setAcquisitionCost(assetDetail.getAcquisitionCost());
        pForm.setDateLastHMR(assetDetail.getDateLastHMR());
        pForm.setLastHMR(assetDetail.getLastHMR());

        pForm.setAssetId(assetDetail.getAssetData().getAssetId());

    }

    private static AssetContentDetailView getMainImage(Asset assetEjb,AssetContentViewVector contents) throws RemoteException {

        AssetContentViewVector assetImgContents = new AssetContentViewVector();

        if (contents != null) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                AssetContentView contentV = ((AssetContentView) it.next());
                if(RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE.equals(contentV.getAssetContentData().getTypeCd())){
                    assetImgContents.add(contentV);
                }
            }
        }
        if(!assetImgContents.isEmpty()){
            int assetContentId = ((AssetContentView) assetImgContents.get(0)).getAssetContentData().getAssetContentId();
            return assetEjb.getAssetContentDetails(assetContentId);
        } else{
            return null;
        }
    }    

    private static ActionErrors processExceptionOfSave(int assetId, Exception e, String[] param) {
        ActionErrors ae = new ActionErrors();

        try {
            ae.add(StringUtils.getUiErrorMess(e));
        } catch (Exception e1) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    private static String getFileExt(String fileName) {
        String fileExt = null;

        int i = fileName.lastIndexOf(".");
        if (i < 0) {
            fileExt = "";
        } else {
            fileExt = fileName.substring(i);
        }
        return fileExt;
    }

    public static ActionErrors uploadImageFile(HttpServletRequest request, StoreAssetDetailForm pForm) {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        try {
            APIAccess factory = APIAccess.getAPIAccess();
            Asset assetEJB = factory.getAssetAPI();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            AssetContentDetailView newImg = setNewAssetImage(assetEJB, pForm.getAssetData(), pForm.getImageFile(), pForm.getMainAssetImage(), appUser.getUser());
            pForm.setImagePath("");
            pForm.setMainAssetImage(newImg);
            if (newImg != null && newImg.getContent() != null) {
                try {
                    File imgTempFile = File.createTempFile("Asset", getFileExt(newImg.getContent().getPath()),pForm.getAssetImageDir());
                    FileOutputStream fos= new FileOutputStream(imgTempFile);
                    fos.write(newImg.getContent().getData());
                    fos.flush();
                    fos.close();
                    pForm.setImagePath(imgTempFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }


    public static ActionErrors changeAssetType(HttpServletRequest request, StoreAssetDetailForm pForm) {
        ActionErrors ae = new ActionErrors();
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;

        if (RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(pForm.getAssetData().getAssetTypeCd())) {
            String name = pForm.getAssetData().getShortDesc();
            ae.add(init(request, (StoreAssetDetailForm) null));
            pForm = (StoreAssetDetailForm) request.getSession().getAttribute(StoreAssetMgrLogic.STORE_ASSET_DETAIL_FORM);
            ae.add(checkRequest(request, pForm));
            if (ae.size() > 0) return ae;

            pForm.getAssetData().setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
            pForm.getAssetData().setShortDesc(name);
        }
        return ae;
    }

    public static ActionErrors updateAssetConfiguration(HttpServletRequest request, ActionForm pForm) {
        ActionErrors ae = new ActionErrors();
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;
        try {
            StoreAssetConfigForm sForm = (StoreAssetConfigForm) pForm;
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();

            AssetAssocDataVector assetAssoc = assetEJB.getAssetAssociations(sForm.getAssetId(), null);

            Iterator it = assetAssoc.iterator();
            AssetAssocData currentAssoc;
            int newSiteId = sForm.getAssetLocation();
            boolean assocPresent = false;
                    while (it.hasNext()) {
                currentAssoc = (AssetAssocData) it.next();
                if (currentAssoc.getBusEntityId() == newSiteId &&
                    RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE.equals(currentAssoc.getAssetAssocCd()) &&
                    RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE.equals(currentAssoc.getAssetAssocStatusCd())) {
                    assocPresent = true;
                    break;
                } else if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE.equals(currentAssoc.getAssetAssocCd()) &&
                           RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE.equals(currentAssoc.getAssetAssocStatusCd())) {
                    it.remove();
                    }
                }
            if (!assocPresent) {
                AssetAssocData assetSiteAssoc = AssetAssocData.createValue();
                assetSiteAssoc.setBusEntityId(sForm.getAssetLocation());
                assetSiteAssoc.setAssetId(sForm.getAssetId());
                assetSiteAssoc.setAssetAssocCd(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                assetSiteAssoc.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
                assetAssoc.add(assetSiteAssoc);

                assetEJB.updateAssetAssocDataVector(sForm.getAssetId(), assetAssoc, appUser.getUser().getUserName());
            }

            ae.add(getAssetConfiguration(request, sForm));

        } catch (NamingException e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        } catch (APIServiceAccessException e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        } catch (RemoteException e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        }
        return ae;
    }


    private static IdVector getAssetAssocIdsByBusDV(int assetId, AssetAssocDataVector assocDV, List entityDV) {
        IdVector result = new IdVector();
        if (assocDV != null) {

            Iterator entityIter = entityDV.iterator();
            while (entityIter.hasNext()) {
                int entityId = ((BusEntityData) entityIter.next()).getBusEntityId();
                Iterator assocIter = assocDV.iterator();
                while (assocIter.hasNext()) {
                    AssetAssocData assetAssocData = ((AssetAssocData) assocIter.next());
                    if (assetId == assetAssocData.getAssetId() && entityId == assetAssocData.getBusEntityId()) {
                        result.add(new Integer(assetAssocData.getAssetAssocId()));
                    }
                }
            }
        }
        return result;

    }

    public static ActionErrors changeAssetStatus(HttpServletRequest request, StoreAssetDetailForm pForm) {

        ActionErrors ae = new ActionErrors();
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;

        if (RefCodeNames.ASSET_STATUS_CD.ACTIVE.equals(pForm.getAssetData().getStatusCd())) {
            pForm.getInactiveReason().setValue("");
        }
        return ae;
    }


    public static ActionErrors locate(HttpServletRequest request, ActionForm form) {

        ActionErrors ae = new ActionErrors();

        ae = checkRequest(request, form);
        if (ae.size() > 0) return ae;
        HttpSession session = request.getSession();
        StoreAssetMgrForm pForm = (StoreAssetMgrForm) form;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData siteData = null;
        try {
            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();
            Site siteEjb = factory.getSiteAPI();

            AssetSearchCriteria criteria = new AssetSearchCriteria();
            if (RefCodeNames.SEARCH_TYPE_CD.ID.equals(pForm.getSearchType())) {
                int id = 0;
                try {
                    id = Integer.parseInt(pForm.getSearchField());
                } catch (NumberFormatException e) {
                    ae.add("Asset", new ActionError("error.invalidNumber", pForm.getSearchField()));
                    return ae;
                }
                criteria.setAssetId(id);
            } else if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(pForm.getSearchType())) {
                criteria.setAssetName(pForm.getSearchField());
                criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.BEGINS);
            } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(pForm.getSearchType())) {
                criteria.setAssetName(pForm.getSearchField());
                criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
            }
            String siteId = request.getParameter("siteId");
            int siteIdInt = 0;
            try {
                siteIdInt = Integer.parseInt(siteId);
            } catch (NumberFormatException e) {
                ae.add("error", new ActionError("error.simpleGenericError", "Invalid site Id"));
                return ae;
            }

            criteria.setShowInactive(pForm.getShowInactiveFl());
            criteria.setUserId(appUser.getUser().getUserId());
            criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());

            if (siteIdInt > 0) {
                siteData = siteEjb.getSite(siteIdInt, 0, false, SessionTool.getCategoryToCostCenterView(session, siteIdInt));
                IdVector siteIds = new IdVector();
                siteIds.add(new Integer(siteIdInt));
                criteria.setSiteIds(siteIds);

            }
            if (Utility.isSet(pForm.getStoreSearchField())) {
                criteria.setStoreIds(Utility.parseIdStringToVector(pForm.getStoreSearchField(), ","));
            } else {
                criteria.setStoreIds(appUser.getUserStoreAsIdVector());
            }

            AssetViewVector assetVV = assetEJB.getAssetViewVector(criteria);
            if (siteData != null) {
                assetVV = filterAssetByLocation(assetEJB, assetVV, siteData.getSiteAddress());
            }

            pForm.setMainSearchResult(assetVV);
        } catch (Exception e) {
            try {
                resetSessionAttributes(request, pForm);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    private static AssetViewVector filterAssetByLocation(Asset assetEjb, AssetViewVector assetVV, AddressData location) throws Exception {

        AddressData assetLocationData = null;
        AssetViewVector result = new AssetViewVector();

        if (assetVV != null) {
            Iterator it = assetVV.iterator();
            while (it.hasNext()) {
                AssetView assetView = ((AssetView) it.next());
                int assetId = assetView.getAssetId();
              //assetLocationData = assetEjb.getAssetLocation(staged_assetId);
                    if (UserAssetMgrLogic.matchAssetLocation(assetLocationData, location)) {

                        result.add(assetView);

                    }

            }
        }
        return result;
    }


    public static IdVector getOnlyItemIds(ItemDataVector itemDV) {

        IdVector ids = new IdVector();
        if (itemDV != null) {
            Iterator it = itemDV.iterator();
            while (it.hasNext()) {
                ids.add(new Integer(((ItemData) it.next()).getItemId()));
            }
        }
        return ids;
    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in StoreAssetMgrLogic :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }

    public static ActionErrors init(HttpServletRequest request, StoreAssetContentMgrForm pForm) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        pForm = new StoreAssetContentMgrForm();
        session.setAttribute(STORE_ASSET_CONTENT_MGR_FORM, pForm);
        StoreAssetDetailForm detailForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);
        ae.add(checkRequest(request, detailForm));
        ae.add(checkRequest(request, pForm));

        if(ae.size()>0){
            return ae;
        }

        pForm.setAsset(detailForm.getAssetData());
        session.setAttribute(STORE_ASSET_CONTENT_MGR_FORM, pForm);

        return ae;
    }


    public static ActionErrors getContentDetail(HttpServletRequest request, StoreAssetContentMgrForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        try {

            int assetContentIdInt = getAssetContentIdFromRequest(request,pForm);

            ae.add(init(request, pForm));
            if (ae.size() > 0) {
                return ae;
            }

            StoreAssetDetailForm detailForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);
            pForm = (StoreAssetContentMgrForm) session.getAttribute(STORE_ASSET_CONTENT_MGR_FORM);

            AssetContentView content = findAssetContent(detailForm.getContents(), assetContentIdInt);

            if (content == null ) {
                throw new Exception("Can't find content.AssetContentId:" + assetContentIdInt);
            }
            APIAccess factory = new APIAccess();
            Asset assetEjb = factory.getAssetAPI();

            AssetContentDetailView contentDetails = assetEjb.getAssetContentDetails(assetContentIdInt);

            uploadContentDetailData(contentDetails,pForm);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                resetSessionAttributes(request, pForm);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        session.setAttribute(STORE_ASSET_CONTENT_MGR_FORM, pForm);
        return ae;
    }

    private static void resetSessionAttributes(HttpServletRequest request, StoreAssetContentMgrForm pForm) { }

    private static void uploadContentDetailData(AssetContentDetailView content, StoreAssetContentMgrForm form) {

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

    private static int getAssetContentIdFromRequest(HttpServletRequest request, StoreAssetContentMgrForm pForm) throws Exception {

        String assetContentIdStr = request.getParameter("assetContentId");
        int assetContentIdInt = -1;
        if (assetContentIdStr == null
                && pForm.getAsset() != null) {
            assetContentIdInt = pForm.getAssetContentId();
        } else {
            try {
                assetContentIdInt = Integer.parseInt(assetContentIdStr);
            }
            catch (NumberFormatException e) {
                throw new Exception(e.getMessage());
            }
        }
        return assetContentIdInt;
    }

    public static ActionErrors creteNewContent(HttpServletRequest request, StoreAssetContentMgrForm pForm) {
        ActionErrors ae;
        HttpSession session = request.getSession();
        ae = init(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }


        try {
            pForm = (StoreAssetContentMgrForm) session.getAttribute(STORE_ASSET_CONTENT_MGR_FORM);
            StoreAssetDetailForm detailForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);

            pForm.setAsset(detailForm.getAssetData());
            AssetContentDetailView content = new AssetContentDetailView(ContentDetailView.createValue(), AssetContentData.createValue());
            content.getAssetContentData().setAssetId(pForm.getAsset().getAssetId());

            uploadContentDetailData(content, pForm);

        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        session.setAttribute(STORE_ASSET_CONTENT_MGR_FORM, pForm);
        return ae;
    }

    public static ActionErrors updateContentData(HttpServletRequest request, StoreAssetContentMgrForm pForm) {
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

        try {
            APIAccess factory = new APIAccess();
            Asset assetEjb = factory.getAssetAPI();

            AssetContentDetailView assetContent = AssetContentDetailView.createValue();
            assetContent = loadContentDetailData(assetContent, pForm);
            assetContent = assetEjb.updateAssetContent(assetContent, appUser.getUser());
            refreshFormData(request, pForm);
            uploadContentDetailData(assetContent, pForm);
            return getContentDetail(request, pForm);

        } catch (Exception e) {
            try {
                error(e.getMessage(),e);
                resetSessionAttributes(request, pForm);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    private static void refreshFormData(HttpServletRequest request, StoreAssetContentMgrForm pForm) throws Exception {
        HttpSession session = request.getSession();
        try {
            StoreAssetDetailForm detForm = (StoreAssetDetailForm) session.getAttribute(STORE_ASSET_DETAIL_FORM);

            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();

            AssetContentViewVector contents    = assetEJB.getAssetContents(detForm.getAssetData().getAssetId());

            detForm.setContents(contents);
            session.setAttribute(STORE_ASSET_DETAIL_FORM,detForm);

        } catch (Exception e) {
            throw new Exception("Detail data can't be refreshed");
        }

    }

    private static AssetContentDetailView loadContentDetailData(AssetContentDetailView assetContent, StoreAssetContentMgrForm form) throws Exception {

        AssetContentData assetContentData = AssetContentData.createValue();
        assetContentData.setAssetId(form.getAssetId());
        assetContentData.setAssetContentId(form.getAssetContentId());
        assetContentData.setUrl(form.getUrl());
        assetContentData.setAddBy(form.getAssetAddBy());
        assetContentData.setModBy(form.getAssetModBy());
        assetContentData.setAddDate(form.getAssetAddDate());
        assetContentData.setModDate(form.getAssetModDate());
        assetContentData.setTypeCd(RefCodeNames.ASSET_CONTENT_TYPE_CD.SPEC);

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

        assetContent = new AssetContentDetailView(content, assetContentData);
        return assetContent;
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

    private static ActionErrors checkFormAttribute(HttpServletRequest request, StoreAssetContentMgrForm form) {
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

    public static ActionErrors readDocument(HttpServletRequest request, HttpServletResponse response, StoreAssetContentMgrForm pForm) throws IOException {
        ActionErrors ae = getContentDetail(request,pForm);
        if(ae.size()>0) {
            return ae;
        }

        pForm = (StoreAssetContentMgrForm) request.getSession().getAttribute(STORE_ASSET_CONTENT_MGR_FORM);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(pForm.getData());
        response.setContentType(pForm.getContentTypeCd());
        response.setContentLength(out.size());
        out.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return ae;
    }


    public static ActionErrors removeAssetContent(HttpServletRequest request, StoreAssetContentMgrForm pForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            Asset assetEjb = factory.getAssetAPI();

            int acId = pForm.getAssetContentId();
            int cId   = pForm.getContentId();
            boolean removeFlag = assetEjb.removeAssetContent(acId,cId);
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
}

