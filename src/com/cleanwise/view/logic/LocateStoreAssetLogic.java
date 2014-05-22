package com.cleanwise.view.logic;

import com.cleanwise.view.forms.LocateStoreAssetForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;


/**
 * Title:        LocateStoreAssetLogic
 * Description:  Logic class for the  asset search.
 * Purpose:      asset search
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         01.10.2007
 * Time:         13:11:07
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class LocateStoreAssetLogic {

    private static final String className = "LocateStoreAssetLogic";
    /**
     * Action processing
     * @param request HttpServletRequest
     * @param baseForm base form
     * @param action  action
     * @return  errors
     * @throws Exception exception
     */
    public static ActionErrors processAction(HttpServletRequest request,
                                             StorePortalForm baseForm,
                                             String action)  throws Exception
    {
        LocateStoreAssetForm pForm = baseForm.getLocateStoreAssetForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session  = request.getSession();
            if("initSearch".equals(action)) {
                ae = initSearch(request,baseForm);
                return ae;
            }
            int myLevel = pForm.getLevel()+1;
            pForm.setAssetToReturn(null);
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


    /**
     * Init data of form bean
     * @param request   HttpServletRequest
     * @param baseForm  form bean
     * @return   errors
     * @throws Exception  exceptions
     */
    public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();

        if(storeD==null) {
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }

        LocateStoreAssetForm pForm = baseForm.getLocateStoreAssetForm();
        if(pForm==null) {
            pForm = new LocateStoreAssetForm();
            pForm.setLevel(1);
            baseForm.setLocateStoreAssetForm(pForm);
        }

        String searchType = pForm.getSearchType();
        if(searchType==null || searchType.trim().length()==0) searchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        pForm.setSearchType(searchType);

        AssetViewVector assetViewVector=pForm.getAssets();

        if(assetViewVector==null) {
            pForm.setAssets(new AssetViewVector());
        }
        pForm.setAssetToReturn(null);
        pForm.setSearchStoreId(-1);

        return ae;
    }



    public static ActionErrors returnNoValue(HttpServletRequest request,LocateStoreAssetForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        pForm.setAssetToReturn(new AssetViewVector());
        return ae;
    }


    public static ActionErrors search(HttpServletRequest request, LocateStoreAssetForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session    = request.getSession();
            CleanwiseUser appUser  = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            IdVector storeIds      = new IdVector();
            AssetSearchCriteria criteria = new  AssetSearchCriteria ();
            if(Utility.isSet(pForm.getSearchField())){
                if(RefCodeNames.SEARCH_TYPE_CD.ID.equals(pForm.getSearchType())){
                    int id=0;
                    try {
                        id=Integer.parseInt(pForm.getSearchField());
                    } catch (NumberFormatException e) {
                        ae.add("Asset",new ActionError("error.invalidNumber",pForm.getSearchField()));
                        return ae;
                    }
                    criteria.setAssetId(id);
                } else if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(pForm.getSearchType())){
                    criteria.setAssetName(pForm.getSearchField());
                    criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.BEGINS);
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(pForm.getSearchType())){
                    criteria.setAssetName(pForm.getSearchField());
                    criteria.setSearchNameTypeCd(RefCodeNames.SEARCH_TYPE_CD.CONTAINS);
                }
            }
            if(Utility.isSet(pForm.getSearchFieldAssetNumber())){
                criteria.setAssetNumber(pForm.getSearchFieldAssetNumber());
            }

            if(Utility.isSet(pForm.getWarrantyIds())){
              criteria.setWarrantyIds(Utility.parseIdStringToVector(pForm.getWarrantyIds(),","));
            }
            
            criteria.setShowInactive(pForm.getShowInactiveFl());
            //criteria.setUserId(appUser.getUser().getUserId());
            //criteria.setUserTypeCd(appUser.getUser().getUserTypeCd());
            storeIds.add(new Integer(appUser.getUserStore().getStoreId()));
            criteria.setStoreIds(storeIds);


            AssetViewVector assetVV = search(criteria);

            pForm.setAssets(assetVV);
            pForm.setSearchStoreId(appUser.getUserStore().getStoreId());
        }
        catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionMessage("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }

    /**
     * Returns an Asset view vector based off the specified search criteria
     *
     * @param crit AssetSearchCriteria
     * @return Asset view vector
     * @throws Exception exception
     */
    static public AssetViewVector search(AssetSearchCriteria crit) throws Exception {

        APIAccess factory = new APIAccess();
        Asset assetEJB = factory.getAssetAPI();

        return assetEJB.getAssetViewVector(crit);
    }

    public static ActionErrors returnSelected(HttpServletRequest request, LocateStoreAssetForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        int[] selected = pForm.getSelected();
        AssetViewVector  assetVV = pForm.getAssets();
        AssetViewVector retVV = new AssetViewVector();
        for(Iterator iter=assetVV.iterator(); iter.hasNext();) {
            AssetView assetView = (AssetView) iter.next();
            for(int ii=0; ii<selected.length; ii++) {
                if(assetView.getAssetId()==selected[ii]) {
                    retVV.add(assetView);
                }
            }
        }
        pForm.setAssetToReturn(retVV);
        return ae;
    }

    public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
        HttpSession session = request.getSession();
        LocateStoreAssetForm assetForm = pForm.getLocateStoreAssetForm();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(assetForm.getName()),assetForm.getProperty(),null);
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)  throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        LocateStoreAssetForm sForm = pForm.getLocateStoreAssetForm();
        AssetViewVector assetVV = sForm.getAssetToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(sForm.getName()),sForm.getProperty(),assetVV);
        sForm.setLocateAssetFl(false);
        return ae;
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

}
