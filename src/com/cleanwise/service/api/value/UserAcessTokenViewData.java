/*
 * UserAcessTokenViewData.java
 *
 * Created on August 16, 2004, 11:22 AM
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
/**
 *
 * @author  bstevens
 */
public class UserAcessTokenViewData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4070406530286695150L;
    
    /**
     * Holds value of property userData.
     */
    private UserData userData;
    
    /**
     * Holds value of property accessToken.
     */
    private String accessToken;
    
    /**
     * Holds value of property userSites.
     */
    private IdVector userSiteIds;
 
    
    /**
     * Holds value of property defaultSite
     */
    private String defaultSite;
    
    /** Creates a new instance of UserAcessTokenViewData */
    public UserAcessTokenViewData() {
    }
    
    /**
     * Getter for property userData.
     * @return Value of property userData.
     */
    public UserData getUserData() {
        return this.userData;
    }
    
    /**
     * Setter for property userData.
     * @param userData New value of property userData.
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
    
    /**
     * Getter for property accessToken.
     * @return Value of property accessToken.
     */
    public String getAccessToken() {
        return this.accessToken;
    }
    
    /**
     * Setter for property accessToken.
     * @param accessToken New value of property accessToken.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    /**
     * Getter for property userSiteIds.
     * @return Value of property userSites.
     */
    public IdVector getUserSiteIds() {
        return this.userSiteIds;
    }
    
    /**
     * Setter for property userSiteIds.
     * @param userSites New value of property userSites.
     */
    public void setUserSiteIds(IdVector userSiteIds) {
        this.userSiteIds = userSiteIds;
    }

    /**
     * Getter for property defaultSite
     * @return Value of property defaultSite.
     */
    public String getDefaultSite() {
        return this.defaultSite;
    }
    
    /**
     * Setter for property defaultSite.
     * @param accessToken New value of property defaultSite.
     */
    public void setDefaultSite(String pDefaultSite) {
        this.defaultSite = pDefaultSite;
    }
}
