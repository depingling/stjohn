package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.DomainAdmConfigurationForm;
import com.cleanwise.view.forms.DomainAdmDetailForm;
import com.cleanwise.view.forms.SiteShoppingControlForm;
import com.cleanwise.view.forms.BusEntitySearchForm;
import com.cleanwise.view.forms.InventoryForm;
import com.cleanwise.view.forms.BSCDetailForm;

import com.cleanwise.view.forms.SiteBudget;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.ClwCustomizer;


public class StoreDomainMgrLogic {

    private static void cleanDetailFields
        (HttpServletRequest request, DomainAdmConfigurationForm pForm)
        throws Exception {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getStoreId();
        init(request, pForm);
        session.removeAttribute("Site.account.blanketPos");
        pForm.setBlanketPoNumId(new Integer(0));

        pForm.setName("");
        pForm.setStatusCd("");
        pForm.setId(0);

        pForm.setTypeCd("");
        pForm.setSiteNumber("");
        pForm.setOldSiteNumber("");
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        pForm.setEffDate("");
        pForm.setDomainData(null);
        pForm.setExpDate("");
        pForm.setSubContractor("");
        pForm.setTargetFacilityRank("");
        pForm.setTaxableIndicator("N");
        pForm.setInventoryShoppingStr("");
        pForm.setInventoryShoppingTypeStr("");
        pForm.setInventoryShoppingHoldOrderUntilDeliveryDate(false);
        pForm.setAccountId("0");
        pForm.setAccountName("");
        session.removeAttribute("Site.account.fields");
        session.removeAttribute("Site.account.id");
        session.removeAttribute("Account.id");
        pForm.setAccountName("");
        pForm.setStoreId(String.valueOf(storeId));
        session.setAttribute("Site.store.id", pForm.getStoreId());
        pForm.setStoreName(storeD.getBusEntity().getShortDesc());
        session.setAttribute("Site.store.name", pForm.getStoreName());

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


        session.removeAttribute("Site.id");
        pForm.setERPEnabled(false);
        pForm.setAvailableShipto(new IdVector());

        pForm.setShareBuyerOrderGuides(false);
        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initFormVectors(HttpServletRequest request)
        throws Exception {

        HttpSession session = request.getSession();

        // Cache the lists needed for Sites.
        APIAccess factory = new APIAccess();

        ListService lsvc = factory.getListServiceAPI();

        // Set up the domain status list.
        if (null == session.getAttribute("Site.status.vector")) {
            RefCdDataVector statusv =
                lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
            session.setAttribute("Site.status.vector", statusv);
        }

        if (null == session.getAttribute("country.vector")) {
          Country countryBean = factory.getCountryAPI();
          CountryDataVector countriesv = countryBean.getAllCountries();
          session.setAttribute("country.vector", countriesv);
        }
    }


    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
                            ActionForm form)
        throws Exception {
        initFormVectors(request);

        DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm)form;

        pForm.setSelectIds(new String[0]);
        pForm.setSelectDefaultStoreIds(new String[0]);
        pForm.setDisplayIds(new String[0]);
        pForm.setDisplayDefaultStoreIds(new String[0]);
        pForm.setDefaultStoreId("");
        initConfig(request, form);

        return;
    }


    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
          ActionForm form)
  throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        SiteViewVector domains =
            (SiteViewVector)session.getAttribute("Site.found.vector");
        if (domains == null) {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(domains, sortField);
    }

    /**
     *  Describe <code>addSite</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void addSite(HttpServletRequest request,
                               ActionForm form)
        throws Exception {

        HttpSession session = request.getSession();
        DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm) form;
        cleanDetailFields(request, pForm);
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        StoreData store = appUser.getUserStore();
        pForm.setAvailableShipto(new IdVector());


        // These can only be set after the account has been chosen.
        session.removeAttribute("Site.account.fields");
        setDefaultCountry(pForm);
    }


    public static void setDefaultCountry(DomainAdmConfigurationForm pForm) throws Exception {
      AccountDataVector accountDV = pForm.getAccountFilter();
      if(accountDV!=null && accountDV.size()>0) {
        AccountData accountD = (AccountData) accountDV.get(0);
        pForm.setCountry(accountD.getPrimaryAddress().getCountryCd());
      }
    }


    /**
     *  Describe <code>cloneSite</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
//    public static void cloneSite(HttpServletRequest request,
//                               ActionForm form)
//        throws Exception {
//
//        DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm) form;
//
//        String name = pForm.getSiteData().getBusEntity().getShortDesc();
//        if (name == null) name = "";
//        name = "Clone of >>> " + name;
//
//        pForm.setName(name);
//        pForm.setId(0);
//
//        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
//        StoreData store = appUser.getUserStore();
//        if(RefCodeNames.ERP_SYSTEM_CD.LAWSON.equals(store.getErpSystemCode())){
//          pForm.setERPEnabled(true);
//          pForm.setLawsonExistsFlag(false);
//        }
//    }


    /**
     *  Describe <code>updateSite</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
//    public static ActionErrors updateSite(HttpServletRequest request,
//                                          ActionForm form)
//        throws Exception {
//        ActionErrors lUpdateErrors = new ActionErrors();
//
//        HttpSession session = request.getSession();
//        DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm) form;
//        Date effDate = null;
//        Date expDate = null;
//        BlanketPoNumData blanketPo = null;
//        Integer targetFacilityRank = null;
//        int accountId = 0;
//        try{
//            if(Utility.isSet(pForm.getAccountId())){
//              accountId = Integer.parseInt(pForm.getAccountId());
//            }
//        }catch(NumberFormatException e){//exception handled later in validation
//        }
//        if (pForm != null) {
//          String oldSiteNumber = pForm.getOldSiteNumber();
//          if(oldSiteNumber!=null && oldSiteNumber.trim().length()>0 ) {
//          String domainNumber = pForm.getSiteNumber();
//          StoreData store = null;
//          if(accountId > 0){
//              store = AccountMgrLogic.getStoreForManagedAccount(session, accountId);
//          }
//          if(store != null && RefCodeNames.ERP_SYSTEM_CD.LAWSON.equals(store.getErpSystemCode())){
//            if(!oldSiteNumber.equals(domainNumber)) {
//              //Get lawson data
//              try {
//                getLawsonShipto (request,pForm,false);
//              }catch(Exception exc) {
//                exc.printStackTrace();
//                lUpdateErrors.add("error",
//                     new ActionError("error.simpleGenericError",exc.getMessage()));
//                return lUpdateErrors;
//              }
//              if(pForm.getLawsonErrorFlag()) {
//                lUpdateErrors.add("error",
//                       new ActionError("error.simpleGenericError",
//                       "Error happened when Lawson data requested"));
//                return lUpdateErrors;
//              }
//              if(pForm.getLawsonExistsFlag()) {
//                lUpdateErrors.add("error",
//                        new ActionError("error.simpleGenericError",
//                        "Site number couldn't be changed when lawson address exists"));
//                return lUpdateErrors;
//              }
//            }
//          }
//        }
//        // Verify the form information submitted.
//        if(pForm.getCity()!=null){pForm.setCity(pForm.getCity().trim());}
//        if(pForm.getStreetAddr1()!=null){pForm.setStreetAddr1(pForm.getStreetAddr1().trim());}
//        if(pForm.getStreetAddr2()!=null){pForm.setStreetAddr2(pForm.getStreetAddr2().trim());}
//        if(pForm.getStreetAddr3()!=null){pForm.setStreetAddr3(pForm.getStreetAddr3().trim());}
//        if(pForm.getStreetAddr4()!=null){pForm.setStreetAddr4(pForm.getStreetAddr4().trim());}
//        if(pForm.getStateOrProv()!=null){pForm.setStateOrProv(pForm.getStateOrProv().trim());}
//        if(pForm.getPostalCode()!=null){pForm.setPostalCode(pForm.getPostalCode().trim());}
//
//        if (pForm.getAccountId().length() == 0 ||
//          pForm.getAccountId().equals("0")) {
//          lUpdateErrors.add("accountId",
//                    new ActionError("variable.empty.error", "Account Id"));
//          }
//        if (pForm.getStatusCd().length() == 0) {
//          lUpdateErrors.add("statusCd", new ActionError("variable.empty.error",
//                    "Status"));
//        }
//        if (pForm.getName().length() == 0) {
//          lUpdateErrors.add("name", new ActionError("variable.empty.error",
//                "Name"));
//        }
//
//        /*if (pForm.getName1().length() == 0) {
//          lUpdateErrors.add("name1", new ActionError("variable.empty.error",
//                 "Name1"));
//        }
//
//        if (pForm.getName2().length() == 0) {
//          lUpdateErrors.add("name2", new ActionError("variable.empty.error",
//                 "Name2"));
//        }*/
//        if (pForm.getStreetAddr1().length() == 0) {
//          lUpdateErrors.add("streetAddr1", new ActionError("variable.empty.error",
//                  "Street Address 1"));
//        }
//        if (pForm.getCity().length() == 0) {
//          lUpdateErrors.add("city", new ActionError("variable.empty.error",
//                  "City"));
//        }
//        if (pForm.getCountry().length() == 0) {
//          lUpdateErrors.add("country", new ActionError("variable.empty.error",
//                   "Country"));
//        }
//
//       CountryData country = getCountry(session, pForm);
//       APIAccess factory = new APIAccess();
//       Country countryBean = factory.getCountryAPI();
//       CountryPropertyData countryProp =
//         countryBean.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
//       boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");
//
//       if (isStateProvinceRequired && pForm.getStateOrProv().length() == 0) {
//         lUpdateErrors.add("stateOrProv",
//                           new ActionError("variable.empty.error",
//                                           "State/Province"));
//       }
//       if (!isStateProvinceRequired && pForm.getStateOrProv().length() > 0) {
//         lUpdateErrors.add("stateOrProv",
//                           new ActionError("variable.must.be.empty.error",
//                                           "State/Province"));
//       }
//
//
//        if (pForm.getPostalCode().length() == 0) {
//          lUpdateErrors.add("postalCode",new ActionError("variable.empty.error",
//                   "Zip/Postal Code"));
//        }
//        if (pForm.getTargetFacilityRank()!= null && pForm.getTargetFacilityRank().length() > 0) {
//          try{
//            targetFacilityRank = new Integer(pForm.getTargetFacilityRank());
//          }catch(NumberFormatException e){
//            lUpdateErrors.add("targetFacilityRank", new ActionError("error.invalidNumber",
//                   "Target Facility Rank"));
//          }
//        }
//
//        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//        String effDateS = pForm.getEffDate();
//        if(effDateS!=null && effDateS.trim().length()>0) {
//          try{
//            effDate = df.parse(effDateS);
//          } catch (Exception exc) {
//           lUpdateErrors.add("effDate", new ActionError("variable.date.format.error",
//                 "Effective Date"));
//          }
//        }
//        String expDateS = pForm.getExpDate();
//        if(expDateS!=null && expDateS.trim().length()>0) {
//          try{
//            expDate = df.parse(expDateS);
//          } catch (Exception exc) {
//            lUpdateErrors.add("expDate", new ActionError("variable.date.format.error",
//                  "Expiration Date"));
//          }
//        }
//        if(effDate!=null && expDate!=null && expDate.before(effDate)) {
//          lUpdateErrors.add("EffExpDates", new ActionError("error.simpleGenericError",
//                   "Effective date can't be after expiration date"));
//        }
//        String statusCd = pForm.getStatusCd();
//        if(statusCd.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)){
//          Date curDate = new Date();
//          if((effDate!=null && effDate.after(curDate)) ||
//             (expDate!=null && expDate.before(curDate))) {
//            lUpdateErrors.add("EffExpDatesStatusCd", new ActionError("error.simpleGenericError",
//                     "Exffective and expiration dates conflict with domain status"));
//
//          }
//        }
//      }
//      if(pForm.getBlanketPoNumId() != null && pForm.getBlanketPoNumId().intValue()!=0){
//        BlanketPoNumDataVector bpdv =
//              (BlanketPoNumDataVector)session.getAttribute("Site.account.blanketPos");
//        if(bpdv != null){
//          int bpoid = pForm.getBlanketPoNumId().intValue();
//          Iterator it = bpdv.iterator();
//          while(it.hasNext()){
//            BlanketPoNumData avaBpo = (BlanketPoNumData) it.next();
//            if(avaBpo.getBlanketPoNumId() == bpoid){
//              blanketPo = avaBpo;
//            }
//          }
//        }
//
//        if(blanketPo == null){
//          lUpdateErrors.add("blanketPoNumId",new ActionError("error.domainBlanketPoNotAvaliable"));
//        }
//      }
//
//      if (lUpdateErrors.size() > 0) {
//        // Report the errors to allow for edits.
//        return lUpdateErrors;
//      }
//
//      APIAccess factory = new APIAccess();
//      Site siteBean = factory.getSiteAPI();
//      AddressValidator validator = factory.getAddressValidatorAPI();
//
//      int domainid = pForm.getIntId();
//      // Get the current information for this Site.
//      SiteData dd;
//      BusEntityData bed;
//      PropertyDataVector props;
//      PropertyDataVector domainFields = new PropertyDataVector();
//      PropertyDataVector domainFieldsRuntime = new PropertyDataVector();
//      AddressData address;
//      PropertyData taxableIndicator, invprop,invpropHoldOrder,invpropType;
//
//      if (domainid > 0) {
//        dd = siteBean.getSite(domainid, accountId);
//      }
//      else {
//        dd = SiteData.createValue();
//      }
//
//      dd.setBlanketPoNum(blanketPo);
//
//      dd.setTargetFacilityRank(targetFacilityRank);
//      bed = dd.getBusEntity();
//      props = dd.getMiscProperties();
//      address = dd.getSiteAddress();
//      taxableIndicator = dd.getTaxableIndicator();
//      invprop = dd.getInventoryShopping();
//      invpropHoldOrder = dd.getInventoryShoppingHoldOrderUntilDeliveryDate();
//      invpropType=dd.getInventoryShoppingType();
//
//      bed.setWorkflowRoleCd("UNKNOWN");
//      String cuser = (String) session.getAttribute(Constants.USER_NAME);
//
//      // Now update with the data from the form.
//      bed.setShortDesc(pForm.getName());
//      bed.setLongDesc(pForm.getName());
//      bed.setBusEntityStatusCd(pForm.getStatusCd());
//      bed.setEffDate(effDate);
//      bed.setExpDate(expDate);
//      bed.setErpNum(pForm.getSiteNumber());
//      bed.setLocaleCd("unk");
//      bed.setModBy(cuser);
//
//      address.setModBy(cuser);
//      address.setName1(pForm.getName1());
//      address.setName2(pForm.getName2());
//      address.setAddress1(pForm.getStreetAddr1());
//      address.setAddress2(pForm.getStreetAddr2());
//      address.setAddress3(pForm.getStreetAddr3());
//      address.setAddress4(pForm.getStreetAddr4());
//      address.setCity(pForm.getCity());
//      address.setStateProvinceCd(pForm.getStateOrProv());
//      address.setPostalCode(pForm.getPostalCode());
//      address.setCountryCd(pForm.getCountry());
//      address.setPrimaryInd(true);
//      address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
//      address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
//
//      if(!validator.validateAddress(address)){
//        lUpdateErrors.add("updateSite",new ActionError("error.addressValidation"));
//        return(lUpdateErrors);
//      }
//
//      dd.setBSC(findBSC(request,pForm.getSubContractor()));
//
//      taxableIndicator.setValue(pForm.getTaxableIndicator());
//      taxableIndicator.setAddBy(cuser);
//
//      boolean updfComments = false;
//      boolean updfShipMsg = false;
//      boolean domainRefNum = false;
//      boolean bypassOrderRouting = false;
//      boolean consolidatedOrderWarehouse = false;
//      boolean shareOrderGuide = false;
//      for (Iterator itr = props.iterator(); itr.hasNext();) {
//        PropertyData prop = (PropertyData) itr.next();
//        if (prop.getShortDesc().equals
//          (RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)) {
//          prop.setValue(pForm.getComments());
//          updfComments = true;
//        }
//        else if (prop.getShortDesc().equals
//                 (RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)) {
//          prop.setValue(pForm.getShippingMessage());
//          updfShipMsg = true;
//        }
//        else if (prop.getShortDesc().equals
//                 (RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)) {
//          prop.setValue(pForm.getSiteReferenceNumber());
//          domainRefNum = true;
//        }
//        else if (prop.getShortDesc().equals
//                 (RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
//          if(pForm.isBypassOrderRouting()){
//            prop.setValue("true");
//          }else{
//            prop.setValue("false");
//          }
//          //prop.setValue(Boolean.toString(pForm.isBypassOrderRouting()));
//          bypassOrderRouting = true;
//        }
//        else if (prop.getShortDesc().equals
//                 (RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
//          if(pForm.isConsolidatedOrderWarehouse()){
//            prop.setValue("true");
//          }else{
//            prop.setValue("false");
//          }
//          consolidatedOrderWarehouse = true;
//        }
//        else if (prop.getShortDesc().equals
//                 (RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES)) {
//          if(pForm.getShareBuyerOrderGuides()){
//            prop.setValue("true");
//          }else{
//            prop.setValue("false");
//          }
//          shareOrderGuide = true;
//        }
//      }
//
//      boolean tf = pForm.isInventoryShoppingHoldOrderUntilDeliveryDate();
//      if ( tf == true ) {
//        invpropHoldOrder.setValue("true");
//        invpropHoldOrder.setModBy(cuser);
//      }
//      else {
//        invpropHoldOrder.setValue("false");
//        invpropHoldOrder.setModBy(cuser);
//      }
//      dd.setInventoryShoppingHoldOrderUntilDeliveryDate(invpropHoldOrder);
//
//
//      tf = pForm.getInventoryShopping();
//      if ( tf == true ) {
//        invprop.setValue("on");
//        invprop.setAddBy(cuser);
//      }
//      else {
//        invprop.setValue("off");
//        invprop.setModBy(cuser);
//      }
//
//      tf = pForm.getInventoryShoppingType();
//	  if(invpropType == null){
//		invpropType=PropertyData.createValue();
//	  }
//      if ( tf == true ) {
//        invpropType.setValue("on");
//        invpropType.setAddBy(cuser);
//      }
//      else {
//        invpropType.setValue("off");
//        invpropType.setModBy(cuser);
//      }
//      dd.setInventoryShoppingType(invpropType);
//
//      if (updfComments == false) {
//        PropertyData nprop = PropertyData.createValue();
//        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS);
//        nprop.setValue(pForm.getComments());
//        nprop.setAddBy(cuser);
//        props.add(nprop);
//      }
//
//      if (updfShipMsg == false) {
//        PropertyData nprop = PropertyData.createValue();
//        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
//        nprop.setValue(pForm.getShippingMessage());
//        nprop.setAddBy(cuser);
//        props.add(nprop);
//      }
//
//      if (domainRefNum == false) {
//        PropertyData nprop = PropertyData.createValue();
//        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
//        nprop.setValue(pForm.getSiteReferenceNumber());
//        nprop.setAddBy(cuser);
//        props.add(nprop);
//      }
//
//      if (bypassOrderRouting == false) {
//        PropertyData nprop = PropertyData.createValue();
//        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING);
//        if(pForm.isBypassOrderRouting()){
//          nprop.setValue("true");
//        }else{
//          nprop.setValue("false");
//        }
//        //nprop.setValue(Boolean.toString(pForm.isBypassOrderRouting()));
//        nprop.setAddBy(cuser);
//        props.add(nprop);
//      }
//      if (consolidatedOrderWarehouse == false) {
//        PropertyData nprop = PropertyData.createValue();
//        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
//        if(pForm.isConsolidatedOrderWarehouse()){
//          nprop.setValue("true");
//        }else{
//          nprop.setValue("false");
//        }
//        nprop.setAddBy(cuser);
//        props.add(nprop);
//      }
//      if (shareOrderGuide == false) {
//        PropertyData nprop = PropertyData.createValue();
//        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
//        if(pForm.getShareBuyerOrderGuides()){
//          nprop.setValue("true");
//        }else{
//          nprop.setValue("false");
//        }
//        nprop.setAddBy(cuser);
//        props.add(nprop);
//      }
//
//      // Set up the domain fields vector.
//      BusEntityFieldsData sfd =
//              (BusEntityFieldsData) session.getAttribute("Site.account.fields");
//      if (null != sfd ) {
//        if (sfd.getF1ShowAdmin() || sfd.getF1ShowRuntime() ) {
//          if(sfd.getF1Required() && !Utility.isSet(pForm.getF1Value())){
//            lUpdateErrors.add("F1Tag", new ActionError("variable.empty.error",
//                sfd.getF1Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF1Tag());
//          nprop.setValue(pForm.getF1Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF1ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//
//        }
//        if ( sfd.getF2ShowAdmin() || sfd.getF2ShowRuntime()  ) {
//          if(sfd.getF2Required() && !Utility.isSet(pForm.getF2Value())){
//              lUpdateErrors.add("F2Tag",
//                            new ActionError("variable.empty.error",sfd.getF2Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF2Tag());
//          nprop.setValue(pForm.getF2Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF2ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF3ShowAdmin() || sfd.getF3ShowRuntime()  ) {
//          if(sfd.getF3Required() && !Utility.isSet(pForm.getF3Value())){
//            lUpdateErrors.add("F3Tag",
//                        new ActionError("variable.empty.error",sfd.getF3Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF3Tag());
//          nprop.setValue(pForm.getF3Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF3ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF4ShowAdmin() || sfd.getF4ShowRuntime()  ) {
//          if(sfd.getF4Required() && !Utility.isSet(pForm.getF4Value())){
//            lUpdateErrors.add("F4Tag",
//                          new ActionError("variable.empty.error",sfd.getF4Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF4Tag());
//          nprop.setValue(pForm.getF4Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF4ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF5ShowAdmin() || sfd.getF5ShowRuntime()  ) {
//          if(sfd.getF5Required() && !Utility.isSet(pForm.getF5Value())){
//            lUpdateErrors.add("F5Tag",
//                       new ActionError("variable.empty.error",sfd.getF5Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF5Tag());
//          nprop.setValue(pForm.getF5Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF5ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF6ShowAdmin() || sfd.getF6ShowRuntime()  ) {
//          if(sfd.getF6Required() && !Utility.isSet(pForm.getF6Value())){
//            lUpdateErrors.add("F6Tag",
//                     new ActionError("variable.empty.error",sfd.getF6Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF6Tag());
//          nprop.setValue(pForm.getF6Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF6ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF7ShowAdmin() || sfd.getF7ShowRuntime()  ) {
//          if(sfd.getF7Required() && !Utility.isSet(pForm.getF7Value())){
//              lUpdateErrors.add("F7Tag",
//                            new ActionError("variable.empty.error",sfd.getF7Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF7Tag());
//          nprop.setValue(pForm.getF7Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF7ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF8ShowAdmin() || sfd.getF8ShowRuntime()  ) {
//          if(sfd.getF8Required() && !Utility.isSet(pForm.getF8Value())){
//            lUpdateErrors.add("F8Tag",
//                        new ActionError("variable.empty.error",sfd.getF8Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF8Tag());
//          nprop.setValue(pForm.getF8Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF8ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF9ShowAdmin() || sfd.getF9ShowRuntime()  ) {
//          if(sfd.getF9Required() && !Utility.isSet(pForm.getF9Value())){
//            lUpdateErrors.add("F9Tag",
//                          new ActionError("variable.empty.error",sfd.getF9Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF9Tag());
//          nprop.setValue(pForm.getF9Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF9ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        if ( sfd.getF10ShowAdmin() || sfd.getF10ShowRuntime()  ) {
//          if(sfd.getF10Required() && !Utility.isSet(pForm.getF10Value())){
//            lUpdateErrors.add("F10Tag",
//                          new ActionError("variable.empty.error",sfd.getF10Tag()));
//          }
//          PropertyData nprop = PropertyData.createValue();
//          nprop.setPropertyTypeCd
//              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
//          nprop.setShortDesc(sfd.getF10Tag());
//          nprop.setValue(pForm.getF10Value());
//          nprop.setAddBy(cuser);
//          domainFields.add(nprop);
//          if(sfd.getF10ShowRuntime()){
//            domainFieldsRuntime.add(nprop);
//          }
//        }
//        dd.setDataFieldProperties(domainFields);
//        dd.setDataFieldPropertiesRuntime(domainFieldsRuntime);
//
//      }
//      if(!(lUpdateErrors.size() == 0)){
//        return lUpdateErrors;
//      }
//
//      if (domainid == 0) {
//        // get account - both to verify that the id is that
//        // of an account and get it's name
//        try {
//          Account accountBean = factory.getAccountAPI();
//          AccountData ad = accountBean.getAccount(accountId, 0);
//          pForm.setAccountName(ad.getBusEntity().getShortDesc());
//        } catch (DataNotFoundException de) {
//          // the given account id is apparently not an account
//          pForm.setAccountName("");
//          lUpdateErrors.add("Account.id", new ActionError("domain.bad.account"));
//          return lUpdateErrors;
//        } catch (Exception e) {
//          e.printStackTrace();
//           lUpdateErrors.add("SiteErrors",
//                    new ActionError("error.genericError",e.getMessage()));
//          return lUpdateErrors;
//        }
//
//        // create the default property ShareOrderGuide
//        PropertyData pdata = PropertyData.createValue();
//        pdata.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
//        pdata.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
//        pdata.setAddBy(cuser);
//        pdata.setValue("true");
//        props.add(pdata);
//
//        // Add the domain.
//        bed.setAddBy(cuser);
//        bed.setBusEntityStatusCd(pForm.getStatusCd());
//        address.setAddBy(cuser);
//         try {
//          dd = siteBean.addSite(dd, accountId);
//        } catch (DuplicateNameException ne) {
//          lUpdateErrors.add("name", new ActionError("error.field.notUnique",
//              "Name"));
//          return lUpdateErrors;
//        } catch (Exception e) {
//          String errorMess = Utility.getUiErrorMess(e.getMessage());
//          if(errorMess==null) errorMess = e.getMessage();
//           lUpdateErrors.add("SiteErrors",
//                new ActionError("error.simpleGenericError",errorMess));
//          return lUpdateErrors;
//        }
//        domainid = dd.getBusEntity().getBusEntityId();
//        pForm.setId(domainid);
//      }
//      else {
//        // Update this Site
//        try {
//          siteBean.updateSite(dd);
//        } catch (DuplicateNameException ne) {
//          lUpdateErrors.add("name", new ActionError("error.field.notUnique",
//              "Name"));
//          return lUpdateErrors;
//        } catch (Exception e) {
//          String errorMess = Utility.getUiErrorMess(e.getMessage());
//          if(errorMess==null) errorMess = e.getMessage();
//           lUpdateErrors.add("SiteErrors",
//                new ActionError("error.simpleGenericError",errorMess));
//          return lUpdateErrors;
//        }
//      }
//      pForm.setAvailableShipto(null);
//      try {
//          fetchDetail(request, domainid,pForm);
//      }
//      catch (DataNotFoundException e) {
//        String errorMess = Utility.getUiErrorMess(e.getMessage());
//        if(errorMess==null) errorMess = e.getMessage();
//        lUpdateErrors.add("SiteErrors",
//              new ActionError("error.simpleGenericError",errorMess));
//        return lUpdateErrors;
//      }
//
//      return lUpdateErrors;
//    }


    /**
     *  The <code>delete</code> method removes the database entries defining
     *  this domain if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Site
     */
    public static ActionErrors delete(HttpServletRequest request,
              ActionForm form)
        throws Exception {

        ActionErrors deleteErrors = new ActionErrors();
        DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        String strid = request.getParameter("id");
        if (strid == null || strid.length() == 0) {
      deleteErrors.add("id", new ActionError("error.badRequest", "id"));
      return deleteErrors;
        }

        Integer id = new Integer(strid);
        int acctid = Integer.parseInt(request.getParameter("accountId"));
        SiteData dd = siteBean.getSite(id.intValue(), acctid, false, SessionTool.getCategoryToCostCenterView(session, id.intValue()));

        if (null != dd) {
      try {
    siteBean.removeSite(dd);
      } catch (Exception e) {
                deleteErrors.add("id",
         new ActionError("error.deleteFailed",
             "Site"));
    return deleteErrors;
      }
            session.removeAttribute("Site.found.vector");
        }

  return deleteErrors;
    }


    /**
     *  <code>initConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void initConfig(HttpServletRequest request,
          ActionForm form)
  throws Exception {

        HttpSession session = request.getSession();
        session.removeAttribute("domain.assoc.default.store.ids");
        session.removeAttribute("domain.stores.vector");
        session.removeAttribute("domain.assoc.store.ids");
        return;
    }

    /**
     *  <code>searchConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static ActionErrors searchConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
     initConfig(request, form);
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm)form;
    String configType = pForm.getConfigSearchType();

//    if (configType.equals("Stores")) {
      ae = searchStoreConfig(request,form);
//    } else if (configType.equals("Catalog")) {
//      ae = searchCatalogConfig(request,form);
//    } else if(configType.equals("Distributor Schedule")) {
//      ae = searchSiteSchedules(request,form);
//    }
    return ae;
  }

  public static ActionErrors searchStoreConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Store storeBean = factory.getStoreAPI();
    DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm)form;
    String searchField = pForm.getConfigSearchField();
    String searchType = pForm.getConfigSearchType();

//     int accountId = Integer.parseInt(pForm.getAccountId());
    DomainAdmDetailForm domAdmDetailForm = (DomainAdmDetailForm)session.getAttribute("DOMAIN_ADM_DETAIL_FORM");
    int domainId = Integer.parseInt(domAdmDetailForm.getId());
//    int storeId = Integer.parseInt(pForm.getStoreId());


    // get all stores that could possibly be associated with this domain
    // (i.e. all account stores)
    BusEntityDataVector sDV = null;
    BusEntityDataVector stores = null;
    BusEntityDataVector storesAssociatedWithDomain = null;

    if(!Utility.isSet(searchField)) {
      //get all
      //Collects all already configured stores
      stores = storeBean.getStoresCollectionByBusEntity(domainId);
      if(pForm.getConfiguredOnly()) {
        sDV = stores;
      } else {
        //Collects all stores eligible
        sDV =  storeBean.getAllStoresBusEntityData(Store.ORDER_BY_NAME);
        request.getSession().setAttribute("domain.stores.vector", sDV);
      }
    } else { //filter is set
          int searchTypeCdInt = searchType.equals("nameBegins")?
                                    User.NAME_BEGINS_WITH_IGNORE_CASE:
                                    User.NAME_CONTAINS_IGNORE_CASE;


          if(pForm.getConfiguredOnly()) {
              sDV = stores;
          } else {
              stores = storeBean.getStoresCollectionByBusEntity(  domainId,
                  searchField,
                  searchTypeCdInt,
                  User.ORDER_BY_NAME);
              sDV = storeBean.getStoresCollectionByBusEntity(searchField, searchTypeCdInt, User.ORDER_BY_NAME);
          }
    }
    storesAssociatedWithDomain = storeBean.getStoresCollectionByBusEntity(domainId);

    IdVector storeIds = new IdVector();
    String[] displayIds = new String[sDV.size()];
    String[] selectIds = new String[sDV.size()];
    String defaultStoreId = null;
    int ind = 0;
    BusEntityData storData = null;
    Integer dSId = null;
    int intDSId;
    int intSSId;
    for (Iterator storeI = sDV.iterator(); storeI.hasNext(); ind++) {
      storData = (BusEntityData)storeI.next();
      Integer id = new Integer((storData).getBusEntityId());
      storeIds.add(id);
      displayIds[ind] = id.toString();
      for (Iterator storeIterator = stores.iterator(); storeIterator.hasNext();) {
    	  dSId = new Integer(((BusEntityData)storeIterator.next()).getBusEntityId());
    	  intDSId = dSId.intValue();
    	  intSSId = id.intValue();
    	  if (intDSId==intSSId) {
          	  selectIds[ind] = id.toString();
          }
      }
    }

    BusEntityDataVector defSt = storeBean.getDefaultStoreByBusEntity(domainId);
//    int defaultStoreId = 0;

    IdVector defStoreIds = storeIds;
    String[] displayDefaultStoreIds = new String[sDV.size()];
    String[] selectDefaultStoreIds = new String[sDV.size()];
    int indD = 0;
    boolean defStoreToBeDisplayed = false;
    for (Iterator storeI = sDV.iterator(); storeI.hasNext(); indD++) {
    	Integer id = new Integer(((BusEntityData)storeI.next()).getBusEntityId());  // get displayed element
    	displayDefaultStoreIds[indD] = id.toString();
        for (Iterator storI = defSt.iterator(); storI.hasNext();) {
            Integer id2 = new Integer(((BusEntityData)storI.next()).getBusEntityId());
            if (id.intValue()==id2.intValue()) {
            	defStoreToBeDisplayed = true;
            	break;
            }
         }
        //defStoreIds.add(id);
        if (defStoreToBeDisplayed) {
            selectDefaultStoreIds[indD] = id.toString();
            defaultStoreId = id.toString();
            defStoreToBeDisplayed = false;  // reset
         }
    }

    pForm.setSelectIds(selectIds);
    pForm.setSelectDefaultStoreIds(selectDefaultStoreIds);
    pForm.setDisplayIds(displayIds);
    pForm.setDisplayDefaultStoreIds(displayDefaultStoreIds);
    pForm.setDefaultStoreId(defaultStoreId);

    IdVector domAssocDefStAttrib = new IdVector();
    for (Iterator storI = defSt.iterator(); storI.hasNext();) {
        Integer id2 = new Integer(((BusEntityData)storI.next()).getBusEntityId());
        domAssocDefStAttrib.add(id2);
    }
    session.setAttribute("domain.assoc.default.store.ids", domAssocDefStAttrib);
    IdVector domAssocStAttrib = new IdVector();
    for (Iterator storI = storesAssociatedWithDomain.iterator(); storI.hasNext();) {
        Integer id2 = new Integer(((BusEntityData)storI.next()).getBusEntityId());
        domAssocStAttrib.add(id2);
    }
    session.setAttribute("domain.assoc.store.ids", domAssocStAttrib);
    session.setAttribute("domain.assoc.default.store.id", defaultStoreId);

    request.getSession().setAttribute("domain.stores.vector", sDV);

    return ae;
  }


    /**
     *  <code>updateConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static ActionErrors updateConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception {
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    ActionErrors updateErrors = new ActionErrors();

    DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm) form;
    DomainAdmDetailForm domAdmDetailForm = (DomainAdmDetailForm)session.getAttribute("DOMAIN_ADM_DETAIL_FORM");
    int domainId = Integer.parseInt(domAdmDetailForm.getId());

    String configType = pForm.getConfigType();
    boolean oneDefStAssocAdded = false;
    boolean oneDefStAssocRemoved = false;
    if (configType.equals("Stores")) {
      Store storeBean = factory.getStoreAPI();
      // get list of store ids displayed
      String[] displayed = pForm.getDisplayIds();
      // get list of store ids selected
      String[] selected = pForm.getSelectIds();
      String[] displayedDefaultStore = pForm.getDisplayDefaultStoreIds();
      displayed = displayedDefaultStore;
      String[] selectedDefaultStore = pForm.getSelectDefaultStoreIds();
      String selectedDefStore = pForm.getDefaultStoreId();
      int selDefStOldPosition = -1;
      int selDefStNewPosition = -1;
      for (int k=0; k < displayedDefaultStore.length; k++) {
    	  if (displayedDefaultStore[k].equalsIgnoreCase(selectedDefStore)) {
    		  selDefStNewPosition = k;
    	  }
    	  if (selectedDefaultStore[k]!=null) {
    		  selDefStOldPosition = k;
    	  }
      }
//      if (selDefStNewPosition>=0 && selDefStOldPosition>=0) {
//	      selectedDefaultStore[selDefStNewPosition] = selectedDefStore;
//	      selectedDefaultStore[selDefStOldPosition] = null;
//      }
      if (selDefStNewPosition>=0) {
	      selectedDefaultStore[selDefStNewPosition] = selectedDefStore;
      }
      if (selDefStOldPosition>=0) {
    	  if (selDefStOldPosition!=selDefStNewPosition) {
    		  selectedDefaultStore[selDefStOldPosition] = null;
    	  }
      }
      if (selectedDefStore!=null && (selectedDefStore.length() > 0)) {
          boolean validationOK = false;
	      validation:	     // setting unselected store as a default store
	      for (int h=0; h < selectedDefaultStore.length; h++) {
	          if (selectedDefaultStore[h] != null) {
	        	  for (int g=0; g < selected.length; g++) {
		        	  if (selectedDefaultStore[h].equalsIgnoreCase(selected[g]))  {
		        		  validationOK = true;
		        		  break validation;
		        	  }
	        	  }
	          }
	      }
	      if (!validationOK) {
		   	  updateErrors.add("domain",
					new ActionError
				    	("error.simpleGenericError", "Unselected store cannot be set as default."));
			  return updateErrors;
	      }
      }

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String updaterName = appUser.getUser().getUserName();

      // get list of currently associated store ids
      IdVector assocStoreIds = (IdVector)session.getAttribute("domain.assoc.store.ids");
      int defaultStoreAssocAdded = -1;
//      BusEntityDataVector assocStoreV = (BusEntityDataVector)session.getAttribute("domain.assoc.store.ids");
//      IdVector assocStoreIds = null;
//      for (Iterator storI = assocStoreV.iterator(); storI.hasNext();) {
//          Integer id2 = new Integer(((BusEntityData)storI.next()).getBusEntityId());
//          assocStoreIds.add(id2);
//       }
      IdVector assocDefaultStoreIds = (IdVector)session.getAttribute("domain.assoc.default.store.ids");

      int assocDefaultStoreIdsSizeBeforeUpdate = assocDefaultStoreIds.size();
//      BusEntityDataVector assocDefaultStoreV = (BusEntityDataVector)session.getAttribute("domain.assoc.default.store.ids");

//      for (Iterator storI = assocDefaultStoreV.iterator(); storI.hasNext();) {
//          Integer id2 = new Integer(((BusEntityData)storI.next()).getBusEntityId());
//          assocDefaultStoreIds.add(id2);
//      }

      // Looking for two cases:
      // 1. user is selected, but not currently associated - this
      //    means we need to add the association
      // 2. user is not selected, but is currently associated - this
      //    means we need to remove the association

      for (int i = 0; i < displayed.length; i++) {
       String did = displayed[i];
       boolean foundId = false;
       for (int j = 0; j < selected.length; j++) {
         if (did.equals(selected[j])) {
           foundId = true;
           break;
         }
       }
       boolean foundDefaultStoreId = false;
       for (int j = 0; j < selectedDefaultStore.length; j++) {
         if (did.equals(selectedDefaultStore[j])) {
           foundDefaultStoreId = true;
           break;
         }
       }

       Integer id = new Integer(did);
       int assocIndex = assocStoreIds.indexOf(id);
       int assocDefaultStoreIndex = assocDefaultStoreIds.indexOf(id);
       if (foundId) {   // displayed and selected
          if (assocIndex < 0) {
            // we need to add the association, the selected list
            // has the id, but not on assoc domains list
            storeBean.addStoreAssoc(id.intValue(), domainId, RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_OF_DOMAIN, updaterName);
            assocStoreIds.add(id);
          }
       } else if (assocIndex >= 0) {
         // we need to remove the association, the selected list
         // doesn't have the id, but it is on the assoc domains list
         // (means it was 'unselected')
         storeBean.removeStoreAssoc(id.intValue(), domainId, RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_OF_DOMAIN);
         assocStoreIds.remove(assocIndex);
       }
       if (foundDefaultStoreId) {   // displayed and selected
           if (assocDefaultStoreIndex < 0) {
             // we need to add the association, the selected list
             // has the id, but not on assoc domains list
             storeBean.addStoreAssoc(id.intValue(), domainId, RefCodeNames.BUS_ENTITY_ASSOC_CD.DEFAULT_STORE_OF_DOMAIN, updaterName);
             assocDefaultStoreIds.add(id);
             defaultStoreAssocAdded = id.intValue();
             oneDefStAssocAdded = true;
           }
        } else if (assocDefaultStoreIndex >= 0) {
          // we need to remove the association, the selected list
          // doesn't have the id, but it is on the assoc domains list
          // (means it was 'unselected')
          storeBean.removeStoreAssoc(id.intValue(), domainId, RefCodeNames.BUS_ENTITY_ASSOC_CD.DEFAULT_STORE_OF_DOMAIN);
          assocDefaultStoreIds.remove(assocDefaultStoreIndex);
          oneDefStAssocRemoved = true;
        }
     }
     if (oneDefStAssocAdded && !oneDefStAssocRemoved && (assocDefaultStoreIdsSizeBeforeUpdate>0)) {   // need to remove 'excessive' default store association
    	 for (Iterator storeIterator = assocDefaultStoreIds.iterator(); storeIterator.hasNext();) {
        	 int id = ((Integer)storeIterator.next()).intValue();
        	 if (defaultStoreAssocAdded!=id) {    // default store association which existed before
        		 storeBean.removeStoreAssoc(id, domainId, RefCodeNames.BUS_ENTITY_ASSOC_CD.DEFAULT_STORE_OF_DOMAIN);
        	 }
         }
     }

     // update with changes

     session.setAttribute("domain.assoc.store.ids", assocStoreIds);
     session.setAttribute("domain.assoc.default.store.ids", assocDefaultStoreIds);

   }
   return updateErrors;
 }

    /**
     *  <code>sortConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void sortConfig(HttpServletRequest request,
          ActionForm form)
  throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm)form;
    String sortField = request.getParameter("sortField");
    String configType = request.getParameter("configType");
    if (configType == null) {
      return;
    }

    if (configType.equals("Stores")) {
      StoreDataVector stores = (StoreDataVector)session.getAttribute("domain.stores.vector");
      if (stores == null) {
        return;
      }
      DisplayListSort.sort(stores, sortField);

      // Need to init the selected/checked stores
      IdVector assocUserIds = (IdVector)session.getAttribute("domain.assoc.user.ids");
      String[] selected = new String[assocUserIds.size()];
      Iterator assocI = assocUserIds.iterator();
      int i = 0;
      while (assocI.hasNext()) {
        selected[i++] = new String(assocI.next().toString());
      }
      pForm.setSelectIds(selected);
    } else if (configType.equals("Catalog")) {
      CatalogDataVector catalogs =
                 (CatalogDataVector)session.getAttribute("domain.stores.vector");
      if (catalogs == null) {
        return;
      }
      DisplayListSort.sort(catalogs, sortField);
    }

    // so that the display correctly shows the type of config being done
    pForm.setConfigType(configType);
    return;
  }


    public static ActionErrors updateInventoryData
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        String cuser =
        (String) session.getAttribute(Constants.USER_NAME);

        try {
            factory = new APIAccess();
            InventoryForm iform = (InventoryForm)form;
            SiteInventoryConfigViewVector sivv = iform.getInventoryItems();
            int siteId = (sivv!=null)? ((SiteInventoryConfigView)sivv.get(0)).getSiteId() : 0;

            InventoryForm oform = (InventoryForm)
                session.getAttribute("SITE_INVENTORY_FORM");
            SiteInventoryConfigViewVector ov = oform.getInventoryItems();
            for ( int ix = 0; ov != null && ix < ov.size(); ix++ ) {
                SiteInventoryConfigView oldv =
                    (SiteInventoryConfigView)ov.get(ix);
                SiteInventoryConfigView newv =
                    (SiteInventoryConfigView)sivv.get(ix);
                newv.setSiteId(oldv.getSiteId());
                newv.setItemId(oldv.getItemId());
                newv.setSumOfAllParValues(oldv.getSumOfAllParValues());
                newv.setModBy(cuser);
                siteId = oldv.getSiteId();
            }

            Site sbean = factory.getSiteAPI();
            iform.setInventoryItems
                (sbean.updateInventoryConfig(sivv, SessionTool.getCategoryToCostCenterView(session,siteId, 0 )));

        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                                           "No ejb access"));
        }
        return ae;
    }

    public static ActionErrors lookupInventoryData
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();
        InventoryForm iform;
        if ( form != null ) {
            iform = (InventoryForm)form;
        }
        else {
            iform = new InventoryForm();
        }

        if (ShopTool.getCurrentSiteId(request) <= 0) {
            SiteInventoryConfigViewVector ov =
    new SiteInventoryConfigViewVector();
            iform.setInventoryItems(ov);
            request.getSession().setAttribute("SITE_INVENTORY_FORM",
                                              iform);
            return ae   ;
        }
        HttpSession session = request.getSession();
        APIAccess factory = null;

        try {
            factory = new APIAccess();
            Site sbean = factory.getSiteAPI();
            SiteInventoryConfigViewVector ov = sbean.lookupInventoryConfig
                (ShopTool.getCurrentSiteId(request),false, SessionTool.getCategoryToCostCenterView(session,ShopTool.getCurrentSiteId(request)));

            iform.setInventoryItems(ov);
            request.getSession().setAttribute("SITE_INVENTORY_FORM",
                                              iform);

        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                                           "No ejb access"));
        }
        return ae;
    }

    public static ActionErrors lookupShoppingControls
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null ) {
            ae.add("error",new ActionError("error.systemError",
                                           "User is not logged in 11.3"));
            return ae   ;
        }

        SiteData currSite = ShopTool.getCurrentSite(request);
        if (currSite == null ) {
            ae.add("error",new ActionError("error.systemError",
                                           "No domain defined"));
            return ae   ;
        }

  SessionTool st = new SessionTool(request);
  SiteSettingsData siteSpecific
      = st.mkSiteSpecificData(request,currSite);

        HttpSession session = request.getSession();
        APIAccess factory = null;
        try {
            factory = new APIAccess();
      ShoppingServices ssvc = null;
      ssvc = factory.getShoppingServicesAPI();
      String storeType = "";
      StoreData store = ShopTool.getCurrentUser(request).getUserStore();
      if (store != null && store.getStoreType() != null ) {
    storeType = store.getStoreType().getValue();
      }

      ShoppingCartItemDataVector controlItems =
    ssvc.getItemControlInfo(storeType, currSite,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
      siteSpecific.setShoppingControls(controlItems);
      st.setSiteSettings(request,siteSpecific);
        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                                           "No ejb access"));
        }
        return ae;
    }

    public static ActionErrors listAll
        (String pListReq, HttpServletRequest request,
         ActionForm form)    {
        return listAll( pListReq, request, form, false);
   }

    public static ActionErrors listAll
        (String pListReq, HttpServletRequest request,
         ActionForm form, boolean pResetList)    {
        ActionErrors ae = new ActionErrors();

        if ( pListReq.equals("list.all.bsc") &&
            request.getSession().getAttribute(pListReq) != null
             && pResetList == false
        )
        {
            return ae;
        }

        HttpSession session = request.getSession();
        ArrayList resl = new ArrayList();
        session.setAttribute(pListReq, resl);
        try {
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            IdVector storeIds;
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                storeIds = appUser.getUserStoreAsIdVector();
            }else{
                storeIds = null;
            }
            // Get all the business entities in question.
            Request rBean = factory.getRequestAPI();
            session.setAttribute
                (pListReq,
                 rBean.listAll
                 (storeIds,RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR)
                 );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ae;
    }


   public static ActionErrors updateShoppingControls
        (HttpServletRequest request,
         ActionForm form)    {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = null;
    String cuser = (String) session.getAttribute(Constants.USER_NAME);

    try {
      factory = new APIAccess();
      SiteShoppingControlForm pForm = (SiteShoppingControlForm)form;
      int domainid = pForm.getSiteId();
      Iterator it = pForm.getShoppingControlItemIds();
      ShoppingControlDataVector scdv = new ShoppingControlDataVector();

      while (it.hasNext()){
        String thisitemid = (String)it.next();
        if ( thisitemid != null ) {
          String maxqty = (String)
          pForm.getItemIdMaxAllowed(thisitemid);
          ShoppingControlData scd =
          ShoppingControlData.createValue();
          scd.setItemId(Integer.parseInt(thisitemid));
          scd.setMaxOrderQty(Integer.parseInt(maxqty));
          scd.setSiteId(domainid);
          scd.setModBy(cuser);
          scdv.add(scd);
        }
      }

      Site sbean = factory.getSiteAPI();
      ShoppingControlDataVector newvals =	sbean.updateShoppingControls(scdv, true);
      SessionTool st = new SessionTool(request);
      SiteSettingsData ssd = st.getSiteSettings(request,domainid);
      ssd.setShoppingControls(newvals);
    }
    catch (Exception e){
      e.printStackTrace();
    }

    return ae;
 }


//  public static ActionErrors saveFieldValues(HttpServletRequest request,
//              ActionForm form)
//  throws Exception {
//    ActionErrors lUpdateErrors = new ActionErrors();
//    DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm)form;
//    HashMap propsmap = pForm.getGeneralPropertyMap();
//    HttpSession session = request.getSession();
//
//    APIAccess factory = new APIAccess();
//    Site domainB = factory.getSiteAPI();
//
//    SiteData dd = pForm.getSiteData();
//    PropertyDataVector domainFields = dd.getDataFieldProperties();
//    String cuser = (String) session.getAttribute(Constants.USER_NAME);
//
//    // iterate through the values in the form and
//    // update the values in the domain.
//    java.util.Iterator keyset = propsmap.keySet().iterator();
//    while (keyset.hasNext() ) {
//      String k = (String)keyset.next();
//
//      // Look for this id in the domain fields
//      for (int idx = 0; null != domainFields && idx < domainFields.size(); idx++ ) {
//        PropertyData sf = (PropertyData)domainFields.get(idx);
//        if ( Integer.parseInt(k) == sf.getPropertyId() ) {
//            sf.setValue((String)propsmap.get(k));
//            sf.setModBy(cuser);
//            break;
//        }
//      }
//    }
//    domainB.saveSiteFields(dd.getSiteId(), domainFields);
//    return lUpdateErrors;
//  }
//
//
//  public static ActionErrors setSiteAccountFields(HttpServletRequest request,
//              ActionForm form)
//  throws Exception {
//    ActionErrors ae = new ActionErrors();
//    DomainAdmConfigurationForm pForm = (DomainAdmConfigurationForm)form;
//    HttpSession session = request.getSession();
//
//    APIAccess factory = new APIAccess();
//    PropertyService propi = factory.getPropertyServiceAPI();
//    AccountDataVector accountDV = pForm.getAccountFilter();
//    if(accountDV!=null && accountDV.size()>0) {
//      AccountData accountD = (AccountData) accountDV.get(0);
//      BusEntityFieldsData sfd =
//              propi.fetchSiteFieldsData(accountD.getBusEntity().getBusEntityId());
//      session.setAttribute("Site.account.fields", sfd);
//      pForm.setCountry(accountD.getPrimaryAddress().getCountryCd());
//      //Get lawson available shiptos
//      if(pForm.isERPEnabled()){
//         LawsonAdmin lawsonAdminEjb = factory.getLawsonAdminAPI();
//        try {
//          IdVector availableShiptos =
//                lawsonAdminEjb.getAvailableShipto(accountD.getBusEntity().getErpNum(),10);
//          pForm.setAvailableShipto(availableShiptos);
//        } catch (Exception e) {
//          pForm.setAvailableShipto(new IdVector());
//          String errorMess = "Error getting available ERP numbers: "+e.getMessage();
//          ae.add("error",
//                new ActionError("error.simpleGenericError",errorMess));
//          return ae;
//
//        }
//      }
//    }
//    return ae;
//  }

}


