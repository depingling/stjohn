package com.cleanwise.service.api.value;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         24.01.2007
 * Time:         15:18:11
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public abstract class ShoppingCartItemBase implements ItemInterface, Serializable {

    public int _orderNumber = 0;

    public CatalogCategoryData _category = null;
    public ItemShoppingHistoryData _shoppingHistory = null;
    public int _templQuantity = 0;
    public int _quantity = 0;
    public String _quantityString = "";
    public double _price = 0;
    public int _sourceCd = 0;
    public int _sourceId = 0;
    public boolean _contractFlag = false;
    public boolean _duplicateFlag = false;
    public String _actualSkuNum = "";
    public int _actualSkuType = 0;
    public List _shoppingCartHistory = null;

    public abstract Object copy();
    public abstract String toString();




    public List getItemShoppingCartHistory() {
        return _shoppingCartHistory;
    }

    public void setShoppingCartHistory(List pValue) {
        _shoppingCartHistory = new ArrayList();
        if (pValue == null || pValue.size() <= 0) return;

        for (int idx = 0; idx < pValue.size(); idx++) {
            ShoppingInfoData sid =
                    (ShoppingInfoData) pValue.get(idx);
            if (this.isSameItem(sid.getItemId())) {
                _shoppingCartHistory.add(sid);
            }
        }
    }

    private boolean isSameItem(ItemInterface p) {
        return !(p == null ||
                p.getItemData() == null) && isSameItem(p.getItemData().getItemId());
    }

    private boolean isSameItem(int pItemId) {
        return this.getItemData() != null && this.getItemData().getItemId() == pItemId;
    }

    public void setOrderNumber(int pValue) {
        _orderNumber = pValue;
    }

    public int getOrderNumber() {
        return _orderNumber;
    }

    public void setContractFlag(boolean pValue) {
        _contractFlag = pValue;
    }

    public boolean getContractFlag() {
        return _contractFlag;
    }

    public void setSourceCd(int pValue) {
        _sourceCd = pValue;
    }

    public int getSourceCd() {
        return _sourceCd;
    }

    public void setSourceId(int pValue) {
        _sourceId = pValue;
    }

    public int getSourceId() {
        return _sourceId;
    }

    public void setQuantity(int pValue) {
        _quantity = pValue;
    }

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantityString(String pValue) {
        if (pValue != null) {
            pValue = pValue.trim();
        }
        _quantityString = pValue;
    }

    public String getQuantityString() {
        return _quantityString;
    }

    public void setTemplQuantity(int pValue) {
        _templQuantity = pValue;
    }

    public int getTemplQuantity() {
        return _templQuantity;
    }

    public void setPrice(double pValue) {
        _price = pValue;
    }

    public double getPrice() {
        return _price;
    }

    public void setCategory(CatalogCategoryData pValue) {
        _category = pValue;
    }

    public CatalogCategoryData getCategory() {
        return _category;
    }

    public String getCategoryName() {
        if (_category == null) return "";
        return _category.getCatalogCategoryShortDesc();
    }


    public double getAmount() {
		BigDecimal priceBD = new BigDecimal(getPrice());
		priceBD = priceBD.multiply(new BigDecimal(getQuantity()));
		return priceBD.doubleValue();
    }

    public void setDuplicateFlag(boolean pValue) {
        _duplicateFlag = pValue;
    }

    public boolean getDuplicateFlag() {
        return _duplicateFlag;
    }

    public void setShoppingHistory(ItemShoppingHistoryData pValue) {
        _shoppingHistory = pValue;
    }

    public ItemShoppingHistoryData getShoppingHistory() {
        return _shoppingHistory;
    }

    public void setActualSkuNum(String pValue) {
        _actualSkuNum = pValue;
    }

    public String getActualSkuNum() {
        return _actualSkuNum;
    }

    public void setActualSkuType(int pValue) {
        _actualSkuType = pValue;
    }

    public int getActualSkuType() {
        return _actualSkuType;
    }

    public int getItemId() {
        if (
                this.getItemData() == null) {
            return 0;
        }
        return this.getItemData().getItemId();
    }


    public String description() {
      	String os =
	    " [ Sku = " 		+ _actualSkuNum
	    + " Description = " + this.getItemData().getShortDesc()
	    + " ] ";

	return os;
    }

    public String getItemDesc() {
  	if ( null == this.getItemData()||
	     null == this.getItemData().getShortDesc() ){
	    return "";
	}
	return this.getItemData().getShortDesc();
    }
}
