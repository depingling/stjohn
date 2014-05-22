/**
 * 
 */
package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author ssharma
 *
 */
public class StoreProfileDto implements Serializable{
	
	private boolean profileNameDisplay;
	private boolean languageDisplay;
	private boolean countryDisplay;
	private boolean contactAddressDisplay;
	private boolean phoneDisplay;
	private boolean mobileDisplay;
	private boolean faxDisplay;
	private boolean emailDisplay;
	
	private boolean profileNameEdit;
	private boolean languageEdit;
	private boolean countryEdit;
	private boolean contactAddressEdit;
	private boolean phoneEdit;
	private boolean mobileEdit;
	private boolean faxEdit;
	private boolean emailEdit;
	
	private boolean changePassword;
	
	private String[] storeLanguages;
	
	/**
	 * @return the storeLanguages
	 */
	public final String[] getStoreLanguages() {
		return storeLanguages;
	}

	/**
	 * @param storeLanguages the storeLanguages to set
	 */
	public final void setStoreLanguages(String[] storeLanguages) {
		this.storeLanguages = storeLanguages;
	}

	/**
	 * @return the profileNameDisplay
	 */
	public final boolean isProfileNameDisplay() {
		return profileNameDisplay;
	}

	/**
	 * @param profileNameDisplay the profileNameDisplay to set
	 */
	public final void setProfileNameDisplay(boolean profileNameDisplay) {
		this.profileNameDisplay = profileNameDisplay;
	}

	/**
	 * @return the languageDisplay
	 */
	public final boolean isLanguageDisplay() {
		return languageDisplay;
	}

	/**
	 * @param languageDisplay the languageDisplay to set
	 */
	public final void setLanguageDisplay(boolean languageDisplay) {
		this.languageDisplay = languageDisplay;
	}

	/**
	 * @return the countryDisplay
	 */
	public final boolean isCountryDisplay() {
		return countryDisplay;
	}

	/**
	 * @param countryDisplay the countryDisplay to set
	 */
	public final void setCountryDisplay(boolean countryDisplay) {
		this.countryDisplay = countryDisplay;
	}

	/**
	 * @return the contactAddressDisplay
	 */
	public final boolean isContactAddressDisplay() {
		return contactAddressDisplay;
	}

	/**
	 * @param contactAddressDisplay the contactAddressDisplay to set
	 */
	public final void setContactAddressDisplay(boolean contactAddressDisplay) {
		this.contactAddressDisplay = contactAddressDisplay;
	}

	/**
	 * @return the phoneDisplay
	 */
	public final boolean isPhoneDisplay() {
		return phoneDisplay;
	}

	/**
	 * @param phoneDisplay the phoneDisplay to set
	 */
	public final void setPhoneDisplay(boolean phoneDisplay) {
		this.phoneDisplay = phoneDisplay;
	}

	/**
	 * @return the mobileDisplay
	 */
	public final boolean isMobileDisplay() {
		return mobileDisplay;
	}

	/**
	 * @param mobileDisplay the mobileDisplay to set
	 */
	public final void setMobileDisplay(boolean mobileDisplay) {
		this.mobileDisplay = mobileDisplay;
	}

	/**
	 * @return the faxDisplay
	 */
	public final boolean isFaxDisplay() {
		return faxDisplay;
	}

	/**
	 * @param faxDisplay the faxDisplay to set
	 */
	public final void setFaxDisplay(boolean faxDisplay) {
		this.faxDisplay = faxDisplay;
	}

	/**
	 * @return the emailDisplay
	 */
	public final boolean isEmailDisplay() {
		return emailDisplay;
	}

	/**
	 * @param emailDisplay the emailDisplay to set
	 */
	public final void setEmailDisplay(boolean emailDisplay) {
		this.emailDisplay = emailDisplay;
	}

	/**
	 * @return the profileNameEdit
	 */
	public final boolean isProfileNameEdit() {
		return profileNameEdit;
	}

	/**
	 * @param profileNameEdit the profileNameEdit to set
	 */
	public final void setProfileNameEdit(boolean profileNameEdit) {
		this.profileNameEdit = profileNameEdit;
	}

	/**
	 * @return the languageEdit
	 */
	public final boolean isLanguageEdit() {
		return languageEdit;
	}

	/**
	 * @param languageEdit the languageEdit to set
	 */
	public final void setLanguageEdit(boolean languageEdit) {
		this.languageEdit = languageEdit;
	}

	/**
	 * @return the countryEdit
	 */
	public final boolean isCountryEdit() {
		return countryEdit;
	}

	/**
	 * @param countryEdit the countryEdit to set
	 */
	public final void setCountryEdit(boolean countryEdit) {
		this.countryEdit = countryEdit;
	}

	/**
	 * @return the contactAddressEdit
	 */
	public final boolean isContactAddressEdit() {
		return contactAddressEdit;
	}

	/**
	 * @param contactAddressEdit the contactAddressEdit to set
	 */
	public final void setContactAddressEdit(boolean contactAddressEdit) {
		this.contactAddressEdit = contactAddressEdit;
	}

	/**
	 * @return the phoneEdit
	 */
	public final boolean isPhoneEdit() {
		return phoneEdit;
	}

	/**
	 * @param phoneEdit the phoneEdit to set
	 */
	public final void setPhoneEdit(boolean phoneEdit) {
		this.phoneEdit = phoneEdit;
	}

	/**
	 * @return the mobileEdit
	 */
	public final boolean isMobileEdit() {
		return mobileEdit;
	}

	/**
	 * @param mobileEdit the mobileEdit to set
	 */
	public final void setMobileEdit(boolean mobileEdit) {
		this.mobileEdit = mobileEdit;
	}

	/**
	 * @return the faxEdit
	 */
	public final boolean isFaxEdit() {
		return faxEdit;
	}

	/**
	 * @param faxEdit the faxEdit to set
	 */
	public final void setFaxEdit(boolean faxEdit) {
		this.faxEdit = faxEdit;
	}

	/**
	 * @return the emailEdit
	 */
	public final boolean isEmailEdit() {
		return emailEdit;
	}

	/**
	 * @param emailEdit the emailEdit to set
	 */
	public final void setEmailEdit(boolean emailEdit) {
		this.emailEdit = emailEdit;
	}

	/**
	 * @return the changePassword
	 */
	public final boolean isChangePassword() {
		return changePassword;
	}

	/**
	 * @param changePassword the changePassword to set
	 */
	public final void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}


}