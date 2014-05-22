
package com.cleanwise.view.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportView;
import com.cleanwise.service.api.value.GenericReportViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UIConfigData;
import com.cleanwise.service.api.value.UiView;
import com.cleanwise.service.api.value.UserAccountRightsView;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserInfoData;

import com.cleanwise.service.api.value.AllStoreDataVector;


/**
 *  Object that represents a user of the application.
 *
 *@author     Dvieira
 *@created    September 18, 2003
 */
public final class CleanwiseUser implements Serializable, ICleanwiseUser {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4017305707337408700L;
	private static final Logger log = Logger.getLogger(CleanwiseUser.class);

	/**
     *  Constructor for the CleanwiseUser object
     */
    public CleanwiseUser() { }
    
    
    // ----------------------------------------- Instance Variables
    
    private String _userKey = "";
    private String _userName = "";
    private String _userRole = "";
    
    private BusEntityDataVector _distributors = new BusEntityDataVector();
    private BusEntityDataVector _associatedStores = new BusEntityDataVector();
    private BusEntityDataVector _accounts = new BusEntityDataVector();
    private UserData _user = UserData.createValue();
    private StoreData _store = null;
    private IdVector _userStoreAsIdVector = null;
    private AccountData _account = null;
    private SiteData _site = null;
    private int _siteNumber = 0;
    private UIConfigData _UIConfigData = new UIConfigData();
    private String _distributionCenterId = "";
    private UserInfoData _contact = null;
    private Set authorizedForReportNames;
    private GenericReportViewVector authorizedForReports;
    private GenericReportViewVector authorizedForRuntimeReports;
    private Set authorizedForFunctions;
    private UserAccountRightsViewVector mUserAccountRights = null;
    private List contactUsList = new ArrayList();
    private HashMap cachedData = new HashMap();
    private boolean hasNotes = false;
    private UiView ui;
    private int configuredLocationCount = 0;
    private CleanwiseUser originalUser = null;
    private AllStoreDataVector allStoreDataVector = new AllStoreDataVector();

    //STJ-5358
    // is used to fetch user's locale/storeprefix messages.
    private Locale _storePrefixLocale;

    public void setRights(  UserAccountRightsViewVector v ) {
        mUserAccountRights = v;
        resetAccountRights();
    }
    
    public UserAccountRightsViewVector getRights() {
        if ( null == mUserAccountRights )
            mUserAccountRights = new UserAccountRightsViewVector();
        return mUserAccountRights;
    }
    
    public String getRightsForAccount(int pAccountId) {
        for ( int i = 0; null != mUserAccountRights &&
                  i < mUserAccountRights.size();
              i++) {
            UserAccountRightsView uar = (UserAccountRightsView)
                mUserAccountRights.get(i);
            BusEntityData aD = uar.getAccountData();
            if ( pAccountId == aD.getBusEntityId() ) {
                PropertyData pd = uar.getUserSettings();
                if ( pd.getValue() != null &&
                     pd.getValue().length() > 0 ) {
                    return pd.getValue();
                }
            }
        }
        return null;
    }

    public BusEntityDataVector getUserAccountsCollection() {
	BusEntityDataVector ret = new BusEntityDataVector();
        for ( int i = 0; null != mUserAccountRights &&
                  i < mUserAccountRights.size();
              i++) {
            UserAccountRightsView uar = (UserAccountRightsView)
                mUserAccountRights.get(i);
            ret.add(uar.getAccountData());
        }
	return ret;
    }

    /**
     *  Gets the contact attribute of the CleanwiseUser object
     *
     *@return    The contact value
     */
    public UserInfoData getContact() {
        return _contact;
    }
    
    
    /**
     *  Sets the contact attribute of the CleanwiseUser object
     *
     *@param  pContact  The new contact value
     */
    public void setContact(UserInfoData pContact) {
        _contact = pContact;
    }
    
    
    /**
     *  Gets the distributionCenterId attribute of the CleanwiseUser object
     *
     *@return    The distributionCenterId value
     */
    public String getDistributionCenterId() {
        return _distributionCenterId;
    }
    
    
    /**
     *  Sets the distributionCenterId attribute of the CleanwiseUser object
     *
     *@param  pDistributionCenterId  The new distributionCenterId value
     */
    public void setDistributionCenterId(String pDistributionCenterId) {
        _distributionCenterId = pDistributionCenterId;
    }
    
    
    /**
     *  Gets the distributors attribute of the CleanwiseUser object
     *
     *@return    The distributors value
     */
    public BusEntityDataVector getDistributors() {
        return _distributors;
    }
    
    
    /**
     *  Sets the distributors attribute of the CleanwiseUser object
     *
     *@param  pDistributors  The new distributors value
     */
    public void setDistributors(BusEntityDataVector pDistributors) {
        _distributors = pDistributors;
    }

    public BusEntityDataVector getAssociatedStores() {
        return _associatedStores;
    }

    public void setAssociatedStores(BusEntityDataVector pStores) {
        _associatedStores = pStores;
    }

    public BusEntityDataVector getStores() {
        return getActiveStores();
    }

    public BusEntityDataVector getActiveStores() {
        return getActiveStores(this._associatedStores);
    }

    public BusEntityDataVector getActiveStores(BusEntityDataVector pStores) {
        BusEntityDataVector activeEntities = new BusEntityDataVector();
        String status;
        for (Object oEntity : pStores) {
            status = ((BusEntityData) oEntity).getBusEntityStatusCd();
            if (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(status)) {
                activeEntities.add(oEntity);
            }

        }
        return activeEntities;
    }

    /**
     *  Gets the accounts attribute of the CleanwiseUser object
     *
     *@return    The accounts value
     */
    public BusEntityDataVector getAccounts() {
        return _accounts;
    }

    /**
     *  Sets the pAccounts attribute of the CleanwiseUser object
     *
     *@param  pAccounts  The new accounts value
     */
    public void setAccounts(BusEntityDataVector pAccounts) {
        _accounts = pAccounts;

    }
    
    /**
     *  Gets the uIConfigData attribute of the CleanwiseUser object
     *
     *@return    The uIConfigData value
     */
    public UIConfigData getUIConfigData() {
        return _UIConfigData;
    }
    
    
    /**
     *  Sets the uIConfigData attribute of the CleanwiseUser object
     *
     *@param  v  The new uIConfigData value
     */
    public void setUIConfigData(UIConfigData v) {
        _UIConfigData = v;
    }
    
    /**
     *  Gets the userStore attribute of the CleanwiseUser object
     *
     *@return    The userStore value
     */
    public StoreData getUserStore() {
        return _store;
    }
    
    
    /**
     *  Sets the userStore attribute of the CleanwiseUser object
     *
     *@param  v  The new userStore value
     */
    public void setUserStore(StoreData v) {
        _store = v;
        if(_userStoreAsIdVector == null){
            _userStoreAsIdVector = new IdVector();
        }else{
            _userStoreAsIdVector.clear();
        }
        if(_store != null && _store.getBusEntity() != null){
            _userStoreAsIdVector.add(new Integer(_store.getBusEntity().getBusEntityId()));
        }
    }
    
    
     public IdVector getUserStoreAsIdVector(){
        return _userStoreAsIdVector;
    }
    
    /**
     *  Gets the userAccount attribute of the CleanwiseUser object
     *
     *@return    The userAccount value
     */
    public AccountData getUserAccount() {
        return _account;
    }
    
    
    /**
     *  Sets the userAccount attribute of the CleanwiseUser object
     *
     *@param  v  The new userAccount value
     */
    public void setUserAccount(AccountData v) {
        _account = v;
        resetAccountRights();
    }
    
    private void resetAccountRights() {
        int aid = _account.getBusEntity().getBusEntityId();
        String t = getRightsForAccount(aid);
        if ( t != null && t.length() > 0 ) {
            _user.setUserRoleCd(t);
        }
    }
    
    /**
     *Returns a GenericReportViewVector of all the reports that this user has access to.
     */
    public GenericReportViewVector getAuthorizedReports(){
        return authorizedForReports;
    }
    
    /**
     *Returns a GenericReportViewVector of all the reports that this user has access to that are also allowed
     *to be shown in the runtime (customer) portal.
     */
    public GenericReportViewVector getAuthorizedRuntimeReports(){
        return authorizedForRuntimeReports;
    }
    
    
    private GenericReportView toGenericReportView(GenericReportData pGenericReportData){
        GenericReportData data = pGenericReportData;
        GenericReportView view = GenericReportView.createValue();
        view.setGenericReportId(data.getGenericReportId());
        view.setLongDesc(data.getLongDesc());
        view.setReportCategory(data.getCategory());
        view.setReportName(data.getName());
        view.setDBName(data.getDbName());
        if(Utility.isSet(data.getClassname())){
     	   view.setReportClass(data.getClassname());
        }
        return view;
    }

    private static boolean isInReportList(
       GenericReportViewVector v, int pRepId ) {
       if ( null == v ) {
           return false;
       }
       for ( int i = 0; i < v.size(); i++ ) {
           GenericReportView thisGenericReportView
             = (GenericReportView)v.get(i);
           if ( thisGenericReportView.getGenericReportId() == pRepId ) {
               return true;
           }
       }

       return false;
    }
    
    /**
     *  Sets the authorizedFor attribute of the CleanwiseUser object
     *
     *@param  pMap  The new authorizedFor value
     */
    public void setAuthorizedFor(Map pMap) {
        authorizedForReportNames = new HashSet();
        authorizedForReports = new GenericReportViewVector();
        authorizedForRuntimeReports = new GenericReportViewVector();
        authorizedForFunctions = new HashSet();
        if(pMap == null){
            return;
        }
        
        Set reports = (Set) pMap.get(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
        if(reports == null){
            authorizedForReportNames.clear();
            authorizedForReports.clear();
            authorizedForRuntimeReports.clear();
        }else{

            Iterator it = reports.iterator();
            while(it.hasNext()){
                GenericReportData rp = (GenericReportData) it.next();
		if ( isInReportList(authorizedForReports, rp.getGenericReportId()) == false) {
                if(Utility.isTrue(rp.getRuntimeEnabled())){
                    authorizedForRuntimeReports.add(toGenericReportView(rp));
                }
                authorizedForReportNames.add(rp.getName());
                authorizedForReports.add(toGenericReportView(rp));
		     }
            }
        }
        
        authorizedForFunctions = (Set) pMap.get(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);
        if(authorizedForFunctions == null){
            authorizedForFunctions = new HashSet();
        }
    }
    
    
    /**
     *  The state of the firm manager page.
     */
    private InformationState _storeState = new InformationState();
    
    
    // ----------------------------------------------- Properties
    
    
    /**
     *  Return the firm manager state object.
     *
     *@return    The storeState value
     */
    public InformationState getStoreState() {
        return (this._storeState);
    }
    
    
    /**
     *  Set the firm manager state object.
     *
     *@param  state  The new storeState value
     */
    public void setStoreState(InformationState state) {
        this._storeState = state;
    }
    
    
    /**
     *  Retrieves the user key of the current user.
     *
     *@return    String containing the user key.
     */
    public String getUserKey() {
        return (this._userKey);
    }
    
    
    /**
     *  Sets the user key of the current user.
     *
     *@param  key  The user key of the current user.
     */
    public void setUserKey(String key) {
        this._userKey = key;
    }
    
    
    /**
     *  Retrieves the full name of the current user.
     *
     *@return    String containing the full name.
     */
    public String getUserName() {
        return (this._userName);
    }
    
    
    /**
     *  Sets the full name of the current user.
     *
     *@param  name  The full name of the current user.
     */
    public void setUserName(String name) {
        this._userName = name;
    }
    
    
    /**
     *  Retrieves the role of the current user.
     *
     *@return    String containing the role.
     */
    public String getUserRole() {
        return (this._userRole);
    }
    
    
    /**
     *  Sets the role of the current user.
     *
     *@param  role  The role of the current user.
     */
    public void setUserRole(String role) {
        this._userRole = role;
    }
    
    
    /**
     *  Retrieves the object containing information about the application user.
     *
     *@return    Object describing the application user.
     */
    public UserData getUser() {
        return (this._user);
    }
    public int getUserId() {
        return (this._user.getUserId());
    }
    
    
    /**
     *  Sets the object containing information about the application user.
     *
     *@param  user  Object describing the application user.
     */
    public void setUser(UserData user) {
        this._user = user;
    }
    
    /**
     *  Sets the site attribute of the CleanwiseUser object
     *
     *@param  pValue  The new site value
     */
    public void setSite(SiteData pValue) {
        _site = pValue;
    }
    
    
    /**
     *  Gets the site attribute of the CleanwiseUser object
     *
     *@return    The site value
     */
    public SiteData getSite() {
        return _site;
    }
    
    public boolean readyToShop() {

	UserData ud = getUser();
	if (null == ud ) {
            return false;
	}
	String t = ud.getUserTypeCd();
	if ( null == t || 
	     t.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER) ||
	     t.equals(RefCodeNames.USER_TYPE_CD.REGISTRATION_USER) ) {
	    return false;
	}

        if (null == getSite()) {
            return false;
        }
        if (getSite().getSiteCatalogId() <= 0) {
            return false;
        }
        return true;
    }
    
    /**
     *  Sets the siteNumber attribute of the CleanwiseUser object
     *
     *@param  pValue  The new siteNumber value
     */
    public void setSiteNumber(int pValue) {
        _siteNumber = pValue;
    }
    
    
    /**
     *  Gets the siteNumber attribute of the CleanwiseUser object
     *
     *@return    The siteNumber value
     */
    public int getSiteNumber() {
        if (_siteNumber == 0 && null != _site) {
            return 1;
        }
        return _siteNumber;
    }
    
    
    /**
     *  Gets the allowPurchase attribute of the CleanwiseUser object
     *
     *@return    The allowPurchase value
     */
    public boolean getAllowPurchase() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getAllowPurchase();
    }
    
    
    /**
     *  Gets the showPrice attribute of the CleanwiseUser object
     *
     *@return    The showPrice value
     */
    public boolean getShowPrice() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getShowPrice();
    }
    
    public boolean getShowWholeCatalog() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getShowWholeCatalog();
    }
    
    /**
     *  Creates a new <code>isUserOnContract</code> instance.
     *
     *@return    The userOnContract value
     */
    public boolean isUserOnContract() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.isUserOnContract();
    }
    
    
    /**
     *  Creates a new <code>isPresentationOnly</code> instance.
     *
     *@return    The presentationOnly value
     */
    public boolean isPresentationOnly() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.isPresentationOnly();
    }
    
    
    /**
     *  Creates a new <code>isNoReporting</code> instance.
     *
     *@return    The noReporting value
     */
    public boolean isNoReporting() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.isNoReporting();
    }
    
    /**
     *  Determine if the user is allowed to approve purchases.
     *
     *@return a boolean value of true if the user can approve purchases, and false otherwise.
     */
    public boolean isCanApprovePurchases() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.canApprovePurchases();
    }
    
    /**
     *  Determine if the user is browse only.
     *
     *@return a boolean value of true if the user is browse only, and false otherwise.
     */
    public boolean isBrowseOnly() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.isBrowseOnly();
    }
    
    /**
     *  Gets the userOnContract attribute of the CleanwiseUser class
     *
     *@param  pUserRoleCd  Description of the Parameter
     *@return              The userOnContract value
     */
    public static boolean isUserOnContract(String pUserRoleCd) {
        boolean contractItemOnly = (pUserRoleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY) >= 0) ? true : false;
        return contractItemOnly;
    }
    
    
    /**
     *  Gets the presentationOnly attribute of the CleanwiseUser class
     *
     *@param  pUserRoleCd  Description of the Parameter
     *@return              The presentationOnly value
     */
    public static boolean isPresentationOnly(String pUserRoleCd) {
        boolean pres = (pUserRoleCd.indexOf(Constants.UserRole.SALES_PRESENTATION_ONLY) >= 0) ? true : false;
        return pres;
    }
    
    
    /**
     *  Gets the noReporting attribute of the CleanwiseUser class
     *
     *@param  pUserRoleCd  Description of the Parameter
     *@return              The noReporting value
     */
    public static boolean isNoReporting(String pUserRoleCd) {
        boolean pres = (pUserRoleCd.indexOf(Constants.UserRole.NO_REPORTING) >= 0) ? true : false;
        return pres;
    }
    
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean canMakePurchases() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.canMakePurchases();
    }
    public boolean canEditShipTo() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.canEditShipTo();
    }
    public boolean canEditBillTo() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.canEditBillTo();
    }

    private ArrayList _userBillTos;

    /**
     * Holds value of property contactUsList.
     */
    public void setUserBillTos(ArrayList v) {
	_userBillTos = v;
    }
    public ArrayList getUserBillTos() {
	if ( null == _userBillTos ) {
	    _userBillTos = new ArrayList();
	}
	return _userBillTos;
    }

    public boolean hasMultiBillTos() {
	if ( null != _userBillTos &&
	   _userBillTos.size() > 1  ) {
	    return true;
	}
	return false;
    }
    
    /**
     *  Gets the onAccount attribute of the CleanwiseUser object
     *
     *@return    The onAccount value
     */
    public boolean getOnAccount() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getOnAccount();
    }
    
    
    /**
     *  Gets the poNumRequired attribute of the CleanwiseUser object
     *
     *@return    The poNumRequired value
     */
    public boolean getPoNumRequired() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getPoNumRequired();
    }
    
    
    /**
     *  Gets the creditCardFlag attribute of the CleanwiseUser object
     *
     *@return    The creditCardFlag value
     */
    public boolean getCreditCardFlag() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getCreditCardFlag();
    }
    
    
    
    /**
     *  Returns a String representation of the planner user object.
     *
     *@return    Description of the Return Value
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("CleanwiseUser[userData=");
        sb.append((_user != null) ? _user.toString() : "null");
        sb.append("] [accountData=");
        sb.append((_account != null) ? _account.toString() : "null");
        sb.append("] [storeData=");
        sb.append((_store != null) ? _store.toString() : "null");
        sb.append("] [reports=");
        sb.append((authorizedForReportNames != null) ? authorizedForReportNames.toString() : "null");
        sb.append("] [functions=");
        sb.append((authorizedForFunctions != null) ? authorizedForFunctions.toString() : "null");
        sb.append("]");
        return (sb.toString());
    }
    
    
    // Permissions checks.
    /**
        *  Returns true if this user has administrator privileges
     *(User must be an ADMINISTRATOR, SYSTEM_ADMINISTRATOR, or STORE_ADMINISTRATOR
        */
    public boolean isaAdmin() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) || 
                utype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
                utype.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR));
    }

    public boolean isaSystemAdmin() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return utype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR);
    }

	/**
	*  Returns true if this is a registration user
	*/
    public boolean isaRegistrationUser() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.REGISTRATION_USER));
    }
	
	/**
	*  Returns true if this is a distributor user
	*/
	public boolean isaDistributor() {
		if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR));
    }
    
    /**
	*  Returns true if this is a service provider user
	*/
	public boolean isaServiceProvider() {
		if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER));
    }
        
    /**
	*  Returns true if this is a store administrator user
	*/
    public boolean isaStoreAdmin() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR));
    }
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public String getContactTypeCd() {
        if(getUserStore() == null){
            return null;
        }else{
            return Utility.getPropertyValue(getUserStore().getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD);
        }
    }
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean isaAccountAdmin() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR));
    }
    
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean isaCustomer() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER));
    }
    public boolean isaReportingUser() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER));
    }
    
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean isaCustServiceRep() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        boolean retCode = false;
        if(utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE)||
        utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
            retCode = true;
        }
        return retCode;
    }
    
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean isaMSB() {
    	if(_user==null){
    		return false;
    	}
        String utype = _user.getUserTypeCd();
        return (utype.equals(RefCodeNames.USER_TYPE_CD.MSB));
    }
    
    
    /**
     *  Returns true if the user is authorized for the specified report
     *
     *@param  pReport  Description of the Parameter
     *@return          The authorizedForReport value
     */
    public boolean isAuthorizedForReport(String pReport) {
        if (authorizedForReportNames == null) {
            authorizedForReportNames = new HashSet();
        }
        return authorizedForReportNames.contains(pReport);
    }
    
    
    /**
     *  Returns true if the user has any reports they are authorized for
     *
     *@return          true or false
     */
    public boolean isHasReporting() {
        if (authorizedForReportNames == null) {
            authorizedForReportNames = new HashSet();
        }
        return !authorizedForReportNames.isEmpty();
    }
    
    
    
    
    
    /**
     *  Returns true if the user is authorized for the specified report
     *
     *@param  pFunction  Description of the Parameter
     *@return          The authorizedForReport value
     */
    public boolean isAuthorizedForFunction(String pFunction) {
        if (authorizedForFunctions == null) {
            return false;
        }
        return authorizedForFunctions.contains(pFunction);
    }
    
    
    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean hasAccountReports() {
        
        if (getSite() != null) {
            return getSite().getHasBudgets();
        }
        
        return false;
    }

    // ex: record of call for dhhs
    public boolean getOtherPaymentFlag() {
        UserRightsTool ur = new UserRightsTool(_user);
        return ur.getOtherPaymentFlag();
    }

    /**
     * Getter for property contactUsList.
     * @return Value of property contactUsList.
     */
    public List getContactUsList() {
        return this.contactUsList;
    }
    
    /**adds a contact us to the appUser*/
    public void addContactUs(ContactUsInfo info){
        this.contactUsList.add(info);
        Collections.sort(this.contactUsList,ClwComparatorFactory.getContactUsNameComparator());
    }
    
    private Locale mLocale = null;
    public java.util.Locale getPrefLocale() {	
		return getUserLocaleCode(java.util.Locale.US);
	}
    /**
     *Gets the locale code for this user.  Pass in the locale as determined by the session @see SessionTool.getUserLocaleCode
     */
    public Locale getUserLocaleCode(Locale defaultLocale){
      if(mLocale == null){
            if(Utility.isSet(getUser().getPrefLocaleCd())){
                try{
                    mLocale = Utility.parseLocaleCode(getUser().getPrefLocaleCd());
                }catch(Exception e){
                    log.info("Error parsing user configured locale: "+getUser().getPrefLocaleCd());
                }
            }
      }
		
		if(mLocale == null){
		    mLocale = defaultLocale;
		}
		
        return mLocale;
    }

    
    public boolean isUserAllowed(
                 javax.servlet.http.HttpServletRequest request,
                 javax.servlet.http.HttpServletResponse response)
    {
    String utype = _user.getUserTypeCd();
    String userName = _user.getUserName();
    String servletPath = request.getServletPath();


    log.info("##### isUserAllowed, user=" + userName
                       + " user type=" + utype
                       + " servlet path =" + servletPath);
	  if ( utype == null ) {
	    return false;
	  }

	  if ( servletPath.indexOf("/admin") == 0 ) {
	    // Only admins are allowed here.
	    if (utype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
                utype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)) {
		    return true;
	    }
	  }

	  if ( servletPath.indexOf("/console") == 0 ) {
	    // Only admins and customer service reps are allowed here.
	    if (utype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
          utype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
		      utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
          utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)
	    ) {
		    return true;
	    }
	    return false;
	  }
        
    if ( servletPath.indexOf("/estoreclient") == 0 ) {
	    // Only admins and estore clients are allowed here.
	    if (utype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
          utype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
		      utype.equals(RefCodeNames.USER_TYPE_CD.ESTORE_CLIENT)
	    ) {
		    return true;
	    }
	    return false;
	  }

	  return true;
  }
    
    /**
     *Contains a cache of data.  This is useful as when flushing the cache you need only go to one place instead
     *of the session and having to explicetly remove caches one by one.
     */
    public Map getCache(){
        return cachedData;
    }

    /**
     * Holds value of property userProperties.
     */
    private Properties userProperties;

    /**
     * Getter for property userProperties.
     * @return Value of property userProperties.
     */
    public Properties getUserProperties() {
        if ( null == this.userProperties ) {
            userProperties = new Properties();
        }
        return this.userProperties;
    }

    /**
     * Setter for property userProperties.
     * @param userProperties New value of property userProperties.
     */
    public void setUserProperties(Properties userProperties) {
        this.userProperties = userProperties;
    }

    public boolean isHasNotes() {
        return hasNotes;
    }

    public void setHasNotes(boolean hasNotes) {
        this.hasNotes = hasNotes;
    }

	/**
	 * @return the configuredLocationCount
	 */
	public int getConfiguredLocationCount() {
		return configuredLocationCount;
	}

	/**
	 * @param configuredLocationCount the configuredLocationCount to set
	 */
	public void setConfiguredLocationCount(int configuredLocationCount) {
		this.configuredLocationCount = configuredLocationCount;
	}

	public boolean canEditWorkOrder(String workOrderStatus) {

        if (RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(workOrderStatus) ||
            RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(workOrderStatus)) {
            return false;
        } else if (RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(workOrderStatus)) {
            if (this.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER)) {
                return true;
            }
        } else if (RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(workOrderStatus)) {
            return true;
        }
        return false;
    }

    public UiView getUi() {
        return ui;
    }

    public UiView setUi(UiView pUi) {
        return ui = pUi;
    }

    public boolean isSpecialPermissionRequired() {
          StoreData store = getUserStore();
          return store != null && store.isAllowSpecialPermission();
    }

	/**
	 * @return the originalUser
	 */
	public CleanwiseUser getOriginalUser() {
		return originalUser;
	}

	/**
	 * @param originalUser the originalUser to set
	 */
	public void setOriginalUser(CleanwiseUser originalUser) {
		this.originalUser = originalUser;
		//to ensure that changes made by this user while logged in as a proxy user are recorded
		//correctly, update the username value here (and on the user object) to include both
		//the original user and the proxy user
		if (originalUser != null) {
			StringBuilder userName = new StringBuilder(50);
			userName.append(originalUser.getUserName());
			userName.append(Constants.FORWARD_SLASH);
			userName.append(getUserName());
			setUserName(userName.toString());
			getUser().setUserName(userName.toString());
		}
	}
	
	//method to return the username of the logged in user (i.e. the username of the
	//proxy user if one exists, or the name of the single user if not) 
	public String getLoggedInUserName() {
		String returnValue = null;
		if (originalUser != null) {
			returnValue = getUserName().substring(getUserName().indexOf(Constants.FORWARD_SLASH) + 1);
		}
		else {
			returnValue = getUserName();
		}
		return returnValue;
	}
	
	//method to return all functions the logged in user is authorized for
	public Set getAuthorizedForFunctions(){
        return authorizedForFunctions;
	}
	
	//methods for Multi Store DB Schemas
	public AllStoreDataVector getMultiDbStores() {
		return allStoreDataVector;
	}
	
	public void setMultiDbStores(AllStoreDataVector allStoreDataVector) {
		this.allStoreDataVector = allStoreDataVector;	
	}
	
	/**
	 * @return the storePrefixLocale
	 */
    public Locale getStorePrefixLocale() {
		return _storePrefixLocale;
	}

    /**
	 * @param storePrefixLocale the storePrefixLocale to set
	 */
	public void setStorePrefixLocale(Locale storePrefixLocale) {
		_storePrefixLocale = storePrefixLocale;
	}

}

