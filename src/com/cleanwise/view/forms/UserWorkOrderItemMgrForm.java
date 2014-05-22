package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.logic.UserWorkOrderMgrLogic;

import java.util.Date;
import java.util.HashMap;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         15.10.2007
 * Time:         17:36:47
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderItemMgrForm extends ActionForm {

    public static interface CREATE_STEP_CD{
        public  String STEP1 = "STEP1";
        public  String STEP2 = "STEP2";
        public  String STEP3 = "STEP3";
    }

    WorkOrderData workOrder;
    WorkOrderItemDetailViewVector allWorkOrderItems;
    private WarrantyDataVector warrantyForActiveAsset;
    private AssetDataVector assetCategories;
    private AssetDataVector allAssets;
    private AssetDataVector assetForActiveCategory;

    WorkOrderItemDetailView workOrderItemDetail;

    //detail field
    private int busEntityId;
    private int warrantyId;
    private int workOrderId;
    private int workOrderItemId;
    private String actualLabor;
    private String actualPart;
    private String actualTotalCost;    
    private String addBy;
    private Date addDate;
    private String estimatedPart;
    private String estimatedLabor;
    private String estimateTotalCost;
    private String longDesc;
    private String modBy;
    private Date modDate;
    private String sequence;
    private String shortDesc;
    private String statusCd;   
    private PairViewVector assetGroups;
    private OrderDataVector itemOrders;

    private AssetData activeAsset;
    private AssetData activeAssetCategory;


    private String activeAssetIdStr;
    private String activeWarrantyIdStr;
    private String activeStep;
    private String activeCategoryIdStr;
    private boolean allowBuyWorkOrderParts = false;


    public AssetData getActiveAssetCategory() {
        return activeAssetCategory;
    }

    public void setActiveAssetCategory(AssetData activeAssetCategory) {
        this.activeAssetCategory = activeAssetCategory;
    }

    public void setActiveAsset(AssetData activeAsset) {
        this.activeAsset = activeAsset;
    }

    public AssetData getActiveAsset() {
        return activeAsset;
    }

    public AssetDataVector getAssetForActiveCategory() {
        return assetForActiveCategory;
    }

    public void setAssetForActiveCategory(AssetDataVector assetForActiveCategory) {
        this.assetForActiveCategory = assetForActiveCategory;
    }

    public void setAssetCategories(AssetDataVector assetCategories) {
        this.assetCategories = assetCategories;
    }

    public AssetDataVector getAssetCategories() {
        return assetCategories;
    }

    public String getActiveCategoryIdStr() {
        return activeCategoryIdStr;
    }

    public void setActiveCategoryIdStr(String activeCategoryIdStr) {
        this.activeCategoryIdStr = activeCategoryIdStr;
    }

    public WorkOrderData getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderData workOrder) {
        this.workOrder = workOrder;
    }

    public WorkOrderItemDetailViewVector getAllWorkOrderItems() {
        return allWorkOrderItems;
    }

    public void setAllWorkOrderItems(WorkOrderItemDetailViewVector allWorkOrderItems) {
        this.allWorkOrderItems = allWorkOrderItems;
    }


    public WorkOrderItemDetailView getWorkOrderItemDetail() {
        return workOrderItemDetail;
    }

    public void setWorkOrderItemDetail(WorkOrderItemDetailView workOrderItemDetail) {
        this.workOrderItemDetail = workOrderItemDetail;
    }


    public int getBusEntityId() {
        return busEntityId;
    }

    public void setBusEntityId(int busEntityId) {
        this.busEntityId = busEntityId;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getWorkOrderItemId() {
        return workOrderItemId;
    }

    public void setWorkOrderItemId(int workOrderItemId) {
        this.workOrderItemId = workOrderItemId;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }


    public String getEstimatedPart() {
        return estimatedPart;
    }

    public void setEstimatedPart(String estimatedPart) {
        this.estimatedPart = estimatedPart;
    }

    public String getEstimatedLabor() {
        return estimatedLabor;
    }

    public void setEstimatedLabor(String estimatedLabor) {
        this.estimatedLabor = estimatedLabor;
    }

    public String getEstimateTotalCost() {
        return estimateTotalCost;
    }

    public void setEstimateTotalCost(String estimateTotalCost) {
        this.estimateTotalCost = estimateTotalCost;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getModBy() {
        return modBy;
    }

    public void setModBy(String modBy) {
        this.modBy = modBy;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }


    public String getActiveWarrantyIdStr() {
        return activeWarrantyIdStr;
    }

    public void setActiveWarrantyIdStr(String warrantyIdStr) {
        this.activeWarrantyIdStr = warrantyIdStr;
    }

    public String getActiveAssetIdStr() {
        return activeAssetIdStr;
    }

    public void setActiveAssetIdStr(String assetIdStr) {
        this.activeAssetIdStr = assetIdStr;
    }

    public void setWarrantyForActiveAsset(WarrantyDataVector warranties) {
        this.warrantyForActiveAsset = warranties;
    }

    public WarrantyDataVector getWarrantyForActiveAsset() {
        return warrantyForActiveAsset;
    }

    public void setAllAssets(AssetDataVector assets) {
        this.allAssets = assets;
    }

    public AssetDataVector getAllAssets() {
        return allAssets;
    }

    public String getActiveStep() {
        return activeStep;
    }

    public void setActiveStep(String activeStep) {
        this.activeStep = activeStep;
    }

    public void setActualLabor(String actualLabor) {
         this.actualLabor = actualLabor;
     }

    public void setActualPart(String actualPart) {
        this.actualPart = actualPart;
    }

    public String getActualLabor() {
        return actualLabor;
    }

    public String getActualPart() {
        return actualPart;
    }

    public void setActualTotalCost(String actualTotalCost) {
        this.actualTotalCost = actualTotalCost;
    }

    public String getActualTotalCost() {
        return actualTotalCost;
    }

    public void setAssetGroups(PairViewVector assetGroups) {
        this.assetGroups = assetGroups;
    }

    public PairViewVector getAssetGroups() {
        return assetGroups;
    }
    
    public void setItemOrders(OrderDataVector itemOrders) {
        this.itemOrders = itemOrders;
    }

    public OrderDataVector getItemOrders() {
        return itemOrders;
    }
    
    public void setAllowBuyWorkOrderParts(boolean allowBuyWorkOrderParts) {
        this.allowBuyWorkOrderParts = allowBuyWorkOrderParts;
    }

    public boolean getAllowBuyWorkOrderParts() {
        return this.allowBuyWorkOrderParts;
    }
}
