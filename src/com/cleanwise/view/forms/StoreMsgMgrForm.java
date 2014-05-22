package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
import com.cleanwise.view.utils.Constants;

public class StoreMsgMgrForm extends StorePortalBaseForm {
    private String searchField = "";
    private String searchType = Constants.NAME_BEGINS;
    private String searchPostedDateFrom;
    private String searchPostedDateTo;
    private String postedDate;
    private String endDate;
    private boolean forcedRead;
    private String howManyTimes;
    private StoreMessageDataVector messages;
    private StoreMessageData messageDetail = StoreMessageData.createValue();

    private String confSearchField = "";
    private String confSearchType = Constants.NAME_BEGINS;
    private boolean configuratedOnlyFl;
    private boolean confShowInactiveFl;
    private String[] configAccountsSelectedIds;
    private BusEntityDataVector configAccounts;
    private boolean showPreview;
    private boolean showInactive;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.forcedRead = false;
        this.configuratedOnlyFl = false;
        this.confShowInactiveFl = false;
        this.configAccountsSelectedIds = null;
        this.showPreview = false;
        this.showInactive = false;
    }

    public StoreMessageDataVector getMessages() {
        return messages;
    }

    public void setMessages(StoreMessageDataVector messages) {
        this.messages = messages;
    }

    public StoreMessageData getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(StoreMessageData messageDetail) {
        this.messageDetail = messageDetail;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isForcedRead() {
        return forcedRead;
    }

    public void setForcedRead(boolean forcedRead) {
        this.forcedRead = forcedRead;
    }

    public String getHowManyTimes() {
        return howManyTimes;
    }

    public void setHowManyTimes(String howManyTimes) {
        this.howManyTimes = howManyTimes;
    }

    public String getSearchPostedDateFrom() {
        return searchPostedDateFrom;
    }

    public void setSearchPostedDateFrom(String searchPostedDateFrom) {
        this.searchPostedDateFrom = searchPostedDateFrom;
    }

    public String getSearchPostedDateTo() {
        return searchPostedDateTo;
    }

    public void setSearchPostedDateTo(String searchPostedDateTo) {
        this.searchPostedDateTo = searchPostedDateTo;
    }

    public String getConfSearchType() {
        return confSearchType;
    }

    public void setConfSearchType(String confSearchType) {
        this.confSearchType = confSearchType;
    }

    public boolean isConfiguratedOnlyFl() {
        return configuratedOnlyFl;
    }

    public void setConfiguratedOnlyFl(boolean configuratedOnlyFl) {
        this.configuratedOnlyFl = configuratedOnlyFl;
    }

    public boolean isConfShowInactiveFl() {
        return confShowInactiveFl;
    }

    public void setConfShowInactiveFl(boolean confShowInactiveFl) {
        this.confShowInactiveFl = confShowInactiveFl;
    }

    public String getConfSearchField() {
        return confSearchField;
    }

    public void setConfSearchField(String confSearchField) {
        this.confSearchField = confSearchField;
    }

    public String[] getConfigAccountsSelectedIds() {
        return configAccountsSelectedIds;
    }

    public void setConfigAccountsSelectedIds(String[] configAccountsSelectedIds) {
        this.configAccountsSelectedIds = configAccountsSelectedIds;
    }

    public BusEntityDataVector getConfigAccounts() {
        return configAccounts;
    }

    public void setConfigAccounts(BusEntityDataVector configAccounts) {
        this.configAccounts = configAccounts;
    }

    public boolean isShowPreview() {
        return showPreview;
    }

    public void setShowPreview(boolean showPreview) {
        this.showPreview = showPreview;
    }

    public boolean isShowInactive() {
        return showInactive;
    }
    public boolean getShowInactive() {
        return showInactive;
    }

    public void setShowInactive(boolean showInactive) {
        this.showInactive = showInactive;
    }
}
