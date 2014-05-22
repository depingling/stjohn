package com.cleanwise.service.api.value;

import com.cleanwise.service.api.util.Utility;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         24.01.2007
 * Time:         15:25:55
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class ShoppingCartServiceData  extends ShoppingCartItemBase {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2793189457452741389L;
   private ServiceData _service;
   public AssetData _assetData;

    public ShoppingCartServiceData(ServiceData serviceData, AssetData assetData) {
        this._service=serviceData;
        this._assetData=assetData;
    }

    public ShoppingCartServiceData() {
      super();
    }
    public ServiceData getService() {
        return _service;
    }

    public void setService(ServiceData _service) {
        this._service = _service;
    }

    public Object copy() {
    ShoppingCartServiceData scsD = new ShoppingCartServiceData();

    scsD._orderNumber = _orderNumber;
    scsD._service= _service;
    scsD._category = _category;
    scsD._quantity = _quantity;
    scsD._templQuantity = _templQuantity;
    scsD._price = _price;
    scsD._sourceCd = _sourceCd;
    scsD._sourceId = _sourceId;
    scsD._contractFlag = _contractFlag;
    scsD._duplicateFlag = _duplicateFlag;
    scsD._shoppingHistory = _shoppingHistory;
    scsD._actualSkuNum = _actualSkuNum;
    scsD._actualSkuType = _actualSkuType;

    return scsD;
    }

    public String toString() {
       String os = "ShoppingCartItemData = [ " +
	    " OrderNumber = " 		+ _orderNumber +
	    " Service = " 		+ _service +
	    " Category = " 		+ _category +
	    " ShoppingHistory = " 	+ _shoppingHistory +
	    " TemplQuantity = " 	+ _templQuantity +
	    " Quantity = " 		+ _quantity +
	    " QuantityString = " 		+ _quantityString +
	    " Price = " 		+ _price +
	    " SourceCd = " 		+ _sourceCd +
	    " SourceId = " 		+ _sourceId +
	    " ContractFlag = " 		+ _contractFlag +
	    " DuplicateFlag = " 	+ _duplicateFlag +
	    " ActualSkuNum = " 		+ _actualSkuNum  +
	    " ActualSkuType = " 	+ _actualSkuType +
	    " ] ";
 	return os;
    }


    public ItemData getItemData() {
        if (_service != null) {
            return _service.getItemData();
        }
        return null;
    }

    public AssetData getAssetData() {
        return _assetData;
    }

    public void setAssetData(AssetData assetData) {
        this._assetData = assetData;
    }

    public boolean isTaxable() {
        if (_service != null) {
            CatalogStructureData csd = _service.getCatalogStructureData();
            if (csd != null) {
                return !Utility.isTrue(csd.getTaxExempt());
            }
        }
        return true;
    }
}
