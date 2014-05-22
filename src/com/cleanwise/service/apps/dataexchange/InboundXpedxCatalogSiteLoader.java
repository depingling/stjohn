package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.XpedxCatalogSiteView;
import com.cleanwise.service.apps.dataexchange.InboundXpedxLoaderHelper.ComparableList;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.util.*;

public class InboundXpedxCatalogSiteLoader extends InboundFlatFile {

    protected static Logger log = Logger.getLogger(InboundXpedxCatalogSiteLoader.class);

    private final List<XpedxCatalogSiteView> parsedDataList = new ArrayList<XpedxCatalogSiteView>();

    private final static String ADD_MOD_BY = "InboundXpedxCatalogSiteLoader";

    private boolean headerWasLoad = false;

    public InboundXpedxCatalogSiteLoader() {
        super.setSepertorChar('|');
    }

    protected void init() {
        super.init();
        parsedDataList.clear();
        headerWasLoad = false;
    }

    /**
     * Called when the object has successfully been parsed
     */
    protected void processParsedObject(Object pParsedObject) throws Exception {

        if (pParsedObject == null) {
            throw new IllegalArgumentException("No parsed catalog site object present");
        }

        if (headerWasLoad) {
            XpedxCatalogSiteView parsedDataItem = (XpedxCatalogSiteView) pParsedObject;
            if (!parsedDataItem.getSiteReference().equals("not setup")) {
                parsedDataList.add((XpedxCatalogSiteView) pParsedObject);
            }
        } else {
            headerWasLoad = true;
        }
    }

    @Override
    protected void doPostProcessing() throws Exception {

        log.info("doPostProcessing()=> BEGIN. ParsedDataList, Size: "+ parsedDataList.size()+" row(s)");

        Set<ComparableList> accountCatalogRefsHolder = new TreeSet<ComparableList>();
        Set<ComparableList> accountSiteRefs = new TreeSet<ComparableList>();
        Set<String> accountRefs = new TreeSet<String>();
        List<String> errors = new ArrayList<String>();

        for (XpedxCatalogSiteView parsedDataItem : parsedDataList) {

            String accountReference = parsedDataItem.getAccountReference().trim();
            String catalogReference = parsedDataItem.getCatalogReference().trim();
            String siteReference = parsedDataItem.getSiteReference().trim();

            accountRefs.add(accountReference);
            accountCatalogRefsHolder.add(ComparableList.createValue(accountReference, catalogReference));
            accountSiteRefs.add(ComparableList.createValue(accountReference, siteReference));
        }

        log.info("doPostProcessing()=> Detected Account Reference(s): " + accountRefs);
        log.info("doPostProcessing()=> Detected Catalog Reference(s): " + accountCatalogRefsHolder);
        log.info("doPostProcessing()=> Detected Site Reference(s): " + accountSiteRefs);

        Connection con = null;
        try {

            con = getConnection();

            int tradingPartnerId = getTranslator().getPartner().getTradingPartnerId();

            Map<String, Integer> accountIdsByAccountRefMap =
                    InboundXpedxLoaderHelper.getAccountIdsByAccountRef(con,
                            tradingPartnerId,
                            accountRefs,
                            errors);

            if (accountIdsByAccountRefMap.isEmpty() && !accountRefs.isEmpty()) {
                appendErrorMsgs("Not found account(s) for account reference(s) '" + accountRefs + "'.");
                throw new Exception(getFormatedErrorMsgs());
            }

            log.info("doPostProcessing()=> Found AccountID(s): " + accountIdsByAccountRefMap);

            int i = 1;

            log.info("doPostProcessing()=> Start process for each accepted account");

            for (Map.Entry<String, Integer> es : accountIdsByAccountRefMap.entrySet()) {

                log.info("doPostProcessing()=> BEGIN LOOP FOR " + es);

                Set<ComparableList> accountCatalogRefs = new TreeSet<ComparableList>();
                accountCatalogRefs.addAll(accountCatalogRefsHolder);

                String key = Utility.getFirstToken(es.getKey());

                Map<String, Integer> accountIdsByAccountRef = new TreeMap<String, Integer>();
                accountIdsByAccountRef.put(key, es.getValue());

                log.info("doPostProcessing()=> Loop index " + (i++) + " of " + accountIdsByAccountRefMap.size());
                log.info("doPostProcessing()=> AccountRef.: " + key + ", AccountID: " + es.getValue());

                Map<Integer, Integer> storeIdsByAccountId =
                        InboundXpedxLoaderHelper.getStoreIdsByAccountId(con,
                                accountIdsByAccountRef.values(),
                                errors);

               Map<ComparableList, Integer> catIdsByAccountCatRef =
                        InboundXpedxLoaderHelper.getCatalogIdsByAccountCatalogRef(con,
                                tradingPartnerId,
                                accountCatalogRefs,
                                errors);

                Map<String, Set<Integer>> catalogIdsByNameForStore =
                        InboundXpedxLoaderHelper.getCatalogIdsByNameForStore(con,
                                Utility.intNN(storeIdsByAccountId.get(es.getValue())));

                Map<ComparableList, Integer> siteIdsByAccountSiteRef =
                        InboundXpedxLoaderHelper.getSiteIdsByAccountSiteRef(con,
                                tradingPartnerId,
                                accountSiteRefs,
                                errors);

                Map<Integer, Integer> accountIdsBySiteId =
                        InboundXpedxLoaderHelper.getAccountIdsBySiteId(con,
                                siteIdsByAccountSiteRef.values(),
                                errors);

                for (ComparableList accountCatalogRef : catIdsByAccountCatRef.keySet()) {
                    accountCatalogRefs.remove(accountCatalogRef);
                }

                Iterator<ComparableList> it = accountCatalogRefs.iterator();
                while (it.hasNext()) {
                    ComparableList catRef = it.next();
                    String accountRef = (String) catRef.get(0);
                    if (!accountIdsByAccountRef.containsKey(accountRef)) {
                        it.remove();
                    }
                }

                checkStores(storeIdsByAccountId, accountIdsByAccountRef, errors);
                checkAccounts(accountIdsByAccountRefMap, accountRefs, errors);
                checkSites(siteIdsByAccountSiteRef);
                checkExistCatalogs(accountCatalogRefs, catalogIdsByNameForStore, errors);

                for (String s : errors) {
                    appendErrorMsgs(s);
                }

                String errorMessage = getFormatedErrorMsgs();
                if (Utility.isSet(errorMessage)) {
                    throw new Exception(errorMessage);
                }

                log.info("doPostProcessing()=> Creating catalogs: " + accountCatalogRefs);
                Map<ComparableList, Integer> newCatalogs =InboundXpedxLoaderHelper.createCatalogs(con, accountCatalogRefs, ADD_MOD_BY);
                 log.info("doPostProcessing()=> NEW Catalogs: " + newCatalogs.toString());
                catIdsByAccountCatRef.putAll(newCatalogs);

                Set<Integer> catalogIds = new HashSet<Integer>(catIdsByAccountCatRef.values());
                Map<Integer, Set<Integer>> busEntityIdsMappedByCatalogId =
                        InboundXpedxLoaderHelper.getBusEntIdsByCatalogId(con,
                                catalogIds,
                                errors);

                Map<ComparableList, Integer> catIdsMappedByComplexKey = new TreeMap<ComparableList, Integer>();
                List<ComparableList> complexKeys = new ArrayList<ComparableList>();
                for (Object aParsedData : parsedDataList) {

                    XpedxCatalogSiteView parsedDataItem = (XpedxCatalogSiteView) aParsedData;

                    String accountReference = parsedDataItem.getAccountReference().trim();
                    String catalogReference = parsedDataItem.getCatalogReference().trim();
                    String siteReference = parsedDataItem.getSiteReference().trim();

                    ComparableList key1 = ComparableList.createValue(accountReference, catalogReference);
                    ComparableList key2 = ComparableList.createValue(accountReference, catalogReference, siteReference);

                    catIdsMappedByComplexKey.put(key2, catIdsByAccountCatRef.get(key1));
                    complexKeys.add(key2);
                }

                InboundXpedxLoaderHelper.synchronizeCatalogAssoc(con,
                        ADD_MOD_BY,
                        busEntityIdsMappedByCatalogId,
                        complexKeys,
                        catIdsMappedByComplexKey,
                        storeIdsByAccountId,
                        accountIdsByAccountRef,
                        accountIdsBySiteId,
                        siteIdsByAccountSiteRef,
                        newCatalogs);

                log.info("doPostProcessing()=> END LOOP FOR " + es);
            }

        } finally {
            closeConnection(con);
        }

        log.info("doPostProcessing()=> END.");

    }

    private void checkStores(Map<Integer, Integer> pStoreIdsByAccountId,
                             Map<String, Integer> pAccountIdsByAccountRef,
                             List<String> pErrors) {
        for (Map.Entry<String, Integer> e : pAccountIdsByAccountRef.entrySet()) {
            if (!pStoreIdsByAccountId.containsKey(e.getValue())) {
                String accountRef = Utility.getFirstToken(e.getKey());
                pErrors.add("Not found store for account reference:'" + accountRef + "'.");
            }
        }
    }

    private void checkAccounts(Map<String, Integer> pAccountIdsByAccountRefMap,
                               Set<String> pAccountRefs,
                               List<String> pErrors) {

        for (String accountRef : pAccountRefs) {

            boolean found = false;

            for (String accountRefToken : pAccountIdsByAccountRefMap.keySet()) {
                String key = Utility.getFirstToken(accountRefToken);
                if (accountRef.equals(key)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                pErrors.add("Not found account for account reference:'" + accountRef + "'.");
            }
        }
    }

    private void checkSites(Map<ComparableList, Integer> pSiteIdsMappedByGroupSenderOverrideAndSiteReferenceNumber) {

        for (int i = 0; i < parsedDataList.size(); i++) {

            XpedxCatalogSiteView parsedDataItem = parsedDataList.get(i);

            String accountRef = parsedDataItem.getAccountReference().trim();
            String siteRef = parsedDataItem.getSiteReference().trim();
            Integer siteId = pSiteIdsMappedByGroupSenderOverrideAndSiteReferenceNumber.get(ComparableList.createValue(accountRef, siteRef));
            if (siteId == null) {
                appendErrorMsgs("Not found site for line:" + (i + 1)
                        + " account ref:'" + accountRef +
                        "', site ref:'" + siteRef + "'");
            }
        }

    }

    private void checkExistCatalogs(Set<ComparableList> pCatalogsForCreate,
                                    Map<String, Set<Integer>> pCatalogIdsByNameForStore,
                                    List<String> pErrors) {

        for (Map.Entry<String, Set<Integer>> entry : pCatalogIdsByNameForStore.entrySet()) {
            if (entry.getValue().size() > 1) {
                pErrors.add("Found duplicated catalog with name: " + entry.getKey() + ", CatalogIDs: " + entry.getValue());
            }
        }

        for (ComparableList catalogRef : pCatalogsForCreate) {
            String name = (String) catalogRef.get(1);
            if (pCatalogIdsByNameForStore.containsKey(name)) {
                pErrors.add("Catalog with name '" + catalogRef.get(1) + "' already exists. CatalogIDs: " + pCatalogIdsByNameForStore.get(name));
            }
        }

    }

}
