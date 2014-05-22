package com.espendwise.view.forms.esw;



public class RemoteAccessMgrForm extends EswForm {

    private String _serviceTicketNumbers;
    private String _context;
    private int _siteId;
    private String _backUri;
    private String _serviceTicketDetailUri;
    private int _shopSiteId;

    public void setServiceTicketNumbers(String pServiceTicketNumbers) {
        _serviceTicketNumbers = pServiceTicketNumbers;
    }

    public String getServiceTicketNumbers() {
        return _serviceTicketNumbers;
    }

    public String getContext() {
        return _context;
    }

    public void setContext(String pContext) {
        _context = pContext;
    }

    public void setSiteId(int pSiteId) {
        _siteId = pSiteId;
    }

    public int getSiteId() {
        return _siteId;
    }

    public void setBackUri(String pBackUri) {
        _backUri = pBackUri;
    }

    public void setServiceTicketDetailUri(String pServiceTicketDetailUri) {
        _serviceTicketDetailUri = pServiceTicketDetailUri;
    }

    public String getBackUri() {
        return _backUri;
    }

    public String getServiceTicketDetailUri() {
        return _serviceTicketDetailUri;
    }

    public int getShopSiteId() {
        return _shopSiteId;
    }

    public void setShopSiteId(int pShopSiteId) {
        _shopSiteId = pShopSiteId;
    }
}
