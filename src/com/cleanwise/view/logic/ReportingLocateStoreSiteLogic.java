package com.cleanwise.view.logic;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import com.cleanwise.view.forms.ReportingLocateStoreSiteForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Site;
import java.util.Iterator;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.session.DWOperation;
import com.cleanwise.service.api.util.Utility;

public class ReportingLocateStoreSiteLogic {

   public static ActionErrors processAction(HttpServletRequest request,
    		StorePortalForm baseForm, String action)
            throws Exception {
        ReportingLocateStoreSiteForm pForm = baseForm
                .getReportingLocateStoreSiteForm();
        try {
            ActionErrors ae = new ActionErrors();
            if ("initSearch".equals(action)) {
                ae = initSearch(request, baseForm);
                return ae;
            }
            pForm.setSitesToReturn(null);
            if ("Cancel".equals(action)) {
                ae = returnNoValue(request, pForm);
            } else if ("Search".equals(action)) {
                ae = search(request, pForm);
            } else if ("Return Selected".equals(action)) {
                ae = returnSelected(request, pForm);
            }
            return ae;
        } finally {
            if (pForm != null)
                pForm.reset(null, null);
        }
    }

    public static ActionErrors initSearch(HttpServletRequest request,
    		StorePortalForm baseForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser appUser = (CleanwiseUser) session
                .getAttribute(Constants.APP_USER);
        int mode = -1;
        StoreData storeD = null;
        if (appUser != null) {
            mode = 1;
            storeD = appUser.getUserStore();
            if (storeD == null) {
                mode = 2;
            }
        } else {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "error.simpleGenericError", "No user info"));
            return ae;

        }
        ReportingLocateStoreSiteForm pForm = baseForm
                .getReportingLocateStoreSiteForm();
        if (pForm == null) {
            pForm = new ReportingLocateStoreSiteForm();
            pForm.setLevel(1);
            baseForm.setReportingLocateStoreSiteForm(pForm);
        }
        String searchType = pForm.getSearchType();
        if (searchType == null || searchType.trim().length() == 0) {
            searchType = "nameBegins";
        }
        pForm.setSearchType(searchType);
        SiteViewVector siteDV = pForm.getSites();
        if (siteDV == null) {
            pForm.setSites(new SiteViewVector());
        }
        pForm.setSitesToReturn(null);
        pForm.setSearchStoreId(-1);
        pForm.setMode(mode);
        if (mode > 0) {
            pForm.setUserId(appUser.getUserId());
        }
        if (mode == 1) {
            pForm.setStoreId(storeD.getBusEntity().getBusEntityId());
        }
        return ae;
    }

    public static ActionErrors returnNoValue(HttpServletRequest request,
            ReportingLocateStoreSiteForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        pForm.setSitesToReturn(new SiteViewVector());
        return ae;
    }

    public static ActionErrors search(HttpServletRequest request,
            ReportingLocateStoreSiteForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        try {
            SiteViewVector sVV = new SiteViewVector();
            if (pForm.getDataSourceType().equals("DW")){
              sVV=searchDW(request,pForm);
            } else {
              switch (pForm.getMode()) {
                case 1:
                  sVV = searchMode_1(request, pForm);
                  break;
                case 2:
                  sVV = searchMode_2(request, pForm);
                  break;
                default:
                  return ae;
              }
            }
            pForm.setSites(sVV);
        } catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionMessage(
                    "error.simpleGenericError", e.getMessage()));
            return ae;
        }
        return ae;
    }

    /**
     * Returns an user data vector based off the specified search criteria
     */
    public static ActionErrors returnSelected(HttpServletRequest request,
            ReportingLocateStoreSiteForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        int[] selected = pForm.getSelected();
        String stringOfArrayIds = new String();
        SiteViewVector vv = pForm.getSites();
        SiteViewVector retVV = new SiteViewVector();
        int i = 0;
        for (Iterator iter = vv.iterator(); iter.hasNext();) {
            SiteView sV = (SiteView) iter.next();
            for (int ii = 0; ii < selected.length; ii++) {
                if (sV.getId() == selected[ii]) {
                    retVV.add(sV);
                    if (i != 0)
                        stringOfArrayIds = stringOfArrayIds.concat(", ");
                        stringOfArrayIds = stringOfArrayIds.concat(String.valueOf(sV.getId()));
                    i++;
                }
            }
        }
        pForm.setSelectedIdsAsString(stringOfArrayIds);
        pForm.setSitesToReturn(retVV);
 /** @todo /
//        clearMultiSiteFilterInSession(request);
//        putMultiSiteFilter2Session(request, retVV);
*/
        return ae;
    }

    public static ActionErrors clearFilter(HttpServletRequest request,
    		StorePortalForm pForm) throws Exception {
        HttpSession session = request.getSession();
        System.out
                .println("============================================================");
        System.out
                .println("                ClearFilter   Reporting LOCATE SITE                 ");
        System.out
                .println("============================================================");
        ReportingLocateStoreSiteForm siteForm = pForm.getReportingLocateStoreSiteForm();
        if (siteForm != null) {
          siteForm.setSelectedIdsAsString("");
          org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(siteForm.getName()), siteForm.getProperty(), null);
        }
        /** @todo / clearMultiSiteFilterInSession(request);    */
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,
    		StorePortalForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        ReportingLocateStoreSiteForm siteForm = pForm
                .getReportingLocateStoreSiteForm();
        SiteViewVector sVV = siteForm.getSitesToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session
                .getAttribute(siteForm.getName()), siteForm.getProperty(), sVV);
        siteForm.setLocateUserFl(false);

        // pForm.setReturnLocateTypeCd(RefCodeNames.RETURN_LOCATE_TYPE_CD.SITE);
        return ae;
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    private static SiteViewVector searchMode_1(HttpServletRequest request,
            ReportingLocateStoreSiteForm pForm) throws Exception,
            APIServiceAccessException, NamingException {
        String fieldValue1 = pForm.getSearchField();
        int siteId = -1;
        int storeId = pForm.getStoreId();
        boolean nameBeginsFl = false;
        String fieldValue2 = pForm.getCity();
        String fieldValue3 = pForm.getState();
        String fieldSearchType = pForm.getSearchType();

        int userId = pForm.getUserId();
        boolean showInactiveFl = pForm.getShowInactiveFl();
        IdVector accountIdv = pForm.getAccountIds();

        // use selected storeId and account Ids for site search

        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.STORE_FILTER_NAME);
        IdVector selectedAccountIds = AnalyticReportLogic.getAccountSelectedFilter(request, Constants.ACCOUNT_FILTER_NAME);

        storeId = (selectedStoreIds != null && selectedStoreIds.size() >0 )? ((Integer)selectedStoreIds.get(0)).intValue() : storeId;
        accountIdv = (selectedAccountIds != null && selectedAccountIds.size() >0 )? selectedAccountIds : accountIdv;

        int resultLimit = Constants.MAX_SITES_TO_RETURN;
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        if ("id".equals(fieldSearchType)) {
            try {
              siteId = (Utility.isSet(fieldValue1))?Integer.parseInt(fieldValue1):-1;
              if (siteId == 0) return new SiteViewVector();
            } catch (Exception ex){
              return new SiteViewVector();
            }
            fieldValue1 = null;
        } else if ("nameBegins".equals(fieldSearchType)) {
            nameBeginsFl = true;
        }
        SiteViewVector vv = siteBean.getUserSites(storeId, userId, siteId,
                fieldValue1, nameBeginsFl, null, false,fieldValue2, fieldValue3,
                accountIdv, showInactiveFl, resultLimit);
        return vv;
    }

    private static SiteViewVector searchDW(HttpServletRequest request, ReportingLocateStoreSiteForm pForm) throws Exception, APIServiceAccessException, NamingException {

      String fieldValue1 = pForm.getSearchField();
      int siteId=-1;
      int storeId=-1;
      int userId=-1;
      IdVector accountIdv = null;//pForm.getAccountIds();

     // boolean nameBeginsFl =false;
      int nameBeginsFl =QueryRequest.CONTAINS; //false;
      String fieldValue2=pForm.getCity();
      String fieldValue3=pForm.getState();
      String fieldSearchType = pForm.getSearchType();

      boolean showInactiveFl=pForm.getShowInactiveFl();
      SiteViewVector vv = null;
      try {
        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.DW_STORE_FILTER_NAME);

        IdVector selectedUserIds = AnalyticReportLogic.getUserSelectedFilter(request, Constants.DW_USER_FILTER_NAME);

        IdVector selectedAccountIds = AnalyticReportLogic.getAccountSelectedFilter(request, Constants.DW_ACCOUNT_FILTER_NAME);

        storeId = (selectedStoreIds != null && selectedStoreIds.size() >0 )? ((Integer)selectedStoreIds.get(0)).intValue() : storeId;
        userId = (selectedUserIds != null && selectedUserIds.size() >0 )? ((Integer)selectedUserIds.get(0)).intValue() : userId;
        accountIdv = (selectedAccountIds != null && selectedAccountIds.size() >0 )? selectedAccountIds : accountIdv;

        int resultLimit=Constants.MAX_SITES_TO_RETURN;
        APIAccess factory = new APIAccess();
        DWOperation dwEjb = factory.getDWOperationAPI();

        if ("id".equals(fieldSearchType)) {
          try {
            siteId = (Utility.isSet(fieldValue1))?Integer.parseInt(fieldValue1):-1;
            if (siteId == 0) return new SiteViewVector();
          } catch (Exception ex){
            return new SiteViewVector();
          }
          fieldValue1=null;
        } else if ("nameBegins".equals(fieldSearchType))
        {
          nameBeginsFl=QueryRequest.BEGINS; //true;
        }

        vv = dwEjb.getUserSites(storeId, userId, new Integer(siteId), fieldValue1,
                                nameBeginsFl, fieldValue2, fieldValue3,
                                accountIdv, showInactiveFl, resultLimit);
       }
       catch (Exception ex) {
        ex.getStackTrace();
      }

      return vv;
}

/*
    private static SiteViewVector searchDW(HttpServletRequest request,
            ReportingLocateStoreSiteForm pForm) throws Exception,
            APIServiceAccessException, NamingException {
        String fieldValue1 = pForm.getSearchField();
        int siteId = -1;
        int storeId = pForm.getStoreId();
        boolean nameBeginsFl = false;
        String fieldValue2 = pForm.getCity();
        String fieldValue3 = pForm.getState();
        String fieldSearchType = pForm.getSearchType();

        int userId = pForm.getUserId();
        boolean showInactiveFl = pForm.getShowInactiveFl();
        IdVector accountIdv = pForm.getAccountIds();

        // use selected storeId and account Ids for site search

        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request);
        IdVector selectedAccountIds = AnalyticReportLogic.getAccountSelectedFilter(request);

        storeId = (selectedStoreIds != null && selectedStoreIds.size() >0 )? ((Integer)selectedStoreIds.get(0)).intValue() : storeId;
        accountIdv = (selectedAccountIds != null && selectedAccountIds.size() >0 )? selectedAccountIds : accountIdv;

        //

        int resultLimit = Constants.MAX_SITES_TO_RETURN;
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        if ("id".equals(fieldSearchType)) {
            siteId = Integer.parseInt(fieldValue1);
            fieldValue1 = null;
        } else if ("nameBegins".equals(fieldSearchType)) {
            nameBeginsFl = true;
        }
        SiteViewVector vv = siteBean.getUserSites(storeId, userId, siteId,
                fieldValue1, nameBeginsFl, null,false,fieldValue2, fieldValue3,
                accountIdv, showInactiveFl, resultLimit);
        return vv;
    }
*/

    private static SiteViewVector searchMode_2(HttpServletRequest request,
            ReportingLocateStoreSiteForm pForm) throws Exception,
            APIServiceAccessException, RemoteException {
        String fieldValue1 = pForm.getSearchField();
        int siteId = -1;
        boolean nameBeginsFl = false;
        String fieldValue2 = pForm.getCity();
        String fieldValue3 = pForm.getState();
        String fieldSearchType = pForm.getSearchType();
        int userId = pForm.getUserId();
        boolean showInactiveFl = pForm.getShowInactiveFl();
        IdVector accountIdv = pForm.getAccountIds();
        int resultLimit = Constants.MAX_SITES_TO_RETURN;
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        if ("id".equals(fieldSearchType)) {
          try {
            siteId = (Utility.isSet(fieldValue1))?Integer.parseInt(fieldValue1):-1;
            if (siteId == 0) return new SiteViewVector();
          } catch (Exception ex){
            return new SiteViewVector();
          }
            fieldValue1 = null;
        } else if ("nameBegins".equals(fieldSearchType)) {
            nameBeginsFl = true;
        }
        SiteViewVector vv = siteBean.getUserSites(-1, userId, siteId,
                fieldValue1, nameBeginsFl, null, false, fieldValue2, fieldValue3,
                accountIdv, showInactiveFl, resultLimit);
        return vv;
    }


}
