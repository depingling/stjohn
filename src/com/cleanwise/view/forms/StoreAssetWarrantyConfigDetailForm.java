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

public class StoreAssetWarrantyConfigDetailForm extends StorePortalBaseForm {

    AssetWarrantyView editAssetWarranty;
    private WarrantyData warrantyData;

    public WarrantyData getWarrantyData() {
        return warrantyData;
    }

    public void setWarrantyData(WarrantyData warrantyData) {
        this.warrantyData = warrantyData;
    }

    public AssetWarrantyView getEditAssetWarranty() {
        return editAssetWarranty;
    }

    public void setEditAssetWarranty(AssetWarrantyView editAssetWarranty) {
        this.editAssetWarranty = editAssetWarranty;
    }


}
