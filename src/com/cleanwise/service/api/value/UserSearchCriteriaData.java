package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
import java.util.List;

public class UserSearchCriteriaData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4926241100527800800L;

    private int    _mAccountId           = 0;
    private String _mUserId              = "";
    private String _mUserName            = "";
    private int    _mUserNameMatch       = 0;
    private String _mFirstName           = "";
    private String _mLastName            = "";
    private String _mUserTypeCd          = "";
    private List _mUserTypes = null;
    private String customerSystemKey;
	private boolean _mIncludeInactiveFl  = true;
    private IdVector storeIds;
    private IdVector _mUserStoreIds;
    private IdVector _accountIds;
    private IdVector _mUserAccountIds;
    private IdVector _mSiteIds;
    private IdVector _mUserIds;

    /**
     *Convienience method so caller does not need to construct an IdVector and add a stor id to it.
     *<code>
     *UserSearchCriteriaData userCrit = new UserSearchCriteriaData();
     *userCrit.setStoreId(1);
     *</code>
     *Is Equivilent to:
     *<code>
     *UserSearchCriteriaData userCrit = new UserSearchCriteriaData();
     *IdVector storeIds = new IdVector();
     *storeIds.add(new Integer(1));
     *userCrit.setStoreIds(storeIds);
     *</code>
     *If there was anything in the store ids vbefore thi method is called it gets removed.
     */
    public void setStoreId(int storeId){
        if(this.storeIds == null){
            this.storeIds = new IdVector();
        }else{
            this.storeIds.clear();
        }
        storeIds.add(new Integer(storeId));
    }
      
    /**
     * @return Returns the storeIds.
     */
    public IdVector getStoreIds() {
        return storeIds;
    }
    /**
     * @param storeIds The storeIds to set.
     */
    public void setStoreIds(IdVector storeIds) {
        this.storeIds = storeIds;
    }

    public IdVector getUserStoreIds() {
        return _mUserStoreIds;
    }

    public void setUserStoreIds(IdVector pUserStoreIds) {
        this._mUserStoreIds = pUserStoreIds;
    }
    /**
     * @return Returns the customerSystemKey.
     */
    public String getCustomerSystemKey() {
        return customerSystemKey;
    }
    /**
     * @param customerSystemKey The customerSystemKey to set.
     */
    public void setCustomerSystemKey(String customerSystemKey) {
        this.customerSystemKey = customerSystemKey;
    }
    public UserSearchCriteriaData() {
        this._mAccountId            = 0;
        this._mUserId               = new String("");
        this._mUserName             = new String("");
        this._mUserNameMatch        = 0;
        this._mFirstName            = new String("");
        this._mLastName             = new String("");
        this._mUserTypeCd           = new String("");
        this._mUserTypes            = null;
        this._mIncludeInactiveFl    = true;
        _accountIds                 = new IdVector();
    }

    /**
     * Creates a new UserSearchCriteriaData
     *
     * @return
     *  Newly initialized UserSearchCriteriaData object.
     */
    public static UserSearchCriteriaData createValue () 
    {
        UserSearchCriteriaData valueData = new UserSearchCriteriaData();

        return valueData;
    }
    

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserSearchCriteriaData object
     */
    public String toString()
    {
        
        return "[" + "AccountId=" + _mAccountId + ", UserId=" + _mUserId +
            ", UserName=" + _mUserName + ", UserNameMatch=" + _mUserNameMatch + ", FirstName=" + _mFirstName +
            ", LastName=" + _mLastName + ", UserTypeCd=" + _mUserTypeCd + ", IncludeInactiveFl=" + _mIncludeInactiveFl +
            ", UserTypes=" + _mUserTypes +    
            "]";
    }
    
    /**
     * <code>getAccountId</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getAccountId() {
        return (this._mAccountId);
    }

    /**
     * <code>setAccountId</code> method.
     *
     * @param pVal a <code>int</code> value
     */
    public void setAccountId(int pVal) {
        this._mAccountId = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getUserId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserId() {
        return (this._mUserId);
    }

    /**
     * <code>setUserId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserId(String pVal) {
        this._mUserId = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getUserName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserName() {
        return (this._mUserName);
    }

    /**
     * <code>setUserName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserName(String pVal) {
        this._mUserName = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getUserNameMatch</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getUserNameMatch() {
        return (this._mUserNameMatch);
    }

    /**
     * <code>setUserNameMatch</code> method.
     *
     * @param pVal a <code>int</code> value
     */
    public void setUserNameMatch(int pVal) {
        this._mUserNameMatch = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getFirstName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getFirstName() {
        return (this._mFirstName);
    }

    /**
     * <code>setFirstName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setFirstName(String pVal) {
        this._mFirstName = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getLastName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getLastName() {
        return (this._mLastName);
    }

    /**
     * <code>setLastName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setLastName(String pVal) {
        this._mLastName = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getUserTypeCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserTypeCd() {
        return (this._mUserTypeCd);
    }

    /**
     * <code>setUserTypeCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserTypeCd(String pVal) {
        this._mUserTypeCd = pVal;
        setDirty(true);
    }
    
    /**
     * <code>getUserTypes</code> method.
     *
     * @return a <code>String</code> value
     */
    public List getUserTypes() {
        return (this._mUserTypes);
    }

    /**
     * <code>setUserTypes</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserTypes(List pVal) {
        this._mUserTypes = pVal;
        setDirty(true);
    }

    /**
     * <code>getIncludeInactiveFl</code> method.
     *
     * @return a <code>boolean</code> value
     */
    public boolean getIncludeInactiveFl() {
        return (this._mIncludeInactiveFl);
    }

    /**
     * <code>setIncludeInactiveFl</code> method.
     *
     * @param pVal a <code>boolean</code> value
     */
    public void setIncludeInactiveFl(boolean pVal) {
        this._mIncludeInactiveFl = pVal;
        setDirty(true);
    }

    /**
     * @param accountIds The accountIds to set.
     */
    public void setAccountIds(IdVector accountIds) {
        _accountIds = accountIds;
    }

    /**
     * @return Returns the accountIds.
     */
    public IdVector getAccountIds() {
        return _accountIds;
    }


    public IdVector getUserAccountIds() {
        return _mUserAccountIds;
    }

    public void setUserAccountIds(IdVector pUserAccountIds) {
        this._mUserAccountIds = pUserAccountIds;
    }

    public IdVector getSiteIds() {
        return  _mSiteIds;
    }

    public void setSiteIds(IdVector pSiteIds) {
        this._mSiteIds = pSiteIds;
    }

    public void setUserIds(IdVector pUserIds) {
        this._mUserIds = pUserIds;
    }

    public IdVector getUserIds() {
        return _mUserIds;
    }
}
