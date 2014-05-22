package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.FreightTable;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.LocateStoreFhForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

/**
 * @author Alexander Chikin
 * Date: 22.08.2006
 * Time: 15:47:04
 */
public class LocateStoreFhLogic {

    public static ActionErrors processAction(HttpServletRequest request,
                                             StorePortalForm baseForm, String action)
            throws Exception
    {
        LocateStoreFhForm pForm = baseForm.getLocateStoreFhForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session  = request.getSession();
            if("initSearch".equals(action)) {
                ae = initSearch(request,baseForm);
                return ae;
            }
            int myLevel = pForm.getLevel()+1;

            pForm.setFreightHandlerToReturn(null);

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


        LocateStoreFhForm pForm = baseForm.getLocateStoreFhForm();
        if(pForm==null) {
            pForm = new LocateStoreFhForm();
            pForm.setLevel(1);
            baseForm.setLocateStoreFhForm(pForm);
        }

        //baseForm.setEmbeddedForm(pForm);

        String searchType = pForm.getSearchType();
        if(searchType==null || searchType.trim().length()==0) searchType = "nameBegins";
        pForm.setSearchType(searchType);

        FreightHandlerViewVector fhVV=pForm.getFreightHandler();

        if(fhVV==null) {
            pForm.setFreightHandler(new FreightHandlerViewVector());
        }


        pForm.setFreightHandlerToReturn(null);
        pForm.setSearchStoreId(-1);

        return ae;
    }




    public static ActionErrors returnNoValue(HttpServletRequest request,
                                             LocateStoreFhForm pForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        pForm.setFreightHandlerToReturn(new FreightHandlerViewVector());
        return ae;
    }

    public static ActionErrors search(HttpServletRequest request,
                                      LocateStoreFhForm pForm)
            throws  Exception {
        ActionErrors ae = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            StoreData storeD = appUser.getUserStore();
            int storeId = storeD.getBusEntity().getBusEntityId();
            String searchField= pForm.getSearchField();
            String fieldSearchType = pForm.getSearchType();
            boolean showInactiveFl=pForm.getShowInactiveFl();

            /*  if(!Utility.isSet(fieldSearchType)){
               ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("variable.empty.error","Search Type"));
           }
           if(!Utility.isSet(searchField)){
               ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("variable.empty.error","Search Field"));
           }
           if(ae.size() > 0){
               return ae;
           }
            */
            if(fieldSearchType.equals("id")&&searchField.length()>0){

                Integer.parseInt(searchField);
            }

            FreightHandlerViewVector fhv = search(searchField, fieldSearchType, appUser, showInactiveFl);

            pForm.setFreightHandler(fhv);
            pForm.setSearchStoreId(storeId);
            pForm.setShowInactiveFl(false);
        }catch(NumberFormatException e){
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Search Field"));
            return ae;
        }
        catch(Exception e){

            ae.add(ActionErrors.GLOBAL_ERROR,new ActionMessage("error.simpleGenericError",e.getMessage()));
        }

        return ae;

    }


    /**
     *Returns an FreightHandler data vector based off the specified search criteria
     */
    static public FreightHandlerViewVector search(String searchField, String searchType,CleanwiseUser appUser, boolean showInactiveFl) throws Exception{

        APIAccess factory = new APIAccess();
        Distributor distBean = factory.getDistributorAPI();
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        }

        if(searchType.equals("id"))
        {    if(searchField.length()>0)
            crit.setSearchId(searchField);

        }
        else if(searchType.equals("nameBegins")&&(searchField.length()>0)){
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
            crit.setSearchName(searchField);

        }else if(searchType.equals("nameContains")&&(searchField.length()>0)){
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
            crit.setSearchName(searchField);

        }




        crit.setSearchForInactive(showInactiveFl);
        crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
        FreightHandlerViewVector fhv = distBean.getFreightHandlersByCriteria(crit);

        if ( null == fhv) {
            fhv = new FreightHandlerViewVector();
        }

        return fhv;

    }

    public static ActionErrors returnSelected(HttpServletRequest request,
                                              LocateStoreFhForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int[] selected = pForm.getSelected();
        FreightHandlerViewVector fhVV = pForm.getFreightHandler();
        FreightHandlerViewVector retVV = new FreightHandlerViewVector();
        for(Iterator iter=fhVV.iterator(); iter.hasNext();) {
            FreightHandlerView fhD = (FreightHandlerView) iter.next();
            for(int ii=0; ii<selected.length; ii++) {
                if(fhD.getBusEntityData().getBusEntityId()==selected[ii]) {
                    retVV.add(fhD);
                }
            }
        }

        pForm.setFreightHandlerToReturn(retVV);
        return ae;
    }



    public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
        HttpSession session = request.getSession();
        LocateStoreFhForm fhForm = pForm.getLocateStoreFhForm();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(fhForm.getName()),fhForm.getProperty(),null);
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        LocateStoreFhForm fhForm = pForm.getLocateStoreFhForm();
        FreightHandlerViewVector fhVV = fhForm.getFreightHandlerToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(fhForm.getName()),fhForm.getProperty(),fhVV);
        fhForm.setLocateFhFl(false);
        return ae;
    }

    protected void finalize() throws Throwable {

        super.finalize();
    }
}
