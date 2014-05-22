package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.ProductViewDefDataVector;

public final class AccountMgrProductUITemplateForm extends ActionForm {
    private int accountId = 0;

    private String accountName = "";

    private String shopUIProductViewCd = RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DEFAULT;

    private ProductViewDefDataVector data;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public ProductViewDefDataVector getData() {
        return data;
    }

    public void setData(ProductViewDefDataVector data) {
        this.data = data;
    }

    public String getShopUIProductViewCd() {
        return shopUIProductViewCd;
    }

    public void setShopUIProductViewCd(String shopUIProductViewCd) {
        this.shopUIProductViewCd = shopUIProductViewCd;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
