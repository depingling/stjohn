package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.UIConfigData;
import com.cleanwise.service.api.value.UserData;

/**
 * @author Alexander Chikin Date: 15.08.2006 Time: 7:45:37
 * 
 */
public class StoreUIConfigForm extends ActionForm {

	private UIConfigData mUIData;

	private int mBusEntityId;

	private FormFile mLogo1ImageFile;

	private FormFile mLogo2ImageFile;

	private String mLocaleCd = "";

	private String userNameForSearch;
	
	private UserData userInfo;

	/**
	 * Constructor for the StoreUIConfigForm object
	 */
	public StoreUIConfigForm() {
		mUIData = new UIConfigData();
	}

	/**
	 * Sets the Logo1ImageFile attribute of the StoreUIConfigForm object
	 * 
	 * @param v
	 *            The new Logo1ImageFile value
	 */
	public void setLogo1ImageFile(FormFile v) {
		mLogo1ImageFile = v;
	}

	/**
	 * Sets the Logo2ImageFile attribute of the StoreUIConfigForm object
	 * 
	 * @param v
	 *            The new Logo2ImageFile value
	 */
	public void setLogo2ImageFile(FormFile v) {
		mLogo2ImageFile = v;
	}

	/**
	 * Sets the BusEntityId attribute of the StoreUIConfigForm object
	 * 
	 * @param v
	 *            The new BusEntityId value
	 */
	public void setBusEntityId(int v) {
		mBusEntityId = v;
	}

	/**
	 * Sets the Config attribute of the StoreUIConfigForm object
	 * 
	 * @param v
	 *            The new Config value
	 */
	public void setConfig(UIConfigData v) {
		mUIData = v;
	}

	/**
	 * Sets the LocaleCd attribute of the StoreUIConfigForm object
	 * 
	 * @param v
	 *            The new LocaleCd value
	 */
	public void setLocaleCd(String v) {
		mLocaleCd = v;
	}

	/**
	 * Gets the Logo1ImageFile attribute of the StoreUIConfigForm object
	 * 
	 * @return The Logo1ImageFile value
	 */
	public FormFile getLogo1ImageFile() {
		return mLogo1ImageFile;
	}

	/**
	 * Gets the Logo2ImageFile attribute of the StoreUIConfigForm object
	 * 
	 * @return The Logo2ImageFile value
	 */
	public FormFile getLogo2ImageFile() {
		return mLogo2ImageFile;
	}

	/**
	 * Gets the BusEntityId attribute of the StoreUIConfigForm object
	 * 
	 * @return The BusEntityId value
	 */
	public int getBusEntityId() {
		return mBusEntityId;
	}

	/**
	 * Gets the Config attribute of the StoreUIConfigForm object
	 * 
	 * @return The Config value
	 */
	public UIConfigData getConfig() {
		return mUIData;
	}

	/**
	 * Gets the LocaleCd attribute of the StoreUIConfigForm object
	 * 
	 * @return The LocaleCd value
	 */
	public String getLocaleCd() {
		return mLocaleCd;
	}

	/**
	 * Returns the value of <code>userInfo</code> property.
	 *
	 * @return The value of <code>userInfo</code> property.
	 */
	public UserData getUserInfo() {
		return userInfo;
	}

	/**
	 * Sets new value of <code>userInfo</code> property.
	 *
	 * @param userInfo new property value.
	 */
	public void setUserInfo(UserData userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Returns the value of <code>userNameForSearch</code> property.
	 *
	 * @return The value of <code>userNameForSearch</code> property.
	 */
	public String getUserNameForSearch() {
		return userNameForSearch;
	}

	/**
	 * Sets new value of <code>userNameForSearch</code> property.
	 *
	 * @param userNameForSearch new property value.
	 */
	public void setUserNameForSearch(String userNameForSearch) {
		this.userNameForSearch = userNameForSearch;
	}

}
