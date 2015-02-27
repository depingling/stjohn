/**
 * Title:        Constants
 * Description:  This is the Constants class with many constants used in the application.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

package com.cleanwise.view.utils;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Constants used in the application.
 *
 */
public final class Constants {

	public static final String USER_SHOP_FORM = "SHOP_FORM";
	public static final String ORDER_OP_DETAIL_FORM = "ORDER_OP_DETAIL_FORM";
	public static final String WORKFLOW_NOTE = "Workflow Note";
	public static final String PENDING_ORDERS_VECTOR = "pending.orders.vector";
	public static final String PREVIOUS_LOCATION_SEARCH_DTO = "previousLocationSearchDto";
	public static final String PREVIOUS_PREVIOUS_ORDER_SEARCH_DTO = "previousPreviousOrderSearchDto";
	public static final String PREVIOUS_PENDING_ORDER_SEARCH_DTO = "previousPendingOrderSearchDto";
	public static final int LOCATION_SEARCH_RESULTS_MAX_DISPLAY = 500;
	public static final String FORWARD_SLASH = "/";
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String PORTAL_ESW = "esw";
	public static final String PORTAL_CLASSIC = "classic";
	public static final String COMPANY_NAME = "orderline";
	public static final String COOKIE_USERNAME = "STJOHN_USERNAME";
	public static final int COOKIE_AGE_ONE_YEAR = 60*60*24*365;
	public static final String ORIGIN_URL="originURL"; //url to return user to on logoff or failed logon.
												//used when someone wants to embed our logon in their page.
	public static final String ORIGIN_URL_MESSAGE="originURLMessage"; //param to be returned to the source
	                                           //origin url when a failed logon occurs.
	public static final String CONTINUATION_STRING = "continuationString";
	public static final String UTF_8 = "UTF-8";
	public static final String CONTENT_TYPE_JSON = "json";
    public static final String ENTRY_SCHEME = "entry.scheme";
    public static final String ENTRY_PORT = "entry.port";
    public static final String SSL_FORWARDED_HEADER = "ssl_forwarded_header";
    public static final String REDIRECT_CONFIDENTIAL_PORT = "redirectConfidentialPort";
    public static final String LISTNING_CONFIDENTIAL_PORT = "servletConfidentialPort";
    public static final String PARAMETER_DESTINATION = "destination";
    public static final String PARAMETER_CATALOG_ITEM_KEY = "catalogItemKey";
    public static final String PARAMETER_OPERATION = "operation";
    public static final String PARAMETER_CONFIRMATION = "confirmation";
    public static final String PARAMETER_PASSWORD_ERROR = "passwordError";
    public static final String PARAMETER_OPERATION_VALUE_INIT = "init";
    public static final String PARAMETER_OPERATION_VALUE_LOGIN = "login";
    public static final String PARAMETER_OPERATION_VALUE_PROXY_LOGIN = "proxyLogin";
    public static final String PARAMETER_OPERATION_VALUE_LOGOUT = "logout";
    public static final String PARAMETER_OPERATION_VALUE_END_SHOPPING = "endShopping";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_CONTENT = "showContent";
    public static final String PARAMETER_OPERATION_VALUE_SEND_PASSWORD = "sendPassword";
    public static final String PARAMETER_OPERATION_VALUE_USE_NEW_UI = "useNewUI";
    public static final String PARAMETER_OPERATION_VALUE_USE_CLASSIC_UI = "useClassicUI";
    public static final String PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG = "shopByCatalog";
    public static final String PARAMETER_OPERATION_VALUE_SHOP_BY_LISTS = "shopByLists";
    public static final String PARAMETER_OPERATION_VALUE_MOVE_PANEL = "movePanel";
    public static final String PARAMETER_OPERATION_VALUE_SHOP_BY_QUICK_ORDER = "shopByQuickOrder";
    public static final String PARAMETER_OPERATION_VALUE_VIEW_CART = "viewCart";
    public static final String PARAMETER_OPERATION_VALUE_VIEW_SCHEDULED_CART = "viewSheduledCart";
    public static final String PARAMETER_OPERATION_VALUE_REMOVE_ITEM_FROM_CART = "removeItem";
    public static final String PARAMETER_OPERATION_VALUE_REMOVE_ALL_FROM_CART = "removeAll";
    public static final String PARAMETER_OPERATION_VALUE_SAVE_CART = "saveCart";
    public static final String PARAMETER_OPERATION_VALUE_CHECK_OUT = "checkOut";
    public static final String PARAMETER_OPERATION_VALUE_PLACE_ORDER = "placeOrder";
    public static final String PARAMETER_OPERATION_VALUE_SELECT_LOCATION_START = "selectLocationStart";
    public static final String PARAMETER_OPERATION_VALUE_SELECT_LOCATION = "selectLocation";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PENDING_ORDERS = "showPendingOrders";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MOST_RECENT_ORDER = "showMostRecentOrder";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PREVIOUS_ORDERS = "showPreviousOrders";
    public static final String PARAMETER_OPERATION_VALUE_SEARCH_PRODUCTS = "searchProducts";
    public static final String PARAMETER_OPERATION_VALUE_SEARCH_ORDERS = "searchOrders";
    public static final String PARAMETER_OPERATION_VALUE_SEARCH_LOCATIONS = "searchLocations";
    public static final String PARAMETER_OPERATION_VALUE_SORT_LOCATIONS = "sortLocations";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MESSAGE = "showMessage";
    public static final String PARAMETER_OPERATION_VALUE_USER_PROFILE = "showUserProfile";
    public static final String PARAMETER_OPERATION_VALUE_UPDATE_USER_PROFILE = "updateUserProfile";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_CHANGE_PASSWORD = "showChangePassword";
    public static final String PARAMETER_OPERATION_VALUE_CANCEL_PASSWORD = "cancelPassword";
    public static final String PARAMETER_OPERATION_VALUE_UPDATE_PASSWORD = "updatePassword";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_ORDER = "showOrder";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_ALL_ORDERS = "showAllOrders";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PRODUCT_LIMITS = "showProductLimits";
    public static final String PARAMETER_OPERATION_VALUE_EDIT_PRODUCT_LIMITS = "editProductLimits";
    public static final String PARAMETER_OPERATION_VALUE_UPDATE_PRODUCT_LIMITS = "updateProductLimits";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PAR_VALUES = "showParValues";
    public static final String PARAMETER_OPERATION_VALUE_UPDATE_PAR_VALUES = "updateParValues";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULES = "showOrderSchedules";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_FUTURE_ORDERS = "showFutureOrders";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PAR_ORDER = "showParOrder";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER = "showCorporateOrder";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PHYSICAL_CART = "showPhysicalCart";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_BUDGETS_REPORTS = "showBudgetsReports";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_ORDERS_REPORTS = "showOrdersReports";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MISC_REPORTS = "showMiscReports";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_STANDARD_FILTER_REPORTS = "showStandardReportsFilter";
    public static final String PARAMETER_OPERATION_VALUE_CHANGE_STANDARD_FILTER_REPORTS = "changeStandardReportsFilter";
    public static final String PARAMETER_OPERATION_VALUE_SEARCH_STANDARD_REPORT = "searchStandardReport";
    public static final String PARAMETER_OPERATION_VALUE_REPORT_REULSTS_SUB_TAB = "reportResultTab";
    public static final String PARAMETER_OPERATION_VALUE_DOWNLOAD_REPORT = "downLoadReport";
    public static final String PARAMETER_OPERATION_VALUE_GENERATE_EXCEL_REPORT = "generateExcelReport";
    public static final String PARAMETER_REPORT_FORMAT = "reportFormat";   
    public static final String PARAMETER_OPERATION_VALUE_SHOW_PRODUCT = "showProduct";
    public static final String PARAMETER_OPERATION_VALUE_ADD_ORDER_COMMENT = "addComment";
    public static final String PARAMETER_OPERATION_VALUE_APPROVE_ORDERS = "approveOrders";
    public static final String PARAMETER_OPERATION_VALUE_REJECT_ORDERS = "rejectOrders";
    public static final String PARAMETER_OPERATION_VALUE_REORDER = "reorder";
    public static final String PARAMETER_OPERATION_VALUE_APPROVE_ORDER = "approveOrder";
    public static final String PARAMETER_OPERATION_VALUE_REJECT_ORDER = "rejectOrder";    
    public static final String PARAMETER_OPERATION_VALUE_MODIFY_ORDER = "modifyOrder";   
    public static final String PARAMETER_OPERATION_VALUE_UPDATE_ORDER = "updateOrder"; 
    public static final String PARAMETER_OPERATION_VALUE_UPDATE_RECEIVE = "updateReceive"; 
    public static final String PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULE = "showOrderSchedule";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER_SCHEDULE = "showCorporateOrderSchedule";
    public static final String PARAMETER_OPERATION_VALUE_START_NEW_ORDER_SCHEDULE = "createNewOrderSchedule";
    public static final String PARAMETER_OPERATION_VALUE_SAVE_ORDER_SCHEDULE = "saveOrderSchedule";
    public static final String PARAMETER_OPERATION_VALUE_DELETE_ORDER_SCHEDULE = "deleteOrderSchedule";
    public static final String PARAMETER_ORDER_ID = "orderId";
    public static final String PARAMETER_ORDER_GUIDE_ID = "orderGuideId";
    public static final String PARAMETER_ORDER_SCHEDULE_ID = "orderSchedulerForm.orderScheduleId";
    public static final String PARAMETER_CURRENT_MESSAGE_MESSAGE_ID = "currentMessage.storeMessageId";
    public static final String PARAMETER_CURRENT_MESSAGE_FORCED_READ = "currentMessage.forcedRead";
    public static final String PARAMETER_CURRENT_MESSAGE_MESSAGE_BODY = "currentMessage.messageBody";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_CHART = "showChart";
    public static final String PARAMETER_FORM_BEAN_NAME = "formBeanName";
    public static final String PARAMETER_BUDGET_CHART_DTO = "budgetChartDto";
    public static final String PARAMETER_MESSAGE_URL = "messageURL";
    public static final String PARAMETER_ADDITIONAL_CSS_STYLES = "additionalCssStyles";
    public static final String PARAMETER_ORIENTATION = "orientation";
    public static final String PARAMETER_OPERATION_VALUE_ADD_ALL_TO_SHOPPING_LIST = "addAllToShoppingList";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MODULE_ASSETS = "showModuleAssets";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MODULE_SERVICES = "showModuleServices";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MODULE_SHOPPING = "showModuleShopping";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MODULE_REPORTING = "showModuleReporting";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MODULE_DOCUMENTATION = "showModuleDocumentation";
    public static final String ACTION_FORM = "ACTION_FORM";
    public static final String PARAMETER_REPORT_NAME = "reportName";
    //STJ-5261
    public static final String PARAMETER_OPERATION_VALUE_SHOW_TEN_MORE_ORDERS = "showTenMoreOrders";	
    public static final String PARAMETER_OPERATION_VALUE_SHOW_TEN_MORE_LOCATIONS = "showTenMoreLocations"; 
    public static final String PARAMETER_OPERATION_VALUE_VIEW_FULL_WEBSITE_SELECT_LOCATION = "viewFullWebsiteSelectLocation";
    
    public static final String PARAMETER_OPERATION_VALUE_RESET_PASSWORD = "resetPassword";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_RESET_PASSWORD = "showResetPassword";
    
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MESSAGES = "showMessages";
    public static final String PARAMETER_OPERATION_VALUE_SORT_MESSAGES = "sortMessages";
    public static final String PARAMETER_OPERATION_VALUE_SHOW_MESSAGE_DETAIL = "showMessageDetail";
    public static final String PARAMETER_OPERATION_VALUE_CREATE_MESSAGE = "createMessage";
    public static final String PARAMETER_OPERATION_VALUE_DELETE_MESSAGE = "deleteMessage";
    public static final String PARAMETER_OPERATION_VALUE_SAVE_MESSAGE = "saveMessage";
    public static final String PARAMETER_OPERATION_VALUE_PUBLISH_MESSAGE = "publishMessage";
    public static final String PARAMETER_OPERATION_VALUE_ADD_TRANSLATION = "addTranslation";
    public static final String PARAMETER_OPERATION_VALUE_DELETE_TRANSLATION = "deleteTranslation";
    public static final String PARAMETER_OPERATION_VALUE_PREVIEW_MESSAGE = "previewMessage";
    public static final String STORE_MESSAGE_SORT_ORDER_ASCENDING = "ascending";
    public static final String STORE_MESSAGE_SORT_ORDER_DESCENDING = "descending";
    public static final String STORE_MESSAGE_SORT_FIELD_MESSAGE_TITLE = "messageTitle";
    public static final String STORE_MESSAGE_SORT_FIELD_MESSAGE_TYPE = "messageType";
    public static final String STORE_MESSAGE_SORT_FIELD_PUBLISHED = "published";
    public static final String STORE_MESSAGE_SORT_FIELD_MOD_BY = "modBy";
    public static final String STORE_MESSAGE_SORT_FIELD_CREATED_DATE = "createdDate";
    public static final String STORE_MESSAGE_SORT_FIELD_POSTED_DATE = "postedDate";
    public static final String STORE_MESSAGE_SORT_FIELD_END_DATE = "endDate";
    
    //constants for the various tabs in the application
    //top level tabs
    public static final String TAB_ASSETS = "assets";
    public static final String TAB_SERVICES = "services";
    public static final String TAB_SHOPPING = "shopping";
    public static final String TAB_REPORTING = "reporting";
    public static final String TAB_DOCUMENTATION = "documentation";
    //subtabs under the shopping tab
    public static final String TAB_DASHBOARD = "dashboard";
    public static final String TAB_PRODUCTS = "products";
    public static final String TAB_ORDERS = "orders";
    public static final String TAB_CONTROLS = "controls";
    public static final String TAB_CONTENT = "content";
    
    //subtabs under the dashboard subtab
    public static final String TAB_PENDING_ORDERS = "pendingOrders";
    public static final String TAB_MOST_RECENT_ORDER = "mostRecentOrder";
    public static final String TAB_PREVIOUS_ORDERS = "previousOrders";
    //subtabs under the reporting tab
    public static final String TAB_ORDERS_REPORTS = "ordersReports";
    public static final String TAB_BUDGETS_REPORTS = "budgetsReports";
    public static final String TAB_MISC_REPORTS = "miscReports";
    
    public static final String SESSION_DATA = "sessionData";
    
  //Begin ESW: Orders tab data related links.
    public static final String PARAMETER_OPERATION_VALUE_FILTER_ALL_ORDERS = "filterAllOrders";
    public static final String PARAMETER_OPERATION_VALUE_FILTER_BUDGETS_REPORT = "filterBudgetsReport";
    public static final String PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT = "filterOrdersReport";
    public static final String PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT_CATEGORIES = "filterOrdersReportCategories";
    public static final String PARAMETER_OPERATION_VALUE_FILTER_GENERIC_REPORT_CATEGORIES = "filterGenericReportCategories";
    public static final String PARAMETER_SHOW_BUDGETS_REPORTS_URL = "budgetsReports";
    public static final String PARAMETER_SHOW_ORDERS_REPORTS_URL = "ordersReports";
    public static final String PARAMETER_SHOW_MISC_REPORTS_URL = "miscReports";
    public static final String PARAMETER_OPERATION_SPECIFY_LOCATIONS = "specifyLocations";
    public static final String PARAMETER_OPERATION_SPECIFY_LOCATIONS_SEARCH = "specifyLocationsSearch";
    public static final String PARAMETER_OPERATION_SPECIFY_ACCOUNTS = "specifyAccounts";
    public static final String PARAMETER_OPERATION_SPECIFY_ACCOUNTS_SEARCH = "specifyAccountsSearch";
    
    public static final String PARAMETER_SHOW_ORDER = "showOrder";
    public static final String PARAMETER_OPERATION_VALUE_SORT_ORDERS = "sortOrders";
   
    //layout orientation values
    public static final String ORIENTATION_VERTICAL = "vertical";
    public static final String ORIENTATION_HORIZONTAL = "horizontal";
    
    //Search filter drop down values
    public static final String ORDERS_ALL_LOCATIONS = "allLocations";
    public static final String ORDERS_SPECIFIED_LOCATIONS = "specifiedLocations";
    public static final String ORDERS_CURRENT_LOCATION = "currentLocation";
    public static final String DATE_RANGE_LAST_PERIOD = "Last Period";
    public static final String DATE_RANGE_CURRENT_PERIOD = "Current Period";
    public static final String DATE_RANGE_30_DAYS = "30 Days";
    public static final String DATE_RANGE_60_DAYS ="60 Days";
    public static final String DATE_RANGE_90_DAYS="90 Days";
    public static final String DATE_RANGE_CUSTOM_RANGE = "custom";
    public static final String ORDER_STATUS_PENDING_APPROVAL = "Pending Approval";
    public static final String ORDER_STATUS_PENDING_DATE = "Pending Date";
    public static final String ORDER_STATUS_PENDING_CONSOLIDATION = "Pending Consolidation";
    public static final String ORDER_STATUS_RECEIVED = "Received";
    public static final String ORDER_STATUS_INVOICED = "Invoiced";
    public static final String ORDER_STATUS_REJECTED = "Rejected";
    public static final String ORDER_STATUS_CANCELED = "Cancelled";
    public static final String ORDER_STATUS_ORDERED = "Ordered";
    public static final String ORDER_STATUS_SHIPPED = "Shipped";
    public static final String ORDER_STATUS_CANCELLED = "Cancelled";
    public static final String ORDER_STATUS_PENDING = "Pending";
    
    public static final String ORDERS_SORT_FIELD_ORDER_NUMBER = "Order Number";
    public static final String ORDERS_SORT_FIELD_PO_NUMBER = "PO Number";
    public static final String ORDERS_SORT_FIELD_ORDER_DATE = "Order Date";
    public static final String ORDERS_SORT_FIELD_STATUS = "Status";
    public static final String ORDERS_SORT_FIELD_LOCATION_NAME = "Location Name";
    public static final String ORDERS_SORT_FIELD_PLACED_BY = "Placed By";
    public static final String ORDERS_SORT_FIELD_PRICE = "Price";
    public static final String ORDERS_SORT_ORDER_ASCENDING = "ascending";
    public static final String ORDERS_SORT_ORDER_DESCENDING = "descending";
    
    public static final int ORDERS_SEARCH_RESULTS_MAX_DISPLAY = 500;
    public static final int ORDER_SCHEDULES_SEARCH_RESULTS_MAX_DISPLAY = 500;
    
    public static final String ORDERS_DATE_RANGE_BEGIN_TIME = "00:00:00";
    public static final String ORDERS_DATE_RAGE_END_TIME = "23:59:59";
    public static final String ORDERS_DATE_RANGE_FORMAT = "mm/dd/yyyy";
    
    public static final String DB_SORT_ORDER_ASCENDING = "ASC";
    public static final String DB_SORT_ORDER_DESCENDING = "DESC";
    
    public static final String REQUEST_ATTRIBUTE_PAGE_GENERATION_TIME = "pageGenerationTime";
    
    //Location Fly Out
    /*public static final String ORDERS_LOCATION_SELECTED_ALL = "All Locations";
    public static final String ORDERS_LOCATION_SELECTED_CURRENT = "Current Location";
    public static final String ORDERS_LOCATION_SELECTED_SELECTED = "Selected Location";*/
    
    //Max length constants
    public static final int MAX_LENGTH_ORDER_PROPERTY_VALUE = 2000;
    public static final int MAX_LENGTH_ORDER_SCHEDULE_CC_EMAIL_VALUE = 255;
    
    //PAR Order 
    public static final String REQUEST_PARAMETER_SHOPPING_CART_INVENTORY = "inventory";
    public static final String REQUEST_PARAMETER_SHOPPING_CART_REGULAR = "regular";
    public static final String REQUEST_PARAMETER_ORDER_ITEMS_BY = "orderBy";    
    public static final String PARAMETER_OPERATION_VALUE_SAVE_PAR_ORDER = "saveParOrder";
    public static final String PARAMETER_OPERATION_VALUE_SAVE_CORPORATE_ORDER = "saveCorporateOrder";
    public static final String PARAMETER_OPERATION_VALUE_SAVE_PHYSICAL_CART = "savePhysicalCart";
    
    //Order Schedule constants
    public static int MONTH_DAY_MIN = 1;
    public static int MONTH_DAY_MAX = 28;
    public static int MONTH_DAY_LAST = 32;
    public static String MULTIPLE_DATE_SEPARATOR = ",";
    
 //End: ESW Orders
    
    public static final String PARAMETER_FUNCTIONALITY_VIEW_SHOPPING_CART = "viewCart";
    public static final String PARAMETER_FUNCTIONALITY_PAR_ORDER = "parOrder";
    
    public static final String LOCATION_SORT_FIELD_NAME = "name";
    public static final String LOCATION_SORT_FIELD_ADDRESS = "address";
    public static final String LOCATION_SORT_FIELD_NUMBER = "number";
    public static final String LOCATION_SORT_FIELD_LAST_VISIT = "lastVisit";
    public static final String LOCATION_SORT_ORDER_ASCENDING = "ascending";
    public static final String LOCATION_SORT_ORDER_DESCENDING = "descending";
    
    public static final String ACCOUNT_SORT_FIELD_NAME = "name";
    public static final String ACCOUNT_SORT_FIELD_CITY = "city";
    public static final String ACCOUNT_SORT_FIELD_STATE = "state";
    public static final String ACCOUNT_SORT_FIELD_TYPE = "type";
    public static final String ACCOUNT_SORT_FIELD_STATUS = "status";
    public static final String ACCOUNT_SORT_ORDER_ASCENDING = "ascending";
    public static final String ACCOUNT_SORT_ORDER_DESCENDING = "descending";
    
    public static final String PAR_VALUE_SORT_FIELD_SKU = "sku";
    public static final String PAR_VALUE_SORT_FIELD_PRODUCT_NAME = "productName";
    public static final String PAR_VALUE_SORT_FIELD_PACK = "pack";
    public static final String PAR_VALUE_SORT_FIELD_UOM = "uom";
    public static final String PAR_VALUE_SORT_FIELD_MODIFED = "sortField_5";
    public static final String PAR_VALUE_SORT_ORDER_ASCENDING = "ascending";
    public static final String PAR_VALUE_SORT_ORDER_DESCENDING = "descending";
    
    public static final String PRODUCT_LIMIT_SORT_FIELD_SKU = "sku";
    public static final String PRODUCT_LIMIT_SORT_FIELD_PRODUCT_NAME = "productName";
    public static final String PRODUCT_LIMIT_SORT_FIELD_PACK = "pack";
    public static final String PRODUCT_LIMIT_SORT_FIELD_UOM = "uom";
    public static final String PRODUCT_LIMIT_SORT_FIELD_LOCATION_MAX_QTY = "locationMaxQty";
    public static final String PRODUCT_LIMIT_SORT_FIELD_ACCOUNT_MAX_QTY = "accountMaxQty";
    public static final String PRODUCT_LIMIT_SORT_FIELD_RESTRICTED_DAYS = "restrictedDays";
    public static final String PRODUCT_LIMIT_SORT_FIELD_MODIFIED = "modified";
    public static final String PRODUCT_LIMIT_SORT_ORDER_ASCENDING = "ascending";
    public static final String PRODUCT_LIMIT_SORT_ORDER_DESCENDING = "descending";    
    
    public static final String PRODUCT_SEARCH_VALUE_ALL = "all";
    public static final String PRODUCT_SEARCH_VALUE_MANUFACTURER = "manufacturer";
    public static final String PRODUCT_SEARCH_VALUE_PRODUCT_NAME = "productName";
    public static final String PRODUCT_SEARCH_VALUE_SKU = "sku";
    public static final String PRODUCT_SEARCH_VALUE_MANUF_SKU = "manufSku";
    public static final String PRODUCT_SEARCH_VALUE_UPC = "upc";
    
    public static final String REPORTING_FILTER_THIS_PERIOD = "thisPeriod";
    public static final String REPORTING_FILTER_LAST_PERIOD = "lastPeriod";
    public static final String REPORTING_FILTER_THIS_FISCAL_YEAR = "thisFiscalYear";
    public static final String REPORTING_FILTER_LAST_FISCAL_YEAR = "lastFiscalYear";
    public static final String REPORTING_FILTER_ALL_MANUFACTURERS = "All";
    public static final String REPORTING_FILTER_ALL_CATEGORIES = "All";
    
    public static final String PREVIOUS_ORDER_DATE_RANGE_VALUE_THIS_PERIOD = "thisPeriod";
    public static final String PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_PERIOD = "lastPeriod";
    public static final String PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_THIRTY_DAYS = "lastThirtyDays";
    public static final String PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_SIXTY_DAYS = "lastSixtyDays";
    public static final String PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_NINETY_DAYS = "lastNinetyDays";
    
    public static final String FUTURE_ORDER_DATE_RANGE_VALUE_CURRENT_PERIOD = "currentPeriod";
    public static final String FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_PERIOD = "nextPeriod";
    public static final String FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_THIRTY_DAYS = "nextThirtyDays";
    public static final String FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_SIXTY_DAYS = "nextSixtyDays";
    public static final String FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_NINETY_DAYS = "nextNinetyDays";
    
    public static final String LOCALE_CODE_OR_PRICE_CURRENCY="localeCodeOrPriceCurrency";
    
    public static final String PAR_ORDER_OPEN_TIME= "12.00 AM";
    public static final int PAR_ORDER_SCHEDULE_ID = -99;
    
    public static final String FUTURE_ORDERS_SORT_FIELD_RELEASE_DATE = "Release Date";

    public static final String MOBILE_CLIENT = "mobileClient";

    public static final String BREAD_CRUMB_NAVIGATOR = "user.breadcrumb";

    public static final String EXTERNAL_LOGON="externalLogon";
    public static final String REQUESTED_DOMAIN="domain";

    public static final String REQUESTED_COUNTRY_CD="COUNTRY";
    public static final String REQUESTED_LANG_CD="LANG";

    public static final String SCHEDULE_PROCESS_START_TIME = "SCHEDULE_PROCESS_START_TIME";
    public static final String SCHEDULE_PROCESS_FINISH_TIME = "SCHEDULE_PROCESS_FINISH_TIME";
    public static final String CORP_SCHED_PROCESS_START_TIME = "CORP_SCHED_PROCESS_START_TIME";
    public static final String CORP_SCHED_PROCESS_FINISH_TIME = "CORP_SCHED_PROCESS_FINISH_TIME";
    public static final String SCH_PROC_BY_REP_START_TIME = "SCH_PROC_BY_REP_START_TIME";
    public static final String SCH_PROC_BY_REP_FINISH_TIME = "SCH_PROC_BY_REP_FINISH_TIME";

    public static final String CATEGORIES_FOR_STORE_FLAG = "CATEGORIES_FOR_STORE_FLAG";
    // Session Filter Names Main DB
    public static String USER_FILTER_NAME = "USER_SELECT";
    public static String STORE_FILTER_NAME = "STORE_SELECT";
    public static String SITE_FILTER_NAME = "LOCATE_SITE_MULTI";
    public static String ACCOUNT_FILTER_NAME = "LOCATE_ACCOUNT_MULTI";
    public static String ITEM_FILTER_NAME = "LOCATE_ITEM_MULTI";
    public static String DISTRIBUTOR_FILTER_NAME = "LOCATE_DISTRIBUTOR_MULTI";
    public static String CATALOG_FILTER_NAME = "LOCATE_CATALOG_MULTI";

    // Session Filter Names for Data Werehouse
    public static String DW_USER_FILTER_NAME = "DW_USER_SELECT";
    public static String DW_STORE_FILTER_NAME = "DW_STORE_SELECT";
    public static String DW_SITE_FILTER_NAME = "DW_LOCATE_SITE_MULTI";
    public static String DW_ACCOUNT_FILTER_NAME = "DW_LOCATE_ACCOUNT_MULTI";
    public static String DW_DISTRIBUTOR_FILTER_NAME = "DW_LOCATE_DISTRIBUTOR_MULTI";

    //struts related constants
    public static final String GLOBAL_FORWARD_LOGON = "/userportal/logon";
    public static final String GLOBAL_FORWARD_ESW_LOGON = "/userportal/esw/logon";
    public static final String GLOBAL_FORWARD_ESW_ERROR = "/userportal/esw/error";
    public static final String GLOBAL_FORWARD_ESW_ERROR_NO_HEADER_NO_FOOTER = "/userportal/esw/errorNoHeaderNoFooter";
    public static final String GLOBAL_FORWARD_ESW_LANDING_PAGE = "/userportal/esw/landingPage";
    public static final String GLOBAL_FORWARD_ESW_DASHBOARD = "/userportal/esw/dashboard";
    public static final String GLOBAL_FORWARD_ESW_ORDERS = "/userportal/esw/orders";
    public static final String GLOBAL_FORWARD_ESW_SHOPPING = "/userportal/esw/shopping";
    public static final String GLOBAL_FORWARD_ESW_RESET_PASSWORD = "/userportal/esw/resetPassword";

    public static final String GLOBAL_ESW_UI = "/userportal/esw/";
    
  /**
   * Shopping page constants
   */
   public static final int SHOP_BY_CATALOG = 10;
   public static final int SHOP_BY_ORDER_GUIDE = 11;
   public static final int SHOP_BY_GREEN_PRODUCT =12 ;
   public static final int SHOP_BY_CATEGORY = 13 ;
   /**
   * Plase order
   */
   public static final byte[] CC_BYTES = {-101,110,-66,9,81,-112,14,4,83,100,63,-115,118,-31,-45,3};

   /** List order constants
   *
   */
   public static final int ORDER_BY_CATEGORY = 0;
   public static final int ORDER_BY_CUST_SKU = 1;
   public static final int ORDER_BY_NAME = 2;
   public static final int ORDER_BY_MFG_NAME = 3;
   public static final int ORDER_BY_MFG_SKU = 4;
   public static final int ORDER_BY_CATEGORY_AND_SKU = 5;
   public static final int ORDER_BY_NAME_DESC = 6;
   public static final int ORDER_BY_CUST_SKU_DESC = 7;
   public static final int ORDER_BY_CUSTOM_CATEGORY = 8;
   public static final int ORDER_BY_GROUP_SIZE = 9;

   public static final int ORDER_DIRECTION_ASC = 0;
   public static final int ORDER_DIRECTION_DESC = 1;

    /**
     *  Appearance constants
     */
    private static final int _tableBaseWidth = 769; //769 - minimum
    private static final int _tableBaseWidth800 = 800; //xpedx

    public static final String TABLEWIDTH=""+_tableBaseWidth;
    public static final String TABLEWIDTH800=""+_tableBaseWidth800;
    public static final String TABLE_BORDER_WIDTH="1";
    public static final String TABLE_BOTTOM_MIDDLE_BORDER_WIDTH=""+(_tableBaseWidth-16);
    public static final String
        TABLEWIDTH1=""+(_tableBaseWidth-2),
        TABLEWIDTH_m2=""+(_tableBaseWidth-2),
        TABLEWIDTH_m4=""+(_tableBaseWidth-4);

    private static final int _globalSpacerLeftWidth = (_tableBaseWidth-8-120-120-120-119-120-8)/2;
    public static final String GLOBAL_SPACER_LEFT_WIDTH = ""+_globalSpacerLeftWidth;
    private static final int _globalSpacerRigthWidth = _tableBaseWidth-8-120-120-120-119-120-8-_globalSpacerLeftWidth;
    public static final String GLOBAL_SPACER_RIGTH_WIDTH = ""+_globalSpacerRigthWidth;
    private static final int _headerAddressWidth = (_tableBaseWidth/10)*7;
    public static final String HEADER_ADDRESS_WIDTH = ""+_headerAddressWidth;
    public static final String HEADER_LAST_LOGIN_WIDTH = ""+(_tableBaseWidth-_headerAddressWidth-2);
    public static final int MAX_PAGE_ITEMS = 20;
    public static final int MAX_INDEX_PAGES = 30;
    public static final int MAX_SITES_TO_RETURN = 1000;
    public static final int MAX_USERS_TO_RETURN = 1000;
    public static final int MAX_ACCOUNTS_TO_RETURN = 200;
    public static final int MAX_ITEMS_TO_RETURN = 200;
    public static final int MAX_ORDER_SCHEDULES_TO_RETURN = 1000;
    public static final int MAX_ASSETS_TO_RETURN = 1000;
    public static final int MAX_ORDER_GUIDES_TO_RETURN = 200;
    public static final int MAX_NOTES_TO_RETURN = 100;
    public static final int MAX_DYNAMIC_LIST_SITES = 100;
    public static final int MAX_GROUPS_TO_RETURN = 200;
    public static final int MAX_CATALOGS_TO_RETURN = 200;
    public static final String NOTES_SORT_EFF_DATE = "Eff_Date";
    /**
     * Yes/No constants.
     */
    public static final String YES   = "YES";
    public static final String NO    = "NO";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String YON   = "Y";
    public static final String NOFF  = "N";

    public static final String UNK    = "unk";
    public static final String TRUE   = "true";
    public static final String FALSE  = "false";

    public static final String ON  = "ON";
    public static final String OFF = "OFF";

    public static final String UNKNOWN = "UNKNOWN";
    public static final String NA = "NA";
    public static final String ID = "ID";

    public static final String NAME_CONTAINS = "nameContains";
    public static final String NAME_BEGINS = "nameBegins";

    public static final Integer MAX_PERIODS = 13; // temporary stop gap

    /**
     * The APIAccess (EJB factory) of the applications, after logon, every call need this.
     * Logoff will remove this from the session.
     */
    public static final String APIACCESS = "APIACCESS";

    /**
     * Known session location of the application user object.
     */
    public static final String APP_USER = "ApplicationUser";
    // Static text for failure to get the APP_USER
    // session object.
    public static final String NO_APP_USER =  "User is not logged in.";

    public static final String UI_CONFIG_CONTEXT = "UI_CONFIG_CONTEXT";

    public static final String ADMIN2_SYSTEM_ERROR = "admin2SystemError";
    /**
     * Known session location of the odd row color if it is defined
     */
    public static final String ODD_ROW_COLOR = "OddRowColor";
    /**
     * Known session location of the even row color if it is defined
     */
    public static final String EVEN_ROW_COLOR = "EvenRowColor";

    /**
     * Known session location of the application user addresses object. Should be removed when address is chosen.
     */
    public static final String APP_USER_SITES = "ApplicationUserSites";

    /**
     * Known session location of the application user type object.
     */
    public static final String USER_TYPE = "UserType";

    /**
     * Known session location of the application user name object.
     */
    public static final String USER_NAME = "LoginUserName";

    /**
     * Known session location of the application user password object.
     */
    public static final String USER_PASSWORD = "LoginUserPassword";

    /**
     * Known session location of the application user password object.
     */
    public static final String USER_SITE_ID = "siteId";


    /**

     * Known session location of the application user id.
     */
    public static final String USER_ID = "LoginUserId";

    /**
     * user login failure count .
     */
    public static final String USER_LOGIN_FAIL_COUNT= "LoginFailureCount";

    /**
     * last successful user login date.
     */
    public static final String USER_LOGIN_DATE= "LoginDate";

    /**
     * Known session location of current catalog id.
     */
    public static final String CATALOG_ID = "CatalogId";

    /**
     * Known session location of current catalog locale.
     */
    public static final String CATALOG_LOCALE = "CatalogLocale";

    /**
     * Known session location of current catalog price decimal digits.
     */
    public static final String CATALOG_DECIMALS = "CatalogDecimals";

    /**
     * Known session location of current catalog name.
     */
    public static final String CATALOG_NAME = "CatalogName";

    /**
     * Known session location of current contract id.
     */
    public static final String CONTRACT_ID = "ContractId";

    /**
     * Known session location of current contract name.
     */
    public static final String CONTRACT_NAME = "ContractName";

    /**
     * Known session location of user preferred sorting of shopping cart items
     */
    public static final String SORT_BY = "SortBy";

    public static final String SORT_FIELD = "sortField";

    /**
     * Known session location of the image path string.
     */
    public static final String IMAGES_PATH = "ImagesPath";

    //orca request
    /**
     * Session attribute which indicates that customer are logging into our system from a Orca
     */
    public static final String ORCA_ACCESS = "orca.access";
    public static final String ORCA_WEB_CLIENT = "orca.web.client";
    public static final String ORCA_SERVER = "orca.server.name";
    public static final String ORCA_SERVER_PORT = "orca.server.port";
    public static final String ORCA_CONTEXT = "orca.context";
    public static final String ORCA_URI_BACK = "orca.uri.back";
    public static final String ORCA_URI_ST_DETAIL = "orca.uri.st.detail";
    public static final String ORCA_ACCESS_TOKEN = "orca.accessToken";
    public static final String SERVICE_TICKET_NUMBERS = "serviceTicketNumbers";

    public interface CLW_FILE_TYPE {
        public static final String IMAGE ="images";
        public static final String MSDS="msds";
        public static final String DED="ded";
        public static final String SPEC="spec";
    }
    public static final String FILE_TEMP_SYMBOL="~";

    public static interface THRESHOLD_VALIDATE_COST_CENTER_REQUEST {
        public static final String NEW_ITEMS = "NEW_ITEMS";
        public static final String REQUEST_ITEMS = "REQUEST_ITEMS";
        public static final String SHOPPING_CART_ITEMS = "SHOPPING_CART_ITEMS";
    }

    /**
     * Known session location of the exception object passed to the
     * global error handler.
     */
    public static final String EXCEPTION_OBJECT	= "ExceptionObject";

    /**
     * Known session locations of shopping cart.
     */
    public static final String
        SHOPPING_CART           = "shoppingCart",
        INVENTORY_SHOPPING_CART = "INVENTORY_SHOPPING_CART",
        PHYSICAL_SHOPPING_CART  = "PHYSICAL_SHOPPING_CART",
        SHOPPING_CART_FORM      = "SHOPPING_CART_FORM";

   public static final String INVENTORY_SHOPPING_CART_FORM = "INVENTORY_SHOPPING_CART_FORM";

    /**
     * Request number parameter name (for order checkings).
     */
    public static final String REQUEST_NUM     = "requestNumber";

    /**
     * Property name (CLW_PRORERTY table) for Note Attachment File Server. The server, which should
     * have the latest version of the attachment
     */
     public static final String NOTE_FILE_SERVER = "NOTE_FILE_SERVER";

     /**
      * Property name (CLW_PROPERTY table) for the property that indicates if the system should
      * check to see if it has previously dealt with a given email subject/recipient pair before
      * sending an email message. If the property value is missing or any value other than false 
      * it will perform the check and only send the message if it has not already sent a message 
      * to the specified recipient with the specified subject.  If the property value is false the 
      * system will send the message regardless of whether or not it has already sent a message to 
      * the specified recipient with the specified subject.
      */
      public static final String PERFORM_EMAIL_SENT_CHECK = "PERFORM_EMAIL_SENT_CHECK";
      
      /*
       * Various email related constants
       */
      public static final String EMAIL_DEFAULT_FROM_ADDRESS = "xadmin.default@cleanwise.com";
      public static final String EMAIL_FORMAT_PLAIN_TEXT = "text/plain";
      public static final String EMAIL_FORMAT_HTML = "text/html";
      public static final String EMAIL_IMPORTANCE_HIGH   = "High";
      public static final String EMAIL_IMPORTANCE_NORMAL = "Normal";
      public static final String EMAIL_IMPORTANCE_LOW    = "Low";

    /**
     * access token Id, used for access token login method
     */
    public static final String ACCESS_TOKEN = "accessToken";

    /**
     * A unique identifier that is used by the customer if they are loging into
     * our system from a remote system and need to maintain this context across
     * both systems.
     */
    public static final String CUSTOMER_SYSTEM_ID = "custSysId";

    /**
     * Used for inline communication where a notification needs to be sent to the customer's
     * system, this would be the URL to use to send this reequest to.
     */
    public static final String CUSTOMER_SYSTEM_URL = "custSysURL";

    /**
     *flag to indicate wheather or not the logout button is avaliable.  For systems that are heavily cusomtized
     *we will disable this button, or forward them to some other website (future).  This is typically only used
     *when inline authentication is used as the user will never see the login screen.
     */
    public static final String CW_LOGOUT_ENABLED = "logOutCW";
    
    /*
     * Constant representing the default site for a user
     */
    public static final String DEFAULT_SITE = "defaultSite";
    
    /*
     * Constant used to check if change location option is provided
     */
    public static final String CHANGE_LOCATION = "changeLocation";
    
    /*
     * Constant representing the default site for a user
     */
    public static final String UNIQUE_NAME = "uniqueName";

    /**
     * Used to cache where the application domain name is stored in the session
     */
    public static final String APPLICATION_DOMAIN = "APPLICATION_DOMAIN";

    /**
     * Country names
     */
    public static final String UNITED_STATES = "USA";
    public static final String CANADA = "Canada";

    /**
     * Next Delivery Data for Site session attribute.
     */
    public static final String NEXT_DELIVERY_DATA = "NextDeliveryData";
    public static final String SIMPLE_TIME_PATTERN = "hh:mm a";
    public static final String SIMPLE_TIME24_PATTERN = "HH:mm:ss";
    public static final String SIMPLE_TIME24_PATTERN1 = "HH:mm";
    public static final String SIMPLE_DATE_PATTERN = "MM/dd/yyyy";
    public static final String SIMPLE_DATETIME_PATTERN = SIMPLE_DATE_PATTERN + " " + SIMPLE_TIME_PATTERN;
    public static final String BASE_TIME_ZONE = "America/New_York";

    /**
     * Default Locale
     * Used for getting messages and labels when locale key is absent
     */
    public static final Locale DEFAULT_LOCALE = Locale.US;


    public static String getCountryName(String pCountryCd) {
  if(pCountryCd==null){
      return "Unknown Country";
  }
  else if(pCountryCd.equals(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES)) {
      return UNITED_STATES;
  }
  else if(pCountryCd.equals(RefCodeNames.ADDRESS_COUNTRY_CD.CANADA)) {
      return CANADA;
  }

  return pCountryCd;
    }

    public static final Date MAX_DATE =
        (new GregorianCalendar(3000,GregorianCalendar.JANUARY,1)).getTime();

    public static Date getCurrentDate() {
  GregorianCalendar cal = new GregorianCalendar();
  cal.setTime(new Date(System.currentTimeMillis()));
  cal = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
  return cal.getTime();
    }
    public static Date getYearBegin() {
  GregorianCalendar cal = new GregorianCalendar();
  cal.setTime(new Date(System.currentTimeMillis()));
  cal = new GregorianCalendar(cal.get(Calendar.YEAR),Calendar.JANUARY,1);
  return cal.getTime();
    }
    public static String date2string (Date pDate) {
  GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(pDate);
    int year = cal.get(Calendar.YEAR);
    String yearS = ""+year;
    if(year>=2000 && year<2100) yearS = yearS.substring(2,4);
    String ss = (cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+yearS;
    return ss;
    }
  //-------------------------------------------------------------------------------
  public static GregorianCalendar createCalendar(String pDateS){
    if(pDateS==null) return null;
    char[] dateA = pDateS.toCharArray();
    int ii=0;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa=='/') break;
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==0 || ii==dateA.length) return null;
    String monthS = new String(dateA,0,ii);
    ii++;
    int ii1=ii;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa=='/') break;
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==ii1 || ii==dateA.length) return null;
    String dayS = new String(dateA,ii1,ii-ii1);
    ii++;
    ii1 = ii;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==ii1) return null;
    String yearS = new String(dateA,ii1,ii-ii1);
    int month = 0;
    int day = 0;
    int year = 0;
    try{
      month = Integer.parseInt(monthS);
      month--;
      day = Integer.parseInt(dayS);
      year = Integer.parseInt(yearS);
      if(year<100) year += 2000;
    } catch(NumberFormatException exc) {
      return null;
    }
    GregorianCalendar calendar = null;
    try{
      calendar = new GregorianCalendar(year,month,day);
    } catch(Exception exc) {
      return null;
    }
    return calendar;
  }

///////////////////////////////////////////////////////
///////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////
    public static class UserRole {
        public static final String
        CONTRACT_ITEMS_ONLY = "CI^",
            SHOW_PRICE = "SP^",
            BROWSE_ONLY = "BO^",
            ON_ACCOUNT = "OA^",
            CREDIT_CARD = "CC^",
            OTHER_PAYMENT = "OPmt^",  // ex: HHS record of call
            PO_NUM_REQUIRED = "PR^",
            SALES_PRESENTATION_ONLY = "SaP^",
            NO_REPORTING = "NR^",
            CAN_EDIT_SHIPTO = "eST^",
            CAN_EDIT_BILLTO = "eBT^",
            REPORTING_MANAGER = "RepM^",				//Aj
            REPORPTING_ASSIGN_ALL_ACCTS = "RepOA^"        //Aj
            ;
    }

    public static String[] xpedxShoppingOrderStatusList = new String[]{
            RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL,
            RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING,
            RefCodeNames.ORDER_STATUS_CD.SHIPPED,
            RefCodeNames.ORDER_STATUS_CD.CANCELLED,
            RefCodeNames.ORDER_STATUS_CD.REJECTED
    };

   public static final String ORDER_STATUS_ALL ="All";

   public static final String XPEDX_CONFIRM_MSG ="xpedx_confirm_msg";

   public static final String SEARCH_POS = "Search POs";

    public static String OCI_HOOK_URL = "OCI_HOOK_URL",
  OCI_TARGET = "OCI_TARGET",
  OCI_BUYER_USERNAME = "OCI_BUYER_USERNAME",
  OCI_VERSION = "OCI_VERSION";

    public static class CwUi {
  public static final String SELECT = "-- Select --";
    }

    public static final String ROOT = "ROOT";
	public static final String DISTRIBUTOR_NAME = "DISTRIBUTOR_NAME";

    public static final String PAGE_VISIT_TIME    = "PageVisitTime";
    public static final String PAGES_STORE_PREFIX = "pages.store.prefix";
    public static final String PAGES_STORE_LOCALE = "pages.store.locale";
    public static final String PAGES_ACCOUNT_ID   = "pages.account.id";

    public static final int AUTOSAGGEST_REQUEST_ROWS = 10;

    public static interface HTML_COLORS {
        public static final String GRAY_25_PERCENT = "#999999";
    }

    public static interface REPORT_FORMAT {
        public static final String PDF   = "PDF";
        public static final String EXCEL = "EXCEL";
        public static final String HTML  = "HTML";
    }
    public static interface REP_DATE_CONST {
       public static final String LAST_MONTH_YEAR_BEGIN = "last_month_year_begin";
       public static final String THIS_YEAR_BEGIN = "this_year_begin";
       public static final String LAST_MONTH_END = "last_month_end";
       public static final String TODAY = "today";
       public static final String LAST_WEEKDAY = "last_weekday";
  }
  public static final int MAX_REPORT_PAGE_SIZE = 65000;
  //--------- report lock -------------------//
  public static final String lockName = "==RunReportLock==";
  public static final String lockMessage = "report.errors.reportIsInProgress";

  //paymetrics
  public static final String PAYMETRICS = "paymetrics";
  public static final String PAYMETRICS_CC = "paymetrics_cc";
  
  //properties file
  public static final String DEFAULT_PROPERTIES_FILE = "defst.default.properties";

  //template related constants
  public static final String FORWARD_TEMPLATE_SEARCH_EMAIL = "templateSearchEmail";
  public static final String FORWARD_TEMPLATE_DETAIL_EMAIL = "templateDetailEmail";
  public static final String FORWARD_TEMPLATE_PREVIEW_EMAIL = "templatePreviewEmail";
  public static final String ATTRIBUTE_FOUND_TEMPLATE_VECTOR = "template.found.vector";
  public static final String ATTRIBUTE_TEMPLATE_FULL_LOCALES_VECTOR = "template.fullLocales.vector";
  public static final String ATTRIBUTE_TEMPLATE_LOCALES_VECTOR = "template.locales.vector";
  public static final String TEMPLATE_TYPE_EMAIL = "EMAIL";
  public static final int TEMPLATE_MAX_SIZE_NAME = 50;
  public static final int TEMPLATE_MAX_SIZE_EMAIL_SUBJECT = 4000;
  public static final String TEMPLATE_EMAIL_PROPERTY_LOCALE = "LOCALE";
  public static final String TEMPLATE_EMAIL_PROPERTY_LOCALE_DEFAULT = "Default";
  public static final String TEMPLATE_EMAIL_PROPERTY_SUBJECT = "SUBJECT";
  public static final String TEMPLATE_ACTION_PREVIEW_START = "previewStart";
  public static final String TEMPLATE_ACTION_GET_DETAIL = "getDetail";
  public static final String TEMPLATE_SEARCH_BY_ID = "id"; 
  public static final String TEMPLATE_SEARCH_BY_NAME_CONTAINS = "nameContains";
  public static final String TEMPLATE_SEARCH_BY_NAME_BEGINS = "nameBegins";
  public static final String TEMPLATE_EMAIL_SYSTEM_ORDER_CONFIRMATION_TEMPLATE_NAME = "SYSTEM_ORDER_CONFIRMATION_TEMPLATE";
  public static final String TEMPLATE_EMAIL_SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE_NAME = "SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE";
  public static final String TEMPLATE_EMAIL_SYSTEM_PENDING_APPROVAL_TEMPLATE_NAME = "SYSTEM_PENDING_APPROVAL_TEMPLATE";
  public static final String TEMPLATE_EMAIL_SYSTEM_REJECTED_ORDER_TEMPLATE_NAME = "SYSTEM_REJECTED_ORDER_TEMPLATE";
  public static final String TEMPLATE_EMAIL_SYSTEM_MODIFIED_ORDER_TEMPLATE_NAME = "SYSTEM_MODIFIED_ORDER_TEMPLATE";
  public static final String ORDER_ID = "orderId";
  public static final String TEMPLATE_OUTPUT_EMAIL_SUBJECT = "Email Subject";
  public static final String TEMPLATE_OUTPUT_EMAIL_BODY = "Email Body";
  //constants that can be used in template/subject insertion strings
  public static final String TEMPLATE_EMAIL_MAP_KEY_ORDER = "order";
  
  //XML processing
  public static final String XML_DOCUMENT_BUILDER_FACTORY = "javax.xml.parsers.DocumentBuilderFactory";
  public static final String XML_SAX_PARSER_FACTORY = "javax.xml.parsers.SAXParserFactory";
  public static final String XML_DOCUMENT_BUILDER_FACTORY_VALUE_XERCES = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
  public static final String XML_SAX_PARSER_FACTORY_VALUE_XERCES = "org.apache.xerces.jaxp.SAXParserFactoryImpl";
  
  //Constants that can be used for site budgets.
  public static final String UNLIMITED = "Unlimited";
  public static final String LIMITED = "limited";

  
  //Constants related to file loader email notification.
  public static final String FILE_PARSING_FAILED = "File parsing failed.";
  public static final String NO_INPUT_DATA_IN_FILE = "No input data in file.";
  public static final String FILE_NAME = "File Name: ";
  public static final String PROCESS_DATE = "Process Date: ";
  public static final String FILE_STATUS = "File Status: ";
  public static final String PROCESSED_SUCCESSFULLY = "PROCESSED SUCCESSFULLY";
  public static final String PROCESSING_FAILED = "FAILED";
  public static final String ERROR = "Error: ";
  public static final String FILE_LOADER_ACKNOWLEDGEMENT = "File Loader Acknowledgement: ";
  public static final String EMAIL_ADDR_NO_REPLY = "No Reply<noreply@veritivcorp.com>";
  public static final String EMAIL_ADDR_ORDERLINE = "orderline@veritivcorp.com";
  public static final String EMAIL_ADDR_EBUSINESS = "ebusiness@veritivcorp.com";
  
  //Constant that can be used for Account Fiscal Calendar.
  public static final String ALL="All";
  
  public static final String EVENT_ID_PROCESS_PARAM = "EVENT_ID";
  public static final String FORCED_PASSWORD_UPDATE = "forcedPasswordUupdate";
  
  
  //Constant that can be used for Location Budget Chart
  public static final String LOCATION_BUDGET_CHART_STATE = "showChart";
  public static final String BUDGET_COLOR = "budgetColor";
  public static final String OVER_COLOR = "overColor";
  public static final String SPENT_COLOR = "spentColor";
  public static final String CART_COLOR = "cartColor";
  public static final String PENDING_COLOR = "pendingColor";
  public static final String UNUSED_COLOR = "unusedColor";
  public static final String FONT_FAMILY = "fontFamily";
  public static final String LOCATION_BUDGET_SHOW_COST_CENTER = "showCC";
  public static final String LOCATION_BUDGET_HIDE_COST_CENTER = "hideCC";
  public static final String DISPLAY_CURRENCY_LEFT = "left";
  public static final String POLLOCK_STORE_GLOBAL_CODE = "USD-4";
  
  public static final String PARAMETER_OPERATION_VALUE_SORT_PRODUCT_CATALOG_ITEMS = "sort";
  public static final String PARAMETER_OPERATION_VALUE_SEARCH_PRODUCT_CATALOG_ITEMS = "search";
  public static final String PARAMETER_OPERATION_VALUE_EXCEL_PRINT = "excelPrintCatalog";
  public static final String PARAMETER_OPERATION_VALUE_PDF_PRINT = "pdfPrintCatalog";
  public static final String PARAMETER_OPERATION_VALUE_NAVIGATE_CATALOG = "navigateCatalog";
  public static final String PARAMETER_CATALOG_KEY = "categoryKey";
  public static final String PARAMETER_OPERATION_VALUE_ADD_TO_CART = "addToCart";
  public static final String PARAMETER_OPERATION_VALUE_ITEM_TO_CART = "itemToCart";
  public static final String PARAMETER_OPERATION_VALUE_ITEM_TO_PAR_ORDER = "itemToParOrder";
  public static final String PARAMETER_OPERATION_VALUE_ITEM_TO_CORPORATE_ORDER = "itemToCorporateOrder";
  
  public static final int MAX_ITEMS_TO_DISPLAY=500;
  public static final int SEARCH_BY_MFG_SKU = 0;
  public static final int SEARCH_BY_CUST_SKU = 1;
  public static final int SEARCH_BY_NAME = 2;
  public static final int SEARCH_BY_MANUFACTURER = 3;
  public static final String PARAMETER_OPERATION_VALUE_SHOPPING_LIST = "shoppingList";
  public static final String PARAMETER_OPERATION_VALUE_SCHEDULED_ORDER = "scheduledOrder";
  public static final String CustSku = "Cust.Sku";
  public static final String MfgSku = "Mfg.Sku";
  public static final String PARAMETER_OPERATION_VALUE_ITEMS = "item";
  public static final String PARAMETER_OPERATION_VALUE_ADD_TO_EXISTING_LIST = "addToExistingShoppingList";
  public static final String PARAMETER_OPERATION_VALUE_CREATE_LIST = "createShoppingList";
  public static final String PARAMETER_OPERATION_VALUE_SAVE_LEFT_MENU_STATE = "saveLeftMenuState";
  public static final String PARAMETER_OPERATION_VALUE_ADD_CATALOG_ITEMS_TO_PAR_ORDER = "addCatalogItemsToParOrder";
  public static final String PARAMETER_OPERATION_VALUE_ADD_CATALOG_ITEMS_TO_CORPORATE_ORDER = "addCatalogItemsToCorporateOrder";
  public static final String PARAMETER_OPERATION_VALUE_ITEM_DOCUMENT_FROM_E3_STORAGE = "itemDocumentFromE3Storage";
  public static final String PARAMETER_OPERATION_VALUE_ITEM_DOCUMENT_FROM_DB= "itemDocumentFromDb";
  public static final String PARAMETER_OPERATION_VALUE_GROUP_ITEMS = "groupItem";
  public static final String PARAMETER_OPERATION_VALUE_CLEAR_QTY = "clearQty";
  public static final String PARAMETER_OPERATION_VALUE_GROUP_ITEMS_CLEAR_QTY = "clearGroupItemQty";
  public static final String PARAMETER_OPERATION_VALUE_GROUP_ITEMS_ADD_TO_CART = "groupItemAddToCart";
  public static final String PARAMETER_OPERATION_VALUE_GROUP_ITEMS_ADD_TO_EXISTING_LIST = "groupItemAddToExistingShoppingList";
  public static final String PARAMETER_OPERATION_VALUE_GROUP_ITEMS_CREATE_LIST = "groupItemCreateShoppingList";
  public static final String PARAMETER_OPERATION_VALUE_GROUP_ITEMS_ADD_CATALOG_ITEMS_TO_CORPORATE_ORDER = "groupItemAddCatalogItemsToCorporateOrder";
  //Constant that can be used for messages
  public final static int MAX_SIZE_MESSAGE_ABSTRACT = 200;
  public final static int MAX_SIZE_MESSAGE_BODY = 4000;
  
  //Shopping List
  public static final String PARAMETER_OPERATION_VALUE_ORDER_GUIDE_SELECT = "orderGuideSelect";
  public static final String PARAMETER_OPERATION_VALUE_ORDER_GUIDE = "orderGuide";
  public static final String PARAMETER_OPERATION_VALUE_DELETE_GUIDE = "deleteOrderGuide";
  public static final String PARAMETER_OPERATION_VALUE_RENAME_ORDER_GUIDE = "renameOrderGuide";
  public static final String PARAMETER_OPERATION_VALUE_SAVE_USER_ORDER_GUIDE = "saveUserOrderGuide";
  public static final String PARAMETER_OPERATION_VALUE_REMOVE_SELECTED_ITEMS = "removeSelectedItems";
  public static final String PARAMETER_OPERATION_VALUE_ADD_TO_CART_FROM_USER_ORDER_GUIDE = "addToCartFromOrderGuide";
  public static final String PARAMETER_OPERATION_VALUE_UPDATE_QUANTITY = "updateQuantity";
  public static final String PARAMETER_OPERATION_VALUE_SEARCH_SHOPPING_LSIT_PRODUCTS = "searchForShoppingListItem";
  public static final String PARAMETER_OPERATION_VALUE_ADD_SHOPPING_LIST_ITEMS_TO_PAR_ORDER="addShoppingListItemsToParOrder";
  public static final String PARAMETER_OPERATION_VALUE_ADD_SHOPPING_LIST_ITEMS_TO_CORPORATE_ORDER="addShoppingListItemsToCorporateOrder";
  public static final String PARAMETER_OPERATION_VALUE_PRINT_OR_EXPORT_SHOPPING_LIST_ITEMS="excelOrPrintShoppingListItems";
  public static final String PARAMETER_OPERATION_VALUE_EXCEL_PRINT_PERS = "excelPrintPers";
  //Order Detail panel
  public static final String PARAMETER_OPERATION_VALUE_UPDATE_ORDER_DETAIL = "updateOrderDetailInfo";
  
  //NewUI: Checkout functionality 
  public static final String CHECK_OUT_PAYMENT_TYPE_PO = "PO";
  public static final String CHECK_OUT_PAYMENT_CREDIT_CARD = "CC";
  public static final String CHECK_OUT_PAYMENT_OTHER = "Other";
  public static final String CHECK_OUT_ORDER_METHOD_WEB = "Web";
  public static final String CHECK_OUT_ORDER_METHOD_EDI = "EDI";
  public static final String CHECK_OUT_ORDER_METHOD_OTHER = "Other";
  public static final String CUSTOMER_REPORT_CATEGORY = "Customer";
  
  public static final String DEFAULT_BEGIN_DATE = "01/01/1900";
  public static final int MAX_SIZE_OF_TEXT_AREA_VALUE = 4000;
  public static final int MAX_SIZE_OF_CUSTOMER_COMMENTS = 1999;
  public static final int MAX_SIZE_OF_SHIPPING_COMMNETS = 1000;
  
  //NewUI: Order Detail functionality 
  public static final int MAX_SIZE_OF_PO_NUM = 255;
  
  //New UI: Standard Reporting Filter
  public static final String REPORT_NAME_ORDER_INFORMATION = "Order Information";
  public static final String REPORT_NAME_INV_ORDERING_ACTIVITY = "Inventory Ordering Activity";
  public static final String REPORT_NAME_BUDGET = "Budget";
  public static final String REPORT_CONTROL_ACCOUNT = "ACCOUNT";
  public static final String REPORT_CONTROL_ACCOUNT_MULTI = "ACCOUNT_MULTI";
  public static final String REPORT_CONTROL_ACCOUNT_MULTI_OPT = "ACCOUNT_MULTI_OPT";
  public static final String REPORT_CONTROL_LOCATE_ACCOUNT_MULTI_OPT = "LOCATE_ACCOUNT_MULTI_OPT";
  public static final String REPORT_CONTROL_STORE = "STORE";
  public static final String REPORT_CONTROL_STORE_OPT = "STORE_OPT";
  public static final String REPORT_CONTROL_STORE_SELECT = "STORE_SELECT";
  public static final String REPORT_CONTROL_SITE = "SITE";
  public static final String REPORT_CONTROL_DATE_FMT = "DATE_FMT";
  public static final String REPORT_CONTROL_CUSTOMER = "CUSTOMER";
  public static final String REPORT_CONTROL_BEG_DATE = "BEG_DATE";
  public static final String REPORT_CONTROL_BEG_DATE_OPT = "BEG_DATE_OPT";
  public static final String REPORT_CONTROL_END_DATE = "END_DATE";
  public static final String REPORT_CONTROL_END_DATE_OPT = "END_DATE_OPT";
  public static final String REPORT_CONTROL_BEG_DATE_ACTUAL = "BEG_DATE_ACTUAL";
  public static final String REPORT_CONTROL_BEG_DATE_ACTUAL_OPT = "BEG_DATE_ACTUAL_OPT";
  public static final String REPORT_CONTROL_END_DATE_ACTUAL = "END_DATE_ACTUAL";
  public static final String REPORT_CONTROL_END_DATE_ACTUAL_OPT = "END_DATE_ACTUAL_OPT";
  public static final String REPORT_CONTROL_BEG_DATE_ESTIMATE = "BEG_DATE_ESTIMATE";
  public static final String REPORT_CONTROL_BEG_DATE_ESTIMATE_OPT = "BEG_DATE_ESTIMATE_OPT";
  public static final String REPORT_CONTROL_END_DATE_ESTIMATE = "END_DATE_ESTIMATE";
  public static final String REPORT_CONTROL_END_DATE_ESTIMATE_OPT = "END_DATE_ESTIMATE_OPT";
  public static final String REPORT_CONTROL_RECEIVED_DATE_FROM = "RECEIVED_DATE_FROM";
  public static final String REPORT_CONTROL_RECEIVED_DATE_FROM_OPT = "RECEIVED_DATE_FROM_OPT";
  public static final String REPORT_CONTROL_RECEIVED_DATE_TO = "RECEIVED_DATE_TO";
  public static final String REPORT_CONTROL_RECEIVED_DATE_TO_OPT = "RECEIVED_DATE_TO_OPT";
  public static final String REPORT_CONTROL_END_YEAR_OPT = "endYear_OPT";
  public static final String REPORT_CONTROL_END_MONTH_OPT = "endMonth_OPT";
  public static final String REPORT_CONTROL_BUDGET_YEAR = "BUDGET_YEAR";
  public static final String REPORT_CONTROL_BUDGET_PERIOD_INFO = "BUDGET_PERIOD_INFO";
  public static final String REPORT_CONTROL_BUDGET_PERIODS_INFO = "BUDGET_PERIODS_INFO";
  public static final String REPORT_CONTROL_SINGLE_DAYS_BACK = "SINGLE_DAYS_BACK_";
  public static final String REPORT_CONTROL_MANUFACTURER = "MANUFACTURER";
  public static final String REPORT_CONTROL_MANUFACTURER_OPT = "MANUFACTURER_OPT";
  public static final String REPORT_CONTROL_DISTRIBUTOR = "DISTRIBUTOR";
  public static final String REPORT_CONTROL_DISTRIBUTOR_OPT = "DISTRIBUTOR_OPT";
  public static final String REPORT_CONTROL_INVOICE_TYPE_2 = "INVOICE_TYPE_2";
  public static final String REPORT_CONTROL_INVOICE_TYPE_2_OPT = "INVOICE_TYPE_2_OPT";
  public static final String REPORT_CONTROL_INVOICE_STATUS_SELECT = "INVOICE_STATUS_SELECT";
  public static final String REPORT_CONTROL_INVOICE_STATUS_SELECT_OPT = "INVOICE_STATUS_SELECT_OPT";
  public static final String REPORT_CONTROL_CATEGORIES_OPT = "CATEGORIES_OPT";
  public static final String REPORT_CONTROL_LOCATE_CATALOG_MULTI = "LOCATE_CATALOG_MULTI";
  public static final String REPORT_CONTROL_LOCATE_CATALOG_MULTI_OPT = "LOCATE_CATALOG_MULTI_OPT";
  public static final String REPORT_STANDARD_FILTER_BUDGET_PERIOD_LABEL = "Budget Period";
  public static final String REPORT_STANDARD_FILTER_BUDGET_PERIODS_LABEL = "Budget Periods";
  public static final String REPORT_STANDARD_FILTER_MANUFACTURER_LABEL = "Select Manufacturer";
  public static final String REPORT_STANDARD_FILTER_DISTRIBUTOR_LABEL = "Select Distributor";
  public static final String REPORT_STANDARD_FILTER_INVOICE_TYPE_LABEL = "Select Invoice Type";
  public static final String REPORT_STANDARD_FILTER_INVOICE_STATUS_LABEL = "Invoice Status";
  public static final String REPORT_STANDARD_FILTER_END_DATE_LABEL = "End Date";
  public static final String REPORT_STANDARD_FILTER_RECEIVED_DATE_FROM_LABEL = "Received Date From";
  public static final String REPORT_STANDARD_FILTER_RECEIVED_DATE_TO_LABEL = "Received Date To";
  public static final String REPORT_STANDARD_FILTER_BEGIN_DATE_LABEL = "Begin Date";
  public static final String REPORT_STANDARD_FILTER_ACTUAL_START_DATE_LABEL = "Actual Start Date";
  public static final String REPORT_STANDARD_FILTER_ACTUAL_END_DATE_LABEL = "Actual End Date";
  public static final String REPORT_STANDARD_FILTER_ESTIMATED_START_DATE_LABEL = "Quoted Start Date";
  public static final String REPORT_STANDARD_FILTER_ESTIMATED_END_DATE_LABEL = "Quoted End Date";
  public static final String REPORT_STANDARD_FILTER_SELECT_A_DATE = "Select a Date";
  public static final String REPORT_NAME_DAILY_BACK_ORDER_REPORT = "Daily Back Order Report";
  public static final long MULTIPLICATION_FACTOR = 1000;
  //ESW/CheckOut
  public static final String FIELD_REQUSTED_DELIVERY_DATE = "Requested Delivery Date";
  public static final String FIELD_PO_NUMBER = "PO Number";
  public static final String FIELD_PROCESS_ORDER_ON_DATE = "Process Order Date";
  public static final String FIELD_EXCLUDE_ORDER_FROM_BUDGET_REQUIRES_APPROVAL = "Exclude Order from Budget (Requires Approval)";
  public static final String FIELD_EXCLUDE_ORDER_FROM_BUDGET = "Exclude Order from Budget";

  public static final String PARAMETER_OPERATION_VALUE_VIEW_WEB_SITE = "viewWebsite";
  public static final String PARAMETER_OPERATION_VALUE_BACK_TO_PENDING_ORDERS = "backToPendingOrders";
  public static final String USERPORTAL_ESW = "userportal/esw";
  public static final int MAX_CATEGORY_ITEM_PIE_PIECES = 12;  
  //
  public static final String SELECT_HEADER = "Select";
  public static final String SELECT_OPTION = "-- Select --";
  public static final String CREATE_LIST_OPTION = "-- Create List --";
  public static final String SHOPPING_LIST_NAME = "List Name";
  public static final String COLUMN_SUB_TOTAL = "Sub Total";
  
  public static final String WEB = "web";
  public static final String MOBILE = "mobile";
  public static final String PARAMETER_OPERATION_VALUE_VIEW_WEB_ORDER_DETAIL = "viewWebsiteOrderDetail";
  
  public static final String PANEL_PRODUCT_CATALOG = "PANEL_PRODUCT_CATALOG";
  public static final String PANEL_SHOPPING_LIST = "PANEL_SHOPPING_LIST";
  public static final String EXPAND_PANEL = "EXPAND_PANEL";
  public static final String COLLAPSE_PANEL = "COLLAPSE_PANEL";
  
  public static String[] STORE_LANGUAGE_LIST = new String[]{
	  "CHINESE",
	  "DUTCH",
	  "ENGLISH",
	  "FRENCH",
	  "GERMAN",
	  "GREEK",
	  "HUNGARIAN",
	  "ITALIAN",
	  "JAPANESE",
	  "POLISH",
	  "SPANISH", 
	  "TURKISH"	  
  };
  
  public static String[] STORE_COUNTRY_LIST = new String[]{	  
	  "AUSTRALIA",
	  "BRAZIL",
	  "CANADA",
	  "CHILE",
	  "UNITED KINGDOM",
	  "UNITED STATES",
	  "USA - POLLOCK"
  };

  public static final String DB_ESCAPE_SYMBOL = "^";
  public static final String PARAMETER_OPERATION_VALUE_ADD_QUICK_SHOP_ITEMS_TO_CART = "addQuickShopItemsToCart";
  public static final String PARAMETER_OPERATION_VALUE_ADD_QUICK_SHOP_RESOLVE_SKU = "quickShopResolveSku";
  public static final String PARAMETER_OPERATION_VALUE_QUICK_SHOP = "quickShop";
  
  public static final String SUBSTITUTION_ACTION = "SubstitutionAction";
  public static interface SUBSTITUTION_ACTION_TYPE {
      public static final String LOGON = "LogOn";
      public static final String LOGOFF = "LogOff";
  }
  
  // Multiple Store Database Schemas
  public static final String CALLER_PARAMETERS_CONTEXT = "CallerParametersContext";
  public static final String JNDI_NAME_PARTIAL = "com.cleanwise.service.api.util.JNDINames.STORE";
  public static final String DATASOURCE_NAME_PARTIAL = "java:/Store";

  public static final String ORDER_ITEM_ACTION_KEY = "order.detail.actionCode.";
  
  public static final String ORDER_PROPERTY_KEY = "pipeline.message.";
  public static final String DEFAULT_DOMAIN = "http://store.connexion-online.com";
  public static final String LOCALHOST = "localhost";
  //STJ-5218 location budget enhancement.
  public static final String TOTAL_BUDGETED = "totalBudgeted";
  public static final String TOTAL_UNBUDGETED = "totalUnbudgeted";
  public static final String BUDGETED_COST_CENTER = "budgetedCostCenter";
  public static final String UNBUDGETED_COST_CENTER = "unbudgetedCostCenter";
  public static final String TOTAL_COMBINED = "totalCombined";
  public static final String COMBINED_COST_CENTER = "combinedCostCenter";
  
  //STJ-5766: Functional areas that needed Specify Location option.
  public static final String SPECIFY_LOCATIONS_ALL_ORDERS="allOrders";
  public static final String SPECIFY_LOCATIONS_ORDERS_AT_A_GLANCE="ordersAtAGlance";
  public static final String SPECIFY_LOCATIONS_BUDGET_AT_A_GLANCE="budgetAtAGlance";
  public static final String SPECIFY_LOCATIONS_STANDARD_REPORTS="standardReports";
  public static final String[] SPECIFY_LOCATIONS_FUNCTIONAL_AREAS = new String[] {
	  CHANGE_LOCATION,
	  SPECIFY_LOCATIONS_ALL_ORDERS,
	  SPECIFY_LOCATIONS_ORDERS_AT_A_GLANCE,
	  SPECIFY_LOCATIONS_BUDGET_AT_A_GLANCE,
	  SPECIFY_LOCATIONS_STANDARD_REPORTS
  };
  
  public static final String PREVIOUS_ORDERS = "previousOrders";
}
