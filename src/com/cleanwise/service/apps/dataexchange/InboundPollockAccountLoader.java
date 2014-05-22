package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;

import org.apache.log4j.Logger;


public class InboundPollockAccountLoader extends InboundFlatFile {
private final static int VERSION = 0;
private final static int ACTION = 1;
private final static int STORE_ID = 2;
private final static int STORE_NAME = 3;
private final static int ACCOUNT_REF_NUM = 4;
private final static int ACCOUNT_NAME = 5;
private final static int ACCOUNT_TYPE = 6;
private final static int BUDGET_TYPE = 7;
private final static int TIME_ZONE = 8;
private final static int SITE_LLC_OVERRIDE = 9;
private final static int CUSTOMER_SERVICE_EMAIL = 10;
private final static int CONTACT_CC_EMAIL = 11;
private final static int DEFAULT_EMAIL = 12;
private final static int DIST_ACCOUNT_REF_NUM = 13;
private final static int PRIMARY_CONTACT_FIRST_NAME = 14;
private final static int PRIMARY_CONTACT_LAST_NAME = 15;
private final static int PRIMARY_CONTACT_PHONE = 16;
private final static int PRIMARY_CONTACT_FAX = 17;
private final static int PRIMARY_CONTACT_EMAIL = 18;
private final static int PRIMARY_CONTACT_ADDESS1 = 19;
private final static int PRIMARY_CONTACT_ADDESS2 = 20;
private final static int PRIMARY_CONTACT_ADDESS3 = 21;
private final static int PRIMARY_CONTACT_CITY = 22;
private final static int PRIMARY_CONTACT_STATE = 23;
private final static int PRIMARY_CONTACT_COUNTRY = 24;
private final static int PRIMARY_CONTACT_POSTAL_CODE = 25;
private final static int BILLING_ADDESS1 = 26;
private final static int BILLING_ADDESS2 = 27;
private final static int BILLING_ADDESS3 = 28;
private final static int BILLING_CITY = 29;
private final static int BILLING_STATE  = 30;
private final static int BILLING_COUNTRY = 31;
private final static int BILLING_POSTAL_CODE = 32;
private final static int PURCHASE_ORDER_ACCOUNT_NAME = 33;
private final static int ORDER_CONTACT_PHONE = 34;
private final static int ORDER_CONTACT_FAX = 35;
private final static int ORDER_GUIDE_COMMENTS = 36;
private final static int ORDER_GUIDE_NOTES = 37;
private final static int ORDER_GUIDE_SKU_HEADING = 38;
private final static int ORDER_ITEM_ACTION = 39;
private final static int TAXABLE = 40;
private final static int ALLOW_USER_TO_CHANGE_PASSWORD = 41;
private final static int ENABLE_INVENTORY_ORDER_PROCESS = 42;
private final static int ALLOW_CUSTOMER_PO_NUM = 43;
private final static int AUTHORIZED_RESELLING_ITEMS = 44;
private final static int SHOW_DIST_SKU_NUM = 45;
private final static int SHOW_DIST_DELIVERY_DATE = 46;
private final static int MODIFY_ORDER_QTY_COST_CENTER = 47;
private final static int ALLOW_REORDER = 48;
private final static int CREATE_ORDER_BY_ORDER_ACK = 49;
private final static int MODER_SHOPPING_FOLDER = 50;
private final static int SHOW_INVENTORY_ITEM_PRICE = 51;
private final static int DISPLAY_MY_SHOPPING_LIST = 52;
private final static int EXPRESS_ORDER = 53;
private final static int DELIVERY_CUTOFF_DAYS = 54;
private final static int ACCOUNT_FOLDER = 55;
private final static int AUTO_ORDER_QTY_FACTOR = 56;
private final static int ALLOW_ORD_INV_ITEM = 57;
private final static int USER_PHYSICAL_ING = 58;
private final static int SHOP_UI_TYPE = 59;
private final static int FAQ_LINK = 60;
private final static int PDF_ORDER_CLASS_NAME = 61;
private final static int PDF_ORDER_STATUS_NAME = 62;
private final static int REMINDER_EMAIL_SUBJECT = 63;
private final static int REMINDER_EMAIL_MESSAGE = 64;
private final static int ORDER_CONFIRM_EMAIL_GEN = 65;
private final static int ORDER_NOTIFICATION_EMAIL_GEN = 66;
private final static int ORDER_REJECTED_EMAIL_GEN = 67;
private final static int PENDING_APPROVAL_EMAIL = 68;
private final static int INVENTORY_PROPERTY_BUDGET = 69;
private final static int INVENTORY_PROPERTY_PO_SUFFIX = 70;
private final static int INVENTORY_PROPERTY_OG = 71;
private final static int INV_PROPERTY_SEND_NOTIFICATION = 72;
private final static int INV_PROP_DONOT_PLACE_INV_ORDER = 73;
private final static int INV_PROP_DIST_PO_TYPE = 74;
private final static int FISCAL_CALENDAR_YEAR = 75;
private final static int FISCAL_CALENDAR_START = 76;
private final static int FISCAL_CALENDAR_PERIOD = 77;
private final static int ALLOW_TO_SET_WORKORDER_PO = 78;
private final static int WORK_ORDER_PO_NUM_REQ = 79;
private final static int USER_ASSIGNED_ASSET_NUM = 80;
private final static int ALLOW_ORDER_PARTS_WORKORDER = 81;
private final static int POPULATE_CONTACT_INFO = 82;
private final static int PRODUCT_UI_TEMPLATE = 83;

private final static String accountloader = "pollockAccountLoader";
private final static String accountActionAdd = "A";
private final static String accountActionChange = "C";
private final static String accountActionDelete = "D";
//private final static int storeNum = 182241;
private int storeNum = 0;
private Date runDate = new Date();
Connection conn = null;

private final static String tempTable ="CLT_POLLARD_ACCOUNT_LOADER";

    private static final String insertSql = "insert into CLT_POLLARD_ACCOUNT_LOADER(VERSION,ACTION,STORE_ID,STORE_NAME,ACCOUNT_REF_NUM,ACCOUNT_NAME,ACCOUNT_TYPE,BUDGET_TYPE,TIME_ZONE, " +
    "SITE_LLC_OVERRIDE,CUSTOMER_SERVICE_EMAIL,CONTACT_CC_EMAIL,DEFAULT_EMAIL,DIST_ACCOUNT_REF_NUM, " +
    "PRIMARY_CONTACT_FIRST_NAME,PRIMARY_CONTACT_LAST_NAME,PRIMARY_CONTACT_PHONE,PRIMARY_CONTACT_FAX, " +
    "PRIMARY_CONTACT_EMAIL,PRIMARY_CONTACT_ADDESS1,PRIMARY_CONTACT_ADDESS2,PRIMARY_CONTACT_ADDESS3, " +
    "PRIMARY_CONTACT_CITY,PRIMARY_CONTACT_STATE,PRIMARY_CONTACT_COUNTRY,PRIMARY_CONTACT_POSTAL_CODE, " +
    "BILLING_ADDESS1,BILLING_ADDESS2,BILLING_ADDESS3,BILLING_CITY,BILLING_STATE,BILLING_COUNTRY, " +
    "BILLING_POSTAL_CODE,PURCHASE_ORDER_ACCOUNT_NAME,ORDER_CONTACT_PHONE,ORDER_CONTACT_FAX, " +
    "ORDER_GUIDE_COMMENTS,ORDER_GUIDE_NOTES,ORDER_GUIDE_SKU_HEADING,ORDER_ITEM_ACTION,TAXABLE, " +
    "ALLOW_USER_TO_CHANGE_PASSWORD,ENABLE_INVENTORY_ORDER_PROCESS,ALLOW_CUSTOMER_PO_NUM, " +
    "AUTHORIZED_RESELLING_ITEMS,SHOW_DIST_SKU_NUM,SHOW_DIST_DELIVERY_DATE,MODIFY_ORDER_QTY_COST_CENTER, " +
    "ALLOW_REORDER,CREATE_ORDER_BY_ORDER_ACK,MODER_SHOPPING_FOLDER,SHOW_INVENTORY_ITEM_PRICE, " +
    "DISPLAY_MY_SHOPPING_LIST,EXPRESS_ORDER,DELIVERY_CUTOFF_DAYS,ACCOUNT_FOLDER,AUTO_ORDER_QTY_FACTOR, " +
    "ALLOW_ORD_INV_ITEM,USER_PHYSICAL_ING,SHOP_UI_TYPE,FAQ_LINK,PDF_ORDER_CLASS_NAME,PDF_ORDER_STATUS_NAME, " +
    "REMINDER_EMAIL_SUBJECT,REMINDER_EMAIL_MESSAGE,ORDER_CONFIRM_EMAIL_GEN,ORDER_NOTIFICATION_EMAIL_GEN, " +
    "ORDER_REJECTED_EMAIL_GEN,PENDING_APPROVAL_EMAIL,INVENTORY_PROPERTY_BUDGET,INVENTORY_PROPERTY_PO_SUFFIX, " +
    "INVENTORY_PROPERTY_OG,INV_PROPERTY_SEND_NOTIFICATION,INV_PROP_DONOT_PLACE_INV_ORDER,INV_PROP_DIST_PO_TYPE, " +
    "FISCAL_CALENDAR_YEAR,FISCAL_CALENDAR_START,FISCAL_CALENDAR_PERIOD,ALLOW_TO_SET_WORKORDER_PO, " +
    "WORK_ORDER_PO_NUM_REQ,USER_ASSIGNED_ASSET_NUM,ALLOW_ORDER_PARTS_WORKORDER,POPULATE_CONTACT_INFO,ADD_BY,ADD_DATE,PRODUCT_UI_TEMPLATE)" +
    	" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
    	"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";


    private static final String trimDataSql = "update CLT_POLLARD_ACCOUNT_LOADER set " +
    		"VERSION = trim(VERSION),ACTION	= trim(ACTION),STORE_ID	= trim(STORE_ID)," +
    		"STORE_NAME = trim(STORE_NAME)," +
    		"ACCOUNT_REF_NUM = trim(ACCOUNT_REF_NUM)," +
    		"ACCOUNT_NAME = trim(ACCOUNT_NAME)," +
    		"ACCOUNT_TYPE = trim(ACCOUNT_TYPE),BUDGET_TYPE = trim(BUDGET_TYPE)," +
    		"TIME_ZONE = trim(TIME_ZONE),SITE_LLC_OVERRIDE = trim(SITE_LLC_OVERRIDE)," +
    		"CUSTOMER_SERVICE_EMAIL = trim(CUSTOMER_SERVICE_EMAIL),CONTACT_CC_EMAIL = trim(CONTACT_CC_EMAIL)," +
    		"DEFAULT_EMAIL = trim(DEFAULT_EMAIL),DIST_ACCOUNT_REF_NUM = trim(DIST_ACCOUNT_REF_NUM)," +
    		"PRIMARY_CONTACT_FIRST_NAME = trim(PRIMARY_CONTACT_FIRST_NAME)," +
    		"PRIMARY_CONTACT_LAST_NAME = trim(PRIMARY_CONTACT_LAST_NAME)," +
    		"PRIMARY_CONTACT_PHONE = trim(PRIMARY_CONTACT_PHONE),PRIMARY_CONTACT_FAX	= trim(PRIMARY_CONTACT_FAX)," +
    		"PRIMARY_CONTACT_EMAIL = trim(PRIMARY_CONTACT_EMAIL),PRIMARY_CONTACT_ADDESS1 = trim(PRIMARY_CONTACT_ADDESS1)," +
    		"PRIMARY_CONTACT_ADDESS2	= trim(PRIMARY_CONTACT_ADDESS2)," +
    		"PRIMARY_CONTACT_ADDESS3	= trim(PRIMARY_CONTACT_ADDESS3)," +
    		"PRIMARY_CONTACT_CITY = trim(PRIMARY_CONTACT_CITY),PRIMARY_CONTACT_STATE	= trim(PRIMARY_CONTACT_STATE)," +
    		"PRIMARY_CONTACT_COUNTRY	= trim(PRIMARY_CONTACT_COUNTRY)," +
    		"PRIMARY_CONTACT_POSTAL_CODE = trim(PRIMARY_CONTACT_POSTAL_CODE)," +
    		"BILLING_ADDESS1	= trim(BILLING_ADDESS1),BILLING_ADDESS2	= trim(BILLING_ADDESS2)," +
    		"BILLING_ADDESS3	= trim(BILLING_ADDESS3),BILLING_CITY = trim(BILLING_CITY)," +
    		"BILLING_STATE = trim(BILLING_STATE),BILLING_COUNTRY = trim(BILLING_COUNTRY)," +
    		"BILLING_POSTAL_CODE = trim(BILLING_POSTAL_CODE)," +
    		"PURCHASE_ORDER_ACCOUNT_NAME = trim(PURCHASE_ORDER_ACCOUNT_NAME)," +
    		"ORDER_CONTACT_PHONE = trim(ORDER_CONTACT_PHONE)," +
    		"ORDER_CONTACT_FAX = trim(ORDER_CONTACT_FAX)," +
    		"ORDER_GUIDE_COMMENTS = trim(ORDER_GUIDE_COMMENTS)," +
    		"ORDER_GUIDE_NOTES = trim(ORDER_GUIDE_NOTES)," +
    		"ORDER_GUIDE_SKU_HEADING	= trim(ORDER_GUIDE_SKU_HEADING)," +
    		"ORDER_ITEM_ACTION = trim(ORDER_ITEM_ACTION)," +
    		"TAXABLE	= trim(TAXABLE),ALLOW_USER_TO_CHANGE_PASSWORD	= trim(ALLOW_USER_TO_CHANGE_PASSWORD)," +
    		"ENABLE_INVENTORY_ORDER_PROCESS	= trim(ENABLE_INVENTORY_ORDER_PROCESS)," +
    		"ALLOW_CUSTOMER_PO_NUM = trim(ALLOW_CUSTOMER_PO_NUM)," +
    		"AUTHORIZED_RESELLING_ITEMS = trim(AUTHORIZED_RESELLING_ITEMS)," +
    		"SHOW_DIST_SKU_NUM = trim(SHOW_DIST_SKU_NUM)," +
    		"SHOW_DIST_DELIVERY_DATE	= trim(SHOW_DIST_DELIVERY_DATE)," +
    		"MODIFY_ORDER_QTY_COST_CENTER = trim(MODIFY_ORDER_QTY_COST_CENTER)," +
    		"ALLOW_REORDER = trim(ALLOW_REORDER),CREATE_ORDER_BY_ORDER_ACK = trim(CREATE_ORDER_BY_ORDER_ACK)," +
    		"MODER_SHOPPING_FOLDER = trim(MODER_SHOPPING_FOLDER)," +
    		"SHOW_INVENTORY_ITEM_PRICE = trim(SHOW_INVENTORY_ITEM_PRICE)," +
    		"DISPLAY_MY_SHOPPING_LIST = trim(DISPLAY_MY_SHOPPING_LIST)," +
    		"EXPRESS_ORDER = trim(EXPRESS_ORDER),DELIVERY_CUTOFF_DAYS = trim(DELIVERY_CUTOFF_DAYS)," +
    		"ACCOUNT_FOLDER = trim(ACCOUNT_FOLDER),AUTO_ORDER_QTY_FACTOR = trim(AUTO_ORDER_QTY_FACTOR)," +
    		"ALLOW_ORD_INV_ITEM = trim(ALLOW_ORD_INV_ITEM),USER_PHYSICAL_ING	= trim(USER_PHYSICAL_ING)," +
    		"SHOP_UI_TYPE = trim(SHOP_UI_TYPE),FAQ_LINK = trim(FAQ_LINK)," +
    		"PDF_ORDER_CLASS_NAME = trim(PDF_ORDER_CLASS_NAME)," +
    		"PDF_ORDER_STATUS_NAME = trim(PDF_ORDER_STATUS_NAME)," +
    		"REMINDER_EMAIL_SUBJECT = trim(REMINDER_EMAIL_SUBJECT)," +
    		"REMINDER_EMAIL_MESSAGE	= trim(REMINDER_EMAIL_MESSAGE)," +
    		"ORDER_CONFIRM_EMAIL_GEN	= trim(ORDER_CONFIRM_EMAIL_GEN)," +
    		"ORDER_NOTIFICATION_EMAIL_GEN = trim(ORDER_NOTIFICATION_EMAIL_GEN)," +
    		"ORDER_REJECTED_EMAIL_GEN = trim(ORDER_REJECTED_EMAIL_GEN)," +
    		"PENDING_APPROVAL_EMAIL = trim(PENDING_APPROVAL_EMAIL)," +
    		"INVENTORY_PROPERTY_BUDGET = trim(INVENTORY_PROPERTY_BUDGET)," +
    		"INVENTORY_PROPERTY_PO_SUFFIX = trim(INVENTORY_PROPERTY_PO_SUFFIX)," +
    		"INVENTORY_PROPERTY_OG = trim(INVENTORY_PROPERTY_OG)," +
    		"INV_PROPERTY_SEND_NOTIFICATION	= trim(INV_PROPERTY_SEND_NOTIFICATION)," +
    		"INV_PROP_DONOT_PLACE_INV_ORDER	= trim(INV_PROP_DONOT_PLACE_INV_ORDER)," +
    		"INV_PROP_DIST_PO_TYPE	= trim(INV_PROP_DIST_PO_TYPE)," +
    		"FISCAL_CALENDAR_YEAR	= trim(FISCAL_CALENDAR_YEAR)," +
    		"FISCAL_CALENDAR_START	= trim(FISCAL_CALENDAR_START)," +
    		"FISCAL_CALENDAR_PERIOD	= trim(FISCAL_CALENDAR_PERIOD)," +
    		"ALLOW_TO_SET_WORKORDER_PO	= trim(ALLOW_TO_SET_WORKORDER_PO)," +
    		"WORK_ORDER_PO_NUM_REQ	= trim(WORK_ORDER_PO_NUM_REQ)," +
    		"USER_ASSIGNED_ASSET_NUM	= trim(USER_ASSIGNED_ASSET_NUM)," +
    		"ALLOW_ORDER_PARTS_WORKORDER	= trim(ALLOW_ORDER_PARTS_WORKORDER), " +
    		"POPULATE_CONTACT_INFO	= trim(POPULATE_CONTACT_INFO), " +
    		"PRODUCT_UI_TEMPLATE = TRIM(PRODUCT_UI_TEMPLATE)";

    private static String selectStoreId = "select distinct STORE_ID,STORE_NAME from CLT_POLLARD_ACCOUNT_LOADER";

    private static String verifyStoreId = "select bus_entity_id from clw_bus_entity where " +
	"upper(short_desc) = upper(?) and bus_entity_type_cd = 'STORE' " +
	"and bus_entity_status_cd = 'ACTIVE'";

    protected Logger log = Logger.getLogger(InboundPollockAccountLoader.class);

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        IntegrationServices isEjb = APIAccess.getAPIAccess()
        	.getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.parse(pIn);
         //   parser.cleanUpResult();
            List<List<String>> list = (List<List<String>>) parser
                    .getParsedStrings();
            process(list);
            log.info("STORE ID =====================" + storeNum);
            isEjb.processAccount(conn,accountloader,accountActionAdd,accountActionChange,accountActionDelete,storeNum,tempTable);
        } catch (Exception e) {
            log.error("translate => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms", e);
            setFail(true);
            throw e;
        }
        log.info("translate => END.Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
    }


    private void process(List<List<String>> parsedData) throws Exception {
        TradingPartnerData partner = translator.getPartner();
        if (partner == null) {
            appendErrorMsgs("Trading Partner ID cannot be determined");
        }
        if (parsedData.size() == 0)
        	throw new Exception("Input file could be emptyyyyy.");
        //checkLine(parsedData);

        if (conn == null){
			conn = getConnection();
		}
        //log.info("Getting Connectionnnnnnnnnn:= " + conn);
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //loadTempTable(parsedData);
        log.info("SQL insertSql in Temp Table = " +  insertSql);
        //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size(); i++) {
            List<String> line = (List<String>) parsedData.get(i);
            String versionCd 				= line.get(VERSION);
            String actionCd 				= line.get(ACTION);
            String storeID 					= line.get(STORE_ID);
            String storeName 				= line.get(STORE_NAME);
            String accountRefNum 			= line.get(ACCOUNT_REF_NUM);
            String accountName 				= line.get(ACCOUNT_NAME);
            String accountType 				= line.get(ACCOUNT_TYPE);
            String budgetType 				= line.get(BUDGET_TYPE);
            String timeZone 				= line.get(TIME_ZONE);
            String siteLlcOverride 			= line.get(SITE_LLC_OVERRIDE);
            String customerServiceEmail 	= line.get(CUSTOMER_SERVICE_EMAIL);
            String contactCcEmail 			= line.get(CONTACT_CC_EMAIL);
            String defaultEmail 			= line.get(DEFAULT_EMAIL);
            String distAccountRefNum 		= line.get(DIST_ACCOUNT_REF_NUM);
            String primaryContactFirstName 	= line.get(PRIMARY_CONTACT_FIRST_NAME);
            String primaryContactLastName 	= line.get(PRIMARY_CONTACT_LAST_NAME);
            String primaryContactPhone 		= line.get(PRIMARY_CONTACT_PHONE);
            String primaryContactFax 		= line.get(PRIMARY_CONTACT_FAX);
            String primaryContactEmail 		= line.get(PRIMARY_CONTACT_EMAIL);
            String primaryContactAddress1 	= line.get(PRIMARY_CONTACT_ADDESS1);
            String primaryContactAddress2 	= line.get(PRIMARY_CONTACT_ADDESS2);
            String primaryContactAddress3 	= line.get(PRIMARY_CONTACT_ADDESS3);
            String primaryContactCity 		= line.get(PRIMARY_CONTACT_CITY);
            String primaryContactState 		= line.get(PRIMARY_CONTACT_STATE);
            String primaryContactCountry 	= line.get(PRIMARY_CONTACT_COUNTRY);
            String primaryContactPostalCode = line.get(PRIMARY_CONTACT_POSTAL_CODE);
            String billingAddress1 			= line.get(BILLING_ADDESS1);
            String billingAddress2 			= line.get(BILLING_ADDESS2);
            String billingAddress3 			= line.get(BILLING_ADDESS3);
            String billingCity 				= line.get(BILLING_CITY);
            String billingState 			= line.get(BILLING_STATE);
            String billingCountry 			= line.get(BILLING_COUNTRY);
            String billingPostalCode 		= line.get(BILLING_POSTAL_CODE);
            String purchaseOrderAccountName = line.get(PURCHASE_ORDER_ACCOUNT_NAME);
            String orderContactPhone 		= line.get(ORDER_CONTACT_PHONE);
            String orderContactFax 			= line.get(ORDER_CONTACT_FAX);
            String orderGuideComments 		= line.get(ORDER_GUIDE_COMMENTS);
            String orderGuideNotes 			= line.get(ORDER_GUIDE_NOTES);
            String orderGuideSkuHeading 	= line.get(ORDER_GUIDE_SKU_HEADING);
            String orderItemAction 			= line.get(ORDER_ITEM_ACTION);
            String taxableCd 				= line.get(TAXABLE);
            String allowUserToChangePasswd 	= line.get(ALLOW_USER_TO_CHANGE_PASSWORD);
            String enableInvOrderProcess 	= line.get(ENABLE_INVENTORY_ORDER_PROCESS);
            String allowCustomerPoNum 		= line.get(ALLOW_CUSTOMER_PO_NUM);
            String authorizedResellingItems = line.get(AUTHORIZED_RESELLING_ITEMS);
            String showDistSkuNum 			= line.get(SHOW_DIST_SKU_NUM);
            String showDistDeliveryDate 	= line.get(SHOW_DIST_DELIVERY_DATE);
            String modifyOrderQtyCostCenter = line.get(MODIFY_ORDER_QTY_COST_CENTER);
            String allowReorder 			= line.get(ALLOW_REORDER);
            String createOrderByOrderAck 	= line.get(CREATE_ORDER_BY_ORDER_ACK);
            String modernShoppingFolder 	= line.get(MODER_SHOPPING_FOLDER);
            String showInvItemPrice 		= line.get(SHOW_INVENTORY_ITEM_PRICE);
            String displayMyShoppingList 	= line.get(DISPLAY_MY_SHOPPING_LIST);
            String expressOrder 			= line.get(EXPRESS_ORDER);
            String deliveryCutoffDays 		= line.get(DELIVERY_CUTOFF_DAYS);
            String accountFolder 			= line.get(ACCOUNT_FOLDER);
            String autoOrderQtyFactor 		= line.get(AUTO_ORDER_QTY_FACTOR);
            String allowOrdInvItem 			= line.get(ALLOW_ORD_INV_ITEM);
            String userPhysicalIng 			= line.get(USER_PHYSICAL_ING);
            String shopUiType 				= line.get(SHOP_UI_TYPE);
            String faqLink 					= line.get(FAQ_LINK);
            String pdfOrderClassName 		= line.get(PDF_ORDER_CLASS_NAME);
            String pdfOrderStatusName 		= line.get(PDF_ORDER_STATUS_NAME);
            String reminderEmailSubject 	= line.get(REMINDER_EMAIL_SUBJECT);
            String reminderEmailMessage 	= line.get(REMINDER_EMAIL_MESSAGE);
            String orderConfirmEmailGen 	= line.get(ORDER_CONFIRM_EMAIL_GEN);
            String orderNotificationEmailGen = line.get(ORDER_NOTIFICATION_EMAIL_GEN);
            String orderRejectedEmailGen 	= line.get(ORDER_REJECTED_EMAIL_GEN);
            String pendingApprovalEmail 	= line.get(PENDING_APPROVAL_EMAIL);
            String invPropertyBudget 		= line.get(INVENTORY_PROPERTY_BUDGET);
            String invPropertyPoSuffix 		= line.get(INVENTORY_PROPERTY_PO_SUFFIX);
            String invPropertyOg 			= line.get(INVENTORY_PROPERTY_OG);
            String invPropSendNotification 	= line.get(INV_PROPERTY_SEND_NOTIFICATION);
            String invPropDonotPlaceInvOrd 	= line.get(INV_PROP_DONOT_PLACE_INV_ORDER);
            String invPropDistPoType 		= line.get(INV_PROP_DIST_PO_TYPE);
            String fiscalCalendarYear 		= line.get(FISCAL_CALENDAR_YEAR);
            String fiscalCalendarStart 		= line.get(FISCAL_CALENDAR_START);
            String fiscalCalendarPeriod 	= line.get(FISCAL_CALENDAR_PERIOD);
            String allowToSetWorkorderPo 	= line.get(ALLOW_TO_SET_WORKORDER_PO);
            String workOrderPoNumReq 		= line.get(WORK_ORDER_PO_NUM_REQ);
            String userAssignedAsserNum 	= line.get(USER_ASSIGNED_ASSET_NUM);
            String allowOrdPartsWorkOrder 	= line.get(ALLOW_ORDER_PARTS_WORKORDER);
            String populateContactInfo 		= line.get(POPULATE_CONTACT_INFO);
            
            String productUiTemplate = "";
            if(line.size() == 83){
            	productUiTemplate = "";
            }else{
            	productUiTemplate		= line.get(PRODUCT_UI_TEMPLATE);
            }
            
            try {
            	pstmt.setString(1,versionCd);
            	pstmt.setString(2,actionCd);
            	pstmt.setString(3,storeID);
            	pstmt.setString(4,storeName);
            	pstmt.setString(5,accountRefNum);
            	pstmt.setString(6,accountName);
            	pstmt.setString(7,accountType);
            	pstmt.setString(8,budgetType);
            	pstmt.setString(9,timeZone);
            	pstmt.setString(10,siteLlcOverride);
            	pstmt.setString(11,customerServiceEmail);
            	pstmt.setString(12,contactCcEmail);
            	pstmt.setString(13,defaultEmail);
            	pstmt.setString(14,distAccountRefNum);
            	pstmt.setString(15,primaryContactFirstName);
            	pstmt.setString(16,primaryContactLastName);
            	pstmt.setString(17,primaryContactPhone);
            	pstmt.setString(18,primaryContactFax);
            	pstmt.setString(19,primaryContactEmail);
            	pstmt.setString(20,primaryContactAddress1);
            	pstmt.setString(21,primaryContactAddress2);
            	pstmt.setString(22,primaryContactAddress3);
            	pstmt.setString(23,primaryContactCity);
            	pstmt.setString(24,primaryContactState);
            	pstmt.setString(25,primaryContactCountry);
            	pstmt.setString(26,primaryContactPostalCode);
            	pstmt.setString(27,billingAddress1);
            	pstmt.setString(28,billingAddress2);
            	pstmt.setString(29,billingAddress3);
            	pstmt.setString(30,billingCity);
            	pstmt.setString(31,billingState);
            	pstmt.setString(32,billingCountry);
            	pstmt.setString(33,billingPostalCode);
            	pstmt.setString(34,purchaseOrderAccountName);
            	pstmt.setString(35,orderContactPhone);
            	pstmt.setString(36,orderContactFax);
            	pstmt.setString(37,orderGuideComments);
            	pstmt.setString(38,orderGuideNotes);
            	pstmt.setString(39,orderGuideSkuHeading);
            	pstmt.setString(40,orderItemAction);
            	pstmt.setString(41,taxableCd);
            	pstmt.setString(42,allowUserToChangePasswd);
            	pstmt.setString(43,enableInvOrderProcess);
            	pstmt.setString(44,allowCustomerPoNum);
            	pstmt.setString(45,authorizedResellingItems);
            	pstmt.setString(46,showDistSkuNum);
            	pstmt.setString(47,showDistDeliveryDate);
            	pstmt.setString(48,modifyOrderQtyCostCenter);
            	pstmt.setString(49,allowReorder);
            	pstmt.setString(50,createOrderByOrderAck);
            	pstmt.setString(51,modernShoppingFolder);
            	pstmt.setString(52,showInvItemPrice);
            	pstmt.setString(53,displayMyShoppingList);
            	pstmt.setString(54,expressOrder);
            	pstmt.setString(55,deliveryCutoffDays);
            	pstmt.setString(56,accountFolder);
            	pstmt.setString(57,autoOrderQtyFactor);
            	pstmt.setString(58,allowOrdInvItem);
            	pstmt.setString(59,userPhysicalIng);
            	pstmt.setString(60,shopUiType);
            	pstmt.setString(61,faqLink);
            	pstmt.setString(62,pdfOrderClassName);
            	pstmt.setString(63,pdfOrderStatusName);
            	pstmt.setString(64,reminderEmailSubject);
            	pstmt.setString(65,reminderEmailMessage);
            	pstmt.setString(66,orderConfirmEmailGen);
            	pstmt.setString(67,orderNotificationEmailGen);
            	pstmt.setString(68,orderRejectedEmailGen);
            	pstmt.setString(69,pendingApprovalEmail);
            	pstmt.setString(70,invPropertyBudget);
            	pstmt.setString(71,invPropertyPoSuffix);
            	pstmt.setString(72,invPropertyOg);
            	pstmt.setString(73,invPropSendNotification);
            	pstmt.setString(74,invPropDonotPlaceInvOrd);
            	pstmt.setString(75,invPropDistPoType);
            	pstmt.setString(76,fiscalCalendarYear);
            	pstmt.setString(77,fiscalCalendarStart);
            	pstmt.setString(78,fiscalCalendarPeriod);
            	pstmt.setString(79,allowToSetWorkorderPo);
            	pstmt.setString(80,workOrderPoNumReq);
            	pstmt.setString(81,userAssignedAsserNum);
            	pstmt.setString(82,allowOrdPartsWorkOrder);
            	pstmt.setString(83,populateContactInfo);
            	pstmt.setString(84,accountloader);
            	pstmt.setString(85,productUiTemplate);

                pstmt.addBatch();
                if(i > 0 && i % 10000 == 0){
                     log.info("Calling execute batch at 10000 records");
                     pstmt.executeBatch();
                }

            }catch(Exception e){
			//	conn.rollback();
				conn.close();
				conn = null;
				throw e;
			}

        }

        pstmt.executeBatch();
        pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
        pstmt.close();
        // verify that all records are loaded into temporary table
        if (n != parsedData.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records.");
        }

        pstmt = conn.prepareStatement(selectStoreId);
        ResultSet rs = pstmt.executeQuery();
        String storeNam = "";
        while(rs.next()){
			storeNum = rs.getInt(1);
			storeNam = rs.getString(2);
			if (rs.next())
        		throw new Exception("Error Found Two distinct Store ID's in File");
		}
        pstmt.close();
        if(storeNum == 0){
        	throw new Exception("Store ID is missing in File");
        } /*else{
     	   log.info("storeID in File = " + storeNum + "storeName is file = " + storeNam);
   	    pstmt = conn.prepareStatement(verifyStoreId);
       	pstmt.setString(1, storeNam);
       	rs = pstmt.executeQuery();
       	int busEntityI = 0;
       	while(rs.next()){
       		busEntityI = rs.getInt(1);
       	}
       	log.info("storeID from system = " + busEntityI);
       	if(busEntityI != storeNum){
       		throw new Exception("Error Found Store ID and store name in File does not match store_id and store name in System =" + storeNum + " store Name in File =" + storeNam + "store_id in system = " + busEntityI + "sql=" + verifyStoreId);
       	}


		pstmt.close();
      }*/


        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }
        IntegrationServices isEjb = APIAccess.getAPIAccess()
                .getIntegrationServicesAPI();
        IntegrationRequestsVector irv = new IntegrationRequestsVector();
        int tradingPartnerId = partner.getTradingPartnerId();



    }
}
