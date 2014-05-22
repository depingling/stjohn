package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.apps.loaders.TabFileParser;

import java.io.InputStream;
import java.util.*;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;


public class InboundAccountTxt extends InboundFlatFile {

    protected Logger log = Logger.getLogger(InboundAccountTxt.class);

    private static final String USA = "USA";
    private static final String US = "US";
    private static final String NA = "NA";

    private HashMap<String, AccountTxtData> _parsedMap;
    private Integer _storeId;
    private int _parsedLineNum;
    private Report _report;

    public InboundAccountTxt() {
       this._parsedMap = new HashMap<String, AccountTxtData>();
        this._parsedLineNum = 0;
        this._report = new Report();
    }

    /**
     * Called when the object has successfully been parsed
     */
    protected void processParsedObject(Object pParsedObject) throws Exception {
        _parsedLineNum++;

        if (pParsedObject instanceof AccountTxtData) {
            AccountTxtData txtData = (AccountTxtData) pParsedObject;
            if (isValid(txtData, _parsedLineNum)) {
                
                if(!Utility.isSet(txtData.getCountry()) || 
                    USA.equals(txtData.getCountry()) || 
                    US.equals(txtData.getCountry())) {
                    txtData.setCountry(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
                }
                
                String custMaj = txtData.getCustomerNumber();

                if (_parsedMap.get(custMaj) != null) {
                    throw new Exception("A line with Account '" + custMaj + "' already exists.");
                }
                _parsedMap.put(custMaj, txtData);
            }
        } else {
            throw new Exception("Found object of wrong type: " + pParsedObject.getClass().getName());
        }
    }

    private boolean isValid(AccountTxtData parsedData, int line) {
        boolean valid = true;

        if (!Utility.isSet(parsedData.getCompanyNumber())) {
            addError("Empty Company Number.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getCustomerNumber())) {
            addError("Empty Customer Number.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getCustomerName())) {
            addError("Empty Customer Name.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getAddress1()) && 
            !Utility.isSet(parsedData.getAddress2()) &&
            !Utility.isSet(parsedData.getAddress3())) {
            addError("Empty Address1&Address2&Address3.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getZip())) {
            addError("Empty Zip/Postal Code.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getStateProvince())) {
            addError("Empty State/Province.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getCity())) {
            addError("Empty City.Line: " + line);
            valid = false;
        }

        return valid;
    }

    private void addError(String s) {
        appendErrorMsgs(s);
    }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        if (log.isInfoEnabled()) {
            log.info("translate => BEGIN.");
        }
        try {
            doPreProcessing();
            TabFileParser parser = new TabFileParser();
            parser.parse(pIn);
            parser.cleanUpResult();
            parser.processParsedStrings(this);
            doPostProcessing();
            doErrorProcessing();
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("translate => FAILED.Process time at : " + (System.currentTimeMillis() - startTime) + " ms");
            }
            e.printStackTrace();
            setFail(true);
            throw e;
        }
        Report report = getReport();
        if (log.isInfoEnabled()) {
            log.info("translate => report: " + report);
            log.info("translate => END.Process time at : " + (System.currentTimeMillis() - startTime) + " ms");
        }
    }

    private void doErrorProcessing() throws Exception {
        Vector errors = getErrorMsgs();
        if (errors.size() > 0) {
            if (log.isInfoEnabled()) {
                log.info("doErrorProcessing errors:" + getFormatedErrorMsgs());
            }
            String errorMessage = "Errors:" + getFormatedErrorMsgs();
            throw new Exception(errorMessage);
        }
    }

    private void doPreProcessing() throws Exception {
        _storeId = getStoreId();
        if (log.isInfoEnabled()) {
            log.info("doPreProcessing => Store Id: " + _storeId);
        }
    }

    protected void doPostProcessing() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("doPostProcessing => BEGIN.");
            log.info("doPostProcessing => parsedMap.size:" + _parsedMap.size());
        }

        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Catalog catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();
        PropertyService propertyEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
        Report report = getReport();
        Integer storeId = getStoreId();
        
        Set<String> custMajsSet = _parsedMap.keySet();
        for (String custMaj : custMajsSet) {
            try {
                AccountTxtData accountTxtData = _parsedMap.get(custMaj);
                correctAccountTxtData(accountTxtData);
                ///
                Integer accountId = getAccountIdByCustmaj(storeId, accountEjb, custMaj);
                if (accountId == null) {
                    if (log.isInfoEnabled()) {
                        log.info("[doPostProcessing] Try to add new Account with CustMaj=" + 
                            accountTxtData.getCustomerNumber() + " and Name=" + 
                            accountTxtData.getCustomerName());
                    }
                    addNewAccount(accountEjb, catalogEjb, propertyEjb, _storeId, accountTxtData);
                    if (log.isInfoEnabled()) {
                        log.info("[doPostProcessing] A new Account has been created.");
                    }
                    report.setAdded(report.getAdded() + 1);
                    continue;
                }
                ///
                AccountData accountDataFromDb = 
                    accountEjb.getAccount(accountId.intValue(), _storeId);
                
                /*** check, are there records in the "catalog" and "catalog_assoc" tables
                 * for the EXISTING(NOT NEW!) Account; if there are no records, add them
                 */
                checkAccountCatalogs(accountEjb, catalogEjb, propertyEjb, _storeId, accountTxtData, accountId);
                /**********************************************************************/
                
                if (checkAccountDataToUpdate(accountTxtData, accountDataFromDb)) {
                    if (log.isInfoEnabled()) {
                        log.info("[doPostProcessing] Try to update Account with CustMaj=" + 
                            accountTxtData.getCustomerNumber() + " and Name=" + 
                            accountTxtData.getCustomerName());
                    }
                    updateAccount(accountEjb, accountTxtData, accountDataFromDb);
                    if (log.isInfoEnabled()) {
                        log.info("[doPostProcessing] An Account has been updated.");
                    }
                    report.setUpdated(report.getUpdated() + 1);
                } else {
                    report.setNotchanged(report.getNotchanged() + 1);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                addError(ex.getMessage());
            }
        }
        if (log.isInfoEnabled()) {
            log.info("doPostProcessing => END.");
        }
    }

    private Integer getAccountIdByCustmaj(Integer storeId,Account accountEjb, String custMaj) {
        List<Integer> accountIds = null;
        try {
        	BusEntitySearchCriteria bcrit = new BusEntitySearchCriteria();
        	IdVector storeIds = new IdVector();
        	storeIds.add(storeId);
        	bcrit.setStoreBusEntityIds(storeIds);
        	bcrit.addPropertyCriteria(RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, custMaj);
        	accountIds = Utility.toIdVector(accountEjb.getAccountBusEntByCriteria(bcrit));
        	//accountIds = propertyEjb.getBusEntityVector(
             //   RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, custMaj);
        } catch (RemoteException ex) {
            log.error("Remote Exception occured during getting account ids by CUST_MAJ", ex);
        }
        if (accountIds == null || accountIds.size() < 1) {
        	if (log.isInfoEnabled()) {
                log.info("No accounts found with CUST_MAJ: '" + 
                    custMaj + "'.");
            }
            return null;
        }
        return accountIds.get(0);
    }

    private void correctAccountTxtData(AccountTxtData accountTxtData) {
        if (accountTxtData == null) {
            return;
        }
        String address1 = accountTxtData.getAddress1();
        String address2 = accountTxtData.getAddress2();
        String address3 = accountTxtData.getAddress3();
        if (Utility.isSet(address1) || Utility.isSet(address2) || Utility.isSet(address3)) {
            if (!Utility.isSet(address1)) {
                if (Utility.isSet(address2)) {
                    accountTxtData.setAddress1(address2);
                    accountTxtData.setAddress2("");
                } else if (Utility.isSet(address3)) {
                    accountTxtData.setAddress1(address3);
                    accountTxtData.setAddress3("");
                }
            }
            if (!Utility.isSet(address2)) {
                if (Utility.isSet(address3)) {
                    accountTxtData.setAddress2(address3);
                    accountTxtData.setAddress3("");
                }
            }
        }
    }

    private void fillAccountDataByTxtData(AccountData accountData, 
        AccountTxtData accountTxtData) {

    	if (!Utility.isSet(accountData.getBudgetTypeCd())) {
            accountData.setBudgetTypeCd(RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_PERIOD);
        }

        BusEntityData busEntity = accountData.getBusEntity();
        if (!Utility.isSet(busEntity.getBusEntityStatusCd())) {
            busEntity.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        }
        if (!Utility.isSet(busEntity.getWorkflowRoleCd())) {
            busEntity.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        }
        if (!Utility.isSet(busEntity.getLocaleCd())) {
            busEntity.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
        }
        if (!strNN(busEntity.getShortDesc()).equals(strNN(accountTxtData.getCustomerName()))) {
            busEntity.setShortDesc(accountTxtData.getCustomerName());
        }

        PropertyData accountType = accountData.getAccountType();
        if (!Utility.isSet(accountType.getValue())) {
            accountType.setValue(RefCodeNames.ACCOUNT_TYPE_CD.OTHER);
        }

        AddressData primaryAddress = accountData.getPrimaryAddress();
        if (!strNN(primaryAddress.getAddress1()).equals(strNN(accountTxtData.getAddress1()))) {
            primaryAddress.setAddress1(accountTxtData.getAddress1());
        }
        if (!strNN(primaryAddress.getAddress2()).equals(strNN(accountTxtData.getAddress2()))) {
            primaryAddress.setAddress2(accountTxtData.getAddress2());
        }
        if (!strNN(primaryAddress.getAddress3()).equals(strNN(accountTxtData.getAddress3()))) {
            primaryAddress.setAddress3(accountTxtData.getAddress3());
        }
        if (!strNN(primaryAddress.getCity()).equals(strNN(accountTxtData.getCity()))) {
            primaryAddress.setCity(accountTxtData.getCity());
        }
        if (!strNN(primaryAddress.getStateProvinceCd()).equals(strNN(accountTxtData.getStateProvince()))) {
            primaryAddress.setStateProvinceCd(accountTxtData.getStateProvince());
        }
        if (!strNN(primaryAddress.getPostalCode()).equals(strNN(accountTxtData.getZip()))) {
            primaryAddress.setPostalCode(accountTxtData.getZip());
        }
        if (!strNN(primaryAddress.getCountryCd()).equals(strNN(accountTxtData.getCountry()))) {
            primaryAddress.setCountryCd(accountTxtData.getCountry());
        }
        if (!Utility.isSet(primaryAddress.getName1())) {
            primaryAddress.setName1(NA);
        }
        if (!Utility.isSet(primaryAddress.getName2())) {
            primaryAddress.setName2(NA);
        }

        EmailData primaryEmail = accountData.getPrimaryEmail();
        if (!Utility.isSet(primaryEmail.getShortDesc())) {
            primaryEmail.setShortDesc("Primary Email");
        }
        if (!Utility.isSet(primaryEmail.getEmailAddress())) {
            primaryEmail.setEmailAddress(NA);
        }
        
        PhoneData primaryPhone = accountData.getPrimaryPhone();
        if (!Utility.isSet(primaryPhone.getShortDesc())) {
            primaryPhone.setShortDesc("Primary Phone");
        }
        if (!Utility.isSet(primaryPhone.getPhoneNum())) {
            primaryPhone.setPhoneNum(NA);
        }

        PhoneData primaryFax = accountData.getPrimaryFax();
        if (!Utility.isSet(primaryFax.getShortDesc())) {
            primaryFax.setShortDesc("Primary Fax");
        }
        if (!Utility.isSet(primaryFax.getPhoneNum())) {
            primaryFax.setPhoneNum(NA);
        }

        AddressData billingAddress = accountData.getBillingAddress();
        if (!Utility.isSet(billingAddress.getAddress1())) {
            billingAddress.setAddress1(accountTxtData.getAddress1());
        }
        if (!Utility.isSet(billingAddress.getAddress2())) {
            billingAddress.setAddress2(accountTxtData.getAddress2());
        }
        if (!Utility.isSet(billingAddress.getAddress3())) {
            billingAddress.setAddress3(accountTxtData.getAddress3());
        }
        if (!Utility.isSet(billingAddress.getCity())) {
            billingAddress.setCity(accountTxtData.getCity());
        }
        if (!Utility.isSet(billingAddress.getCountryCd())) {
            billingAddress.setCountryCd(accountTxtData.getCountry());
        }
        if (!Utility.isSet(billingAddress.getStateProvinceCd())) {
            billingAddress.setStateProvinceCd(accountTxtData.getStateProvince());
        }
        if (!Utility.isSet(billingAddress.getPostalCode())) {
            billingAddress.setPostalCode(accountTxtData.getZip());
        }

        PhoneData orderPhone = accountData.getOrderPhone();
        if (!Utility.isSet(orderPhone.getShortDesc())) {
            orderPhone.setShortDesc("Order Phone");
        }
        if (!Utility.isSet(orderPhone.getPhoneNum())) {
            orderPhone.setPhoneNum(NA);
        }

        PhoneData orderFax = accountData.getOrderFax();
        if (!Utility.isSet(orderFax.getShortDesc())) {
            orderFax.setShortDesc("Order Fax");
        }
        if (!Utility.isSet(orderFax.getPhoneNum())) {
            orderFax.setPhoneNum(NA);
        }
        List dataFieldProps = new ArrayList();
        if(Utility.isSet(accountTxtData.getCompanyNumber())){
        	PropertyData p = PropertyData.createValue();
        	p.setShortDesc("Company Number:");
            p.setValue(accountTxtData.getCompanyNumber());
            p.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            p.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
            p.setBusEntityId(accountData.getAccountId());
            BusEntityFieldDataElement bef = new BusEntityFieldDataElement(p);
            dataFieldProps.add(bef);
        }
        
        if(Utility.isSet(accountTxtData.getAutoOrderFactor())){
        	accountData.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR, 
        			accountTxtData.getAutoOrderFactor(),"InboundAccount");
        }else{
        	if(!Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR)))
        	accountData.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR, 
        			"0.500001","InboundAccount");
        }
        if(Utility.isSet(accountTxtData.getCustomerNumber())){
        	PropertyData p = PropertyData.createValue();
        	p.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ);
            p.setValue(accountTxtData.getCustomerNumber());
            p.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            p.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
            p.setBusEntityId(accountData.getAccountId());
            BusEntityFieldDataElement bef = new BusEntityFieldDataElement(p);
            dataFieldProps.add(bef);
        }
        // NSC Customer Number for Kranz
        if(Utility.isSet(accountTxtData.getNscCustomerNumber())){
        	PropertyData p = PropertyData.createValue();
        	p.setShortDesc("NSC Customer Number");
            p.setValue(accountTxtData.getNscCustomerNumber());
            p.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            p.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
            p.setBusEntityId(accountData.getAccountId());
            BusEntityFieldDataElement bef = new BusEntityFieldDataElement(p);
            dataFieldProps.add(bef);
        }
        if(dataFieldProps!=null && dataFieldProps.size()>0){
        	accountData.setDataFieldProperties(dataFieldProps);
        }
        
        accountData.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS, 
    			"com.cleanwise.view.utils.pdf.PdfOrderNetSupply","InboundAccount");
        accountData.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS, 
    			"com.cleanwise.view.utils.pdf.PdfOrderStatusNetSupply","InboundAccount");
    }

    private void addNewAccount(Account accountEjb, Catalog catalogEjb, 
        PropertyService propertyEjb, int storeId, 
        AccountTxtData accountTxtData) throws Exception {
        AccountData accountData = AccountData.createValue();
        fillAccountDataByTxtData(accountData, accountTxtData);
        AccountData newAccountData = accountEjb.addAccount(accountData, storeId);

        CatalogData catalog = CatalogData.createValue();
        catalog.setShortDesc("Account Catalog " + String.valueOf(newAccountData.getAccountId()));
        catalog.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        CatalogRequestData catalogRequestData = catalogEjb.addCatalog(catalog, 
            storeId, "InboundAccount");
        catalogEjb.addCatalogAssoc(catalogRequestData.getCatalogId(), 
            newAccountData.getAccountId(), "InboundAccount");

        propertyEjb.setBusEntityProperty(newAccountData.getAccountId(), 
            RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, accountTxtData.getCustomerNumber());
        
    }

    private boolean checkAccountDataToUpdate(AccountTxtData accountTxtData, 
        AccountData accountData) throws Exception {
        BusEntityData busEntity = accountData.getBusEntity();
        List<PropertyData> dataFieldProperties = accountData.getDataFieldProperties();
    			
        if (!strNN(busEntity.getShortDesc()).equals(strNN(accountTxtData.getCustomerName()))) {
            return true;
        }
        AddressData primaryAddress = accountData.getPrimaryAddress();
        if (!strNN(primaryAddress.getAddress1()).equals(strNN(accountTxtData.getAddress1()))) {
            return true;
        }
        if (!strNN(primaryAddress.getAddress2()).equals(strNN(accountTxtData.getAddress2()))) {
            return true;
        }
        if (!strNN(primaryAddress.getAddress3()).equals(strNN(accountTxtData.getAddress3()))) {
            return true;
        }
        if (!strNN(primaryAddress.getCity()).equals(strNN(accountTxtData.getCity()))) {
            return true;
        }
        if (!strNN(primaryAddress.getStateProvinceCd()).equals(strNN(accountTxtData.getStateProvince()))) {
            return true;
        }
        if (!strNN(primaryAddress.getPostalCode()).equals(strNN(accountTxtData.getZip()))) {
            return true;
        }
        if (!strNN(primaryAddress.getCountryCd()).equals(strNN(accountTxtData.getCountry()))) {
            return true;
        }
        if (!strNN(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR)) 
        		.equals(strNN(accountTxtData.getAutoOrderFactor()))
        		|| !Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR))){
        	return true;
        }
        //Nsc Customer Number for Kranz
        if (Utility.isSet(accountTxtData.getNscCustomerNumber())){
	        if (dataFieldProperties != null && dataFieldProperties.size()>0){
	        	for(Object oProp : dataFieldProperties){
	        		PropertyData prop = (PropertyData)oProp;
	        		if (prop != null && "NSC Customer Number".equals(prop.getShortDesc()) &&
	        			!accountTxtData.getNscCustomerNumber().equals(prop.getValue())	){
	        			return true;
	        		}
	        	}
	        }
        }
        return false;
    }

    private void updateAccount(Account accountEjb, AccountTxtData accountTxtData, 
        AccountData accountDataFromDb) throws Exception {
        fillAccountDataByTxtData(accountDataFromDb, accountTxtData);
        accountEjb.updateAccount(accountDataFromDb);
    }

    private void checkAccountCatalogs(Account accountEjb, Catalog catalogEjb, PropertyService propertyEjb, Integer _storeId, AccountTxtData accountTxtData, Integer accountId)
    	throws Exception {
    	
    	AccountData accountData = AccountData.createValue();
        fillAccountDataByTxtData(accountData, accountTxtData);

    	log.info(".checkAccountCatalogs() => Store Id = " + _storeId);

    	log.info(".checkAccountCatalogs() => Account Id = " + accountId.intValue());
    	
    	int return_code;
        return_code = catalogEjb.checkCatalogAssocExist(accountId.intValue());
        log.info(".checkAccountCatalogs() => return_code = " + return_code);
    	
    	if (return_code == 1) { //does Account Catalog exist (is there a corresponding entry in the catalog_assoc table) ?
    	   //entry in the catalog_assoc table does not exist => create entry in the catalog_assoc and catalog tables
           CatalogData catalog = CatalogData.createValue();
           catalog.setShortDesc("Account Catalog " + String.valueOf(accountId.intValue()));
           catalog.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
           catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
           CatalogRequestData catalogRequestData = catalogEjb.addCatalog(catalog, 
        	   _storeId.intValue(), "InboundAccount");

           catalogEjb.addCatalogAssoc(catalogRequestData.getCatalogId(), 
            		accountId.intValue(), "InboundAccount");
           
           propertyEjb.setBusEntityProperty(accountId.intValue(), 
                   RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, accountTxtData.getCustomerNumber());
    	}

    }
    
    private String strNN(String val) {
        return Utility.strNN(val);
    }

    public Integer getStoreId() throws Exception {
        TradingPartner partnerEjb = APIAccess.getAPIAccess().getTradingPartnerAPI();
        TradingPartnerData partner = translator.getPartner();
        if (partner == null) {
            throw new IllegalArgumentException("Trading Partner ID cann't be determined");
        }

        HashMap assocMap = partnerEjb.getMapTradingPartnerAssocIds(partner.getTradingPartnerId());
        IdVector storeIds = (IdVector) assocMap.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        if (storeIds == null || storeIds.isEmpty()) {
            throw new IllegalArgumentException("Trading partner is not associated with any store. " +
                    "Trading parther id: " +
                    partner.getTradingPartnerId());
        }

        if (storeIds.size() > 1) {
            throw new IllegalArgumentException("Trading partner associated with multiple stores. " +
                    "Trading parther id: " +
                    partner.getTradingPartnerId());
        }
        return (Integer) storeIds.get(0);
    }

    public class Report {
        int added      = 0;
        int removed    = 0;
        int updated    = 0;
        int notchanged = 0;

        public int getAdded() {
            return added;
        }

        public void setAdded(int added) {
            this.added = added;
        }

        public int getRemoved() {
            return removed;
        }

        public void setRemoved(int removed) {
            this.removed = removed;
        }

        public int getUpdated() {
            return updated;
        }

        public void setUpdated(int updated) {
            this.updated = updated;
        }

        public int getNotchanged() {
            return notchanged;
        }

        public void setNotchanged(int notchanged) {
            this.notchanged = notchanged;
        }

        public String toString() {
            return  "Not changed:" + notchanged + ", " +
                    "Updated: " + updated + ", " + "Removed:" + removed + ", " + "Added:" + added;
        }
    }

    public Report getReport() {
        return _report;
    }

    public void setReport(Report report) {
        this._report = report;
    }

}
