package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.LocateStoreUserForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import com.cleanwise.service.api.util.Utility;

/**
 * 
 * @author Alexander Chikin
 * Date: 17.08.2006
 * Time: 14:50:29
 *
 */
public class LocateStoreUserLogic {

    public static ActionErrors processAction(HttpServletRequest request,
                                             StorePortalForm baseForm, String action)
            throws Exception
    {
        LocateStoreUserForm pForm = baseForm.getLocateStoreUserForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session  = request.getSession();
            if("initSearch".equals(action)) {
                ae = initSearch(request,baseForm);
                return ae;
            }
            int myLevel = pForm.getLevel()+1;
            //pForm.setLevel(myLevel);
            //pForm = (LocateStoreAccountForm)
            //  session.getAttribute(SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM);
            pForm.setUsersToReturn(null);

            if("Cancel".equals(action)) {
                ae = returnNoValue(request,pForm);
            } else if("Search".equals(action)) {
                ae = search(request,pForm);
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
        ListService lsvc = factory.getListServiceAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        if(storeD==null) {
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }

        LocateStoreUserForm pForm = baseForm.getLocateStoreUserForm();
        if(pForm==null) {
            pForm = new LocateStoreUserForm();
            pForm.setLevel(1);
            baseForm.setLocateStoreUserForm(pForm);
        }

        //baseForm.setEmbeddedForm(pForm);


        String searchType = pForm.getSearchType();
        if(searchType==null || searchType.trim().length()==0) searchType = "nameBegins";
        pForm.setSearchType(searchType);


        UserSearchCriteriaData  uSearchCrVw = UserSearchCriteriaData .createValue();
        uSearchCrVw.setStoreId(storeD.getBusEntity().getBusEntityId());
        UserDataVector userDV = pForm.getUsers();
        if(userDV==null) {
            pForm.setUsers(new UserDataVector());
        }


        pForm.setUsersToReturn(null);
        pForm.setSearchStoreId(-1);
        List userTypeList=(List)session.getAttribute("Locate.users.types.vector");
        if(userTypeList==null){

           RefCdDataVector list=pForm.getUserTypeList();

           if(list==null)
           {
            list= lsvc.getRefCodesCollection("USER_TYPE_CD");
            pForm.setUserTypeList(list);
           }

          session.setAttribute("Locate.users.types.vector",list);
        }

            pForm.setUserType("");
        return ae;
    }

    private static List loadListUserTypes() {

      RefCdDataVector list=new RefCdDataVector();

      list.add(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR);
      list.add(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR);
      list.add(RefCodeNames.USER_TYPE_CD.CRC_MANAGER);
      list.add(RefCodeNames.USER_TYPE_CD.CUSTOMER);
      list.add(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE);
      list.add(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR);
      list.add(RefCodeNames.USER_TYPE_CD.ESTORE_CLIENT);
      list.add(RefCodeNames.USER_TYPE_CD.MSB);
      list.add(RefCodeNames.USER_TYPE_CD.REGISTRATION_USER);
      list.add(RefCodeNames.USER_TYPE_CD.REPORTING_USER);
      list.add(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
      list.add(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR);

      return list;
    }


    public static ActionErrors returnNoValue(HttpServletRequest request,
                                             LocateStoreUserForm pForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        pForm.setUsersToReturn(new UserDataVector());
        return ae;
    }

    public static ActionErrors search(HttpServletRequest request,
                                      LocateStoreUserForm pForm)
            throws  Exception {
      ActionErrors ae = new ActionErrors();
        try{
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        String fieldValue1 = pForm.getSearchField();
        String fieldValue2=pForm.getFirstName();
        String fieldValue3=pForm.getLastName();
        String fieldSearchType = pForm.getSearchType();
        String userType=pForm.getUserType();
        boolean showInactiveFl=pForm.getShowInactiveFl();
        UserDataVector dv = search(request, fieldValue1,fieldValue2,fieldValue3,fieldSearchType,userType,storeId,showInactiveFl);
        // User Types Autorized filter application
        UserDataVector udV = new UserDataVector();
        String repUserTypesAutorized = pForm.getUserTypesAutorized();
        for (int i = 0; i < dv.size(); i++) {
          UserData user = (UserData)dv.get(i);
          if (Utility.isUserAutorizedForReport(user.getUserTypeCd(), repUserTypesAutorized)){
            udV.add(user);
          }
        }
        /* if(!pForm.getShowInactiveFl()) {
          for(Iterator iter=dv.iterator(); iter.hasNext();) {
            UserData aD = (UserData) iter.next();
            if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.
                    equals(aD.getUserStatusCd())) {
              iter.remove();
            }
          }
        }*/
        pForm.setUsers(udV);
        pForm.setSearchStoreId(storeId);
       }
       catch(Exception e){
          
           ae.add(ActionErrors.GLOBAL_ERROR,new ActionMessage("error.simpleGenericError",e.getMessage()));
       }


        return ae;

    }


    /**
     *Returns an user data vector based off the specified search criteria
     */
    static public UserDataVector search(HttpServletRequest request,String fieldValue,String firstName,String lastName,
                                        String fieldSearchType,String userType,int storeId,boolean showInactiveFl ) throws Exception{

        // Get a reference to the admin facade
        ActionErrors ae=new ActionErrors();

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserSearchCriteriaData crit = new UserSearchCriteriaData();
        crit.setStoreId(storeId);
        crit.setUserTypeCd(userType);
        crit.setFirstName(firstName);
        crit.setLastName(lastName);
        crit.setIncludeInactiveFl(showInactiveFl);
        if(fieldValue!=null && fieldValue.trim().length()>0) {
            fieldValue = fieldValue.trim();
            if ("id".equals(fieldSearchType)) {
                crit.setUserId(fieldValue);
            }
            else {
                if ("nameBegins".equals(fieldSearchType)) {
                    crit.setUserName(fieldValue);
                    crit.setUserNameMatch(User.NAME_BEGINS_WITH);
                } else {
                    crit.setUserName(fieldValue);
                    crit.setUserNameMatch(User.NAME_CONTAINS);
                }
            }
        }




        UserDataVector dv = userBean.getUsersCollectionByCriteria(crit);

        return dv;


    }


    public static ActionErrors returnSelected(HttpServletRequest request,
                                              LocateStoreUserForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int[] selected = pForm.getSelected();
        UserDataVector dv = pForm.getUsers();
        UserDataVector retDV = new UserDataVector();
        for(Iterator iter=dv.iterator(); iter.hasNext();) {
            UserData aD = (UserData) iter.next();
            for(int ii=0; ii<selected.length; ii++) {
                if(aD.getUserId()==selected[ii]) {
                    retDV.add(aD);
                }
            }
        }

        pForm.setUsersToReturn(retDV);
        return ae;
    }



    public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
        HttpSession session = request.getSession();
        LocateStoreUserForm userForm = pForm.getLocateStoreUserForm();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(userForm.getName()),userForm.getProperty(),null);
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        LocateStoreUserForm userForm = pForm.getLocateStoreUserForm();
        UserDataVector userDV = userForm.getUsersToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(userForm.getName()),userForm.getProperty(),userDV);
        userForm.setLocateUserFl(false);
        return ae;
    }

    protected void finalize() throws Throwable {

        super.finalize();
    }
}
