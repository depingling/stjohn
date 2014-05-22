package com.cleanwise.service.api.session;

import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Title:        WorkOrder
 * Description:  Remote Interface for Warranty Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date: 09.10.2007
 * Time: 19:21:02
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface WorkOrder extends javax.ejb.EJBObject {

    public WorkOrderDataVector getWorkOrderCollection(WorkOrderSimpleSearchCriteria criteria) throws RemoteException;

    public WorkOrderDetailView getWorkOrderDetailView(int workOrderId) throws RemoteException;

    public WorkOrderData updateWorkOrderData(WorkOrderData workOrder,
                                             WorkOrderAssocDataVector workOrderAssoc,
                                             WorkOrderPropertyDataVector properties,
                                             UserData user) throws RemoteException;

    public WarrantyDataVector getWorkOrderWarrantiesForAssets(IdVector assetIds) throws RemoteException;

    public WarrantyDataVector getWorkOrderWarrantiesForAssets(IdVector assetIds,String statusCd) throws RemoteException;

    public WorkOrderItemData updateWorkOrderItemData(WorkOrderItemData workOrderItem, WorkOrderAssetDataVector assetAssoc, UserData user) throws RemoteException;

    public WorkOrderNoteData updateWorkOrderNoteData(WorkOrderNoteData note, UserData user) throws RemoteException;

    public WorkOrderContentView updateWorkOrderContent(WorkOrderContentView workOrderContent, UserData user)throws RemoteException;

    public WorkOrderItemData updateWorkOrderItem(WorkOrderItemData workOrderItem, UserData user) throws RemoteException;

    public boolean updateWoiSequence(int workOrderItemId,int sequence, UserData user) throws RemoteException;

    public boolean removeWorkOrderContent(int wocId) throws RemoteException;

    public boolean removeWorkOrderContent(int wocId,int contentId) throws RemoteException;

    public boolean removeWorkOrderItem(int woiId) throws RemoteException;

    public int getClwWorkOrderItemSeq() throws RemoteException;

    public WorkOrderItemDetailViewVector updateWorkOrderItems(int workOrderId, WorkOrderItemDetailViewVector workOrderItems, UserData user) throws RemoteException;

    public WorkOrderItemDataVector getWorkOrderItems(WorkOrderItemSearchCriteria criteria) throws RemoteException;
    
    public WorkOrderDetailDataVector getWorkOrderDetails(int workOrderId) throws RemoteException;

    public HashMap getWoTotalCostMapForAsset(WorkOrderDataVector woDVector, int assetId) throws RemoteException;

    public WorkOrderItemDetailViewVector getWorkOrderItemDetailColection(WorkOrderItemSearchCriteria criteria) throws RemoteException;

    public WorkOrderDetailViewVector getWorkOrderDetailCollection(WorkOrderSimpleSearchCriteria criteria) throws RemoteException;

    public WorkOrderDetailView updateWorkOrderDetail(WorkOrderDetailView workOrder, ICleanwiseUser user, boolean workflowProcessing) throws RemoteException;
    public WorkOrderDetailView updateWorkOrderDetail(WorkOrderDetailView workOrder, UserData user) throws RemoteException;

    public HashMap getCostCentersOrderSumMap(int workOrderId) throws RemoteException;

    public SiteLedgerDataVector getSiteLedgerEntries(int workOrderId) throws RemoteException;

    public String updateStatus(int workOrderId, String status, String userName) throws RemoteException;

    public WorkOrderNoteData updateWorkOrderNoteData(WorkOrderNoteData note, String userName) throws RemoteException;

    public void removeSiteLedgerEntries(int workOrderId) throws RemoteException;
    
    public boolean checkForDuplicateWorkOrderNum(int accountId, int siteNumber, String workOrderNum) throws RemoteException;
    
    public boolean updateItemizedServiceLines(WorkOrderDetailDataVector updateDV) throws RemoteException;
    
    public boolean insertItemizedServiceLines(WorkOrderDetailDataVector insertDV) throws RemoteException;

    public HashMap getWorkOrderPriorities(WorkOrderSimpleSearchCriteria criteria) throws RemoteException;

    public WorkOrderSearchResultViewVector getWorkOrderSerchResult(WorkOrderSimpleSearchCriteria criteria) throws RemoteException;

    public WorkOrderData getWorkOrder(int workOrderId) throws RemoteException;

    public int getWorkOrderAccountId(int workOrderId) throws RemoteException;

    public WorkOrderPropertyData updateWorkOrderProperty(WorkOrderPropertyData property, UserData user) throws RemoteException;
	
}

