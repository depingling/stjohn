package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;


public class StoreDiscountFhMgrSearchForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "nameBegins";

    public void setSearchField(String searchField) {
        _searchField = searchField;
    }

    public void setSearchType(String searchType) {
        _searchType = searchType;
    }

    public String getSearchField() {
        return _searchField;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _searchField = "";
        _searchType = "";
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }

}
