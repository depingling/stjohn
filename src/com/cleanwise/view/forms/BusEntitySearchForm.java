/*
 * BusEntitySearchForm.java
 *
 * Created on February 21, 2005, 4:46 PM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


/**
 *
 * @author bstevens
 */
public class BusEntitySearchForm extends ValidatorForm  {
    private String _mCity = "";
    private String _mCounty = "";
    private String _mState = "";
    private String PostalCode;
    
    /**
     * Get the value of PostalCode.
     * @return value of PostalCode.
     */
    public String getPostalCode() {
        return PostalCode;
    }
    
    /**
     * Set the value of PostalCode.
     * @param v  Value to assign to PostalCode.
     */
    public void setPostalCode(String  v) {
        this.PostalCode = v;
    }
    
    /**
     *  <code>setCity</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCity(String pVal) {
        this._mCity = pVal;
    }
    
    
    /**
     *  <code>getCity</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCity() {
        return (this._mCity);
    }
    
    /**
     *  <code>setCounty</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCounty(String pVal) {
        this._mCounty = pVal;
    }
    
    
    /**
     *  <code>getCounty</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCounty() {
        return (this._mCounty);
    }
    
    /**
     *  <code>setState</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setState(String pVal) {
        this._mState = pVal;
    }
    
    
    /**
     *  <code>getState</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getState() {
        return (this._mState);
    }
    String RefValue;
    /**
     * Get the value of RefValue.
     * @return value of RefValue.
     */
    public String getRefValue() {
        return RefValue;
    }
    
    /**
     * Set the value of RefValue.
     * @param v  Value to assign to RefValue.
     */
    public void setRefValue(String  v) {
        this.RefValue = v;
    }
    /**
     * Holds value of property searchField.
     */
    private String searchField;
    
    /**
     * Holds value of property searchType.
     */
    private String searchType = "nameContains";
    
    /**
     * Holds value of property parentIdList.
     */
    private String parentIdList;

    /**
     * Holds value of property results.
     */
    private List results;
    
    /** Creates a new instance of BusEntitySearchForm */
    public BusEntitySearchForm() {
    }
    
    /**
     * Getter for property searchField.
     * @return Value of property searchField.
     */
    public String getSearchField() {
        
        return this.searchField;
    }
    
    /**
     * Setter for property searchField.
     * @param searchField New value of property searchField.
     */
    public void setSearchField(String searchField) {
        
        this.searchField = searchField;
    }
    
    /**
     * Getter for property searchType.
     * @return Value of property searchType.
     */
    public String getSearchType() {
        
        return this.searchType;
    }
    
    /**
     * Setter for property searchType.
     * @param searchType New value of property searchType.
     */
    public void setSearchType(String searchType) {
        
        this.searchType = searchType;
    }
    
    /**
     * Getter for property parentIdList.
     * @return Value of property parentIdList.
     */
    public String getParentIdList() {
        
        return this.parentIdList;
    }
    
    /**
     * Setter for property parentIdList.
     * @param parentIdList New value of property parentIdList.
     */
    public void setParentIdList(String parentIdList) {
        
        this.parentIdList = parentIdList;
    }
    
    
    
    /**
     *Creates the search criteria suitable for sending to the EJB layer
     */
    public BusEntitySearchCriteria constructCriteria(HttpServletRequest request){
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        if("id".equals(getSearchType())){
            crit.setSearchId(getSearchField());
        }else if("nameStarts".equals(getSearchType())){
            crit.setSearchName(getSearchField());
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }else{
            crit.setSearchName(getSearchField());
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        }
        
        String aList = getParentIdList();
        if(Utility.isSet(aList)) {         
          IdVector idV = new IdVector();
          StringTokenizer tok = new StringTokenizer(aList,",");
          
          while(tok.hasMoreTokens()){
            String aIdS =tok.nextToken().trim();
            try{
              int id = Integer.parseInt(aIdS);
              idV.add(new Integer(id));
            } catch(Exception exc) {}
          }
          crit.setParentBusEntityIds(idV);
        }
        
        
        //XXX finish up the parsing of the fields
        return crit;
    }

    /**
     * Getter for property results.
     * @return Value of property results.
     */
    public List getResults() {

        return this.results;
    }

    /**
     * Setter for property results.
     * @param results New value of property results.
     */
    public void setResults(List results) {

        this.results = results;
    }
    
    
   /*public ActionMessages validate(ActionMapping mapping,ServletRequest request){
        return super.validate(mapping,request);
    }
    public ActionMessages validate(ActionMapping mapping,HttpServletRequest request){
        return super.validate(mapping,request);
    }*/
}
