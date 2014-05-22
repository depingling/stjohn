package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

import java.util.HashMap;

import com.cleanwise.service.api.value.*;
import com.cleanwise.view.logic.UserWarrantyMgrLogic;
import com.cleanwise.view.logic.UserWorkOrderMgrLogic;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         27.11.2007
 * Time:         8:18:47
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWarrantyDetailMgrForm extends ActionForm {

    public String managementSource = UserWarrantyMgrLogic.className;

    private HashMap allWarrantyProvidersMap;
    private HashMap allServiceProvidersMap;

    private WarrantyData warrantyData;
    private BusEntityData warrantyProvider;
    private WarrantyContentViewVector contents;
    private WarrantyAssocViewVector warrantyAssocViewVector;
    private WarrantyStatusHistDataVector statusHistory;
    private WarrantyNoteDataVector warrantyNotes;
    private AssetWarrantyViewVector assetWarrantyViewVector;
    private WorkOrderItemWarrantyViewVector workOrderItemWarrantyViewVector;
    private BusEntityData serviceProvider;

    //detail form edit fields
    private int warrantyId;
    private String businessName;
    private String cost;
    private String duration;
    private String longDesc;
    private String warrantyName;
    private String statusCd;
    private String typeCd;
    private String warrantyNumber;
    private String warrantyProviderId;
    private String durationTypeCd;
    private AssetDataVector assetWarrantyCategoryAssoc;
    private String serviceProviderId;




    public void setAllWarrantyProvidersMap(HashMap allWarrantyProvidersMap) {
        this.allWarrantyProvidersMap = allWarrantyProvidersMap;
    }

    public HashMap getAllWarrantyProvidersMap() {
        return allWarrantyProvidersMap;
    }

    public void setWarrantyData(WarrantyData warrantyData) {
        this.warrantyData = warrantyData;
    }

    public void setWarrantyProvider(BusEntityData provider) {
        this.warrantyProvider = provider;
    }

    public void setContents(WarrantyContentViewVector contents) {
        this.contents = contents;
    }

    public void setWarrantyAssocViewVector(WarrantyAssocViewVector warrantyAssocViewVector) {
        this.warrantyAssocViewVector = warrantyAssocViewVector;
    }

    public void setStatusHistory(WarrantyStatusHistDataVector statusHistory) {
        this.statusHistory = statusHistory;
    }

    public void setWarrantyNotes(WarrantyNoteDataVector warrantyNotes) {
        this.warrantyNotes = warrantyNotes;
    }

    public void setAssetWarrantyViewVector(AssetWarrantyViewVector assetWarrantyViewVector) {
        this.assetWarrantyViewVector = assetWarrantyViewVector;
    }

    public void setWorkOrderItemWarrantyViewVector(WorkOrderItemWarrantyViewVector workOrderItemWarrantyViewVector) {
        this.workOrderItemWarrantyViewVector = workOrderItemWarrantyViewVector;
    }


    public WarrantyData getWarrantyData() {
        return warrantyData;
    }

    public BusEntityData getWarrantyProvider() {
        return warrantyProvider;
    }

    public WarrantyContentViewVector getContents() {
        return contents;
    }

    public WarrantyAssocViewVector getWarrantyAssocViewVector() {
        return warrantyAssocViewVector;
    }

    public WarrantyStatusHistDataVector getStatusHistory() {
        return statusHistory;
    }

    public WarrantyNoteDataVector getWarrantyNotes() {
        return warrantyNotes;
    }

    public AssetWarrantyViewVector getAssetWarrantyViewVector() {
        return assetWarrantyViewVector;
    }

    public WorkOrderItemWarrantyViewVector getWorkOrderItemWarrantyViewVector() {
        return workOrderItemWarrantyViewVector;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getWarrantyName() {
        return warrantyName;
    }

    public void setWarrantyName(String warrantyName) {
        this.warrantyName = warrantyName;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getWarrantyNumber() {
        return warrantyNumber;
    }

    public void setWarrantyNumber(String warrantyNumber) {
        this.warrantyNumber = warrantyNumber;
    }

    public String getWarrantyProviderId() {
        return warrantyProviderId;
    }

    public void setWarrantyProviderId(String warrantyProviderId) {
        this.warrantyProviderId = warrantyProviderId;
    }

    public String getDurationTypeCd() {
        return durationTypeCd;
    }

    public void setDurationTypeCd(String durationTypeCd) {
        this.durationTypeCd = durationTypeCd;
    }

    public void setAssetWarrantyCategoryAssoc(AssetDataVector assetWarrantyCategoryAssoc) {
        this.assetWarrantyCategoryAssoc = assetWarrantyCategoryAssoc;
    }


    public AssetDataVector getAssetWarrantyCategoryAssoc() {
        return assetWarrantyCategoryAssoc;
    }

    public void setServiceProvider(BusEntityData serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public BusEntityData getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public HashMap getAllServiceProvidersMap() {
        return allServiceProvidersMap;
    }

    public void setAllServiceProvidersMap(HashMap allServiceProvidersMap) {
        this.allServiceProvidersMap = allServiceProvidersMap;
    }

    public void resetManagementSource() {
        managementSource = UserWarrantyMgrLogic.className;
    }
}
