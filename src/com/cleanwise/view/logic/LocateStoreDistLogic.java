/*
 * LocateStoreDistLogic.java
 *
 * Created on May 24, 2005, 4:14 PM
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
import com.cleanwise.view.forms.LocateStoreDistForm;
import com.cleanwise.view.forms.StorePortalForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.view.forms.AnalyticReportForm;


/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreDistLogic {
 public static ActionErrors processAction(HttpServletRequest request,
         StorePortalForm baseForm, String action)
    	throws Exception
    {
    LocateStoreDistForm pForm = null;
    //int myLevel = baseForm.getLevel()+1;
    //baseForm.setLevel(myLevel);

    try {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    if("initSearch".equals(action)) {
      ae = initSearch(request,baseForm);
      return ae;
    }

    pForm = baseForm.getLocateStoreDistForm();
    pForm.setDistributorsToReturn(null);

    if("Cancel".equals(action)) {
      ae = returnNoValue(request,pForm);
    } else if("Search".equals(action)) {
      ae = search(request,pForm, baseForm );
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
    Group groupEjb = factory.getGroupAPI();


    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if(storeD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No store info"));
      return ae;
    }

    LocateStoreDistForm pForm = baseForm.getLocateStoreDistForm();
    if(pForm==null) {
      pForm = new LocateStoreDistForm();
      baseForm.setLocateStoreDistForm(pForm);
    }
    //baseForm.setEmbeddedForm(pForm);

    String searchType = pForm.getSearchType();
    if(searchType==null || searchType.trim().length()==0) searchType = "nameBegins";
    pForm.setSearchType(searchType);

    pForm.setSearchGroupId(0);

    GroupSearchCriteriaView grSearchCrVw = GroupSearchCriteriaView.createValue();
    grSearchCrVw.setGroupType(RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR);
    grSearchCrVw.setGroupName("");
    grSearchCrVw.setStoreId(storeD.getBusEntity().getBusEntityId());
    grSearchCrVw.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
    GroupDataVector groups =
        groupEjb.getGroups(grSearchCrVw,Group.NAME_BEGINS_WITH,Group.ORDER_BY_NAME);
    pForm.setDistGroups(groups);

    DistributorDataVector distributoDV = pForm.getDistributors();
    if(distributoDV==null) {
      pForm.setDistributors(new DistributorDataVector());
    }
    pForm.setDistributorsToReturn(null);
    return ae;
 }


 public static ActionErrors returnNoValue(HttpServletRequest request,
                      LocateStoreDistForm pForm)
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    session.removeAttribute(SessionAttributes.SEARCH_RESULT.STORE_ACCOUNTS);
    return ae;
 }

  public static ActionErrors search(HttpServletRequest request,
            LocateStoreDistForm pForm, StorePortalForm baseForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();


    String fieldValue = pForm.getSearchField();
    String fieldSearchType = pForm.getSearchType();
    String searchCity = pForm.getSearchCity();
    String searchState = pForm.getSearchState();
    String searchCounty = pForm.getSearchCounty();
    String searchPostalCode = pForm.getSearchPostalCode();
    int searchGroupId = pForm.getSearchGroupId();
    DistributorDataVector dv = null;
    if (pForm.getDataSourceType().equals("DW")) {
       dv = searchDW(request, fieldValue, fieldSearchType,
                                        searchCity, searchState, searchCounty,
                                        searchPostalCode, searchGroupId);
    } else {
      dv = search(request, fieldValue, fieldSearchType,
                                         searchCity, searchState, searchCounty,
                                         searchPostalCode, searchGroupId, baseForm,
                                         pForm.getShowInactiveFl());
    }
    if(!pForm.getShowInactiveFl()) {
      for(Iterator iter=dv.iterator(); iter.hasNext();) {
        DistributorData aD = (DistributorData) iter.next();
        if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.
                equals(aD.getBusEntity().getBusEntityStatusCd())) {
          iter.remove();
        }
      }
    }
    pForm.setDistributors(dv);
    return ae;
    }



    /**
     *Returns a distributor data vector based off the specified search criteria
     */
    static public DistributorDataVector search(HttpServletRequest request,
       String fieldValue,String fieldSearchType, String searchCity,String searchState,
        String searchCounty,String searchPostalCode,int searchGroupId, StorePortalForm baseForm,
        boolean showInactive) throws Exception{

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();


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
  //          crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
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

        if(searchCity!=null && searchCity.trim().length()>0) {
          emptyCritFl = false;
          crit.setSearchTerritoryCity(searchCity.trim());
        }
        if(searchState!=null && searchState.trim().length()>0) {
          emptyCritFl = false;
          crit.setSearchTerritoryState(searchState.trim());
        }
        if(searchCounty!=null && searchCounty.trim().length()>0) {
          emptyCritFl = false;
          crit.setSearchTerritoryCounty(searchCounty.trim());
        }
        if(searchPostalCode!=null && searchPostalCode.trim().length()>0) {
          emptyCritFl = false;
          crit.setSearchTerritoryPostalCode(searchPostalCode.trim());
        }
        if(searchGroupId >0) {
            emptyCritFl = false;
            crit.setSearchGroupId(""+searchGroupId);
        }
        crit.setSearchForInactive(showInactive);
        DistributorDataVector dv;
        if(emptyCritFl) {
            dv = distBean.getAllDistributors(Distributor.ORDER_BY_NAME);
        } else {
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            dv = distBean.getDistributorByCriteria(crit);
        }
        return dv;
    }
    /**
    *Returns a distributor data vector based off the specified search criteria
    * in Data Werehose
    */
   static public DistributorDataVector searchDW(HttpServletRequest request,
      String fieldValue,String fieldSearchType, String searchCity,String searchState,
       String searchCounty,String searchPostalCode,int searchGroupId)
   throws Exception{
       // Get a reference to the admin facade
       HttpSession session = request.getSession();
       APIAccess factory = new APIAccess();
       DWOperation dwBean = factory.getDWOperationAPI();

       BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
       crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);
       crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

      // Store FILTER
       IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.DW_STORE_FILTER_NAME);
       crit.setStoreBusEntityIds(selectedStoreIds);

       // Name FILTER
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
       // Other Filters
       if(searchCity!=null && searchCity.trim().length()>0) {
         crit.setSearchTerritoryCity(searchCity.trim());
       }
       if(searchState!=null && searchState.trim().length()>0) {
         crit.setSearchTerritoryState(searchState.trim());
       }
       if(searchCounty!=null && searchCounty.trim().length()>0) {
         crit.setSearchTerritoryCounty(searchCounty.trim());
       }
       if(searchPostalCode!=null && searchPostalCode.trim().length()>0) {
         crit.setSearchTerritoryPostalCode(searchPostalCode.trim());
       }
       if(searchGroupId >0) {
           crit.setSearchGroupId(""+searchGroupId);
       }

       DistributorDataVector dv;
       dv = dwBean.getDistributorByCriteria(crit);

       return dv;
   }


  public static ActionErrors returnSelected(HttpServletRequest request,
            LocateStoreDistForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    int[] selected = pForm.getSelected();
    DistributorDataVector dv = pForm.getDistributors();
    DistributorDataVector retDV = new DistributorDataVector();
    for(Iterator iter=dv.iterator(); iter.hasNext();) {
      DistributorData aD = (DistributorData) iter.next();
      for(int ii=0; ii<selected.length; ii++) {
        if(aD.getBusEntity().getBusEntityId()==selected[ii]) {
          retDV.add(aD);
        }
      }
    }
    pForm.setDistributorsToReturn(retDV);
    return ae;
    }

  public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
      HttpSession session = request.getSession();
      LocateStoreDistForm form = pForm.getLocateStoreDistForm();
      org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),null);
      return new ActionErrors();
  }

  public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
             throws Exception {


    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    LocateStoreDistForm form = pForm.getLocateStoreDistForm();
    String property = null;
    if(pForm.getFormVars() != null && Boolean.TRUE.equals(pForm.getFormVars().get(StorePortalForm.FORM_VAR_ALT_LOCATE))){
        property = form.getPropertyAlt();
    }
    if(!Utility.isSet(property)){
        property = form.getProperty();
    }
    Object o = form.getDistributorsToReturn();
    org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),property,o);
    form.setLocateDistFl(false);
    form.setReturnLocateTypeCd(RefCodeNames.RETURN_LOCATE_TYPE_CD.DISTRIBUTOR);
    return ae;
    }

    public static ActionErrors setFilterProgrammatically(HttpServletRequest request, 
        StorePortalForm baseForm, DistributorDataVector data, String formName, 
        String dataPropertyName) throws Exception {
        LocateStoreDistForm locateForm = baseForm.getLocateStoreDistForm();
        if (locateForm == null) {
            locateForm = new LocateStoreDistForm();
        }
        if (locateForm.getName() == null) {
            locateForm.setName(formName);
        }
        if (locateForm.getProperty() == null) {
            locateForm.setProperty(dataPropertyName);
        }
        if (data == null || data.size() == 0) {
            locateForm.setDistributorsToReturn(null);
        } else {
            locateForm.setDistributorsToReturn(data);
        }
        baseForm.setLocateStoreDistForm(locateForm);
        return new ActionErrors();
    }

}

