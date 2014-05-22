package com.cleanwise.service.api.session;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

import javax.ejb.CreateException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.AssetWarrantyDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.ContentDataAccess;
import com.cleanwise.service.api.dao.DispatchDataAccess;
import com.cleanwise.service.api.dao.DispatchWorkOrderDataAccess;
import com.cleanwise.service.api.dao.SiteLedgerDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dao.WarrantyDataAccess;
import com.cleanwise.service.api.dao.WoiStatusHistDataAccess;
import com.cleanwise.service.api.dao.WorkOrderAssetDataAccess;
import com.cleanwise.service.api.dao.WorkOrderAssocDataAccess;
import com.cleanwise.service.api.dao.WorkOrderContentDataAccess;
import com.cleanwise.service.api.dao.WorkOrderDAO;
import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import com.cleanwise.service.api.dao.WorkOrderDetailDataAccess;
import com.cleanwise.service.api.dao.WorkOrderItemDataAccess;
import com.cleanwise.service.api.dao.WorkOrderNoteDataAccess;
import com.cleanwise.service.api.dao.WorkOrderPropertyDataAccess;
import com.cleanwise.service.api.dao.WorkOrderStatusHistDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.process.WorkOrderProcessManager;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderLifeCycle;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.AssetView;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.ContentDetailView;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.ServiceProviderData;
import com.cleanwise.service.api.value.SiteLedgerDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserAccountRightsView;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.WarrantyData;
import com.cleanwise.service.api.value.WarrantyDataVector;
import com.cleanwise.service.api.value.WoiStatusHistData;
import com.cleanwise.service.api.value.WoiStatusHistDataVector;
import com.cleanwise.service.api.value.WorkOrderAssetData;
import com.cleanwise.service.api.value.WorkOrderAssetDataVector;
import com.cleanwise.service.api.value.WorkOrderAssetView;
import com.cleanwise.service.api.value.WorkOrderAssetViewVector;
import com.cleanwise.service.api.value.WorkOrderAssocData;
import com.cleanwise.service.api.value.WorkOrderAssocDataVector;
import com.cleanwise.service.api.value.WorkOrderContentData;
import com.cleanwise.service.api.value.WorkOrderContentView;
import com.cleanwise.service.api.value.WorkOrderContentViewVector;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDataVector;
import com.cleanwise.service.api.value.WorkOrderDetailData;
import com.cleanwise.service.api.value.WorkOrderDetailDataVector;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.value.WorkOrderDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderItemData;
import com.cleanwise.service.api.value.WorkOrderItemDataVector;
import com.cleanwise.service.api.value.WorkOrderItemDetailView;
import com.cleanwise.service.api.value.WorkOrderItemDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderItemSearchCriteria;
import com.cleanwise.service.api.value.WorkOrderNoteData;
import com.cleanwise.service.api.value.WorkOrderNoteDataVector;
import com.cleanwise.service.api.value.WorkOrderPropertyData;
import com.cleanwise.service.api.value.WorkOrderPropertyDataVector;
import com.cleanwise.service.api.value.WorkOrderSearchResultViewVector;
import com.cleanwise.service.api.value.WorkOrderSimpleSearchCriteria;
import com.cleanwise.service.api.value.WorkOrderStatusHistData;
import com.cleanwise.service.api.value.WorkOrderStatusHistDataVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import org.apache.log4j.Logger;

/**
 * Title:        WorkOrderBean
 * Description:  Bean implementation for WorkOrder Session Bean
 * Purpose:      Ejb for scheduled work order management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date: 09.10.2007
 * Time: 19:23:22
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class WorkOrderBean extends ApplicationServicesAPI {
    private static final Logger log = Logger.getLogger(WorkOrderBean.class);
    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

	public WorkOrderSearchResultViewVector getWorkOrderSerchResult(WorkOrderSimpleSearchCriteria criteria)  throws RemoteException {
		WorkOrderSearchResultViewVector result = new WorkOrderSearchResultViewVector();
        Connection conn = null;
        try {
            if (criteria != null) {
                conn = getConnection();
                result = getWorkOrderSearchResult(conn, criteria);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return result;
		
	}

    private WorkOrderSearchResultViewVector getWorkOrderSearchResult(Connection conn, WorkOrderSimpleSearchCriteria criteria) throws SQLException {
        DBCriteria dbCriteria = new DBCriteria();
        WorkOrderSearchResultViewVector result = new WorkOrderSearchResultViewVector();

        if (criteria != null) {

            dbCriteria = convertToDBCriteria(criteria);
            result = WorkOrderDAO.selectWorkOrderSearchResult(conn, dbCriteria);

        }

        return result;

    }

    public WorkOrderDataVector getWorkOrderCollection(WorkOrderSimpleSearchCriteria criteria) throws RemoteException {
        WorkOrderDataVector result = new WorkOrderDataVector();
        Connection conn = null;
        try {
            if (criteria != null) {
                conn = getConnection();
                result = getWorkOrderCollection(conn, criteria);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return result;
    }

    private WorkOrderDataVector getWorkOrderCollection(Connection conn, WorkOrderSimpleSearchCriteria criteria) throws SQLException {
        DBCriteria dbCriteria = new DBCriteria();
        WorkOrderDataVector result = new WorkOrderDataVector();

        if (criteria != null) {

            dbCriteria = convertToDBCriteria(criteria);
            result = WorkOrderDataAccess.select(conn, dbCriteria);

        }

        return result;

    }

    public WorkOrderDetailViewVector getWorkOrderDetailCollection(WorkOrderSimpleSearchCriteria criteria) throws RemoteException {

        WorkOrderDetailViewVector result = new WorkOrderDetailViewVector();
        Connection conn = null;

        try {
            if (criteria != null) {
                conn = getConnection();
                result = getWorkOrderDetailCollection(conn, criteria);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return result;
    }

    private WorkOrderDetailViewVector getWorkOrderDetailCollection(Connection conn, WorkOrderSimpleSearchCriteria criteria) throws Exception {

        WorkOrderDetailViewVector result = new WorkOrderDetailViewVector();

        DBCriteria dbCriteria = convertToDBCriteria(criteria);
        WorkOrderDataVector woDataCollection = WorkOrderDataAccess.select(conn, dbCriteria);

        Iterator it = woDataCollection.iterator();
        while (it.hasNext()) {
            WorkOrderData wo = (WorkOrderData) it.next();
            WorkOrderDetailView woDetail = getWorkOrderDetailView(conn, wo);
            result.add(woDetail);
        }

        return result;
    }

    private WorkOrderDetailView getWorkOrderDetailView(Connection conn, WorkOrderData workOrder) throws Exception {

        WorkOrderDetailView detailView = WorkOrderDetailView.createValue();

        int workOrderId = workOrder.getWorkOrderId();

        WorkOrderNoteDataVector notes = getNotesByWorkOrderId(conn, workOrderId);
        WorkOrderStatusHistDataVector histories = getStatusHistoriesByWorkOrderId(conn, workOrderId);
        WorkOrderContentViewVector contents = getWorkOrderContentsByWorkOrderId(conn, workOrderId);
        WorkOrderItemDetailViewVector workOrderItems = getWorkOrderItems(conn, workOrderId);
        WorkOrderAssocDataVector assoc = getWorkOrderAssocByWorkOrderId(conn, workOrderId);
        WorkOrderPropertyDataVector properties = getWorkOrderProperties(conn, workOrderId);
    	ServiceProviderData serviceProvider = getWorkOrderServiceProvider(conn, workOrderId);
    	WorkOrderDetailDataVector itemizedService = getWorkOrderDetails(conn, workOrderId);
    	
        detailView.setWorkOrder(workOrder);
        detailView.setContents(contents);
        detailView.setNotes(notes);
        detailView.setStatusHistory(histories);
        detailView.setWorkOrderItems(workOrderItems);
        detailView.setWorkOrderAssocCollection(assoc);
        detailView.setProperties(properties);
    	detailView.setServiceProvider(serviceProvider);
    	detailView.setItemizedService(itemizedService);
        

        return detailView;
    }

	private ServiceProviderData getWorkOrderServiceProvider(Connection conn,
			int workOrderId) throws SQLException, DataNotFoundException,
			RemoteException {
		ServiceProviderData woProvider = null;
        int providerId = WorkOrderUtil.getAssignedServiceProviderId(getWorkOrderAssocByWorkOrderId(conn, workOrderId));
        if(providerId > 0)
        {
        	woProvider = new ServiceBean().getServiceProvider(conn, providerId);
        }
		return woProvider;
	}

    private DBCriteria convertToDBCriteria(WorkOrderSimpleSearchCriteria criteria) {

        DBCriteria dbCrit = new DBCriteria();

        if (Utility.isSet(criteria.getWorkOrderNum())) {
            if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchType())) {
                if (criteria.getIgnoreCase()) {
                    dbCrit.addBeginsWithIgnoreCase(WorkOrderDataAccess.WORK_ORDER_NUM, criteria.getWorkOrderNum());
                } else {
                    dbCrit.addBeginsWith(WorkOrderDataAccess.WORK_ORDER_NUM, criteria.getWorkOrderNum());
                }
            } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchType())) {
                if (criteria.getIgnoreCase()) {
                    dbCrit.addContainsIgnoreCase(WorkOrderDataAccess.WORK_ORDER_NUM, criteria.getWorkOrderNum());
                } else {
                    dbCrit.addContains(WorkOrderDataAccess.WORK_ORDER_NUM, criteria.getWorkOrderNum());
                }
            } else {
                if (criteria.getIgnoreCase()) {
                    dbCrit.addEqualToIgnoreCase(WorkOrderDataAccess.WORK_ORDER_NUM, criteria.getWorkOrderNum());
                } else {
                    dbCrit.addEqualTo(WorkOrderDataAccess.WORK_ORDER_NUM, criteria.getWorkOrderNum());
                }
            }
        }

        if (Utility.isSet(criteria.getPoNumber())) {
            if (criteria.getIgnoreCase()) {
                dbCrit.addContainsIgnoreCase(WorkOrderDataAccess.PO_NUMBER, criteria.getPoNumber());
            } else {
                dbCrit.addContains(WorkOrderDataAccess.PO_NUMBER, criteria.getPoNumber());
            }
        }

        if (criteria.getWorkOrderId() > 0) {
            dbCrit.addEqualTo(WorkOrderPropertyDataAccess.WORK_ORDER_ID, criteria.getWorkOrderId());
        }

        if (Utility.isSet(criteria.getWorkOrderName())) {
            if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchType())) {
                if (criteria.getIgnoreCase()) {
                    dbCrit.addBeginsWithIgnoreCase(WorkOrderDataAccess.SHORT_DESC, criteria.getWorkOrderName());
                } else {
                    dbCrit.addBeginsWith(WorkOrderDataAccess.SHORT_DESC, criteria.getWorkOrderName());
                }
            } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchType())) {
                if (criteria.getIgnoreCase()) {
                    dbCrit.addContainsIgnoreCase(WorkOrderDataAccess.SHORT_DESC, criteria.getWorkOrderName());
                } else {
                    dbCrit.addContains(WorkOrderDataAccess.SHORT_DESC, criteria.getWorkOrderName());
                }
            } else {
                if (criteria.getIgnoreCase()) {
                    dbCrit.addEqualToIgnoreCase(WorkOrderDataAccess.SHORT_DESC, criteria.getWorkOrderName());
                } else {
                    dbCrit.addEqualTo(WorkOrderDataAccess.SHORT_DESC, criteria.getWorkOrderName());
                }
            }
        }

        if (Utility.isSet(criteria.getPriority())) {
            dbCrit.addEqualTo(WorkOrderDataAccess.PRIORITY, criteria.getPriority());
        }

        if (Utility.isSet(criteria.getType())) {
            dbCrit.addEqualTo(WorkOrderDataAccess.TYPE_CD, criteria.getType());
        }

        if (Utility.isSet(criteria.getStatus())) {
            dbCrit.addEqualTo(WorkOrderDataAccess.STATUS_CD, criteria.getStatus());
        }

        if ((criteria.getExcludeStatus() != null) && (!criteria.getExcludeStatus().isEmpty())) {
            dbCrit.addNotOneOf(WorkOrderDataAccess.STATUS_CD, criteria.getExcludeStatus());
        }

        if (criteria.getActualStartDate() != null) {
            dbCrit.addGreaterOrEqual(WorkOrderDataAccess.ACTUAL_START_DATE, criteria.getActualStartDate());
        }

        if (criteria.getActualFinishDate() != null) {
            dbCrit.addLessOrEqual(WorkOrderDataAccess.ACTUAL_FINISH_DATE, criteria.getActualFinishDate());
        }

        if (criteria.getEstimateStartDate() != null) {
            dbCrit.addGreaterOrEqual(WorkOrderDataAccess.ESTIMATE_START_DATE, criteria.getEstimateStartDate());
        }

        if (criteria.getEstimateFinishDate() != null) {
            dbCrit.addLessOrEqual(WorkOrderDataAccess.ESTIMATE_FINISH_DATE, criteria.getEstimateFinishDate());
        }
        
        
        if (criteria.getSiteIds() != null) {
        	// current site only
            dbCrit.addOneOf(WorkOrderDataAccess.BUS_ENTITY_ID, criteria.getSiteIds());
        } else {
            if (criteria.getUserId() > 0 && !criteria.isUserAuthorizedForAssetWOViewAllForStore()) {
            	// only sites assigned to the user
                DBCriteria isolCrit = new DBCriteria();

                dbCrit.addJoinTable(UserDataAccess.CLW_USER);
                dbCrit.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);
                dbCrit.addJoinTable(WorkOrderDataAccess.CLW_WORK_ORDER);

                isolCrit.addEqualTo(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, criteria.getUserId());
                isolCrit.addCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID + "=" + UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ID);
                isolCrit.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID + "=" + WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.BUS_ENTITY_ID);
                isolCrit.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);

                dbCrit.addIsolatedCriterita(isolCrit);
            }
            else
            {
            	// all sites of current store
            	dbCrit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA_SITE_TO_ACCT");
            	dbCrit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA_ACCT_TO_STORE");
            	DBCriteria isolCrit = new DBCriteria();
            	isolCrit.addEqualTo("BEA_SITE_TO_ACCT." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, 
            			RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            	isolCrit.addEqualTo("BEA_ACCT_TO_STORE." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, 
            			RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            	isolCrit.addCondition("BEA_SITE_TO_ACCT." + BusEntityAssocDataAccess.BUS_ENTITY1_ID +"=" +
            			WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.BUS_ENTITY_ID);
            	isolCrit.addCondition("BEA_ACCT_TO_STORE." + BusEntityAssocDataAccess.BUS_ENTITY1_ID +"=" +
            			"BEA_SITE_TO_ACCT." + BusEntityAssocDataAccess.BUS_ENTITY2_ID);
            	isolCrit.addEqualTo("BEA_ACCT_TO_STORE." + BusEntityAssocDataAccess.BUS_ENTITY2_ID, 
            			criteria.getStoreId());
                dbCrit.addIsolatedCriterita(isolCrit);
            }
        }
        
//        if (criteria.getProviderId() > 0) {
//            DBCriteria isolCrit = new DBCriteria();
//
//            dbCrit.addJoinTable(BusEntityDataAccess.CLW_BUS_ENTITY);
//            dbCrit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA_SITE");
//            dbCrit.addJoinTable(WorkOrderDataAccess.CLW_WORK_ORDER);
//
//            isolCrit.addEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID, criteria.getProviderId());
//            isolCrit.addCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID + " = " + "BEA_SITE." + BusEntityAssocDataAccess.BUS_ENTITY1_ID);
//            isolCrit.addEqualTo("BEA_SITE." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE);
//            isolCrit.addCondition("BEA_SITE." + BusEntityAssocDataAccess.BUS_ENTITY2_ID + " = " + WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.BUS_ENTITY_ID);
//
//            if (criteria.getStoreId() > 0) {
//                dbCrit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA_STORE");
//
//                isolCrit.addEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID, criteria.getProviderId());
//                isolCrit.addCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID + " = " + "BEA_STORE." + BusEntityAssocDataAccess.BUS_ENTITY1_ID);
//                isolCrit.addEqualTo("BEA_STORE." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE);
//                isolCrit.addEqualTo("BEA_STORE." + BusEntityAssocDataAccess.BUS_ENTITY2_ID, criteria.getStoreId());
//            }
//
//            dbCrit.addIsolatedCriterita(isolCrit);
//            
//            dbCrit.addJoinCondition(WorkOrderDataAccess.CLW_WORK_ORDER, WorkOrderDataAccess.WORK_ORDER_ID,
//                WorkOrderAssocDataAccess.CLW_WORK_ORDER_ASSOC, WorkOrderAssocDataAccess.WORK_ORDER_ID);
//            dbCrit.addJoinTableEqualTo(WorkOrderAssocDataAccess.CLW_WORK_ORDER_ASSOC,
//                WorkOrderAssocDataAccess.BUS_ENTITY_ID, criteria.getProviderId());
//        }
        
        if (criteria.getProviderIds() != null && !criteria.getProviderIds().isEmpty()) {
            dbCrit.addJoinTable(WorkOrderAssocDataAccess.CLW_WORK_ORDER_ASSOC + " BEA_PROVIDER");
            DBCriteria isolCrit = new DBCriteria();
            isolCrit.addCondition(WorkOrderDataAccess.CLW_WORK_ORDER + "." +  WorkOrderDataAccess.WORK_ORDER_ID + " = " + 
            		"BEA_PROVIDER." + WorkOrderAssocDataAccess.WORK_ORDER_ID);
            isolCrit.addEqualTo("BEA_PROVIDER." + WorkOrderAssocDataAccess.WORK_ORDER_ASSOC_CD, 
            		RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER);
            isolCrit.addOneOf("BEA_PROVIDER." + WorkOrderAssocDataAccess.BUS_ENTITY_ID, criteria.getProviderIds());
            dbCrit.addIsolatedCriterita(isolCrit);
        }
        
        if (Utility.isSet(criteria.getHistStatus())) {
            dbCrit.addOneOf(WorkOrderDataAccess.WORK_ORDER_ID,
                    "SELECT DISTINCT work_order_id FROM " + WorkOrderStatusHistDataAccess.CLW_WORK_ORDER_STATUS_HIST + " " +
                                                   "WHERE status_cd = '"+ criteria.getHistStatus()+ "' AND " +
                                                   WorkOrderStatusHistDataAccess.CLW_WORK_ORDER_STATUS_HIST + "." +
                                                   WorkOrderStatusHistDataAccess.WORK_ORDER_ID + " = " +
                                                   WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.WORK_ORDER_ID);
        }
        
        if (criteria.getSpCancelCondition()) {
            dbCrit.addNotOneOf(WorkOrderDataAccess.WORK_ORDER_ID,
                    "SELECT DISTINCT wos_hist2.work_order_id " +
                        "FROM clw_work_order_status_hist  wos_hist1, " +
                        "clw_work_order_status_hist  wos_hist2 " +

                        "WHERE wos_hist1.work_order_id = wos_hist2.work_order_id " +
                        "AND wos_hist1.work_order_id = " + WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.WORK_ORDER_ID + " " +
                        "AND wos_hist2.work_order_id = " + WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.WORK_ORDER_ID + " " +
                        "AND wos_hist1.status_cd = '" + RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED + "' " +
                        "AND wos_hist2.status_cd = '" + RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER + "' " +
                        "AND wos_hist2.mod_date > wos_hist1.mod_date");
        }
        
        if (criteria.getDispatchId() > 0) {
            dbCrit.addJoinCondition(WorkOrderDataAccess.CLW_WORK_ORDER, WorkOrderDataAccess.WORK_ORDER_ID,
                    DispatchWorkOrderDataAccess.CLW_DISPATCH_WORK_ORDER, DispatchWorkOrderDataAccess.WORK_ORDER_ID);
            dbCrit.addJoinCondition(DispatchWorkOrderDataAccess.CLW_DISPATCH_WORK_ORDER, DispatchWorkOrderDataAccess.DISPATCH_ID,
                    DispatchDataAccess.CLW_DISPATCH, DispatchDataAccess.DISPATCH_ID);
            dbCrit.addJoinTableEqualTo(DispatchDataAccess.CLW_DISPATCH, DispatchDataAccess.DISPATCH_ID, criteria.getDispatchId());

        }
        
        if(criteria.getDistributorShipToLocationNumber() != null) {
        	dbCrit.addOneOf(WorkOrderDataAccess.BUS_ENTITY_ID, 
        			String.format(WorkOrderUtil.SUB_QUERY_FOR_SITES_WITH_DIST_SITE_REF_NUM, 
        					new Object[] {criteria.getDistributorShipToLocationNumber()}) );
        }
        
        if(criteria.getDistributorAccountNumber() != null) {
        	dbCrit.addOneOf(WorkOrderDataAccess.BUS_ENTITY_ID, 
					String.format(WorkOrderUtil.SUB_QUERY_FOR_SITES_WITH_DIST_ACCT_REF_NUM, 
        					new Object[] {criteria.getDistributorAccountNumber()}));
        }

        return dbCrit;
    }

    public WorkOrderDetailView getWorkOrderDetailView(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWorkOrderDetailView(conn, workOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

    private WorkOrderDetailView getWorkOrderDetailView(Connection conn, int workOrderId) throws Exception {

        WorkOrderData workOrder = getWorkOrder(conn, workOrderId);
        return getWorkOrderDetailView(conn,workOrder);

    }

    private WorkOrderPropertyDataVector getWorkOrderProperties(Connection conn, int workOrderId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderPropertyDataAccess.WORK_ORDER_ID, workOrderId);
        dbCrit.addIsNull(WorkOrderPropertyDataAccess.WORK_ORDER_ITEM_ID);
        return WorkOrderPropertyDataAccess.select(conn, dbCrit);
    }

    private WorkOrderPropertyDataVector getWorkOrderProperties(Connection conn, int workOrderId, int workOrderItemId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderPropertyDataAccess.WORK_ORDER_ID, workOrderId);
        dbCrit.addEqualTo(WorkOrderPropertyDataAccess.WORK_ORDER_ITEM_ID, workOrderItemId);
        return WorkOrderPropertyDataAccess.select(conn, dbCrit);
    }

    private WorkOrderAssocDataVector getWorkOrderAssocByWorkOrderId(Connection conn, int workOrderId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderAssocDataAccess.WORK_ORDER_ID, workOrderId);
        return WorkOrderAssocDataAccess.select(conn, dbCrit);
    }

    private WorkOrderItemDetailViewVector getWorkOrderItems(Connection conn, int workOrderId) throws Exception {

        WorkOrderItemDetailViewVector result = new WorkOrderItemDetailViewVector();

        WorkOrderItemDataVector woiCollection = getWorkOrderItemsByWorkOrderId(conn, workOrderId);

        if (!woiCollection.isEmpty()) {
            Iterator itar = woiCollection.iterator();
            while (itar.hasNext()) {
                result.add(getWoiDetailView(conn, (WorkOrderItemData) itar.next()));
            }
        }

        return result;
    }

    private WorkOrderItemDetailView getWoiDetailView(Connection conn, WorkOrderItemData woi) throws Exception {

        WorkOrderItemDetailView detailView = WorkOrderItemDetailView.createValue();

        WorkOrderContentViewVector contents = getWorkOrderItemContents(conn, woi.getWorkOrderId(), woi.getWorkOrderItemId());
        WoiStatusHistDataVector histories = getStatusHistoriesByWoiId(conn, woi.getWorkOrderItemId());
        WorkOrderAssetViewVector assets = getAssetAssocViewByWoiId(conn, woi.getWorkOrderItemId());
        WarrantyData warranty = getWarranty(conn, woi.getWarrantyId());
        WorkOrderPropertyDataVector props = getWorkOrderProperties(conn, woi.getWorkOrderId(), woi.getWorkOrderItemId());

        detailView.setWorkOrderItem(woi);
        detailView.setWarranty(warranty);
        detailView.setStatusHistories(histories);
        detailView.setAssetAssoc(assets);
        detailView.setContents(contents);
        detailView.setProperties(props);

        return detailView;
    }

    private WarrantyData getWarranty(Connection conn, int warrantyId) throws SQLException, DataNotFoundException {
        if (warrantyId > 0) {
            return WarrantyDataAccess.select(conn, warrantyId);
        } else {
            return null;
        }
    }

    private WorkOrderAssetViewVector getAssetAssocViewByWoiId(Connection conn, int workOrderItemId) throws Exception {

        DBCriteria dbCrit = new DBCriteria();
        WorkOrderAssetViewVector result = new WorkOrderAssetViewVector();
        HashMap assetMap = new HashMap();

        dbCrit.addEqualTo(WorkOrderAssetDataAccess.WORK_ORDER_ITEM_ID, workOrderItemId);
        WorkOrderAssetDataVector woAssetDV = WorkOrderAssetDataAccess.select(conn, dbCrit);

        if (!woAssetDV.isEmpty()) {

            IdVector assetIds = new IdVector();
            Iterator itar = woAssetDV.iterator();
            while (itar.hasNext()) {
                WorkOrderAssetData woAsset = (WorkOrderAssetData) itar.next();
                assetIds.add(new Integer(woAsset.getAssetId()));
            }

            //gets asset map
            if (!assetIds.isEmpty()) {
                AssetSearchCriteria crit = new AssetSearchCriteria();
                Asset assetEjb=APIAccess.getAPIAccess().getAssetAPI();
                crit.setAssetIds(assetIds);
                crit.setShowInactive(true);
                AssetViewVector assets = assetEjb.getAssetViewVector(crit);
                assetMap = toMap(assets);
            }

            itar = woAssetDV.iterator();
            while (itar.hasNext()) {
                WorkOrderAssetView woAssetView = WorkOrderAssetView.createValue();

                WorkOrderAssetData woAssetData = (WorkOrderAssetData) itar.next();
                Integer assetKey = new Integer(woAssetData.getAssetId());
                AssetView asset = (AssetView) assetMap.get(assetKey);

                woAssetView.setAssetView(asset);
                woAssetView.setWorkOrderAssetData(woAssetData);

                result.add(woAssetView);
            }
        }
        return result;
    }

    private WorkOrderAssetDataVector getAssetAssocByWoiId(Connection conn, int workOrderItemId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderAssetDataAccess.WORK_ORDER_ITEM_ID, workOrderItemId);
        return WorkOrderAssetDataAccess.select(conn, dbCrit);

    }

    private HashMap toMap(AssetViewVector assets) {
        HashMap assetInfoMap = new HashMap();
        if ( assets != null) {
            Iterator it =  assets.iterator();
            while (it.hasNext()) {

                AssetView asset = (AssetView) it.next();
                assetInfoMap.put(new Integer(asset.getAssetId()),asset);
            }
        }
        return assetInfoMap;
    }

    private WoiStatusHistDataVector getStatusHistoriesByWoiId(Connection conn, int workOrderItemId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WoiStatusHistDataAccess.WORK_ORDER_ITEM_ID, workOrderItemId);
        return WoiStatusHistDataAccess.select(conn, dbCrit);

    }

    private WorkOrderItemDataVector getWorkOrderItemsByWorkOrderId(Connection conn, int workOrderId) throws SQLException, DataNotFoundException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderItemDataAccess.WORK_ORDER_ID, workOrderId);
        return WorkOrderItemDataAccess.select(conn, dbCrit);
    }

    private WorkOrderContentViewVector getWorkOrderContentsByWorkOrderId(Connection conn, int workOrderId) throws Exception {


        String sql = "SELECT C.BUS_ENTITY_ID," +
                "C.CONTENT_ID," +
                "C.ITEM_ID," +
                "C.ADD_BY," +
                "C.ADD_DATE," +
                "C.CONTENT_STATUS_CD," +
                "C.CONTENT_TYPE_CD," +
                "C.CONTENT_USAGE_CD," +
                "C.EFF_DATE," +
                "C.EXP_DATE," +
                "C.LANGUAGE_CD," +
                "C.LOCALE_CD," +
                "C.LONG_DESC," +
                "C.MOD_BY," +
                "C.MOD_DATE," +
                "C.PATH," +
                "C.SHORT_DESC," +
                "C.SOURCE_CD," +
                "C.VERSION," +
                "C.BINARY_DATA,"+
                "WRC.WORK_ORDER_CONTENT_ID," +
                "WRC.WORK_ORDER_ID," +
                "WRC.URL," +
                "WRC.MOD_BY," +
                "WRC.MOD_DATE," +
                "WRC.ADD_BY," +
                "WRC.ADD_DATE " +
                " FROM CLW_CONTENT C,CLW_WORK_ORDER_CONTENT WRC " +
                "   WHERE WRC.WORK_ORDER_ID = ? "+
                "   AND WRC.CONTENT_ID = C.CONTENT_ID " +
                "   AND WRC.WORK_ORDER_ITEM_ID IS NULL";

        logInfo("getWorkOrderContentsByWorkOrderId => sql:" + sql);
        logInfo("getWorkOrderContentsByWorkOrderId => param[workOrderId]:"+workOrderId);

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,workOrderId);
        ResultSet rs = pstmt.executeQuery();

        WorkOrderContentViewVector v = new WorkOrderContentViewVector();
        while (rs.next()) {
            ContentData c = ContentData.createValue();
            WorkOrderContentData wrc = WorkOrderContentData.createValue();

            c.setBusEntityId(rs.getInt(1));
            c.setContentId(rs.getInt(2));
            c.setItemId(rs.getInt(3));
            c.setAddBy(rs.getString(4));
            c.setAddDate(rs.getTimestamp(5));
            c.setContentStatusCd(rs.getString(6));
            c.setContentTypeCd(rs.getString(7));
            c.setContentUsageCd(rs.getString(8));
            c.setEffDate(rs.getDate(9));
            c.setExpDate(rs.getDate(10));
            c.setLanguageCd(rs.getString(11));
            c.setLocaleCd(rs.getString(12));
            c.setLongDesc(rs.getString(13));
            c.setModBy(rs.getString(14));
            c.setModDate(rs.getTimestamp(15));
            c.setPath(rs.getString(16));
            c.setShortDesc(rs.getString(17));
            c.setSourceCd(rs.getString(18));
            c.setVersion(rs.getInt(19));
            c.setBinaryData(rs.getBytes(20));

            wrc.setWorkOrderContentId(rs.getInt(21));
            wrc.setWorkOrderId(rs.getInt(22));
            wrc.setUrl(rs.getString(23));
            wrc.setModBy(rs.getString(24));
            wrc.setModDate(rs.getTimestamp(25));
            wrc.setAddBy(rs.getString(26));
            wrc.setAddDate(rs.getTimestamp(27));

            v.add(new WorkOrderContentView(convertToContentDetailView(c), wrc));
        }

        rs.close();
        pstmt.close();

        return v;
    }

    private WorkOrderContentViewVector getWorkOrderItemContents(Connection conn, int workOrderId, int workOderItemId) throws Exception {

        String sql = "SELECT C.BUS_ENTITY_ID," +
                "C.CONTENT_ID," +
                "C.ITEM_ID," +
                "C.ADD_BY," +
                "C.ADD_DATE," +
                "C.CONTENT_STATUS_CD," +
                "C.CONTENT_TYPE_CD," +
                "C.CONTENT_USAGE_CD," +
                "C.EFF_DATE," +
                "C.EXP_DATE," +
                "C.LANGUAGE_CD," +
                "C.LOCALE_CD," +
                "C.LONG_DESC," +
                "C.MOD_BY," +
                "C.MOD_DATE," +
                "C.PATH," +
                "C.SHORT_DESC," +
                "C.SOURCE_CD," +
                "C.VERSION," +
                "C.BINARY_DATA,"+
                "WRC.WORK_ORDER_CONTENT_ID," +
                "WRC.WORK_ORDER_ID," +
                "WRC.WORK_ORDER_ITEM_ID," +
                "WRC.URL," +
                "WRC.MOD_BY," +
                "WRC.MOD_DATE," +
                "WRC.ADD_BY," +
                "WRC.ADD_DATE " +
                " FROM CLW_CONTENT C,CLW_WORK_ORDER_CONTENT WRC " +
                "   WHERE WRC.WORK_ORDER_ID = ?"+
                "   AND WRC.CONTENT_ID = C.CONTENT_ID " +
                "   AND WRC.WORK_ORDER_ITEM_ID = ?";

        logInfo("getWorkOrderItemContents => sql:" + sql);
        logInfo("getWorkOrderItemContents => param[workOrderId,workOderItemId]:"+workOrderId+","+workOderItemId);

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,workOrderId);
        pstmt.setInt(2,workOderItemId);

        ResultSet rs = pstmt.executeQuery();

        WorkOrderContentViewVector v = new WorkOrderContentViewVector();
        while (rs.next()) {
            ContentData c = ContentData.createValue();
            WorkOrderContentData wrc = WorkOrderContentData.createValue();

            c.setBusEntityId(rs.getInt(1));
            c.setContentId(rs.getInt(2));
            c.setItemId(rs.getInt(3));
            c.setAddBy(rs.getString(4));
            c.setAddDate(rs.getTimestamp(5));
            c.setContentStatusCd(rs.getString(6));
            c.setContentTypeCd(rs.getString(7));
            c.setContentUsageCd(rs.getString(8));
            c.setEffDate(rs.getDate(9));
            c.setExpDate(rs.getDate(10));
            c.setLanguageCd(rs.getString(11));
            c.setLocaleCd(rs.getString(12));
            c.setLongDesc(rs.getString(13));
            c.setModBy(rs.getString(14));
            c.setModDate(rs.getTimestamp(15));
            c.setPath(rs.getString(16));
            c.setShortDesc(rs.getString(17));
            c.setSourceCd(rs.getString(18));
            c.setVersion(rs.getInt(19));
            c.setBinaryData(rs.getBytes(20));

            wrc.setWorkOrderContentId(rs.getInt(21));
            wrc.setWorkOrderId(rs.getInt(22));
            wrc.setWorkOrderItemId(rs.getInt(23));
            wrc.setUrl(rs.getString(24));
            wrc.setModBy(rs.getString(25));
            wrc.setModDate(rs.getTimestamp(26));
            wrc.setAddBy(rs.getString(27));
            wrc.setAddDate(rs.getTimestamp(28));

            v.add(new WorkOrderContentView(convertToContentDetailView(c), wrc));
        }

        rs.close();
        pstmt.close();

        return v;
    }


    private ContentDetailView convertToContentDetailView(ContentData contentData) throws SQLException {
        ContentDetailView contentDetailView = ContentDetailView.createValue();

        if (contentData != null) {
            contentDetailView.setContentId(contentData.getContentId());
            contentDetailView.setBusEntityId(contentData.getBusEntityId());
            contentDetailView.setItemId(contentData.getItemId());
            contentDetailView.setContentStatusCd(contentData.getContentStatusCd());
            contentDetailView.setContentTypeCd(contentData.getContentTypeCd());
            contentDetailView.setLocaleCd(contentData.getLocaleCd());
            contentDetailView.setLanguageCd(contentData.getLanguageCd());
            contentDetailView.setLongDesc(contentData.getLongDesc());
            contentDetailView.setPath(contentData.getPath());
            contentDetailView.setShortDesc(contentData.getShortDesc());
            contentDetailView.setSourceCd(contentData.getSourceCd());
            contentDetailView.setVersion(contentData.getVersion());
            contentDetailView.setEffDate(contentData.getEffDate());
            contentDetailView.setExpDate(contentData.getExpDate());
            contentDetailView.setAddDate(contentData.getAddDate());
            contentDetailView.setAddBy(contentData.getAddBy());
            contentDetailView.setModDate(contentData.getModDate());
            contentDetailView.setModBy(contentData.getModBy());
            contentDetailView.setContentUsageCd(contentData.getContentUsageCd());
            byte[] data =new byte[0];
            if(contentData.getBinaryData()!=null){
                data=contentData.getBinaryData();
            }
            contentDetailView.setData(data);

        }

        return contentDetailView;
    }

    private WorkOrderStatusHistDataVector getStatusHistoriesByWorkOrderId(Connection conn, int workOrderId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderStatusHistDataAccess.WORK_ORDER_ID, workOrderId);
        dbCrit.addOrderBy(WorkOrderStatusHistDataAccess.MOD_DATE);
        return WorkOrderStatusHistDataAccess.select(conn, dbCrit);
    }

    private WorkOrderNoteDataVector getNotesByWorkOrderId(Connection conn, int workOrderId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(WorkOrderNoteDataAccess.WORK_ORDER_ID, workOrderId);
        return WorkOrderNoteDataAccess.select(conn, dbCrit);
    }

    public WorkOrderData getWorkOrder(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWorkOrder(conn, workOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WorkOrderData getWorkOrder(Connection conn, int workOrderId) throws SQLException, DataNotFoundException {
        return WorkOrderDataAccess.select(conn, workOrderId);
    }
    
    public int getWorkOrderAccountId(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            
            String sql =
            	"SELECT   a.bus_entity2_id account_id " +
            	  "FROM   clw_bus_entity_assoc a, clw_work_order wo " +
            	 "WHERE       (wo.bus_entity_id = a.bus_entity1_id) " +
            	         "AND (a.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "') " +
            	         "AND (wo.work_order_id = " + workOrderId + ") ";            	

            logInfo("getWorkOrderAccountId => sql:" + sql);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
            	return rs.getInt("account_id");
            }
            else {
            	throw new Exception("Account not found for wok order id=" + workOrderId );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    

    private WorkOrderItemData getWorkOrderItem(Connection conn, int workOrderItemId) throws SQLException, DataNotFoundException {
        return WorkOrderItemDataAccess.select(conn, workOrderItemId);
    }

    public WorkOrderData updateWorkOrderData(WorkOrderData workOrder,
                                             WorkOrderAssocDataVector workOrderAssoc,
                                             WorkOrderPropertyDataVector properties,
                                             UserData user) throws RemoteException {
        Connection conn = null;
        try {
            if (workOrder != null) {

                conn = getConnection();

                workOrder = updateWorkOrder(conn, workOrder, user);
                updateWorkOrderAssoc(conn, workOrder.getWorkOrderId(), workOrderAssoc, user);
                updateWorkOrderProperties(conn, workOrder.getWorkOrderId(), 0, properties, user);

            }
            return workOrder;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    private WorkOrderStatusHistData addStatusToHistory(Connection conn, int workOrderId, String statusCd, String userName) throws SQLException {
        WorkOrderStatusHistData statusHistory = WorkOrderStatusHistData.createValue();
        statusHistory.setStatusCd(statusCd);
        statusHistory.setTypeCd(RefCodeNames.WORK_ORDER_STATUS_HIST_TYPE_CD.UNKNOWN);
        statusHistory.setStatusDate(new Date());
        statusHistory.setWorkOrderId(workOrderId);
        return updateWorkOrderStatusHistory(conn, statusHistory, userName);
    }

    private WorkOrderStatusHistData updateWorkOrderStatusHistory(Connection conn, WorkOrderStatusHistData statusHistory, String userName) throws SQLException {
        if (statusHistory != null) {
            if (statusHistory.getWorkOrderStatusHistId() > 0) {
                statusHistory.setModBy(userName);
                WorkOrderStatusHistDataAccess.update(conn, statusHistory);
            } else {
                statusHistory.setAddBy(userName);
                statusHistory.setModBy(userName);
                statusHistory = WorkOrderStatusHistDataAccess.insert(conn, statusHistory);
            }
        }
        return statusHistory;
    }

    private HashSet toSet(WorkOrderAssocDataVector workOrderAssoc) {
        HashSet set = new HashSet();
        if (workOrderAssoc != null) {
            Iterator it = workOrderAssoc.iterator();
            while (it.hasNext()) {

                WorkOrderAssocData wrkAssoc = (WorkOrderAssocData) it.next();
                set.add(new Integer(wrkAssoc.getWorkOrderAssocId()));
            }
        }
        return set;
    }

    private WorkOrderAssocDataVector updateWorkOrderAssoc(Connection conn,
                                                          int workOrderId,
                                                          WorkOrderAssocDataVector workOrderAssoc,
                                                          UserData user) throws SQLException {

        WorkOrderAssocDataVector currentAssoc = getWorkOrderAssocByWorkOrderId(conn, workOrderId);
        HashSet currentAssocIdsSet = toSet(currentAssoc);

        if (workOrderAssoc != null && !workOrderAssoc.isEmpty()) {
            Iterator it = workOrderAssoc.iterator();
            while (it.hasNext()) {
                WorkOrderAssocData assoc = (WorkOrderAssocData) it.next();
                assoc.setWorkOrderId(workOrderId);
                if (assoc.getWorkOrderAssocId() > 0) {
                    assoc.setModBy(user.getUserName());
                    WorkOrderAssocDataAccess.update(conn, assoc);
                    if (currentAssocIdsSet.contains(new Integer(assoc.getWorkOrderAssocId()))) {
                        currentAssocIdsSet.remove(new Integer(assoc.getWorkOrderAssocId()));
                    }
                } else {
                    assoc.setAddBy(user.getUserName());
                    assoc.setModBy(user.getUserName());
                    assoc = WorkOrderAssocDataAccess.insert(conn, assoc);
                }
            }
        }

        if (!currentAssocIdsSet.isEmpty()) {
            IdVector ids = new IdVector();
            ids.addAll(currentAssocIdsSet);
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(WorkOrderAssocDataAccess.WORK_ORDER_ASSOC_ID, ids);
            WorkOrderAssocDataAccess.remove(conn, crit);
        }

        return workOrderAssoc;
    }

    private WorkOrderData updateWorkOrder(Connection conn, WorkOrderData workOrder, UserData user) throws Exception {
        return updateWorkOrder(conn, workOrder, user.getUserName());
    }

    public WarrantyDataVector getWorkOrderWarrantiesForAssets(IdVector assetIds) throws RemoteException {
        Connection conn = null;
        WarrantyDataVector result = new WarrantyDataVector();
        try {
            if (assetIds != null && !assetIds.isEmpty()) {
                conn = getConnection();
                result = getWorkOrderWarrantiesForAssets(conn, assetIds,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return result;
    }


    public WarrantyDataVector getWorkOrderWarrantiesForAssets(IdVector assetIds,String statusCd) throws RemoteException {
        Connection conn = null;
        WarrantyDataVector result = new WarrantyDataVector();
        try {
            if (assetIds != null && !assetIds.isEmpty()) {
                conn = getConnection();
                result = getWorkOrderWarrantiesForAssets(conn, assetIds,statusCd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return result;
    }

    private WarrantyDataVector getWorkOrderWarrantiesForAssets(Connection conn, IdVector assetIds,String statusCd) throws SQLException {

        DBCriteria crit;
        crit= new DBCriteria();
        crit.addOneOf(AssetWarrantyDataAccess.ASSET_ID,assetIds);

        IdVector warrantyIds = AssetWarrantyDataAccess.selectIdOnly(conn,AssetWarrantyDataAccess.WARRANTY_ID,crit);

        crit= new DBCriteria();
        crit.addOneOf(WarrantyDataAccess.WARRANTY_ID,warrantyIds);
        if(Utility.isSet(statusCd)){
            crit.addEqualTo(WarrantyDataAccess.STATUS_CD,statusCd);
        }

        return WarrantyDataAccess.select(conn, crit);

    }


    public WorkOrderItemData updateWorkOrderItemData(WorkOrderItemData workOrderItem, WorkOrderAssetDataVector assetAssoc, UserData user) throws RemoteException {
        Connection conn = null;
        try {
            if (workOrderItem != null) {
                conn = getConnection();
                String currentStatusCd = "";
                // get current status cd if workOrder exist
                if (workOrderItem.getWorkOrderItemId() > 0) {
                    WorkOrderItemData currentWorkOrderItem = getWorkOrderItem(conn, workOrderItem.getWorkOrderItemId());
                    currentStatusCd = currentWorkOrderItem.getStatusCd();
                }
                workOrderItem = updateWorkOrderItem(conn, workOrderItem, user);
                updateWorkOrderAsset(conn, workOrderItem.getWorkOrderItemId(), assetAssoc, user);
                if (!currentStatusCd.equals(workOrderItem.getStatusCd())) {
                    addStatusToWorkOrderItemHistory(conn, workOrderItem.getWorkOrderItemId(), workOrderItem.getStatusCd(), user);
                }
            }
            return workOrderItem;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WoiStatusHistData addStatusToWorkOrderItemHistory(Connection conn, int workOrderItemId, String statusCd, UserData user) throws SQLException {
        WoiStatusHistData statusHistory = WoiStatusHistData.createValue();
        statusHistory.setStatusCd(statusCd);
        statusHistory.setTypeCd(RefCodeNames.WOI_STATUS_HIST_TYPE_CD.UNKNOWN);
        statusHistory.setStatusDate(new Date());
        statusHistory.setWorkOrderItemId(workOrderItemId);
        return updateWorkOrderItemStatusHistory(conn, statusHistory, user);
    }

    private WoiStatusHistData updateWorkOrderItemStatusHistory(Connection conn, WoiStatusHistData statusHistory, UserData user) throws SQLException {
        if (statusHistory != null) {
            if (statusHistory.getWoiStatusHistId() > 0) {
                statusHistory.setModBy(user.getUserName());
                WoiStatusHistDataAccess.update(conn, statusHistory);
            } else {
                statusHistory.setAddBy(user.getUserName());
                statusHistory.setModBy(user.getUserName());
                statusHistory = WoiStatusHistDataAccess.insert(conn, statusHistory);
            }
        }
        return statusHistory;
    }

    private WorkOrderAssetDataVector updateWorkOrderAsset(Connection conn, int workOrderItemId, WorkOrderAssetDataVector assetAssoc, UserData user) throws SQLException {
        WorkOrderAssetDataVector currentAssoc = getAssetAssocByWoiId(conn, workOrderItemId);
        HashSet currentAssocIdsSet = toSet(currentAssoc);

        if (assetAssoc != null && !assetAssoc.isEmpty()) {
            Iterator it = assetAssoc.iterator();
            while (it.hasNext()) {
                WorkOrderAssetData assoc = (WorkOrderAssetData) it.next();
                assoc.setWorkOrderItemId(workOrderItemId);
                if (assoc.getWorkOrderAssetId() > 0) {
                    assoc.setModBy(user.getUserName());
                    WorkOrderAssetDataAccess.update(conn, assoc);
                    if (currentAssocIdsSet.contains(new Integer(assoc.getWorkOrderAssetId()))) {
                        currentAssocIdsSet.remove(new Integer(assoc.getWorkOrderAssetId()));
                    }
                } else {
                    assoc.setAddBy(user.getUserName());
                    assoc.setModBy(user.getUserName());
                    assoc = WorkOrderAssetDataAccess.insert(conn, assoc);
                }
            }
        }

        if (!currentAssocIdsSet.isEmpty()) {
            IdVector ids = new IdVector();
            ids.addAll(currentAssocIdsSet);
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(WorkOrderAssetDataAccess.WORK_ORDER_ASSET_ID, ids);
            WorkOrderAssetDataAccess.remove(conn, crit);
        }

        return assetAssoc;
    }

    public WorkOrderItemDetailViewVector updateWorkOrderItems(int workOrderId, WorkOrderItemDetailViewVector newItems, UserData user) throws RemoteException {
        Connection conn = null;
        WorkOrderItemDetailViewVector result = new WorkOrderItemDetailViewVector();
        logInfo("updateWorkOrderItems => BEGIN");
        try {

            conn = getConnection();

            WorkOrderItemDetailViewVector currentItems = getWorkOrderItems(conn, workOrderId);
            HashMap statusMap = getChangeStatusMap(currentItems, newItems);

            WorkOrderItemDetailViewVector update = (WorkOrderItemDetailViewVector) statusMap.get(RefCodeNames.CHANGE_STATUS.UPDATE);
            WorkOrderItemDetailViewVector insert = (WorkOrderItemDetailViewVector) statusMap.get(RefCodeNames.CHANGE_STATUS.INSERT);
            WorkOrderItemDetailViewVector delete = (WorkOrderItemDetailViewVector) statusMap.get(RefCodeNames.CHANGE_STATUS.DELETE);

            if (delete != null && !delete.isEmpty()) {
                Iterator it = delete.iterator();
                while (it.hasNext()) {
                    WorkOrderItemDetailView detItem = (WorkOrderItemDetailView) it.next();
                    boolean removeFl = removeWorkOrderItem(detItem.getWorkOrderItem().getWorkOrderItemId());
                    if (!removeFl) {
                        result.add(detItem);
                    }
                }
            }


            if (update != null && !update.isEmpty()) {
                Iterator it = update.iterator();
                while (it.hasNext()) {
                    WorkOrderItemDetailView updateItem = (WorkOrderItemDetailView) it.next();
                    updateItem = updateWorkOrderItem(conn, updateItem, user);
                }
                result.addAll(update);
            }

            if (insert != null && !insert.isEmpty()) {
                Iterator it = insert.iterator();
                while (it.hasNext()) {
                    WorkOrderItemDetailView insertItem = (WorkOrderItemDetailView) it.next();
                    insertItem.getWorkOrderItem().setWorkOrderItemId(0);
                    insertItem.getWorkOrderItem().setWorkOrderId(workOrderId);
                    insertItem = updateWorkOrderItem(conn, insertItem, user);
                }
                result.addAll(insert);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally{
            closeConnection(conn);
        }
        logInfo("updateWorkOrderItems => END");

        return result;
    }

    private WorkOrderItemDetailView updateWorkOrderItem(Connection conn, WorkOrderItemDetailView updateItem, UserData user) throws Exception {

        WorkOrderItemDetailView detailView = WorkOrderItemDetailView.createValue();

        WorkOrderItemData woi = updateWorkOrderItemData(updateItem.getWorkOrderItem(), getWorkOrderAssetCollection(updateItem.getAssetAssoc()), user);
        WorkOrderContentViewVector contents = updateWorkOrderItemContents(conn, woi.getWorkOrderId(),woi.getWorkOrderItemId(),updateItem.getContents(), user);
        WoiStatusHistDataVector histories = getStatusHistoriesByWoiId(conn, woi.getWorkOrderItemId());
        WorkOrderAssetViewVector assets = getAssetAssocViewByWoiId(conn, woi.getWorkOrderItemId());
        WarrantyData warranty = getWarranty(conn, woi.getWarrantyId());
        WorkOrderPropertyDataVector props = updateWorkOrderProperties(conn, woi.getWorkOrderId(), woi.getWorkOrderItemId(),updateItem.getProperties(),user);

        detailView.setWorkOrderItem(woi);
        detailView.setWarranty(warranty);
        detailView.setStatusHistories(histories);
        detailView.setAssetAssoc(assets);
        detailView.setContents(contents);
        detailView.setProperties(props);

        return  detailView;
    }

    private WorkOrderDetailView updateWorkOrderDetail(Connection conn, WorkOrderDetailView workOrderDetailView, ICleanwiseUser user, boolean workflowProcessing) throws Exception {
    	logInfo("updateWorkOrderDetail => begin");
    	logInfo("updateWorkOrderDetail => user: " + user.getUser().getUserName());
    	logInfo("updateWorkOrderDetail => wo: " + getMessageWorkOrderDetailInfo(workOrderDetailView));
    	WorkOrderData oldWorkOrder = null;
    	if(workOrderDetailView.getWorkOrder().getWorkOrderId() > 0)
    	{
    		oldWorkOrder = WorkOrderDataAccess.select(conn, workOrderDetailView.getWorkOrder().getWorkOrderId());
        	logInfo("updateWorkOrderDetail => old wo: " + getMessageWorkOrderDataInfo(oldWorkOrder));
    	
        	WorkOrderLifeCycle lifeCycle = new WorkOrderLifeCycle(oldWorkOrder.getStatusCd(), user);
	    	if(!lifeCycle.AllowableTransition(workOrderDetailView.getWorkOrder().getStatusCd()))
	    	{
	    		throw new RemoteException("Transition from %1s status to %2s is not allowable");
	    	}
    	}
    	
    	logInfo("updateWorkOrderDetail => update started");
    	WorkOrderDetailView newWorkOrderDetailView = updateWorkOrderDetail(conn, workOrderDetailView, user.getUser());
    	logInfo("updateWorkOrderDetail => update ended");
    	
    	WorkOrderLifeCycle lifeCycle = new WorkOrderLifeCycle(newWorkOrderDetailView.getWorkOrder().getStatusCd(), user);
    	logInfo("updateWorkOrderDetail => wo: lifeCycle.getWorkOrderStatus()=" + lifeCycle.getWorkOrderStatus());
    	logInfo("updateWorkOrderDetail => wo: lifeCycle.isNeedToSendEvent()=" + lifeCycle.isNeedToSendEvent());
    	if(lifeCycle.isNeedToSendEvent())
    	{
    		WorkOrderProcessManager processManager = new WorkOrderProcessManager(newWorkOrderDetailView, user, workflowProcessing);
    		processManager.sendEvent(conn, lifeCycle);
    	}
    	logInfo("updateWorkOrderDetail => end");
    	return newWorkOrderDetailView;
    }

    private static String getMessageWorkOrderDetailInfo(
    		WorkOrderDetailView workOrderDetailView) {
    	final WorkOrderData workOrder = workOrderDetailView.getWorkOrder();
		return workOrderDetailView == null ? "null" :
    		getMessageWorkOrderDataInfo(workOrder) + ", " +				
    		String.format("%1s", 
    				workOrderDetailView.getServiceProvider() == null ? "null" : workOrderDetailView.getServiceProvider().getPrimaryEmail().getEmailAddress());
    }

	private static String getMessageWorkOrderDataInfo(final WorkOrderData workOrder) {
		return String.format("%1s, %2s", 
				workOrder.getWorkOrderId(),
				workOrder.getStatusCd());
	}
    
    private WorkOrderDetailView updateWorkOrderDetail(Connection conn, WorkOrderDetailView workOrder, UserData user) throws Exception {


        WorkOrderData wo = updateWorkOrder(conn, workOrder.getWorkOrder(), user);
        WorkOrderAssocDataVector assoc = updateWorkOrderAssoc(conn, wo.getWorkOrderId(), workOrder.getWorkOrderAssocCollection(), user);
        WorkOrderContentViewVector contents = updateWorkOrderContents(conn, wo.getWorkOrderId(), workOrder.getContents(), user);
        WorkOrderStatusHistDataVector histories = getStatusHistoriesByWorkOrderId(conn, wo.getWorkOrderId());
        WorkOrderPropertyDataVector props = updateWorkOrderProperties(conn, wo.getWorkOrderId(), 0, workOrder.getProperties(), user);
        WorkOrderNoteDataVector notes = updateWorkOrderNotes(conn, wo.getWorkOrderId(), workOrder.getNotes(), user);
        WorkOrderItemDetailViewVector items = updateWorkOrderItems(wo.getWorkOrderId(), workOrder.getWorkOrderItems(), user);
    	ServiceProviderData serviceProvider = getWorkOrderServiceProvider(conn, wo.getWorkOrderId());
        WorkOrderDetailDataVector details = updateItemizedService(conn, wo.getWorkOrderId(), workOrder.getItemizedService(), user);
        
        WorkOrderDetailView detailView;

        detailView = new WorkOrderDetailView(wo,
                histories,
                contents,
                notes,
                items,
                assoc,
                props,
                serviceProvider,
                details);

        return detailView;
    }

    private WorkOrderDetailDataVector updateItemizedService(Connection conn,
			int workOrderId, WorkOrderDetailDataVector details, UserData user) throws Exception{
    	
      WorkOrderDetailDataVector oldDetails = this.getWorkOrderDetails(conn, workOrderId);
      for(Iterator iter = details.iterator(); iter.hasNext();) {
          WorkOrderDetailData wodD = (WorkOrderDetailData) iter.next();
          wodD.setWorkOrderId(workOrderId);
      }
      updateItemizedService(conn, details, oldDetails, user);
      return this.getWorkOrderDetails(conn, workOrderId);
	}
    
    private void updateItemizedService(Connection conn,
			WorkOrderDetailDataVector newITD, WorkOrderDetailDataVector oldITD,
			UserData userData) throws Exception {

		WorkOrderDetailDataVector insert = new WorkOrderDetailDataVector();
		WorkOrderDetailDataVector delete = new WorkOrderDetailDataVector();

		int sizeOld = oldITD.size();
		int sizeNew = newITD.size();
		WorkOrderDetailData currentLineOld;
		WorkOrderDetailData currentLineNew;

		// find lines to delete
		for (int i = 0; i < sizeOld; i++) {
			currentLineOld = (WorkOrderDetailData) oldITD.get(i);
			if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineOld
					.getStatusCd())) {
				for (int j = 0; j < sizeNew; j++) {
					currentLineNew = (WorkOrderDetailData) newITD.get(j);
					if (currentLineOld.getLineNum() == currentLineNew
							.getLineNum()) {
						if (RefCodeNames.STATUS_CD.INACTIVE
								.equals(currentLineNew.getStatusCd())) {

							currentLineOld.setStatusCd(RefCodeNames.STATUS_CD.INACTIVE);
							//currentLineOld.setModDate(new Date());
							//currentLineOld.setModBy(userData.getUserName());
                                                        delete.add(currentLineOld);
                                                        if (!userData.getUserName().equals(currentLineOld.getModBy())) {
                                                            WorkOrderDetailData tmpCloneLine = (WorkOrderDetailData)currentLineOld.clone();
                                                            tmpCloneLine.setWorkOrderDetailId(0);
                                                            tmpCloneLine.setAddBy(userData.getUserName());
                                                            tmpCloneLine.setModBy(userData.getUserName());
                                                            tmpCloneLine.setAddDate(new Date());
                                                            tmpCloneLine.setModDate(new Date());
                                                            insert.add(tmpCloneLine);
                                                        }	
						}
						break;
					}
				}
			}
		}

		// find lines to insert
		for (int i = sizeOld; i < sizeNew; i++) {
			currentLineNew = (WorkOrderDetailData) newITD.get(i);

			if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineNew
					.getStatusCd())
					&& (!RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE
							.equals(currentLineNew.getPaymentTypeCd())
							|| BigDecimal.valueOf(0.00).compareTo(
									currentLineNew.getPartPrice()) != 0
							|| BigDecimal.valueOf(0.00).compareTo(
									currentLineNew.getLabor()) != 0
							|| BigDecimal.valueOf(0.00).compareTo(
									currentLineNew.getTravel()) != 0
							|| currentLineNew.getQuantity() != 0
							|| !"".equals(currentLineNew.getShortDesc())
							|| !"".equals(currentLineNew.getPartNumber()) || !""
							.equals(currentLineNew.getComments()))) {
				insert.add(currentLineNew);
				// delete the line from the 'newITD'
			}
		}

		// find lines to update
		for (int i = 0; i < sizeOld; i++) {
			currentLineOld = (WorkOrderDetailData) oldITD.get(i);
			if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLineOld
					.getStatusCd())) {
				for (int j = 0; j < sizeNew; j++) {
					currentLineNew = (WorkOrderDetailData) newITD.get(j);
					if (currentLineOld.getLineNum() == currentLineNew
							.getLineNum()
							&& RefCodeNames.STATUS_CD.ACTIVE
									.equals(currentLineNew.getStatusCd())) {

						if ((Utility.compareTo(currentLineOld
								.getPaymentTypeCd(), currentLineNew
								.getPaymentTypeCd()) != 0)
								||

								(Utility.compareTo(currentLineOld
										.getPartNumber(), currentLineNew
										.getPartNumber()) != 0)
								||

								(Utility.compareBigDecimal(currentLineOld
										.getPartPrice(), currentLineNew
										.getPartPrice()) == 1)
								||

								(Utility.compareBigDecimal(currentLineOld
										.getLabor(), currentLineNew.getLabor()) == 1)
								||

								(Utility.compareBigDecimal(currentLineOld
										.getTravel(), currentLineNew
										.getTravel()) == 1)
								||

								currentLineOld.getQuantity() != currentLineNew
										.getQuantity()
								||

								(Utility.compareTo(currentLineOld
										.getShortDesc(), currentLineNew
										.getShortDesc()) != 0)
								||

								(Utility.compareTo(
										currentLineOld.getComments(),
										currentLineNew.getComments()) != 0)) {

							// update.add(currentLineNew);
							currentLineOld.setStatusCd(RefCodeNames.STATUS_CD.INACTIVE);
							//currentLineOld.setModDate(new Date());
							//currentLineOld.setModBy(userData.getUserName());
							delete.add(currentLineOld);

							currentLineNew.setWorkOrderDetailId(0);
							currentLineNew.setAddBy(userData.getUserName());
							currentLineNew.setModBy(userData.getUserName());
							currentLineNew.setAddDate(new Date());
							currentLineNew.setModDate(new Date());
							insert.add(currentLineNew);
						}
						break;
					}
				}
			}
		}
		if (!delete.isEmpty()) {
			this.updateItemizedServiceLines(conn, delete);
		}
		if (!insert.isEmpty()) {
			this.insertItemizedServiceLines(conn, insert);
		}
	}

	public WorkOrderDetailView updateWorkOrderDetail(WorkOrderDetailView workOrder, ICleanwiseUser user, boolean  workflowProcessing) throws RemoteException {
        Connection conn = null;

        try {

            conn = getConnection();
            return updateWorkOrderDetail(conn, workOrder, user, workflowProcessing);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    	
    }	
    public WorkOrderDetailView updateWorkOrderDetail(WorkOrderDetailView workOrder, UserData user) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();
            return updateWorkOrderDetail(conn, workOrder, user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

    private WorkOrderNoteDataVector updateWorkOrderNotes(Connection conn,
                                                         int workOrderId,
                                                         WorkOrderNoteDataVector notes,
                                                         UserData user) throws Exception {

        if (notes != null && !notes.isEmpty()) {
            Iterator it = notes.iterator();
            while (it.hasNext()) {
                WorkOrderNoteData note = (WorkOrderNoteData) it.next();
                note.setWorkOrderId(workOrderId);
                note = updateWorkOrderNoteData(conn, note, user);
            }
        }
        return notes;
    }

    private WorkOrderContentViewVector updateWorkOrderContents(Connection conn,
                                                               int workOrderId,
                                                               WorkOrderContentViewVector contents,
                                                               UserData user) throws Exception {
        if (contents != null && !contents.isEmpty()) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                WorkOrderContentView content = (WorkOrderContentView) it.next();
                content.getWorkOrderContentData().setWorkOrderId(workOrderId);
                content.getWorkOrderContentData().setWorkOrderItemId(0);
                content = updateWorkOrderContent(conn, content, user);
            }
        }
        return contents;
    }
    
    public WorkOrderPropertyData updateWorkOrderProperty(WorkOrderPropertyData property, UserData user) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return updateWorkOrderProperty(conn, property, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    private WorkOrderPropertyData updateWorkOrderProperty(Connection conn, WorkOrderPropertyData property, UserData user) throws Exception {
        if (property != null) {
            if (property.getWorkOrderPropertyId() > 0) {
                property.setModBy(user.getUserName());
                WorkOrderPropertyDataAccess.update(conn, property);
            } else {
                property.setAddBy(user.getUserName());
                property.setModBy(user.getUserName());
                WorkOrderPropertyDataAccess.insert(conn, property);
            }
        }
        return property;
    }

    private WorkOrderPropertyDataVector updateWorkOrderProperties(Connection conn,
                                                                  int workOrderId,
                                                                  int workOrderItemId,
                                                                  WorkOrderPropertyDataVector properties,
                                                                  UserData user) throws Exception {
        WorkOrderPropertyDataVector currentProperties = null;
        WorkOrderPropertyDataVector result = new WorkOrderPropertyDataVector();

        if ((workOrderId > 0) || (workOrderId > 0 && workOrderItemId > 0)) {

            if (workOrderId > 0 && workOrderItemId > 0) {
                currentProperties = getWorkOrderProperties(conn, workOrderId, workOrderItemId);
            } else if (workOrderId > 0) {
                currentProperties = getWorkOrderProperties(conn, workOrderId);
            }

            HashMap statusMap = getChangeStatusMap(currentProperties, properties);


            WorkOrderPropertyDataVector update = (WorkOrderPropertyDataVector) statusMap.get(RefCodeNames.CHANGE_STATUS.UPDATE);
            WorkOrderPropertyDataVector insert = (WorkOrderPropertyDataVector) statusMap.get(RefCodeNames.CHANGE_STATUS.INSERT);
            WorkOrderPropertyDataVector delete = (WorkOrderPropertyDataVector) statusMap.get(RefCodeNames.CHANGE_STATUS.DELETE);

            if (delete != null && !delete.isEmpty()) {
                Iterator it = delete.iterator();
                while (it.hasNext()) {
                    WorkOrderPropertyData delProp = (WorkOrderPropertyData) it.next();
                    boolean removeFl = removeWorkOrderProperty(conn, delProp.getWorkOrderPropertyId());
                    if (!removeFl) {
                        result.add(delProp);
                    }
                }
            }


            if (update != null && !update.isEmpty()) {
                Iterator it = update.iterator();
                while (it.hasNext()) {
                    WorkOrderPropertyData updateProp = (WorkOrderPropertyData) it.next();
                    updateProp = updateWorkOrderProperty(conn, updateProp, user);
                    result.add(updateProp);
                }

            }

            if (insert != null && !insert.isEmpty()) {
                Iterator it = insert.iterator();
                while (it.hasNext()) {
                    WorkOrderPropertyData insertProp = (WorkOrderPropertyData) it.next();
                    insertProp.setWorkOrderId(workOrderId);
                    if (workOrderItemId > 0) {
                        insertProp.setWorkOrderItemId(workOrderItemId);
                    }
                    insertProp = updateWorkOrderProperty(conn, insertProp, user);
                    result.add(insertProp);
                }
            }
        }
        return result;
    }

    private boolean removeWorkOrderProperty(Connection conn,int workOrderPropertyId) throws SQLException {
        return WorkOrderPropertyDataAccess.remove(conn,workOrderPropertyId)>0;
    }

    private static WorkOrderAssetDataVector getWorkOrderAssetCollection(WorkOrderAssetViewVector assetAssoc) {
        WorkOrderAssetDataVector result = new WorkOrderAssetDataVector();
        if (assetAssoc != null) {
            Iterator it = assetAssoc.iterator();
            while (it.hasNext()) {
                result.add(((WorkOrderAssetView) it.next()).getWorkOrderAssetData());
            }
        }
        return result;
    }

    private WorkOrderContentViewVector updateWorkOrderItemContents(Connection conn, int workOrderId, int workOrderItemId, WorkOrderContentViewVector contents, UserData user) throws Exception {
        if(contents!=null && !contents.isEmpty()) {
            Iterator it = contents.iterator();
            while(it.hasNext()){
                WorkOrderContentView content = (WorkOrderContentView) it.next();
                content.getWorkOrderContentData().setWorkOrderId(workOrderId);
                content.getWorkOrderContentData().setWorkOrderItemId(workOrderItemId);
                content=updateWorkOrderContent(conn,content,user);
            }
        }
        return contents;
    }

    private HashMap getChangeStatusMap(WorkOrderItemDetailViewVector currentItems, WorkOrderItemDetailViewVector newItems) {

        HashMap changesMap = new HashMap();

        WorkOrderItemDetailViewVector insert = new WorkOrderItemDetailViewVector();
        WorkOrderItemDetailViewVector update = new WorkOrderItemDetailViewVector();
        WorkOrderItemDetailViewVector delete = new WorkOrderItemDetailViewVector();

        changesMap.put(RefCodeNames.CHANGE_STATUS.DELETE, delete);
        changesMap.put(RefCodeNames.CHANGE_STATUS.INSERT, insert);
        changesMap.put(RefCodeNames.CHANGE_STATUS.UPDATE, update);


        if (newItems.size() == 0 && currentItems.size() > 0) {

            delete.addAll(currentItems);

        } else if (newItems.size() > 0 && currentItems.size() == 0) {

            insert.addAll(newItems);

        } else {

            Iterator it = currentItems.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView currItem = (WorkOrderItemDetailView) it.next();
                Iterator it1 = newItems.iterator();
                while (it1.hasNext()) {
                    WorkOrderItemDetailView newItem = (WorkOrderItemDetailView) it1.next();
                    if ((currItem.getWorkOrderItem().getWorkOrderItemId() > 0) &&
                            (currItem.getWorkOrderItem().getWorkOrderItemId() == newItem.getWorkOrderItem().getWorkOrderItemId())) {
                        update.add(newItem);
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }

            insert.addAll(newItems);
            delete.addAll(currentItems);
        }

        return changesMap;
    }

    private HashMap getChangeStatusMap(WorkOrderPropertyDataVector currentProprties, WorkOrderPropertyDataVector newProperties) {

        HashMap changesMap = new HashMap();

        WorkOrderPropertyDataVector insert = new WorkOrderPropertyDataVector();
        WorkOrderPropertyDataVector update = new WorkOrderPropertyDataVector();
        WorkOrderPropertyDataVector delete = new WorkOrderPropertyDataVector();

        changesMap.put(RefCodeNames.CHANGE_STATUS.DELETE, delete);
        changesMap.put(RefCodeNames.CHANGE_STATUS.INSERT, insert);
        changesMap.put(RefCodeNames.CHANGE_STATUS.UPDATE, update);


        if (newProperties.size() == 0 && currentProprties.size() > 0) {

            delete.addAll(currentProprties);

        } else if (newProperties.size() > 0 && currentProprties.size() == 0) {

            insert.addAll(newProperties);

        } else {

            Iterator it = currentProprties.iterator();
            while (it.hasNext()) {
                WorkOrderPropertyData currProp = (WorkOrderPropertyData) it.next();
                Iterator it1 = newProperties.iterator();
                while (it1.hasNext()) {
                    WorkOrderPropertyData newProp = (WorkOrderPropertyData) it1.next();
                    if ((newProp.getWorkOrderPropertyId() > 0) &&
                            (newProp.getWorkOrderPropertyId() == currProp.getWorkOrderPropertyId())) {
                        update.add(newProp);
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }

            insert.addAll(newProperties);
            delete.addAll(currentProprties);
        }

        return changesMap;
    }

    public int getClwWorkOrderItemSeq() throws RemoteException {
        Connection conn = null;
        int seq =0;
        try {
            conn=getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
            rs.next();
            seq = rs.getInt(1);
            stmt.close();
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally{
            closeConnection(conn);
        }
        return seq;
    }


    private HashSet toSet(WorkOrderAssetDataVector workOrderAssets) {
        HashSet set = new HashSet();
        if (workOrderAssets != null) {
            Iterator it = workOrderAssets.iterator();
            while (it.hasNext()) {

                WorkOrderAssetData wrkAsset = (WorkOrderAssetData) it.next();
                set.add(new Integer(wrkAsset.getWorkOrderAssetId()));
            }
        }
        return set;
    }

    private WorkOrderItemData updateWorkOrderItem(Connection conn, WorkOrderItemData workOrderItem, UserData user) throws SQLException {
        if (workOrderItem != null) {
            if (workOrderItem.getWorkOrderItemId() > 0) {
                workOrderItem.setModBy(user.getUserName());
                WorkOrderItemDataAccess.update(conn, workOrderItem);
            } else {
                workOrderItem.setAddBy(user.getUserName());
                workOrderItem.setModBy(user.getUserName());
                workOrderItem = WorkOrderItemDataAccess.insert(conn, workOrderItem);
            }
        }
        return workOrderItem;
    }

    public WorkOrderItemData updateWorkOrderItem(WorkOrderItemData workOrderItem, UserData user) throws RemoteException {
        Connection conn = null;
        try {
            if (workOrderItem != null) {
                conn = getConnection();
                workOrderItem=updateWorkOrderItem(conn, workOrderItem, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return workOrderItem;
    }

    public boolean updateWoiSequence(int workOrderItemId,int sequence, UserData user) throws RemoteException {
        Connection conn = null;
        try {
            if (workOrderItemId>0) {
                conn = getConnection();
                return updateWoiSequence(conn, workOrderItemId,sequence, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return false;
    }

    private boolean updateWoiSequence(Connection conn, int workOrderItemId, int sequence, UserData user) throws SQLException {


        String updateSql = "update CLW_WORK_ORDER_ITEM set SEQUENCE=? WHERE WORK_ORDER_ITEM_ID = " + workOrderItemId;
        logInfo("updateWoiSequence => updateSql:"+updateSql);
        PreparedStatement pstmt = conn.prepareStatement(updateSql);
        pstmt.setInt(1, sequence);
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    public WorkOrderNoteData updateWorkOrderNoteData(WorkOrderNoteData note, UserData user) throws RemoteException{
        Connection conn=null;
        try {
            if(note!=null){
                conn=getConnection();
                return updateWorkOrderNoteData(conn,note,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return note;
    }

    private WorkOrderNoteData updateWorkOrderNoteData(Connection conn, WorkOrderNoteData note, UserData user) throws Exception {
        return updateWorkOrderNoteData(conn, note, user.getUserName());
    }

    private WorkOrderNoteData updateWorkOrderNoteData(Connection conn, WorkOrderNoteData note, String userName) throws Exception {
        if (note != null) {
            if (note.getWorkOrderNoteId() > 0) {
                note.setModBy(userName);
                WorkOrderNoteDataAccess.update(conn, note);
            } else {
                note.setAddBy(userName);
                note.setModBy(userName);
                WorkOrderNoteDataAccess.insert(conn, note);
            }
        }
        return note;
    }

    public WorkOrderNoteData updateWorkOrderNoteData(WorkOrderNoteData note, String userName) throws RemoteException {
        Connection conn = null;
        try {
            if (note != null) {
                conn = getConnection();
                return updateWorkOrderNoteData(conn, note, userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return note;
    }


    public WorkOrderContentView updateWorkOrderContent(WorkOrderContentView workOrderContent, UserData user) throws RemoteException{
        Connection conn=null;
        try {
            if(workOrderContent!=null){
                conn=getConnection();
                return updateWorkOrderContent(conn,workOrderContent,user);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return workOrderContent;
    }

    private WorkOrderContentView updateWorkOrderContent(Connection conn, WorkOrderContentView workOrderContent,UserData user) throws Exception {

        if(workOrderContent!=null && workOrderContent.getContent()!=null &&workOrderContent.getWorkOrderContentData()!=null){

            Content contentEjb= APIAccess.getAPIAccess().getContentAPI();
            ContentDetailView content = contentEjb.updateContent(workOrderContent.getContent(),user);
            workOrderContent.getWorkOrderContentData().setContentId(content.getContentId());

            if(workOrderContent.getWorkOrderContentData().getWorkOrderContentId()<=0){
                workOrderContent.getWorkOrderContentData().setAddBy(user.getUserName());
                workOrderContent.getWorkOrderContentData().setModBy(user.getUserName());
                WorkOrderContentData wC = WorkOrderContentDataAccess.insert(conn, workOrderContent.getWorkOrderContentData());
                workOrderContent.setWorkOrderContentData(wC);
            }  else{
                workOrderContent.getWorkOrderContentData().setModBy(user.getUserName());
                WorkOrderContentDataAccess.update(conn, workOrderContent.getWorkOrderContentData());
            }
            workOrderContent= new WorkOrderContentView(content,workOrderContent.getWorkOrderContentData());
        }
        return  workOrderContent;
    }

    public boolean removeWorkOrderContent(int wocId) throws RemoteException{
        Connection conn=null;
        try {
            if(wocId>0){
                conn=getConnection();
                return removeWorkOrderContent(conn,wocId,0);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return false;
    }

    public boolean removeWorkOrderContent(int wocId,int contentId) throws RemoteException{
        Connection conn=null;
        try {
            if(wocId>0){
                conn=getConnection();
                return removeWorkOrderContent(conn,wocId,contentId);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return false;
    }

    private boolean removeWorkOrderContent(Connection conn, int wocId, int contentId) throws SQLException {
        try {
            logInfo("removeWorkOrderContent => wocId:"+wocId+" contentId:"+contentId);
            if (contentId <= 0) {
                WorkOrderContentData wocData = WorkOrderContentDataAccess.select(conn, wocId);
                wocId = wocData.getWorkOrderContentId();
                contentId = wocData.getContentId();
                logInfo("removeWorkOrderContent => now wocId:"+wocId+" contentId:"+contentId);
            }
            WorkOrderContentDataAccess.remove(conn, wocId);
            if (contentId > 0) {
                ContentDataAccess.remove(conn, contentId);
            }
            return true;
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean removeWorkOrderItem(int woiId) throws RemoteException{
        Connection conn=null;
        try {
            if(woiId>0){
                conn=getConnection();
                return removeWorkOrderItem(conn,woiId);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return false;
    }

    private boolean removeWorkOrderItem(Connection conn, int woiId) throws SQLException {

        DBCriteria dbCrit;

        dbCrit=new DBCriteria();
        dbCrit.addEqualTo(WorkOrderContentDataAccess.WORK_ORDER_ITEM_ID,woiId);
        IdVector contentsIds=WorkOrderContentDataAccess.selectIdOnly(conn,WorkOrderContentDataAccess.CONTENT_ID,dbCrit);
        WorkOrderContentDataAccess.remove(conn,dbCrit);
        dbCrit=new DBCriteria();
        dbCrit.addOneOf(ContentDataAccess.CONTENT_ID,contentsIds);
        ContentDataAccess.remove(conn,dbCrit);

        dbCrit=new DBCriteria();
        dbCrit.addEqualTo(WoiStatusHistDataAccess.WORK_ORDER_ITEM_ID,woiId);
        WoiStatusHistDataAccess.remove(conn,dbCrit);

        dbCrit=new DBCriteria();
        dbCrit.addEqualTo(WorkOrderPropertyDataAccess.WORK_ORDER_ITEM_ID,woiId);
        WorkOrderPropertyDataAccess.remove(conn,dbCrit);

        dbCrit=new DBCriteria();
        dbCrit.addEqualTo(WorkOrderAssetDataAccess.WORK_ORDER_ITEM_ID,woiId);
        WorkOrderAssetDataAccess.remove(conn,dbCrit);

        WorkOrderItemDataAccess.remove(conn,woiId);

        return true;

    }

    public WorkOrderItemDataVector getWorkOrderItems(WorkOrderItemSearchCriteria criteria) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCrit = convertToDBCriteria(criteria);
            return WorkOrderItemDataAccess.select(conn, dbCrit);

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    public WorkOrderDetailDataVector getWorkOrderDetails(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWorkOrderDetails(conn, workOrderId);

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

	private WorkOrderDetailDataVector getWorkOrderDetails(Connection conn,
			int workOrderId) throws SQLException {
		DBCriteria dbCrit = new DBCriteria();
		dbCrit.addEqualTo(WorkOrderDetailDataAccess.WORK_ORDER_ID, workOrderId);
		dbCrit.addOrderBy(WorkOrderDetailDataAccess.LINE_NUM + " ASC," + WorkOrderDetailDataAccess.STATUS_CD + " ASC," + WorkOrderDetailDataAccess.ADD_DATE, false);
		return WorkOrderDetailDataAccess.select(conn, dbCrit);
	}

    public WorkOrderItemDetailViewVector getWorkOrderItemDetailColection(WorkOrderItemSearchCriteria criteria) throws RemoteException {
        Connection conn = null;
        WorkOrderItemDetailViewVector woItemDetailColl = new WorkOrderItemDetailViewVector();
        try {
            conn = getConnection();
            DBCriteria dbCrit = convertToDBCriteria(criteria);
            WorkOrderItemDataVector woItems = WorkOrderItemDataAccess.select(conn, dbCrit);
            if (!woItems.isEmpty()) {
                Iterator it = woItems.iterator();
                while (it.hasNext()) {
                    WorkOrderItemData item = (WorkOrderItemData) it.next();
                    WorkOrderItemDetailView itemDetail = getWoiDetailView(conn, item);
                    woItemDetailColl.add(itemDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return woItemDetailColl;
    }

    private DBCriteria convertToDBCriteria(WorkOrderItemSearchCriteria criteria) {
        DBCriteria dbCriteria = new DBCriteria();
        if (criteria != null) {
            if (criteria.getAssetId() > 0) {
                dbCriteria.addJoinTableEqualTo(WorkOrderAssetDataAccess.CLW_WORK_ORDER_ASSET,
                        WorkOrderAssetDataAccess.ASSET_ID,
                        criteria.getAssetId());
                dbCriteria.addJoinCondition(WorkOrderItemDataAccess.CLW_WORK_ORDER_ITEM,
                        WorkOrderItemDataAccess.WORK_ORDER_ITEM_ID,
                        WorkOrderAssetDataAccess.CLW_WORK_ORDER_ASSET,
                        WorkOrderAssetDataAccess.WORK_ORDER_ITEM_ID);
            }

            if (criteria.getWorkOrderId() > 0) {
                dbCriteria.addEqualTo(WorkOrderItemDataAccess.WORK_ORDER_ID, criteria.getWorkOrderId());
            }
        }
        return dbCriteria;
    }

    /**
     * gets work order total cost map for asset
     *
     * @param woVector work order ids collection
     * @param assetId  assetId
     * @return cost map(key is workOrderId,object is BigDecimal actual total cost)
     * @throws RemoteException if an error
     */
    public HashMap getWoTotalCostMapForAsset(WorkOrderDataVector woDVector, int assetId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWoTotalCostMapForAsset(conn, woDVector, assetId);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * gets work order total cost map for asset
     *
     * @param conn     connection
     * @param woVector work order ids collection
     * @param assetId  assetId
     * @return cost map(key is workOrderId,object is BigDecimal actual total cost)
     * @throws SQLException if an error
     */
    private HashMap getWoTotalCostMapForAsset(Connection conn, WorkOrderDataVector woDVector, int assetId) throws RemoteException {
        HashMap woCostMap = new HashMap();
        
        logInfo("getWoTotalCostMapForAsset ");
        
        WorkOrderDetailDataVector woDetailDV;
        ArrayList partsCost;
        ArrayList laborCost;
        ArrayList travelCost;
        WorkOrderData workOrder;
        Integer key;
        Iterator it = woDVector.iterator();
        BigDecimal total;
        while (it.hasNext()) {
            workOrder = (WorkOrderData)it.next();
            key = Integer.valueOf(workOrder.getWorkOrderId());
            
            if (!RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(workOrder.getStatusCd())) {
            woDetailDV = getWorkOrderDetails(workOrder.getWorkOrderId());
            
            partsCost = WorkOrderUtil.getPartsCostSum(woDetailDV);
            laborCost =  WorkOrderUtil.getLaborCostSum(woDetailDV);
            travelCost = WorkOrderUtil.getTravelCostSum(woDetailDV);
                
            total = ((BigDecimal)partsCost.get(3)).
                    add((BigDecimal)laborCost.get(3)).
                    add((BigDecimal)travelCost.get(3));
            } else {
                total = new BigDecimal(0);
            }
            woCostMap.put(key, total);
        }

        return woCostMap;
    }

    public HashMap getCostCentersOrderSumMap(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getCostCentersOrderSumMap(conn, workOrderId);
        } catch (Exception e) {
        	
            throw processException(e); 
        } finally {
            closeConnection(conn);
        }
    }

    private HashMap getCostCentersOrderSumMap(Connection conn, int workOrderId) throws SQLException {

        HashMap costMap = new HashMap();

        // Get the cost centers and amounts spent in this order.
//        String query = " SELECT wo.cost_center_id,sum(woi.actual_labor) + sum(woi.actual_part)" +
//                " FROM clw_work_order_item woi,clw_work_order wo " +
//                " WHERE wo.cost_center_id > 0 " +
//                " AND wo.work_order_id = woi.work_order_id" +
//                " AND wo.work_order_id = " + workOrderId +
//                " GROUP BY wo.cost_center_id  ";

        String query =         
			"SELECT wo.cost_center_id, " +
				"SUM(detail.part_price * detail.quantity + detail.labor + detail.travel) AS amount " +
			"FROM clw_work_order_detail detail, clw_work_order wo " +
			"WHERE wo.work_order_id = detail.work_order_id AND " +
				"wo.cost_center_id > 0 AND " +
				"detail.status_cd = '" + RefCodeNames.STATUS_CD.ACTIVE +"' AND " +
				"wo.work_order_id = " + workOrderId +
				"GROUP BY wo.cost_center_id";
        
        logInfo("getCostCentersOrderSumMap SQL:" + query);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Integer key = new Integer(rs.getInt(1));
            BigDecimal cost = rs.getBigDecimal(2);

            costMap.put(key, cost);
        }

        rs.close();
        stmt.close();

        return costMap;
    }


    public SiteLedgerDataVector getSiteLedgerEntries(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getSiteLedgerEntries(conn, workOrderId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private SiteLedgerDataVector getSiteLedgerEntries(Connection conn, int workOrderId) throws SQLException {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(SiteLedgerDataAccess.ENTRY_TYPE_CD, RefCodeNames.LEDGER_ENTRY_TYPE_CD.WORK_ORDER);
        crit.addEqualTo(SiteLedgerDataAccess.WORK_ORDER_ID, workOrderId);
        return SiteLedgerDataAccess.select(conn, crit);
    }

    public void removeSiteLedgerEntries(int workOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            removeSiteLedgerEntries(conn, workOrderId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

     private void removeSiteLedgerEntries(Connection conn,int workOrderId) throws SQLException {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(SiteLedgerDataAccess.ENTRY_TYPE_CD, RefCodeNames.LEDGER_ENTRY_TYPE_CD.WORK_ORDER);
        crit.addEqualTo(SiteLedgerDataAccess.WORK_ORDER_ID, workOrderId);
        SiteLedgerDataAccess.remove(conn, crit);
    }

    public String updateStatus(int workOrderId, String status, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            WorkOrderData workOrder = getWorkOrder(conn, workOrderId);
            workOrder.setStatusCd(status);
            workOrder = updateWorkOrder(conn, workOrder, userName);
            return workOrder.getStatusCd();
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private WorkOrderData updateWorkOrder(Connection conn, WorkOrderData workOrder, String userName) throws Exception {
        String currentStatusCd = "";

        if (workOrder.getWorkOrderId() > 0) {

            WorkOrderData currentWorkOrder = getWorkOrder(conn, workOrder.getWorkOrderId());
            currentStatusCd = currentWorkOrder.getStatusCd();

            workOrder.setModBy(userName);
            WorkOrderDataAccess.update(conn, workOrder);

        } else {
            workOrder.setAddBy(userName);
            workOrder.setModBy(userName);
            workOrder = WorkOrderDataAccess.insert(conn, workOrder);
			int workOrderId = workOrder.getWorkOrderId();
			workOrder.setWorkOrderNum(String.valueOf(workOrderId));
            WorkOrderDataAccess.update(conn, workOrder);
        }

        if (!currentStatusCd.equals(workOrder.getStatusCd())) {
            addStatusToHistory(conn, workOrder.getWorkOrderId(), workOrder.getStatusCd(), userName);
            if (RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(workOrder.getStatusCd()) ||
                RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER.equals(workOrder.getStatusCd()) ||
                RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER.equals(workOrder.getStatusCd()))  {
                sendWorkOrderNotificationEmail(workOrder);
            }
        }
//        new MakeLedgerEntry().ledgerUpdate(workOrder, userName);

        return workOrder;
    }

    public boolean checkForDuplicateWorkOrderNum(int accountId, int siteNumber, String workOrderNum) throws RemoteException {
        boolean res = false;
        Connection conn = null;

        // check for WorkOrderNum with the same value for given Account
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountId);
            crit.addNotEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteNumber);
            String sitesReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);


            crit = new DBCriteria();
            crit.addEqualTo(WorkOrderDataAccess.WORK_ORDER_NUM, workOrderNum);
            crit.addOneOf(WorkOrderDataAccess.BUS_ENTITY_ID, sitesReq);

            IdVector dups = WorkOrderDataAccess.selectIdOnly(conn, crit);
            if (dups.size() > 0) {
                res = true;
            }
         } catch (Exception ex) {
               throw processException(ex);
         } finally {
               closeConnection(conn);
         }

        return res;
    }
    
    public boolean updateItemizedServiceLines(WorkOrderDetailDataVector updateDV) throws RemoteException {
        boolean res = true;
        Connection conn = null;
        
        try {
            conn = getConnection();
            
            updateItemizedServiceLines(conn, updateDV);
        
        } catch (Exception e) {
            res = false;
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        
        return res;
    }

	private void updateItemizedServiceLines(Connection conn,
			WorkOrderDetailDataVector updateDV) throws SQLException {
		Iterator itUpdate = updateDV.iterator();
		while(itUpdate.hasNext()) {
		    WorkOrderDetailDataAccess.update(conn, (WorkOrderDetailData)itUpdate.next());
		}
	}

    public boolean insertItemizedServiceLines(WorkOrderDetailDataVector insertDV) throws RemoteException {
        boolean res = true;
        Connection conn = null;
        
        try {
            conn = getConnection();
            
            insertItemizedServiceLines(conn, insertDV);
        
        } catch (Exception e) {
            res = false;
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        
        return res;
    }

	private void insertItemizedServiceLines(Connection conn,
			WorkOrderDetailDataVector insertDV) throws SQLException {
		Iterator itUpdate = insertDV.iterator();
		while(itUpdate.hasNext()) {
		    WorkOrderDetailDataAccess.insert(conn, (WorkOrderDetailData)itUpdate.next());
		}
	}

    public HashMap getWorkOrderPriorities(WorkOrderSimpleSearchCriteria criteria) throws RemoteException {

    	HashMap result = new HashMap();
        Connection conn = null;

        if (criteria == null) {
            throw new RemoteException("criteria is null");
        }

        try {
                conn = getConnection();
                result =  getWorkOrderPriorities(conn, criteria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return result;
    }

    private HashMap getWorkOrderPriorities(Connection conn, WorkOrderSimpleSearchCriteria criteria) throws Exception {

        DBCriteria dbCriteria = convertToDBCriteria(criteria);
        return WorkOrderDAO.selectWorkOrderPriorities(conn, dbCriteria);
    }
    
    private void sendWorkOrderNotificationEmail(WorkOrderData workOrder) throws Exception {
        String emailType = "";
        if (RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(workOrder.getStatusCd())) {
            emailType = UserInfoData.USER_GETS_EMAIL_WORK_ORDER_COMPLETED;
        } else if (RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER.equals(workOrder.getStatusCd())) {
            emailType = UserInfoData.USER_GETS_EMAIL_WORK_ORDER_ACCEPTED;
        } else if (RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER.equals(workOrder.getStatusCd()))  {
            emailType = UserInfoData.USER_GETS_EMAIL_WORK_ORDER_REJECTED;
        } else {
            return;
        }
        
        User userEjb = APIAccess.getAPIAccess().getUserAPI();
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
        PropertyService propertyEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
        
        int siteId = workOrder.getBusEntityId();
        AccountData account = accountEjb.getAccountForSite(siteId);
        int storeId = storeEjb.getStoreIdByAccount(account.getAccountId());
        StoreData store = storeEjb.getStore(storeId);
        int acctId = account.getAccountId();
        UserDataVector users = userEjb.getAllActiveUsers(siteId, User.ORDER_BY_ID);
        Iterator usersIt = users.iterator();
        
        
        IdVector accts = new IdVector();
        accts.add(new Integer(acctId));
        
        //get account permissions for users
        UserData uD;
        String rights = null;
        while (usersIt.hasNext()) {
            uD = (UserData)usersIt.next();
            try {
                UserAccountRightsViewVector uRightsView = userEjb.getUserAccountRights(uD.getUserId(), accts);
                if (uRightsView != null && uRightsView.size() > 0) {
                    // it is just one account in 'accts'
                    UserAccountRightsView uView = (UserAccountRightsView)uRightsView.get(0);
                    PropertyData userSettings = uView.getUserSettings();
                    rights = userSettings.getValue();
                } else {
                    rights = uD.getUserRoleCd();                    
                }
                if (rights != null) {
                    if (rights.indexOf(emailType) <= 0) {
                        usersIt.remove();
                    }
                } else {
                    usersIt.remove();
                }
            } catch (RemoteException e){
                log.info("User Account rights not set. UserId: " + uD.getUserId() + " AccountId: " + acctId);
            }
        }
        
        if (!users.isEmpty()) {
            String lineSeparator = System.getProperty("line.separator");
            WorkOrderDetailView woDView = getWorkOrderDetailView(workOrder.getWorkOrderId());
            
            String name = "Work Order " + workOrder.getWorkOrderNum();
            String fileName = name + ".pdf";

            StringBuffer automatedMessage = new StringBuffer();
            automatedMessage.append("************************************************************************************************")
                            .append(lineSeparator)
                            .append("This is an automated email.  Do not reply to this email.")
                            .append(lineSeparator)
                            .append(lineSeparator)
                            .append("This Work Order status has been changed to '")
                            .append(workOrder.getStatusCd())
                            .append("'.")
                            .append(lineSeparator);
            if (account != null && store != null) {
                automatedMessage.append("Attached please find, Work order: ")
                                .append(workOrder.getWorkOrderNum())
                                .append(" (from Store: ")
                                .append(store.getBusEntity().getShortDesc())
                                .append("/Account: ")
                                .append(account.getBusEntity().getShortDesc())
                                .append(")");
            } else {
                automatedMessage.append("Attached please find, Work order");
            }
            automatedMessage.append(" for your review.")
                            .append(lineSeparator)
                            .append("You will need Adobe Acrobat Reader to open the attached file.")
                            .append(lineSeparator)
                            .append("Please respond as appropriate.")
                            .append(lineSeparator)
                            .append(lineSeparator)
                            .append("Thank you.")
                            .append(lineSeparator)
                            .append("************************************************************************************************")
                            .append(lineSeparator);

            String emailFromAddress = "";
            
            try {
            	emailFromAddress = propertyEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS);
            } catch(DataNotFoundException e) {
            	log.info("Work order email not found for store " + storeId);
            }
            
            if(!(emailFromAddress.trim().length() > 0)){
            	if (store != null && store.getPrimaryEmail() != null) {
                    emailFromAddress = store.getPrimaryEmail().getEmailAddress();
            	}
            }
            
            UserInfoData userInfo;
            EventEmailDataView eventEmail;
            ArrayList attachArray;
            FileAttach pdf;
            String emailToAddress;
            usersIt = users.iterator();
            UserData userD;

            while (usersIt.hasNext()) {
                userD = (UserData)usersIt.next();
                
                Locale localeCd = Locale.US;
                String userLocale = userD.getPrefLocaleCd();
                String [] temp = null;
                if (Utility.isSet(userLocale)) {
                    temp = userLocale.split("_");
                    if (temp != null && temp.length == 2) {
                        try {
                            localeCd = new Locale(temp[0], temp[1]);
                        } catch (NullPointerException e) {}
                    }
                }
                
                byte[] outbytes = null;
                ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
                WorkOrderUtil woUtil = new WorkOrderUtil();
                if (woUtil.writeWoPdfToStream(woDView, pdfout, localeCd)) {
                    outbytes = pdfout.toByteArray();
                }
                
                userInfo = userEjb.getUserContactForNotification(userD.getUserId());
                
                emailToAddress = null;
                if (userInfo.getEmailData() != null) {
                    emailToAddress = userInfo.getEmailData().getEmailAddress();
                }

                if (Utility.isSet(emailToAddress)) {
                    eventEmail = new EventEmailDataView();

                    eventEmail.setFromAddress(emailFromAddress);
                    eventEmail.setToAddress(emailToAddress);
                    eventEmail.setSubject(name);
                    eventEmail.setText(automatedMessage.toString());
                    eventEmail.setEmailStatusCd(Event.STATUS_READY);

                    attachArray = new ArrayList();

                    if (outbytes != null ) {
                        pdf = new FileAttach(fileName, outbytes, "application/pdf", outbytes.length);
                        attachArray.add(pdf);
                    }
                    eventEmail.setAttachments((FileAttach[]) attachArray.toArray(new FileAttach[attachArray.size()]));
                    new ApplicationsEmailTool().createEvent(eventEmail);
                } else {
                    log.info("sendWorkOrderNotificationEmail() => WARNING: No EmailTo Address for userID: " + userD.getUserId() +
                            " (" + userD.getUserName() + ")");
                }
            }
        }
    }
    
}
