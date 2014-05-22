package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         01.10.2007
 * Time:         1:03:12
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class StoreAssetWarrantyRelationshipForm extends StorePortalBaseForm {

    AssetWarrantyView editAssetWarranty;
    AssetWarrantyViewVector configResult;
    private WarrantyData warrantyData;
    private String filterVal="";


    public AssetWarrantyViewVector getConfigResult() {
        return configResult;
    }

    public void setConfigResult(AssetWarrantyViewVector configResult) {
        this.configResult = configResult;
    }

    public WarrantyData getWarrantyData() {
        return warrantyData;
    }

    public void setWarrantyData(WarrantyData warrantyData) {
        this.warrantyData = warrantyData;
    }


    public String getFilterVal() {
        return filterVal;
    }

    public void setFilterVal(String filterVal) {
        this.filterVal = filterVal;
    }


    public AssetWarrantyView getEditAssetWarranty() {
        return editAssetWarranty;
    }

    public void setEditAssetWarranty(AssetWarrantyView editAssetWarranty) {
        this.editAssetWarranty = editAssetWarranty;
    }


}
