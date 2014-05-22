/*
 * ProfilingMgrSurveyForm.java
 *
 * Created on May 15, 2003, 4:00 PM
 */

package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
/**
 *
 * @author  bstevens
 */
public class ProfilingMgrSurveyForm extends ActionForm {
    
    /** Holds value of property profile. */
    private ProfileViewContainer profile;
    
    /** Holds value of property searchType. */
    private String searchType;
    
    /** Holds value of property searchField. */
    private String searchField;
    
    /** Holds value of property accountResultList. */
    private AccountDataVector accountResultList;
    
    /** Holds value of property updateType. */
    private String updateType;
    
    /** Holds value of property selectableAccountResults. */
    private SelectableObjects selectableAccountResults;
    
    /** Holds value of property temp. */
    private org.apache.struts.upload.FormFile temp;
    
    /** Creates a new instance of ProfilingMgrSurveyForm */
    public ProfilingMgrSurveyForm() {
    }
    
    /** Getter for property profile.
     * @return Value of property profile.
     *
     */
    public ProfileViewContainer getProfile() {
        return this.profile;
    }
    
    /** Setter for property profile.
     * @param survey New value of property profile.
     *
     */
    public void setProfile(ProfileViewContainer profile) {
        this.profile = profile;
    }
    
    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if(getSelectableAccountResults() != null){
            getSelectableAccountResults().handleStutsFormResetRequest();
        }
    }
    
    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, ServletRequest request) {
        if(getSelectableAccountResults() != null){
            getSelectableAccountResults().handleStutsFormResetRequest();
        }
    }
    
    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {
        // No validation necessary.
        return null;
    }
    
    /** Getter for property searchType.
     * @return Value of property searchType.
     *
     */
    public String getSearchType() {
        return this.searchType;
    }
    
    /** Setter for property searchType.
     * @param searchType New value of property searchType.
     *
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    
    /** Getter for property searchField.
     * @return Value of property searchField.
     *
     */
    public String getSearchField() {
        return this.searchField;
    }
    
    /** Setter for property searchField.
     * @param searchField New value of property searchField.
     *
     */
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
    
    /*java.util.ArrayList selected = new java.util.ArrayList();
    public java.util.ArrayList getSelected(){
        return selected;
    }
    
    public String getSelected(int pIndex){
        while(pIndex >= selected.size()){
            selected.add("");
        }
        return (String) selected.get(pIndex);
    }
    
    java.util.ArrayList origSelected = new java.util.ArrayList();
    public java.util.ArrayList getOrigSelected(){
        return selected;
    }
    public void setOrigSelected(java.util.ArrayList pOrigSelected){
        origSelected = pOrigSelected;
    }
    
    public void setSelected(int pIndex,String pValue){
        while(pIndex > selected.size()){
            selected.add("");
        }
        selected.add(pIndex,pValue);
    }*/
    
    /** Getter for property accountResultList.
     * @return Value of property accountResultList.
     *
     */
    //public AccountDataVector getAccountResultList() {
    //    return this.accountResultList;
    //}
    
    /** Setter for property accountResultList.
     * @param accountResultList New value of property accountResultList.
     *
     */
    //public void setAccountResultList(AccountDataVector accountResultList) {
    //    this.accountResultList = accountResultList;
    //}
    
    /** Getter for property updateType.
     * @return Value of property updateType.
     *
     */
    public String getUpdateType() {
        return this.updateType;
    }
    
    /** Setter for property updateType.
     * @param updateType New value of property updateType.
     *
     */
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
    
    /** Getter for property selectableAccountResults.
     * @return Value of property selectableAccountResults.
     *
     */
    public SelectableObjects getSelectableAccountResults() {
        return this.selectableAccountResults;
    }
    
    /** Setter for property selectableAccountResults.
     * @param selectableAccountResults New value of property selectableAccountResults.
     *
     */
    public void setSelectableAccountResults(SelectableObjects selectableAccountResults) {
        this.selectableAccountResults = selectableAccountResults;
    }
    
    /** Getter for property temp.
     * @return Value of property temp.
     *
     */
    public org.apache.struts.upload.FormFile getTemp() {
        return this.temp;
    }
    
    /** Setter for property temp.
     * @param temp New value of property temp.
     *
     */
    public void setTemp(org.apache.struts.upload.FormFile temp) {
        this.temp = temp;
    }
    
}
