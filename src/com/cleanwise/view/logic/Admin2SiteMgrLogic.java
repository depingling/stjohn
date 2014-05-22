package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.rmi.RemoteException;


public class Admin2SiteMgrLogic {

    private static final Logger log = Logger.getLogger(Admin2SiteMgrLogic.class);

    public static final String ADMIN2_SITE_MGR_FORM = "ADMIN2_SITE_MGR_FORM";
    public static final String ADMIN2_SITE_DETAIL_MGR_FORM = "ADMIN2_SITE_DETAIL_MGR_FORM";
    public static final String ADMIN2_SITE_BUDGET_MGR_FORM = "ADMIN2_SITE_BUDGET_MGR_FORM";
    public static final String ADMIN2_SITE_WORKFLOW_MGR_FORM = "ADMIN2_SITE_WORKFLOW_MGR_FORM";
    public static final String ADMIN2_SITE_CONFIG_MGR_FORM = "ADMIN2_SITE_CONFIG_MGR_FORM";

    private static final String SITE_ID = "SITE_ID";
    private static final String DISTRIBUTOR = "DISTRIBUTOR";

    private static final Comparator<? super UserData> USER_DATA_ID_COMPARE = new Comparator<UserData>() {
        public int compare(UserData o1, UserData o2) {
            int id1 = o1.getUserId();
            int id2 = o2.getUserId();
            return id1 - id2;
        }
    };

    private static final Comparator<? super CatalogData> CATALOG_DATA_ID_COMPARE = new Comparator<CatalogData>() {
        public int compare(CatalogData o1, CatalogData o2) {
            int id1 = o1.getCatalogId();
            int id2 = o2.getCatalogId();
            return id1 - id2;
        }
    };

    private static final String ADMIN2_SITE_WORKFLOW_TYPES = "Site.workflow.type.vector";


    public static ActionErrors init(Admin2SiteMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit(request)) {

            pForm = new Admin2SiteMgrForm();
            pForm.init(request);

            ae = checkRequest(pForm, request);
            if (ae.size() > 0) {
                return ae;
            }

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            log.info("init => user: " + appUser.getUser().getUserTypeCd());

            session.setAttribute(ADMIN2_SITE_MGR_FORM, pForm);

        }

        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors search(Admin2SiteMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("search => BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) return ae;

        ae = checkSiteSearchAttr(pForm, request);
        if (ae.size() > 0) return ae;


        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        String fieldSearchType = pForm.getSearchType();
        String fieldValue = pForm.getSearchField();
        String fieldSearchRefNum = pForm.getSearchRefNum();
        String fieldSearchRefNumType = pForm.getSearchRefNumType();
        String city = pForm.getSearchCity();
        String county = pForm.getSearchCounty();
        String state = pForm.getSearchState();
        String postalCode = pForm.getSearchPostalCode();
        boolean showInactiveFl = pForm.getShowInactiveFl();
        IdVector accountIds = appUser.isaAccountAdmin() ? Utility.toIdVector(appUser.getUserAccount().getAccountId()) : Utility.toIdVector(pForm.getAccountFilter());

        SiteViewVector siteVwV = search(request,
                fieldValue,
                fieldSearchType,
                fieldSearchRefNum,
                fieldSearchRefNumType,
                accountIds,
                city,
                county,
                state,
                postalCode,
                showInactiveFl);

        pForm.setSiteSearchResult(siteVwV);

        log.info("search => END.");

        return ae;

    }

    public static SiteViewVector search(HttpServletRequest request,
                                        String pFieldValue,
                                        String pFieldSearchType,
                                        String pFieldSearchRefNum,
                                        String pFieldSearchRefNumType,
                                        IdVector pAccountIds,
                                        String pCity,
                                        String pCounty,
                                        String pState,
                                        String pPostalCode,
                                        boolean pShowInactive) throws Exception {


        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();


        QueryRequest qr = new QueryRequest();
        qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);

        pFieldValue = pFieldValue.trim();
        if (Utility.isSet(pFieldValue)) {
            if (pFieldSearchType.equals("id")) {
                int id = 0;
                try {
                    id = Integer.parseInt(pFieldValue.trim());
                } catch (Exception exc) {
                    log.error(exc.getMessage(), exc);
                }
                qr.filterBySiteId(id);
            } else {
                if (pFieldSearchType.equals("nameBegins")) {
                    qr.filterByOnlySiteName(pFieldValue, QueryRequest.BEGINS_IGNORE_CASE);
                } else if (pFieldSearchType.equals("nameContains")) {
                    qr.filterByOnlySiteName(pFieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
                }
            }
        }

        pFieldSearchRefNum = pFieldSearchRefNum.trim();
        if (Utility.isSet(pFieldSearchRefNum)) {
            if (pFieldSearchRefNumType.equals("nameBegins")) {
                qr.filterByRefNum(pFieldSearchRefNum, QueryRequest.BEGINS_IGNORE_CASE);
            } else if (pFieldSearchRefNumType.equals("nameContains")) {
                qr.filterByRefNum(pFieldSearchRefNum, QueryRequest.CONTAINS_IGNORE_CASE);
            }
        }

        if (Utility.isSet(pCity)) {
            qr.filterByCity(pCity.trim(), QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (Utility.isSet(pCounty)) {
            qr.filterByCounty(pCounty.trim(), QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (Utility.isSet(pState)) {
            qr.filterByState(pState.trim(), QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (Utility.isSet(pPostalCode)) {
            qr.filterByZip(pPostalCode.trim(), QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (!pShowInactive) {
            ArrayList<String> statusList = new ArrayList<String>();
            statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
            qr.filterBySiteStatusCdList(statusList);
        }

        int storeId = appUser.getUserStore().getStoreId();
        qr.filterByStoreIds(Utility.toIdVector(storeId));

        if (pAccountIds != null && pAccountIds.size() > 0) {
            qr.filterByAccountIds(pAccountIds);
        }

        return siteEjb.getSiteCollection(qr);

    }

    public static ActionErrors getDetail(Admin2SiteDetailMgrForm pForm, HttpServletRequest request) throws Exception {

        int reqSiteId = Admin2Tool.getRequestedId(request);
        if (reqSiteId <= 0) {
            reqSiteId = pForm.getIntId();
        }

        return detail(request, reqSiteId);
    }

    public static ActionErrors detail(HttpServletRequest request,int pSiteId) throws Exception {

        log.info("detail =>  BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = init((Admin2SiteDetailMgrForm) null, request);
        if (ae.size() > 0) {
            return ae;
        }

        Admin2SiteDetailMgrForm pForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();
        Account accountEjb = factory.getAccountAPI();
        Store storeEjb = factory.getStoreAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        CustomerRequestPoNumber custPoEjb = factory.getCustomerRequestPoNumberAPI();

        SiteData site;
        try {
            site = siteEjb.getSiteForStore(pSiteId, appUser.getUserStoreAsIdVector(), true, SessionTool.getCategoryToCostCenterView(session, pSiteId));
            pForm.setSiteData(site);
        } catch (DataNotFoundException e) {
            ae.add("site", new ActionError("bad.site"));
            return ae;
        }

        BusEntityData bed = site.getBusEntity();

        // Set the form values.

        BlanketPoNumDataVector bpodv = custPoEjb.getBlanketPosForBusEntity(site.getAccountId());
        bpodv = bpodv == null || bpodv.isEmpty() ? null : bpodv;

        pForm.setBlanketPos(bpodv);
        pForm.setName(bed.getShortDesc());
        pForm.setStatusCd(bed.getBusEntityStatusCd());
        pForm.setId(bed.getBusEntityId());

        pForm.setTypeCd(bed.getBusEntityTypeCd());
        pForm.setSiteNumber(bed.getErpNum());
        pForm.setOldSiteNumber(bed.getErpNum());

        if (site.getBlanketPoNum() != null) {
            pForm.setBlanketPoNumId(site.getBlanketPoNum().getBlanketPoNumId());
        } else {
            pForm.setBlanketPoNumId(0);
        }

        if (bed.getEffDate() != null) {
            pForm.setEffDate(ClwI18nUtil.formatDateInp(request, bed.getEffDate()));
        } else {
            pForm.setEffDate(Constants.EMPTY);
        }

        if (bed.getExpDate() != null) {
            pForm.setExpDate(ClwI18nUtil.formatDateInp(request, bed.getEffDate()));
        } else {
            pForm.setExpDate(Constants.EMPTY);
        }

        if ( site.hasBSC()){
            pForm.setSubContractor(site.getBSC().getBusEntityData().getShortDesc());
        } else{
            pForm.setSubContractor(Constants.EMPTY);
        }

        if(site.getTargetFacilityRank() != null){
            pForm.setTargetFacilityRank(site.getTargetFacilityRank().toString());
        }else{
            pForm.setTargetFacilityRank(Constants.EMPTY);
        }

        PropertyData taxableIndicator = site.getTaxableIndicator();
        String taxable = Constants.NOFF;
        if(taxableIndicator!=null && Utility.isSet(taxableIndicator.getValue())) {
            if(Utility.isTrue(taxableIndicator.getValue())) {
                taxable = Constants.YON;
            }
        }
        pForm.setTaxableIndicator(taxable);

        PropertyData invShopping = site.getInventoryShopping();
        pForm.setInventoryShopping(String.valueOf(Utility.isSelected(invShopping.getValue())));

        PropertyData invShoppingType = site.getInventoryShoppingType();
        pForm.setInventoryShoppingType(String.valueOf(Utility.isSelected(invShoppingType.getValue())));

        PropertyData invShoppingHoldOrder = site.getInventoryShoppingHoldOrderUntilDeliveryDate();
        pForm.setInventoryShoppingHoldOrderUntilDeliveryDate(String.valueOf(Utility.isTrue(invShoppingHoldOrder.getValue())));

        // Set the account name.
        AccountData account = accountEjb.getAccount(site.getAccountId(), 0);

        pForm.setAccountName(account.getBusEntity().getShortDesc());
        pForm.setAccountId(String.valueOf(account.getAccountId()));

        // Get the fields configured by the account.
        BusEntityFieldsData sfd = propEjb.fetchSiteFieldsData(account.getAccountId());
        pForm.setSiteFields(sfd);

        // Set the store id and store name.
        StoreData store = storeEjb.getStore(account.getStoreAssoc().getBusEntity2Id());

        pForm.setStoreId(store.getStoreId());
        pForm.setStoreName(store.getBusEntity().getShortDesc());

        AddressData address = site.getSiteAddress();
        pForm.setName1(address.getName1());
        pForm.setName2(address.getName2());
        pForm.setPostalCode(address.getPostalCode());
        pForm.setStateOrProv(address.getStateProvinceCd());
        pForm.setCounty(address.getCountyCd());
        pForm.setCountry(address.getCountryCd());
        pForm.setCity(address.getCity());
        pForm.setStreetAddr1(address.getAddress1());
        pForm.setStreetAddr2(address.getAddress2());
        pForm.setStreetAddr3(address.getAddress3());
        pForm.setStreetAddr4(address.getAddress4());

        PropertyDataVector props = site.getMiscProperties();
        pForm.setComments(Constants.EMPTY);
        pForm.setShippingMessage(Constants.EMPTY);
        pForm.setDistSiteReferenceNumber(Constants.EMPTY);

        for (Object oProp : props) {

            PropertyData prop = (PropertyData) oProp;

            if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)) {
                pForm.setComments(prop.getValue());
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)) {
                pForm.setShippingMessage(prop.getValue());
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)) {
                pForm.setSiteReferenceNumber(prop.getValue());
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER)) {
                pForm.setDistSiteReferenceNumber(prop.getValue());
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
                if (Utility.isTrue(prop.getValue())) {
                    pForm.setBypassOrderRouting(String.valueOf(true));
                } else {
                    pForm.setBypassOrderRouting(String.valueOf(false));
                }
            } else if (prop.getShortDesc().
                    equals(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
                if (Utility.isTrue(prop.getValue())) {
                    pForm.setConsolidatedOrderWarehouse(String.valueOf(true));
                } else {
                    pForm.setConsolidatedOrderWarehouse(String.valueOf(false));
                }
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES)) {
                if (Utility.isTrue(prop.getValue())) {
                    pForm.setShareBuyerOrderGuides(String.valueOf(true));
                } else {
                    pForm.setShareBuyerOrderGuides(String.valueOf(false));
                }
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC)) {
                pForm.setLineLevelCode(prop.getValue());
            }

        }

        PropertyDataVector props2 = site.getDataFieldProperties();
        Iterator iter2 = props2.iterator();

        pForm.setF1Value(Constants.EMPTY);
        pForm.setF2Value(Constants.EMPTY);
        pForm.setF3Value(Constants.EMPTY);
        pForm.setF4Value(Constants.EMPTY);
        pForm.setF5Value(Constants.EMPTY);
        pForm.setF6Value(Constants.EMPTY);
        pForm.setF7Value(Constants.EMPTY);
        pForm.setF8Value(Constants.EMPTY);
        pForm.setF9Value(Constants.EMPTY);
        pForm.setF10Value(Constants.EMPTY);

        while (iter2.hasNext()) {
            PropertyData prop = (PropertyData) iter2.next();
            if (prop.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)) {

                String tag = prop.getShortDesc();

                if (null == tag) continue;
                if (tag.equals(sfd.getF1Tag())) {
                    pForm.setF1Value(prop.getValue());
                } else if (tag.equals(sfd.getF2Tag())) {
                    pForm.setF2Value(prop.getValue());
                } else if (tag.equals(sfd.getF3Tag())) {
                    pForm.setF3Value(prop.getValue());
                } else if (tag.equals(sfd.getF4Tag())) {
                    pForm.setF4Value(prop.getValue());
                } else if (tag.equals(sfd.getF5Tag())) {
                    pForm.setF5Value(prop.getValue());
                } else if (tag.equals(sfd.getF6Tag())) {
                    pForm.setF6Value(prop.getValue());
                } else if (tag.equals(sfd.getF7Tag())) {
                    pForm.setF7Value(prop.getValue());
                } else if (tag.equals(sfd.getF8Tag())) {
                    pForm.setF8Value(prop.getValue());
                } else if (tag.equals(sfd.getF9Tag())) {
                    pForm.setF9Value(prop.getValue());
                } else if (tag.equals(sfd.getF10Tag())) {
                    pForm.setF10Value(prop.getValue());
                }
            }
        }

        pForm.setAuthorizedReSaleAccount(String.valueOf(account.isAuthorizedForResale()));
        pForm.setMakeShiptoBillto(String.valueOf(account.isMakeShipToBillTo()));
        pForm.setERPEnabled(String.valueOf(false));
        pForm.setAvailableShipto(new IdVector());

        log.info("detail =>  END.");

        return ae;
    }

    public static ActionErrors init(Admin2SiteDetailMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = new Admin2SiteDetailMgrForm();
        pForm.init();

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        initFormVectors(request);

        session.setAttribute(ADMIN2_SITE_DETAIL_MGR_FORM, pForm);

        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors init(Admin2SiteWorkflowMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = new Admin2SiteWorkflowMgrForm();
        pForm.init();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        initFormVectors(request);

        session.setAttribute(ADMIN2_SITE_WORKFLOW_MGR_FORM, pForm);
        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors update(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();

        int accountId = getAccountId(pForm);

        Date effDate = null;
        Date expDate = null;
        BlanketPoNumData blanketPo = null;
        Integer targetFacilityRank = null;

        ae = validateSiteErpNum(accountId, pForm.getIntId(), pForm.getSiteNumber());
        if (ae.size() > 0) {
            return ae;
        }

        // Verify the form information submitted.
        if (pForm.getCity() != null) {
            pForm.setCity(pForm.getCity().trim());
        }
        if (pForm.getStreetAddr1() != null) {
            pForm.setStreetAddr1(pForm.getStreetAddr1().trim());
        }
        if (pForm.getStreetAddr2() != null) {
            pForm.setStreetAddr2(pForm.getStreetAddr2().trim());
        }
        if (pForm.getStreetAddr3() != null) {
            pForm.setStreetAddr3(pForm.getStreetAddr3().trim());
        }
        if (pForm.getStreetAddr4() != null) {
            pForm.setStreetAddr4(pForm.getStreetAddr4().trim());
        }
        if (pForm.getStateOrProv() != null) {
            pForm.setStateOrProv(pForm.getStateOrProv().trim());
        }
        if (pForm.getPostalCode() != null) {
            pForm.setPostalCode(pForm.getPostalCode().trim());
        }

        if (accountId <= 0) {
            ae.add("accountId", new ActionError("variable.empty.error", "Account Id"));
        }
        if (pForm.getStatusCd().length() == 0) {
            ae.add("statusCd", new ActionError("variable.empty.error", "Status"));
        }
        if (pForm.getName().length() == 0) {
            ae.add("name", new ActionError("variable.empty.error", "Name"));
        }

        if (pForm.getStreetAddr1().length() == 0) {
            ae.add("streetAddr1", new ActionError("variable.empty.error", "Street Address 1"));
        }
        if (pForm.getCity().length() == 0) {
            ae.add("city", new ActionError("variable.empty.error", "City"));
        }
        if (pForm.getCountry().length() == 0) {
            ae.add("country", new ActionError("variable.empty.error", "Country"));
        }

        // check country and province
        CountryData country = getCountry(session, pForm);
        Country countryEjb = factory.getCountryAPI();
        CountryPropertyData countryProp = countryEjb.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
        boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");

        if (isStateProvinceRequired && !Utility.isSet(pForm.getStateOrProv())) {
            ae.add("stateOrProv", new ActionError("variable.empty.error", "State/Province"));
        }
        if (!isStateProvinceRequired && Utility.isSet(pForm.getStateOrProv())) {
            ae.add("stateOrProv", new ActionError("variable.must.be.empty.error", "State/Province"));
        }

        if (pForm.getPostalCode().length() == 0) {
            ae.add("postalCode", new ActionError("variable.empty.error", "Zip/Postal Code"));
        }

        if (pForm.getTargetFacilityRank() != null && pForm.getTargetFacilityRank().length() > 0) {
            try {
                targetFacilityRank = new Integer(pForm.getTargetFacilityRank());
            } catch (NumberFormatException e) {
                ae.add("targetFacilityRank", new ActionError("error.invalidNumber", "Target Facility Rank"));
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String effDateS = pForm.getEffDate();
        if (effDateS != null && effDateS.trim().length() > 0) {
            try {
                effDate = df.parse(effDateS);
            } catch (Exception exc) {
                ae.add("effDate", new ActionError("variable.date.format.error", "Effective Date"));
            }
        }
        String expDateS = pForm.getExpDate();
        if (expDateS != null && expDateS.trim().length() > 0) {
            try {
                expDate = df.parse(expDateS);
            } catch (Exception exc) {
                ae.add("expDate", new ActionError("variable.date.format.error", "Expiration Date"));
            }
        }
        if (effDate != null && expDate != null && expDate.before(effDate)) {
            ae.add("EffExpDates", new ActionError("error.simpleGenericError", "Effective date can't be after expiration date"));
        }
        String statusCd = pForm.getStatusCd();
        if (statusCd.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
            Date curDate = new Date();
            if ((effDate != null && effDate.after(curDate)) ||
                    (expDate != null && expDate.before(curDate))) {
                ae.add("EffExpDatesStatusCd", new ActionError("error.simpleGenericError", "Effective and expiration dates conflict with site status"));

            }
        }

        if (pForm.getBlanketPoNumId() != null && pForm.getBlanketPoNumId() != 0) {
            BlanketPoNumDataVector bpdv = pForm.getBlanketPos();
            if (bpdv != null) {
                int bpoid = pForm.getBlanketPoNumId();
                Iterator it = bpdv.iterator();
                while (it.hasNext()) {
                    BlanketPoNumData avaBpo = (BlanketPoNumData) it.next();
                    if (avaBpo.getBlanketPoNumId() == bpoid) {
                        blanketPo = avaBpo;
                    }
                }
            }

            if (blanketPo == null) {
                ae.add("blanketPoNumId", new ActionError("error.siteBlanketPoNotAvaliable"));
            }
        }

        if (ae.size() > 0) {
            // Report the errors to allow for edits.
            return ae;
        }

        factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        //AddressValidator validator = factory.getAddressValidatorAPI();

        int siteid = pForm.getIntId();
        // Get the current information for this Site.
        SiteData dd;
        BusEntityData bed;
        PropertyDataVector props;
        PropertyDataVector siteFields = new PropertyDataVector();
        PropertyDataVector siteFieldsRuntime = new PropertyDataVector();
        AddressData address;
        PropertyData taxableIndicator, invprop, invpropHoldOrder, invpropType;

        if (siteid > 0) {
            dd = siteBean.getSite(siteid, accountId, true);
        } else {
            dd = SiteData.createValue();
        }

        dd.setBlanketPoNum(blanketPo);

        dd.setTargetFacilityRank(targetFacilityRank);
        bed = dd.getBusEntity();
        props = dd.getMiscProperties();
        address = dd.getSiteAddress();
        taxableIndicator = dd.getTaxableIndicator();
        invprop = dd.getInventoryShopping();
        invpropHoldOrder = dd.getInventoryShoppingHoldOrderUntilDeliveryDate();
        invpropType = dd.getInventoryShoppingType();

        bed.setWorkflowRoleCd(Constants.UNKNOWN);
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        // Now update with the data from the form.
        bed.setShortDesc(pForm.getName());
        bed.setLongDesc(pForm.getName());
        bed.setBusEntityStatusCd(pForm.getStatusCd());
        bed.setEffDate(effDate);
        bed.setExpDate(expDate);
        bed.setErpNum(pForm.getSiteNumber());
        bed.setLocaleCd(Constants.UNK);
        bed.setModBy(cuser);

        address.setModBy(cuser);
        address.setName1(pForm.getName1());
        address.setName2(pForm.getName2());
        address.setAddress1(pForm.getStreetAddr1());
        address.setAddress2(pForm.getStreetAddr2());
        address.setAddress3(pForm.getStreetAddr3());
        address.setAddress4(pForm.getStreetAddr4());
        address.setCity(pForm.getCity());
        address.setStateProvinceCd(pForm.getStateOrProv());
        address.setPostalCode(pForm.getPostalCode());
        address.setCountryCd(pForm.getCountry());
        address.setPrimaryInd(true);
        address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);

        //if (!validator.validateAddress(address)) {
        //    ae.add("updateSite", new ActionError("error.addressValidation"));
       //     return (ae);
       // }

        dd.setBSC(findBSC(request, pForm.getSubContractor()));

        taxableIndicator.setValue(pForm.getTaxableIndicator());
        taxableIndicator.setAddBy(cuser);

        boolean updfComments = false;
        boolean updfShipMsg = false;
        boolean siteRefNum = false;
        boolean distSiteRefNum = false;
        boolean lineLevelCode = false;
        boolean bypassOrderRouting = false;
        boolean consolidatedOrderWarehouse = false;
        boolean shareOrderGuide = false;
        for (Object oProp : props) {
            PropertyData prop = (PropertyData) oProp;
            if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)) {
                prop.setValue(pForm.getComments());
                updfComments = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)) {
                prop.setValue(pForm.getShippingMessage());
                updfShipMsg = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)) {
                prop.setValue(pForm.getSiteReferenceNumber());
                siteRefNum = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER)) {
                prop.setValue(pForm.getDistSiteReferenceNumber());
                distSiteRefNum = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC)) {
                prop.setValue(pForm.getLineLevelCode());
                lineLevelCode = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
                if (Utility.isSelected(pForm.isBypassOrderRouting())) {
                    prop.setValue(Constants.TRUE);
                } else {
                    prop.setValue(Constants.FALSE);
                }
                bypassOrderRouting = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
                if (Utility.isSelected(pForm.isConsolidatedOrderWarehouse())) {
                    prop.setValue(Constants.TRUE);
                } else {
                    prop.setValue(Constants.FALSE);
                }
                consolidatedOrderWarehouse = true;
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES)) {
                if (Utility.isTrue(pForm.getShareBuyerOrderGuides())) {
                    prop.setValue(Constants.TRUE);
                } else {
                    prop.setValue(Constants.FALSE);
                }
                shareOrderGuide = true;
            }
        }

        boolean tf = Utility.isSelected(pForm.isInventoryShoppingHoldOrderUntilDeliveryDate());
        if (tf) {
            invpropHoldOrder.setValue(Constants.TRUE);
            invpropHoldOrder.setModBy(cuser);
        } else {
            invpropHoldOrder.setValue(Constants.FALSE);
            invpropHoldOrder.setModBy(cuser);
        }
        dd.setInventoryShoppingHoldOrderUntilDeliveryDate(invpropHoldOrder);

        tf = Utility.isSelected(pForm.getInventoryShopping());
        if (tf) {
            invprop.setValue(Constants.ON);
            invprop.setAddBy(cuser);
        } else {
            invprop.setValue(Constants.OFF);
            invprop.setModBy(cuser);
        }
        dd.setInventoryShopping(invprop);

        tf = Utility.isSelected(pForm.getInventoryShoppingType());
        if (invpropType == null) {
            invpropType = PropertyData.createValue();
        }

        if (tf) {
            invpropType.setValue(Constants.ON);
            invpropType.setAddBy(cuser);
        } else {
            invpropType.setValue(Constants.OFF);
            invpropType.setModBy(cuser);
        }
        dd.setInventoryShoppingType(invpropType);

        if (!updfComments) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS);
            nprop.setValue(pForm.getComments());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!updfShipMsg) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
            nprop.setValue(pForm.getShippingMessage());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!siteRefNum) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
            nprop.setValue(pForm.getSiteReferenceNumber());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!distSiteRefNum) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
            nprop.setValue(pForm.getDistSiteReferenceNumber());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!lineLevelCode) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
            nprop.setValue(pForm.getLineLevelCode());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!bypassOrderRouting) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING);
            if (Utility.isSelected(pForm.isBypassOrderRouting())) {
                nprop.setValue(Constants.TRUE);
            } else {
                nprop.setValue(Constants.FALSE);
            }
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!consolidatedOrderWarehouse) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
            if (Utility.isSelected(pForm.isConsolidatedOrderWarehouse())) {
                nprop.setValue(Constants.TRUE);
            } else {
                nprop.setValue(Constants.FALSE);
            }
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (!shareOrderGuide) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
            nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
            if (Utility.isTrue(pForm.getShareBuyerOrderGuides())) {
                nprop.setValue(Constants.TRUE);
            } else {
                nprop.setValue(Constants.FALSE);
            }
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        // Set up the site fields vector.
        BusEntityFieldsData sfd = pForm.getSiteFields();
        if (null != sfd) {
            if (sfd.getF1ShowAdmin() || sfd.getF1ShowRuntime()) {
                if (sfd.getF1Required() && !Utility.isSet(pForm.getF1Value())) {
                    ae.add("F1Tag", new ActionError("variable.empty.error", sfd.getF1Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF1Tag());
                nprop.setValue(pForm.getF1Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF1ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }

            }

            if (sfd.getF2ShowAdmin() || sfd.getF2ShowRuntime()) {
                if (sfd.getF2Required() && !Utility.isSet(pForm.getF2Value())) {
                    ae.add("F2Tag", new ActionError("variable.empty.error", sfd.getF2Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF2Tag());
                nprop.setValue(pForm.getF2Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF2ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF3ShowAdmin() || sfd.getF3ShowRuntime()) {
                if (sfd.getF3Required() && !Utility.isSet(pForm.getF3Value())) {
                    ae.add("F3Tag", new ActionError("variable.empty.error", sfd.getF3Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                        (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF3Tag());
                nprop.setValue(pForm.getF3Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF3ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF4ShowAdmin() || sfd.getF4ShowRuntime()) {
                if (sfd.getF4Required() && !Utility.isSet(pForm.getF4Value())) {
                    ae.add("F4Tag", new ActionError("variable.empty.error", sfd.getF4Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF4Tag());
                nprop.setValue(pForm.getF4Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF4ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF5ShowAdmin() || sfd.getF5ShowRuntime()) {
                if (sfd.getF5Required() && !Utility.isSet(pForm.getF5Value())) {
                    ae.add("F5Tag", new ActionError("variable.empty.error", sfd.getF5Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF5Tag());
                nprop.setValue(pForm.getF5Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF5ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF6ShowAdmin() || sfd.getF6ShowRuntime()) {
                if (sfd.getF6Required() && !Utility.isSet(pForm.getF6Value())) {
                    ae.add("F6Tag", new ActionError("variable.empty.error", sfd.getF6Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF6Tag());
                nprop.setValue(pForm.getF6Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF6ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF7ShowAdmin() || sfd.getF7ShowRuntime()) {
                if (sfd.getF7Required() && !Utility.isSet(pForm.getF7Value())) {
                    ae.add("F7Tag", new ActionError("variable.empty.error", sfd.getF7Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                        (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF7Tag());
                nprop.setValue(pForm.getF7Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF7ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF8ShowAdmin() || sfd.getF8ShowRuntime()) {
                if (sfd.getF8Required() && !Utility.isSet(pForm.getF8Value())) {
                    ae.add("F8Tag", new ActionError("variable.empty.error", sfd.getF8Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                        (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF8Tag());
                nprop.setValue(pForm.getF8Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF8ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF9ShowAdmin() || sfd.getF9ShowRuntime()) {
                if (sfd.getF9Required() && !Utility.isSet(pForm.getF9Value())) {
                    ae.add("F9Tag", new ActionError("variable.empty.error", sfd.getF9Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                        (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF9Tag());
                nprop.setValue(pForm.getF9Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF9ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            if (sfd.getF10ShowAdmin() || sfd.getF10ShowRuntime()) {
                if (sfd.getF10Required() && !Utility.isSet(pForm.getF10Value())) {
                    ae.add("F10Tag", new ActionError("variable.empty.error", sfd.getF10Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                        (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF10Tag());
                nprop.setValue(pForm.getF10Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if (sfd.getF10ShowRuntime()) {
                    siteFieldsRuntime.add(nprop);
                }
            }

            dd.setDataFieldProperties(siteFields);
            dd.setDataFieldPropertiesRuntime(siteFieldsRuntime);

        }

        if (!(ae.size() == 0)) {
            return ae;
        }

        if (siteid == 0) {
            // get account - both to verify that the id is that
            // of an account and get it's name
            try {
                Account accountBean = factory.getAccountAPI();
                AccountData ad = accountBean.getAccount(accountId, 0);
                pForm.setAccountName(ad.getBusEntity().getShortDesc());
            } catch (DataNotFoundException de) {
                // the given account id is apparently not an account
                pForm.setAccountName("");
                ae.add("Account.id", new ActionError("site.bad.account"));
                return ae;
            } catch (Exception e) {
                e.printStackTrace();
                ae.add("SiteErrors", new ActionError("error.genericError", e.getMessage()));
                return ae;
            }

            // create the default property ShareOrderGuide
            PropertyData pdata = PropertyData.createValue();
            pdata.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
            pdata.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
            pdata.setAddBy(cuser);
            pdata.setValue(Constants.TRUE);
            props.add(pdata);

            // Add the site.
            bed.setAddBy(cuser);
            bed.setBusEntityStatusCd(pForm.getStatusCd());
            address.setAddBy(cuser);
            try {
                dd = siteBean.addSite(dd, accountId);
                pForm.setId(dd.getBusEntity().getBusEntityId());
                if(pForm.isClone() && pForm.getCloneId()>0 && Admin2SiteDetailMgrForm.CLONE_WITH_RELATIONSHIPS.equals(pForm.getCloneCode())) {
                    cloneRelationships(request, pForm.getIntId(), pForm.getCloneId());
                }
            } catch (DuplicateNameException ne) {
                ae.add("name", new ActionError("error.field.notUnique", "Name"));
                return ae;
            } catch (Exception e) {
                String errorMess = Utility.getUiErrorMess(e.getMessage());
                if (errorMess == null) errorMess = e.getMessage();
                ae.add("SiteErrors", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

        } else {
            // Update this Site
            try {
                siteBean.updateSite(dd);
                pForm.setId(dd.getBusEntity().getBusEntityId());
            } catch (DuplicateNameException ne) {
                ae.add("name", new ActionError("error.field.notUnique", "Name"));
                return ae;
            } catch (Exception e) {
                String errorMess = Utility.getUiErrorMess(e.getMessage());
                if (errorMess == null) errorMess = e.getMessage();
                ae.add("SiteErrors", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        }

        pForm.setAvailableShipto(null);
        try {
            ae = detail(request, pForm.getIntId());
        } catch (DataNotFoundException e) {
            initDetailFields(request, pForm);
            String errorMess = Utility.getUiErrorMess(e.getMessage());
            if (errorMess == null) errorMess = e.getMessage();
            ae.add("SiteErrors", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    private static void cloneRelationships(HttpServletRequest request, int pSiteId, int pCloneSiteId) throws Exception {

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        Workflow wrkEjb = factory.getWorkflowAPI();
        User userEjb = factory.getUserAPI();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Catalog catEjb = factory.getCatalogAPI();

        IdVector workflowIds = wrkEjb.getSiteWorkflowIds(pCloneSiteId);
        for (Object oWorflowId : workflowIds) {
            wrkEjb.assignWorkflow((Integer) oWorflowId, pSiteId, appUser.getUserName());
        }

        CatalogData siteCatalog = catInfEjb.getSiteCatalog(pCloneSiteId);
        if (siteCatalog != null) {
            catEjb.resetCatalogAssoc(siteCatalog.getCatalogId(), pSiteId, appUser.getUserName());
        }

        List userTypeCds = Admin2Tool.getFormVector(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);
        List<String> userTypes = Admin2Tool.toValueList((RefCdDataVector) userTypeCds);
        IdVector storeIds = Utility.toIdVector(appUser.getUserStore().getStoreId());
        IdVector siteIds = Utility.toIdVector(pCloneSiteId);

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        searchCriteria.setStoreIds(storeIds);
        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
        searchCriteria.setIncludeInactiveFl(true);
        searchCriteria.setUserTypes(userTypes);
        searchCriteria.setSiteIds(siteIds);

        if (appUser.isaAccountAdmin()) {
            searchCriteria.setAccountId(appUser.getUserAccount().getAccountId());
            searchCriteria.setUserAccountIds(Utility.toIdVector(appUser.getAccounts()));
        }

        UserDataVector users = userEjb.getUsersCollectionByCriteria(searchCriteria);
        for (Object oUser : users) {
            UserData user = (UserData) oUser;
            if (userTypes.contains(user.getUserTypeCd())) {
                userEjb.addUserAssoc(user.getUserId(), pSiteId, RefCodeNames.USER_ASSOC_CD.SITE);
            }
        }
    }

    public static ActionErrors delete(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {

        ActionErrors ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Site siteEjb = factory.getSiteAPI();
        log.info("delete => siteId: "+pForm.getIntId()+", AccountId: "+pForm.getAccountId());
        SiteData site;
        try {
            site = siteEjb.getSite(pForm.getIntId(), Integer.parseInt(pForm.getAccountId()), true);
        } catch (DataNotFoundException e) {
            return ae;
        }

        try {
            siteEjb.removeSite(site);
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("id", new ActionError("error.deleteFailed", "Site"));
        }

        return ae;
    }

    private static ActionErrors validateSiteErpNum(int pAccountId, int pSiteId, String pSiteErpNum) {

        log.info("validateSiteErpNum => BEGIN.");
        log.info("validateSiteErpNum => pAccountId:" + pAccountId + " ,pSiteId: " + pSiteId + " ,pSiteErpNum:" + pSiteErpNum);

        ActionErrors ae = new ActionErrors();

        if (Utility.isSet(pSiteErpNum)) {
            try {

                APIAccess factory = new APIAccess();
                Site siteEjb = factory.getSiteAPI();

                BusEntityDataVector sites = siteEjb.getSiteByErpNum(pAccountId, pSiteErpNum);

                if (sites.size() > 1 ||
                        (sites.size() == 1 &&
                                pSiteId == 0)) {

                    ae.add("SiteErrors", new ActionError("error.field.notUnique", "Site Erp Number"));
                    log.info("validateSiteErpNum => Site Erp Number is not unique");

                } else if (sites.size() == 1 && pSiteId > 0) {

                    if (((BusEntityData) sites.get(0)).getBusEntityId() != pSiteId) {
                        ae.add("SiteErrors", new ActionError("error.field.notUnique", "Site Erp Number"));
                        log.info("validateSiteErpNum => Site Erp Number is not unique");
                    }

                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                String errorMess = Utility.getUiErrorMess(e.getMessage());
                if (errorMess == null) errorMess = e.getMessage();
                ae.add("SiteErrors", new ActionError("error.simpleGenericError", errorMess));
            }
        }

        log.info("validateSiteErpNum => END.Action Errors: " + ae);

        return ae;

    }

    private static int getAccountId(Admin2SiteDetailMgrForm pForm) {
        try {
            if (Utility.isSet(pForm.getAccountId())) {
                return Integer.parseInt(pForm.getAccountId());
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return 0;
    }


    private static ActionErrors checkRequest(Admin2SiteMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_SITE_MGR_FORM});
            ae.add("error", new ActionError("error.systemError", errorMess));
        }

        return ae;

    }

    private static ActionErrors checkRequest(Admin2SiteDetailMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_SITE_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.systemError", errorMess));
            return ae;
        }

        return ae;
    }

    private static ActionErrors checkRequest(Admin2SiteBudgetMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_SITE_BUDGET_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (!(pForm.getSiteId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.siteIdWasNotSpecified", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (!(pForm.getAccountId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.accountIdWasNotSpecified", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;

    }

    private static ActionErrors checkRequest(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_SITE_CONFIG_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (pForm.getSite() == null || !(pForm.getSite().getBusEntityId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noSiteInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (pForm.getAccount() == null || !(pForm.getAccount().getBusEntityId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noAccountInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (pForm.getStore() == null || !(pForm.getStore().getBusEntityId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noStoreInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (appUser.isaAccountAdmin()) {
            if (pForm.getAccount().getBusEntityId() != appUser.getUserAccount().getAccountId()) {
                ae.add("error", new ActionError("error.badRequest2"));
                return ae;
            }
        }

        if (pForm.getStore().getBusEntityId() != appUser.getUserStore().getStoreId()) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        return ae;

    }

    private static CountryData getCountry(HttpSession session, Admin2SiteDetailMgrForm pForm) {
        CountryDataVector countries = (CountryDataVector) session.getAttribute(Admin2Tool.FORM_VECTORS.COUNTRY_CD);
        String countryStr = pForm.getCountry();
        if (countryStr != null && countries != null) {
            for (Object country1 : countries) {
                CountryData country = (CountryData) country1;
                if (country.getShortDesc().equals(countryStr)) {
                    return country;
                }
            }
        }
        return CountryData.createValue();
    }

    private static BuildingServicesContractorView findBSC(HttpServletRequest request, String pBSCName) {

        BuildingServicesContractorViewVector bscs = (BuildingServicesContractorViewVector) request.getSession().getAttribute(Admin2Tool.FORM_VECTORS.BSC);

        if (bscs == null || bscs.size() == 0) {
            return null;
        }

        for (Object oBsc : bscs) {
            BuildingServicesContractorView bsc = (BuildingServicesContractorView) oBsc;
            if (bsc.getBusEntityData().getShortDesc().equals(pBSCName)) {
                return bsc;
            }
        }

        return null;

    }

    public static void initFormVectors(HttpServletRequest request) throws Exception {
        Admin2Tool.initFormVectors(request,
                Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD,
                Admin2Tool.FORM_VECTORS.BSC,
                Admin2Tool.FORM_VECTORS.COUNTRY_CD);
    }

    public static ActionErrors create(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = init((Admin2SiteDetailMgrForm) null, request);
        if (ae.size() > 0) {
            return ae;
        }

        Admin2SiteMgrForm sForm = (Admin2SiteMgrForm) session.getAttribute(ADMIN2_SITE_MGR_FORM);
        ae = checkRequest(sForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm.setAccountFilter(sForm.getAccountFilter());

        initDetailFields(request, pForm);

        session.setAttribute(ADMIN2_SITE_DETAIL_MGR_FORM, pForm);

        return ae;
    }

    private static void initDetailFields(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {

        log.info("initDetailFields => BEGIN");

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getStoreId();

        pForm.setBlanketPoNumId(0);
        pForm.setName("");
        pForm.setStatusCd("");
        pForm.setId(0);
        pForm.setAccountId(String.valueOf(0));
        pForm.setAccountName("");
        pForm.setBlanketPos(null);
        pForm.setSiteFields(null);
        pForm.setTypeCd("");
        pForm.setSiteNumber("");
        pForm.setSiteReferenceNumber("");
        pForm.setDistSiteReferenceNumber("");
        pForm.setLineLevelCode("");
        pForm.setOldSiteNumber("");
        pForm.setEffDate("");
        pForm.setSiteData(null);
        pForm.setExpDate("");
        pForm.setSubContractor("");
        pForm.setTargetFacilityRank("");
        pForm.setTaxableIndicator("");
        pForm.setInventoryShopping("");
        pForm.setInventoryShoppingType("");
        pForm.setInventoryShoppingHoldOrderUntilDeliveryDate("");
        pForm.setStoreId(storeId);
        pForm.setStoreName(storeD.getBusEntity().getShortDesc());
        pForm.setName1("");
        pForm.setName2("");
        pForm.setPostalCode("");
        pForm.setStateOrProv("");
        pForm.setCounty("");
        pForm.setCountry("");
        pForm.setCity("");
        pForm.setStreetAddr1("");
        pForm.setStreetAddr2("");
        pForm.setStreetAddr3("");
        pForm.setStreetAddr4("");

        pForm.setComments("");
        pForm.setShippingMessage("");

        pForm.setF1Value("");
        pForm.setF2Value("");
        pForm.setF3Value("");
        pForm.setF4Value("");
        pForm.setF5Value("");
        pForm.setF6Value("");
        pForm.setF7Value("");
        pForm.setF8Value("");
        pForm.setF9Value("");
        pForm.setF10Value("");

        pForm.setERPEnabled(String.valueOf(false));
        pForm.setAvailableShipto(new IdVector());

        pForm.setShareBuyerOrderGuides("");

        pForm.setClone(false);
        pForm.setCloneCode(null);
        pForm.setCloneId(0);

        initAccountFields(request, pForm);

        log.info("initDetailFields => END");

    }

    public static void initAccountFields(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {

        log.info("initAccountFields => BEGIN");

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser.isaAccountAdmin()) {
            initAccountFields(request, pForm, appUser.getUserAccount().getAccountId());
        } else if (pForm.getAccountFilter() != null && !pForm.getAccountFilter().isEmpty()) {
            initAccountFields(request, pForm, ((AccountData) pForm.getAccountFilter().get(0)).getAccountId());
        }

        log.info("initAccountFields => END");

    }

    private static void initAccountFields(HttpServletRequest request, Admin2SiteDetailMgrForm pForm, int pAccountId) throws Exception {

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        log.info("initAccountFields => pAccount: " + pAccountId);

        CustomerRequestPoNumber custPoEjb = factory.getCustomerRequestPoNumberAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        Account accountEjb = factory.getAccountAPI();

        BusEntityData account = accountEjb.getAccountBusEntity(pAccountId);

        pForm.setAccountId(String.valueOf(account.getBusEntityId()));
        pForm.setAccountName(String.valueOf(account.getShortDesc()));

        BlanketPoNumDataVector bpodv = custPoEjb.getBlanketPosForBusEntity(account.getBusEntityId());
        bpodv = bpodv == null || bpodv.isEmpty() ? null : bpodv;

        pForm.setBlanketPos(bpodv);

        BusEntityFieldsData sfd = propEjb.fetchSiteFieldsData(account.getBusEntityId());
        pForm.setSiteFields(sfd);

    }


    public static ActionErrors cloneSite(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        pForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        String name = pForm.getSiteData().getBusEntity().getShortDesc();
        name = "Clone of >>> " + Utility.strNN(name);
        pForm.setName(name);
        pForm.setClone(true);
        pForm.setCloneId(pForm.getIntId());
        pForm.setId(0);

        pForm.setSiteNumber("");
        pForm.setSiteReferenceNumber("");
        pForm.setDistSiteReferenceNumber("");

        return ae;
    }

    public static ActionErrors cloneSiteWithRelationships(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {
        pForm.setCloneCode(Admin2SiteDetailMgrForm.CLONE_WITH_RELATIONSHIPS);
        return cloneSite(request, pForm);
    }

      public static ActionErrors cloneSiteWithoutRelationships(HttpServletRequest request, Admin2SiteDetailMgrForm pForm) throws Exception {
        pForm.setCloneCode(Admin2SiteDetailMgrForm.CLONE_WITHOUT_RELATIONSHIPS);
        return cloneSite(request, pForm);
    }

    public static ActionErrors init(HttpServletRequest request, Admin2SiteBudgetMgrForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        Admin2SiteDetailMgrForm dForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
        if (dForm == null || !dForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_SITE_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (dForm.getSiteData() == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noSiteInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        pForm = new Admin2SiteBudgetMgrForm();
        pForm.init();

        pForm.setSiteId(dForm.getSiteData().getSiteId());
        pForm.setSiteName(dForm.getSiteData().getBusEntity().getShortDesc());
        pForm.setAccountId(dForm.getSiteData().getAccountId());
        pForm.setAccountName(dForm.getSiteData().getAccountBusEntity().getShortDesc());

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        session.setAttribute(ADMIN2_SITE_BUDGET_MGR_FORM, pForm);

        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors getBudgets(HttpServletRequest request, Admin2SiteBudgetMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        ActionErrors ae = init(request, (Admin2SiteBudgetMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = (Admin2SiteBudgetMgrForm) session.getAttribute(ADMIN2_SITE_BUDGET_MGR_FORM);
        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        // Get the site data
        Budget budgetEjb = factory.getBudgetAPI();
        Site siteEjb = factory.getSiteAPI();
        Account accountEjb = factory.getAccountAPI();

        int siteId = pForm.getSiteId();
        int accountId = pForm.getAccountId();

        // Get all the cost centers available for the site's account
        CostCenterDataVector costCenters = accountEjb.getAllCostCenters(accountId, Account.ORDER_BY_NAME);

        FiscalPeriodView fiscalInfo = accountEjb.getFiscalInfo(accountId);

        BudgetViewVector budgetsForSite = budgetEjb.getBudgetsForSite(accountId, siteId, 0, fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear());

        // This will be the array of all the site budget info
        ArrayList<SiteBudget> budgets = new ArrayList<SiteBudget>();

        // Iterate thru the account's cost centers
        for (Object oCostCenter : costCenters) {

            CostCenterData costCenter = (CostCenterData) oCostCenter;
            int costCenterId = costCenter.getCostCenterId();

            SiteBudget siteBudget = new SiteBudget();

            siteBudget.setName(costCenter.getShortDesc());
            siteBudget.setCostCenterData(costCenter);

            // look to see if this site has a budget for this cost center
            if (budgetsForSite != null) {
                for (Object oSiteBudget : budgetsForSite) {
                    BudgetView budget = (BudgetView) oSiteBudget;
                    if (budget.getBudgetData().getCostCenterId() == costCenterId) {
                        siteBudget.setBudgetView(budget);
                        BudgetSpendView spent = siteEjb.getBudgetSpent(siteId, costCenterId);
                        siteBudget.setSiteBudgetRemaining(spent.getAmountAllocated().subtract(spent.getAmountSpent()));
                        siteBudget.setCostCenterData(costCenter);
                        siteBudget.setCurrentBudgetPeriod(spent.getBudgetPeriod());
                        siteBudget.setCurrentBudgetYear(spent.getBudgetYear());
                        break;
                    }
                }
            }
            // if it has no explicit budget, it's zero
            if (siteBudget.getBudgetView() == null) {
                BudgetData budgetData = BudgetData.createValue();
                budgetData.setCostCenterId(costCenterId);
                budgetData.setBudgetTypeCd(costCenter.getCostCenterTypeCd());
                budgetData.setBudgetYear(fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear());
                siteBudget.setBudgetView(new BudgetView(budgetData, new BudgetDetailDataVector()));
            }

            budgets.add(siteBudget);
        }

        pForm.setSiteBudgetList(budgets);
        pForm.setFiscalInfo(fiscalInfo);

        return ae;

    }

    public static ActionErrors updateBudgets(HttpServletRequest request, Admin2SiteBudgetMgrForm pForm) throws Exception {

        ActionErrors ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        Budget budgetEjb = factory.getBudgetAPI();

        // Get the site data
        ArrayList v = pForm.getSiteBudgetList();
        for (int i = 0; null != v && i < v.size(); i++) {

            SiteBudget sb = (SiteBudget) v.get(i);

            String cctype = sb.getCostCenterData().getCostCenterTypeCd();

            if (RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(cctype)) {

                BudgetView budget = sb.getBudgetView();
                if (budget.getBudgetData().getBudgetId() == 0) {
                    budget.getBudgetData().setAddBy(appUser.getUserName());
                }

                budget.getBudgetData().setBusEntityId(Integer.parseInt(sb.getId()));
                budget.getBudgetData().setBudgetTypeCd(cctype);

                budget = budgetEjb.updateBudget(budget, appUser.getUserName());

                sb.setBudgetView(budget);

            } else {

                log.info("updateBudgets => NO UPDATE, updateBudgets, upd costCenterId="
                        + sb.getCostCenterData().getCostCenterId()
                        + " site Id=" + sb.getId()
                        + " cctype=" + cctype
                        + " wrong cctype");
            }
        }

        return getBudgets(request, pForm);

    }

    public static ActionErrors workflow(Admin2SiteWorkflowMgrForm pForm , HttpServletRequest request)  throws Exception {

      log.info("workflow =>  BEGIN");

      HttpSession session = request.getSession();
      ActionErrors ae = new ActionErrors();

      if (pForm == null) {
        ae = init(pForm, request);
        if (ae.size() > 0) {
          return ae;
        }
      }
      Admin2SiteDetailMgrForm dForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
      if (dForm == null ){
        String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteDetailFormIsNull", null);
        ae.add("workflow", new ActionError(errorMess));
        return ae;
      }
      pForm.setAccountId(dForm.getAccountId());
      pForm.setAccountName(dForm.getAccountName());
      pForm.setSiteData(dForm.getSiteData());

      SiteData siteDetail = dForm.getSiteData();
      int reqSiteId = siteDetail.getSiteId();
      if (reqSiteId <= 0) {
        String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteIdWasNotSpecified", null);
        ae.add("workflow", new ActionError(errorMess));
        return ae;
      }



      APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
      Workflow wBean = factory.getWorkflowAPI();
      ListService listServiceEJB = factory.getListServiceAPI();
      try {

            if(session.getAttribute(ADMIN2_SITE_WORKFLOW_TYPES)==null) {
              RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORKFLOW_TYPE_CD");
              session.setAttribute(ADMIN2_SITE_WORKFLOW_TYPES, statusCds);
//             pForm.setWorkflowStatusVector(statusCds);
            }

            //reset
            pForm.setWorkflowFound(null);
            pForm.setAssignedWorkflowId("");
            pForm.setSelectedWorkflowId("");
            if (!Utility.isSet(pForm.getWorkflowTypeCd())) {
              pForm.setWorkflowTypeCd(RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);
            }

            //to get workflows for account
            WorkflowDataVector wDV = filterByType(getAllWorkflows(pForm.getAccountId()), pForm.getWorkflowTypeCd());

            Hashtable<Integer, WorkflowRuleDataVector> htWorkflowRulesColl=new Hashtable<Integer, WorkflowRuleDataVector>();
            if(wDV!=null)
            {
              Iterator it=wDV.iterator();

              while(it.hasNext())
              {
                int  id=((WorkflowData)it.next()).getWorkflowId();
                htWorkflowRulesColl.put(new Integer(id), wBean.getWorkflowRulesCollection(id));
              }
            }

            pForm.setWorkflowFound(wDV);
            pForm.setWorkflowRulesTab(htWorkflowRulesColl);
            //
            WorkflowData wfd = wBean.fetchWorkflowForSite(reqSiteId, pForm.getWorkflowTypeCd());

            if (wfd != null) {

              pForm.setSiteWorkflow(wfd);
              pForm.setAssignedWorkflowId(String.valueOf(wfd.getWorkflowId()));
              pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());
              pForm.setWorkflowTypeCd(wfd.getWorkflowTypeCd());

              // Get the rules for the workflow.
              //WorkflowRuleDataVector wrv =wBean.getWorkflowRulesCollection(wfd.getWorkflowId());
              pForm.setWorkflowRules(htWorkflowRulesColl.get(new Integer(wfd.getWorkflowId())));
            }

          }catch (DataNotFoundException e ){

            pForm.setSiteWorkflow(null);
            pForm.setWorkflowRules(null);
            pForm.setAssignedWorkflowId("");
            pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());

          }catch (Exception e) {

            pForm.setSiteWorkflow(null);
            pForm.setWorkflowRules(null);
            pForm.setAssignedWorkflowId("");
            pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());

            String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.problemGettingSiteWorkflow",new String[]{""+reqSiteId, e.getMessage()});
            ae.add("workflow", new ActionError(errorMess));
          }
          return ae;
  }

  public static WorkflowDataVector  getAllWorkflows(String accountId)
          throws Exception {

      APIAccess factory = new APIAccess();
      Workflow wkfl = factory.getWorkflowAPI();
      int aid = 0;
      String s = accountId;
      if (null == s)
          throw new  Exception("Site Error: the account id was not specified.");
      aid = Integer.parseInt(s);
      return   wkfl.getWorkflowCollectionByEntity(aid, RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
  }

  private static WorkflowDataVector filterByType(WorkflowDataVector allWorkflows, String pTypeCd) {
       WorkflowDataVector result = new WorkflowDataVector();
       if (!allWorkflows.isEmpty()) {
           Iterator it = allWorkflows.iterator();
           while (it.hasNext()) {
               WorkflowData wd = (WorkflowData) it.next();
               if (wd.getWorkflowTypeCd().equals(pTypeCd)) {
                   result.add(wd);
               }
           }
       }
       return result;
    }
    public static void sortWorkflows(HttpServletRequest request, Admin2SiteWorkflowMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

//        WorkflowDataVector v =(WorkflowDataVector) session.getAttribute("Site.workflow.found.vector");
        WorkflowDataVector v =pForm.getWorkflowFound();
        if (v == null)
        {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(v, sortField);
        return;
    }

    public static void setAssignedToSelectedWorkflowId(HttpServletRequest request, Admin2SiteWorkflowMgrForm pForm) {

      if(pForm!=null)
      {
       pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());
      }
    }


   public static ActionErrors linkSites(HttpServletRequest request, Admin2SiteWorkflowMgrForm pForm)
           throws Exception {

       ActionErrors ae = new ActionErrors();
       HttpSession session = request.getSession();
//       String strSiteId =(String)session.getAttribute("Site.id");
       String strWId=pForm.getSelectedWorkflowId();
       String assignWId=pForm.getAssignedWorkflowId();
       if (null == pForm.getSiteData())
       {
         String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteIdWasNotSpecified", null);
         ae.add("workflow", new ActionError(errorMess));
         return ae;
       }
       int siteId = pForm.getSiteData().getSiteId();

       APIAccess factory = new APIAccess();
       Workflow wkflBean = factory.getWorkflowAPI();
       Site siteBean = factory.getSiteAPI();

       if (assignWId==null)  assignWId="";
       if (strWId==null)   strWId="";

       if(!strWId.equals(assignWId)) {

         try {

           if(assignWId.trim().length()>0)
           {
            wkflBean.unassignWorkflow(Integer.parseInt(assignWId), siteId);
           }
           if(strWId.trim().length()>0)
           {
               WorkflowData w = null;
               int wid=Integer.parseInt(strWId);

               //Assign
               SessionTool lses = new SessionTool(request);
               wkflBean.assignWorkflow(wid, siteId, lses.getLoginName());

           }
         } catch (Exception e) {
                 String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.workflowWasNotFound",new String[]{e.getMessage()});
                 ae.add("workflow", new ActionError(errorMess));
         }

         if(ae.size()==0) {
           ae = workflow(pForm, request);
         }
       }

      return ae;
   }


    public static ActionErrors init(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        Store storeEjb = factory.getStoreAPI();

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        Admin2SiteDetailMgrForm dForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);

        if (dForm == null || !dForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_SITE_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (dForm.getSiteData() == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noSiteInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        pForm = new Admin2SiteConfigMgrForm();
        pForm.init();

        pForm.setSite(dForm.getSiteData().getBusEntity());
        pForm.setAccount(dForm.getSiteData().getAccountBusEntity());
        try {
            BusEntityData store = storeEjb.getStoreBusEntityByAccount(dForm.getSiteData().getAccountId());
            pForm.setStore(store);
        } catch (DataNotFoundException e) {
            pForm.setStore(null);
        }


        pForm.setConfFunction(Admin2SiteConfigMgrForm.CONF_FUNUNCTION.USERS);

        session.setAttribute(ADMIN2_SITE_CONFIG_MGR_FORM, pForm);

        ae = initConfig(request, pForm);

        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors initConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        log.info("initConfig => BEGIN");

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        reset(pForm);

        session.setAttribute(ADMIN2_SITE_CONFIG_MGR_FORM, pForm);

        log.info("initConfig => Conf.Function: " + pForm.getConfFunction());
        log.info("initConfig => config: " + pForm.getConfig());

        log.info("initConfig => END.");

        return ae;
    }


    private static void reset(Admin2SiteConfigMgrForm pForm) {
        pForm.setConfig(null);
        pForm.setConfSearchType(Constants.NAME_BEGINS);
        pForm.setConifiguredOnlyFl(false);
    }

    private static ActionErrors checkConfigSearchAttr(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) {
        ActionErrors ae = new ActionErrors();
        if (Constants.ID.equalsIgnoreCase(pForm.getConfSearchType()) && Utility.isSet(pForm.getConfSearchField())) {
            try {
                Integer.parseInt(pForm.getConfSearchField());
            } catch (NumberFormatException e) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.wrongIdFormat", new Object[]{pForm.getConfSearchField()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
            }
        }
        return ae;
    }

    public static ActionErrors searchConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        log.info("searchConfig => BEGIN.");

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        String confFunc = pForm.getConfFunction();

        log.info("searchConfig => confFunc: " + confFunc);

        if (Admin2SiteConfigMgrForm.CONF_FUNUNCTION.USERS.equals(confFunc)) {
            ae = searchSiteUserConfig(request, pForm);
        } else if (Admin2SiteConfigMgrForm.CONF_FUNUNCTION.CATALOG.equals(confFunc)) {
            ae = searchSiteCatalogConfig(request, pForm);
        } else if (Admin2SiteConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTOR_SCHEDULE.equals(confFunc)) {
            ae = searchDistributorScheduleConfig(request, pForm);
        }

        log.info("searchConfig => END.Errors: " + ae);

        return ae;
    }

    private static ActionErrors searchDistributorScheduleConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        log.info("searchDistributorScheduleConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info(" searchDistributorScheduleConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config = getDistributorScheduleConfig(request, pForm);

        pForm.setConfig(config);

        log.info(" searchDistributorScheduleConfig => END.");

        return ae;
    }

    private static SelectableObjects getDistributorScheduleConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        Distributor distrEjb = factory.getDistributorAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        int siteId = pForm.getSite().getBusEntityId();

        Hashtable<String, String> critParams = new Hashtable<String, String>();
        critParams.put(SITE_ID, String.valueOf(siteId));
        boolean beginsWithFl = Constants.NAME_BEGINS.equals(searchType);
        if (Utility.isSet(fieldValue)) {
            critParams.put(DISTRIBUTOR, fieldValue);
        }

        DeliveryScheduleViewVector selected = distrEjb.getDeliverySchedules(0, critParams, beginsWithFl);

        return new SelectableObjects(selected, selected, null, true);
    }

    private static ActionErrors searchSiteCatalogConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws RemoteException, APIServiceAccessException {

        log.info("searchSiteCatalogConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("searchSiteCatalogConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config =  getSiteCatalogConfig(request, pForm);

        pForm.setConfig(config);

        log.info("searchSiteCatalogConfig => END.");

        return ae;

    }

    private static SelectableObjects getSiteCatalogConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws RemoteException, APIServiceAccessException {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        int accountId = pForm.getAccount().getBusEntityId();
        int siteId = pForm.getSite().getBusEntityId();

        // Get the current site catalog

        CatalogDataVector selected = new CatalogDataVector();
        CatalogDataVector options;

        CatalogData siteCatalog = catInfEjb.getSiteCatalog(siteId);
        if (siteCatalog != null) {
            if (Utility.isSet(fieldValue)) {
                fieldValue = fieldValue.toUpperCase();
                String catalogName = siteCatalog.getShortDesc().toUpperCase();
                int ind = catalogName.indexOf(fieldValue);
                if (ind == 0 && Constants.NAME_BEGINS.equals(searchType)) {
                    selected.add(siteCatalog);
                } else if (ind >= 0 && Constants.NAME_CONTAINS.equals(searchType)) {
                    selected.add(siteCatalog);
                }
            } else {
                selected.add(siteCatalog);
            }
        }

        if (Utility.isSet(fieldValue)) {
            if (Constants.NAME_BEGINS.equals(searchType)) {
                options = catInfEjb.getCatalogsCollectionByNameAndBusEntity(fieldValue, SearchCriteria.BEGINS_WITH_IGNORE_CASE, accountId);
            } else { // "nameContains"
                options = catInfEjb.getCatalogsCollectionByNameAndBusEntity(fieldValue, SearchCriteria.CONTAINS_IGNORE_CASE, accountId);
            }
        } else {
            options = catInfEjb.getCatalogsByAccountId(accountId);
        }

        if (options != null) {
            for (Iterator iter = options.iterator(); iter.hasNext();) {
                CatalogData cD = (CatalogData) iter.next();
                String catalogType = cD.getCatalogTypeCd();
                String catalogStatus = cD.getCatalogStatusCd();
                if (!RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogStatus) &&
                        !RefCodeNames.CATALOG_STATUS_CD.LIMITED.equals(catalogStatus)) {
                    iter.remove();
                } else if (!RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(catalogType) &&
                        !RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING.equals(catalogType)) {
                    iter.remove();
                }
            }
        }

        return new SelectableObjects(selected, options, CATALOG_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());

    }

    private static ActionErrors searchSiteUserConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {
        log.info("searchSiteUserConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("searchSiteUserConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config =  getSiteUserConfig(request, pForm);

        pForm.setConfig(config);

        log.info("searchSiteUserConfig => END.");

        return ae;
    }

    private static SelectableObjects getSiteUserConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        User userEjb = factory.getUserAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        searchCriteria.setStoreId(pForm.getStore().getBusEntityId());
        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
        searchCriteria.setAccountId(pForm.getAccount().getBusEntityId());
        if (appUser.isaAccountAdmin()) {
            searchCriteria.setUserAccountIds(Utility.toIdVector(appUser.getAccounts()));
        }

        if (Utility.isSet(fieldValue)) {
            if (Constants.ID.equalsIgnoreCase(searchType)) {
                searchCriteria.setUserId(fieldValue);
            } else if (Constants.NAME_CONTAINS.equals(searchType)) {
                searchCriteria.setUserName(fieldValue);
                searchCriteria.setUserNameMatch(User.NAME_CONTAINS_IGNORE_CASE);
            } else if (Constants.NAME_BEGINS.equals(searchType)) {
                searchCriteria.setUserName(fieldValue);
                searchCriteria.setUserNameMatch(User.NAME_BEGINS_WITH_IGNORE_CASE);
            }
        }

        searchCriteria.setIncludeInactiveFl(true);

        Admin2Tool.initFormVectors(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);
        List userTypeCds = Admin2Tool.getFormVector(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);
        List<String> userTypes = Admin2Tool.toValueList((RefCdDataVector) userTypeCds);
        searchCriteria.setUserTypes(userTypes);

        UserDataVector options = userEjb.getUsersCollectionByCriteria(searchCriteria);

        searchCriteria.setSiteIds(Utility.toIdVector(pForm.getSite().getBusEntityId()));

        UserDataVector selected = userEjb.getUsersCollectionByCriteria(searchCriteria);

        return new SelectableObjects(selected, options, USER_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());
    }

    public static ActionErrors updateSiteCatalogConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        log.info("updateSiteUserConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        SelectableObjects configObjs = pForm.getConfig();
        if (configObjs != null) {

            ae = chaeckSiteCatalogConfigObj(request, pForm, configObjs);
            if (ae.size() > 0) {
                return ae;
            }

            ae = updateSiteCatalogConfig(request, pForm, configObjs);
            if (ae.size() > 0) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        } else {
            log.info("updateSiteConfig => no objects to update.");
        }

        log.info("updateSiteUserConfig => END.");

        return ae;

    }

    private static ActionErrors chaeckSiteCatalogConfigObj(HttpServletRequest request, Admin2SiteConfigMgrForm pForm, SelectableObjects pConfigObjs) {

        ActionErrors ae = new ActionErrors();

        List newlySelected = pConfigObjs.getNewlySelected();
        if (newlySelected.size() > 1) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.onlyOneCatalogCanBeSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        List delected = pConfigObjs.getDeselected();
        if (delected.size() > 1) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.onlyOneCatalogCanBeDeselected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    private static ActionErrors updateSiteCatalogConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm, SelectableObjects pConfigObjs) throws Exception {

        log.info("updateSiteCatalogConfig => BEGIN");

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        Catalog catalogEjb = factory.getCatalogAPI();

        IdVector selected = Utility.toIdVector(pConfigObjs.getNewlySelected());
        IdVector deselected = Utility.toIdVector(pConfigObjs.getDeselected());

        log.info("updateSiteCatalogConfig => selected: " + selected);
        log.info("updateSiteCatalogConfig => deselected: " + deselected);

        if (!selected.isEmpty()) {
            catalogEjb.resetCatalogAssoc((Integer) selected.get(0), pForm.getSite().getBusEntityId(), appUser.getUserName());
        } else if (!deselected.isEmpty()) {
            catalogEjb.removeCatalog((Integer) deselected.get(0), appUser.getUserName());
        }

        log.info("updateSiteCatalogConfig => END.");

        return ae;
    }

    public static ActionErrors updateSiteUserConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm) throws Exception {

        log.info("updateSiteUserConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        SelectableObjects configObjs = pForm.getConfig();
        if (configObjs != null) {
            ae = updateSiteUserConfig(request, pForm, configObjs);
            if (ae.size() > 0) {
                return ae;
            }
            ae = searchConfig(request, pForm);
        } else {
            log.info("updateSiteConfig => no objects to update.");
        }

        log.info("updateSiteUserConfig => END.");

        return ae;

    }

    private static ActionErrors updateSiteUserConfig(HttpServletRequest request, Admin2SiteConfigMgrForm pForm, SelectableObjects pConfigObjs) throws Exception {

        log.info(" updateSiteUserConfig => BEGIN");

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        User userEjb = factory.getUserAPI();

        IdVector selected = Utility.toIdVector(pConfigObjs.getNewlySelected());
        IdVector deselected = Utility.toIdVector(pConfigObjs.getDeselected());

        log.info(" updateSiteUserConfig => selected: " + selected);
        log.info(" updateSiteUserConfig => deselected: " + deselected);

        // Looking for two cases:
        // 1. user is selected, but not currently associated - this
        //    means we need to add the association
        // 2. user is not selected, but is currently associated - this
        //    means we need to remove the association

        for (Object oUserId : deselected) {
            userEjb.removeUserAssoc((Integer) oUserId, pForm.getSite().getBusEntityId());
        }

        for (Object oUserId : selected) {
            userEjb.addUserAssoc((Integer) oUserId, pForm.getSite().getBusEntityId(), RefCodeNames.USER_ASSOC_CD.SITE);
        }

        log.info("updateSiteUserConfig => END.");

        return ae;

    }

    private static ActionErrors checkSiteSearchAttr(Admin2SiteMgrForm pForm, HttpServletRequest request) {
        ActionErrors ae = new ActionErrors();
        if (Constants.ID.equalsIgnoreCase(pForm.getSearchType()) && Utility.isSet(pForm.getSearchField())) {
            try {
                Integer.parseInt(pForm.getSearchField());
            } catch (NumberFormatException e) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.wrongIdFormat", new Object[]{pForm.getSearchField()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
            }
        }
        return ae;
    }


}
