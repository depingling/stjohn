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

import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.ListService;
import java.text.DateFormat;
import java.util.regex.*;


public class InboundNSCSapAccountLoader extends InboundNSCSapLoader {//InboundFlatFile {
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

private final static String accountloader = "NSCSapAccountLoader";
private final static String accountActionAdd = "A";
private final static String accountActionChange = "C";
private final static String accountActionDelete = "D";
private final static String tempTable = "CLT_NSC_ACCOUNT_LOADER";
private final static int ERROR_LIMIT = 5;//00;

//private final static int storeNum = 182241;
private int storeNum = 0;
private Date runDate = new Date();
private int currentYear = 0;
Connection conn = null;

    private static final String insertSql = "insert into " + tempTable + "(VERSION,ACTION,STORE_ID,STORE_NAME,ACCOUNT_REF_NUM,ACCOUNT_NAME,ACCOUNT_TYPE,BUDGET_TYPE,TIME_ZONE, " +
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
    "WORK_ORDER_PO_NUM_REQ,USER_ASSIGNED_ASSET_NUM,ALLOW_ORDER_PARTS_WORKORDER,POPULATE_CONTACT_INFO,ADD_BY,ADD_DATE)" +
    	" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
    	"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";


    private static final String trimDataSql = "update " + tempTable + " set " +
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
    		"POPULATE_CONTACT_INFO	= trim(POPULATE_CONTACT_INFO) ";


    protected Logger log = Logger.getLogger(InboundNSCSapAccountLoader.class);

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        currentYear = Utility.getYearForDate(new Date());
        log.info("[InboundNSCSapAccountLoader].translate() => BEGIN.");
        IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.parse(pIn);
         //   parser.cleanUpResult();
            storeNum = getStoreIdFromTradingPartner();
            List<List<String>> list = (List<List<String>>) parser.getParsedStrings();
            process(list);
            log.info("[InboundNSCSapAccountLoader].tranlate() => STORE ID =" + storeNum);
            isEjb.processNSCSapAccount(conn,accountloader,accountActionAdd,accountActionChange,accountActionDelete,storeNum, tempTable);
            log.info("[InboundNSCSapAccountLoader].doPostProcessing() => END. Processed: " + list.size()+" accounts. ");

          } catch (Exception e) {
            log.info("ERROR(s) :" + Utility.getInitErrorMsg(e));
            log.error("[InboundNSCSapAccountLoader].translate() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms");
            setFail(true);
            throw e;
        }
        log.info("[InboundNSCSapAccountLoader].translate() => END.Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
    }


    private void process(List<List<String>> parsedData) throws Exception {

        initRefCodeNamesMap();
        initCountriesRequiredStateSet();

        if (parsedData.size() == 0) {
           throw new Exception("^clw^Input file could be empty.^clw^");
         }

        if (conn == null){
           conn = getConnection();
        }
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size() && !isErrorLimit() ; i++) {
           List<String> line = (List<String>) parsedData.get(i);
           if (isValid (line, i)){
             processLine (line,  pstmt);
           }
         }
         if (errorLines.size() > 0){
           processErrorMsgs();
           throw new Exception("^clw^"+getFormatedErrorMsgs()+"^clw^");
         }

        pstmt.executeBatch();
        pstmt = conn.prepareStatement(trimDataSql);
        pstmt.executeUpdate();

//        getStoreIdFromFile(pstmt);
        pstmt.close();


    }
    private void processLine (List<String> line, PreparedStatement pstmt) throws Exception {
      String versionCd 			= line.get(VERSION);
      String actionCd 			= line.get(ACTION);
      String storeID 			= line.get(STORE_ID);
      String storeName 			= line.get(STORE_NAME);
      String accountRefNum 		= line.get(ACCOUNT_REF_NUM);
      String accountName 		= line.get(ACCOUNT_NAME);
      String accountType 		= line.get(ACCOUNT_TYPE);
      String budgetType 		= line.get(BUDGET_TYPE);
      String timeZone 		= line.get(TIME_ZONE);
      String siteLlcOverride 		= line.get(SITE_LLC_OVERRIDE);
      String customerServiceEmail 	= line.get(CUSTOMER_SERVICE_EMAIL);
      String contactCcEmail 		= line.get(CONTACT_CC_EMAIL);
      String defaultEmail 		= line.get(DEFAULT_EMAIL);
      String distAccountRefNum 		= line.get(DIST_ACCOUNT_REF_NUM);
      String primaryContactFirstName 	= line.get(PRIMARY_CONTACT_FIRST_NAME);
      String primaryContactLastName 	= line.get(PRIMARY_CONTACT_LAST_NAME);
      String primaryContactPhone	= line.get(PRIMARY_CONTACT_PHONE);
      String primaryContactFax 		= line.get(PRIMARY_CONTACT_FAX);
      String primaryContactEmail	= line.get(PRIMARY_CONTACT_EMAIL);
      String primaryContactAddress1 	= line.get(PRIMARY_CONTACT_ADDESS1);
      String primaryContactAddress2 	= line.get(PRIMARY_CONTACT_ADDESS2);
      String primaryContactAddress3 	= line.get(PRIMARY_CONTACT_ADDESS3);
      String primaryContactCity 	= line.get(PRIMARY_CONTACT_CITY);
      String primaryContactState	= line.get(PRIMARY_CONTACT_STATE);
      String primaryContactCountry 	= line.get(PRIMARY_CONTACT_COUNTRY);
      String primaryContactPostalCode = line.get(PRIMARY_CONTACT_POSTAL_CODE);
      String billingAddress1 		= line.get(BILLING_ADDESS1);
      String billingAddress2 		= line.get(BILLING_ADDESS2);
      String billingAddress3 		= line.get(BILLING_ADDESS3);
      String billingCity 		= line.get(BILLING_CITY);
      String billingState 		= line.get(BILLING_STATE);
      String billingCountry 		= line.get(BILLING_COUNTRY);
      String billingPostalCode 		= line.get(BILLING_POSTAL_CODE);
      String purchaseOrderAccountName = line.get(PURCHASE_ORDER_ACCOUNT_NAME);
      String orderContactPhone 		= line.get(ORDER_CONTACT_PHONE);
      String orderContactFax 		= line.get(ORDER_CONTACT_FAX);
      String orderGuideComments 	= line.get(ORDER_GUIDE_COMMENTS);
      String orderGuideNotes 		= line.get(ORDER_GUIDE_NOTES);
      String orderGuideSkuHeading 	= line.get(ORDER_GUIDE_SKU_HEADING);
      String orderItemAction 		= line.get(ORDER_ITEM_ACTION);
      String taxableCd 			= line.get(TAXABLE);
      String allowUserToChangePasswd 	= line.get(ALLOW_USER_TO_CHANGE_PASSWORD);
      String enableInvOrderProcess 	= line.get(ENABLE_INVENTORY_ORDER_PROCESS);
      String allowCustomerPoNum 	= line.get(ALLOW_CUSTOMER_PO_NUM);
      String authorizedResellingItems = line.get(AUTHORIZED_RESELLING_ITEMS);
      String showDistSkuNum 		= line.get(SHOW_DIST_SKU_NUM);
      String showDistDeliveryDate 	= line.get(SHOW_DIST_DELIVERY_DATE);
      String modifyOrderQtyCostCenter = line.get(MODIFY_ORDER_QTY_COST_CENTER);
      String allowReorder 		= line.get(ALLOW_REORDER);
      String createOrderByOrderAck 	= line.get(CREATE_ORDER_BY_ORDER_ACK);
      String modernShoppingFolder 	= line.get(MODER_SHOPPING_FOLDER);
      String showInvItemPrice 		= line.get(SHOW_INVENTORY_ITEM_PRICE);
      String displayMyShoppingList 	= line.get(DISPLAY_MY_SHOPPING_LIST);
      String expressOrder 		= line.get(EXPRESS_ORDER);
      String deliveryCutoffDays 	= line.get(DELIVERY_CUTOFF_DAYS);
      String accountFolder 		= line.get(ACCOUNT_FOLDER);
      String autoOrderQtyFactor 	= line.get(AUTO_ORDER_QTY_FACTOR);
      String allowOrdInvItem 		= line.get(ALLOW_ORD_INV_ITEM);
      String userPhysicalIng 		= line.get(USER_PHYSICAL_ING);
      String shopUiType 		= line.get(SHOP_UI_TYPE);
      String faqLink 			= line.get(FAQ_LINK);
      String pdfOrderClassName 		= line.get(PDF_ORDER_CLASS_NAME);
      String pdfOrderStatusName 	= line.get(PDF_ORDER_STATUS_NAME);
      String reminderEmailSubject 	= line.get(REMINDER_EMAIL_SUBJECT);
      String reminderEmailMessage 	= line.get(REMINDER_EMAIL_MESSAGE);
      String orderConfirmEmailGen 	= line.get(ORDER_CONFIRM_EMAIL_GEN);
      String orderNotificationEmailGen = line.get(ORDER_NOTIFICATION_EMAIL_GEN);
      String orderRejectedEmailGen 	= line.get(ORDER_REJECTED_EMAIL_GEN);
      String pendingApprovalEmail 	= line.get(PENDING_APPROVAL_EMAIL);
      String invPropertyBudget 		= line.get(INVENTORY_PROPERTY_BUDGET);
      String invPropertyPoSuffix	= line.get(INVENTORY_PROPERTY_PO_SUFFIX);
      String invPropertyOg 		= line.get(INVENTORY_PROPERTY_OG);
      String invPropSendNotification 	= line.get(INV_PROPERTY_SEND_NOTIFICATION);
      String invPropDonotPlaceInvOrd 	= line.get(INV_PROP_DONOT_PLACE_INV_ORDER);
      String invPropDistPoType 		= line.get(INV_PROP_DIST_PO_TYPE);
      String fiscalCalendarYear 	= line.get(FISCAL_CALENDAR_YEAR);
      String fiscalCalendarStart	= line.get(FISCAL_CALENDAR_START);
      String fiscalCalendarPeriod 	= line.get(FISCAL_CALENDAR_PERIOD);
      String allowToSetWorkorderPo 	= line.get(ALLOW_TO_SET_WORKORDER_PO);
      String workOrderPoNumReq 		= line.get(WORK_ORDER_PO_NUM_REQ);
      String userAssignedAsserNum 	= line.get(USER_ASSIGNED_ASSET_NUM);
      String allowOrdPartsWorkOrder 	= line.get(ALLOW_ORDER_PARTS_WORKORDER);
      String populateContactInfo 	= line.get(POPULATE_CONTACT_INFO);



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

          pstmt.addBatch();

      }catch(Exception e){
            conn.close();
            conn = null;
            throw e;
      }

    }

 private boolean checkFiscalCalendar (String fiscalYear, String fiscalStartDate, String fiscalPeriods , int line) throws Exception {
   boolean valid = true;
   if (!Utility.isSet(fiscalYear) &&
       !Utility.isSet(fiscalStartDate) &&
       !Utility.isSet(fiscalPeriods)) {
     return true;

   }
   Pattern regExp = Pattern.compile("(\\d{2}/\\d{2}[,])+");
   if (fiscalPeriods.lastIndexOf(",") < fiscalPeriods.length() - 1) {
     fiscalPeriods += ",";
   }
   Matcher m = regExp.matcher(fiscalPeriods);
   boolean isValidPeriods= m.matches();
   if (!isValidPeriods){
      addError("Incorrect date(s) for FISCAL CALENDAR PERIODS : '" + fiscalPeriods ,line);
   }

   boolean isValidType = true;
   boolean ok = true;
   isValidType &= checkType(fiscalYear, "Fiscal Calendar Year",TYPE.YEAR, true, line);
   //------------------------------------------------------
   isValidType &= checkType(fiscalStartDate, "Fiscal Calendar Start",TYPE.DATE, true, line);

   if(isValidType && isValidPeriods){
     String[] fp = fiscalPeriods.split(",");
     if ( Pattern.matches("\\d{2}/\\d{2}", fp[0]) ) {
       ok = fiscalStartDate.substring(0, 5).equals(fp[0]);
       if (!ok) {
         addError("Incorrect FISCAL START DAY value : '" + fiscalStartDate +"'. Value of FISCAL START DAY must be equal with value of first period. ", line);
       }
     }
   }
   valid &= ok;

   if (isValidType && Integer.parseInt(fiscalYear)==0){  // validation for Fiscal year = 0;
     ok = ("01/01".equals(fiscalStartDate.substring(0,5)));//
     if (!ok){
       addError("Incorrect FISCAL START DAY value : '" + fiscalStartDate +"'. Fiscal calendars of 0 require date of first period to start on 01/01. " , line);
     }
     valid &= ok;


 /*
     DateFormat df  = new SimpleDateFormat("yyyy");
     ok = (Integer.parseInt(fiscalStartDate.substring(6)) >= Integer.parseInt(df.format(new Date())));
     if (!ok){
       addError("Incorrect FISCAL START DAY value : '" + fiscalStartDate +"'. Year can't be less then current when FISCAL YEAR = 0. Line: " + line);
     }
     valid &= ok;
*/
   }
   valid &= isValidPeriods && isValidType;
   return valid;
 }

private boolean isValid(List<String> lineValues, int line) throws Exception{
   StringBuffer newValue = new StringBuffer();
   boolean valid = true;
   boolean ok = true;
    valid &= checkRequired (lineValues.get(VERSION), "Version Number", line);

    String action = lineValues.get(ACTION);
    ok = checkRequired (action, "Action", line);
    if (ok && (action.trim().length()>1 || !"ACD".contains(action.trim().toUpperCase()))){
        addError("Incorrect Action =" + action+" . Should be A|C|D. " , line);
        ok = false;
    }
    valid &= ok;

    ok = checkType(lineValues.get(STORE_ID), "Store Id", TYPE.INTEGER, true, line);
    valid &=ok;
    if (ok && Integer.parseInt(lineValues.get(STORE_ID)) != storeNum) {
      addError("Incorrect STORE ID value = '" + Integer.parseInt(lineValues.get(STORE_ID)) + "'. Trading Partner associated with store Id =" + storeNum +". ", line);
      valid = false;
    }

    valid &= checkRequired(lineValues.get(STORE_NAME), "Store Name"+". ", line);

    String accName = lineValues.get(ACCOUNT_NAME);
    ok = checkRequired(accName, "Account Name", line);
    if (ok && accName.trim().length() > 30){
      addError("Account Name: '" + accName+"' is too long. Max length is 30 char. " ,line);
      ok = false;
    }
    valid &= ok;

    valid &= checkRequired(lineValues.get(ACCOUNT_REF_NUM), "Account Ref Num", line);
    valid &= checkType(lineValues.get(ACCOUNT_TYPE), "Account Type", TYPE.TEXT, false, line);

    newValue = new StringBuffer();
    valid &= checkType(lineValues.get(BUDGET_TYPE), "Budget Type", TYPE.REF_CODE_NAME, true, line, newValue);
    lineValues.set(BUDGET_TYPE, newValue.toString());

//  valid &= checkRequired(lineValues.get(TIME_ZONE), "Tyme Zone", line);
    valid &= checkType(lineValues.get(SITE_LLC_OVERRIDE), "Allow Site LLC Override", TYPE.BOOLEAN,true, line);

    valid &= checkType(lineValues.get(CUSTOMER_SERVICE_EMAIL), "Customer Service Email",TYPE.TEXT, false,line);
    valid &= checkType(lineValues.get(CONTACT_CC_EMAIL), "Contact Us CC Email",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(DEFAULT_EMAIL), "Default Email",TYPE.TEXT,true, line);
    valid &= checkType(lineValues.get(DIST_ACCOUNT_REF_NUM), "Distributor Account Ref Num",TYPE.TEXT,true, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_FIRST_NAME), "PRIMARY_CONTACT_FIRST_NAME",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_LAST_NAME), "PRIMARY_CONTACT_LAST_NAME",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_PHONE), "PRIMARY_CONTACT_PHONE",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_FAX), "PRIMARY_CONTACT_FAX",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_EMAIL), "PRIMARY_CONTACT_EMAIL",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_ADDESS1), "PRIMARY_CONTACT_ADDESS1",TYPE.TEXT, false, line);

    valid &= checkType(lineValues.get(PRIMARY_CONTACT_ADDESS2), "PRIMARY_CONTACT_ADDESS2",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_ADDESS3), "PRIMARY_CONTACT_ADDESS3",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_CITY), "PRIMARY_CONTACT_CITY", TYPE.TEXT, false,line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_STATE), "PRIMARY_CONTACT_STATE",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_COUNTRY), "PRIMARY_CONTACT_COUNTRY",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(PRIMARY_CONTACT_POSTAL_CODE), "",TYPE.TEXT, false, line);
    valid &= checkRequired(lineValues.get(BILLING_ADDESS1), "Billing Street Address1", line);
    valid &= checkType(lineValues.get(BILLING_ADDESS2), "Billing Street Address2", TYPE.TEXT, false,line);
    valid &= checkType(lineValues.get(BILLING_ADDESS3), "Billing Street Address3",TYPE.TEXT, false, line);
    valid &= checkRequired(lineValues.get(BILLING_CITY), "Billing City", line);

    String country = lineValues.get(BILLING_COUNTRY);
    valid &= checkRequired(country, "Billing Country", line);
    boolean isStateReq = isStateRequired(country);
    valid &= checkType(lineValues.get(BILLING_STATE), "Billing State",TYPE.TEXT, isStateReq ,line);
    valid &= checkType(lineValues.get(BILLING_POSTAL_CODE), "Billing Postal Code",TYPE.TEXT, isStateReq, line);

    valid &= checkType(lineValues.get(PURCHASE_ORDER_ACCOUNT_NAME), "PURCHASE_ORDER_ACCOUNT_NAME",TYPE.TEXT, false, line);
    valid &= checkRequired(lineValues.get(ORDER_CONTACT_PHONE), "Order Contact Phone", line);
    valid &= checkRequired(lineValues.get(ORDER_CONTACT_FAX), "Order Contact Fax", line);
    valid &= checkType(lineValues.get(ORDER_GUIDE_COMMENTS), "ORDER_GUIDE_COMMENTS", TYPE.TEXT, false,line);
    valid &= checkType(lineValues.get(ORDER_GUIDE_NOTES), "ORDER_GUIDE_NOTES",TYPE.TEXT, false, line);
    valid &= checkType(lineValues.get(ORDER_GUIDE_SKU_HEADING), "ORDER_GUIDE_SKU_HEADING",TYPE.TEXT, false, line);

    newValue = new StringBuffer();
    valid &= checkType(lineValues.get(ORDER_ITEM_ACTION), "Order Item Actions", TYPE.REF_CODE_NAME_LIST,true, line, newValue);
    lineValues.set(ORDER_ITEM_ACTION, newValue.toString());

     valid &= checkType(lineValues.get(TAXABLE), "Taxable", TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(ALLOW_USER_TO_CHANGE_PASSWORD), "Allow User to Change Password", TYPE.BOOLEAN, true,line);
     valid &= checkType(lineValues.get(ENABLE_INVENTORY_ORDER_PROCESS), "Enable Inventory order processing",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(ALLOW_CUSTOMER_PO_NUM), "Allow customer to enter PO number",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(AUTHORIZED_RESELLING_ITEMS), "Authorized for Re-Selling Items",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(SHOW_DIST_SKU_NUM), "Show Distributor SKU #", TYPE.BOOLEAN, true,line);
     valid &= checkType(lineValues.get(SHOW_DIST_DELIVERY_DATE), "Show Dist. Delivery Date", TYPE.BOOLEAN, true,line);
     valid &= checkType(lineValues.get(MODIFY_ORDER_QTY_COST_CENTER), "Modify Order Quantity...",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(ALLOW_REORDER), "Allow Reorder", TYPE.BOOLEAN, true,line);
     valid &= checkType(lineValues.get(CREATE_ORDER_BY_ORDER_ACK), "Create Order by Order Acknowledgment(855)",TYPE.BOOLEAN, true, line);

     valid &= checkType(lineValues.get(MODER_SHOPPING_FOLDER), "", TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(SHOW_INVENTORY_ITEM_PRICE), "Show Inv Item Price in cart total",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(DISPLAY_MY_SHOPPING_LIST), "Display My Shopping Lists", TYPE.BOOLEAN, true,line);
     valid &= checkType(lineValues.get(EXPRESS_ORDER), "Express Order",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(DELIVERY_CUTOFF_DAYS), "Delivery Cuttoff Days",TYPE.INTEGER, true, line);
     valid &= checkType(lineValues.get(ACCOUNT_FOLDER), "ACCOUNT_FOLDER", TYPE.TEXT, false,line);
     valid &= checkType(lineValues.get(AUTO_ORDER_QTY_FACTOR), "Auto Order Qty Factor", TYPE.INTEGER, true,line);
     valid &= checkType(lineValues.get(ALLOW_ORD_INV_ITEM), "Allow Order Inv Items in Scheduled cart period ",TYPE.BOOLEAN, true, line);
     valid &= checkType(lineValues.get(USER_PHYSICAL_ING), "Use Physical Inventory",TYPE.BOOLEAN, true, line);

     newValue = new StringBuffer();
     valid &= checkType(lineValues.get(SHOP_UI_TYPE), "Shop UI Type", TYPE.REF_CODE_NAME,false, line, newValue);
     lineValues.set(SHOP_UI_TYPE, newValue.toString());

     valid &= checkType(lineValues.get(FAQ_LINK), "FAQ_LINK", TYPE.TEXT, false,line);
     valid &= checkType(lineValues.get(PDF_ORDER_CLASS_NAME), "PDF_ORDER_CLASS_NAME",TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(PDF_ORDER_STATUS_NAME), "PDF_ORDER_STATUS_NAME", TYPE.TEXT, false,line);
     valid &= checkType(lineValues.get(REMINDER_EMAIL_SUBJECT), "REMINDER_EMAIL_SUBJECT",TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(REMINDER_EMAIL_MESSAGE), "REMINDER_EMAIL_MESSAGE",TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(ORDER_CONFIRM_EMAIL_GEN), "ORDER_CONFIRM_EMAIL_GEN",TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(ORDER_NOTIFICATION_EMAIL_GEN), "ORDER_NOTIFICATION_EMAIL_GEN",TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(ORDER_REJECTED_EMAIL_GEN), "ORDER_REJECTED_EMAIL_GEN",TYPE.TEXT, false, line);
     valid &= checkType(lineValues.get(PENDING_APPROVAL_EMAIL), "PENDING_APPROVAL_EMAIL", TYPE.TEXT, false,line);
     valid &= checkType(lineValues.get(INVENTORY_PROPERTY_BUDGET), "Inv Property Budget",TYPE.BOOLEAN, true, line);

     valid &= checkType(lineValues.get(INVENTORY_PROPERTY_PO_SUFFIX), "INVENTORY_PROPERTY_PO_SUFFIX",TYPE.TEXT, false, line);

     newValue = new StringBuffer();
     valid &= checkType(lineValues.get(INVENTORY_PROPERTY_OG), "Inv Property O.G. Inventory UI", TYPE.REF_CODE_NAME,false, line, newValue);
     lineValues.set(INVENTORY_PROPERTY_OG, newValue.toString());

     valid &= checkType(lineValues.get(INV_PROPERTY_SEND_NOTIFICATION), "Inv Property Send Notification...", TYPE.INTEGER, false, line);
     valid &= checkType(lineValues.get(INV_PROP_DONOT_PLACE_INV_ORDER), "Inv Property Do not place inv. order...",TYPE.INTEGER, true, line);

     newValue = new StringBuffer();
     valid &= checkType(lineValues.get(INV_PROP_DIST_PO_TYPE), "Inv Property Distr PO type", TYPE.REF_CODE_NAME, true, line, newValue);
     lineValues.set(INV_PROP_DIST_PO_TYPE, newValue.toString());

     valid &= checkFiscalCalendar(lineValues.get(FISCAL_CALENDAR_YEAR), lineValues.get(FISCAL_CALENDAR_START), lineValues.get(FISCAL_CALENDAR_PERIOD), line  );

     valid &= checkType(lineValues.get(ALLOW_TO_SET_WORKORDER_PO), "Allow to set Work Order PO Number",TYPE.BOOLEAN, false, line);
     valid &= checkType(lineValues.get(WORK_ORDER_PO_NUM_REQ), "Work Order PO Number is required",TYPE.BOOLEAN, false, line);

     valid &= checkType(lineValues.get(USER_ASSIGNED_ASSET_NUM), "User assigned Asset Number",TYPE.BOOLEAN, false, line);
     valid &= checkType(lineValues.get(ALLOW_ORDER_PARTS_WORKORDER), "Allow to Order Parts for Work Order ",TYPE.BOOLEAN, false, line);

     newValue = new StringBuffer();
     valid &= checkType(lineValues.get(POPULATE_CONTACT_INFO), "Populate Contact Information", TYPE.REF_CODE_NAME, false, line, newValue);
     lineValues.set(POPULATE_CONTACT_INFO, newValue.toString());

   return valid;
 }

    private void initRefCodeNamesMap () throws Exception{
      ListService lsvc = APIAccess.getAPIAccess().getListServiceAPI();

      refCodeNamesMap.put("Order Item Actions", lsvc.getRefCodesCollection("ORDER_ITEM_DETAIL_ACTION_CD"));
//      refCodeNamesMap.put("Budget Type", lsvc.getRefCodesCollection("BUDGET_ACCRUAL_TYPE_CD"));
//      refCodeNamesMap.put("Shop UI Type", lsvc.getRefCodesCollection("SHOP_UI_TYPE"));
//      refCodeNamesMap.put("Inv Property Distr PO type", lsvc.getRefCodesCollection("DISTR_PO_TYPE"));
//      refCodeNamesMap.put("Inv Property O.G. Inventory UI", lsvc.getRefCodesCollection("INVENTORY_OG_LIST_UI"));

      RefCdDataVector rfcd0 = new RefCdDataVector();

      RefCdData rf01 = RefCdData.createValue();
      RefCdData rf02 = RefCdData.createValue();
      rf01.setValue(RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_FISCAL_YEAR);
      rf01.setValue(RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_FISCAL_YEAR);
      rfcd0.add(rf01);
      rfcd0.add(rf02);
      refCodeNamesMap.put("Budget Type", rfcd0);

      RefCdDataVector rfcd1 = new RefCdDataVector();
      RefCdData rf11 = RefCdData.createValue();
      RefCdData rf12 = RefCdData.createValue();
      rf11.setValue(RefCodeNames.SHOP_UI_TYPE.B2B);
      rf12.setValue(RefCodeNames.SHOP_UI_TYPE.B2C);
      rfcd1.add(rf11);
      rfcd1.add(rf12);
      refCodeNamesMap.put("Shop UI Type", rfcd1);

      RefCdDataVector rfcd2 = new RefCdDataVector();
      RefCdData rf21 = RefCdData.createValue();
      RefCdData rf22 = RefCdData.createValue();
      rf21.setValue(RefCodeNames.INVENTORY_OG_LIST_UI.COMMON_LIST);
      rf22.setValue(RefCodeNames.INVENTORY_OG_LIST_UI.SEPARATED_LIST);
      rfcd2.add(rf21);
      rfcd2.add(rf22);
      refCodeNamesMap.put("Inv Property O.G. Inventory UI", rfcd2);

      RefCdDataVector rfcd3 = new RefCdDataVector();
      RefCdData rf31 = RefCdData.createValue();
      RefCdData rf32 = RefCdData.createValue();
      RefCdData rf33 = RefCdData.createValue();
      rf31.setValue(RefCodeNames.DISTR_PO_TYPE.CUSTOMER);
      rf32.setValue(RefCodeNames.DISTR_PO_TYPE.REQUEST);
      rf33.setValue(RefCodeNames.DISTR_PO_TYPE.SYSTEM);
      rfcd3.add(rf31);
      rfcd3.add(rf32);
      rfcd3.add(rf33);
      refCodeNamesMap.put("Inv Property Distr PO type", rfcd3);

      RefCdDataVector rfcd4 = new RefCdDataVector();
      RefCdData rf41 = RefCdData.createValue();
      RefCdData rf42 = RefCdData.createValue();
      RefCdData rf43 = RefCdData.createValue();
      rf41.setValue("USER");
      rf42.setValue("SITE");
      rf43.setValue("NONE");
      rfcd4.add(rf41);
      rfcd4.add(rf42);
      rfcd4.add(rf43);
      refCodeNamesMap.put("Populate Contact Information", rfcd4);


    }

 }
