
package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.util.RefCodeNames;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.view.forms.ManufMgrSearchForm;
import com.cleanwise.view.forms.ManufMgrDetailForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;


/**
 *  <code>ManufMgrLogic</code> implements the logic needed to manipulate
 *  Manufacturer records.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public class ManufMgrLogic {

    /**
     *  <code>getAll</code> manufacturers.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAll(HttpServletRequest request,
            ActionForm form)
             throws Exception {
                 
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Manufacturer manufBean = factory.getManufacturerAPI();
        ManufacturerDataVector dv;
        
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            dv = manufBean.getManufacturerByCriteria(crit);
        }else{
            dv = manufBean.getAllManufacturers(Manufacturer.ORDER_BY_NAME);
        }
        session.setAttribute("Manuf.found.vector", dv);
    }


    /**
     *  <code>getDetail</code> method provides the data needed to describe
     a manufacturer.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getDetail(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        HttpSession session = request.getSession();
        ManufMgrDetailForm sForm = (ManufMgrDetailForm) form;
        APIAccess factory = new APIAccess();
        Manufacturer manufBean = factory.getManufacturerAPI();

        String fieldValue = request.getParameter("searchField");
        if (null == fieldValue) {
            fieldValue = "0";
        }

        Integer id = new Integer(fieldValue);
        ManufacturerData dd = manufBean.getManufacturer(id.intValue());
        if (null != dd) {
            BusEntityData bed = dd.getBusEntity();
            // Set the form values.
            sForm.setName(bed.getShortDesc());
            sForm.setStatusCd(bed.getBusEntityStatusCd());
            sForm.setId(String.valueOf(bed.getBusEntityId()));
            sForm.setTypeCd(bed.getBusEntityTypeCd());
            sForm.setStoreId(Integer.toString(dd.getStoreId()));
            PhoneData fax;

            PhoneData phone;

            fax = dd.getPrimaryFax();
            sForm.setFax(fax.getPhoneNum());

            phone = dd.getPrimaryPhone();
            sForm.setPhone(phone.getPhoneNum());

            EmailData email = dd.getPrimaryEmail();
            sForm.setEmailAddress(email.getEmailAddress());

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

            // Get the specializations for this manufacturer.
            PropertyDataVector specz = dd.getSpecializations();
            setSPFlags(sForm, specz);

            return;
        }
    }


    /**
     *  <code>init</code> method.  Creates the Manuf.status.vector
     * , countries.vector, and the states.vector session scoped objects.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
            ActionForm form)
             throws Exception {
        HttpSession session = request.getSession();

        // Cache the lists needed for Manufacturers.
        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the manufacturer status list.
        RefCdDataVector statusv =
	    lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
        session.setAttribute("Manuf.status.vector", statusv);

        RefCdDataVector countriesv =
	    lsvc.getRefCodesCollection("ADDRESS_COUNTRY_CD");
        session.setAttribute("countries.vector", countriesv);

        RefCdDataVector business_classv =
	    lsvc.getRefCodesCollection("BUSINESS_CLASS_CD");
        session.setAttribute("business.class.vector", business_classv);

	RefCdDataVector woman_owned_businessv =
	    lsvc.getRefCodesCollection("WOMAN_OWNED_BUSINESS_CD"); 
        session.setAttribute("woman.owned.business.vector", 
			     woman_owned_businessv);

	RefCdDataVector minority_owned_businessv =
	    lsvc.getRefCodesCollection("MINORITY_OWNED_BUSINESS_CD");
        session.setAttribute("minority.owned.business.vector", 
			     minority_owned_businessv);

	RefCdDataVector jwodv = lsvc.getRefCodesCollection("JWOD_CD");
        session.setAttribute("jwod.vector", jwodv);

	RefCdDataVector other_businessv =
	    lsvc.getRefCodesCollection("OTHER_BUSINESS_CD");
        session.setAttribute("other.business.vector", other_businessv);

        return;
    }

    /**
     *  <code>search</code> for the manufacturer(s) 
     according to the criteria specified.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void search(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        ManufMgrSearchForm sForm = (ManufMgrSearchForm) form;
        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        session.setAttribute("Manuf.found.vector", search(request,fieldValue,fieldSearchType));
    }
    
     public static ManufacturerDataVector search(HttpServletRequest request,
            String fieldValue,String fieldSearchType)
             throws Exception {   
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Manufacturer manufBean = factory.getManufacturerAPI();

        // Reset the session variables.
        ManufacturerDataVector dv = new ManufacturerDataVector();
        session.setAttribute("Manuf.found.vector", dv);

        

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        }
        if (fieldSearchType.equals("id")) {
            Integer id = new Integer(fieldValue);
            crit.setSearchId(fieldValue);
        }else {
            crit.setSearchName(fieldValue);
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
	    if (fieldSearchType.equals("nameBegins")) {
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
            }
            else { // nameContains
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
            }
        }
        
        dv = manufBean.getManufacturerByCriteria(crit);

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
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	ManufacturerDataVector manufacturers = 
	    (ManufacturerDataVector)session.getAttribute("Manuf.found.vector");
	if (manufacturers == null) {
	    return;
	}
	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(manufacturers, sortField);
    }

    /**
     *   <code>addManufacturer</code>, clears the MANUF_DETAIL_FORM.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void addManufacturer(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        HttpSession session = request.getSession();
        ManufMgrDetailForm sForm = new ManufMgrDetailForm();
        session.setAttribute("MANUF_DETAIL_FORM", sForm);
    }


    /**
     *  <code>updateManufacturer</code>, update the values pertaining
     to a manufacturer with the data carried in the MANUF_DETAIL_FORM.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors updateManufacturer(HttpServletRequest request,
            ActionForm form)
             throws Exception {
                 
        ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        ManufMgrDetailForm sForm = (ManufMgrDetailForm) form;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (sForm != null) {
            // Verify the form information submitted.
            verifyUpdateValues(appUser, sForm, lUpdateErrors);
        }

        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Manufacturer manufacturerBean = factory.getManufacturerAPI();

        int manufacturerid = 0;
        if (!sForm.getId().equals("")) {
            manufacturerid = Integer.parseInt(sForm.getId());
        }

        // Get the current information for this Manufacturer.
        ManufacturerData dd;
        BusEntityData bed;
        PropertyDataVector props;
        AddressData address;
        EmailData email;
        PhoneData phone, fax;

        if (manufacturerid > 0) {
            dd = manufacturerBean.getManufacturer(manufacturerid);
        }
        else {
            dd = ManufacturerData.createValue();
        }

        if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            dd.setStoreId(Integer.parseInt(sForm.getStoreId()));
        }else{
            dd.setStoreId(appUser.getUserStore().getStoreId());
        }
        
        bed = dd.getBusEntity();
        props = dd.getSpecializations();
        email = dd.getPrimaryEmail();
        fax = dd.getPrimaryFax();
        phone = dd.getPrimaryPhone();
        address = dd.getPrimaryAddress();

        // XXX, values to be determined.
        bed.setWorkflowRoleCd("UNKNOWN");

        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        // Now update with the data from the form.
        bed.setShortDesc(sForm.getName());
        bed.setLongDesc(sForm.getName());
        bed.setBusEntityStatusCd(sForm.getStatusCd());
        bed.setLocaleCd("unk");
        bed.setModBy(cuser);

        email.setShortDesc("MANUFACTURER email");
        email.setModBy(cuser);
        email.setEmailAddress(sForm.getEmailAddress());
        email.setPrimaryInd(true);

        fax.setModBy(cuser);
        fax.setPhoneAreaCode(" ");
        fax.setPhoneCountryCd(" ");
        fax.setPhoneNum(sForm.getFax());
        fax.setPrimaryInd(true);
        fax.setShortDesc("MANUFACTURER fax");

        phone.setModBy(cuser);
        phone.setPhoneAreaCode(" ");
        phone.setPhoneCountryCd(" ");
        phone.setPhoneNum(sForm.getPhone());
        phone.setPrimaryInd(true);
        phone.setShortDesc("MANUFACTURER phone");

        address.setModBy(cuser);
        address.setName1(sForm.getName1());
        address.setName2(sForm.getName2());
        address.setAddress1(sForm.getStreetAddr1());
        address.setAddress2(sForm.getStreetAddr2());
        address.setAddress3(sForm.getStreetAddr3());
        address.setCity(sForm.getCity());
        address.setStateProvinceCd(sForm.getStateOrProv());
        address.setPostalCode(sForm.getPostalCode());
        address.setCountryCd(sForm.getCountry());
        address.setPrimaryInd(true);

        if (props.size() == 0) {
            // Set the specialization properties.
            dd.setSpecializations(createSPFlags(sForm));
        }
        else {
            getSPFlags(sForm, props);
        }

        if (manufacturerid == 0) {
            bed.setAddBy(cuser);
            email.setAddBy(cuser);
            fax.setAddBy(cuser);
            phone.setAddBy(cuser);
            address.setAddBy(cuser);

            dd = manufacturerBean.addManufacturer(dd);
            sForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));
        }
        else {

            // Now update this Manufacturer
            manufacturerBean.updateManufacturer(dd);
        }

        session.setAttribute("MANUF_DETAIL_FORM", sForm);

        return lUpdateErrors;
    }


    /**
     *  The <code>delete</code> method removes the database entries defining
     *  this manufacturer if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Manufacturer
     */
    public static ActionErrors delete(HttpServletRequest request,
				      ActionForm form)
	throws Exception {

        ActionErrors deleteErrors = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Manufacturer manufBean = factory.getManufacturerAPI();
        String strid = request.getParameter("id");
        if (strid == null || strid.length() == 0) {
	    deleteErrors.add("id", new ActionError("error.badRequest", "id"));
	    return deleteErrors;
        }

        Integer id = new Integer(strid);
        ManufacturerData dd = manufBean.getManufacturer(id.intValue());
        if (null != dd) {
	    try {
		manufBean.removeManufacturer(dd);
	    } catch (Exception e) {
                deleteErrors.add("id", 
				 new ActionError("error.deleteFailed",
						 "Manufacturer"));
		return deleteErrors;
	    }
            session.removeAttribute("Manuf.found.vector");
        }
	return deleteErrors;
    }


    /**
     *  Sets the specialization flags for a manufacturer.
     *
     *@param  pForm  , ManufMgrDetailForm
     *@param  pSpecializationProps , the property vector to hold the flags.
     */
    private static void setSPFlags(ManufMgrDetailForm pForm,
            PropertyDataVector pSpecializationProps) {

        pForm.setSpecialization1(true);
        pForm.setSpecialization2(true);
        pForm.setSpecialization3(true);
        pForm.setSpecialization4(true);

        for (int i = 0; i < pSpecializationProps.size(); i++) {
            PropertyData pd = (PropertyData) pSpecializationProps.get(i);
            if (pd.getShortDesc().equals("SP1") &&
                    pd.getValue().equals("0")) {
                pForm.setSpecialization1(false);
            }
            else if (pd.getShortDesc().equals("SP2") &&
                    pd.getValue().equals("0")) {
                pForm.setSpecialization2(false);
            }
            else if (pd.getShortDesc().equals("SP3") &&
                    pd.getValue().equals("0")) {
                pForm.setSpecialization3(false);
            }
            else if (pd.getShortDesc().equals("SP4") &&
                    pd.getValue().equals("0")) {
                pForm.setSpecialization4(false);
            }
        }
    }


    /**
     *  Gets the specialization flags for a manufacturer.
     *
     *@param  pForm  , ManufMgrDetailForm
     *@param  pSpecializationProps , the property vector to hold the flags.
     */
    private static void getSPFlags(ManufMgrDetailForm pForm, PropertyDataVector pSpecializationProps) {
        for (int i = 0; i < pSpecializationProps.size(); i++) {
            PropertyData pd = (PropertyData) pSpecializationProps.get(i);
            if (pd.getShortDesc().equals("SP1")) {
                if (pForm.getSpecialization1()) {
                    pd.setValue("1");
                }
                else {
                    pd.setValue("0");
                }
            }
            else if (pd.getShortDesc().equals("SP2")) {
                if (pForm.getSpecialization2()) {
                    pd.setValue("1");
                }
                else {
                    pd.setValue("0");
                }
            }
            else if (pd.getShortDesc().equals("SP3")) {
                if (pForm.getSpecialization3()) {
                    pd.setValue("1");
                }
                else {
                    pd.setValue("0");
                }
            }
            else if (pd.getShortDesc().equals("SP4")) {
                if (pForm.getSpecialization4()) {
                    pd.setValue("1");
                }
                else {
                    pd.setValue("0");
                }
            }

        }
    }


    /**
     *  Create the specialization flags for a manufacturer.
     *
     *@param  pForm  , ManufMgrDetailForm
     *@return       PropertyDataVector  , the property vector to hold the flags.
     */
    private static PropertyDataVector createSPFlags(ManufMgrDetailForm pForm) {
        PropertyDataVector specz = new PropertyDataVector();

        PropertyData pd1 = PropertyData.createValue();
        pd1.setShortDesc("SP1");
        if (pForm.getSpecialization1()) {
            pd1.setValue("1");
        }
        else {
            pd1.setValue("0");
        }
        specz.add(pd1);

        PropertyData pd2 = PropertyData.createValue();
        pd2.setShortDesc("SP2");
        if (pForm.getSpecialization2()) {
            pd2.setValue("1");
        }
        else {
            pd2.setValue("0");
        }
        specz.add(pd2);

        PropertyData pd3 = PropertyData.createValue();
        pd3.setShortDesc("SP3");
        if (pForm.getSpecialization3()) {
            pd3.setValue("1");
        }
        else {
            pd3.setValue("0");
        }
        specz.add(pd3);

        PropertyData pd4 = PropertyData.createValue();
        pd4.setShortDesc("SP4");
        if (pForm.getSpecialization4()) {
            pd4.setValue("1");
        }
        else {
            pd4.setValue("0");
        }
        specz.add(pd4);

        return specz;
    }


    /**
     *  Description of the Method
     *
     *@param  pDataErrors  Description of Parameter
     *@param  pForm        Description of Parameter
     */
    private static void verifyUpdateValues(CleanwiseUser appUser, ManufMgrDetailForm pForm, ActionErrors pDataErrors) {
        
        if (pForm.getName().length() == 0) {
            pDataErrors.add("name", new ActionError("variable.empty.error",
                    "Name"));
        }

        if (pForm.getStatusCd().length() == 0) {
            pDataErrors.add("statusCd", new ActionError("variable.empty.error",
                    "Status"));
        }

        if (pForm.getStreetAddr1().length() == 0) {
            pDataErrors.add("streetAddr1", new ActionError("variable.empty.error",
                    "Street Address 1"));
        }

        if (pForm.getStateOrProv().length() == 0) {
            pDataErrors.add("stateOrProv", 
			    new ActionError("variable.empty.error",
					    "State/Province"));
        }

        if (pForm.getCity().length() == 0) {
            pDataErrors.add("city", new ActionError("variable.empty.error",
                    "City"));
        }

        if (pForm.getCountry().length() == 0) {
            pDataErrors.add("country", new ActionError("variable.empty.error",
                    "Country"));
        }

        if (pForm.getPostalCode().length() == 0) {
            pDataErrors.add("postalCode", new ActionError("variable.empty.error",
                    "Zip/Postal Code"));
        }
        
        if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            if(pForm.getStoreId().trim().length() == 0){
                pDataErrors.add("storeId", new ActionError("variable.empty.error",
                    "Store Id"));
            }else{
                try{
                    Integer.parseInt(pForm.getStoreId());
                }catch(NumberFormatException e){
                    pDataErrors.add("storeId",
                        new ActionError("error.invalidNumber","Store Id"));
                }
            }
        }
    }

}


