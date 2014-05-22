package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>CopySiteRequest</code>
 */
public class CopySiteRequest extends ValueObject
{
    public interface REQACTION {
	public String CopySite = "Copy Site";
    }

    public CopySiteRequest() { Action = REQACTION.CopySite; }
    public CopySiteRequest(
		       int pSourceSiteId,
		       int pToAccountId) {

	SourceSiteId=pSourceSiteId;
	ToAccountId = pToAccountId;
    }
    int CatalogIdForSite;

    /**
     * Get the CatalogIdForSite value.
     * @return the CatalogIdForSite value.
     */
    public int getCatalogIdForSite() {
	return CatalogIdForSite;
    }

    /**
     * Set the CatalogIdForSite value.
     * @param newCatalogIdForSite The new CatalogIdForSite value.
     */
    public void setCatalogIdForSite(int newCatalogIdForSite) {
	this.CatalogIdForSite = newCatalogIdForSite;
    }

    
    String Action;

    /**
     * Get the Action value.
     * @return the Action value.
     */
    public String getAction() {
	return Action;
    }

    /**
     * Set the Action value.
     * @param newAction The new Action value.
     */
    public void setAction(String newAction) {
	this.Action = newAction;
    }

    

    int ToAccountId;

    /**
     * Get the ToAccountId value.
     * @return the ToAccountId value.
     */
    public int getToAccountId() {
	return ToAccountId;
    }

    /**
     * Set the ToAccountId value.
     * @param newToAccountId The new ToAccountId value.
     */
    public void setToAccountId(int newToAccountId) {
	this.ToAccountId = newToAccountId;
    }

    

    int SourceSiteId;

    /**
     * Get the SourceSiteId value.
     * @return the SourceSiteId value.
     */
    public int getSourceSiteId() {
	return SourceSiteId;
    }

    /**
     * Set the SourceSiteId value.
     * @param newSourceSiteId The new SourceSiteId value.
     */
    public void setSourceSiteId(int newSourceSiteId) {
	this.SourceSiteId = newSourceSiteId;
    }

    

    String UserRoleForAccount;

    /**
     * Get the UserRoleForAccount value.
     * @return the UserRoleForAccount value.
     */
    public String getUserRoleForAccount() {
	return UserRoleForAccount;
    }
    String NewSiteNamePrefix;

    /**
     * Get the NewSiteNamePrefix value.
     * @return the NewSiteNamePrefix value.
     */
    public String getNewSiteNamePrefix() {
	return NewSiteNamePrefix;
    }

    /**
     * Set the NewSiteNamePrefix value.
     * @param newNewSiteNamePrefix The new NewSiteNamePrefix value.
     */
    public void setNewSiteNamePrefix(String newNewSiteNamePrefix) {
	this.NewSiteNamePrefix = newNewSiteNamePrefix;
    }

    
    /**
     * Set the UserRoleForAccount value.
     * @param newUserRoleForAccount The new UserRoleForAccount value.
     */
    public void setUserRoleForAccount(String newUserRoleForAccount) {
	this.UserRoleForAccount = newUserRoleForAccount;
    }

    
    boolean CopyUsersFlag;

    /**
     * Get the CopyUsersFlag value.
     * @return the CopyUsersFlag value.
     */
    public boolean isCopyUsersFlag() {
	return CopyUsersFlag;
    }

    /**
     * Set the CopyUsersFlag value.
     * @param newCopyUsersFlag The new CopyUsersFlag value.
     */
    public void setCopyUsersFlag(boolean newCopyUsersFlag) {
	this.CopyUsersFlag = newCopyUsersFlag;
    }

    public String toString() {
	return "CopySiteRequest { "
	    + " Action=" + Action
	    + " ToAccountId=" + ToAccountId
	    + " SourceSiteId=" + SourceSiteId
	    + " CatalogIdForSite=" + CatalogIdForSite
	    + " UserRoleForAccount=" + UserRoleForAccount
	    + " NewSiteNamePrefix=" + NewSiteNamePrefix
	    + " CopyUsersFlag=" + CopyUsersFlag
	    ;
    }
}
