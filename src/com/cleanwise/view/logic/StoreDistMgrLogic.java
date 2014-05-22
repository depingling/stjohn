package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.ContactView;
import com.cleanwise.service.api.value.ContactViewVector;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.DistBranchData;
import com.cleanwise.service.api.value.DistShipFromAddressViewVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.FreightHandlerViewVector;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.view.forms.FhMgrDetailForm;
import com.cleanwise.view.forms.FhMgrSearchForm;
import com.cleanwise.view.forms.ItemMgrCatalogForm;
import com.cleanwise.view.forms.LocateStoreGroupForm;
import com.cleanwise.view.forms.StoreDistMgrDetailForm;
import com.cleanwise.view.forms.StoreDistMgrLocateShipForm;
import com.cleanwise.view.forms.StoreDistMgrSearchForm;
import com.cleanwise.view.forms.StoreFhMgrSearchForm;
import com.cleanwise.view.forms.StoreItemCatalogMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.validators.EmailValidator;

/**
 *  <code>StoreDistMgrLogic</code> implements the logic needed to manipulate
 *  purchasing site records.
 *
 *@author     Veronika Denega
 *@created    June, 2006
 */
public class StoreDistMgrLogic {
    private static final String DIST_STORE_MAP_CACHE =
            "distributor.store.map.cache";

    /**
     *updates the caching between stores and distributors
     */
    private static void resetStoreDistributorCache(HttpSession pSession,
                                                   int pDistributorId,
                                                   int pStoreId) {
        if (pDistributorId == 0) {
            return;
        }
        Map storeMap = (HashMap) pSession.getAttribute(DIST_STORE_MAP_CACHE);
        if (storeMap != null) {
            Integer key = new Integer(pDistributorId);
            storeMap.remove(key);
        }
    }

    /**
     *  <code>getAll</code> distributors, note that the Distributor
     *  list returned will be limited to a certain amount of
     *  records. It is up to the jsp page to detect this and to issued
     *  a subsequent call to get more records.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception Exception if an error occurs */
    public static void getAll(HttpServletRequest request,
                              ActionForm form) throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        DistributorDataVector dv;

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().
                getUserTypeCd())) {
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            dv = distBean.getDistributorByCriteria(crit);
        } else {
            dv = distBean.getAllDistributors(Distributor.ORDER_BY_NAME);
        }

        session.setAttribute("StoreDist.found.vector", dv);
    }


    /**
     *  Describe <code>getDetail</code> method here.
     *
     * @param  request        a <code>HttpServletRequest</code> value
     * @param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors getDetail(HttpServletRequest request,
                                 ActionForm form) throws Exception {

        ActionErrors ae=new ActionErrors();
        initFormVectors(request);

        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm =
                (StoreDistMgrDetailForm) session.getAttribute("STORE_DIST_DETAIL_FORM");

        if (sForm == null) {
            sForm = new StoreDistMgrDetailForm();
            session.setAttribute("STORE_DIST_DETAIL_FORM",
                    sForm);
            request.setAttribute("STORE_DIST_DETAIL_FORM",
                    sForm);
        }

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        String fieldValue = request.getParameter("searchField");
        if (null == fieldValue) {
            fieldValue = "0";

            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        Integer id = new Integer(fieldValue);
        DistributorData dd = null;
        try {
            dd = distBean.getDistributorForStore(id.intValue(),appUser.getUserStoreAsIdVector(),true);

        }  catch (DataNotFoundException e) {
            ae.add("error",new ActionError("error.simpleGenericError",e.getMessage()));
            sForm= new  StoreDistMgrDetailForm();
            request.setAttribute("STORE_DIST_DETAIL_FORM",sForm);
            init(request,sForm);
            return ae;
       }
      if (null != dd) {
            BusEntityData bed = dd.getBusEntity();
            // Set the form values.
            sForm.setStoreId(Integer.toString(dd.getStoreId()));
            sForm.setName(bed.getShortDesc());
            sForm.setStatusCd(bed.getBusEntityStatusCd());
            sForm.setId(String.valueOf(bed.getBusEntityId()));
            sForm.setTypeCd(bed.getBusEntityTypeCd());
            sForm.setDistNumber(bed.getErpNum());
            sForm.setDistLocale(bed.getLocaleCd());
            sForm.setCallHours(dd.getCallHours());
            sForm.setRuntimeDisplayName(dd.getRuntimeDisplayName());
            sForm.setHoldInvoiceDays(Integer.toString(dd.getHoldInvoiceDays()));
            if(dd.getMinimumOrderAmount() != null){
                sForm.setMinimumOrderAmount(dd.getMinimumOrderAmount().toString());
            }else{
                sForm.setMinimumOrderAmount("");
            }

            String s1 = dd.getSmallOrderHandlingFee();
            if( s1 != null){
                sForm.setSmallOrderHandlingFee(s1);
            }else{
                sForm.setSmallOrderHandlingFee("");
            }

            s1 = dd.getWebInfo();
            if( s1 != null){
                sForm.setWebInfo(s1);
            }else{
                sForm.setWebInfo("");
            }

            s1 = dd.getAccountNumbers();
            if( s1 != null){
                sForm.setAccountNumbers(s1);
            }else{
                sForm.setAccountNumbers("");
            }

            if(dd.getExceptionOnOverchargedFreight() != null){
                if(dd.getExceptionOnOverchargedFreight().equals(Boolean.TRUE)){
                    sForm.setExceptionOnOverchargedFreight("Y");
                }else{
                    sForm.setExceptionOnOverchargedFreight("N");
                }
            }else{
                sForm.setExceptionOnOverchargedFreight(null);
            }

            if(dd.getExceptionOnTaxDifference() != null){
                sForm.setExceptionOnTaxDifference(dd.getExceptionOnTaxDifference());
            }

            sForm.setMaxInvoiceFreightAllowed("");
            if (dd.getMaxInvoiceFreightAllowed() != null) {
                sForm.setMaxInvoiceFreightAllowed
                        (dd.getMaxInvoiceFreightAllowed());
            }

            if(dd.getIgnoreOrderMinimumForFreight() != null){
                if(dd.getIgnoreOrderMinimumForFreight().equals(Boolean.TRUE)){
                    sForm.setIgnoreOrderMinimumForFreight("Y");
                }else{
                    sForm.setIgnoreOrderMinimumForFreight("N");
                }
            }else{
                sForm.setIgnoreOrderMinimumForFreight(null);
            }

            if(dd.getAllowFreightOnBackorders() != null){
                if(dd.getAllowFreightOnBackorders().equals(Boolean.TRUE)){
                    sForm.setAllowFreightOnBackorders("Y");
                }else{
                    sForm.setAllowFreightOnBackorders("N");
                }
            }else{
                sForm.setAllowFreightOnBackorders(null);
            }

            if(dd.getCancelBackorderedLines() != null){
                if(dd.getCancelBackorderedLines().equals(Boolean.TRUE)){
                    sForm.setCancelBackorderedLines("Y");
                }else{
                    sForm.setCancelBackorderedLines("N");
                }
            }else{
                sForm.setCancelBackorderedLines(null);
            }

            if(dd.getAllowFreightOnFreightHandlerOrders() != null){
                if(dd.getAllowFreightOnFreightHandlerOrders().equals(Boolean.TRUE)){
                    sForm.setAllowFreightOnFreightHandlerOrders("Y");
                }else{
                    sForm.setAllowFreightOnFreightHandlerOrders("N");
                }
            }else{
                sForm.setAllowFreightOnFreightHandlerOrders(null);
            }

            if(dd.getPrintCustContactOnPurchaseOrder() != null){
                if(dd.getPrintCustContactOnPurchaseOrder().equals(Boolean.TRUE)){
                    sForm.setPrintCustContactOnPurchaseOrder("Y");
                }else{
                    sForm.setPrintCustContactOnPurchaseOrder("N");
                }
            }else{
                sForm.setPrintCustContactOnPurchaseOrder(null);
            }

            if(dd.getManualPOAcknowldgementRequiered() != null){
                if(dd.getManualPOAcknowldgementRequiered().equals(Boolean.TRUE)){
                    sForm.setManualPOAcknowldgementRequiered("Y");
                }else{
                    sForm.setManualPOAcknowldgementRequiered("N");
                }
            }else{
                sForm.setManualPOAcknowldgementRequiered("Y");
            }

            if(dd.getDoNotAllowInvoiceEdits() != null){
                if(dd.getDoNotAllowInvoiceEdits().equals(Boolean.TRUE)){
                    sForm.setDoNotAllowInvoiceEdits("Y");
                }else{
                    sForm.setDoNotAllowInvoiceEdits("N");
                }
            }else{
                sForm.setDoNotAllowInvoiceEdits("N");
            }

            if(dd.getPurchaseOrderComments()!=null){
                sForm.setPurchaseOrderComments(dd.getPurchaseOrderComments().toString());
            }else{
                sForm.setPurchaseOrderComments("");
            }

            if(dd.getDistributorsCompanyCode()!=null){
                sForm.setDistributorsCompanyCode(dd.getDistributorsCompanyCode());
            }else{
                sForm.setDistributorsCompanyCode("");
            }

            if(dd.getInvoiceLoadingPriceModel()!=null){
                sForm.setInvoiceLoadingPriceModel(dd.getInvoiceLoadingPriceModel().
                        toString());
            }else{
                sForm.setInvoiceLoadingPriceModel("");
            }
            if(dd.getInvoiceAmountPercentAllowanceUpper()!=null){
                sForm.setInvoiceAmountPercentAllowanceUpper(dd.
                        getInvoiceAmountPercentAllowanceUpper().toString());
            }else{
                sForm.setInvoiceAmountPercentAllowanceUpper("");
            }
            if(dd.getInvoiceAmountPercentAllowanceLower()!=null){
                sForm.setInvoiceAmountPercentAllowanceLower(dd.
                		getInvoiceAmountPercentAllowanceLower().toString());
            }else{
                sForm.setInvoiceAmountPercentAllowanceLower("");
            }
            if(dd.getAllowedFreightSurchargeAmount()!=null){
                sForm.setAllowedFreightSurchargeAmount(dd.
                		getAllowedFreightSurchargeAmount().toString());
            }else{
                sForm.setAllowedFreightSurchargeAmount("");
            }

            if(dd.getReceivingSystemInvoiceCd()!=null){
                sForm.setReceivingSystemInvoiceCd(dd.getReceivingSystemInvoiceCd().
                        toString());
            }else{
                sForm.setReceivingSystemInvoiceCd("");
            }

            sForm.setExchangeCompanyCode(Utility.strNN(dd.getExchangeCompanyCode()));
            sForm.setExchangeInventoryURL(Utility.strNN(dd.getExchangeInventoryURL()));
            sForm.setExchangeUser(Utility.strNN(dd.getExchangeUser()));
            sForm.setExchangePassword(Utility.strNN(dd.getExchangePassword()));

            PhoneData fax;
            PhoneData poFax;
            PhoneData phone;

            fax = dd.getPrimaryFax();
            sForm.setFax(fax.getPhoneNum());

            poFax = dd.getPoFax();
            sForm.setPoFax(poFax.getPhoneNum());

            phone = dd.getPrimaryPhone();
            sForm.setPhone(phone.getPhoneNum());

            EmailData email = dd.getPrimaryEmail();
            sForm.setEmailAddress(email.getEmailAddress());
            
            EmailData rejectedInvEmail = dd.getRejectedInvEmail();
            sForm.setRejectedInvEmail(rejectedInvEmail.getEmailAddress());

            AddressData address = dd.getPrimaryAddress();
            sForm.setName1(address.getName1());
            sForm.setName2(address.getName2());
            sForm.setPostalCode(address.getPostalCode());
            sForm.setStateOrProv(address.getStateProvinceCd());
            sForm.setCountry(address.getCountryCd());
            sForm.setCity(address.getCity());
            sForm.setStreetAddr1(address.getAddress1());
            sForm.setStreetAddr2(address.getAddress2());
            sForm.setStreetAddr3(address.getAddress3());

            AddressData billingAddress = dd.getBillingAddress();
            if(billingAddress==null) billingAddress = AddressData.createValue();
            sForm.setBillingAddress(billingAddress);

            PropertyData prop = dd.getDistributorTypeProp();
            sForm.setTypeDesc(prop.getValue());

            sForm.setCustomerReferenceCode(dd.getCustomerReferenceCode());


            getShipFromForDist( request, bed.getBusEntityId());
            session.setAttribute("STORE_DIST_DETAIL_FORM",
                    sForm);
            request.setAttribute("STORE_DIST_DETAIL_FORM",
                    sForm);

            //Get additional contacts

            ContactViewVector contactVwV =
                    distBean.getDistributorContacts(id.intValue());
            sForm.setAdditionalContacts(contactVwV);
            //Get groups
            GroupDataVector groupDV = distBean.getDistributorGroups(id.intValue());
            sForm.setDistGroups(groupDV);

            //Primary manufactruers
            BusEntityDataVector manufacturers = distBean.getPrimaryManufacturers(id.
                    intValue());
            sForm.setPrimaryManufacturers(manufacturers);

            //Branches
            ArrayList branches =
                    distBean.getBranchesCollection(id.intValue());
            sForm.setBranchesCollection(branches);

            //States served
            ArrayList servedStates = distBean.getServedStates(id.intValue());
            sForm.setServedStates(servedStates);

            //Accounts served
            BusEntityDataVector servedAccounts = distBean.getServedAccounts(id.
                    intValue());
            sForm.setServedAccounts(servedAccounts);

        }
        return ae;

    }

    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
                            ActionForm form) throws Exception {
        HttpSession session = request.getSession();

        initFormVectors(request);

        IdVector ids = null;
        String filter = request.getParameter("locateFilter");
        if (filter != null && filter.equals("itemCatalog")) {
            ItemMgrCatalogForm itemForm =
                    (ItemMgrCatalogForm) session.getAttribute("ITEM_CATALOG_FORM");
            if (itemForm != null) {
                ids = itemForm.getPossibleDistributorIds();
            }
        }
        if (ids != null) {
            session.setAttribute("StoreDist.filter.vector", ids);
        } else {
            session.removeAttribute("StoreDist.filter.vector");
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (form instanceof StoreDistMgrDetailForm) {
            StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
            Manufacturer manufEjb = factory.getManufacturerAPI();
            readStoreManufacturers(manufEjb, sForm);
        }
        return;
    }


    private static ActionErrors readStoreManufacturers(Manufacturer manufEjb,
                                                       StoreDistMgrDetailForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        if(pForm!=null&&pForm.getStoreId()!=null&&pForm.getStoreId().trim().length()>0)
        {
        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
        IdVector storeIdAsV = new IdVector();
        storeIdAsV.add(new Integer(pForm.getStoreId()));
        besc.setStoreBusEntityIds(storeIdAsV);

        ManufacturerDataVector manufDV = manufEjb.getManufacturerByCriteria(besc);
        if (manufDV.size() > 1) {
            Object[] manufA = manufDV.toArray();
            for (int ii = 0; ii < manufA.length - 1; ii++) {
                boolean exitFl = true;
                for (int jj = 0; jj < manufA.length - ii - 1; jj++) {
                    ManufacturerData mD1 = (ManufacturerData) manufA[jj];
                    ManufacturerData mD2 = (ManufacturerData) manufA[jj + 1];
                    int comp = mD1.getBusEntity().getShortDesc().compareToIgnoreCase(mD2.
                            getBusEntity().getShortDesc());
                    if (comp > 0) {
                        manufA[jj] = mD2;
                        manufA[jj + 1] = mD1;
                        exitFl = false;
                    }
                }
                if (exitFl)break;
            }
            manufDV = new ManufacturerDataVector();
            for (int ii = 0; ii < manufA.length; ii++) {
                ManufacturerData mD1 = (ManufacturerData) manufA[ii];
                manufDV.add(mD1);
            }
        }
        pForm.setStoreManufacturers(manufDV);
        }
        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initFormVectors(HttpServletRequest request) throws
            Exception {

        HttpSession session = request.getSession();

        // Cache the lists needed for Sites.
        APIAccess factory = new APIAccess();

        ListService lsvc = factory.getListServiceAPI();

        if (session.getAttribute("Dist.status.vector") == null) {
            RefCdDataVector statusv =
                    lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
            session.setAttribute("Dist.status.vector", statusv);
        }

        if (session.getAttribute("countries.vector") == null) {
        	Country countryBean = factory.getCountryAPI();
            CountryDataVector countriesv = countryBean.getAllCountries();
            session.setAttribute("countries.vector", countriesv);
        }

        if (session.getAttribute("Dist.type.vector") == null) {
            RefCdDataVector dtypev =
                    lsvc.getRefCodesCollection("DISTRIBUTOR_TYPE_CD");
            session.setAttribute("Dist.type.vector", dtypev);
        }

        if (session.getAttribute("RECEIVING_SYSTEM_INVOICE_CD") == null) {
            RefCdDataVector refcds =
                    lsvc.getRefCodesCollection("RECEIVING_SYSTEM_INVOICE_CD");
            session.setAttribute("RECEIVING_SYSTEM_INVOICE_CD", refcds);
        }
        session.removeAttribute("distributor.catalogs.vector");
        session.removeAttribute("distributor.sites.vector");

    }


    /**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void search(HttpServletRequest request,
                              ActionForm form) throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        StoreDistMgrSearchForm sForm = (StoreDistMgrSearchForm) form;

        // Reset the session variables.
        DistributorDataVector dv = new DistributorDataVector();
        session.setAttribute("StoreDist.found.vector", dv);

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        String searchCity = sForm.getSearchCity();
        String searchState = sForm.getSearchState();
        String searchCounty = sForm.getSearchCounty();
        String searchPostalCode = sForm.getSearchPostalCode();
        String searchGroupId = sForm.getSearchGroupId();

        dv = search(request, fieldValue, fieldSearchType,
                searchCity, searchState, searchCounty,
                searchPostalCode, searchGroupId,
                sForm.getSearchShowInactiveFl());

        session.setAttribute("StoreDist.found.vector", dv);
    }

    /**
     *Returns a distributor data vector based off the specified search criteria
     */
    static public DistributorDataVector search(HttpServletRequest request,
                                               String fieldValue,
                                               String fieldSearchType,
                                               String searchCity,
                                               String searchState,
                                               String searchCounty,
                                               String searchPostalCode,
                                               String searchGroupId,
                                               boolean showInactiveFl) throws
            Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());

        if (fieldValue != null && fieldValue.trim().length() > 0) {
            fieldValue = fieldValue.trim();
            if ("id".equals(fieldSearchType)) {
                crit.setSearchId(fieldValue);
            } else {
                if ("nameBegins".equals(fieldSearchType)) {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                } else {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                }
            }
        }

        if (searchCity != null && searchCity.trim().length() > 0) {
            crit.setSearchTerritoryCity(searchCity.trim());
        }
        if (searchState != null && searchState.trim().length() > 0) {
            crit.setSearchTerritoryState(searchState.trim());
        }
        if (searchCounty != null && searchCounty.trim().length() > 0) {
            crit.setSearchTerritoryCounty(searchCounty.trim());
        }
        if (searchPostalCode != null && searchPostalCode.trim().length() > 0) {
            crit.setSearchTerritoryPostalCode(searchPostalCode.trim());
        }
        if (searchGroupId != null && searchGroupId.trim().length() > 0) {
            crit.setSearchGroupId(searchGroupId.trim());
        }
        crit.setSearchForInactive(showInactiveFl);

        crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        DistributorDataVector dv = distBean.getDistributorByCriteria(crit);
        return dv;
    }

    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
                            ActionForm form) throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        DistributorDataVector distributors =
                (DistributorDataVector) session.getAttribute("StoreDist.found.vector");
        if (distributors == null) {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(distributors, sortField);

        // Reset the distributor specific information.
        StoreDistMgrDetailForm sForm =
                (StoreDistMgrDetailForm) session.getAttribute("STORE_DIST_DETAIL_FORM");
        request.setAttribute("STORE_DIST_DETAIL_FORM", sForm);
        session.setAttribute("STORE_DIST_DETAIL_FORM", sForm);
    }


    public static ActionErrors addShipFromAddress
            (HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        AddressData addr = sForm.getShipFrom();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        addr.setAddBy(cuser);
        addr.setBusEntityId(sForm.getIntId());

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        if (distBean.addShipFromAddress(addr)) {
            getShipFromForDist(request, sForm.getIntId());
        } else {
            ae.add("distributor",
                    new ActionError
                            ("The ship from address is a duplicate."));
        }
        return ae;
    }

    public static void getShipFromForDist(HttpServletRequest request,
                                          int pDistId) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        DistShipFromAddressViewVector shipFroms =
                distBean.getShipFromAddressCollection(pDistId, null);

        session.setAttribute("StoreDist.ship_from.vector", shipFroms);
    }

    public static void locateShipFrom(HttpServletRequest request,
                                      ActionForm pForm,
                                      String pAction) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        DistShipFromAddressViewVector shipFroms =
                new DistShipFromAddressViewVector();
        StoreDistMgrLocateShipForm sForm =
                (StoreDistMgrLocateShipForm) pForm;

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        Integer distId = null;
        try {
            String distIdS = sForm.getDistributorId();
            if (distIdS != null) {
                distId = new Integer(distIdS);
            }
        } catch (RuntimeException e) {
        }

        if (pAction.equals("Search")) {
            shipFroms = distBean.locateShipFrom(fieldSearchType, fieldValue, distId);
        } else {
            shipFroms = distBean.locateShipFrom("all", null, distId);
        }

        session.setAttribute("StoreDist.ship_from.vector", shipFroms);
    }


    /**
     *  Describe <code>addDistributor</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void addDistributor(HttpServletRequest request,
                                      ActionForm form) throws Exception {

        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm = new StoreDistMgrDetailForm();
        session.setAttribute("STORE_DIST_DETAIL_FORM", sForm);
        // Reset the ship from list.
        ArrayList empty = new ArrayList();
        session.setAttribute("StoreDist.ship_from.vector", empty);

    }


    /**
     *  Describe <code>updateDistributor</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors updateDistributor(HttpServletRequest request,
                                                 ActionForm form) throws
            Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        
        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        BigDecimal minOrderAmount = null;
        BigDecimal invoiceAmountPercentAllowanceUpper = null;
        BigDecimal invoiceAmountPercentAllowanceLower = null;
        BigDecimal allowedFreightSurchargeAmount = null;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        int storeId = 0;

        if (sForm != null) {
            // Verify the form information submitted.
            if (sForm.getName().length() == 0) {
                lUpdateErrors.add("name",
                        new ActionError("variable.empty.error",
                                "Name"));
            }
            if (sForm.getStatusCd().length() == 0) {
                lUpdateErrors.add("statusCd",
                        new ActionError("variable.empty.error",
                                "Status"));
            } else {
            	// bug # 4793 : Modified to get total count of items of a distributor from catalog.
            	if(sForm.getStatusCd().trim().equalsIgnoreCase("INACTIVE")) {
	            	int distributorId = sForm.getIntId();
	                APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	                CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
	            	List<Integer> catalogIdList = catInfEjb.getCatalogStructureIds(distributorId);
	            	if(catalogIdList!=null && catalogIdList.size()>0){
	            		lUpdateErrors.add("statusCd",
	                            new ActionError("error.simpleGenericError",
                                            "Distributor cannot be set to INACTIVE " +
                                            "because there are existing items in shopping catalog(s)"
	                            		));
	            	}
            	}
            	 
            }
            if (sForm.getStreetAddr1().length() == 0) {
                lUpdateErrors.add("address1",
                        new ActionError("variable.empty.error",
                                "Street Address 1"));
            }
            if (sForm.getCity().length() == 0) {
                lUpdateErrors.add("city",
                        new ActionError("variable.empty.error",
                                "City"));
            }
            if (sForm.getStateOrProv().length() == 0) {
                lUpdateErrors.add("stateOrProv",
                        new ActionError("variable.empty.error",
                                "State/Province"));
            }
            if (sForm.getCountry().length() == 0) {
                lUpdateErrors.add("country",
                        new ActionError("variable.empty.error",
                                "Country"));
            }
            if (sForm.getPostalCode().length() == 0) {
                lUpdateErrors.add("postalCode",
                        new ActionError("variable.empty.error",
                                "Zip/Postal Code"));
            }
            if (sForm.getPhone().length() == 0) {
                lUpdateErrors.add("phone",
                        new ActionError("variable.empty.error",
                                "Phone"));
            }
            if (!Utility.isSet(sForm.getStoreId())) {
                if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.
                        getUser().getUserTypeCd())) {
                    lUpdateErrors.add("storeId",
                            new ActionError("variable.empty.error", "Store"));
                }
            } else {
                try {
                    storeId = Integer.parseInt(sForm.getStoreId());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("storeId",
                            new ActionError("error.invalidAmount", "storeId"));
                }
            }

            if (Utility.isSet(sForm.getMinimumOrderAmount())) {
                try {
                    minOrderAmount = new BigDecimal(sForm.getMinimumOrderAmount());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("minimumOrderAmount",
                            new ActionError("error.invalidAmount",
                                    "Minimum Order"));
                }
            }

            if (Utility.isSet(sForm.getInvoiceAmountPercentAllowanceUpper())) {
                try {
                    invoiceAmountPercentAllowanceUpper = new BigDecimal(sForm.
                            getInvoiceAmountPercentAllowanceUpper());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("invoiceAmountPercentAllowanceUpper",
                            new ActionError("error.invalidAmount",
                                    "InvoiceAmount Percent Allowance Upper"));
                }
            }

            if (Utility.isSet(sForm.getInvoiceAmountPercentAllowanceLower())) {
                try {
                    invoiceAmountPercentAllowanceLower = new BigDecimal(sForm.
                    		getInvoiceAmountPercentAllowanceLower());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("invoiceAmountPercentAllowanceLower",
                            new ActionError("error.invalidAmount",
                            		"InvoiceAmount Percent Allowance Lower"));
                }
            }
            if (Utility.isSet(sForm.getAllowedFreightSurchargeAmount())) {
                try {
                	allowedFreightSurchargeAmount = new BigDecimal(sForm.
                    		getAllowedFreightSurchargeAmount());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("allowedFreightSurchargeAmount",
                            new ActionError("error.invalidAmount",
                            		"Allowed Freight Surcharge"));
                }
            }
        }

        AddressData billingAddress;
        billingAddress = sForm.getBillingAddress();
        String billingAddress1 = billingAddress.getAddress1();
        if (Utility.isSet(billingAddress1)) {
            if (!Utility.isSet(billingAddress.getCountryCd())) {
                lUpdateErrors.add("billCountry",
                        new ActionError("variable.empty.error",
                                "Billing Country"));
            }
            if (!Utility.isSet(billingAddress.getStateProvinceCd())) {
                lUpdateErrors.add("billState",
                        new ActionError("variable.empty.error",
                                "Billing State/Province"));
            }
        }
        int holdInvoiceDays = 0;
        if (Utility.isSet(sForm.getHoldInvoiceDays())) {
            try {
                holdInvoiceDays = Integer.parseInt(sForm.getHoldInvoiceDays());
            } catch (Exception e) {
                lUpdateErrors.add("holdInvoiceDays",
                        new ActionError("error.invalidAmount",
                                "holdInvoiceDays"));
            }
        }
        if(sForm.getEmailAddress()!=null){
        	sForm.setEmailAddress(sForm.getEmailAddress().trim());
        }
        EmailValidator.validateEmail(request, lUpdateErrors, sForm.getEmailAddress());

        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Distributor distributorBean = factory.getDistributorAPI();

        int distributorid = 0;
        if (!sForm.getId().equals("")) {
            distributorid = Integer.parseInt(sForm.getId());
        }

        // Get the current information for this Distributor.
        DistributorData dd;
        BusEntityData bed;
        PropertyData prop;
        AddressData address;
        EmailData email;
        PhoneData phone;
        PhoneData fax;
        //PhoneData poFax;

        if (distributorid > 0) {
            dd = distributorBean.getDistributor(distributorid);
        } else {
            dd = DistributorData.createValue();
        }

        bed = dd.getBusEntity();
        prop = dd.getDistributorTypeProp();
        email = dd.getPrimaryEmail();
        fax = dd.getPrimaryFax();
        //poFax = dd.getPoFax();
        phone = dd.getPrimaryPhone();
        address = dd.getPrimaryAddress();

        // XXX, values to be determined.
        bed.setWorkflowRoleCd("UNKNOWN");

        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        // Now update with the data from the form.
        bed.setShortDesc(sForm.getName());
        bed.setBusEntityStatusCd(sForm.getStatusCd());
        bed.setErpNum(sForm.getDistNumber());
        String locale = sForm.getDistLocale();
        if(Utility.isSet(locale)){
        	bed.setLocaleCd(locale);
        }else{
        	bed.setLocaleCd("unk");
        }
        bed.setModBy(cuser);

        // optional field
        if (sForm.getEmailAddress().length() != 0) {
            email.setEmailAddress(sForm.getEmailAddress());
            email.setModBy(cuser);
            if (distributorid == 0) {
                email.setAddBy(cuser);
            }
        }

        // optional field
        if (sForm.getFax().length() != 0) {
            fax.setPhoneNum(sForm.getFax());
            fax.setModBy(cuser);
            if (distributorid == 0) {
                fax.setAddBy(cuser);
            }
        }

        // optional field (read only)
        /*
        if (sForm.getPoFax().length() != 0) {
            poFax.setPhoneNum(sForm.getPoFax());
            poFax.setModBy(cuser);
            if (distributorid == 0) {
         poFax.setAddBy(cuser);
            }
        }*/


        if (Utility.isSet(sForm.getSmallOrderHandlingFee())) {
            dd.setSmallOrderHandlingFee(sForm.getSmallOrderHandlingFee());
        }
        if (Utility.isSet(sForm.getWebInfo())) {
            dd.setWebInfo(sForm.getWebInfo());
        }
        if (Utility.isSet(sForm.getAccountNumbers())) {
            dd.setAccountNumbers(sForm.getAccountNumbers());
        }

        if (Utility.isSet(sForm.getExceptionOnOverchargedFreight())) {
            if (sForm.getExceptionOnOverchargedFreight().trim().equals("Y")) {
                dd.setExceptionOnOverchargedFreight(Boolean.TRUE);
            } else {
                dd.setExceptionOnOverchargedFreight(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getExceptionOnTaxDifference())) {
            dd.setExceptionOnTaxDifference(sForm.getExceptionOnTaxDifference());
        }

        if (Utility.isSet(sForm.getIgnoreOrderMinimumForFreight())) {
            if (sForm.getIgnoreOrderMinimumForFreight().trim().equals("Y")) {
                dd.setIgnoreOrderMinimumForFreight(Boolean.TRUE);
            } else {
                dd.setIgnoreOrderMinimumForFreight(Boolean.FALSE);
            }
        }

        dd.setMaxInvoiceFreightAllowed(sForm.getMaxInvoiceFreightAllowed());

        if (Utility.isSet(sForm.getAllowFreightOnBackorders())) {
            if (sForm.getAllowFreightOnBackorders().trim().equals("Y")) {
                dd.setAllowFreightOnBackorders(Boolean.TRUE);
            } else {
                dd.setAllowFreightOnBackorders(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getCancelBackorderedLines())) {
            if (sForm.getCancelBackorderedLines().trim().equals("Y")) {
                dd.setCancelBackorderedLines(Boolean.TRUE);
            } else {
                dd.setCancelBackorderedLines(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getAllowFreightOnFreightHandlerOrders())) {
            if (sForm.getAllowFreightOnFreightHandlerOrders().trim().equals("Y")) {
                dd.setAllowFreightOnFreightHandlerOrders(Boolean.TRUE);
            } else {
                dd.setAllowFreightOnFreightHandlerOrders(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getPrintCustContactOnPurchaseOrder())) {
            if (sForm.getPrintCustContactOnPurchaseOrder().trim().equals("Y")) {
                dd.setPrintCustContactOnPurchaseOrder(Boolean.TRUE);
            } else {
                dd.setPrintCustContactOnPurchaseOrder(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getManualPOAcknowldgementRequiered())) {
            if (sForm.getManualPOAcknowldgementRequiered().trim().equals("Y")) {
                dd.setManualPOAcknowldgementRequiered(Boolean.TRUE);
            } else {
                dd.setManualPOAcknowldgementRequiered(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getDoNotAllowInvoiceEdits())) {
            if (sForm.getDoNotAllowInvoiceEdits().trim().equals("Y")) {
                dd.setDoNotAllowInvoiceEdits(Boolean.TRUE);
            } else if (dd.getDoNotAllowInvoiceEdits() != null) {
                dd.setDoNotAllowInvoiceEdits(Boolean.FALSE);
            }
        }

        if (sForm.getPurchaseOrderComments() != null) {
            dd.setPurchaseOrderComments(sForm.getPurchaseOrderComments());
        }

        dd.setDistributorsCompanyCode(sForm.getDistributorsCompanyCode());
        
        if (minOrderAmount != null) {
            dd.setMinimumOrderAmount(minOrderAmount);
        }
        if (sForm.getCallHours() != null) {
            dd.setCallHours(sForm.getCallHours());
        }
        if (sForm.getRuntimeDisplayName() != null) {
            dd.setRuntimeDisplayName(sForm.getRuntimeDisplayName());
        }
        if (sForm.getHoldInvoiceDays() != null) {
            dd.setHoldInvoiceDays(holdInvoiceDays);
        }

        if (invoiceAmountPercentAllowanceUpper != null) {
            dd.setInvoiceAmountPercentAllowanceUpper(
                    invoiceAmountPercentAllowanceUpper);
        }
        if (invoiceAmountPercentAllowanceLower != null) {
            dd.setInvoiceAmountPercentAllowanceLower(
                    invoiceAmountPercentAllowanceLower);
        }
        if (allowedFreightSurchargeAmount != null) {
            dd.setAllowedFreightSurchargeAmount(
                    allowedFreightSurchargeAmount);
        }

        dd.setInvoiceLoadingPriceModel(sForm.getInvoiceLoadingPriceModel());
        dd.setReceivingSystemInvoiceCd(sForm.getReceivingSystemInvoiceCd());

        phone.setPhoneNum(sForm.getPhone());
        phone.setModBy(cuser);

        address.setName1(sForm.getName1());
        address.setName2(sForm.getName2());
        address.setAddress1(sForm.getStreetAddr1());
        address.setAddress2(sForm.getStreetAddr2());
        address.setAddress3(sForm.getStreetAddr3());
        address.setCity(sForm.getCity());
        address.setStateProvinceCd(sForm.getStateOrProv());
        address.setPostalCode(sForm.getPostalCode());
        address.setCountryCd(sForm.getCountry());
        address.setModBy(cuser);

        // optional field
        if (sForm.getTypeDesc().length() != 0) {
            prop.setValue(sForm.getTypeDesc());
            prop.setModBy(cuser);
            if (distributorid == 0) {
                prop.setAddBy(cuser);
            }
        }
        dd.setCustomerReferenceCode(sForm.getCustomerReferenceCode());
        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().
                getUserTypeCd())) {
            dd.setStoreId(appUser.getUserStore().getStoreId());
            resetStoreDistributorCache(session, dd.getBusEntity().getBusEntityId(),
                    appUser.getUserStore().getStoreId());
        }else{
            dd.setStoreId(storeId);
            resetStoreDistributorCache(session, dd.getBusEntity().getBusEntityId(),
                    storeId);
        }

        if (billingAddress.getBusEntityId() == 0) {
            billingAddress.setAddBy(cuser);
        }
        billingAddress.setModBy(cuser);
        dd.setBillingAddress(billingAddress);

        if (Utility.isSet(sForm.getExchangeCompanyCode())) {
        	dd.setExchangeCompanyCode(sForm.getExchangeCompanyCode());
        }
        if (Utility.isSet(sForm.getExchangeInventoryURL())) {
        	dd.setExchangeInventoryURL(sForm.getExchangeInventoryURL());
        }
        if (Utility.isSet(sForm.getExchangeUser())) {
        	dd.setExchangeUser(sForm.getExchangeUser());
        }
        if (Utility.isSet(sForm.getExchangePassword())) {
        	dd.setExchangePassword(sForm.getExchangePassword());
        }

        if (distributorid == 0) {
            bed.setAddBy(cuser);
            phone.setAddBy(cuser);
            address.setAddBy(cuser);
            dd = distributorBean.addDistributor(dd);

            // set Distributor Number to #id
            dd.getBusEntity().setErpNum("#" + dd.getBusEntity().getBusEntityId());
            dd = distributorBean.updateDistributor(dd);
            sForm.setDistNumber(dd.getBusEntity().getErpNum());
            sForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));
        } else {
            // Now update this Distributor
            dd = distributorBean.updateDistributor(dd);
            distributorid = dd.getBusEntity().getBusEntityId();
        }

        billingAddress = dd.getBillingAddress();
        if (billingAddress == null) billingAddress = AddressData.createValue();
        sForm.setBillingAddress(billingAddress);

        session.setAttribute("STORE_DIST_DETAIL_FORM", sForm);

        return lUpdateErrors;
    }

  /*
    public static ActionErrors updateDistributorConfiguration(HttpServletRequest
            request,
                                                              ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        BigDecimal invoiceAmountPercentAllowanceUpper = null;
        BigDecimal invoiceAmountPercentAllowanceLower = null;
        BigDecimal allowedFreightSurchargeAmount = null;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        int storeId = 0;

        if (sForm != null) {
            if (Utility.isSet(sForm.getInvoiceAmountPercentAllowanceUpper())) {
                try {
                    invoiceAmountPercentAllowanceUpper = new BigDecimal(sForm.
                            getInvoiceAmountPercentAllowanceUpper());
                } catch (NumberFormatException e) {
                    lUpdateErrors.add("invoiceLoadingPriceExceptionThreshold",
                            new ActionError("error.invalidAmount",
                                    "Invoice Loading Price Exception Threshold"));
                }
            }
        }
        if (Utility.isSet(sForm.getInvoiceAmountPercentAllowanceLower())) {
            try {
                invoiceAmountPercentAllowanceLower = new BigDecimal(sForm.
                		getInvoiceAmountPercentAllowanceLower());
            } catch (NumberFormatException e) {
                lUpdateErrors.add("invoiceAmountPercentAllowanceLower",
                        new ActionError("error.invalidAmount",
                        		"InvoiceAmount Percent Allowance Lower"));
            }
        }
        if (Utility.isSet(sForm.getAllowedFreightSurchargeAmount())) {
            try {
            	allowedFreightSurchargeAmount = new BigDecimal(sForm.
                		getAllowedFreightSurchargeAmount());
            } catch (NumberFormatException e) {
                lUpdateErrors.add("allowedFreightSurchargeAmount",
                        new ActionError("error.invalidAmount",
                        		"Allowed Freight Surcharge Amount"));
            }
        }

        int holdInvoiceDays = 0;
        if (Utility.isSet(sForm.getHoldInvoiceDays())) {
            try {
                holdInvoiceDays = Integer.parseInt(sForm.getHoldInvoiceDays());
            } catch (Exception e) {
                lUpdateErrors.add("holdInvoiceDays",
                        new ActionError("error.invalidAmount",
                                "holdInvoiceDays"));
            }
        }

        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Distributor distributorBean = factory.getDistributorAPI();

        int distributorid = 0;
        if (!sForm.getId().equals("")) {
            distributorid = Integer.parseInt(sForm.getId());
        }
        // Get the current information for this Distributor.
        DistributorData dd = distributorBean.getDistributor(distributorid);

        if (Utility.isSet(sForm.getExceptionOnOverchargedFreight())) {
            if (sForm.getExceptionOnOverchargedFreight().trim().equals("Y")) {
                dd.setExceptionOnOverchargedFreight(Boolean.TRUE);
            } else {
                dd.setExceptionOnOverchargedFreight(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getExceptionOnTaxDifference())) {
               dd.setExceptionOnTaxDifference(sForm.getExceptionOnTaxDifference());
        }

        if (Utility.isSet(sForm.getIgnoreOrderMinimumForFreight())) {
            if (sForm.getIgnoreOrderMinimumForFreight().trim().equals("Y")) {
                dd.setIgnoreOrderMinimumForFreight(Boolean.TRUE);
            } else {
                dd.setIgnoreOrderMinimumForFreight(Boolean.FALSE);
            }
        }

        dd.setMaxInvoiceFreightAllowed(sForm.getMaxInvoiceFreightAllowed());

        if (Utility.isSet(sForm.getAllowFreightOnBackorders())) {
            if (sForm.getAllowFreightOnBackorders().trim().equals("Y")) {
                dd.setAllowFreightOnBackorders(Boolean.TRUE);
            } else {
                dd.setAllowFreightOnBackorders(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getCancelBackorderedLines())) {
            if (sForm.getCancelBackorderedLines().trim().equals("Y")) {
                dd.setCancelBackorderedLines(Boolean.TRUE);
            } else {
                dd.setCancelBackorderedLines(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getAllowFreightOnFreightHandlerOrders())) {
            if (sForm.getAllowFreightOnFreightHandlerOrders().trim().equals("Y")) {
                dd.setAllowFreightOnFreightHandlerOrders(Boolean.TRUE);
            } else {
                dd.setAllowFreightOnFreightHandlerOrders(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getPrintCustContactOnPurchaseOrder())) {
            if (sForm.getPrintCustContactOnPurchaseOrder().trim().equals("Y")) {
                dd.setPrintCustContactOnPurchaseOrder(Boolean.TRUE);
            } else {
                dd.setPrintCustContactOnPurchaseOrder(Boolean.FALSE);
            }
        }

        if (Utility.isSet(sForm.getManualPOAcknowldgementRequiered())) {
            if (sForm.getManualPOAcknowldgementRequiered().trim().equals("Y")) {
                dd.setManualPOAcknowldgementRequiered(Boolean.TRUE);
            } else {
                dd.setManualPOAcknowldgementRequiered(Boolean.FALSE);
            }
        }

        if (sForm.getPurchaseOrderComments() != null) {
            dd.setPurchaseOrderComments(sForm.getPurchaseOrderComments());
        }

        if (sForm.getHoldInvoiceDays() != null) {
            dd.setHoldInvoiceDays(holdInvoiceDays);
        }

        if (invoiceAmountPercentAllowanceUpper != null) {
            dd.setInvoiceAmountPercentAllowanceUpper(
                    invoiceAmountPercentAllowanceUpper);
        }
        if (invoiceAmountPercentAllowanceLower != null) {
            dd.setInvoiceAmountPercentAllowanceLower(
                    invoiceAmountPercentAllowanceLower);
        }
        if (allowedFreightSurchargeAmount != null) {
            dd.setAllowedFreightSurchargeAmount(
                    allowedFreightSurchargeAmount);
        }

        dd.setInvoiceLoadingPriceModel(sForm.getInvoiceLoadingPriceModel());
        dd.setReceivingSystemInvoiceCd(sForm.getReceivingSystemInvoiceCd());

        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().
                getUserTypeCd())) {
            dd.setStoreId(appUser.getUserStore().getStoreId());
            resetStoreDistributorCache(session, dd.getBusEntity().getBusEntityId(),
                    appUser.getUserStore().getStoreId());
        } else {
            dd.setStoreId(storeId);
            resetStoreDistributorCache(session, dd.getBusEntity().getBusEntityId(),
                    storeId);
        }

        dd = distributorBean.updateDistributor(dd);
        distributorid = dd.getBusEntity().getBusEntityId();
        session.setAttribute("STORE_DIST_DETAIL_FORM", sForm);

        return lUpdateErrors;
    }
*/

    /**
     *  The <code>delete</code> method removes the database entries defining
     *  this distributor if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Distributor
     */
    public static ActionErrors delete(HttpServletRequest request,
                                      ActionForm form) throws Exception {

        ActionErrors deleteErrors = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        String strid = request.getParameter("id");
        if (strid == null || strid.length() == 0) {
            deleteErrors.add("id", new ActionError("error.badRequest", "id"));
            return deleteErrors;
        }

        Integer id = new Integer(strid);
        DistributorData dd = distBean.getDistributor(id.intValue());
        if (null != dd) {
            try {
                distBean.removeDistributor(dd);
            } catch (Exception e) {
                deleteErrors.add("id",
                        new ActionError("error.deleteFailed",
                                "Distributor"));
                return deleteErrors;
            }
            session.removeAttribute("StoreDist.found.vector");
        }
        return deleteErrors;
    }

    public static ActionErrors deleteShipFromAddress
            (HttpServletRequest request,
             ActionForm form) throws Exception {

        ActionErrors deleteErrors = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        int sid = sForm.getShipFrom().getAddressId();

        try {
            distBean.removeShipFrom(sid);
            // Refresh the list of ship to addresses.
            getShipFromForDist(request, sForm.getIntId());
        } catch (Exception e) {
            deleteErrors.add
                    ("id", new ActionError
                            ("error.deleteFailed",
                                    "Distributor Ship From Address"));
        }
        return deleteErrors;
    }

    public static ActionErrors updateShipFromAddress
            (HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        AddressData addr = AddressData.createValue();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        addr.setModBy(cuser);
        addr.setAddressId(sForm.getShipFrom().getAddressId());
        addr.setName1(sForm.getShipFrom().getName1());
        addr.setCity(sForm.getShipFrom().getCity());
        addr.setAddress1(sForm.getShipFrom().getAddress1());
        addr.setAddress2(sForm.getShipFrom().getAddress2());
        addr.setAddress3(sForm.getShipFrom().getAddress3());
        addr.setStateProvinceCd(sForm.getShipFrom().getStateProvinceCd());
        addr.setCountryCd(sForm.getShipFrom().getCountryCd());
        addr.setPostalCode(sForm.getShipFrom().getPostalCode());
        addr.setAddressStatusCd(sForm.getShipFrom().getAddressStatusCd());

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        if (distBean.updateShipFromAddress(addr)) {
            // Refresh the list of ship to addresses.
            getShipFromForDist(request, sForm.getIntId());
        } else {
            ae.add("distributor",
                    new ActionError
                            ("The ship from address could not be updated."));
        }
        return ae;
    }

    public static ActionErrors sortShipFrom
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String sortField = request.getParameter("sortField");
        DistShipFromAddressViewVector shipFromLocations =
                (DistShipFromAddressViewVector)
                        session.getAttribute("StoreDist.ship_from.vector");
        if (null == shipFromLocations) {
            ae.add("distributor",
                    new ActionError
                            ("No shipping location to sort to sort."));

        }
        shipFromLocations.sort(sortField);
        return ae;
    }


    // <- add oxy
    public static ActionErrors getFreightHandlers(HttpServletRequest request,
                                                  StoreFhMgrSearchForm form, boolean b) {
        HttpSession session = request.getSession();
        session.removeAttribute("freight_handler.vector");
        request.removeAttribute("freight_handler.vector");
        ActionErrors ae = new ActionErrors();

        StoreFhMgrSearchForm sForm =  form;
        FreightHandlerViewVector fhv = null;
        try {
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();

            if (!Utility.isSet(sForm.getSearchType())) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("variable.empty.error", "Search Type"));
            }
            if (!Utility.isSet(sForm.getSearchField())) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("variable.empty.error", "Search Field"));
            }

            if (ae.size() > 0) {
                getAllFreightHandlers(request,sForm);
                return ae;
            }
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                    APP_USER);
            if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.
                    getUser().getUserTypeCd())) {
                crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            }
            if (sForm.getSearchType().equals("id")) {
                try {
                    int lId = Integer.parseInt(sForm.getSearchField());
                    crit.setSearchId(sForm.getSearchField());
                } catch (NumberFormatException e) {
                    ae.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("error.invalidNumber", "Search Field"));
                    return ae;
                }
            } else if (sForm.getSearchType().equals("nameBegins")) {
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                crit.setSearchName(sForm.getSearchField());
            } else if (sForm.getSearchType().equals("nameContains")) {
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                crit.setSearchName(sForm.getSearchField());
            } else { getAllFreightHandlers(request,sForm); return ae;}

            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
            fhv = distBean.getFreightHandlersByCriteria(crit);

            if (null == fhv) {
                fhv = new FreightHandlerViewVector();
            }
            session.setAttribute("freight_handler.vector", fhv);
        } catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.simpleGenericError", e.getMessage()));
            e.printStackTrace();
        }
        return ae;
    }



    // ->

    public static ActionErrors getFreightHandlers(HttpServletRequest request,
                                                  ActionForm form) {
        HttpSession session = request.getSession();
        session.removeAttribute("freight_handler.vector");
        request.removeAttribute("freight_handler.vector");
        ActionErrors ae = new ActionErrors();
        FhMgrSearchForm sForm = (FhMgrSearchForm) form;
        FreightHandlerViewVector fhv = null;
        try {
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();

            if (!Utility.isSet(sForm.getSearchType())) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("variable.empty.error", "Search Type"));
            }
            if (!Utility.isSet(sForm.getSearchField())) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("variable.empty.error", "Search Field"));
            }
            if (ae.size() > 0) {
                return ae;
            }

            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                    APP_USER);
            if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.
                    getUser().getUserTypeCd())) {
                crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            }
            if (sForm.getSearchType().equals("id")) {
                try {
                    int lId = Integer.parseInt(sForm.getSearchField());
                    crit.setSearchId(sForm.getSearchField());
                } catch (NumberFormatException e) {
                    ae.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("error.invalidNumber", "Search Field"));
                    return ae;
                }
            } else if (sForm.getSearchType().equals("nameBegins")) {
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                crit.setSearchName(sForm.getSearchField());
            } else if (sForm.getSearchType().equals("nameContains")) {
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                crit.setSearchName(sForm.getSearchField());
            }

            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
            fhv = distBean.getFreightHandlersByCriteria(crit);

            if (null == fhv) {
                fhv = new FreightHandlerViewVector();
            }
            session.setAttribute("freight_handler.vector", fhv);
        } catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.simpleGenericError", e.getMessage()));
            e.printStackTrace();
        }
        return ae;
    }

    public static ActionErrors getAllFreightHandlers
            (HttpServletRequest request,
             ActionForm form) {

        HttpSession session = request.getSession();
        session.removeAttribute("freight_handler.vector");
        request.removeAttribute("freight_handler.vector");
        ActionErrors ae = new ActionErrors();
        try {
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                    APP_USER);
            if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.
                    getUser().getUserTypeCd())) {
                crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            }
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
            FreightHandlerViewVector fhv = distBean.getFreightHandlersByCriteria(crit);

            if (null == fhv) {
                fhv = new FreightHandlerViewVector();
            }
            session.setAttribute("freight_handler.vector", fhv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ae;
    }

    public static ActionErrors createNewFreightHandler
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        try {
            initFormVectors(request);
            FhMgrDetailForm sForm = new FhMgrDetailForm();
            sForm.setId("0");
            sForm.setName("");
            session.setAttribute("FH_DETAIL_FORM",
                    sForm);

        } catch (Exception e) {
            ae.add("distributor",
                    new ActionError
                            ("error.simpleGenericError", "error:" + e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors saveFreightHandler
            (HttpServletRequest request,
             ActionForm form) {
        FhMgrDetailForm sForm = (FhMgrDetailForm) form;
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
            initFormVectors(request);
            FreightHandlerViewVector fhv = (FreightHandlerViewVector) session.
                    getAttribute("freight_handler.vector");
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();
            int storeId = 0;

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                    APP_USER);
            if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.
                    getUser().getUserTypeCd())) {
                storeId = appUser.getUserStore().getStoreId();
            } else {
                try {
                    storeId = Integer.parseInt(sForm.getStoreId());
                } catch (NumberFormatException e) {
                    ae.add("storeId", new ActionError("error.invalidAmount", "storeId"));
                }
            }

            if (ae.size() > 0) {
                return ae;
            }

            int fhid = Integer.parseInt(sForm.getId());
            if (fhid == 0) {
                FreightHandlerView fh = FreightHandlerView.createValue();
                fhv.add(fh);
                fh.setBusEntityData(BusEntityData.createValue());
                fh.getBusEntityData().setAddBy(appUser.getUserName());
                fh.getBusEntityData().setBusEntityTypeCd(RefCodeNames.
                        BUS_ENTITY_TYPE_CD.
                        FREIGHT_HANDLER);
                fh.getBusEntityData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.
                        UNKNOWN);
                fh.getBusEntityData().setLocaleCd(appUser.getUserStore().getBusEntity().
                        getLocaleCd());
            }
            Iterator itr = fhv.iterator();
            while (itr.hasNext()) {
                FreightHandlerView fh = (FreightHandlerView) itr.next();
                if (fh.getBusEntityData().getBusEntityId() == fhid) {
                    fh.getBusEntityData().setShortDesc(sForm.getName());
                    fh.getBusEntityData().setBusEntityStatusCd(sForm.getStatusCd());
                    if (fh.getPrimaryAddress() == null) {
                        fh.setPrimaryAddress(AddressData.createValue());
                    }
                    fh.getPrimaryAddress().setAddressStatusCd(RefCodeNames.
                            ADDRESS_STATUS_CD.ACTIVE);
                    fh.getPrimaryAddress().setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.
                            PRIMARY_CONTACT);
                    fh.getPrimaryAddress().setAddress1(sForm.getStreetAddr1());
                    fh.getPrimaryAddress().setAddress2(sForm.getStreetAddr2());
                    fh.getPrimaryAddress().setAddress3(sForm.getStreetAddr3());
                    fh.getPrimaryAddress().setAddress4(sForm.getStreetAddr4());
                    fh.getPrimaryAddress().setCity(sForm.getCity());
                    fh.getPrimaryAddress().setPostalCode(sForm.getPostalCode());
                    fh.getPrimaryAddress().setCountryCd(sForm.getCountry());
                    fh.getPrimaryAddress().setStateProvinceCd(sForm.getStateOrProv());
                    fh.setEdiRoutingCd(sForm.getEdiRoutingCd());
                    fh.setAcceptFreightOnInvoice
                            (sForm.getAcceptFreightOnInvoice());
                    if (fh.getStoreId() == 0) {
                        fh.setStoreId(storeId);
                    }
                    fh = distBean.saveFreightHandler(fh);
                    sForm.setId(fh.getBusEntityData().getBusEntityId());
                    fetchFreightHandlerDetail(request, form);
                    //remove old and add new one to make sure our lists in sync
                    itr.remove();
                    fhv.add(fhv);
                    return ae;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("distributor",
                    new ActionError
                            ("error.simpleGenericError", "error:" + e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors fetchFreightHandlerDetail
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
            initFormVectors(request);
            String rid = request.getParameter("searchField");
            if (rid == null) {
                ae.add("distributor",
                        new ActionError
                                ("error.simpleGenericError", "error: id missing"));
                return ae;
            }
            int fhid = Integer.parseInt(rid);
            FreightHandlerViewVector fhv = (FreightHandlerViewVector)
                    session.getAttribute(
                            "freight_handler.vector");
            if (null == fhv) {
                ae.add("distributor",
                        new ActionError
                                ("error.simpleGenericError", "error: no freight handlers"));
                return ae;
            }
            Iterator itr = fhv.iterator();
            while (itr.hasNext()) {
                FreightHandlerView fh = (FreightHandlerView) itr.next();
                if (fh.getBusEntityData().getBusEntityId() == fhid) {
                    FhMgrDetailForm sForm = (FhMgrDetailForm) form;
                    sForm.setId(rid);
                    sForm.setName(fh.getBusEntityData().getShortDesc());
                    sForm.setStatusCd(fh.getBusEntityData().getBusEntityStatusCd());
                    sForm.setStreetAddr1(fh.getPrimaryAddress().getAddress1());
                    sForm.setStreetAddr2(fh.getPrimaryAddress().getAddress2());
                    sForm.setStreetAddr3(fh.getPrimaryAddress().getAddress3());
                    sForm.setStreetAddr4(fh.getPrimaryAddress().getAddress4());
                    sForm.setCity(fh.getPrimaryAddress().getCity());
                    sForm.setPostalCode(fh.getPrimaryAddress().getPostalCode());
                    sForm.setCountry(fh.getPrimaryAddress().getCountryCd());
                    sForm.setStateOrProv(fh.getPrimaryAddress().getStateProvinceCd());
                    sForm.setEdiRoutingCd(fh.getEdiRoutingCd());
                    sForm.setEdiRoutingCd(fh.getEdiRoutingCd());
                    sForm.setAcceptFreightOnInvoice
                            (fh.getAcceptFreightOnInvoice());

                    sForm.setStoreId(Integer.toString(fh.getStoreId()));
                    return ae;
                }
            }

        } catch (Exception e) {
            ae.add("distributor",
                    new ActionError
                            ("error.simpleGenericError", "error:" + e.getMessage()));
        }
        return ae;
    }

    public static ActionErrors prepareAddContact
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        sForm.setWrkContact(ContactView.createValue());
        sForm.setWrkBranchContact(ContactView.createValue());
        return ae;
    }

    private static ContactView findContactInCollections
            (int contactId, ContactViewVector cVwV, ArrayList branches) {

        for (int ii = 0; ii < cVwV.size(); ii++) {
            ContactView cVw = (ContactView) cVwV.get(ii);
            if (contactId == cVw.getContactId()) {
                return cVw;
            }
        }
        for (int i = 0; null != branches
                && i < branches.size(); i++) {
            DistBranchData dbd = (DistBranchData) branches.get(i);
            ContactViewVector cvv = dbd.getContactsCollection();
            for (int j = 0; null != cvv
                    && j < cvv.size(); j++) {
                ContactView cVw = (ContactView) cvv.get(j);
                if (contactId == cVw.getContactId()) {
                    return cVw;
                }
            }
        }

        return null;
    }

    public static ActionErrors prepareEditContact
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        String contactIdS = request.getParameter("contactId"),
                contactType = request.getParameter("contactType");
        if (null == contactType) {
            contactType = "";
        }
        int contactId = 0;

        try {
            contactId = Integer.parseInt(contactIdS);
        } catch (Exception exc) {
            String errorMess = "Wrong contact id format. Id: " + contactIdS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        boolean foundFl = false;
        ContactViewVector cVwV = sForm.getAdditionalContacts();
        ArrayList branches = sForm.getBranchesCollection();
        ContactView cVw = findContactInCollections(contactId, cVwV, branches);
        if (null == cVw) {
            foundFl = false;
            sForm.setWrkContact(ContactView.createValue());
            sForm.setWrkBranchContact(ContactView.createValue());
        } else {
            foundFl = true;
            sForm.setWrkContact(cVw);
            sForm.setWrkBranchContact(cVw);
        }

        if (!foundFl) {
            String errorMess = "Contact not found. Contact id: " + contactId;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        return ae;
    }

    public static ActionErrors saveAdditionalContact
            (HttpServletRequest request,
             ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        String contype = sForm.getWrkContactType();
        ContactView cVw = sForm.getWrkContact();
        if ("branch".equals(contype)) {
            cVw = sForm.getWrkBranchContact();
        }
        EmailValidator.validateEmail(request, ae, "eMail", cVw.getEmail());
        int contactId = cVw.getContactId();
        String lastName = cVw.getLastName();
        if (!Utility.isSet(lastName)) {
            String errorMess = "Last name should not be empty";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
        if (ae.size() > 0) {
            return ae;
        }
        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        cVw = distBean.updateDistributorContact(cVw, cuser);
        if ("branch".equals(contype)) {
            ArrayList branches =
                    distBean.getBranchesCollection(sForm.getIntId());
            sForm.setBranchesCollection(branches);
        } else {
            sForm.setAdditionalContacts
                    (distBean.getDistributorContacts(cVw.getBusEntityId()));
        }
        return ae;
    }

    public static ActionErrors deleteAdditionalContact
            (HttpServletRequest request,
             ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        String contactIdS = request.getParameter("contactId");
        int contactId = 0;
        try {
            contactId = Integer.parseInt(contactIdS);
        } catch (Exception exc) {
            String errorMess = "Wrong contact id format. Id: " + contactIdS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        boolean foundFl = false;

        ContactViewVector cVwV = sForm.getAdditionalContacts();
        ArrayList branches = sForm.getBranchesCollection();
        ContactView cVw = findContactInCollections(contactId, cVwV, branches);
        if (null == cVw) {
            foundFl = false;
            sForm.setWrkContact(ContactView.createValue());
            sForm.setWrkBranchContact(ContactView.createValue());
        } else {
            foundFl = true;
            sForm.setWrkContact(cVw);
            sForm.setWrkBranchContact(cVw);
        }

        if (!foundFl) {
            String errorMess = "Contact not found. Contact id: " + contactId;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        distBean.deleteDistributorContact(contactId);
        // Reload the contact lists.
        sForm.setAdditionalContacts(distBean.getDistributorContacts
                (sForm.getIntId()));
        sForm.setBranchesCollection(distBean.getBranchesCollection
                (sForm.getIntId()));

        return ae;
    }

    public static ActionErrors addPriamryManufacturer
            (HttpServletRequest request,
             ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        int distId = sForm.getIntId();
        ManufacturerDataVector mfv = sForm.getManufFilter();
        if (mfv != null && mfv.size() > 0) {
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();
            Iterator i = mfv.iterator();
            while (i.hasNext()) {
                ManufacturerData mD = (ManufacturerData) i.next();
                distBean.addPrimaryManufacturer(distId,
                        mD.getBusEntity().getBusEntityId(),
                        cuser);
            }
            BusEntityDataVector manufacturers = distBean.
                    getPrimaryManufacturers(distId);
            sForm.setPrimaryManufacturers(manufacturers);
        }
        return ae;
    }

    public static ActionErrors deletePriamryManufacturer
            (HttpServletRequest request,
             ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        int distId = sForm.getIntId();
        int mfgId = sForm.getManufacturerId();

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        distBean.deletePrimaryManufacturer(distId, mfgId);
        BusEntityDataVector manufacturers = distBean.getPrimaryManufacturers(distId);
        sForm.setPrimaryManufacturers(manufacturers);
        sForm.setManufacturerId(0);
        return ae;
    }

    public static ActionErrors prepareAddBranchAddress
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        sForm.setWrkAddress(AddressData.createValue());
        return ae;
    }

    public static ActionErrors prepareEditBranchAddress
            (HttpServletRequest request,
             ActionForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        String branchAddrIdS = request.getParameter("branchAddrId");
        int addrId = 0;
        try {
            addrId = Integer.parseInt(branchAddrIdS);
        } catch (Exception exc) {
            String errorMess = "Wrong branch address id format. Id: " + branchAddrIdS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        boolean foundFl = false;

        ArrayList branchDV = sForm.getBranchesCollection();
        for (int ii = 0; ii < branchDV.size(); ii++) {
            AddressData addrD = ((DistBranchData)
                    branchDV.get(ii)).getBranchAddress();
            if (addrId == addrD.getAddressId()) {
                foundFl = true;
                sForm.setWrkAddress(addrD);
                break;
            }
        }
        if (!foundFl) {
            String errorMess = "Branch address not found. Address id: " + addrId;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        return ae;
    }

    public static ActionErrors saveBranchAddress
            (HttpServletRequest request,
             ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        AddressData addrD = sForm.getWrkAddress();
        int addrId = addrD.getAddressId();
        String state = addrD.getStateProvinceCd();
        if (!Utility.isSet(state)) {
            String errorMess = "State/Province should not be empty";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        addrD.setBusEntityId(sForm.getIntId());
        addrD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
        addrD.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.BRANCH);
        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        addrD = distBean.saveAddress(addrD, cuser);
        ArrayList branches =
                distBean.getBranchesCollection(sForm.getIntId());
        sForm.setBranchesCollection(branches);

        return ae;
    }

    public static ActionErrors deleteBranchAddress
            (HttpServletRequest request,
             ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
        String addrIdS = request.getParameter("branchAddrId");
        int addrId = 0;
        try {
            addrId = Integer.parseInt(addrIdS);
        } catch (Exception exc) {
            String errorMess = "Wrong branch address id format. Id: " + addrIdS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        distBean.deleteAddress(addrId);
        sForm.setBranchesCollection(distBean.getBranchesCollection
                (sForm.getIntId()));

        return ae;
    }


    public static ActionErrors clearSelectBox(HttpServletRequest request,
                                              ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
        pForm.setSelectedLines(new String[0]);
        return ae;
    }

    public static ActionErrors resetSelectBox(HttpServletRequest request,
                                              ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreDistMgrDetailForm pForm = (StoreDistMgrDetailForm) form;
        pForm.setSelectedLines(new String[0]);
        return ae;
    }

    public static ActionErrors returnSelectedGroups(HttpServletRequest request, 
        ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        if (form instanceof StoreDistMgrSearchForm) {
            StoreDistMgrSearchForm storeDistMgrSearchForm = (StoreDistMgrSearchForm)form;
            LocateStoreGroupForm locateForm = storeDistMgrSearchForm.getLocateStoreGroupForm();
            if (locateForm != null) {
                GroupDataVector returnData = locateForm.getGroupsToReturn();
                if (returnData != null && returnData.size() > 0) {
                    GroupData groupData = (GroupData)returnData.get(0);
                    storeDistMgrSearchForm.setSearchGroupId(String.valueOf(groupData.getGroupId()));
                }
            }
        }
        return ae;
    }

}
