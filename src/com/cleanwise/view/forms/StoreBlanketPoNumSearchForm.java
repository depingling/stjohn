/*
 * StoreBlanketPoNumSearchForm.java
 *
 * Created on December 4, 2008, 6:54 PM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import org.apache.struts.action.ActionForm;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
/**
 * 
 * @author scher
 */
public class StoreBlanketPoNumSearchForm extends ActionForm {

    private boolean mSearchShowInactiveFl = false;
    /**
     * Holds value of property searchField.
     */
    private String searchField;

    /**
     * Holds value of property searchType.
     */
    private String searchType;

    /**
     * Holds value of property results.
     */
    private BlanketPoNumDataVector results;
    
    /** Creates a new instance of StoreBlanketPoNumSearchForm */
    public StoreBlanketPoNumSearchForm() {
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
     * Getter for property results.
     * @return Value of property results.
     */
    public BlanketPoNumDataVector getResults() {

        return this.results;
    }

    /**
     * Setter for property results.
     * @param results New value of property results.
     */
    public void setResults(BlanketPoNumDataVector results) {

        this.results = results;
    }
 
    // --  Configuration    
    public boolean getSearchShowInactiveFl() {return mSearchShowInactiveFl;}
    public boolean isSearchShowInactiveFl() {return mSearchShowInactiveFl;}
    public void setSearchShowInactiveFl(boolean pVal) { mSearchShowInactiveFl = pVal; }
     
    public void reset(ActionMapping mapping, HttpServletRequest request) {
 
        mSearchShowInactiveFl = false;

    } 
}
