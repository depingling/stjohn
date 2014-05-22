package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Title:        AssetDetailData
 * Description:  Value object extension for marshalling asset data.
 * Purpose:      To contain asset detail data .
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date: 21.12.2006
 * Time: 8:51:15
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class AssetDetailData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5373589613215011325L;

    public AssetData mAssetData;
    public AssetPropertyData mLongDesc;
    public AssetPropertyData mCustomDesc;
    public AssetPropertyData mInactiveReason;
    public AssetPropertyData mDateInService;
    public AssetPropertyData mAcquisitionDate;
    public AssetPropertyData mAcquisitionCost;
    public AssetPropertyData mLastHMR;
    public AssetPropertyData mDateLastHMR;

    public AssetDetailData(AssetData mAssetData,
                           AssetPropertyData mLongDesc,
                           AssetPropertyData mCustomDesc,
                           AssetPropertyData mInactiveReason,
                           AssetPropertyData mAcquisitionCost,
                           AssetPropertyData mAcquisitionDate,
                           AssetPropertyData mDateInService,
                           AssetPropertyData mDateLastHMR,
                           AssetPropertyData mLastHMR) {

        this.mAssetData = (mAssetData != null) ? mAssetData : AssetData.createValue();

        if (mLongDesc != null) {
            this.mLongDesc = mLongDesc;
        } else {
            this.mLongDesc = AssetPropertyData.createValue();
            this.mLongDesc.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC);
        }
        
        if (mCustomDesc != null) {
            this.mCustomDesc = mCustomDesc;
        } else {
            this.mCustomDesc = AssetPropertyData.createValue();
            this.mCustomDesc.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.CUSTOM_DESC);
        }

        if (mInactiveReason != null) {
            this.mInactiveReason = mInactiveReason;
        } else {
            this.mInactiveReason = AssetPropertyData.createValue();
            this.mInactiveReason.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.INACTIVE_REASON);
        }

        if (mAcquisitionCost != null) {
            this.mAcquisitionCost = mAcquisitionCost;
        } else {
            this.mAcquisitionCost = AssetPropertyData.createValue();
            this.mAcquisitionCost.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_COST);
        }

        if (mAcquisitionDate != null) {
            this.mAcquisitionDate = mAcquisitionDate;
        } else {
            this.mAcquisitionDate = AssetPropertyData.createValue();
            this.mAcquisitionDate.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE);
        }

        if (mDateInService != null) {
            this.mDateInService = mDateInService;
        } else {
            this.mDateInService = AssetPropertyData.createValue();
            this.mDateInService.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE);
        }

        if (mDateLastHMR != null) {
            this.mDateLastHMR = mDateLastHMR;
        } else {
            this.mDateLastHMR = AssetPropertyData.createValue();
            this.mDateLastHMR.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_LAST_HOUR_METER_READING);
        }

        if (mLastHMR != null) {
            this.mLastHMR = mLastHMR;
        } else {
            this.mLastHMR = AssetPropertyData.createValue();
            this.mLastHMR.setAssetPropertyCd(RefCodeNames.ASSET_PROPERTY_TYPE_CD.LAST_HOUR_METER_READING);
        }
    }

    public static AssetDetailData createValue()
    {
        return new  AssetDetailData(null,null,null,null,null,null,null,null,null);

    }

    public AssetData getAssetData() {
        return mAssetData;
    }

    public void setAssetData(AssetData mAssetData) {
        this.mAssetData = mAssetData;
        setDirty(true);
    }

    public AssetPropertyData getLongDesc() {
        return mLongDesc;
    }

    public void setLongDesc(AssetPropertyData mLongDesc) {
        this.mLongDesc = mLongDesc;
    }
    
    public AssetPropertyData getCustomDesc() {
        return mCustomDesc;
    }

    public void setCustomDesc(AssetPropertyData mCustomDesc) {
        this.mCustomDesc = mCustomDesc;
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

    public AssetPropertyData getDateInService() {
        return mDateInService;
    }

    public void setDateInService(AssetPropertyData mDateInService) {
        this.mDateInService = mDateInService;
    }

    public AssetPropertyData getDateLastHMR() {
        return mDateLastHMR;
    }

    public void setDateLastHMR(AssetPropertyData mDateLastHMR) {
        this.mDateLastHMR = mDateLastHMR;
    }

    public AssetPropertyData getLastHMR() {
        return mLastHMR;
    }

    public void setLastHMR(AssetPropertyData mLastHMR) {
        this.mLastHMR = mLastHMR;
    }
}
