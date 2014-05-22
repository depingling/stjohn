package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.AssetDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.session.Budget;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.api.session.Service;
import com.cleanwise.service.api.session.ServiceProviderType;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.Warranty;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.util.BudgetSpentCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.FiscalCalendarUtility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderBudgetUtil;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.AssetView;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetSpendViewVector;
import com.cleanwise.service.api.value.BudgetSpentAmountView;
import com.cleanwise.service.api.value.BudgetSpentShortView;
import com.cleanwise.service.api.value.BudgetSpentShortViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.ContentDetailView;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalPeriodView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderScheduleDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ServiceProviderData;
import com.cleanwise.service.api.value.ServiceProviderDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.WarrantyData;
import com.cleanwise.service.api.value.WarrantyDataVector;
import com.cleanwise.service.api.value.WoiStatusHistDataVector;
import com.cleanwise.service.api.value.WorkOrderAssetData;
import com.cleanwise.service.api.value.WorkOrderAssetDataVector;
import com.cleanwise.service.api.value.WorkOrderAssetView;
import com.cleanwise.service.api.value.WorkOrderAssetViewVector;
import com.cleanwise.service.api.value.WorkOrderAssocData;
import com.cleanwise.service.api.value.WorkOrderAssocDataVector;
import com.cleanwise.service.api.value.WorkOrderContentData;
import com.cleanwise.service.api.value.WorkOrderContentView;
import com.cleanwise.service.api.value.WorkOrderContentViewVector;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDataVector;
import com.cleanwise.service.api.value.WorkOrderDetailData;
import com.cleanwise.service.api.value.WorkOrderDetailDataVector;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.value.WorkOrderDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderItemData;
import com.cleanwise.service.api.value.WorkOrderItemDetailView;
import com.cleanwise.service.api.value.WorkOrderItemDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderNoteData;
import com.cleanwise.service.api.value.WorkOrderNoteDataVector;
import com.cleanwise.service.api.value.WorkOrderPropertyData;
import com.cleanwise.service.api.value.WorkOrderPropertyDataVector;
import com.cleanwise.service.api.value.WorkOrderSimpleSearchCriteria;
import com.cleanwise.service.api.value.WorkOrderSiteNameView;
import com.cleanwise.service.api.value.WorkOrderSiteNameViewVector;
import com.cleanwise.service.api.value.WorkOrderStatusHistData;
import com.cleanwise.service.api.value.WorkOrderStatusHistDataVector;
import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.forms.UserWorkOrderContentMgrForm;
import com.cleanwise.view.forms.UserWorkOrderDetailMgrForm;
import com.cleanwise.view.forms.UserWorkOrderItemMgrForm;
import com.cleanwise.view.forms.UserWorkOrderMgrForm;
import com.cleanwise.view.forms.UserWorkOrderNoteMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.StringUtils;
import com.cleanwise.view.utils.WorkOrderItemizedServiceStr;
import com.cleanwise.view.utils.WorkOrderItemizedServiceStrVector;
import com.cleanwise.view.utils.validators.BigDecimalValidator;
import com.cleanwise.view.utils.validators.IntegerValidator;
import com.cleanwise.view.utils.validators.Validator;

/**
 * Title:        ServiceProviderMgrLogic
 * Description:  logic manager for the work order processing.
 * Purpose:      execute operation for the work order processing.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date: 10.10.2007
 * Time: 15:27:47
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class ServiceProviderMgrLogic {

    public static String className = "UserWorkOrderMgrLogic";

    private static final Logger log = Logger.getLogger(ServiceProviderMgrLogic.class);

    public static final String USER_WORK_ORDER_MGR_FORM         = "USER_WORK_ORDER_MGR_FORM";
    public static final String USER_WORK_ORDER_DETAIL_MGR_FORM  = "USER_WORK_ORDER_DETAIL_MGR_FORM";
    public static final String USER_WORK_ORDER_ITEM_MGR_FORM    = "USER_WORK_ORDER_ITEM_MGR_FORM";
    public static final String USER_WORK_ORDER_NOTE_MGR_FORM    = "USER_WORK_ORDER_NOTE_MGR_FORM";
    public static final String USER_WORK_ORDER_CONTENT_MGR_FORM = "USER_WORK_ORDER_CONTENT_MGR_FORM";

    private static final String EMPTY = "";

 public static ActionErrors search(HttpServletRequest request, UserWorkOrderMgrForm mgrForm) throws Exception {

        ActionErrors ae;

        String dateFormat = ClwI18nUtil.getCountryDateFormat(request);
        SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, ClwI18nUtil.getUserLocale(request));
        Date date1970 = new Date(0);
        Date tmpDate;
        Date tmpDateTmp;
        if (dateFormat == null) {
            dateFormat = "MM/dd/yyyy";
        }

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        try {
            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkOrderEjb = factory.getWorkOrderAPI();
            User userEjb = factory.getUserAPI();

            WorkOrderSimpleSearchCriteria criteria = new WorkOrderSimpleSearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();

            //add data to criteria

            //IdVector siteIds = new IdVector();
            //if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER)) {
            //    siteIds.add(new Integer(appUser.getSite().getSiteId()));
            ///}

            IdVector siteIds = new IdVector();
            WorkOrderSiteNameViewVector resultAndSiteName = new WorkOrderSiteNameViewVector();
            IdVector spIds = new IdVector();
            BusEntityDataVector spV = userEjb.getBusEntityCollection(appUser.getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
            BusEntityData beD;
            for (int i = 0; i < spV.size(); i++) {
                beD = (BusEntityData)spV.get(i);
                if (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(beD.getBusEntityStatusCd())) {
                    spIds.add(Integer.valueOf(beD.getBusEntityId()));
                }
            }

            if (!spIds.isEmpty()) {
                criteria.setProviderIds(spIds);
                criteria.setStoreId(storeId);
                criteria.setWorkOrderName(mgrForm.getSearchField());
                criteria.setSearchType(mgrForm.getSearchType());

                if (mgrForm.getLocateStoreSiteForm() != null) {
                    SiteViewVector sites = mgrForm.getLocateStoreSiteForm().getSitesToReturn();
                    SiteView site;
                    if (sites != null && !sites.isEmpty()) {
                        for (int i = 0; i < sites.size(); i++) {
                            site = (SiteView)sites.get(i);
                            siteIds.add(Integer.valueOf(site.getId()));
                        }
                    }
                }

                if (!siteIds.isEmpty()) {
                    criteria.setSiteIds(siteIds);
                }

                if (Utility.isSet(mgrForm.getWorkOrderNumber())) {

                    String workOrderNum = mgrForm.getWorkOrderNumber().trim();
                    if (workOrderNum.length() > 0) {
                        criteria.setWorkOrderNum(workOrderNum);
                    } else {
                        ae.add("error", new ActionError("userWorkOrder.errors.invalidWorkOrderNumber", mgrForm.getWorkOrderNumber()));
                    }
                }

                if (Utility.isSet(mgrForm.getPoNumber())) {

                    String poNumber = mgrForm.getPoNumber().trim();
                    if (poNumber.length() > 0) {
                        criteria.setPoNumber(poNumber);
                    } else {
                        ae.add("error", new ActionError("userWorkOrder.errors.invalidPoNumber", mgrForm.getPoNumber()));
                    }
                }

                if (Utility.isSet(mgrForm.getActualBeginDate())) {
                    try {
                        tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getActualBeginDate());
                        if (date1970.after(tmpDate)) {
                            throw new Exception();
                        }
                        mgrForm.setActualBeginDate(sdFormat.format(tmpDate));
                        criteria.setActualStartDate(tmpDate);
                    } catch (Exception e) {
                        String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualBeginDate");
                        if (property == null) {
                            property = "ActualBeginDate";
                        }
                        String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getActualBeginDate()}, true);
                        ae.add("actualBeginDate", new ActionError("error.simpleError", mess));
                    }
                }

                if (Utility.isSet(mgrForm.getActualEndDate())) {
                    try {
                        tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getActualEndDate());
                        if (date1970.after(tmpDate)) {
                            throw new Exception();
                        }
                        mgrForm.setActualEndDate(sdFormat.format(tmpDate));
                        criteria.setActualFinishDate(tmpDate);
                    } catch (Exception e) {
                        String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualEndDate");
                        if (property == null) {
                            property = "ActualEndDate";
                        }
                        String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getActualEndDate()}, true);
                        ae.add("actualEndDate", new ActionError("error.simpleError", mess));
                    }
                }

                if (Utility.isSet(mgrForm.getEstimateBeginDate())) {
                    try {
                        tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getEstimateBeginDate());
                        if (date1970.after(tmpDate)) {
                            throw new Exception();
                        }
                        mgrForm.setEstimateBeginDate(sdFormat.format(tmpDate));
                        criteria.setEstimateStartDate(tmpDate);
                    } catch (Exception e) {
                        String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.estimateBeginDate");
                        if (property == null) {
                            property = "EstimateBeginDate";
                        }
                        String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getEstimateBeginDate()}, true);
                        ae.add("estimateBeginDate", new ActionError("error.simpleError", mess));
                    }
                }

                if (Utility.isSet(mgrForm.getEstimateEndDate())) {
                    try {
                        tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getEstimateEndDate());
                        if (date1970.after(tmpDate)) {
                            throw new Exception();
                        }
                        mgrForm.setEstimateEndDate(sdFormat.format(tmpDate));
                        criteria.setEstimateFinishDate(tmpDate);
                    } catch (Exception e) {
                        String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.estimateEndDate");
                        if (property == null) {
                            property = "EstimateEndDate";
                        }
                        String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getEstimateEndDate()}, true);
                        ae.add("estimateEndDate", new ActionError("error.simpleError", mess));
                    }
                }

                if (Utility.isSet(mgrForm.getActualBeginDate()) && Utility.isSet(mgrForm.getActualEndDate())) {
                    try {
                        tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getActualBeginDate());
                        tmpDateTmp = ClwI18nUtil.parseDateInp(request, mgrForm.getActualEndDate());
                        if (tmpDate.after(tmpDateTmp)) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualBeginDate");
                        if (property == null) {
                            property = "Actual Begin Date";
                        }
                        String property1 = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualEndDate");
                        if (property1 == null) {
                            property1 = "Actual End Date";
                        }

                        String mess = ClwI18nUtil.getMessage(request, "error.date.interval.wrong", new Object[]{property1, property}, true);
                        ae.add("actualDateIntervalWrong", new ActionError("error.simpleError", mess));
                    }
                }

                if (Utility.isSet(mgrForm.getEstimateBeginDate()) && Utility.isSet(mgrForm.getEstimateEndDate())) {
                    try {
                        tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getEstimateBeginDate());
                        tmpDateTmp = ClwI18nUtil.parseDateInp(request, mgrForm.getEstimateEndDate());
                        if (tmpDate.after(tmpDateTmp)) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.estimateBeginDate");
                        if (property == null) {
                            property = "Estimate Begin Date";
                        }
                        String property1 = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.estimateEndDate");
                        if (property1 == null) {
                            property1 = "Estimate End Date";
                        }

                        String mess = ClwI18nUtil.getMessage(request, "error.date.interval.wrong", new Object[]{property1, property}, true);
                        ae.add("estimateDateIntervalWrong", new ActionError("error.simpleError", mess));
                    }
                }

                if (ae.size() > 0) {
                    return ae;
                }

                criteria.setType(mgrForm.getType());
                criteria.setStatus(mgrForm.getStatus());
                criteria.setPriority(mgrForm.getPriority());
                criteria.setHistStatus(RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER);
                criteria.setSpCancelCondition(true);

                WorkOrderDataVector result = wrkOrderEjb.getWorkOrderCollection(criteria);
                if ((result != null) && (!result.isEmpty())) {
                    Site siteEjb = factory.getSiteAPI();
                    SiteData siteD;
                    WorkOrderSiteNameView woSNV;
                    WorkOrderData woD;
                    for (int i = 0; i < result.size(); i++) {
                        woSNV = new WorkOrderSiteNameView();
                        woD = (WorkOrderData)result.get(i);
                        woSNV.setWorkOrder(woD);
                        siteD = siteEjb.getSite(woD.getBusEntityId(), 0, false, SessionTool.getCategoryToCostCenterView(session, woD.getBusEntityId()));
                        if (siteD != null) {
                            woSNV.setSiteName(siteD.getBusEntity().getShortDesc());
                        } else {
                            woSNV.setSiteName("");
                        }
                        resultAndSiteName.add(woSNV);
                    }
                }

                mgrForm.setSearchResult(resultAndSiteName);
                session.setAttribute(USER_WORK_ORDER_MGR_FORM, mgrForm);

            } else {
                throw new Exception("No active Service Providers configured for the user: " + appUser.getUserName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                ae.add("error", new ActionError("error.simpleGenericError", e.toString()));
            }
        }

        return ae;
    }

    public static ActionErrors initSearch(HttpServletRequest request, UserWorkOrderMgrForm mgrForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if(mgrForm==null || !mgrForm.init){
            mgrForm = new UserWorkOrderMgrForm();
            mgrForm.init();
        }

        try {

            ae.add(checkRequest(request, mgrForm));
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

            ListService listServiceEJB = factory.getListServiceAPI();
            WorkOrder wrkEjb           = factory.getWorkOrderAPI();
            Service serviceEjb         = factory.getServiceAPI();
            ServiceProviderType sptEjb = factory.getServiceProviderTypeAPI();

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            //IdVector siteIds = new IdVector();
            //if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER)) {
            //siteIds.add(new Integer(appUser.getSite().getSiteId()));
            //}

            session.setAttribute(Constants.BREAD_CRUMB_NAVIGATOR,null);

            if (isEmptySessionCd(session,"WorkOrder.user.status.vector")) {
                RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_STATUS_CD", ListService.ORDER_BY_NAME);
                Iterator it = statusCds.iterator();
                RefCdData refData;
                while (it.hasNext()) {
                    refData = (RefCdData)it.next();
                    if (RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(refData.getValue()) ||
                        RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(refData.getValue()) ||
                        RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED.equals(refData.getValue())) {
                        it.remove();
                    }
                }
                session.setAttribute("WorkOrder.user.status.vector", statusCds);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.type.vector")) {
                //RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_TYPE_CD");
                BusEntityDataVector types = sptEjb.getUserServiceProviderTypes(appUser.getUser().getUserId(), appUser.getUserStore().getStoreId());
                session.setAttribute("WorkOrder.user.type.vector", types);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.priority.vector")) {
                RefCdDataVector priorityCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_PRIORITY_CD", ListService.ORDER_BY_NAME);
                session.setAttribute("WorkOrder.user.priority.vector", priorityCds);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.service.provider.vector")) {
                ServiceProviderDataVector serviceProviders = serviceEjb.getServiceProvidersForUser(appUser.getUserId());
                session.setAttribute("WorkOrder.user.service.provider.vector", serviceProviders);
            }

            // get all records for all WorkOrders
            //WorkOrderSimpleSearchCriteria criteria = new WorkOrderSimpleSearchCriteria();
            //if (!siteIds.isEmpty()) {
            //    criteria.setSiteIds(siteIds);
            //}
            //WorkOrderDataVector workOrders = wrkEjb.getWorkOrderCollection(criteria);
            //HashMap queueStatistic = buildQueueStatistic(workOrders);
            //mgrForm.setQueueStatistic(queueStatistic);

            mgrForm.setDateType(RefCodeNames.WORK_ORDER_DATE_TYPE.ACTUAL);

        } catch (Exception e) {
			e.printStackTrace();
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

        session.setAttribute(USER_WORK_ORDER_MGR_FORM, mgrForm);
        return ae;
    }

    private static HashMap buildQueueStatistic(WorkOrderDataVector workOrders) {
        HashMap statistic = new HashMap();
        if (workOrders != null) {
            Iterator itar = workOrders.iterator();
            while (itar.hasNext()) {
                WorkOrderData workOrder = (WorkOrderData) itar.next();
                if (RefCodeNames.WORK_ORDER_PRIORITY_CD.LOW.equals(workOrder.getPriority())) {
                    if (statistic.containsKey(workOrder.getPriority())) {
                        Integer valInt = (Integer) statistic.get(RefCodeNames.WORK_ORDER_PRIORITY_CD.LOW);
                        int newVal = valInt.intValue() + 1;
                        statistic.put(RefCodeNames.WORK_ORDER_PRIORITY_CD.LOW, new Integer(newVal));
                    } else {
                        int newVal = 1;
                        statistic.put(RefCodeNames.WORK_ORDER_PRIORITY_CD.LOW, new Integer(newVal));
                    }
                } else if (RefCodeNames.WORK_ORDER_PRIORITY_CD.MEDIUM.equals(workOrder.getPriority())) {
                    if (statistic.containsKey(workOrder.getPriority())) {
                        Integer valInt = (Integer) statistic.get(RefCodeNames.WORK_ORDER_PRIORITY_CD.MEDIUM);
                        int newVal = valInt.intValue() + 1;
                        statistic.put(RefCodeNames.WORK_ORDER_PRIORITY_CD.MEDIUM, new Integer(newVal));
                    } else {
                        int newVal = 1;
                        statistic.put(RefCodeNames.WORK_ORDER_PRIORITY_CD.MEDIUM, new Integer(newVal));
                    }
                } else if (RefCodeNames.WORK_ORDER_PRIORITY_CD.HIGH.equals(workOrder.getPriority())) {
                    if (statistic.containsKey(workOrder.getPriority())) {
                        Integer valInt = (Integer) statistic.get(RefCodeNames.WORK_ORDER_PRIORITY_CD.HIGH);
                        int newVal = valInt.intValue() + 1;
                        statistic.put(RefCodeNames.WORK_ORDER_PRIORITY_CD.HIGH, new Integer(newVal));
                    } else {
                        int newVal = 1;
                        statistic.put(RefCodeNames.WORK_ORDER_PRIORITY_CD.HIGH, new Integer(newVal));
                    }
                }
            }
        }

        return statistic;
    }

    private static AssetDataVector getAssetCategories(AssetDataVector assetDV) throws Exception {

        IdVector assetIds = new IdVector();
        AssetDataVector assetCat = new AssetDataVector();
        APIAccess factory = new APIAccess();
        Asset assetEjb = factory.getAssetAPI();
        if (assetDV != null && !assetDV.isEmpty()) {
            Iterator it = assetDV.iterator();
            while (it.hasNext()) {
                AssetData assetData = (AssetData) it.next();
                if (assetData.getParentId() > 0 ) {
                    assetIds.add(new Integer(assetData.getParentId()));
                }
            }
            if (!assetIds.isEmpty()) {
                AssetSearchCriteria criteria = new AssetSearchCriteria();
                criteria.setAssetIds(assetIds);
                criteria.setShowInactive(false);
                criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
                criteria.setOrderBy(new PairView(AssetDataAccess.SHORT_DESC, Boolean.TRUE));
                assetCat = assetEjb.getAssetDataByCriteria(criteria);
            }
        }
        return assetCat;
    }


    private static ActionErrors checkRequest(HttpServletRequest request, UserWorkOrderMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (mgrForm == null) {
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

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }

        return ae;
    }


    public static ActionErrors searchByPriority(HttpServletRequest request, UserWorkOrderMgrForm mgrForm) throws Exception {

        String priority = request.getParameter("priority");

        ActionErrors ae = initSearch(request,null);
        if(ae.size()>0){
            return ae;
        }

        mgrForm = (UserWorkOrderMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_MGR_FORM);
        mgrForm.setPriority(priority);

        return search(request, mgrForm);
    }


    public static ActionErrors getDetail(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        try {
            int workOrderId = getWorkOrderIdFromRequest(request, mgrForm);

            if (workOrderId > 0) {
            ae = init(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            WorkOrder wrkEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
            Service serviceEjb =  APIAccess.getAPIAccess().getServiceAPI();

            mgrForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

            WorkOrderDetailView detail = wrkEjb.getWorkOrderDetailView(workOrderId);

            uploadDetailData(request, detail, mgrForm);
            }

        } catch (Exception e) {
        	e.printStackTrace();
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, mgrForm);
        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String managementSource = className;
        try {

            if(mgrForm!=null){
                managementSource = mgrForm.managementSource;
            }

            mgrForm = new UserWorkOrderDetailMgrForm();

            mgrForm.managementSource = managementSource;
            mgrForm.setProviderTradingType(WorkOrderUtil.EMAIL);
            mgrForm.setSchedules(new OrderScheduleDataVector());

            ae.add(checkRequest(request, mgrForm));
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            ListService listServiceEJB = factory.getListServiceAPI();
            Service serviceEjb = factory.getServiceAPI();
            Country countryEjb = factory.getCountryAPI();
            Budget budgetEjb   = factory.getBudgetAPI();
            ServiceProviderType sptEjb = factory.getServiceProviderTypeAPI();

            SiteData site       = appUser.getSite();
            StoreData store     = appUser.getUserStore();
            AccountData account = appUser.getUserAccount();

            if (isEmptySessionCd(session,"WorkOrder.user.status.vector")) {
                RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_STATUS_CD", ListService.ORDER_BY_NAME);
                Iterator it = statusCds.iterator();
                RefCdData refData;
                while (it.hasNext()) {
                    refData = (RefCdData)it.next();
                    if (RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(refData.getValue()) ||
                        RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(refData.getValue()) ||
                        RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED.equals(refData.getValue())) {
                        it.remove();
                    }
                }
                session.setAttribute("WorkOrder.user.status.vector", statusCds);
            }

            if (isEmptySessionCd(session,"countries.vector")) {
                CountryDataVector countries = countryEjb.getAllCountries();
                session.setAttribute("countries.vector", countries);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.action.vector")) {
                RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_ACTION_CD");
                session.setAttribute("WorkOrder.user.action.vector", statusCds);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.type.vector")) {
                //RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_TYPE_CD");
                BusEntityDataVector types = sptEjb.getUserServiceProviderTypes(appUser.getUser().getUserId(), appUser.getUserStore().getStoreId());
                session.setAttribute("WorkOrder.user.type.vector", types);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.priority.vector")) {
                RefCdDataVector priorityCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_PRIORITY_CD", ListService.ORDER_BY_NAME);
                session.setAttribute("WorkOrder.user.priority.vector", priorityCds);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.service.provider.vector")) {
                ServiceProviderDataVector serviceProviders = serviceEjb.getServiceProvidersForUser(appUser.getUserId());
                session.setAttribute("WorkOrder.user.service.provider.vector", serviceProviders);
            }

            if (isEmptySessionCd(session,"WorkOrder.user.category.vector")) {
                RefCdDataVector categoryCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_CATEGORY_CD");
                session.setAttribute("WorkOrder.user.category.vector", categoryCds);
            }

            if (isEmptySessionCd(session, "WorkOrder.user.costcenter.vector")) {
//                CostCenterDataVector cc = budgetEjb.getWorkOrderCostCenters(store.getStoreId(),
//                        account.getAccountId(),
//                        site.getSiteId(),
//                        false);
				CostCenterDataVector cc = new CostCenterDataVector();
                session.setAttribute("WorkOrder.user.costcenter.vector", cc);
            }

            // init workOrderContactData
            uploadWorkOrderContactData(mgrForm, WorkOrderUtil.createEmptyWorkOrderContact());

            mgrForm.setWorkOrderItemDetail(new WorkOrderItemDetailView(
                                                    WorkOrderItemData.createValue(),
                                                    new WoiStatusHistDataVector(),
                                                    WarrantyData.createValue(),
                                                    new WorkOrderAssetViewVector(),
                                                    new WorkOrderContentViewVector(),
                                                    new WorkOrderPropertyDataVector()));
            mgrForm.setAllAssets(new AssetDataVector());
            mgrForm.setWarrantyForActiveAsset(new WarrantyDataVector());

            AssetDataVector assets = getAllAssetsForUser(factory, appUser);
            AssetDataVector assetCats = getAssetCategories(assets);

            // load orders made for this WorkOrderItem (parts orders)
            int workOrderItemId = mgrForm.getWorkOrderItemId();
            if (workOrderItemId > 0) {
                Order orderEjb = factory.getOrderAPI();
                OrderDataVector orders = orderEjb.getOrdersByItem(workOrderItemId);
                mgrForm.setItemOrders(orders);
            }
            mgrForm.setAllowBuyWorkOrderParts(checkAllowBuyWorkOrderParts(appUser));

            //key is <String> categoryName,object is <AssetDataVector> assets
            PairViewVector assetGroupMap = groupByCategory(assetCats,assets);

            mgrForm.setAssetCategories(assetCats);
            mgrForm.setAllAssets(assets);
            mgrForm.setAssetGroups(assetGroupMap);

            session.setAttribute("WorkOrderItem.user.status.vector", new RefCdDataVector());
            RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_ITEM_STATUS_CD");
            session.setAttribute("WorkOrderItem.user.status.vector", statusCds);

        } catch (Exception e) {
        	e.printStackTrace();
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, mgrForm);
        return ae;
    }

    private static boolean isEmptySessionCd(HttpSession session, String s) {
        if(session!=null){
            if(session.getAttribute(s)!=null){
                if(session.getAttribute(s) instanceof List){
                    return ((List)session.getAttribute(s)).isEmpty();
                }
            }
        }
        return true;
    }

    public static ActionErrors addTableLines(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

        int woId = mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId();
        WorkOrderDetailDataVector woDetailDV = mgrForm.getItemizedService();
        int maxLineNumber = WorkOrderUtil.getItemizedServiceTableMaxLineNumber(woDetailDV);

        WorkOrderDetailData currentLineOld;
        WorkOrderDetailData currentLineNew;
        int count = 0;
        if (woId == 0) {
            int size = woDetailDV.size();
            for (int i = 0; i < size; i++) {
                currentLineNew = (WorkOrderDetailData)woDetailDV.get(i);
                if (RefCodeNames.STATUS_CD.INACTIVE.equals(currentLineNew.getStatusCd())) {
                    currentLineNew.setStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
                }
            }
        } else {
            WorkOrder wrkEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
            WorkOrderDetailDataVector woDetailDVold = wrkEjb.getWorkOrderDetails(woId);

            int sizeOld = woDetailDVold.size();
            int sizeNew = woDetailDV.size();
            //clear delete status in new vector
            for (int i = 0; i < sizeOld; i++) {
                currentLineOld = (WorkOrderDetailData)woDetailDVold.get(i);
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineOld.getStatusCd())) {
                    for (int j = 0; j < sizeNew; j++) {
                        currentLineNew = (WorkOrderDetailData)woDetailDV.get(j);
                        if (currentLineOld.getLineNum() == currentLineNew.getLineNum()) {
                            if (RefCodeNames.STATUS_CD.INACTIVE.equals(currentLineNew.getStatusCd())) {
                                currentLineNew.setStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
                            }
                            break;
                        }
                    }
                }
            }
        }
        count = WorkOrderUtil.getEmptyLinesCount(woDetailDV);
        WorkOrderItemizedServiceStrVector woISSV = mgrForm.getItemizedServiceStr();
        if (count < 5) {
            int addCount = 5 - count;
            WorkOrderUtil.addEmptyWorkOrderDetailData(woDetailDV, woId, maxLineNumber + 1, appUser.getUserName(), addCount);
            for (int i = 0; i < addCount; i++) {
                woISSV.add(WorkOrderItemizedServiceStr.createValue());
            }

            mgrForm.setItemizedServiceTableMaxLineNumber(maxLineNumber + addCount);
            mgrForm.setItemizedServiceTableActiveCount(WorkOrderUtil.getItemizedServiceTableActiveCount(woDetailDV));
            mgrForm.setItemizedService(woDetailDV);
            mgrForm.setItemizedServiceStr(woISSV);
        }

        return new ActionErrors();
    }

public static void uploadDetailData(HttpServletRequest request,
                                        WorkOrderDetailView detail,
                                        UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

        mgrForm.setWorkOrderDetail(detail);

        mgrForm.setBusEntityId(detail.getWorkOrder().getBusEntityId());
        mgrForm.setWorkOrderId(detail.getWorkOrder().getWorkOrderId());
        mgrForm.setTypeCd(Utility.strNN(detail.getWorkOrder().getTypeCd()));
        mgrForm.setStatusCd(Utility.strNN(detail.getWorkOrder().getStatusCd()));
        mgrForm.setShortDesc(Utility.strNN(detail.getWorkOrder().getShortDesc()));
        mgrForm.setLongDesc(Utility.strNN(detail.getWorkOrder().getLongDesc()));
        mgrForm.setCategoryCd(Utility.strNN(detail.getWorkOrder().getCategoryCd()));
        mgrForm.setPriority(Utility.strNN(detail.getWorkOrder().getPriority()));
        mgrForm.setActionCode(Utility.strNN(detail.getWorkOrder().getActionCd()));
        mgrForm.setCostCenterId(String.valueOf(detail.getWorkOrder().getCostCenterId()));

        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER)) {
            if (!Utility.isSet(mgrForm.getWorkflowProcessing())) {
                if (RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(detail.getWorkOrder().getStatusCd())) {
                    mgrForm.setWorkflowProcessing(Boolean.toString(false));
                } else {
                    mgrForm.setWorkflowProcessing(Boolean.toString(true));
                }
            }
        }

        String quotedStartDate = "";
        if (detail.getWorkOrder().getEstimateStartDate() != null) {
            quotedStartDate = ClwI18nUtil.formatDateInp(request,detail.getWorkOrder().getEstimateStartDate());
        }
        mgrForm.setQuotedStartDate(quotedStartDate);

        String quotedFinishDate = "";
        if (detail.getWorkOrder().getEstimateFinishDate() != null) {
            quotedFinishDate = ClwI18nUtil.formatDateInp(request,detail.getWorkOrder().getEstimateFinishDate());
        }
        mgrForm.setQuotedFinishDate(quotedFinishDate);

        String actualStartDate = "";
        if (detail.getWorkOrder().getActualStartDate() != null) {
            actualStartDate = ClwI18nUtil.formatDateInp(request,detail.getWorkOrder().getActualStartDate());
        }
        mgrForm.setActualStartDate(actualStartDate);

        String actualFinishDate = "";
        if (detail.getWorkOrder().getActualFinishDate() != null) {
            actualFinishDate = ClwI18nUtil.formatDateInp(request,detail.getWorkOrder().getActualFinishDate());
        }
        mgrForm.setActualFinishDate(actualFinishDate);

        APIAccess factory = APIAccess.getAPIAccess();
        WorkOrder workOrderEjb = factory.getWorkOrderAPI();
        Site siteEjb = factory.getSiteAPI();
        Store storeEjb = factory.getStoreAPI();
        User userEjb = factory.getUserAPI();
        WorkOrderDetailDataVector woDetailDV;
        WorkOrderItemizedServiceStrVector woISSV = new WorkOrderItemizedServiceStrVector();

        if (detail.getWorkOrder().getBusEntityId() > 0) {
            SiteData siteD = siteEjb.getSite(detail.getWorkOrder().getBusEntityId(), 0, false, SessionTool.getCategoryToCostCenterView(request.getSession(), detail.getWorkOrder().getBusEntityId()));
            int storeId = storeEjb.getStoreIdByAccount(siteD.getAccountId());
            UserData userD = userEjb.getUserByName(detail.getWorkOrder().getAddBy(), storeId);

            if (userD != null) {
                mgrForm.setUserWorkOrderCreatorFullName(userD.getFirstName() +  " " + userD.getLastName());
            }

//            woDetailDV = workOrderEjb.getWorkOrderDetails(detail.getWorkOrder().getWorkOrderId());
            woDetailDV = detail.getItemizedService();
        } else {
            woDetailDV = new WorkOrderDetailDataVector();
        }

        // Categorized Costs
        ArrayList partsCost = WorkOrderUtil.getPartsCostSum(woDetailDV);
        ArrayList laborCost =  WorkOrderUtil.getLaborCostSum(woDetailDV);
        ArrayList travelCost = WorkOrderUtil.getTravelCostSum(woDetailDV);

        String partsBilledServiceStr = ((BigDecimal)partsCost.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String partsWarrantyStr = ((BigDecimal)partsCost.get(1)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String partsPMContractStr = ((BigDecimal)partsCost.get(2)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String partsTotalStr = ((BigDecimal)partsCost.get(3)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborBilledServiceStr = ((BigDecimal)laborCost.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborWarrantyStr = ((BigDecimal)laborCost.get(1)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborPMContractStr = ((BigDecimal)laborCost.get(2)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborTotalStr = ((BigDecimal)laborCost.get(3)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelBilledServiceStr = ((BigDecimal)travelCost.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelWarrantyStr = ((BigDecimal)travelCost.get(1)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelPMContractStr = ((BigDecimal)travelCost.get(2)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelTotalStr = ((BigDecimal)travelCost.get(3)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        String totalBilledServiceStr =      (((BigDecimal)partsCost.get(0)).
                                         add(((BigDecimal)laborCost.get(0))).
                                         add(((BigDecimal)travelCost.get(0)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String totalWarrantyStr =           (((BigDecimal)partsCost.get(1)).
                                         add(((BigDecimal)laborCost.get(1))).
                                         add(((BigDecimal)travelCost.get(1)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String totalPMContractStr =      (((BigDecimal)partsCost.get(2)).
                                         add(((BigDecimal)laborCost.get(2))).
                                         add(((BigDecimal)travelCost.get(2)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String totalTotalStr =              (((BigDecimal)partsCost.get(3)).
                                         add(((BigDecimal)laborCost.get(3))).
                                         add(((BigDecimal)travelCost.get(3)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        mgrForm.setPartsBilledService(partsBilledServiceStr);
        mgrForm.setPartsWarranty(partsWarrantyStr);
        mgrForm.setPartsPMContract(partsPMContractStr);
        mgrForm.setPartsTotal(partsTotalStr);
        mgrForm.setLaborBilledService(laborBilledServiceStr);
        mgrForm.setLaborWarranty(laborWarrantyStr);
        mgrForm.setLaborPMContract(laborPMContractStr);
        mgrForm.setLaborTotal(laborTotalStr);
        mgrForm.setTravelBilledService(travelBilledServiceStr);
        mgrForm.setTravelWarranty(travelWarrantyStr);
        mgrForm.setTravelPMContract(travelPMContractStr);
        mgrForm.setTravelTotal(travelTotalStr);
        mgrForm.setTotalBilledService(totalBilledServiceStr);
        mgrForm.setTotalWarranty(totalWarrantyStr);
        mgrForm.setTotalPMContract(totalPMContractStr);
        mgrForm.setTotalTotal(totalTotalStr);

        int maxLineNumber = WorkOrderUtil.getItemizedServiceTableMaxLineNumber(woDetailDV);
        mgrForm.setItemizedServiceTableDataLineCount(WorkOrderUtil.getItemizedServiceTableActiveCount(woDetailDV));

        int woId = detail.getWorkOrder().getWorkOrderId();
        int toAddEmpty = 0;
        if (appUser.canEditWorkOrder(detail.getWorkOrder().getStatusCd())) {
            toAddEmpty = 5 - (WorkOrderUtil.getItemizedServiceTableActiveCount(woDetailDV) % 5);
            WorkOrderUtil.addEmptyWorkOrderDetailData(woDetailDV, woId, maxLineNumber + 1, appUser.getUserName(), toAddEmpty);
        }
        mgrForm.setItemizedServiceTableMaxLineNumber(maxLineNumber + toAddEmpty);
        mgrForm.setItemizedServiceTableActiveCount(WorkOrderUtil.getItemizedServiceTableActiveCount(woDetailDV));
        mgrForm.setItemizedService(woDetailDV);

        WorkOrderItemizedServiceStr woISS;
        WorkOrderDetailData woDD;
        int size = woDetailDV.size();
        for (int i = 0; i < size; i++) {
            woDD = (WorkOrderDetailData)woDetailDV.get(i);
            woISS = WorkOrderItemizedServiceStr.createValue();
            if (RefCodeNames.STATUS_CD.ACTIVE.equals(woDD.getStatusCd())) {
                woISS.setQuantity(String.valueOf(woDD.getQuantity()));
                woISS.setPartPrice(woDD.getPartPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                woISS.setLabor(woDD.getLabor().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                woISS.setTravel(woDD.getTravel().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            woISSV.add(woISS);
        }
        mgrForm.setItemizedServiceStr(woISSV);

        mgrForm.setAddBy(detail.getWorkOrder().getAddBy());
        mgrForm.setAddDate(detail.getWorkOrder().getAddDate());
        mgrForm.setModBy(detail.getWorkOrder().getModBy());
        mgrForm.setModDate(detail.getWorkOrder().getModDate());

        int provider = WorkOrderUtil.getAssignedServiceProviderId(detail.getWorkOrderAssocCollection());
        if(provider>0){
            mgrForm.setServiceProviderIdStr(String.valueOf(provider));
            mgrForm.setServiceProvider(getServiceProvider(provider));
        } else {
            mgrForm.setServiceProviderIdStr(EMPTY);
            mgrForm.setServiceProvider(null);
        }

//        ServiceProviderDataVector actualProviders = getDisplayServiceProviders(mgrForm,appUser);
//        mgrForm.setActualServiceProviders(actualProviders);
      mgrForm.setActualServiceProviders(new ServiceProviderDataVector());

//        OrderScheduleDataVector schedules = getSchedule(appUser.getSite().getSiteId(),
//                mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId());
//        mgrForm.setSchedules(schedules);
      	mgrForm.setSchedules(new OrderScheduleDataVector());

//        int siteId = mgrForm.getBusEntityId();
//        if (siteId == 0) {
//            siteId = appUser.getSite().getSiteId();
//        }
//        CostCenterDataVector costCenters = getDisplayCostCenters(mgrForm.getWorkOrderDetail().getWorkOrder(), siteId);
//        mgrForm.setDisplayCostCenters(costCenters);
      	mgrForm.setDisplayCostCenters(new CostCenterDataVector());

        //----------------------------------------------------------------------------
        mgrForm.setAllowSetWorkOrderPoNumber(checkAllowSetWorkOrderPoNumber(appUser));
        mgrForm.setWorkOrderPoNumberIsRequired(checkWorkOrderPoNumberIsRequired(appUser));
        mgrForm.setContactInformationType(checkContactInformationType(appUser));

        String workOrderNum = detail.getWorkOrder().getWorkOrderNum();
        if (workOrderNum == null || workOrderNum.trim().length() == 0) {
            workOrderNum = Integer.toString(detail.getWorkOrder().getWorkOrderId());
        }
        mgrForm.setWorkOrderNum(workOrderNum);

        String workOrderPoNum = detail.getWorkOrder().getPoNumber();
        if (mgrForm.getWorkOrderPoNumberIsRequired() && (workOrderPoNum == null || workOrderPoNum.trim().length() == 0)) {
            workOrderPoNum = Integer.toString(detail.getWorkOrder().getWorkOrderId());
        }
        mgrForm.setWorkOrderPoNum(workOrderPoNum);

        // NTE field
        if(detail.getWorkOrder().getNte() != null)
        {
        	mgrForm.setNte(detail.getWorkOrder().getNte());
        	mgrForm.setNteStr(String.format("%.2f", detail.getWorkOrder().getNte()));
        }
        else
        {
        	mgrForm.setNte(null);
        	mgrForm.setNteStr(null);
        }


        //----------------------------------------------------------------------------

//        PairView budgetInfo = getBudgetInfo(mgrForm, appUser);
//        mgrForm.setShortBudgetSpent((BudgetSpentAmountView)budgetInfo.getObject1());
//        mgrForm.setDetailBudgetSpent((BudgetSpendViewVector)budgetInfo.getObject2());

        // fill out current WorkOrderItem data
        WorkOrderItemDetailViewVector woiDV = detail.getWorkOrderItems();
        if (woiDV != null && woiDV.size() > 0) {
            WorkOrderItemDetailView woiDet;
            DisplayListSort.sort(woiDV, "sequence");

            woiDet = getWoiDetail(woiDV, ((WorkOrderItemDetailView)woiDV.get(0)).getWorkOrderItem().getWorkOrderItemId());

                if (woiDet != null) {
                    uploadItemDetailData(woiDet, mgrForm);
                }
            }
        uploadWorkOrderContactData(mgrForm,detail.getProperties());
    }

    public static void uploadItemDetailData(WorkOrderItemDetailView woiDet, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        if (woiDet != null && woiDet.getWorkOrderItem() != null) {
            mgrForm.setWorkOrderItemDetail(woiDet);

            WorkOrderItemData woi = woiDet.getWorkOrderItem();
            BigDecimal quotedTotalCost = null;
            BigDecimal actualTotalCost = null;
            mgrForm.setWorkOrderItemId(woi.getWorkOrderItemId());
            mgrForm.setWarrantyId(woi.getWarrantyId());
            mgrForm.setItemShortDesc(Utility.strNN(woi.getShortDesc()));
            mgrForm.setItemLongDesc(Utility.strNN(woi.getLongDesc()));
            mgrForm.setItemSequence(String.valueOf(woi.getSequence()));
            mgrForm.setItemStatusCd(Utility.strNN(woi.getStatusCd()));

            String quotedLaborStr = "";
            if (woi.getEstimateLabor() != null) {
                BigDecimal quotedLabor = woi.getEstimateLabor();
                quotedTotalCost   = Utility.addAmt(quotedTotalCost, quotedLabor);
                quotedLaborStr    = quotedLabor.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setItemQuotedLabor(quotedLaborStr);

            String quotedPartStr = "";
            if (woi.getEstimatePart() != null) {
                BigDecimal quotedPart = woi.getEstimatePart();
                quotedTotalCost  = Utility.addAmt(quotedTotalCost, quotedPart);
                quotedPartStr    = quotedPart.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setItemQuotedPart(quotedPartStr);

            String quotedTotalCostStr = "";
            if (quotedTotalCost != null) {
                quotedTotalCostStr = quotedTotalCost.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setItemQuotedTotalCost(quotedTotalCostStr);

            String actualLaborStr = "";
            if (woi.getActualLabor() != null) {
                BigDecimal actualLabor = woi.getActualLabor();
                actualTotalCost   = Utility.addAmt(actualTotalCost,actualLabor);
                actualLaborStr    = actualLabor.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setItemActualLabor(actualLaborStr);

            String actualPartStr = "";
            if (woi.getActualPart() != null) {
                BigDecimal actualPart = woi.getActualPart();
                actualTotalCost  = Utility.addAmt(actualTotalCost,actualPart);
                actualPartStr    = actualPart.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setItemActualPart(actualPartStr);

            String actualTotalCostStr = "";
            if (actualTotalCost != null) {
                actualTotalCostStr = actualTotalCost.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setItemActualTotalCost(actualTotalCostStr);

            int assetId = WorkOrderUtil.getAssignedAssetId(woiDet.getAssetAssoc());
            if (assetId > 0) {
                mgrForm.setActiveAssetIdStr(String.valueOf(assetId));
            }

            if (Utility.isSet(mgrForm.getActiveAssetIdStr())) {

                AssetData asset = getAssetData(Integer.parseInt(mgrForm.getActiveAssetIdStr()), false);
                mgrForm.setActiveAsset(asset);

                buildDisplayAssets(mgrForm);

                WarrantyDataVector warranties = new WarrantyDataVector();
                if (mgrForm.getActiveAsset() != null) {
                    WorkOrder wrkejb = APIAccess.getAPIAccess().getWorkOrderAPI();
                    IdVector assetIds = new IdVector();
                    assetIds.add(new Integer(mgrForm.getActiveAsset().getAssetId()));
                    warranties = wrkejb.getWorkOrderWarrantiesForAssets(assetIds,RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);
                }
                mgrForm.setWarrantyForActiveAsset(warranties);

            } else {
                mgrForm.setActiveAssetIdStr(EMPTY);
                mgrForm.setActiveAsset(null);
            }

            if (woiDet.getWarranty() != null) {
                mgrForm.setActiveWarrantyIdStr(String.valueOf(woiDet.getWarranty().getWarrantyId()));
            } else {
                mgrForm.setActiveWarrantyIdStr(EMPTY);
            }

            buildDisplayWarranties(mgrForm);

            mgrForm.setItemAddBy(woi.getAddBy());
            mgrForm.setItemAddDate(woi.getAddDate());
            mgrForm.setItemModBy(woi.getModBy());
            mgrForm.setItemModDate(woi.getModDate());
        }
    }

    public static ActionErrors searchPendingOrders(HttpServletRequest request, UserWorkOrderMgrForm mgrForm) throws Exception {
        ActionErrors ae = initSearch(request,null);
        if(ae.size()>0){
            return ae;
        }

        mgrForm = (UserWorkOrderMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_MGR_FORM);
        mgrForm.setStatus(RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL);

        ae = search(request, mgrForm);

        mgrForm.setStatus(EMPTY);

        return ae;
    }

    private static CostCenterDataVector getDisplayCostCenters(WorkOrderData workOrder, int siteId) throws Exception {

        Budget budgetEjb = APIAccess.getAPIAccess().getBudgetAPI();
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();

        int accountId = accountEjb.getAccountIdForSite(siteId);
        FiscalCalenderData fiscalCalendar = accountEjb.getCurrentFiscalCalender(accountId);
        if(fiscalCalendar == null)  {
           log.info("getCurrentBudgetYear => No fiscal calendar found.AccountId => " + accountId);
           return new CostCenterDataVector();
        }

        CostCenterDataVector cc = budgetEjb.getWorkOrderCostCenters(siteId, fiscalCalendar.getFiscalYear(), false);

        IdVector ccIds = Utility.toIdVector(cc);
        if (workOrder.getCostCenterId() > 0 && !ccIds.contains(new Integer(workOrder.getCostCenterId()))) {
            try {
                CostCenterData assignedCc = accountEjb.getCostCenter(workOrder.getCostCenterId(),0);
                cc.add(assignedCc);
            } catch (DataNotFoundException e) {
                log.info("getDisplayCostCenters => WARANING:" + e.getMessage());
            }
        }
        return cc;
    }

    private static boolean checkAllowSetWorkOrderPoNumber(CleanwiseUser appUser) throws Exception {
        boolean result = false;

        AccountData ad = null;
        if ((ad = appUser.getUserAccount()) != null) {
            String property = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER);
            result = Utility.isTrue(property, false);
        }
        return result;
    }

    private static boolean checkWorkOrderPoNumberIsRequired(CleanwiseUser appUser) throws Exception {
        boolean result = false;

        AccountData ad = null;
        if ((ad = appUser.getUserAccount()) != null) {
            String property = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED);
            result = Utility.isTrue(property, false);
        }
        return result;
    }

    private static String checkContactInformationType(CleanwiseUser appUser) throws Exception {
        String result = "";

        AccountData ad = null;
        if ((ad = appUser.getUserAccount()) != null) {
            String property = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE);
            result = Utility.strNN(property);
        }
        return result;
    }

    private static boolean checkAllowBuyWorkOrderParts(CleanwiseUser appUser) throws Exception {
        boolean result = false;

        AccountData ad = null;
        if ((ad = appUser.getUserAccount()) != null) {
            String property = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS);
            result = Utility.isTrue(property, false);
        }
        return result;
    }

    private static ServiceProviderData getServiceProvider(int providerId) throws Exception {
        Service serviceEjb = APIAccess.getAPIAccess().getServiceAPI();
        ServiceProviderData serviceProvider = null;
        try {
            serviceProvider = serviceEjb.getServiceProvider(providerId);
        } catch (DataNotFoundException e) {
            log.info("getServiceProvider => Error." + e.getMessage());
        }
        return serviceProvider;
    }

    private static OrderScheduleDataVector getSchedule(int siteId, int workOrderId) throws Exception {
        APIAccess factory = APIAccess.getAPIAccess();
        AutoOrder autoOrderEjb = factory.getAutoOrderAPI();
        OrderScheduleDataVector result = new OrderScheduleDataVector();
        IdVector workOrderIds = new IdVector();
        if (workOrderId > 0) {
            workOrderIds.add(new Integer(workOrderId));
            result = autoOrderEjb.getWorkOrderSchedules(siteId, workOrderIds);
        }
        return result;
    }

    private static ServiceProviderDataVector getServiceProviders(Service serviceEjb,int accountId,String typeCd) throws RemoteException {
        if(Utility.isSet(typeCd) && accountId>0){
            return serviceEjb.getServiceProvidersForAccount(accountId,typeCd,false);
        } else {
            return new ServiceProviderDataVector();
        }
    }

    private static ServiceProviderDataVector getDisplayServiceProviders(UserWorkOrderDetailMgrForm mgrForm, CleanwiseUser appUser) throws Exception {

        Service serviceEjb = APIAccess.getAPIAccess().getServiceAPI();

        String type = mgrForm.getTypeCd();
        int accountId = appUser.getUserAccount().getAccountId();

        ServiceProviderDataVector providers = getServiceProviders(serviceEjb, accountId, type);

        int providerId = WorkOrderUtil.getAssignedServiceProviderId(mgrForm.getWorkOrderDetail().getWorkOrderAssocCollection());

        if (providerId > 0) {

            ServiceProviderData assignedPovider;
            if (mgrForm.getActiveServiceProvider() != null
                    && mgrForm.getActiveServiceProvider().getBusEntity().getBusEntityId() == providerId) {
                assignedPovider = mgrForm.getActiveServiceProvider();
            } else {
                assignedPovider = getServiceProvider(providerId);
            }

            if (assignedPovider != null && Utility.isSet(type) && type.equals(assignedPovider.getServiceProvider().getValue())) {
                IdVector providerIds = Utility.toIdVector(providers);
                Integer assignedProviderId = new Integer(assignedPovider.getBusEntity().getBusEntityId());
                if (!providerIds.contains(assignedProviderId)) {
                    providers.add(assignedPovider);
                }
            }
        }

        return providers;
    }

    private static void uploadWorkOrderContactData(UserWorkOrderDetailMgrForm mgrForm,
                                                   WorkOrderPropertyDataVector properties) {
        if(properties!=null && !properties.isEmpty()){
            Iterator it = properties.iterator();
            while(it.hasNext()){
                WorkOrderPropertyData workOrderProperty = (WorkOrderPropertyData) it.next();
                if(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO.equals(workOrderProperty.getPropertyCd())){
                    if(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS1.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactAddress1(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS2.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactAddress2(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.CITY.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactCity(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.COUNTRY.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactCountry(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.FAX.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactFaxNum(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.PHONE.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactPhoneNum(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.MOBILE.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactMobilePhone(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactFirstName(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactLastName(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.STATE.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactState(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.ZIP.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactPostalCode(workOrderProperty);
                    } else if(RefCodeNames.WORK_ORDER_CONTACT.EMAIL.equals(workOrderProperty.getShortDesc())){
                        mgrForm.setUserContactEmail(workOrderProperty);
                    }
                }
            }
        }

    }

    private static ServiceProviderData findServiceProvider(HttpSession session, int id) throws Exception {
        ServiceProviderData resultProvider = null;
        ServiceProviderDataVector providers = (ServiceProviderDataVector) session.getAttribute("WorkOrder.user.service.provider.vector");

        if (providers != null && !providers.isEmpty()) {
            Iterator it = providers.iterator();
            while (it.hasNext()) {
                ServiceProviderData servProvider = (ServiceProviderData) it.next();
                if (servProvider.getBusEntity().getBusEntityId() == id) {
                    resultProvider = servProvider;
                    break;
                }
            }
        }

        if (resultProvider == null) {
            throw new Exception("Service Provider is not found.Provider Id: " + id);
        }

        return resultProvider;

    }

    private static void loadDetailData(HttpServletRequest request,WorkOrderDetailView detail, UserWorkOrderDetailMgrForm mgrForm, CleanwiseUser appUser) throws Exception {

        detail = mgrForm.getWorkOrderDetail();
        WorkOrderData newWrkData = WorkOrderData.createValue();

        if (mgrForm.getBusEntityId() <= 0) {
            newWrkData.setBusEntityId(appUser.getSite().getSiteId());
        } else {
            newWrkData.setBusEntityId(mgrForm.getBusEntityId());
        }

        newWrkData.setWorkOrderId(mgrForm.getWorkOrderId());
        newWrkData.setWorkOrderNum(mgrForm.getWorkOrderNum());
        newWrkData.setPoNumber(mgrForm.getWorkOrderPoNum());

        // NTE Field
        newWrkData.setNte(mgrForm.getNte());

        newWrkData.setTypeCd(mgrForm.getTypeCd());

        newWrkData.setStatusCd(mgrForm.getStatusCd());
        newWrkData.setShortDesc(mgrForm.getShortDesc());
        newWrkData.setLongDesc(mgrForm.getLongDesc());

        if(Utility.isSet(mgrForm.getCategoryCd())){
            newWrkData.setCategoryCd(mgrForm.getCategoryCd());
        } else{
            newWrkData.setCategoryCd(RefCodeNames.WORK_ORDER_CATEGORY_CD.UNKNOWN);
        }

        newWrkData.setPriority(mgrForm.getPriority());
        newWrkData.setActionCd(mgrForm.getActionCode());

        if (Utility.isSet(mgrForm.getCostCenterId())) {
            newWrkData.setCostCenterId(Integer.parseInt(mgrForm.getCostCenterId()));
        } else {
            newWrkData.setCostCenterId(0);
        }

        if (Utility.isSet(mgrForm.getActualStartDate())) {
            try {
                newWrkData.setActualStartDate(ClwI18nUtil.parseDateInp(request,mgrForm.getActualStartDate()));
            } catch (ParseException e) {
                newWrkData.setActualStartDate(null);
            }
        } else {
            newWrkData.setActualStartDate(null);
        }

        if (Utility.isSet(mgrForm.getEstimatedFinishDate())) {
            try {
                newWrkData.setEstimateFinishDate(ClwI18nUtil.parseDateInp(request,mgrForm.getEstimatedFinishDate()));
            } catch (ParseException e) {
                newWrkData.setEstimateFinishDate(null);
            }
        } else {
            newWrkData.setEstimateFinishDate(null);
        }

        if (Utility.isSet(mgrForm.getEstimatedStartDate())) {
            try {
                newWrkData.setEstimateStartDate(ClwI18nUtil.parseDateInp(request,mgrForm.getEstimatedStartDate()));
            } catch (ParseException e) {
                newWrkData.setEstimateStartDate(null);
            }
        } else {
            newWrkData.setEstimateStartDate(null);
        }

        if (Utility.isSet(mgrForm.getActualFinishDate())) {
            try {
                newWrkData.setActualFinishDate(ClwI18nUtil.parseDateInp(request,mgrForm.getActualFinishDate()));
            } catch (ParseException e) {
                newWrkData.setActualFinishDate(null);
            }
        } else {
            newWrkData.setActualFinishDate(null);
        }

        newWrkData.setAddBy(mgrForm.getAddBy());
        newWrkData.setAddDate(mgrForm.getAddDate());
        newWrkData.setModBy(mgrForm.getModBy());
        newWrkData.setModDate(mgrForm.getModDate());

        int providerId;
        if (Utility.isSet(mgrForm.getServiceProviderIdStr())) {
            try {
                providerId = Integer.parseInt(mgrForm.getServiceProviderIdStr());
                setProviderToAssoc(providerId, detail.getWorkOrderAssocCollection(), newWrkData);
            } catch (NumberFormatException e) {
                error(e.getMessage(),e);
            }
        } else {
            removeAssoc(detail.getWorkOrderAssocCollection(), RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER);
        }

        loadWorkOrderContactData(mgrForm,detail);

        detail.setWorkOrder(newWrkData);
    }

    private static void loadWorkOrderContactData(UserWorkOrderDetailMgrForm mgrForm,
                                                 WorkOrderDetailView woDetail) {

        removePropertiesFromVector(woDetail.getProperties(),RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);

        woDetail.getProperties().add(mgrForm.getUserContactAddress1());
        woDetail.getProperties().add(mgrForm.getUserContactAddress2());
        woDetail.getProperties().add(mgrForm.getUserContactCity());
        woDetail.getProperties().add(mgrForm.getUserContactFirstName());
        woDetail.getProperties().add(mgrForm.getUserContactLastName());
        woDetail.getProperties().add(mgrForm.getUserContactState());
        woDetail.getProperties().add(mgrForm.getUserContactMobilePhone());
        woDetail.getProperties().add(mgrForm.getUserContactPostalCode());
        woDetail.getProperties().add(mgrForm.getUserContactFaxNum());
        woDetail.getProperties().add(mgrForm.getUserContactEmail());
        woDetail.getProperties().add(mgrForm.getUserContactCountry());
        woDetail.getProperties().add(mgrForm.getUserContactPhoneNum());

    }

    private static void removePropertiesFromVector(WorkOrderPropertyDataVector properties, String typeCd) {
        if (!properties.isEmpty()) {
            Iterator it = properties.iterator();
            while (it.hasNext()) {
                WorkOrderPropertyData property = (WorkOrderPropertyData) it.next();
                if(typeCd.equals(property.getPropertyCd())){
                    it.remove();
                }
            }
        }
    }

    private static WorkOrderAssocDataVector setProviderToAssoc(int newProviderId, WorkOrderAssocDataVector workOrderAssoc, WorkOrderData workOrderData) {
        if (workOrderAssoc == null) {
            workOrderAssoc = new WorkOrderAssocDataVector();
            WorkOrderAssocData assocData = createProviderAssoc(newProviderId, workOrderData);
            if (assocData != null) {
                workOrderAssoc.add(assocData);
            }
        } else {
            workOrderAssoc = setNewProviderAssoc(workOrderAssoc, newProviderId, workOrderData);
        }
        return workOrderAssoc;
    }

    private static WorkOrderAssocDataVector setNewProviderAssoc(WorkOrderAssocDataVector workOrderAssoc, int newProviderId, WorkOrderData workOrderData) {
        boolean isSet = false;
        if (workOrderAssoc != null) {
            if (!workOrderAssoc.isEmpty()) {
                //find and replace provider if provider exist
                Iterator it = workOrderAssoc.iterator();
                while (it.hasNext()) {
                    WorkOrderAssocData assoc = (WorkOrderAssocData) it.next();
                    if (RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER.equals(assoc.getWorkOrderAssocCd())) {
                        assoc.setBusEntityId(newProviderId);
                        isSet = true;
                    }
                }
            }
            //if provider not exist create new assoc
            if (!isSet && newProviderId > 0) {
                WorkOrderAssocData assocData = createProviderAssoc(newProviderId, workOrderData);
                if (assocData != null) {
                    workOrderAssoc.add(assocData);
                }
            }
        }
        return workOrderAssoc;
    }

    private static WorkOrderAssocData createProviderAssoc(int providerId, WorkOrderData workOrderData) {

        if (workOrderData != null && providerId > 0) {
            WorkOrderAssocData assocData = WorkOrderAssocData.createValue();

            assocData.setBusEntityId(providerId);
            assocData.setWorkOrderId(workOrderData.getWorkOrderId());
            assocData.setWorkOrderAssocCd(RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER);
            assocData.setWorkOrderAssocStatusCd(RefCodeNames.WORK_ORDER_ASSOC_STATUS_CD.ACTIVE);

            return assocData;
        }
        return null;
    }

    private static int getIdFromRequest(HttpServletRequest request, String idName) {
        String IdStr = request.getParameter(idName);
        int IdInt = 0;
        if (Utility.isSet(IdStr)) {
            try {
                IdInt = Integer.parseInt(IdStr);
            } catch (NumberFormatException e) {
                error(e.getMessage(),e);
            }
        }
        return IdInt;
    }

    private static int getWorkOrderIdFromRequest(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) {

        int id = getIdFromRequest(request, "workOrderId");

        if (id <= 0 && mgrForm != null &&
                mgrForm.getWorkOrderDetail() != null &&
                mgrForm.getWorkOrderDetail().getWorkOrder() != null &&
                mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId() > 0) {
            return mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId();
        } else {
            return id;
        }
    }

    private static ActionErrors checkRequest(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (mgrForm == null) {
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

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }

        return ae;
    }

     public static ActionErrors createNewWorkOrder(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();

        ActionErrors ae = init(request, (UserWorkOrderDetailMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = APIAccess.getAPIAccess();
        User userEjb = factory.getUserAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        UserWorkOrderDetailMgrForm detailForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        WorkOrderData wrkData = WorkOrderData.createValue();
        wrkData.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST);

        WorkOrderDetailView detView = new WorkOrderDetailView(wrkData,
                new WorkOrderStatusHistDataVector(),
                new WorkOrderContentViewVector(),
                new WorkOrderNoteDataVector(),
                new WorkOrderItemDetailViewVector(),
                new WorkOrderAssocDataVector(),
                new WorkOrderPropertyDataVector(),
                null,
                new WorkOrderDetailDataVector());

        String contactInfoType = appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE);

        WorkOrderPropertyDataVector contactProps = WorkOrderUtil.createEmptyWorkOrderContact();
        if ("user".equals(contactInfoType)) {
        UserInfoData userContact = userEjb.getUserContact(appUser.getUserId());
            contactProps = WorkOrderUtil.convertUserContact(userContact);
        } else if ("site".equals(contactInfoType)) {
            SiteData sd = appUser.getSite();
            contactProps = WorkOrderUtil.getSiteContactDataAsWOPropertyVector(sd);
        }

        detView.setProperties(contactProps);
        uploadDetailData(request, detView, detailForm);

        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, detailForm);

        return ae;

    }

    public static ActionErrors updateWorkOrder(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        try {

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            ae = checkUserRight(request,appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            ae = checkFormAttribute(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();
            User userEjb = factory.getUserAPI();
            Account accountEjb = factory.getAccountAPI();
            Store storeEjb = factory.getStoreAPI();

            loadDetailData(request,mgrForm.getWorkOrderDetail(), mgrForm, appUser);

            int statusHistorySize = 0;
            WorkOrderData woD = mgrForm.getWorkOrderDetail().getWorkOrder();
            String forceStatus = mgrForm.getForceStatusChange();
            if (forceStatus != null) {
                if ("close".equals(forceStatus)) {
                    woD.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED);
                } else if ("cancel".equals(forceStatus)) {
                    woD.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED);
                } else if ("complete".equals(forceStatus)) {
                    woD.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED);
                } else if ("accept".equals(forceStatus)) {
                    woD.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER);
                } else if ("reject".equals(forceStatus)) {
                    woD.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER);
                } else if ("previous".equals(forceStatus)) {
                    WorkOrderStatusHistDataVector woSH = mgrForm.getWorkOrderDetail().getStatusHistory();
                    if (woSH != null) {
                        statusHistorySize = woSH.size();
                        if (statusHistorySize > 1) {
                            woD.setStatusCd(((WorkOrderStatusHistData)woSH.get(statusHistorySize - 2)).getStatusCd());
                        }
                    }
                }
            }


            // Work Order Details
            mgrForm.getWorkOrderDetail().setItemizedService(mgrForm.getItemizedService());

            // Ejb Update
            WorkOrderDetailView newData = wrkEjb.updateWorkOrderDetail(mgrForm.getWorkOrderDetail(), appUser,
            		Utility.isTrue(mgrForm.getWorkflowProcessing(),true));

            mgrForm.setWorkOrderDetail(newData);

                loadItemDetailData(factory, mgrForm.getWorkOrderItemDetail(), mgrForm, appUser);

                WorkOrderItemData woiData = mgrForm.getWorkOrderItemDetail().getWorkOrderItem();
                woiData = woiUpdateProcess( woiData,
                                            getWorkOrderAssetCollection(mgrForm.getWorkOrderItemDetail().getAssetAssoc()),
                                            appUser.getUser());
                //refreshData(request);
                mgrForm.getWorkOrderItemDetail().setWorkOrderItem(woiData);

                addNewItemToWorkOrder(mgrForm.getWorkOrderDetail(), mgrForm.getWorkOrderItemDetail());

            // check if the Work Order Status has been changed to 'Completed'
            woD = mgrForm.getWorkOrderDetail().getWorkOrder();
            if (RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woD.getStatusCd()) && !"previous".equals(forceStatus)) {
                statusHistorySize = mgrForm.getWorkOrderDetail().getStatusHistory().size();
                Locale localeCd = ClwI18nUtil.getUserLocale(request);
                if (statusHistorySize > 1) {
                    if (!RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(((WorkOrderStatusHistData)mgrForm.
                          getWorkOrderDetail().
                          getStatusHistory().
                          get(statusHistorySize - 2)).getStatusCd())) { //the previous WO Status was not 'Completed'
                          sendWorkOrderCompleteNotificationEmail(mgrForm.getWorkOrderDetail(),
                                                                 woD.getBusEntityId(),
                                                                 localeCd,
                                                                 userEjb,
                                                                 accountEjb,
                                                                 storeEjb);
                    }

                }
            }

            return getDetail(request, mgrForm);
        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

    }

    private static void sendWorkOrderCompleteNotificationEmail(WorkOrderDetailView woDView,
                                                               int siteId,
                                                               Locale localeCd,
                                                               User userEjb,
                                                               Account accountEjb,
                                                               Store storeEjb) throws Exception {
        AccountData account = accountEjb.getAccountForSite(siteId);
        int storeId = storeEjb.getStoreIdByAccount(account.getAccountId());
        StoreData store = storeEjb.getStore(storeId);

        UserDataVector users = userEjb.getAllActiveUsers(siteId, User.ORDER_BY_ID);
        Iterator usersIt = users.iterator();
        UserData userD;
        UserRightsTool urt;
        while (usersIt.hasNext()) {
            userD = (UserData)usersIt.next();
            urt = new UserRightsTool(userD);

            if (!urt.getWorkOrderCompletedNotification()) {
                usersIt.remove();
            }
        }

        if (!users.isEmpty()) {
            String lineSeparator = System.getProperty("line.separator");
            byte[] outbytes = null;
            ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
            WorkOrderUtil woUtil = new WorkOrderUtil();
            if (woUtil.writeWoPdfToStream(woDView, pdfout, localeCd)) {
                outbytes = pdfout.toByteArray();
            }
            WorkOrderData woD = woDView.getWorkOrder();
            String name = "Work Order";
            if (woD != null) {
                name += " " + woDView.getWorkOrder().getWorkOrderNum();
            }
            String fileName = name + ".pdf";

            StringBuffer automatedMessage = new StringBuffer();
            automatedMessage.append("************************************************************************************************")
                            .append(lineSeparator)
                            .append("This is an automated email.  Do not reply to this email.")
                            .append(lineSeparator)
                            .append(lineSeparator)
                            .append("This Work Order status has been changed to 'Completed'.")
                            .append(lineSeparator);
            if (woD != null && account != null && store != null) {
                automatedMessage.append("Attached please find, Work order: ")
                                .append(woD.getWorkOrderNum())
                                .append(" (from Store: ")
                                .append(store.getBusEntity().getShortDesc())
                                .append("/Account: ")
                                .append(account.getBusEntity().getShortDesc())
                                .append(")");
            } else {
                automatedMessage.append("Attached please find, Work order");
            }
            automatedMessage.append(" for your review.")
                            .append(lineSeparator)
                            .append("You will need Adobe Acrobat Reader to open the attached file.")
                            .append(lineSeparator)
                            .append("Please respond as appropriate.")
                            .append(lineSeparator)
                            .append(lineSeparator)
                            .append("Thank you.")
                            .append(lineSeparator)
                            .append("************************************************************************************************")
                            .append(lineSeparator);

            String emailFromAddress = "";
            if (store != null && store.getPrimaryEmail() != null) {
                emailFromAddress = store.getPrimaryEmail().getEmailAddress();
            }
            UserInfoData userInfo;
            EventEmailDataView eventEmail;
            ArrayList attachArray;
            FileAttach pdf;
            String emailToAddress;
            usersIt = users.iterator();

            while (usersIt.hasNext()) {
                userD = (UserData)usersIt.next();
                userInfo = userEjb.getUserContactForNotification(userD.getUserId());

                emailToAddress = null;
                if (userInfo.getEmailData() != null) {
                    emailToAddress = userInfo.getEmailData().getEmailAddress();
                }

                if (Utility.isSet(emailToAddress)) {
                    eventEmail = new EventEmailDataView();

                    eventEmail.setFromAddress(emailFromAddress);
                    eventEmail.setToAddress(emailToAddress);
                    eventEmail.setSubject(name);
                    eventEmail.setText(automatedMessage.toString());
                    eventEmail.setEmailStatusCd(Event.STATUS_READY);

                    attachArray = new ArrayList();

                    if (outbytes != null ) {
                        pdf = new FileAttach(fileName, outbytes, "application/pdf", outbytes.length);
                        attachArray.add(pdf);
                    }
                    eventEmail.setAttachments((FileAttach[]) attachArray.toArray(new FileAttach[attachArray.size()]));
                    new ApplicationsEmailTool().createEvent(eventEmail);
                }
            }

        }
    }

    private static void updateItemizedServiceInfo(  WorkOrder wrkEjb,
                                                    WorkOrderDetailDataVector newITD,
                                                    WorkOrderDetailDataVector oldITD,
                                                    CleanwiseUser appUser) throws Exception {

        WorkOrderDetailDataVector update = new WorkOrderDetailDataVector();
        WorkOrderDetailDataVector insert = new WorkOrderDetailDataVector();
        WorkOrderDetailDataVector delete = new WorkOrderDetailDataVector();

        int sizeOld = oldITD.size();
        int sizeNew = newITD.size();
        WorkOrderDetailData currentLineOld;
        WorkOrderDetailData currentLineNew;

        //find lines to delete
        for (int i = 0; i < sizeOld; i++) {
            currentLineOld = (WorkOrderDetailData)oldITD.get(i);
            if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineOld.getStatusCd())) {
                for (int j = 0; j < sizeNew; j++) {
                    currentLineNew = (WorkOrderDetailData)newITD.get(j);
                    if (currentLineOld.getLineNum() == currentLineNew.getLineNum()) {
                        if (RefCodeNames.STATUS_CD.INACTIVE.equals(currentLineNew.getStatusCd())) {

                            currentLineOld.setStatusCd(RefCodeNames.STATUS_CD.INACTIVE);
                            currentLineOld.setModDate(new Date());
                            currentLineOld.setModBy(appUser.getUserName());
                            delete.add(currentLineOld);
                        }
                        break;
                    }
                }
            }
        }

        //find lines to insert
        for (int i = sizeOld; i < sizeNew; i++) {
            currentLineNew = (WorkOrderDetailData)newITD.get(i);

            if ( RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineNew.getStatusCd()) &&
                 (!RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE.equals(currentLineNew.getPaymentTypeCd()) ||
                   BigDecimal.valueOf(0.00).compareTo(currentLineNew.getPartPrice()) != 0 ||
                   BigDecimal.valueOf(0.00).compareTo(currentLineNew.getLabor()) != 0 ||
                   BigDecimal.valueOf(0.00).compareTo(currentLineNew.getTravel()) != 0 ||
                   currentLineNew.getQuantity() != 0 ||
                   !"".equals(currentLineNew.getShortDesc()) ||
                   !"".equals(currentLineNew.getPartNumber()) ||
                   !"".equals(currentLineNew.getComments())  )) {
                insert.add(currentLineNew);
                // delete the line from the 'newITD'
            }
        }

        //find lines to update
        for (int i = 0; i < sizeOld; i++) {
            currentLineOld = (WorkOrderDetailData)oldITD.get(i);
            if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineOld.getStatusCd())) {
                for (int j = 0; j < sizeNew; j++) {
                    currentLineNew = (WorkOrderDetailData)newITD.get(j);
                    if (currentLineOld.getLineNum() == currentLineNew.getLineNum() &&
                        RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineNew.getStatusCd())) {

                        if ( (currentLineOld.getPaymentTypeCd() == null && currentLineNew.getPaymentTypeCd() != null) ||
                             (currentLineOld.getPaymentTypeCd() != null &&
                             !currentLineOld.getPaymentTypeCd().equals(currentLineNew.getPaymentTypeCd())) ||

                             (currentLineOld.getPartNumber() == null && currentLineNew.getPartNumber() != null) ||
                             (currentLineOld.getPartNumber() != null &&
                             !currentLineOld.getPartNumber().equals(currentLineNew.getPartNumber())) ||

                             (currentLineOld.getPartPrice() == null && currentLineNew.getPartPrice() != null) ||
                             (currentLineOld.getPartPrice() != null &&
                            		 (Utility.compareBigDecimal(currentLineOld.getPartPrice(), currentLineNew.getPartPrice())==1))||

                             (currentLineOld.getLabor() == null && currentLineNew.getLabor() != null) ||
                             (currentLineOld.getLabor() != null &&
                            		 (Utility.compareBigDecimal(currentLineOld.getLabor(), currentLineNew.getLabor())==1))||

                             (currentLineOld.getTravel() == null && currentLineNew.getTravel() != null) ||
                             (currentLineOld.getTravel() != null &&
                            		 (Utility.compareBigDecimal(currentLineOld.getTravel(), currentLineNew.getTravel())==1))||

                              currentLineOld.getQuantity() != currentLineNew.getQuantity() ||

                             (currentLineOld.getShortDesc() == null && currentLineNew.getShortDesc() != null) ||
                             (currentLineOld.getShortDesc() != null &&
                             !currentLineOld.getShortDesc().equals(currentLineNew.getShortDesc())) ||

                             (currentLineOld.getComments() == null && (currentLineNew.getComments() != null && !"".equals(currentLineNew.getComments())) ) ||
                             (currentLineOld.getComments() != null &&
                             !currentLineOld.getComments().equals(currentLineNew.getComments()))   ) {

                            //update.add(currentLineNew);
                            currentLineOld.setStatusCd(RefCodeNames.STATUS_CD.INACTIVE);
                            currentLineOld.setModDate(new Date());
                            currentLineOld.setModBy(appUser.getUserName());
                            delete.add(currentLineOld);

                            currentLineNew.setWorkOrderDetailId(0);
                            currentLineNew.setAddBy(appUser.getUserName());
                            currentLineNew.setModBy(appUser.getUserName());
                            currentLineNew.setAddDate(new Date());
                            currentLineNew.setModDate(new Date());
                            insert.add(currentLineNew);
                        }
                        break;
                    }
                }
            }
        }
        if (!delete.isEmpty()) {
            wrkEjb.updateItemizedServiceLines(delete);
        }
        //if (!update.isEmpty()) {
        //    wrkEjb.updateItemizedServiceLines(update);
        //}
        if (!insert.isEmpty()) {
            wrkEjb.insertItemizedServiceLines(insert);
        }
    }

    private static void loadItemDetailData( APIAccess factory,
                                            WorkOrderItemDetailView workOrderItemDetail,
                                            UserWorkOrderDetailMgrForm mgrForm,
                                            CleanwiseUser appUser) throws Exception {

        WorkOrderItemData newWrkItemData = WorkOrderItemData.createValue();

        if (mgrForm.getBusEntityId() <= 0) {
            newWrkItemData.setBusEntityId(appUser.getSite().getSiteId());
        } else {
            newWrkItemData.setBusEntityId(mgrForm.getBusEntityId());
        }
        if (mgrForm.getWorkOrderId() <= 0) {
            newWrkItemData.setWorkOrderId(mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId());
        } else {
            newWrkItemData.setWorkOrderId(mgrForm.getWorkOrderId());
        }

        newWrkItemData.setWorkOrderItemId(mgrForm.getWorkOrderItemId());

        if (Utility.isSet(mgrForm.getItemStatusCd())) {
            newWrkItemData.setStatusCd(mgrForm.getItemStatusCd());
        } else {
            newWrkItemData.setStatusCd(RefCodeNames.WORK_ORDER_ITEM_STATUS_CD.ACTIVE);
        }

        newWrkItemData.setShortDesc(mgrForm.getItemShortDesc());
        newWrkItemData.setLongDesc(mgrForm.getItemLongDesc());

        if (Utility.isSet(mgrForm.getItemQuotedLabor())) {
            try {
                newWrkItemData.setEstimateLabor(new BigDecimal(mgrForm.getItemQuotedLabor()));
            } catch (NumberFormatException e) {
                newWrkItemData.setEstimateLabor(null);
            }
        } else {
            newWrkItemData.setEstimateLabor(null);
        }

        if (Utility.isSet(mgrForm.getItemQuotedPart())) {
            try {
                BigDecimal cost = new BigDecimal(mgrForm.getItemQuotedPart());
                newWrkItemData.setEstimatePart(cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            } catch (NumberFormatException e) {
                newWrkItemData.setEstimatePart(null);
            }
        } else {
            newWrkItemData.setEstimatePart(null);
        }

        if (Utility.isSet(mgrForm.getItemActualLabor())) {
            try {
                newWrkItemData.setActualLabor(new BigDecimal(mgrForm.getItemActualLabor()));
            } catch (NumberFormatException e) {
                newWrkItemData.setActualLabor(null);
            }
        } else {
            newWrkItemData.setActualLabor(null);
        }


        if (Utility.isSet(mgrForm.getItemActualPart())) {
            try {
                BigDecimal cost = new BigDecimal(mgrForm.getItemActualPart());
                newWrkItemData.setActualPart(cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            } catch (NumberFormatException e) {
                newWrkItemData.setActualPart(null);
            }
        } else {
            newWrkItemData.setActualPart(null);
        }

        newWrkItemData.setAddBy(mgrForm.getItemAddBy());
        newWrkItemData.setAddDate(mgrForm.getItemAddDate());
        newWrkItemData.setModBy(mgrForm.getItemModBy());
        newWrkItemData.setModDate(mgrForm.getItemModDate());

        int assetId;
        if (Utility.isSet(mgrForm.getActiveAssetIdStr())) {
            try {
                assetId = Integer.parseInt(mgrForm.getActiveAssetIdStr());
                int assignedAssetId = WorkOrderUtil.getAssignedAssetId(workOrderItemDetail.getAssetAssoc());

                if (assignedAssetId > 0 || assetId <= 0) {
                    if (assignedAssetId != assetId) {
                        removeAssoc(mgrForm.getWorkOrderItemDetail().getAssetAssoc(), assignedAssetId);
                    }
                }

                if (assetId > 0 && assignedAssetId != assetId) {
                    setAssetToAssoc(assetId, workOrderItemDetail.getAssetAssoc(), newWrkItemData);
                }
            } catch (NumberFormatException e) {
                error(e.getMessage(), e);
            }
        } else {
            int assignedAssetId = WorkOrderUtil.getAssignedAssetId(workOrderItemDetail.getAssetAssoc());
            if (assignedAssetId > 0) {
                removeAssoc(mgrForm.getWorkOrderItemDetail().getAssetAssoc(), assignedAssetId);
            }
            mgrForm.setActiveWarrantyIdStr("");
        }

        int warrantyId;
        if (Utility.isSet(mgrForm.getActiveWarrantyIdStr())) {
            try {
                warrantyId = Integer.parseInt(mgrForm.getActiveWarrantyIdStr());
                newWrkItemData.setWarrantyId(warrantyId);
                Warranty warrantyEjb = factory.getWarrantyAPI();
                workOrderItemDetail.setWarranty(warrantyEjb.getWarrantyData(warrantyId));

            } catch (NumberFormatException e) {
                error(e.getMessage(),e);
            }
        } else {
            newWrkItemData.setWarrantyId(0);
        }

        try {
            newWrkItemData.setSequence(Integer.parseInt(mgrForm.getItemSequence()));
        } catch (NumberFormatException e) {
            newWrkItemData.setSequence(0);
        }

        workOrderItemDetail.setWorkOrderItem(newWrkItemData);
    }

    private static ActionErrors checkUserRight(HttpServletRequest request, CleanwiseUser appUser, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        String statusCd = mgrForm.getWorkOrderDetail().getWorkOrder().getStatusCd();

        if(!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)){
            if(RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(statusCd)){
                ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
                throw new Exception("Unauthorized access");

            }
        }
        return ae;
    }

private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        String dateFormat = ClwI18nUtil.getCountryDateFormat(request);
        SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, ClwI18nUtil.getUserLocale(request));
        Date date1970 = new Date(0);

        BigDecimalValidator bigDecimalValidator;
        IntegerValidator intValidator;

        Date tmpDate;
        if (dateFormat == null) {
            dateFormat = "MM/dd/yyyy";
        }

        if (mgrForm.getWorkOrderDetail() == null) {
            throw new Exception("Work Order Detail Data can't be null");
        }

        if (mgrForm.getWorkOrderDetail().getWorkOrder() == null) {
            throw new Exception("Work Order Data can't be null");
        }

        if (Utility.isSet(mgrForm.getActualStartDate())) {
            try {
                tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getActualStartDate());
                if (date1970.after(tmpDate)) {
                    throw new Exception();
                }
                mgrForm.setActualStartDate(sdFormat.format(tmpDate));
            } catch (Exception e) {
                String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualStartDate");
                if (property == null) {
                    property = "ActualStartDate";
                }
                String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getActualStartDate()}, true);
                ae.add("actualStartDate", new ActionError("error.simpleError", mess));
            }
        }

        if (Utility.isSet(mgrForm.getActualFinishDate())) {
            try {
                tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getActualFinishDate());
                if (date1970.after(tmpDate)) {
                    throw new Exception();
                }
                mgrForm.setActualFinishDate(sdFormat.format(tmpDate));
            } catch (Exception e) {
                String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualFinishDate");
                if (property == null) {
                    property = "ActualFinishDate";
                }
                String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getActualFinishDate()}, true);
                ae.add("actualFinishDate", new ActionError("error.simpleError", mess));
            }
        }

        if (Utility.isSet(mgrForm.getQuotedStartDate())) {
            try {
                tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getQuotedStartDate());
                if (date1970.after(tmpDate)) {
                    throw new Exception();
                }
                mgrForm.setQuotedStartDate(sdFormat.format(tmpDate));
            } catch (Exception e) {
                String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.quotedStartDate");
                if (property == null) {
                    property = "QuotedStartDate";
                }
                String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getQuotedStartDate()}, true);
                ae.add("quotedStartDate", new ActionError("error.simpleError", mess));
            }
        }

        if (Utility.isSet(mgrForm.getQuotedFinishDate())) {
            try {
                tmpDate = ClwI18nUtil.parseDateInp(request, mgrForm.getQuotedFinishDate());
                if (date1970.after(tmpDate)) {
                    throw new Exception();
                }
                mgrForm.setQuotedFinishDate(sdFormat.format(tmpDate));
            } catch (Exception e) {
                String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.quotedFinishDate");
                if (property == null) {
                    property = "QuotedFinishDate";
                }
                String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getQuotedFinishDate()}, true);
                ae.add("quotedFinishDate", new ActionError("error.simpleError", mess));
            }
        }

        if (mgrForm.getItemizedServiceStr() != null && mgrForm.getItemizedService() != null) {
            WorkOrderItemizedServiceStrVector woISSV = mgrForm.getItemizedServiceStr();
            WorkOrderDetailDataVector woDDV = mgrForm.getItemizedService();

            WorkOrderItemizedServiceStr woISS;
            WorkOrderDetailData woDD;
            int size = woDDV.size();
            for (int i = 0; i < size; i++) {
                woISS = (WorkOrderItemizedServiceStr)woISSV.get(i);
                woDD = (WorkOrderDetailData)woDDV.get(i);
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(woDD.getStatusCd())) {
                    final int lineNum = woDD.getLineNum();

					intValidator = Validator.getIntegerInstance(request, woISS
							.getQuantity(), "userWorkOrder.text.Quantity",
							"Quantity");
					if (intValidator.isNumberFormatValid()) {
						if (intValidator.isPositiveValue()) {
							woDD.setQuantity(intValidator.getValue());
						} else {
							intValidator.addNegativeErrMsg(ae, lineNum);
						}
					} else {
						woDD.setQuantity(Integer.parseInt("0"));
						intValidator.addNumberFormatErrMsg(ae, lineNum);
					}

					bigDecimalValidator = Validator.getBigDecimalInstance(
							request, woISS.getPartPrice(),
							"userWorkOrder.text.PriceEach", "Price Each");
					if (bigDecimalValidator.isNumberFormatValid()) {
						if (bigDecimalValidator.isPositiveValue()) {
							woDD.setPartPrice(bigDecimalValidator.getValue());
						} else {
							bigDecimalValidator.addNegativeErrMsg(ae, lineNum);
						}
					} else {
						woDD.setPartPrice(new BigDecimal("0.00"));
						bigDecimalValidator.addNumberFormatErrMsg(ae, lineNum);
					}

					bigDecimalValidator = Validator.getBigDecimalInstance(
							request, woISS.getLabor(),
							"userWorkOrder.text.Labor", "Labor");
					if (bigDecimalValidator.isNumberFormatValid()) {
						if (bigDecimalValidator.isPositiveValue()) {
							woDD.setLabor(bigDecimalValidator.getValue());
						} else {
							bigDecimalValidator.addNegativeErrMsg(ae, lineNum);
						}
					} else {
						woDD.setLabor(new BigDecimal("0.00"));
						bigDecimalValidator.addNumberFormatErrMsg(ae, lineNum);
					}

					bigDecimalValidator = Validator.getBigDecimalInstance(
							request, woISS.getTravel(),
							"userWorkOrder.text.Travel", "Travel");
					if (bigDecimalValidator.isNumberFormatValid()) {
						if (bigDecimalValidator.isPositiveValue()) {
							woDD.setTravel(bigDecimalValidator.getValue());
						} else {
							bigDecimalValidator.addNegativeErrMsg(ae, lineNum);
						}
					} else {
						woDD.setTravel(new BigDecimal("0.00"));
						bigDecimalValidator.addNumberFormatErrMsg(ae, lineNum);
					}

                }
            }
        }

        if (!Utility.isSet(mgrForm.getWorkOrderPoNum()) && mgrForm.getWorkOrderPoNumberIsRequired()) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.PONumber");
            if (field == null) {
                field = "Work Order PO Number";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("WorkOrderPONumber", new ActionError("error.simpleGenericError", mess));
        }

        // NTE field
        if(!Utility.isSet(mgrForm.getNteStr())) {
        	mgrForm.setNte(null);
        } else {
			bigDecimalValidator = Validator.getBigDecimalInstance(request, mgrForm.getNteStr(),
					"userWorkOrder.text.NTE", "Nte");
            if(bigDecimalValidator.isNumberFormatValid())
            {
            	if(bigDecimalValidator.isPositiveValue())
            	{
            		mgrForm.setNte( bigDecimalValidator.getValue() );
            	}
            	else
            	{
            		bigDecimalValidator.addNegativeErrorMessageToActionErrors(ae);
				}
			} else {
	        	final Object[] parameters = new Object[] {"$," + '\u20AC' + "," + '\u00A5' + ", ..."};
				bigDecimalValidator.addNumberFormatErrorMessageToActionErrors(ae, "userWorkOrder.errors.wrongNteNumberFormat", parameters);
			}
        }

        if (Utility.isSet(mgrForm.getCostCenterId())) {
            try {
                Integer.parseInt(mgrForm.getCostCenterId());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        if (!Utility.isSet(mgrForm.getPriority())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.priority");
            if (field == null) {
                field = "Priority";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Priority", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getShortDesc())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderName");
            if (field == null) {
                field = "Name";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Name", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getStatusCd())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.statusCd");
            if (field == null) {
                field = "Status Code";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Status Code", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getLongDesc())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.longDesc");
            if (field == null) {
                field = "Requested Service Detail";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Requested Service Detail", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getTypeCd())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type");
            if (field == null) {
                field = "Type Code";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Type Code", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactFirstName().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.firstName");
            if (field == null) {
                field = "First Name";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("firstName", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactLastName().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.lastName");
            if (field == null) {
                field = "Last Name";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("lastName", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactAddress1().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.address1");
            if (field == null) {
                field = "Address 1";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("address1", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactCountry().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.conutry");
            if (field == null) {
                field = "Country";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("country", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactCity().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.city");
            if (field == null) {
                field = "City";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("city", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactState().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.state");
            if (field == null) {
                field = "State";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("state", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactPhoneNum().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.phone");
            if (field == null) {
                field = "Phone";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("phone", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getUserContactPostalCode().getValue())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.zip");
            if (field == null) {
                field = "Zip";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("zip", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getActionCode())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.action");
            if (field == null) {
                field = "Action";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information") : mess;
            ae.add("Action", new ActionError("error.simpleGenericError", mess));
        } else {
            if(RefCodeNames.WORK_ORDER_ACTION_CD.SERVICE.equals(mgrForm.getActionCode())){
                if(!Utility.isSet(mgrForm.getServiceProviderIdStr())){
                    String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider");
                    if (field == null) {
                        field = "Service Provider";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
                    mess = mess == null ? ("Field  " + field + " requires information:") : mess;
                    ae.add("ServiceProvider", new ActionError("error.simpleGenericError", mess));
                }
            }
        }
        return ae;
    }


    public static ActionErrors getDetail(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae;

        try {

            int workOrderItemId = getWorkOrderItemIdFromRequest(request, mgrForm);
            ae = init(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            mgrForm = (UserWorkOrderItemMgrForm) session.getAttribute(USER_WORK_ORDER_ITEM_MGR_FORM);

            WorkOrderItemDetailView woiDet = null;
            if (workOrderItemId > 0) {
                woiDet = getWoiDetail(mgrForm.getAllWorkOrderItems(), workOrderItemId);
            }

            uploadDetailData(woiDet, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
        session.setAttribute(USER_WORK_ORDER_ITEM_MGR_FORM, mgrForm);

        return ae;
    }

    public static void uploadDetailData(WorkOrderItemDetailView woiDet, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        mgrForm.setWorkOrderItemDetail(woiDet);
        if (woiDet != null && woiDet.getWorkOrderItem() != null) {

            WorkOrder wrkejb = APIAccess.getAPIAccess().getWorkOrderAPI();

            WorkOrderItemData woi = woiDet.getWorkOrderItem();
            BigDecimal estimateTotalCost = null;
            BigDecimal actualTotalCost = null;
            mgrForm.setWorkOrderItemId(woi.getWorkOrderItemId());
            mgrForm.setBusEntityId(woi.getBusEntityId());
            mgrForm.setWarrantyId(woi.getWarrantyId());
            mgrForm.setShortDesc(Utility.strNN(woi.getShortDesc()));
            mgrForm.setLongDesc(Utility.strNN(woi.getLongDesc()));
            mgrForm.setSequence(String.valueOf(woi.getSequence()));
            mgrForm.setStatusCd(Utility.strNN(woi.getStatusCd()));

            String estimateLaborStr = "";
            if (woi.getEstimateLabor() != null) {
                BigDecimal estLabor = woi.getEstimateLabor();
                estimateTotalCost   = Utility.addAmt(estimateTotalCost,estLabor);
                estimateLaborStr    = estLabor.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setEstimatedLabor(estimateLaborStr);

            String estimatePartStr = "";
            if (woi.getEstimatePart() != null) {
                BigDecimal estPart = woi.getEstimatePart();
                estimateTotalCost  = Utility.addAmt(estimateTotalCost,estPart);
                estimatePartStr    = estPart.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setEstimatedPart(estimatePartStr);

            String estimateTotalCostStr = "";
            if (estimateTotalCost != null) {
                estimateTotalCostStr = estimateTotalCost.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setEstimateTotalCost(estimateTotalCostStr);

            String actualLaborStr = "";
            if (woi.getActualLabor() != null) {
                BigDecimal actualLabor = woi.getActualLabor();
                actualTotalCost   = Utility.addAmt(actualTotalCost,actualLabor);
                actualLaborStr    = actualLabor.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setActualLabor(actualLaborStr);

            String actualPartStr = "";
            if (woi.getActualPart() != null) {
                BigDecimal actualPart = woi.getActualPart();
                actualTotalCost  = Utility.addAmt(actualTotalCost,actualPart);
                actualPartStr    = actualPart.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setActualPart(actualPartStr);

            String actualTotalCostStr = "";
            if (actualTotalCost != null) {
                actualTotalCostStr = actualTotalCost.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            mgrForm.setActualTotalCost(actualTotalCostStr);

            int assetId = WorkOrderUtil.getAssignedAssetId(woiDet.getAssetAssoc());
            if(assetId>0) {
                mgrForm.setActiveAssetIdStr(String.valueOf(assetId));
            }

            if (Utility.isSet(mgrForm.getActiveAssetIdStr())) {

                AssetData asset = getAssetData(Integer.parseInt(mgrForm.getActiveAssetIdStr()), false);
                mgrForm.setActiveAsset(asset);

                buildDisplayAssets(mgrForm);

                WarrantyDataVector warranties = new WarrantyDataVector();
                if (mgrForm.getActiveAsset() != null) {
                    IdVector assetIds = new IdVector();
                    assetIds.add(new Integer(mgrForm.getActiveAsset().getAssetId()));
                    warranties = wrkejb.getWorkOrderWarrantiesForAssets(assetIds,RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);
                }
                mgrForm.setWarrantyForActiveAsset(warranties);

            } else {
                mgrForm.setActiveAssetIdStr(EMPTY);
                mgrForm.setActiveAsset(null);
            }

            if (woiDet.getWarranty() != null) {
                mgrForm.setActiveWarrantyIdStr(String.valueOf(woiDet.getWarranty().getWarrantyId()));
            } else {
                mgrForm.setActiveWarrantyIdStr(EMPTY);
            }

            buildDisplayWarranties(mgrForm);

            mgrForm.setAddBy(woi.getAddBy());
            mgrForm.setAddDate(woi.getAddDate());
            mgrForm.setModBy(woi.getModBy());
            mgrForm.setModDate(woi.getModDate());
        }
    }

    private static void buildDisplayWarranties(UserWorkOrderItemMgrForm mgrForm) {
        if (mgrForm.getWorkOrderItemDetail().getWarranty() != null) {
            int assignedAssetId = WorkOrderUtil.getAssignedAssetId(mgrForm.getWorkOrderItemDetail().getAssetAssoc());
            if (mgrForm.getActiveAsset() != null && assignedAssetId == mgrForm.getActiveAsset().getAssetId()) {
                IdVector warrantyIds = Utility.toIdVector(mgrForm.getWarrantyForActiveAsset());
                Integer assignedWarrId = new Integer(mgrForm.getWorkOrderItemDetail().getWarranty().getWarrantyId());
                if (!warrantyIds.contains(assignedWarrId)) {
                    mgrForm.getWarrantyForActiveAsset().add(mgrForm.getWorkOrderItemDetail().getWarranty());
                }
            }
        }
    }

    private static void buildDisplayWarranties(UserWorkOrderDetailMgrForm mgrForm) {
        if (mgrForm.getWorkOrderItemDetail().getWarranty() != null) {
            int assignedAssetId = WorkOrderUtil.getAssignedAssetId(mgrForm.getWorkOrderItemDetail().getAssetAssoc());
            if (mgrForm.getActiveAsset() != null && assignedAssetId == mgrForm.getActiveAsset().getAssetId()) {
                IdVector warrantyIds = Utility.toIdVector(mgrForm.getWarrantyForActiveAsset());
                Integer assignedWarrId = new Integer(mgrForm.getWorkOrderItemDetail().getWarranty().getWarrantyId());
                if (!warrantyIds.contains(assignedWarrId)) {
                    mgrForm.getWarrantyForActiveAsset().add(mgrForm.getWorkOrderItemDetail().getWarranty());
                }
            }
        }
    }

    private static void buildDisplayAssets(UserWorkOrderItemMgrForm mgrForm) throws Exception {

        int assetId = WorkOrderUtil.getAssignedAssetId(mgrForm.getWorkOrderItemDetail().getAssetAssoc());

        if (assetId > 0) {

            AssetData assignedAsset;
            if (mgrForm.getActiveAsset() != null
                    && mgrForm.getActiveAsset().getAssetId() == assetId) {
                assignedAsset = mgrForm.getActiveAsset();
            } else {
                assignedAsset = getAssetData(assetId, true);
            }

            if (assignedAsset != null) {
                int categoryId = assignedAsset.getParentId();
                AssetData category = null;
                if (mgrForm.getActiveAssetCategory() != null &&
                        mgrForm.getActiveAssetCategory().getAssetId() == categoryId) {
                    category = mgrForm.getActiveAssetCategory();
                } else if (categoryId > 0) {
                    category = getAssetData(categoryId, true);
                }

                IdVector assetIds = Utility.toIdVector(mgrForm.getAllAssets());
                Integer assignedAssetId = new Integer(assignedAsset.getAssetId());
                if (!assetIds.contains(assignedAssetId)) {
                    mgrForm.getAllAssets().add(assignedAsset);
                }

                if (category != null) {
                    IdVector categoryIds = Utility.toIdVector(mgrForm.getAllAssets());
                    Integer assignedCategorId = new Integer(assignedAsset.getAssetId());
                    if (!categoryIds.contains(assignedCategorId)) {
                        mgrForm.getAssetCategories().add(category);
                    }
                }

                PairViewVector assetGroupMap = groupByCategory(mgrForm.getAssetCategories(), mgrForm.getAllAssets());
                mgrForm.setAssetGroups(assetGroupMap);
            }
        }
    }

    private static void buildDisplayAssets(UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        int assetId = WorkOrderUtil.getAssignedAssetId(mgrForm.getWorkOrderItemDetail().getAssetAssoc());

        if (assetId > 0) {

            AssetData assignedAsset;
            if (mgrForm.getActiveAsset() != null
                    && mgrForm.getActiveAsset().getAssetId() == assetId) {
                assignedAsset = mgrForm.getActiveAsset();
            } else {
                assignedAsset = getAssetData(assetId, true);
            }

            if (assignedAsset != null) {
                int categoryId = assignedAsset.getParentId();
                AssetData category = null;
                if (mgrForm.getActiveAssetCategory() != null &&
                        mgrForm.getActiveAssetCategory().getAssetId() == categoryId) {
                    category = mgrForm.getActiveAssetCategory();
                } else if (categoryId > 0) {
                    category = getAssetData(categoryId, true);
                }

                IdVector assetIds = Utility.toIdVector(mgrForm.getAllAssets());
                Integer assignedAssetId = new Integer(assignedAsset.getAssetId());
                if (!assetIds.contains(assignedAssetId)) {
                    mgrForm.getAllAssets().add(assignedAsset);
                }

                if (category != null) {
                    IdVector categoryIds = Utility.toIdVector(mgrForm.getAllAssets());
                    Integer assignedCategorId = new Integer(assignedAsset.getAssetId());
                    if (!categoryIds.contains(assignedCategorId)) {
                        mgrForm.getAssetCategories().add(category);
                    }
                }

                PairViewVector assetGroupMap = groupByCategory(mgrForm.getAssetCategories(), mgrForm.getAllAssets());
                mgrForm.setAssetGroups(assetGroupMap);
            }
        }
    }

    private static WorkOrderItemDetailView getWoiDetail(WorkOrderItemDetailViewVector allWorkOrderItems, int workOrderItemId) {
        if (allWorkOrderItems != null) {
            Iterator it = allWorkOrderItems.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView woiDet = (WorkOrderItemDetailView) it.next();
                if (woiDet.getWorkOrderItem() != null &&
                        woiDet.getWorkOrderItem().getWorkOrderItemId() == workOrderItemId) {
                    return woiDet;
                }
            }
        }
        return null;
    }

    private static int getWorkOrderItemIdFromRequest(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) {

        int id = getIdFromRequest(request, "workOrderItemId");

        if (id <= 0 && mgrForm != null &&
                mgrForm.getWorkOrderItemDetail() != null &&
                mgrForm.getWorkOrderItemDetail().getWorkOrderItem() != null &&
                mgrForm.getWorkOrderItemDetail().getWorkOrderItem().getWorkOrderItemId() > 0) {
            return mgrForm.getWorkOrderItemDetail().getWorkOrderItem().getWorkOrderItemId();
        } else {
            return id;
        }
    }

    public static ActionErrors cancelWorkOrder(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        try {

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            ae = checkUserRight(request, appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();
            Event eventEjb   = factory.getEventAPI();
            int workOrderId = mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId();

            wrkEjb.updateStatus(workOrderId, RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED, appUser.getUser().getUserName());

            // send an email to the contact person (if emailAddress exist) and WorkOrder Status is 'Pending Approval'
            WorkOrderData workOrder = mgrForm.getWorkOrderDetail().getWorkOrder();
            if ((workOrder != null) && (RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(workOrder.getStatusCd()))) {
                if (Utility.isSet(mgrForm.getUserContactEmail().getValue())) {

//                    com.cleanwise.service.api.eventsys.EventData eventData =
//                    new com.cleanwise.service.api.eventsys.EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//
//                    eventData = eventEjb.addEventToDB(eventData);
                    EventEmailDataView eventEmailData = new EventEmailDataView();

                    String modAddBy = "WorkOrderCancel";
                    if (appUser != null) {
                        if (Utility.isSet(appUser.getUserName())) {
                            modAddBy = appUser.getUserName();
                        }
                    }

                    String emailContact = mgrForm.getUserContactEmail().getValue();
                    StringBuffer subject = new StringBuffer("Work Order: ");
                    StringBuffer message = new StringBuffer("The Work Order with Number ");

                    String workOrderNum = mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderNum();
                    String workOrderShortDesc = mgrForm.getWorkOrderDetail().getWorkOrder().getShortDesc();

                    subject.append(workOrderNum);
                    subject.append(" (");
                    subject.append(workOrderShortDesc);
                    subject.append(") cancelled");

                    message.append(workOrderNum);
                    message.append(" (");
                    message.append(workOrderShortDesc);
                    message.append(" ) has been cancelled by ");
                    message.append(mgrForm.getUserContactFirstName().getValue());
                    message.append(" ");
                    message.append(mgrForm.getUserContactLastName().getValue());
                    message.append(" at ");

                    Date currentDate = new Date();
                    String timeCancelled = new SimpleDateFormat("MM/dd/yyyy hh:mm").format(currentDate);
                    message.append(timeCancelled);

                    eventEmailData.setEventEmailId(0);
                    eventEmailData.setToAddress(emailContact);
                    eventEmailData.setFromAddress("ResourceCenter@cleanwise.com");
                    eventEmailData.setSubject(subject.toString());
                    eventEmailData.setText(message.toString());
//                    eventEmailData.setEventId(eventData.getEventId());
                    eventEmailData.setEmailStatusCd(Event.STATUS_READY);
                    eventEmailData.setModBy(modAddBy);
                    eventEmailData.setAddBy(modAddBy);
//                    eventEjb.addEventEmail(eventEmailData);
                    EventData eventData = Utility.createEventDataForEmail();
                    EventEmailView eev = new EventEmailView(eventData, eventEmailData);
                    eventEjb.addEventEmail(eev, modAddBy);
                }
            }
            return getDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public static ActionErrors completeWorkOrder(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        try {
            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            ae = checkUserRight(request,appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            ae = checkFormAttribute(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();

            loadDetailData(request, mgrForm.getWorkOrderDetail(), mgrForm, appUser);

            WorkOrderDetailView newData = wrkEjb.updateWorkOrderDetail(mgrForm.getWorkOrderDetail(), appUser.getUser());
            mgrForm.setWorkOrderDetail(newData);

            int workOrderId = mgrForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId();
            wrkEjb.updateStatus(workOrderId, RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED, appUser.getUser().getUserName());

            return getDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

    }

    public static ActionErrors rejectWorkOrder(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        try {
            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            ae = checkUserRight(request,appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            ae = checkFormAttribute(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();

            loadDetailData(request, mgrForm.getWorkOrderDetail(), mgrForm, appUser);

            mgrForm.getWorkOrderDetail().getWorkOrder().setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER);
            mgrForm.getWorkOrderDetail().setItemizedService(mgrForm.getItemizedService());
            WorkOrderDetailView newData = wrkEjb.updateWorkOrderDetail(mgrForm.getWorkOrderDetail(), appUser,
            		Utility.isTrue(mgrForm.getWorkflowProcessing(),true));

            return getDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    public static ActionErrors acceptWorkOrder(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        try {
            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            ae = checkUserRight(request,appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            ae = checkFormAttribute(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();

            loadDetailData(request, mgrForm.getWorkOrderDetail(), mgrForm, appUser);

            mgrForm.getWorkOrderDetail().getWorkOrder().setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER);
            mgrForm.getWorkOrderDetail().setItemizedService(mgrForm.getItemizedService());
            WorkOrderDetailView newData = wrkEjb.updateWorkOrderDetail(mgrForm.getWorkOrderDetail(), appUser,
            		Utility.isTrue(mgrForm.getWorkflowProcessing(),true));

            return getDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    private static ActionErrors checkFormForItemData(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) {
        ActionErrors ae  = new ActionErrors();
                if (!Utility.isSet(mgrForm.getItemShortDesc())) {
                    String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderItems.description");
                    if (field == null) {
                        field = "Requested Service";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
                    mess = (mess == null) ? ("Field  " + field + " requires information:") : mess;
                    ae.add("WorkOrderPONumber", new ActionError("error.simpleGenericError", mess));
                }
        return ae;
    }

    private static boolean checkItemDataNotEmpty(UserWorkOrderDetailMgrForm form) {

        String tmpStr = form.getActiveAssetIdStr();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        tmpStr = form.getActiveWarrantyIdStr();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        tmpStr = form.getItemShortDesc();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        tmpStr = form.getItemLongDesc();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        tmpStr = form.getItemQuotedLabor();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        tmpStr = form.getItemQuotedPart();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        tmpStr = form.getItemActualLabor();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }tmpStr = form.getItemActualPart();
        if (tmpStr != null && tmpStr.length() > 0) {
            return true;
        }
        return false;
    }

    public static ActionErrors updateWorkOrderItem(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {

        ActionErrors ae;

        try {

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            ae = checkUserRight(request, appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            ae = checkFormAttribute(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();
            UserWorkOrderDetailMgrForm detailForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

            loadDetailData(factory, mgrForm.getWorkOrderItemDetail(), mgrForm, appUser);

            WorkOrderItemData wrkData;
            if (detailForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId() > 0) {
                wrkData = woiUpdateProcess(mgrForm.getWorkOrderItemDetail().getWorkOrderItem(),
                        getWorkOrderAssetCollection(mgrForm.getWorkOrderItemDetail().getAssetAssoc()),
                        appUser.getUser());
                refreshData(request);
                mgrForm.getWorkOrderItemDetail().setWorkOrderItem(wrkData);
            } else {
                if (mgrForm.getWorkOrderItemDetail().getWorkOrderItem().getWorkOrderItemId() <= 0) {
                    int dbSeq = wrkEjb.getClwWorkOrderItemSeq();
                    mgrForm.getWorkOrderItemDetail().getWorkOrderItem().setWorkOrderItemId(dbSeq);
                }

                addNewItemToWorkOrder(detailForm.getWorkOrderDetail(), mgrForm.getWorkOrderItemDetail());

                session.setAttribute(USER_WORK_ORDER_ITEM_MGR_FORM, mgrForm);
                session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, detailForm);

                return ae;
            }

            return getDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    private static WorkOrderItemData woiUpdateProcess(WorkOrderItemData item,
                                                      WorkOrderAssetDataVector assetAssoc,
                                                      UserData user) throws Exception {

        APIAccess factory = APIAccess.getAPIAccess();
        WorkOrder wrkEjb  = factory.getWorkOrderAPI();

        return wrkEjb.updateWorkOrderItemData(item,
                assetAssoc,
                user);

    }

    private static ActionErrors checkUserRight(HttpServletRequest request, CleanwiseUser appUser, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        ae = checkUserRight(request,appUser, detForm);
        if (ae.size() > 0) {
            return ae;
        }

        return ae;
    }

    private static void addNewItemToWorkOrder(WorkOrderDetailView workOrderDetail, WorkOrderItemDetailView newItem) throws Exception {
        if(workOrderDetail!=null && newItem!=null ){
            WorkOrderItemDetailViewVector currItems = workOrderDetail.getWorkOrderItems();
            if(currItems==null){
                currItems= new WorkOrderItemDetailViewVector();
            }

            Iterator it = currItems.iterator();
            while(it.hasNext()){
                WorkOrderItemDetailView item = (WorkOrderItemDetailView) it.next();
                if((item.getWorkOrderItem().getWorkOrderItemId()>0) &&
                        (item.getWorkOrderItem().getWorkOrderItemId()==newItem.getWorkOrderItem().getWorkOrderItemId())){
                    it.remove();
                }
            }

            currItems.add(newItem);
        }

    }

    private static ActionErrors refreshData(HttpServletRequest request) throws Exception {
        ActionErrors ae;
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);
        ae = getDetail(request, detForm);
        if (ae.size() > 0) {
            throw new Exception("Detail data can't be refreshed");
        }
        return ae;
    }

    private static WorkOrderAssetDataVector getWorkOrderAssetCollection(WorkOrderAssetViewVector assetAssoc) {
        WorkOrderAssetDataVector result = new WorkOrderAssetDataVector();
        if (assetAssoc != null) {
            Iterator it = assetAssoc.iterator();
            while (it.hasNext()) {
                result.add(((WorkOrderAssetView) it.next()).getWorkOrderAssetData());
            }
        }
        return result;
    }

    private static void loadDetailData(APIAccess factory,
                                       WorkOrderItemDetailView workOrderItemDetail,
                                       UserWorkOrderItemMgrForm mgrForm,
                                       CleanwiseUser appUser) throws Exception {

        WorkOrderItemData newWrkItemData = WorkOrderItemData.createValue();

        if (mgrForm.getBusEntityId() <= 0) {
            newWrkItemData.setBusEntityId(appUser.getSite().getSiteId());
        } else {
            newWrkItemData.setBusEntityId(mgrForm.getBusEntityId());
        }
        if (mgrForm.getWorkOrderId() <= 0) {
            newWrkItemData.setWorkOrderId(mgrForm.getWorkOrder().getWorkOrderId());
        } else {
            newWrkItemData.setWorkOrderId(mgrForm.getWorkOrderId());
        }

        newWrkItemData.setWorkOrderItemId(mgrForm.getWorkOrderItemId());

        if (Utility.isSet(mgrForm.getStatusCd())) {
            newWrkItemData.setStatusCd(mgrForm.getStatusCd());
        } else {
            newWrkItemData.setStatusCd(RefCodeNames.WORK_ORDER_ITEM_STATUS_CD.ACTIVE);
        }

        newWrkItemData.setShortDesc(mgrForm.getShortDesc());
        newWrkItemData.setLongDesc(mgrForm.getLongDesc());

        if (Utility.isSet(mgrForm.getEstimatedLabor())) {
            try {
                newWrkItemData.setEstimateLabor(new BigDecimal(mgrForm.getEstimatedLabor()));
            } catch (NumberFormatException e) {
                newWrkItemData.setEstimateLabor(null);
            }
        } else {
            newWrkItemData.setEstimateLabor(null);
        }

        if (Utility.isSet(mgrForm.getEstimatedPart())) {
            try {
                BigDecimal cost = new BigDecimal(mgrForm.getEstimatedPart());
                newWrkItemData.setEstimatePart(cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            } catch (NumberFormatException e) {
                newWrkItemData.setEstimatePart(null);
            }
        } else {
            newWrkItemData.setEstimatePart(null);
        }

        if (Utility.isSet(mgrForm.getActualLabor())) {
            try {
                newWrkItemData.setActualLabor(new BigDecimal(mgrForm.getActualLabor()));
            } catch (NumberFormatException e) {
                newWrkItemData.setActualLabor(null);
            }
        } else {
            newWrkItemData.setActualLabor(null);
        }


        if (Utility.isSet(mgrForm.getActualPart())) {
            try {
                BigDecimal cost = new BigDecimal(mgrForm.getActualPart());
                newWrkItemData.setActualPart(cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            } catch (NumberFormatException e) {
                newWrkItemData.setActualPart(null);
            }
        } else {
            newWrkItemData.setActualPart(null);
        }

        newWrkItemData.setAddBy(mgrForm.getAddBy());
        newWrkItemData.setAddDate(mgrForm.getAddDate());
        newWrkItemData.setModBy(mgrForm.getModBy());
        newWrkItemData.setModDate(mgrForm.getModDate());

        int assetId;
        if (Utility.isSet(mgrForm.getActiveAssetIdStr())) {
            try {
                assetId = Integer.parseInt(mgrForm.getActiveAssetIdStr());
                int assignedAssetId = WorkOrderUtil.getAssignedAssetId(workOrderItemDetail.getAssetAssoc());

                if (assignedAssetId > 0 || assetId <= 0) {
                    if (assignedAssetId != assetId) {
                        removeAssoc(mgrForm.getWorkOrderItemDetail().getAssetAssoc(), assignedAssetId);
                    }
                }

                if (assetId > 0 && assignedAssetId != assetId) {
                    setAssetToAssoc(assetId, workOrderItemDetail.getAssetAssoc(), newWrkItemData);
                }
            } catch (NumberFormatException e) {
                error(e.getMessage(), e);
            }
        } else {
            int assignedAssetId = WorkOrderUtil.getAssignedAssetId(workOrderItemDetail.getAssetAssoc());
            if (assignedAssetId > 0) {
                removeAssoc(mgrForm.getWorkOrderItemDetail().getAssetAssoc(), assignedAssetId);
            }
            mgrForm.setActiveWarrantyIdStr("");
        }

        int warrantyId;
        if (Utility.isSet(mgrForm.getActiveWarrantyIdStr())) {
            try {
                warrantyId = Integer.parseInt(mgrForm.getActiveWarrantyIdStr());
                newWrkItemData.setWarrantyId(warrantyId);
                Warranty warrantyEjb = factory.getWarrantyAPI();
                workOrderItemDetail.setWarranty(warrantyEjb.getWarrantyData(warrantyId));

            } catch (NumberFormatException e) {
                error(e.getMessage(),e);
            }
        } else {
            newWrkItemData.setWarrantyId(0);
        }

        try {
            newWrkItemData.setSequence(Integer.parseInt(mgrForm.getSequence()));
        } catch (NumberFormatException e) {
            newWrkItemData.setSequence(0);
        }

        workOrderItemDetail.setWorkOrderItem(newWrkItemData);
    }

    private static WorkOrderAssetViewVector setAssetToAssoc(int assetId, WorkOrderAssetViewVector assetAssoc, WorkOrderItemData wrkItemData) throws Exception {
        if (assetAssoc == null) {
            assetAssoc = new WorkOrderAssetViewVector();
            WorkOrderAssetView wrkAssetData = createAssetAssoc(assetId, wrkItemData);
            if (wrkAssetData != null) {
                assetAssoc.add(wrkAssetData);
            }
        } else {
            assetAssoc = setNewAssetAssoc(assetAssoc, assetId, wrkItemData);
        }
        return assetAssoc;
    }

    private static WorkOrderAssetViewVector setNewAssetAssoc(WorkOrderAssetViewVector assetAssoc, int assetId, WorkOrderItemData wrkItemData) throws Exception {
        boolean isSet = false;
        if (assetAssoc != null) {
            if (!assetAssoc.isEmpty()) {
                //find asset if asset exist
                Iterator it = assetAssoc.iterator();
                while (it.hasNext()) {
                    WorkOrderAssetView assoc = (WorkOrderAssetView) it.next();
                    if (assoc.getWorkOrderAssetData() != null && assoc.getWorkOrderAssetData().getAssetId() == assetId) {
                        isSet = true;
                    }
                }
            }
            //if asset not exist create new assoc
            if (!isSet && assetId > 0) {
                WorkOrderAssetView assocData = createAssetAssoc(assetId, wrkItemData);

                if (assocData != null) {
                    assetAssoc.add(assocData);
                }
            }
        }
        return assetAssoc;
    }

    private static WorkOrderAssetView createAssetAssoc(int assetId, WorkOrderItemData wrkItemData) throws Exception {
        if (wrkItemData != null && assetId > 0) {

            WorkOrderAssetData wrkAssetData = WorkOrderAssetData.createValue();
            wrkAssetData.setAssetId(assetId);
            wrkAssetData.setWorkOrderItemId(wrkItemData.getWorkOrderItemId());
            AssetView asset = getAssetView(assetId,true);
            return new WorkOrderAssetView(asset, wrkAssetData);
        }
        return null;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        ActionErrors ae = new ActionErrors();

        if (mgrForm.getWorkOrderItemDetail() == null) {
            throw new Exception("Work Order Item Detail Data can't be null");
        }

        if (mgrForm.getWorkOrderItemDetail().getWorkOrderItem() == null) {
            throw new Exception("Work Order Item Data can't be null");
        }

        if (Utility.isSet(mgrForm.getActiveWarrantyIdStr())) {
            try {
                Integer.parseInt(mgrForm.getActiveWarrantyIdStr());
            } catch (NumberFormatException e) {
                throw new Exception(e.getMessage());
            }
        }

        if (Utility.isSet(mgrForm.getActiveAssetIdStr())) {
            try {
                Integer.parseInt(mgrForm.getActiveAssetIdStr());
            } catch (NumberFormatException e) {
                throw new Exception(e.getMessage());
            }
        }

        if (Utility.isSet(mgrForm.getEstimatedPart())) {
            try {
                Double.parseDouble(mgrForm.getEstimatedPart());
            } catch (Exception e) {
                String mess = ClwI18nUtil.getMessage(request, "shop.errors.priceFormat", new Object[]{mgrForm.getEstimatedPart()}, true);
                mess = mess == null ? ("Error value: " + mgrForm.getEstimatedPart()) : mess;
                ae.add("EstimatedPart", new ActionError("error.simpleGenericError", mess));
            }
        }

        if (Utility.isSet(mgrForm.getEstimatedLabor())) {
            try {
                Double.parseDouble(mgrForm.getEstimatedLabor());
            } catch (Exception e) {
                String mess = ClwI18nUtil.getMessage(request, "shop.errors.priceFormat", new Object[]{mgrForm.getEstimatedLabor()}, true);
                mess = mess == null ? ("Error value: " + mgrForm.getEstimatedLabor()) : mess;
                ae.add("EstimatedLabor", new ActionError("error.simpleGenericError", mess));
            }
        }


        if (Utility.isSet(mgrForm.getActualLabor())) {
            try {
                Double.parseDouble(mgrForm.getActualLabor());
            } catch (Exception e) {
                String mess = ClwI18nUtil.getMessage(request, "shop.errors.priceFormat", new Object[]{mgrForm.getActualLabor()}, true);
                mess = mess == null ? ("Error value: " + mgrForm.getActualLabor()) : mess;
                ae.add("ActualLabor", new ActionError("error.simpleGenericError", mess));
            }
        }

        if (Utility.isSet(mgrForm.getActualPart())) {
            try {
                Double.parseDouble(mgrForm.getActualPart());
            } catch (Exception e) {
                String mess = ClwI18nUtil.getMessage(request, "shop.errors.wrongNumberFormat", new Object[]{mgrForm.getActualPart()}, true);
                mess = mess == null ? ("Error value: " + mgrForm.getActualPart()) : mess;
                ae.add("ActualPart", new ActionError("error.simpleGenericError", mess));
            }
        }

        if (!Utility.isSet(mgrForm.getShortDesc())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderItems.workOrderName");
            if (field == null) {
                field = "Name";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Status", new ActionError("error.simpleGenericError", mess));
        }


        return ae;
    }

    public static ActionErrors createNewWorkOrderItem(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        mgrForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        WorkOrderItemDetailView detView = new WorkOrderItemDetailView(WorkOrderItemData.createValue(),
                new WoiStatusHistDataVector(),
                WarrantyData.createValue(),
                new WorkOrderAssetViewVector(),
                new WorkOrderContentViewVector(),
                new WorkOrderPropertyDataVector());


        uploadItemDetailData(detView, mgrForm);

        mgrForm.setActiveAssetIdStr(EMPTY);
        mgrForm.setActiveAsset(null);
        mgrForm.setActiveWarrantyIdStr(EMPTY);

        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, mgrForm);

        return ae;
    }

    public static ActionErrors createNewWorkOrderItem(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        HttpSession session = request.getSession();

        ActionErrors ae = init(request, (UserWorkOrderItemMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm = (UserWorkOrderItemMgrForm) session.getAttribute(USER_WORK_ORDER_ITEM_MGR_FORM);

        WorkOrderItemDetailView detView = new WorkOrderItemDetailView(WorkOrderItemData.createValue(),
                new WoiStatusHistDataVector(),
                WarrantyData.createValue(),
                new WorkOrderAssetViewVector(),
                new WorkOrderContentViewVector(),
                new WorkOrderPropertyDataVector());

        uploadDetailData(detView, mgrForm);
        session.setAttribute(USER_WORK_ORDER_ITEM_MGR_FORM, mgrForm);

        return ae;
    }

    private static PairViewVector groupByCategory(AssetDataVector assetCats, AssetDataVector assets) {

        PairViewVector result = new PairViewVector();
        AssetDataVector tempAssetArray;
        tempAssetArray = (AssetDataVector) assets.clone();

        if (assetCats != null && !assetCats.isEmpty()) {
            Iterator catIt = assetCats.iterator();
            while (catIt.hasNext()) {
                AssetData category = (AssetData) catIt.next();
                AssetDataVector groupAssets = new AssetDataVector();
                if (!tempAssetArray.isEmpty()) {
                    Iterator assetIt = tempAssetArray.iterator();
                    while (assetIt.hasNext()) {
                        AssetData asset = (AssetData) assetIt.next();
                        if (asset.getParentId() == category.getAssetId()) {
                            groupAssets.add(asset);
                            assetIt.remove();
                        }
                    }
                }
                DisplayListSort.sort(groupAssets, "short_desc");
                result.add(new PairView(category.getShortDesc(), groupAssets));
            }
        }

        if (!tempAssetArray.isEmpty()) {
            DisplayListSort.sort(tempAssetArray, "short_desc");
            result.add(new PairView(EMPTY, tempAssetArray));
        }

        return result;
    }

    public static ActionErrors init(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();
        try {

            mgrForm = new UserWorkOrderItemMgrForm();

            UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);
            mgrForm.setAllWorkOrderItems(detForm.getWorkOrderDetail().getWorkOrderItems());
            DisplayListSort.sort(mgrForm.getAllWorkOrderItems(),"sequence");
            mgrForm.setWorkOrder(detForm.getWorkOrderDetail().getWorkOrder());

            mgrForm.setAllAssets(new AssetDataVector());
            mgrForm.setWarrantyForActiveAsset(new WarrantyDataVector());

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            ListService listServiceEJB = factory.getListServiceAPI();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            AssetDataVector assets = getAllAssetsForUser(factory, appUser);
            AssetDataVector assetCats = getAssetCategories(assets);

            // load orders made for this WorkOrderItem
            int workOrderItemId = getWorkOrderItemIdFromRequest(request, mgrForm);
            if (workOrderItemId > 0) {
                Order orderEjb = factory.getOrderAPI();
                OrderDataVector orders = orderEjb.getOrdersByItem(workOrderItemId);
                mgrForm.setItemOrders(orders);
            }
            mgrForm.setAllowBuyWorkOrderParts(checkAllowBuyWorkOrderParts(appUser));

            //key is <String> categoryName,object is <AssetDataVector> assets
            PairViewVector assetGroupMap = groupByCategory(assetCats,assets);

            mgrForm.setAssetCategories(assetCats);
            mgrForm.setAllAssets(assets);
            mgrForm.setAssetGroups(assetGroupMap);

            session.setAttribute("WorkOrderItem.user.status.vector", new RefCdDataVector());
            RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_ITEM_STATUS_CD");
            session.setAttribute("WorkOrderItem.user.status.vector", statusCds);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

        session.setAttribute(USER_WORK_ORDER_ITEM_MGR_FORM, mgrForm);
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        if (mgrForm == null) {
            throw new Exception("Form not initialized");
        }

        if (detForm == null) {
            throw new Exception("Detail form not initialized");
        }

        if (detForm.getWorkOrderDetail() == null) {
            throw new Exception("Parent form not contains detail data");
        }

        if (detForm.getWorkOrderDetail().getWorkOrder() == null) {
            throw new Exception("Parent form not contains work order data");
        }

        if (mgrForm.getWorkOrder() == null) {
            throw new Exception("Mgr.Form not contains work order data");
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (detForm.getWorkOrderDetail().getWorkOrder().getWorkOrderId() !=
                mgrForm.getWorkOrder().getWorkOrderId()) {
            ae.add("error", new ActionError("error.systemError", "shop.errors.pageExpired"));
            return ae;
        }

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }

        return ae;
    }


    public static AssetDataVector getAllAssetsForUser(APIAccess factory, CleanwiseUser appUser) throws Exception {

        Asset assetEJB = factory.getAssetAPI();
        AssetSearchCriteria criteria = new AssetSearchCriteria();

        criteria.setUserId(appUser.getUserId());
        criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());
        criteria.setShowInactive(false);

        HashMap assocCds = new HashMap();
        assocCds.put(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
        criteria.setAssetAssocCds(assocCds);

        if(appUser.getSite() != null)
        {
	        IdVector siteIds = new IdVector();
	        siteIds.add(new Integer(appUser.getSite().getSiteId()));
	        criteria.setSiteIds(siteIds);
        }
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);

        return assetEJB.getAssetDataByCriteria(criteria);
    }

    public static ActionErrors init(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();
        try {

            mgrForm = new UserWorkOrderNoteMgrForm();

            UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);
            mgrForm.setWorkOrderNotes(detForm.getWorkOrderDetail().getNotes());
            mgrForm.setWorkOrder(detForm.getWorkOrderDetail().getWorkOrder());

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            ListService listServiceEJB = factory.getListServiceAPI();

            session.setAttribute("WorkOrderNote.user.type.vector", new RefCdDataVector());
            RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_NOTE_TYPE_CD");
            session.setAttribute("WorkOrderNote.user.type.vector", statusCds);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

        session.setAttribute(USER_WORK_ORDER_NOTE_MGR_FORM, mgrForm);
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        if (mgrForm == null) {
            throw new Exception("Form not initialized");
        }

        if (detForm == null) {
            throw new Exception("Detail form not initialized");
        }

        if (detForm.getWorkOrderDetail() == null) {
            throw new Exception("Parent form not contains detail data");
        }

        if (detForm.getWorkOrderDetail().getWorkOrder() == null) {
            throw new Exception("Parent form not contains work order data");
        }

        if (mgrForm.getWorkOrder() == null) {
            throw new Exception("Mgr.Form not contains work order data");
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (!detForm.getWorkOrderDetail().getWorkOrder().equals(mgrForm.getWorkOrder())) {
            ae.add("error", new ActionError("error.systemError", "shop.errors.pageExpired"));
            return ae;
        }

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }

        return ae;
    }

    public static ActionErrors getNoteDetail(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae;
        try {

            int workOrderNoteId = getWorkOrderNotedFromRequest(request, mgrForm);
            ae = init(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            mgrForm = (UserWorkOrderNoteMgrForm) session.getAttribute(USER_WORK_ORDER_NOTE_MGR_FORM);
            WorkOrderNoteData note = WorkOrderUtil.findNote(mgrForm.getWorkOrderNotes(), workOrderNoteId);

            if (note != null) {
                uploadDetailData(note, mgrForm);
            }


        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
        session.setAttribute(USER_WORK_ORDER_NOTE_MGR_FORM, mgrForm);

        return ae;
    }

    private static void uploadDetailData(WorkOrderNoteData note, UserWorkOrderNoteMgrForm mgrForm) {
        mgrForm.setWorkOrderNoteId(note.getWorkOrderNoteId());
        mgrForm.setWorkOrderId(note.getWorkOrderId());
        mgrForm.setShortDesc(note.getShortDesc());
        mgrForm.setNote(note.getNote());
        mgrForm.setTypeCd(note.getTypeCd());
        mgrForm.setAddBy(note.getAddBy());
        mgrForm.setAddDate(note.getAddDate());
        mgrForm.setModBy(note.getModBy());
        mgrForm.setModDate(note.getModDate());
    }

    private static int getWorkOrderNotedFromRequest(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) {
        int id = getIdFromRequest(request, "workOrderNoteId");
        if (id <= 0 && mgrForm != null &&
                mgrForm.getWorkOrderNoteId() > 0) {
            return mgrForm.getWorkOrderNoteId();
        } else {
            return id;
        }
    }

    public static ActionErrors createNewWorkOrderNote(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae;

        try {

            ae = init(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            mgrForm = (UserWorkOrderNoteMgrForm) session.getAttribute(USER_WORK_ORDER_NOTE_MGR_FORM);
            WorkOrderNoteData note = WorkOrderNoteData.createValue();
            note.setWorkOrderId(mgrForm.getWorkOrder().getWorkOrderId());

            uploadDetailData(note, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
        session.setAttribute(USER_WORK_ORDER_NOTE_MGR_FORM, mgrForm);
        return ae;
    }

    public static ActionErrors updateWorkOrderNote(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) throws Exception {

        ActionErrors ae;

        try {

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            ae = checkUserRight(request,appUser, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            ae = checkFormAttribute(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            WorkOrder wrkEjb = factory.getWorkOrderAPI();
            WorkOrderNoteData note = WorkOrderNoteData.createValue();

            loadDetailData(note, mgrForm);

            note = wrkEjb.updateWorkOrderNoteData(note, appUser.getUser());

            refreshData(request);
            uploadDetailData(note, mgrForm);
            return getNoteDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    private static ActionErrors checkUserRight(HttpServletRequest request, CleanwiseUser appUser, UserWorkOrderNoteMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        ae = checkUserRight(request,appUser, detForm);
        if (ae.size() > 0) {
            return ae;
        }

        return ae;
    }

    private static void loadDetailData(WorkOrderNoteData note, UserWorkOrderNoteMgrForm mgrForm) {
        note.setWorkOrderNoteId(mgrForm.getWorkOrderNoteId());
        note.setWorkOrderId(mgrForm.getWorkOrderId());
        note.setShortDesc(mgrForm.getShortDesc());
        note.setNote(mgrForm.getNote());
        note.setTypeCd(mgrForm.getTypeCd());
        note.setAddBy(mgrForm.getAddBy());
        note.setAddDate(mgrForm.getAddDate());
        note.setModBy(mgrForm.getModBy());
        note.setModDate(mgrForm.getModDate());
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWorkOrderNoteMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();

        if (mgrForm == null) {
            throw new Exception("UserWorkOrderNoteMgrForm can't be null");
        }

        if (!Utility.isSet(mgrForm.getShortDesc())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.note.description");
            if (field == null) {
                field = "Description";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Description", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getTypeCd())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.note.typeCd");
            if (field == null) {
                field = "Type Code";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("typeCd", new ActionError("error.simpleGenericError", mess));
        }

        if (!Utility.isSet(mgrForm.getNote())) {

            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.note");
            if (field == null) {
                field = "Note";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Note", new ActionError("error.simpleGenericError", mess));
        }

        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();
        try {

            mgrForm = new UserWorkOrderContentMgrForm();

            UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);
            WorkOrderContentViewVector contents = new WorkOrderContentViewVector();
            contents.addAll(detForm.getWorkOrderDetail().getContents());
            Collection woiContent = WorkOrderUtil.getContentOnly(detForm.getWorkOrderDetail().getWorkOrderItems());
            contents.addAll(woiContent);
            mgrForm.setWorkOrderContentAllTypes(contents);
            mgrForm.setWorkOrder(detForm.getWorkOrderDetail().getWorkOrder());

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            ListService listServiceEJB = factory.getListServiceAPI();

            session.setAttribute("WorkOrderContent.status.vector", new RefCdDataVector());
            RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_CONTENT_STATUS_CD");
            session.setAttribute("WorkOrderContent.status.vector", statusCds);

            session.setAttribute("WorkOrderContent.locale.vecto", new RefCdDataVector());
            RefCdDataVector contentLocaleCds = listServiceEJB.getRefCodesCollection("LOCALE_CD");
            session.setAttribute("WorkOrderContent.locale.vector", contentLocaleCds);

        } catch (Exception e) {
            error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }

        session.setAttribute(USER_WORK_ORDER_CONTENT_MGR_FORM, mgrForm);
        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        if (mgrForm == null) {
            throw new Exception("Form not initialized");
        }

        if (detForm == null) {
            throw new Exception("Detail form not initialized");
        }

        if (detForm.getWorkOrderDetail() == null) {
            throw new Exception("Parent form not contains detail data");
        }

        if (detForm.getWorkOrderDetail().getWorkOrder() == null) {
            throw new Exception("Parent form not contains work order data");
        }

        if (mgrForm.getWorkOrder() == null) {
            throw new Exception("Mgr.Form not contains work order data");
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (!detForm.getWorkOrderDetail().getWorkOrder().equals(mgrForm.getWorkOrder())) {
            ae.add("error", new ActionError("error.systemError", "shop.errors.pageExpired"));
            return ae;
        }

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }

        return ae;
    }

    public static ActionErrors createNewWorkOrderContent(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm = (UserWorkOrderContentMgrForm) session.getAttribute(USER_WORK_ORDER_CONTENT_MGR_FORM);
        try {

            int workOrderId     = getIdFromRequest(request, "workOrderId");
            int workOrderItemId = getIdFromRequest(request, "workOrderItemId");
            WorkOrderContentView content;
            if (workOrderId > 0 && workOrderItemId > 0) {
                content = new WorkOrderContentView(ContentDetailView.createValue(), WorkOrderContentData.createValue());
                content.getWorkOrderContentData().setWorkOrderId(workOrderId);
                content.getWorkOrderContentData().setWorkOrderItemId(workOrderItemId);
            } else if (workOrderId > 0) {
                content = new WorkOrderContentView(ContentDetailView.createValue(), WorkOrderContentData.createValue());
                content.getWorkOrderContentData().setWorkOrderId(workOrderId);
            } else {
                throw new Exception("Work Order Content can't be created");
            }

            uploadContentDetailData(request,content, mgrForm);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        session.setAttribute(USER_WORK_ORDER_CONTENT_MGR_FORM, mgrForm);
        return ae;
    }


    private static void uploadContentDetailData(HttpServletRequest request,WorkOrderContentView content, UserWorkOrderContentMgrForm mgrForm) {

        mgrForm.setWorkOrderId(content.getWorkOrderContentData().getWorkOrderId());
        mgrForm.setWorkOrderContentId(content.getWorkOrderContentData().getWorkOrderContentId());
        mgrForm.setWorkOrderItemId(content.getWorkOrderContentData().getWorkOrderItemId());
        mgrForm.setUrl(content.getWorkOrderContentData().getUrl());
        mgrForm.setWorkOrderAddBy(content.getWorkOrderContentData().getAddBy());
        mgrForm.setWorkOrderModBy(content.getWorkOrderContentData().getModBy());
        mgrForm.setWorkOrderAddDate(content.getWorkOrderContentData().getAddDate());
        mgrForm.setWorkOrderModDate(content.getWorkOrderContentData().getModDate());

        mgrForm.setContentId(content.getContent().getContentId());
        mgrForm.setBusEntityId(content.getContent().getBusEntityId());
        mgrForm.setItemId(content.getContent().getItemId());
        mgrForm.setContentStatusCd(Utility.strNN(content.getContent().getContentStatusCd()));
        mgrForm.setContentTypeCd(Utility.strNN(content.getContent().getContentTypeCd()));
        mgrForm.setContentUsageCd(Utility.strNN(content.getContent().getContentUsageCd()));
        mgrForm.setLanguageCd(Utility.strNN(content.getContent().getLanguageCd()));
        mgrForm.setLocaleCd(Utility.strNN(content.getContent().getLocaleCd()));
        mgrForm.setLongDesc(Utility.strNN(content.getContent().getLongDesc()));
        mgrForm.setShortDesc(Utility.strNN(content.getContent().getShortDesc()));
        mgrForm.setSourceCd(Utility.strNN(content.getContent().getSourceCd()));

        if (content.getContent().getEffDate() != null) {
            try {
                mgrForm.setEffDate(ClwI18nUtil.formatDateInp(request,content.getContent().getEffDate()));
            } catch (Exception e) {
                mgrForm.setEffDate(content.getContent().getEffDate().toString());
            }
        } else {
            mgrForm.setEffDate("");
        }

        if (content.getContent().getExpDate() != null) {
            try {
                mgrForm.setExpDate(ClwI18nUtil.formatDateInp(request,content.getContent().getExpDate()));
            } catch (Exception e) {
                mgrForm.setExpDate(content.getContent().getExpDate().toString());
            }
        } else {
            mgrForm.setExpDate("");
        }

        mgrForm.setData(content.getContent().getData());
        mgrForm.setPath(Utility.strNN(content.getContent().getPath()));
        mgrForm.setVersion(String.valueOf(content.getContent().getVersion()));

        if (content.getContent().getData() != null) {
            mgrForm.setContentSize(String.valueOf(content.getContent().getData().length) + " bytes");
        }

        mgrForm.setContentAddBy(content.getContent().getAddBy());
        mgrForm.setContentModBy(content.getContent().getModBy());
        mgrForm.setContentAddDate(content.getContent().getAddDate());
        mgrForm.setContentModDate(content.getContent().getModDate());

    }

    public static ActionErrors updateWorkOrderContent(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = checkUserRight(request,appUser, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkFormAttribute(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            WorkOrder workOrderEjb = factory.getWorkOrderAPI();

            WorkOrderContentView workOrderContent = WorkOrderContentView.createValue();
            workOrderContent = loadContentDetailData(request,workOrderContent, mgrForm);

            workOrderContent = workOrderEjb.updateWorkOrderContent(workOrderContent, appUser.getUser());

            refreshData(request);

            uploadContentDetailData(request,workOrderContent, mgrForm);
            return getWorkOrderContentDetail(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    private static ActionErrors checkUserRight(HttpServletRequest request,CleanwiseUser appUser, UserWorkOrderContentMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        ae = checkUserRight(request,appUser, detForm);
        if (ae.size() > 0) {
            return ae;
        }

        return ae;
    }

    public static ActionErrors getWorkOrderContentDetail(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        try {
            int workorderContentIdInt = getWorkOrderContentIdFromRequest(request, mgrForm);

            ae.add(init(request, mgrForm));
            if (ae.size() > 0) {
                return ae;
            }

            mgrForm = (UserWorkOrderContentMgrForm) session.getAttribute(USER_WORK_ORDER_CONTENT_MGR_FORM);

            WorkOrderContentView content = WorkOrderUtil.findWarrantyContent(mgrForm.getWorkOrderContentAllTypes(), workorderContentIdInt);

            if (content == null) {
                throw new Exception("Can't find content.WorkOrderContentId:" + workorderContentIdInt);
            }
            if (content.getContent() == null || content.getWorkOrderContentData() == null) {
                throw new Exception("Not correct data.WorkOrderContentId:" + workorderContentIdInt);
            }

            uploadContentDetailData(request,content, mgrForm);

        } catch (Exception e) {
            try {
                error(e.getMessage(),e);
                ae.add(StringUtils.getUiErrorMess(e));
            } catch (Exception e1) {
                error(e1.getMessage(),e1);
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        session.setAttribute(USER_WORK_ORDER_CONTENT_MGR_FORM, mgrForm);
        return ae;
    }

    private static int getWorkOrderContentIdFromRequest(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) {
        int id = getIdFromRequest(request, "workOrderContentId");
        if (id <= 0 && mgrForm != null &&
                mgrForm.getWorkOrderContentId() > 0) {
            return mgrForm.getWorkOrderContentId();
        } else {
            return id;
        }
    }

    private static WorkOrderContentView loadContentDetailData(HttpServletRequest request,WorkOrderContentView workOrderContent, UserWorkOrderContentMgrForm mgrForm) throws IOException {

        WorkOrderContentData workOrderContentData = WorkOrderContentData.createValue();
        workOrderContentData.setWorkOrderId(mgrForm.getWorkOrderId());
        workOrderContentData.setWorkOrderItemId(mgrForm.getWorkOrderItemId());
        workOrderContentData.setWorkOrderContentId(mgrForm.getWorkOrderContentId());
        workOrderContentData.setUrl(mgrForm.getUrl());
        workOrderContentData.setAddBy(mgrForm.getWorkOrderAddBy());
        workOrderContentData.setModBy(mgrForm.getWorkOrderModBy());
        workOrderContentData.setAddDate(mgrForm.getWorkOrderAddDate());
        workOrderContentData.setModDate(mgrForm.getWorkOrderModDate());

        ContentDetailView content = ContentDetailView.createValue();
        content.setContentId(mgrForm.getContentId());
        content.setBusEntityId(mgrForm.getBusEntityId());
        content.setItemId(mgrForm.getItemId());

        if(!Utility.isSet(mgrForm.getContentStatusCd())){
            content.setContentStatusCd(RefCodeNames.CONTENT_STATUS_CD.ACTIVE);
        } else{
            content.setContentStatusCd(mgrForm.getContentStatusCd());
        }

        if(!Utility.isSet(mgrForm.getLanguageCd())){
            content.setLanguageCd("x");
        } else{
            content.setLanguageCd(mgrForm.getLanguageCd());
        }

        if(!Utility.isSet(mgrForm.getLocaleCd())){
            content.setLocaleCd("x");
        } else{
            content.setLocaleCd(mgrForm.getLocaleCd());
        }

        if(!Utility.isSet(mgrForm.getContentUsageCd())){
            content.setContentUsageCd("N/A");
        } else{
            content.setContentUsageCd(mgrForm.getContentUsageCd());
        }

        if(!Utility.isSet(mgrForm.getSourceCd())){
            content.setSourceCd("N/A");
        } else{
            content.setSourceCd(mgrForm.getSourceCd());
        }

        content.setLongDesc(mgrForm.getLongDesc());
        content.setShortDesc(mgrForm.getShortDesc());
        content.setAddBy(mgrForm.getContentAddBy());
        content.setModBy(mgrForm.getContentModBy());
        content.setAddDate(mgrForm.getContentAddDate());
        content.setModDate(mgrForm.getContentModDate());

        if (mgrForm.getUploadNewFile() != null && Utility.isSet(mgrForm.getUploadNewFile().getFileName())) {
            content.setPath(mgrForm.getUploadNewFile().getFileName());
            content.setData(mgrForm.getUploadNewFile().getFileData());
            content.setContentTypeCd(mgrForm.getUploadNewFile().getContentType());
        } else {
            if (mgrForm.getData() != null) {
                content.setPath(mgrForm.getPath());
                content.setData(mgrForm.getData());
                if(!Utility.isSet(mgrForm.getContentTypeCd())){
                    content.setContentTypeCd("N/A");
                } else{
                    content.setContentTypeCd(mgrForm.getContentTypeCd());
                }

            } else {
                content.setData(new byte[0]);
                content.setPath("");
                content.setContentTypeCd("N/A");
            }
        }

        if (Utility.isSet(mgrForm.getExpDate())) {
            try {
                content.setExpDate(ClwI18nUtil.parseDateInp(request,mgrForm.getExpDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                content.setExpDate(null);
            }
        } else {
            content.setExpDate(null);
        }

        if (Utility.isSet(mgrForm.getEffDate())) {
            try {
                content.setEffDate(ClwI18nUtil.parseDateInp(request,mgrForm.getEffDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                content.setEffDate(null);
            }
        } else {
            content.setEffDate(null);
        }

        content.setVersion(Integer.parseInt(mgrForm.getVersion()));

        workOrderContent = new WorkOrderContentView(content, workOrderContentData);
        return workOrderContent;
    }


    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) {

        ActionErrors ae = new ActionErrors();

        if (!Utility.isSet(mgrForm.getShortDesc())) {
            ae.add("ShortDesc", new ActionError("variable.empty.error", "Short Description"));
        }

        try {
            Integer.parseInt(mgrForm.getVersion());
        } catch (NumberFormatException e) {
            ae.add("Version", new ActionError("error.simpleGenericError", "Invalid Number"));
        }

        if (Utility.isSet(mgrForm.getExpDate())) {
            try {
                ClwI18nUtil.parseDateInp(request,mgrForm.getExpDate());
            } catch (ParseException e) {
                e.printStackTrace();
                ae.add("ExpDate", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        if (Utility.isSet(mgrForm.getEffDate())) {
            try {
                ClwI18nUtil.parseDateInp(request,mgrForm.getEffDate());
            } catch (ParseException e) {
                e.printStackTrace();
                ae.add("EffDate", new ActionError("error.simpleGenericError", e.getMessage()));
            }
        }
        return ae;
    }

    public static ActionErrors readDocument(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm, HttpServletResponse response) throws Exception {

        ActionErrors ae = getWorkOrderContentDetail(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm = (UserWorkOrderContentMgrForm) request.getSession().getAttribute(USER_WORK_ORDER_CONTENT_MGR_FORM);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(mgrForm.getData());
        response.setContentType(mgrForm.getContentTypeCd());
        response.setContentLength(out.size());
        out.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return ae;

    }

    public static ActionErrors downItemSequence(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        WorkOrder workOrderEjb = factory.getWorkOrderAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        int woiId = getIdFromRequest(request, "workOrderItemId");
        if (woiId > 0) {

            WorkOrderItemDetailView woiDet = getWoiDetail(mgrForm.getAllWorkOrderItems(), woiId);
            if (woiDet != null && woiDet.getWorkOrderItem() != null) {

                int currSeq = woiDet.getWorkOrderItem().getSequence();
                currSeq = currSeq < 0 ? 0 : currSeq;
                int newSeq = currSeq + 1;

                boolean updateFlag = workOrderEjb.updateWoiSequence(woiDet.getWorkOrderItem().getWorkOrderItemId(), newSeq, appUser.getUser());

                if (updateFlag) {
                    woiDet.getWorkOrderItem().setSequence(newSeq);
                }
            }
        }
        return ae;
    }

    public static ActionErrors upItemSequence(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        WorkOrder workOrderEjb = factory.getWorkOrderAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        int woiId = getIdFromRequest(request, "workOrderItemId");
        if (woiId > 0) {

            WorkOrderItemDetailView woiDet = getWoiDetail(mgrForm.getAllWorkOrderItems(), woiId);
            if (woiDet != null && woiDet.getWorkOrderItem() != null) {

                int currSeq = woiDet.getWorkOrderItem().getSequence();
                int newSeq = currSeq - 1;
                newSeq = newSeq < 0 ? 0 : newSeq;

                boolean updateFlag = workOrderEjb.updateWoiSequence(woiDet.getWorkOrderItem().getWorkOrderItemId(), newSeq, appUser.getUser());
                if (updateFlag) {
                    woiDet.getWorkOrderItem().setSequence(newSeq);
                }
            }
        }
        return ae;
    }

    public static ActionErrors removeWorkOrderContent(HttpServletRequest request, UserWorkOrderContentMgrForm mgrForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            WorkOrder workOrderEjb = factory.getWorkOrderAPI();

            int wocId = mgrForm.getWorkOrderContentId();
            int cId   = mgrForm.getContentId();
            boolean removeFlag = workOrderEjb.removeWorkOrderContent(wocId,cId);
            if(removeFlag){
                refreshData(request);
            }

            return init(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public static ActionErrors removeWorkOrderItem(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            WorkOrder workOrderEjb = factory.getWorkOrderAPI();

            int woiId = mgrForm.getWorkOrderItemId();
            boolean removeFlag = workOrderEjb.removeWorkOrderItem(woiId);
            if(removeFlag){
                refreshData(request);
            }

            return init(request, mgrForm);

        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public static ActionErrors removeWorkOrderItem(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm) throws Exception {
        ActionErrors ae;

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        ae = checkUserRight(request,appUser, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        try {
            APIAccess factory = new APIAccess();
            WorkOrder workOrderEjb = factory.getWorkOrderAPI();

            int woiId = mgrForm.getWorkOrderItemId();
            boolean removeFlag = workOrderEjb.removeWorkOrderItem(woiId);
            if (removeFlag) {
                ae = refreshData(request);
            }

            return ae;

            //return init(request, mgrForm);


        } catch (Exception e) {
            error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public static ActionErrors nextStep(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        WorkOrder wrkejb = factory.getWorkOrderAPI();
        mgrForm = (UserWorkOrderItemMgrForm) session.getAttribute(USER_WORK_ORDER_ITEM_MGR_FORM);

        if (UserWorkOrderItemMgrForm.CREATE_STEP_CD.STEP1.equals(mgrForm.getActiveStep())) {

            initStep(mgrForm, 1);

            int activeCatIdInt = -1;
            try {
                activeCatIdInt = Integer.parseInt(mgrForm.getActiveCategoryIdStr());
            } catch (NumberFormatException e) {
                log.info("nextStep => Warning:" + e.getMessage());
            }
            AssetData assetCat = WorkOrderUtil.findAsset(mgrForm.getAssetCategories(), activeCatIdInt);
            if (assetCat != null) {
                AssetDataVector assets = WorkOrderUtil.findAssets(mgrForm.getAllAssets(), assetCat.getAssetId());
                mgrForm.setAssetForActiveCategory(assets);
                mgrForm.setActiveStep(UserWorkOrderItemMgrForm.CREATE_STEP_CD.STEP2);
                mgrForm.setActiveAssetCategory(assetCat);
            } else {
                String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderItems.assetCategory");
                if (field == null) {
                    field = "Asset Category";
                }
                String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
                mess = mess == null ? ("Field  " + field + " requires information:") : mess;
                ae.add("AssetCategory", new ActionError("error.simpleGenericError", mess));
            }

        } else if (UserWorkOrderItemMgrForm.CREATE_STEP_CD.STEP2.equals(mgrForm.getActiveStep())) {

            initStep(mgrForm, 2);

            int activeAssetIdInt = -1;
            try {
                activeAssetIdInt = Integer.parseInt(mgrForm.getActiveAssetIdStr());
            } catch (NumberFormatException e) {
                log.info("nextStep => Warning:" + e.getMessage());
            }
            AssetData asset = WorkOrderUtil.findAsset(mgrForm.getAllAssets(), activeAssetIdInt);
            if (asset != null) {
                IdVector assetIds = new IdVector();
                assetIds.add(new Integer(asset.getAssetId()));
                WarrantyDataVector warranties = wrkejb.getWorkOrderWarrantiesForAssets(assetIds, RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);
                mgrForm.setWarrantyForActiveAsset(warranties);
                mgrForm.setActiveStep(UserWorkOrderItemMgrForm.CREATE_STEP_CD.STEP3);
                mgrForm.setActiveAsset(asset);
            } else {
                String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderItems.assets");
                if (field == null) {
                    field = "Asset";
                }
                String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
                mess = mess == null ? ("Field  " + field + " requires information:") : mess;
                ae.add("Asset", new ActionError("error.simpleGenericError", mess));
            }
        }

        session.setAttribute(USER_WORK_ORDER_ITEM_MGR_FORM, mgrForm);

        return ae;
    }

    private static void initStep(UserWorkOrderItemMgrForm mgrForm, int step) {
        switch (step){
            case 1:
                mgrForm.setActiveAssetCategory(null);
                mgrForm.setAssetForActiveCategory(new AssetDataVector());
                mgrForm.setActiveAssetIdStr(EMPTY);
            case 2:
                mgrForm.setWarrantyForActiveAsset(new WarrantyDataVector());
                mgrForm.setActiveWarrantyIdStr(EMPTY);
        }
    }

    public static ActionErrors getWarrantyInfo(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm, HttpServletResponse response) throws Exception {

        ActionErrors ae = checkRequest(request, mgrForm);
        if(ae.size()>0){
            return ae;
        }

        WorkOrderItemDetailView woiDet=null;
        int woiId=getIdFromRequest(request,"workOrderItemId");
        if(woiId>0){
            woiDet = getWoiDetail(mgrForm.getWorkOrderDetail().getWorkOrderItems(), woiId);
        }

        if(woiDet!=null && woiDet.getWarranty()!=null){

            response.setContentType("application/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder docBuilder = null;

            try {
                docBuilder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {


            }
            Document doc = docBuilder.getDOMImplementation().createDocument("", "Warranty", null);
            Element root = woiDet.getWarranty().toXml(doc);
            Element node = doc.createElement("WorkOrderItemId");
            node.appendChild(doc.createTextNode(String.valueOf(woiDet.getWorkOrderItem().getWorkOrderItemId())));
            root.appendChild(node);
            OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
            XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
            serializer.serialize(root);
        }
        return ae;
    }

    public static ActionErrors print(HttpServletRequest request, UserWorkOrderDetailMgrForm mgrForm, HttpServletResponse response) throws Exception {
        ActionErrors ae = checkRequest(request, mgrForm);
        if(ae.size()>0){
            return ae;
        }

        ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
        Locale localeCd  = ClwI18nUtil.getUserLocale(request);
        WorkOrderUtil woUtil = new WorkOrderUtil();
        woUtil.writeWoPdfToStream(mgrForm.getWorkOrderDetail(),pdfout,localeCd);

        response.setContentType("application/pdf");
        String browser = (String)  request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
      	  	response.setHeader("extension", "pdf");
      	  	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
        }

        response.setContentLength(pdfout.size());
        pdfout.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return ae;
    }

    public static ActionErrors changeServiceProvider(HttpServletRequest request,
                                                     UserWorkOrderDetailMgrForm mgrForm,
                                                     HttpServletResponse response) throws Exception {
        ActionErrors ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        int providerId = getIdFromRequest(request,"newServiceProviderId");

        HttpSession session = request.getSession();
        ServiceProviderData newServiceProvider = null;
        String newServiceProviderStr="";

        if(providerId>0){
            newServiceProvider = findServiceProvider(session, providerId);
            newServiceProviderStr = String.valueOf(newServiceProvider.getBusEntity().getBusEntityId());
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document responseXml = docBuilder.newDocument();

        Element root = responseXml.createElement("ServiceProvider");

        if (newServiceProvider != null) {

            root.setAttribute("Id", String.valueOf(newServiceProvider.getBusEntity().getBusEntityId()));

            Element contactNode = responseXml.createElement("ContactInfo");
            {
                Element node;

                if(Utility.isSet(newServiceProvider.getPrimaryAddress().getName1()) ||
                        Utility.isSet(newServiceProvider.getPrimaryAddress().getName2())){
                    node = responseXml.createElement("ContactName");
                    node.appendChild(responseXml.createTextNode(Utility.strNN(newServiceProvider.getPrimaryAddress().getName1())+
                            " "+Utility.strNN(newServiceProvider.getPrimaryAddress().getName2())));
                    contactNode.appendChild(node);
                }

                if(Utility.isSet(newServiceProvider.getPrimaryPhone().getPhoneNum())){
                    node = responseXml.createElement("ContactPhone");
                    node.appendChild(responseXml.createTextNode(Utility.strNN(newServiceProvider.getPrimaryPhone().getPhoneNum())));
                    contactNode.appendChild(node);
                }

                if(Utility.isSet(newServiceProvider.getPrimaryEmail().getEmailAddress())){
                    node = responseXml.createElement("ContactEmail");
                    node.appendChild(responseXml.createTextNode(" "+Utility.strNN(newServiceProvider.getPrimaryEmail().getEmailAddress())));
                    contactNode.appendChild(node);
                }

                if(Utility.isSet(newServiceProvider.getPrimaryFax().getPhoneNum())){
                    node = responseXml.createElement("ContactFax");
                    node.appendChild(responseXml.createTextNode(" "+Utility.strNN(newServiceProvider.getPrimaryFax().getPhoneNum())));
                    contactNode.appendChild(node);
                }

                root.appendChild(contactNode);
            }
        }

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(root.toString());

        mgrForm.setServiceProviderIdStr(newServiceProviderStr);
        mgrForm.setServiceProvider(newServiceProvider);

        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, mgrForm);


        return ae;
    }

    public static ActionErrors changeWorkOrderType(HttpServletRequest request,
                                                   UserWorkOrderDetailMgrForm mgrForm,
                                                   HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

        String newWrkTypeCd = request.getParameter("newWorkOrderTypeCd");
        mgrForm.setTypeCd(Utility.strNN(newWrkTypeCd));

        ServiceProviderDataVector providers = getDisplayServiceProviders(mgrForm, appUser);

        String responsStr = prepareSPResponseXml(providers);

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responsStr);

        mgrForm.setActualServiceProviders(providers);
        mgrForm.setServiceProvider(null);
        mgrForm.setServiceProviderIdStr("");

        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, mgrForm);
        return ae;

    }

    private static String prepareSPResponseXml(ServiceProviderDataVector providers) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document responseXml = docBuilder.newDocument();

        Element root = responseXml.createElement("ActualServiceProviders");
        root.setAttribute("time", String.valueOf(System.currentTimeMillis()));

        if (!providers.isEmpty()) {
            Iterator it = providers.iterator();
            while (it.hasNext()) {
                ServiceProviderData provider = (ServiceProviderData) it.next();

                Element providerNode = responseXml.createElement("ServiceProvider");

                providerNode.setAttribute("Id", String.valueOf(provider.getBusEntity().getBusEntityId()));

                Element node;
                if (Utility.isSet(provider.getBusEntity().getShortDesc())) {
                    node = responseXml.createElement("ProviderName");
                    node.appendChild(responseXml.createTextNode(provider.getBusEntity().getShortDesc()));
                    providerNode.appendChild(node);
                }

                root.appendChild(providerNode);
            }

        }

        return root.toString();
    }

    public static ActionErrors changeActiveAsset(HttpServletRequest request,
                                                 UserWorkOrderItemMgrForm mgrForm,
                                                 HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        WorkOrder wrkejb = factory.getWorkOrderAPI();
        mgrForm = (UserWorkOrderItemMgrForm) session.getAttribute(USER_WORK_ORDER_ITEM_MGR_FORM);

        mgrForm.setWarrantyForActiveAsset(new WarrantyDataVector());
        mgrForm.setActiveWarrantyIdStr(EMPTY);
        mgrForm.setWarrantyId(0);

        int activeAssetIdInt = getIdFromRequest(request, "newActiveAssetId");
        AssetData asset = WorkOrderUtil.findAsset(mgrForm.getAllAssets(), activeAssetIdInt);

        if (asset != null) {
            // gets Warranties
            IdVector assetIds = new IdVector();
            assetIds.add(new Integer(asset.getAssetId()));

            WarrantyDataVector warranties = wrkejb.getWorkOrderWarrantiesForAssets(assetIds, RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);

            mgrForm.setWarrantyForActiveAsset(warranties);
            mgrForm.setActiveAsset(asset);
            mgrForm.setActiveAssetIdStr(String.valueOf(asset.getAssetId()));

            buildDisplayWarranties(mgrForm);

            mgrForm.setActiveWarrantyIdStr(EMPTY);
            mgrForm.setWarrantyId(0);

            String responsStr = prepareWOIResponseXml(request, asset, warranties);

            response.setContentType("application/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responsStr);

        } else {

            mgrForm.setActiveAssetIdStr(EMPTY);
            mgrForm.setActiveAsset(null);


            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderItems.assets");
            if (field == null) {
                field = "Asset";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Asset", new ActionError("error.simpleGenericError", mess));
        }


        session.setAttribute(USER_WORK_ORDER_ITEM_MGR_FORM, mgrForm);
        return ae;
    }

    public static ActionErrors changeActiveAsset(HttpServletRequest request,
                                                 UserWorkOrderDetailMgrForm mgrForm,
                                                 HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        WorkOrder wrkejb = factory.getWorkOrderAPI();
        mgrForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        mgrForm.setWarrantyForActiveAsset(new WarrantyDataVector());
        mgrForm.setActiveWarrantyIdStr(EMPTY);
        mgrForm.setWarrantyId(0);

        int activeAssetIdInt = getIdFromRequest(request, "newActiveAssetId");
        AssetData asset = WorkOrderUtil.findAsset(mgrForm.getAllAssets(), activeAssetIdInt);

        if (asset != null) {
            // gets Warranties
            IdVector assetIds = new IdVector();
            assetIds.add(new Integer(asset.getAssetId()));

            WarrantyDataVector warranties = wrkejb.getWorkOrderWarrantiesForAssets(assetIds, RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);

            mgrForm.setWarrantyForActiveAsset(warranties);
            mgrForm.setActiveAsset(asset);
            mgrForm.setActiveAssetIdStr(String.valueOf(asset.getAssetId()));

            buildDisplayWarranties(mgrForm);

            mgrForm.setActiveWarrantyIdStr(EMPTY);
            mgrForm.setWarrantyId(0);

            String responsStr = prepareWOIResponseXml(request, asset, warranties);

            response.setContentType("application/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responsStr);

        } else {

            mgrForm.setActiveAssetIdStr(EMPTY);
            mgrForm.setActiveAsset(null);


            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.workOrderItems.assets");
            if (field == null) {
                field = "Asset";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("Asset", new ActionError("error.simpleGenericError", mess));
        }


        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, mgrForm);
        return ae;
    }

    private static void removeAssoc(WorkOrderAssetViewVector assetAssoc, int assetId) {
        if(assetAssoc!=null && !assetAssoc.isEmpty()){
            Iterator it = assetAssoc.iterator();
            while(it.hasNext()){
                WorkOrderAssetView woAsset = (WorkOrderAssetView)it.next();
                if(woAsset.getAssetView().getAssetId() == assetId){
                    it.remove();
                }
            }
        }
    }

    private static void removeAssoc(WorkOrderAssocDataVector woAssoc,String assocTypeCd) {
        if(woAssoc!=null && !woAssoc.isEmpty() && assocTypeCd!=null){
            Iterator it = woAssoc.iterator();
            while(it.hasNext()){
                WorkOrderAssocData assocData = (WorkOrderAssocData)it.next();
                if(assocTypeCd.equals(assocData.getWorkOrderAssocCd())){
                    it.remove();
                }
            }
        }
    }

    private static String prepareWOIResponseXml(HttpServletRequest request, AssetData asset, WarrantyDataVector warranties) {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, ClwI18nUtil.getUserLocale(request));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document responseXml = docBuilder.newDocument();

        Element root = responseXml.createElement("WOIResponseXml");
        root.setAttribute("time", String.valueOf(System.currentTimeMillis()));

        Element assetInfoNode = responseXml.createElement("AssetInfo");
        {
            Element assetNode = responseXml.createElement("Asset");

            assetNode.setAttribute("Id", String.valueOf(asset.getAssetId()));

            Element node;
            if(Utility.isSet(asset.getModelNumber())){
                node = responseXml.createElement("ModelNumber");
                node.appendChild(responseXml.createTextNode(asset.getModelNumber()));
                assetNode.appendChild(node);
            }

            if(Utility.isSet(asset.getSerialNum())){
                node = responseXml.createElement("SerialNumber");
                node.appendChild(responseXml.createTextNode(asset.getSerialNum()));
                assetNode.appendChild(node);
            }

            assetInfoNode.appendChild(assetNode);

        }
        root.appendChild(assetInfoNode);

        Element warrantyInfoNode = responseXml.createElement("WarrantyInfo");
        {
            if (!warranties.isEmpty()) {
                Iterator it = warranties.iterator();
                while (it.hasNext()) {
                    WarrantyData warranty = (WarrantyData) it.next();

                    Element warrantyNode = responseXml.createElement("Warranty");

                    warrantyNode.setAttribute("Id", String.valueOf(warranty.getWarrantyId()));

                    Element node;

                    node = responseXml.createElement("WarrantyNumber");
                    if (Utility.isSet(warranty.getWarrantyNumber())) {
                        node.appendChild(responseXml.createTextNode(warranty.getWarrantyNumber()));
                    } else {
                        node.appendChild(responseXml.createTextNode(""));
                    }
                    warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyName");
                    if(Utility.isSet(warranty.getShortDesc())){
                        node.appendChild(responseXml.createTextNode(warranty.getShortDesc()));
                    } else {
                        node.appendChild(responseXml.createTextNode(""));
                    }
                    warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyLongDesc");
                    if (Utility.isSet(warranty.getLongDesc())) {
                        node.appendChild(responseXml.createTextNode(warranty.getLongDesc()));
                    } else {
                        node.appendChild(responseXml.createTextNode(""));
                    }
                        warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyCost");
                    if (Utility.isSet(warranty.getCost())) {
                        node.appendChild(responseXml.createTextNode(warranty.getCost().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                    } else {
                        node.appendChild(responseXml.createTextNode(""));
                    }
                    warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyType");
                    if (Utility.isSet(warranty.getTypeCd())) {
                        node.appendChild(responseXml.createTextNode(warranty.getTypeCd()));
                    } else {
                        node.appendChild(responseXml.createTextNode(""));
                    }
                    warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyDurationType");
                    if (Utility.isSet(warranty.getDurationTypeCd())) {
                        node.appendChild(responseXml.createTextNode(warranty.getDurationTypeCd()));
                    } else {
                        node.appendChild(responseXml.createTextNode(""));
                    }
                    warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyDuration");
                    node.appendChild(responseXml.createTextNode(String.valueOf(warranty.getDuration())));
                    warrantyNode.appendChild(node);

                    node = responseXml.createElement("WarrantyAddDate");
                    node.appendChild(responseXml.createTextNode(sdFormat.format(warranty.getAddDate())));
                    warrantyNode.appendChild(node);

                    warrantyInfoNode.appendChild(warrantyNode);
                }

            }
            root.appendChild(warrantyInfoNode);
        }
        return root.toString();
    }


/*
  private static void resetSessionAttributes(HttpServletRequest request) {

      HttpSession session = request.getSession();

      session.setAttribute("WorkOrder.user.priority.vector", new RefCdDataVector());
      session.setAttribute("countries.vector", new CountryDataVector());
      session.setAttribute("WorkOrder.user.status.vector", new RefCdDataVector());
      session.setAttribute("WorkOrder.user.action.vector",new RefCdDataVector());
      session.setAttribute("WorkOrder.user.type.vector", new RefCdDataVector());
      session.setAttribute("WorkOrder.user.category.vector", new RefCdDataVector());
      session.setAttribute("WorkOrder.user.service.provider.vector", new BusEntityDataVector());
      session.setAttribute("WorkOrderItem.user.status.vector", new RefCdDataVector());
      session.setAttribute("WorkOrderContent.status.vector", new RefCdDataVector());
      session.setAttribute("WorkOrderContent.locale.vector", new RefCdDataVector());

  }
*/


    public static ActionErrors sendPdfToProvider(HttpServletRequest request,
                                                 UserWorkOrderDetailMgrForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        try {

            HttpSession session = request.getSession();

            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            APIAccess factory     = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            Service serviceEjb    = factory.getServiceAPI();
            WorkOrder workOrderEjb = factory.getWorkOrderAPI();

            mgrForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

            final WorkOrderDetailView workOrderDetailView = mgrForm.getWorkOrderDetail();
			if (!readyForSend(request, workOrderDetailView, ae)) {
                return ae;
            }
            int providerId = WorkOrderUtil.getAssignedServiceProviderId(workOrderDetailView.getWorkOrderAssocCollection());

            BusEntitySearchCriteria busEntityCrit = new BusEntitySearchCriteria();
            busEntityCrit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            busEntityCrit.setSearchId(providerId);
            busEntityCrit.setSearchForInactive(false);
            //busEntityCrit.setSearchForInactive(true);

            ServiceProviderDataVector woProviders = serviceEjb.getServiceProviderByCriteria(busEntityCrit);

            if (woProviders.isEmpty()) {
                throw new Exception("Error.No Service Provider for the Work Order.");
            } else if (woProviders.size() > 1) {
                throw new Exception("Error.Multiple Service Provider for the Work Order.");
            }

            ServiceProviderData provider = (ServiceProviderData) woProviders.get(0);
            ae = checkProviderContact(request, provider, mgrForm.getProviderTradingType());
            if (ae.size() > 0) {
                return ae;
            }

            Locale localeCd = ClwI18nUtil.getUserLocale(request);
            String providerTradingType = mgrForm.getProviderTradingType();
            String emailAddress = provider.getPrimaryEmail().getEmailAddress();

//            WorkOrderEventBuilder.sendEvent(workOrderDetailView,
//                    localeCd,
//                    providerTradingType,
//                    emailAddress,
//                    Utility.isTrue(mgrForm.getWorkflowProcessing(),true),
//                    ServiceProviderMgrLogic.class.getSimpleName(),
//                    appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER),
//                    appUser.getUser());
//
//
//            workOrderEjb.updateStatus(workOrderDetailView.getWorkOrder().getWorkOrderId(),
//                                      RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER,
//                                      appUser.getUser().getUserName());
            mgrForm.setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER);
            updateWorkOrder(request, mgrForm);
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("sendPdfToProvider", new ActionError("userWorkOrder.errors.emailFatalError", null));
            return ae;
        }


        return ae;
    }

    private static ActionErrors checkProviderContact(HttpServletRequest request, ServiceProviderData provider, String tradingType) {

        ActionErrors ae = new ActionErrors();

        if (WorkOrderUtil.EMAIL.equals(tradingType) &&
                (provider == null || provider.getPrimaryEmail() == null || !Utility.isSet(provider.getPrimaryEmail().getEmailAddress()))) {

            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider.contactEmail");
            if (field == null) {
                field = "Contact Email";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("sendPdfToProvider", new ActionError("userWorkOrder.errors.emailError", mess));

        } else if (WorkOrderUtil.FAX.equals(tradingType) &&
                (provider == null || provider.getPrimaryFax() == null || !Utility.isSet(provider.getPrimaryFax().getPhoneNum()))) {

            String field = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider.contactFax");
            if (field == null) {
                field = "Contact Fax";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("sendPdfToProvider", new ActionError("userWorkOrder.errors.emailError", mess));

        }

        return ae;
    }

    private static boolean readyForSend(HttpServletRequest request, WorkOrderDetailView workOrderDetail, ActionErrors ae) throws Exception {

        if (workOrderDetail == null) {
            throw new Exception("Work Order Detail Data is null");
        }

        if (workOrderDetail.getWorkOrderItems().isEmpty()) {
            ae.add("sendPdfToProvider",
                    new ActionError("userWorkOrder.errors.emailError",
                            Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.errors.emptyWorkOrder"))));
            return false;
        }

        if (RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER.equals(workOrderDetail.getWorkOrder().getStatusCd())) {
            ae.add("sendPdfToProvider",
                    new ActionError("error.simpleGenericError",
                    "Error: " + Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.errors.SendingWorkOrderInProgress"))));
            return false;
        }

        return true;
    }

    public static ActionErrors createWorkOrderFromTemplate(HttpServletRequest request, UserWorkOrderMgrForm mgrForm) throws Exception {

        ActionErrors ae;

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session   = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        WorkOrder wrkEjb      = APIAccess.getAPIAccess().getWorkOrderAPI();
        Service serviceEjb    = APIAccess.getAPIAccess().getServiceAPI();

        WorkOrderDetailViewVector woTemplate = new WorkOrderDetailViewVector();

        int woTemplateId = getIdFromRequest(request, "workOrderTemplate");
        if (woTemplateId > 0) {

            WorkOrderSimpleSearchCriteria criteria = new WorkOrderSimpleSearchCriteria();

            criteria.setWorkOrderId(woTemplateId);
            IdVector siteIds = new IdVector();
            siteIds.add(new Integer(appUser.getSite().getSiteId()));
            criteria.setSiteIds(siteIds);

            woTemplate = wrkEjb.getWorkOrderDetailCollection(criteria);

        }

        if (woTemplate.isEmpty() || woTemplate.size() > 1) {
            throw new Exception("Can't create work order from template.Work Order Template Identifier: " + woTemplateId);
        }

        ae = createNewWorkOrder(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        UserWorkOrderDetailMgrForm detailForm = (UserWorkOrderDetailMgrForm) session.getAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM);

        WorkOrderDetailView woDataTemp = clone((WorkOrderDetailView) woTemplate.get(0));
        WorkOrderData newWorkOrderData = detailForm.getWorkOrderDetail().getWorkOrder();

        // copy template value
        newWorkOrderData.setPriority(woDataTemp.getWorkOrder().getPriority());
        newWorkOrderData.setTypeCd(woDataTemp.getWorkOrder().getTypeCd());
        newWorkOrderData.setLongDesc(woDataTemp.getWorkOrder().getLongDesc());
        newWorkOrderData.setShortDesc(woDataTemp.getWorkOrder().getShortDesc());
        newWorkOrderData.setTypeCd(woDataTemp.getWorkOrder().getTypeCd());


        detailForm.getWorkOrderDetail().setWorkOrderAssocCollection(woDataTemp.getWorkOrderAssocCollection());
        detailForm.getWorkOrderDetail().setContents(woDataTemp.getContents());
        detailForm.getWorkOrderDetail().setProperties(woDataTemp.getProperties());

        //copy item template value
        WorkOrderItemDetailViewVector itemsTemplate = woDataTemp.getWorkOrderItems();
        Iterator it = itemsTemplate.iterator();
        while (it.hasNext()) {

            WorkOrderItemDetailView tempItemData = (WorkOrderItemDetailView) it.next();

            WorkOrderItemData newItemData = WorkOrderItemData.createValue();
            newItemData.setBusEntityId(tempItemData.getWorkOrderItem().getBusEntityId());
            newItemData.setWarrantyId(tempItemData.getWorkOrderItem().getWarrantyId());
            newItemData.setLongDesc(tempItemData.getWorkOrderItem().getLongDesc());
            newItemData.setShortDesc(tempItemData.getWorkOrderItem().getShortDesc());
            newItemData.setSequence(tempItemData.getWorkOrderItem().getSequence());
            newItemData.setStatusCd(tempItemData.getWorkOrderItem().getStatusCd());
            newItemData.setEstimateLabor(tempItemData.getWorkOrderItem().getEstimateLabor());
            newItemData.setEstimatePart(tempItemData.getWorkOrderItem().getEstimatePart());

            int dbSeq = wrkEjb.getClwWorkOrderItemSeq();
            newItemData.setWorkOrderItemId(dbSeq);

            addNewItemToWorkOrder(detailForm.getWorkOrderDetail(),
                    new WorkOrderItemDetailView(newItemData,
                            new WoiStatusHistDataVector(),
                            tempItemData.getWarranty(),
                            tempItemData.getAssetAssoc(),
                            tempItemData.getContents(),
                            tempItemData.getProperties()));


        }

        uploadDetailData(request, detailForm.getWorkOrderDetail(), detailForm);

        session.setAttribute(USER_WORK_ORDER_DETAIL_MGR_FORM, detailForm);
        return ae;
    }

    private static WorkOrderDetailView clone(WorkOrderDetailView woData) {

        if (woData != null) {
            WorkOrderUtil woUtil = new WorkOrderUtil();
            return woUtil.clone(woData);
        }
        return null;
    }

    public static AssetView getAssetView(int assetId, boolean throwExc) throws Exception {

        Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
        AssetSearchCriteria crit = new AssetSearchCriteria();
        crit.setAssetId(assetId);
        crit.setShowInactive(true);
        AssetViewVector assets = assetEjb.getAssetViewVector(crit);
        if (assets.isEmpty()) {
            if (throwExc) {
                throw new Exception("Asset not found.Asset Id:" + assetId);
            }
        } else if (assets.size() > 1) {
            if (throwExc) {
                throw new Exception("Multiple Asset.Asset Id:" + assetId);
            }
        } else {
            return (AssetView) assets.get(0);
        }
        return null;
    }

    public static PairView getBudgetInfo(UserWorkOrderDetailMgrForm mgrForm, CleanwiseUser appUser) throws Exception {

        PairView result = new PairView();

        Budget budgetEjb   = APIAccess.getAPIAccess().getBudgetAPI();
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Request requestEjb = APIAccess.getAPIAccess().getRequestAPI();

        SiteData site       = appUser.getSite();
        StoreData store     = appUser.getUserStore();
        AccountData account = appUser.getUserAccount();

        String budgetTypeCd;
        int budgetBusEntityId;
        FiscalPeriodView fiscalInfo = null;

        if (mgrForm.getBusEntityId() > 0) {

            String busEntityType = requestEjb.getBusEntityType(mgrForm.getBusEntityId());
            if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntityType)) {
                int accId = accountEjb.getAccountIdForSite(mgrForm.getBusEntityId());
                fiscalInfo = accountEjb.getFiscalInfo(accId);
            } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(busEntityType)) {
                fiscalInfo = accountEjb.getFiscalInfo(mgrForm.getBusEntityId());
            }

            if (fiscalInfo != null) {

                int budgetYear = fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
                budgetTypeCd = budgetEjb.getWorkOrderBudgetType(mgrForm.getBusEntityId(), budgetYear);

                if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(budgetTypeCd)) {
                    budgetBusEntityId = accountEjb.getAccountIdForSite(mgrForm.getBusEntityId());
                } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(budgetTypeCd)) {
                    budgetBusEntityId = mgrForm.getBusEntityId();
                } else {
                    return result;
                }

            } else {
                return result;
            }

        } else {

            fiscalInfo = accountEjb.getFiscalInfo(appUser.getSite().getAccountId());

            if (fiscalInfo != null) {

                int budgetYear = fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
                budgetTypeCd = budgetEjb.getWorkOrderBudgetType(store.getStoreId(), account.getAccountId(), site.getSiteId(), budgetYear);

                if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(budgetTypeCd)) {
                    budgetBusEntityId = account.getAccountId();
                } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(budgetTypeCd)) {
                    budgetBusEntityId = site.getSiteId();
                } else {
                    return result;
                }

            } else {
                return result;
            }
        }

        CostCenterDataVector cc = budgetEjb.getCostCenters(store.getStoreId(),
                budgetTypeCd,
                null,
                false);


        BudgetSpentCriteria budgetSpentCriteria = new BudgetSpentCriteria();
        budgetSpentCriteria.setBusEntityId(budgetBusEntityId);
        budgetSpentCriteria.setBudgetTypeCd(budgetTypeCd);
        budgetSpentCriteria.setCostCenters(Utility.toIdVector(cc));
        budgetSpentCriteria.setBudgetYear(fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear());
        budgetSpentCriteria.setNumberOfBudgetPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()));

        BudgetSpentShortViewVector budgetSpentInfo = budgetEjb.getWorkOrderBudgetSpendInfo(budgetSpentCriteria);

        if (fiscalInfo.getFiscalCalenderView() != null && fiscalInfo.getFiscalCalenderView().getFiscalCalender() != null) {

            WorkOrderBudgetUtil woBudgetUtil = new WorkOrderBudgetUtil(budgetSpentInfo);

            BudgetSpentShortViewVector budgetShortCollection = woBudgetUtil.getPeriodInfo(budgetBusEntityId,
                    fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear(),
                    1,
                    fiscalInfo.getCurrentFiscalPeriod());

            if (!budgetShortCollection.isEmpty()) {

                if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.DISPLAY_COST_CENTER_DETAIL)) {
                    BudgetSpendViewVector detailSpendCollection = getDetailSpentAmountInfo(budgetShortCollection, site, fiscalInfo);
                    detailSpendCollection = groupByCostCenter(detailSpendCollection);
                    DisplayListSort.sort(detailSpendCollection, "cost_center_name");
                    result.setObject2(detailSpendCollection);
                }

                BudgetSpentAmountView amountView = getShortSpentAmountInfo(budgetShortCollection, mgrForm);
                result.setObject1(amountView);
            }
        }

        return result;

    }

    private static BudgetSpendViewVector groupByCostCenter(BudgetSpendViewVector detailSpentAmountInfo) {

        BudgetSpendViewVector result = new BudgetSpendViewVector();
        HashMap ccSpendMap = new HashMap();

        Iterator it = detailSpentAmountInfo.iterator();
        while (it.hasNext()) {
            BudgetSpendView bsv = (BudgetSpendView) it.next();
            BudgetSpendView mapObj = (BudgetSpendView) ccSpendMap.get(new Integer(bsv.getCostCenterId()));
            if (mapObj != null) {
                mapObj.setAmountAllocated(Utility.addAmt(mapObj.getAmountAllocated(), bsv.getAmountAllocated()));
                mapObj.setAmountSpent(Utility.addAmt(mapObj.getAmountSpent(), bsv.getAmountSpent()));
                ccSpendMap.put(new Integer(bsv.getCostCenterId()), mapObj);
            } else {
                ccSpendMap.put(new Integer(bsv.getCostCenterId()), bsv);
            }
        }

        result.addAll(ccSpendMap.values());

        return result;
    }


    public static BudgetSpentAmountView getShortSpentAmountInfo(BudgetSpentShortViewVector budgetShortCollection,
                                                                UserWorkOrderDetailMgrForm mgrForm) {

        BudgetSpentAmountView amountView = BudgetSpentAmountView.createValue();

        Iterator it = budgetShortCollection.iterator();
        while (it.hasNext()) {
            BudgetSpentShortView sv = (BudgetSpentShortView) it.next();
            amountView.setAmountAllocated(Utility.addAmt(sv.getAmountAllocated(), amountView.getAmountAllocated()));
            amountView.setAmountSpent(Utility.addAmt(sv.getAmountSpent(), amountView.getAmountSpent()));
        }

        int costcenterId  = 0;
        try {
            costcenterId =  Integer.parseInt(mgrForm.getCostCenterId());
        } catch (NumberFormatException e) {
            error(e.getMessage(),e);
        }

        amountView.setAmountDifference(Utility.subtractAmt(amountView.getAmountAllocated(), amountView.getAmountSpent()));

        if (costcenterId > 0) {
            amountView.setAmount(WorkOrderUtil.getWorkOrderAmount(mgrForm.getWorkOrderDetail().getWorkOrderItems()));
        } else {
            amountView.setAmount(new BigDecimal(0));
        }

        amountView.setAmountTotal(Utility.subtractAmt(amountView.getAmountDifference(), amountView.getAmount()));

        return amountView;
    }

    private static BudgetSpendViewVector getDetailSpentAmountInfo(BudgetSpentShortViewVector budgetShortCollection, SiteData site, FiscalPeriodView fiscalInfo) throws Exception {

        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();

        BudgetSpendViewVector result = new BudgetSpendViewVector();

        CostCenterData ccData;
        Iterator it = budgetShortCollection.iterator();
        while (it.hasNext()) {
            BudgetSpentShortView sv = (BudgetSpentShortView) it.next();
            try {
                ccData = orderEjb.getCostCenter(sv.getCostCenterId());

                BudgetSpendView detailSpendView = BudgetSpendView.createValue();

                detailSpendView.setAmountAllocated(sv.getAmountAllocated());
                detailSpendView.setAmountSpent(sv.getAmountSpent());

                detailSpendView.setBudgetPeriod(fiscalInfo.getCurrentFiscalPeriod());
                detailSpendView.setBudgetYear(fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear());
                detailSpendView.setCurrentBudgetPeriod(fiscalInfo.getCurrentFiscalPeriod());
                detailSpendView.setCurrentBudgetYear(fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear());
                detailSpendView.setBudgetPeriodStart(String.valueOf(1));
                detailSpendView.setBudgetPeriodEnd(String.valueOf(fiscalInfo.getCurrentFiscalPeriod()));

                detailSpendView.setBusEntityName(site.getBusEntity().getShortDesc());
                detailSpendView.setBusEntityId(site.getSiteId());
                detailSpendView.setBusEntityTypeCd(site.getBusEntity().getBusEntityTypeCd());
                detailSpendView.setSiteStatus(site.getBusEntity().getBusEntityStatusCd());
                detailSpendView.setPostalCode(site.getSiteAddress().getPostalCode());
                detailSpendView.setState(site.getSiteAddress().getStateProvinceCd());
                detailSpendView.setCity(site.getBusEntity().getBusEntityStatusCd());

                detailSpendView.setCostCenterName(ccData.getShortDesc());
                detailSpendView.setCostCenterId(ccData.getCostCenterId());
                detailSpendView.setCostCenterTaxType(ccData.getCostCenterTaxType());

                result.add(detailSpendView);

            } catch (DataNotFoundException e) {
                error(e.getMessage(), e);
            }
        }

        return result;

    }


    public static AssetData getAssetData(int assetId, boolean throwExc) throws Exception {

        Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
        AssetSearchCriteria crit = new AssetSearchCriteria();
        crit.setAssetId(assetId);
        crit.setShowInactive(true);
        AssetDataVector assets = assetEjb.getAssetDataByCriteria(crit);
        if (assets.isEmpty()) {
            if (throwExc) {
                throw new Exception("Asset not found.Asset Id:" + assetId);
            }
        } else if (assets.size() > 1) {
            if (throwExc) {
                throw new Exception("Multiple Asset.Asset Id:" + assetId);
            }
        } else {
            return (AssetData) assets.get(0);
        }
        return null;
    }
    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in " + className + " :: " + message);

        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);

    }

    /**
     *
     */
    public static ActionErrors orderPartsForWorkOrderItem(HttpServletRequest request, UserWorkOrderItemMgrForm mgrForm) {
        ActionErrors ae;
        try {
            ae = checkRequest(request, mgrForm);
            if (ae.size() > 0) {
                return ae;
            }

            //OrderJoinData ojd = new OrderJoinData();
            HttpSession session = request.getSession();

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            ShoppingCartData sc = new ShoppingCartData();
            sc.setUser(appUser.getUser());
            sc.setSite(appUser.getSite());

            WorkOrderItemData currentWorkOrderItem = mgrForm.getWorkOrderItemDetail().getWorkOrderItem();
            sc.setWorkOrderItem(currentWorkOrderItem);
            sc.setIsPersistent(false);

            session.setAttribute(Constants.SHOPPING_CART, sc);
        } catch (Exception e) {
            error(e.getMessage(), e);
        }
        return new ActionErrors();
    }

    public static ActionErrors sort(HttpServletRequest request, UserWorkOrderMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        WorkOrderSiteNameViewVector workOrders = form.getSearchResult();
        if (workOrders == null) {
            return ae;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(workOrders, sortField);
        return ae;
    }

    public static boolean isWOworkflowConfiguredForSite(int siteId) {
        boolean result = false;
        try {
            Workflow workflowEjb = APIAccess.getAPIAccess().getWorkflowAPI();
            WorkflowData wfD = workflowEjb.fetchWorkflowForSite(siteId, RefCodeNames.WORKFLOW_TYPE_CD.WORK_ORDER_WORKFLOW);
            if (wfD != null ) {
                if (RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE.equals(wfD.getWorkflowStatusCd())) {
                    result = true;
                }
            }
        } catch(Exception e) {
        } finally {
            return result;
        }
    }
}
