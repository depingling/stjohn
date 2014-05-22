package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Title:        UserServiceShopForm
 * Description:  Form bean for the  asset  services  page
 * Purpose:      Holds data for the services shoping of the asset
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         17.01.2007
 * Time:         15:04:51
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class UserServiceShopForm extends ActionForm {

    private int mSitetId;
    private AssetData mAssetData;
    private HashMap mAssetPropertiesMap;
    private ItemDataVector mServiceAssoc;
    private BusEntityDataVector mSiteAssoc;
    private BusEntityDataVector mStoreAssoc;
    private AssetAssocDataVector mAssociations;
    private SiteData mSiteData;
    private String mServiceName;
    private int[] mSelectedServiceIds=new int[0];
    private AddressData mAssetLocationData;
    private WarrantyDataVector  mAssetWarrantyAssoc;
    private WorkOrderDataVector mAssetWorkOrderAssoc;
    private AssetContentViewVector mContents;
    private AssetContentDetailView mViewAssetContentDetails;

    public AssetData getAssetData() {
       return mAssetData;
    }

    public void setAssetData(AssetData assetData) {
        this.mAssetData = assetData;
    }

    public int getSitetId() {
        return mSitetId;
    }

    public void setSitetId(int sitetId) {
        this.mSitetId = sitetId;
    }

 /*   public AssetPropertyData getLongDesc() {
        return mLongDesc;
    }

    public void setLongDesc(AssetPropertyData mLongDesc) {
        this.mLongDesc = mLongDesc;
    }

    public AssetPropertyData getProductSpec() {
        return mProductSpec;
    }

    public void setProductSpec(AssetPropertyData mProductSpec) {
        this.mProductSpec = mProductSpec;
    }

    public AssetPropertyData getImage() {
        return mImage;
    }

    public void setImage(AssetPropertyData mImage) {
        this.mImage = mImage;
    }

    public AssetPropertyData getInactiveReason() {
        return mInactiveReason;
    }

    public void setInactiveReason(AssetPropertyData mInactiveReason) {
        this.mInactiveReason = mInactiveReason;
    }

    public AssetPropertyData getAcquisitionDate() {
        return mAcquisitionDate;
    }

    public void setAcquisitionDate(AssetPropertyData mAcquisitionDate) {
        this.mAcquisitionDate = mAcquisitionDate;
    }

    public AssetPropertyData getTotalRepairLabor() {
        return mTotalRepairLabor;
    }

    public void setTotalRepairLabor(AssetPropertyData mTotalRepairLabor) {
        this.mTotalRepairLabor = mTotalRepairLabor;
    }

    public AssetPropertyData getAcquisitionCost() {
        return mAcquisitionCost;
    }

    public void setAcquisitionCost(AssetPropertyData mAcquisitionCost) {
        this.mAcquisitionCost = mAcquisitionCost;
    }

    public AssetPropertyData getTotalRepairParts() {
        return mTotalRepairParts;
    }

    public void setTotalRepairParts(AssetPropertyData mTotalRepairParts) {
        this.mTotalRepairParts = mTotalRepairParts;
    }
    */
   public void reset(ActionMapping mapping, ServletRequest request) {
        mSelectedServiceIds = new int[0];

   }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        mSelectedServiceIds = new int[0];

    }


    public void setAssociations(AssetAssocDataVector associations) {
        this.mAssociations = associations;
    }

    public AssetAssocDataVector getAssociations() {
        return mAssociations;
    }

    public void setServiceAssoc(ItemDataVector serviceAssoc) {
        this.mServiceAssoc = serviceAssoc;
    }



    public HashMap getAssetPropertiesMap() {
        return mAssetPropertiesMap;
    }

    public void setAssetPropertiesMap(HashMap assetPropertiesMap) {
        this.mAssetPropertiesMap= assetPropertiesMap;
    }

    public ItemDataVector getServiceAssoc() {
        return mServiceAssoc;
    }

    public void setSiteAssoc(BusEntityDataVector siteAssoc) {
        this.mSiteAssoc = siteAssoc;
    }

    public BusEntityDataVector getSiteAssoc() {
        return mSiteAssoc;
    }

    public void setStoreAssoc(BusEntityDataVector storeAssoc) {
        this.mStoreAssoc = storeAssoc;
    }

    public BusEntityDataVector getStoreAssoc() {
        return mStoreAssoc;
    }

    public void setSiteData(SiteData siteData) {
        this.mSiteData=siteData;
    }

    public SiteData getSiteData() {
        return mSiteData;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String serviceName) {
        this.mServiceName = serviceName;
    }

    public int[] getSelectedServiceIds() {
        return mSelectedServiceIds;
    }

    public void setSelectedServiceIds(int[] selectedServiceIds) {
        this.mSelectedServiceIds = selectedServiceIds;
    }

    public void setAssetLocationData(AddressData assetLocationData) {
        this.mAssetLocationData = assetLocationData;
    }

    public AddressData getAssetLocationData() {
        return mAssetLocationData;
    }

    public AssetContentViewVector getContents() {
        return mContents;
    }

    public void setContents(AssetContentViewVector mContents) {
        this.mContents = mContents;
    }

    public WarrantyDataVector getAssetWarrantyAssoc() {
        return mAssetWarrantyAssoc;
    }

    public void setAssetWarrantyAssoc(WarrantyDataVector mAssetWarrantyAssoc) {
        this.mAssetWarrantyAssoc = mAssetWarrantyAssoc;
    }

    public WorkOrderDataVector getAssetWorkOrderAssoc() {
        return mAssetWorkOrderAssoc;
    }

    public void setAssetWorkOrderAssoc(WorkOrderDataVector mAssetWorkOrderAssoc) {
        this.mAssetWorkOrderAssoc = mAssetWorkOrderAssoc;
    }

    public void setViewAssetContentDetails(AssetContentDetailView viewAssetContentDetails) {
        this.mViewAssetContentDetails = viewAssetContentDetails;
    }

    public AssetContentDetailView getViewAssetContentDetails() {
        return mViewAssetContentDetails;
    }
}




