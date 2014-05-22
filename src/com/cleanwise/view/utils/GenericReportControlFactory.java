package com.cleanwise.view.utils;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Service;
import com.cleanwise.service.api.session.ServiceProviderType;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.i18n.ClwI18nUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import com.cleanwise.view.logic.AnalyticReportLogic;
import javax.servlet.http.HttpSession;

/**
 */
public class GenericReportControlFactory {

    private static String className = "GenericReportControlFactory";

    public static interface GENERIC_REPORT_CONTROL_CD {
        public final String WORK_ORDER_DATE_TYPE   = "WORK_ORDER_DATE_TYPE";
        public final String WORK_ORDER_SEARCH_TYPE = "WORK_ORDER_SEARCH_TYPE";
        public final String WORK_ORDER_STATUS      = "WORK_ORDER_STATUS";
        public final String WORK_ORDER_PRIORITY    = "WORK_ORDER_PRIORITY";
        public final String WORK_ORDER_TYPE        = "WORK_ORDER_TYPE";
        public final String WORK_ORDER_NUMBER      = "WORK_ORDER_NUMBER";

        public final String WORK_ORDER_DIST_ACCT_REF_NUM      = "WORK_ORDER_DIST_ACCT_REF_NUM";
        public final String WORK_ORDER_DIST_SITE_REF_NUM      = "WORK_ORDER_DIST_SITE_REF_NUM";

        public final String REQUESTED_SERVICE      = "REQUESTED_SERVICE";
        public final String SERVICE_PROVIDER       = "SERVICE_PROVIDER";
        public final String BEG_DATE               = "BEG_DATE";
        public final String END_DATE               = "END_DATE";
        public final String BEG_DATE_ACTUAL        = "BEG_DATE_ACTUAL";
        public final String END_DATE_ACTUAL        = "END_DATE_ACTUAL";
        public final String BEG_DATE_ESTIMATE      = "BEG_DATE_ESTIMATE";
        public final String END_DATE_ESTIMATE      = "END_DATE_ESTIMATE";
        public final String RECEIVED_DATE_FROM     = "RECEIVED_DATE_FROM";
        public final String RECEIVED_DATE_TO 	   = "RECEIVED_DATE_TO";
        public final String ACCOUNT                = "ACCOUNT";
        public final String AMOUNT                 = "AMOUNT";
        public final String ACCOUNT_LIST           = "ACCOUNT_LIST";
        public final String CONTRACT               = "CONTRACT";
        public final String DISTRIBUTOR            = "DISTRIBUTOR";
        public final String SITE                   = "SITE";
        public final String SITE_LIST              = "SITE_LIST";
        public final String MANUFACTURER           = "MANUFACTURER";
        public final String MANUFACTURER_LIST      = "MANUFACTURER_LIST";
        public final String ITEM                   = "ITEM";
        public final String ITEM_OPT               = "ITEM_OPT";
        public final String ITEM_LIST              = "ITEM_LIST";
        public final String INTERVAL               = "INTERVAL";
        public final String DATE_TYPE_GROUPING     = "DATE_TYPE_GROUPING";
        public final String SHIPMENT               = "SHIPMENT";
        public final String INCLUSIVE              = "INCLUSIVE";
        public final String STATE                  = "STATE";
        public final String COUNTY                 = "COUNTY";
        public final String CATALOG                = "CATALOG";
        public final String ASSET_NAME             = "ASSET_NAME";
        public final String SERIAL_NUMBER          = "SERIAL_NUMBER";
        public final String MODEL                  = "MODEL";
        public final String ASSET_CATEGORY         = "ASSET_CATEGORY";
        public final String ASSET_MODEL            = "ASSET_MODEL";
        public final String ASSET_NUMBER           = "ASSET_NUMBER";
        public final String CUSTOMER               = "CUSTOMER";

        public static String USER_SELECT             = "USER_SELECT";
        public static String STORE_SELECT            = "STORE_SELECT";
        public static String LOCATE_SITE_MULTI       = "LOCATE_SITE_MULTI";
        public static String LOCATE_ACCOUNT_MULTI    = "LOCATE_ACCOUNT_MULTI";

       public static String DAYS_OPEN    = "DAYS_OPEN";

        // Data Werehouse controls
        public static String DW_USER_SELECT          = "DW_USER_SELECT";
        public static String DW_STORE_SELECT         = "DW_STORE_SELECT";
        public static String DW_LOCATE_SITE_MULTI    = "DW_LOCATE_SITE_MULTI";
        public static String DW_LOCATE_ACCOUNT_MULTI = "DW_LOCATE_ACCOUNT_MULTI";


    }

    public static String OPT = "_OPT";
    public static String DW = "DW_";


    public GenericReportControlView getControl(HttpServletRequest request, String contrloName) throws Exception {
        GenericReportControlView control = GenericReportControlView.createValue();
        control.setName(contrloName);
        initControl(request, control);
        return control;
    }


    public void initControl(HttpServletRequest request, GenericReportControlView control) throws Exception {

        if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.SERVICE_PROVIDER) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.SERVICE_PROVIDER))) {

            initServiceProviderControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_SEARCH_TYPE) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_SEARCH_TYPE))) {

            initWoSearchTypeControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_STATUS) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_STATUS))) {

            initWorkOrderStatusControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_PRIORITY) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_PRIORITY))) {

            initWorkOrderPriorityControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_TYPE) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_TYPE))) {

            initWorkOrderTypeControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_NUMBER) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_NUMBER))) {

            initWorkOrderNumberControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_ACCT_REF_NUM) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_ACCT_REF_NUM))) {

            initWorkOrderDistAccountRefNumberControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_SITE_REF_NUM) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_SITE_REF_NUM))) {

            initWorkOrderDistSiteRefNumberControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.REQUESTED_SERVICE) ||
                control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.REQUESTED_SERVICE))) {

            initWorkOrderReqServiceControl(request, control,isOpt(control.getName()));

        } else if (control.getName().equalsIgnoreCase(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DATE_TYPE)
                || control.getName().equalsIgnoreCase(opt(GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DATE_TYPE))) {

            initWorkOrderDateTypeControl(request, control,isOpt(control.getName()));
        } else if (control.getName().startsWith(this.DW) ){
            initFromSessionFilter(request, control);
        } else if(Utility.isSet(control.getName())){

            initTxtControl(request,control,isOpt(control.getName()));

        }
        initControlLabels(request,control,isOpt(control.getName()));
    }

	private void initTxtControl(HttpServletRequest request, GenericReportControlView control, boolean opt) {
        String name = opt ? (control.getName().substring(0, control.getName().length() - OPT.length())) : (control.getName());
        String message = ClwI18nUtil.getMessageOrNull(request, "report.control." + name + ":");
        if (message != null) {
            control.setLabel(message);
        }
        control.setMandatoryFl(String.valueOf(!opt));
    }
    private void initControlLabels(HttpServletRequest request, GenericReportControlView control, boolean opt) {
      String name = opt ? (control.getName().substring(0, control.getName().length() - OPT.length())) : (control.getName());
      String message = ClwI18nUtil.getMessageOrNull(request, "report.control.label." + name );
      if (!Utility.isSet(control.getLabel()) && message != null) {
        control.setLabel(message);
      }
    }

    private boolean isOpt(String name) {
       return name.endsWith(OPT);
    }

    public String opt(String name){
        return name+OPT;
    }

    private void initWorkOrderDateTypeControl(HttpServletRequest request, GenericReportControlView control, boolean opt) {
        control.setLabel(" ");
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.RADIO);

        PairViewVector invTypeValues = new PairViewVector();

        PairView invTypeValue1 = new PairView(RefCodeNames.WORK_ORDER_DATE_TYPE.ACTUAL, ClwI18nUtil.getMessage(request, "report.text.control.radio.actual", null));
        PairView invTypeValue2 = new PairView(RefCodeNames.WORK_ORDER_DATE_TYPE.ESTIMATE, ClwI18nUtil.getMessage(request, "report.text.control.radio.estimate", null));

        invTypeValues.add(invTypeValue1);
        invTypeValues.add(invTypeValue2);

        control.setDefault(RefCodeNames.WORK_ORDER_DATE_TYPE.ACTUAL);

        control.setChoiceValues(invTypeValues);
        control.setMandatoryFl(String.valueOf(!opt));
    }

    private void initWorkOrderNumberControl(HttpServletRequest request, GenericReportControlView control, boolean opt) {
        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.workOrderNumber:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.TEXT);
        control.setMandatoryFl(String.valueOf(!opt));
    }

    private void initWorkOrderDistAccountRefNumberControl(
			HttpServletRequest request, GenericReportControlView control,
			boolean opt) throws Exception {
        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.workOrderDistAcctRefNum:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.TEXT);
        control.setMandatoryFl(String.valueOf(!opt));

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        PropertyService propertyServiceEjb = factory.getPropertyServiceAPI();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
        boolean isDisplayDistAcctRefNum = Boolean.parseBoolean(propertyServiceEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM));
        control.setIgnore(!isDisplayDistAcctRefNum);

	}

    private void initWorkOrderDistSiteRefNumberControl(
			HttpServletRequest request, GenericReportControlView control,
			boolean opt) throws Exception {
        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.workOrderDistSiteRefNum:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.TEXT);
        control.setMandatoryFl(String.valueOf(!opt));

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        PropertyService propertyServiceEjb = factory.getPropertyServiceAPI();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
        boolean isDisplayDistSiteRefNum = Boolean.parseBoolean(propertyServiceEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM));
        control.setIgnore(!isDisplayDistSiteRefNum);

	}

    private void initWorkOrderReqServiceControl(HttpServletRequest request, GenericReportControlView control, boolean opt) {
        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.requestedService:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.TEXT);
        control.setMandatoryFl(String.valueOf(!opt));
    }

    private void initWorkOrderStatusControl(HttpServletRequest request, GenericReportControlView control, boolean opt) throws Exception {

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        ListService listServiceEJB = factory.getListServiceAPI();

        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.workOrderStatus:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN);
        PairViewVector invTypeValues = new PairViewVector();

        PairView invTypeValue1 = new PairView("", ClwI18nUtil.getMessage(request, "report.text.control.anyOption", null));
        invTypeValues.add(invTypeValue1);

        RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_STATUS_CD", ListService.ORDER_BY_NAME);
        if (!statusCds.isEmpty()) {
            Iterator it = statusCds.iterator();
            while (it.hasNext()) {
                RefCdData status = (RefCdData) it.next();
                String value = status.getShortDesc();
                String label = status.getValue();
                if (Utility.isSet(label)) {
                    PairView invTypeValue = new PairView(value, label);
                    invTypeValues.add(invTypeValue);
                }
            }
        }
        control.setChoiceValues(invTypeValues);
        control.setMandatoryFl(String.valueOf(!opt));

    }

    private void initWorkOrderTypeControl(HttpServletRequest request, GenericReportControlView control, boolean opt) throws Exception {

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        //ListService listServiceEJB = factory.getListServiceAPI();
        ServiceProviderType sptEjb = factory.getServiceProviderTypeAPI();

        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.workOrderType:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN);
        PairViewVector invTypeValues = new PairViewVector();

        PairView invTypeValue1 = new PairView("", ClwI18nUtil.getMessage(request, "report.text.control.anyOption", null));
        invTypeValues.add(invTypeValue1);

        //RefCdDataVector typeCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_TYPE_CD");

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        BusEntityDataVector types = sptEjb.getServiceProviderTypesForStore(appUser.getUserStore().getStoreId(), false);

        if (!types.isEmpty()) {
            Iterator it = types.iterator();
            BusEntityData type;
            while (it.hasNext()) {
                //RefCdData type = (RefCdData) it.next();
                type = (BusEntityData) it.next();
                String value = type.getShortDesc();
                String label = type.getShortDesc();
                if (Utility.isSet(label)) {
                    PairView invTypeValue = new PairView(value, label);
                    invTypeValues.add(invTypeValue);
                }
            }
        }
        control.setChoiceValues(invTypeValues);
        control.setMandatoryFl(String.valueOf(!opt));
    }


    private void initWorkOrderPriorityControl(HttpServletRequest request, GenericReportControlView control, boolean opt) throws Exception {

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        ListService listServiceEJB = factory.getListServiceAPI();

        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.workOrderPriority:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN);
        PairViewVector invTypeValues = new PairViewVector();

        PairView invTypeValue1 = new PairView("", ClwI18nUtil.getMessage(request, "report.text.control.anyOption", null));
        invTypeValues.add(invTypeValue1);

        RefCdDataVector priorityCds = listServiceEJB.getRefCodesCollection("WORK_ORDER_PRIORITY_CD");
        if (!priorityCds.isEmpty()) {
            Iterator it = priorityCds.iterator();
            while (it.hasNext()) {
                RefCdData priority = (RefCdData) it.next();
                String value = priority.getShortDesc();
                String label = priority.getShortDesc();
                if (Utility.isSet(label)) {
                    PairView invTypeValue = new PairView(value, label);
                    invTypeValues.add(invTypeValue);
                }
            }
        }
        control.setChoiceValues(invTypeValues);
        control.setMandatoryFl(String.valueOf(!opt));
    }

    private void initWoSearchTypeControl(HttpServletRequest request, GenericReportControlView control, boolean opt) {

        control.setLabel(" ");
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.RADIO);

        PairViewVector invTypeValues = new PairViewVector();

        PairView invTypeValue1 = new PairView(RefCodeNames.SEARCH_TYPE_CD.CONTAINS, ClwI18nUtil.getMessage(request, "report.text.control.radio.nameContains", null));
        PairView invTypeValue2 = new PairView(RefCodeNames.SEARCH_TYPE_CD.BEGINS, ClwI18nUtil.getMessage(request, "report.text.control.radio.nameBegins", null));

        invTypeValues.add(invTypeValue1);
        invTypeValues.add(invTypeValue2);

        control.setDefault(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);

        control.setChoiceValues(invTypeValues);
        control.setMandatoryFl(String.valueOf(!opt));
    }

    private void initServiceProviderControl(HttpServletRequest request, GenericReportControlView control, boolean opt) throws Exception {

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        Service serviceEjb = factory.getServiceAPI();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

        control.setLabel(ClwI18nUtil.getMessage(request, "report.control.serviceProvider:", null));
        control.setControlTypeCd(RefCodeNames.CONTROL_TYPE_CD.DROP_DOWN);

        PairViewVector invTypeValues = new PairViewVector();

        PairView invTypeValue1 = new PairView("", ClwI18nUtil.getMessage(request, "report.text.control.anyOption", null));
        invTypeValues.add(invTypeValue1);

        BusEntitySearchCriteria busEntityCrit = new BusEntitySearchCriteria();
        busEntityCrit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        busEntityCrit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        busEntityCrit.setSearchForInactive(false);

        ServiceProviderDataVector providers = serviceEjb.getServiceProviderByCriteria(busEntityCrit);

        if (!providers.isEmpty()) {
            Iterator it = providers.iterator();
            while (it.hasNext()) {
                ServiceProviderData provider = (ServiceProviderData) it.next();
                String value = String.valueOf(provider.getBusEntity().getBusEntityId());
                String label = provider.getBusEntity().getShortDesc();
                if (Utility.isSet(label)) {
                    PairView invTypeValue = new PairView(value, label);
                    invTypeValues.add(invTypeValue);
                }
            }
        }

        control.setChoiceValues(invTypeValues);
        control.setMandatoryFl(String.valueOf(!opt));
    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private void error(String message, Exception e) {
        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }

    private void initFromSessionFilter (HttpServletRequest request, GenericReportControlView control) throws Exception {
        //try to populate Generic Controls values from session Filter
        String name = control.getName();
        Object value = AnalyticReportLogic.getControlFilter(request, name);
        if (name.equals(Constants.DW_STORE_FILTER_NAME) && value == null ){
          value = Integer.valueOf(getInitStoreId(request));
          AnalyticReportLogic.setControlFilter(request, name, value);
          control.setValue(String.valueOf(value));
        }
        if (value != null && value instanceof String) {
          control.setValue( (String) AnalyticReportLogic.getControlFilter(request, name));
        }
    }

    private int getInitStoreId (HttpServletRequest request){
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int userId = appUser.getUser().getUserId();
      //init storeId
      int storeId;
      if(appUser.getUserStore() != null){
        storeId = appUser.getUserStore().getStoreId();
      }else if(appUser.getStores() != null && ! appUser.getStores().isEmpty()){
        storeId = ((BusEntityData)appUser.getStores().get(0)).getBusEntityId();
      }else{
        storeId = 0;
      }
      return storeId;
    }
}
