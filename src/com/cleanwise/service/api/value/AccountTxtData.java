package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;


public class AccountTxtData extends ValueObject implements Cloneable {

    ///Do not remove or modify next line. It would break object DB saving 
    private static final long serialVersionUID = -260177456174918428L;

    private int    _field;
    private String _companyNumber;
    private String _customerNumber;
    private String _customerName;
    private String _contactName;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _address4;
    private String _city;
    private String _stateProvince;
    private String _zip;
    private String _country;
    private String _defaultWarehouse;
    private String _userField1;
    private String _userField2;
    private String _userField3;
    private String _userField4;
    private String _userField5;
    private String _userField6;
    private String _userCode1;
    private String _userCode2;
    private String _userCode3;    
    private String _carrierCode;
    private String _priceList;
    private String _customerClass;
    private String _customerSubclass;
    private String _sequenceCodeWH;
    private String _numberRequiredPO;
    private String _suspendFlag;
    private String _defaultShipTo;
    private String _territory;
    private String _corporateGroup;
    private String _autoOrderFactor;
    private String _nscCustomerNumber;

    public AccountTxtData(int    field,
                          String companyNumber,
                          String customerNumber,
                          String customerName,
                          String contactName,
                          String address1,
                          String address2,
                          String address3,
                          String address4,
                          String city,
                          String stateProvince,
                          String zip,
                          String country,
                          String defaultWarehouse,
                          String userField1,
                          String userField2,
                          String userField3,
                          String userField4,
                          String userField5,
                          String userField6,
                          String userCode1,
                          String userCode2,
                          String userCode3,    
                          String carrierCode,
                          String priceList,
                          String customerClass,
                          String customerSubclass,
                          String sequenceCodeWH,
                          String numberRequiredPO,
                          String suspendFlag,
                          String defaultShipTo,
                          String territory,
                          String corporateGroup,
                          String autoOrderFactor,
                          String nscCustomerNumber) {
        _field = field;
        _companyNumber = companyNumber;
        _customerNumber = customerNumber;
        _customerName = customerName;
        _contactName = contactName;
        _address1 = address1;
        _address2 = address2;
        _address3 = address3;
        _address4 = address4;
        _city = city;
        _stateProvince = stateProvince;
        _zip = zip;
        _country = country;
        _defaultWarehouse = defaultWarehouse;
        _userField1 = userField1;
        _userField2 = userField2;
        _userField3 = userField3;
        _userField4 = userField4;
        _userField5 = userField5;
        _userField6 = userField6;
        _userCode1 = userCode1;
        _userCode2 = userCode2;
        _userCode3 = userCode3;    
        _carrierCode = carrierCode;
        _priceList = priceList;
        _customerClass = customerClass;
        _customerSubclass = customerSubclass;
        _sequenceCodeWH = sequenceCodeWH;
        _numberRequiredPO = numberRequiredPO;
        _suspendFlag = suspendFlag;
        _defaultShipTo = defaultShipTo;
        _territory = territory;
        _corporateGroup = corporateGroup;
        _autoOrderFactor = autoOrderFactor;
        _nscCustomerNumber = nscCustomerNumber;
    }

    public AccountTxtData() {
        this(
             -1,   // field
             "",   // companyNumber
             "",   // customerNumber
             "",   // customerName,
             "",   // contactName,
             "",   // address1,
             "",   // address2,
             "",   // address3,
             "",   // address4,
             "",   // city,
             "",   // stateProvince,
             "",   // zip,
             "",   // country,
             "",   // defaultWarehouse,
             "",   // userField1,
             "",   // userField2,
             "",   // userField3,
             "",   // userField4,
             "",   // userField5,
             "",   // userField6,
             "",   // "", // userCode1,
             "",   // userCode2,
             "",   // userCode3,    
             "",   // carrierCode,
             "",   // priceList,
             "",   // customerClass,
             "",   // customerSubclass,
             "",   // sequenceCodeWH,
             "",   // numberRequiredPO,
             "",   // suspendFlag,
             "",   // defaultShipTo,
             "",   // territory,
             "",   // corporateGroup
             "",   // autoOrderFactor
             ""    // nscCustomerNumber
             
        );
    }

    public Object clone() throws CloneNotSupportedException {
        AccountTxtData myClone = new AccountTxtData();
        myClone._field = _field;
        myClone._companyNumber = _companyNumber;
        myClone._customerNumber = _customerNumber;
        myClone._customerName = _customerName;
        myClone._contactName = _contactName;
        myClone._address1 = _address1;
        myClone._address2 = _address2;
        myClone._address3 = _address3;
        myClone._address4 = _address4;
        myClone._city = _city;
        myClone._stateProvince = _stateProvince;
        myClone._zip = _zip;
        myClone._country = _country;
        myClone._defaultWarehouse = _defaultWarehouse;
        myClone._userField1 = _userField1;
        myClone._userField2 = _userField2;
        myClone._userField3 = _userField3;
        myClone._userField4 = _userField4;
        myClone._userField5 = _userField5;
        myClone._userField6 = _userField6;
        myClone._userCode1 = _userCode1;
        myClone._userCode2 = _userCode2;
        myClone._userCode3 = _userCode3;    
        myClone._carrierCode = _carrierCode;
        myClone._priceList = _priceList;
        myClone._customerClass = _customerClass;
        myClone._customerSubclass = _customerSubclass;
        myClone._sequenceCodeWH = _sequenceCodeWH;
        myClone._numberRequiredPO = _numberRequiredPO;
        myClone._suspendFlag = _suspendFlag;
        myClone._defaultShipTo = _defaultShipTo;
        myClone._territory = _territory;
        myClone._corporateGroup = _corporateGroup;
        myClone._autoOrderFactor = _autoOrderFactor;
        myClone._nscCustomerNumber = _nscCustomerNumber;
        return myClone;
    }

    public String getAddress1() {
        return _address1;
    }

    public void setAddress1(String address1) {
        this._address1 = address1;
    }

    public String getAddress2() {
        return _address2;
    }

    public void setAddress2(String address2) {
        this._address2 = address2;
    }

    public String getAddress3() {
        return _address3;
    }

    public void setAddress3(String address3) {
        this._address3 = address3;
    }

    public String getAddress4() {
        return _address4;
    }

    public void setAddress4(String address4) {
        this._address4 = address4;
    }

    public String getCarrierCode() {
        return _carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this._carrierCode = carrierCode;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        this._city = city;
    }

    public String getCompanyNumber() {
        return _companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this._companyNumber = companyNumber;
    }

    public String getContactName() {
        return _contactName;
    }

    public void setContactName(String contactName) {
        this._contactName = contactName;
    }

    public String getCorporateGroup() {
        return _corporateGroup;
    }

    public void setCorporateGroup(String corporateGroup) {
        this._corporateGroup = corporateGroup;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String country) {
        this._country = country;
    }

    public String getCustomerClass() {
        return _customerClass;
    }

    public void setCustomerClass(String customerClass) {
        this._customerClass = customerClass;
    }

    public String getCustomerName() {
        return _customerName;
    }

    public void setCustomerName(String customerName) {
        this._customerName = customerName;
    }

    public String getCustomerNumber() {
        return _customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this._customerNumber = customerNumber;
    }

    public String getCustomerSubclass() {
        return _customerSubclass;
    }

    public void setCustomerSubclass(String customerSubclass) {
        this._customerSubclass = customerSubclass;
    }

    public String getDefaultShipTo() {
        return _defaultShipTo;
    }

    public void setDefaultShipTo(String defaultShipTo) {
        this._defaultShipTo = defaultShipTo;
    }

    public String getDefaultWarehouse() {
        return _defaultWarehouse;
    }

    public void setDefaultWarehouse(String defaultWarehouse) {
        this._defaultWarehouse = defaultWarehouse;
    }

    public int getField() {
        return _field;
    }

    public void setField(int field) {
        this._field = field;
    }

    public String getNumberRequiredPO() {
        return _numberRequiredPO;
    }

    public void setNumberRequiredPO(String numberRequiredPO) {
        this._numberRequiredPO = numberRequiredPO;
    }

    public String getPriceList() {
        return _priceList;
    }

    public void setPriceList(String priceList) {
        this._priceList = priceList;
    }

    public String getSequenceCodeWH() {
        return _sequenceCodeWH;
    }

    public void setSequenceCodeWH(String sequenceCodeWH) {
        this._sequenceCodeWH = sequenceCodeWH;
    }

    public String getStateProvince() {
        return _stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this._stateProvince = stateProvince;
    }

    public String getSuspendFlag() {
        return _suspendFlag;
    }

    public void setSuspendFlag(String suspendFlag) {
        this._suspendFlag = suspendFlag;
    }

    public String getTerritory() {
        return _territory;
    }

    public void setTerritory(String territory) {
        this._territory = territory;
    }

    public String getUserCode1() {
        return _userCode1;
    }

    public void setUserCode1(String userCode1) {
        this._userCode1 = userCode1;
    }

    public String getUserCode2() {
        return _userCode2;
    }

    public void setUserCode2(String userCode2) {
        this._userCode2 = userCode2;
    }

    public String getUserCode3() {
        return _userCode3;
    }

    public void setUserCode3(String userCode3) {
        this._userCode3 = userCode3;
    }

    public String getUserField1() {
        return _userField1;
    }

    public void setUserField1(String userField1) {
        this._userField1 = userField1;
    }

    public String getUserField2() {
        return _userField2;
    }

    public void setUserField2(String userField2) {
        this._userField2 = userField2;
    }

    public String getUserField3() {
        return _userField3;
    }

    public void setUserField3(String userField3) {
        this._userField3 = userField3;
    }

    public String getUserField4() {
        return _userField4;
    }

    public void setUserField4(String userField4) {
        this._userField4 = userField4;
    }

    public String getUserField5() {
        return _userField5;
    }

    public void setUserField5(String userField5) {
        this._userField5 = userField5;
    }

    public String getUserField6() {
        return _userField6;
    }

    public void setUserField6(String userField6) {
        this._userField6 = userField6;
    }

    public String getZip() {
        return _zip;
    }

    public void setZip(String zip) {
        this._zip = zip;
    }

    public String getAutoOrderFactor() {
		return _autoOrderFactor;
	}

	public void setAutoOrderFactor(String autoOrderFactor) {
		this._autoOrderFactor = autoOrderFactor;
	}
 
	public String getNscCustomerNumber() {
		return _nscCustomerNumber;
	}

	public void setNscCustomerNumber(String nscCustomerNumber) {
		this._nscCustomerNumber = nscCustomerNumber;
	}

	public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("field: ").append(_field).append(", ");
        buffer.append("companyNumber: ").append(_companyNumber).append(", ");
        buffer.append("customerNumber: ").append(_customerNumber).append(", ");
        buffer.append("customerName: ").append(_customerName).append(", ");
        buffer.append("contactName: ").append(_contactName).append(", ");
        buffer.append("address1: ").append(_address1).append(", ");
        buffer.append("address2: ").append(_address2).append(", ");
        buffer.append("address3: ").append(_address3).append(", ");
        buffer.append("address4: ").append(_address4).append(", ");
        buffer.append("city: ").append(_city).append(", ");
        buffer.append("state: ").append(_stateProvince).append(", ");
        buffer.append("zip: ").append(_zip).append(", ");
        buffer.append("country: ").append(_country).append(", ");
        buffer.append("corporateGroup: ").append(_corporateGroup);
        buffer.append("autoOrderFactor: ").append(_autoOrderFactor);
        buffer.append("nscCustomerNumber: ").append(_nscCustomerNumber);
        return buffer.toString();
    }

}
