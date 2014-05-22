package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;

import java.io.File;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.09.2007
 * Time:         15:44:54
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class UserWarrantyMgrForm extends ActionForm {

    public static final String ASSET_NUMBER       = "ASSET_NUMBER";
    public static final String ASSET_NAME         = "ASSET_NAME";
    public static final String WARRANTY_ID        = "WARRANTY_ID" ;
    public static final String WARRANTY_NAME      = "WARRANTY_NAME" ;
    public static final String WARRANTY_PROVIDER  = "WARRANTY_PROVIDER" ;
    public static final String SERVICE_PROVIDER   = "SERVICE_PROVIDER";

    private String mSearchField;
    private String mSearchType;
    private String mSearchFieldType;
    private WarrantyViewVector mSearchResult;
    public boolean init = false;

    public String getSearchField() {
        return mSearchField;
    }

    public void setSearchField(String pValue) {
        this.mSearchField = pValue;
    }

    public String getSearchType() {
        return mSearchType;
    }

    public void setSearchType(String pValue) {
        this.mSearchType = pValue;
    }

    public String getSearchFieldType() {
        return mSearchFieldType;
    }

    public void setSearchFieldType(String searchFieldType) {
        this.mSearchFieldType = searchFieldType;
    }

    public void setSearchResult(WarrantyViewVector result) {
        this.mSearchResult = result;
    }

    public WarrantyViewVector getSearchResult() {
        return mSearchResult;
    }

    public void init() {
        init = true;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        init = false;
    }
}
