/**
 * 
 */
package com.espendwise.view.logic.esw;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.StoreProfileDto;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Language;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.PasswordUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.LanguageData;
import com.cleanwise.service.api.value.LanguageDataVector;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.StoreProfileData;
import com.cleanwise.service.api.value.StoreProfileDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.validators.EmailValidator;
import com.espendwise.view.forms.esw.UserProfileForm;
import com.cleanwise.view.utils.DisplayListSort;

/**
 * @author ssharma
 *
 */
public class UserProfileLogic {
	
	private static final Logger log = Logger.getLogger(UserProfileLogic.class);
	
	/*
	 * Get user detail from user id
	 */
	public static ActionErrors getUserDetailById (HttpServletRequest request, int pUserId,
			UserProfileForm pForm) {
		
		ActionErrors errors = new ActionErrors();
		String locale = null;
		HttpSession session = request.getSession();
		
        try{
        	APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();
            Language languageBean = factory.getLanguageAPI();
            Country countryBean = factory.getCountryAPI(); 
            Store storeBean = factory.getStoreAPI();

        	UserInfoData pUserInfo = userBean.getUserContact(pUserId);
        	
        	locale = pUserInfo.getUserData().getPrefLocaleCd();
        	String pLanguageCode = locale.substring(0,2);
            String pCountryCode = locale.substring(3,locale.trim().length());          
           
            LanguageData languageData = languageBean.getLanguageByLanguageCode(pLanguageCode);
            if(languageData!=null) {
            	pUserInfo.setLanguageData(languageData);
            }
            
            CountryData countryData = countryBean.getCountryByCountryCode(pCountryCode);
            if(countryData!=null) {
            	pUserInfo.setCountryData(countryData);
            }

            pForm.setUserInfo(pUserInfo);

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            StoreProfileDataVector storeProfileDV = storeBean.getStoreProfile(appUser.getUserStore().getStoreId());
            
            if(storeProfileDV!=null){
            	StoreProfileDto storeProfile = new StoreProfileDto();
            	List selectedLanguageIds = new ArrayList();
            	
            	Iterator it = storeProfileDV.iterator();
            	while(it.hasNext()){
            		StoreProfileData prof = (StoreProfileData)it.next();
          		  
	          		  if(RefCodeNames.STORE_PROFILE_FIELD.PROFILE_NAME.equals(prof.getShortDesc())){
	          			  storeProfile.setProfileNameDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setProfileNameEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.LANGUAGE.equals(prof.getShortDesc())){
	          			  storeProfile.setLanguageDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setLanguageEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.COUNTRY.equals(prof.getShortDesc())){
	          			  storeProfile.setCountryDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setCountryEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.CONTACT_ADDRESS.equals(prof.getShortDesc())){
	          			  storeProfile.setContactAddressDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setContactAddressEdit(Utility.isTrue(prof.getEdit()));
	          		
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.PHONE.equals(prof.getShortDesc())){
	          			  storeProfile.setPhoneDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setPhoneEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.MOBILE.equals(prof.getShortDesc())){
	          			  storeProfile.setMobileDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setMobileEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.FAX.equals(prof.getShortDesc())){
	          			  storeProfile.setFaxDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setFaxEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.EMAIL.equals(prof.getShortDesc())){
	          			  storeProfile.setEmailDisplay(Utility.isTrue(prof.getDisplay()));
	          			  storeProfile.setEmailEdit(Utility.isTrue(prof.getEdit()));
	          			  
	          		  }else if(RefCodeNames.STORE_PROFILE_FIELD.CHANGE_PASSWORD.equals(prof.getShortDesc())){
	          			  
	          			AccountData accD = appUser.getUserAccount();
                        String allowChangePass = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
                        
                        if(Utility.isTrue(prof.getDisplay()) && allowChangePass.equalsIgnoreCase("true")){
                        	storeProfile.setChangePassword(true);
                        }
	  
	          		  } else {
	          	            if(RefCodeNames.STORE_PROFILE_TYPE_CD.LANGUAGE_OPTION.equals(prof.getOptionTypeCd())){
	          			        if (Utility.isSet(prof.getShortDesc())) {
	          				        selectedLanguageIds.add(prof.getShortDesc());
                                }
	          			    }		  
	          		  }
            	}

          	    if(session.getAttribute("store.languages.options")==null){

            		LanguageDataVector lDV = null;
            		int langCount = 0;
            		if (selectedLanguageIds != null && selectedLanguageIds.size() > 0) {
                        lDV = languageBean.getLanguagesInList(selectedLanguageIds);
                    }
                    if (lDV == null || lDV.size() == 0) {
                        lDV = languageBean.getSupportedLanguages();
                    }
                    DisplayListSort.sort(lDV, "uiName");

            		List<LabelValueBean> langList = new ArrayList();


                    langCount = lDV.size();
                    if (langCount > 0) {
                        String[] storeLanguages = new String[langCount];
                        Iterator it2 = lDV.iterator();
                        int ii=0;
                        while(it2.hasNext()){
                            LanguageData lData = (LanguageData)it2.next();
                            storeLanguages[ii] = Integer.toString(lData.getLanguageId());
                            langList.add(new LabelValueBean(lData.getUiName(),Integer.toString(lData.getLanguageId())));
                            ii++;
                        }
                        storeProfile.setStoreLanguages(storeLanguages);
                    }
               		session.setAttribute("store.languages.options",langList);
            	}
            	
            	pForm.setStoreProfile(storeProfile);
            }
            
            if (session.getAttribute("store.countries.options") == null) {
        		CountryDataVector countriesv = countryBean.getAllCountries();
        		List<LabelValueBean> countryList = new ArrayList();
        		for(int i=0; i<countriesv.size(); i++){
        			CountryData cData = (CountryData)countriesv.get(i);
            		countryList.add(new LabelValueBean(cData.getShortDesc(),cData.getShortDesc()));
            	}
        		session.setAttribute("store.countries.options", countryList);
        	}
            
        }catch(Exception exc){
        	//exc.printStackTrace();//STJ-5794
        	log.error("Unable to retrieve user detail for userId:"+pUserId);
        	String errorMess =ClwI18nUtil.getMessage(request,"shop.errors.missingUserInformation1",null);
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
        
        return errors;
	}

	public static ActionErrors updateUserDetail(HttpServletRequest pRequest, UserProfileForm pForm) {
		
		ActionErrors errors = new ActionErrors();
		HttpSession session = pRequest.getSession();
		UserInfoData pUserInfo = pForm.getUserInfo();
		if(pUserInfo==null){
			String errorMess =ClwI18nUtil.getMessage(pRequest,"shop.errors.missingUserInformation1",null);
			errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			return errors;
		}
		
		try{
        	APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();
            Language languageBean = factory.getLanguageAPI();
            Country countryBean = factory.getCountryAPI(); 
            
            errors = validate(pRequest, pUserInfo);
            if (errors.size() > 0) {
                return errors;
            }
            
            UserData user = pUserInfo.getUserData();
            user.setFirstName(I18nUtil.getUtf8Str(user.getFirstName().trim()));
            user.setLastName(I18nUtil.getUtf8Str(user.getLastName().trim()));
            AddressData ad = pUserInfo.getAddressData();
            if(ad.getAddress1()!=null){
            	ad.setAddress1(I18nUtil.getUtf8Str(ad.getAddress1().trim()));
            }
            if(ad.getAddress2()!=null){
            	ad.setAddress2(I18nUtil.getUtf8Str(ad.getAddress2().trim()));
            }
            if(ad.getCity()!=null){
            	ad.setCity(I18nUtil.getUtf8Str(ad.getCity().trim()));
            }
            if(ad.getStateProvinceCd()!=null){
            	ad.setStateProvinceCd(I18nUtil.getUtf8Str(ad.getStateProvinceCd().trim()));
            }
            if(ad.getPostalCode()!=null){
            	ad.setPostalCode(I18nUtil.getUtf8Str(ad.getPostalCode().trim()));
            }
            
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            
            CountryData countryData = null;
            String pCountry =  pUserInfo.getCountryData().getShortDesc();
            
            countryData = countryBean.getCountryByShortDesc(pCountry);
            if(countryData!=null) {
            	pUserInfo.setCountryData(countryData);
            	ad.setCountryCd(I18nUtil.getUtf8Str(countryData.getShortDesc()));
            }
            
            LanguageData langugeData = null;
            int lId = pUserInfo.getLanguageData().getLanguageId();
            
            langugeData = languageBean.getLanguageById(lId);
            if(langugeData!=null) {
            	pUserInfo.setLanguageData(langugeData);
            }
            
            if(langugeData!=null && countryData!=null) {
            	String pPrefLocaleCd = langugeData.getLanguageCode().trim()+"_"+countryData.getCountryCode().trim();
            	pUserInfo.getUserData().setPrefLocaleCd(pPrefLocaleCd);
            	appUser.getUser().setPrefLocaleCd(pPrefLocaleCd);
            }
     
            String emailAddress = pUserInfo.getEmailData().getEmailAddress();
            EmailValidator.validateEmail(pRequest, errors,"shop.userProfile.text.email", null, emailAddress);
            if (errors.size() > 0) {
                return errors;
            }
            
            userBean.updateUserProfile(pUserInfo);
            
            errors = getUserDetailById(pRequest,pUserInfo.getUserData().getUserId(), pForm);

            
		}catch(Exception exc){
        	log.error("Unable to retrieve user detail for userId:"+pUserInfo.getUserData().getUserId());
        	exc.printStackTrace();
        	Object[] args = new Object[1];
        	args[0] = new Integer(pUserInfo.getUserData().getUserId());
        	String errorMess = ClwI18nUtil.getMessage(pRequest, "shop.errors.userNotFound", args);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
         
        }
		
		return errors;
	}
	
	
	public static ActionErrors updatePassword(HttpServletRequest pRequest, UserProfileForm pForm){
		
		ActionErrors errors = new ActionErrors();
        String errorMess = "";
        
        CleanwiseUser appUser = (CleanwiseUser) pRequest.getSession().getAttribute(Constants.APP_USER);
        if (pForm.getUserInfo().getUserData().getUserId() == 0){
    		pForm.getUserInfo().setUserData(appUser.getUser());
    	}
        AccountData accD = appUser.getUserAccount();
        String allowChangePass = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
        
        if(allowChangePass == null || !allowChangePass.equalsIgnoreCase("true")){
        	errorMess = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.error.changePasswordNotAllowed",null);
            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
            return errors;
        }
        
        String pOldPassword = pForm.getOldPassword();
        String pNewPassword = pForm.getNewPassword();
        String pConfirmPassword = pForm.getConfirmPassword();
        
        if(!Utility.isSet(pOldPassword)){
    		Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.text.oldPassword",null);
    		errorMess = ClwI18nUtil.getMessage(pRequest, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    	}
        
        if(!Utility.isSet(pNewPassword)){
    		Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.text.newPassword",null);
    		errorMess = ClwI18nUtil.getMessage(pRequest, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));         
    	}
        
        if(!Utility.isSet(pConfirmPassword)){
    		Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.text.confirmPassword",null);
    		errorMess = ClwI18nUtil.getMessage(pRequest, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));         
    	}
        
        if (errors.size() > 0) {
            return errors;
        }
        LdapItemData ldapItemData = new LdapItemData();
        ldapItemData.setUserName(pForm.getUserInfo().getUserData().getUserName());
        ldapItemData.setPassword(pOldPassword);
        
        try {
        	APIAccess factory = new APIAccess();
        	User userBean = factory.getUserAPI();
            userBean.login(ldapItemData, "");
            if (!pNewPassword.equals(pConfirmPassword)) {
                errorMess = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.error.confirmPasswordNotMatch", null);
                errors.add("error", new ActionError("error.simpleGenericError",errorMess));
                return errors;
            }
            
            if (pNewPassword.equals(pOldPassword)) {
                errorMess = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.error.newPasswordMustDifferentThanCurrPassword", null);
                errors.add("error", new ActionError("error.simpleGenericError",errorMess));
                return errors;
            }            
            
            UserData user = pForm.getUserInfo().getUserData();
    		user.setPassword(PasswordUtil.getHash(user.getUserName(), pNewPassword));
    		user.setModBy((String)pRequest.getSession().getAttribute(Constants.USER_NAME));
    		
    		userBean.updateUser(user, pForm.getUserInfo().getUserData().getUserId(), true);
			errors = getUserDetailById(pRequest,pForm.getUserInfo().getUserData().getUserId(), pForm);
    		
        } catch (InvalidLoginException ile) {
            errorMess = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.error.oldNewPasswordNotMatch",null);
            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
        } catch (Exception exc) {
			exc.printStackTrace();
			errorMess = exc.getMessage();
            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
		}
        return errors;
        
	}
	
    public static ActionErrors validate(HttpServletRequest request, UserInfoData userInfo) {
    	
    	ActionErrors errors = new ActionErrors();
    	
    	HttpSession session = request.getSession();
    	if ((session == null) || (session.getAttribute(Constants.APIACCESS) == null) 
    			|| (session.getAttribute(Constants.APP_USER) == null)) {
    		return null;
        }  
    	
    	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    	
    	if(!Utility.isSet(userInfo.getUserData().getFirstName())){
    		Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(request,"shop.userProfile.text.firstName",null);
    		String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));         
    	} 
    	        
    	if (!Utility.isSet(userInfo.getUserData().getLastName())) {
    		Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(request,"shop.userProfile.text.lastName",null);
    		String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));        
    	} 
          
    	if(!(userInfo.getLanguageData().getLanguageId()>0)){
    		Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(request,"shop.userProfile.text.prefLanguage",null);
    		String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess)); 
    	}       

    	if(!Utility.isSet(userInfo.getEmailData().getEmailAddress())){
    		//removed for bug 4255
    		/*Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(request,"shop.userProfile.text.email",null);
    		String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));*/ 
    	}else{
    		String emailAddress = userInfo.getEmailData().getEmailAddress().trim();
            EmailValidator.validateEmail(request, errors,"shop.userProfile.text.email", null, emailAddress);
    	}
    	
        if (!Utility.isSet(userInfo.getCountryData().getShortDesc())) {
        	Object[] obj =new Object[1];
    		obj[0] = ClwI18nUtil.getMessage(request,"shop.userProfile.text.country",null);
    		String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.requiredField", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));         
        }        
                     
        return errors;

    }
	
}
