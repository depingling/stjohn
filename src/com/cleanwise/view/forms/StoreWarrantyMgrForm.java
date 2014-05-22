package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.WarrantyViewVector;
import com.cleanwise.service.api.value.WarrantyDetailView;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Title:        StoreWarrantyMgrForm
 * Description:  Form bean for the warranty management.
 * Purpose:      Holds data for the warranty management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         25.09.2007
 * Time:         16:49:03
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class StoreWarrantyMgrForm extends StorePortalBaseForm  {

    public static final String ASSET_NUMBER       = "ASSET_NUMBER";
    public static final String ASSET_NAME         = "ASSET_NAME";
    public static final String WARRANTY_ID        = "WARRANTY_ID" ;
    public static final String WARRANTY_NAME      = "WARRANTY_NAME" ;
    public static final String WARRANTY_PROVIDER  = "WARRANTY_PROVIDER" ;
    public static final String SERVICE_PROVIDER   = "SERVICE_PROVIDER" ;

    private boolean mShowInactiveFl;
    private String mSearchField;
    private String mSearchType;
    private String mSearchFieldType;
    private WarrantyViewVector mSearchResult;


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

    public boolean getShowInactiveFl() {
        return mShowInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        this.mShowInactiveFl = showInactiveFl;
    }


    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        this.mShowInactiveFl=false;
    }

}
