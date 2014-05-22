package com.cleanwise.service.apps.dataexchange;

import org.apache.log4j.Logger;


import java.util.*;
import java.io.InputStream;
import java.rmi.RemoteException;

import com.cleanwise.service.api.value.NSCMasterAcctView;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.loaders.TabFileParser;


public class InboundNSCMasterAcctLoader extends InboundFlatFile {

    protected Logger log = Logger.getLogger(InboundNSCMasterAcctLoader.class);

    /**
     * Input
     * 119	380	075663	119	380	075663	1
     * 119	380	075663	119	379	000177	1
     * 
     * <CUST_MAJ of user,HashMap<USER_ID_CODE,List<CUST_MAJ of site+SITE_REFERENCE_NUMBERs>>>
     * <380, <380*075663, <380/075663, 379/000179>>>
     */
    
    
    private HashMap<String, HashMap<String, List<String>>> parsedMap;

    private int storeId;
    private int parsedLineNum;

    private int addCount;
    private int removeCount;

    public InboundNSCMasterAcctLoader() {
        this.parsedMap     = new HashMap<String, HashMap<String, List<String>>>();
        this.parsedLineNum = 0;
        this.addCount      = 0;
        this.removeCount   = 0;
    }

    protected void processParsedObject(Object pParsedObject) throws Exception {

        parsedLineNum++;

        if (pParsedObject instanceof NSCMasterAcctView) {

            NSCMasterAcctView parsedData = (NSCMasterAcctView) pParsedObject;
            if (isValid(parsedLineNum, parsedData)) {

            	HashMap<String, List<String>> userMap = parsedMap.get(parsedData.getCustMajOfUser());
                if (userMap == null) {
                    userMap = new HashMap<String, List<String>>();
                }

                String clwUserCode = getClwUserCode(parsedData.getCustMajOfUser(), parsedData.getUserCode());

                List<String> siteLinksList = userMap.get(clwUserCode);
                if (siteLinksList == null) {
                	siteLinksList = new ArrayList<String>();
                }
                siteLinksList.add(parsedData.getCustMajOfSite()+ "/" + parsedData.getSiteRefNumber());
                userMap.put(clwUserCode, siteLinksList);

                parsedMap.put(parsedData.getCustMajOfUser(), userMap);

            }
        } else {
            throw new Exception("Found object of wrong type: " + pParsedObject.getClass().getName());
        }
    }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        try {
            doPreProcessing();
            TabFileParser parser = new TabFileParser();
            parser.parse(pIn);
            parser.cleanUpResult();
            parser.processParsedStrings(this);
            doPostProcessing();
            doErrorProcessing();
        } catch (Exception e) {
            log.info("translate => FAILED.Process time at : " + (System.currentTimeMillis() - startTime) + " ms");
            e.printStackTrace();
            setFail(true);
            throw e;
        }
        log.info("translate => Added: " +String.valueOf(addCount)+", Removed: "+String.valueOf(removeCount));
        log.info("translate => END.Process time at : " + (System.currentTimeMillis() - startTime) + " ms");
    }

    private void doErrorProcessing() throws Exception {
        Vector errors = getErrorMsgs();
        if (errors.size() > 0) {
            log.info("doErrorProcessing errors:" + getFormatedErrorMsgs());
            String errorMessage = "Errors:"+getFormatedErrorMsgs();
            throw new Exception(errorMessage);
        }
    }

    private void doPreProcessing() throws Exception {
        storeId = getStoreId();
        log.info("doPreProcessing => Store Id: " + storeId);
    }


    protected void doPostProcessing() throws Exception {

        log.info("doPostProcessing => BEGIN.");

        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        User userEjb = APIAccess.getAPIAccess().getUserAPI();

        Set<String> custMajs = getAllCustMajs();

        for (String custMaj : custMajs) { // 380

            Set<String> userCodes = getUserCodes(custMaj);
            List<String> userList = new ArrayList<String>(userCodes);

            //HashMap<USER_ID_CODE,USER_ID>
            HashMap<String, Integer> userMap = userEjb.getUserIdCodeMap(this.storeId, custMaj, userList);

            for (String userCode : userCodes) { // 380*075663

                Integer userId = userMap.get(userCode);
                if (userId == null) {
                    String errorMessage = "Unknown User";
                    String paramMessage = "CustMajofUser => " + custMaj + ", UserCode => " + userCode;
                    addError(errorMessage + "." + paramMessage);
                    continue;
                }

                HashMap<String, Integer> siteRefNumConfigMap = siteEjb.getAcrossAccountsSiteRefNumberMapConfigOnly(storeId,userId);// <380/075663, siteId>
                List<String> siteRefNumConfig = new ArrayList<String>(siteRefNumConfigMap.keySet());
                List<String> siteRefNumInbound = getSiteRefNumberInboundMap(custMaj, userCode);
                List<String> refNumSetForRemove = new ArrayList<String>(siteRefNumConfig);
                refNumSetForRemove.removeAll(siteRefNumInbound);
                if (refNumSetForRemove.size() > 0){
                	removeUserAssoc(userEjb, custMaj, userId, userCode, siteRefNumConfigMap, refNumSetForRemove);
                }

                List<String> refNumSetForAdd = new ArrayList<String>(siteRefNumInbound);
                refNumSetForAdd.removeAll(siteRefNumConfig);
                if (refNumSetForAdd.size() > 0){
                	HashMap<String, Integer> refMap = siteEjb.getAcrossAccountsSiteRefNumberMap(storeId, userId, refNumSetForAdd);
                	addUserAssoc(userEjb, userId, userCode, refMap, refNumSetForAdd);
                }
                
                
                //correctUserAccountsAssoc(userEjb, storeId, userId.intValue());
            }
        }

        log.info("doPostProcessing => END.");

    }

    private void removeUserAssoc(User userEjb,
                                 String custMaj,
                                 int userId,
                                 String userCode,
                                 HashMap<String, Integer> refMap,
                                 List<String> refSet) throws DataNotFoundException, RemoteException {

        for (String acctSiteRef : refSet) {
            Integer siteId = refMap.get(acctSiteRef);
            if (siteId == null) {
                String errorMessage = "Unknown Site";
                String paramMessage = "CustMajOfUser => " + custMaj + ", UserCode/UserId => " + userCode + "/" + userId + ", CustMajOfSite/SiteRefNum => " + acctSiteRef;
                addError(errorMessage + "." + paramMessage);
                continue;
            }

            try {
                userEjb.removeUserAssoc(userId, siteId);
                removeCount++;
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                String paramMessage = "CustMajOfUser => " + custMaj + ", UserCode/UserId => " + userCode + "/" + userId + ", CustMajOfSite/SiteRefNum => " + acctSiteRef + ", SiteId => " + siteId;
                addError(errorMessage + "." + paramMessage);
            }
        }

    }

    private void addUserAssoc(User userEjb,
                              int userId,
                              String userCode,
                              HashMap<String, Integer> refMap,
                              List<String> custMajSiteRefNums) throws DataNotFoundException, RemoteException {

        for (String custMajSiteRefNum : custMajSiteRefNums) {

            Integer siteId = refMap.get(custMajSiteRefNum);
            if (siteId == null) {
                String errorMessage = "Unknown Site";
                String paramMessage = "UserCode/UserId => " + userCode + "/" + userId + ", CustMajOfSite/SiteRefNum => " + custMajSiteRefNum;
                addError(errorMessage + "." + paramMessage);
                continue;
            }

            try {
                userEjb.addUserAssoc(userId, siteId, RefCodeNames.USER_ASSOC_CD.SITE);
                addCount++;
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                String paramMessage = "UserCode/UserId => " + userCode + "/" + userId + ", CustMajOfSite/SiteRefNum => " + custMajSiteRefNum + ", SiteId => " + siteId;
                addError(errorMessage + "." + paramMessage);
            }

        }
    }

    private List<String> getSiteRefNumberInboundMap(String custMaj, String userCode) {
        HashMap<String, List<String>> usermap = parsedMap.get(custMaj);
        if (usermap != null) {
            return usermap.get(userCode);
        }
        return null;
    }

    private Set<String> getAllCustMajs() {
        return parsedMap.keySet();
    }

    private String getClwUserCode(String custMajOfUser, String userCode) {
        return custMajOfUser + "*" + userCode;
    }

    private Set<String> getUserCodes(String custMaj) {

        HashMap<String, List<String>> usermap = parsedMap.get(custMaj);
        Set<String> userCodes = new HashSet<String>();
        if (usermap != null) {
            userCodes.addAll(usermap.keySet());
        }

        return userCodes;
    }


    private boolean isValid(int line, NSCMasterAcctView parsedData) {
        boolean valid = true;

        if (!Utility.isSet(parsedData.getCustMajOfUser())) {
            addError("Empty Customer Major Number Of User.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getCustMajOfSite())) {
            addError("Empty Customer Major Number Of Site.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getSiteRefNumber())) {
            addError("Empty Site Reference Number.Line: " + line);
            valid = false;
        }

        if (!Utility.isSet(parsedData.getUserCode())) {
            addError("Empty User Code.Line: " + line);
            valid = false;
        }

        return valid;
    }

    private void addError(String s) {
        appendErrorMsgs(s);
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

    void correctUserAccountsAssoc(User userEjb, int storeId, int userId) {
        try {
            IdVector accountIdsForUserSitesAssoc = userEjb.getAccountIdsForUserSitesAssoc(storeId, userId);
            IdVector accountIdsForUserAccountsAssoc = userEjb.getAccountIdsForUserAccountsAssoc(storeId, userId);

            if (accountIdsForUserSitesAssoc != null) {
                for (int i = 0; i < accountIdsForUserSitesAssoc.size(); ++i) {
                    Integer accountId = (Integer)accountIdsForUserSitesAssoc.get(i);
                    boolean found = false;
                    if (accountIdsForUserAccountsAssoc != null) {
                        for (int j = 0; j < accountIdsForUserAccountsAssoc.size(); ++j) {
                            Integer exAccountId = (Integer)accountIdsForUserAccountsAssoc.get(j);
                            if (accountId.intValue() == exAccountId.intValue()) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        log.info("[correctUserAccountsAssoc] Try to create association between user " + userId + " and account " + accountId);
                        userEjb.addUserAssoc(userId, accountId.intValue(), RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                    }
                }
            }

            if (accountIdsForUserAccountsAssoc != null) {
                for (int i = 0; i < accountIdsForUserAccountsAssoc.size(); ++i) {
                    Integer exAccountId = (Integer)accountIdsForUserAccountsAssoc.get(i);
                    boolean found = false;
                    if (accountIdsForUserSitesAssoc != null) {
                        for (int j = 0; j < accountIdsForUserSitesAssoc.size(); ++j) {
                            Integer accountId = (Integer)accountIdsForUserSitesAssoc.get(j);
                            if (accountId.intValue() == exAccountId.intValue()) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        log.info("[correctUserAccountsAssoc] Try to remove association between user " + userId + " and account " + exAccountId);
                        userEjb.removeUserAssoc(userId, exAccountId.intValue());
                    }
                }
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            String paramMessage = "storeId: " + storeId + ", userId: " + userId;
            addError(errorMessage + "." + paramMessage);
        }
    }

}
