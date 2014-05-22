/*
 * Created on Apr 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

/**
 *
 */
public class UserAssocRequestData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 539237668712612413L;
	private String systemPermissionsKey;
	private String systemPermissionsKeyParent;
	private String siteBudgetReference;
	/**
	 * @return Returns the systemPermissionsKey.
	 */
	public String getCustomerSystemKey() {
		return systemPermissionsKey;
	}
	/**
	 * @param systemPermissionsKey The systemPermissionsKey to set.
	 */
	public void setCustomerSystemKey(String systemPermissionsKey) {
		this.systemPermissionsKey = systemPermissionsKey;
	}
	/**
	 * @return Returns the systemPermissionsKeyParent.
	 */
	public String getCustomerSystemKeyParent() {
		return systemPermissionsKeyParent;
	}
	/**
	 * @param systemPermissionsKeyParent The systemPermissionsKeyParent to set.
	 */
	public void setCustomerSystemKeyParent(String systemPermissionsKeyParent) {
		this.systemPermissionsKeyParent = systemPermissionsKeyParent;
	}
    /**
     * @return Returns the siteBudgetReference.
     */
    public String getSiteBudgetReference() {
        return siteBudgetReference;
    }
    /**
     * @param siteBudgetReference The siteBudgetReference to set.
     */
    public void setSiteBudgetReference(String siteBudgetReference) {
        this.siteBudgetReference = siteBudgetReference;
    }
}
