/**
 * Title: ControlsForm

 * Description: This is the Struts ActionForm class handling the ESW control functionality.
 *
 */
package com.espendwise.view.forms.esw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.Constants;
import com.espendwise.view.forms.esw.StoreMessageDetailForm;

/**
 * Implementation of <code>ActionForm</code> that handles store messages functionality.
 */
public final class StoreMessageForm extends EswForm {
    private static final long serialVersionUID = -1L;
    // used for store message list UI
    private String sortField;
    private String sortOrder;
    private StoreMessageViewVector storeMessages = new StoreMessageViewVector();
    
    // used for store message detail UI
    private int storeMessageId;
    private String postedDate;
    private String endDate;
    private String forcedReadCount;
    private boolean published;
    private String messageAuthor;
    private boolean forcedRead;
    private String messageType = RefCodeNames.MESSAGE_TYPE_CD.REGULAR;
    private int selectedFormIndex=-1;
    private List<StoreMessageDetailForm> detail = new ArrayList<StoreMessageDetailForm>();
    
    public void reset() {
        storeMessageId = 0;
        postedDate = null;
        endDate = null;
        forcedReadCount = null;
        published = false;
        messageAuthor = null;
        forcedRead = false;
        messageType = RefCodeNames.MESSAGE_TYPE_CD.REGULAR;
        selectedFormIndex=-1;
        detail = new ArrayList<StoreMessageDetailForm>();
    }
    
    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public StoreMessageViewVector getStoreMessages() {
        return storeMessages;
    }
    public void setStoreMessages(StoreMessageViewVector storeMessages) {
        this.storeMessages = storeMessages;
    }
    
    public int getStoreMessageId() {
        return storeMessageId;
    }

    public void setStoreMessageId(int storeMessageId) {
        this.storeMessageId = storeMessageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public boolean getForcedRead() {
        return forcedRead;
    }

    public void setForcedRead(boolean forcedRead) {
        this.forcedRead = forcedRead;
    }

    public String getForcedReadCount() {
        return forcedReadCount;
    }

    public void setForcedReadCount(String forcedReadCount) {
        this.forcedReadCount = forcedReadCount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public  boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getMessageAuthor() {
        return messageAuthor;
    }

    public void setMessageAuthor(String messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    public int getSelectedFormIndex() {
        return selectedFormIndex;
    }

    public void setSelectedFormIndex(int previewFormIdx) {
        this.selectedFormIndex = previewFormIdx;
    }

    public boolean getIsNew() {
       return (storeMessageId == 0);
    }

    public List<StoreMessageDetailForm> getDetail() {
        return detail;
    }

    public void setDetail(List<StoreMessageDetailForm> detail) {
        this.detail = detail;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
        SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat);
        ActionErrors errors = new ActionErrors();
        Date now = new Date();
        String nowDateStr = sdf.format(now);
        try {
            now = sdf.parse(nowDateStr);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        if (!Utility.isSet(postedDate) || defaultDateFormat.equalsIgnoreCase(postedDate)){
            errors.add("postedDate", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.effectiveDate")));
        }else{
            try {
                Date tempDate = ClwI18nUtil.parseDateInp(request, postedDate);
                if (!getPublished() && tempDate.compareTo(now) < 0){                    
                    errors.add("postedDate", new ActionError("validation.error.wrongDateRange", 
                            new Object[]{ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.effectiveDate")}));
                }
            } catch (ParseException e) {
                errors.add("postedDate", new ActionError("validation.error.wrongDateFormat", 
                        new Object[]{ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.effectiveDate"), postedDate}));
            }
        }
        if (!Utility.isSet(endDate)  || defaultDateFormat.equalsIgnoreCase(endDate)){
            errors.add("endDate", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.expirationDate")));
        }else{
            try {
                Date tempDate = ClwI18nUtil.parseDateInp(request, endDate);
                if (tempDate.compareTo(now) < 0) {
                    errors.add("endDate", new ActionError("validation.error.wrongDateRange", 
                            new Object[]{ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.expirationDate")}));
                }
            } catch (ParseException e) {
                errors.add("endDate", new ActionError("validation.error.wrongDateFormat", 
                        new Object[]{ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.expirationDate"), endDate}));
            }
        }
        if (!Utility.isSet(messageType)){
            errors.add("messageType", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.messageType")));
        }else if (messageType.equals(RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ)){
            if (!Utility.isSet(forcedReadCount)){
                errors.add("forcedReadCount", new ActionError("validation.error.requireNumber", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.forceReadCount")));
            }else{
                try {
                    Integer.valueOf(forcedReadCount);
                } catch (NumberFormatException e){
                    errors.add("forcedReadCount", new ActionError("validation.error.wrongNumberFormat", 
                            new Object[]{ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.forceReadCount"), forcedReadCount}));
                }
            }
        }
        for (int i = 0; i < detail.size(); i++) {
            StoreMessageDetailForm detailForm = detail.get(i);
            if (!Utility.isSet(detailForm.getMessageTitle()) && !errors.get("messageTitle").hasNext()){
                errors.add("messageTitle", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.messageTitle")));
            }
            if (!Utility.isSet(detailForm.getMessageAuthor()) && !errors.get("messageAuthor").hasNext()){
                errors.add("messageAuthor", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.author")));
            }
            if (!Utility.isSet(detailForm.getMessageBody()) && !errors.get("messageBody").hasNext()){
                errors.add("messageBody", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.messageContent")));
            }
            if (i > 0){
                if (!Utility.isSet(detailForm.getLanguageCd()) && !errors.get("languageCd").hasNext()){
                    errors.add("languageCd", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.language")));
                }
                if (!Utility.isSet(detailForm.getCountry()) && !errors.get("country").hasNext()){
                    errors.add("country", new ActionError("validation.error.dataRequired", ClwMessageResourcesImpl.getMessage(request, "userportal.esw.label.country")));
                }
            }
        }
        return errors;
        
        /*userportal.esw.label.messageTitle=Message Title

userportal.esw.label.published=Published?
userportal.esw.label.lastModifiedBy=Last Modified by
userportal.esw.label.created=Created
userportal.esw.label.deleteTranslation=Delete Translation
userportal.esw.label.regular=Regular
userportal.esw.label.acknowledgementRequired=Acknowledgement Required
userportal.esw.label.forceRead=Force Read*/
    
    }

}