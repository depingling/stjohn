package com.cleanwise.view.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Language;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.StoreMessage;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames.MESSAGE_STATUS_CD;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LanguageDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
import com.cleanwise.view.forms.StoreMsgMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;

public class StoreMsgMgrLogic {
    public static ActionErrors getDetail(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) throws Exception {
        ActionErrors errors = new ActionErrors();
        StoreMessageData messageData = getMessageData(pRequest, pForm);
        if (messageData == null) {
            messageData = StoreMessageData.createValue();
        }
        pForm.setMessageDetail(messageData);
        data2Form(pRequest, pForm);
        return errors;
    }

    public static StoreMessageData getMessageData(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) throws Exception {
        int id = Utility.parseInt(pRequest.getParameter("id"));
        if (id == 0 && pForm.getMessageDetail() != null) {
            id = pForm.getMessageDetail().getStoreMessageId();
        }
        StoreMessageData messageData = null;
        if (id > 0) {
            CleanwiseUser user = new SessionTool(pRequest).getUserData();
            StoreMessage storeMessage = APIAccess.getAPIAccess()
                    .getStoreMessageAPI();
            messageData = storeMessage.getStoreMessageData(user.getUserStore()
                    .getStoreId(), id);
        }
        return messageData;
    }

    public static void initFormVectors(HttpServletRequest request)
            throws Exception {
        HttpSession session = request.getSession(true);
        APIAccess factory = new APIAccess();
        if (null == session.getAttribute("msg.status.vector")) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector statusv = lsvc
                    .getRefCodesCollection("MESSAGE_STATUS_CD");
            session.setAttribute("msg.status.vector", statusv);
        }
        if (null == session.getAttribute("country.vector")) {
            Country countryBean = factory.getCountryAPI();
            CountryDataVector countriesv = countryBean.getAllCountries();
            session.setAttribute("country.vector", countriesv);
        }
        if (null == session.getAttribute("language.vector")) {
            Language languageBean = factory.getLanguageAPI();
            LanguageDataVector countriesv = languageBean.getAllLanguages();
            session.setAttribute("language.vector", countriesv);
        }
    }

    public static void init(HttpServletRequest request, StoreMsgMgrForm pForm)
            throws Exception {
        initFormVectors(request);
        pForm.setConfigAccounts(null);
        pForm.setMessages(null);
    }

    public static ActionErrors searchMessages(HttpServletRequest request,
            StoreMsgMgrForm pForm) throws Exception {
        ActionErrors errors = new ActionErrors();
        validateMessageSearch(request, errors, pForm);
        if (errors.size() == 0) {
            StoreMessage storeMessage = APIAccess.getAPIAccess()
                    .getStoreMessageAPI();
            SearchCriteria crit = null;
            if (Utility.isSet(pForm.getSearchField()) == true) {
                if (Constants.ID.equals(pForm.getSearchType())) {
                    crit = new SearchCriteria(SearchCriteria.MESSAGE_ID,
                            SearchCriteria.EXACT_MATCH, pForm.getSearchField());
                } else if (Constants.NAME_BEGINS.equals(pForm.getSearchType())) {
                    crit = new SearchCriteria(SearchCriteria.MESSAGE_TITLE,
                            SearchCriteria.BEGINS_WITH_IGNORE_CASE, pForm
                                    .getSearchField());
                } else if (Constants.NAME_CONTAINS
                        .equals(pForm.getSearchType())) {
                    crit = new SearchCriteria(SearchCriteria.MESSAGE_TITLE,
                            SearchCriteria.CONTAINS_IGNORE_CASE, pForm
                                    .getSearchField());
                }
            }
            String datePattern = I18nUtil.getDatePattern(ClwI18nUtil
                    .getUserLocale(request));
            Date postedDateFrom = Utility.parseDate(pForm
                    .getSearchPostedDateFrom(), datePattern, false);
            Date postedDateTo = Utility.parseDate(
                    pForm.getSearchPostedDateTo(), datePattern, false);
            CleanwiseUser user = new SessionTool(request).getUserData();
            StoreMessageDataVector storeMessageDV = storeMessage
                    .getMessagesByCriteria(user.getUserStore().getStoreId(),
                            crit, postedDateFrom, postedDateTo, null, null,pForm.isShowInactive());
            pForm.setMessages(storeMessageDV);
        } else {
            pForm.setMessages(null);
        }
        return errors;
    }

    public static void createMessage(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) throws Exception {
        init(pRequest, pForm);
        pForm.setMessageDetail(StoreMessageData.createValue());
        data2Form(pRequest, pForm);
    }

    public static ActionErrors updateMessage(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm, boolean pPublish) throws Exception {
        ActionErrors errors = new ActionErrors();
        StoreMessageData buffer = getMessageData(pRequest, pForm);
        boolean isPublished = false;
        if (buffer != null) {
            isPublished = Utility.isTrue(buffer.getPublished());
        }
        validateDetail(pRequest, errors, pForm, isPublished);
        if (errors.size() == 0) {
            CleanwiseUser user = new SessionTool(pRequest).getUserData();
            StoreMessage storeMessage = APIAccess.getAPIAccess()
                    .getStoreMessageAPI();
            if (isPublished == true) {
            	
            	//form2DataPublished(pRequest, pForm);
            	buffer.setMessageAbstract(pForm.getMessageDetail().getMessageAbstract());
            	buffer.setMessageBody(pForm.getMessageDetail().getMessageBody());
                buffer.setStoreMessageStatusCd(pForm.getMessageDetail()
                        .getStoreMessageStatusCd());
                String oldForcedRead = buffer.getForcedRead();
                int oldHowManyTimes = buffer.getHowManyTimes();
                buffer.setForcedRead(Boolean.valueOf(pForm.isForcedRead())
                        .toString());
                buffer.setHowManyTimes(pForm.isForcedRead() ? Utility
                        .parseInt(pForm.getHowManyTimes()) : 0);
                boolean clearViewHistory = false;
                if (oldForcedRead.equals(buffer.getForcedRead()) == false
                        || oldHowManyTimes != buffer.getHowManyTimes()) {
                    clearViewHistory = true;
                }
                storeMessage.updateStoreMessage(user.getUserStore()
                        .getStoreId(), buffer, user.getUserName(),
                        clearViewHistory);
                pForm.setMessageDetail(buffer);
                //data2FormPublished(pRequest, pForm);
                data2Form(pRequest, pForm);
            } else {
                form2Data(pRequest, pForm);
                if (pPublish == true) {
                    pForm.getMessageDetail().setPublished(
                            Boolean.valueOf(true).toString().toUpperCase());
                }
                storeMessage.updateStoreMessage(user.getUserStore()
                        .getStoreId(), pForm.getMessageDetail(), user
                        .getUserName());
                data2Form(pRequest, pForm);
            }
        } else if (isPublished == true) {
            data2Form(pRequest, pForm);
        }
        return errors;
    }

    public static ActionErrors cloneMessage(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) throws Exception {
        getDetail(pRequest, pForm);
        ActionErrors errors = new ActionErrors();
        StoreMessageData messageD = pForm.getMessageDetail();
        messageD.setStoreMessageId(0);
        messageD.setPublished(null);
        messageD.setMessageTitle(ClwI18nUtil.getMessage(pRequest,
                "storemessages.text.cloned", new String[] { messageD
                        .getMessageTitle() }));
        return errors;
    }

    public static ActionErrors previewMessage(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) throws Exception {
        ActionErrors errors = new ActionErrors();
        validateDetail(pRequest, errors, pForm, false);
        pForm.setShowPreview(errors.size() == 0);
        if (errors.size() == 0) {
            form2Data(pRequest, pForm);
        }
        return errors;
    }

    private final static String EMPTY_KEY = "variable.empty.error";

    private static void validateMessageSearch(HttpServletRequest pRequest,
            ActionErrors pErrors, StoreMsgMgrForm pForm) throws Exception {
        validateDate(pRequest, pErrors, pForm.getSearchPostedDateFrom(),
                ClwI18nUtil.getMessage(pRequest,
                        "storemessages.text.datePostedFrom", null));
        validateDate(pRequest, pErrors, pForm.getSearchPostedDateTo(),
                ClwI18nUtil.getMessage(pRequest,
                        "storemessages.text.datePostedTo", null));
        String datePattern = I18nUtil.getDatePattern(ClwI18nUtil
                .getUserLocale(pRequest));
        Date dateFrom = Utility.parseDate(pForm.getSearchPostedDateFrom(),
                datePattern, false);
        Date dateTo = Utility.parseDate(pForm.getSearchPostedDateTo(),
                datePattern, false);
        validateDateRange(pRequest, pErrors, dateFrom, dateTo,
                ClwI18nUtil.getMessage(pRequest,
                        "storemessages.text.datePostedFrom", null), ClwI18nUtil
                        .getMessage(pRequest,
                                "storemessages.text.datePostedTo", null));
        if (Constants.ID.equals(pForm.getSearchType()) == true) {
            if (Utility.parseInt(pForm.getSearchField()) <= 0) {
                pErrors.add("messageId", new ActionMessage(
                        "variable.integer.format.error", ClwI18nUtil
                                .getMessage(pRequest,
                                        "storemessages.text.messageId", null)));
            }
        }
    }

    private static void validateDateRange(HttpServletRequest pRequest,
            ActionErrors pErrors, Date pDateFrom, Date pDateTo,
            String pLabelDateFrom, String pLabelDateTo) {
        if (pDateFrom != null && pDateTo != null
                && pDateTo.compareTo(pDateFrom) < 0) {
            String errorMess = ClwI18nUtil.getMessage(pRequest,
                    "storemessages.text.dateIsEarlier", new String[] {
                            pLabelDateTo, pLabelDateFrom });
            pErrors.add("errorDateRange", new ActionMessage(
                    "error.simpleGenericError", errorMess));
        }
    }

    private static void validateAccountSearch(HttpServletRequest pRequest,
            ActionErrors pErrors, StoreMsgMgrForm pForm) {
        if (Constants.ID.equals(pForm.getConfSearchType()) == true) {
            if (Utility.parseInt(pForm.getConfSearchField()) <= 0) {
                pErrors.add("accountId", new ActionMessage(
                        "variable.integer.format.error", ClwI18nUtil
                                .getMessage(pRequest,
                                        "storemessages.text.accountId", null)));
            }
        }
    }

    private static void validateDetail(HttpServletRequest pRequest,
            ActionErrors pErrors, StoreMsgMgrForm pForm, boolean pIsPublish)
            throws Exception {
      StoreMessageData message = pForm.getMessageDetail();
      message.setMessageTitle(message.getMessageTitle().trim());
      String statusCd = message.getStoreMessageStatusCd();
      if (MESSAGE_STATUS_CD.ACTIVE.equals(statusCd) == false
                && MESSAGE_STATUS_CD.INACTIVE.equals(statusCd) == false) {
            pErrors.add("statusCd", new ActionMessage(EMPTY_KEY, ClwI18nUtil
                    .getMessage(pRequest, "storemessages.text.status", null)));
      }
      /***
      if (pIsPublish == true) {
            return;
      }
      ***/
      if (pForm.isForcedRead() == true) {
          int howManyTimes = Utility.parseInt(pForm.getHowManyTimes());
          if (howManyTimes <= 0) {
              pErrors.add("howManyTimes", new ActionMessage(EMPTY_KEY,
                      ClwI18nUtil.getMessage(pRequest,
                              "storemessages.text.howManyTimes", null)));
          }
      }
      if (!pIsPublish == true) { //message was NOT published
        if (Utility.isSet(message.getMessageTitle()) == false) {
            pErrors.add("name", new ActionMessage(EMPTY_KEY, ClwI18nUtil
                    .getMessage(pRequest, "storemessages.text.messageTitle",
                            null)));
        }
        if (Utility.isSet(message.getLanguageCd()) == false) {
            pErrors
                    .add("name", new ActionMessage(EMPTY_KEY, ClwI18nUtil
                            .getMessage(pRequest,
                                    "storemessages.text.language", null)));
        }
        validateDate(pRequest, pErrors, pForm.getPostedDate(), ClwI18nUtil
                .getMessage(pRequest, "storemessages.text.datePosted", null));
        validateDate(pRequest, pErrors, pForm.getEndDate(), ClwI18nUtil
                .getMessage(pRequest, "storemessages.text.endDate", null));
        String datePattern = I18nUtil.getDatePattern(ClwI18nUtil
                .getUserLocale(pRequest));
        Date datePosted = Utility.parseDate(pForm.getPostedDate(), datePattern,
                false);
        Date dateEnd = Utility
                .parseDate(pForm.getEndDate(), datePattern, false);
        validateDateRange(pRequest, pErrors, datePosted, dateEnd, ClwI18nUtil
                .getMessage(pRequest, "storemessages.text.datePosted", null),
                ClwI18nUtil.getMessage(pRequest, "storemessages.text.endDate",
                        null));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date dateCurrent = c.getTime();
        validateDateRange(pRequest, pErrors, dateCurrent, datePosted,
                ClwI18nUtil.getMessage(pRequest,
                        "storemessages.text.currentDate", null), ClwI18nUtil
                        .getMessage(pRequest, "storemessages.text.datePosted",
                                null));
        validateDateRange(pRequest, pErrors, dateCurrent, dateEnd, ClwI18nUtil
                .getMessage(pRequest, "storemessages.text.currentDate", null),
                ClwI18nUtil.getMessage(pRequest, "storemessages.text.endDate",
                        null));
        if (Utility.isSet(message.getMessageAuthor()) == false) {
            pErrors.add("messageAuthor", new ActionMessage(EMPTY_KEY,
                    ClwI18nUtil.getMessage(pRequest,
                            "storemessages.text.messageAuthor", null)));
        }
      } //if (!pIsPublish == true) {
      
        String fieldMessageAbstract = ClwI18nUtil.getMessage(pRequest,
                "storemessages.text.messageAbstract");
        String fieldMessageBody = ClwI18nUtil.getMessage(pRequest,
                "storemessages.text.messageBody");
        if (Utility.isSet(message.getMessageAbstract()) == false) {
            pErrors.add("messageAbstract", new ActionMessage(EMPTY_KEY,
                    fieldMessageAbstract));
        } else {
            validateTextSize(pRequest, pErrors, fieldMessageAbstract, message
                    .getMessageAbstract(), Constants.MAX_SIZE_MESSAGE_ABSTRACT);
        }
        validateTextSize(pRequest, pErrors, fieldMessageBody, message
                .getMessageBody(), Constants.MAX_SIZE_MESSAGE_BODY);
        
      if (!pIsPublish == true) { //message was NOT published  
        if (Utility.isSet(message.getMessageTitle())
                && Utility.isSet(message.getLanguageCd())) {
            SearchCriteria crit = new SearchCriteria(
                    SearchCriteria.MESSAGE_TITLE,
                    SearchCriteria.EXACT_MATCH_IGNORE_CASE, message
                            .getMessageTitle());
            StoreMessage storeMessage = APIAccess.getAPIAccess()
                    .getStoreMessageAPI();
            CleanwiseUser user = new SessionTool(pRequest).getUserData();
            StoreMessageDataVector storeMessageDV = storeMessage
                    .getMessagesByCriteria(user.getUserStore().getStoreId(),
                            crit, null, null, message.getCountry(), message
                                    .getLanguageCd());
            if (storeMessageDV != null && storeMessageDV.size() > 0) {
                for (int i = 0; i < storeMessageDV.size(); i++) {
                    StoreMessageData storeMessageD = (StoreMessageData) storeMessageDV
                            .get(i);
                    if (Utility.isSet(message.getCountry()) == false
                            && Utility.isSet(storeMessageD.getCountry()) == true) {
                        continue;
                    }
                    if (storeMessageD.getStoreMessageId() != message
                            .getStoreMessageId()) {
                        String errorMess = ClwI18nUtil.getMessage(pRequest,
                                "storemessages.text.messageExists", null);
                        pErrors.add("messageExists", new ActionMessage(
                                "error.simpleGenericError", errorMess));
                        break;
                    }
                }
            }
        }
      } //if (!pIsPublish == true) {
    }

    private static void validateTextSize(HttpServletRequest pRequest,
            ActionErrors pErrors, String pFieldName, String pText, int pMaxSize) {
        if (Utility.isSet(pText)) {
            if (pText.length() > pMaxSize) {
                int needToRemove = pText.length() - pMaxSize;
                String errorMess = ClwI18nUtil.getMessage(pRequest,
                        "storemessages.text.maxTextSize", new String[] {
                                pFieldName, String.valueOf(pMaxSize),
                                String.valueOf(needToRemove) });
                pErrors.add("errorTextSize", new ActionMessage(
                        "error.simpleGenericError", errorMess));
            }
        }
    }

    private static void validateDate(HttpServletRequest pRequest,
            ActionErrors pErrors, String pDateValue, String pDateParamName) {
        if (Utility.isSet(pDateValue) == false) {
            return;
        }
        try {
            String datePattern = I18nUtil.getDatePattern(ClwI18nUtil
                    .getUserLocale(pRequest));
            Date val = Utility.parseDate(pDateValue, datePattern, false);
            if (val == null) {
                String errorMess = ClwI18nUtil.getMessage(pRequest,
                        "storemessages.text.wrongDateFormat", new String[] {
                                pDateParamName, datePattern.toLowerCase() });
                pErrors.add("errorDateFormat", new ActionMessage(
                        "error.simpleGenericError", errorMess));
            }
        } catch (Exception e) {
        }
    }

    private static void form2Data(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) {
        try {
            String datePattern = I18nUtil.getDatePattern(ClwI18nUtil
                    .getUserLocale(pRequest));
            StoreMessageData messageD = pForm.getMessageDetail();
            Date datePosted = Utility.parseDate(pForm.getPostedDate(),
                    datePattern, false);
            Date dateEnd = Utility.parseDate(pForm.getEndDate(), datePattern,
                    false);
            messageD.setPostedDate(datePosted);
            messageD.setEndDate(dateEnd);
            messageD.setForcedRead(Boolean.valueOf(pForm.isForcedRead())
                    .toString());
            messageD.setHowManyTimes(pForm.isForcedRead() ? Utility
                    .parseInt(pForm.getHowManyTimes()) : 0);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void data2Form(HttpServletRequest pRequest,
            StoreMsgMgrForm pForm) {
        String datePattern = I18nUtil.getDatePattern(ClwI18nUtil
                .getUserLocale(pRequest));
        StoreMessageData messageD = pForm.getMessageDetail();
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        pForm.setPostedDate(messageD.getPostedDate() == null ? "" : sdf
                .format(messageD.getPostedDate()));
        pForm.setEndDate(messageD.getEndDate() == null ? "" : sdf
                .format(messageD.getEndDate()));
        pForm.setForcedRead(Utility.isTrue(messageD.getForcedRead(), false));
        pForm.setHowManyTimes(messageD.getHowManyTimes() > 0 ? ""
                + messageD.getHowManyTimes() : "");
    }
    
    public static ActionErrors updateConfigAccounts(HttpServletRequest request,
            StoreMsgMgrForm pForm, boolean pAllAccounts) throws Exception {
        ActionErrors errors = new ActionErrors();
        APIAccess factory = new APIAccess();
        StoreMessage storeMessage = factory.getStoreMessageAPI();
        CleanwiseUser user = (CleanwiseUser) request.getSession(true)
                .getAttribute(Constants.APP_USER);

        int messageId = pForm.getMessageDetail().getStoreMessageId();
        int storeId = user.getUserStore().getStoreId();
        if (pAllAccounts == true) {
            storeMessage.configureAllAccounts(messageId, storeId, user
                    .getUserName());
        } else {
            IdVector addAccountIds = new IdVector();
            Set<Integer> buffer = new HashSet<Integer>();
            BusEntityDataVector displayAccounts = pForm.getConfigAccounts();
            for (int i = 0; displayAccounts != null
                    && i < displayAccounts.size(); i++) {
                BusEntityData account = (BusEntityData) displayAccounts.get(i);
                buffer.add(account.getBusEntityId());
            }
            String selectedIds[] = pForm.getConfigAccountsSelectedIds();
            for (int i = 0; selectedIds != null && i < selectedIds.length; i++) {
                int id = Utility.parseInt(selectedIds[i]);
                addAccountIds.add(id);
                buffer.remove(id);
            }
            IdVector removeAccountIds = new IdVector();
            removeAccountIds.addAll(buffer);
            storeMessage.configureAccounts(messageId, addAccountIds,
                    removeAccountIds, user.getUserName());
        }
        return errors;
    }

    public static ActionErrors searchConfigAccounts(
            HttpServletRequest pRequest, StoreMsgMgrForm pForm)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        validateAccountSearch(pRequest, errors, pForm);
        CleanwiseUser user = (CleanwiseUser) pRequest.getSession(true)
                .getAttribute(Constants.APP_USER);
        int messageId = pForm.getMessageDetail().getStoreMessageId();
        int storeId = user.getUserStore().getStoreId();
        APIAccess factory = new APIAccess();
        Account acctBean = factory.getAccountAPI();
        StoreMessage storeMessage = factory.getStoreMessageAPI();
        IdVector accountIds = storeMessage.getConfiguratedAccounts(messageId);
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(storeId));
        crit.setStoreBusEntityIds(storeIds);
        if (pForm.isConfiguratedOnlyFl()) {
            crit.setAccountBusEntityIds(accountIds);
        }
        if (Utility.isSet(pForm.getConfSearchField()) == true) {
            if (Constants.ID.equals(pForm.getConfSearchType())) {
                crit.setSearchId(Utility.parseInt(pForm.getConfSearchField()));
            } else if (Constants.NAME_BEGINS.equals(pForm.getConfSearchType())) {
                crit.setSearchName(pForm.getConfSearchField());
                crit.setSearchNameType(Account.BEGINS_WITH_IGNORE_CASE);
            } else if (Constants.NAME_CONTAINS
                    .equals(pForm.getConfSearchType())) {
                crit.setSearchName(pForm.getConfSearchField());
                crit.setSearchNameType(Account.CONTAINS_IGNORE_CASE);
            }
        }
        crit.setSearchForInactive(pForm.isConfShowInactiveFl());
        BusEntityDataVector accounts = null;
        if (!(pForm.isConfiguratedOnlyFl() && (accountIds == null || accountIds
                .size() == 0))) {
            accounts = acctBean.getAccountBusEntByCriteria(crit);
        }
        pForm.setConfigAccounts(accounts);
        String[] selected = new String[accountIds.size()];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = accountIds.get(i).toString();
        }
        pForm.setConfigAccountsSelectedIds(selected);
        return errors;
    }
}
