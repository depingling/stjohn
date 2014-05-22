/*
 * ProfilingMgrSearchForm.java
 *
 * Created on May 15, 2003, 4:00 PM
 */

package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
/**
 *
 * @author  bstevens
 */
public class StoreProfilingMgrSearchForm extends ActionForm {
    
    /** Holds value of property searchType. */
    private String searchType;
    
    /** Holds value of property searchField. */
    private String searchField;
    
    /** Holds value of property searchPofileTypeCd. */
    private String searchPofileTypeCd;
    
    /** Creates a new instance of ProfilingMgrSearchForm */
    public StoreProfilingMgrSearchForm() {
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
    
    /** Getter for property profileTypeCd.
     * @return Value of property profileTypeCd.
     *
     */
    public String getSearchPofileTypeCd() {
        return this.searchPofileTypeCd;
    }
    
    /** Setter for property profileTypeCd.
     * @param profileTypeCd New value of property profileTypeCd.
     *
     */
    public void setSearchPofileTypeCd(String searchPofileTypeCd) {
        this.searchPofileTypeCd = searchPofileTypeCd;
    }
    
    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }
    
     /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, ServletRequest request) {
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

}
