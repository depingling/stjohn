package com.espendwise.view.forms.esw;


public class StoreMessageDetailForm extends EswForm {
    private int storeMessageDetailId;
    private String messageTitle;
    private String languageCd;
    private String country;
    private String messageAuthor;
    private String messageBody;
    
    public StoreMessageDetailForm(int storeMessageDetailId){
        this.storeMessageDetailId = storeMessageDetailId;
    }
    public int getStoreMessageDetailId() {
		return storeMessageDetailId;
	}

	public void setStoreMessageDetailId(int storeMessageDetailId) {
		this.storeMessageDetailId = storeMessageDetailId;
	}

	public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getLanguageCd() {
        return languageCd;
    }

    public void setLanguageCd(String languageCd) {
        this.languageCd = languageCd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMessageAuthor() {
        return messageAuthor;
    }

    public void setMessageAuthor(String messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public boolean getIsNew() {
       return (storeMessageDetailId == 0);
    }
    

}


