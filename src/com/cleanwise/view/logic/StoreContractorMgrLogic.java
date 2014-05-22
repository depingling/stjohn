package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.session.Contractor;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BuildingServicesContractorView;
import com.cleanwise.service.api.value.BuildingServicesContractorViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.view.forms.ContractorDetailForm;
import com.cleanwise.view.forms.StoreContractorMgrSearchForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <code>ContractorMgrLogic</code> implements the logic needed to manipulate
 * Contractor records.
 * 
 */
public class StoreContractorMgrLogic {

	/**
	 * <code>getDetail</code> method provides the data needed to describe a
	 * Contractor.
	 * 
	 * @param request
	 *            a <code>HttpServletRequest</code> value
	 * @param form
	 *            an <code>ActionForm</code> value
	 *@exception Exception
	 *                if an error occurs
	 */

	public static ActionErrors getDetail(HttpServletRequest request,
			ActionForm form) throws Exception {
		ContractorDetailForm sForm = (ContractorDetailForm) form;
		APIAccess factory = new APIAccess();
		Contractor contractorBean = factory.getContractorAPI();
		ActionErrors ae = new ActionErrors();
		String fieldValue = request.getParameter("searchField");
		if (null == fieldValue) {
			fieldValue = "0";
		}
		sForm.reset(null, null);

		Integer id = new Integer(fieldValue);
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);
		BuildingServicesContractorView dd = null;
		try {
			dd = contractorBean.getContractorForStore(id.intValue(), appUser
					.getUserStoreAsIdVector(), true);
		} catch (DataNotFoundException e) {
			ae.add("error", new ActionError("error.simpleGenericError", e
					.getMessage()));
			sForm = new ContractorDetailForm();
			session.setAttribute("CONTRACTOR_DETAIL_FORM", sForm);
			// init(request,sForm);
			return ae;
		}
		if (null != dd) {
			BusEntityData bed = dd.getBusEntityData();
			// Set the form values.
			sForm.setDescription(bed.getLongDesc());
			sForm.setName(bed.getShortDesc());
			sForm.setStatusCd(bed.getBusEntityStatusCd());
			sForm.setId(String.valueOf(bed.getBusEntityId()));
			sForm.setTypeCd(bed.getBusEntityTypeCd());
			// sForm.setStoreId(Integer.toString(dd.getStoreId()));
			PhoneData fax;

			PhoneData phone;

			fax = dd.getFaxNumber();
			if (null != fax) {
				sForm.setFaxPhoneNumber(fax.getPhoneNum());
			}

		}
		return ae;
	}

	public static ActionErrors listAll(String pListReq,
			HttpServletRequest request, ActionForm form) {
		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();
		StoreContractorMgrSearchForm sForm = (StoreContractorMgrSearchForm) form;
		String fieldValue = sForm.getSearchField();
		String fieldSearchType = sForm.getSearchType();
		ArrayList resl = new ArrayList();
		session.setAttribute(pListReq, resl);
		try {
			APIAccess factory = (APIAccess) session
					.getAttribute(Constants.APIACCESS);
			CleanwiseUser appUser = (CleanwiseUser) session
					.getAttribute(Constants.APP_USER);
			IdVector storeIds;
			if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser
					.getUser().getUserTypeCd())) {
				storeIds = appUser.getUserStoreAsIdVector();
			} else {
				storeIds = null;
			}
			// Get all the business entities in question.
			Contractor cBean = factory.getContractorAPI();
			List resList = cBean.listAll(storeIds,
					RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR,
					fieldValue, fieldSearchType, sForm
							.getSearchShowInactiveFl());

			for (int it = 0; it < resList.size() - 1; it++) {
				for (int j = 0; j < (resList.size() - 1) - it; j++) {

					BuildingServicesContractorView B1 = (BuildingServicesContractorView) resList
							.get(j);
					BuildingServicesContractorView B2 = (BuildingServicesContractorView) resList
							.get(j + 1);
					BuildingServicesContractorView temp = null;

					if (B1.getBusEntityData().getShortDesc()
							.compareToIgnoreCase(
									B2.getBusEntityData().getShortDesc()) > 0) {
						temp = B1;
						resList.set(j, B2);
						resList.set(j + 1, temp);
					}
				}
			}

			session.setAttribute(pListReq, resList);

			/*
			 * session.setAttribute (pListReq, rBean.listAll
			 * (storeIds,RefCodeNames
			 * .BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR) );
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ae;
	}

	/**
	 * <code>addContractor</code>, clears the CONTRACTOR_DETAIL_FORM.
	 * 
	 *@param request
	 *            a <code>HttpServletRequest</code> value
	 *@param form
	 *            an <code>ActionForm</code> value
	 *@exception Exception
	 *                if an error occurs
	 */

	public static void addContractor(HttpServletRequest request, ActionForm form)
			throws Exception {

		HttpSession session = request.getSession();
		ContractorDetailForm sForm = new ContractorDetailForm();
		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);
		sForm.setStoreId("" + appUser.getUserStore().getStoreId());
		session.setAttribute("CONTRACTOR_DETAIL_FORM", sForm);
	}

	public static ActionErrors updateContractor(HttpServletRequest request,
			ActionForm form) {
		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();
		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);
		try {
			ContractorDetailForm detForm = (ContractorDetailForm) form;
			Integer storeId;
			CleanwiseUser appUser = (CleanwiseUser) session
					.getAttribute(Constants.APP_USER);
			if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser
					.getUser().getUserTypeCd())) {
				storeId = new Integer(appUser.getUserStore().getStoreId());
			} else {
				try {
					storeId = new Integer(detForm.getStoreId());
				} catch (Exception e) {
					ae.add("storeId", new ActionError("error.invalidNumber",
							"Store"));
					return ae;
				}
			}

			Hashtable vals = new Hashtable();
			vals.put("busEntityTypeCd",
					RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
			vals.put("id", detForm.getId());
			vals.put("bscName", detForm.getName());
			vals.put("bscDesc", detForm.getDescription());
			vals.put("bscOrderFaxNumber", detForm.getFaxPhoneNumber());
			vals.put("storeId", storeId);
			String cuser = (String) session.getAttribute(Constants.USER_NAME);
			vals.put("userName", cuser);

			Contractor cBean = factory.getContractorAPI();
			detForm.setId(cBean.updateInfo(vals));
			// listAll("list.all.bsc", request, form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ae;
	}

	public static ActionErrors listContractorRelationships(String pListReq,
			HttpServletRequest request, ActionForm form) {
		String bscid = request.getParameter("bscid");
		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();
		APIAccess factory = null;
		try {
			factory = new APIAccess();

			Hashtable vals = new Hashtable();
			vals.put("busEntityTypeCd",
					RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
			vals.put("bscid", bscid);

			Contractor rBean = factory.getContractorAPI();
			ArrayList sv = rBean.getSitesFor(vals);
			if (null == sv) {
				sv = new ArrayList();
			}
			session.setAttribute("list.bsc.sites", sv);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ae;

	}

}
