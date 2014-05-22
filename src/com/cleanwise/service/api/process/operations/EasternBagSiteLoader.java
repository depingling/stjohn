package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import org.apache.log4j.*;

/**
 */
public class EasternBagSiteLoader {
    private static final Logger log = Logger.getLogger(EasternBagSiteLoader.class);
    private static final String className = "EasternBagSiteLoader";

    public void process(EBagIn101View inData, int storeId) throws Exception {
        if (objectReadyForProcess(inData)) {
            EBagIn101ComponentView in101Account = inData.getAccount();
            EBagIn101ComponentViewVector in101Sites = inData.getSites();
            int accountId = processOfAccount(in101Account, storeId);
            processOfSites(accountId, in101Sites, storeId);
        }
    }
    


    private void processOfSites(int accountId, EBagIn101ComponentViewVector in101Sites, int storeId) throws Exception {
        SiteDataVector processedSites = new SiteDataVector();
        Iterator it = in101Sites.iterator();
        while (it.hasNext()) {
            try {
                EBagIn101ComponentView in101Site = (EBagIn101ComponentView) it.next();
                processOfSite(accountId, in101Site, storeId);
            } catch (Exception e) {
                error(e.getMessage(),e);
                throw e;
            }
        }

    }

    private SiteData processOfSite(int accountId, EBagIn101ComponentView in101Site, int storeId) throws Exception {
        if (accountId == 0) {
        	accountId = getCurrentAccount(in101Site.getArAttribute(), storeId);
        	if (accountId == 0){
        		throw new Exception("Failed to process site for site ref number: " + in101Site.getRefNum() + ". Account not exists for Account Reference Number: " + in101Site.getArAttribute());
        	}
        }

       

        SiteData site = null;
        String siteRefNum = in101Site.getRefNum();
        //gets site if exist
        if (Utility.isSet(siteRefNum)) {
            site = getSiteIfExist(siteRefNum, accountId);
            if (site == null && accountId > 0) {
                site = createNewSite(in101Site, accountId, storeId);
            } else if (site != null){
                site = updateSite(in101Site, site, storeId);
            }
        }

        return site;
    }

    private SiteData updateSite(EBagIn101ComponentView in101Site, SiteData currentSite, int storeId) throws Exception {
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

        if (!in101Site.getBusEntityName().equals(currentSite.getBusEntity().getShortDesc())) {
            currentSite.getBusEntity().setShortDesc(in101Site.getBusEntityName());
        }

        AddressData currAddress = currentSite.getSiteAddress();
        if (currAddress != null && in101Site.getPrimaryAddress() != null) {

        	if (!Utility.isEqual(currAddress.getAddress1(), in101Site.getPrimaryAddress().getAddress1())){
                currAddress.setAddress1(in101Site.getPrimaryAddress().getAddress1());
            }
        			
	        if (!Utility.isEqual(currAddress.getAddress2(), in101Site.getPrimaryAddress().getAddress2())){
	            currAddress.setAddress2(in101Site.getPrimaryAddress().getAddress2());
	        }
	        
	        if (!Utility.isEqual(currAddress.getCity(), in101Site.getPrimaryAddress().getCity())){
	            currAddress.setCity(in101Site.getPrimaryAddress().getCity());
	        }

	        if (!Utility.isEqual(currAddress.getPostalCode(), in101Site.getPrimaryAddress().getPostalCode())){
	            currAddress.setPostalCode(in101Site.getPrimaryAddress().getPostalCode());
	        }
            
	        if (!Utility.isEqual(currAddress.getStateProvinceCd(), in101Site.getPrimaryAddress().getStateProvinceCd())){
                currAddress.setStateProvinceCd(in101Site.getPrimaryAddress().getStateProvinceCd());
            }
        }

        try {
        	currentSite = siteEjb.updateSite(currentSite);
        } catch (DuplicateNameException ne) {
        	currentSite.getBusEntity().setShortDesc(currentSite.getBusEntity().getShortDesc() + " - " + in101Site.getRefNum());
        	currentSite = siteEjb.updateSite(currentSite);
        }

        return currentSite;
    }

    private SiteData createNewSite(EBagIn101ComponentView in101Site, int accountId, int storeId) throws Exception {
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        CatalogInformation catInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
        Catalog catEjb = APIAccess.getAPIAccess().getCatalogAPI();

        SiteData site = SiteData.createValue();
        PropertyDataVector props = site.getMiscProperties();

        BusEntityData busEntityData = BusEntityData.createValue();
        busEntityData.setBusEntityTypeCd(in101Site.getBusEntityTypeCd());
        busEntityData.setShortDesc(in101Site.getBusEntityName());
        busEntityData.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        busEntityData.setWorkflowRoleCd("UNKNOWN");
        busEntityData.setLocaleCd("en_US");
        busEntityData.setAddBy(className);
        busEntityData.setModBy(className);

        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
        nprop.setValue(in101Site.getRefNum());
        nprop.setAddBy(className);
        nprop.setModBy(className);
        props.add(nprop);

        in101Site.getPrimaryAddress().setAddBy(className);
        in101Site.getPrimaryAddress().setModBy(className);

        site.setBusEntity(busEntityData);
        site.setSiteAddress(in101Site.getPrimaryAddress());

        try {
        	site = siteEjb.addSite(site, accountId);
        }catch (DuplicateNameException e){// two different site with same name
        	site.getBusEntity().setShortDesc(site.getBusEntity().getShortDesc() + "-" + in101Site.getRefNum());
        	site = siteEjb.addSite(site, accountId);
        }

        return site;
    }

    private SiteData getSiteIfExist(String siteRefNum, int accountId)
    throws Exception{
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteDataVector siteDV =
           siteEjb.getSiteByProperty(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER, siteRefNum, accountId);
                    
        if (siteDV.size() > 1) {
            throw new Exception("Multiple Site.Inbound Site Reference Number: " + siteRefNum + ".Account Id: " + accountId);
        } else if (siteDV.size() == 1) {
            return (SiteData) siteDV.get(0);
        }else{
        	return null;
        }
    }
    
    private boolean objectReadyForProcess(EBagIn101View inData) {
        return true;
    }

    private int processOfAccount(EBagIn101ComponentView inData, int storeId) throws Exception {
        int accountId = 0;
        if(inData!=null){
            String distAcctRefNum = inData.getRefNum();
            //gets  account if exist
            if (Utility.isSet(distAcctRefNum)) {
                accountId = getCurrentAccount(distAcctRefNum, storeId);
                if (accountId == 0) {
                    accountId = createNewAccount(inData, storeId);
                } else {
                	accountId = updateAccount(inData, accountId, storeId);
                }
            }
        }
        return accountId;
    }

    private int updateAccount(EBagIn101ComponentView inData, int accountId, int storeId) throws Exception {
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        
        AccountData currentAccount = accountEjb.getAccount(accountId, storeId);
        
        String accountName = getAccountName(inData);

        if (!accountName.equals(currentAccount.getBusEntity().getShortDesc())) {
            currentAccount.getBusEntity().setShortDesc(accountName);
        }
        
        currentAccount.setDistributorAccountRefNum(inData.getRefNum());

        AddressData billingAddress = currentAccount.getBillingAddress();
        AddressData primaryAddress = currentAccount.getPrimaryAddress();
        
        if (billingAddress != null && inData.getPrimaryAddress() != null) {

        	if (!Utility.isEqual(billingAddress.getAddress1(), inData.getPrimaryAddress().getAddress1())){
                billingAddress.setAddress1(inData.getPrimaryAddress().getAddress1());
                primaryAddress.setAddress1(inData.getPrimaryAddress().getAddress1());
            }

        	if (!Utility.isEqual(billingAddress.getAddress2(), inData.getPrimaryAddress().getAddress2())){
                billingAddress.setAddress2(inData.getPrimaryAddress().getAddress2());
                primaryAddress.setAddress2(inData.getPrimaryAddress().getAddress2());
            }

        	if (!Utility.isEqual(billingAddress.getCity(), inData.getPrimaryAddress().getCity())){
                billingAddress.setCity(inData.getPrimaryAddress().getCity());
                primaryAddress.setCity(inData.getPrimaryAddress().getCity());
            }

        	if (!Utility.isEqual(billingAddress.getPostalCode(), inData.getPrimaryAddress().getPostalCode())){
                billingAddress.setPostalCode(inData.getPrimaryAddress().getPostalCode());
                primaryAddress.setPostalCode(inData.getPrimaryAddress().getPostalCode());
            }
            
        	if (!Utility.isEqual(billingAddress.getStateProvinceCd(), inData.getPrimaryAddress().getStateProvinceCd())){
                billingAddress.setStateProvinceCd(inData.getPrimaryAddress().getStateProvinceCd());
                primaryAddress.setStateProvinceCd(inData.getPrimaryAddress().getStateProvinceCd());
            }
            
			String country = inData.getPrimaryAddress().getCountryCd();
			if(!Utility.isSet(country)) {
				country = RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;
			}
			if (!country.equals(billingAddress.getCountryCd())) {
                billingAddress.setCountryCd(country);
                primaryAddress.setCountryCd(country);
            }
        }

        accountEjb.updateAccount(currentAccount);

        return accountId;

    }

    private int createNewAccount(EBagIn101ComponentView inData, int storeId) throws Exception {
    	
        PropertyService propServEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
        String defaultAccountIdStr = null;
        try {
        	defaultAccountIdStr = propServEjb.getBusEntityProperty(storeId,
                RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_PROPERTY_ACCOUNT);
        }catch (DataNotFoundException e){
        	throw new Exception("RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_PROPERTY_ACCOUNT is not set for store " + storeId);
        }
        int cloneAccountId = new Integer(defaultAccountIdStr).intValue();
        
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        int accountId = accountEjb.cloneAccount(cloneAccountId, storeId, getAccountName(inData), className);
        
        updateAccount(inData, accountId, storeId);        
        return accountId;

    }

    private int getCurrentAccount(String distAcctRefNum, int storeId) throws Exception {

        if (Utility.isSet(distAcctRefNum)) {

            Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();

            BusEntitySearchCriteria criteria = new BusEntitySearchCriteria();
            IdVector storeIds = new IdVector();
            storeIds.add(new Integer(storeId));
            criteria.setStoreBusEntityIds(storeIds);
            criteria.addPropertyCriteria(RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM, distAcctRefNum);

            AccountViewVector accounts = accountEjb.getAccountsViewList(criteria);

            if (accounts.size() > 1) {
                throw new Exception("Multiple Account.Inbound Distributor Account Reference Number: " + distAcctRefNum + ".Store Id: " + storeId);
            } else if (accounts.size() == 1) {
                return ((AccountView) accounts.get(0)).getAcctId();
            }
        }
        return 0;
    }

    private String getAccountName(EBagIn101ComponentView inData){
    	return inData.getBusEntityName() + " - " + inData.getRefNum();
    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }
}
