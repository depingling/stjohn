package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import com.cleanwise.view.forms.Admin2AccountMgrDetailForm;
import com.cleanwise.view.forms.Admin2AccountMgrForm;
import com.cleanwise.view.forms.Admin2UserMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class Admin2AccountMgrLogic {

    private static final Logger log = Logger.getLogger(Admin2AccountMgrLogic.class);

    public static final String ADMIN2_ACCOUNT_MGR_FORM = "ADMIN2_ACCOUNT_MGR_FORM";
    public static final String ADMIN2_ACCOUNT_DETAIL_MGR_FORM = "ADMIN2_ACCOUNT_DETAIL_MGR_FORM";
    public static final String ADMIN2_ACCOUNT_CONFIG_MGR_FORM = "ADMIN2_ACCOUNT_CONFIG_MGR_FORM";

    public static ActionErrors getDetail(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        int reqAccountId;
        if (appUser.isaAccountAdmin()) {
            reqAccountId = appUser.getUserAccount().getAccountId();
        } else {
            reqAccountId = Admin2Tool.getRequestedId(request);
            if (reqAccountId <= 0) {
                reqAccountId = pForm.getIntId();
            }
        }

        return detail(request, reqAccountId);

    }

    public static ActionErrors detail(HttpServletRequest request, int pAccountId) throws Exception {

        log.info("detail =>  BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init(request, (Admin2AccountMgrDetailForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        Admin2AccountMgrDetailForm pForm = (Admin2AccountMgrDetailForm) session.getAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM);
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = checkUserAccessTo(request, pAccountId);
        if (ae.size() > 0) {
            return ae;
        }

        Account accountEjb = factory.getAccountAPI();
        Store storeEjb = factory.getStoreAPI();

        AccountData account;
        try {
            account = accountEjb.getAccount(pAccountId, appUser.getUserStore().getStoreId());
        } catch (DataNotFoundException e) {
            reset(pForm);
            session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);
            ae.add("error", new ActionError("error.simpleGenericError", "Account is not found"));
            e.printStackTrace();
            return ae;
        }

        pForm.setAccountData(account);

        // Set the form values.

        pForm.setRuntimeDisplayOrderItemActionTypes((String[]) account.getRuntimeDisplayOrderItemActionTypes().toArray(new String[0]));

        BusEntityData bed = account.getBusEntity();

        pForm.setName(bed.getShortDesc());
        pForm.setStatusCd(bed.getBusEntityStatusCd());
        pForm.setTimeZoneCd(bed.getTimeZoneCd());
        pForm.setId(String.valueOf(bed.getBusEntityId()));
        pForm.setTypeCd(bed.getBusEntityTypeCd());

        pForm.setGlTransformationType(account.getGlTransformationType());
        pForm.setDistributorAccountRefNum(account.getDistributorAccountRefNum());

        if (Utility.isSet(bed.getErpNum())) {
            pForm.setAccountNumber(bed.getErpNum());
        } else {
            pForm.setAccountNumber("#" + pAccountId);
            bed.setErpNum("#" + pAccountId);
        }

        BusEntityAssocData storeassoc = account.getStoreAssoc();

        pForm.setStoreId(String.valueOf(storeassoc.getBusEntity2Id()));

        StoreData storedata = storeEjb.getStore(storeassoc.getBusEntity2Id());

        pForm.setStoreName(storedata.getBusEntity().getShortDesc());

        PropertyData acctType = account.getAccountType();
        pForm.setTypeDesc(acctType.getValue());

        try {
            PropertyData orderMinimum = account.getOrderMinimum();
            BigDecimal minvalue = CurrencyFormat.parse(orderMinimum.getValue());
            pForm.setOrderMinimum(CurrencyFormat.format(minvalue));
        } catch (ParseException pe) {
            pForm.setOrderMinimum(CurrencyFormat.format(new BigDecimal(0)));
        }

        try {
            PropertyData creditLimit = account.getCreditLimit();
            BigDecimal value = CurrencyFormat.parse(creditLimit.getValue());
            pForm.setCreditLimit(CurrencyFormat.format(value));
        } catch (ParseException pe) {
            pForm.setCreditLimit(CurrencyFormat.format(new BigDecimal(0)));
        }

        PropertyData creditRating = account.getCreditRating();
        pForm.setCreditRating(creditRating.getValue());

        PropertyData crcShop = account.getCrcShop();
        pForm.setCrcShop(isTRUE(crcShop.getValue()));

        PhoneData phone = account.getPrimaryPhone();
        pForm.setPhone(phone.getPhoneNum());

        PhoneData orderPhone = account.getOrderPhone();
        pForm.setOrderPhone(orderPhone.getPhoneNum());

        PhoneData fax = account.getPrimaryFax();
        pForm.setFax(fax.getPhoneNum());

        PhoneData orderFax = account.getOrderFax();
        pForm.setOrderFax(orderFax.getPhoneNum());

        PhoneData poConfirmFax = account.getFaxBackConfirm();
        pForm.setFaxBackConfirm(poConfirmFax.getPhoneNum());

        PropertyData comments = account.getComments();
        pForm.setComments(comments.getValue());

        PropertyData orderGuideNote = account.getOrderGuideNote();
        if (null != orderGuideNote) {
            pForm.setOrderGuideNote(orderGuideNote.getValue());
        } else {
            pForm.setOrderGuideNote("");
        }

        PropertyData skuTag = account.getSkuTag();
        if (null != skuTag) {
            pForm.setSkuTag(skuTag.getValue());
        } else {
            pForm.setSkuTag("");
        }

        pForm.setCustomerSystemApprovalCd(account.getCustomerSystemApprovalCd());
        pForm.setCustomerRequestPoAllowed(account.isCustomerRequestPoAllowed());
        pForm.setTaxableIndicator(account.isTaxableIndicator());

        EmailData email = account.getPrimaryEmail();
        pForm.setEmailAddress(email.getEmailAddress());

        EmailData customerEmail = account.getCustomerServiceEmail();
        pForm.setCustomerEmail(customerEmail.getEmailAddress());

        EmailData defaultEmail = account.getDefaultEmail();
        pForm.setDefaultEmail(defaultEmail.getEmailAddress());

        String contactUsCCEmail = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD);
        pForm.setContactUsCCEmail(contactUsCCEmail);
        pForm.setDataFieldProperties(account.getDataFieldProperties());

        AddressData address = account.getPrimaryAddress();
        pForm.setName1(address.getName1());
        pForm.setName2(address.getName2());
        pForm.setPostalCode(address.getPostalCode());
        pForm.setStateOrProv(address.getStateProvinceCd());
        pForm.setCountry(address.getCountryCd());
        pForm.setCity(address.getCity());
        pForm.setStreetAddr1(address.getAddress1());
        pForm.setStreetAddr2(address.getAddress2());
        pForm.setStreetAddr3(address.getAddress3());

        AddressData billingAddress = account.getBillingAddress();
        pForm.setBillingPostalCode(billingAddress.getPostalCode());
        pForm.setBillingState(billingAddress.getStateProvinceCd());
        pForm.setBillingCountry(billingAddress.getCountryCd());
        pForm.setBillingCity(billingAddress.getCity());
        pForm.setBillingAddress1(billingAddress.getAddress1());
        pForm.setBillingAddress2(billingAddress.getAddress2());
        pForm.setBillingAddress3(billingAddress.getAddress3());

        pForm.setAuthorizedForResale(account.isAuthorizedForResale());

        if (account.isAuthorizedForResale()) {
            pForm.setReSaleAccountErpNumber(bed.getErpNum());
            account.getResaleAccountErpNumber().setValue(bed.getErpNum());
        } else {
            if (account.getResaleAccountErpNumber() != null) {
                pForm.setReSaleAccountErpNumber(account.getResaleAccountErpNumber().getValue());
            } else {
                pForm.setReSaleAccountErpNumber(null);
            }
        }

        String ediShipToPrefix = account.getEdiShipToPrefix();
        if (ediShipToPrefix == null || ediShipToPrefix.trim().length() == 0) {
            ediShipToPrefix = "" + pAccountId;
        }
        pForm.setEdiShipToPrefix(ediShipToPrefix);

        if (account.getTargetMargin() == null) {
            pForm.setTargetMarginStr("0");
        } else {
            pForm.setTargetMarginStr(account.getTargetMargin().toString());
        }
        session.setAttribute("Account.id", pForm.getId());
        session.setAttribute("Account.name", pForm.getName());
        session.setAttribute("Store.id", pForm.getStoreId());
        session.setAttribute("Store.name", pForm.getStoreName());

        if (!Utility.isSet(account.getFreightChargeType())) {
            pForm.setFreightChargeType("PC");
        } else {
            pForm.setFreightChargeType(account.getFreightChargeType());
        }

        pForm.setPurchaseOrderAccountName(account.getPurchaseOrderAccountName());
        pForm.setBudgetTypeCd(account.getBudgetTypeCd());
        pForm.setDistrPoType(account.getDistrPOType());

        try {
            // Look for an order pipeline.
            initOrderPipeline(factory, pAccountId, pForm);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        String showSchedDelS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
        pForm.setMakeShipToBillTo(account.isMakeShipToBillTo());
        pForm.setShowScheduledDelivery(isTRUE(showSchedDelS));

        pForm.setRushOrderCharge(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE));
        pForm.setAutoOrderFactor(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR));
        pForm.setCartReminderInterval(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL));

        pForm.setInvPOSuffix(Utility.strNN(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX)));
        pForm.setInvLedgerSwitch(isOFF(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH)));
        pForm.setInvOGListUI(Utility.strNN(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI)));
        pForm.setInvMissingNotification(Utility.strNN(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION)));
        pForm.setInvCheckPlacedOrder(Utility.strNN(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER)));
        pForm.setConnectionCustomer(isON(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONNECTION_CUSTOMER)));

        pForm.setShowDistInventory(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY));

        String allowOrderConsolidationS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
        pForm.setAllowOrderConsolidation(isTRUE(allowOrderConsolidationS));

        String showDistSkuNumS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM);
        pForm.setShowDistSkuNum(isTRUE(showDistSkuNumS));

        String showDistDeliveryDateS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE);
        pForm.setShowDistDeliveryDate(isTRUE(showDistDeliveryDateS));

        String scheduleCutoffDaysS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
        pForm.setScheduleCutoffDays(Utility.strNN(scheduleCutoffDaysS));

        BusEntityParameterData rebatePersentBEPD = account.getAccountParameterActual("Rebate Persent");
        String rebatePersentS = "";
        String rebateEffDateS = "";
        if (rebatePersentBEPD != null) {
            rebatePersentS = rebatePersentBEPD.getValue();
            if (Utility.isSet(rebatePersentS)) {
                Date rebateEffDate = rebatePersentBEPD.getEffDate();
                rebateEffDateS = ClwI18nUtil.formatDateInp(request,rebateEffDate);
            }
        }

        pForm.setRebatePersent(rebatePersentS);
        pForm.setRebateEffDate(rebateEffDateS);

        ArrayList<String> orderManagerEmailV = account.getOrderManagerEmails();
        String orderManagerEmailS = "";
        if (orderManagerEmailV != null) {
            for (int ii = 0; ii < orderManagerEmailV.size(); ii++) {
                String eMail = orderManagerEmailV.get(ii);
                if (eMail != null) {
                    eMail = eMail.trim();
                    if (eMail.length() > 0) {
                        if (ii > 0)
                            orderManagerEmailS += ",";
                        orderManagerEmailS += eMail;
                    }
                }
            }
        }
        pForm.setOrderManagerEmails(orderManagerEmailS);

        String emailSub = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);
        if (emailSub != null) {
            pForm.setInvReminderEmailSub(emailSub);
        }

        String emailMsg = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);
        if (emailMsg != null) {
            pForm.setInvReminderEmailMsg(emailMsg);
        }

        String notifyOrderEmailGenerator = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR);
        if (notifyOrderEmailGenerator != null) {
            pForm.setNotifyOrderEmailGenerator(notifyOrderEmailGenerator);
        }

        String confirmOrderEmailGenerator = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR);
        if (confirmOrderEmailGenerator != null) {
            pForm.setConfirmOrderEmailGenerator(confirmOrderEmailGenerator);
        }

        String rejectOrderEmailGenerator = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR);
        if (rejectOrderEmailGenerator != null) {
            pForm.setRejectOrderEmailGenerator(rejectOrderEmailGenerator);
        }

        String pendingApprovEmailGenerator = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR);
        if (pendingApprovEmailGenerator != null) {
            pForm.setPendingApprovEmailGenerator(pendingApprovEmailGenerator);
        }
        
        String orderConfirmationEmailTemplate = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
        if (orderConfirmationEmailTemplate != null) {
            pForm.setOrderConfirmationEmailTemplate(orderConfirmationEmailTemplate);
        }
        String shippingNotificationEmailTemplate = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
        if (shippingNotificationEmailTemplate != null) {
            pForm.setShippingNotificationEmailTemplate(shippingNotificationEmailTemplate);
        }
        String pendingApprovalEmailTemplate = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
        if (pendingApprovalEmailTemplate != null) {
            pForm.setPendingApprovalEmailTemplate(pendingApprovalEmailTemplate);
        }
        String rejectedOrderEmailTemplate = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
        if (rejectedOrderEmailTemplate != null) {
            pForm.setRejectedOrderEmailTemplate(rejectedOrderEmailTemplate);
        }
        String modifiedOrderEmailTemplate = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);
        if (modifiedOrderEmailTemplate != null) {
            pForm.setModifiedOrderEmailTemplate(modifiedOrderEmailTemplate);
        }

        String showSPLS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL);
        pForm.setShowSPL(isTRUE(showSPLS));

        String addServiceFeeS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        pForm.setAddServiceFee(isTRUE(addServiceFeeS));

        String holdPOS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO);
        pForm.setHoldPO(isTRUE(holdPOS));

        String allowChangePasswordS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
        pForm.setAllowChangePassword(isTRUE(allowChangePasswordS));

        String accountFolder = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
        if (accountFolder != null) {
            pForm.setAccountFolder(accountFolder);
        }
        String accountFolderNew = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER_NEW);
        if (accountFolderNew != null) {
            pForm.setAccountFolderNew(accountFolderNew);
        }

        String modifyQtyBy855 = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADJUST_QTY_BY_855);
        pForm.setModifyQtyBy855(isTRUE(modifyQtyBy855));

        String createOrder855 = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_BY_855);
        pForm.setCreateOrderBy855(isTRUE(createOrder855));

        String createOrderItems855 = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_ITEMS_BY_855);
        pForm.setCreateOrderItemsBy855(isTRUE(createOrderItems855));

        String allowModernShopping = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING);
        pForm.setAllowModernShopping(isTRUE(allowModernShopping));

        String allowSiteLLC = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
        pForm.setAllowSiteLLC(isTRUE(allowSiteLLC));

        String showExpressOrder = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER);
        pForm.setShowExpressOrder(isTRUE(showExpressOrder));

        String allowOrderInvItems = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS);
        pForm.setAllowOrderInvItems(isTRUE(allowOrderInvItems));

        String allowReorder = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER);
        pForm.setAllowReorder(isTRUE(allowReorder));

        String usePhysicalInventory = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
        pForm.setUsePhysicalInventory(isTRUE(usePhysicalInventory));

        String showInvCartTotal = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL);
        pForm.setShowInvCartTotal(isTRUE(showInvCartTotal));

        String showMyShoppingLists = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
        pForm.setShowMyShoppingLists(isTRUE(showMyShoppingLists));

        pForm.setShopUIType(Utility.strNN(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE)));

        String allowSetWorkOrderPoNumber = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER);
        pForm.setAllowSetWorkOrderPoNumber(isTRUE(allowSetWorkOrderPoNumber));

        String workOrderPoNumberIsRequired = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED);
        pForm.setWorkOrderPoNumberIsRequired(isTRUE(workOrderPoNumberIsRequired));

        String allowBuyWorkOrderParts = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS);
        pForm.setAllowBuyWorkOrderParts(isTRUE(allowBuyWorkOrderParts));

        String userAssignedAssetNumber = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER);
        pForm.setUserAssignedAssetNumber(isTRUE(userAssignedAssetNumber));

        String contactInfTtype = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE);
        if (Utility.isSet(contactInfTtype)) {
            pForm.setContactInformationType(contactInfTtype);
        } else {
            pForm.setContactInformationType(null);
        }

        String faqLink = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK);
        pForm.setFaqLink(faqLink);

        String pdfOrderClass = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS);
        pForm.setPdfOrderClass(pdfOrderClass);

        String pdfOrderStatusClass = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS);
        pForm.setPdfOrderStatusClass(pdfOrderStatusClass);

        String allowCCS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT);
        pForm.setAllowCreditCard(isTRUE(allowCCS));

        pForm.setFirstUpdate(false);

        session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);

        return ae;
    }

    private static void reset(Admin2AccountMgrDetailForm pForm) throws Exception {
        pForm = new Admin2AccountMgrDetailForm();
        pForm.init();
    }

    private static ActionErrors checkUserAccessTo(HttpServletRequest request, int pAccountId) throws APIServiceAccessException, DataNotFoundException, RemoteException {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(pAccountId>0){
            if (appUser.isaAccountAdmin()) {
                IdVector accessibleAccounts = Utility.toIdVector(appUser.getAccounts());
                log.info("checkUserAccessTo => " + accessibleAccounts);
                if (!accessibleAccounts.contains(pAccountId)) {
                    String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.illegalAccessToAccount", new Object[]{pAccountId});
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            } else if (!appUser.isaSystemAdmin()) {

                APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
                Store storeEjb = factory.getStoreAPI();

                int storeId = storeEjb.getStoreIdByAccount(pAccountId);
                if (!appUser.getUserStoreAsIdVector().contains(storeId)) {
                    String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.illegalAccessToAccount", new Object[]{pAccountId});
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            }
        }

        return ae;
    }

    private static void initUiPage(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) {
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser.getUi() != null) {
            UiPageView uiPage = Utility.getUiPage(appUser.getUi().getUiPages(), RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL);
            pForm.setUiPage(new UiPageViewWrapper(uiPage));
        } else {
            pForm.setUiPage(null);
        }
    }

    public static ActionErrors init(HttpServletRequest request, Admin2AccountMgrForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = APIAccess.getAPIAccess();

        Group groupEjb = factory.getGroupAPI();

        if (pForm == null || !pForm.isInit(request)) {

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            log.info("init => user: " + appUser.getUser().getUserTypeCd());

            pForm = new Admin2AccountMgrForm();
            pForm.init(request);

            ae = checkRequest(pForm, request);
            if (ae.size() > 0) {
                return ae;
            }

            if (!appUser.isaAccountAdmin()) {

                GroupSearchCriteriaView grSearchCrVw = GroupSearchCriteriaView.createValue();
                grSearchCrVw.setGroupType(RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
                grSearchCrVw.setGroupName("");
                grSearchCrVw.setStoreId(appUser.getUserStore().getStoreId());

                GroupDataVector groups = groupEjb.getGroups(grSearchCrVw, Group.NAME_BEGINS_WITH, Group.ORDER_BY_NAME);

                pForm.setAccountGroups(groups);
            }

            session.setAttribute(ADMIN2_ACCOUNT_MGR_FORM, pForm);

        }

        log.info("init => END.");

        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = new Admin2AccountMgrDetailForm();
        pForm.init();

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        initDetailFormVectors(request);

        initUiPage(request, pForm);

        session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);

        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }


    private static ActionErrors checkRequest(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_ACCOUNT_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.systemError", errorMess));
            return ae;
        }

        if (pForm.getUiPage() == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.uiNotConfiguredForPage", new Object[]{RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL});
            ae.add("error", new ActionError("error.systemError", errorMess));
            return ae;
        }

        return ae;
    }

    private static ActionErrors checkRequest(Admin2AccountMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_ACCOUNT_MGR_FORM});
            ae.add("error", new ActionError("error.systemError", errorMess));
        }

        return ae;
    }

    public static void initDetailFormVectors(HttpServletRequest request) throws Exception {
        Admin2Tool.initFormVectors(request,
                Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD,
                Admin2Tool.FORM_VECTORS.COUNTRY_CD,
                Admin2Tool.FORM_VECTORS.ACCOUNT_TYPE_CD,
                Admin2Tool.FORM_VECTORS.CUSTOMER_SYSTEM_APPROVAL_CD,
                Admin2Tool.FORM_VECTORS.INVENTORY_OG_LIST_UI,
                Admin2Tool.FORM_VECTORS.ORDER_ITEM_DETAIL_ACTION_CD,
                Admin2Tool.FORM_VECTORS.BUDGET_ACCRUAL_TYPE_CD,
                Admin2Tool.FORM_VECTORS.GL_TRANSFORMATION_TYPE,
                Admin2Tool.FORM_VECTORS.TIME_ZONE_CD,
                Admin2Tool.FORM_VECTORS.SHOP_UI_TYPE,
                Admin2Tool.FORM_VECTORS.FREIGHT_CHARGE_CD,
                Admin2Tool.FORM_VECTORS.DIST_INVENTORY_DISPLAY,
                Admin2Tool.FORM_VECTORS.DISTR_PO_TYPE);
    }

    public static ActionErrors search(HttpServletRequest request, Admin2AccountMgrForm pForm) throws Exception {

        ActionErrors ae = checkRequest(pForm, request);
        if (ae.size() > 0) return ae;

        ae = checkAccountSearchAttr(pForm, request);
        if (ae.size() > 0) return ae;


        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = new APIAccess();
        Account accountEjb = factory.getAccountAPI();

        AccountSearchResultViewVector acntSrchRsltVctr;

        //Reset the session variables.
        pForm.setSearchResult(null);

        String fieldValue = pForm.getSearchField();
        String fieldSearchType = pForm.getSearchType();
        String searchGroupId = pForm.getSearchGroupId();
        boolean showInactive = pForm.getShowInactiveFl();

        String storeStr = null;
        if (!appUser.isaSystemAdmin()) {
            IdVector stores = appUser.getUserStoreAsIdVector();
            storeStr = Utility.toCommaSting(stores);
        }

        try {
            acntSrchRsltVctr = accountEjb.search(storeStr, fieldValue, fieldSearchType, searchGroupId, showInactive);
        } catch (Exception e) {
            return StringUtils.getUiErrorMess(e);
        }

        pForm.setSearchResult(acntSrchRsltVctr);

        return ae;
    }

    public static ActionErrors addAccount(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init(request, (Admin2AccountMgrDetailForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = (Admin2AccountMgrDetailForm) session.getAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM);
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        pForm.setAccountData(AccountData.createValue());

        pForm.setFirstUpdate(true);

        session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);

        return ae;

    }

    private static void initOrderPipeline(APIAccess pFactory, int pAccountId, Admin2AccountMgrDetailForm pForm) throws Exception {

        AccountOrderPipeline orderPipeline = new AccountOrderPipeline(pFactory, pAccountId);
        pForm.setOrderPipeline(orderPipeline);
        pForm.setMaxItemCubicSize(orderPipeline.getMaxItemCubicSize().toString());
        pForm.setMaxItemWeight(orderPipeline.getMaxItemWeight().toString());
        Account act = pFactory.getAccountAPI();
        OrderRoutingDescViewVector lOrderRouting = null;
        try {
            lOrderRouting = act.getAccountOrderRoutingCollection(pAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pForm.setAccountOrderRoutingList(lOrderRouting);
    }

    public static ActionErrors updateAccount(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = validate(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        int accountId = 0;
        if (!pForm.getId().equals("")) {
            accountId = Integer.parseInt(pForm.getId());
        }

        ae = checkUserAccessTo(request, accountId);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm.isClone()) {
            ae = checkUserAccessTo(request, pForm.getCloneAccoundId());
            if (ae.size() > 0) {
                return ae;
            }
        }

        Account accountEjb = factory.getAccountAPI();
        Store storeEjb = factory.getStoreAPI();

        // Get the current information for this Account.
        AccountData dd;
        BusEntityData bed;
        AddressData address;
        AddressData billingAddress;
        EmailData email;
        EmailData customerEmail;
        EmailData defaultEmail;
        PhoneData phone;
        PhoneData orderPhone;
        PhoneData fax;
        PhoneData orderFax;
        PhoneData poConfirmFax;
        PropertyData acctType;
        PropertyData orderMinimum;
        PropertyData creditLimit;
        PropertyData creditRating;
        PropertyData crcShop;
        PropertyData comments, orderGuideNote, skuTag;
        PropertyData reSaleAccountErpNumber;

        int storeid = 0;
        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
            storeid = appUser.getUserStore().getStoreId();
        } else {
            if (!pForm.getStoreId().equals("")) {
                storeid = Integer.parseInt(pForm.getStoreId());
            }
        }

        if (accountId > 0) {
            dd = accountEjb.getAccount(accountId, storeid);
            pForm.setAccountData(dd);
        } else {
            dd = AccountData.createValue();
        }

        boolean noAccountErpNumber = false;
        if (pForm.getAccountNumber() == null || pForm.getAccountNumber().length() == 0) {
            noAccountErpNumber = true;
        }

        dd.getRuntimeDisplayOrderItemActionTypes().clear();
        for (int i = 0; i < pForm.getRuntimeDisplayOrderItemActionTypes().length; i++) {
            if (Utility.isSet(pForm.getRuntimeDisplayOrderItemActionTypes()[i])) {
                dd.getRuntimeDisplayOrderItemActionTypes().add(pForm.getRuntimeDisplayOrderItemActionTypes()[i]);
            }
        }

        BigDecimal targetMargin;
        if (Utility.isSet(pForm.getTargetMarginStr())) {
            targetMargin = new BigDecimal(pForm.getTargetMarginStr());
        } else {
            targetMargin = null;
        }

        String orderManagerEmailS = pForm.getOrderManagerEmails();
        ArrayList<String> orderManagerEmailV = new ArrayList<String>();
        java.util.StringTokenizer st = new StringTokenizer(orderManagerEmailS, ",");
        while (st.hasMoreElements()) {
            String eMail = st.nextToken();
            eMail = eMail.trim();
            if (eMail.indexOf('@') < 0) {
                eMail += "@cleanwise.com";
            }
            orderManagerEmailV.add(eMail);
        }

        bed = dd.getBusEntity();
        address = dd.getPrimaryAddress();
        billingAddress = dd.getBillingAddress();
        phone = dd.getPrimaryPhone();
        orderPhone = dd.getOrderPhone();
        fax = dd.getPrimaryFax();
        orderFax = dd.getOrderFax();
        poConfirmFax = dd.getFaxBackConfirm();
        email = dd.getPrimaryEmail();
        customerEmail = dd.getCustomerServiceEmail();
        defaultEmail = dd.getDefaultEmail();
        acctType = dd.getAccountType();
        orderMinimum = dd.getOrderMinimum();
        reSaleAccountErpNumber = dd.getResaleAccountErpNumber();
        creditLimit = dd.getCreditLimit();
        creditRating = dd.getCreditRating();
        crcShop = dd.getCrcShop();
        comments = dd.getComments();
        orderGuideNote = dd.getOrderGuideNote();
        skuTag = dd.getSkuTag();

        bed.setWorkflowRoleCd(Constants.UNKNOWN);
        SessionTool stl = new SessionTool(request);
        String cuser = stl.getLoginName();

        // Now update with the data from the form.
        dd.setDataFieldProperties(pForm.getDataFieldProperties());
        dd.setAuthorizedForResale(isTrue(pForm.getAuthorizedForResale()));
        dd.setEdiShipToPrefix(pForm.getEdiShipToPrefix());
        dd.setTargetMargin(targetMargin);
        bed.setShortDesc(pForm.getName());
        bed.setLongDesc(pForm.getName());
        bed.setBusEntityStatusCd(pForm.getStatusCd());
        bed.setTimeZoneCd(pForm.getTimeZoneCd());
        bed.setErpNum(pForm.getAccountNumber());
        dd.setOrderManagerEmails(orderManagerEmailV);

        dd.setCustomerSystemApprovalCd(pForm.getCustomerSystemApprovalCd());
        dd.setCustomerRequestPoAllowed(isTrue(pForm.getCustomerRequestPoAllowed()));
        dd.setTaxableIndicator(isTrue(pForm.getTaxableIndicator()));

        bed.setLocaleCd(Constants.UNK);
        bed.setModBy(cuser);

        acctType.setValue(pForm.getTypeDesc());

        // In the property, save as a number rather than a currency
        BigDecimal minvalue = CurrencyFormat.parse(pForm.getOrderMinimum());
        orderMinimum.setValue(CurrencyFormat.formatAsNumber(minvalue));
        pForm.setOrderMinimum(CurrencyFormat.format(minvalue));

        BigDecimal credvalue = CurrencyFormat.parse(pForm.getCreditLimit());
        creditLimit.setValue(CurrencyFormat.formatAsNumber(credvalue));
        pForm.setCreditLimit(CurrencyFormat.format(credvalue));

        creditRating.setShortDesc("Credit Rating");
        creditRating.setValue(pForm.getCreditRating());

        crcShop.setShortDesc("Crc Shop");
        crcShop.setValue(String.valueOf(isTrue(pForm.getCrcShop())));

        comments.setShortDesc("Comments");
        comments.setValue(pForm.getComments());

        skuTag.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG);
        skuTag.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG);
        skuTag.setValue(pForm.getSkuTag());

        orderGuideNote.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE);
        orderGuideNote.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE);
        orderGuideNote.setValue(pForm.getOrderGuideNote());

        reSaleAccountErpNumber.setShortDesc("ReSaleAccountErpNumber");
        reSaleAccountErpNumber.setValue(pForm.getReSaleAccountErpNumber());

        phone.setShortDesc("Primary Phone");
        phone.setPhoneNum(pForm.getPhone());

        orderPhone.setShortDesc("Order Phone");
        orderPhone.setPhoneNum(pForm.getOrderPhone());

        fax.setShortDesc("Primary Fax");
        fax.setPhoneNum(pForm.getFax());

        orderFax.setShortDesc("Order Fax");
        orderFax.setPhoneNum(pForm.getOrderFax());

        poConfirmFax.setShortDesc("Fax Back Confirm");
        poConfirmFax.setPhoneNum(pForm.getFaxBackConfirm());

        email.setShortDesc(pForm.getName1() + " " + pForm.getName2());
        email.setEmailAddress(pForm.getEmailAddress());

        customerEmail.setShortDesc(RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE);
        customerEmail.setEmailAddress(pForm.getCustomerEmail());

        defaultEmail.setShortDesc(RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
        defaultEmail.setEmailAddress(pForm.getDefaultEmail());

        address.setName1(pForm.getName1());
        address.setName2(pForm.getName2());
        address.setAddress1(pForm.getStreetAddr1());
        address.setAddress2(pForm.getStreetAddr2());
        address.setAddress3(pForm.getStreetAddr3());
        address.setCity(pForm.getCity());
        address.setStateProvinceCd(pForm.getStateOrProv());
        address.setPostalCode(pForm.getPostalCode());
        address.setCountryCd(pForm.getCountry());

        billingAddress.setAddress1(pForm.getBillingAddress1());
        billingAddress.setAddress2(pForm.getBillingAddress2());
        billingAddress.setAddress3(pForm.getBillingAddress3());
        billingAddress.setCity(pForm.getBillingCity());
        billingAddress.setStateProvinceCd(pForm.getBillingState());
        billingAddress.setPostalCode(pForm.getBillingPostalCode());
        billingAddress.setCountryCd(pForm.getBillingCountry());

        String emailSub = pForm.getInvReminderEmailSub();
        String emailMsg = pForm.getInvReminderEmailMsg();
        String notifyOrderEmailGenerator = pForm.getNotifyOrderEmailGenerator();
        String confirmOrderEmailGenerator = pForm.getConfirmOrderEmailGenerator();
        String rejectOrderEmailGenerator = pForm.getRejectOrderEmailGenerator();
        String pendingApprovEmailGenerator = pForm.getPendingApprovEmailGenerator();
        String orderConfirmationEmailTemplate = pForm.getOrderConfirmationEmailTemplate();
        String shippingNotificationEmailTemplate = pForm.getShippingNotificationEmailTemplate();
        String pendingApprovalEmailTemplate = pForm.getPendingApprovalEmailTemplate();
        String rejectedOrderEmailTemplate = pForm.getRejectedOrderEmailTemplate();
        String modifiedOrderEmailTemplate = pForm.getModifiedOrderEmailTemplate();

        // inventory properties
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH,
                isTrue(pForm.getInvLedgerSwitch()) ? Constants.OFF : Constants.ON,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX,
                pForm.getInvPOSuffix(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI,
                pForm.getInvOGListUI(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION,
                pForm.getInvMissingNotification(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER,
                pForm.getInvCheckPlacedOrder(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONNECTION_CUSTOMER,
                isTrue(pForm.getConnectionCustomer()) ? Constants.ON : Constants.OFF,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL,
                pForm.getCartReminderInterval(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY,
                String.valueOf(isTrue(pForm.getShowScheduledDelivery())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION,
                String.valueOf(isTrue(pForm.getAllowOrderConsolidation())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM,
                String.valueOf(isTrue(pForm.getShowDistSkuNum())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE,
                String.valueOf(isTrue(pForm.getShowDistDeliveryDate())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE,
                pForm.getRushOrderCharge(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR,
                Utility.strNN(pForm.getAutoOrderFactor()).trim(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY,
                pForm.getShowDistInventory(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL,
                String.valueOf(isTrue(pForm.getShowSPL())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE,
                String.valueOf(isTrue(pForm.getAddServiceFee())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO,
                String.valueOf(isTrue(pForm.getHoldPO())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD,
                String.valueOf(isTrue(pForm.getAllowChangePassword())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER,
                pForm.getAccountFolder(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER_NEW,
                pForm.getAccountFolderNew(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADJUST_QTY_BY_855,
                String.valueOf(isTrue(pForm.getModifyQtyBy855())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_BY_855,
                String.valueOf(isTrue(pForm.getCreateOrderBy855())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_ITEMS_BY_855,
                String.valueOf(isTrue(pForm.getCreateOrderItemsBy855())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING,
                String.valueOf(isTrue(pForm.getAllowModernShopping())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC,
                String.valueOf(isTrue(pForm.getAllowSiteLLC())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER,
                String.valueOf(isTrue(pForm.getShowExpressOrder())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS,
                String.valueOf(isTrue(pForm.getAllowOrderInvItems())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER,
                String.valueOf(isTrue(pForm.getAllowReorder())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY,
                String.valueOf(isTrue(pForm.getUsePhysicalInventory())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL,
                String.valueOf(isTrue(pForm.getShowInvCartTotal())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS,
                String.valueOf(isTrue(pForm.getShowMyShoppingLists())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER,
                String.valueOf(isTrue(pForm.getAllowSetWorkOrderPoNumber())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER,
                String.valueOf(isTrue(pForm.getUserAssignedAssetNumber())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE,
                pForm.getContactInformationType(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED,
                String.valueOf(isTrue(pForm.getWorkOrderPoNumberIsRequired())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE,
                pForm.getShopUIType(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK,
                pForm.getFaqLink(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT,
                emailSub,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG,
                emailMsg,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR,
                notifyOrderEmailGenerator,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR,
                pendingApprovEmailGenerator,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR,
                rejectOrderEmailGenerator,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR,
                confirmOrderEmailGenerator,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE,
                orderConfirmationEmailTemplate,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE,
                shippingNotificationEmailTemplate,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE,
                pendingApprovalEmailTemplate,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE,
                rejectedOrderEmailTemplate,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE,
                modifiedOrderEmailTemplate,
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD,
                pForm.getContactUsCCEmail(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS,
                pForm.getPdfOrderClass(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS,
                pForm.getPdfOrderStatusClass(),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT,
                String.valueOf(isTrue(pForm.getAllowCreditCard())),
                cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS,
                String.valueOf(isTrue(pForm.getAllowBuyWorkOrderParts())),
                cuser);

        String scheduleCutoffDaysS = pForm.getScheduleCutoffDays();
        if (Utility.isSet(scheduleCutoffDaysS)) {

            dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS,
                    scheduleCutoffDaysS,
                    cuser);
        } else {
            dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS,
                    "0",
                    cuser);
        }

        String rebatePersentS = pForm.getRebatePersent();
        if (Utility.isSet(rebatePersentS)) {
            String rebateEffDateS = pForm.getRebateEffDate();
            Date rebateEffDate = ClwI18nUtil.parseDateInp(request, rebateEffDateS);
            BusEntityParameterData rebatePersentBEPD = BusEntityParameterData.createValue();
            rebatePersentBEPD.setBusEntityId(bed.getBusEntityId());
            rebatePersentBEPD.setName("Rebate Persent");
            rebatePersentBEPD.setValue(rebatePersentS);
            rebatePersentBEPD.setEffDate(rebateEffDate);
            rebatePersentBEPD.setAddBy(cuser);
            rebatePersentBEPD.setModBy(cuser);
            dd.setAccountParameter(rebatePersentBEPD);
        } else {
            BusEntityParameterData rebatePersentBEPD = dd.getAccountParameterActual("Rebate Persent");
            if (rebatePersentBEPD != null) {
                dd.removeAccountParameter(rebatePersentBEPD.getBusEntityParameterId());
                rebatePersentBEPD = dd.getAccountParameterActual("Rebate Persent");
                rebatePersentS = "";
                String rebateEffDateS = "";
                if (rebatePersentBEPD != null) {
                    rebatePersentS = rebatePersentBEPD.getValue();
                    if (Utility.isSet(rebatePersentS)) {
                        Date rebateEffDate = rebatePersentBEPD.getEffDate();
                        rebateEffDateS = ClwI18nUtil.formatDateInp(request,rebateEffDate);
                    }
                }
                pForm.setRebatePersent(rebatePersentS);
                pForm.setRebateEffDate(rebateEffDateS);
            }
        }

        dd.setMakeShipToBillTo(isTrue(pForm.getMakeShipToBillTo()));

        PropertyService propertyServiceBean = factory.getPropertyServiceAPI();

        dd.setFreightChargeType(pForm.getFreightChargeType());
        dd.setPurchaseOrderAccountName(pForm.getPurchaseOrderAccountName());
        dd.setBudgetTypeCd(pForm.getBudgetTypeCd());
        dd.setGlTransformationType(pForm.getGlTransformationType());
        dd.setDistributorAccountRefNum(pForm.getDistributorAccountRefNum());
        dd.setDistrPOType(pForm.getDistrPoType());

        if (ae.size() > 0) {
            return ae;
        }

        bed.setModBy(cuser);
        address.setModBy(cuser);
        billingAddress.setModBy(cuser);
        email.setModBy(cuser);
        customerEmail.setModBy(cuser);
        orderPhone.setModBy(cuser);
        orderFax.setModBy(cuser);
        poConfirmFax.setModBy(cuser);
        acctType.setModBy(cuser);
        reSaleAccountErpNumber.setModBy(cuser);
        phone.setModBy(cuser);
        fax.setModBy(cuser);
        skuTag.setModBy(cuser);
        orderGuideNote.setModBy(cuser);
        if (customerEmail.getEmailId() == 0) {
            customerEmail.setAddBy(cuser);
        }

        if (accountId == 0) {

            // get store - both to verify that id is that of store and get
            // it's name
            try {
                StoreData sd = storeEjb.getStore(storeid);
                pForm.setStoreName(sd.getBusEntity().getShortDesc());
            } catch (DataNotFoundException de) {
                // the given store id is apparently not a store
                pForm.setStoreName("");
                ae.add("Store.id", new ActionError("account.bad.store"));
                return ae;
            } catch (Exception e) {
                throw e;
            }

            bed.setAddBy(cuser);
            address.setAddBy(cuser);
            billingAddress.setAddBy(cuser);
            email.setAddBy(cuser);
            customerEmail.setAddBy(cuser);
            phone.setAddBy(cuser);
            fax.setAddBy(cuser);
            acctType.setAddBy(cuser);
            reSaleAccountErpNumber.setAddBy(cuser);
            skuTag.setAddBy(cuser);
            orderGuideNote.setAddBy(cuser);

            dd = accountEjb.addAccount(dd, storeid);
            pForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));
            pForm.setDataFieldProperties(dd.getDataFieldProperties());
            session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);


            if (pForm.isFirstUpdate() && pForm.isClone()) {
                try {
                    FiscalCalenderViewVector fcDV = cloneFiscalCalendars(accountEjb,
                            pForm.getCloneAccoundId(),
                            dd.getBusEntity().getBusEntityId());
                    // Setting clone Objects in AccountData
                    dd.setFiscalCalenders(fcDV);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {

            // Now update this Account
            accountEjb.updateAccount(dd);

            pForm.setDataFieldProperties(dd.getDataFieldProperties());
            session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);
        }

        if (pForm.isFirstUpdate()) {

            boolean isUpdate = false;
            // add if no AccountErpNumber
            if (noAccountErpNumber) {
                dd.getBusEntity().setErpNum("#" + dd.getBusEntity().getBusEntityId());
                isUpdate = true;
            }

            // add if no EdiShipToPrefix
            if ((dd.getEdiShipToPrefix() == null || dd.getEdiShipToPrefix().trim().length() == 0)) {
                isUpdate = true;
            }

            pForm.setFirstUpdate(false);
            if (isUpdate) {
                accountEjb.updateAccount(dd);
            }

            if (pForm.isClone()) {
                pForm.setClone(false);
            }
        }

        Admin2HomeMgrLogic.reloadUserHome(request);

        return getDetail(request, pForm);
    }

    private static ActionErrors validate(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();

        boolean noAccountErpNumber = false;

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        Country countryEjb = factory.getCountryAPI();
        int accountId = 0;
        if (!pForm.getId().equals("")) {
            accountId = Integer.parseInt(pForm.getId());
        }

        if (appUser.isaAccountAdmin() && accountId == 0) {
            String message = ClwI18nUtil.getMessage(request, "admin2.errors.userCantCreateAccount", new Object[]{appUser.getUser().getUserTypeCd()});
            lUpdateErrors.add("rightsToSave", new ActionError("error.simpleGenericError", message));
            return lUpdateErrors;
        }

        // Verify the form information submitted.
        if (pForm.getControlAccountData().equals("YES") && pForm.getAccountData() == null) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.pageExpired", null);
        	lUpdateErrors.add("error1", new ActionError("error.simpleGenericError", message));
            recoveryForm(request, pForm);
            return lUpdateErrors;
        }

        if (pForm.getControlAccountData().equals("NO") && !pForm.isFirstUpdate()) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.pageExpired", null);
        	lUpdateErrors.add("error2", new ActionError("error.simpleGenericError", message));
            recoveryForm(request, pForm);
            return lUpdateErrors;
        }

        if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.accountId", null);
        	if (pForm.getStoreId() == null || pForm.getStoreId().length() == 0) {
                lUpdateErrors.add("storeId", new ActionError("variable.empty.error", message));
            }
        }

        if (pForm.getName() == null || pForm.getName().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.accountName", null);
        	lUpdateErrors.add("name", new ActionError("variable.empty.error", message));
        }

        if (pForm.getTypeDesc() == null || pForm.getTypeDesc().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.accountType", null);
            lUpdateErrors.add("typeDesc", new ActionError("variable.empty.error", message));
        }

        if (pForm.getStatusCd() == null || pForm.getStatusCd().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.accountStatus", null);
        	lUpdateErrors.add("statusCd", new ActionError("variable.empty.error", message));
        }

        if (pForm.getBudgetTypeCd() == null || pForm.getBudgetTypeCd().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.budgetType", null);
        	lUpdateErrors.add("budgetTypeCd", new ActionError("variable.empty.error", message));
        }

        if (pForm.getAccountNumber() == null || pForm.getAccountNumber().length() == 0) {
            noAccountErpNumber = true;
        }

        if (!pForm.isFirstUpdate() && noAccountErpNumber) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.accountNumber", null);
        	lUpdateErrors.add("accountNumber", new ActionError("variable.empty.error", message));
            pForm.setAccountNumber("#" + pForm.getId());
        }

        if (pForm.getName1() == null || pForm.getName1().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.name1" , null);
        	lUpdateErrors.add("name1", new ActionError("variable.empty.error", message));
        }

        if (pForm.getName2() == null || pForm.getName2().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.name2" , null);
        	lUpdateErrors.add("name2", new ActionError("variable.empty.error", message));
        }

        if (pForm.getStreetAddr1() == null || pForm.getStreetAddr1().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.streetAddr1" , null);
            lUpdateErrors.add("streetAddr1", new ActionError("variable.empty.error", message));
        }

        if (pForm.getCity() == null || pForm.getCity().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.city" , null);
            lUpdateErrors.add("city", new ActionError("variable.empty.error", message));
        }

        if (pForm.getCountry() == null || pForm.getCountry().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.country" , null);
        	lUpdateErrors.add("country", new ActionError("variable.empty.error", message));
        }
        // check country and province
        CountryData country = getCountry(session, pForm.getCountry());
        CountryPropertyData countryProp = countryEjb.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);

        boolean isStateProvinceRequired = countryProp != null && Utility.isTrue(countryProp.getValue());
        if (isStateProvinceRequired && !Utility.isSet(pForm.getStateOrProv())) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.stateOrProv" , null);
        	lUpdateErrors.add("stateOrProv", new ActionError("variable.empty.error", message));
        } else if (!isStateProvinceRequired && Utility.isSet(pForm.getStateOrProv())) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.stateOrProv", null);
        	lUpdateErrors.add("stateOrProv", new ActionError("variable.must.be.empty.error", "State/Province"));
        }

        if (pForm.getPostalCode() == null || pForm.getPostalCode().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.zip" , null);
        	lUpdateErrors.add("postalCode", new ActionError("variable.empty.error", message));
        }

        if (pForm.getPhone() == null || pForm.getPhone().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.phone", null);
        	lUpdateErrors.add("phone", new ActionError("variable.empty.error", message));
        }

        if (pForm.getOrderPhone() == null || pForm.getOrderPhone().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.orderPhone", null);
        	lUpdateErrors.add("orderPhone", new ActionError("variable.empty.error", message));
        }

        if (pForm.getFax() == null || pForm.getFax().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.fax", null);
        	lUpdateErrors.add("fax", new ActionError("variable.empty.error", message));
        }

        if (pForm.getOrderFax() == null || pForm.getOrderFax().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.orderFax", null);
        	lUpdateErrors.add("orderFax", new ActionError("variable.empty.error", message));
        }

        if (pForm.getEmailAddress() == null || pForm.getEmailAddress().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.emailAddress", null);
        	lUpdateErrors.add("emailAddress", new ActionError("variable.empty.error", message));
        }

        if (pForm.getOrderMinimum() != null && pForm.getOrderMinimum().length() != 0) {
            try {
                CurrencyFormat.parse(pForm.getOrderMinimum());
            } catch (ParseException pe) {
            	String message = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.orderMinimum", null);
            	lUpdateErrors.add("orderMinimum", new ActionError("error.invalidAmount", message));
            }
        }

        if (pForm.getCreditLimit() != null && pForm.getCreditLimit().length() != 0) {
            try {
                CurrencyFormat.parse(pForm.getCreditLimit());
            } catch (ParseException pe) {
            	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.creditLimit", null);
            	lUpdateErrors.add("CreditLimit", new ActionError("error.invalidAmount", message));
            }
        }

        if (pForm.getBillingAddress1() == null || pForm.getBillingAddress1().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.billingAddress1", null);
        	lUpdateErrors.add("billingAddress1", new ActionError("variable.empty.error", message));
        }

        if (pForm.getBillingCity() == null || pForm.getBillingCity().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.billingCity", null);
        	lUpdateErrors.add("billingCity", new ActionError("variable.empty.error", message));
        }

        if (pForm.getBillingCountry() == null || pForm.getBillingCountry().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.billingCountry", null);
        	lUpdateErrors.add("billingCountry", new ActionError("variable.empty.error", message));
        }

        // check Billing State/Province
        country = getCountry(session, pForm.getBillingCountry());
        countryProp = countryEjb.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);

        isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");
        if (isStateProvinceRequired && !Utility.isSet(pForm.getBillingState()) ) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.billingState", null);
        	lUpdateErrors.add("billingState", new ActionError("variable.empty.error", message));
        } else
        if (!isStateProvinceRequired && Utility.isSet(pForm.getBillingState())) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.billingState", null);
        	lUpdateErrors.add("billingState", new ActionError("variable.must.be.empty.error", message));
        }

        if (pForm.getBillingPostalCode() == null || pForm.getBillingPostalCode().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.billingPostalCode", null);
        	lUpdateErrors.add("billingPostalCode", new ActionError("variable.empty.error", message));
        }

        try {
            if (Utility.isSet(pForm.getTargetMarginStr())) {
                new BigDecimal(pForm.getTargetMarginStr());
            }
        } catch (Exception e) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.targetMarginStr", null);
        	lUpdateErrors.add("targetMarginStr", new ActionError("error.invalidAmount", message));
        }

        if (pForm.getAutoOrderFactor() == null || pForm.getAutoOrderFactor().trim().length() == 0) {
        	String message = ClwI18nUtil.getMessage(request,"admin2.account.detail.label.autoOrderFactor", null);
        	lUpdateErrors.add("autoOrderFactor", new ActionError("variable.empty.error", message));
        }

        String autoOrderFactorS = Utility.strNN(pForm.getAutoOrderFactor()).trim();

        double autoOrderFactor = 0;
        if (autoOrderFactorS.length() > 0) {

            try {
                autoOrderFactor = Double.parseDouble(autoOrderFactorS);
            } catch (Exception exc) {
                log.info(exc.getMessage());
            }

            if (autoOrderFactor <= 0) {
            	String message = ClwI18nUtil.getMessage(request,"admin2.account.errors.invalidOrderFactor", null);
            	lUpdateErrors.add("error1", new ActionError("error.simpleGenericError", message + autoOrderFactorS));
            }

            if (autoOrderFactor < 0 || autoOrderFactor > 1) {
            	String message = ClwI18nUtil.getMessage(request,"admin2.account.errors.orderFactorMustBeBetween", null);
            	lUpdateErrors.add("autoOrderFactorError", new ActionError("error.simpleGenericError", message + autoOrderFactorS));
            }
        }

        if (Utility.isSet(pForm.getCartReminderInterval())) {
            try {
                int val = Integer.parseInt(pForm.getCartReminderInterval());
                if (val <= 0) {
                	String message = ClwI18nUtil.getMessage(request,"admin2.account.errors.cartReminderInterval", null);
                	lUpdateErrors.add("cartReminderInterval", new ActionError("error.simpleGenericError", message));
                }
            } catch (NumberFormatException e) {
            	String message = ClwI18nUtil.getMessage(request,"admin2.account.errors.wrongCartRemiderInterval", null);
            	lUpdateErrors.add("cartReminderInterval", new ActionError("error.simpleGenericError", message));
            }
        }

        if (Utility.isSet(pForm.getInvMissingNotification())) {
            try {
                Integer.parseInt(pForm.getInvMissingNotification());
            } catch (NumberFormatException e) {
            	String message = ClwI18nUtil.getMessage(request,"admin2.account.errors.invMissingNotification", null);
            	lUpdateErrors.add("invMissingNotification", new ActionError("error.simpleGenericError", message));
            }
        }

        if (Utility.isSet(pForm.getInvCheckPlacedOrder())) {
            try {
                Integer.parseInt(pForm.getInvCheckPlacedOrder());
            } catch (NumberFormatException e) {
            	String message = ClwI18nUtil.getMessage(request,"admin2.account.errors.doNotPlaceInvOrder", null);
            	lUpdateErrors.add("doNotPlaceInvOrder", new ActionError("error.simpleGenericError", message));
            }
        }

        String scheduleCutoffDaysS = pForm.getScheduleCutoffDays();
        if (Utility.isSet(scheduleCutoffDaysS)) {
            try {
                int scheduleCutoffDays = Integer.parseInt(scheduleCutoffDaysS);
                if (scheduleCutoffDays < 0) {
                    String errorMess = "Field Schedule Cutoff Days. Invalid value ";
                    lUpdateErrors.add("CutoffDays", new ActionError("error.simpleGenericError", errorMess));
                }
            } catch (Exception exc) {
                String errorMess = "Field Schedule Cutoff Days. Invalid value ";
                lUpdateErrors.add("CutoffDays", new ActionError("error.simpleGenericError", errorMess));
            }
        }

        String rebatePersentS = pForm.getRebatePersent();
        if (Utility.isSet(rebatePersentS)) {
            try {
                new BigDecimal(rebatePersentS);
                String rebateEffDateS = pForm.getRebateEffDate();
                if (!Utility.isSet(rebateEffDateS)) {
                    String errorMess = "Field Rebate Eff Date can't be empty when Rebate Persent is set";
                    lUpdateErrors.add("RebatePersent", new ActionError("error.simpleGenericError", errorMess));
                } else {
                    try {
                        ClwI18nUtil.parseDateInp(request, rebateEffDateS);
                    } catch (Exception exc) {
                        String errorMess = "Field Rebate Eff Date. Invalid value ";
                        lUpdateErrors.add("RebatePersent", new ActionError("error.simpleGenericError", errorMess));
                    }
                }
            } catch (Exception exc) {
                String errorMess = "Field Rebate Persent. Invalid value ";
                lUpdateErrors.add("RebatePersent", new ActionError("error.simpleGenericError", errorMess));
            }
        }

        return lUpdateErrors;
    }

    private static void recoveryForm(HttpServletRequest request, Admin2AccountMgrDetailForm sForm) throws Exception {

        if (!sForm.getId().trim().equals("") && sForm.getIntId() > 0) {
            if (new Integer(sForm.getId()) == sForm.getIntId()) {
                getDetail(request, sForm);
            }
        } else {
            if (sForm.getCloneAccoundId() > 0) {
                sForm.setId(sForm.getCloneAccoundId());
                sForm.setId(String.valueOf(sForm.getCloneAccoundId()));
                getDetail(request, sForm);
                cloneAccountData(request, sForm);
            } else {
                addAccount(request, sForm);
            }
        }
    }

    public static ActionErrors cloneAccountData(HttpServletRequest request, Admin2AccountMgrDetailForm pForm) throws Exception {


        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        AccountData ad = pForm.getAccountData();

        if (ad != null && ad.getAccountId() > 0) {

            BusEntityData bed = ad.getBusEntity();
            PropertyData acctType = ad.getAccountType();
            PropertyData orderMinimum = ad.getOrderMinimum();
            PropertyData creditLimit = ad.getCreditLimit();
            PropertyData creditRating = ad.getCreditRating();
            AddressData address = ad.getPrimaryAddress();
            PhoneData phone = ad.getPrimaryPhone();
            PhoneData fax = ad.getPrimaryFax();
            PhoneData poConfirmFax = ad.getFaxBackConfirm();
            EmailData email = ad.getPrimaryEmail();
            EmailData customerEmail = ad.getCustomerServiceEmail();
            EmailData defaultEmail = ad.getDefaultEmail();
            AddressData billingAddress = ad.getBillingAddress();
            PhoneData orderPhone = ad.getOrderPhone();
            PhoneData orderFax = ad.getOrderFax();
            PropertyData comments = ad.getComments();
            PropertyData orderGuideNote = ad.getOrderGuideNote();
            PropertyData skuTag = ad.getSkuTag();
            PropertyData crcShop = ad.getCrcShop();
            BusEntityParameterData rebatePersentBEPD = ad.getAccountParameterActual("Rebate Persent");
            int cloneAccountId = ad.getAccountId();
            String cloneStatusCd = bed.getBusEntityStatusCd();
            String cloneTimeZoneCd = bed.getTimeZoneCd();
            String cloneTypeCd = bed.getBusEntityTypeCd();
            FiscalCalenderViewVector cloneFiscalCalendar = ad.getFiscalCalenders();
            String cloneName = "Clone of >>> " + bed.getShortDesc();
            String cloneAccountType = acctType.getValue(); //

            // String cloneAccountNumber=bed.getErpNum();
            String cloneOrderMinimum = orderMinimum.getValue();
            BigDecimal minvalue;
            try {
                minvalue = CurrencyFormat.parse(cloneOrderMinimum);
            } catch (ParseException pe) {
                // this should only happen if bad value in db
                minvalue = new BigDecimal(0);
            }
            cloneOrderMinimum = CurrencyFormat.format(minvalue);

            String cloneCreditLimit = creditLimit.getValue();
            BigDecimal creditvalue;
            try {
                creditvalue = CurrencyFormat.parse(cloneCreditLimit);
            } catch (ParseException pe) {
                // this should only happen if bad value in db
                creditvalue = new BigDecimal(0);
            }
            cloneCreditLimit = CurrencyFormat.format(creditvalue);

            // /////////////////////////////////////////////////////
            String cloneCreditRating = creditRating.getValue();
            // ////////////////////////////////////////////////////
            String cloneBudgetTypeCd = ad.getBudgetTypeCd();
            String cloneDistrPOType = ad.getDistrPOType();
            // ////////////////////////////////////////////////////
            List cloneDataFieldProperties = ad.getDataFieldProperties();
            // ////////////////////////////////////////////////////
            String cloneWeightThreshold = null;
            String cloneContractThreshold = null;
            String clonePriceThreshold = null;
            if (!ClwCustomizer.getClwSwitch()) {
                cloneWeightThreshold = pForm.getWeightThreshold();
                cloneContractThreshold = pForm.getContractThreshold();
                clonePriceThreshold = pForm.getPriceThreshold();
            }

            // /////////////////Primary Contact/////////////////////

            String cloneName1 = address.getName1();
            String cloneName2 = address.getName2();
            String clonePhone = phone.getPhoneNum();
            String cloneFax = fax.getPhoneNum();
            String cloneConfirmFax = poConfirmFax.getPhoneNum();
            String cloneStreetAddress1 = address.getAddress1();
            String cloneStreetAddress2 = address.getAddress2();
            String cloneStreetAddress3 = address.getAddress3();
            String cloneEmailAddress = email.getEmailAddress();
            String cloneCustomerEmail = customerEmail.getEmailAddress();
            String cloneDefaultEmail = defaultEmail.getEmailAddress();
            String cloneCountry = address.getCountryCd();
            String cloneStateOrProvince = address.getStateProvinceCd();
            String cloneCity = address.getCity();
            String clonePostalCode = address.getPostalCode();

            // ////////////////////Billing Address//////////////////////

            String clonebilStreetAddress1 = billingAddress.getAddress1();
            String clonebilCountry = billingAddress.getCountryCd();
            String clonebilStreetAddress2 = billingAddress.getAddress2();
            String clonebilStateOrProvince = billingAddress.getStateProvinceCd();
            String clonebilBillingAddress3 = billingAddress.getAddress3();
            String clonebilZipOrPostalCode = billingAddress.getPostalCode();
            String clonebilCity = billingAddress.getCity();
            String clonePurchaseOrderAccountName = ad.getPurchaseOrderAccountName();

            // //////////////Order Contact Information////////////////////////
            String cloneOrderPhone = orderPhone.getPhoneNum();
            String cloneOrderFax = orderFax.getPhoneNum();
            String cloneOrderGuideComments = comments.getValue();
            String cloneOrderGuideNote = "";
            if (null != orderGuideNote) {
                cloneOrderGuideNote = orderGuideNote.getValue();
            }

            // ////////////////////////////////////////////////////////////////
            String cloneSkuTag = "";
            if (skuTag != null) {
                cloneSkuTag = skuTag.getValue();
            }

            // ///////////////////////////////////////////////////////////////////
            String[] cloneRuntimeDisplayOrderItemActionTypes = (String[]) ad.getRuntimeDisplayOrderItemActionTypes().toArray(new String[0]);
            // //////////////////////////////////////////////////////////////////

            boolean cloneTaxableIndicator = ad.isTaxableIndicator();

            // //////////////////////////////////////////////////////////////////
            boolean cloneCrcShop = false;
            String crcValue = crcShop.getValue();
            if (crcValue != null && crcValue.length() > 0
                    && "T".equalsIgnoreCase(crcShop.getValue().substring(0, 1))) {
                cloneCrcShop = true;
            }
            // //////////////////////////////////////////////////////////////////

            String cloneFreightChargeType = "";
            if (!Utility.isSet(ad.getFreightChargeType())) {
                cloneFreightChargeType = "PC";
            } else {
                cloneFreightChargeType = ad.getFreightChargeType();
            }

            // //////////////////////////////////////////////////////////////////
            boolean cloneMakeShipToBillTo = ad.isMakeShipToBillTo();

            // //////////////////////////////////////////////////////////////////
            String cloneContactUsCCEmail = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD);
            String cloneOrderManagerEmailS = "";
            String cloneEmailSub = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);
            String cloneEmailMsg = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);
            String cloneNotifyOrderEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR);
            String clonePendingApprovEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR);
            String cloneRejectOrderEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR);
            String cloneConfirmOrderEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR);
            String cloneOrderConfirmationEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
            String cloneShippingNotificationEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
            String clonePendingApprovalEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
            String cloneRejectedOrderEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
            String cloneModifiedOrderEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);

            ArrayList orderManagerEmailV = ad.getOrderManagerEmails();
            String orderManagerEmailS = "";
            if (orderManagerEmailV != null) {
                for (int ii = 0; ii < orderManagerEmailV.size(); ii++) {
                    String eMail = (String) orderManagerEmailV.get(ii);
                    if (eMail != null) {
                        eMail = eMail.trim();
                        if (eMail.length() > 0) {
                            if (ii > 0)
                                orderManagerEmailS += ",";
                            orderManagerEmailS += eMail;
                        }
                    }
                }
            }
            cloneOrderManagerEmailS = orderManagerEmailS;
            boolean cloneShowSchedDel = false;
            String showSchedDelS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
            if (showSchedDelS != null && showSchedDelS.length() > 0
                    && "T".equalsIgnoreCase(showSchedDelS.substring(0, 1))) {
                cloneShowSchedDel = true;
            }


            boolean cloneConnectionCustomer = Utility.isOn(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONNECTION_CUSTOMER), false);

            boolean cloneLedgerSwitch = Utility.isOff(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH), false);
            String clonePoSuffix = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX));
            String cloneInvOGListUI = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI));
            String cloneInvMissingNotify = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION));
            String cloneCheckPlacedOrder = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER));
            String cloneShopUIType = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE));
            String clonePdfOrderClass = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS));
            String clonePdfOrderStatusClass = Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS));

            boolean cloneCustomerRequestPoAllowed = ad.isCustomerRequestPoAllowed();

            String cloneAutoOrderFactor = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR);
            String cloneCartReminderInt = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL);
            String cloneShowDistInventory = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY);

            boolean cloneAuthorizedForResale = ad.isAuthorizedForResale();

            // String cloneReSaleAccountErpNumber =
            // ad.getResaleAccountErpNumber().getValue();
            boolean cloneAllowOrderConsolidationt = false;
            String allowOrderConsolidationS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
            if (allowOrderConsolidationS != null
                    && allowOrderConsolidationS.length() > 0
                    && "T".equalsIgnoreCase(allowOrderConsolidationS.substring(
                    0, 1))) {
                cloneAllowOrderConsolidationt = true;
            }

            // /////////////////////////////////////////////////////////////////
            String cloneScheduleCutoffDays = "";
            String scheduleCutoffDaysS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
            if (scheduleCutoffDaysS == null) {
                scheduleCutoffDaysS = "";
            }
            cloneScheduleCutoffDays = scheduleCutoffDaysS;

            // ///////////////////////////////////////////////////////////////////
            boolean cloneShowDistSkuNum = false;
            String showDistSkuNumS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM);
            if (showDistSkuNumS != null && showDistSkuNumS.length() > 0
                    && "T".equalsIgnoreCase(showDistSkuNumS.substring(0, 1))) {
                cloneShowDistSkuNum = true;
            }

            String showDistDeliveryDateS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE);
            boolean cloneShowDistDeliveryDate = isTRUE(showDistDeliveryDateS);

            // /////////////////////////////////////////////////////////////////////
            String cloneRebatePersentS = "";
            String cloneRebateEffDateS = "";
            if (rebatePersentBEPD != null) {
                cloneRebatePersentS = rebatePersentBEPD.getValue();
                if (Utility.isSet(cloneRebatePersentS)) {
                    Date rebateEffDate = rebatePersentBEPD.getEffDate();
                    cloneRebateEffDateS = ClwI18nUtil.formatDateInp(request, rebateEffDate);
                }
            }

            // ////////////////////////////////////////////////////////
            String cloneTargetMarginStr = "0";
            if (ad.getTargetMargin() != null) {
                cloneTargetMarginStr = ad.getTargetMargin().toString();
            }
            ///////////////////////////////////////////////////////////
            String cloneCustomerSystemApprovalCd = ad.getCustomerSystemApprovalCd();

            boolean cloneShowSPL = false;
            String showSPLS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL);
            if (showSPLS != null && showSPLS.length() > 0
                    && "T".equalsIgnoreCase(showSPLS.substring(0, 1))) {
                cloneShowSPL = true;
            }

            boolean cloneAddServiceFee = false;
            String addServiceFeeS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
            if (addServiceFeeS != null && addServiceFeeS.length() > 0
                    && "T".equalsIgnoreCase(addServiceFeeS.substring(0, 1))) {
                cloneAddServiceFee = true;
            }

            boolean cloneHoldPO;
            String holdPOS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO);
            cloneHoldPO = isTRUE(holdPOS);

            boolean cloneAllowChangePassword;
            String allowChangePasswordS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
            cloneAllowChangePassword = isTRUE(allowChangePasswordS);

            String cloneAccountFolder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
            String cloneAccountFolderNew = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER_NEW);
            // String cloneWorkflowEmail =
            // ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORKFLOW_EMAIL);
            String cloneModifyQtyBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADJUST_QTY_BY_855);
            String cloneCreateOrderItemsBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_ITEMS_BY_855);
            String cloneCreateOrderBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_BY_855);
            String cloneAllowModernShopping = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING);
            String cloneAllowSiteLLC = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
            String cloneShowExpressOrder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER);
            String cloneAllowOrderInvItems = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS);
            String cloneAllowReorder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER);
            String cloneUsePhysicalInventory = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
            String cloneShowMyShoppingLists = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
            String cloneShowInvCartTotal = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL);
            String cloneAllowSetWorkOrderPoNumber = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER);
            String cloneWorkOrderPoNumberIsRequired = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED);
            String cloneAllowBuyWorkOrderParts = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS);
            String cloneUserAssignedAssetNumber = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER);
            String cloneContactInformationType = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE);
            String cloneFaqLink = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK);

            boolean cloneAllowCC = false;
            String allowCCS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT);
            if (allowCCS != null && allowCCS.length() > 0
                    && "T".equalsIgnoreCase(allowCCS.substring(0, 1))) {
                cloneAllowCC = true;
            }

            // //////////////////////////////////////////////////////////
            // /////////////////////SETTING Clone data in form //////////
            // //////////////////////////////////////////////////////////

            ae = init(request, (Admin2AccountMgrDetailForm) null);
            if (ae.size() > 0) {
                return ae;
            }

            pForm = (Admin2AccountMgrDetailForm) session.getAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM);

            pForm.setAccountData(AccountData.createValue());

            pForm.setStoreId(String.valueOf(appUser.getUserStore().getStoreId()));
            pForm.setCloneAccoundId(cloneAccountId);
            pForm.setStatusCd(cloneStatusCd);
            pForm.setTimeZoneCd(cloneTimeZoneCd);
            pForm.setTypeCd(cloneTypeCd);
            pForm.setTypeDesc(cloneAccountType);
            pForm.setName(cloneName);
            pForm.setOrderMinimum(cloneOrderMinimum);
            pForm.setCreditLimit(cloneCreditLimit);
            pForm.setCreditRating(cloneCreditRating);
            pForm.setBudgetTypeCd(cloneBudgetTypeCd);
            pForm.setDistrPoType(cloneDistrPOType);
            pForm.setDataFieldProperties(cloneDataFieldProperties);
            pForm.setWeightThreshold(cloneWeightThreshold);
            pForm.setContractThreshold(cloneContractThreshold);
            pForm.setPriceThreshold(clonePriceThreshold);
            pForm.setName1(cloneName1);
            pForm.setName2(cloneName2);
            pForm.setPhone(clonePhone);
            pForm.setFax(cloneFax);
            pForm.setFaxBackConfirm(cloneConfirmFax);
            pForm.setStreetAddr1(cloneStreetAddress1);
            pForm.setStreetAddr2(cloneStreetAddress2);
            pForm.setStreetAddr3(cloneStreetAddress3);
            pForm.setEmailAddress(cloneEmailAddress);
            pForm.setCustomerEmail(cloneCustomerEmail);
            pForm.setContactUsCCEmail(cloneContactUsCCEmail);
            pForm.setDefaultEmail(cloneDefaultEmail);
            pForm.setCountry(cloneCountry);
            pForm.setStateOrProv(cloneStateOrProvince);
            pForm.setCity(cloneCity);
            pForm.setPostalCode(clonePostalCode);
            pForm.setBillingAddress1(clonebilStreetAddress1);
            pForm.setBillingAddress2(clonebilStreetAddress2);
            pForm.setBillingAddress3(clonebilBillingAddress3);
            pForm.setBillingPostalCode(clonebilZipOrPostalCode);
            pForm.setBillingCountry(clonebilCountry);
            pForm.setBillingState(clonebilStateOrProvince);
            pForm.setBillingCity(clonebilCity);
            pForm.setPurchaseOrderAccountName(clonePurchaseOrderAccountName);
            pForm.setOrderPhone(cloneOrderPhone);
            pForm.setOrderFax(cloneOrderFax);
            pForm.setOrderGuideNote(cloneOrderGuideNote);
            pForm.setComments(cloneOrderGuideComments);
            pForm.setSkuTag(cloneSkuTag);
            pForm.setRuntimeDisplayOrderItemActionTypes(cloneRuntimeDisplayOrderItemActionTypes);
            pForm.setTaxableIndicator(cloneTaxableIndicator);
            pForm.setCrcShop(cloneCrcShop);
            pForm.setFreightChargeType(cloneFreightChargeType);
            pForm.setMakeShipToBillTo(cloneMakeShipToBillTo);
            pForm.setOrderManagerEmails(cloneOrderManagerEmailS);
            pForm.setInvReminderEmailSub(cloneEmailSub);
            pForm.setInvReminderEmailMsg(cloneEmailMsg);
            pForm.setNotifyOrderEmailGenerator(cloneNotifyOrderEmailGenerator);
            pForm.setPendingApprovEmailGenerator(clonePendingApprovEmailGenerator);
            pForm.setRejectOrderEmailGenerator(cloneRejectOrderEmailGenerator);
            pForm.setConfirmOrderEmailGenerator(cloneConfirmOrderEmailGenerator);
            pForm.setOrderConfirmationEmailTemplate(cloneOrderConfirmationEmailTemplate);
            pForm.setShippingNotificationEmailTemplate(cloneShippingNotificationEmailTemplate);
            pForm.setPendingApprovalEmailTemplate(clonePendingApprovalEmailTemplate);
            pForm.setRejectedOrderEmailTemplate(cloneRejectedOrderEmailTemplate);
            pForm.setModifiedOrderEmailTemplate(cloneModifiedOrderEmailTemplate);

            pForm.setCustomerRequestPoAllowed(cloneCustomerRequestPoAllowed);
            pForm.setAutoOrderFactor(cloneAutoOrderFactor);
            pForm.setShowDistInventory(cloneShowDistInventory);
            pForm.setInvPOSuffix(clonePoSuffix);
            pForm.setInvLedgerSwitch(cloneLedgerSwitch);
            pForm.setConnectionCustomer(cloneConnectionCustomer);
            pForm.setInvOGListUI(cloneInvOGListUI);
            pForm.setInvCheckPlacedOrder(cloneCheckPlacedOrder);
            pForm.setInvMissingNotification(cloneInvMissingNotify);
            pForm.setCartReminderInterval(cloneCartReminderInt);
            pForm.setAuthorizedForResale(cloneAuthorizedForResale);
            pForm.setAllowOrderConsolidation(cloneAllowOrderConsolidationt);
            pForm.setScheduleCutoffDays(cloneScheduleCutoffDays);
            pForm.setShowScheduledDelivery(cloneShowSchedDel);
            pForm.setShowDistSkuNum(cloneShowDistSkuNum);
            pForm.setShowDistDeliveryDate(cloneShowDistDeliveryDate);
            pForm.setRebateEffDate(cloneRebateEffDateS);
            pForm.setRebatePersent(cloneRebatePersentS);
            pForm.setTargetMarginStr(cloneTargetMarginStr);
            pForm.setCustomerSystemApprovalCd(cloneCustomerSystemApprovalCd);
            pForm.setShowSPL(cloneShowSPL);
            pForm.setAddServiceFee(cloneAddServiceFee);
            pForm.setHoldPO(cloneHoldPO);
            pForm.setAllowChangePassword(cloneAllowChangePassword);
            pForm.setAccountFolder(cloneAccountFolder);
            pForm.setAccountFolderNew(cloneAccountFolderNew);

            pForm.setModifyQtyBy855(isTRUE(cloneModifyQtyBy855));
            pForm.setCreateOrderItemsBy855(isTRUE(cloneCreateOrderItemsBy855));
            pForm.setCreateOrderBy855(isTRUE(cloneCreateOrderBy855));

            pForm.setAllowModernShopping(isTRUE(cloneAllowModernShopping));
            pForm.setAllowSiteLLC(isTRUE(cloneAllowSiteLLC));
            pForm.setShowExpressOrder(isTRUE(cloneShowExpressOrder));
            pForm.setAllowOrderInvItems(isTRUE(cloneAllowOrderInvItems));
            pForm.setAllowReorder(isTRUE(cloneAllowReorder));
            pForm.setUsePhysicalInventory(isTRUE(cloneUsePhysicalInventory));
            pForm.setShowInvCartTotal(isTRUE(cloneShowInvCartTotal));
            pForm.setShowMyShoppingLists(isTRUE(cloneShowMyShoppingLists));
            pForm.setAllowSetWorkOrderPoNumber(isTRUE(cloneAllowSetWorkOrderPoNumber));
            pForm.setWorkOrderPoNumberIsRequired(isTRUE(cloneWorkOrderPoNumberIsRequired));
            pForm.setAllowBuyWorkOrderParts(isTRUE(cloneAllowBuyWorkOrderParts));
            pForm.setUserAssignedAssetNumber(isTRUE(cloneUserAssignedAssetNumber));
            pForm.setContactInformationType(cloneContactInformationType);
            pForm.setShopUIType(cloneShopUIType);
            pForm.setFaqLink(cloneFaqLink);
            pForm.setPdfOrderClass(clonePdfOrderClass);
            pForm.setPdfOrderStatusClass(clonePdfOrderStatusClass);
            pForm.setAllowCreditCard(cloneAllowCC);

            // flag of Clone
            pForm.setClone(true);
            pForm.setFirstUpdate(true);

            session.setAttribute(ADMIN2_ACCOUNT_DETAIL_MGR_FORM, pForm);


        } else {

            ae.add("error", new ActionError("error.simpleGenericError", "No account data found.Probably expired page was used. Please select account again"));
            return ae;

        }

        return ae;
    }

    private static CountryData getCountry(HttpSession session, String countryStr) {
        CountryDataVector countries = (CountryDataVector) session.getAttribute("admin2.country.vector");
        if (countryStr != null && countries != null) {
            for (int i = 0; i < countries.size(); i++) {
                CountryData country = (CountryData) countries.get(i);
                if (country.getShortDesc().equals(countryStr)) {
                    return country;
                }
            }
        }
        return CountryData.createValue();
    }

    private static FiscalCalenderViewVector cloneFiscalCalendars(Account pAccountEjb,
                                                                 int pCloneAccoundId,
                                                                 int pNewAccountId) throws RemoteException {

        FiscalCalenderViewVector fcCollection = pAccountEjb.getFiscalCalCollection(pCloneAccoundId);
        FiscalCalenderViewVector cloneFCCollection = new FiscalCalenderViewVector();
        for (Object aFcCollection : fcCollection) {
            FiscalCalenderView fcv = ((FiscalCalenderView) aFcCollection);
            FiscalCalenderView newFiscalCal = FiscalCalenderView.createValue();
            newFiscalCal.setFiscalCalender((FiscalCalenderData) fcv.getFiscalCalender().clone());
            newFiscalCal.getFiscalCalender().setBusEntityId(pNewAccountId);
            newFiscalCal.setFiscalCalenderDetails(new FiscalCalenderDetailDataVector());
            for (Object oDet : fcv.getFiscalCalenderDetails()) {
                newFiscalCal.getFiscalCalenderDetails().add(((FiscalCalenderDetailData) oDet).clone());
            }
            cloneFCCollection.add(pAccountEjb.updateFiscalCal(newFiscalCal));
        }

        return cloneFCCollection;
    }

    private static Boolean isTRUE(String pValue) {
        if (!Utility.isSet(pValue)) {
            return null;
        } else {
            return Utility.isTrue(pValue);
        }
    }

    private static boolean isTrue(Boolean pValue) {
        return pValue != null && pValue.booleanValue();
    }

    private static Boolean isON(String pValue) {
        if (!Utility.isSet(pValue)) {
            return null;
        } else {
            return Constants.ON.equalsIgnoreCase(pValue.trim());
        }
    }

    private static Boolean isOFF(String pValue) {
        if (!Utility.isSet(pValue)) {
            return null;
        } else {
            return Constants.OFF.equalsIgnoreCase(pValue.trim());
        }
    }


    public static ActionErrors editAccountFiscalCal(HttpServletRequest request, ActionForm form) throws Exception {

    	ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Admin2AccountMgrDetailForm sForm = (Admin2AccountMgrDetailForm) form;
        int id = Utility.parseInt(request.getParameter("fiscalCalendarId"));
        if (sForm == null) {
            lUpdateErrors.add("Fiscal Calendar", new ActionError("variable.empty.error", "Fiscal Calendar"));
            return lUpdateErrors;
        }
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        sForm.getAccountData().setFiscalCalenders(
                accountBean.getFiscalCalCollection(sForm.getAccountData()
                        .getBusEntity().getBusEntityId()));
        List list = sForm.getAccountData().getFiscalCalenders();

        if (id > 0) {
            for (int i = 0; list != null && i < list.size(); i++) {
                FiscalCalenderView fcd = (FiscalCalenderView) list.get(i);
                if (fcd.getFiscalCalender().getFiscalCalenderId() == id) {
                	sForm.setFiscalYearString(Integer.toString(fcd.getFiscalCalender().getFiscalYear()));
                    FiscalCalenderView fiscalCalUpdate =  FiscalCalenderView.createValue();
                    fiscalCalUpdate.setFiscalCalender((FiscalCalenderData) fcd.getFiscalCalender().clone());
                    FiscalCalenderDetailDataVector details = new FiscalCalenderDetailDataVector();
                    for(Object oDet:fcd.getFiscalCalenderDetails()) {
                        details.add(((FiscalCalenderDetailData)oDet).clone());
                    }
                    fiscalCalUpdate.setFiscalCalenderDetails(details);
                    sForm.setFiscalCalUpdate(fiscalCalUpdate);
                    sForm.getFiscalCalUpdate().getFiscalCalender().setFiscalCalenderId(id);
                    break;
                }
            }
        }

        return lUpdateErrors;
    }

    public static ActionErrors createAccountFiscalCal(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Admin2AccountMgrDetailForm sForm = (Admin2AccountMgrDetailForm) form;

        if (sForm == null) {
            String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.fiscalCalendar", null);
            lUpdateErrors.add("Fiscal Calendar", new ActionError("variable.empty.error", message));
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        if (sForm.getNumberOfBudgetPeriods() > Constants.MAX_PERIODS) {
            String numberOfPeriodField = ClwI18nUtil.getMessage(request, "admin2.account.detail.label.numberOfPeriods", null);
            lUpdateErrors.add("Fiscal Calendar", new ActionError("error.maximum.value.of.field", numberOfPeriodField, Constants.MAX_PERIODS));
        }

        if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
            if (sForm.getStoreId() == null || sForm.getStoreId().length() == 0) {
                String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.storeId", null);
                lUpdateErrors.add("storeId", new ActionError("variable.empty.error", message));
            }
        }

        if (lUpdateErrors.size() > 0) {
            return lUpdateErrors;
        }

        sForm.setFiscalCalUpdate(null);
        sForm.getFiscalCalUpdate().getFiscalCalender().setFiscalCalenderId(0);
        sForm.setNumberOfBudgetPeriods(null);

        return lUpdateErrors;
    }

    public static ActionErrors updateAccountFiscalCal(HttpServletRequest request,
    	    ActionForm form) throws Exception {

    	    ActionErrors lUpdateErrors = new ActionErrors();
    	    HttpSession session = request.getSession();

    	    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    	    Admin2AccountMgrDetailForm sForm = (Admin2AccountMgrDetailForm) form;

    	    if (sForm == null) {
    	    	String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.fiscalCalendar", null);
    	    	lUpdateErrors.add("Fiscal Calendar",new ActionError("variable.empty.error",message));
    	      // Report the errors to allow for edits.
    	      return lUpdateErrors;
    	    }

   	    	String fiscalYearStr = sForm.getFiscalYearString();
    	    	try{
    	    		sForm.getFiscalCalUpdate().getFiscalCalender().setFiscalYear(Integer.parseInt(fiscalYearStr));
    	    	}catch(Exception e)  {
    	    		String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.invalidFiscalYear", null);
    	    		lUpdateErrors.add("Fiscal Calendar Data", new ActionError("error.simpleGenericError",message));
    	    		return lUpdateErrors;
    	    	}
    	    if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
    	      if (sForm.getStoreId() == null || sForm.getStoreId().length() == 0) {
    	    	String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.storeId", null);
    	        lUpdateErrors.add("storeId",new ActionError("variable.empty.error",message));
    	      }
    	    }
    	    if (null != lUpdateErrors && lUpdateErrors.size() > 0) {
    	      return lUpdateErrors;
    	    }

    	    APIAccess factory = new APIAccess();
    	    Account accountBean = factory.getAccountAPI();
    	    SessionTool stl = new SessionTool(request);
    	    String cuser = stl.getLoginName();
    	    sForm.getFiscalCalUpdate().getFiscalCalender().setModBy(cuser);

    	      //remove any spaces
    	      FiscalCalenderDetailDataVector details = sForm.getFiscalCalUpdate().getFiscalCalenderDetails();
    	      if (details != null && details.size() > 0) {
    	          for (Object oDetail : details) {
    	              FiscalCalenderDetailData fiscalDet = (FiscalCalenderDetailData) oDetail;
    	              if (Utility.isSet(fiscalDet.getMmdd())) {
    	                  fiscalDet.setMmdd(fiscalDet.getMmdd().replaceAll(" ", ""));
    	              }
    	          }
    	      }

    	    lUpdateErrors = checkOnFiscalCalData(sForm, request);
    	    if (null != lUpdateErrors && lUpdateErrors.size() > 0) {
    	    	String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.checkData", null);
    	    	lUpdateErrors.add("Fiscal Calendar Data",new ActionError("error.simpleGenericError",message));
    	      return lUpdateErrors;
    	    }
    	    FiscalCalenderView fcd = accountBean.updateFiscalCal(sForm.getFiscalCalUpdate());
    	    sForm.getAccountData().setFiscalCalenders
    	      (accountBean.getFiscalCalCollection(fcd.getFiscalCalender().getBusEntityId())
    	      );
    	    sForm.setFiscalCalUpdate(null);
    	    return lUpdateErrors;
    }



	private static ActionErrors checkOnFiscalCalData(Admin2AccountMgrDetailForm pForm, HttpServletRequest request) {
		final FiscalCalenderView pFcd = pForm.getFiscalCalUpdate();
		final List fiscalCalenders = pForm.getAccountData().getFiscalCalenders();
		int fYear = pFcd.getFiscalCalender().getFiscalYear();
		StringBuilder sb = new StringBuilder();
		ActionErrors dataCk = new ActionErrors();
		Date firstMmddDate = null;
		Date lastMmddDate;

		FiscalCalenderDetailDataVector details = pFcd.getFiscalCalenderDetails();

		if (details != null && details.size() > 0) {
			String firstMmddStr = ((FiscalCalenderDetailData) details.get(0)).getMmdd();
			firstMmddDate = parsePeriodDate(firstMmddStr, fYear);
			if (firstMmddDate == null) {
				if (sb.length() > 0) {
					sb.append(',');
				}
				sb.append(1);
			}

			for (int i = 1; i < details.size() - 1; i++) {
				Date mmddDate = parsePeriodDate(
						((FiscalCalenderDetailData) details.get(i)).getMmdd(),fYear);
				if (mmddDate == null) {
					if (sb.length() > 0) {
						sb.append(',');
					}
					sb.append(i + 1);
				}
			}

			String lastMmddStr = ((FiscalCalenderDetailData) details.get(details.size() - 1)).getMmdd();
			lastMmddDate = parsePeriodDate(lastMmddStr, fYear);
			if (lastMmddDate == null && lastMmddStr != null
					&& lastMmddStr.trim().length() > 0) {
				if (sb.length() > 0) {
					sb.append(',');
				}
				sb.append(details.size());
			}

		}
		if (sb.length() > 0) {
			String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.incorrectDates", null);
			dataCk.add("Fiscal Calendar Year:Wrong format of periods",
					new ActionError("error.simpleGenericError",message));
		}
		if (pFcd.getFiscalCalender().getEffDate() == null) {
			String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.needEnterCorrectEffDate", null);
			dataCk.add("Fiscal Calendar Year:Wrong format of Effective Date", new ActionError("error.simpleGenericError",message));
		} else if (firstMmddDate != null) {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(pFcd.getFiscalCalender().getEffDate());
			c2.setTime(firstMmddDate);
			if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)
					|| c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
				String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.effDateNotEqualFirstPeriod", null);
				dataCk.add("Fiscal Calendar Year:Wrong value of Effective Date",new ActionError("error.simpleGenericError",message));
			}
		}
		if (pFcd.getFiscalCalender().getFiscalYear() == 0) {
			Calendar calendar = null;
			if (firstMmddDate != null) {
				calendar = Calendar.getInstance();
				calendar.setTime(firstMmddDate);
			}
			if (calendar != null
					&& (calendar.get(Calendar.DAY_OF_MONTH) != 1 || calendar
							.get(Calendar.MONTH) != Calendar.JANUARY)) {
				String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.FisCalsReqDate", null);
				dataCk.add("Fiscal Calendar Year",new ActionError("error.simpleGenericError",message));
			}
		} else {
			for (int i = 0; fiscalCalenders != null
					&& i < fiscalCalenders.size(); i++) {
				FiscalCalenderView fcv = (FiscalCalenderView) fiscalCalenders
						.get(i);
				if (fcv.getFiscalCalender().getFiscalYear() == pFcd
						.getFiscalCalender().getFiscalYear()
						&& fcv.getFiscalCalender().getFiscalCalenderId() != pFcd
								.getFiscalCalender().getFiscalCalenderId()) {
					String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.FisCalsExists", new Object[]{pFcd.getFiscalCalender().getFiscalYear()});
					dataCk.add("Fiscal Calendar Year",new ActionError("error.simpleGenericError",message));
					break;
				}
			}
		}

		if (null == pFcd) {
			String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.noData", null);
			dataCk.add("Fiscal Calendar Data", new ActionError("error.simpleGenericError", message));
		}
		if (pFcd.getFiscalCalender().getFiscalYear() < 0 || pFcd.getFiscalCalender().getFiscalYear() > 9999) {
			String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.fisCalYearOutOfRange", new Object[]{pFcd.getFiscalCalender().getFiscalYear()});
			dataCk.add("Fiscal Calendar Year", new ActionError("error.simpleGenericError",message));
		}

		Calendar now = Calendar.getInstance();
		int nowYear = now.get(Calendar.YEAR);
		log.debug("pFcd.getFiscalYear()="+ pFcd.getFiscalCalender().getFiscalYear() + " nowYear="+ nowYear);

		if (pFcd.getFiscalCalender().getFiscalYear() < nowYear && pFcd.getFiscalCalender().getFiscalYear() != 0) {
			String message = ClwI18nUtil.getMessage(request, "admin2.account.errors.fisCalNotAllowed", new Object[]{pFcd.getFiscalCalender().getFiscalYear(),nowYear});
			dataCk.add("Fiscal Calendar Year", new ActionError(
					"error.simpleGenericError", message));
		}
		return dataCk;
	}

	private static Date parsePeriodDate(String source, int fYear) {
		DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT,Locale.US);

		if (fYear == 0) {
			if (source.equals("2/29")) {
				return null;
			} else {
				// allow use 0 for fiscal year
				fYear = 1900;
			}
		}
		String dateStr = source + "/" + fYear;
		format.setLenient(false);
		try {
			/*
			 * Here mainly checking for leap year. Parse will throw error, if
			 * source is 2/29 and fYear is not a leap year.
			 */
			format.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
		return parsePeriodDate(source);
	}

	private final static SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("M/d yyyy");
	static {
		PERIOD_DATE_FORMAT.setLenient(false);
	}

	private static Date parsePeriodDate(String source) {
		try {
			/*
			 * Checking whether the source is formatted correctly. For instance,
			 * Utility.parseDate will return null if source is like "0001/5"
			 */
			Date d = Utility.parseDate(source + "/2000");
			if (d == null) {
				return null;
			}
			return PERIOD_DATE_FORMAT.parse(source + " 2000");
		} catch (Exception e) {
			return null;
		}
	}

    private static ActionErrors checkAccountSearchAttr(Admin2AccountMgrForm pForm, HttpServletRequest request) {
        ActionErrors ae = new ActionErrors();
        if (Constants.ID.equalsIgnoreCase(pForm.getSearchType()) && Utility.isSet(pForm.getSearchField())) {
            try {
                Integer.parseInt(pForm.getSearchField());
            } catch (NumberFormatException e) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.wrongIdFormat", new Object[]{pForm.getSearchField()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
            }
        }
        return ae;
    }


}
