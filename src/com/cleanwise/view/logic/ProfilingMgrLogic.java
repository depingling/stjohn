/*
 * ProfilingMgrLogic.java
 *
 * Created on May 15, 2003, 3:55 PM
 */

package com.cleanwise.view.logic;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Profiling;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountSearchResultView;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ProfileData;
import com.cleanwise.service.api.value.ProfileDataVector;
import com.cleanwise.service.api.value.ProfileDetailData;
import com.cleanwise.service.api.value.ProfileMetaData;
import com.cleanwise.service.api.value.ProfileView;
import com.cleanwise.view.forms.CustomerProfilingForm;
import com.cleanwise.view.forms.ProfilingMgrSearchForm;
import com.cleanwise.view.forms.ProfilingMgrSurveyForm;
import com.cleanwise.view.forms.SiteMgrDetailForm;
import com.cleanwise.view.forms.SiteShoppingControlForm;
import com.cleanwise.view.forms.StoreSiteMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.ProfileViewContainer;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.ShopTool;
/**
 *
 * @author  bstevens
 */
public class ProfilingMgrLogic {
    public static final String PROFILE_FOUND_VECTOR = "Profiling.found.vector";
    private static final String CONFIG_SEARCH_ACCOUNT = "Account";
    private static final List CONFIG_SEARCH_TYPES;
    static{
        CONFIG_SEARCH_TYPES = new ArrayList();
        CONFIG_SEARCH_TYPES.add(new FormArrayElement(CONFIG_SEARCH_ACCOUNT,CONFIG_SEARCH_ACCOUNT));
    }

    private static final java.util.Comparator ACCOUNT_EQUALITY_COMPARE = new java.util.Comparator() {
        public int compare(Object o1, Object o2) {
        	int id1;
        	if(o1 instanceof AccountSearchResultView){
        		id1 = ((AccountSearchResultView)o1).getAccountId();
        	} else{
            id1 = ((AccountData)o1).getBusEntity().getBusEntityId();
        	}
            int id2 = ((AccountData)o2).getBusEntity().getBusEntityId();
            return id1 - id2;
        }
    };

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,ActionForm form)
    throws Exception {
        HttpSession session = request.getSession();
        // Set up the site status list.
        if (null == session.getAttribute("profiling.config.type.vector")) {
            session.setAttribute("profiling.config.type.vector", CONFIG_SEARCH_TYPES);
        }
        return;
    }

    private static int getProfileId(HttpServletRequest request,ActionForm form){
        HttpSession session = request.getSession();
        ProfilingMgrSurveyForm sForm = (ProfilingMgrSurveyForm) form;
        String profileIdS = (String) request.getParameter("profileId");
        if(!Utility.isSet(profileIdS) && !"0".equals(profileIdS)){
        	profileIdS = (String) request.getAttribute("profileId");
        	if(!Utility.isSet(profileIdS) && !"0".equals(profileIdS)){
        		profileIdS = (String) session.getAttribute("profileId");
        	}
        }

        int profileId = 0;
        if(Utility.isSet(profileIdS)){
            try{
                profileId = Integer.parseInt(profileIdS);
            }catch(RuntimeException e){}
        }
        if(profileId == 0 && sForm != null){
            profileId = sForm.getProfile().getProfileView().getProfileData().getProfileId();
        }
        return profileId;
    }

    /**
     *Updates the profile configuration as specified by the form.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors updateConfig(HttpServletRequest request,ActionForm form)
    throws Exception {
        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        try{
            String userName = (String) session.getAttribute(Constants.USER_NAME);
            ProfilingMgrSurveyForm sForm = (ProfilingMgrSurveyForm) form;
            if(CONFIG_SEARCH_ACCOUNT.equals(sForm.getUpdateType())){
                int profileId = getProfileId(request,form);
                if(profileId == 0){
                    updateErrors.add("profileId", new ActionError("error.badRequest","profileId"));
                    return updateErrors;
                }
                APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
                Profiling profilingEjb = factory.getProfilingAPI();

                //add the newly selected associations
                IdVector newBusEntAssoc = new IdVector();
                List tmp = sForm.getSelectableAccountResults().getNewlySelected();
                Iterator it = tmp.iterator();
                while(it.hasNext()){
                    AccountData account = (AccountData) it.next();
                    newBusEntAssoc.add(new Integer(account.getBusEntity().getBusEntityId()));
                }
                profilingEjb.addProfileBusEntityAssociations(profileId,newBusEntAssoc,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,userName);

                //remove the de-selected associations
                IdVector remBusEntAssoc = new IdVector();
                it = sForm.getSelectableAccountResults().getDeselected().iterator();
                while(it.hasNext()){
                    AccountData account = (AccountData) it.next();
                    if(account != null){
                        remBusEntAssoc.add(new Integer(account.getBusEntity().getBusEntityId()));
                    }
                }
                profilingEjb.removeProfileBusEntityAssociations(profileId,remBusEntAssoc,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,userName);

                sForm.getSelectableAccountResults().resetState();
            }else{
                updateErrors.add("searchType", new ActionError("error.simpleGenericError","invalid update type: "+sForm.getUpdateType()));
            }
        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }

    /**
     * <code>getConfig</code> method.  Fetches the configuration info as specified by the form.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors getConfig(HttpServletRequest request,ActionForm form)
    throws Exception {
        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        try{
            ProfilingMgrSurveyForm sForm = (ProfilingMgrSurveyForm) form;
            int profileId = getProfileId(request,form);
            if(profileId == 0){
                updateErrors.add("profileId", new ActionError("error.badRequest","profileId"));
            }


            String sSearchType = sForm.getSearchType();
            String sSearchName = sForm.getSearchField();
            if(!Utility.isSet(sSearchType)){
                updateErrors.add("searchType", new ActionError("variable.empty.error","searchType"));
            }
            if(!Utility.isSet(sSearchName)){
                sSearchName = null;
                //updateErrors.add("searchField", new ActionError("variable.empty.error","searchField"));
            }
            //if we found errors while scrubbing the data return without updating
            if(updateErrors.size() > 0){
                sForm.setSelectableAccountResults(new SelectableObjects(new ArrayList(),new ArrayList(),null));
                return updateErrors;
            }
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            Profiling profilingEjb = factory.getProfilingAPI();
            Account accountEjb = factory.getAccountAPI();
            if(sSearchType.equals(CONFIG_SEARCH_ACCOUNT)){
                BusEntityDataVector exsistingAccounts = profilingEjb.getBusEntityCollectionForProfile(profileId, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,sSearchName);
                AccountDataVector exsistingAccountsFake = new AccountDataVector();
                Iterator it = exsistingAccounts.iterator();
                while(it.hasNext()){
                    BusEntityData b = (BusEntityData) it.next();
                    AccountData a = AccountData.createValue();
                    a.setBusEntity(b);
                    exsistingAccountsFake.add(a);
                }
                Account accountBean = factory.getAccountAPI();
                AccountSearchResultViewVector acntSrchRsltVctr = null;
                CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                String storeStr = null;
                if (!appUser.isaSystemAdmin()) {
            		IdVector stores = appUser.getUserStoreAsIdVector();
            		storeStr = stores.toCommaString(stores);
            	}
                acntSrchRsltVctr = accountBean.search(storeStr, sSearchName, "contains", "0", true);
                SelectableObjects results = new SelectableObjects(exsistingAccountsFake, acntSrchRsltVctr, ACCOUNT_EQUALITY_COMPARE);
                sForm.setSelectableAccountResults(results);
            }else{
                updateErrors.add("searchType", new ActionError("error.simpleGenericError","invalid search type criteria: "+sSearchType));
            }
        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }

    /**
     * <code>createEmptySurvey</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns ActionErrors a populated ActionErrors object with any errors that occured
     * @exception Exception if an unexpected error occurs
     */
    public static ProfileView createEmptySurvey(HttpServletRequest request)
    throws Exception {
        ProfileView survey = ProfileView.createValue();
        survey.getProfileData().setProfileTypeCd(RefCodeNames.PROFILE_TYPE_CD.SURVEY);
        survey.getProfileData().setProfileStatusCd(RefCodeNames.PROFILE_STATUS_CD.ACTIVE);
        return survey;
    }

    /**
     *extracts the profile out of all the various form subclasses that we know how to deal with
     */
    public static ProfileViewContainer getProfile(ActionForm form){
        if(form instanceof SiteMgrDetailForm){
            return ((SiteMgrDetailForm) form).getProfile();
        }else if(form instanceof StoreSiteMgrForm) {
            return ((StoreSiteMgrForm) form).getProfile();
        }else if(form instanceof ProfilingMgrSurveyForm){
            return ((ProfilingMgrSurveyForm) form).getProfile();
        }else if(form instanceof CustomerProfilingForm){
            return ((CustomerProfilingForm) form).getProfile();
        } else if (form instanceof SiteShoppingControlForm) {
            return ((SiteShoppingControlForm)form).getProfile();
        }else{
            throw new ClassCastException("Unknown ActionForm subclass for profiling operations");
        }
    }

    /**
     *sets the profile property of all the various form subclasses that we know how to deal with
     */
    public static void setProfile(ActionForm form, ProfileViewContainer profile){
        if(form instanceof SiteMgrDetailForm){
            ((SiteMgrDetailForm) form).setProfile(profile);
        }else if(form instanceof StoreSiteMgrForm) {
            ((StoreSiteMgrForm) form).setProfile(profile);
        }else if(form instanceof ProfilingMgrSurveyForm){
            ((ProfilingMgrSurveyForm) form).setProfile(profile);
        }else if(form instanceof CustomerProfilingForm){
            ((CustomerProfilingForm) form).setProfile(profile);
        } else if (form instanceof SiteShoppingControlForm) {
            ((SiteShoppingControlForm)form).setProfile(profile);
        }else{
            throw new ClassCastException("Unknown ActionForm subclass for profiling operations");
        }
    }

    /**
     * <code>getSurveyDetail</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns ActionErrors a populated ActionErrors object with any errors that occured
     * @exception Exception if an unexpected error occurs
     */
    public static ActionErrors getSurveyDetail(HttpServletRequest request,ActionForm form,
    boolean fetchDetail,boolean customerInterface)
    throws Exception {
        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        try{
            ProfileViewContainer pro = getProfile(form);
            int busEntId = 0;
            if(fetchDetail){
                if(customerInterface){
                    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
                    int accountId = appUser.getUserAccount().getBusEntity().getBusEntityId();
                    busEntId = appUser.getSite().getSiteId();
                    if(busEntId == 0){
                        updateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("bad.site"));
                    }
                    if (form instanceof SiteShoppingControlForm) {
                      getProfilesForAccount(busEntId, request);
                    }
                }else{
                    //if we are getting detail then we better know the form type passed in
                    if(form instanceof SiteMgrDetailForm) {
                      SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;
                      busEntId = sForm.getIntId();
                    } else if(form instanceof StoreSiteMgrForm) {
                      StoreSiteMgrForm sForm = (StoreSiteMgrForm) form;
                      busEntId = sForm.getIntId();
                    }
                    if(busEntId == 0){
                        updateErrors.add("id", new ActionError("error.badRequest","site id"));
                    }
                }
            }
            
            int profileId = getProfileId(request, null);
            if(profileId == 0){
                if (form!=null && pro!= null && pro.getProfileView().getProfileData() != null){
                    profileId = pro.getProfileView().getProfileData().getProfileId();
                }else{
                    updateErrors.add("profileId", new ActionError("error.badRequest","profileId"));
                }
            }
            if(updateErrors.size() > 0){
                setProfile(form,ProfileViewContainer.getProfileViewContainer(createEmptySurvey(request)));
                return updateErrors;
            }
            //do not refresh an unsaved survey
            if(profileId != 0){
                APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
                Profiling profilingEjb = factory.getProfilingAPI();
                if(customerInterface){
                    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
                    int accountId = appUser.getUserAccount().getBusEntity().getBusEntityId();
                    ProfileDataVector pdv = profilingEjb.getProfileCollectionForBusEntity(accountId, false);
                    Iterator it = pdv.iterator();
                    boolean isValidProfile = false;
                    while(it.hasNext()){
                        ProfileData pd = (ProfileData) it.next();
                        if(pd.getProfileId() == profileId){
                            isValidProfile = true;
                            break;
                        }
                    }
                    if(!isValidProfile){
                        updateErrors.add("profileId", new ActionError("variable.integer.format.error","profileId"));
                        return updateErrors;
                    }
                }

                ProfileView survey;
                if(fetchDetail){
                    survey = profilingEjb.getProfileResultsForBusEntity(profileId,busEntId);
                }else{
                    survey = profilingEjb.getProfileView(profileId);
                }
                if(survey == null){
                    updateErrors.add("profileId",new ActionError("bad.profile"));
                    survey = createEmptySurvey(request);
                }
                ProfileViewContainer pvc = ProfileViewContainer.getProfileViewContainer(survey);
                pvc.deepSort();
                setProfile(form,pvc);
            }
        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }

    /**
     * <code>getProfilesForAdminSite</code> method.  Gets the profiles for the for the
     * site currently logged in.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @returns ActionErrors a populated ActionErrors object with any errors that occured
     * @exception Exception if an unexpected error occurs
     */
    public static ActionErrors getProfilesForSite(HttpServletRequest request)
    throws Exception {
        HttpSession session = request.getSession();

        int acctId = ShopTool.getCurrentUser(request).getUserAccount().getAccountId();

  // Initialize to an empty set.
        ProfileDataVector profiles = new ProfileDataVector();
        session.setAttribute(PROFILE_FOUND_VECTOR, profiles);

  if ( acctId > 0 ) {
      getProfilesForAccount(acctId, request);
  }
        return new ActionErrors();
    }


    /**
     * <code>getProfilesForAdminSite</code> method.  Gets the profiles for the admin interface for the
     * given site id.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns ActionErrors a populated ActionErrors object with any errors that occured
     * @exception Exception if an unexpected error occurs
     */
    public static ActionErrors getProfilesForAdminSite(HttpServletRequest request,ActionForm form)
    throws Exception {
        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        session.removeAttribute(PROFILE_FOUND_VECTOR);
        try{
            String fieldValue = request.getParameter("searchField");
            if(form instanceof SiteMgrDetailForm) {
              SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;
              fieldValue = sForm.getId();
            } else if(form instanceof StoreSiteMgrForm) {
              StoreSiteMgrForm sForm = (StoreSiteMgrForm) form;
              fieldValue = sForm.getId();
            }
            if (null == fieldValue) {
                // Look for a cached site id.
                fieldValue = (String) session.getAttribute("Site.id");
                if (null == fieldValue) {
                    fieldValue = "0";
                }
            }
            if (null != fieldValue) {
                fieldValue = fieldValue.trim();
            }
            int siteid = Integer.parseInt(fieldValue);
            session.setAttribute("Site.id",fieldValue);
            
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            Profiling profilingBean = factory.getProfilingAPI();
            ProfileDataVector profiles = profilingBean.getProfileCollectionForBusEntity(siteid, false);
            session.setAttribute(PROFILE_FOUND_VECTOR, profiles);
            if(profiles.size() == 1){
                ProfileData p = (ProfileData) profiles.get(0);
                session.setAttribute("profileId",Integer.toString(p.getProfileId()));
            }
            
        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }


    private static void getProfilesForAccount(int pAccountId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Profiling profilingBean = factory.getProfilingAPI();
        ProfileDataVector profiles = profilingBean.getProfileCollectionForBusEntity(pAccountId, true);
        session.setAttribute(PROFILE_FOUND_VECTOR, profiles);
        if(profiles.size() == 1){
            ProfileData p = (ProfileData) profiles.get(0);
            //request.setAttribute("profileId",Integer.toString(p.getProfileId()));
            session.setAttribute("profileId",Integer.toString(p.getProfileId()));
        }
    }


    /**
     * <code>search</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns ActionErrors a populated ActionErrors object with any errors that occured
     * @exception Exception if an unexpected error occurs
     */
    public static ActionErrors search(HttpServletRequest request,ActionForm form)
    throws Exception {

        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        session.removeAttribute(PROFILE_FOUND_VECTOR);
        try{
            ProfilingMgrSearchForm sForm = (ProfilingMgrSearchForm) form;
            if(!Utility.isSet(sForm.getSearchPofileTypeCd())){
                updateErrors.add("searchProfileTypeCd", new ActionError("variable.empty.error","searchProfileTypeCd"));
            }
            if(updateErrors.size() > 0){
                return updateErrors;
            }

            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }

            IdVector stores;
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                stores = appUser.getUserStoreAsIdVector();
            }else{
                stores = null;
            }

            Profiling profilingBean = factory.getProfilingAPI();
            int matchType = 0;
            if("id".equals(sForm.getSearchType())){
                try{
                    int lProfileId = Integer.parseInt(sForm.getSearchField());
                    ProfileData pd = profilingBean.getProfile(stores,lProfileId);
                    ProfileDataVector results = new ProfileDataVector();
                    if(pd != null){
                        results.add(pd);
                    }
                    session.setAttribute(PROFILE_FOUND_VECTOR, results);
                }catch(NumberFormatException e){
                    updateErrors.add("searchField", new ActionError("variable.integer.format.error","searchField"));
                    return updateErrors;
                }
            }else{
                if("nameBegins".equals(sForm.getSearchType())){
                    matchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
                }else{
                    matchType = SearchCriteria.CONTAINS_IGNORE_CASE;
                }

                ProfileDataVector results = profilingBean.getProfileCollection(stores,sForm.getSearchField(),sForm.getSearchPofileTypeCd(),matchType);
                session.setAttribute(PROFILE_FOUND_VECTOR, results);
            }
        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }



    //recursively scrubs and prunes the passed in survey detail results.
    private static void scrubSurveyDetailResultsData(ActionErrors pErrors,
    ProfileViewContainer pSurvey,int pBusEntityId,
    MessageResources pResources,Map pViewContainerCache, HttpServletRequest request){

        Iterator it = pSurvey.getProfileView().getProfileDetailDataVector().iterator();
        String lQType = pSurvey.getProfileView().getProfileData().getProfileQuestionTypeCd();
        while(it.hasNext()){
            ProfileDetailData data = (ProfileDetailData) it.next();
            data.setLoopValue(pSurvey.getLoopValue());
            data.setBusEntityId(pBusEntityId);
            if(RefCodeNames.PROFILE_TYPE_CD.QUESTION.equals(pSurvey.getProfileView().getProfileData().getProfileTypeCd()) && !Utility.isSet(data.getValue())){
                //pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("variable.question.empty.error",pSurvey.getProfileView().getProfileData().getShortDesc()));
                //pSurvey.setErrorMessage(pResources.getMessage("variable.question.empty.error",pSurvey.getProfileView().getProfileData().getShortDesc()));
            }else{
                if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER.equals(lQType)){
                    try{
                        java.math.BigDecimal val = null;
                        val = new java.math.BigDecimal(data.getValue());
                    }catch(Exception e){
                        if(pErrors.size() == 0){
                            pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("survey.contains.error"));
                        }
                        pSurvey.setErrorMessage(pResources.getMessage("variable.question.invalidNumber"));
                    }
                } else if (RefCodeNames.PROFILE_QUESTION_TYPE_CD.DATE.equals(lQType)){
                  String valueS = data.getValue();
                  Date value = null;
                  try {
                    value = ClwI18nUtil.parseDateInp(request,valueS);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    data.setValue(sdf.format(value));
                  } catch (Exception exc) {
                    Object[] params = new Object[1];
                    params[0] = valueS;
                    String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongDateFormat",params);
                    pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("survey.contains.error",errorMess));
                    pSurvey.setErrorMessage(pResources.getMessage("variable.question.invalidDate"));
                    data.setValue(valueS);
                  }


                }
            }
        }

        //recurse through the children
        if(pSurvey.getChildren() != null){
            //determine the number of unique children, this is important to make sure that when
            //validating we get them all, and not just the first of the last set
            it = pSurvey.getChildren().iterator();
            HashSet uniqueKids = new HashSet();
            while(it.hasNext()){
                ProfileViewContainer child = (ProfileViewContainer) it.next();
                uniqueKids.add(new Integer(child.getProfileView().getProfileData().getProfileId()));
            }

            int uniqueKidCount = uniqueKids.size();
            it = pSurvey.getChildren().iterator();
            int i=0;
            while(it.hasNext()){
                ProfileViewContainer child = (ProfileViewContainer) it.next();
                if(child.getProfileView().getProfileData().getProfileId() == 0){
                    //trim out the junk
                    it.remove();
                    continue;
                }
                //clear out any lingering error messages
                child.setErrorMessage(null);
                Integer key = new Integer(child.getProfileView().getProfileData().getProfileId());
                if(child.getProfileView().getProfileData().getAddDate() == null && pViewContainerCache.containsKey(key)){
                    ProfileViewContainer tmp = (ProfileViewContainer) pViewContainerCache.get(key);
                    tmp = tmp.cloneNoDetail();
                    tmp.getProfileView().setProfileDetailDataVector(child.getProfileView().getProfileDetailDataVector());
                    pSurvey.getChildren().set(i, tmp);
                    child = tmp;
                }else{
                    pViewContainerCache.put(key, child);
                }
                //checks if this child should be set
                if(pSurvey != null && pSurvey.shouldChildAtIndexBeSet((i/uniqueKidCount)+1)){
                    boolean isSet = false;

                    if(Utility.isSet(child.getProfileView().getProfileDetailDataVectorElement(0).getValue())){
                        isSet = true;
                    }
                    if(child.getDetailValues() != null && child.getDetailValues().length >0 ) {
                        isSet = true;
                    }

                    if(!isSet){
                        if(pErrors.size() == 0){
                            pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("survey.contains.error"));
                        }
                      //pErrors.add("error",new ActionError("error.simpleGenericError",
                      //child.getProfileView().getProfileData().getShortDesc()));
                       child.setErrorMessage(pResources.getMessage("variable.question.empty.error"));
                    }
                }else{
                    if(pSurvey.getErrorMessage() == null && child.getErrorMessage()==null){
                        child.getProfileView().getProfileDetailDataVectorElement(0).setValue(null);
                        //child.getProfileView().setProfileDetailDataVector(new ProfileDetailDataVector());
                    }
                }



                scrubSurveyDetailResultsData(pErrors,child,pBusEntityId,pResources, pViewContainerCache, request);
                i++;
            }
        }
    }


    //recursively scrubs and prunes the passed in survey ADMIN SCRUBBER
    private static void scrubSurveyData(ActionErrors pErrors, ProfileViewContainer pSurvey){
        if(pSurvey.getChildren() == null){
            return;
        }
        Iterator it = pSurvey.getChildren().iterator();
        while(it.hasNext()){
            ProfileViewContainer child = (ProfileViewContainer) it.next();
            if(child.getProfileView() == null || child.getProfileView().getProfileData() == null){
                it.remove();
            }else if(!Utility.isSet(child.getProfileView().getProfileData().getShortDesc()) &&
            (child.getChildren() == null || child.getChildren().size() == 0)){
                //prune this child
                if(child.getProfileView().getProfileData().getProfileId() == 0){
                    it.remove();
                }
            }else{
                try{
                    uploadProfileImageFile(child);
                }catch (IOException e){
                    e.printStackTrace();
                    pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError",e.getMessage()));
                }
                if(!Utility.isSet(child.getProfileView().getProfileData().getProfileTypeCd())){
                    child.getProfileView().getProfileData().setProfileTypeCd(RefCodeNames.PROFILE_TYPE_CD.QUESTION);
                }
                if(!Utility.isSet(child.getProfileView().getProfileData().getProfileStatusCd())){
                    child.getProfileView().getProfileData().setProfileStatusCd(RefCodeNames.PROFILE_STATUS_CD.ACTIVE);
                }
                if(!Utility.isSet(child.getProfileView().getProfileData().getReadOnly())){
                    child.getProfileView().getProfileData().setReadOnly(Boolean.TRUE.toString());
                }
                if(Utility.isSet(child.getProfileOrderString())){
                    int orderVal = 0;
                    try{
                        orderVal = Integer.parseInt(child.getProfileOrderString());
                        child.getProfileView().getProfileData().setProfileOrder(orderVal);
                    }catch(Exception e){
                        pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Order for question: " + child.getProfileView().getProfileData().getShortDesc()));
                    }

                }
                //scrub the meta data
                if(child.getProfileView().getProfileMetaDataVector() != null){
                    Iterator metaIt = child.getProfileView().getProfileMetaDataVector().iterator();
                    while(metaIt.hasNext()){
                        ProfileMetaData meta = (ProfileMetaData) metaIt.next();
                        if(!Utility.isSet(meta.getProfileMetaStatusCd())){
                            meta.setProfileMetaStatusCd(RefCodeNames.PROFILE_META_STATUS_CD.ACTIVE);
                        }
                        if(!Utility.isSet(meta.getProfileMetaTypeCd())){
                            pErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("variable.empty.error","type cd for meta data of question: " + child.getProfileView().getProfileData().getShortDesc()));
                        }
                    }
                }
                scrubSurveyData(pErrors,child);
            }
        }
    }

    public static ActionErrors updateSurveyForAdminSite(HttpServletRequest request,
    ActionForm form,MessageResources pResources,boolean customerInterface)
    throws Exception{

        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        try{
            Integer id;
            if(customerInterface){
                CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
                int busEntId = appUser.getSite().getBusEntity().getBusEntityId();
                if(busEntId == 0){
                    updateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("bad.site"));
                }
                id = new Integer(busEntId);
            }else{
                String idS = (String)session.getAttribute("Site.id");
                if(!Utility.isSet(idS)){
                    if(form instanceof SiteMgrDetailForm) {
                      SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;
                      idS = sForm.getId();
                    } else if(form instanceof StoreSiteMgrForm) {
                      StoreSiteMgrForm sForm = (StoreSiteMgrForm) form;
                      idS = sForm.getId();
                    }
                    if(!Utility.isSet(idS)){
                        updateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.badRequest","Site.id"));
                    }
                }
                id = new Integer(idS);
            }
            if(updateErrors.size() > 0){
                return updateErrors;
            }

            ProfileViewContainer prof = getProfile(form);
            debugProfileView(prof,0);

            scrubSurveyDetailResultsData(updateErrors,prof,id.intValue(),pResources, new HashMap(), request);

            if(updateErrors.size() > 0){
    // Partial profiles are allowed per Doug and Mary
                // durval 8/29/2005.
                //return updateErrors;
            }

            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            String userName = (String) session.getAttribute(Constants.USER_NAME);
            Profiling profilingEjb = factory.getProfilingAPI();

            profilingEjb.updateProfileView(prof.toProfileView(id.intValue()),userName,true);
            ProfileView results = profilingEjb.getProfileResultsForBusEntity(prof.getProfileView().getProfileData().getProfileId(), id.intValue());
            ProfileViewContainer pvc = ProfileViewContainer.getProfileViewContainer(results);
            pvc.deepSort();
            setProfile(form,pvc);
        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }


    private static void debugProfileView(ProfileViewContainer prof,int pLvl){
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<pLvl;i++){
            buf.append(">>>>");
        }
        buf.append(prof.getProfileView().getProfileData().getShortDesc());
        buf.append("["+prof.getProfileView().getProfileDetailDataVector().size()+"]");
        if(prof.getProfileView().getProfileDetailDataVector().size() > 0){
            buf.append("::"+prof.getProfileView().getProfileDetailDataVectorElement(0).getValue());
            buf.append("::"+prof.getProfileView().getProfileDetailDataVectorElement(0).getProfileDetailId());
        }
        Iterator it = prof.getChildren().iterator();
        while(it.hasNext()){
            debugProfileView((ProfileViewContainer) it.next(), pLvl + 1);
        }
    }

    /**
     * <code>uploadProfileImageFile</code> is a method that completes the action of
     * uploading the image file for this profile.  The profileView's metaData vector is updated
     * with the new image.
     *
     * @param the profileView
     * @param the username of the user preforming the action
     * @throws an IO exception on any error
     */
    private static void uploadProfileImageFile(ProfileViewContainer profile) throws IOException {
        FormFile file = profile.getImageUploadFile();

        // Don't know any other way to discern if the file exists
        // or is readable, or some other problem
        if (file==null || !Utility.isSet(file.getFileName()) || file.getFileSize() == 0) {
            return;
        }

        // get the file extension (e.g. ".jpg", ".pdf", etc.)
        String fileExt = null;
        String uploadFileName = file.getFileName();
        int i = uploadFileName.lastIndexOf(".");
        if (i < 0) {
            fileExt = "";
        } else {
            fileExt = uploadFileName.substring(i);
        }

        // this is the path to be saved in the database
        String basepath =
        "/en/profiling/images/" + String.valueOf(profile.getProfileView().getProfileData().getProfileId()) + fileExt;

        // this is the absolute path where we will be writing
        String fileName = System.getProperty("webdeploy") + basepath;
        //retrieve the file data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream stream = file.getInputStream();
        OutputStream bos = new FileOutputStream(fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();

        //close the stream
        stream.close();
        // clear the contents???
        file.destroy();

        //find exiting Image, if not add it, otherwise replace it
        Iterator it = profile.getProfileView().getProfileMetaDataVector().iterator();
        boolean foundImage = false;
        while(it.hasNext()){
            ProfileMetaData meta = (ProfileMetaData) it.next();
            if(RefCodeNames.PROFILE_META_TYPE_CD.IMAGE.equals(meta.getProfileMetaTypeCd())){
                foundImage = true;
                meta.setValue(basepath);
            }
        }
        //if there was no existing image create a new meta data entry for it
        if(!foundImage){
            ProfileMetaData newMeta = ProfileMetaData.createValue();
            newMeta.setProfileId(profile.getProfileView().getProfileData().getProfileId());
            newMeta.setProfileMetaStatusCd(RefCodeNames.PROFILE_META_STATUS_CD.ACTIVE);
            newMeta.setProfileMetaTypeCd(RefCodeNames.PROFILE_META_TYPE_CD.IMAGE);
            newMeta.setValue(basepath);
            profile.getProfileView().getProfileMetaDataVector().add(newMeta);
        }
    }

    /**
     * <code>search</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @returns ActionErrors a populated ActionErrors object with any errors that occured
     * @exception Exception if an unexpected error occurs
     */
    public static ActionErrors updateSurvey(HttpServletRequest request,ActionForm form)
    throws Exception {

        ActionErrors updateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        try{

            ProfileViewContainer prof = getProfile(form);
            scrubSurveyData(updateErrors,prof);

            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            String userName = (String) session.getAttribute(Constants.USER_NAME);
            Profiling profilingEjb = factory.getProfilingAPI();

            //send the update request and update the form with the updated survey
            ProfileView results = profilingEjb.updateProfileView(prof.toProfileView(0),userName,false);

            ProfileViewContainer pvc = ProfileViewContainer.getProfileViewContainer(results);
            pvc.deepSort();
            setProfile(form, pvc);

        }catch(Exception e){
            e.printStackTrace();
            updateErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return updateErrors;
    }
}
