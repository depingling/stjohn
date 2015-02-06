/**
 * Title: StoreMessageLogic
 * Description: This is the business logic class handling the ESW Store Message functionality.
 */
package com.espendwise.view.logic.esw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.StoreMessage;
import com.cleanwise.service.api.util.BeanComparator;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.LanguageDataVector;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDetailData;
import com.cleanwise.service.api.value.StoreMessageDetailDataVector;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.service.apps.MessageService;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.StoreMessageDetailForm;
import com.espendwise.view.forms.esw.StoreMessageForm;

public class StoreMessageLogic {
    private static final Logger log = Logger.getLogger(StoreMessageLogic.class);
    
    public static void init(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("countries.vector") == null){
            APIAccess factory = new APIAccess();
            Country countryBean = factory.getCountryAPI();
            CountryDataVector countriesv = countryBean.getAllCountries();
            session.setAttribute("countries.vector", countriesv);
            LanguageDataVector languageVector = SessionTool.getUserAvailableLanguages(session);
            session.setAttribute("languages.vector",languageVector);
        }
    }
    
    public static ActionErrors getStoreMessages(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            CleanwiseUser user = ShopTool.getCurrentUser(request);
            StoreMessageViewVector storeMessages = MessageService.getCustomerDefaultMessages(user.getUserAccount().getAccountId());
            for (int i = 0; i < storeMessages.size(); i++){
                StoreMessageView messageV = (StoreMessageView) storeMessages.get(i);
                messageV.setMessageType(getMessageTypeValue(request, messageV.getMessageType()));
            }
            form.setStoreMessages(storeMessages);
            sortStoreMessage(request, form);
        } catch (Exception e) {
            log.error("Error in getStoreMessage", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    public static ActionErrors createStoreMessage(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            init(request);
            form.reset();
            List<StoreMessageDetailForm> messageDetailForms = new ArrayList<StoreMessageDetailForm>();
            StoreMessageDetailForm detailForm = new StoreMessageDetailForm(0);
            messageDetailForms.add(detailForm);
            form.setDetail(messageDetailForms);            
        } catch (Exception e) {
            log.error("Error in createStoreMessage", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    public static ActionErrors getStoreMessageDetail(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            init(request);
            
            CleanwiseUser user = ShopTool.getCurrentUser(request);
            int messageId = form.getStoreMessageId();
            StoreMessage storeMessage = APIAccess.getAPIAccess().getStoreMessageAPI();
            StoreMessageData storeMessageD = storeMessage.getStoreMessageData(user.getUserStore().getStoreId(), messageId);
            StoreMessageDetailDataVector storeMessageDetailDV = storeMessage.getStoreMessageDetails(messageId);
            form.setStoreMessageId(messageId);
            form.setPostedDate(ClwI18nUtil.formatDateInp(request, storeMessageD.getPostedDate()));
            form.setEndDate(ClwI18nUtil.formatDateInp(request, storeMessageD.getEndDate()));
            form.setPublished(Utility.isTrue(storeMessageD.getPublished()));
            form.setMessageType(storeMessageD.getMessageType());
            form.setForcedReadCount(storeMessageD.getForcedReadCount() > 0 ? String
                    .valueOf(storeMessageD.getForcedReadCount()) : Constants.EMPTY);
            List<StoreMessageDetailForm> messageDetailForms = new ArrayList<StoreMessageDetailForm>();
            
            for (int i = 0; i < storeMessageDetailDV.size(); i++){
                StoreMessageDetailData detail = (StoreMessageDetailData) storeMessageDetailDV.get(i);
                StoreMessageDetailForm detailForm = new StoreMessageDetailForm(detail.getStoreMessageDetailId());
                detailForm.setMessageTitle(detail.getMessageTitle());
                detailForm.setLanguageCd(detail.getLanguageCd());
                detailForm.setCountry(detail.getCountry());
                detailForm.setMessageAuthor(detail.getMessageAuthor());
                detailForm.setMessageBody(detail.getMessageBody());
                messageDetailForms.add(detailForm);
            }
            
            BeanComparator comparator = new BeanComparator(new String[]{"getStoreMessageDetailId"});
            Collections.sort(messageDetailForms,comparator);
            form.setDetail(messageDetailForms);
            
        } catch (Exception e) {
            log.error("Error in getStoreMessageDetail", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    public static ActionErrors deleteStoreMessage(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            CleanwiseUser user = ShopTool.getCurrentUser(request);
            int messageId = form.getStoreMessageId();
            MessageService.deleteMessage(user.getUserStore().getStoreId(), messageId, user.getUserName());
            return getStoreMessages(request, form);
        } catch (Exception e) {
            log.error("Error in deleteStoreMessage", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    
    public static ActionErrors saveStoreMessage(HttpServletRequest request, StoreMessageForm form) {
        return saveStoreMessage(request, form, false);
        
    }
    public static ActionErrors saveStoreMessage(HttpServletRequest request, StoreMessageForm form, boolean publish) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = form.validate(null, request);
            if (!ae.isEmpty())
                return ae;
            CleanwiseUser user = ShopTool.getCurrentUser(request);
            int messageId = form.getStoreMessageId();
            StoreMessageData storeMessageD = null;
            StoreMessageDetailDataVector storeMessageDetailDV = new StoreMessageDetailDataVector();
            
            if (messageId > 0){
                StoreMessage storeMessage = APIAccess.getAPIAccess().getStoreMessageAPI();
                storeMessageD = storeMessage.getStoreMessageData(user.getUserStore().getStoreId(), messageId);
                storeMessageDetailDV = storeMessage.getStoreMessageDetails(messageId);
            }else{
                storeMessageD = StoreMessageData.createValue();
                storeMessageD.setShortDesc("N/A");
                storeMessageD.setMessageManagedBy(RefCodeNames.MESSAGE_MANAGED_BY.CUSTOMER);
                storeMessageD.setStoreMessageStatusCd(RefCodeNames.STORE_MESSAGE_STATUS_CD.ACTIVE);
            }
            
            storeMessageD.setPostedDate(ClwI18nUtil.parseDateInp(request, form.getPostedDate()));
            storeMessageD.setEndDate(ClwI18nUtil.parseDateInp(request, form.getEndDate()));
            storeMessageD.setPublished(publish ? String.valueOf(true) : String.valueOf(form.getPublished()));
            storeMessageD.setMessageType(form.getMessageType());
            if (RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED.equals(form.getMessageType()))
                storeMessageD.setForcedReadCount(1);
            else if (RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ.equals(form.getMessageType())){
                storeMessageD.setForcedReadCount(Integer.parseInt(form.getForcedReadCount()));
            } else {
                storeMessageD.setForcedReadCount(0);
            }

            StoreMessageDetailDataVector messageDetailDV = new StoreMessageDetailDataVector();
            for (int i = 0; i < form.getDetail().size(); i++) {
                StoreMessageDetailForm messageDetailForm = form.getDetail().get(i);
                StoreMessageDetailData smdData = null;
                if (!messageDetailForm.getIsNew()){
                    for (Iterator iterator2 = storeMessageDetailDV.iterator(); iterator2.hasNext();) {
                        StoreMessageDetailData messageDetailData = (StoreMessageDetailData) iterator2.next();
                        if (messageDetailData.getStoreMessageDetailId()==messageDetailForm.getStoreMessageDetailId()){
                            smdData = messageDetailData;
                            break;
                        }
                    }
                }
                if (smdData==null){
                    smdData = StoreMessageDetailData.createValue();
                    smdData.setMessageAbstract(".");
                    if (i==0) // set for first message
                        smdData.setMessageDetailTypeCd(RefCodeNames.MESSAGE_DETAIL_TYPE_CD.DEFAULT);
                }
                smdData.setMessageTitle(messageDetailForm.getMessageTitle());
                smdData.setLanguageCd(messageDetailForm.getLanguageCd());
                smdData.setCountry(messageDetailForm.getCountry());
                smdData.setMessageAuthor(messageDetailForm.getMessageAuthor());
                smdData.setMessageBody(messageDetailForm.getMessageBody());
                messageDetailDV.add(smdData);
            }
                
            MessageService.saveStoreMessage(user.getUserStore().getStoreId(), user.getUserAccount().getAccountId(),
                    storeMessageD, messageDetailDV, user.getUserName());
            form.setStoreMessageId(storeMessageD.getStoreMessageId());            
            
            return getStoreMessageDetail(request, form);
        } catch (Exception e) {
            log.error("Error in saveStoreMessage", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    
    public static ActionErrors previewStoreMessage(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            int formIdx = form.getSelectedFormIndex();
            StoreMessageView message = new StoreMessageView();
            message.setPostedDate(ClwI18nUtil.parseDateInp(request, form.getPostedDate()));
            message.setEndDate(ClwI18nUtil.parseDateInp(request, form.getEndDate()));
                        
            StoreMessageDetailForm detailForm = form.getDetail().get(formIdx);
            message.setMessageBody(detailForm.getMessageBody());
            message.setMessageAuthor(detailForm.getMessageAuthor());
            message.setMessageTitle(detailForm.getMessageTitle());
            form.setCurrentMessage(message);
        } catch (Exception e) {
            log.error("Error in deleteStoreMessage", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    
    public static ActionErrors addMessageTranslation(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            StoreMessageDetailForm detailForm = new StoreMessageDetailForm(0);
            form.getDetail().add(detailForm);
        } catch (Exception e) {
            log.error("Error in addMessageTranslation", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    
    public static ActionErrors deleteMessageTranslation(HttpServletRequest request, StoreMessageForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            int formIdx = form.getSelectedFormIndex();
            StoreMessageDetailForm detailForm = form.getDetail().get(formIdx);
            if (!detailForm.getIsNew())
                MessageService.deleteMessageDetail(detailForm.getStoreMessageDetailId());
            form.getDetail().remove(formIdx);
        } catch (Exception e) {
            log.error("Error in deleteMessageTranslation", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }
    
    public static void sortStoreMessage(final HttpServletRequest request, final StoreMessageForm form) {
        ActionMessages result = new ActionMessages();
        if (form == null) {
            return;
        }
        if (Utility.isSet(form.getSortField()) == false) {
            form.setSortField(Constants.STORE_MESSAGE_SORT_FIELD_CREATED_DATE);
        }
        if (Utility.isSet(form.getSortOrder()) == false) {
            form.setSortOrder(Constants.STORE_MESSAGE_SORT_ORDER_ASCENDING);
        }
        StoreMessageViewVector list = form.getStoreMessages();
        
        if (list != null && list.size() > 1) {
            Collections.sort(list, new Comparator<StoreMessageView>() {
                @Override
                public int compare(StoreMessageView o1,
                        StoreMessageView o2) {
                    String s1 = o1.getMessageTitle();
                    String s2 = o2.getMessageTitle();
                    if (Constants.STORE_MESSAGE_SORT_FIELD_MESSAGE_TITLE.equals(form
                            .getSortField())) {
                        s1 = o1.getMessageTitle();
                        s2 = o2.getMessageTitle();
                    } else if (Constants.STORE_MESSAGE_SORT_FIELD_MESSAGE_TYPE.equals(form
                            .getSortField())) {
                        s1 = o1.getMessageType();
                        s2 = o2.getMessageType();
                    } else if (Constants.STORE_MESSAGE_SORT_FIELD_MOD_BY
                            .equals(form.getSortField())) {
                        s1 = o1.getModBy();
                        s2 = o2.getModBy();
                    } else if (Constants.STORE_MESSAGE_SORT_FIELD_CREATED_DATE.equals(form
                            .getSortField())) {
                        s1 = (o1.getAddDate()==null)?"":o1.getAddDate().getTime()+"";
                        s2 = (o2.getAddDate()==null)?"":o2.getAddDate().getTime()+"";
                    } else if (Constants.STORE_MESSAGE_SORT_FIELD_POSTED_DATE.equals(form
                            .getSortField())) {
                        s1 = (o1.getPostedDate()==null)?"":o1.getPostedDate().getTime()+"";
                        s2 = (o2.getPostedDate()==null)?"":o2.getPostedDate().getTime()+"";
                    } else if (Constants.STORE_MESSAGE_SORT_FIELD_END_DATE.equals(form
                            .getSortField())) {
                        s1 = (o1.getEndDate()==null)?"":o1.getEndDate().getTime()+"";
                        s2 = (o2.getEndDate()==null)?"":o2.getEndDate().getTime()+"";
                    }
                    s1 = Utility.strNN(s1);
                    s2 = Utility.strNN(s2);
                    if (Constants.STORE_MESSAGE_SORT_ORDER_ASCENDING.equals(form
                            .getSortOrder())) {
                        return s1.compareToIgnoreCase(s2);
                    } else {
                        return s2.compareToIgnoreCase(s1);
                    }
                }
            });
            form.setMessages(list);
        }
    }
    
    private static String getMessageTypeValue(HttpServletRequest request, String code){
        if (code.equals(RefCodeNames.MESSAGE_TYPE_CD.REGULAR))
            return ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.regular");
        else if (code.equals(RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED))
            return ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.acknowledgementRequired");
        else if (code.equals(RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ))
            return ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.forceRead");
        else
            return code;
    }
}