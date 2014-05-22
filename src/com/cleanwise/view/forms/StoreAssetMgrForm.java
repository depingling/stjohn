package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Title:        StoreAssetMgrForm
 * Description:  Form bean for the asset management.
 * Purpose:      Holds data for the asset management
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         8:23:15
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreAssetMgrForm extends StorePortalBaseForm  {

    //search
    private String mSearchField;/*asset value of search*/
    private String mSearchType="begins";/*type of search*/
    private boolean mShowInactiveFl;/*includes  object  in search result where status_cd='INACTIVE'*/
    private AssetViewVector mMainSearchResult;/*search result*/
    private String mStoreSearchField;/*store_id  with which asset  is assigned */
    private String mSiteSearchField;/*site_id  with which asset  is assigned */

    public String getSearchType() {
        return mSearchType;
    }

    public void setSearchType(String searchType) {
        this.mSearchType = searchType;
    }

    public String getSearchField() {
        return mSearchField;
    }

    public void setSearchField(String searchField) {
        this.mSearchField = searchField;
    }

    public boolean getShowInactiveFl() {
        return mShowInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        this.mShowInactiveFl = showInactiveFl;
    }

    public AssetViewVector getMainSearchResult() {
        return mMainSearchResult;
    }

    public void setMainSearchResult(AssetViewVector searchResult) {
        this.mMainSearchResult = searchResult;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      this.mShowInactiveFl=false;
    }


    public String getStoreSearchField() {
        return mStoreSearchField;
    }

    public void setStoreSearchField(String storeSearchField) {
        this.mStoreSearchField = storeSearchField;
    }

    public String getSiteSearchField() {
        return mSiteSearchField;
    }

    public void setSiteSearchField(String siteSearchField) {
        this.mSiteSearchField = siteSearchField;
    }


}

