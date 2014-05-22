/*
 * LocateStoreManufLogic.java
 *
 * Created on February 21, 2006, 12:16 PM
 */
package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import com.cleanwise.view.forms.LocateStoreManufForm;
import com.cleanwise.view.forms.StorePortalForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.view.forms.AnalyticReportForm;


/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreManufLogic {
 public static ActionErrors processAction(HttpServletRequest request,
         StorePortalForm baseForm, String action)
    	throws Exception
    {
    LocateStoreManufForm pForm = null;

    try {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    if("initSearch".equals(action)) {
      ae = initSearch(request,baseForm);
      return ae;
    }

    pForm = baseForm.getLocateStoreManufForm();
    pForm.setManufacturersToReturn(null);

    if("Cancel".equals(action)) {
      ae = returnNoValue(request,pForm);
    } else if("Search".equals(action)) {
      ae = search(request,pForm, baseForm);
    } else if("Return Selected".equals(action)) {
      ae = returnSelected(request,pForm);
    }

    return ae;
    }finally {
      if(pForm!=null)  pForm.reset(null, null);
    }
 }



 public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();

    APIAccess factory = new APIAccess();

    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if(storeD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No store info"));
      return ae;
    }
    LocateStoreManufForm pForm = baseForm.getLocateStoreManufForm();
    if(pForm==null) {
      pForm = new LocateStoreManufForm();
      pForm.setLevel(1);
      baseForm.setLocateStoreManufForm(pForm);
    }
    //baseForm.setEmbeddedForm(pForm);

    String searchType = pForm.getSearchType();
    if(searchType==null || searchType.trim().length()==0) searchType = "nameBegins";
    pForm.setSearchType(searchType);


    ManufacturerDataVector manufacturerDV = pForm.getManufacturers();
    if(manufacturerDV==null) {
      pForm.setManufacturers(new ManufacturerDataVector());
    }
    pForm.setManufacturersToReturn(null);
    return ae;
 }


 public static ActionErrors returnNoValue(HttpServletRequest request,
                      LocateStoreManufForm pForm)
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    pForm.setManufacturersToReturn(new ManufacturerDataVector());
    return ae;
 }

  public static ActionErrors search(HttpServletRequest request,
            LocateStoreManufForm pForm, StorePortalForm baseForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();


    String fieldValue = pForm.getSearchField();
    String fieldSearchType = pForm.getSearchType();
    ManufacturerDataVector dv = null;
    if (pForm.getDataSourceType().equals("DW")) {
       dv = searchDW(request, fieldValue, fieldSearchType);
    } else {
      dv = search(request, fieldValue, fieldSearchType, baseForm);
    }
    if(!pForm.getShowInactiveFl()) {
      for(Iterator iter=dv.iterator(); iter.hasNext();) {
        ManufacturerData aD = (ManufacturerData) iter.next();
        if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.
                equals(aD.getBusEntity().getBusEntityStatusCd())) {
          iter.remove();
        }
      }
    }
    pForm.setManufacturers(dv);
    return ae;
    }


    /**
     *Returns a manufacturer data vector based off the specified search criteria
     */
    static public ManufacturerDataVector search(HttpServletRequest request,
       String fieldValue,String fieldSearchType, StorePortalForm baseForm)
    throws Exception{

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Manufacturer manufBean = factory.getManufacturerAPI();

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setSearchForInactive(baseForm.getLocateStoreManufForm().getShowInactiveFl());

        boolean emptyCritFl = true;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            emptyCritFl = false;
            IdVector storeIdV = appUser.getUserStoreAsIdVector();
            if (baseForm instanceof AnalyticReportForm) {
              IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.STORE_FILTER_NAME);
              if (selectedStoreIds != null && selectedStoreIds.size() > 0) {
                storeIdV = selectedStoreIds;
              }
            }
            crit.setStoreBusEntityIds(storeIdV);
        }

        if(fieldValue!=null && fieldValue.trim().length()>0) {
          emptyCritFl = false;
          fieldValue = fieldValue.trim();
          if ("id".equals(fieldSearchType)) {
              crit.setSearchId(fieldValue);
          }
          else {
	    if ("nameBegins".equals(fieldSearchType)) {
                crit.setSearchName(fieldValue);
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
            } else {
                crit.setSearchName(fieldValue);
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
            }
          }
        }

        ManufacturerDataVector manufDV;
        if(emptyCritFl) {
            manufDV = manufBean.getAllManufacturers(Manufacturer.ORDER_BY_NAME);
        } else {
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            manufDV = manufBean.getManufacturerByCriteria(crit);
        }
        return manufDV;
    }

    /**
     *Returns a manufacturer data vector based off the specified search criteria
     */
    static public ManufacturerDataVector searchDW(HttpServletRequest request,
       String fieldValue,String fieldSearchType)
    throws Exception{

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        DWOperation dwBean = factory.getDWOperationAPI();

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

        // Store FILTER
        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.DW_STORE_FILTER_NAME);
        crit.setStoreBusEntityIds(selectedStoreIds);

        // Name Filter
        if(fieldValue!=null && fieldValue.trim().length()>0) {
          fieldValue = fieldValue.trim();
          if ("id".equals(fieldSearchType)) {
              crit.setSearchId(fieldValue);
          }
          else {
            if ("nameBegins".equals(fieldSearchType)) {
                crit.setSearchName(fieldValue);
                crit.setSearchNameType(QueryRequest.BEGINS);
            } else {
                crit.setSearchName(fieldValue);
                crit.setSearchNameType(QueryRequest.CONTAINS);
            }
          }
        }

        ManufacturerDataVector manufDV;
        manufDV = dwBean.getManufacturerByCriteria(crit);
        return manufDV;
    }

  public static ActionErrors returnSelected(HttpServletRequest request,
            LocateStoreManufForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    int[] selected = pForm.getSelected();
    ManufacturerDataVector dv = pForm.getManufacturers();
    ManufacturerDataVector retDV = new ManufacturerDataVector();
    for(Iterator iter=dv.iterator(); iter.hasNext();) {
      ManufacturerData mD = (ManufacturerData) iter.next();
      for(int ii=0; ii<selected.length; ii++) {
        if(mD.getBusEntity().getBusEntityId()==selected[ii]) {
          retDV.add(mD);
        }
      }
    }
    pForm.setManufacturersToReturn(retDV);
    return ae;
    }

  public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm)
  throws Exception{
      HttpSession session = request.getSession();
      LocateStoreManufForm form = pForm.getLocateStoreManufForm();
      org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),null);
      return new ActionErrors();
  }

  public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
             throws Exception {


    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    LocateStoreManufForm lsmForm = pForm.getLocateStoreManufForm();
    String property = null;
    if(pForm.getFormVars() != null &&
       Boolean.TRUE.equals(pForm.getFormVars().get(StorePortalForm.FORM_VAR_ALT_LOCATE))){
      property = lsmForm.getPropertyAlt();
    }
    if(!Utility.isSet(property)){
        property = lsmForm.getProperty();
    }
    Object o = lsmForm.getManufacturersToReturn();
    org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(lsmForm.getName()),property,o);
    lsmForm.setLocateManufFl(false);
    return ae;
    }

}


