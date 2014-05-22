package com.cleanwise.view.forms;

import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.*;

import java.util.HashMap;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

/**
 * Title:        UserAssetProfileMgrForm
 * Description:  Form bean for the  asset  profile  page
 * Purpose:      Holds data for the asset profile management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         20.11.2007
 * Time:         14:15:02
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class UserAssetProfileMgrForm extends StorePortalBaseForm {

    public static interface CREATE_STEP_CD {
        public String STEP1 = "STEP1";
        public String STEP2 = "STEP2";
    }

    private String mActiveStep="";

    int mAssetId;
    int mSitetId;
    private AssetData mAssetData;

    private AssetPropertyData mInactiveReason;
    private AssetPropertyData mAcquisitionDate;
    private AssetPropertyData mDateInService;
    private AssetPropertyData mAcquisitionCost;
    private AssetPropertyData mLastHMR;
    private AssetPropertyData mDateLastHMR;
    private AssetPropertyData mLongDesc;
    private AssetPropertyData mCustomDesc;
    private BigDecimal mWorkOrderTotalCost;
    private HashMap mWorkOrderActualCostMap;//key is workOrderId,object is BigDecimal total cost

    private WarrantyDataVector  mAssetWarrantyAssoc;
    private WorkOrderDataVector mAssetWorkOrderAssoc;
    private AssetContentViewVector mContents;
    private AssetAssocDataVector mAssociations;

    private String mMainAssetImageName;

    private PairViewVector mManufIdNamePairs;
    private AssetDataVector mAssetCategories;
    private AddressData mAssetLocationData;
    private AssetContentDetailView mViewAssetContentDetail;

    private FormFile mImageFile;

    private AssetContentDetailView mMainAssetImage;
    private WarrantyDataVector  mStoreWarranties;
    private String mAssetWarrantyIds[];
    private boolean mShowConfiguredWarrantiesOnly;
    private boolean mUserAssignedAssetNumber;
    
    private boolean mShowLinkedStores = false;
    private BusEntityDataVector mLinkedStores;
    private boolean mIsEditable;
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	    mShowConfiguredWarrantiesOnly = false;
    }
    
    public int getAssetId() {
        return mAssetId;
    }

    public void setAssetId(int assetId) {
        this.mAssetId = assetId;
    }

    public AssetData getAssetData() {
        return mAssetData;
    }

    public void setAssetData(AssetData assetData) {
        this.mAssetData = assetData;
    }

    public AssetAssocDataVector getAssociations() {
        return mAssociations;
    }

    public void setAssociations(AssetAssocDataVector associations) {
        this.mAssociations = associations;
    }

    public AssetPropertyData getInactiveReason() {
        return mInactiveReason;
    }

    public void setInactiveReason(AssetPropertyData inactiveReason) {
        this.mInactiveReason = inactiveReason;
    }

    public AssetPropertyData getAcquisitionDate() {
        return mAcquisitionDate;
    }

    public void setAcquisitionDate(AssetPropertyData acquisitionDate) {
        this.mAcquisitionDate = acquisitionDate;
    }

    public AssetPropertyData getAcquisitionCost() {
        return mAcquisitionCost;
    }

    public void setAcquisitionCost(AssetPropertyData acquisitionCost) {
        this.mAcquisitionCost = acquisitionCost;
    }

    public AssetPropertyData getDateInService() {
        return mDateInService;
    }

    public void setDateInService(AssetPropertyData dateInService) {
        this.mDateInService = dateInService;
    }

    public AssetPropertyData getLastHMR() {
        return mLastHMR;
    }

    public void setLastHMR(AssetPropertyData lastHMR) {
        this.mLastHMR = lastHMR;
    }

    public AssetPropertyData getDateLastHMR() {
        return mDateLastHMR;
    }

    public void setDateLastHMR(AssetPropertyData dateLastHMR) {
        this.mDateLastHMR = dateLastHMR;
    }

    public AssetPropertyData getLongDesc() {
        return mLongDesc;
    }

    public void setLongDesc(AssetPropertyData longDesc) {
        this.mLongDesc = longDesc;
    }

    public AssetPropertyData getCustomDesc() {
        return mCustomDesc;
    }

    public void setCustomDesc(AssetPropertyData customDesc) {
        this.mCustomDesc = customDesc;
    }
    
    public WarrantyDataVector getAssetWarrantyAssoc() {
        return mAssetWarrantyAssoc;
    }

    public void setAssetWarrantyAssoc(WarrantyDataVector assetWarrantyAssoc) {
        this.mAssetWarrantyAssoc = assetWarrantyAssoc;
    }

    public WorkOrderDataVector getAssetWorkOrderAssoc() {
        return mAssetWorkOrderAssoc;
    }

    public void setAssetWorkOrderAssoc(WorkOrderDataVector assetWorkOrderAssoc) {
        this.mAssetWorkOrderAssoc = assetWorkOrderAssoc;
    }

    public AssetContentViewVector getContents() {
        return mContents;
    }

    public void setContents(AssetContentViewVector contents) {
        this.mContents = contents;
    }

    public void setMainAssetImageName(String mainAssetImageName) {
        this.mMainAssetImageName = mainAssetImageName;
    }

    public String getMainAssetImageName() {
        return mMainAssetImageName;
    }

    public PairViewVector getManufIdNamePairs() {
        return mManufIdNamePairs;
    }

    public void setManufIdNamePairs(PairViewVector manufIdNamePairs) {
        this.mManufIdNamePairs = manufIdNamePairs;
    }

    public void setAssetCategories(AssetDataVector categories) {
        this.mAssetCategories = categories;
    }

    public AssetDataVector getAssetCategories() {
        return mAssetCategories;
    }
    public int getSitetId() {
        return mSitetId;
    }

    public void setSiteId(int sitetId) {
        this.mSitetId = sitetId;
    }

    public void setAssetLocationData(AddressData assetLocationData) {
        this.mAssetLocationData = assetLocationData;
    }

    public AddressData getAssetLocationData() {
        return mAssetLocationData;
    }

    public void setViewAssetContentDetail(AssetContentDetailView viewAssetContentDetail) {
        this.mViewAssetContentDetail = viewAssetContentDetail;
    }

    public AssetContentDetailView getViewAssetContentDetail() {
        return mViewAssetContentDetail;
    }


    public String getActiveStep() {
        return mActiveStep;
    }

    public void setActiveStep(String mActiveStep) {
        this.mActiveStep = mActiveStep;
    }

    public FormFile getImageFile() {
        return mImageFile;
    }

    public void setImageFile(FormFile mImageFile) {
        this.mImageFile = mImageFile;
    }

    public void setMainAssetImage(AssetContentDetailView mainAssetImage) {
        this.mMainAssetImage = mainAssetImage;
    }

    public AssetContentDetailView getMainAssetImage() {
        return mMainAssetImage;
    }

    public void setWorkOrderTotalCost(BigDecimal workOrderTotalCost) {
        this.mWorkOrderTotalCost = workOrderTotalCost;
    }

    public BigDecimal getWorkOrderTotalCost() {
        return mWorkOrderTotalCost;
    }

    public void setWorkOrderActualCostMap(HashMap workOrderCostMap) {
        this.mWorkOrderActualCostMap = workOrderCostMap;
    }

    public HashMap getWorkOrderActualCostMap() {
        return mWorkOrderActualCostMap;
    }

    public String[] getAssetWarrantyIds() {
        return mAssetWarrantyIds;
    }

    public void setAssetWarrantyIds(String[] assetWarrantyIds) {
        this.mAssetWarrantyIds = assetWarrantyIds;
    }

    public WarrantyDataVector getStoreWarranties() {
        return mStoreWarranties;
    }

    public void setStoreWarranties(WarrantyDataVector storeWarranties) {
        this.mStoreWarranties = storeWarranties;
    }

    public boolean getShowConfiguredWarrantiesOnly() {
        return mShowConfiguredWarrantiesOnly;
    }

    public void setShowConfiguredWarrantiesOnly(boolean showConfiguredWarrantiesOnly) {
        this.mShowConfiguredWarrantiesOnly = showConfiguredWarrantiesOnly;
    }
    
    public void setUserAssignedAssetNumber(boolean userAssignedAssetNumber) {
        this.mUserAssignedAssetNumber = userAssignedAssetNumber;
    }
    
    public boolean getUserAssignedAssetNumber() {
        return mUserAssignedAssetNumber;
    }

    public BusEntityDataVector getLinkedStores() {
        return mLinkedStores;
    }

    public void setLinkedStores(BusEntityDataVector linkedStores) {
        this.mLinkedStores = linkedStores;
    }

    public boolean getIsEditable() {
        return mIsEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.mIsEditable = isEditable;
    }

    public boolean getShowLinkedStores() {
        return mShowLinkedStores;
    }

    public void setShowLinkedStores(boolean showLinkedStores) {
        this.mShowLinkedStores = showLinkedStores;
    }
    

}




