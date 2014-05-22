/*
 * BlanketPoNumSearchForm.java
 *
 * Created on February 9, 2005, 11:57 AM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author bstevens
 */
public class BlanketPoNumSearchForm extends ActionForm {

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
    
    /** Creates a new instance of BlanketPoNumSearchForm */
    public BlanketPoNumSearchForm() {
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
    
}
