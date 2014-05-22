package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.AssetWarrantyView;
import com.cleanwise.service.api.value.WarrantyData;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         27.11.2007
 * Time:         11:58:20
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWarrantyConfigDetailMgrForm extends ActionForm {
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
