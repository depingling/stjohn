/*
 * BlanketPoNumForm.java
 *
 * Created on February 9, 2005, 11:57 AM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.BlanketPoNumDescData;
import com.cleanwise.view.utils.SelectableObjects;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author bstevens
 */
public class BlanketPoNumForm extends ActionForm {

    /**
     * Holds value of property BlanketPoNumDescData.
     */
    private BlanketPoNumDescData BlanketPoNumDescData;

    /**
     * Holds value of property currentRelease.
     */
    private String currentRelease;

    /**
     * Holds value of property searchType.
     */
    private String searchType="nameBegins";

    /**
     * Holds value of property searchField.
     */
    private String searchField;

    /**
     * Holds value of property results.
     */
    private SelectableObjects results;

    /**
     * Holds value of property configType.
     */
    private String configType;

    /**
     * Holds value of property resultsType.
     */
    private String resultsType;
    
    /** Creates a new instance of BlanketPoNumForm */
    public BlanketPoNumForm() {
    }

    /**
     * Getter for property BlanketPoNumDescData.
     * @return Value of property BlanketPoNumDescData.
     */
    public BlanketPoNumDescData getBlanketPoNumDescData() {

        return this.BlanketPoNumDescData;
    }

    /**
     * Setter for property BlanketPoNumDescData.
     * @param BlanketPoNumDescData New value of property BlanketPoNumDescData.
     */
    public void setBlanketPoNumDescData(BlanketPoNumDescData BlanketPoNumDescData) {

        this.BlanketPoNumDescData = BlanketPoNumDescData;
    }

    /**
     * Getter for property currentRelease.
     * @return Value of property currentRelease.
     */
    public String getCurrentRelease() {

        return this.currentRelease;
    }

    /**
     * Setter for property currentRelease.
     * @param currentRelease New value of property currentRelease.
     */
    public void setCurrentRelease(String currentRelease) {

        this.currentRelease = currentRelease;
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
     * Getter for property results.
     * @return Value of property results.
     */
    public SelectableObjects getResults() {

        return this.results;
    }

    /**
     * Setter for property results.
     * @param results New value of property results.
     */
    public void setResults(SelectableObjects results) {

        this.results = results;
    }

    /**
     * Getter for property configType.
     * @return Value of property configType.
     */
    public String getConfigType() {

        return this.configType;
    }

    /**
     * Setter for property configType.
     * @param configType New value of property configType.
     */
    public void setConfigType(String configType) {

        this.configType = configType;
    }

    /**
     * Getter for property resultsType.
     * @return Value of property resultsType.
     */
    public String getResultsType() {

        return this.resultsType;
    }

    /**
     * Setter for property resultsType.
     * @param resultsType New value of property resultsType.
     */
    public void setResultsType(String resultsType) {

        this.resultsType = resultsType;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reset();
    }
    public void reset(ActionMapping mapping, ServletRequest request) {
       reset();
    }
    private void reset(){
        if(results != null){
            results.handleStutsFormResetRequest();
        }
    }
    
}
