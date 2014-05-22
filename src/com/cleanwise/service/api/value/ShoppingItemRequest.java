package com.cleanwise.service.api.value;

import java.io.Serializable;
import java.util.List;


public class ShoppingItemRequest implements Serializable {

    private static final long serialVersionUID = 5886423276496112327L;

    private boolean mSpecialPermissionRequired;
    private boolean mSpecialPermission;
    private boolean mContractItemsOnly;

    private String mProductBundle;

    private int mSiteId;

    private int mContractId;
    private int mShoppingCatalogId;
    private int mAccountCatalogId;
    private Integer mPriceListRank1Id;
    private Integer mPriceListRank2Id;
    private IdVector mProprietaryPriceListIds;
    private List<Integer> mAvailableTemplateOrderGuideIds;
    private String mShoppingItemIdsSelectSql;
    private IdVector mShoppingItemIds;
    private IdVector mExcProductBundleFilterIds;
    private IdVector mIncProductBundleFilterIds;


    public ShoppingItemRequest(int pSiteId,
                               int pAccountCatalogId,
                               int pShoppingCatalogId,
                               int pContractId,
                               Integer pPriceListRank1Id,
                               Integer pPriceListRank2Id,
                               IdVector pProprietaryPriceListIds,
                               List<Integer> pAvailableTemplateOrderGuideIds,
                               boolean pContractItemsOnly,
                               boolean pSpecialPermissionRequired,
                               boolean pSpecialPermission,
                               String pProductBundle) {
        this.mSiteId = pSiteId;
        this.mAccountCatalogId = pAccountCatalogId;
        this.mShoppingCatalogId = pShoppingCatalogId;
        this.mContractId = pContractId;
        this.mPriceListRank1Id = pPriceListRank1Id;
        this.mPriceListRank2Id = pPriceListRank2Id;
        this.mProprietaryPriceListIds = pProprietaryPriceListIds;
        this.mAvailableTemplateOrderGuideIds = pAvailableTemplateOrderGuideIds;         
        this.mContractItemsOnly = pContractItemsOnly;
        this.mSpecialPermissionRequired = pSpecialPermissionRequired;
        this.mSpecialPermission = pSpecialPermission;
        this.mProductBundle = pProductBundle;

    }

    public boolean isContractItemsOnly() {
        return mContractItemsOnly;
    }

    public void setContractItemsOnly(boolean pContractItemsOnly) {
        this.mContractItemsOnly = pContractItemsOnly;
    }

    public boolean getSpecialPermission() {
        return mSpecialPermission;
    }

    public void setSpecialPermission(boolean pSpecialPermissionOnly) {
        this.mSpecialPermission = pSpecialPermissionOnly;
    }


    public int getContractId() {
        return mContractId;
    }

    public void setContractId(int pContractId) {
        this.mContractId = pContractId;
    }

    public int getAccountCatalogId() {
        return mAccountCatalogId;
    }

    public void setAccountCatalogId(int pAccountCatalogId) {
        this.mAccountCatalogId = pAccountCatalogId;
    }

    public int getShoppingCatalogId() {
        return mShoppingCatalogId;
    }

    public void setShoppingCatalogId(int pShoppingCatalogId) {
        this.mShoppingCatalogId = pShoppingCatalogId;
    }

    public boolean isSpecialPermissionRequired() {
        return mSpecialPermissionRequired;
    }

    public void setSpecialPermissionRequired(boolean pSpecialPermissionRequired) {
        this.mSpecialPermissionRequired = pSpecialPermissionRequired;
    }

    public String getProductBundle() {
        return mProductBundle;
    }

    public void setProductBundle(String pProductBundle) {
        this.mProductBundle = pProductBundle;
    }

    public int getSiteId() {
        return mSiteId;
    }

    public void setSiteId(int pSiteId) {
        this.mSiteId = pSiteId;
    }

    public Integer getPriceListRank1Id() {
        return mPriceListRank1Id;
    }

    public void setPriceListRank1Id(Integer pPriceListRank1Id) {
        this.mPriceListRank1Id = pPriceListRank1Id;
    }

    public Integer getPriceListRank2Id() {
        return mPriceListRank2Id;
    }

    public void setPriceListRank2Id(Integer pPriceListRank2Id) {
        this.mPriceListRank2Id = pPriceListRank2Id;
    }


    public IdVector getProprietaryPriceListIds() {
        return mProprietaryPriceListIds;
    }

    public void setProprietaryPriceListId(IdVector pProprietaryPriceListIds) {
        this.mProprietaryPriceListIds = pProprietaryPriceListIds;
    }

    public List<Integer> getAvailableTemplateOrderGuideIds() {
        return mAvailableTemplateOrderGuideIds;
    }

    public void setAvailableTemplateOrderGuideIds(List<Integer> pAvailableTemplateOrderGuideIds) {
        this.mAvailableTemplateOrderGuideIds = pAvailableTemplateOrderGuideIds;
    }
    
    public String getShoppingItemIdsSelectSql() {
        return mShoppingItemIdsSelectSql;
    }

    public void setShoppingItemIdsSelectSql(String pShoppingItemIdsSelectSql) {
        this.mShoppingItemIdsSelectSql = pShoppingItemIdsSelectSql;
    }
    
    public IdVector getShoppingItemIds() {
        return mShoppingItemIds;
    }

    public void setShoppingItemIds(IdVector pShoppingItemIds) {
        this.mShoppingItemIds = pShoppingItemIds;
    }

	public IdVector getExcProductBundleFilterIds() {
		return mExcProductBundleFilterIds;
	}

	public void setExcProductBundleFilterIds(IdVector pExcProductBundleFilterIds) {
		this.mExcProductBundleFilterIds = pExcProductBundleFilterIds;
	}   

	public IdVector getIncProductBundleFilterIds() {
		return mIncProductBundleFilterIds;
	}

	public void setIncProductBundleFilterIds(IdVector pIncProductBundleFilterIds) {
		this.mIncProductBundleFilterIds = pIncProductBundleFilterIds;
	}   
	
}
