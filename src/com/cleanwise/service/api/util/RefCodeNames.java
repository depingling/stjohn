package com.cleanwise.service.api.util;



/**
 * This class is the central location to store the reference
 * code constants used throughout the system.
 *
 * Copyright:   Copyright (c) 200
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

public interface RefCodeNames {

    public interface WORK_ORDER_PROPERTY_TYPE_CD {
        public static final String CONTACT_INFO = "CONTACT_INFO";
        public static final String WORKFLOW_RULE = "WORKFLOW_RULE";
    }

    public interface WORK_ORDER_CONTACT {
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME  = "LAST_NAME";
        public static final String FAX        = "FAX";
        public static final String COUNTRY    = "COUNTRY";
        public static final String PHONE      = "PHONE";
        public static final String MOBILE     = "MOBILE";
        public static final String ADDRESS1   = "ADDRESS1";
        public static final String ADDRESS2   = "ADDRESS2";
        public static final String CITY       = "CITY";
        public static final String STATE      = "STATE";
        public static final String ZIP        = "ZIP";
        public static final String EMAIL      = "EMAIL";
    }

    public interface STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
        public static final String DELETED = "DELETED";
    }

    public interface WORK_ORDER_DATE_TYPE {
        public static final String ACTUAL   = "Actual";
        public static final String ESTIMATE = "Estimate";
    }

    public interface WORK_ORDER_PROPERTY_STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface PRODUCT_TYPE_CD {
        public static final String INVENTORY = "Inventory";
        public static final String REGULAR   = "Regular";
    }

    public interface ASSET_CONTENT_TYPE_CD {
        public static final String SPEC   = "SPEC";
        public static final String ASSET_IMAGE = "ASSET_IMAGE";
    }

    public interface WARRANTY_DURATION_TYPE_CD {
        public static final String MONTHS   = "MONTHS";
        public static final String YEARS    = "YEARS";
    }

    public interface WORK_ORDER_CONTENT_STATUS_CD{
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface WOI_STATUS_HIST_TYPE_CD {
        public static final String UNKNOWN = "UNKNOWN";
    }

    public interface WORK_ORDER_NOTE_TYPE_CD {
        public static final String UNKNOWN = "UNKNOWN";
        public static final String PROCESS_NOTE = "PROCESS_NOTE";
    }

    public interface WORK_ORDER_ITEM_STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface WORK_ORDER_ACTION_CD{
        public static final String QUOTE   = "REQUEST FOR QUOTE";
        public static final String SERVICE = "REQUEST FOR SERVICE";
    }

    public interface WORK_ORDER_STATUS_HIST_TYPE_CD {
        public static final String UNKNOWN = "UNKNOWN";
    }

    public interface WORK_ORDER_ASSOC_STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface WORK_ORDER_TYPE_CD {
        public static final String EQUIPMENT    = "Equipment";
        public static final String LANDSCAPING  = "Landscaping";
		public static final String JANITORIAL = "Janitorial";
        public static final String ELECTRICAL   = "Electrical";
    }

    public interface WORK_ORDER_PAYMENT_TYPE_CD {
        public static final String BILLED_SERVICE = "BILLED_SERVICE";
        public static final String WARRANTY = "WARRANTY";
        public static final String PM_CONTRACT = "PM_CONTRACT";
    }
    public interface WORK_ORDER_ASSOC_CD {
        public static final String WORK_ORDER_PROVIDER = "WORK_ORDER_PROVIDER";
    }

    public interface WORK_ORDER_CATEGORY_CD {
        public static final String UNKNOWN = "UNKNOWN";
    }

   public interface WORK_ORDER_STATUS_CD {
        public static final String COMPLETED   = "Completed";
        public static final String CANCELLED   = "Cancelled";
        public static final String PENDING_APPROVAL = "Pending Approval" ;
        public static final String NEW_REQUEST = "New Request";
        //public static final String SEND_TO_PROVIDER = "Send To Provider";
        public static final String SENT_TO_PROVIDER = "Sent To Provider";
        public static final String SENDING_TO_PROVIDER = "Sending To Provider";
        public static final String CLOSED   = "Closed";
        public static final String ACCEPTED_BY_PROVIDER = "Accepted by Provider";
        public static final String REJECTED_BY_PROVIDER = "Rejected by Provider";
        public static final String APPROVED = "Approved";
   }

    public interface WORK_ORDER_PRIORITY_CD {
        public static final String LOW    = "Low";
        public static final String MEDIUM = "Medium";
        public static final String HIGH   = "High";
    }

    public interface WARRANTY_STATUS_HIST_TYPE_CD {
        public static final String UNKNOWN="UNKNOWN";
    }

    public interface WARRANTY_ASSOC_STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface WARRANTY_ASSOC_CD {
        public static final String WARRANTY_PROVIDER = "WARRANTY_PROVIDER";
        public static final String WARRANTY_STORE    = "WARRANTY_STORE";
        public static final String SERVICE_PROVIDER  = "SERVICE_PROVIDER";
    }

    public interface WARRANTY_TYPE_CD {
        public static final String LIMITED = "LIMITED";
    }

    public interface WARRANTY_NOTE_TYPE_CD {
        public static final String UNKNOWN="UNKNOWN";
    }

    public interface COVERAGE_IND_CD {
        public static final String UNKNOWN = "UNKNOWN";
    }

    public interface WARRANTY_STATUS_CD  {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface CONTENT_STATUS_CD  {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface ASSET_TYPE_CD  {
        public static final String ASSET = "ASSET";
        public static final String CATEGORY = "CATEGORY";
        public static final String MASTER_ASSET = "MASTER_ASSET";
    }

    public interface ASSET_ASSOC_STATUS_CD  {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface ASSET_STATUS_CD  {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface ASSET_ASSOC_CD {
        public static final String ASSET_STORE= "ASSET_STORE";
        public static final String ASSET_SITE= "ASSET_SITE";
        public static final String ASSET_SERVICE= "ASSET_SERVICE";
    }

    public interface ASSET_PROPERTY_TYPE_CD {
        public static final String LONG_DESC        = "LONG_DESC";
        public static final String IMAGE            = "IMAGE";
        public static final String INACTIVE_REASON  = "INACTIVE_REASON";
        public static final String ACQUISITION_DATE = "ACQUISITION_DATE";
        public static final String ACQUISITION_COST = "ACQUISITION_COST";
        public static final String DATE_IN_SERVICE  = "DATE_IN_SERVICE";
        public static final String DATE_LAST_HOUR_METER_READING = "DATE_LAST_HOUR_METER_READING";
        public static final String LAST_HOUR_METER_READING = "LAST_HOUR_METER_READING";
        public static final String NOTE             = "NOTE";
        public static final String CUSTOM_DESC      = "CUSTOM_DESC";
    }

    public interface ASSET_MANUF_TYPE_CD {
        public static final String OUTER = "OUTER";
        public static final String STORE = "STORE";
    }

    public interface SEARCH_TYPE_CD{
        public static final String ID="id";
        public static final String CONTAINS="contains";
        public static final String BEGINS="begins";
        public static final String EQUALS="equals";
    }

    public interface BUS_ENTITY_TYPE_CD {
        public static final String STORE = "STORE",
                ACCOUNT = "ACCOUNT",
                ACCOUNT_BILLTO = "ACCOUNT_BILLTO",
                SITE = "SITE",
                DISTRIBUTOR = "DISTRIBUTOR",
                MANUFACTURER = "MANUFACTURER",
                SERVICE_PROVIDER = "SERVICE_PROVIDER",
                BUILDING_SVC_CONTRACTOR = "BUILDING_SVC_CONTRACTOR",
                FREIGHT_HANDLER = "FREIGHT_HANDLER",
                DOMAIN_NAME="DOMAIN_NAME",
                CERTIFIED_COMPANY ="CERTIFIED_COMPANY",
                PROVIDER="PROVIDER",
                SERVICE_PROVIDER_TYPE = "SERVICE_PROVIDER_TYPE",
                REGION = "REGION";
    }

    public interface BUS_ENTITY_STATUS_CD {
        public static final String ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }

    public interface SIMPLE_STATUS_CD {
        public static final String ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }

    public interface BLANKET_PO_NUM_TYPE_CD {
        public static final String
                PO_RELEASE = "PO_RELEASE",
                STATIC = "STATIC";
    }

    public interface BUS_ENTITY_ASSOC_CD {
        public static final String
                ACCOUNT_STORE= "ACCOUNT OF STORE",
                BILLTO_FOR_ACCOUNT= "BILLTO FOR ACCOUNT",
                SITE_ACCOUNT="SITE OF ACCOUNT",
                BSC_FOR_SITE="BSC FOR SITE",
                BSC_STORE="BSC OF STORE",
                COST_CENTER_ACCOUNT =  "COST CENTER OF ACCOUNT",
                DISTRIBUTOR_MANUFACTURER = "DISTRIBUTOR - MANUFACTURER",
                SITE_BUDGET_COST_CENTER =  "SITE BUDGET OF COST CENTER",
                DISTRIBUTOR_STORE = "DISTRIBUTOR OF STORE",
                FREIGHT_HANDLER_STORE = "FREIGHT HANDLER OF STORE",
                MANUFACTURER_STORE = "MANUFACTURER OF STORE",
                SERVICE_PROVIDER_STORE = "SERVICE PROVIDER OF STORE",
                SERVICE_PROVIDER_ACCOUNT = "SERVICE PROVIDER OF ACCOUNT",
                SERVICE_PROVIDER_SITE = "SERVICE PROVIDER OF SITE",
                STORE_OF_DOMAIN = "STORE OF DOMAIN",
                DEFAULT_STORE_OF_DOMAIN = "DEFAULT STORE OF DOMAIN",
                DISTRIBUTOR_SITE = "DISTRIBUTOR OF SITE",
                STORE_SERVICE_PROVIDER_TYPE = "STORE SERVICE PROVIDER TYPE",
                SERVICE_PROVIDER_TO_TYPE_ASSOC = "SERVICE PROVIDER TO TYPE ASSOC",
                ENTERPRISE_MANUF_ASSOC = "ENTERPRISE MANUF ASSOC",
				CROSS_STORE_DIST_LINK = "CROSS_STORE_DIST_LINK",
				CROSS_STORE_MANUF_LINK = "CROSS_STORE_MANUF_LINK",
				CROSS_STORE_REGION_LINK = "CROSS_STORE_REGION_LINK",
                REGION_STORE = "REGION OF STORE";
    }

    public static final String DISTRIBUTOR_TYPE_LABEL = "DistTypeLabel";
    public interface DISTRIBUTOR_TYPE_CD {
        public static final String
                NATIONAL = "NATIONAL",
                REGIONAL = "REGIONAL",
                SUB_DISTRIBUTOR = "SUB_DISTRIBUTOR";
    }

    public interface DISTRIBUTOR_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }

    public interface WORKFLOW_ROLE_CD {
        public static final String
                CUSTOMER = "CUSTOMER",
                UNKNOWN = "UNKNOWN",
                ORDER_APPROVER = "ORDER APPROVER";
    }
    public interface WORKFLOW_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIVE = "LIMITED";
    }
    public interface WORKFLOW_TYPE_CD {
        public static final String
                CWSKU = "CW Sku",
                ORDER_WORKFLOW = "ORDER WORKFLOW",
                WORK_ORDER_WORKFLOW = "WORK_ORDER_WORKFLOW";
    }
    public interface WORKFLOW_RULE_TYPE_CD {
        public static final String
                BREAK_POINT = "BreakPoint",
                ORDER_TOTAL = "OrderTotal",
                ORDER_VELOCITY = "OrderVelocity",
                ORDER_SKU = "OrderSku",
                BUDGET_REMAINING_PER_CC = "CostCenterBudget",
                BUDGET_YTD = "BudgetYTD",
                ORDER_SKU_QTY = "OrderSkuQty",
                EVERY_ORDER = "EveryOrder",
                NON_ORDER_GUIDE_ITEM = "NonOrderGuideItem",
                FREIGHT_HANDLER = "FreightType",
                RUSH_ORDER = "RushOrder",
                ITEM_CATEGORY = "ItemCategory",
                ITEM = "Item",
                CATEGORY_TOTAL = "CategoryTotal",
                WORK_ORDER_BUDGET = "WorkOrderBudget",
                WORK_ORDER_TOTAL = "WorkOrderTotal",
                WORK_ORDER_ANY = "WorkOrderAny",
                WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER = "WorkOrderBudgetSpendingForCC",
                ITEM_PRICE = "ItemPrice",
                USER_LIMIT = "UserLimit",
                ORDER_BUDGET_PERIOD_CHANGED = "OrderBudgetPeriodChanged",
                ORDER_EXCLUDED_FROM_BUDGET = "OrderExcludedFromBudget",
                SHOPPING_CONTROLS = "ShoppingControls";
    }
    public interface WORKFLOW_RULE_EXPRESSION {
        public static final String
                GREATER = ">",
                GREATER_OR_EQUAL = ">=",
                LESS = "<",
                LESS_OR_EQUAL = "<=",
                EQUALS = "==",
                SKU_NUM = "SKU_NUM",
                CATEGORY_ID = "CATEGORY_ID",
                DISTR_ID = "DISTR_ID", 
                ITEM_ID = "ITEM_ID",
                SPLIT_ORDER="SPLIT_ORDER",
                INCLUDE_BUYER_LIST="INCLUDE_BUYER_LIST"
               ;
    }

    public interface SHOPPING_CONTROL_ACTION_CD {
        public static final String
                APPLY = "APPLY",
                WORKFLOW = "WORKFLOW";
    }
    
    public interface WORKFLOW_ASSOC_CD {
        public static final String
                APPLY_FOR_GROUP_USERS = "Apply For Group Users",
                SKIP_FOR_GROUP_USERS = "Skip For Group Users";
    }
    public interface RULE_EXPRESSION {
        public static final String
                GREATER = ">",
                GREATER_OR_EQUAL = ">=",
                LESS = "<",
                LESS_OR_EQUAL = "<=",
                EQUALS = "==",
                SKU_NUM = "SKU_NUM",
                SPLIT_ORDER = "SPLIT_ORDER",
                CATEGORY_ID = "CATEGORY_ID",
                DISTR_ID = "DISTR_ID",
                ITEM_ID = "ITEM_ID";
    }

    public interface WORKFLOW_RULE_ACTION {
        public static final String
                SEND_EMAIL = "Send Email",
                FWD_FOR_APPROVAL = "Forward for approval",
                STOP_ORDER = "Hold order for review",
                APPROVE_ORDER = "Approve the order",
                REJECT_ORDER = "Reject the order",
                PENDING_REVIEW = "Pending review",
                DISPLAY_MESSAGE = "Display Message";
    }
    public interface LOCALE_CD {
        public static final String
                EN_US = "en_US",
                EN_GB = "en_GB",
                FR_CA = "fr_CA",
                FR_FR = "fr_FR",
                EN_CA = "en_CA",
                ES_MX = "es_MX",
                ES_US = "es_US",
                NL_NL = "nl_NL", //Netherlands - Dutch
                NL_BE = "nl_BE",
                RU_RU = "ru_RU",
                TR_TR = "tr_TR",
        JA_JA = "ja_JP", //japan
        IT_IT = "it_IT", //italy
        ZH_CN = "zh_CN", //chinese (simplified)
		ES_ES = "es_ES", //spain
                RU="ru",
                es_CL="es_CL",//Spanish Chilie
                DE_DE="de_DE",//Germany German;
                DE_CH="de_CH", //German Swiss;
				FR_CH="fr_CH", //french Swiss...for currency codes
				EN_PH="en_PH", //english Philippines
				PT_BR="pt_BR", //portuguese brazil
                                EN_AU="en_AU", //english Australia
                EL_GR="el_GR",  //greek Greece
				HU_HU="hu_HU", //Hungarian Hungary
                                PL_PL="pl_PL", //Polish Poland
				en_IN="en_IN", //English India
				en_NZ="en_NZ", //English New Zeland
				en_FJ="en_FJ", //English Fiji  
				XX_PIGLATIN="xx-piglatin", //piglatin
                EN_XXPO="en_XXPO", //pollock custom locale
                TS_US="ts_US"; //test locale

    }
    public interface USER_TYPE_CD {
        public static final String
                ADMINISTRATOR = "ADMINISTRATOR",
                SYSTEM_ADMINISTRATOR = "SYSTEM_ADMINISTRATOR",
                STORE_ADMINISTRATOR = "STORE ADMINISTRATOR",
                ACCOUNT_ADMINISTRATOR = "ACCOUNT ADMINISTRATOR",
                CUSTOMER = "CUSTOMER",
                MSB = "MULTI-SITE BUYER",
                ESTORE_CLIENT = "ESTORE CLIENT",
                CUSTOMER_SERVICE = "CUSTOMER SERVICE",
                CRC_MANAGER = "CRC_MANAGER",
                DISTRIBUTOR = "DISTRIBUTOR",
                REPORTING_USER = "REPORTING_USER",
                REGISTRATION_USER = "REGISTRATION_USER",
                SERVICE_PROVIDER = "SERVICE_PROVIDER";
    }
    public interface USER_CLASS_CD {
        public static final String
                END_USER = "END_USER"; // Customers, MSB, Reporting
    }
    public interface CUSTOMER_SERVICE_ROLE_CD {
        public static final String
                VIEWER = "Viewer",
                PUBLISHER = "Publisher",
                APPROVER = "Approver";
    }
    public interface USER_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }
    public interface ACCOUNT_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }
    public interface PHONE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface PHONE_TYPE_CD {
        public static final String
                PHONE = "PHONE",
                FAX = "FAX",
                POFAX = "POFAX",
                ORDERPHONE = "ORDERPHONE",
                ORDERFAX = "ORDERFAX",
                MOBILE = "MOBILE";
    }
    public interface EMAIL_TYPE_CD {
        public static final String
                PRIMARY_CONTACT = "PRIMARY",
                CUSTOMER_SERVICE = "CUSTOMER SERVICE",
                CONTACT_US = "CONTACT US",
                DEFAULT = "DEFAULT",
                ORDER_MANAGER = "ORDER_MANAGER",
                REJECTED_INVOICE	= "REJECTED_INVOICE";
    }
    public interface EMAIL_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface ADDRESS_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface ADDRESS_COUNTRY_CD {
        public static final String
                UNITED_STATES = "UNITED STATES",
                UNITED_KINGDOM = "UNITED KINGDOM",
                CANADA = "CANADA",
                JAPAN="JAPAN",
                NETHERLANDS="NETHERLANDS",
                NEDERLAND="NEDERLAND",
                RUSSIAN_FEDERATION="RUSSIAN FEDERATION",
                TURKEY="TURKEY";
    }
    // Same countries in the list.
    public interface PHONE_COUNTRY_CD
            extends ADDRESS_COUNTRY_CD { }

    public interface COUNTRY_PROPERTY {
        public static final String
                USES_STATE="USES_STATE";
    }

    public interface ADDRESS_TYPE_CD {
        public static final String
                PRIMARY_CONTACT = "PRIMARY CONTACT",
                SHIPPING = "SHIPPING",
                DIST_SHIP_FROM = "DISTRIBUTOR SHIP FROM",
                BILLING = "BILLING",
                ACCOUNT_BILLTO = "ACCOUNT BILLTO",
                CUSTOMER_SHIPPING = "CUSTOMER SHIPPING",
                CUSTOMER_BILLING = "CUSTOMER BILLING",
                SHIP_VIA = "SHIP VIA",
                RETURN_PICKUP = "RETURN PICKUP",
                BRANCH = "BRANCH";
    }
    public interface REF_STATUS_CD {
        public static final String
                ORDERED = "ORDERED",
                DEPRECATED = "DEPRECATED",
                OBSOLETE = "OBSOLETE";
    }

    public interface CONTRACT_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                DELETED = "DELETED",
                ROUTING = "ROUTING",
                INACTIVE = "INACTIVE",
                LIVE = "LIMITED";
    }

    public interface CATALOG_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIVE = "LIMITED",
                LIMITED = "LIMITED";
    }

    public interface CATALOG_TYPE_CD {
        public static final String
                SYSTEM = "SYSTEM",
                STORE = "STORE",
                ACCOUNT = "ACCOUNT",
                GENERIC_SHOPPING = "GENERIC_SHOPPING",
                SHOPPING = "SHOPPING",
                ESTIMATOR = "ESTIMATOR";
    }

    public interface CATALOG_PROPERTY_TYPE_CD {
        public static final String
                UPDATE_PRICE = "UPDATE_PRICE";
    }

    public interface CATALOG_ASSOC_CD {
        public static final String
                CATALOG_STORE ="CATALOG_STORE",
                CATALOG_ACCOUNT ="CATALOG_ACCOUNT",
                CATALOG_SITE ="CATALOG_SITE",
                CATALOG_DISTRIBUTOR ="CATALOG_DISTRIBUTOR",
                CATALOG_MAIN_DISTRIBUTOR ="CATALOG_MAIN_DISTRIBUTOR";
    }
    public interface CATALOG_STRUCTURE_CD {
        public static final String
                CATALOG_PRODUCT ="CATALOG_PRODUCT",
                CATALOG_MULTI_PRODUCT = "CATALOG_MULTI_PRODUCT",
                CATALOG_CATEGORY ="CATALOG_CATEGORY",
                CATALOG_MAJOR_CATEGORY ="CATALOG_MAJOR_CATEGORY",
                CATALOG_SERVICE ="CATALOG_SERVICE" ;
    }

    public interface CATALOG_STRUCTURE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface PROPERTY_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }

    public interface PROPERTY_TYPE_CD {
        public static final String
                GOOGLE_ANALYTICS_ID = "GOOGLE_ANALYTICS_ID",
                ERP_ACCOUNT_NUMBER="ERP_ACCOUNT_NUMBER",
                ASSET_MANAGEMENT="ASSET_MANAGEMENT",
                SITE_PRODUCT_BUNDLE = "SITE_PRODUCT_BUNDLE",
                STORE_PREFIX_CODE = "STORE PREFIX",
//                STORE_PREFIX_NEW = "STORE PREFIX NEW",
                BUDGET_AMOUNT = "BUDGET AMOUNT",
                MANUFACTURER_SPECIALIZATION = "MANUFACTURER SPECIALIZATION",
                ACCOUNT_TYPE = "ACCOUNT_TYPE_CD",
                ORDER_MINIMUM = "ORDER MINIMUM",
                CREDIT_LIMIT = "CREDIT LIMIT",
                CREDIT_RATING = "CREDIT RATING",
                STORE_TYPE = "STORE_TYPE_CD",
                STORE_BUSINESS_NAME = "STORE_BUSINESS_NAME",
                STORE_PRIMARY_WEB_ADDRESS = "STORE_PRIMARY_WEB_ADDRESS",
                EXTRA = "EXTRA",
                BUSINESS_CLASS = "BUSINESS_CLASS_CD",
                WOMAN_OWNED_BUSINESS = "WOMAN_OWNED_BUSINESS_CD",
                MINORITY_OWNED_BUSINESS = "MINORITY_OWNED_BUSINESS_CD",
                LAST_CUST_INVOICE_NUM = "LAST_CUST_INVOICE_NUM",
                PURCH_ORDER_FREIGHT_TERMS = "PURCH_ORDER_FREIGHT_TERMS",
                PURCH_ORDER_DUE_DAYS = "PURCH_ORDER_DUE_DAYS",
                CREATE_ERP_SHIP_TO = "CREATE_ERP_SHIP_TO",
                MAKE_SHIP_TO_BILL_TO = "MAKE_SHIP_TO_BILL_TO",
                SHOW_SCHED_DELIVERY = "SHOW_SCHED_DELIVERY",
                ERP_SHIP_TO_COUNTER = "ERP_SHIP_TO_COUNTER",
                CRC_SHOP = "CRC SHOP",
                FREIGHT_CHARGE_TYPE = "FREIGHT_CHARGE_TYPE",
                COMMENTS = "COMMENTS",
                ORDER_GUIDE_NOTE = "ORDER_GUIDE_NOTE",
                SKU_TAG = "SKU_TAG",
                CRYPTO = "CRYPTO",
                ERROR_ON_OVERCHARGED_FREIGHT = "ERROR_ON_OVERCHARGED_FREIGHT",
                IGNORE_ORDER_MIN_FOR_FREIGHT = "IGNORE_ORDER_MIN_FOR_FREIGHT",
                MINIMUM_ORDER_AMOUNT = "MINIMUM_ORDER_AMOUNT",
                DIST_SMALL_ORDER_FEE = "DIST_SMALL_ORDER_FEE",
                DIST_WEB_INFO = "DIST_WEB_INFO",
                DIST_MAX_INVOICE_FREIGHT = "DIST_MAX_INVOICE_FREIGHT",
                DIST_ACCOUNT_NUMBERS = "DIST_ACCOUNT_NUMBERS",
                SITE_REFERENCE_NUMBER = "SITE_REFERENCE_NUMBER",
                DIST_SITE_REFERENCE_NUMBER = "DIST_SITE_REFERENCE_NUMBER",
                RETURN_REQUEST_NUMBER="RETURN_REQUEST_NUMBER",
                EDI_GS_NUMBER="EDI_GS_NUMBER",  //account property that jd's edi uses,
                FR_ON_INVOICE_CD = "FR_ON_INVOICE_CD",
                FR_ROUTING_CD = "FR_ROUTING_CD",
                INVOICE_LOADING_PRICE_MODEL_CD = "INVOICE_LOADING_PRICE_MODEL_CD",
                INVOICE_AMT_PERCNT_ALLOW_UPPER = "INVOICE_AMT_PERCNT_ALLOW_UPPER", //
                INVOICE_AMT_PERCNT_ALLOW_LOWER = "INVOICE_AMT_PERCNT_ALLOW_LOWER",
                ALLOWED_FRT_SURCHARGE_AMOUNT = "ALLOWED_FRT_SURCHARGE_AMOUNT",
                DO_NOT_ALLOW_INVOICE_EDITS = "DO_NOT_ALLOW_INVOICE_EDITS",
                INVENTORY_SHOPPING = "INVENTORY_SHOPPING",
                INVENTORY_SHOPPING_TYPE = "INVENTORY_SHOPPING_TYPE",
                INVENTORY_SHOP_HOLD_DEL_DATE = "INVENTORY_SHOP_HOLD_DEL_DATE",
                ACCOUNT_ORDER_MAX_WEIGHT = "AccountOrder" ,
                ACCOUNT_ORDER_MAX_CUBE_SIZE = "AccountOrderMaxCubeSize",
                ALLOW_FREIGHT_ON_BACKORDERS = "ALLOW_FREIGHT_ON_BACKORDERS",
                EXCEPTION_ON_TAX_DIFFERENCE = "EXCEPTION_ON_TAX_DIFFERENCE",
                ALLOW_FREIGHT_ON_FH_ORDERS = "ALLOW_FREIGHT_ON_FH_ORDERS",
                PURCHASE_ORDER_COMMENTS = "PURCHASE_ORDER_COMMENTS",
                DISTRIBUTORS_COMPANY_CODE = "DISTRIBUTORS_COMPANY_CODE",
                PRINT_CUST_CONTACT_ON_PO = "PRINT_CUST_CONTACT_ON_PO",
                MAN_PO_ACK_REQUIERED = "MAN_PO_ACK_REQUIERED",
                TARGET_FACILITY_RANK = "TARGET_FACILITY_RANK",
                BYPASS_ORDER_ROUTING = "BYPASS_ORDER_ROUTING",
                DELIVERY_SCHEDULE = "DELIVERY SCHEDULE",
                DISTRIBUTION_CENTER_ID = "DISTRIBUTION_CENTER_ID",
                CURRENT_MANIFEST_ID = "CURRENT_MANIFEST_ID",
                DUNS_NUMBER = "DUNS_NUMBER",
                ORDER_NUMBERING_STORE_ID = "ORDER_NUMBERING_STORE_ID",
                ERP_SYSTEM = "ERP_SYSTEM",
                ERP_STATUS = "ERP_STATUS",
                ACCOUNT_NAME_IN_SITE_ADDRESS="ACCOUNT_NAME_IN_SITE_ADDRESS",
                AUTHORIZED_FOR_RESALE = "AUTHORIZED_FOR_RESALE",
                RESALE_ACCOUNT_ERP_NUM = "RESALE_ACCOUNT_ERP_NUM",
                EDI_SHIP_TO_PREFIX = "EDI_SHIP_TO_PREFIX", //Address id prefix
                CONTACT_US_TYPE_CD = "CONTACT_US_TYPE_CD",
                TARGET_MARGIN = "TARGET_MARGIN", //used for reporting
                NEXT_ORDER_DELIVERY_DATE = "NEXT_ORDER_DELIVERY_DATE",
                NEXT_ORDER_CUTOFF_DATE = "NEXT_ORDER_CUTOFF_DATE",
                NEXT_ORDER_CUTOFF_TIME = "NEXT_ORDER_CUTOFF_TIME",
                ACCOUNT_FIELD_CD = "ACCOUNT_FIELD_CD",
                MASTER_ITEM_FIELD_CD = "MASTER_ITEM_FIELD_CD",
                USER_ACCOUNT_RIGHTS = "USER_ACCOUNT_RIGHTS",
                DISTRIBUTOR_NOTE_TOPIC = "DISTRIBUTOR_NOTE_TOPIC",
                SITE_NOTE_TOPIC = "SITE_NOTE_TOPIC",
                ACCOUNT_NOTE_TOPIC = "ACCOUNT_NOTE_TOPIC",
                NEWS_NOTE_TOPIC = "NEWS_NOTE_TOPIC",
                DIST_INVOICE_NUM_MODIFIER = "DIST_INVOICE_NUM_MODIFIER",
                CUSTOMER_SYSTEM_APPROVAL_CD = "CUSTOMER_SYSTEM_APPROVAL_CD",
                TRACK_PUNCHOUT_ORDER_MESSAGES = "TRACK_PUNCHOUT_ORDER_MESSAGES",
                ACCESS_TOKEN = "ACCESS_TOKEN",
                ALLOW_ORDER_CONSOLIDATION = "ALLOW_ORDER_CONSOLIDATION",
                SHOW_DIST_SKU_NUM = "SHOW_DIST_SKU_NUM",
                REL_PATH_FOR_ARCHIEVE_INT_FILE = "REL_PATH_FOR_ARCHIEVE_INT_FILE",
                REL_PATH_FOR_FAILED_INT_FILE = "REL_PATH_FOR_FAILED_INT_FILE",
                CONSOLIDATED_ORDER_WAREHOUSE = "CONSOLIDATED_ORDER_WAREHOUSE",
                REBATE_PERSENT = "REBATE_PERSENT",
                SCHEDULE_CUTOFF_DAYS = "SCHEDULE_CUTOFF_DAYS",
                CURRENT_INVENTORY_PERIOD = "CURRENT_INVENTORY_PERIOD",
                RUSH_ORDER_CHARGE = "RUSH_ORDER_CHARGE",
                ALLOWED_RUNTIME_ORD_ITM_ACT = "ALLOWED_RUNTIME_ORD_ITM_ACT",
                RESOURCE = "RESOURCE",
                PURCHASE_ORDER_ACCOUNT_NAME = "PURCHASE_ORDER_ACCOUNT_NAME",
                CALL_HOURS = "CALL_HOURS",
                RUNTIME_DISPLAY_NAME = "RUNTIME_DISPLAY_NAME",
                BUDGET_ACCRUAL_TYPE_CD = "BUDGET_ACCRUAL_TYPE_CD",
                EVEN_ROW_COLOR = "EVEN_ROW_COLOR",
                ODD_ROW_COLOR = "ODD_ROW_COLOR",
                AUTO_SKU_ASSIGN = "AUTO_SKU_ASSIGN",
                REQUIRE_EXTERNAL_SYS_LOGON = "REQUIRE_EXTERNAL_SYS_LOGON",
                CUSTOMER_SYSTEM_KEY = "CUSTOMER_SYSTEM_KEY",
                CANCEL_BACKORDERED_LINES = "CANCEL_BACKORDERED_LINES",
                ALLOW_CUSTOMER_PO_NUMBER = "ALLOW_CUSTOMER_PO_NUMBER",
                SHOW_DISTR_NOTES_TO_CUSTOMER = "SHOW_DISTR_NOTES_TO_CUSTOMER",
                HOLD_INVOICE = "HOLD_INVOICE",
                SYSTEM_MESSAGE = "SYSTEM_MESSAGE",
                ORDER_PROCESSING_SPLIT_TAX = "ORDER_PROCESSING_SPLIT_TAX",
                RECEIVING_SYSTEM_INVOICE_CD = "RECEIVING_SYSTEM_INVOICE_CD",
                CHECKOUT_OPTION = "CHECKOUT_OPTION",
                SHARE_ORDER_GUIDES = "SHARE_ORDER_GUIDES",
                PREF_VIEW_DETAIL_COST_CENTER = "PREF_VIEW_DETAIL_COST_CENTER",
                CUSTOMER_REFERENCE_CODE = "CUSTOMER_REFERENCE_CODE",
                TOTAL_FIELD_READONLY = "TOTAL_FIELD_READONLY",
                SHOW_DIST_DELIVERY_DATE = "SHOW_DIST_DELIVERY_DATE",
                SSL_DOMAIN_NAME = "SSL_DOMAIN_NAME",
                DEFAULT = "DEFAULT",
                ACCOUNT_FOLDER = "ACCOUNT_FOLDER",
                ACCOUNT_FOLDER_NEW = "ACCOUNT_FOLDER_NEW",
                SHOW_SPL = "SHOW_SPL",
                HOLD_PO = "HOLD_PO",
                AUTO_ORDER_FACTOR = "AUTO_ORDER_FACTOR",
                ALLOW_USER_CHANGE_PASSWORD = "ALLOW_USER_CHANGE_PASSWORD",
                DIST_INVENTORY_DISPLAY = "DIST_INVENTORY_DISPLAY",
                INVENTORY_LOG = "INVENTORY_LOG",
                INVENTORY_LEDGER_SWITCH = "INVENTORY_LEDGER_SWITCH",
                INVENTORY_PO_SUFFIX="INVENTORY_PO_SUFFIX",
                INVENTORY_OG_LIST_UI = "INVENTORY_OG_LIST_UI",
                ALLOW_PO_NUM_BY_VENDER = "ALLOW_PO_NUM_BY_VENDER",
                USE_REQUEST_PO_AS_DISTR_PO = "USE_REQUEST_PO_AS_DISTR_PO",
                DISTR_PO_TYPE = "DISTR_PO_TYPE",
                CUSTOMER_PO_NUM = "CUSTOMER_PO_NUM",
                INVENTORY_MISSING_NOTIFICATION = "INVENTORY_MISSING_NOTIFICATION",
                INVENTORY_CHECK_PLACED_ORDER ="INVENTORY_CHECK_PLACED_ORDER",
                CART_REMINDER_INTERVAL="CART_REMINDER_INTERVAL",
                HIDE_SHIPPING_COMMENTS="HIDE_SHIPPING_COMMENTS",
                WORKFLOW_EMAIL="WORKFLOW_EMAIL",
                CUST_MAJ="CUST_MAJ",
                ADJUST_QTY_BY_855 = "ADJUST_QTY_BY_855",
                CREATE_ORDER_ITEMS_BY_855 = "CREATE_ORDER_ITEMS_BY_855",
                CREATE_ORDER_BY_855 = "CREATE_ORDER_BY_855",
                AGENT_ID = "Agent_Id",
                GL_TRANSFORMATION_TYPE = "GL_TRANSFORMATION_TYPE",
                EQUAL_COST_AND_PRICE = "EQUAL_COST_AND_PRICE",
                SERVICE_PROVIDER_CD = "SERVICE_PROVIDER_CD",
                DIST_ACCT_REF_NUM = "DIST_ACCT_REF_NUM",
                HIDE_ITEM_MFG = "HIDE_ITEM_MFG",
                ALLOW_MODERN_SHOPPING = "ALLOW_MODERN_SHOPPING",
                UNIQUE_PO_NUM_DAYS = "UNIQUE_PO_NUM_DAYS",
                SHOP_UI_TYPE = "SHOP_UI_TYPE",
                ALLOW_SET_WORKORDER_PO_NUMBER = "ALLOW_SET_WORKORDER_PO_NUMBER",
                CONTACT_US_TOPIC = "CONTACT_US_TOPIC",
                DEFAULT_PROPERTY_ACCOUNT = "DEFAULT_PROPERTY_ACCOUNT",
                OTHER_NAMES = "OTHER_NAMES",
                FAQ_LINK = "FAQ_LINK",
                WORK_ORDER_PO_NUM_REQUIRED = "WORK_ORDER_PO_NUM_REQUIRED",
                ALLOW_BUY_WORK_ORDER_PARTS = "ALLOW_BUY_WORK_ORDER_PARTS",
                USER_ID_CODE = "USER_ID_CODE",
                ALLOW_SITE_LLC = "ALLOW_SITE_LLC",
                ALLOW_REORDER = "ALLOW_REORDER",
                SHOW_INV_CART_TOTAL = "SHOW_INV_CART_TOTAL",
                ALLOW_ORDER_INV_ITEMS = "ALLOW_ORDER_INV_ITEMS",
                REMINDER_EMAIL_SUBJECT = "REMINDER_EMAIL_SUBJECT",
                REMINDER_EMAIL_MSG = "REMINDER_EMAIL_MSG",
                CORPORATE_USER = "CORPORATE_USER",
                SEND_EMAIL_TO_CORPORATE = "SEND_EMAIL_TO_CORPORATE",
                RECEIVE_INV_MISSING_EMAIL = "RECEIVE_INV_MISSING_EMAIL",
                EMAIL_GENERATOR_CLASS = "EMAIL_GENERATOR_CLASS",
                SHOW_MY_SHOPPING_LISTS = "SHOW_MY_SHOPPING_LISTS",
                SHOW_REBILL_ORDER = "SHOW_REBILL_ORDER",
                NOTIFY_ORDER_EMAIL_GENERATOR = "NOTIFY_ORDER_EMAIL_GENERATOR",
                PENDING_APPROV_EMAIL_GENERATOR = "PENDING_APPROV_EMAIL_GENERATOR",
                REJECT_ORDER_EMAIL_GENERATOR = "REJECT_ORDER_EMAIL_GENERATOR",
                CONFIRM_ORDER_EMAIL_GENERATOR = "CONFIRM_ORDER_EMAIL_GENERATOR",
                //constants to store account and store email template selections
                //NOTE: values must be 30 characters or less since they apply to the
                //store, as that is the max width of the property_type_cd column (for stores)
                //these values are placed into that property_type_cd column
                ORDER_CONFIRMATION_EMAIL_TEMPLATE = "ORDER_CONFIRM_EMAIL_TEMPLATE",
                SHIPPING_NOTIFICATION_EMAIL_TEMPLATE = "SHIP_NOTIFY_EMAIL_TEMPLATE",
                PENDING_APPROVAL_EMAIL_TEMPLATE = "PEND_APPROVAL_EMAIL_TEMPLATE",
                REJECTED_ORDER_EMAIL_TEMPLATE = "REJECTED_ORDER_EMAIL_TEMPLATE",
                MODIFIED_ORDER_EMAIL_TEMPLATE = "MODIFIED_ORDER_EMAIL_TEMPLATE",
                //end of email template selections
                ALTERNATE_UI = "ALTERNATE_UI",
                CONTACT_US_CC_ADD = "CONTACT_US_CC_ADD",
                SHOW_EXPRESS_ORDER = "SHOW_EXPRESS_ORDER",
                CONTACT_INFORMATION_TYPE = "CONTACT_INFORMATION_TYPE",
                ORDER_GUIDE_NOT_REQD = "ORDER_GUIDE_NOT_REQD",
                ADD_SERVICE_FEE = "ADD_SERVICE_FEE",
                WORK_ORDER_EMAIL_ADDRESS = "WORK_ORDER_EMAIL_ADDRESS",
                USE_PHYSICAL_INVENTORY = "USE_PHYSICAL_INVENTORY",
                FACILITY_TYPE = "FACILITY_TYPE",
                PENDING_ORDER_NOTIFICATION = "PENDING_ORDER_NOTIFICATION",
                CONNECTION_CUSTOMER = "CONNECTION_CUSTOMER",
                USER_ASSIGNED_ASSET_NUMBER = "USER_ASSIGNED_ASSET_NUMBER",
                BUDGET_THRESHOLD_FL = "BUDGET_THRESHOLD_FL",
                BUDGET_THRESHOLD_TYPE = "BUDGET_THRESHOLD_TYPE",
                DISPLAY_DISTR_ACCT_REF_NUM = "DISPLAY_DISTR_ACCT_REF_NUM",
                DISPLAY_DISTR_SITE_REF_NUM = "DISPLAY_DISTR_SITE_REF_NUM",
                CHECKOUT_FIELD_CD = "CHECKOUT_FIELD_CD",
                ALLOW_SPECIAL_PRTMISSION_ITEMS = "ALLOW_SPECIAL_PRTMISSION_ITEMS",
                IS_PARENT_STORE = "IS_PARENT_STORE",
                PARENT_STORE_ID = "PARENT_STORE_ID",
                STAGE_UNMATCHED = "STAGE_UNMATCHED",
                USE_XI_PAY = "USE_XI_PAY",
                ALLOW_ALTERNATE_SHIP_TO_ADDRESS = "ALLOW_ALTERNATE_SHIP_TO_ADDRESS",
                MODIFY_CUST_PO_NUM_BY_855 = "MODIFY_CUST_PO_NUM_BY_855",
                DEFAULT_SITE = "DEFAULT_SITE",
                RESTRICT_ACCT_INVOICES = "RESTRICT_ACCT_INVOICES",
                USER_NAME_MAX_SIZE = "USER_NAME_MAX_SIZE",
                MSDS_PLUGIN = "MSDS Plug-in",
                ORDER_TOTALS_LAST_TIMESTAMP = "ORDER_TOTALS_LAST_TIMESTAMP",
                USER_UI_PREFERENCE = "USER UI PREFERENCE",
                SUPPORTS_BUDGET = "SUPPORTS_BUDGET",
                ALLOW_CORPORATE_SCHED_ORDER = "ALLOW_CORPORATE_SCHED_ORDER",
                CUTOFF_TIME_EMAIL_REMINDER_CNT = "CUTOFF_TIME_EMAIL_REMINDER_CNT",
                DISPLAY_GENERIC_CONTENT = "DISPLAY_GENERIC_CONTENT",
                CUSTOM_CONTENT_URL = "CUSTOM_CONTENT_URL",
                CUSTOM_CONTENT_EDITOR = "CUSTOM_CONTENT_EDITOR",
                CUSTOM_CONTENT_VIEWER = "CUSTOM_CONTENT_VIEWER",
                ALLOW_MIXED_CATEGORY_AND_ITEM = "ALLOW_MIXED_CATEGORY_AND_ITEM",
                RESET_PASSWORD_UPON_INIT_LOGIN = "RESET_PASSWORD_UPON_INIT_LOGIN",
                RESET_PASSWORD_WITHIN_DAYS = "RESET_PASSWORD_WITHIN_DAYS",
                NOTIFY_PASSWORD_EXPIRY_IN_DAYS = "NOTIFY_PASSWORD_EXPIRY_IN_DAYS";

        // UI properties which apply to both accounts and
        // stores.
        public static final String
                UI_MAINMSG = "UI_MAINMSG",
                UI_TIPSMSG = "UI_TIPSMSG",
                UI_CONTACT_MSG = "UI_CONTACT_MSG",
                UI_CART = "UI_CART",
                UI_TOOLBAR_STYLE = "UI_TOOLBAR_STYLE",
                UI_LOGO1 = "UI_LOGO1",
                UI_LOGO2 = "UI_LOGO2",
                UI_STYLESHEET = "UI_STYLESHEET",
                UI_FOOTER = "UI_FOOTER",
                UI_PAGE_TITLE = "UI_PAGE_TITLE",
                UI_HOME_PAGE_BUTTON_LABEL = "UI_HOME_PAGE_BUTTON_LABEL",
                UI_CUST_SERVICE_ALIAS = "UI_CUST_SERVICE_ALIAS";

        public static final String
                JWOD = "JWOD_CD",
                OTHER_BUSINESS = "OTHER_BUSINESS_CD",
                SUB_CONTRACTOR = "SUB_CONTRACTOR",
                TAXABLE_INDICATOR = "TAXABLE_INDICATOR",
                STORE_ORDER_NUM = "STORE_ORDER_NUM",
                WORK_ORDER_NUM = "WORK_ORDER_NUM",
                PROJECT_CD = "PROJECT_CD",
                TASK_CD = "TASK_CD";

        public static final String
                SITE_COMMENTS = "SITE_COMMENTS",
                SITE_FIELD_CD = "SITE_FIELD_CD",
                SITE_SHIP_MSG = "SITE_SHIP_MSG";

        //user properties that adjust the way the mnifest label is printed out (distributor users)
        public static final String
                MANIFEST_LABEL_WIDTH = "PDF_MANIFEST_LABEL_WIDTH",
                MANIFEST_LABEL_HEIGHT = "PDF_MANIFEST_LABEL_HEIGHT",
                MANIFEST_LABEL_MODE = "PDF_MANIFEST_LABEL_MODE",
                MANIFEST_LABEL_TYPE = "PDF_MANIFEST_LABEL_TYPE";

        public static final String
                PDF_ORDER_STATUS_CLASS = "PDF_ORDER_STATUS_CLASS",
                PDF_ORDER_CLASS = "PDF_ORDER_CLASS";

        public static final String
                ALLOW_CC_PAYMENT  = "ALLOW_CC_PAYMENT";

    }

    public interface BUDGET_ACCRUAL_TYPE_CD {
        public static final String
                BY_PERIOD = "BY_PERIOD",
                BY_FISCAL_YEAR = "BY_FISCAL_YEAR";
    }

    public interface GL_TRANSFORMATION_TYPE {
        public static final String
        SITE_POS6_LZF4 = "SITE POS6 LZF4";
    }

    public interface CONTACT_US_TYPE_CD {
        public static final String
                STORE = "STORE",
                DISTRIBUTOR = "DISTRIBUTOR";
    }

    public interface MANIFEST_LABEL_TYPE_CD {
        public static final String
                USPS_DELIVERY_CONFIRM = "USPS_DELIVERY_CONFIRM",
                CW_SHIP_LABEL = "CW_SHIP_LABEL",
                PLAIN = "PLAIN";
    }

    public interface MANIFEST_LABEL_MODE_CD {
        public static final String
                CONTINUOUS_PRINT_MODE = "CONTINUOUS_PRINT_MODE",
                MULTIPLE_PRINT_MODE = "MULTIPLE_PRINT_MODE";
    }

    public interface ITEM_TYPE_CD {
        public static final String PRODUCT = "PRODUCT";
        public static final String CATEGORY = "CATEGORY";
        public static final String MAJOR_CATEGORY = "MAJOR_CATEGORY";
        public static final String SERVICE ="SERVICE";
        public static final String ITEM_GROUP = "ITEM_GROUP";
    }
    public interface ITEM_STATUS_CD {
        public static final  String  INACTIVE ="INACTIVE" ;
        public static final  String  ACTIVE ="ACTIVE" ;
    }
    public interface ITEM_ASSOC_CD {
        public static final String
                CATEGORY_ANCESTOR_CATEGORY = "CATEGORY_ANCESTOR_CATEGORY",
                CATEGORY_PARENT_CATEGORY = "CATEGORY_PARENT_CATEGORY",
                PRODUCT_PARENT_CATEGORY = "PRODUCT_PARENT_CATEGORY",
                PRODUCT_PARENT_PRODUCT = "PRODUCT_PARENT_PRODUCT",
                CATEGORY_PARENT_PRODUCT = "CATEGORY_PARENT_PRODUCT",
                CATEGORY_MAJOR_CATEGORY = "CATEGORY_MAJOR_CATEGORY",
                MANAGED_ITEM_PARENT = "MANAGED_ITEM_PARENT",
                CROSS_STORE_ITEM_LINK = "CROSS_STORE_ITEM_LINK",
                SERVICE_PARENT_CATEGORY="SERVICE_PARENT_CATEGORY",
                ITEM_GROUP_ITEM = "ITEM_GROUP_ITEM";
    }
    public interface ITEM_MAPPING_CD {
        public static final String
                ITEM_MANUFACTURER = "ITEM_MANUFACTURER",
                ITEM_SERVICE_PROVIDER = "ITEM_SERVICE_PROVIDER",
                ITEM_DISTRIBUTOR = "ITEM_DISTRIBUTOR",
                ITEM_GENERIC_MFG = "ITEM_GENERIC_MFG",
                ITEM_STORE = "ITEM_STORE",
                ITEM_CERTIFIED_COMPANY = "ITEM_CERTIFIED_COMPANY";
    }

    public interface ITEM_MAPPING_ASSOC_CD {
        public static final String
                DIST_GENERIC_MFG = "DIST_GENERIC_MFG";
    }

    public interface ITEM_UOM_CD {
        public static final String
                UOM_CS = "CS",
                UOM_EA = "EA",
                UOM_PK = "PK",
                UOM_BX = "BX",
                UOM_DZ = "DZ",
                UOM_PR = "PR",
                UOM_DR = "DR",
                UOM_TB = "TB", // Added for Lagasse (durval 8/23/2002)
                UOM_RL = "RL", // Added for Lagasse (durval 8/23/2002)
                UOM_DP = "DP", // Added for Lagasse (durval 8/23/2002)
                UOM_BD = "BD", // (vladimir 6/23/2006)
                UOM_PL = "PL", // PAIL
                UOM_BG = "BG",
                UOM_CT = "CT",
                UOM_KT = "KT",
                UOM_OTHER = "OTHER";
    }
    public interface BINARY_DATA_STORAGE_TYPE {
        public static final String
                E3_STORAGE = "E3",
                DATABASE = "DATABASE";
    }
    
    public interface CATEGORY_TYPE_CD {
        public static final String
                TROUBLE_SHOOTING = "TROUBLE_SHOOTING",
                TRAINING_CATALOG = "TRAINING_CATALOG",
                SUPPLIER_PROGRAMS = "SUPPLIER_PROGRAMS",
                SUPERVISOR = "SUPERVISOR",
                STEP_BY_STEP = "STEP_BY_STEP",
                SAFETY_REGULATORY = "SAFETY_REGULATORY",
                ROOT = "ROOT",
                PROD_SPECS = "PROD_SPECS",
                PRODUCT_CATALOG = "PRODUCT_CATALOG",
                ONSITE_TRAINING = "ONSITE_TRAINING",
                ONSITE_CONSULTING = "ONSITE_CONSULTING",
                NEW_EMPLOYEE = "NEW_EMPLOYEE",
                MSDS_CD = "MSDS_CD",
                MSDS = "MSDS",
                EDUCATOR_BY_AREA = "EDUCATOR_BY_AREA",
                DED = "DED";
    }
    public interface EXPRESSION_CD {
        public static final String VALUE="VALUE";
    }
    public interface PRICE_CD {
        public static final String
                CAS80_FTL = "CAS80_FTL",
                CAS80_LIST = "CAS80_LIST",
                FTL_US = "FTL_US",
                LIST_US = "LIST_US";
    }

    public interface ORDER_GUIDE_TYPE_CD {
        public static final String
                ORDER_GUIDE_TEMPLATE = "ORDER_GUIDE_TEMPLATE",
                SITE_ORDER_GUIDE_TEMPLATE = "SITE_ORDER_GUIDE_TEMPLATE",
                BUYER_ORDER_GUIDE = "BUYER_ORDER_GUIDE",
                SHOPPING_CART = "SHOPPING_CART",
                MSDS_CLOSET = "MSDS_CLOSET",
                SPEC_CLOSET = "SPEC_CLOSET",
                DED_CLOSET = "DED_CLOSET",
                ESTIMATOR_ORDER_GUIDE = "ESTIMATOR_ORDER_GUIDE",
                DELETED = "DELETED",
                INVENTORY_CART = "INVENTORY_CART",
                PHYSICAL_CART = "PHYSICAL_CART",
                CUSTOM_ORDER_GUIDE = "CUSTOM_ORDER_GUIDE";
    }

    public interface ACCOUNT_TYPE_CD {
        public static final String
                AIRPORT = 	    "Airport",
                BSC = "BSC",
                BUILDING_SERVICE_CONTRACTOR = "Building Service Contractor",
                CHURCHES = "Churches",
                COMMERCIAL = "Commercial",
                CONVENTION_CENTER =	    "Convention Center",
                DISTRIBUTOR = 	    "Distributor",
                EDUCATION = 	    "Education",
                FOOD_INSTITUTIONAL =    "Food - Institutional",
                FOOD_RETAIL = 	    "Food - Retail",
                FOOD_SERVICE = "Food Service",
                GOVERNMENT = 	    "Government",
                HEALTH_CARE = "Health Care",
                HOSPITALITY = 	    "Hospitality",
                HOSPITAL_ACUTE_CARE  = "Hospital - Acute Care",
                INDUSTRIAL = "Industrial",
                INDUSTRIAL_MANUFACTURING = 	    "Industrial & Manufacturing",
                LODGING = "Lodging",
                MISCELLANEOUS = "Miscellaneous",
                NON_FOOD_RETAIL = 	    "Non-Food - Retail",
                NURSING_HOME = 	    "Nursing Home",
                OFFICE_BUILDING = 	    "Office Building",
                RECREATION = "Recreation",
                REDISTRIBUTOR = "Redistributor",
                RETAIL = "Retail",
                SHOPPING_MALL = 	    "Shopping Mall",
                OTHER = 	    "Other";
    }
    public interface STORE_TYPE_CD {
        public static final String DISTRIBUTOR = "DISTRIBUTOR";
        public static final String MLA = "MLA";
        public static final String OTHER = "OTHER";
        public static final String ENTERPRISE = "ENTERPRISE";
    }
    public interface BUDGET_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface ORDER_BUDGET_TYPE_CD {
        public static final String
                NON_APPLICABLE = "NON_APPLICABLE",
                REBILL = "REBILL";
    }
    public interface BUDGET_TYPE_CD {
        public static final String
                SITE_BUDGET = "SITE BUDGET",
                ACCOUNT_BUDGET = "ACCOUNT BUDGET",
                ACCOUNT_WORK_ORDER_BUDGET = "ACCOUNT WORK ORDER BUDGET",
                SITE_WORK_ORDER_BUDGET = "SITE WORK ORDER BUDGET";
    }
    public interface USER_ASSOC_CD {
        public static final String
                STORE = "STORE",
                ACCOUNT = "ACCOUNT",
                DISTRIBUTOR = "DISTRIBUTOR",
                SITE = "SITE",
                SERVICE_PROVIDER = "SERVICE_PROVIDER";
    }
    public interface BUDGET_PERIOD_CD {
        public static final String
                WEEKLY = "WEEKLY",
                MONTHLY = "MONTHLY",
                QUARTERLY = "QUARTERLY",
                SEMIANNUALLY = "SEMIANNUALLY",
                ANNUALLY = "ANNUALLY";
    }
    public interface BUSINESS_CLASS_CD {
        public static final String
                L = "L - Large",
                S = "S - Small",
                U = "U - Unclassified";
    }
    public interface WOMAN_OWNED_BUSINESS_CD {
        public static final String
                Y = "Y - Yes",
                N = "N - No",
                U = "U - Unclassified";
    }
    public interface MINORITY_OWNED_BUSINESS_CD {
        public static final String
                N = "N - No",
                Y0 = "Y0 - Yes Unclassified",
                Y1 = "Y1 - Yes African American",
                Y2 = "Y2 - Yes Hispanic American",
                Y3 = "Y3 - Yes Native American",
                Y4 = "Y4 - Yes Asian American",
                U = "U - Unclassified";
    }
    public interface JWOD_CD {
        public static final String
                N = "N - No",
                Y0 = "Y0 - Yes Unclassified",
                Y1 = "Y1 - Yes NIB",
                Y2 = "Y2 - Yes NISH",
                U = "U - Unclassified";
    }
    public interface OTHER_BUSINESS_CD {
        public static final String
                N = "N - No",
                U = "U - Unclassified",
                Y0 = "Y0 - Yes Unclassified",
                Y1 = "Y1 - Yes UNICORE";
    }
    public interface PAYMENT_TYPE_CD {
        public static final String
                ACCOUNT = "ACCOUNT",
                MASTER_CARD = "Master Card",
                VISA = "Visa",
                AMERICAN_EXPRESS = "American Express",
                DISCOVER = "Discover";
    }
    public interface CURRENCY_CD {
        public static final String USD = "USD";
    }
    public interface FREIGHT_TABLE_TYPE_CD {
        public static final String
                DOLLARS = "DOLLARS",
                //WEIGHT = "WEIGHT",
                POUND = "POUND",
                KILOGRAMME = "KILOGRAMME",
                DOLLARS_PERCENTAGE = "PERCENTAGE-DOLLARS";
        //WEIGHT_PERCENTAGE = "PERCENTAGE-WEIGHT";
    }
    public interface FREIGHT_TABLE_CHARGE_CD {
        public static final String
                FREIGHT = "FREIGHT",
                DISCOUNT = "DISCOUNT";
    }
    public interface FREIGHT_TABLE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface ORDER_STATUS_CD {
        public static final String
                RECEIVED = "Received",                    // Order received, not processed
                CANCELLED = "Cancelled",                // Cancelled -- anywhere -- END
                DUPLICATED = "Duplicated",              // Duplicated -- anywhere -- END
                PENDING_APPROVAL = "Pending Approval",  // Workflow state
                PENDING_DATE = "Pending Date",  //Change to Ordered when date come
                PENDING_CONSOLIDATION = "Pending Consolidation",     //
                REJECTED = "Rejected",                  // Workflow state -- END
                ORDERED = "Ordered",                    // Order successfully placed
                PENDING_REVIEW = "Pending Review",      // Problem placing order
                PENDING_ORDER_REVIEW = "Pending Order Review",      // cw sku
                INVOICED = "Invoiced",                  // -- END
                PROCESS_ERP_PO = "Process ERP PO",      // Order sent to Lawson, or order generated to send (JWP)
                ERP_REJECTED = "ERP Rejected",          // Problem sending order to Lawson
                ERP_RELEASED = "ERP Released",          // PO retrieved from Lawson
                ERP_RELEASED_PO_ERROR  = "ERP Released PO Error", // Problem getting PO
                ERP_CANCELLED = "ERP Cancelled",        // PO cancelled in Lawson
                SENDING_TO_ERP = "Sending to Erp",    //Order has been selected out of database, but has not gone further (orders should not be in this state for very long (seconds))
                READY_TO_SEND_TO_CUST_SYS = "Ready To Send To Cust System", //Means customer is set up for punch out ordering, and order is ready to send to them
                SENT_TO_CUST_SYSTEM = "Sent To Customer System", //Means customer is set up for punch out ordering, and order has been sent to them
                WAITING_FOR_ACTION = "Waiting For Action", //Means that the order is pending some other action.  Should replace the SENT_TO_CUST_SYSTEM action
                PRE_PROCESSED = "Pre Processed", //any order that does not need to go through the full pipeline
                REFERENCE_ONLY = "REFERENCE ONLY";  //order is not a "real" order, it should not be picked up in UI, or reports

        // Addtional status codes that are not "real".  That is to say an order will
        //display as "Shipped" when all items have been shipped.  This is not a status that is stored in the database.
        public static final String
        	ORDERED_PROCESSING="Ordered-Processing",
        	SHIPPED="Ordered-Shipped",
        	SHIPMENT_RECEIVED="Shipment Received",
        	ON_HOLD="On Hold";
    }
    public interface ORDER_ITEM_STATUS_CD {
        public static final String
                CANCELLED = "CANCELLED",                // Line item cancelled
                PENDING_ERP_PO = "PENDING_ERP_PO",      // Covers ORDERED+PROCESS_ERP_PO above
                PENDING_REVIEW = "PENDING_REVIEW",      // Problem placing order
                PENDING_FULFILLMENT = "PENDING_FULFILLMENT", // PO retrieved from Lawson
                SENT_TO_DISTRIBUTOR = "SENT_TO_DISTRIBUTOR", // PO sent to distributor
                PENDING_FULFILLMENT_PROCESSING = "PENDING_FULFILLMENT_PROCESSING",//po was picked up for transmittal and should be in the client program processing
                PO_ACK_SUCCESS = "PO_ACK_SUCCESS", // 850 accepted
                PO_ACK_ERROR = "PO_ACK_ERROR", // 850 accepted with error
                PO_ACK_REJECT = "PO_ACK_REJECT", // 850 rejected
                SENT_TO_DISTRIBUTOR_FAILED= "SENT_TO_DISTRIBUTOR_FAILED", // Problem sending PO
                INVOICED = "INVOICED";                  // Cust invoice received from Lawson
    }
    public interface PURCHASE_ORDER_STATUS_CD {
        public static final String
                PENDING_FULFILLMENT = "PENDING_FULFILLMENT",  // PO retrieved from Lawson
                SENT_TO_DISTRIBUTOR = "SENT_TO_DISTRIBUTOR", // PO sent to distributor
                PENDING_FULFILLMENT_PROCESSING = "PENDING_FULFILLMENT_PROCESSING", //po was picked up for transmittal and should be in the client program processing
                SENT_TO_DISTRIBUTOR_FAILED = "SENT_TO_DISTRIBUTOR_FAILED", // Problem sending PO
                DIST_ACKD_PURCH_ORDER = "DIST_ACKD_PO", // Distributor has acknowledge they recieved data
                CANCELLED = "CANCELLED"; //indicates the po has been cancelled
    }

    public interface PURCH_ORD_MANIFEST_STATUS_CD {
        public static final String
                PENDING_MANIFEST = "PENDING_MANIFEST", //po has not yet been manifested, this is synonymous with null
                MANIFESTED = "MANIFESTED"; //po has been manifested by the distributor
    }

    public interface MANIFEST_ITEM_STATUS_CD {
        public static final String
                INITIATED = "INITIATED",
                READY_TO_SEND = "READY_TO_SEND",
                SENT_TO_FREIGHT_HANDLER = "SENT_TO_FREIGHT_HANDLER",
                RECONCILED_FREIGHT_HANDLER = "RECONCILED_FREIGHT_HANDLER",
                RECONCILED_FREIGHT_HANDLER_ERR = "RECONCILED_FREIGHT_HANDLER_ERR";
    }

    public interface ORDER_SOURCE_CD {
        public static final String
                EDI_850 = "EDI",
                FAX = "Fax",
                MAIL = "Mail",
                EMAIL = "Email",
                TELEPHONE = "Phone",
                WEB = "Web",
                LAW = "Law",
                INVENTORY = "Inventory",
                EXTERNAL = "External",  //came through a loader of some kind
                OTHER = "Other",
                SCHEDULER = "Scheduler";
    }

    public interface SYS_ORDER_SOURCE_CD {
        public static final String
                INVENTORY = "Inventory";
    }

    public interface ORDER_TYPE_CD {
        public static final String
                CONSOLIDATED = "CONSOLIDATED",
                TO_BE_CONSOLIDATED = "TO_BE_CONSOLIDATED",
                BATCH_ORDER = "BATCH_ORDER",
                SPLIT = "SPLIT";
    }

    public interface ORDER_ASSOC_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }

    public interface METHOD_TYPE_CD {
        public static final String
                EDI_850 = "E",
                FAX = "F",
                MAIL = "M",
                TELEPHONE = "T",
                WEB = "I",
                LAW = "I",
                OTHER = "O";
    }

    public interface ORDER_PROPERTY_TYPE_CD {
        public static final String
                ORDER_NOTE = "Notes",
                CUSTOMER_ORDER_NOTE = "Order Request Note",
                CUSTOMER_ORDER_DATE = "Customer Order Date",
                PIPELINE_STEP = "PIPELINE_STEP",
                WORKFLOW_STEP = "WORKFLOW_STEP",
                CUSTOMER_BILLING_UNIT = "Customer Billing Unit",
                REQUESTED_SHIP_DATE = "Requested Ship Date",
                PENDING_DATE = "Pending Date",
                SHIPPING_STATUS = "Shipping Status",
                VOUCHER_NUMBER = "Voucher Number",
                VENDOR_ORDER_NUMBER = "Vendor Order Number",
                VENDOR_REQUESTED_FREIGHT = "VENDOR_REQUESTED_FREIGHT",
                VENDOR_REQUESTED_MISC_CHARGE = "VENDOR_REQUESTED_MISC_CHARGE",
                VENDOR_REQUESTED_TAX = "VENDOR_REQUESTED_TAX",
                VENDOR_REQUESTED_INV_DATE = "VENDOR_REQUESTED_INV_DATE",
                VENDOR_REQUESTED_DISCOUNT = "VENDOR_REQUESTED_DISCOUNT",
                QUANTITY_UPDATE = "QUANTITY_UPDATE",
                ERP_COMPANY = "ERP_COMPANY",  //JWP (but roughly equivilant to lawsons company field)
                ORDER_TYPE = "ORDER_TYPE",    //JWP
                BILLING_ORDER = "BILLING_ORDER",
                BILLING_ORIGINAL_PO_NUM = "BILLING_ORIGINAL_PO_NUM",
                BILLING_DISTRIBUTOR_INVOICE = "BILLING_DISTRIBUTOR_INVOICE",
                CUST_REQ_RESHIP_ORDER_NUM = "CUST_REQ_RESHIP_ORDER_NUM",
                OPEN_LINE_STATUS_CD = "Open Line Status Code",
                CUSTOMER_CART_COMMENTS = "CUSTOMER_CART_COMMENTS",
                PUNCH_OUT_ORDER_ORIG_ORDER_NUM = "Punch Out Order Orig Order Num",
                CUSTOMER_SYSTEM_ID = "custSysId",
                CUSTOMER_SYSTEM_URL = "custSysUrl",
                INVOICE_DIST_APPROVED = "Invoice Approved",
                OTHER_PAYMENT_INFO = "Other Payment Information",
                DISTRIBUTOR_PO_NOTE = "Distributor PO Note",
                ORDER_RECEIVED = "Order Received",
                INVENTORY_ORDER_HOLD = "INVENTORY_ORDER_HOLD",
                VENDOR_REQUESTED_TOTAL="VENDOR_REQUESTED_TOTAL",
                EVENT="EVENT",
                CUSTOMER_PO_NUM = "CUSTOMER_PO_NUM",
                NETWORK_INVOICE = "NETWORK_INVOICE",
                NETWORK_INVOICE_NOTE = "NETWORK_INVOICE_NOTE",
                JANPAK_INVOICE = "JANPAK_INVOICE",
                JANPAK_INVOICE_ITEM = "JANPAK_INVOICE_ITEM",
                BRANCH = "BRANCH",
                REP_NUM = "REP_NUM",
                REP_NAME = "REP_NAME",
                ORDER_SENT_TO_EXTERNAL_SYS = "ORDER_SENT_TO_EXTERNAL_SYS",
                HISTORICAL_ORDER = "Historical Order",
                SKIP_DUPLICATED_ORDER_VALIDATION = "Skip Dup Order Validation",
                CHECKOUT_FIELD_CD = "CHECKOUT_FIELD_CD",
                REBILL_ORDER = "REBILL_ORDER",
                EVENT_ID_OF_SEND_PROCESS = "EVENT_ID_OF_SEND_PROCESS",
                ERP_RELEASED_TIME = "ERP_RELEASED_TIME",
                BUDGET_YEAR_PERIOD = "BUDGET_YEAR_PERIOD",
                BUDGET_YEAR_PERIOD_LABEL = "BUDGET_YEAR_PERIOD_LABEL",
                SHIP_TO_OVERRIDE = "SHIP_TO_OVERRIDE",
                SEND_ORDER_CONFIRMATION = "SEND_ORDER_CONFIRMATION";;
    }
    public interface ORDER_PROPERTY_SHIP_STATUS{
        public static final String
                BACKORDERED = "Backordered",
                SHIPPED = "Dist Shipped",
                SCHEDULED  = "Scheduled";
    }
    public interface ORDER_PROPERTY_STATUS_CD{
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface ORDER_ITEM_DETAIL_ACTION_CD {
        public static final String ACCEPTED = "Accepted",
                SYSTEM_ACCEPTED = "System Accepted",
                DELETED = "Deleted",
                REJECTED = "Rejected",
                CANCELED = "Canceled",
                CANCELED_BACKORDER = "Canceled Backorder",
                DIST_SHIPPED = "Dist Shipped",      // 810 received form distributor
                CUST_SHIPPED = "Cust Shipped",      // 856 sent out to customer
                TRACKING_NUMBER = "Tracking Number",
                SUBSTITUTED = "Substituted",
                DIST_INVOICED = "Dist Invoiced",    // 810 received form distributor
                CUST_INVOICED = "Cust Invoiced",    // 810 sent out to customer
                DIST_INVOICE_REJECTED = "Invoice Rejectd", //User rejected the distributor invoice
                CIT_INVOICED = "CIT Invoiced",      // 810 sent out to CIT
                // PENDING_REVIEW = "PENDING_REVIEW",
                // following action inserted when parsing inbound 855
                ACK_ACCEPTED = "ACK Accepted",
                ACK_BACKORDERED = "ACK Backordered",
                ACK_BACKORDERED_ENHANCED = "ACK Backordered Enhanced",
                ACK_ACCEPTED_CHANGES_MADE = "ACK Accepted-Changes Made",
                ACK_DELETED = "ACK Deleted",
                ACK_ON_HOLD = "ACK On Hold",
                ACK_ACCEPTED_QUANTITY_CHANGED = "ACK Accepted-Quantity Changed",
                ACK_REJECTED = "ACK Rejected",
                ACK_ACCEPTED_SUBSTITUTION = "ACK Accepted-Substitution",
                // following action inserted by po interface
                BACKORDERED = "Backordered",
                SHIPPED = "Shipped",
                SCHEDULED  = "Scheduled",
                //following made by the returns(RGA/RMA) screens
                RETURNED = "Returned",
                //following made by the reciving system
                RECEIVED_AGAINST = "Received Against",
                QUANTITY_CHANGE = "Quantity Change",
                TAX_CHANGE = "Tax changed",
                DELIVERY_REF_NUMBER = "Delivery Ref Number";
    }

    public interface SHIPPING_CARRIER_CD {
        public static final String
                COMMON_CARRIER = "Common Carrier",
                FEDEX = "FedEx",
                OUR_TRUCK = "Our Truck",
                SALESPERSON = "Salesperson",
                UPS = "UPS";
    }

    public interface SHIPPING_TRACKING_TYPE_CD {
        public static final String
                TRACKING_NUM = "Tracking #",
                BILL_OF_LADING_NUM = "Bill of Lading #",
                CARRIER_REF_NUM = "Carrier Ref # (PRO Number)";
    }

    public interface ORDER_EXCEPTION_TYPE_CD {
        public static final String
                CIT_INVOICE = "CIT Invoice",
                CUSTOMER_ACKNOWLEDGEMENT = "Customer Acknowledgement",
                DISTRIBUTOR_ACKNOWLEDGEMENT = "Distributor Acknowledgement",
                CUSTOMER_INVOICE = "Customer Invoice",
                CUSTOMER_SHIP_NOTICE = "Customer Ship Notice",
                INBOUND_ORDER = "Inbound Order",
                INVOICE_FROM_ERP = "Invoice from ERP",
                INVOICE_TO_ERP = "Invoice to ERP",
                ORDER_TO_ERP = "Order to ERP",
                PO_FROM_ERP = "PO from ERP",
                PO_TO_VENDOR = "PO to Vendor",
                VENDOR_INVOICE = "Vendor Invoice";
    }

    public interface LEDGER_ENTRY_TYPE_CD {
        public static final String ORDER = "ORDER",
                INVOICE_DIST_ACTUAL = "INVOICE_DIST_ACTUAL",
                ADJUSTMENT = "ADJUSTMENT",
                PRIOR_PERIOD_BUDGET_ACTUAL = "PRIOR PERIOD BUDGET ACTUAL",
                WORK_ORDER = "WORK_ORDER",
                CONSOLIDATED_INV_DIST_ACTUAL =  "CONSOLIDATED_INV_DIST_ACTUAL";
    }

    public interface CALL_TYPE_CD {
        public static final String ORDER = "Order",
                COMPLAINT = "Complaint",
                INFORMATION = "Information",
                RETURN = "Return",
                MISC = "Misc";
    }
    public interface CALL_SEVERITY_CD {
        public static final String LOW = "Low",
                NORMAL = "Normal",
                CRITICAL = "Critical";
    }
    public interface CALL_STATUS_CD {
        public static final String OPEN = "Open",
                CLOSED = "Closed",
                CANCELLED = "Cancelled";
    }
    public interface CALL_PROPERTY_TYPE_CD {
        public static final String
                CALL_NOTE = "Notes";
    }
    public interface RECORD_STATUS_CD {
        public static final String
                VALID = "VALID",
                INVALID = "INVALID";
    }
    public interface ORDER_SCHEDULE_CD {
        public static final String
                NOTIFY = "NOTIFY",
                PLACE_ORDER = "PLACE_ORDER";
    }

    public interface ORDER_SCHEDULE_RULE_CD {
        public static final String
                WEEK = "WEEK",
                DAY_MONTH = "DAY_MONTH",
                WEEK_MONTH = "WEEK_MONTH",
                DATE_LIST = "DATE_LIST";
    }
    public interface ORDER_SCHEDULE_DETAIL_CD {
        public static final String
                ELEMENT = "ELEMENT",
                ALSO_DATE = "ALSO_DATE",
                EXCEPT_DATE = "EXCEPT_DATE",
                CONTACT_NAME = "CONTACT_NAME",
                CONTACT_PHONE = "CONTACT_PHONE",
                CONTACT_EMAIL = "CONTACT_EMAIL";
    }
    public interface ORDER_BATCH_TYPE_CD {
        public static final String
                SCHEDULE_ORDER = "SCHEDULE_ORDER";
    }
    public interface ORDER_BATCH_STATUS_CD {
        public static final String
                SUCCESS = "SUCCESS",
                SUCCESS_NO_EMAIL = "SUCCESS_NO_EMAIL",
                MANUALLY_RESOLVED = "MANUALLY_RESOLVED",
                FAILURE = "FAILURE";
    }

    public interface TRADING_TYPE_CD {
        public static final String
                PAPER = "PAPER",
                FAX = "FAX",
                EDI = "EDI",
                OTHER = "OTHER",
				EMAIL = "EMAIL",
                XML = "XML",
                PUNCHOUT = "PUNCHOUT";
    }

    public interface TRADING_PARTNER_TYPE_CD {
        public static final String
                APPLICATION = "APPLICATION",
                STORE = "STORE",
                CUSTOMER = "CUSTOMER",
                DISTRIBUTOR = "DISTRIBUTOR",
                MANUFACTURER = "MANUFACTURER";
    }

    public interface TRADING_PARTNER_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }

    public interface TRADING_PARTNER_ASSOC_CD {
        public static final String
                MANUFACTURER = "MANUFACTURER",
                ACCOUNT = "ACCOUNT",
                DISTRIBUTOR = "DISTRIBUTOR",
                STORE = "STORE";
    }

    public interface SKU_TYPE_CD {
        public static final String CLW = "CLW",
                CUSTOMER = "CUSTOMER",
                DISTRIBUTOR = "DISTRIBUTOR",
                MANUFACTURER = "MANUFACTURER";
    }

    public interface KNOWLEDGE_STATUS_CD {
        public static final String ACTIVE = "Active",
                INACTIVE = "Inactive",
                PENDING = "Pending";
    }

    public interface KNOWLEDGE_CATEGORY_CD {
        public static final String
                DISTRIBUTORS = "Distributors",
                ENGINEERING = "Engineering",
                ENVIRONMENT_HEALTH_SAFETY = "Environmental Health & Safety",
                LOGISTICS = "Logistics",
                MANUFACTURERS = "Manufactures",
                OPERATIONS = "Operations",
                PRODUCT_FEATURES_BENEFITS = "Product Features & Benefits",
                PRODUCT_PROCEDURES = "Product Procedures",
                REGULATORY = "Regulatory",
                TECHNOLOGY = "Technology";
    }
    public interface KNOWLEDGE_PROPERTY_TYPE_CD {
        public static final String
                KNOWLEDGE_NOTE = "Notes";
    }

    public interface ORDER_EXCEPTION_CD {
        public static final String ERP_SHIPTO_REJECTED = "ERP_SHIPTO_REJECTED";
        public static final String ERP_ORDER_REJECTED = "ERP_ORDER_REJECTED";
    }

    // Attributes stored for a credit card purchase in the
    // CLW_ORDER_META table.
    public interface CC_CD {
        public static final String
                TYPE        = "CC_TYPE",
                NUMBER      = "CC_NUMBER",
                EXP_MONTH   = "CC_EXP_MONTH",
                EXP_YEAR    = "CC_EXP_YEAR",
                BUYER_NAME  = "CC_BUYER_NAME",
                STREET1     = "CC_STREET1",
                STREET2     = "CC_STREET2",
                CITY        = "CC_CITY",
                STATE       = "CC_STATE",
                POSTAL_CODE = "CC_POSTAL_CODE";
    }
    public interface INVOICE_STATUS_CD {
        public static final String
                CANCELLED = "CANCELLED",            // Cancelled -- END
                REJECTED = "REJECTED",              // Dist Invoice was examined and rejected -- END
                DIST_SHIPPED = "DIST_SHIPPED",      // Received invoice from distributor
                PENDING_REVIEW = "PENDING_REVIEW",  // Problem receiving invoice from distributor
                DUPLICATE = "DUPLICATE",            // This invoice was already recieved from the distributor
                PROCESS_ERP = "PROCESS_ERP",        // Distributor invoice sent to Lawson
                ERP_REJECTED = "ERP_REJECTED",      // Problem sending distributor invoice to L
                ERP_GENERATED = "ERP_GENERATED",    // Got incomplete cust. invoice from Lawson
                ERP_GENERATED_ERROR = "ERP_GENERATED_ERROR", // Problem getting incomplete invoice
                ERP_RELEASED = "ERP_RELEASED",      // Got customer invoice from Lawson -- END
                ERP_RELEASED_ERROR = "ERP_RELEASED_ERROR",  // Problem getting cust. invoice from L
                MANUAL_INVOICE_RELEASE = "MANUAL_INVOICE_RELEASE", // The invoice was examined and updated.
                CUST_INVOICED = "CUST_INVOICED",    // Sent invoice to customer -- END
                CUST_INVOICED_FAILED = "CUST_INVOICED_FAILED", // Problem sending invoice to cust.
                CLW_ERP_PROCESSED = "CLW_ERP_PROCESSED", // Pass thru store customer invoice generated
                PENDING = "PENDING", // Inbound invoice has a dist uom conversion and was not yet shipped complete
                CLW_ERP_RELEASED = "CLW_ERP_Released",
                INVOICE_HISTORY = "Invoice_History";
    }

    public interface INVOICE_DETAIL_STATUS_CD {
        public static final String
                PENDING_REVIEW = "PENDING_REVIEW",
                ERP_RELEASED = "ERP_RELEASED",
                //ERP_GENERATED = "ERP_GENERATED",
                //CUST_SHIPPED = "CUST_SHIPPED",
                CUST_INVOICED = "CUST_INVOICED";
    }

    //This is really a tansaction type code, not just EDI
    public interface EDI_TYPE_CD {
        public static final String
                TGENERIC_IN = "GENERIC_IN",
                TFLAT_FILE_IN = "FLAT_FILE_IN",
                TXML_FILE_IN = "XML_FILE_IN",
                TORDER_PROCESSING_OUT = "ORDER_PROCESSING_OUT",
                TMANIFEST_OUT = "MANIFEST_OUT",
                MANIFEST_IN = "MANIFEST_IN",
                TPUNCH_OUT_ORDER_OUT = "PUNCH_OUT_ORDER_OUT",
                TPUNCH_OUT_LOGON = "PunchOutSetupRequest",
                TORDER_REPORT_XML_OUT = "TORDER_REPORT_XML_OUT",
                TINVOICE_REPORT_XML_OUT = "TINVOICE_REPORT_XML_OUT",
                TORDER_SEND_TO_EXT_CUST_SYS = "TORDER_SEND_TO_EXT_CUST_SYS",
                TORDER_TOTALS_OUT = "ORDER_TOTALS_OUT",
                TACCOUNT_OUT = "ACCOUNT_OUT",
                TSITE_OUT = "SITE_OUT",
                TON_HAND_VALUE_OUT = "ON_HAND_VALUE_OUT",
                T856_SENT = "T856_SENT",
                T855_SENT = "T855_SENT",
                T850_REBATE = "850_REBATE",
                T850     = "850",
                T810     = "810",
                T855     = "855",
                T856     = "856",
                T997     = "997",
                T824     = "824",
                T867     = "867",
                T860     = "860",
                T812     = "812",
                T101     = "101",
                T843     = "843";
    }
    public interface INTERCHANGE_TYPE_CD {
        public static final String
                INBOUND     = "INBOUND",
                OUTBOUND    = "OUTBOUND";
    }
    public interface SUBST_STATUS_CD {
        public static final String
                ACTIVE     = "ACTIVE",
                INACTIVE    = "INACTIVE";
    }

    public interface SUBST_TYPE_CD {
        public static final String
                ITEM_ACCOUNT     = "ITEM_ACCOUNT";
    }

    public interface SHIP_STATUS_CD {
        public static final String
                PENDING = "PENDING",
                SUCCESS = "SUCCESS",
                FAILED  = "FAILED";
    }
    public interface ACK_STATUS_CD {
        public static final String
                CANCELLED_ACK_SUCCESS = "CANCELLED_ACK_SUCCESS", // ACK success for cancelled item
                CANCELLED_ACK_FAILED = "CANCELLED_ACK_FAILED",   // ACK failed for cancelled item
                CUST_ACK_SUCCESS = "CUST_ACK_SUCCESS", // Sent ACK to cust
                CUST_ACK_FAILED = "CUST_ACK_FAILED",   // Problem sending ACK
                NO_ACK_NEEDED = "NO_ACK_NEEDED";
    }

    public interface CUSTOMER_REPORT_TYPE_CD {
        public static final String
                TOTAL_VOLUME = "Total Volume",//not used?
                TOTAL_VOLUME_BY_LOCATION = "Total Volume by Location",//not used?
                VOLUME_BY_CATEGORY = "Volume by Category",//not used?
                VOLUME_BY_ITEM = "Volume by Item",//not used?
                AVERAGE_ORDER_SIZE = "Average Order Size",//not used?
                DELIVERY_SCHEDULE = "Delivery Schedule", //used directly
                INVOICE_LISTING = "INVOICE_LISTING", //used indirectly
                ORDERS_AT_A_GLANCE = "ORDERS_AT_A_GLANCE"
                        ;
    }
    public interface ACCOUNT_REPORT_TYPE_CD {
        public static final String
                ORDER_INFORMATION_REPORT = "Order Information Report";
    }


    public interface CIT_STATUS_CD {
        public static final String
                PENDING = "PENDING",// Initial state
                SUCCESS = "SUCCESS",// outbound 810 to CIT created success
                FAILED  = "FAILED", // failed to create outbound 810 to CIT
                ACCEPTED = "ACCEPTED", // CIT has accepted the invoice sent
                REJECTED = "REJECTED"; // CIT has rejected the invoice sent
    }
    public interface FREIGHT_CHARGE_CD {
        public static final String
                CC = "CC", // Collect
                CF = "CF", // Collect - Freight Credited to Payment Customer
                DF = "DF", // Defined by Buyer and Seller
                PA = "PA", // Advance Payment
                PB = "PB", // Customer Pick Up/ Backhaul
                PC = "PC", // Prepaid But Charged to customer
                PO = "PO", // Prepaid Only
                PP = "PP"; // Prepaid by seller
    }

    public interface PAYMENT_TERMS_CD {
        public static final String
                //N15 = "N15", // Net 15
                //N20 = "N20", // Net 20
                //N30 = "N30", // Net 30
                PRE = "PRE"; // Prepaid
    }

    public interface INVOICE_TYPE_CD {
        public static final String
                IN = "IN", // regular invoice
                CR = "CR"; // credit for a issured invoice
    }

    public interface REMITTANCE_STATUS_CD {
        public static final String
                RECIEVED = "RECIEVED",   //Recieved the remittance
                PENDING = "PENDING",   //Recieved the remittance
                INFORMATION_ONLY = "INFORMATION_ONLY", //log, but do not send to Lawson
                PROCESS_INFORMATION_ONLY = "PROCESS_INFORMATION_ONLY", //someone fixed a information only remit
                RECIEVED_ERROR = "RECIEVED_ERROR",   //error while loading
                PROCESSING = "PROCESSING", //in transit to lawson, tmporary state
                PROCESS_ERP = "PROCESS_ERP",  //sent to lawson
                PROCESS_ERP_ERROR = "PROCESS_ERP_ERROR";  //sent to lawson
    }

    public interface REMITTANCE_DETAIL_STATUS_CD {
        public static final String
                APPLIED_ADJUSTMENT = "APPLIED_ADJUSTMENT",
                RECIEVED = "RECIEVED",   //Recieved the remittance, not sent to lawson yet
                INFORMATION_ONLY = "INFORMATION_ONLY",   //Recieved the remittance, do not send to lawson
                PROCESS_INFORMATION_ONLY = "PROCESS_INFORMATION_ONLY",
                RECIEVED_ERROR = "RECIEVED_ERROR",   //error while loading
                PROCESS_ERP = "PROCESS_ERP",  //sent to lawson
                PROCESS_ERP_ERROR = "PROCESS_ERP_ERROR";  //sent to lawson
    }

    public interface REMITTANCE_INVOICE_TYPE_CD {
        public static final String
                SHORT_PAY = "SP",
                INVOICE = "I",
                OTHER = "O",
                CREDIT = "C";
    }

    public interface REMITTANCE_PROPERTY_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }

    public interface REMITTANCE_PROPERTY_TYPE_CD {
        public static final String
                NOTE = "NOTE",
                ERROR = "ERROR",
                ERROR_RESOLVED = "ERROR_RESOLVED";
    }

    public interface INVOICE_DIST_SOURCE_CD {
        public static final String
                EDI = "EDI",
                WEB = "WEB",
                XML = "XML";
    }
    public interface BUS_ENTITY_TERR_CD {
        public static final String
                DIST_TERRITORY = "DIST_TERRITORY";
    }
    public interface BUS_ENTITY_TERR_FREIGHT_CD {
        public static final String
                NO_FREIGHT = "No Freight",
                FREIGHT = "Freight";
    }
    public interface REPORT_SCHEMA_CD {
        public static final String
                MAIN = "MAIN",
                STAGED_INFO = "STAGED_INFO",
                REPORT = "REPORT";
    }
    public interface GENERIC_REPORT_TYPE_CD {
        public static final String
                SQL = "SQL",
                XSQL = "XSQL",
                JAVA_CLASS = "JAVA_CLASS",
                JAVA_CLASS_MULTI = "JAVA_CLASS_MULTI",
                JAVA_CLASS_2 = "JAVA_CLASS_2";
    }

    public interface PIPELINE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                DELETED = "DELETED",
                INACTIVE = "INACTIVE" ;
    }

    public interface PIPELINE_CD {
        public static final String
                SYNCH_WEB = "SYNCH_WEB",
                SYNCH_EDI = "SYNCH_EDI",
                SYNCH_ASYNCH = "SYNCH_ASYNCH",
                ASYNCH = "ASYNCH",
                ASYNCH_PRE_PROCESSED = "ASYNCH_PRE_PROCESSED",
                POST_ORDER_CAPTURE = "POST_ORDER_CAPTURE",
                PRE_ORDER_CAPTURE= "PRE_ORDER_CAPTURE",
                WEB_PRE_ORDER_CAPTURE = "WEB_PRE_ORDER_CAPTURE",
                REPROCESS_ORDER = "REPROCESS_ORDER",
                SYNCH_CHANGE_ORDER = "SYNCH_CHANGE_ORDER",
                BATCH_REPROCESS_ORDER = "BATCH_REPROCESS_ORDER",
                BATCH_REPROCESS_MULTI_ORDER="BATCH_REPROCESS_MULTI_ORDER",
                STORE_ORDER_UPDATE = "STORE_ORDER_UPDATE",
                STORE_ORDER_CANCEL = "STORE_ORDER_CANCEL",
                STORE_ORDER_ITEMS_CANCEL = "STORE_ORDER_ITEMS_CANCEL",
                CANCEL_BACKORDERED = "CANCEL_BACKORDERED",
                CHECKOUT_CAPTURE = "CHECKOUT_CAPTURE",
                CUST_ORDER_PROCESSING = "CUST_ORDER_PROCESSING",
                UPDATE_ORDER = "UPDATE_ORDER";
    }

    public interface CITY_POSTAL_ENTRY_TYPE_CD {
        public static final String
                MANUAL = "Manual",
                LOADER = "Loader";
    }

    public interface GROUP_TYPE_CD {
        public static final String
                ACCOUNT = "ACCOUNT",
                DISTRIBUTOR = "DISTRIBUTOR",
//                SITE = "SITE",
                MANUFACTURER = "MANUFACTURER",
                STORE = "STORE",
                USER = "USER",
                STORE_UI = "STORE_UI",
                USER_UI = "USER_UI",
                ACCOUNT_UI = "ACCOUNT_UI";
    }
    public interface GROUP_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }
    public interface GROUP_ASSOC_CD {
        public static final String
                STORE_OF_GROUP      = "STORE_OF_GROUP",
                ACCOUNT_OF_GROUP    = "ACCOUNT_OF_GROUP",
                BUS_ENTITY_OF_GROUP = "BUS_ENTITY_OF_GROUP",
                USER_OF_GROUP       = "USER_OF_GROUP",
                REPORT_OF_GROUP     = "REPORT_OF_GROUP",
                FUNCTION_OF_GROUP   = "FUNCTION_OF_GROUP";
    }

    public interface APPLICATION_FUNCTIONS {
        public static final String

                STORE_MGR_TAB_GROUP = "Store Mgr Group Tab",
                STORE_MGR_TAB_STORE = "Store Mgr Store Tab",
                STORE_MGR_TAB_ACCOUNT = "Store Mgr Account Tab",
                STORE_MGR_TAB_SETUP = "Store Mgr Setup Tab",
                STORE_MGR_PO_USER = "Store Mgr PO Tab",
                STORE_MGR_RGA_USER = "Store Mgr RGA Tab",
                STORE_MGR_TAB_SITE = "Store Mgr Site Tab",
                STORE_MGR_TAB_USER = "Store Mgr User Tab",
                STORE_MGR_TAB_VENDOR_INVOICE = "Store Mgr Vendor Invoice Tab",
                STORE_MGR_TAB_ITEM = "Store Mgr Item Tab",
                STORE_MGR_TAB_CATALOG = "Store Mgr Catalog Tab",
                STORE_MGR_TAB_COST_CENTER = "Store Mgr Cost Center Tab",
                STORE_MGR_TAB_ITEM_MANAGER = "Store Mgr Item Manager Tab",
                STORE_MGR_TAB_DIST = "Store Mgr Distributor Tab",
                STORE_MGR_TAB_MANUF = "Store Mgr Manufacturer Tab",
                STORE_MGR_TAB_CONTRACTOR = "Store Mgr Contractor Tab",
                STORE_MGR_TAB_SERVICE_PROVIDER = "Store Mgr Service Provider Tab",
                STORE_MGR_TAB_SERVICE_PROVIDER_TYPE = "Store Mgr Service Provider Type Tab",
                STORE_MGR_TAB_ENTERPRISE = "Store Mgr Enterprise Tab",
                STORE_MGR_TAB_PROFILES = "Store Mgr Profiles",
                STORE_MGR_TAB_CUST_BLANKET_PO = "Store Mgr Cust Blanket Pos",
                STORE_MGR_TAB_FREIGHT_HANDLER = "Store Mgr Freight Handler",
                STORE_MGR_TAB_CUST_INVOICE = "Store Mgr Cust Invoice Tab",
                STORE_MGR_TAB_MASTER_ASSETS = "Store Mgr Master Assets Tab",
                STORE_MGR_TAB_CORPORATE_SCHEDULES = "Store Mgr Corporate Schedules Tab",
    	    	STORE_MGR_TAB_EMAIL_TEMPLATES = "Store Mgr Email Templates Tab",

		        STORE_MGR_TAB_CMS = "New Admin - Content Management",
        	    STORE_MGR_TAB_COST_CENTERS = "Store Mgr Cost Centers Tab",

                EDIT_SITE_PAR_VALUES = "Edit Site Par Values",
                VIEW_SITE_PAR_VALUES = "View Site Par Values",
                EDIT_SITE_SHOPPING_CONTROLS = "Edit Shopping Controls",
                VIEW_SHOPPING_CONTROLS = "View Shopping Controls",
                EDIT_PROFILING = "Edit Profiling",
                EDIT_MESSAGES = "Edit Messages",
                ADD_CUSTOMER_ORDER_NOTES = "Add Customer Order Notes",
                ADD_SHIPPING_ORDER_COMMENTS = "Add Order Shipping Comments",
                VIEW_PARTIAL_ORD_CREDIT_CARD   = "View Partial Ord Credit Card",
                MODIFY_ORD_CREDIT_CARD         = "Modify Credit Card For Order",
                PO_MANIFESTING = "PO Manifesting Sub System",
                PO_ADDRESS_PRINTING = "PO Address Printing",
                PLACE_CONFIRMATION_ONLY_ORDER = "Place Confirmation Only Order",
                EXCLUDE_ORDER_FROM_BUDGET = "Exclude Order From Budget",
                VIEW_INVOICES = "View invoice in runtime pages",
                RUN_SPENDING_ESTIMATOR = "Run Spending Estimator",
                RECEIVING = "Receiving",
                ADD_RE_SALE_ITEMS = "Add Re Sale items at checkout",
                DISPLAY_COST_CENTER_DETAIL = "Display Cost Center Details",
                EDIT_USER_PROFILE_NAME = "Edit User Profile Name",
                PLACE_ORDER_REQUEST_SHIP_DATE = "Place Order Request Ship Date",
                PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE = "Place Order MANDATORY Request Ship Date",
                PLACE_ORDER_PROCESS_ORDER_ON = "Place Order Process Order On",
                STORE_MGR_TAB_ORDERS = "Store Mgr Orders Tab",
                STORE_MGR_TAB_ASSET = "Store Mgr Asset Tab",
                OVERRIDE_SHOPPING_RESTRICTION = "Override Shopping Restriction",
                INVENTORY_EARLY_RELEASE ="Inventory Early Release",
                STORE_MGR_TAB_WARRANTY = "Store Mgr Warranty Tab",
                ASSET_USER ="Asset User",
                ASSET_ADMINISTRATOR ="Asset Administrator",
                MODIFY_ORDER_ITEM_QTY = "Modify Order Item Qty",
                RUNTIME_INVOICE_TAB = "Runtime Invoice Tab",
                WORK_ORDER_APPROVER = "Work Order Approver",
                SHOP_ACCESS = "Access to Shop Pages",
                AUTO_DISTRO_ACCESS = "Access to Auto Distro Pages",
                UPDATE_AUTO_DISTRO = "Update Auto Distro",
                TRACK_ORDER_ACCESS = "Access to Track Order Pages",
                REPORTS_ACCESS = "Access to Reports Pages",
                USER_PROFILE_ACCESS = "Access to User Profile Pages",
                STORE_PROFILE_ACCESS = "Access to Store Profile Pages",
                PRODUCT_INFORMATION_ACCESS = "Access to Product Info Pages",
                MSDS_ACCESS = "Access to MSDS Pages",
                FAQ_ACCESS = "Access to FAQs Pages",
                CONTACT_US_ACCESS = "Access to Contact Us Pages",
                MAINTENANCE_ACCESS = "Access to Maintenance Pages",
                MAINTENANCE_NEWS = "Access to News Maint. Page",
	            MAINTENANCE_TEMPLATE = "Access to Template Maint. Page",
		        MAINTENANCE_FAQ = "Access to FAQ Maintenance Page",
                APPROVE_ORDERS_ACCESS = "Access to Approve Orders",
                CHANGE_LOCATION_ACCESS = "Access to Change Location",
                VIEW_SPECIAL_ITEMS = "View Special Items",
                STORE_MGR_TAB_ORDER_TRACKING = "Store Mgr Order Tracking",
                STORE_MGR_TAB_EXCEPTIONS = "Store Mgr Excepions",
                BYPASS_SMALL_ORDER_ROUTING = "Bypass small order routing",
                BYPASS_CUSTOMER_WORKFLOW = "Bypass customer workflow",
                CUST_REQ_RESHIPMENT_ORDER_NUM ="Cust Req Reshipment Order Num",
				CRC_MANAGER = "CRC Manager",
                ADMIN2_MGR_TAB_SITE = "Admin 2.0 Mgr Site Tab",
                ADMIN2_MGR_TAB_USER = "Admin 2.0 Mgr User Tab",
                ADMIN2_MGR_TAB_ACCOUNT = "Admin 2.0 Mgr Account Tab",
                ADMIN2_MGR_TAB_LOADER = "Admin 2.0 Mgr Loader Tab",
                ADMIN2_MGR_SITE_LOADER = "Admin 2.0 Mgr Site Loader Page",
                ADMIN2_MGR_BUDGET_LOADER = "Admin 2.0 Mgr Budget Loader Page",
                ADMIN2_MGR_SHOPPING_CONTROL_LOADER = "Admin 2.0 Mgr Shopping Control Loader Page",
                ADMIN2_MGR_TAB_PROFILE = "Admin 2.0 Mgr Profile Tab",
                UI_MGR_TAB_GROUP = "Ui Mgr Group Tab",
                UI_MGR_TAB_SITE = "UI Mgr Site Tab",
                UI_MGR_TAB_USER = "Ui Mgr User Tab",
                UI_MGR_TAB_ACCOUNT = "Ui Mgr Account Tab",
                FREIGHT_TABLE_ADMINISTRATION = "Access to Freight Tables",
                DISCOUNT_ADMINISTRATION = "Access to Discount Tables",
                ASSET_WO_VIEW_ALL_FOR_STORE = "Asset WO - View All for Store",
                SPECIAL_PERMISSION_ITEMS = "Special Permission Items",
                STORE_MGR_MASTER_ASSETS = "Store Mgr Master Assets",
                STORE_MGR_TAB_MESSAGES = "Store Mgr Messages Tab",
                TRACKING_MAINTENANCE = "Tracking Maintenance",
                NEW_UI_ACCESS = "Access to New UI",
                CHANGE_ORDER_BUDGET_PERIOD = "Change Order Budget Period",
                ACCESS_ASSETS = "Access to Assets",
                ACCESS_SERVICES = "Access to Services",
                ACCESS_SHOPPING = "Access to Shopping",
                ACCESS_CONTENT = "Access to Content",
                ACCESS_HISTORY = "Access to History Records",
                ACCESS_DASHBOARD = "Access to Dashboard",
                ACCESS_ORDERS = "Access to Orders",
                MANTA_STORE_MGR_TAB_ACCOUNT = "New Admin - Accounts",
                MANTA_STORE_MGR_TAB_COST_CENTERS = "New Admin - Cost Centers",
                MANTA_STORE_MGR_TAB_DISTRIBUTOR = "New Admin - Distributor",
                MANTA_STORE_MGR_TAB_SITE = "New Admin - Locations",
                MANTA_STORE_MGR_TAB_MANUFACTURER = "New Admin - Manufacturers",
                MANTA_STORE_MGR_TAB_MESSAGES = "New Admin - Messages",
                MANTA_STORE_MGR_TAB_USERS = "New Admin - Users",
                MANTA_STORE_MGR_TAB_ORDERS = "New Admin - Orders",
                MANTA_STORE_MGR_TAB_EMAIL_TEMPLATES = "New Admin - Email Templates";
    }

    public interface INVOICE_LOADING_PRICE_MODEL_CD {
        public static final String
                EXCEPTION = "EXCEPTION",
                LOWEST = "LOWEST",
                DISTRIBUTOR_INVOICE = "DISTRIBUTOR_INVOICE",
                PREDETERMINED = "PREDETERMINED",
                HOLD_ALL = "HOLD_ALL";
    }

    public interface EMAIL_TRACKING_CD {
        public static final String
                WORKFLOW = "WORKFLOW",
                ORDER_SCHEDULE = "ORDER_SCHEDULE",
                USER_PASSWORD = "USER_PASSWORD",
                OG_ITEM_REMOVED = "OG_ITEM_REMOVED",
                SC_ITEM_REMOVED = "SC_ITEM_REMOVED";
    }

    public interface EMAIL_TRACKING_STATUS_CD {
        public static final String
                SENT = "SENT",
                SENDING_ERROR = "SENDING_ERROR",
                READY_TO_SEND = "READY_TO_SEND",
                INALID_ADDRESS = "INALID_ADDRESS";
    }

    public interface OPEN_LINE_STATUS_CD {
        public static final String
                BACKORDER = "Backorder",
                LINE_DROPPED = "Line Was Dropped",
                SHIPPED_INVOICED = "Shipped And Invoiced",
                SHIPPED_NOT_INVOICED = "Shipped NOT Invoiced",
                NEVER_RECEIVED_PO = "Never Received PO",
                HELD_IN_HOUSE = "Held In House";
    }

    public interface SCHEDULE_TYPE_CD {
        public static final String
                DELIVERY = "DELIVERY",
                CORPORATE = "CORPORATE";
    }

    public interface SCHEDULE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }
    public interface SCHEDULE_RULE_CD {
        public static final String
                WEEK = "WEEK",
                DAY_MONTH = "DAY_MONTH",
                WEEK_MONTH = "WEEK_MONTH",
                DATE_LIST = "DATE_LIST";
    }
    public interface SCHEDULE_DETAIL_CD {
        public static final String
                ELEMENT = "ELEMENT",
                WEEK_DAY = "WEEK_DAY",
                MONTH_DAY = "MONTH_DAY",
                MONTH_WEEK = "MONTH_WEEK",
                ALSO_DATE = "ALSO_DATE",
                EXCEPT_DATE = "EXCEPT_DATE",
                HOLIDAY = "HOLIDAY",
                CUTOFF_DAY = "CUTOFF_DAY",
                CUTOFF_TIME = "CUTOFF_TIME",
                ACCOUNT_ID = "ACCOUNT_ID",
                SITE_ID = "SITE_ID",
                ZIP_CODE = "ZIP_CODE",
                INV_CART_ACCESS_INTERVAL="INV_CART_ACCESS_INTERVAL",
                PHYSICAL_INV_START_DATE = "PHYSICAL_INV_START_DATE",
                PHYSICAL_INV_END_DATE = "PHYSICAL_INV_END_DATE",
                PHYSICAL_INV_FINAL_DATE = "PHYSICAL_INV_FINAL_DATE";
    }

    public interface PROFILE_TYPE_CD {
        public static final String
                SURVEY = "SURVEY",
                QUESTION = "QUESTION";
    }

    public interface PROFILE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }

    public interface PROFILE_DETAIL_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }

    public interface PROFILE_ASSOC_CD {
        public static final String
                PROFILE_OF_STORE = "PROFILE_OF_STORE",
                PROFILE_OF_BUS_ENTITY = "PROFILE_OF_BUS_ENTITY",
                PROFILE_OF_PROFILE = "PROFILE_OF_PROFILE";
    }

    public interface PROFILE_QUESTION_TYPE_CD {
        public static final String
                FREE_FORM_TEXT = "FREE_FORM_TEXT",
                NUMBER = "NUMBER",
                MULTIPLE_CHOICE = "MULTIPLE_CHOICE",
                MULTIPLE_CHOICES = "MULTIPLE_CHOICES",
                DATE = "DATE";
    }

    public interface PROFILE_META_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";
    }

    public interface PROFILE_META_TYPE_CD {
        public static final String
                CHOICE = "CHOICE",
                OTHER_CHOICE = "OTHER_CHOICE",
                CHOICE_SHOW_CHILDREN = "CHOICE_SHOW_CHILDREN",
                OTHER_CHOICE_SHOW_CHILDREN = "OTHER_CHOICE_SHOW_CHILDREN",
                IMAGE = "IMAGE";
    }

    public interface APPLICATION_IDENTIFIER {
        public static final String
                SERVICE_TYPE_CD = "91";
    }

    public interface ERP_SYSTEM_CD {
        public static final String
                CLW_JCI = "CLW_JCI",
                SELF_SERVICE = "SELF_SERVICE";
    }

    public interface PO_NUMBER_RENDERING_TYPE_CD {
        public static final String
                CONCAT_WITH_ERP_ORDER = "CONCAT_WITH_ERP_ORDER",
                PLAIN = "PLAIN";
    }

    public interface UOM_CONVERSION_TYPE_CD {
        public static final String
                CONVERT_UOM_TO_EACH = "CONVERT_UOM_TO_EACH",
                NONE = "NONE";
    }

    public interface SITE_IDENTIFIER_TYPE_CD {
        public static final String
                CONCATONATED = "CONCATONATED",
                SEPERATED_ACCOUNT_IN_REF = "SEPERATED_ACCOUNT_IN_REF",
                SEPERATED_SITE_REF_NUMBER = "SEPERATED_SITE_REF_NUMBER",
                DIST_SITE_REFERENCE_NUMBER = "DIST_SITE_REFERENCE_NUMBER";
    }

    public interface ACCOUNT_IDENTIFIER_INBOUND {
        public static final String
        ACCT_IDENTIFIER_IN_N1_LOOP = "ACCT_IDENTIFIER_IN_N1_LOOP";
    }

    public interface ITEM_SALE_TYPE_CD {
        public static final String
                END_USE = "END_USE",
                RE_SALE = "RE_SALE";
    }


    /**
     *Trading partner mapping property type
     */
    public interface ENTITY_PROPERTY_TYPE {
        public static final String
                ORDER_COLUMN = "ORDER_COLUMN",
                HARD_VALUE = "HARD_VALUE",
                ORDER_PROPERTY = "ORDER_PROPERTY",
				ORDER_META = "ORDER_META",
                SITE_PROPERTY = "SITE_PROPERTY",
                SITE_FIELD_PROPERTY = "SITE_FIELD_PROPERTY",
                ACCOUNT_PROPERTY = "ACCOUNT_PROPERTY",
                ACCOUNT_FIELD_PROPERTY = "ACCOUNT_FIELD_PROPERTY",
                COLUMN_HEADER = "COLUMN_HEADER",
                FLAT_FILE_CONTAINS_HEADER = "Flat file contains header",
                FIELD_MAP_USE_HEADERS = "Use header names for mapping",
                VALUE_OBJECT_CLASSNAME = "Value Object Classname",
                PADDING_CHAR="PADDING_CHAR",
                PADDING_TOTAL_LENGTH="PADDING_TOTAL_LENGTH",
                SET_SHIP_TO_FH_ADDRESS = "FH address in order", // For small orders
                IGNORE_MISSING_LINE_INFO = "Ignore missing line info",
                CLW_TRADING_PARTNER_KEY = "Clw Trading Partner Key",
                PROCESS_NEW_RECORD_ONLY = "Process new record only",
                MATCH_ORDER_BY_DIST_ORDER_NUM = "Match order by Vendor Order#",
                USERNAME = "USERNAME",
                PASSWORD = "PASSWORD",
                ACCOUNT_NUM = "ACCOUNT_NUM",
                EMAIL_ACKNOWLEDGE = "Email addresses for acknowledgement";

    }

    public interface TRADING_PROPERTY_MAP_CD {
        public static final String
                FIELD_MAP = "FIELD_MAP",
                DYNAMIC = "DYNAMIC",
                REFERENCE = "REFERENCE",
                DELETED = "DELETED",
                CONFIGURATION_PROPERTY = "CONFIGURATION_PROPERTY";
    }

    public interface REPORT_RESULT_LINE_CD {
        public static final String
                HEADER = "HEADER",
                REPORT_LINE = "REPORT_LINE";
    }
    public interface REPORT_RESULT_ASSOC_CD {
        public static final String
                USER = "USER",
                GROUP = "GROUP",
                USER_READ = "USER_READ";
    }

    public interface REBATE_STATUS_CD {
        public static final String
                PENDING = "PENDING",
                SENT_FOR_REBATE = "SENT_FOR_REBATE";
    }

    public interface REPORT_RESULT_STATUS_CD {
        public static final String
                GENERATED = "Generated",
                FAILED = "Failed";
    }

    public interface REPORT_SCHEDULE_RULE_CD {
        public static final String
                WEEK = "WEEK",
                DAY_MONTH = "DAY_MONTH",
                WEEK_MONTH = "WEEK_MONTH",
                DATE_LIST = "DATE_LIST";
    }

    public interface REPORT_SCHEDULE_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE",
                LIMITED = "LIMITED";
    }

    public interface REPORT_SCHEDULE_DETAIL_CD {
        public static final String
                ELEMENT = "ELEMENT",
                WEEK_DAY = "WEEK_DAY",
                MONTH_DAY = "MONTH_DAY",
                MONTH_WEEK = "MONTH_WEEK",
                ALSO_DATE = "ALSO_DATE",
                EXCEPT_DATE = "EXCEPT_DATE",
                HOLIDAY = "HOLIDAY",
                ASSOC_USER = "ASSOC_USER",
                NOTIFY_USER = "NOTIFY_USER",
                REPORT_OWNER = "REPORT_OWNER",
                ASSOC_GROUP = "ASSOC_GROUP",
                NOTIFY_GROUP = "NOTIFY_GROUP",
                EMAIL_FLAG = "EMAIL_FLAG",
                REPORT_PARAM = "REPORT_PARAM";
    }

    public interface CONTACT_TYPE_CD {
        public static final String
                JDREP = "JDREP",
                KCREP = "KCREP",
                GPREP = "GPREP",
                OTHER = "OTHER",
                PURCHASING = "PURCHASING",
                TECHNOLOGY = "TECHNOLOGY",
                OPERATIONS = "OPERATIONS",
                OWNER = "OWNER",
                SALES = "SALES",
                PRIMARY_CONTACT = "PRIMARY_CONTACT",
                CUSTOMER_SERVICE = "CUSTOMER_SERVICE",
                ACCOUNTS_RECEIVABLE = "ACCOUNTS_RECEIVABLE";
    }

    public interface NOTE_TYPE_CD {
        public static final String
                DISTRIBUTOR_NOTE = "DISTRIBUTOR_NOTE",
                ACCOUNT_NOTE = "ACCOUNT_NOTE",
                SITE_NOTE = "SITE_NOTE",
                NEWS_NOTE = "NEWS_NOTE";
    }

    public interface CUSTOMER_SYSTEM_APPROVAL_CD {
        public static final String
                NONE = "None",
                PUNCH_OUT_NON_ELEC_ORD = "Punch Out Non Elec Ord",
                PUNCH_OUT_NON_ELEC_ORD_ONLY = "ONLY Punch Out Non Elec Ord",
                PUNCH_OUT_INLIN_NON_E_ORD_ONLY = "ONLY Punch Out Inline NonElec",
                PUNCH_OUT_INLIN_ORD_SAP = "Punch Out Non Elec Ord SAP";
    }

    public interface ORDER_ASSOC_CD {
        public static final String
                CONSOLIDATED = "CONSOLIDATED",
                REPLACED = "REPLACED",
                SPLIT = "SPLIT",
                WORK_ORDER_PART = "WORK_ORDER_PART",
                SERVICE_TICKET = "SERVICE_TICKET";
    }

    public interface WORKFLOW_IND_CD {
        public static final String
                SKIP = "SKIP",
                INTERRUPTED = "INTERRUPTED", // interrupted pipeline
                TO_RESUME = "TO_RESUME", //ready to resume afer interruption
                TO_PROCESS = "TO_PROCESS", //same as null value
                PROCESSED = "PROCESSED";
    }

    public interface CREDIT_CARD_AUTH_STATUS {
        public static final String
                PENDING = "PENDING",
                AUTH_SUCCESS = "AUTH_SUCCESS",
                AUTH_FAILED =  "AUTH_FAILED";
    }
    public interface CREDIT_CARD_AUTH_ADDR_STATUS {
        public static final String
                PENDING = "PENDING";
    }

    public interface CREDIT_CARD_TRANS_TYPE_CD{
        public static final String
                DELAYED_CAPTURE = "DELAYED_CAPTURE",
                AUTHORIZATION = "AUTHORIZATION",
                SALE = "SALE";
    }

    public interface CREDIT_CARD_ENC_ALG {
        public static final String
                NONE = "NONE";
    }

    public interface FACILITY_TYPE_CD {
        public static final String
                INDUSTRIAL = "Industrial",
                OFFICE = "Office",
                NON_FOOD_RETAIL = "Non-Food Retail",
                FOOD_RETAIL = "Food Retail",
                CORE = "CORE",
                DC = "DC",
                CORPORATE = "CORPORATE";
    }

    public interface ESTIMATOR_FACILITY_TYPE_CD {
        public static final String
                TEMPLATE = "TEMPLATE",
                MODEL = "MODEL";
    }

    public interface FACILITY_TRAFFIC_CD {
        public static final String
                HIGH = "High",
                MEDIUM = "Medium",
                LOW = "Low";
    }

    public interface VISITOR_TRAFFIC_CD {
        public static final String
                HIGH = "High",
                MEDIUM = "Medium",
                LOW = "Low",
                NA = "N/A";
    }

    public interface APPEARANCE_STANDARD_CD {
        public static final String
                HIGH = "High",
                MEDIUM = "Medium",
                LOW = "Low",
                NA = "N/A";
    }

    public interface CHEMICAL_USAGE_MODEL_CD {
        public static final String
                RTU = "RTU",
                DILUTION = "Dilution";
    }

    public interface FACILITY_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                DELETED = "DELETED";
    }

    public interface LINER_RATIO_BASE_CD {
        public static final String
                EMPLOYEE = "EMPLOYEE",
                RETAIL_STATION = "RETAIL_STATION";
    }

    public interface CLEANING_ACTIVITY_CD {
        public static final String
                APPLY_BONNET_CLEANER = "Apply bonnet cleaner",
                APPLY_CLEANING_SOLUTION = "Apply cleaning solution",
                APPLY_FINISH = "Apply finish",
                APPLY_SEALER = "Apply sealer",
                APPLY_STRIPPING_SOLUTION = "Apply stripping solution",
                APPLY_WATER_BASE_FINISH = "Apply water base finish",
                APPLY_WATER_BASE_SEALER = "Apply water base sealer",
                AUTO_SCRUB = "Auto scrub",
                BONNET_CLEAN_AREA = "Bonnet clean area",
                BURNISH_FLOOR = "Burnish floor",
                CLEAN_BASEBOARDS = "Clean baseboards",
                CLEAR_WATER_RINSE = "Clear water rinse",
                DUST_MOP_FLOOR = "Dust mop floor",
                EXTRACT_CARPETS = "Extract carpets",
                PICK_UP_SOLUTION = "Pick up solution",
                PRE_SPRAY_CARPETS = "Pre-spray carpets",
                RAKE_CARPETS = "Rake carpets",
                SCREEN_FLOOR = "Screen floor",
                SCRUB_FLOOR = "Scrub floor",
                SHAMPOO_CARPET = "Shampoo carpet",
                CARPET_SPOT_VACUUM = "Carpet Spot Vacuum",
                STRIP_FLOOR = "Strip floor",
                TACK_FLOOR = "Tack floor",
                TREAT_CARPET_SPOTS = "Treat carpet spots",
                VACUUM_CARPET = "Vacuum carpet",
                WET_MOP_FLOOR = "Wet mop floor";
    }

    public interface ESTIMATOR_OBJECT_CD {
        public static final String
                CLEANING_PROCEDURE = "CLEANING_PROCEDURE",
                CLEANING_ACTIVITY = "CLEANING_ACTIVITY",
                CARE_OBJECT = "CARE_OBJECT";
    }

    public interface PRODUCT_UNIT_CD {
        public static final String
                GAL = "Gal",
                LITER = "Liter",
                OZ = "Oz",
                UNIT = "Unit";
    }

    public interface CARE_OBJECT_UNIT_CD {
        public static final String
                SQ_F = "Sq.f",
                USE = "Use";
    }

    public interface UNIT_CD {
        public static final String
                PROCEDURE = "Procedure",
                FACILITY = "Facility",
                FEET = "Feet",
                INCH = "Inch",
                SQ_F = "Sq.f",
                USE = "Use",
                GAL = "Gal",
                LITER = "Liter",
                OZ = "Oz",
                UNIT = "Unit",
                DAY = "Day",
                WORKING_DAY = "Working Day",
                WEEK = "Week",
                QUARTER = "Quarter" ,
                YEAR = "Year",
                FTE = "FTE",
                TOILET = "Toilet",
                SHOWER = "Shower",
                RESTROOM = "Restroom",
                PACK = "Pack(s)",
                LB = "Lb";
    }

    public interface PROD_APPL_PARAM_CD {
        public static final String
                DILUTION = "Dilution";
    }

    public interface TIME_PERIOD_CD {
        public static final String
                DAY = "Day",
                WORKING_DAY = "Working Day",
                WEEK = "Week",
                MONTH = "Month",
                QUARTER = "Quarter" ,
                YEAR = "Year";
    }

    public interface CLEANING_ROLE_CD {
        public static final String
                CHEMICAL = "Chemical",
                ACCESSORY = "Accessory",
                EQUIPMENT = "Equipment";
    }

    public interface FLOOR_TYPE_CD {
        public static final String
                VCT_TILE = "VCT Tile",
                CERAMIC_TILE = "Ceramic Tile",
                TERRAZZO = "Terrazzo",
                CONCRETE = "Concrete",
                CARPET = "Carpet",
                WOOD = "Wood";
    }

    public interface CLEANING_SCHEDULE_CD {
        public static final String
                HT_CARPET_FLOOR_CLEANING = "HT_CARPET_FLOOR_CLEANING",
                HT_CERAMIC_FLOOR_CLEANING = "HT_CERAMIC_FLOOR_CLEANING",
                HT_CONCRETE_FLOOR_CLEANING = "HT_CONCRETE_FLOOR_CLEANING",
                HT_TERRAZZO_FLOOR_CLEANING = "HT_TERRAZZO_FLOOR_CLEANING",
                HT_VCT_FLOOR_CLEANING = "HT_VCT_FLOOR_CLEANING",
                HT_WOOD_FLOOR_CLEANING = "HT_WOOD_FLOOR_CLEANING",
                LT_CARPET_FLOOR_CLEANING = "LT_CARPET_FLOOR_CLEANING",
                LT_CERAMIC_FLOOR_CLEANING = "LT_CERAMIC_FLOOR_CLEANING",
                LT_CONCRETE_FLOOR_CLEANING = "LT_CONCRETE_FLOOR_CLEANING",
                LT_TERRAZZO_FLOOR_CLEANING = "LT_TERRAZZO_FLOOR_CLEANING",
                LT_VCT_FLOOR_CLEANING = "LT_VCT_FLOOR_CLEANING",
                LT_WOOD_FLOOR_CLEANING = "LT_WOOD_FLOOR_CLEANING",
                MT_CARPET_FLOOR_CLEANING = "MT_CARPET_FLOOR_CLEANING",
                MT_CERAMIC_FLOOR_CLEANING = "MT_CERAMIC_FLOOR_CLEANING",
                MT_CONCRETE_FLOOR_CLEANING = "MT_CONCRETE_FLOOR_CLEANING",
                MT_TERRAZZO_FLOOR_CLEANING = "MT_TERRAZZO_FLOOR_CLEANING",
                MT_VCT_FLOOR_CLEANING = "MT_VCT_FLOOR_CLEANING",
                MT_WOOD_FLOOR_CLEANING = "MT_WOOD_FLOOR_CLEANING",
                HAND_SOAP_SUPPLY = "HAND_SOAP_SUPPLY",
                OFFICE_LINER_SUPPLY = "OFFICE_LINER_SUPPLY",
                COMMONON_AREA_LINER_SUPPLY = "COMMONON_AREA_LINER_SUPPLY",
                CONTAINER_LINER_SUPPLY = "CONTAINER_LINER_SUPPLY",
                BATHROOM_LINER_SUPPLY = "BATHROOM_LINER_SUPPLY",
                PAPER_TOWEL_SUPPLY = "PAPER_TOWEL_SUPPLY",
                SEAT_COVER_SUPPLY = "SEAT_COVER_SUPPLY",
                TOILET_TISSUE_SUPPLY = "TOILET_TISSUE_SUPPLY",
                RESTROOM_CLEANING = "RESTROOM_CLEANING",
                OTHER = "OTHER";
    }


    public interface ESTIMATOR_CALCULATE_CD {
        public static final String
                FLOOR = "Floor",
                BASEBOARD = "Baseboard",
                HAND_SOAP = "Hand Soap",
                LINER = "Liner",
                TOILET = "Toilet",
                SHOWER = "Shower",
                RESTROOM = "Restroom";
    }

    public interface CHARGE_TYPE_CD {
        public static final String
                TOTAL_AMOUNT_FREIGTH = "TOTAL_AMOUNT_FREIGTH",
                RUSH_CHARGE = "RUSH_CHARGE",
                SPECIAL_FREIGHT_RULE = "SPECIAL_FREIGHT_RULE";
    }

    public interface FILTER_OPERATOR_CD {
        public static final String
                EQUALS = "=";
    }

    public interface FREIGHT_CRITERIA_TYPE_CD {
        public static final String
                DOLLARS = "DOLLARS",
                ESTIMATE = "ESTIMATE";
    }

    public interface FREIGHT_CRITERIA_RUNTIME_TYPE {
        public static final String
                IMPLIED = "IMPLIED",
                SELECTABLE = "SELECTABLE";
    }

    public interface UPLOAD_STATUS_CD {
        public static final String
                PROCESSING = "PROCESSING",
                PROCESSED = "PROCESSED";
    }

    public interface COST_CENTER_TAX_TYPE {
        public static final String
                DONT_ALLOCATE_SALES_TAX = "Dont allocate sales tax",  //sales tax should not be
                //alocated in this cost center.  This does not affect the taxability of the
                //item just how it is budgeted for
                MASTER_SALES_TAX_COST_CENTER = "Master sales tax cost center", //All sales tax
                //should be accumulated in this cost center
                ALLOCATE_PRODUCT_SALES_TAX = "Allocate Product Sales Tax"; //allocate sales tax for
        //items that are ordered that are a member of this cost center into this cost center
    }

    public interface RECEIVING_SYSTEM_INVOICE_CD {
        public static final String
                REQUIERE_ENTRY_FIRST_ONLY = "Require Entry First Only",
                DISABLED = "Disabled",
                ENTER_ERRORS_ONLY_FIRST_ONLY = "Enter Errors Only First Only";

    }

    public interface COST_CENTER_ASSOC_CD {
        public static final String
                COST_CENTER_ACCOUNT_CATALOG = "COST_CENTER_ACCOUNT_CATALOG";

    }

    public interface COST_CENTER_STATUS_CD {
        public static final String
                ACTIVE = "ACTIVE",
                INACTIVE = "INACTIVE";

    }

    public interface CURRENCY_POSITION_CD {
        public static final String
                LEFT = "LEFT",
                RIGHT = "RIGHT",
                RIGHT_WITH_SPACE = "RIGHT_WITH_SPACE";

    }

    public interface SHOPPING_MESSAGE_ARG_CD {
        public static final String
                STRING = "STRING",
                INTEGER = "INTEGER",
                PRICE = "PRICE";

    }

    public interface PIPELINE_MESSAGE_ARG_CD {
        public static final String
                STRING = "STRING",
                INTEGER = "INTEGER",
                PRICE = "PRICE",
                MESSAGE_KEY = "MESSAGE_KEY";
    }


    public interface RETURN_LOCATE_TYPE_CD {
        public static final String
                SITE = "SITE",
                ACCOUNT = "ACCOUNT",
                DISTRIBUTOR = "DISTRIBUTOR",
                ITEM = "ITEM",
                CATALOG = "CATALOG";

    }

    public interface CONTROL_TYPE_CD {
        public static final String
                TEXT = "TEXT",
                DROP_DOWN = "DROP_DOWN",
                RADIO = "RADIO";
    }

    public interface ORDER_ITEM_META_NAME {
        public static final String
                CATEGORY_NAME = "CATEGORY_NAME",
                STANDARD_PRODUCT_LIST = "STANDARD_PRODUCT_LIST";
    }
    public interface EXCEPTION_ON_TAX_VALUE
    {
        public static final String YES="true";
        public static final String NO="false";
        public static final String YES_FOR_RESALE="true for resale";
    }

    public interface DIST_INVENTORY_DISPLAY
    {
        public static final String DO_NOT_SHOW="Do not show";
        public static final String SHOW_FLAG="Show Flag";
        public static final String SHOW_QUANTITIES="Show Quantities";
    }

    public interface DIST_EXCHANGE_PROPERTY {
      public static final String EXCHANGE_COMPANY_CODE = "EXCHANGE_COMPANY_CODE";
      public static final String EXCHANGE_INVENTORY_URL = "EXCHANGE_INVENTORY_URL";
      public static final String EXCHANGE_USER = "EXCHANGE_USER";
      public static final String EXCHANGE_PASSWORD = "EXCHANGE_PASSWORD";
    }

    public interface ORDER_ITEM_QTY_SOURCE
    {
        public static final String SHOPPING_CART="SHOPPING_CART";
        public static final String INVENTORY="INVENTORY";
    }

    public interface PROCESS_TYPE_CD {
        public static final String TEMPLATE = "TEMPLATE";
        public static final String ACTIVE = "ACTIVE";
    }

    public interface PROCESS_STATUS_CD {
        public static final String READY           = "READY";
        public static final String ACTIVE          = "ACTIVE";
        public static final String WAIT_READY_TASK = "WAIT_READY_TASK";
        public static final String INACTIVE        = "INACTIVE";

        public static final String FAILED          = "FAILED";
        public static final String REJECTED        = "REJECTED";
        public static final String IN_PROGRESS     = "IN_PROGRESS";
        public static final String FINISHED        = "FINISHED";
        public static final String WF_STOPPED      = "WF_STOPPED";
    }

    public interface DOMAIN_STATUS_CD {
        public static final String ACTIVE          = "ACTIVE";
        public static final String INACTIVE        = "INACTIVE";
    }

    public interface TASK_TYPE_CD {
        public static final String TEMPLATE = "TEMPLATE";
        public static final String ACTIVE = "ACTIVE";
    }

    public interface TASK_OPERATION_TYPE_CD{
        public static final String COMMON = "COMMON";
        public static final String WF = "WF";
    }

    public interface TASK_STATUS_CD {
        public static final String READY    = "READY";
        public static final String ACTIVE   = "ACTIVE";
        public static final String FINISHED   = "FINISHED";
        public static final String FAILED   = "FAILED" ;
        public static final String REJECTED = "REJECTED";
        public static final String IN_PROGRESS ="IN_PROGRESS";
        public static final String INACTIVE ="INACTIVE";
    }

    public interface TASK_PROPERTY_TYPE_CD {
        public static final String MANDATORY = "MANDATORY";
        public static final String INPUT = "INPUT";
        public static final String OUTPUT = "OUTPUT";
        public static final String OPTIONAL = "OPTIONAL";
    }

    public interface PROCESS_PROPERTY_TYPE_CD {
        public static final String MANDATORY = "MANDATORY";
        public static final String USE = "USE";
    }

    public interface TASK_PROPERTY_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface TASK_REF_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface ORDER_ITEM_TAX_EXEMPT_CD {
        public static final String TRUE = "true";
        public static final String FALSE = "false";
    }

    public interface PROCESS_NAMES {
         public static final String OUTBOUND_850 = "OUTBOUND_850";
         public static final String ORDER_NOTIFICATION = "ORDER_NOTIFICATION";
         public static final String OUTBOUND_SERVICE = "OUTBOUND_SERVICE" ;
         public static final String INVOICE_PROCESS = "INVOICE_PROCESS" ;
         public static final String PROCESS_ORDER_850 = "PROCESS_ORDER_850";
         public static final String PROCESS_INBOUND_TRANSACTION = "PROCESS_INBOUND_TRANSACTION";
         public static final String PROCESS_OUTBOUND_TRANSACTION = "PROCESS_OUTBOUND_TRANSACTION";
         public static final String WORK_ORDER_UPDATE = "WORK_ORDER_UPDATE";
         public static final String WORK_ORDER_PROCESS = "WORK_ORDER_PROCESS";
         public static final String WORK_ORDER_SENT_TO_PROVIDER = "WORK_ORDER_SENT_TO_PROVIDER";
         public static final String EVENT_SYS_JOB = "EVENT_SYS_JOB";
         public static final String JAN_PACK_SITE_LOADER = "JAN_PACK_SITE_LOADER";
         public static final String JAN_PACK_ITEM_LOADER = "JAN_PACK_ITEM_LOADER";
         public static final String JAN_PACK_INVOICE_LOADER = "JAN_PACK_INVOICE_LOADER";
         public static final String DISTRIBUTOR_DIM_LOAD = "DISTRIBUTOR_DIM_LOAD";
         public static final String MANUFACTURER_DIM_LOAD = "MANUFACTURER_DIM_LOAD";
         public static final String REPORT_SCH_EVENT_GENERATOR = "REPORT_SCH_EVENT_GENERATOR";
         public static final String REPORT_SCHEDULE = "REPORT_SCHEDULE";
         public static final String EXEC_SQL_SEQUENCE = "EXEC_SQL_SEQUENCE";
         public static final String PROCESS_INBOUND_CHUNKS = "PROCESS_INBOUND_CHUNKS";
         public static final String PROCESS_CORPORATE_SCHEDULED_ORDER = "PROCESS_CORPORATE_SCHEDULED_ORDER";
         public static final Object PROCESS_FTP_JOB_OPERATIONS = "PROCESS_FTP_JOB_OPERATIONS";
         public static final Object PROCESS_REPORT_JOB_OPERATIONS = "PROCESS_REPORT_JOB_OPERATIONS";
         public static final Object PROCESS_APP_CMD_OPERATIONS = "PROCESS_APP_CMD_OPERATIONS";
         public static final String PROCESS_BATCH_ORDERS = "PROCESS_BATCH_ORDERS";
     }


    public interface WEIGHT_UNIT {
        public static final String OUNCE = "OUNCE";
        public static final String POUND = "POUND";
        public static final String KILOGRAMME = "KILOGRAMME";
        public static final String GRAMME = "GRAMME";
    }

    public interface INVENTORY_OG_LIST_UI {
        public static final String COMMON_LIST = "COMMON LIST";
        public static final String SEPARATED_LIST = "SEPARATED LIST";
    }

    public interface CHANGE_STATUS{
        public static final String DELETE = "DELETE";
        public static final String INSERT = "INSERT";
        public static final String UPDATE = "UPDATE";
    }

    public interface PROCESS_VAR_CLASS {
        public static final String STRING = "java.lang.String";
        public static final String INTEGER = "java.lang.Integer";
        public static final String DATE = "java.util.Date";
    }

    public interface DISTR_PO_TYPE {
        public static final String SYSTEM = "SYSTEM";
        public static final String CUSTOMER = "CUSTOMER";
        public static final String REQUEST = "REQUEST";
    }

    public interface SERVICE_PROVIDER_CD {
        public static final String EQUIPMENT = "Equipment";
        public static final String LANDSCAPING = "Landscaping";
		public static final String JANITORIAL = "Janitorial";
        public static final String ELECTRICAL = "Electrical";
    }

    public interface NETWORK_PROP_SHORT_DESC {
        public static final String DISTRIBUTION_CENTER_NO = "DISTRIBUTION_CENTER_NO";
        public static final String DISTRIBUTION_CENTER_NAME = "DISTRIBUTION_CENTER_NAME";
        public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
        public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
        public static final String INVOICE_NUMBER = "INVOICE_NUMBER";
        public static final String INVOICE_DATE = "INVOICE_DATE";
        public static final String INVOICE_TYPE = "INVOICE_TYPE";
        public static final String PO_NUMBER = "PO_NUMBER";
        public static final String LINE_NUMBER = "LINE_NUMBER";
        public static final String QUANTITY = "QUANTITY";
        public static final String QUANTITY_UNIT_OF_MEASURE = "QUANTITY_UNIT_OF_MEASURE";
        public static final String UNIT_PRICE = "UNIT_PRICE";
        public static final String UNIT_PRICE_UNIT_OF_MEASURE = "UNIT_PRICE_UNIT_OF_MEASURE";
        public static final String EXTENDED_WEIGHT = "EXTENDED_WEIGHT";
        public static final String EXTENDED_PRICE = "EXTENDED_PRICE";
        public static final String PRODUCT_NUMBER = "PRODUCT_NUMBER";
        public static final String PRODUCT_NAME = "PRODUCT_NAME";
        public static final String PACK = "PACK";
        public static final String PACK_SIZE = "PACK_SIZE";
        public static final String BRAND = "BRAND";
        public static final String MANUFACTURER_NAME = "MANUFACTURER_NAME";
        public static final String MANUFACTURER_PRODUCT_NO = "MANUFACTURER_PRODUCT_NO";
        public static final String RANDOM_WEIGHT_INDICATOR = "RANDOM_WEIGHT_INDICATOR";
        public static final String SPLIT_CASE_INDICATOR = "SPLIT_CASE_INDICATOR";
        public static final String CATCH_WEIGHT_INDICATOR = "CATCH_WEIGHT_INDICATOR";
        public static final String SPLIT_CASE_UOM = "SPLIT_CASE_UOM";
        public static final String TAX_AMOUNT = "TAX_AMOUNT";
        public static final String UPC = "UPC";
        public static final String FREIGHT_CHARGES = "FREIGHT_CHARGES";
        public static final String DISCOUNTS = "DISCOUNTS";
        public static final String DEVIATED_PRICE = "DEVIATED_PRICE";
    }

    public interface SHOPPING_INFO_STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface SITE_DELIVERY_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface FRAME_TYPE_CD {
        public static final String ACCOUNT_TEMPLATE = "ACCOUNT_TEMPLATE";
        public static final String ACCOUNT_UI_FRAME = "ACCOUNT_UI_FRAME";
    }
    public interface SLOT_TYPE_CD {
        public static final String IMAGE = "IMAGE";
        public static final String HTML_TEXT = "HTML_TEXT";
    }

    public interface FRAME_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface TIME_ZONE_CD {
		public static final String
ETC_GMT_P12="Etc/GMT+12",
ACT="ACT",
AET="AET",
AFRICA_ABIDJAN="Africa/Abidjan",
AFRICA_ACCRA="Africa/Accra",
AFRICA_ADDIS_ABABA="Africa/Addis_Ababa",
AFRICA_ALGIERS="Africa/Algiers",
AFRICA_ASMARA="Africa/Asmara",
AFRICA_ASMERA="Africa/Asmera",
AFRICA_BAMAKO="Africa/Bamako",
AFRICA_BANGUI="Africa/Bangui",
AFRICA_BANJUL="Africa/Banjul",
AFRICA_BISSAU="Africa/Bissau",
AFRICA_BLANTYRE="Africa/Blantyre",
AFRICA_BRAZZAVILLE="Africa/Brazzaville",
AFRICA_BUJUMBURA="Africa/Bujumbura",
AFRICA_CAIRO="Africa/Cairo",
AFRICA_CASABLANCA="Africa/Casablanca",
AFRICA_CEUTA="Africa/Ceuta",
AFRICA_CONAKRY="Africa/Conakry",
AFRICA_DAKAR="Africa/Dakar",
AFRICA_DAR_ES_SALAAM="Africa/Dar_es_Salaam",
AFRICA_DJIBOUTI="Africa/Djibouti",
AFRICA_DOUALA="Africa/Douala",
AFRICA_EL_AAIUN="Africa/El_Aaiun",
AFRICA_FREETOWN="Africa/Freetown",
AFRICA_GABORONE="Africa/Gaborone",
AFRICA_HARARE="Africa/Harare",
AFRICA_JOHANNESBURG="Africa/Johannesburg",
AFRICA_KAMPALA="Africa/Kampala",
AFRICA_KHARTOUM="Africa/Khartoum",
AFRICA_KIGALI="Africa/Kigali",
AFRICA_KINSHASA="Africa/Kinshasa",
AFRICA_LAGOS="Africa/Lagos",
AFRICA_LIBREVILLE="Africa/Libreville",
AFRICA_LOME="Africa/Lome",
AFRICA_LUANDA="Africa/Luanda",
AFRICA_LUBUMBASHI="Africa/Lubumbashi",
AFRICA_LUSAKA="Africa/Lusaka",
AFRICA_MALABO="Africa/Malabo",
AFRICA_MAPUTO="Africa/Maputo",
AFRICA_MASERU="Africa/Maseru",
AFRICA_MBABANE="Africa/Mbabane",
AFRICA_MOGADISHU="Africa/Mogadishu",
AFRICA_MONROVIA="Africa/Monrovia",
AFRICA_NAIROBI="Africa/Nairobi",
AFRICA_NDJAMENA="Africa/Ndjamena",
AFRICA_NIAMEY="Africa/Niamey",
AFRICA_NOUAKCHOTT="Africa/Nouakchott",
AFRICA_OUAGADOUGOU="Africa/Ouagadougou",
AFRICA_PORTO_MNOVO="Africa/Porto-Novo",
AFRICA_SAO_TOME="Africa/Sao_Tome",
AFRICA_TIMBUKTU="Africa/Timbuktu",
AFRICA_TRIPOLI="Africa/Tripoli",
AFRICA_TUNIS="Africa/Tunis",
AFRICA_WINDHOEK="Africa/Windhoek",
AGT="AGT",
AMERICA_ADAK="America/Adak",
AMERICA_ANCHORAGE="America/Anchorage",
AMERICA_ANGUILLA="America/Anguilla",
AMERICA_ANTIGUA="America/Antigua",
AMERICA_ARAGUAINA="America/Araguaina",
AMERICA_ARGENTINA_BUENOS_AIRES="America/Argentina/Buenos_Aires",
AMERICA_ARGENTINA_CATAMARCA="America/Argentina/Catamarca",
//AMERICA_ARGENTINA_COMODRIVADAVIA="America/Argentina/ComodRivadavia",
AMERICA_ARGENTINA_CORDOBA="America/Argentina/Cordoba",
AMERICA_ARGENTINA_JUJUY="America/Argentina/Jujuy",
AMERICA_ARGENTINA_LA_RIOJA="America/Argentina/La_Rioja",
AMERICA_ARGENTINA_MENDOZA="America/Argentina/Mendoza",
AMERICA_ARGENTINA_RIO_GALLEGOS="America/Argentina/Rio_Gallegos",
AMERICA_ARGENTINA_SAN_JUAN="America/Argentina/San_Juan",
AMERICA_ARGENTINA_TUCUMAN="America/Argentina/Tucuman",
AMERICA_ARGENTINA_USHUAIA="America/Argentina/Ushuaia",
AMERICA_ARUBA="America/Aruba",
AMERICA_ASUNCION="America/Asuncion",
AMERICA_ATIKOKAN="America/Atikokan",
AMERICA_ATKA="America/Atka",
AMERICA_BAHIA="America/Bahia",
AMERICA_BARBADOS="America/Barbados",
AMERICA_BELEM="America/Belem",
AMERICA_BELIZE="America/Belize",
AMERICA_BLANC_MSABLON="America/Blanc-Sablon",
AMERICA_BOA_VISTA="America/Boa_Vista",
AMERICA_BOGOTA="America/Bogota",
AMERICA_BOISE="America/Boise",
AMERICA_BUENOS_AIRES="America/Buenos_Aires",
AMERICA_CAMBRIDGE_BAY="America/Cambridge_Bay",
AMERICA_CAMPO_GRANDE="America/Campo_Grande",
AMERICA_CANCUN="America/Cancun",
AMERICA_CARACAS="America/Caracas",
AMERICA_CATAMARCA="America/Catamarca",
AMERICA_CAYENNE="America/Cayenne",
AMERICA_CAYMAN="America/Cayman",
AMERICA_CHICAGO="America/Chicago",
AMERICA_CHIHUAHUA="America/Chihuahua",
AMERICA_CORAL_HARBOUR="America/Coral_Harbour",
AMERICA_CORDOBA="America/Cordoba",
AMERICA_COSTA_RICA="America/Costa_Rica",
AMERICA_CUIABA="America/Cuiaba",
AMERICA_CURACAO="America/Curacao",
AMERICA_DANMARKSHAVN="America/Danmarkshavn",
AMERICA_DAWSON="America/Dawson",
AMERICA_DAWSON_CREEK="America/Dawson_Creek",
AMERICA_DENVER="America/Denver",
AMERICA_DETROIT="America/Detroit",
AMERICA_DOMINICA="America/Dominica",
AMERICA_EDMONTON="America/Edmonton",
AMERICA_EIRUNEPE="America/Eirunepe",
AMERICA_EL_SALVADOR="America/El_Salvador",
AMERICA_ENSENADA="America/Ensenada",
AMERICA_FORT_WAYNE="America/Fort_Wayne",
AMERICA_FORTALEZA="America/Fortaleza",
AMERICA_GLACE_BAY="America/Glace_Bay",
AMERICA_GODTHAB="America/Godthab",
AMERICA_GOOSE_BAY="America/Goose_Bay",
AMERICA_GRAND_TURK="America/Grand_Turk",
AMERICA_GRENADA="America/Grenada",
AMERICA_GUADELOUPE="America/Guadeloupe",
AMERICA_GUATEMALA="America/Guatemala",
AMERICA_GUAYAQUIL="America/Guayaquil",
AMERICA_GUYANA="America/Guyana",
AMERICA_HALIFAX="America/Halifax",
AMERICA_HAVANA="America/Havana",
AMERICA_HERMOSILLO="America/Hermosillo",
AMERICA_INDIANA_INDIANAPOLIS="America/Indiana/Indianapolis",
AMERICA_INDIANA_KNOX="America/Indiana/Knox",
AMERICA_INDIANA_MARENGO="America/Indiana/Marengo",
AMERICA_INDIANA_PETERSBURG="America/Indiana/Petersburg",
AMERICA_INDIANA_TELL_CITY="America/Indiana/Tell_City",
AMERICA_INDIANA_VEVAY="America/Indiana/Vevay",
AMERICA_INDIANA_VINCENNES="America/Indiana/Vincennes",
AMERICA_INDIANA_WINAMAC="America/Indiana/Winamac",
AMERICA_INDIANAPOLIS="America/Indianapolis",
AMERICA_INUVIK="America/Inuvik",
AMERICA_IQALUIT="America/Iqaluit",
AMERICA_JAMAICA="America/Jamaica",
AMERICA_JUJUY="America/Jujuy",
AMERICA_JUNEAU="America/Juneau",
AMERICA_KENTUCKY_LOUISVILLE="America/Kentucky/Louisville",
AMERICA_KENTUCKY_MONTICELLO="America/Kentucky/Monticello",
AMERICA_KNOX_IN="America/Knox_IN",
AMERICA_LA_PAZ="America/La_Paz",
AMERICA_LIMA="America/Lima",
AMERICA_LOS_ANGELES="America/Los_Angeles",
AMERICA_LOUISVILLE="America/Louisville",
AMERICA_MACEIO="America/Maceio",
AMERICA_MANAGUA="America/Managua",
AMERICA_MANAUS="America/Manaus",
AMERICA_MARTINIQUE="America/Martinique",
AMERICA_MAZATLAN="America/Mazatlan",
AMERICA_MENDOZA="America/Mendoza",
AMERICA_MENOMINEE="America/Menominee",
AMERICA_MERIDA="America/Merida",
AMERICA_MEXICO_CITY="America/Mexico_City",
AMERICA_MIQUELON="America/Miquelon",
AMERICA_MONCTON="America/Moncton",
AMERICA_MONTERREY="America/Monterrey",
AMERICA_MONTEVIDEO="America/Montevideo",
AMERICA_MONTREAL="America/Montreal",
AMERICA_MONTSERRAT="America/Montserrat",
AMERICA_NASSAU="America/Nassau",
AMERICA_NEW_YORK="America/New_York",
AMERICA_NIPIGON="America/Nipigon",
AMERICA_NOME="America/Nome",
AMERICA_NORONHA="America/Noronha",
AMERICA_NORTH_DAKOTA_CENTER="America/North_Dakota/Center",
AMERICA_NORTH_DAKOTA_NEW_SALEM="America/North_Dakota/New_Salem",
AMERICA_PANAMA="America/Panama",
AMERICA_PANGNIRTUNG="America/Pangnirtung",
AMERICA_PARAMARIBO="America/Paramaribo",
AMERICA_PHOENIX="America/Phoenix",
AMERICA_PORT_OF_SPAIN="America/Port_of_Spain",
AMERICA_PORT_MAU_MPRINCE="America/Port-au-Prince",
AMERICA_PORTO_ACRE="America/Porto_Acre",
AMERICA_PORTO_VELHO="America/Porto_Velho",
AMERICA_PUERTO_RICO="America/Puerto_Rico",
AMERICA_RAINY_RIVER="America/Rainy_River",
AMERICA_RANKIN_INLET="America/Rankin_Inlet",
AMERICA_RECIFE="America/Recife",
AMERICA_REGINA="America/Regina",
AMERICA_RESOLUTE="America/Resolute",
AMERICA_RIO_BRANCO="America/Rio_Branco",
AMERICA_ROSARIO="America/Rosario",
AMERICA_SANTIAGO="America/Santiago",
AMERICA_SANTO_DOMINGO="America/Santo_Domingo",
AMERICA_SAO_PAULO="America/Sao_Paulo",
AMERICA_SCORESBYSUND="America/Scoresbysund",
AMERICA_SHIPROCK="America/Shiprock",
AMERICA_ST_JOHNS="America/St_Johns",
AMERICA_ST_KITTS="America/St_Kitts",
AMERICA_ST_LUCIA="America/St_Lucia",
AMERICA_ST_THOMAS="America/St_Thomas",
AMERICA_ST_VINCENT="America/St_Vincent",
AMERICA_SWIFT_CURRENT="America/Swift_Current",
AMERICA_TEGUCIGALPA="America/Tegucigalpa",
AMERICA_THULE="America/Thule",
AMERICA_THUNDER_BAY="America/Thunder_Bay",
AMERICA_TIJUANA="America/Tijuana",
AMERICA_TORONTO="America/Toronto",
AMERICA_TORTOLA="America/Tortola",
AMERICA_VANCOUVER="America/Vancouver",
AMERICA_VIRGIN="America/Virgin",
AMERICA_WHITEHORSE="America/Whitehorse",
AMERICA_WINNIPEG="America/Winnipeg",
AMERICA_YAKUTAT="America/Yakutat",
AMERICA_YELLOWKNIFE="America/Yellowknife",
ANTARCTICA_CASEY="Antarctica/Casey",
ANTARCTICA_DAVIS="Antarctica/Davis",
ANTARCTICA_DUMONTDURVILLE="Antarctica/DumontDUrville",
ANTARCTICA_MAWSON="Antarctica/Mawson",
ANTARCTICA_MCMURDO="Antarctica/McMurdo",
ANTARCTICA_PALMER="Antarctica/Palmer",
ANTARCTICA_ROTHERA="Antarctica/Rothera",
ANTARCTICA_SOUTH_POLE="Antarctica/South_Pole",
ANTARCTICA_SYOWA="Antarctica/Syowa",
ANTARCTICA_VOSTOK="Antarctica/Vostok",
ARCTIC_LONGYEARBYEN="Arctic/Longyearbyen",
ART="ART",
ASIA_ADEN="Asia/Aden",
ASIA_ALMATY="Asia/Almaty",
ASIA_AMMAN="Asia/Amman",
ASIA_ANADYR="Asia/Anadyr",
ASIA_AQTAU="Asia/Aqtau",
ASIA_AQTOBE="Asia/Aqtobe",
ASIA_ASHGABAT="Asia/Ashgabat",
ASIA_ASHKHABAD="Asia/Ashkhabad",
ASIA_BAGHDAD="Asia/Baghdad",
ASIA_BAHRAIN="Asia/Bahrain",
ASIA_BAKU="Asia/Baku",
ASIA_BANGKOK="Asia/Bangkok",
ASIA_BEIRUT="Asia/Beirut",
ASIA_BISHKEK="Asia/Bishkek",
ASIA_BRUNEI="Asia/Brunei",
ASIA_CALCUTTA="Asia/Calcutta",
ASIA_CHOIBALSAN="Asia/Choibalsan",
ASIA_CHONGQING="Asia/Chongqing",
ASIA_CHUNGKING="Asia/Chungking",
ASIA_COLOMBO="Asia/Colombo",
ASIA_DACCA="Asia/Dacca",
ASIA_DAMASCUS="Asia/Damascus",
ASIA_DHAKA="Asia/Dhaka",
ASIA_DILI="Asia/Dili",
ASIA_DUBAI="Asia/Dubai",
ASIA_DUSHANBE="Asia/Dushanbe",
ASIA_GAZA="Asia/Gaza",
ASIA_HARBIN="Asia/Harbin",
ASIA_HONG_KONG="Asia/Hong_Kong",
ASIA_HOVD="Asia/Hovd",
ASIA_IRKUTSK="Asia/Irkutsk",
ASIA_ISTANBUL="Asia/Istanbul",
ASIA_JAKARTA="Asia/Jakarta",
ASIA_JAYAPURA="Asia/Jayapura",
ASIA_JERUSALEM="Asia/Jerusalem",
ASIA_KABUL="Asia/Kabul",
ASIA_KAMCHATKA="Asia/Kamchatka",
ASIA_KARACHI="Asia/Karachi",
ASIA_KASHGAR="Asia/Kashgar",
ASIA_KATMANDU="Asia/Katmandu",
ASIA_KRASNOYARSK="Asia/Krasnoyarsk",
ASIA_KUALA_LUMPUR="Asia/Kuala_Lumpur",
ASIA_KUCHING="Asia/Kuching",
ASIA_KUWAIT="Asia/Kuwait",
ASIA_MACAO="Asia/Macao",
ASIA_MACAU="Asia/Macau",
ASIA_MAGADAN="Asia/Magadan",
ASIA_MAKASSAR="Asia/Makassar",
ASIA_MANILA="Asia/Manila",
ASIA_MUSCAT="Asia/Muscat",
ASIA_NICOSIA="Asia/Nicosia",
ASIA_NOVOSIBIRSK="Asia/Novosibirsk",
ASIA_OMSK="Asia/Omsk",
ASIA_ORAL="Asia/Oral",
ASIA_PHNOM_PENH="Asia/Phnom_Penh",
ASIA_PONTIANAK="Asia/Pontianak",
ASIA_PYONGYANG="Asia/Pyongyang",
ASIA_QATAR="Asia/Qatar",
ASIA_QYZYLORDA="Asia/Qyzylorda",
ASIA_RANGOON="Asia/Rangoon",
ASIA_RIYADH="Asia/Riyadh",
ASIA_RIYADH87="Asia/Riyadh87",
ASIA_RIYADH88="Asia/Riyadh88",
ASIA_RIYADH89="Asia/Riyadh89",
ASIA_SAIGON="Asia/Saigon",
ASIA_SAKHALIN="Asia/Sakhalin",
ASIA_SAMARKAND="Asia/Samarkand",
ASIA_SEOUL="Asia/Seoul",
ASIA_SHANGHAI="Asia/Shanghai",
ASIA_SINGAPORE="Asia/Singapore",
ASIA_TAIPEI="Asia/Taipei",
ASIA_TASHKENT="Asia/Tashkent",
ASIA_TBILISI="Asia/Tbilisi",
ASIA_TEHRAN="Asia/Tehran",
ASIA_TEL_AVIV="Asia/Tel_Aviv",
ASIA_THIMBU="Asia/Thimbu",
ASIA_THIMPHU="Asia/Thimphu",
ASIA_TOKYO="Asia/Tokyo",
ASIA_UJUNG_PANDANG="Asia/Ujung_Pandang",
ASIA_ULAANBAATAR="Asia/Ulaanbaatar",
ASIA_ULAN_BATOR="Asia/Ulan_Bator",
ASIA_URUMQI="Asia/Urumqi",
ASIA_VIENTIANE="Asia/Vientiane",
ASIA_VLADIVOSTOK="Asia/Vladivostok",
ASIA_YAKUTSK="Asia/Yakutsk",
ASIA_YEKATERINBURG="Asia/Yekaterinburg",
ASIA_YEREVAN="Asia/Yerevan",
AST="AST",
ATLANTIC_AZORES="Atlantic/Azores",
ATLANTIC_BERMUDA="Atlantic/Bermuda",
ATLANTIC_CANARY="Atlantic/Canary",
ATLANTIC_CAPE_VERDE="Atlantic/Cape_Verde",
ATLANTIC_FAEROE="Atlantic/Faeroe",
ATLANTIC_FAROE="Atlantic/Faroe",
ATLANTIC_JAN_MAYEN="Atlantic/Jan_Mayen",
ATLANTIC_MADEIRA="Atlantic/Madeira",
ATLANTIC_REYKJAVIK="Atlantic/Reykjavik",
ATLANTIC_SOUTH_GEORGIA="Atlantic/South_Georgia",
ATLANTIC_ST_HELENA="Atlantic/St_Helena",
ATLANTIC_STANLEY="Atlantic/Stanley",
AUSTRALIA_ACT="Australia/ACT",
AUSTRALIA_ADELAIDE="Australia/Adelaide",
AUSTRALIA_BRISBANE="Australia/Brisbane",
AUSTRALIA_BROKEN_HILL="Australia/Broken_Hill",
AUSTRALIA_CANBERRA="Australia/Canberra",
AUSTRALIA_CURRIE="Australia/Currie",
AUSTRALIA_DARWIN="Australia/Darwin",
AUSTRALIA_EUCLA="Australia/Eucla",
AUSTRALIA_HOBART="Australia/Hobart",
AUSTRALIA_LHI="Australia/LHI",
AUSTRALIA_LINDEMAN="Australia/Lindeman",
AUSTRALIA_LORD_HOWE="Australia/Lord_Howe",
AUSTRALIA_MELBOURNE="Australia/Melbourne",
AUSTRALIA_NORTH="Australia/North",
AUSTRALIA_NSW="Australia/NSW",
AUSTRALIA_PERTH="Australia/Perth",
AUSTRALIA_QUEENSLAND="Australia/Queensland",
AUSTRALIA_SOUTH="Australia/South",
AUSTRALIA_SYDNEY="Australia/Sydney",
AUSTRALIA_TASMANIA="Australia/Tasmania",
AUSTRALIA_VICTORIA="Australia/Victoria",
AUSTRALIA_WEST="Australia/West",
AUSTRALIA_YANCOWINNA="Australia/Yancowinna",
BET="BET",
BRAZIL_ACRE="Brazil/Acre",
BRAZIL_DENORONHA="Brazil/DeNoronha",
BRAZIL_EAST="Brazil/East",
BRAZIL_WEST="Brazil/West",
BST="BST",
CANADA_ATLANTIC="Canada/Atlantic",
CANADA_CENTRAL="Canada/Central",
CANADA_EASTERN="Canada/Eastern",
CANADA_EAST_MSASKATCHEWAN="Canada/East-Saskatchewan",
CANADA_MOUNTAIN="Canada/Mountain",
CANADA_NEWFOUNDLAND="Canada/Newfoundland",
CANADA_PACIFIC="Canada/Pacific",
CANADA_SASKATCHEWAN="Canada/Saskatchewan",
CANADA_YUKON="Canada/Yukon",
CAT="CAT",
CET="CET",
CHILE_CONTINENTAL="Chile/Continental",
CHILE_EASTERISLAND="Chile/EasterIsland",
CNT="CNT",
CST="CST",
CST6CDT="CST6CDT",
CTT="CTT",
CUBA="Cuba",
EAT="EAT",
ECT="ECT",
EET="EET",
EGYPT="Egypt",
EIRE="Eire",
EST="EST",
EST5EDT="EST5EDT",
ETC_GMT="Etc/GMT",
ETC_GMT_P0="Etc/GMT+0",
ETC_GMT_P1="Etc/GMT+1",
ETC_GMT_P10="Etc/GMT+10",
ETC_GMT_P11="Etc/GMT+11",
ETC_GMT_P2="Etc/GMT+2",
ETC_GMT_P3="Etc/GMT+3",
ETC_GMT_P4="Etc/GMT+4",
ETC_GMT_P5="Etc/GMT+5",
ETC_GMT_P6="Etc/GMT+6",
ETC_GMT_P7="Etc/GMT+7",
ETC_GMT_P8="Etc/GMT+8",
ETC_GMT_P9="Etc/GMT+9",
ETC_GMT0="Etc/GMT0",
ETC_GMT_M0="Etc/GMT-0",
ETC_GMT_M1="Etc/GMT-1",
ETC_GMT_M10="Etc/GMT-10",
ETC_GMT_M11="Etc/GMT-11",
ETC_GMT_M12="Etc/GMT-12",
ETC_GMT_M13="Etc/GMT-13",
ETC_GMT_M14="Etc/GMT-14",
ETC_GMT_M2="Etc/GMT-2",
ETC_GMT_M3="Etc/GMT-3",
ETC_GMT_M4="Etc/GMT-4",
ETC_GMT_M5="Etc/GMT-5",
ETC_GMT_M6="Etc/GMT-6",
ETC_GMT_M7="Etc/GMT-7",
ETC_GMT_M8="Etc/GMT-8",
ETC_GMT_M9="Etc/GMT-9",
ETC_GREENWICH="Etc/Greenwich",
ETC_UCT="Etc/UCT",
ETC_UNIVERSAL="Etc/Universal",
ETC_UTC="Etc/UTC",
ETC_ZULU="Etc/Zulu",
EUROPE_AMSTERDAM="Europe/Amsterdam",
EUROPE_ANDORRA="Europe/Andorra",
EUROPE_ATHENS="Europe/Athens",
EUROPE_BELFAST="Europe/Belfast",
EUROPE_BELGRADE="Europe/Belgrade",
EUROPE_BERLIN="Europe/Berlin",
EUROPE_BRATISLAVA="Europe/Bratislava",
EUROPE_BRUSSELS="Europe/Brussels",
EUROPE_BUCHAREST="Europe/Bucharest",
EUROPE_BUDAPEST="Europe/Budapest",
EUROPE_CHISINAU="Europe/Chisinau",
EUROPE_COPENHAGEN="Europe/Copenhagen",
EUROPE_DUBLIN="Europe/Dublin",
EUROPE_GIBRALTAR="Europe/Gibraltar",
EUROPE_GUERNSEY="Europe/Guernsey",
EUROPE_HELSINKI="Europe/Helsinki",
EUROPE_ISLE_OF_MAN="Europe/Isle_of_Man",
EUROPE_ISTANBUL="Europe/Istanbul",
EUROPE_JERSEY="Europe/Jersey",
EUROPE_KALININGRAD="Europe/Kaliningrad",
EUROPE_KIEV="Europe/Kiev",
EUROPE_LISBON="Europe/Lisbon",
EUROPE_LJUBLJANA="Europe/Ljubljana",
EUROPE_LONDON="Europe/London",
EUROPE_LUXEMBOURG="Europe/Luxembourg",
EUROPE_MADRID="Europe/Madrid",
EUROPE_MALTA="Europe/Malta",
EUROPE_MARIEHAMN="Europe/Mariehamn",
EUROPE_MINSK="Europe/Minsk",
EUROPE_MONACO="Europe/Monaco",
EUROPE_MOSCOW="Europe/Moscow",
EUROPE_NICOSIA="Europe/Nicosia",
EUROPE_OSLO="Europe/Oslo",
EUROPE_PARIS="Europe/Paris",
EUROPE_PODGORICA="Europe/Podgorica",
EUROPE_PRAGUE="Europe/Prague",
EUROPE_RIGA="Europe/Riga",
EUROPE_ROME="Europe/Rome",
EUROPE_SAMARA="Europe/Samara",
EUROPE_SAN_MARINO="Europe/San_Marino",
EUROPE_SARAJEVO="Europe/Sarajevo",
EUROPE_SIMFEROPOL="Europe/Simferopol",
EUROPE_SKOPJE="Europe/Skopje",
EUROPE_SOFIA="Europe/Sofia",
EUROPE_STOCKHOLM="Europe/Stockholm",
EUROPE_TALLINN="Europe/Tallinn",
EUROPE_TIRANE="Europe/Tirane",
EUROPE_TIRASPOL="Europe/Tiraspol",
EUROPE_UZHGOROD="Europe/Uzhgorod",
EUROPE_VADUZ="Europe/Vaduz",
EUROPE_VATICAN="Europe/Vatican",
EUROPE_VIENNA="Europe/Vienna",
EUROPE_VILNIUS="Europe/Vilnius",
EUROPE_VOLGOGRAD="Europe/Volgograd",
EUROPE_WARSAW="Europe/Warsaw",
EUROPE_ZAGREB="Europe/Zagreb",
EUROPE_ZAPOROZHYE="Europe/Zaporozhye",
EUROPE_ZURICH="Europe/Zurich",
GB="GB",
GB_MEIRE="GB-Eire",
GMT="GMT",
GMT0="GMT0",
GREENWICH="Greenwich",
HONGKONG="Hongkong",
HST="HST",
ICELAND="Iceland",
IET="IET",
INDIAN_ANTANANARIVO="Indian/Antananarivo",
INDIAN_CHAGOS="Indian/Chagos",
INDIAN_CHRISTMAS="Indian/Christmas",
INDIAN_COCOS="Indian/Cocos",
INDIAN_COMORO="Indian/Comoro",
INDIAN_KERGUELEN="Indian/Kerguelen",
INDIAN_MAHE="Indian/Mahe",
INDIAN_MALDIVES="Indian/Maldives",
INDIAN_MAURITIUS="Indian/Mauritius",
INDIAN_MAYOTTE="Indian/Mayotte",
INDIAN_REUNION="Indian/Reunion",
IRAN="Iran",
ISRAEL="Israel",
IST="IST",
JAMAICA="Jamaica",
JAPAN="Japan",
JST="JST",
KWAJALEIN="Kwajalein",
LIBYA="Libya",
MET="MET",
MEXICO_BAJANORTE="Mexico/BajaNorte",
MEXICO_BAJASUR="Mexico/BajaSur",
MEXICO_GENERAL="Mexico/General",
MIDEAST_RIYADH87="Mideast/Riyadh87",
MIDEAST_RIYADH88="Mideast/Riyadh88",
MIDEAST_RIYADH89="Mideast/Riyadh89",
MIT="MIT",
MST="MST",
MST7MDT="MST7MDT",
NAVAJO="Navajo",
NET="NET",
NST="NST",
NZ="NZ",
NZ_MCHAT="NZ-CHAT",
PACIFIC_APIA="Pacific/Apia",
PACIFIC_AUCKLAND="Pacific/Auckland",
PACIFIC_CHATHAM="Pacific/Chatham",
PACIFIC_EASTER="Pacific/Easter",
PACIFIC_EFATE="Pacific/Efate",
PACIFIC_ENDERBURY="Pacific/Enderbury",
PACIFIC_FAKAOFO="Pacific/Fakaofo",
PACIFIC_FIJI="Pacific/Fiji",
PACIFIC_FUNAFUTI="Pacific/Funafuti",
PACIFIC_GALAPAGOS="Pacific/Galapagos",
PACIFIC_GAMBIER="Pacific/Gambier",
PACIFIC_GUADALCANAL="Pacific/Guadalcanal",
PACIFIC_GUAM="Pacific/Guam",
PACIFIC_HONOLULU="Pacific/Honolulu",
PACIFIC_JOHNSTON="Pacific/Johnston",
PACIFIC_KIRITIMATI="Pacific/Kiritimati",
PACIFIC_KOSRAE="Pacific/Kosrae",
PACIFIC_KWAJALEIN="Pacific/Kwajalein",
PACIFIC_MAJURO="Pacific/Majuro",
PACIFIC_MARQUESAS="Pacific/Marquesas",
PACIFIC_MIDWAY="Pacific/Midway",
PACIFIC_NAURU="Pacific/Nauru",
PACIFIC_NIUE="Pacific/Niue",
PACIFIC_NORFOLK="Pacific/Norfolk",
PACIFIC_NOUMEA="Pacific/Noumea",
PACIFIC_PAGO_PAGO="Pacific/Pago_Pago",
PACIFIC_PALAU="Pacific/Palau",
PACIFIC_PITCAIRN="Pacific/Pitcairn",
PACIFIC_PONAPE="Pacific/Ponape",
PACIFIC_PORT_MORESBY="Pacific/Port_Moresby",
PACIFIC_RAROTONGA="Pacific/Rarotonga",
PACIFIC_SAIPAN="Pacific/Saipan",
PACIFIC_SAMOA="Pacific/Samoa",
PACIFIC_TAHITI="Pacific/Tahiti",
PACIFIC_TARAWA="Pacific/Tarawa",
PACIFIC_TONGATAPU="Pacific/Tongatapu",
PACIFIC_TRUK="Pacific/Truk",
PACIFIC_WAKE="Pacific/Wake",
PACIFIC_WALLIS="Pacific/Wallis",
PACIFIC_YAP="Pacific/Yap",
PLT="PLT",
PNT="PNT",
POLAND="Poland",
PORTUGAL="Portugal",
PRC="PRC",
PRT="PRT",
PST="PST",
PST8PDT="PST8PDT",
ROK="ROK",
SINGAPORE="Singapore",
SST="SST",
TURKEY="Turkey",
UCT="UCT",
UNIVERSAL="Universal",
US_ALASKA="US/Alaska",
US_ALEUTIAN="US/Aleutian",
US_ARIZONA="US/Arizona",
US_CENTRAL="US/Central",
US_EASTERN="US/Eastern",
US_EAST_MINDIANA="US/East-Indiana",
US_HAWAII="US/Hawaii",
US_INDIANA_MSTARKE="US/Indiana-Starke",
US_MICHIGAN="US/Michigan",
US_MOUNTAIN="US/Mountain",
US_PACIFIC="US/Pacific",
US_PACIFIC_MNEW="US/Pacific-New",
US_SAMOA="US/Samoa",
UTC="UTC",
VST="VST",
WET="WET",
W_MSU="W-SU",
ZULU="Zulu";
}

    public interface SHOP_UI_TYPE {
        String B2B = "B2B";
        String B2C = "B2C";
    }

    /**
     *Used to display product attributes dynamically.  Anything in the clw_item_meta
     *table can be mapped, or the following "special" attributes.
     */
    public interface SHOP_UI_SPECIAL_PROD_ATTRIBUTE {
    	String QTY_ON_HAND_COND="Qty or On Hand Box Conditional", //displays *either* the on hand or order qty depending on
                                                                  //what we are showing.
    	PAR_VALUE="Par value",
    	IA="Inventory Mark",
        ORDER_QTY="Order Qty",
    	ACTUAL_SKU="Actual Sku",
        DIST_SKU = "Distributor Sku",
        SYSTEM_SKU = "System Sku",
        CUSTOMER_SKU = "Customer Sku",
        CATEGORY="Category",
        CATALOG_PRODUCT_SHORT_DESC="Product Short Desc",
        PRICE="Price",
        EXTENDED_PRICE="Extended Price",
        LINE_PRICE="Line Price",
        SELECT="Select Checkbox",
        DELETE="Delete Checkbox",
        RESALE="Resale Checkbox",
        SPL="SPL",
        DIST_INVENTORY_FLAG="Dist Inventory Show Flag",
        DIST_INVENTORY_QTY="Dist Inventory Show Quantity",
        MANU_SKU="Manufacturer Sku",
        MANU_NAME="Manufacturer Name",
        MAX_ORDER_QTY="Max Order Qty",
        DIST_PACK="Dist_Pack",          //Distributor pack information
        PACK="Pack",
        COLOR="Color",
        STATUS="Status",
        GREEN="Green Item",
        MAX_ORDER_QTY_ACCOUNT="Acct's Max Order Qty",
        UNLIMITED_MAX_ORDER_QTY="Unlimited",
        INPUT_UNLIMITED_MAX_ORDER_QTY="*",
        RESET_TO_ACCOUNT_MAX_ORDER_QTY="Reset To Acct",
        RESTRICTION_DAYS="Restr Days",
        RE_SALE_ITEM="Resale Item",
        EMPTY="empty";
    }

    /**
     *indicates where the @see ProductDefDetailData should be used.  The product
     *detail page is treated specially from the rest of the pages (order guides,
     *shopping cart, item list, etc)
     */
    public interface SHOP_UI_PRODUCT_VIEW_CD {
    	String SHOP_UI_DEFAULT = "SHOP_UI_DEFAULT",
    	SHOP_UI_DETAIL = "SHOP_UI_DETAIL",
    	SHOP_UI_MULTI = "SHOP_UI_MULTI";
    }

    public interface PRICE_RULE_TYPE_CD {
    	String
                FREIGHT = "FREIGHT",
                FUEL = "FUEL",
                SMALL_ORDER = "SMALL_ORDER",
                SERVICE_FEE = "SERVICE_FEE";
    }

    public interface PRICE_RULE_DETAIL_TYPE_CD {
    	String
                SERVICE_FEE_CODE = "SERVICE_FEE_CODE",
                SERVICE_FEE_ITEM_NUM = "SERVICE_FEE_ITEM_NUM",
                SERVICE_FEE_AMOUNT = "SERVICE_FEE_AMOUNT";
    }

    public interface CHARGE_CD {
        public static final String
                FUEL_SURCHARGE = "FUEL_SURCHARGE",
                SMALL_ORDER_FEE = "SMALL_ORDER_FEE",
                DISCOUNT = "DISCOUNT";
    }

    public interface REPORT_PAPER_ORIENTATION {
        public static final String PORTRAIT = "PORTRAIT";
        public static final String LANDSCAPE = "LANDSCAPE";
    }

    public interface REPORT_PAPER_SIZE {
        public static final String A0 = "A0";
        public static final String A1 = "A1";
        public static final String A10 = "A10";
        public static final String A2 = "A2";
        public static final String A3 = "A3";
        public static final String A4 = "A4";
        public static final String A5 = "A5";
        public static final String A6 = "A6";
        public static final String A7 = "A7";
        public static final String A8 = "A8";
        public static final String A9 = "A9";
        public static final String ARCH_A = "ARCH_A";
        public static final String ARCH_B = "ARCH_B";
        public static final String ARCH_C = "ARCH_C";
        public static final String ARCH_D = "ARCH_D";
        public static final String ARCH_E = "ARCH_E";
        public static final String B0 = "B0";
        public static final String B1 = "B1";
        public static final String B2 = "B2";
        public static final String B3 = "B3";
        public static final String B4 = "B4";
        public static final String B5 = "B5";
        public static final String FLSA = "FLSA";
        public static final String FLSE = "FLSE";
        public static final String HALFLETTER = "HALFLETTER";
        public static final String LEDGER = "LEDGER";
        public static final String LEGAL = "LEGAL";
        public static final String LETTER = "LETTER";
        public static final String NOTE = "NOTE";
        public static final String _11X17 = "_11X17";
    }

    public interface UI_PAGE_TYPE_CD {
        public static final String SITE = "SITE";
        public static final String USER = "USER";
        public static final String ACCOUNT = "ACCOUNT";
    }

    public interface UI_PAGE_CD {
        public static final String SITE_DETAIL = "SITE_DETAIL";
        public static final String USER_DETAIL = "USER_DETAIL";
        public static final String ACCOUNT_DETAIL = "ACCOUNT_DETAIL";
    }

    public interface UI_CONTROL {

    	public static final String ACCOUNT_ID = "ACCOUNT_ID";
        public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
        public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
        public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
        public static final String ACCOUNT_ORDER_MINIMUM = "ACCOUNT_ORDER_MINIMUM";
        public static final String ACCOUNT_CREDIT_LIMIT = "ACCOUNT_CREDIT_LIMIT";
        public static final String ACCOUNT_CREDIT_RATING = "ACCOUNT_CREDIT_RATING";
        public static final String ACCOUNT_BUDGET_TYPE = "ACCOUNT_BUDGET_TYPE";
        public static final String ACCOUNT_GL_TRANSFORMATION_TYPE = "ACCOUNT_GL_TRANSFORMATION_TYPE";
        public static final String ACCOUNT_TIME_ZONE = "ACCOUNT_TIME_ZONE";
        public static final String ACCOUNT_ALLOW_SITE_LLC = "ACCOUNT_ALLOW_SITE_LLC";
        public static final String ACCOUNT_CUSTOMER_EMAIL = "ACCOUNT_CUSTOMER_EMAIL";
        public static final String ACCOUNT_DEFAULT_EMAIL = "ACCOUNT_DEFAULT_EMAIL";
        public static final String ACCOUNT_CONTRACT_US_CC_EMAIL = "ACCOUNT_CONTRACT_US_CC_EMAIL";
        public static final String ACCOUNT_DISTRIBUTOR_REFNUM = "ACCOUNT_DISTRIBUTOR_REFNUM";
        public static final String ACCOUNT_FIRST_NAME = "ACCOUNT_FIRST_NAME";
        public static final String ACCOUNT_LAST_NAME = "ACCOUNT_LAST_NAME";
        public static final String ACCOUNT_PHONE = "ACCOUNT_PHONE";
        public static final String ACCOUNT_FAX = "ACCOUNT_FAX";
        public static final String ACCOUNT_FAX_BACK_CONFIRM = "ACCOUNT_FAX_BACK_CONFIRM";
        public static final String ACCOUNT_STREET_ADDRESS1 = "ACCOUNT_STREET_ADDRESS1";
        public static final String ACCOUNT_STREET_ADDRESS2 = "ACCOUNT_STREET_ADDRESS2";
        public static final String ACCOUNT_STREET_ADDRESS3 = "ACCOUNT_STREET_ADDRESS3";
        public static final String ACCOUNT_COUNTRY = "ACCOUNT_COUNTRY";
        public static final String ACCOUNT_EMAIL_ADDRESS = "ACCOUNT_EMAIL_ADDRESS";
        public static final String ACCOUNT_STATE = "ACCOUNT_STATE";
        public static final String ACCOUNT_ZIP = "ACCOUNT_ZIP";
        public static final String ACCOUNT_CITY = "ACCOUNT_CITY";
        public static final String ACCOUNT_BILLING_ADDRESS1 = "ACCOUNT_BILLING_ADDRESS1";
        public static final String ACCOUNT_BILLING_ADDRESS2 = "ACCOUNT_BILLING_ADDRESS2";
        public static final String ACCOUNT_BILLING_ADDRESS3 = "ACCOUNT_BILLING_ADDRESS3";
        public static final String ACCOUNT_BILLING_COUNTRY = "ACCOUNT_BILLING_COUNTRY";
        public static final String ACCOUNT_BILLING_CITY = "ACCOUNT_BILLING_CITY";
        public static final String ACCOUNT_BILLING_STATE = "ACCOUNT_BILLING_STATE";
        public static final String ACCOUNT_BILLING_ZIP = "ACCOUNT_BILLING_ZIP";
        public static final String ACCOUNT_ORDER_PHONE = "ACCOUNT_ORDER_PHONE";
        public static final String ACCOUNT_ORDER_FAX = "ACCOUNT_ORDER_FAX";
        public static final String ACCOUNT_PURCHASE_ORDER_NAME = "ACCOUNT_PURCHASE_ORDER_NAME";
        public static final String ACCOUNT_ORDER_GUIDE_COMMENTS = "ACCOUNT_ORDER_GUIDE_COMMENTS";
        public static final String ACCOUNT_ORDER_GUIDE_NOTE = "ACCOUNT_ORDER_GUIDE_NOTE";
        public static final String ACCOUNT_ORDER_SKU = "ACCOUNT_ORDER_SKU";
        public static final String ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES="ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES";
        public static final String ACCOUNT_TAXABLE_INDICATOR = "ACCOUNT_TAXABLE_INDICATOR";
        public static final String ACCOUNT_ALLOW_CHANGE_PASSWORD = "ACCOUNT_ALLOW_CHANGE_PASSWORD";
        public static final String ACCOUNT_CREATE_ERP_SHIP_TO = "ACCOUNT_CREATE_ERP_SHIP_TO";
        public static final String ACCOUNT_ERP_SHIP_TO_COUNTER = "ACCOUNT_ERP_SHIP_TO_COUNTER";
        public static final String ACCOUNT_CRC_SHOP = "ACCOUNT_CRC_SHOP";
        public static final String ACCOUNT_FREIGHT_CHARGE_TYPE = "FREIGHT_CHARGE_TYPE";
        public static final String ACCOUNT_MAKE_SHIP_TO_BILL_TO = "ACCOUNT_MAKE_SHIP_TO_BILL_TO";
        public static final String ACCOUNT_ORDER_MANAGER_EMAILS = "ACCOUNT_ORDER_MANAGER_EMAILS";
        public static final String ACCOUNT_SHOW_SCHEDULED_DELIVERY = "ACCOUNT_SHOW_SCHEDULED_DELIVERY";
        public static final String ACCOUNT_EDI_SHIP_TO_PREFIX = "ACCOUNT_EDI_SHIP_TO_PREFIX";
        public static final String ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED = "ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED";
        public static final String ACCOUNT_RUSH_ORDER_CHARGE = "ACCOUNT_RUSH_ORDER_CHARGE";
        public static final String ACCOUNT_AUTHORIZED_FOR_RESALE = "ACCOUNT_AUTHORIZED_FOR_RESALE";
        public static final String ACCOUNT_RE_SALE_ERP_NUMBER = "ACCOUNT_RE_SALE_ERP_NUMBER";
        public static final String ACCOUNT_ALLOW_ORDER_CONSOLIDATION = "ACCOUNT_ALLOW_ORDER_CONSOLIDATION";
        public static final String ACCOUNT_SCHEDULE_CUTOFF_DAYS="ACCOUNT_SCHEDULE_CUTOFF_DAYS";
        public static final String ACCOUNT_SHOW_DIST_SKU_NUM="ACCOUNT_SHOW_DIST_SKU_NUM";
        public static final String ACCOUNT_FOLDER = "ACCOUNT_FOLDER";
        public static final String ACCOUNT_SHOW_DIST_DELIVERY_DATE="ACCOUNT_SHOW_DIST_DELIVERY_DATE";
        public static final String ACCOUNT_SHOW_SPL="ACCOUNT_SHOW_SPL";
        public static final String ACCOUNT_HOLD_PO="ACCOUNT_HOLD_PO";
        public static final String ACCOUNT_AUTO_ORDER_FACTOR = "AUTO_ORDER_FACTOR";
        public static final String ACCOUNT_MODIFY_QTY_BY_855 ="ACCOUNT_MODIFY_QTY_BY_855";
        public static final String ACCOUNT_ALLOW_ORDER_INV_ITEMS ="ACCOUNT_ALLOW_ORDER_INV_ITEMS";
        public static final String ACCOUNT_ALLOW_REORDER="ACCOUNT_ALLOW_REORDER";
        public static final String ACCOUNT_USE_PHYSICAL_INVENTORY="ACCOUNT_USE_PHYSICAL_INVENTORY";
        public static final String ACCOUNT_CREATE_ORDER_BY_855="ACCOUNT_CREATE_ORDER_BY_855";
        public static final String ACCOUNT_SHOP_UI_TYPE=" ACCOUNT_SHOP_UI_TYPE";
        public static final String ACCOUNT_ALLOW_MODERN_SHOPPING="ACCOUNT_ALLOW_MODERN_SHOPPING";
        public static final String ACCOUNT_FOLDER_NEW="ACCOUNT_FOLDER_NEW";
        public static final String ACCOUNT_FAQ_LINK="ACCOUNT_FAQ_LINK";
        public static final String ACCOUNT_REBATE_PERSENT="ACCOUNT_REBATE_PERSENT";
        public static final String ACCOUNT_REBATE_EFF_DATE="ACCOUNT_REBATE_EFF_DATE";
        public static final String ACCOUNT_SHOW_DIST_INVENTORY="ACCOUNT_SHOW_DIST_INVENTORY";
        public static final String ACCOUNT_TARGET_MARGIN_STR="ACCOUNT_TARGET_MARGIN_STR";
        public static final String ACCOUNT_CUSTOMER_SYSTEM_APPROVAL ="ACCOUNT_CUSTOMER_SYSTEM_APPROVAL";
        public static final String ACCOUNT_SHOW_INV_CART_TOTAL="ACCOUNT_SHOW_INV_CART_TOTAL";
        public static final String ACCOUNT_CART_REMINDER_INTERVAL="ACCOUNT_CART_REMINDER_INTERVAL";
        public static final String ACCOUNT_SHOW_MY_SHOPPING_LISTS="ACCOUNT_SHOW_MY_SHOPPING_LISTS";
        public static final String ACCOUNT_ALLOW_CREDIT_CARD="ACCOUNT_ALLOW_CREDIT_CARD";
        public static final String ACCOUNT_SHOW_EXPRESS_ORDER="ACCOUNT_SHOW_EXPRESS_ORDER";
        public static final String ACCOUNT_ADD_SERVICE_FEE="ACCOUNT_ADD_SERVICE_FEE";
        public static final String ACCOUNT_CONNECTION_CUSTOMER="ACCOUNT_CONNECTION_CUSTOMER";
        public static final String ACCOUNT_PDF_ORDER_CLASS="ACCOUNT_PDF_ORDER_CLASS";
        public static final String ACCOUNT_PDF_ORDER_STATUS_CLASS="ACCOUNT_PDF_ORDER_STATUS_CLASS";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_TITLE="ACCOUNT_INV_REMINDER_EMAIL_TITLE";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_SUB="ACCOUNT_INV_REMINDER_EMAIL_SUB";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_MSG="ACCOUNT_INV_REMINDER_EMAIL_MSG";
        public static final String ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR="ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR";
        public static final String ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR="ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR";
        public static final String ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR="ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR";
        public static final String ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR="ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR";
        public static final String ACCOUNT_INV_LEDGER_SWITCH="ACCOUNT_INV_LEDGER_SWITCH";
        public static final String ACCOUNT_INV_PO_SUFFIX="ACCOUNT_INV_PO_SUFFIX";
        public static final String ACCOUNT_INV_OG_LIST_UI="ACCOUNT_INV_OG_LIST_UI";
        public static final String ACCOUNT_INV_MISSING_NOTIFICATION="ACCOUNT_INV_MISSING_NOTIFICATION";
        public static final String ACCOUNT_INV_CHECK_PLACED_ORDER="ACCOUNT_INV_CHECK_PLACED_ORDER";
        public static final String ACCOUNT_DISTR_PO_TYPE="ACCOUNT_DISTR_PO_TYPE";
        public static final String ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER="ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER";
        public static final String ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED="ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED";
        public static final String ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER="ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER";
        public static final String ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS="ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS";
        public static final String ACCOUNT_CONTACT_INFORMATION_TYPE="ACCOUNT_CONTACT_INFORMATION_TYPE";
        public static final String ACCOUNT_DATA_FIELDS="ACCOUNT_DATA_FIELDS";

        public static final String ACCOUNT_PRIMARY_CONTACT_INF = "ACCOUNT_PRIMARY_CONTACT_INF";
        public static final String ACCOUNT_BILLING_ADDRESS_INF = "ACCOUNT_BILLING_ADDRESS_INF";
        public static final String ACCOUNT_ORDER_CONTACT_INF = "ACCOUNT_ORDER_CONTACT_INF";

        public static final String ACCOUNT_INF = "ACCOUNT_INF";
        public static final String STATUS = "STATUS";
        public static final String ID_AND_NAME = "ID_AND_NAME";
        public static final String BSC = "BSC";
        public static final String EFF_DATE = "EFF_DATE";
        public static final String EXP_DATE = "EXP_DATE";
        public static final String BUD_REF_NUM = "BUD_REF_NUM";
        public static final String DISTR_BUD_REF_NUM = "DISTR_BUD_REF_NUM";
        public static final String ADDRESS = "ADDRESS";
        public static final String ERP_NUMBER = "ERP_NUMBER";
        public static final String TAXABLE = "TAXABLE";
        public static final String ENABLE_INV = "ENABLE_INV";
        public static final String INV_SHOP_TYPE = "INV_SHOP_TYPE";
        public static final String INV_SHOP_HOLD_ORDER_UDD = "INV_SHOP_HOLD_ORDER_UDD";
        public static final String TARGET_FACILITY_RANK = "TARGET_FACILITY_RANK";
        public static final String BYPASS_ORDER_ROUTING = "BYPASS_ORDER_ROUTING";
        public static final String SITE_LINE_LEVEL_CODE = "SITE_LINE_LEVEL_CODE";
        public static final String CONSOLIDATED_ORDER_WH = "CONSOLIDATED_ORDER_WH";
        public static final String BLANKET_PO_NUMBER = "BLANKET_PO_NUMBER";
        public static final String SITE_DATA_FIELDS = "SITE_DATA_FIELDS";
        public static final String SHARE_BUYER_ORDER_GUIDES = "SHARE_BUYER_ORDER_GUIDES";
        public static final String SHIPPING_MESSAGE = "SHIPPING_MESSAGE";
        public static final String OG_COMMENTS = "OG_COMMENTS";
        public static final String USER_ID = "USER_ID";
        public static final String USER_NAME = "USER_NAME";
        public static final String USER_TYPE = "USER_TYPE";
        public static final String USER_PASSWORD = "USER_PASSWORD";
        public static final String USER_CONFIRM_PASSWORD = "USER_CONFIRM_PASSWORD";
        public static final String PREFERRED_LANGUAGE = "PREFERRED_LANGUAGE";
        public static final String USER_ACTIVE_DATE = "USER_ACTIVE_DATE";
        public static final String USER_INACTIVE_DATE = "USER_INACTIVE_DATE";
        public static final String USER_CODE = "USER_CODE";
        public static final String CORPARATE_USER = "CORPARATE_USER";
        public static final String RECEIVE_INV_MISSING_EMAIL = "RECEIVE_INV_MISSING_EMAIL";
        public static final String USER_DISTRIBUTOR_LABELS = "USER_DISTRIBUTOR_LABELS";
        public static final String CUST_SERVICE_ROLE = "CUST_SERVICE_ROLE";
        public static final String TOTAL_FIELD_READONLY = "TOTAL_FIELD_READONLY";
        public static final String USER_CONTACT_INFO = "USER_CONTACT_INFO";
        public static final String STORE_OR_ACCOUNT_ASSOC = "STORE_OR_ACCOUNT_ASSOC";
        public static final String USER_RIGHT_ON_ACCOUNT = "USER_RIGHT_ON_ACCOUNT";
        public static final String USER_RIGHT_CREDIT_CARD = "USER_RIGHT_CREDIT_CARD";
        public static final String USER_RIGHT_OTHER_PAYMENT = "USER_RIGHT_OTHER_PAYMENT";
        public static final String USER_RIGHT_PO_NUM_REQ = "USER_RIGHT_PO_NUM_REQ";
        public static final String USER_RIGHT_SHOW_PRICE = "USER_RIGHT_SHOW_PRICE";
        public static final String USER_RIGHT_CONTRACT_ITEMS_ONLY = "USER_RIGHT_CONTRACT_ITEMS_ONLY";
        public static final String USER_RIGHT_BROWSE_ONLY = "USER_RIGHT_BROWSE_ONLY";
        public static final String USER_RIGHT_SALES_PRESENT_ONLY = "USER_RIGHT_SALES_PRESENT_ONLY";
        public static final String USER_RIGHT_NO_REPORTING = "USER_RIGHT_NO_REPORTING";
        public static final String USER_RIGHT_REPORTING_MANAGER = "USER_RIGHT_REPORTING_MANAGER";
        public static final String USER_RIGHT_REP_ASSIGN_ALL_ACCTS = "USER_RIGHT_REP_ASSIGN_ALL_ACCTS";
        public static final String USER_RIGHT_CAN_APPROVE_ORDERS = "USER_RIGHT_CAN_APPROVE_ORDERS";
        public static final String USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL = "USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL";
        public static final String USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL = "USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL";
        public static final String USER_RIGHT_NEEDS_APPROVAL = "USER_RIGHT_NEEDS_APPROVAL";
        public static final String USER_RIGHT_ORDER_WAS_APPROVED = "USER_RIGHT_ORDER_WAS_APPROVED";
        public static final String USER_RIGHT_ORDER_WAS_REJECTED = "USER_RIGHT_ORDER_WAS_REJECTED";
        public static final String USER_RIGHT_ORDER_WAS_MODIFIED = "USER_RIGHT_ORDER_WAS_MODIFIED";
        public static final String USER_RIGHT_WO_COMPLETED = "USER_RIGHT_WO_COMPLETED";
        public static final String USER_RIGHT_WO_ACCEPTED_BY_PROVIDER = "USER_RIGHT_WO_ACCEPTED_BY_PROVIDERL";
        public static final String USER_RIGHT_WO_REJECTED_BY_PROVIDER = "USER_RIGHT_WO_REJECTED_BY_PROVIDERL";
        public static final String USER_RIGHT_CAN_EDIT_BILL_TO = "USER_RIGHT_CAN_EDIT_BILL_TO";
        public static final String USER_RIGHT_CAN_EDIT_SHIP_TO = "USER_RIGHT_CAN_EDIT_SHIP_TO";
        public static final String CLONE_WITH_RELATIONSHIPS = "CLONE_WITH_RELATIONSHIPS";
        public static final String CLONE_WITHOUT_RELATIONSHIPS = "CLONE_WITHOUT_RELATIONSHIPS";
    }

    public interface UI_CONTROL_ELEMENT {
        public static final String ACCOUNT_ID = "ACCOUNT_ID";
        public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
        public static final String ACCOUNT_ID_LABEL = "ACCOUNT_ID_LABEL";
        public static final String ACCOUNT_ID_VALUE = "ACCOUNT_ID_VALUE";
        public static final String ACCOUNT_NAME_LABEL = "ACCOUNT_NAME_LABEL";
        public static final String ACCOUNT_NAME_VALUE = "ACCOUNT_NAME_VALUE";
        public static final String ACCOUNT_TYPE_LABEL = "ACCOUNT_TYPE_LABEL";
        public static final String ACCOUNT_TYPE_VALUE = "ACCOUNT_TYPE_VALUE";
        public static final String ACCOUNT_NUMBER_LABEL = "ACCOUNT_NUMBER_LABEL";
        public static final String ACCOUNT_NUMBER_VALUE = "ACCOUNT_NUMBER_VALUE";
        public static final String ACCOUNT_ORDER_MINIMUM_LABEL = "ACCOUNT_ORDER_MINIMUM_LABEL";
        public static final String ACCOUNT_ORDER_MINIMUM_VALUE = "ACCOUNT_ORDER_MINIMUM_VALUE";
        public static final String ACCOUNT_CREDIT_LIMIT_LABEL = "ACCOUNT_CREDIT_LIMIT_LABEL";
        public static final String ACCOUNT_CREDIT_LIMIT_VALUE = "ACCOUNT_CREDIT_LIMIT_VALUE";
        public static final String ACCOUNT_CREDIT_RATING_LABEL = "ACCOUNT_CREDIT_RATING_LABEL";
        public static final String ACCOUNT_CREDIT_RATING_VALUE = "ACCOUNT_CREDIT_RATING_VALUE";
        public static final String ACCOUNT_BUDGET_TYPE_LABEL = "ACCOUNT_BUDGET_TYPE_LABEL";
        public static final String ACCOUNT_BUDGET_TYPE_VALUE = "ACCOUNT_BUDGET_TYPE_VALUE";
        public static final String ACCOUNT_GL_TRANSFORMATION_TYPE_LABEL = "ACCOUNT_GL_TRANSFORMATION_TYPE_LABEL";
        public static final String ACCOUNT_GL_TRANSFORMATION_TYPE_VALUE = "ACCOUNT_GL_TRANSFORMATION_TYPE_VALUE";
        public static final String ACCOUNT_TIME_ZONE_LABEL = "ACCOUNT_TIME_ZONE_LABEL";
        public static final String ACCOUNT_TIME_ZONE_VALUE = "ACCOUNT_TIME_ZONE_VALUE";
        public static final String ACCOUNT_ALLOW_SITE_LLC_LABEL = "ACCOUNT_ALLOW_SITE_LLC_LABEL";
        public static final String ACCOUNT_ALLOW_SITE_LLC_VALUE = "ACCOUNT_ALLOW_SITE_LLC_VALUE";
        public static final String ACCOUNT_CUSTOMER_EMAIL_LABEL = "ACCOUNT_CUSTOMER_EMAIL_LABEL";
        public static final String ACCOUNT_CUSTOMER_EMAIL_VALUE = "ACCOUNT_CUSTOMER_EMAIL_VALUE";
        public static final String ACCOUNT_DISTRIBUTOR_REFNUM_LABEL = "ACCOUNT_DISTRIBUTOR_REFNUM_LABEL";
        public static final String ACCOUNT_DISTRIBUTOR_REFNUM_VALUE= "ACCOUNT_DISTRIBUTOR_REFNUM_VALUE";
        public static final String ACCOUNT_FIRST_NAME_LABEL = "ACCOUNT_FIRST_NAME_LABEL";
        public static final String ACCOUNT_FIRST_NAME_VALUE = "ACCOUNT_FIRST_NAME_VALUE";
        public static final String ACCOUNT_LAST_NAME_LABEL = "ACCOUNT_LAST_NAME_LABEL";
        public static final String ACCOUNT_LAST_NAME_VALUE = "ACCOUNT_LAST_NAME_VALUE";
        public static final String ACCOUNT_PHONE_LABEL = "ACCOUNT_LAST_PHONE_LABEL";
        public static final String ACCOUNT_PHONE_VALUE = "ACCOUNT_LAST_PHONE_VALUE";
        public static final String ACCOUNT_FAX_LABEL = "ACCOUNT_FAX_LABEL";
        public static final String ACCOUNT_FAX_VALUE = "ACCOUNT_FAX_VALUE";
        public static final String ACCOUNT_FAX_BACK_CONFIRM_LABEL = "ACCOUNT_LAST_FAX_LABEL";
        public static final String ACCOUNT_FAX_BACK_CONFIRM_VALUE = "ACCOUNT_LAST_FAX_VALUE";
        public static final String ACCOUNT_STREET_ADDRESS1_LABEL = "ACCOUNT_STREET_ADDRESS1_LABEL";
        public static final String ACCOUNT_STREET_ADDRESS1_VALUE = "ACCOUNT_STREET_ADDRESS1_VALUE";
        public static final String ACCOUNT_STREET_ADDRESS2_LABEL = "ACCOUNT_STREET_ADDRESS2_LABEL";
        public static final String ACCOUNT_STREET_ADDRESS2_VALUE = "ACCOUNT_STREET_ADDRESS2_VALUE";
        public static final String ACCOUNT_STREET_ADDRESS3_LABEL = "ACCOUNT_STREET_ADDRESS3_LABEL";
        public static final String ACCOUNT_STREET_ADDRESS3_VALUE = "ACCOUNT_STREET_ADDRESS3_VALUE";
        public static final String ACCOUNT_DEFAULT_EMAIL_LABEL = "ACCOUNT_DEFAULT_EMAIL_LABEL";
        public static final String ACCOUNT_DEFAULT_EMAIL_VALUE = "ACCOUNT_DEFAULT_EMAIL_VALUE";
        public static final String ACCOUNT_CONTRACT_US_CC_EMAIL_LABEL = "ACCOUNT_CONTRACT_US_CC_EMAIL_LABEL";
        public static final String ACCOUNT_CONTRACT_US_CC_EMAIL_VALUE = "ACCOUNT_CONTRACT_US_CC_EMAIL_VALUE";
        public static final String ACCOUNT_COUNTRY_LABEL = "ACCOUNT_COUNTRY_LABEL";
        public static final String ACCOUNT_COUNTRY_VALUE = "ACCOUNT_COUNTRY_VALUE";
        public static final String ACCOUNT_EMAIL_ADDRESS_LABEL = "ACCOUNT_EMAIL_ADDRESS_LABEL";
        public static final String ACCOUNT_EMAIL_ADDRESS_VALUE = "ACCOUNT_EMAIL_ADDRESS_VALUE";
        public static final String ACCOUNT_STATE_LABEL = "ACCOUNT_STATE_LABEL";
        public static final String ACCOUNT_STATE_VALUE = "ACCOUNT_STATE_VALUE";
        public static final String ACCOUNT_ZIP_LABEL = "ACCOUNT_ZIP_LABEL";
        public static final String ACCOUNT_ZIP_VALUE = "ACCOUNT_ZIP_VALUE";
        public static final String ACCOUNT_CITY_LABEL = "ACCOUNT_CITY_LABEL";
        public static final String ACCOUNT_CITY_VALUE = "ACCOUNT_CITY_VALUE";
        public static final String ACCOUNT_BILLING_ADDRESS1_LABEL = "ACCOUNT_BILLING_ADDRESS1_LABEL";
        public static final String ACCOUNT_BILLING_ADDRESS1_VALUE = "ACCOUNT_BILLING_ADDRESS1_VALUE";
        public static final String ACCOUNT_BILLING_ADDRESS2_LABEL = "ACCOUNT_BILLING_ADDRESS2_LABEL";
        public static final String ACCOUNT_BILLING_ADDRESS2_VALUE = "ACCOUNT_BILLING_ADDRESS2_VALUE";
        public static final String ACCOUNT_BILLING_ADDRESS3_LABEL = "ACCOUNT_BILLING_ADDRESS3_LABEL";
        public static final String ACCOUNT_BILLING_ADDRESS3_VALUE = "ACCOUNT_BILLING_ADDRESS3_VALUE";
        public static final String ACCOUNT_BILLING_COUNTRY_LABEL = "ACCOUNT_BILLING_COUNTRY_LABEL";
        public static final String ACCOUNT_BILLING_COUNTRY_VALUE = "ACCOUNT_BILLING_COUNTRY_VALUE";
        public static final String ACCOUNT_BILLING_STATE_LABEL = "ACCOUNT_BILLING_STATE_LABEL";
        public static final String ACCOUNT_BILLING_STATE_VALUE = "ACCOUNT_BILLING_STATE_VALUE";
        public static final String ACCOUNT_BILLING_ZIP_LABEL = "ACCOUNT_BILLING_ZIP_LABEL";
        public static final String ACCOUNT_BILLING_ZIP_VALUE = "ACCOUNT_BILLING_ZIP_VALUE";
        public static final String ACCOUNT_ORDER_PHONE_LABEL = "ACCOUNT_ORDER_PHONE_LABEL";
        public static final String ACCOUNT_ORDER_PHONE_VALUE = "ACCOUNT_ORDER_PHONE_VALUE";
        public static final String ACCOUNT_ORDER_FAX_LABEL = "ACCOUNT_ORDER_FAX_LABEL";
        public static final String ACCOUNT_ORDER_FAX_VALUE = "ACCOUNT_ORDER_FAX_VALUE";
        public static final String ACCOUNT_BILLING_CITY_LABEL = "ACCOUNT_BILLING_CITY_LABEL";
        public static final String ACCOUNT_BILLING_CITY_VALUE = "ACCOUNT_BILLING_CITY_VALUE";
        public static final String ACCOUNT_PURCHASE_ORDER_NAME_LABEL = "ACCOUNT_PURCHASE_ORDER_NAME_LABEL";
        public static final String ACCOUNT_PURCHASE_ORDER_NAME_VALUE = "ACCOUNT_PURCHASE_ORDER_NAME_VALUE";
        public static final String ACCOUNT_ORDER_GUIDE_COMMENTS_LABEL = "ACCOUNT_ORDER_GUIDE_COMMENTS_LABEL";
        public static final String ACCOUNT_ORDER_GUIDE_COMMENTS_VALUE = "ACCOUNT_ORDER_GUIDE_COMMENTS_VALUE";
        public static final String ACCOUNT_ORDER_GUIDE_NOTE_LABEL = "ACCOUNT_ORDER_GUIDE_NOTE_LABEL";
        public static final String ACCOUNT_ORDER_GUIDE_NOTE_VALUE = "ACCOUNT_ORDER_GUIDE_NOTE_VALUE";
        public static final String ACCOUNT_ORDER_SKU_LABEL = "ACCOUNT_ORDER_SKU_LABEL";
        public static final String ACCOUNT_ORDER_SKU_VALUE = "ACCOUNT_ORDER_SKU_VALUE";
        public static final String ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_LABEL = "ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_LABEL";
        public static final String ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_VALUE = "ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_VALUE";
        public static final String ACCOUNT_TAXABLE_INDICATOR_LABEL = "ACCOUNT_TAXABLE_INDICATOR_LABEL";
        public static final String ACCOUNT_TAXABLE_INDICATOR_VALUE = "ACCOUNT_TAXABLE_INDICATOR_VALUE";
        public static final String ACCOUNT_ALLOW_CHANGE_PASSWORD_LABEL = "ACCOUNT_ALLOW_CHANGE_PASSWORD_LABEL";
        public static final String ACCOUNT_ALLOW_CHANGE_PASSWORD_VALUE = "ACCOUNT_ALLOW_CHANGE_PASSWORD_VALUE";
        public static final String ACCOUNT_CREATE_ERP_SHIP_TO_LABEL = "ACCOUNT_CREATE_ERP_SHIP_TO_LABEL";
        public static final String ACCOUNT_CREATE_ERP_SHIP_TO_VALUE = "ACCOUNT_CREATE_ERP_SHIP_TO_VALUE";
        public static final String ACCOUNT_ERP_SHIP_TO_COUNTER_LABEL = "ACCOUNT_ERP_SHIP_TO_COUNTER_LABEL";
        public static final String ACCOUNT_ERP_SHIP_TO_COUNTER_VALUE = "ACCOUNT_ERP_SHIP_TO_COUNTER_VALUE";
        public static final String ACCOUNT_CRC_SHOP_LABEL = "ACCOUNT_CRC_SHOP_LABEL";
        public static final String ACCOUNT_CRC_SHOP_VALUE = "ACCOUNT_CRC_SHOP_VALUE";
        public static final String ACCOUNT_FREIGHT_CHARGE_TYPE_LABEL = "ACCOUNT_FREIGHT_CHARGE_TYPE_LABEL";
        public static final String ACCOUNT_FREIGHT_CHARGE_TYPE_VALUE = "ACCOUNT_FREIGHT_CHARGE_TYPE_VALUE";
        public static final String ACCOUNT_MAKE_SHIP_TO_BILL_TO_LABEL = "ACCOUNT_MAKE_SHIP_TO_BILL_TO_LABEL";
        public static final String ACCOUNT_MAKE_SHIP_TO_BILL_TO_VALUE = "ACCOUNT_MAKE_SHIP_TO_BILL_TO_VALUE";
        public static final String ACCOUNT_ORDER_MANAGER_EMAILS_LABEL = "ACCOUNT_ORDER_MANAGER_EMAILS_LABEL";
        public static final String ACCOUNT_ORDER_MANAGER_EMAILS_VALUE = "ACCOUNT_ORDER_MANAGER_EMAILS_VALUE";
        public static final String ACCOUNT_SHOW_SCHEDULED_DELIVERY_LABEL = "ACCOUNT_SHOW_SCHEDULED_DELIVERY_LABEL";
        public static final String ACCOUNT_SHOW_SCHEDULED_DELIVERY_VALUE = "ACCOUNT_SHOW_SCHEDULED_DELIVERY_VALUE";
        public static final String ACCOUNT_EDI_SHIP_TO_PREFIX_LABEL = "ACCOUNT_EDI_SHIP_TO_PREFIX_LABEL";
        public static final String ACCOUNT_EDI_SHIP_TO_PREFIX_VALUE = "ACCOUNT_EDI_SHIP_TO_PREFIX_VALUE";
        public static final String ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_LABEL = "ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_LABEL";
        public static final String ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_VALUE = "ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_VALUE";
        public static final String ACCOUNT_RUSH_ORDER_CHARGE_LABEL = "ACCOUNT_RUSH_ORDER_CHARGE_LABEL";
        public static final String ACCOUNT_RUSH_ORDER_CHARGE_VALUE = "ACCOUNT_RUSH_ORDER_CHARGE_VALUE";
        public static final String ACCOUNT_AUTHORIZED_FOR_RESALE_LABEL = "ACCOUNT_AUTHORIZED_FOR_RESALE_LABEL";
        public static final String ACCOUNT_AUTHORIZED_FOR_RESALE_VALUE = "ACCOUNT_AUTHORIZED_FOR_RESALE_VALUE";
        public static final String ACCOUNT_RE_SALE_ERP_NUMBER_LABEL = "ACCOUNT_RE_SALE_ERP_NUMBER_LABEL";
        public static final String ACCOUNT_RE_SALE_ERP_NUMBER_VALUE = "ACCOUNT_RE_SALE_ERP_NUMBER_VALUE";
        public static final String ACCOUNT_ALLOW_ORDER_CONSOLIDATION_LABEL = "ACCOUNT_ALLOW_ORDER_CONSOLIDATION_LABEL";
        public static final String ACCOUNT_ALLOW_ORDER_CONSOLIDATION_VALUE = "ACCOUNT_ALLOW_ORDER_CONSOLIDATION_VALUE";
        public static final String ACCOUNT_SCHEDULE_CUTOFF_DAYS_LABEL="ACCOUNT_SCHEDULE_CUTOFF_DAYS_LABEL";
        public static final String ACCOUNT_SCHEDULE_CUTOFF_DAYS_VALUE="ACCOUNT_SCHEDULE_CUTOFF_DAYS_VALUE";
        public static final String ACCOUNT_SHOW_DIST_SKU_NUM_LABEL="ACCOUNT_SHOW_DIST_SKU_NUM_LABEL";
        public static final String ACCOUNT_SHOW_DIST_SKU_NUM_VALUE="ACCOUNT_SHOW_DIST_SKU_NUM_VALUE";
        public static final String ACCOUNT_FOLDER_LABEL = "ACCOUNT_FOLDER_LABEL";
        public static final String ACCOUNT_FOLDER_VALUE = "ACCOUNT_FOLDER_VALUE";
        public static final String ACCOUNT_SHOW_DIST_DELIVERY_DATE_LABEL="ACCOUNT_SHOW_DIST_DELIVERY_DATE_LABEL";
        public static final String ACCOUNT_SHOW_DIST_DELIVERY_DATE_VALUE="ACCOUNT_SHOW_DIST_DELIVERY_DATE_VALUE";
        public static final String ACCOUNT_SHOW_SPL_LABEL="ACCOUNT_SHOW_SPL_LABEL";
        public static final String ACCOUNT_SHOW_SPL_VALUE="ACCOUNT_SHOW_SPL_VALUE";
        public static final String ACCOUNT_HOLD_PO_LABEL="ACCOUNT_HOLD_PO_LABEL";
        public static final String ACCOUNT_HOLD_PO_VALUE="ACCOUNT_HOLD_PO_VALUE";
        public static final String ACCOUNT_AUTO_ORDER_FACTOR_LABEL = "AUTO_ORDER_FACTOR_LABEL";
        public static final String ACCOUNT_AUTO_ORDER_FACTOR_VALUE = "AUTO_ORDER_FACTOR_VALUE";
        public static final String ACCOUNT_MODIFY_QTY_BY_855_LABEL ="ACCOUNT_MODIFY_QTY_BY_855_LABEL";
        public static final String ACCOUNT_MODIFY_QTY_BY_855_VALUE ="ACCOUNT_MODIFY_QTY_BY_855_VALUE";
        public static final String ACCOUNT_ALLOW_ORDER_INV_ITEMS_LABEL ="ACCOUNT_ALLOW_ORDER_INV_ITEMS_LABEL";
        public static final String ACCOUNT_ALLOW_ORDER_INV_ITEMS_VALUE ="ACCOUNT_ALLOW_ORDER_INV_ITEMS_VALUE";
        public static final String ACCOUNT_ALLOW_REORDER_LABEL="ACCOUNT_ALLOW_REORDER_LABEL";
        public static final String ACCOUNT_ALLOW_REORDER_VALUE="ACCOUNT_ALLOW_REORDER_VALUE";
        public static final String ACCOUNT_USE_PHYSICAL_INVENTORY_LABEL="ACCOUNT_USE_PHYSICAL_INVENTORY_LABEL";
        public static final String ACCOUNT_USE_PHYSICAL_INVENTORY_VALUE="ACCOUNT_USE_PHYSICAL_INVENTORY_VALUE";
        public static final String ACCOUNT_CREATE_ORDER_BY_855_LABEL="ACCOUNT_CREATE_ORDER_BY_855_LABEL";
        public static final String ACCOUNT_CREATE_ORDER_BY_855_VALUE="ACCOUNT_CREATE_ORDER_BY_855_VALUE";
        public static final String ACCOUNT_SHOP_UI_TYPE_LABEL="ACCOUNT_SHOP_UI_TYPE_LABEL";
        public static final String ACCOUNT_SHOP_UI_TYPE_VALUE="ACCOUNT_SHOP_UI_TYPE_VALUE";
        public static final String ACCOUNT_ALLOW_MODERN_SHOPPING_LABEL="ACCOUNT_ALLOW_MODERN_SHOPPING_LABEL";
        public static final String ACCOUNT_ALLOW_MODERN_SHOPPING_VALUE="ACCOUNT_ALLOW_MODERN_SHOPPING_VALUE";
        public static final String ACCOUNT_FOLDER_NEW_LABEL="ACCOUNT_FOLDER_NEW_LABEL";
        public static final String ACCOUNT_FOLDER_NEW_VALUE="ACCOUNT_FOLDER_NEW_VALUE";
        public static final String ACCOUNT_FAQ_LINK_LABEL="ACCOUNT_FAQ_LINK_LABEL";
        public static final String ACCOUNT_FAQ_LINK_VALUE="ACCOUNT_FAQ_LINK_VALUE";
        public static final String ACCOUNT_REBATE_PERSENT_LABEL="ACCOUNT_REBATE_PERSENT_LABEL";
        public static final String ACCOUNT_REBATE_PERSENT_VALUE = "ACCOUNT_REBATE_PERSENT_VALUE";
        public static final String ACCOUNT_REBATE_EFF_DATE_LABEL = "ACCOUNT_REBATE_EFF_DATE_LABEL";
        public static final String ACCOUNT_REBATE_EFF_DATE_VALUE = "ACCOUNT_REBATE_EFF_DATE_VALUE";
        public static final String ACCOUNT_SHOW_DIST_INVENTORY_LABEL="ACCOUNT_SHOW_DIST_INVENTORY_LABEL";
        public static final String ACCOUNT_SHOW_DIST_INVENTORY_VALUE="ACCOUNT_SHOW_DIST_INVENTORY_VALUE";
        public static final String ACCOUNT_TARGET_MARGIN_STR_LABEL="ACCOUNT_TARGET_MARGIN_STR_LABEL";
        public static final String ACCOUNT_TARGET_MARGIN_STR_VALUE="ACCOUNT_TARGET_MARGIN_STR_VALUE";
        public static final String ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_LABEL ="ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_LABEL";
        public static final String ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_VALUE ="ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_VALUE";
        public static final String ACCOUNT_SHOW_INV_CART_TOTAL_LABEL="ACCOUNT_SHOW_INV_CART_TOTAL_LABEL";
        public static final String ACCOUNT_SHOW_INV_CART_TOTAL_VALUE="ACCOUNT_SHOW_INV_CART_TOTAL_VALUE";
        public static final String ACCOUNT_CART_REMINDER_INTERVAL_LABEL="ACCOUNT_CART_REMINDER_INTERVAL_LABEL";
        public static final String ACCOUNT_CART_REMINDER_INTERVAL_VALUE="ACCOUNT_CART_REMINDER_INTERVAL_VALUE";
        public static final String ACCOUNT_SHOW_MY_SHOPPING_LISTS_LABEL="ACCOUNT_SHOW_MY_SHOPPING_LISTS_LABEL";
        public static final String ACCOUNT_SHOW_MY_SHOPPING_LISTS_VALUE="ACCOUNT_SHOW_MY_SHOPPING_LISTS_VALUE";
        public static final String ACCOUNT_ALLOW_CREDIT_CARD_LABEL="ACCOUNT_ALLOW_CREDIT_CARD_LABEL";
        public static final String ACCOUNT_ALLOW_CREDIT_CARD_VALUE="ACCOUNT_ALLOW_CREDIT_CARD_VALUE";
        public static final String ACCOUNT_SHOW_EXPRESS_ORDER_LABEL="ACCOUNT_SHOW_EXPRESS_ORDER_LABEL";
        public static final String ACCOUNT_SHOW_EXPRESS_ORDER_VALUE="ACCOUNT_SHOW_EXPRESS_ORDER_VALUE";
        public static final String ACCOUNT_ADD_SERVICE_FEE_LABEL="ACCOUNT_ADD_SERVICE_FEE_LABEL";
        public static final String ACCOUNT_ADD_SERVICE_FEE_VALUE="ACCOUNT_ADD_SERVICE_FEE_VALUE";
        public static final String ACCOUNT_CONNECTION_CUSTOMER_LABEL="ACCOUNT_CONNECTION_CUSTOMER_LABEL";
        public static final String ACCOUNT_CONNECTION_CUSTOMER_VALUE="ACCOUNT_CONNECTION_CUSTOMER_VALUE";
        public static final String ACCOUNT_PDF_ORDER_CLASS_LABEL="ACCOUNT_PDF_ORDER_CLASS_LABEL";
        public static final String ACCOUNT_PDF_ORDER_CLASS_VALUE="ACCOUNT_PDF_ORDER_CLASS_VALUE";
        public static final String ACCOUNT_PDF_ORDER_STATUS_CLASS_LABEL="ACCOUNT_PDF_ORDER_STATUS_CLASS_LABEL";
        public static final String ACCOUNT_PDF_ORDER_STATUS_CLASS_VALUE="ACCOUNT_PDF_ORDER_STATUS_CLASS_VALUE";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_TITLE_LABEL="ACCOUNT_INV_REMINDER_EMAIL_TITLE_LABEL";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_SUB_LABEL="ACCOUNT_INV_REMINDER_EMAIL_SUB_LABEL";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_SUB_VALUE="ACCOUNT_INV_REMINDER_EMAIL_SUB_VALUE";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_MSG_LABEL="ACCOUNT_INV_REMINDER_EMAIL_MSG_LABEL";
        public static final String ACCOUNT_INV_REMINDER_EMAIL_MSG_VALUE="ACCOUNT_INV_REMINDER_EMAIL_MSG_VALUE";
        public static final String ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_LABEL="ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_LABEL";
        public static final String ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_VALUE="ACCOUNT_CONFIRM_ORDER_EMAIL_GENERATOR_VALUE";
        public static final String ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_LABEL="ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_LABEL";
        public static final String ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_VALUE="ACCOUNT_NOTIFY_ORDER_EMAIL_GENERATOR_VALUE";
        public static final String ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_LABEL="ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_LABEL";
        public static final String ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_VALUE="ACCOUNT_REJECT_ORDER_EMAIL_GENERATOR_VALUE";
        public static final String ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_LABEL="ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_LABEL";
        public static final String ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_VALUE="ACCOUNT_PENDING_APPROVE_MAIL_GENERATOR_VALUE";
        public static final String ACCOUNT_INV_LEDGER_SWITCH_LABEL="ACCOUNT_INV_LEDGER_SWITCH_LABEL";
        public static final String ACCOUNT_INV_LEDGER_SWITCH_VALUE="ACCOUNT_INV_LEDGER_SWITCH_VALUE";
        public static final String ACCOUNT_INV_PO_SUFFIX_LABEL="ACCOUNT_INV_PO_SUFFIX_LABEL";
        public static final String ACCOUNT_INV_PO_SUFFIX_VALUE="ACCOUNT_INV_PO_SUFFIX_VALUE";
        public static final String ACCOUNT_INV_OG_LIST_UI_LABEL="ACCOUNT_INV_OG_LIST_UI_LABEL";
        public static final String ACCOUNT_INV_OG_LIST_UI_VALUE="ACCOUNT_INV_OG_LIST_UI_VALUE";
        public static final String ACCOUNT_INV_MISSING_NOTIFICATION_LABEL="ACCOUNT_INV_MISSING_NOTIFICATION_LABEL";
        public static final String ACCOUNT_INV_MISSING_NOTIFICATION_VALUE="ACCOUNT_INV_MISSING_NOTIFICATION_VALUE";
        public static final String ACCOUNT_INV_CHECK_PLACED_ORDER_LABEL="ACCOUNT_INV_CHECK_PLACED_ORDER_LABEL";
        public static final String ACCOUNT_INV_CHECK_PLACED_ORDER_VALUE="ACCOUNT_INV_CHECK_PLACED_ORDER_VALUE";
        public static final String ACCOUNT_DISTR_PO_TYPE_LABEL="ACCOUNT_DISTR_PO_TYPE_LABEL";
        public static final String ACCOUNT_DISTR_PO_TYPE_VALUE="ACCOUNT_DISTR_PO_TYPE_VALUE";
        public static final String ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_LABEL="ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_LABEL";
        public static final String ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_VALUE="ACCOUNT_ALLOW_SET_WORK_ORDER_PO_NUMBER_VALUE";
        public static final String ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_LABEL="ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_LABEL";
        public static final String ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_VALUE="ACCOUNT_WORK_ORDER_PO_NUMBER_IS_REQUIRED_VALUE";
        public static final String ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_LABEL="ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_LABEL";
        public static final String ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_VALUE="ACCOUNT_USER_ASSIGNED_AS_SET_NUMBER_VALUE";
        public static final String ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_LABEL="ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_LABEL";
        public static final String ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_VALUE="ACCOUNT_ALLOW_BUY_WORK_ORDER_PARTS_VALUE";
        public static final String ACCOUNT_CONTACT_INFORMATION_TYPE_LABEL="ACCOUNT_CONTACT_INFORMATION_TYPE_LABEL";
        public static final String ACCOUNT_CONTACT_INFORMATION_TYPE_VALUE="ACCOUNT_CONTACT_INFORMATION_TYPE_VALUE";
        public static final String ACCOUNT_DATA_FIELDS_LABEL="ACCOUNT_DATA_FIELDS_LABEL";

        public static final String STATUS_VALUE = "STATUS_VALUE";
        public static final String STATUS_LABEL = "STATUS_LABEL";
        public static final String ID_LABEL = "ID_LABEL";
        public static final String NAME_LABEL = "NAME_LABEL";
        public static final String NAME_VALUE = "NAME_VALUE";
        public static final String BSC_LABEL = "BSC_LABEL";
        public static final String BSC_VALUE = "BSC_VALUE";
        public static final String EFF_DATE_LABEL = "EFF_DATE_LABEL";
        public static final String EFF_DATE_VALUE = "EFF_DATE_VALUE";
        public static final String EXP_DATE_LABEL = "EXP_DATE_LABEL";
        public static final String EXP_DATE_VALUE = "EXP_DATE_VALUE";
        public static final String BUD_REF_NUM_LABEL = "BUD_REF_NUM_LABEL";
        public static final String BUD_REF_NUM_VALUE = "BUD_REF_NUM_VALUE";
        public static final String DISTR_BUD_REF_NUM_LBL = "DISTR_BUD_REF_NUM_LBL";
        public static final String DISTR_BUD_REF_NUM_VAL = "DISTR_BUD_REF_NUM_VAL";
        public static final String ERP_NUMBER_LABEL = "ERP_NUMBER_LABEL";
        public static final String ERP_NUMBER_VALUE = "ERP_NUMBER_VALUE";
        public static final String TAXABLE_LABEL = "TAXABLE_LABEL";
        public static final String TAXABLE_VALUE = "TAXABLE_VALUE";
        public static final String ENABLE_INV_LABEL = "ENABLE_INV_LABEL";
        public static final String UI_ENABLE_INV_VALUE = "UI_ENABLE_INV_VALUE";
        public static final String INV_SHOP_TYPE_LABEL = "INV_SHOP_TYPE_LABEL";
        public static final String INV_SHOP_TYPE_VALUE = "INV_SHOP_TYPE_VALUE";
        public static final String INV_SHOP_HOLD_ORDER_UDD_LABEL = "INV_SHOP_HOLD_ORDER_UDD_LABEL";
        public static final String INV_SHOP_HOLD_ORDER_UDD_VALUE = "INV_SHOP_HOLD_ORDER_UDD_VALUE";
        public static final String TARGET_FACILITY_RANK_LABEL = "TARGET_FACILITY_RANK_LABEL";
        public static final String TARGET_FACILITY_RANK_VALUE = "TARGET_FACILITY_RANK_VALUE";
        public static final String BYPASS_ORDER_ROUTING_LABEL = "BYPASS_ORDER_ROUTING_LABEL";
        public static final String BYPASS_ORDER_ROUTING_VALUE = "BYPASS_ORDER_ROUTING_VALUE";
        public static final String SITE_LINE_LEVEL_CODE_LABEL = "SITE_LINE_LEVEL_CODE_LABEL";
        public static final String SITE_LINE_LEVEL_CODE_VALUE = "SITE_LINE_LEVEL_CODE_VALUE";
        public static final String CONSOLIDATED_ORDER_WH_LABEL = "CONSOLIDATED_ORDER_WH_LABEL";
        public static final String CONSOLIDATED_ORDER_WH_VALUE = "CONSOLIDATED_ORDER_WH_VALUE";
        public static final String BLANKET_PO_NUMBER_LABEL = "BLANKET_PO_NUMBER_LABEL";
        public static final String SITE_DATA_FIELDS_LABEL = "SITE_DATA_FIELDS_LABEL";
        public static final String SHARE_BUYER_ORDER_GUIDES_LBL = "SHARE_BUYER_ORDER_GUIDES_LBL";
        public static final String SHARE_BUYER_ORDER_GUIDES_VAL = "SHARE_BUYER_ORDER_GUIDES_VAL";
        public static final String ADDRESS_FIRST_NAME_LABEL = "ADDRESS_FIRST_NAME_LABEL";
        public static final String ADDRESS_FIRST_NAME_VALUE = "ADDRESS_FIRST_NAME_VALUE";
        public static final String ADDRESS_LAST_NAME_LABEL = "ADDRESS_LAST_NAME_LABEL";
        public static final String ADDRESS_LAST_NAME_VALUE = "ADDRESS_LAST_NAME_VALUE";
        public static final String ADDRESS_STREET_ADDRESS1_LABEL = "ADDRESS_STREET_ADDRESS1_LABEL";
        public static final String ADDRESS_STREET_ADDRESS1_VALUE = "ADDRESS_STREET_ADDRESS1_VALUE";
        public static final String ADDRESS_STREET_ADDRESS2_LABEL = "ADDRESS_STREET_ADDRESS2_LABEL";
        public static final String ADDRESS_STREET_ADDRESS2_VALUE = "ADDRESS_STREET_ADDRESS2_VALUE";
        public static final String ADDRESS_STREET_ADDRESS3_LABEL = "ADDRESS_STREET_ADDRESS3_LABEL";
        public static final String ADDRESS_STREET_ADDRESS3_VALUE = "ADDRESS_STREET_ADDRESS3_VALUE";
        public static final String ADDRESS_STREET_ADDRESS4_LABEL = "ADDRESS_STREET_ADDRESS4_LABEL";
        public static final String ADDRESS_STREET_ADDRESS4_VALUE = "ADDRESS_STREET_ADDRESS4_VALUE";
        public static final String ADDRESS_STATE_LABEL = "ADDRESS_STATE_LABEL";
        public static final String ADDRESS_ZIP_LABEL = "ADDRESS_ZIP_LABEL";
        public static final String ADDRESS_COUNTRY_LABEL = "ADDRESS_COUNTRY_LABEL";
        public static final String ADDRESS_CITY_LABEL = "ADDRESS_CITY_LABEL";
        public static final String ADDRESS_COUNTY_LABEL = "ADDRESS_COUNTY_LABEL";
        public static final String ADDRESS_STATE_VALUE = "ADDRESS_STATE_VALUE";
        public static final String ADDRESS_ZIP_VALUE = "ADDRESS_ZIP_VALUE";
        public static final String ADDRESS_COUNTRY_VALUE = "ADDRESS_COUNTRY_VALUE";
        public static final String ADDRESS_CITY_VALUE = "ADDRESS_CITY_VALUE";
        public static final String ADDRESS_COUNTY_VALUE = "ADDRESS_COUNTY_VALUE";
        public static final String SHIPPING_MESSAGE_LABEL = "SHIPPING_MESSAGE_LABEL";
        public static final String SHIPPING_MESSAGE_VALUE = "SHIPPING_MESSAGE_VALUE";
        public static final String OG_COMMENTS_VALUE = "OG_COMMENTS_VALUE";
        public static final String OG_COMMENTS_LABEL = "OG_COMMENTS_LABEL";
        public static final String ADDRESS_LABELS1 = "ADDRESS_LABELS1";
        public static final String ADDRESS_LABELS2 = "ADDRESS_LABELS2";
        public static final String ADDRESS_VALUES1 = "ADDRESS_VALUES1";
        public static final String ADDRESS_VALUES2 = "ADDRESS_VALUES2";
        public static final String USER_ID_LABEL = "USER_ID_LABEL";
        public static final String USER_ID_VALUE = "USER_ID_VALUE";
        public static final String USER_PASSWORD_LABEL = "USER_PASSWORD_LABEL";
        public static final String USER_PASSWORD_VALUE = "USER_PASSWORD_VALUE";
        public static final String USER_TYPE_LABEL = "USER_TYPE_LABEL";
        public static final String USER_TYPE_VALUE = "USER_TYPE_VALUE";
        public static final String USER_CONFIRM_PASSWORD_LABEL = "USER_CONFIRM_PASSWORD_LABEL";
        public static final String USER_CONFIRM_PASSWORD_VALUE = "USER_CONFIRM_PASSWORD_VALUE";
        public static final String PREFERRED_LANGUAGE_LABEL = "PREFERRED_LANGUAGE_LABEL";
        public static final String PREFERRED_LANGUAGE_VALUE = "PREFERRED_LANGUAGE_VALUE";
        public static final String USER_NAME_LABEL = "USER_NAME_LABEL";
        public static final String USER_NAME_VALUE = "USER_NAME_VALUE";
        public static final String USER_ACTIVE_DATE_LABEL = "USER_ACTIVE_DATE_LABEL";
        public static final String USER_ACTIVE_DATE_VALUE = "USER_ACTIVE_DATE_VALUE";
        public static final String USER_INACTIVE_DATE_LABEL = "USER_INACTIVE_DATE_LABEL";
        public static final String USER_INACTIVE_DATE_VALUE = "USER_INACTIVE_DATE_VALUE";
        public static final String USER_CODE_LABEL = "USER_CODE_LABEL";
        public static final String USER_CODE_VALUE = "USER_CODE_VALUE";
        public static final String CORPARATE_USER_LABEL = "CORPARATE_USER_LABEL";
        public static final String CORPARATE_USER_VALUE = "CORPARATE_USER_VALUE";
        public static final String RECEIVE_INV_MISSING_EMAIL_LBL = "RECEIVE_INV_MISSING_EMAIL_LBL";
        public static final String RECEIVE_INV_MISSING_EMAIL_VAL = "RECEIVE_INV_MISSING_EMAIL_VAL";
        public static final String LABEL_HEIGHT_LABEL = "LABEL_HEIGHT_LABEL";
        public static final String LABEL_HEIGHT_VALUE = "LABEL_HEIGHT_VALUE";
        public static final String LABEL_WEITH_LABEL = "LABEL_WEITH_LABEL";
        public static final String LABEL_WEITH_VALUE = "LABEL_WEITH_VALUE";
        public static final String LABEL_TYPE_LABEL = "LABEL_TYPE_LABEL";
        public static final String LABEL_TYPE_VALUE = "LABEL_TYPE_VALUE";
        public static final String LABEL_PRINT_MODE_LABEL = "LABEL_PRINT_MODE_LABEL";
        public static final String LABEL_PRINT_MODE_VALUE = "LABEL_PRINT_MODE_VALUE";
        public static final String CUST_SERVICE_ROLE_LABEL = "CUST_SERVICE_ROLE_LABEL";
        public static final String CUST_SERVICE_ROLE_VALUE = "CUST_SERVICE_ROLE_VALUE";
        public static final String TOTAL_FIELD_READONLY_LABEL = "TOTAL_FIELD_READONLY_LABEL";
        public static final String TOTAL_FIELD_READONLY_VALUE = "TOTAL_FIELD_READONLY_VALUE";
        public static final String PHONE_LABEL = "PHONE_LABEL";
        public static final String PHONE_VALUE = "PHONE_VALUE";
        public static final String MOBILE_LABEL = "MOBILE_LABEL";
        public static final String MOBILE_VALUE = "MOBILE_VALUE";
        public static final String FAX_LABEL = "FAX_LABEL";
        public static final String FAX_VALUE = "FAX_VALUE";
        public static final String EMAIL_LABEL = "EMAIL_LABEL";
        public static final String EMAIL_VALUE = "EMAIL_VALUE";
        public static final String USER_RIGHT_ON_ACCOUNT_LABEL = "USER_RIGHT_ON_ACCOUNT_LABEL";
        public static final String USER_RIGHT_ON_ACCOUNT_VALUE = "USER_RIGHT_ON_ACCOUNT_VALUE";
        public static final String USER_RIGHT_CREDIT_CARD_LABEL = "USER_RIGHT_CREDIT_CARD_LABEL";
        public static final String USER_RIGHT_CREDIT_CARD_VALUE = "USER_RIGHT_CREDIT_CARD_VALUE";
        public static final String USER_RIGHT_OTHER_PAYMENT_LABEL = "USER_RIGHT_OTHER_PAYMENT_LABEL";
        public static final String USER_RIGHT_OTHER_PAYMENT_VALUE = "USER_RIGHT_OTHER_PAYMENT_VALUE";
        public static final String USER_RIGHT_PO_NUM_REQ_LABEL = "USER_RIGHT_PO_NUM_REQ_LABEL";
        public static final String USER_RIGHT_PO_NUM_REQ_VALUE = "USER_RIGHT_PO_NUM_REQ_VALUE";
        public static final String USER_RIGHT_SHOW_PRICE_LABEL = "USER_RIGHT_SHOW_PRICE_LABEL";
        public static final String USER_RIGHT_SHOW_PRICE_VALUE = "USER_RIGHT_SHOW_PRICE_VALUE";
        public static final String USER_RIGHT_CONTRACT_ITEMS_ONLY_LABEL = "USER_RIGHT_CONTRACT_ITEMS_ONLY_LABEL";
        public static final String USER_RIGHT_CONTRACT_ITEMS_ONLY_VALUE = "USER_RIGHT_CONTRACT_ITEMS_ONLY_VALUE";
        public static final String USER_RIGHT_BROWSE_ONLY_LABEL = "USER_RIGHT_BROWSE_ONLY_LABEL";
        public static final String USER_RIGHT_BROWSE_ONLY_VALUE = "USER_RIGHT_BROWSE_ONLY_VALUE";
        public static final String USER_RIGHT_SALES_PRESENT_ONLY_LABEL = "USER_RIGHT_SALES_PRESENT_ONLY_LABEL";
        public static final String USER_RIGHT_SALES_PRESENT_ONLY_VALUE = "USER_RIGHT_SALES_PRESENT_ONLY_VALUE";
        public static final String USER_RIGHT_NO_REPORTING_LABEL = "USER_RIGHT_NO_REPORTING_LABEL";
        public static final String USER_RIGHT_NO_REPORTING_VALUE = "USER_RIGHT_NO_REPORTING_VALUE";
        public static final String USER_RIGHT_REPORTING_MANAGER_LABEL = "USER_RIGHT_REPORTING_MANAGER_LABEL";
        public static final String USER_RIGHT_REPORTING_MANAGER_VALUE = "USER_RIGHT_REPORTING_MANAGER_VALUE";
        public static final String USER_RIGHT_REP_ASSIGN_ALL_ACCTS_LABEL = "USER_RIGHT_REP_ASSIGN_ALL_ACCTS_LABEL";
        public static final String USER_RIGHT_REP_ASSIGN_ALL_ACCTS_VALUE = "USER_RIGHT_REP_ASSIGN_ALL_ACCTS_VALUE";
        public static final String USER_RIGHT_CAN_APPROVE_ORDERS_LABEL = "USER_RIGHT_CAN_APPROVE_ORDERS_LABEL";
        public static final String USER_RIGHT_CAN_APPROVE_ORDERS_VALUE = "USER_RIGHT_CAN_APPROVE_ORDERS_VALUE";
        public static final String USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_LABEL = "USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_LABEL";
        public static final String USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_VALUE = "USER_RIGHT_ORDER_DETAIL_NOTIFICATION_EMAIL_VALUE";
        public static final String USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_LABEL = "USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_LABEL";
        public static final String USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_VALUE = "USER_RIGHT_ORDER_NOTIFICATION_SHIPPED_EMAIL_VALUE";
        public static final String USER_RIGHT_NEEDS_APPROVAL_LABEL = "USER_RIGHT_NEEDS_APPROVAL_LABEL";
        public static final String USER_RIGHT_NEEDS_APPROVAL_VALUE = "USER_RIGHT_NEEDS_APPROVAL_VALUE";
        public static final String USER_RIGHT_ORDER_WAS_APPROVED_LABEL = "USER_RIGHT_ORDER_WAS_APPROVED_LABEL";
        public static final String USER_RIGHT_ORDER_WAS_APPROVED_VALUE = "USER_RIGHT_ORDER_WAS_APPROVEDL_VALUE";
        public static final String USER_RIGHT_ORDER_WAS_REJECTED_LABEL = "USER_RIGHT_ORDER_WAS_REJECTED_LABEL";
        public static final String USER_RIGHT_ORDER_WAS_REJECTED_VALUE = "USER_RIGHT_ORDER_WAS_REJECTEDL_VALUE";
        public static final String USER_RIGHT_ORDER_WAS_MODIFIED_LABEL = "USER_RIGHT_ORDER_WAS_MODIFIED_LABEL";
        public static final String USER_RIGHT_ORDER_WAS_MODIFIED_VALUE = "USER_RIGHT_ORDER_WAS_MODIFIEDL_VALUE";
        public static final String USER_RIGHT_WO_COMPLETED_LABEL = "USER_RIGHT_WO_COMPLETED_LABEL";
        public static final String USER_RIGHT_WO_COMPLETED_VALUE = "USER_RIGHT_WO_COMPLETEDL_VALUE";
        public static final String USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_LABEL = "USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_LABEL";
        public static final String USER_RIGHT_WO_ACCEPTED_BY_PROVIDER_VALUE = "USER_RIGHT_WO_ACCEPTED_BY_PROVIDERL_VALUE";
        public static final String USER_RIGHT_WO_REJECTED_BY_PROVIDER_LABEL = "USER_RIGHT_WO_REJECTED_BY_PROVIDER_LABEL";
        public static final String USER_RIGHT_WO_REJECTED_BY_PROVIDER_VALUE = "USER_RIGHT_WO_REJECTED_BY_PROVIDERL_VALUE";
        public static final String USER_RIGHT_CAN_EDIT_SHIP_TO_LABEL = "USER_RIGHT_CAN_EDIT_SHIP_TO_LABEL";
        public static final String USER_RIGHT_CAN_EDIT_SHIP_TO_VALUE = "USER_RIGHT_CAN_EDIT_SHIP_TOL_VALUE";
        public static final String USER_RIGHT_CAN_EDIT_BILL_TO_LABEL = "USER_RIGHT_CAN_EDIT_BILL_TO_LABEL";
        public static final String USER_RIGHT_CAN_EDIT_BILL_TO_VALUE = "USER_RIGHT_CAN_EDIT_BILL_TOL_VALUE";
        public static final String STORE_OR_ACCOUNT_ASSOC_VALUE = "STORE_OR_ACCOUNT_ASSOC_VALUE";
        public static final String CLONE_WITH_RELATIONSHIPS_VALUE = "CLONE_WITH_RELATIONSHIPS_VALUE";
        public static final String CLONE_WITHOUT_RELATIONSHIPS_VALUE = "CLONE_WITHOUT_RELATIONSHIPS_VALUE";
    }

    public interface UI_CONTROL_ELEMENT_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
    }

    public interface UI_CONTROL_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
        public static final String DEFAULT = "DEFAULT";
    }

    public interface UI_CONTROL_TYPE_CD {
        public static final String LABEL = "LABEL";
        public static final String TEXT = "TEXT";
        public static final String SELECT = "SELECT";
        public static final String RADIO = "RADIO";
        public static final String CHECKBOX = "CHECKBOX";
        public static final String TEXTAREA = "TEXTAREA";
        public static final String PASSWORD = "PASSWORD";
        public static final String MULTIBOX = "MULTIBOX";
        public static final String BUTTON = "BUTTON";
    }

    public interface UI_PAGE_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
    }

    public interface UI_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
    }

    public interface UI_ASSOC_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface BUDGET_THRESHOLD_TYPE {
        public static final String NONE = "None";
        public static final String ACCOUNT_BUDGET_THRESHOLD = "Account Budget Threshold";
        public static final String SITE_BUDGET_THRESHOLD = "Site Budget Threshold";
    }

    public interface STAGED_ASSETS_SEARCH_TYPE_CD {
        public static final String MATCHED = "MATCHED";
        public static final String NOT_MATCHED = "NOT_MATCHED";
        public static final String BOTH = "BOTH";
    }

    public interface MATCH_PO_NUM_TYPE_CD {
        public static final String DEFAULT = "DEFAULT";
        public static final String VENDOR_ORDER_NUM = "VENDOR_ORDER_NUM";
        public static final String STORE_ERP_PO_NUM = "STORE_ERP_PO_NUM";
    }

    public interface SITE_PRODUCT_BUNDLE {
        public static final String CATALOG = "CATALOG";
        public static final String PRICE_LIST = "PRICE_LIST";
        public static final String ORDER_GUIDE = "ORDER_GUIDE";
    }

    public interface PRICE_LIST_ASSOC_CD {
        public static final String PRICE_LIST_SITE = "PRICE_LIST_SITE";
        public static final String PROPRIETARY_LIST_SITE  = "PROPRIETARY_LIST_SITE";
        public static final String PRICE_LIST_STORE = "PRICE_LIST_STORE";
        public static final String PROPRIETARY_LIST_STORE  = "PROPRIETARY_LIST_STORE";
    }

    public interface PRICE_LIST_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }

    public interface PRICE_LIST_TYPE_CD {
        public static final String PRICE_LIST = "PRICE_LIST";
        public static final String PROPRIETARY_LIST = "PROPRIETARY_LIST";
    }

    public interface MSDS_PLUGIN_CD {
        public static final String DEFAULT = "Default";
        public static final String DIVERSEY_WEB_SERVICES = "DiverseyWebServices";
    }
    
    public interface MESSAGE_STATUS_CD {
        public static final String ACTIVE   = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
        public static final String PUBLISHED = "PUBLISHED";
        public static final String UNPUBLISHED = "UNPUBLISHED";
    }

    public interface STORE_MESSAGE_ASSOC_CD {
        public static final String MESSAGE_STORE = "MESSAGE_STORE";
        public static final String MESSAGE_ACCOUNT = "MESSAGE_ACCOUNT";
        public static final String MESSAGE_SITE = "MESSAGE_SITE";
    }

    public interface STORE_MESSAGE_STATUS_CD {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
    }
    
    public interface FUTURE_ORDER_TYPE{
        public static final String
                PENDING_DATE_ORDER = "Pending Date Order",
                CORPORATE_ORDER_SCHEDULE = "Corporate Order Schedule",
                SHOPPING_LIST_SCHEDULE = "Shopping List Schedule";
    }
    
    public interface FUTURE_ORDER_CONTENT_TYPE{
        public static final String
                CORPORATE_ORDER = "Corporate Order";
    }
    
    public interface STORE_LANGUAGE_CD{
    	public static final String
    			de = "de",
    			el = "el",
    			en = "en",
    			es = "es",
    			fr = "fr",
    			hu = "hu",
    			it = "it",
    			ja = "ja",
    			nl = "nl",
    			pl = "pl",
    			tr = "tr",
    			ts = "ts",
    			zh = "zh";
    }
    
    public interface STORE_COUNTRY_CD{
    	public static final String
    			AU = "AU",
    			BR = "BR",
    			CA = "CA",
    			CL = "CL",
    			GB = "GB",
    			US = "US";			
    }

    public interface STORE_LANGUAGE{
    	public static final String CHINESE = "CHINESE";
    	public static final String DUTCH = "DUTCH";
    	public static final String ENGLISH = "ENGLISH";
    	public static final String FRENCH = "FRENCH";
    	public static final String GERMAN = "GERMAN";
    	public static final String GREEK = "GREEK";
    	public static final String HUNGARIAN ="HUNGARIAN";
    	public static final String ITALIAN = "ITALIAN";
    	public static final String JAPANESE = "JAPANESE";
    	public static final String POLISH = "POLISH";
    	public static final String SPANISH = "SPANISH"; 
    	public static final String TURKISH = "TURKISH";
    }

    public interface STORE_PROFILE_FIELD {
        public static final String PROFILE_NAME = "PROFILE_NAME";
        public static final String LANGUAGE = "LANGUAGE";
        public static final String LANGUAGE_OPTIONS = "LANGUAGE_OPTIONS";
        public static final String COUNTRY = "COUNTRY";
        public static final String CONTACT_ADDRESS = "CONTACT_ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String MOBILE = "MOBILE";
        public static final String FAX = "FAX";
        public static final String EMAIL = "EMAIL";
        public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    }
    
    public interface STORE_PROFILE_TYPE_CD{
    	public static final String FIELD_OPTION = "FIELD_OPTION";
    	public static final String LANGUAGE_OPTION = "LANGUAGE_OPTION";
    }
    
    public interface APP_SOURCE_CD {
        public static final String WEB = "WEB";
        public static final String AUDITOR = "AUDITOR";
        public static final String MANTA = "MANTA";
    }
    
    public interface MESSAGE_TYPE_CD{
    	public static final String REGULAR = "REGULAR";
    	public static final String FORCE_READ = "FORCE_READ";
    	public static final String ACKNOWLEDGEMENT_REQUIRED = "ACKNOWLEDGEMENT_REQUIRED";
 }
    
    public interface MESSAGE_DETAIL_TYPE_CD {
      public static final String DEFAULT = "DEFAULT";
  }
    
    public interface EVENT_STATUS_CD {
    	public static final String STATUS_READY          = "READY";
        public static final String STATUS_PENDING_REVIEW = "PENDING_REVIEW";
        public static final String STATUS_FAILED         = "FAILED";
        public static final String STATUS_IN_PROGRESS    = "IN_PROGRESS";
        public static final String STATUS_REJECTED       = "REJECTED";
        public static final String STATUS_PROCESSED      = "PROCESSED";
        public static final String STATUS_PROC_ERROR     = "PROC_ERROR"; // SVC
        public static final String STATUS_DELETED        = "DELETED";
        public static final String STATUS_LIMITED        = "LIMITED";
        public static final String STATUS_IGNORE         = "IGNORE";
        public static final String STATUS_SYNC_CALL      = "SYNC_CALL";
        public static final String STATUS_HOLD           = "HOLD";
    }
    
    public interface HISTORY_TYPE_CD {
    	public static final String CREATE_PUNCHOUT_ORDER_MESSAGE = "CreatePunchoutOrderMessage";
    	public static final String CREATE_GENERIC_REPORT = "CreateGenericReport";
    	public static final String MODIFY_GENERIC_REPORT = "ModifyGenericReport";
    	
    }
    
    public interface HISTORY_OBJECT_TYPE_CD {
    	public static final String STORE = "STORE";
    	public static final String ACCOUNT = "ACCOUNT";
    	public static final String LOCATION = "LOCATION";
    	public static final String GENERIC_REPORT = "GENERIC_REPORT";
    }
    
    public interface MESSAGE_MANAGED_BY {
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String CUSTOMER = "CUSTOMER";
    }    
}
