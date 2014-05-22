package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;


public class SiteTxtData extends ValueObject implements Cloneable {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -260287456374978428L;
    
    private int     field_;
    private String  companyNumber_;
    private String  customerNumber_;
    private String  locationNumber_;
    private String  locationName_;
    private String  contactName_;
    private String  address1_;
    private String  address2_;
    private String  address3_;
    private String  address4_;
    private String  city_;
    private String  state_;
    private String  zip_;
    private String  country_;
    private String  sequenceCodeWH_;
    private String  carrierCode_;
    private String  territory_;
    private String  defaultWarehouse_;
    private String  nscLocationNumber_;
    private String  catalogName_;
    private String  memberNumber_;
    

    
    public SiteTxtData( int field,
                            String companyNumber,
                            String customerNumber,
                            String locationNumber,
                            String locationName,
                            String contactName,
                            String address1,
                            String address2,
                            String address3,
                            String address4,
                            String city,
                            String state,
                            String zip,
                            String  country,
                            String sequenceCodeWH,
                            String carrierCode,
                            String territory,
                            String defaultWarehouse,
                            String nscLocationNumber,
                            String catalogName,
                            String memberNumber) {
        field_ = field;
        companyNumber_ = companyNumber;
        customerNumber_ = customerNumber;
        locationNumber_ = locationNumber;
        locationName_ = locationName;
        contactName_ = contactName;
        address1_ = address1;
        address2_ = address2;
        address3_ = address3;
        address4_ = address4;
        city_ = city;
        state_ = state;
        zip_ = zip;
        country_ = country;
        sequenceCodeWH_ = sequenceCodeWH;
        carrierCode_ = carrierCode;
        territory_ = territory;
        defaultWarehouse_ = defaultWarehouse;
        nscLocationNumber_ = nscLocationNumber;
        catalogName_ = catalogName;
        memberNumber_ = memberNumber;
        
    }
    
    public SiteTxtData() {
        field_ = -1;
        companyNumber_ = "";
        customerNumber_ = "";
        locationNumber_ = "";
        locationName_ = "";
        contactName_ = "";
        address1_ = "";
        address2_ = "";
        address3_ = "";
        address4_ = "";
        city_ = "";
        state_ = "";
        zip_ = "";
        country_ = "";
        sequenceCodeWH_ = "";
        carrierCode_ = "";
        territory_ = "";
        defaultWarehouse_ = "";
        nscLocationNumber_ = "";
        catalogName_ = "";
        memberNumber_ = "";
        
    }
    
    
    public int getField() {
        return field_;
    }
    public void setField(int field) {
        field_ = field;
    }
    
    public String getCompanyNumber() {
        return companyNumber_;
    }
    public void setCompanyNumber(String companyNumber) {
        companyNumber_ = companyNumber;
    }
    
    public String getCustomerNumber() {
        return customerNumber_;
    }
    public void setCustomerNumber(String customerNumber) {
        customerNumber_ = customerNumber;
    }
    
    public String getLocationNumber() {
        return locationNumber_;
    }
    public void setLocationNumber(String locationNumber) {
        locationNumber_ = locationNumber;
    }
    
    public String getLocationName() {
        return locationName_;
    }
    public void setLocationName(String locationName) {
        locationName_ = locationName;
    }
    
    public String getContactName() {
        return contactName_;
    }
    public void setContactName(String contactName) {
        contactName_ = contactName;
    }
    
    public String getAddress1() {
        return address1_;
    }
    public void setAddress1(String address1) {
        address1_ = address1;
    }
    
    public String getAddress2() {
        return address2_;
    }
    public void setAddress2(String address2) {
        address2_ = address2;
    }
    
    public String getAddress3() {
        return address3_;
    }
    public void setAddress3(String address3) {
        address3_ = address3;
    }

    public String getAddress4() {
        return address4_;
    }
    public void setAddress4(String address4) {
        address4_ = address4;
    }
    
    public String getCity() {
        return city_;
    }
    public void setCity(String city) {
        city_ = city;
    }
    
    public String getState() {
        return state_;
    }
    public void setState(String state) {
        state_ = state;
    }
    
    public String getZip() {
        return zip_;
    }
    public void setZip(String zip) {
        zip_ = zip;
    }
    
    public String getCountry() {
        return country_;
    }
    public void setCountry(String country) {
        country_ = country;
    }
    
    public String getSequenceCodeWH() {
        return sequenceCodeWH_;
    }
    public void setSequenceCodeWH(String sequenceCodeWH) {
        sequenceCodeWH_ = sequenceCodeWH;
    }
    
    public String getCarrierCode() {
        return carrierCode_;
    }
    public void setCarrierCode(String carrierCode) {
        carrierCode_ = carrierCode;
    }
    
    public String getTerritory() {
        return territory_;
    }
    public void setTerritory(String territory) {
        territory_ = territory;
    }
    
    public String getDefaultWarehouse() {
        return defaultWarehouse_;
    }
    public void setDefaultWarehouse(String defaultWarehouse) {
        defaultWarehouse_ = defaultWarehouse;
    }

    public String getNscLocationNumber() {
        return nscLocationNumber_;
    }
    public void setNscLocationNumber(String nscLocationNumber) {
    	nscLocationNumber_ = nscLocationNumber;
    }
    public String getCatalogName() {
        return catalogName_;
    }
    public void setCatalogName(String catalogName) {
    	catalogName_ = catalogName;
    }
    public String getMemberNumber() {
        return memberNumber_;
    }
    public void setMemberNumber(String memberNumber) {
    	memberNumber_ = memberNumber;
    }
    
    public Object clone() throws CloneNotSupportedException {
        SiteTxtData myClone = new SiteTxtData();
        
        myClone.field_ = field_;
        myClone.companyNumber_ = companyNumber_;
        myClone.customerNumber_ = customerNumber_;
        myClone.locationNumber_ = locationNumber_;
        myClone.locationName_ = locationName_;
        myClone.contactName_ = contactName_;
        myClone.address1_ = address1_;
        myClone.address2_ = address2_;
        myClone.address3_ = address3_;
        myClone.address4_ = address4_;
        myClone.city_ = city_;
        myClone.state_ = state_;
        myClone.zip_ = zip_;
        myClone.country_ = country_;
        myClone.sequenceCodeWH_ = sequenceCodeWH_;
        myClone.carrierCode_ = carrierCode_;
        myClone.territory_ = territory_;
        myClone.defaultWarehouse_ = defaultWarehouse_;
        myClone.nscLocationNumber_ = nscLocationNumber_;
        myClone.catalogName_ = catalogName_;
        myClone.memberNumber_ = memberNumber_;
        
        return myClone;
    }

}
