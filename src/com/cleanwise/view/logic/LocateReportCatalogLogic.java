package com.cleanwise.view.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.forms.LocateReportCatalogForm;
import com.cleanwise.service.api.util.*;


public class LocateReportCatalogLogic {

	public static ActionErrors processAction(HttpServletRequest request, StorePortalForm baseForm, String action) throws Exception {
		LocateReportCatalogForm form = baseForm.getLocateReportCatalogForm();

		try {
			ActionErrors ae = new ActionErrors();
			if (LocatePropertyNames.INIT_SEARCH_ACTION.equals(action)) {
				ae = initSearch(request, baseForm);
				return ae;
			}

			if (LocatePropertyNames.CANCEL_ACTION.equals(action)) {
				ae = returnNoValue(request, form);
			}
			else if (LocatePropertyNames.SEARCH_ACTION.equals(action)) {
				ae = search(request, form);
			}
			else if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action)) {
				ae = returnSelected(request, form);
			}
			return ae;
		}
		finally {
			if (form != null) {
				form.reset(null, null);
			}
		}
	}

	public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session  = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
		StoreData storeD = appUser.getUserStore();
		if(storeD == null) {
			ae.add("error",new ActionError("error.simpleGenericError","No store info"));
			return ae;
		}

		LocateReportCatalogForm form = baseForm.getLocateReportCatalogForm();
		if (form == null) {
			form = new LocateReportCatalogForm();
			form.setLevel(1);
			baseForm.setLocateReportCatalogForm(form);
		}

		form.setSearchField("");
		form.setSearchType("catalogNameStarts");
		form.setResultList(null);
		return ae;
	}

	public static ActionErrors returnNoValue(HttpServletRequest request, LocateReportCatalogForm sForm) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute(SessionAttributes.SEARCH_RESULT.REPORT_CATALOGS);
		return new ActionErrors();
	}

	public static ActionErrors search(HttpServletRequest request, LocateReportCatalogForm sForm) throws Exception {
		HttpSession session = request.getSession();
		APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
		if (null == factory) {
			throw new Exception("Without APIAccess.");
		}
        ActionErrors ae = new ActionErrors();
		CatalogInformation catalogInformationEjb   = factory.getCatalogInformationAPI();

		EntitySearchCriteria crit = new EntitySearchCriteria();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

		String searchField = sForm.getSearchField();
		String searchType = sForm.getSearchType();
		CatalogDataVector catalogList = new CatalogDataVector();
		if(searchField != null && searchField.trim().length()>0 &&searchType != null) {
			if(searchType.equalsIgnoreCase("catalogId")) {
			    try {
    				int catalogId = Integer.parseInt(searchField);
                } catch (NumberFormatException e) {
                        ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber", searchField));
                        return ae;
                }
				crit.setSearchId(searchField);
			} else {
				int matchType=
					(searchType.equalsIgnoreCase("catalogNameContains"))?
							EntitySearchCriteria.NAME_CONTAINS:
								EntitySearchCriteria.NAME_STARTS_WITH;

				crit.setSearchNameType(matchType);
				crit.setSearchName(searchField);
			}
		}
		crit.setStoreBusEntityIds(Utility.toIdVector(appUser.getUserStore().getStoreId()));
		crit.setSearchStatusCdList(Utility.getAsList(RefCodeNames.CATALOG_STATUS_CD.ACTIVE));
		crit.setSearchTypeCds(Utility.getAsList(RefCodeNames.CATALOG_TYPE_CD.SHOPPING));
		catalogList = catalogInformationEjb.getCatalogsByCrit(crit);
		sForm.setResultList(catalogList);
		return ae;
	}


	public static ActionErrors returnSelected(HttpServletRequest request, LocateReportCatalogForm form) throws Exception {
		int[] selected = form.getSelected();
		List dv = form.getResultList();

		CatalogDataVector retDV = new CatalogDataVector();
                            
        if (dv != null) {
            for (Iterator iter = dv.iterator(); iter.hasNext();) {
                CatalogData aD = (CatalogData) iter.next();
                for (int i = 0; i < selected.length; i++) {
                    if (aD.getCatalogId() == selected[i]) {
                        retDV.add(aD);
                    }
                }
            }
        }
        form.setResultList(retDV);

		clearMultiCatalogFilterInSession(request, getFilterName(form));
		putMultiCatalogFilter2Session(request, retDV, getFilterName(form));

		return new ActionErrors();
	}

	public static ActionErrors clearFilter(HttpServletRequest request, StorePortalForm form) throws Exception {
		HttpSession session = request.getSession();
		LocateReportCatalogForm catalogForm = form.getLocateReportCatalogForm();
		if( catalogForm !=null ){
			org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(catalogForm.getName()), catalogForm.getProperty(), null);
		}

		clearMultiCatalogFilterInSession(request, getFilterName(catalogForm));
		return new ActionErrors();
	}

	public static ActionErrors setFilter(HttpServletRequest request, StorePortalForm form) throws Exception {

		HttpSession session = request.getSession();
		LocateReportCatalogForm catalogForm = form.getLocateReportCatalogForm();
		CatalogDataVector catalogDV = catalogForm.getResultList();
		org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(catalogForm.getName()), catalogForm.getProperty(), catalogDV);
		catalogForm.setLocateCatalogFl(false);
		form.setReturnLocateTypeCd(RefCodeNames.RETURN_LOCATE_TYPE_CD.CATALOG);
		return new ActionErrors();
	}


	public static HashMap getReportSearchFilter(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
			session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
		}
		HashMap hashMapFilter = (HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");
		return hashMapFilter;
	}

	public static ActionErrors clearMultiCatalogFilterInSession(HttpServletRequest request, String filterName) {
		HashMap hashMapFilter = getReportSearchFilter(request);
		if (hashMapFilter != null) {
			hashMapFilter.put(filterName, new LinkedList());
		}
		return new ActionErrors();
	}


	public static ActionErrors putMultiCatalogFilter2Session(HttpServletRequest request, CatalogDataVector catalogs, String filterName) {

		HashMap hashMapFilter = null;
		boolean canPutCatalogs2Session = false;
		ActionErrors errors = new ActionErrors();

		/// Checking of the presence of the found store in the session attribute
		hashMapFilter = getReportSearchFilter(request);
		if (hashMapFilter == null) {
			// to-do 'error'
		}
		else {
			canPutCatalogs2Session = true;
		}

		/// Putting the found catalogs into proper session attribute
		if (canPutCatalogs2Session) {
			List catalogIds = new LinkedList();
			if (catalogs != null && catalogs.size() > 0) {
				for (int i = 0; i < catalogs.size(); ++i) {
					CatalogData catalog = (CatalogData)catalogs.get(i);
					catalogIds.add(new Integer(catalog.getCatalogId()));
				}
			}
			hashMapFilter.put(filterName, catalogIds);
		}

		return errors;
	}

	private static String getFilterName (LocateReportCatalogForm pForm){
		return Constants.CATALOG_FILTER_NAME;
	}
}
