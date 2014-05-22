package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;


public class OrcaAccessMgrForm extends ActionForm {

    private String mServiceTicketNumbers;
    private String mContext;
    private int mSiteId;
    private String mBackUri;
    private String mServiceTicketDetailUri;
    private int mShopSiteId;

    public void setServiceTicketNumbers(String pServiceTicketNumbers) {
        mServiceTicketNumbers = pServiceTicketNumbers;
    }

    public String getServiceTicketNumbers() {
        return mServiceTicketNumbers;
    }

    public String getContext() {
        return mContext;
    }

    public void setContext(String pContext) {
        mContext = pContext;
    }

    public void setSiteId(int pSiteId) {
        mSiteId = pSiteId;
    }

    public int getSiteId() {
        return mSiteId;
    }

    public void setBackUri(String pBackUri) {
        mBackUri = pBackUri;
    }

    public void setServiceTicketDetailUri(String pServiceTicketDetailUri) {
        mServiceTicketDetailUri = pServiceTicketDetailUri;
    }

    public String getBackUri() {
        return mBackUri;
    }

    public String getServiceTicketDetailUri() {
        return mServiceTicketDetailUri;
    }

    public int getShopSiteId() {
        return mShopSiteId;
    }

    public void setShopSiteId(int pShopSiteId) {
        mShopSiteId = pShopSiteId;
    }
}
