package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;

import org.apache.struts.action.ActionForm;

public final class AccountContactUsTopicMgrForm extends ActionForm {
    private int accountId = 0;

    private String accountName = "";

    private int topicId = 0;

    private String topicName = "";

    private PropertyDataVector topics = new PropertyDataVector();

    private PropertyData topicToEdit = null;

    private String localeCd;
    
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public PropertyDataVector getTopics() {
        return topics;
    }

    public void setTopics(PropertyDataVector topics) {
        this.topics = topics;
    }

    public PropertyData getTopicToEdit() {
        return topicToEdit;
    }

    public void setTopicToEdit(PropertyData topicToEdit) {
        this.topicToEdit = topicToEdit;
    }

    public String getLocaleCd() {
        return localeCd;
    }

    public void setLocaleCd(String localeCd) {
        this.localeCd = localeCd;
    }
}
