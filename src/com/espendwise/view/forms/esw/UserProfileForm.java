/**
 * Title: UserProfileForm 
 * Description: This is the Struts ActionForm class handling the ESW user profile functionality.
 *
 */

package com.espendwise.view.forms.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.dto.StoreProfileDto;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.validators.EmailValidator;

/**
 * Implementation of <code>ActionForm</code> that handles user profile functionality.
 */
public final class UserProfileForm extends EswForm {

	private static final long serialVersionUID = 81170861535055012L;
	
	private UserInfoData userInfo = UserInfoData.createValue();
	private String oldPassword = "";
	private String newPassword = "";
	private String confirmPassword = "";
	private StoreProfileDto storeProfile;

	/**
	 * @return the userInfo
	 */
	public final UserInfoData getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public final void setUserInfo(UserInfoData userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the oldPassword
	 */
	public final String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public final void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the password
	 */
	public final String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param password the password to set
	 */
	public final void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public final String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public final void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
     * Reset all properties to their default values.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	newPassword = "";
    	oldPassword = "";
    	confirmPassword = ""; 
    	super.reset(mapping, request);
    }

	/**
	 * @return the storeProfile
	 */
	public final StoreProfileDto getStoreProfile() {
		return storeProfile;
	}

	/**
	 * @param storeProfile the storeProfile to set
	 */
	public final void setStoreProfile(StoreProfileDto storeProfile) {
		this.storeProfile = storeProfile;
	}


}

