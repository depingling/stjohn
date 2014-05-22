package com.cleanwise.view.logic;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.forms.LocateStoreUserForm;
import com.cleanwise.view.forms.LocateStoreSiteForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.Site;

import java.util.List;
import java.util.Iterator;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.session.DWOperation;
import com.cleanwise.service.api.util.Utility;

/**
 * @author : Alexander Chikin
 * Date: 04.09.2006
 * Time: 15:27:33
 */
public class LocateStoreSiteLogic {

    public static ActionErrors processAction(HttpServletRequest request,
                                             StorePortalForm baseForm, String action)
            throws Exception
    {
        LocateStoreSiteForm pForm = baseForm.getLocateStoreSiteForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session  = request.getSession();
            if("initSearch".equals(action)) {
                ae = initSearch(request,baseForm);
                return ae;
            }
            int myLevel = pForm.getLevel()+1;
            pForm.setSitesToReturn(null);

            if("Cancel".equals(action)) {
                ae = returnNoValue(request,pForm);
            } else if("Search".equals(action)) {
                ae = search(request,pForm);
            } else if ("ServiceProviderSiteLocate".equals(action)) {
                ae = searchUserServiceProviderSites(request, pForm);
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
        int mode=-1;
        StoreData storeD=null;
        if(appUser!=null) {
            mode=1;
            storeD = appUser.getUserStore();
            if(storeD==null) {
                mode=2;
            }
        }
        else
        {
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError","No user info"));
            return ae;

        }

        LocateStoreSiteForm pForm = baseForm.getLocateStoreSiteForm();
        if(pForm==null) {
            pForm = new LocateStoreSiteForm();
            pForm.setLevel(1);
            baseForm.setLocateStoreSiteForm(pForm);
        }

        //baseForm.setEmbeddedForm(pForm);


        String searchType = pForm.getSearchType();
        if(searchType==null || searchType.trim().length()==0) searchType = "nameBegins";
        pForm.setSearchType(searchType);



        SiteViewVector siteDV = pForm.getSites();
        if(siteDV==null) {
            pForm.setSites(new SiteViewVector());
        }


        pForm.setSitesToReturn(null);
        pForm.setSearchStoreId(-1);
        pForm.setMode(mode);
        if(mode>0)
        pForm.setUserId(appUser.getUserId());
        if(mode==1)
            pForm.setStoreId(storeD.getBusEntity().getBusEntityId());

        return ae;
    }

    public static ActionErrors returnNoValue(HttpServletRequest request,
                                             LocateStoreSiteForm pForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        pForm.setSitesToReturn(new SiteViewVector());
        return ae;
    }

    public static ActionErrors search(HttpServletRequest request,
                                      LocateStoreSiteForm pForm)
            throws  Exception {
        ActionErrors ae = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            SiteViewVector sVV=new  SiteViewVector();
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
			int resultLimit = getMaxSitesToReturn(pForm);
            int maxRowsToShow = (sVV.size() > resultLimit) ? resultLimit : sVV.size();
            SiteViewVector sVVR = new SiteViewVector();
            for (int i = 0; i < maxRowsToShow; i++) {
              SiteView sv = (SiteView) sVV.get(i);
              sVVR.add(sv);
            }
           pForm.setSites(sVVR);
        }
        catch(Exception e){

            ae.add(ActionErrors.GLOBAL_ERROR,new ActionMessage("error.simpleGenericError",e.getMessage()));
            return ae;

        }
        return ae;
    }


    /**
     *Returns an user data vector based off the specified search criteria
     */
    public static ActionErrors returnSelected(HttpServletRequest request,
                                              LocateStoreSiteForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int[] selected = pForm.getSelected();
        String stringOfArrayIds=new String();
        SiteViewVector vv = pForm.getSites();
        SiteViewVector retVV = new SiteViewVector();
        int i=0;
        for(Iterator iter=vv.iterator(); iter.hasNext();) {
            SiteView sV= (SiteView) iter.next();
            for(int ii=0; ii<selected.length; ii++) {
                if(sV.getId()==selected[ii]) {
                  retVV.add(sV);
                  if(i!=0) stringOfArrayIds=stringOfArrayIds.concat(", ");
                  stringOfArrayIds=stringOfArrayIds.concat(String.valueOf(sV.getId()));
                  i++;
                }
            }

        }
        pForm.setSelectedIdsAsString(stringOfArrayIds);
        pForm.setSitesToReturn(retVV);
        return ae;
    }



    public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
        HttpSession session = request.getSession();
        LocateStoreSiteForm siteForm = pForm.getLocateStoreSiteForm();
        siteForm.setSelectedIdsAsString("");
        siteForm.setSitesToReturn(null);
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(siteForm.getName()),siteForm.getProperty(),null);
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        LocateStoreSiteForm siteForm = pForm.getLocateStoreSiteForm();
        SiteViewVector sVV = siteForm.getSitesToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(siteForm.getName()),siteForm.getProperty(),sVV);
        siteForm.setLocateUserFl(false);
        pForm.setReturnLocateTypeCd(RefCodeNames.RETURN_LOCATE_TYPE_CD.SITE);
        return ae;
    }

    protected void finalize() throws Throwable {

        super.finalize();
    }


    private static SiteViewVector searchMode_1(HttpServletRequest request, LocateStoreSiteForm pForm) throws RemoteException, APIServiceAccessException, NamingException {

        String fieldValue1 = pForm.getSearchField();
        String fieldSearchRefNum = pForm.getSearchRefNum();
        int siteId=-1;
        int storeId=pForm.getStoreId();
        boolean nameBeginsFl =false;
        boolean refNumNameBeginsFl =false;
        String fieldValue2=pForm.getCity();
        String fieldValue3=pForm.getState();
        String fieldSearchType = pForm.getSearchType();
        String fieldSearchRefNumType = pForm.getSearchRefNumType();
        int userId=pForm.getUserId();
        boolean showInactiveFl=pForm.getShowInactiveFl();
        IdVector accountIdv = pForm.getAccountIds();
		int resultLimit = getMaxSitesToReturn(pForm);
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
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
            nameBeginsFl=true;
        }

        if ("nameBegins".equals(fieldSearchRefNumType))
        {
        	refNumNameBeginsFl=true;
        }

        SiteViewVector vv=siteBean.getUserSites(storeId,userId,siteId,fieldValue1,nameBeginsFl,
        		fieldSearchRefNum,refNumNameBeginsFl,
        		fieldValue2,fieldValue3,accountIdv,showInactiveFl,resultLimit);
        return vv;
    }

    private static SiteViewVector searchMode_2(HttpServletRequest request, LocateStoreSiteForm pForm) throws NamingException, APIServiceAccessException, RemoteException {

        String fieldValue1 = pForm.getSearchField();
        String fieldSearchRefNum = pForm.getSearchRefNum();
        int siteId=-1;
        boolean nameBeginsFl =false;
        boolean refNumNameBeginsFl =false;
        String fieldValue2=pForm.getCity();
        String fieldValue3=pForm.getState();
        String fieldSearchType = pForm.getSearchType();
        String fieldSearchRefNumType = pForm.getSearchRefNumType();
        int userId=pForm.getUserId();
		boolean showInactiveFl=pForm.getShowInactiveFl();
        IdVector accountIdv = pForm.getAccountIds();
        int resultLimit = getMaxSitesToReturn(pForm);
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        if ("id".equals(fieldSearchType)) {
           try{
             siteId = (Utility.isSet(fieldValue1))?Integer.parseInt(fieldValue1):-1;
             if (siteId == 0) return new SiteViewVector();
           } catch (Exception ex){
             return new SiteViewVector();
           }
            fieldValue1=null;
        } else if ("nameBegins".equals(fieldSearchType))
        {
            nameBeginsFl=true;
        }
        if ("nameBegins".equals(fieldSearchRefNumType))
        {
        	refNumNameBeginsFl=true;
        }

        SiteViewVector vv=siteBean.getUserSites(-1,userId,siteId,fieldValue1,nameBeginsFl,
        		fieldSearchRefNum,refNumNameBeginsFl,
        		fieldValue2,fieldValue3,accountIdv,showInactiveFl,resultLimit);

        return vv;
    }
    private static SiteViewVector searchDW(HttpServletRequest request, LocateStoreSiteForm pForm) throws RemoteException, APIServiceAccessException, NamingException {

          String fieldValue1 = pForm.getSearchField();
          int siteId=-1;
          int storeId=pForm.getStoreId();
         // boolean nameBeginsFl =false;
          int nameBeginsFl =QueryRequest.CONTAINS; //false;
          String fieldValue2=pForm.getCity();
          String fieldValue3=pForm.getState();
          String fieldSearchType = pForm.getSearchType();
		  int userId=pForm.getUserId(); // NEED to get customer_dim_id as userId
          boolean showInactiveFl=pForm.getShowInactiveFl();
          IdVector accountIdv = pForm.getAccountIds();
          int resultLimit = getMaxSitesToReturn(pForm);
          APIAccess factory = new APIAccess();
          DWOperation dwEjb = factory.getDWOperationAPI();
          if ("id".equals(fieldSearchType)) {
             try {
               siteId = (Utility.isSet(fieldValue1))?Integer.parseInt(fieldValue1):-1;
               if (siteId == 0) return new SiteViewVector();
             } catch (Exception ex ) {
               return new SiteViewVector();
             }
              fieldValue1=null;
          } else if ("nameBegins".equals(fieldSearchType))
          {
              nameBeginsFl=QueryRequest.BEGINS; //true;
          }

          SiteViewVector vv = null;
          try {
            vv = dwEjb.getUserSites(storeId, userId, new Integer(siteId), fieldValue1,
                                    nameBeginsFl, fieldValue2, fieldValue3,
                                    accountIdv, showInactiveFl, resultLimit);
          }
          catch (Exception ex) {
            ex.getStackTrace();

          }

          return vv;
    }

    private static ActionErrors searchUserServiceProviderSites(HttpServletRequest request, LocateStoreSiteForm pForm) {
        ActionErrors ae = new ActionErrors();

        HttpSession session  = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

        try {
            APIAccess factory = new APIAccess();
            Site siteEJB = factory.getSiteAPI();

            boolean pRefNumNameBeginsFl = true;
            if ("nameContains".equals(pForm.getSearchRefNumType())) {
                pRefNumNameBeginsFl = false;
            }

            SiteViewVector sites = siteEJB.getUserServiceProviderSites(appUser.getUserId(),
                                                                       appUser.getUserStore().getStoreId(),
                                                                       pForm.getSearchField(),
                                                                       pForm.getSearchType(),
                                                                       pForm.getSearchRefNum(),
                                                                       pRefNumNameBeginsFl,
                                                                       pForm.getCity(),
                                                                       pForm.getState(),
                                                                       pForm.getShowInactiveFl(),
                                                                       getMaxSitesToReturn(pForm));
            pForm.setSites(sites);
        } catch(Exception e){

            ae.add(ActionErrors.GLOBAL_ERROR,new ActionMessage("error.simpleGenericError",e.getMessage()));
            return ae;

        }
        return ae;
    }

    public static int getMaxSitesToReturn(LocateStoreSiteForm form) {
        int maxSitesToReturn = Constants.MAX_SITES_TO_RETURN;
        if (form != null) {
            if (form.getMaxSitesToReturn() != null && form.getMaxSitesToReturn().trim().length() > 0) {
                try {
                    maxSitesToReturn = Integer.parseInt(form.getMaxSitesToReturn().trim());
                    if (maxSitesToReturn < 1) {
                        maxSitesToReturn = Constants.MAX_SITES_TO_RETURN;
                    }
                } catch (NumberFormatException ex) {
                    maxSitesToReturn = Constants.MAX_SITES_TO_RETURN;
                }
            }
        }
        return maxSitesToReturn;
    }

}

