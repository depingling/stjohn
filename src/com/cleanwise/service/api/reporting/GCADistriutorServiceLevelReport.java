/*
 * GCADistriutorServiceLevelReport.java
 *
 * Created on June 17, 2005, 3:03 PM
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderItemMetaDataAccess;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.InvoiceCustData;
import com.cleanwise.service.api.value.InvoiceCustDetailData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemMetaData;
import com.cleanwise.service.api.value.OrderItemMetaDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *Implements a report that takes the logic that is defined in the GCA distributoion agreement defined with JanPack on Feb 17 2005.
 *
 *Report will look at both the data that is updated in the distributor portal (shipped and scheduled are deemed equivilent) and the data that
 *is defined contained in the distributor invoice tables.  Th repor is deefined to be inteligent enough so that the user does not need to
 *know the start date of a given distributor on the invoiceing side of things.  The report will look for the first invoice from a distributor
 *and use that as the start date for invoiceing data.  Anything entered into he distributor poral after that date will be ignored by this report.
 *
 *Takes a distributor id as the paramete and an optional invoiceing start date (BEG_DATE) as a parameter. This date would overide the above
 *logic for the invoices sytem start date. This may be useful if there is an invoice entered errounioulsy, or if there was a partial startup
 *of invoiceing.
 *@author bstevens
 */
public class GCADistriutorServiceLevelReport implements GenericReportMulti{
    private static final int MONTHLY_AVG_BASELINE_NUM = 3;
    private Connection mCon;
    private BusEntityData mDisBusEnt;
    private Date mBegDate;
    private Date mEndDate;
    private Date mInvoiceStartDate;
    private HashMap historyMap = new HashMap();
    private SimpleDateFormat cachingSdf = new SimpleDateFormat("MM/dd/yyyy"); //format is never displayed to user, just used as key in hash
    private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private boolean splOnly;
    private HashMap summaryDataMap = new HashMap();
    private int allowedDays;
    private HashMap generalCache = new HashMap();
    
    private int totalNumberOfSPLItems=0;
    private int totalNumberOfSPLItemsShippedOnTime=0;
    private int totalNumberQtyOfSPLItems=0;
    private int totalNumberQtyOfSPLItemsShippedOnTime=0;
    private int totalNumberOfSPLOrders=0;
    private int totalNumberOfSPLOrdersShippedOnTime=0;
    
    private int distId;
            
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
    throws Exception {
        mCon = pCons.getDefaultConnection();
        String errorMess = "No error";
        //format the dates from one string type to anouther
        String invBegDateS = (String)ReportingUtils.getParam(pParams,"INVOICE_BEG_DATE");
        String begDateS = (String)ReportingUtils.getParam(pParams,"BEG_DATE");
        String endDateS = (String)ReportingUtils.getParam(pParams,"END_DATE");
        String distIdS = (String)ReportingUtils.getParam(pParams,"DISTRIBUTOR");
        String splOnlyS = (String)ReportingUtils.getParam(pParams,"SPL Only");
        String allowedDaysS = (String)ReportingUtils.getParam(pParams,"Allowed Days");

        String userIdS = (String)ReportingUtils.getParam(pParams,"CUSTOMER");
        
        splOnly = Utility.isTrue(splOnlyS);
        
        if(Utility.isSet(begDateS)){
            mBegDate = ReportingUtils.parseDate(begDateS);
        }else{
            String mess = "^clw^Begin Date is required^clw^";
            throw new Exception(mess);
        }
        
        if(Utility.isSet(endDateS)){
            mEndDate = ReportingUtils.parseDate(endDateS);
        }else{
            String mess = "^clw^End Date is required^clw^";
            throw new Exception(mess);
        }
        
        int userId=0;
        if(Utility.isSet(userIdS)){
            try{
                userId = Integer.parseInt(userIdS.trim());
            }catch(NumberFormatException e){
                String mess = "^clw^Error code SPL-001^clw^Could not pasrse user id: "+userIdS;
                throw new Exception(mess);
            }
        }

        if(Utility.isSet(distIdS)){
            try{
                distId = Integer.parseInt(distIdS.trim());
            }catch(NumberFormatException e){
                String mess = "^clw^Distributor provided must be numeric.  Use the search button^clw^";
                throw new Exception(mess);
            }
        }else{
            String mess = "^clw^No distributor provided^clw^";
            throw new Exception(mess);
        }

        if(Utility.isSet(allowedDaysS)){
            try{
                allowedDays = Integer.parseInt(allowedDaysS.trim());
            }catch(NumberFormatException e){
                String mess = "^clw^Allowed Days provided must be numeric.^clw^";
                throw new Exception(mess);
            }
        }else{
            String mess = "^clw^No Allowed Days provided^clw^";
            throw new Exception(mess);
        }
        
        
        if(!Utility.isSet(invBegDateS)){
            Statement stmt = null;
            try{
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(InvoiceDistDataAccess.BUS_ENTITY_ID,distId);
                crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD,RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
                stmt = mCon.createStatement();
                ResultSet rs = stmt.executeQuery(InvoiceDistDataAccess.getSqlSelectIdOnly("MIN("+InvoiceDistDataAccess.INVOICE_DATE+")", crit));
                if(!rs.next()){
                    //there are no invoices yet, just use the order item action table
                    mInvoiceStartDate = null;
                }else{
                    mInvoiceStartDate = rs.getDate(1);
                }
            }finally{
                if(stmt != null){
                    stmt.close();
                }
            }
        }else{
            mInvoiceStartDate = df.parse(invBegDateS);
        }
        
        if(userId!= 0 && !ReportingUtils.isUserAuthorizedForDistributor(mCon, distId, userId)){
            String mess = "^clw^Distributor provided does not exist.  Use the search button^clw^ NOT AUTHORIZED distId: "+distId+" userId: "+userId;
            throw new Exception(mess);
        }
        
        
       
        
        try{
            //fetch the distributor bus entity.  This provides a convinient way to go back and forth from the erp number or the
            //distibutor id without having to add it into the sql join.
            mDisBusEnt = BusEntityDataAccess.select(mCon,distId);
        }catch(DataNotFoundException e){
            String mess = "^clw^Distributor provided does not exist.  Use the search button^clw^";
            throw new Exception(mess);
        }

        ArrayList results = new ArrayList();
        //first get all of the order items that we ae interested in
        DBCriteria crit = getValidOrderOrderItemDBC();
        String oi = OrderItemDataAccess.CLW_ORDER_ITEM;
        crit.addJoinTableGreaterOrEqual(oi,OrderItemDataAccess.ERP_PO_DATE,mBegDate);
        crit.addJoinTableLessOrEqual(oi,OrderItemDataAccess.ERP_PO_DATE,mEndDate);
        crit.addJoinTableEqualTo(oi,OrderItemDataAccess.DIST_ERP_NUM,mDisBusEnt.getErpNum());
        OrderItemDataVector oItems = new OrderItemDataVector();
        JoinDataAccess.selectTableInto(new OrderItemDataAccess(), oItems, mCon, crit, 0);
        //OrderItemDataVector oItems = OrderItemDataAccess.select(mCon,crit);
        Iterator it = oItems.iterator();
        while(it.hasNext()){
            DetailRecord aRec = new DetailRecord((OrderItemData) it.next());
            populateOrderItemAction(aRec);
            populateInvoiceData(aRec);
            populateTotalQtyHistory(aRec);
            populateMiscData(aRec);
            setupSummaryRecord(aRec);
            runMetricCalcs(aRec);
            results.add(aRec);
        }
        
        LinkedList summaryDataList = new LinkedList();
        summaryDataList.addAll(summaryDataMap.values());
        it = summaryDataList.iterator();
        while(it.hasNext()){
            runMetricCalcs((SummaryRecord) it.next());
        }
        

        //get the data and populate the parameter results
        ArrayList params = new ArrayList();
        ArrayList paramRow = new ArrayList();
        params.add(paramRow);
        paramRow.add(mDisBusEnt.getShortDesc());
        paramRow.add(mBegDate);
        paramRow.add(mEndDate);
        paramRow.add(xlateBool(splOnly));
        paramRow.add(new Integer(allowedDays));
        paramRow.add(new Integer(totalNumberOfSPLItems));
        paramRow.add(new Integer(totalNumberQtyOfSPLItems));
        paramRow.add(new Integer(totalNumberOfSPLItemsShippedOnTime));
        paramRow.add(new Integer(totalNumberQtyOfSPLItemsShippedOnTime));
        paramRow.add(new Integer(totalNumberOfSPLOrders));
        paramRow.add(new Integer(totalNumberOfSPLOrdersShippedOnTime));
        
        
        
        //now that we have the results go ahead and construct all of the necessay reporting framework
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        resultV.add(ReportingUtils.createReport(results, getReportItemsHeader(), "Items", DETAIL_COMP));
        resultV.add(ReportingUtils.createReport(summaryDataList, getReportOrdersHeader(), "Orders", SUMMARY_COMP));
        resultV.add(ReportingUtils.createReport(params, getReportSummaryHeader(), "Summary", null));
        
        return resultV;
    }
    
    //generate some other total data
    private void runMetricCalcs(DetailRecord aRec){
        if(aRec.isSPLItem){
            totalNumberOfSPLItems++;
            totalNumberQtyOfSPLItems += aRec.mOrderItem.getTotalQuantityOrdered();
            if(aRec.isShippedInTime()){
            	//XXX possibly othewr criteria that needs to be evaluated here
            	//like if the user selected a freight option of some kind it should
            	//be ignored?
                totalNumberOfSPLItemsShippedOnTime++;
                totalNumberQtyOfSPLItemsShippedOnTime += aRec.getTotalQtyShipped();
            }
        }
    }
    private void runMetricCalcs(SummaryRecord sumRec){
        if(sumRec.isSPL){
            totalNumberOfSPLOrders++;
            //if((sumRec.isShippedComplete && sumRec.totalShipments.size() == 1)){
            //switched becasue summary record already calculates the total shipment size and takes into account
            //ignore frieght and prvious history stuff.
            if(sumRec.isShippedComplete){
                totalNumberOfSPLOrdersShippedOnTime++;
            }
        }
    }
    
    /**
     *Returns a DBCriteria object suitable for quierying the order and order item tables are joined and the status codes are filtered out.
     */
    private DBCriteria getValidOrderOrderItemDBC(){
        DBCriteria crit = new DBCriteria();
        String o = OrderDataAccess.CLW_ORDER;
        String oi = OrderItemDataAccess.CLW_ORDER_ITEM;
        String oim = OrderItemMetaDataAccess.CLW_ORDER_ITEM_META;
        crit.addJoinCondition(o,OrderDataAccess.ORDER_ID,oi,OrderItemDataAccess.ORDER_ID);
        crit.addJoinTableOneOf(o,OrderDataAccess.ORDER_STATUS_CD,ReportingUtils.getCommitedOrderStatusCodes());
        ArrayList notOrderItemStatusCds = new ArrayList();
        notOrderItemStatusCds.add(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
        notOrderItemStatusCds.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT);
        notOrderItemStatusCds.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW);
        notOrderItemStatusCds.add(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
        crit.addJoinTableNotOneOf(oi,OrderItemDataAccess.ORDER_ITEM_STATUS_CD,notOrderItemStatusCds);
        
        if(splOnly){
            //this may have to be an outer join, and a flag on the report, but this version restricts report to only those items on the SPL
            crit.addJoinCondition(oim, OrderItemMetaDataAccess.ORDER_ITEM_ID,oi,OrderItemDataAccess.ORDER_ITEM_ID);
            crit.addJoinTableEqualTo(oim,OrderItemMetaDataAccess.NAME,OrderItemJoinData._propertyNames[OrderItemJoinData.STANDARD_PRODUCT_LIST]);
            crit.addJoinTableEqualTo(oim,OrderItemMetaDataAccess.CLW_VALUE,Boolean.TRUE.toString());
        }
        return crit;
    }
    
    
    /**
     *populates some other misc data
     */
    private void populateMiscData(DetailRecord aRec) throws SQLException{
    	DBCriteria crit;
        if(splOnly){
            aRec.isSPLItem=true;
        }else{
            OrderItemData oid = aRec.mOrderItem;
            crit = new DBCriteria();
            ArrayList validMetaStatus = new ArrayList();
            validMetaStatus.add(OrderItemJoinData._propertyNames[OrderItemJoinData.STANDARD_PRODUCT_LIST]);
            crit.addOneOf(OrderItemMetaDataAccess.NAME,validMetaStatus);
            crit.addEqualTo(OrderItemMetaDataAccess.ORDER_ITEM_ID,oid.getOrderItemId());
            aRec.isSPLItem=false;

            OrderItemMetaDataVector metas = OrderItemMetaDataAccess.select(mCon,crit);
            if(metas != null){
                Iterator it = metas.iterator();
                while(it.hasNext()){
                    OrderItemMetaData aMeta = (OrderItemMetaData) it.next();
                    if(OrderItemJoinData._propertyNames[OrderItemJoinData.STANDARD_PRODUCT_LIST].equals(aMeta.getName())){
                        aRec.isSPLItem = Utility.isTrue(aMeta.getValue());
                    }
                }
            }
        }
        
        //get the requested shipping date
        String key = "orderMeta:"+aRec.mOrderItem.getOrderId();
        OrderMetaDataVector omdv;
        if(!generalCache.containsKey(key)){
	        crit = new DBCriteria();
	        crit.addEqualTo(OrderMetaDataAccess.ORDER_ID,aRec.mOrderItem.getOrderId());
	        ArrayList types = new ArrayList();
	        types.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE);
	        crit.addOneOf(OrderMetaDataAccess.NAME,types);
            omdv = OrderMetaDataAccess.select(mCon,crit);
	        generalCache.put(key,omdv);
        }else{
            omdv = (OrderMetaDataVector) generalCache.get(key);
        }
        if(omdv != null){
	        Iterator it = omdv.iterator();
	        while(it.hasNext()){
	        	OrderMetaData opd = (OrderMetaData) it.next();
	        	if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE.equals(opd.getName())){
	        		try{
	        			aRec.requestedShipDate = df.parse(opd.getValue());
	        		}catch(Exception e){}
	        	}
	        }
        }

        
        
        //get user selected freight
        key = "orderFreight:"+aRec.mOrderItem.getOrderId()+"::"+distId;
        OrderFreightDataVector ofdv;
        if(!generalCache.containsKey(key)){
        	crit = new DBCriteria();
        	crit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID,distId);
        	crit.addEqualTo(OrderFreightDataAccess.ORDER_ID,aRec.mOrderItem.getOrderId());
        	ofdv = OrderFreightDataAccess.select(mCon,crit);
        	generalCache.put(key,ofdv);
        }else{
        	ofdv = (OrderFreightDataVector) generalCache.get(key);
        }
        if(ofdv != null){
        	//the data model allows multiples of these, for the time being we are
        	//just going to be looking for the couple that we know about
        	Iterator it = ofdv.iterator();
        	while(it.hasNext()){
        		OrderFreightData ofd = (OrderFreightData) it.next();
        		String frtStr = ofd.getShortDesc().toLowerCase().replaceAll(" ","");
        		if(ofd.getShortDesc() != null && (frtStr.indexOf("willcall") >= 0 || frtStr.indexOf("combinewith") >= 0)){
        			aRec.ignoreBasedOffUserSeletedFreight = true;
        		}
        	}
        }
    }
    
    
    /**
     *Retrieves data relating to historic adjustments, that is data such as the total number of orders in the past 3 months.
     */
    private void populateTotalQtyHistory(DetailRecord aRec) throws SQLException{
        //get the total qty ordered of this item in the past n months
        //cache this info as it is item based not order item.
        
        String key = aRec.mOrderItem.getDistItemSkuNum() + cachingSdf.format(aRec.mOrderItem.getErpPoDate());
        if(historyMap.containsKey(key)){
            Integer qty = (Integer) historyMap.get(key);
            if(qty == null){
                aRec.mOrderHistQty = new Integer(0);
            }else{
                aRec.mOrderHistQty = qty;
            }
            return;
        }
        
        DBCriteria crit = getValidOrderOrderItemDBC();
        String oi = OrderItemDataAccess.CLW_ORDER_ITEM;
		crit.addJoinTableEqualTo(oi,OrderItemDataAccess.DIST_ITEM_SKU_NUM,aRec.mOrderItem.getDistItemSkuNum());
        crit.addJoinTableEqualTo(oi,OrderItemDataAccess.DIST_ERP_NUM,mDisBusEnt.getErpNum());
        Calendar cal = Calendar.getInstance();
        cal.setTime(aRec.mOrderItem.getErpPoDate());
        cal.add(Calendar.MONTH, -1 * MONTHLY_AVG_BASELINE_NUM);
        crit.addJoinTableGreaterOrEqual(oi,OrderItemDataAccess.ERP_PO_DATE,cal.getTime());
        crit.addJoinTableLessThan(oi,OrderItemDataAccess.ERP_PO_DATE,aRec.mOrderItem.getErpPoDate());
        Statement stmt = null;
        Integer qty=new Integer(0);
        try{
            stmt = mCon.createStatement();
            String sql = "Select SUM("+OrderItemDataAccess.TOTAL_QUANTITY_ORDERED+") "+JoinDataAccess.getSqlFromClause(crit);
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next()){
                qty = new Integer(rs.getInt(1));
            }
        }finally{
            if(stmt != null){
                stmt.close();
            }
            stmt.close();
        }
        historyMap.put(key,qty);
        aRec.mOrderHistQty = qty;
    }
    
    
    
    /**
     *Retrieve the data from the order item action table.  This would be added by either a distributor user or an 855 or 856.
     *Although this is not as reliabvl as he invoice it may be the only thing we have to go on depending on the implementaion stage of
     *the distributor.
     */
    private void populateOrderItemAction(DetailRecord aRec) throws SQLException{
        //if po date is after invoice starts don;t populate order item actions
        if(mInvoiceStartDate != null && aRec.mOrderItem.getErpPoDate().compareTo(mInvoiceStartDate) > 0){
            aRec.mActions = new OrderItemActionDataVector();
            return;
        }
        
        OrderItemData oid = aRec.mOrderItem;
        DBCriteria crit = new DBCriteria();
        ArrayList validActionStatus = new ArrayList();
        //validActionStatus.add(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SCHEDULED);
        validActionStatus.add(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED);
        validActionStatus.add(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SHIPPED);
        crit.addOneOf(OrderItemActionDataAccess.ACTION_CD,validActionStatus);
        crit.addEqualTo(OrderItemActionDataAccess.ORDER_ITEM_ID,oid.getOrderItemId());
        //if(mInvoiceStartDate != null){
        //            crit.addLessThan(OrderItemActionDataAccess.ACTION_DATE,mInvoiceStartDate);
        //}
        crit.addOrderBy(OrderItemActionDataAccess.ACTION_DATE);
        
        
        OrderItemActionDataVector actions = OrderItemActionDataAccess.select(mCon,crit);
        
        int qty = aRec.mOrderItem.getTotalQuantityOrdered();
        int shipQty = 0;
        int schedQty = 0;
        int optionBQty = 0;
        
        OrderItemActionDataVector actionShipped = new OrderItemActionDataVector();
        OrderItemActionDataVector actionScheduled = new OrderItemActionDataVector();
        OrderItemActionDataVector actionOptionB = new OrderItemActionDataVector();
        
        if(actions != null){
            Iterator it = actions.iterator();
            while(it.hasNext()){
                OrderItemActionData oia = (OrderItemActionData) it.next();
                if(oia.getActionDate() == null){
                    oia.setActionDate(oia.getAddDate());
                }
                if(schedQty + shipQty + oia.getQuantity() <= qty){
                	actionOptionB.add(oia);
                	optionBQty += oia.getQuantity();
                }
                
                if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SCHEDULED.equals(oia.getActionCd())){
                	schedQty += oia.getQuantity();
                	actionScheduled.add(oia);
                }else if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(oia.getActionCd()) || RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SHIPPED.equals(oia.getActionCd())){
                	shipQty += oia.getQuantity();
                	actionShipped.add(oia);
                }
                
            }
        }
        
        if(shipQty + schedQty <= qty){
        	//the total number of order item actions are equal to or less than the
        	//total quantity ordered so include all of them when evalutaing the results.
        	aRec.mActions = actions;
        }else if(shipQty == qty){
        	//just use shipped actions
        	aRec.mActions = actionShipped; 
        }else if(schedQty == qty){
        	//just use scheduled actions
        	aRec.mActions = actionScheduled;
        }else if(optionBQty == qty){
        	//just use the best guess option
        	aRec.mActions = actionOptionB;
        }else if(shipQty > schedQty){
        	//just use shipped actions
        	aRec.mActions = actionShipped;
        }else{
        	//just use scheduled actions
        	aRec.mActions = actionScheduled;
        }
        
    }
    
    /**
     *Retrieves the data from the invoice tables
     */
    private void populateInvoiceData(DetailRecord aRec) throws SQLException{
        
        //if the date is not set or this po occured beforthe first invoice then there are no distriutor invoices yet.
        if(mInvoiceStartDate == null || aRec.mOrderItem.getErpPoDate().compareTo(mInvoiceStartDate) <= 0){
            aRec.mInvoicesAndInvoiceDetail = new InvoiceDistDetailDataVector();
            return;
        }
        OrderItemData oid = aRec.mOrderItem;
        DBCriteria crit = new DBCriteria();
        String id = InvoiceDistDataAccess.CLW_INVOICE_DIST;
        String idd = InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL;
        //crit.addJoinTableGreaterOrEqual(id,InvoiceDistDataAccess.INVOICE_DATE,mInvoiceStartDate);
        crit.addJoinTableEqualTo(idd,InvoiceDistDetailDataAccess.ORDER_ITEM_ID,oid.getOrderItemId());
        crit.addJoinTableNotEqualTo(id,InvoiceDistDataAccess.INVOICE_STATUS_CD,RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
        crit.addJoinCondition(id,InvoiceDistDataAccess.INVOICE_DIST_ID,idd,InvoiceDistDetailDataAccess.INVOICE_DIST_ID);
        crit.addJoinTableOrderBy(id,InvoiceDistDataAccess.INVOICE_DATE);
        
        crit.addDataAccessForJoin(new InvoiceDistDataAccess());
        crit.addDataAccessForJoin(new InvoiceDistDetailDataAccess());
        aRec.mInvoicesAndInvoiceDetail = JoinDataAccess.select(mCon, crit);
    }
    
    /**
     *Sets up the summary record, creates it for new purchase orders, or adds to an existing mapping
     */
    private void setupSummaryRecord(DetailRecord aRec) throws SQLException{
        String key = aRec.mOrderItem.getPurchaseOrderId() +"-"+ aRec.isSPLItem;
        SummaryRecord aSum = (SummaryRecord) summaryDataMap.get(key);
        if(aSum == null){
            aSum = new SummaryRecord();
            summaryDataMap.put(key,aSum);
        }
        aSum.addDetailRecord(aRec);
    }
    
    /**
     *Generates the parameter info header for this reports
     */
    private GenericReportColumnViewVector getReportSummaryHeader(){
        ArrayList header = new ArrayList();
        header.add(new SimpleHeaderDef("Vendor","String"));
        header.add(new SimpleHeaderDef("Begin Date","Date"));
        header.add(new SimpleHeaderDef("End Date","Date"));
        header.add(new SimpleHeaderDef("Spl Only","String"));
        header.add(new SimpleHeaderDef("Allowed Days","Integer"));
        header.add(new SimpleHeaderDef("Total SPL Item Lines","Integer"));
        header.add(new SimpleHeaderDef("Total SPL Item Qty Ordered","Integer"));
        header.add(new SimpleHeaderDef("Total SPL Item Lines Shipped On Time","Integer"));
        header.add(new SimpleHeaderDef("Total SPL Item Qty Shipped On Time","Integer"));
        header.add(new SimpleHeaderDef("Total Complete SPL Orders","Integer"));
        header.add(new SimpleHeaderDef("Total SPL Orders Shipped Complete","Integer"));
        return ReportingUtils.createGenericReportColumnView(header);
    }
    
    /**
     *Generates the summary header for this reports
     */
    private GenericReportColumnViewVector getReportOrdersHeader(){
        ArrayList header = new ArrayList();
        header.add(new SimpleHeaderDef("Order Number","String"));
        header.add(new SimpleHeaderDef("Order Date","Date"));
        header.add(new SimpleHeaderDef("Order Amount","BigDecimal"));
        header.add(new SimpleHeaderDef("Order Qty","Integer"));
        header.add(new SimpleHeaderDef("Total Qty Shipped","Integer"));
        header.add(new SimpleHeaderDef("Total Shipments For Metric","Integer"));
        header.add(new SimpleHeaderDef("Total Shipments","Integer"));
        header.add(new SimpleHeaderDef("First Ship Date","Date"));
        header.add(new SimpleHeaderDef("Last Ship Date","Date"));
        header.add(new SimpleHeaderDef("Shipped Complete within selected time period","String"));
        header.add(new SimpleHeaderDef("Exceeds Previous 3 Months order total","String"));
        header.add(new SimpleHeaderDef("SPL","String"));
        header.add(new SimpleHeaderDef("User Selected Shipping Method","String"));
        return ReportingUtils.createGenericReportColumnView(header);
    }
    
    
    /**
     *Generates the detail header for this reports
     */
    private GenericReportColumnViewVector getReportItemsHeader(){
        ArrayList header = new ArrayList();
        header.add(new SimpleHeaderDef("Order Number","String"));
        header.add(new SimpleHeaderDef("Order Date","Date"));
        header.add(new SimpleHeaderDef("Order Line","Integer"));
        header.add(new SimpleHeaderDef("Line Amount","BigDecimal"));
        header.add(new SimpleHeaderDef("Order Qty","Integer"));
        header.add(new SimpleHeaderDef("Total Qty Shipped","Integer"));
        header.add(new SimpleHeaderDef("Item Num","String"));
        header.add(new SimpleHeaderDef("Uom","String"));
        header.add(new SimpleHeaderDef("Pack","String"));
        header.add(new SimpleHeaderDef("Item Name","String"));
        header.add(new SimpleHeaderDef("Total Shipments","Integer"));
        header.add(new SimpleHeaderDef("First Ship Date","Date"));
        header.add(new SimpleHeaderDef("Last Ship Date","Date"));
        header.add(new SimpleHeaderDef("Shipped Complete within selected time period","String"));
        header.add(new SimpleHeaderDef("Exceeds Previous 3 Months order total","String"));
        header.add(new SimpleHeaderDef("Prev Qty Ordered In Last "+MONTHLY_AVG_BASELINE_NUM+" Months","Integer"));
        header.add(new SimpleHeaderDef("SPL","String"));
        header.add(new SimpleHeaderDef("Requested Ship Date","Date"));
        header.add(new SimpleHeaderDef("Will Call/Combine Next Order","String"));
        return ReportingUtils.createGenericReportColumnView(header);
    }
    
    private String xlateBool(boolean val){
        if(val){
            return  "Y";
        }
        return  "N";
    }
    
    /**
     *Container class that implemets Record so that the @see ReportingUtil class can do the work of generating the report.  Holds
     *the contents of this report and knows how to format itself.
     */
    public class DetailRecord implements Record{
        private OrderItemData mOrderItem;
        private OrderItemActionDataVector mActions;
        private List mInvoicesAndInvoiceDetail;
        private Integer mOrderHistQty;
        private boolean isSPLItem;
        private Date requestedShipDate;
        private boolean ignoreBasedOffUserSeletedFreight;
        
        private DetailRecord(OrderItemData pOrderItem){
            mOrderItem = pOrderItem;
        }
        
        private boolean isShippedComplete(){
            if(isOrderExceedsPrevHist()){
                return true;
            }
            //if will call (ignoreBasedOffUserSeletedFreight) and nothing filled int return true
            if(ignoreBasedOffUserSeletedFreight){
                if(getTotalShipments() == 0 && 0 == getTotalQtyShipped()){
                    return true;
                }
        	}
            return isShippedInTime() && getTotalShipments() == 1 && mOrderItem.getTotalQuantityOrdered() <= getTotalQtyShipped();
        }
        
        private BigDecimal getLineTotal(){
            return mOrderItem.getCustContractPrice().multiply(new BigDecimal(mOrderItem.getTotalQuantityOrdered()));
        }
        
        /**
         *Contains the actual formating logic to transform this object into a row in the report
         */
        public List toList(){
            ArrayList me = new ArrayList();
            me.add(mOrderItem.getErpPoNum());
            me.add(mOrderItem.getErpPoDate());
            me.add(new Integer(mOrderItem.getErpPoLineNum()));
            me.add(getLineTotal()); //line total
            me.add(new Integer(mOrderItem.getTotalQuantityOrdered()));
            me.add(new Integer(getTotalQtyShipped()));
            me.add(mOrderItem.getDistItemSkuNum());
            me.add(mOrderItem.getDistItemUom());
            me.add(mOrderItem.getDistItemPack());
            me.add(mOrderItem.getItemShortDesc());
            me.add(new Integer(getTotalShipments()));
            me.add(getFirstShipmentDate());
            me.add(getLastShipmentDate());
            me.add(xlateBool(isShippedComplete()));
            me.add(xlateBool(isOrderExceedsPrevHist()));
            me.add(mOrderHistQty);
            me.add(xlateBool(isSPLItem));
            me.add(requestedShipDate);
            me.add(xlateBool(ignoreBasedOffUserSeletedFreight));
            return me;
        }
        
        private int getTotalShipments(){
            HashSet invoiceIds = new HashSet();
            for(int i=0,len=mInvoicesAndInvoiceDetail.size();i<len;i++){
                Object obj = mInvoicesAndInvoiceDetail.get(i);
                //deal with subs shipped complete on a single invoice (single invoice multiple lines associated with one order line)
                if(obj instanceof InvoiceDistData){
                    invoiceIds.add(new Integer(((InvoiceDistData) obj).getInvoiceDistId()));
                }else if(obj instanceof InvoiceDistDetailData){
                    invoiceIds.add(new Integer(((InvoiceDistDetailData) obj).getInvoiceDistId()));
                }else if(obj instanceof InvoiceCustData){
                    invoiceIds.add(new Integer(-1 * ((InvoiceCustData) obj).getInvoiceCustId()));
                }else if(obj instanceof InvoiceCustDetailData){
                    invoiceIds.add(new Integer(-1 * ((InvoiceCustDetailData) obj).getInvoiceCustId()));
                }
            }
            return (mActions.size() + mInvoicesAndInvoiceDetail.size());
        }
        
        /**
         *Assigns unique identifiers to shipments.  This is a derived value, so if an item 
         *in an order item action for example will use the day, so if called for 2 seperate items that have 
         *the same action date that will return the same shipment identifier.  For invoice records the invoice 
         *number is used as this is deemed more accurate.
         */
        private Set getTotalShipmentIds(){
            HashSet shipments = new HashSet();
            Iterator it = mActions.iterator();
            while(it.hasNext()){
                OrderItemActionData oiact = (OrderItemActionData) it.next();
                shipments.add(cachingSdf.format(oiact.getActionDate()));
            }
            it = mInvoicesAndInvoiceDetail.iterator();
            while(it.hasNext()){
                InvoiceDistData inv = getInvoice(it.next());
                shipments.add(inv.getInvoiceNum());
            }
            return shipments;
        }
        
        private int getTotalQtyShipped(){
            int total=0;
            Iterator it = mActions.iterator();
            while(it.hasNext()){
                OrderItemActionData v = (OrderItemActionData) it.next();
                total = total + v.getQuantity();
            }
            it = mInvoicesAndInvoiceDetail.iterator();
            while(it.hasNext()){
                InvoiceDistDetailData v = getInvoiceDetail(it.next());
                total = total + v.getDistItemQuantity();
            }
            return total;
        }
        
        /**
         *Returns the first shipement date for this item
         */
        private Date getFirstShipmentDate(){
            //order of which list to check is important!
            if(mActions.size() >= 1){
                OrderItemActionData act = (OrderItemActionData) mActions.get(0);
                return act.getActionDate();
            }
            if(mInvoicesAndInvoiceDetail.size() >= 1){
                InvoiceDistData inv = getInvoice(mInvoicesAndInvoiceDetail.get(0));
                return inv.getInvoiceDate();
            }
            return null;
        }
        /**
         *Returns the last shipement date for this item
         */
        private Date getLastShipmentDate(){
            //only populate this data if there is a last shippment.  If there is only one shipment
            //then only populate that data and ignore the "last" shipment date.
            if(getTotalShipments() > 1){
                //order of which list to check is important!
                if(mInvoicesAndInvoiceDetail.size() >= 1){
                    InvoiceDistData inv = getInvoice(mInvoicesAndInvoiceDetail.get(mInvoicesAndInvoiceDetail.size() - 1));
                    return inv.getInvoiceDate();
                }
                if(mActions.size() >= 1){
                    OrderItemActionData act = (OrderItemActionData) mActions.get(mActions.size() - 1);
                    return act.getActionDate();
                }
            }
            return null;
        }
        
        /**
         *Retrieves a invoice object from the 2d array
         */
        private InvoiceDistData getInvoice(Object o){
            List l = (List) o;
            return (InvoiceDistData) l.get(0);
        }
        /**
         *Retrieves a invoice detail object from the 2d array
         */
        private InvoiceDistDetailData getInvoiceDetail(Object o){
            List l = (List) o;
            return (InvoiceDistDetailData) l.get(1);
        }
        
        /**
         *According to the provided allowed days parameter returns true if this item was shipped in time.
         */
        private boolean isShippedInTime(){
            //ignore if previous history outside norm
            if(isOrderExceedsPrevHist()){
                return true;
            }
            //Calendar cal = Calendar.getInstance();
        	if(ignoreBasedOffUserSeletedFreight){
        		return true;
        	}
            Date lastShipment = getLastShipmentDate();
            Date firstShipment = getFirstShipmentDate();
            Date theDate;
            if(lastShipment != null){
                theDate = lastShipment;
            }else if(firstShipment != null){
                theDate = firstShipment;
            }else{
                return false;
            }
            theDate = Utility.addWeekdays(theDate,-1 * (allowedDays + 1)); //2 for greater than or equal to
            Date poDate = mOrderItem.getErpPoDate();
            if(requestedShipDate != null){
            	//consider users entering requested shipdate 
            	//at an unreasonable time frame (i.e. next day).
            	if(theDate.before(requestedShipDate)){
            	    return requestedShipDate.after(poDate);
                }
            }
            return theDate.before(poDate) ;
        }
        
        /**
         *According to our Monthly average indicates wheather this item was outside the normal amount ordered
         */
        private boolean isOrderExceedsPrevHist(){
            int prevQty = 0;
            if(mOrderHistQty != null){
                prevQty = mOrderHistQty.intValue();
            }
            return prevQty < mOrderItem.getTotalQuantityOrdered();
        }
        
    }
    
    
    /**
     *Container class that implemets Record so that the @see ReportingUtil class can do the work of generating the report.  Holds
     *the contents of this report and knows how to format itself.
     */
    public class SummaryRecord implements Record{
        private boolean isSPL;
        private List mDetailRecords = new ArrayList();
        private String erpPONum;
        private Date erpPoDate;
        private BigDecimal amount;
        
        int totalQtyOrdered;
        int totalQtyShipped;
        Set totalShipments = new HashSet();
        Set totalShipmentsForMetric = new HashSet();
        Date firstShipment;
        Date lastShipment;
        String userSelectFreightString;
        
        
        boolean isShippedComplete=true;
        boolean orderExceedsPrevHist=false;
        
        private SummaryRecord(){

        }
        
        private void addDetailRecord(DetailRecord detRec) throws SQLException{
            if(erpPONum == null){
                erpPONum = detRec.mOrderItem.getErpPoNum();
                erpPoDate = detRec.mOrderItem.getErpPoDate();
                isSPL = detRec.isSPLItem;
            }
            amount = Utility.addAmt(amount, detRec.getLineTotal());
            mDetailRecords.add(detRec);
            //update all cached values
            totalQtyOrdered = totalQtyOrdered + detRec.mOrderItem.getTotalQuantityOrdered();
            totalQtyShipped = totalQtyShipped + detRec.getTotalQtyShipped();
            
            totalShipments.addAll(detRec.getTotalShipmentIds());
            if(!detRec.isOrderExceedsPrevHist()){
            	totalShipmentsForMetric.addAll(detRec.getTotalShipmentIds());
        	}
            
            Date recDate = detRec.getFirstShipmentDate();
            if(firstShipment == null){
                firstShipment = recDate;
            }else if(recDate != null){
                if(firstShipment.after(recDate)){
                    firstShipment = recDate;
                }
            }
            
            //have to analyize both 1st and last shipment date.
            //dist may have shipped a line out as a second shipment 
            //all by itself.
            recDate = detRec.getLastShipmentDate();
            if(recDate == null){
                recDate = detRec.getFirstShipmentDate();
            }
            if(lastShipment == null){
                lastShipment = recDate;
            }else if(recDate != null){
                if(lastShipment.before(recDate)){
                    lastShipment = recDate;
                }
            }
            
            isShippedComplete = detRec.isShippedComplete() && isShippedComplete;
            
            orderExceedsPrevHist = detRec.isOrderExceedsPrevHist() || orderExceedsPrevHist;
            
            if(isShippedComplete && !detRec.ignoreBasedOffUserSeletedFreight && !detRec.isOrderExceedsPrevHist()){
                isShippedComplete = (totalShipmentsForMetric.size() <= 1);
            }

            //a "" is a significant value!  Don't user Utility.isSet()!
            if(userSelectFreightString == null){
                //get the user selected freight information
                int distId = mDisBusEnt.getBusEntityId();
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(OrderFreightDataAccess.ORDER_ID,detRec.mOrderItem.getOrderId());
                crit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID,distId);
                OrderFreightDataVector ofdv = OrderFreightDataAccess.select(mCon, crit, 1);
                if(ofdv.isEmpty()){
                    userSelectFreightString = "";
                }else{
                    OrderFreightData ofd = (OrderFreightData) ofdv.get(0);
                    userSelectFreightString = ofd.getShortDesc();
                }
            }
            
        }
        
        
        
        /**
         *Contains the actual formating logic to transform this object into a row in the report for the "Order" tab
         */
        public List toList(){
            ArrayList me = new ArrayList();
            me.add(erpPONum);
            me.add(erpPoDate);
            me.add(amount);
            me.add(new Integer(totalQtyOrdered));
            me.add(new Integer(totalQtyShipped));
            if(totalShipmentsForMetric == null){
                me.add(new Integer(0));
            }else{
                me.add(new Integer(totalShipmentsForMetric.size()));
            }
            if(totalShipments == null){
                me.add(new Integer(0));
            }else{
                me.add(new Integer(totalShipments.size()));
            }
            me.add(firstShipment);
            if(totalShipments.size() > 1){
                me.add(lastShipment);
            }else{
                me.add(null); //only add if there are more then 1 shipments
            }
            
            me.add(xlateBool(isShippedComplete));
            //me.add(xlateBool(isShippedInTime));
            me.add(xlateBool(orderExceedsPrevHist));
            me.add(xlateBool(isSPL));
            me.add(userSelectFreightString);
            
            return me;
        }
        
        
    }
    
    
    /**
     *comparator for the detail report, report will be sorted according to this comparators logic
     */
    private static final Comparator DETAIL_COMP = new Comparator() {
        public int compare(Object o1, Object o2) {
            Date d1 = ((DetailRecord)o1).mOrderItem.getErpPoDate();
            Date d2 = ((DetailRecord)o2).mOrderItem.getErpPoDate();
            return d1.compareTo(d2);
        }
    };
    
    /**
     *comparator for the summary report, report will be sorted according to this comparators logic
     */
    private static final Comparator SUMMARY_COMP = new Comparator() {
        public int compare(Object o1, Object o2) {
            Date d1 = ((SummaryRecord)o1).erpPoDate;
            Date d2 = ((SummaryRecord)o2).erpPoDate;
            return d1.compareTo(d2);
        }
    };
    
    
    
    
}

