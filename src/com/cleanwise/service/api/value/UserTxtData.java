package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;


public class UserTxtData extends ValueObject implements Cloneable {
    
    private String userId_;
    private String password_;
    private String customerNumber_;
    private String contactName_;
    private String emailAddress_;
    private String catalogName_;
    private String locationNumber_;
    private String memberMajorNumber_;
    private String memberMinorNumber_;
    private String companyNumber_;
    private String status_;
    
    public UserTxtData( String userId,
                        String password,
                        String customerNumber,
                        String contactName,
                        String emailAddress,
                        String catalogName,
                        String locationNumber,
                        String memberMajorNumber,
                        String memberMinorNumber,
                        String companyNumber,
                        String status) {
        userId_ = userId;
        password_ = password;
        customerNumber_ = customerNumber;
        contactName_ = contactName;
        emailAddress_ = emailAddress;
        catalogName_ = catalogName;
        locationNumber_ = locationNumber;
        memberMajorNumber_ = memberMajorNumber;
        memberMinorNumber_ = memberMinorNumber;
        companyNumber_ = companyNumber;
        status_ = status;
    }
    
    public UserTxtData() {
        userId_ = "";
        password_ = "";
        customerNumber_ = "";
        contactName_ = "";
        emailAddress_ = "";
        catalogName_ = "";
        locationNumber_ = "";
        companyNumber_ = "";
        status_ = "";
    }
    
    public Object clone() throws CloneNotSupportedException {
        UserTxtData myClone = new UserTxtData();
        
        myClone.userId_ = userId_;
        myClone.password_ = password_;
        myClone.customerNumber_ = customerNumber_;
        myClone.contactName_ = contactName_;
        myClone.emailAddress_ = emailAddress_;
        myClone.catalogName_ = catalogName_;
        myClone.locationNumber_ = locationNumber_;
        myClone.companyNumber_ = companyNumber_;
        myClone.status_ = status_;
                
        return myClone;
    }

    public String getCatalogName() {
        return catalogName_;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName_ = catalogName;
    }

    public String getContactName() {
        return contactName_;
    }
    public void setContactName(String contactName) {
        this.contactName_ = contactName;
    }

    public String getCustomerNumber() {
        return customerNumber_;
    }
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber_ = customerNumber;
    }

    public String getEmailAddress() {
        return emailAddress_;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress_ = emailAddress;
    }

    public String getLocationNumber() {
        return locationNumber_;
    }
    public void setLocationNumber(String locationNumber) {
        this.locationNumber_ = locationNumber;
    }

    public String getPassword() {
        return password_;
    }
    public void setPassword(String password) {
        this.password_ = password;
    }

    public String getUserId() {
        return userId_;
    }
    public void setUserId(String userId) {
        this.userId_ = userId;
    }

    public String getMemberMajorNumber() {
        return memberMajorNumber_;
    }

    public void setMemberMajorNumber(String memberMajorNumber) {
        this.memberMajorNumber_ = memberMajorNumber;
    }

    public String getMemberMinorNumber() {
        return memberMinorNumber_;
    }

    public void setMemberMinorNumber(String memberMinorNumber) {
        this.memberMinorNumber_ = memberMinorNumber;
    }
 
    public String getCompanyNumber() {
        return companyNumber_;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber_ = companyNumber;
    }
 
    public String getStatus() {
        return status_;
    }

    public void setsStatus(String status) {
        this.status_ = status;
    }
}    
