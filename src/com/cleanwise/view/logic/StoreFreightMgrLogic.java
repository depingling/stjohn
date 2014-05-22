/*
 * StoreFreightMgrLogic.java
 *
 * Created on August 1, 2005, 2:43 PM
 */

package com.cleanwise.view.logic;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.FreightTable;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.ContractDescData;
import com.cleanwise.service.api.value.ContractDescDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.FreightTableCriteriaDescData;
import com.cleanwise.service.api.value.FreightTableCriteriaDescDataVector;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.FreightTableDataVector;
import com.cleanwise.service.api.value.FreightTableView;
import com.cleanwise.service.api.value.FreightTableViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.forms.LocateStoreCatalogForm;
import com.cleanwise.view.forms.LocateStoreDistForm;
import com.cleanwise.view.forms.StoreFreightMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 * 
 * @author Ykupershmidt
 */
public class StoreFreightMgrLogic {

	public static void init(HttpServletRequest request, ActionForm form)
			throws Exception {

		search(request, form);
		return;
	}

	/**
	 * <code>search</code>
	 * 
	 * @param request
	 *            a <code>HttpServletRequest</code> value
	 * @param form
	 *            an <code>ActionForm</code> value
	 * @exception Exception
	 *                if an error occurs
	 */
	public static void search(HttpServletRequest request, ActionForm form)
			throws Exception {
		HttpSession session = request.getSession();
		StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		FreightTable freightTableEjb = factory.getFreightTableAPI();

		String searchField = pForm.getFreightSearchField();
		String searchType = pForm.getFreightSearchType();

		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);
		StoreData storeD = appUser.getUserStore();
		int storeId = storeD.getStoreId();

		IdVector catalogIdV = null;
		CatalogDataVector catalogDV = pForm.getFilterCatalogs();
		if (catalogDV != null && !catalogDV.isEmpty()) {
			catalogIdV = new IdVector();
			for (Iterator iter = catalogDV.iterator(); iter.hasNext();) {
				CatalogData cD = (CatalogData) iter.next();
				catalogIdV.add(new Integer(cD.getCatalogId()));
			}
		}
		IdVector distributorIdV = null;
		DistributorDataVector distributorDV = pForm.getDistFilter();
		if (distributorDV != null && !distributorDV.isEmpty()) {
			distributorIdV = new IdVector();
			for (Iterator iter = distributorDV.iterator(); iter.hasNext();) {
				DistributorData cd = (DistributorData) iter.next();
				distributorIdV.add(new Integer(cd.getBusEntity().getBusEntityId()));
			}
		}
		
		FreightTableViewVector freightTables = new FreightTableViewVector();
		if ("id".equals(searchType) && Utility.isSet(searchField)) {
			freightTables = new FreightTableViewVector();
			int tableId = 0;
			try {
				tableId = Integer.parseInt(searchField);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			try {
				FreightTableView freightTableView = freightTableEjb
						.getFreightTableView(tableId, storeId);
				if (freightTableView != null)
					freightTables.add(freightTableView);

			} catch (Exception exc) {
				exc.printStackTrace();
			}
		} else if ("nameContains".equals(searchType)
				&& Utility.isSet(searchField)) {
			freightTables = freightTableEjb.getFreightTableViewByName(
					searchField, FreightTable.CONTAINS_IGNORE_CASE, storeId, catalogIdV, distributorIdV);
		} else if ("nameBegins".equals(searchType)
				&& Utility.isSet(searchField)) {
			freightTables = freightTableEjb.getFreightTableViewByName(
					searchField, FreightTable.BEGINS_WITH_IGNORE_CASE, storeId, catalogIdV, distributorIdV);
		} else {
			freightTables = freightTableEjb.getAllFreightTableViews(storeId, catalogIdV, distributorIdV);
		}
		pForm.setResultList(freightTables);
		pForm.setFreightSearchField(searchField);
		pForm.setFreightSearchType(searchType);
	}

    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void reloadFreightTable(HttpServletRequest request,
			      ActionForm form) throws Exception {
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

        FreightTable freightTableEjb = factory.getFreightTableAPI();

        StoreFreightMgrForm pForm = (StoreFreightMgrForm)form;
        FreightTableData freightTableD = pForm.getDetail();

	int freightTableId = freightTableD.getFreightTableId();

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getStoreId();

        // need know the store type, if non-MLA store, we will display extra info such as
        // distributor
        boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
  		(appUser.getUserStore().getStoreType().getValue());
        pForm.setMlaStore(isaMLAStore);

        freightTableD = freightTableEjb.getFreightTable(freightTableId,storeId);

        int distributorId = freightTableD.getDistributorId();
        DistributorDataVector distFilter = new DistributorDataVector();
        if (0 != distributorId) {
            Distributor distributorEjb = factory.getDistributorAPI();
            DistributorData distD = distributorEjb.getDistributor(distributorId);
            if (null != distD) {
                distFilter.add(distD);
            }
        }
        pForm.setDistFilter(distFilter);

        CatalogDataVector catalogsByFreight = getCatalogsByFreightId(appUser, freightTableId);
        LocateStoreCatalogForm locateCatalogForm = pForm.getLocateStoreCatalogForm();
        if (locateCatalogForm != null) {
            locateCatalogForm.setCatalogsToReturn(catalogsByFreight);
        }
        
        pForm.setCatalogsAssociatedWithFreightTable(catalogsByFreight);

    FreightTableCriteriaDescDataVector criterias =
       freightTableEjb.getAllFreightTableCriteriaDescs(freightTableId);

	  pForm.setDetail(freightTableD);
    pForm.setOrgCriteriaList(criterias);
    pForm.setCriteriaLength(10);

    FreightTableCriteriaDescDataVector newCriterias = new FreightTableCriteriaDescDataVector();
    for(int i = 0; i < criterias.size(); i++) {
        FreightTableCriteriaDescData oldCriD = (FreightTableCriteriaDescData)criterias.get(i);
        FreightTableCriteriaDescData newCriD = FreightTableCriteriaDescData.createValue();
        newCriD.setFreightTableId(oldCriD.getFreightTableId());
        newCriD.setFreightTableCriteriaId(oldCriD.getFreightTableCriteriaId());
        newCriD.setLowerAmount(oldCriD.getLowerAmount());
        newCriD.setHigherAmount(oldCriD.getHigherAmount());
        newCriD.setFreightAmount(oldCriD.getFreightAmount());
        newCriD.setHandlingAmount(oldCriD.getHandlingAmount());
        newCriD.setDiscount(oldCriD.getDiscount());
        newCriD.setAddBy(oldCriD.getAddBy());
        newCriD.setAddDate(oldCriD.getAddDate());
        newCriD.setShortDesc(oldCriD.getShortDesc());
        newCriD.setFreightHandlerId(oldCriD.getFreightHandlerId());
        newCriD.setFreightCriteriaTypeCd(oldCriD.getFreightCriteriaTypeCd());
        newCriD.setRuntimeTypeCd(oldCriD.getRuntimeTypeCd());
        newCriD.setChargeCd(oldCriD.getChargeCd());
        newCriD.setUIOrder(oldCriD.getUIOrder());

        newCriterias.add(newCriD);
    }

    if(criterias.size() < pForm.getCriteriaLength()) {
        for (int i = criterias.size(); i < pForm.getCriteriaLength(); i++) {
            newCriterias.add(FreightTableCriteriaDescData.createValue());
        }
    }
    else {
        pForm.setCriteriaLength(newCriterias.size());
    }
    pForm.setCriteriaList(newCriterias);

  	session.setAttribute("FreightTable.id", ""+freightTableId);

    //initialize the comstants lists for states and contries
	  initConstantList(request);

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
//    HttpSession session = request.getSession();
//	  StoreFreightMgrForm pForm = (StoreFreightMgrForm)form;
//  	FreightTableViewVector freightTables =
//	                   (FreightTableViewVector)pForm.getResultList();
//	  if (freightTables == null) {
//	    return;
//	  }
//
//	  String sortField = request.getParameter("sortField");
//	  DisplayListSort.sort(freightTables, sortField);
  }


    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
  public static void initConstantList(HttpServletRequest request)
	throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

        ListService listServiceEjb = factory.getListServiceAPI();
        if(null == session.getAttribute("FreightTable.status.vector")) {
            RefCdDataVector statusv =
                listServiceEjb.getRefCodesCollection("FREIGHT_TABLE_STATUS_CD");
            session.setAttribute("FreightTable.status.vector", statusv);
        }
        if (null == session.getAttribute("FreightTable.type.vector")) {
            RefCdDataVector typesFromDb = listServiceEjb.getRefCodesCollection("FREIGHT_TABLE_TYPE_CD");
            RefCdDataVector typesToSet = new RefCdDataVector();
            if (typesFromDb != null && typesFromDb.size() > 0) {
                for (int i = 0; i < typesFromDb.size(); ++i) {
                    RefCdData ref = (RefCdData)typesFromDb.get(i);
                    if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(ref.getValue()) ) {
                        continue;
                    }
                    typesToSet.add(ref);
                }
            }
            session.setAttribute("FreightTable.type.vector", typesToSet);
        }
        if (null == session.getAttribute("FreightCriteria.type.vector")) {
            RefCdDataVector typev =
                listServiceEjb.getRefCodesCollection("FREIGHT_CRITERIA_TYPE_CD");
            session.setAttribute("FreightCriteria.type.vector", typev);
        }
        if (null == session.getAttribute("FreightCriteria.runtimeType.vector")) {
            RefCdDataVector typev =
                listServiceEjb.getRefCodesCollection("FREIGHT_CRITERIA_RUNTIME_TYPE");
            session.setAttribute("FreightCriteria.runtimeType.vector", typev);
        }
        if (null == session.getAttribute("FreightCriteria.chargeType.vector")) {
            RefCdDataVector typesFromDb = listServiceEjb.getRefCodesCollection("CHARGE_CD");
            RefCdDataVector typesToSet = new RefCdDataVector();
            if (typesFromDb != null && typesFromDb.size() > 0) {
                for (int i = 0; i < typesFromDb.size(); ++i) {
                    RefCdData ref = (RefCdData)typesFromDb.get(i);
                    if (RefCodeNames.CHARGE_CD.DISCOUNT.equals(ref.getValue()) ) {
                        continue;
                    }
                    typesToSet.add(ref);
                }
            }
            session.setAttribute("FreightCriteria.chargeType.vector", typesToSet);
        }
        if (null == session.getAttribute("freight_handler.vector")) {
            StoreFhMgrLogic.getAllFreightHandlers(request, null);
        }

  }


    /**
     * <code>AddFreightTable</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
  public static void addFreightTable(
		  HttpServletRequest request,
		  ActionForm form)
	throws Exception {
        StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
	HttpSession session = request.getSession();

        // need know the store type, if non-MLA store, we will display extra info such as
        // distributor
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
  		(appUser.getUserStore().getStoreType().getValue());

	    FreightTableData detail = FreightTableData.createValue();
        detail.setFreightTableChargeCd(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
	    pForm.setDetail(detail);
        pForm.setMlaStore(isaMLAStore);
        pForm.setDistFilter(null);
        CatalogDataVector catalogsByFreight = new CatalogDataVector();
        pForm.setCatalogsAssociatedWithFreightTable(catalogsByFreight);

        pForm.setOrgCriteriaList(new FreightTableCriteriaDescDataVector());
        
        LocateStoreDistLogic.setFilterProgrammatically(request, pForm,
                new DistributorDataVector(), 
                "STORE_FREIGHT_FORM", "distFilter");
        LocateStoreCatalogLogic.setFilterProgrammatically(request, pForm,
                catalogsByFreight, "STORE_FREIGHT_FORM", "catalogFilter");


    FreightTableCriteriaDescDataVector newCriterias =
                     new FreightTableCriteriaDescDataVector();
    for (int i = 0; i < pForm.getCriteriaLength(); i++) {
      newCriterias.add(FreightTableCriteriaDescData.createValue());
    }
    pForm.setCriteriaList(newCriterias);
    pForm.setCriteriaLength(10);

	  session.removeAttribute("FreightTable.id");

	  //initialize the comstants lists for states and contries
	  initConstantList(request);
  }


    /**
     * <code>editFreightTable</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param freightTableId a <code>String</code> value
     * @exception Exception if an error occurs
     */
  public static void editFreightTable(
				    HttpServletRequest request,
				    ActionForm form,
				    String freightTableId)
	throws Exception {

        HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

        FreightTable freightTableEjb = factory.getFreightTableAPI();
	if(!Utility.isSet(freightTableId)) {
	    freightTableId = (String)session.getAttribute("FreightTable.id");
	}
	FreightTableData detail =
        freightTableEjb.getFreightTable(Integer.parseInt(freightTableId));
        FreightTableCriteriaDescDataVector criterias =
        freightTableEjb.getAllFreightTableCriteriaDescs(Integer.parseInt(freightTableId));

        // need know the store type, if non-MLA store, we will display extra info such as
        // distributor
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
  		(appUser.getUserStore().getStoreType().getValue());

        // set up the distributor info
        int distributorId = detail.getDistributorId();
        DistributorDataVector distFilter = new DistributorDataVector();
        if (0 != distributorId) {
            Distributor distributorEjb = factory.getDistributorAPI();
            DistributorData distD = distributorEjb.getDistributor(distributorId);
            if (null != distD) {
                distFilter.add(distD);
            }
        }

        StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;

        CatalogDataVector catalogsByFreight = getCatalogsByFreightId(appUser, detail.getFreightTableId());

        LocateStoreDistLogic.setFilterProgrammatically(request, pForm, distFilter, 
            "STORE_FREIGHT_FORM", "distFilter");
        LocateStoreCatalogLogic.setFilterProgrammatically(request, pForm, catalogsByFreight, 
            "STORE_FREIGHT_FORM", "catalogFilter");

        pForm.setDetail(detail);
        pForm.setDistFilter(distFilter);
        pForm.setMlaStore(isaMLAStore);
        pForm.setCatalogsAssociatedWithFreightTable(catalogsByFreight);

        pForm.setOrgCriteriaList(criterias);
        pForm.setCriteriaLength(10);

    FreightTableCriteriaDescDataVector newCriterias = new FreightTableCriteriaDescDataVector();
    for(int i = 0; i < criterias.size(); i++) {
        FreightTableCriteriaDescData oldCriD = (FreightTableCriteriaDescData)criterias.get(i);
        FreightTableCriteriaDescData newCriD = FreightTableCriteriaDescData.createValue();
        newCriD.setFreightTableId(oldCriD.getFreightTableId());
        newCriD.setFreightTableCriteriaId(oldCriD.getFreightTableCriteriaId());
        newCriD.setLowerAmount(oldCriD.getLowerAmount());
        newCriD.setHigherAmount(oldCriD.getHigherAmount());
        newCriD.setFreightAmount(oldCriD.getFreightAmount());
        newCriD.setHandlingAmount(oldCriD.getHandlingAmount());
        newCriD.setDiscount(oldCriD.getDiscount());
        newCriD.setAddBy(oldCriD.getAddBy());
        newCriD.setAddDate(oldCriD.getAddDate());
        newCriD.setShortDesc(oldCriD.getShortDesc());
        newCriD.setFreightHandlerId(oldCriD.getFreightHandlerId());
        newCriD.setFreightCriteriaTypeCd(oldCriD.getFreightCriteriaTypeCd());
        newCriD.setRuntimeTypeCd(oldCriD.getRuntimeTypeCd());
        newCriD.setChargeCd(oldCriD.getChargeCd());
        newCriD.setUIOrder(oldCriD.getUIOrder());
        newCriterias.add(newCriD);
    }

    if(criterias.size() < pForm.getCriteriaLength()) {
        for (int i = criterias.size(); i < pForm.getCriteriaLength(); i++) {
            newCriterias.add(FreightTableCriteriaDescData.createValue());
        }
    }
    else {
        pForm.setCriteriaLength(newCriterias.size());
    }
    pForm.setCriteriaList(newCriterias);

  	session.setAttribute("FreightTable.id", freightTableId);

    //initialize the comstants lists for states and contries
	  initConstantList(request);

  }



    /**
     * <code>AddMoreCriteria</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
  public static void addMoreCriteria(
				   HttpServletRequest request,
				   ActionForm form)
	throws Exception {

	  StoreFreightMgrForm freightTable = (StoreFreightMgrForm) form;
	  HttpSession session = request.getSession();

    freightTable.setCriteriaLength(freightTable.getCriteriaLength() + 5 );
    FreightTableCriteriaDescDataVector newCriterias = freightTable.getCriteriaList();

    for(int i = newCriterias.size(); i < freightTable.getCriteriaLength(); i++) {
      newCriterias.add(FreightTableCriteriaDescData.createValue());
    }

    freightTable.setCriteriaList(newCriterias);

  }



    /**
     * <code>saveFreightTable</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
  public static ActionErrors saveFreightTable(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

    ActionErrors lUpdateErrors = new ActionErrors();

	  StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
    ActionErrors validationErrors = pForm.validate(null,request);
    if(validationErrors!=null && validationErrors.size()>0 ) {
      return validationErrors;
    }

    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getStoreId();

	  APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

  	FreightTable freightTableEjb = factory.getFreightTableAPI();

	  FreightTableData detail = pForm.getDetail();
    detail.setStoreId(storeId);

    DistributorDataVector distFilter = pForm.getDistFilter();
    int distributorId = 0;
    if (null != distFilter && 0 < distFilter.size()) {
        DistributorData distD = (DistributorData)distFilter.get(0);
        if (null != distD) {
            distributorId = distD.getBusEntity().getBusEntityId();
            detail.setDistributorId(distD.getBusEntity().getBusEntityId());
        }
    } else {
        detail.setDistributorId(0);
    }

        if ( 0 != distributorId) {
            boolean isAssociatedCatalog = false;

            /// Search associated catalogs
            CatalogDataVector catalogs = getCatalogsByFreightId(appUser, detail.getFreightTableId());
            if (catalogs != null && catalogs.size() > 0) {
                isAssociatedCatalog = true;
            }

            /// Search freight-tables associated with selected distributor
            FreightTableDataVector distrFreightTables = 
                freightTableEjb.getStoreDistributorFreightTables(storeId, distributorId, 
                    RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);

            /// Delete the current freight-table from array
            if (distrFreightTables != null) {
                for (int i = 0; i < distrFreightTables.size(); ++i) {
                    FreightTableData distrFreightTable = (FreightTableData)distrFreightTables.get(i);
                    if (distrFreightTable.getFreightTableId() == detail.getFreightTableId()) {
                        distrFreightTables.remove(i);
                        break;
                    }
                }
            }

            if (isAssociatedCatalog) {
                if (distrFreightTables != null && distrFreightTables.size() > 0) {
                    for (int i = 0; i < distrFreightTables.size(); ++i) {
                        FreightTableData distrFreightTable = (FreightTableData)distrFreightTables.get(i);
                        CatalogDataVector otherCatalogs = getCatalogsByFreightId(appUser, distrFreightTable.getFreightTableId());
                        if (otherCatalogs == null || (otherCatalogs != null && otherCatalogs.size() == 0)) {
                            String msg = 
                                "This distributor is associated with Freight Table ID [" + distrFreightTable.getFreightTableId() + 
                                "] without a catalog.";
                            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", msg));
                            return lUpdateErrors;
                        }
                    }
                }
            } else {
                if (distrFreightTables != null && distrFreightTables.size() > 0) {
                    FreightTableData distrFreightTable = (FreightTableData)distrFreightTables.get(0);
                    String msg = 
                        "This distributor is already associated with Freight Table ID [" + distrFreightTable.getFreightTableId() + 
                        "]. You must first associate this Freight Table with a catalog.";
                    lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", msg));
                    return lUpdateErrors;
                }
            }
        }

    FreightTableData newDetail = null;
	  if(0 != detail.getFreightTableId()) {
	    newDetail = freightTableEjb.updateFreightTable(detail);
      session.setAttribute("FreightTable.id", String.valueOf(newDetail.getFreightTableId()));
	  }
	  else {
      newDetail = freightTableEjb.addFreightTable(detail);
	    session.setAttribute("FreightTable.id", String.valueOf(newDetail.getFreightTableId()));
            pForm.setDetail(newDetail);
	  }

    lUpdateErrors = updateCriterias(request, pForm, newDetail.getFreightTableId());

    CatalogDataVector selectedCatalogs = null;
    LocateStoreCatalogForm locateCatalogForm = pForm.getLocateStoreCatalogForm();
    if (locateCatalogForm != null) {
        selectedCatalogs = locateCatalogForm.getCatalogsToReturn();
    }
    try {
        updateCatalogsFreightRelationship(newDetail.getFreightTableId(), appUser.getUserName(), selectedCatalogs);
    } catch (Exception ex) {
        lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", ex.getMessage()));
    }

    return lUpdateErrors;
  }


  public static boolean isequals(String newStr, String oldStr)
  {
      if (newStr == null){
          if (!(Utility.isSet(oldStr))) {
              return true;
          }
          else {
              return false;
          }
      }
      else if (newStr.trim().equals(oldStr)) {
          return true;
      }
      else return false;
  }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
  public static ActionErrors updateCriterias
	   (HttpServletRequest request, ActionForm form, int pFreightTableId)
  	throws Exception {

    ActionErrors lErrors = new ActionErrors();

    HttpSession session = request.getSession();

    StoreFreightMgrForm pForm = (StoreFreightMgrForm)form;
    ActionErrors validationErrors = pForm.validate(null,request);
    if(validationErrors!=null && validationErrors.size()>0 ) {
      return validationErrors;
    }

    FreightTableData fcd = pForm.getDetail();

    String cuser = (String) session.getAttribute(Constants.USER_NAME);

    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    FreightTable freightTableEjb = factory.getFreightTableAPI();
    int id = fcd.getFreightTableId();

    FreightTableCriteriaDescDataVector idv = pForm.getCriteriaList();
    FreightTableCriteriaDescDataVector idvorig = pForm.getOrgCriteriaList();

    FreightTableCriteriaDescDataVector existedCriterias = new FreightTableCriteriaDescDataVector();
    FreightTableCriteriaDescDataVector newCriterias = new FreightTableCriteriaDescDataVector();
    for(int i = 0; i < idv.size(); i++ ) {
      FreightTableCriteriaDescData criD = (FreightTableCriteriaDescData)idv.get(i);
      criD.setFreightTableId(id);
      if( 0 != criD.getFreightTableCriteriaId()) {
          existedCriterias.add(criD);
      }
      else {
          newCriterias.add(criD);
      }
    }

    for (int i = 0 ; i < idvorig.size(); i++) {
      FreightTableCriteriaDescData oldo = (FreightTableCriteriaDescData) idvorig.get(i);
      FreightTableCriteriaDescData newo = null;
      for(int j = 0; j < existedCriterias.size(); j++) {
        FreightTableCriteriaDescData existedD = (FreightTableCriteriaDescData) existedCriterias.get(j);
        if(existedD.getFreightTableCriteriaId() == oldo.getFreightTableCriteriaId()) {
          newo = existedD;
          break;
        }
      }


      if ( null != newo ) {
        if (!Utility.isSet(newo.getLowerAmount()) && !Utility.isSet(newo.getHigherAmount()) &&
            !Utility.isSet(newo.getFreightAmount()) && !Utility.isSet(newo.getHandlingAmount()) &&
            !Utility.isSet(newo.getFreightHandlerId()) && !Utility.isSet(newo.getShortDesc()) &&
            !Utility.isSet(newo.getFreightCriteriaTypeCd()) && !Utility.isSet(newo.getRuntimeTypeCd()) ) {
          freightTableEjb.removeFreightTableCriteria(newo);
        }
        else if (!isequals(newo.getLowerAmount(),oldo.getLowerAmount()) ||
                !isequals(newo.getHigherAmount(),oldo.getHigherAmount()) ||
                !isequals(newo.getFreightAmount(),oldo.getFreightAmount()) ||
                !isequals(newo.getHandlingAmount(),oldo.getHandlingAmount()) ||
                !isequals(newo.getDiscount(),oldo.getDiscount()) ||
                !isequals(newo.getFreightHandlerId(),oldo.getFreightHandlerId()) ||
                !isequals(newo.getShortDesc(),oldo.getShortDesc()) ||
                !isequals(newo.getFreightCriteriaTypeCd(),oldo.getFreightCriteriaTypeCd()) ||
                !isequals(newo.getRuntimeTypeCd(),oldo.getRuntimeTypeCd()) ||
                !isequals(newo.getChargeCd(),oldo.getChargeCd()) ||
                !isequals(newo.getUIOrder(),oldo.getUIOrder()) ) {
          freightTableEjb.updateFreightTableCriteria(newo, cuser);
            }
      }
    }

    for (int i = 0; i < newCriterias.size(); i++) {
      FreightTableCriteriaDescData newo = (FreightTableCriteriaDescData) newCriterias.get(i);
      if( Utility.isSet(newo.getLowerAmount()) ||
          Utility.isSet(newo.getHigherAmount()) ||
          Utility.isSet(newo.getFreightAmount()) ||
          Utility.isSet(newo.getHandlingAmount())) {
        freightTableEjb.addFreightTableCriteria(pFreightTableId, newo, cuser);
      }
    }

    /// Reload criteria list.
    /// Update the criteria list in the form by actual values from DB.
    FreightTableCriteriaDescDataVector criteriasFromDb =
        freightTableEjb.getAllFreightTableCriteriaDescs(pFreightTableId);
    pForm.setOrgCriteriaList(criteriasFromDb);
    pForm.setCriteriaLength(10);
    FreightTableCriteriaDescDataVector newCriteriasFromDb = new FreightTableCriteriaDescDataVector();
    if (criteriasFromDb != null) {
        for(int i = 0; i < criteriasFromDb.size(); i++) {
            FreightTableCriteriaDescData oldCriD = (FreightTableCriteriaDescData)criteriasFromDb.get(i);
            FreightTableCriteriaDescData newCriD = FreightTableCriteriaDescData.createValue();
            newCriD.setFreightTableId(oldCriD.getFreightTableId());
            newCriD.setFreightTableCriteriaId(oldCriD.getFreightTableCriteriaId());
            newCriD.setLowerAmount(oldCriD.getLowerAmount());
            newCriD.setHigherAmount(oldCriD.getHigherAmount());
            newCriD.setFreightAmount(oldCriD.getFreightAmount());
            newCriD.setHandlingAmount(oldCriD.getHandlingAmount());
            newCriD.setDiscount(oldCriD.getDiscount());
            newCriD.setAddBy(oldCriD.getAddBy());
            newCriD.setAddDate(oldCriD.getAddDate());
            newCriD.setShortDesc(oldCriD.getShortDesc());
            newCriD.setFreightHandlerId(oldCriD.getFreightHandlerId());
            newCriD.setFreightCriteriaTypeCd(oldCriD.getFreightCriteriaTypeCd());
            newCriD.setRuntimeTypeCd(oldCriD.getRuntimeTypeCd());
            newCriD.setChargeCd(oldCriD.getChargeCd());
            newCriD.setUIOrder(oldCriD.getUIOrder());
            newCriteriasFromDb.add(newCriD);
        }
    }
    if (newCriteriasFromDb.size() < pForm.getCriteriaLength()) {
        for (int i = newCriteriasFromDb.size(); i < pForm.getCriteriaLength(); i++) {
            newCriteriasFromDb.add(FreightTableCriteriaDescData.createValue());
        }
    }
    else {
        pForm.setCriteriaLength(newCriteriasFromDb.size());
    }
    pForm.setCriteriaList(newCriteriasFromDb);

    return lErrors;
  }

  public static ActionErrors deleteCriteria(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

    ActionErrors lUpdateErrors = new ActionErrors();
    StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
    HttpSession session = request.getSession();

    int idx=0;
    try{
        idx = Integer.parseInt(pForm.getLineToDelete());
    }catch(Exception e){
        lUpdateErrors = new ActionErrors();
        lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.badRequest2"));
        return lUpdateErrors;
    }
    if(idx > pForm.getCriteriaList().size()){
            lUpdateErrors = new ActionErrors();
            lUpdateErrors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("error.badRequest2"));
            return lUpdateErrors;
   }
   FreightTableCriteriaDescData desc = (FreightTableCriteriaDescData) pForm.getCriteriaList().get(idx);

   desc.setLowerAmount("");
   desc.setHigherAmount("");
   desc.setFreightAmount("");
   desc.setHandlingAmount("");
   desc.setDiscount("");
   desc.setFreightHandlerId("");
   desc.setFreightCriteriaTypeCd("");
   desc.setRuntimeTypeCd("");
   desc.setChargeCd("");
   desc.setShortDesc("");
   desc.setUIOrder("");

   return lUpdateErrors;
  }

    public static ActionErrors clearStoreCatalogFilter(HttpServletRequest request, 
        ActionForm form) throws Exception {
        if (form instanceof StoreFreightMgrForm) {
            StoreFreightMgrForm freightMgrForm = (StoreFreightMgrForm)form;            
            LocateStoreCatalogForm locateCatalogForm = freightMgrForm.getLocateStoreCatalogForm();
            LocateStoreCatalogLogic.returnNoValue(request, locateCatalogForm);
        }
        return new ActionErrors();
    }
    
    public static ActionErrors clearStoreDistributorFilter(HttpServletRequest request, 
            ActionForm form) throws Exception {
            if (form instanceof StoreFreightMgrForm) {
                StoreFreightMgrForm freightMgrForm = (StoreFreightMgrForm)form;            
                LocateStoreDistForm locateStoreDistForm = freightMgrForm.getLocateStoreDistForm();
                LocateStoreDistLogic.returnNoValue(request, locateStoreDistForm);
            }
            return new ActionErrors();
        }

    private static CatalogDataVector getCatalogsByFreightId(CleanwiseUser appUser, int freightId) throws Exception {
        CatalogDataVector result = new CatalogDataVector();
        APIAccess factory = new APIAccess();
        CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
        Contract contractEjb = factory.getContractAPI();

        ContractDescDataVector contractDescVector = contractEjb.getContractDescsByFreight(freightId);
        if (contractDescVector == null) {
            return result;
        }
        if (contractDescVector.size() == 0) {
            return result;
        }

        IdVector catalogIds = new IdVector();
        for (int i = 0; i < contractDescVector.size(); ++i) {
            ContractDescData contractDesc = (ContractDescData)contractDescVector.get(i);
            catalogIds.add(new Integer(contractDesc.getCatalogId()));
        }

        EntitySearchCriteria criteria = new EntitySearchCriteria();
        ArrayList types = new ArrayList();
        types.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        types.add(RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
        types.add(RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
        types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        ArrayList statuses = new ArrayList();
        statuses.add(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        statuses.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);

        criteria.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        criteria.setSearchTypeCds(types);
        criteria.setSearchStatusCdList(statuses);
        result = catalogInformationEjb.getCatalogsByCritAndIds(criteria, catalogIds);

        return result;
    }

    private static void updateCatalogsFreightRelationship(int freightId, String userName, CatalogDataVector selectedCatalogs) throws Exception {
        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();
        ContractDescDataVector contractsFromDb = contractEjb.getContractDescsByFreight(freightId);

        if (selectedCatalogs != null && selectedCatalogs.size() > 0) {
            for (int i = 0; i < selectedCatalogs.size(); ++i) {
                CatalogData catalog = (CatalogData)selectedCatalogs.get(i);
                boolean notFound = true;
                if (contractsFromDb != null && contractsFromDb.size() > 0) {
                    for (int j = 0; j < contractsFromDb.size(); ++j) {
                        ContractDescData contractDesc = (ContractDescData)contractsFromDb.get(j);
                        if (catalog.getCatalogId() == contractDesc.getCatalogId()) {
                            notFound = false;
                            break;
                        }
                    }
                }
                if (notFound) {
                    contractEjb.addCatalogFreightRelationship(userName, freightId, catalog.getCatalogId());
                }
            }
        }

        if (contractsFromDb != null && contractsFromDb.size() > 0) {
            for (int i = 0; i < contractsFromDb.size(); ++i) {
                ContractDescData contractDesc = (ContractDescData)contractsFromDb.get(i);
                boolean notFound = true;
                if (selectedCatalogs != null && selectedCatalogs.size() > 0) {
                    for (int j = 0; j < selectedCatalogs.size(); ++j) {
                        CatalogData catalog = (CatalogData)selectedCatalogs.get(j);
                        if (catalog.getCatalogId() == contractDesc.getCatalogId()) {
                            notFound = false;
                            break;
                        }
                    }
                }
                if (notFound) {
                    contractEjb.deleteCatalogFreightRelationship(userName, contractDesc.getContractId());
                }
            }
        }
    }

	public static ActionErrors setCatalogFilter(HttpServletRequest request, ActionForm form) {
		
		ActionErrors ae = new ActionErrors();
		StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
		HttpSession session = request.getSession();
		
		CatalogDataVector catalogDV = pForm.getLocateStoreCatalogForm().getCatalogsToReturn();
		pForm.setFilterCatalogs(catalogDV);

		return ae;
	}
	
	public static ActionErrors setDistFilter(HttpServletRequest request, ActionForm form) {
		
		ActionErrors ae = new ActionErrors();
		StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
		HttpSession session = request.getSession();
		
		DistributorDataVector distributorDV = pForm.getLocateStoreDistForm().getDistributorsToReturn();
		pForm.setDistFilter(distributorDV);
		return ae;
	}

	public static ActionErrors clearCatalogFilter(HttpServletRequest request, ActionForm form) 
		throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
        pForm.setFilterCatalogs(null);
        return ae;
	}
	
	public static ActionErrors clearDistributorFilter(HttpServletRequest request, ActionForm form) 
	throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreFreightMgrForm pForm = (StoreFreightMgrForm) form;
    pForm.setDistFilter(null);
    return ae;
}
	
}
