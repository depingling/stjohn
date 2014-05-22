package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.view.utils.SelectableObjects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import java.util.Hashtable;

/**
 * @author Evgeny Vlasov
 * Date: 12/04/2006
 */
public class StoreGroupForm extends StorePortalBaseForm {

    /** Holds value of property groupName. */
    private String groupName;

    /** Holds value of property removeGroupIdS. */
    private String removeGroupIdS;

    /** Holds value of property groupType. */
    private String groupType;

    /** Holds value of property groupData. */
    private GroupData groupData;

    /** Holds value of property relationshipName. */
    private String relationshipName;

    /** Holds value of property reports. */
    private String[] reports;

    /** Holds value of property applicationFunctions. */
    private String[] applicationFunctions;

    /** Holds value of property groupNameSelect. */
    private String groupNameSelect;

    /** Holds value of property configSearchField. */
    private String configSearchField;

    /** Holds value of property configSearchType. */
    private String configSearchType = "nameBegins";

    /** Holds value of property configType. */
    private String configType;

    /** Holds value of property configResults. */
    private SelectableObjects configResults;

    /** Holds value of property configResultsType. */
    private String configResultsType;

    /**
     * Holds value of property searchStoreId.
     */
    private String searchStoreId;
    /**
     * flag for enabling search items which are binded
     */
    private boolean showConfiguredOnlyFl;
    /**
     * Information about store that group binds to
     */
    private Hashtable storeInfoOfGroup = new Hashtable();
    /**
     * flag for binding store to group
     */
    private boolean assignGroupStoreAssocFl;

    /**
     * flag for searching inactive stores
     */
    private boolean showGroupInactiveFl = false;

    /** Filter for the first user name. */
    private String _firstUserName;

    /** Filter for the last user name. */
    private String _lastUserName;

    /** Accounts filter. */
    private AccountDataVector _accountFilter;
    
    private boolean _showGropTypeFl = false;

    /** Creates a new instance of GroupForm */
    public StoreGroupForm() {
        _firstUserName = "";
        _lastUserName = "";
        _accountFilter = new AccountDataVector();       
    }

    /** Getter for property newGroupName.
     * @return Value of property newGroupName.
     *
     */
    public String getGroupName() {
        return this.groupName;
    }

    /** Setter for property newGroupName.
     * @param groupName New value of property newGroupName.
     *
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /** Getter for property removeGroupIdS.
     * @return Value of property removeGroupIdS.
     *
     */
    public String getRemoveGroupIdS() {
        return this.removeGroupIdS;
    }

    /** Setter for property removeGroupIdS.
     * @param removeGroupIdS New value of property removeGroupIdS.
     *
     */
    public void setRemoveGroupIdS(String removeGroupIdS) {
        this.removeGroupIdS = removeGroupIdS;
    }

    /** Getter for property groupType.
     * @return Value of property groupType.
     *
     */
    public String getGroupType() {
        return this.groupType;
    }

    /** Setter for property groupType.
     * @param groupType New value of property groupType.
     *
     */
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    /** Getter for property groupData.
     * @return Value of property groupData.
     *
     */
    public GroupData getGroupData() {
        return this.groupData;
    }

    /** Setter for property groupData.
     * @param groupData New value of property groupData.
     *
     */
    public void setGroupData(GroupData groupData) {
        this.groupData = groupData;
    }

    /** Getter for property relationshipName.
     * @return Value of property relationshipName.
     *
     */
    public String getRelationshipName() {
        return this.relationshipName;
    }

    /** Setter for property relationshipName.
     * @param relationshipName New value of property relationshipName.
     *
     */
    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    /** Getter for property reports.
     * @return Value of property reports.
     *
     */
    public String[] getReports() {
        return this.reports;
    }

    /** Setter for property reports.
     * @param reports New value of property reports.
     *
     */
    public void setReports(String[] reports) {
        this.reports = reports;
    }

    /** Getter for property applicationFunctions.
     * @return Value of property applicationFunctions.
     *
     */
    public String[] getApplicationFunctions() {
        return this.applicationFunctions;
    }

    /** Setter for property applicationFunctions.
     * @param applicationFunctions New value of property applicationFunctions.
     *
     */
    public void setApplicationFunctions(String[] applicationFunctions) {
        this.applicationFunctions = applicationFunctions;
    }

    /** Getter for property groupNameSelect.
     * @return Value of property groupNameSelect.
     *
     */
    public String getGroupNameSelect() {
        return this.groupNameSelect;
    }

    /** Setter for property groupNameSelect.
     * @param groupNameSelect New value of property groupNameSelect.
     *
     */
    public void setGroupNameSelect(String groupNameSelect) {
        this.groupNameSelect = groupNameSelect;
    }

    /** Getter for property searchField.
     * @return Value of property searchField.
     *
     */
    public String getConfigSearchField() {
        return this.configSearchField;
    }

    /** Setter for property searchField.
     * @param configSearchField New value of property searchField.
     *
     */
    public void setConfigSearchField(String configSearchField) {
        this.configSearchField = configSearchField;
    }

    /** Getter for property searchType.
     * @return Value of property searchType.
     *
     */
    public String getConfigSearchType() {
        return this.configSearchType;
    }

    /** Setter for property searchType.
     * @param configSearchType New value of property searchType.
     *
     */
    public void setConfigSearchType(String configSearchType) {
        this.configSearchType = configSearchType;
    }

    /** Getter for property configType.
     * @return Value of property configType.
     *
     */
    public String getConfigType() {
        return this.configType;
    }

    /** Setter for property configType.
     * @param configType New value of property configType.
     *
     */
    public void setConfigType(String configType) {
        this.configType = configType;
    }

    /** Getter for property configResults.
     * @return Value of property configResults.
     *
     */
    public SelectableObjects getConfigResults() {
        return this.configResults;
    }

    /** Setter for property configResults.
     * @param configResults New value of property configResults.
     *
     */
    public void setConfigResults(SelectableObjects configResults) {
        this.configResults = configResults;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if(getConfigResults() != null){
            getConfigResults().handleStutsFormResetRequest();
        }

        this.showConfiguredOnlyFl    = false;
        this.showGroupInactiveFl     = false;
        this.assignGroupStoreAssocFl = false;
        this.applicationFunctions    = new String[0];
        this.reports                 = new String[0];
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        if(getConfigResults() != null){
            getConfigResults().handleStutsFormResetRequest();
        }

        this.showConfiguredOnlyFl    = false;
        this.showGroupInactiveFl     = false;
        this.assignGroupStoreAssocFl = false;
        this.applicationFunctions    = new String[0];
        this.reports                 = new String[0];
    }

    /** Getter for property configResultsType.
     * @return Value of property configResultsType.
     *
     */
    public String getConfigResultsType() {
        return this.configResultsType;
    }

    /** Setter for property configResultsType.
     * @param configResultsType New value of property configResultsType.
     *
     */
    public void setConfigResultsType(String configResultsType) {
        this.configResultsType = configResultsType;
    }

    /**
     * Getter for property searchStoreId.
     * @return Value of property searchStoreId.
     */
    public String getSearchStoreId() {

        return this.searchStoreId;
    }

    /**
     * Setter for property searchStoreId.
     * @param searchStoreId New value of property searchStoreId.
     */
    public void setSearchStoreId(String searchStoreId) {

        this.searchStoreId = searchStoreId;
    }

    public boolean getShowConfiguredOnlyFl() {
        return showConfiguredOnlyFl;
    }

    public void setShowConfiguredOnlyFl(boolean showConfiguredOnlyFl) {
        this.showConfiguredOnlyFl = showConfiguredOnlyFl;
    }

    public Hashtable getStoreInfoOfGroup() {
        
        if(storeInfoOfGroup == null){
            storeInfoOfGroup = new Hashtable();
        }

        return storeInfoOfGroup;
    }

    public void setStoreInfoOfGroup(Hashtable storeInfoOfGroup) {
        this.storeInfoOfGroup = storeInfoOfGroup;
    }

    public boolean getAssignGroupStoreAssocFl() {
        return assignGroupStoreAssocFl;
    }

    public void setAssignGroupStoreAssocFl(boolean assignGroupStoreAssocFl) {
        this.assignGroupStoreAssocFl = assignGroupStoreAssocFl;
    }

    public void setShowGroupInactiveFl(boolean showGroupInactiveFl){
        this.showGroupInactiveFl = showGroupInactiveFl;    
    }

    public boolean getShowGroupInactiveFl(){
        return this.showGroupInactiveFl;
    }

    public String getFirstUserName() {
        return _firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        _firstUserName = firstUserName;
    }

    public String getLastUserName() {
        return _lastUserName;
    }

    public void setLastUserName(String lastUserName) {
        _lastUserName = lastUserName;
    }

    public void setAccountFilter(AccountDataVector pVal) {
        _accountFilter = pVal;
    }

    public AccountDataVector getAccountFilter() {
        return _accountFilter;
    }
    
    public String getUserTypeCd(){
    	return this.getLocateStoreUserForm().getUserType();
    }

	public boolean getShowGropTypeFl() {
		return _showGropTypeFl;
	}

	public void setShowGropTypeFl(boolean showGropTypeFl) {
		_showGropTypeFl = showGropTypeFl;
	}
    
    
}
