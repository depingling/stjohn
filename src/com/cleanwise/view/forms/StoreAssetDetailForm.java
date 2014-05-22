package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import java.io.File;


/**
 * Title:        StoreAssetDetailForm
 * Description:  Form bean for the  asset management.
 * Purpose:      Holds data for the asset detail  management
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         25.11.2006
 * Time:         14:17:59
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */

public class StoreAssetDetailForm extends StorePortalBaseForm  {

    private int mSelectedManufId;/*selected id of manufacturer*/
    private PairViewVector mManufIdNamePairs;/*pairs[id,short_desc] of manufacturer  with which current store is assigned*/
    private PairViewVector mParentIdNamePairs;/*pairs[id,short_desc] of parent asset*/

    private AssetData mAssetData;
    private AssetPropertyData mLongDesc;
    private String mImagePath;
    private AssetPropertyData mInactiveReason;
    private AssetPropertyData mAcquisitionDate;
    private AssetPropertyData mDateInService;
    private AssetPropertyData mAcquisitionCost;
    private AssetPropertyData mLastHMR;
    private AssetPropertyData mDateLastHMR;

    private FormFile mImageFile;
    private int mAssetId;
    private WarrantyDataVector  mAssetWarrantyAssoc;
    private WorkOrderDataVector mAssetWorkOrderAssoc;
    private AssetContentViewVector mContents;
    private AssetContentDetailView mMainAssetImage;
    private File mAssetImageDir;


    public FormFile getImageFile() {
        return mImageFile;
    }

    public void setImageFile(FormFile imageFile) {
        this.mImageFile = imageFile;
    }

    public int getSelectedManufId() {
        return mSelectedManufId;
    }

    public void setSelectedManufId(int selectedManufId) {
        this.mSelectedManufId = selectedManufId;
    }

    public PairViewVector getManufIdNamePairs() {
        return mManufIdNamePairs;
    }

    public void setManufIdNamePairs(PairViewVector manufIdNamePairs) {
        this.mManufIdNamePairs = manufIdNamePairs;
    }

    public AssetData getAssetData() {
        return mAssetData;
    }

    public void setAssetData(AssetData mAssetData) {
        this.mAssetData = mAssetData;
    }

    public AssetPropertyData getLongDesc() {
        return mLongDesc;
    }

    public void setLongDesc(AssetPropertyData mLongDesc) {
        this.mLongDesc = mLongDesc;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
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

    public AssetPropertyData getAcquisitionCost() {
        return mAcquisitionCost;
    }

    public void setAcquisitionCost(AssetPropertyData mAcquisitionCost) {
        this.mAcquisitionCost = mAcquisitionCost;
    }

    public int getAssetId() {
        return mAssetId;
    }

    public void setAssetId(int mAssetId) {
        this.mAssetId = mAssetId;
    }


    public PairViewVector getParentIdNamePairs() {
        return mParentIdNamePairs;
    }

    public void setParentIdNamePairs(PairViewVector parentIdNamePairs) {
        this.mParentIdNamePairs = parentIdNamePairs;
    }

    public AssetPropertyData getDateInService() {
        return mDateInService;
    }

    public void setDateInService(AssetPropertyData mDateInService) {
        this.mDateInService = mDateInService;
    }

    public AssetPropertyData getLastHMR() {
        return mLastHMR;
    }

    public void setLastHMR(AssetPropertyData mLastHMR) {
        this.mLastHMR = mLastHMR;
    }

    public AssetPropertyData getDateLastHMR() {
        return mDateLastHMR;
    }

    public void setDateLastHMR(AssetPropertyData mDateLastHMR) {
        this.mDateLastHMR = mDateLastHMR;
    }

    public void setAssetWarrantyAssoc(WarrantyDataVector assetWarrantyAssoc) {
        this.mAssetWarrantyAssoc = assetWarrantyAssoc;
    }

    public WarrantyDataVector getAssetWarrantyAssoc() {
        return mAssetWarrantyAssoc;
    }

    public WorkOrderDataVector getAssetWorkOrderAssoc() {
        return mAssetWorkOrderAssoc;
    }

    public void setAssetWorkOrderAssoc(WorkOrderDataVector mAssetWorkOrderAssoc) {
        this.mAssetWorkOrderAssoc = mAssetWorkOrderAssoc;
    }

    public void setContents(AssetContentViewVector contents) {
        this.mContents = contents;
    }

    public AssetContentViewVector getContents() {
        return mContents;
    }

    public void setMainAssetImage(AssetContentDetailView mainAssetImage) {
        this.mMainAssetImage = mainAssetImage;
    }


    public AssetContentDetailView getMainAssetImage() {
        return mMainAssetImage;
    }

    public void setAssetImageDir(File assetImageDir) {
        this.mAssetImageDir = assetImageDir;
    }


    public File getAssetImageDir() {
        return mAssetImageDir;
    }
}
