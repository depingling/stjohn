package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dao.WorkOrderStatusHistDataAccess;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.session.Service;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.GenericReportControlFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 */
public class ServiceProviderWorkOrderReport implements GenericReportMulti {


    private static String className = "ServiceProviderWorkOrderReport";
    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;
    protected static final String BOLD_STYLE = "BOLD";
    protected static final String[] WOT_CW = new String[] {"8","8","10","8","23","20","25","22","18","8","16","8","12","10","10","10","10","30","8","15","9"};
    protected static final String[] WOTS_CW = new String[] {"8","8","10","8","23","20","25","22","18","8","16","8","12","10","10","10","10","30","8","15","9","15"};
    protected static final String[] WOD_CW = new String[] {"8","8","10","8","23","20","25","22","18","15","8","16","8","12","10","10","10","10","10","25","8","9","9","9","9","9","30","8","15","15"};
    
    
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection conn = pCons.getMainConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        
        
        APIAccess factory = new APIAccess();
        User userEjb = factory.getUserAPI();
        WorkOrderSimpleSearchCriteria criteria = new WorkOrderSimpleSearchCriteria();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Object woSearchType = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_SEARCH_TYPE);
        Object requestedService = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.REQUESTED_SERVICE);
        Object woNumber = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_NUMBER);
        Object serviceProvider = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.SERVICE_PROVIDER);
        Object woStatus = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_STATUS);
        Object woType = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_TYPE);
        Object woPriority = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_PRIORITY);
        //Object woDateType = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DATE_TYPE);
        Object woBegDateActual = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ACTUAL);
        Object woEndDateActual = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ACTUAL);
        Object woBegDateEstimate = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ESTIMATE);
        Object woEndDateEstimate = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ESTIMATE);
        Object storeIdStr = getParamValue(pParams, "STORE");
        //String userIdStr = (String)getParamValue(pParams, "USER_ID");
        String userIdStr = (String)getParamValue(pParams, "CUSTOMER");

        IdVector spIds = new IdVector();
        BusEntityDataVector spV = userEjb.getBusEntityCollection(Integer.parseInt(userIdStr), RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
        BusEntityData beD;
        for (int i = 0; i < spV.size(); i++) {
            beD = (BusEntityData)spV.get(i);
            if (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(beD.getBusEntityStatusCd())) {
                spIds.add(Integer.valueOf(beD.getBusEntityId()));
            }
        }

        String serviceProviderIdStr = "0";
        if (!spIds.isEmpty()) {
            serviceProviderIdStr = IdVector.toCommaString(spIds);
        }
        
        String begDateActualCond = "";
        if(Utility.isSet((String) woBegDateActual)) {
            begDateActualCond = 
            " AND (To_Date(To_Char(wo.actual_start_date,'mm/dd/yyyy'),'mm/dd/yyyy'))  >= \n\r"+
            "      To_Date ('"+((String) woBegDateActual).trim()+"','mm/dd/yyyy') \n\r";
        }
        String endDateActualCond = "";
        if(Utility.isSet((String) woEndDateActual)) {
            endDateActualCond = 
            " AND (To_Date(To_Char(wo.actual_finish_date,'mm/dd/yyyy'),'mm/dd/yyyy'))  <= \n\r"+
            "      To_Date ('"+((String) woEndDateActual).trim()+"','mm/dd/yyyy') \n\r";
        }
        
        String begDateEstimateCond = "";
        if(Utility.isSet((String) woBegDateEstimate)) {
            begDateEstimateCond = 
            " AND (To_Date(To_Char(wo.estimate_start_date,'mm/dd/yyyy'),'mm/dd/yyyy'))  >= \n\r"+
            "      To_Date ('"+((String) woBegDateEstimate).trim()+"','mm/dd/yyyy') \n\r";
        }
        String endDateEstimateCond = "";
        if(Utility.isSet((String) woEndDateEstimate)) {
            endDateEstimateCond = 
            " AND (To_Date(To_Char(wo.estimate_finish_date,'mm/dd/yyyy'),'mm/dd/yyyy'))  <= \n\r"+
            "      To_Date ('"+((String) woEndDateEstimate).trim()+"','mm/dd/yyyy') \n\r";
        }
        
        String requestedServiceCond = "";
        if(Utility.isSet((String) requestedService)) {
            if (Utility.isSet((String)woSearchType) && RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals((String)woSearchType)) {
                requestedServiceCond =
                " AND lower(wo.short_desc) LIKE '"+((String) requestedService).trim().toLowerCase()+"%' \n\r";
            } else {
                requestedServiceCond =
                " AND lower(wo.short_desc) LIKE '%"+((String) requestedService).trim().toLowerCase()+"%' \n\r";
            }
        }

        String workOrderNumCond = "";
        if(Utility.isSet((String) woNumber)) {
            workOrderNumCond =
            " AND lower(wo.work_order_num) LIKE '%"+((String) woNumber).trim().toLowerCase()+"%' \n\r";
        }
        String statusCond = "";
        if(Utility.isSet((String) woStatus)) {
            statusCond =
            " AND lower(wo.status_cd) LIKE '"+((String) woStatus).trim().toLowerCase()+"%' \n\r";
        }
        String typeCond = "";
        if(Utility.isSet((String)woType)) {
            typeCond =
            " AND lower(wo.type_cd) LIKE '"+((String)woType).trim().toLowerCase()+"%' \n\r";
        }

        String priorityCond = "";
        if(Utility.isSet((String)woPriority)) {
            priorityCond =
            " AND lower(wo.priority) LIKE '"+((String)woPriority).trim().toLowerCase()+"%' \n\r";
        }

        String providerCond = "";
        if(Utility.isSet((String)serviceProvider)) {
            providerCond =
            " AND lower(prov.bus_entity_id) = " + ((String)serviceProvider).trim().toLowerCase() + "\n\r";
        }
        
//================================================================================
// Work Order Detail

        String workOrderDetailReq =
        " SELECT DISTINCT \n\r" + 
        " wo.WORK_ORDER_NUM, \n\r" +
        " wo.PO_NUMBER, \n\r" +
        " To_Date(To_Char(wo.add_date,'mm/dd/yyyy'),'mm/dd/yyyy') AS add_date, \n\r" +
        " To_Char(wo.add_date,'hh:mi pm') AS add_time, \n\r" +
        " woacct.short_desc AS account_name, \n\r" +
        " wosite.short_desc AS site_name, \n\r" +
        " wod.PAYMENT_TYPE_CD AS service_type, \n\r" +
        " wo.short_desc AS requested_service, \n\r" +
        " wo.action_cd AS action, \n\r" +
        " wo.type_cd AS TYPE, \n\r" +
        " wo.priority AS priority, \n\r" +
        " wo.status_cd AS status, \n\r" +
        " (CASE WHEN wo.status_cd = 'Completed' \n\r" +
        "   OR wo.status_cd = 'Cancelled' \n\r" +
        "   THEN 'Closed' ELSE 'Open' END) AS po_closed_or_open, \n\r" +
        " woa.bus_entity_id AS provider_id, \n\r" +
        " prov.short_desc AS service_provider, \n\r" +
        " wo.actual_start_date, \n\r" +
        " wo.actual_finish_date, \n\r" +
        " wo.estimate_start_date as Quoted_Start_Date, \n\r" +
        " wo.estimate_finish_date AS Quoted_Finish_Date, \n\r" +

        " wod.part_number AS Part_Num, \n\r" +
        " wod.short_desc AS Description, \n\r" +
        " wod.Quantity, \n\r" +
        " wod.part_price AS Price_Each, \n\r" +
        " wod.part_price * wod.quantity AS Parts_Price_Extended, \n\r" +
        " wod.Labor, \n\r" +
        " wod.Travel, \n\r" +
        " wod.part_price * wod.quantity + wod.labor + wod.travel AS Total_Price, \n\r" +
        
        " woasset.asset_id, \n\r" +
        " asset.short_desc AS asset_name, \n\r" +
        " asset.MODEL_NUMBER AS asset_model, \n\r" +
        " asset.SERIAL_NUM AS asset_serial_number, \n\r" +
        " wo.add_by AS user_name \n\r" +
        
        " FROM CLW_WORK_ORDER wo \n\r" +
        
        " JOIN CLW_BUS_ENTITY be \n\r" +
        "    ON be.BUS_ENTITY_ID IN (" + serviceProviderIdStr +  ") \n\r" +
        " JOIN CLW_BUS_ENTITY_ASSOC bea \n\r" +
        "    ON  be.BUS_ENTITY_ID = bea.BUS_ENTITY1_ID \n\r" +
        "    AND bea.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE + "' \n\r" +
        "    AND bea.BUS_ENTITY2_ID = wo.BUS_ENTITY_ID \n\r" +
        " JOIN CLW_BUS_ENTITY_ASSOC bea1 \n\r" +
        "    ON  be.BUS_ENTITY_ID = bea1.BUS_ENTITY1_ID \n\r" +
        "    AND bea1.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE + "' \n\r" +
        "     AND bea1.BUS_ENTITY2_ID = " + storeIdStr + "\n\r" +

        "JOIN CLW_WORK_ORDER_ASSOC woa \n\r" +
        "    ON  woa.WORK_ORDER_ID = wo.WORK_ORDER_ID \n\r" +
        "    AND woa.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n\r" +
        "    AND wo.WORK_ORDER_ID IN (SELECT DISTINCT work_order_id FROM clw_work_order_status_hist WHERE status_cd = '" + 
                                                                    RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER + "' " +
        "    AND clw_work_order_status_hist.work_order_id = wo.WORK_ORDER_ID ) \n\r" +
                
        "    AND wo.WORK_ORDER_ID NOT IN ( SELECT DISTINCT wos_hist2.work_order_id FROM \n\r" +
        "                                      clw_work_order_status_hist wos_hist1, \n\r" +
        "                                      clw_work_order_status_hist wos_hist2  \n\r" +
        "                                      WHERE   wos_hist1.work_order_id = wos_hist2.work_order_id AND \n\r" +
        "                                              wos_hist1.work_order_id = wo.WORK_ORDER_ID AND \n\r" +
        "                                              wos_hist2.work_order_id = wo.WORK_ORDER_ID AND \n\r" +
        "                                              wos_hist1.status_cd = '" +
                                                       RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED + "' AND \n\r" +
        "                                              wos_hist2.status_cd = '" +
                                                       RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER + "' AND \n\r" +
        "                                              wos_hist2.mod_date > wos_hist1.mod_date) \n\r" +
        " LEFT JOIN clw_bus_entity wosite \n\r" +
        "   ON wosite.bus_entity_id = wo.bus_entity_id \n\r" +
        " LEFT JOIN clw_bus_entity_assoc bea2 \n\r" +
        "   ON wosite.bus_entity_id = bea2.bus_entity1_id \n\r" +
        "   AND bea2.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n\r" +
        " LEFT JOIN clw_bus_entity woacct \n\r" +
        "   ON woacct.bus_entity_id = bea2.bus_entity2_id \n\r" +

        " LEFT JOIN clw_bus_entity prov \n\r" +
        "  ON prov.bus_entity_id = woa.bus_entity_id \n\r" +
        " LEFT JOIN clw_work_order_item woi \n\r" +
        "  ON woi.work_order_id = wo.work_order_id \n\r" +
        " LEFT JOIN clw_work_order_asset woasset \n\r" +
        "  ON woasset.work_order_item_id = woi.work_order_item_id \n\r" +
        " LEFT JOIN clw_asset asset \n\r" +
        "  ON asset.asset_id = woasset.asset_id \n\r" +
        " LEFT JOIN clw_work_order_detail wod \n\r" +
        "  ON wod.work_order_id = wo.work_order_id \n\r" +
        "  AND wod.status_cd = 'ACTIVE' \n\r" +    
        " WHERE 1=1 \n\r" +
        requestedServiceCond +
        workOrderNumCond +
        statusCond +
        typeCond +
        priorityCond +        
        providerCond +
        begDateActualCond +
        endDateActualCond +
        begDateEstimateCond +
        endDateEstimateCond +
        " ORDER BY account_name, site_name, DECODE(priority,'High',0,'Medium',1,'Low',2)";


        PreparedStatement pstmt = conn.prepareStatement(workOrderDetailReq);

        ResultSet rs = pstmt.executeQuery();
        LinkedList ll = new LinkedList();
        while (rs.next()){
            WorkOrderDetails wod = new WorkOrderDetails();
            ll.add(wod);
            wod.workOrderNum = rs.getString("WORK_ORDER_NUM");
            wod.poNum = rs.getString("PO_NUMBER");
            wod.addDate = rs.getDate("add_date");
            wod.addTime = rs.getString("add_time");
            wod.acctName = rs.getString("account_name");
            wod.siteName = rs.getString("site_name");
            wod.serviceType = rs.getString("TYPE");
            wod.requestedService = rs.getString("requested_service");
            wod.action = rs.getString("action");            
            wod.priority = rs.getString("priority");
            wod.status = rs.getString("status");
            wod.poClosedOrOpen = rs.getString("po_closed_or_open");
            wod.providerId = rs.getInt("provider_id");
            wod.serviceProvider = rs.getString("service_provider");
            wod.actualStartDate = rs.getDate("actual_start_date");
            wod.actualFinishDate = rs.getDate("actual_finish_date");
            wod.quotedStartDate = rs.getDate("Quoted_Start_Date");
            wod.quotedFinishDate = rs.getDate("Quoted_Finish_Date");
            wod.partNum = rs.getString("Part_Num");
            wod.partDesc = rs.getString("Description");
            wod.quantity = rs.getInt("Quantity");
            wod.priceEach = rs.getBigDecimal("Price_Each");
            wod.partsPriceExtended = rs.getBigDecimal("Parts_Price_Extended");
            wod.labor = rs.getBigDecimal("Labor");
            wod.travel = rs.getBigDecimal("Travel");
            wod.totalCost = rs.getBigDecimal("Total_Price");
            wod.assetId = rs.getInt("asset_id");
            wod.assetName = rs.getString("asset_name");
            wod.assetModel = rs.getString("asset_model");
            wod.assetSerialNumber = rs.getString("asset_serial_number");
            wod.userName = rs.getString("user_name");
        }
        rs.close();
        pstmt.close();


        GenericReportResultView result2 = GenericReportResultView.createValue();
        GenericReportColumnViewVector header2 = getWODReportHeader();
        result2.setHeader(header2);
        result2.setColumnCount(header2.size());
        result2.setName("Work Order Details");
        result2.setTitle(getReportTitle("Work Order Details", pParams));
        result2.setUserStyle(createReportStyleWODDescriptor());
        result2.setFreezePositionColumn(6);
        result2.setFreezePositionRow(4);
        ArrayList table2 = new ArrayList();
        result2.setTable(table2);

        for(Iterator iter = ll.iterator(); iter.hasNext();) {
            WorkOrderDetails wod = (WorkOrderDetails) iter.next();
            ArrayList line = new ArrayList();
            table2.add(line);
            line.add(wod.workOrderNum);
            line.add(wod.poNum);
            line.add(wod.addDate);
            line.add(wod.addTime);
            line.add(wod.acctName);
            line.add(wod.siteName);
            line.add(wod.serviceType);
            line.add(wod.requestedService);
            line.add(wod.action);
            line.add(wod.priority);
            line.add(wod.status);
            line.add(wod.poClosedOrOpen);
            line.add(wod.serviceProvider);
            line.add(wod.actualStartDate);
            line.add(wod.actualFinishDate);
            line.add(wod.quotedStartDate);
            line.add(wod.quotedFinishDate);
            
            line.add(wod.partNum);
            line.add(wod.partDesc);
            line.add(new Integer(wod.quantity));
            line.add(wod.priceEach);
            line.add(wod.partsPriceExtended);
            line.add(wod.labor);
            line.add(wod.travel);
            line.add(wod.totalCost);
            
            line.add(wod.assetName);
            line.add(wod.assetModel);
            line.add(wod.assetSerialNumber);
            line.add(wod.userName);
        }
        resultV.add(result2);
                

        
//=============================================================
        String[] costTypes = new String[3];
        costTypes[0] = RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT;
        costTypes[1] = RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY;
        costTypes[2] = RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE;

        String[] costPageNames = new String[3];
        costPageNames[0] = "PM Totals";
        costPageNames[1] = "Warranty Totals";
        costPageNames[2] = "Billable Totals";

        String[] valueColumNames = new String[3];
        valueColumNames[0] = "PM Total";
        valueColumNames[1] = "Warranty Total";
        valueColumNames[2] = "Billable Total";
        for(int ii=0; ii<3; ii++) {
            String costType = costTypes[ii];
            String pageName = costPageNames[ii];
            String columnName = valueColumNames[ii];

            String workOrderSpecReq =
            " SELECT DISTINCT \n\r" +
            "  wo.WORK_ORDER_NUM, \n\r" +
            " wo.PO_NUMBER, \n\r" +
            " To_Date(To_Char(wo.add_date,'mm/dd/yyyy'),'mm/dd/yyyy') AS add_date, \n\r" +
            " To_Char(wo.add_date,'hh:mi pm') AS add_time, \n\r" +
            " woacct.short_desc AS account_name, \n\r" +
            " wosite.short_desc AS site_name, \n\r" +
            " wo.short_desc AS requested_service, \n\r" +
            " wo.action_cd AS action, \n\r" +
            " wo.type_cd AS TYPE, \n\r" +
            " wo.priority AS priority, \n\r" +
            " wo.status_cd AS status, \n\r" +
            " (CASE WHEN wo.status_cd = 'Completed' \n\r" +
            "    OR wo.status_cd = 'Cancelled' \n\r" +
            "    THEN 'Closed' ELSE 'Open' END) AS po_closed_or_open, \n\r" +
            " woa.bus_entity_id AS provider_id, \n\r" +
            " prov.short_desc AS service_provider, \n\r" +
            " actual_start_date, \n\r" +
            " actual_finish_date, \n\r" +
            " wo.estimate_start_date as Quoted_Start_Date, \n\r" +
            " estimate_finish_date AS Quoted_Finish_Date, \n\r" +
            " woasset.asset_id, \n\r" +
            " asset.short_desc AS asset_name, \n\r" +
            " asset.MODEL_NUMBER AS asset_model, \n\r" +
            " asset.SERIAL_NUM AS asset_serial_number, \n\r" +
            " (SELECT Sum(PART_PRICE * QUANTITY + LABOR + TRAVEL) \n\r" +
            "   FROM clw_work_order_detail wod \n\r" +
            "   WHERE wod.work_order_id = wo.work_order_id \n\r" +
            "     AND wod.PAYMENT_TYPE_CD = '" + costType + "') cost, \n\r" +
            " wo.add_by AS user_name \n\r" +
             
            " FROM CLW_WORK_ORDER wo \n\r" +
        
            " JOIN CLW_BUS_ENTITY be \n\r" +
            "   ON be.BUS_ENTITY_ID IN (" + serviceProviderIdStr +  ") \n\r" +
            " JOIN CLW_BUS_ENTITY_ASSOC bea \n\r" +
            "   ON  be.BUS_ENTITY_ID = bea.BUS_ENTITY1_ID \n\r" +
            "   AND bea.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE + "' \n\r" +
            "   AND bea.BUS_ENTITY2_ID = wo.BUS_ENTITY_ID \n\r" +
            " JOIN CLW_BUS_ENTITY_ASSOC bea1 \n\r" +
            "   ON  be.BUS_ENTITY_ID = bea1.BUS_ENTITY1_ID \n\r" +
            "   AND bea1.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE + "' \n\r" +
            "   AND bea1.BUS_ENTITY2_ID = " + storeIdStr + "\n\r" +

            " JOIN CLW_WORK_ORDER_ASSOC woa \n\r" +
            "   ON  woa.WORK_ORDER_ID = wo.WORK_ORDER_ID \n\r" +
            "   AND woa.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n\r" +
            "   AND wo.WORK_ORDER_ID IN (SELECT DISTINCT work_order_id FROM clw_work_order_status_hist WHERE status_cd = '" + 
                                                                    RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER + "' " +
            "   AND clw_work_order_status_hist.work_order_id = wo.WORK_ORDER_ID ) \n\r" +
            "   AND wo.WORK_ORDER_ID NOT IN ( SELECT DISTINCT wos_hist2.work_order_id FROM \n\r" +
            "                                                 clw_work_order_status_hist wos_hist1, \n\r" +
            "                                                 clw_work_order_status_hist wos_hist2 \n\r" +
            "                                                 WHERE   wos_hist1.work_order_id = wos_hist2.work_order_id AND \n\r" +
            "                                                         wos_hist1.work_order_id = wo.WORK_ORDER_ID AND \n\r" +
            "                                                         wos_hist2.work_order_id = wo.WORK_ORDER_ID AND \n\r" +
            "                                                         wos_hist1.status_cd = '" +
                                                                      RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED + "' AND \n\r" +
            "                                                         wos_hist2.status_cd = '" +
                                                                      RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER + "' AND \n\r" +
            "                                                         wos_hist2.mod_date > wos_hist1.mod_date) \n\r" +
            " LEFT JOIN clw_bus_entity wosite \n\r" +
            "  ON wosite.bus_entity_id = wo.bus_entity_id \n\r" +
            " LEFT JOIN clw_bus_entity_assoc bea2 \n\r" +
            "  ON wosite.bus_entity_id = bea2.bus_entity1_id \n\r" +
            "  AND bea2.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n\r" +
            " LEFT JOIN clw_bus_entity woacct \n\r" +
            "  ON woacct.bus_entity_id = bea2.bus_entity2_id \n\r" +
            
            " left join clw_bus_entity prov \n\r" +
            "  ON prov.bus_entity_id = woa.bus_entity_id \n\r" +
            " left join clw_work_order_item woi \n\r" +
            "  ON woi.work_order_id = wo.work_order_id \n\r" +
            " left join clw_work_order_asset woasset \n\r" +
            "  ON woasset.work_order_item_id = woi.work_order_item_id \n\r" +
            " left join clw_asset asset \n\r" +
            "  ON asset.asset_id = woasset.asset_id \n\r" +
            " WHERE 1=1 \n\r" +
            requestedServiceCond +
            workOrderNumCond +
            statusCond +
            typeCond +
            priorityCond +        
            providerCond +
            begDateActualCond +
            endDateActualCond +
            begDateEstimateCond +
            endDateEstimateCond +        
            " AND wo.work_order_id IN ( \n\r" +
            " SELECT work_order_id \n\r" +
            " FROM clw_work_order_detail \n\r" +
            "    WHERE PAYMENT_TYPE_CD = '" + costType + "' \n\r" +
            " AND  PART_PRICE * QUANTITY + LABOR + TRAVEL > 0) \n\r" +
            " ORDER BY account_name, site_name, DECODE(priority,'High',0,'Medium',1,'Low',2)";


            pstmt = conn.prepareStatement(workOrderSpecReq);

            rs = pstmt.executeQuery();
            ll = new LinkedList();
            while (rs.next()){
                WorkOrderSpecTotals wost = new WorkOrderSpecTotals();
                ll.add(wost);
                wost.workOrderNum = rs.getString("WORK_ORDER_NUM");
                wost.poNum = rs.getString("PO_NUMBER");
                wost.addDate = rs.getDate("add_date");
                wost.addTime = rs.getString("add_time");
                wost.acctName = rs.getString("account_name");
                wost.siteName = rs.getString("site_name");
                wost.serviceType = rs.getString("TYPE");
                wost.requestedService = rs.getString("requested_service");
                wost.action = rs.getString("action");
                wost.priority = rs.getString("priority");
                wost.status = rs.getString("status");
                wost.poClosedOrOpen = rs.getString("po_closed_or_open");
                wost.providerId = rs.getInt("provider_id");
                wost.serviceProvider = rs.getString("service_provider");
                wost.actualStartDate = rs.getDate("actual_start_date");
                wost.actualFinishDate = rs.getDate("actual_finish_date");
                wost.quotedStartDate = rs.getDate("Quoted_Start_Date");
                wost.quotedFinishDate = rs.getDate("Quoted_Finish_Date");
                wost.assetId = rs.getInt("asset_id");
                wost.assetName = rs.getString("asset_name");
                wost.assetModel = rs.getString("asset_model");
                wost.assetSerialNumber = rs.getString("asset_serial_number");
                wost.cost = rs.getBigDecimal("cost");
                wost.userName = rs.getString("user_name");
            }
            rs.close();
            pstmt.close();


            GenericReportResultView result1 = GenericReportResultView.createValue();
            GenericReportColumnViewVector header1 = getWOTSpecificReportHeader(columnName);
            result1.setHeader(header1);
            result1.setColumnCount(header1.size());
            result1.setName(pageName);
            result1.setTitle(getReportTitle(pageName, pParams));
            result1.setUserStyle(createReportStyleWOTSpecificDescriptor());
            result1.setFreezePositionColumn(6);
            result1.setFreezePositionRow(4);
            ArrayList table1 = new ArrayList();
            result1.setTable(table1);

            for(Iterator iter = ll.iterator(); iter.hasNext();) {
                WorkOrderSpecTotals wost = (WorkOrderSpecTotals) iter.next();
                ArrayList line = new ArrayList();
                table1.add(line);
                line.add(wost.workOrderNum);
                line.add(wost.poNum);
                line.add(wost.addDate);
                line.add(wost.addTime);
                line.add(wost.acctName);
                line.add(wost.siteName);
                line.add(wost.requestedService);
                line.add(wost.action);
                line.add(wost.serviceType);
                line.add(wost.priority);
                line.add(wost.status);
                line.add(wost.poClosedOrOpen);
                line.add(wost.serviceProvider);
                line.add(wost.actualStartDate);
                line.add(wost.actualFinishDate);
                line.add(wost.quotedStartDate);
                line.add(wost.quotedFinishDate);
                line.add(wost.assetName);
                line.add(wost.assetModel);
                line.add(wost.assetSerialNumber);
                line.add(wost.cost);
                line.add(wost.userName);
            }
            resultV.add(result1);
        }        
//===============================================================

        String workOrderTotalsReq =
        "SELECT DISTINCT \n\r" +
        " wo.WORK_ORDER_NUM, \n\r" +
        " wo.PO_NUMBER, \n\r" +
        " To_Date(To_Char(wo.add_date,'mm/dd/yyyy'),'mm/dd/yyyy') AS add_date, \n\r" +
        " To_Char(wo.add_date,'hh:mi pm') AS add_time, \n\r" +
        " woacct.short_desc AS account_name, \n\r" +
        " wosite.short_desc AS site_name, \n\r" +
        " wo.short_desc AS requested_service, \n\r" +
        " wo.action_cd AS action, \n\r" +
        " wo.type_cd AS TYPE, \n\r" +
        " wo.priority AS priority, \n\r" +
        " wo.status_cd AS status, \n\r" +
        " (CASE WHEN wo.status_cd = 'Completed' \n\r" +
        "     OR wo.status_cd = 'Cancelled' \n\r" +
        "     THEN 'Closed' ELSE 'Open' END) AS po_closed_or_open, \n\r" +
        " woa.bus_entity_id AS provider_id, \n\r" +
        " prov.short_desc AS service_provider, \n\r" +
        " actual_start_date, \n\r" +
        " actual_finish_date, \n\r" +
        " wo.estimate_start_date as Quoted_Start_Date, \n\r" +
        " estimate_finish_date AS Quoted_Finish_Date, \n\r" +
        " woasset.asset_id, \n\r" +
        " asset.short_desc AS asset_name, \n\r" +
        " asset.MODEL_NUMBER AS asset_model, \n\r" +
        " asset.SERIAL_NUM AS asset_serial_number, \n\r" +
        " (SELECT Sum(PART_PRICE * QUANTITY + LABOR + TRAVEL) \n\r" +
        "    FROM clw_work_order_detail wod \n\r" +
        "    WHERE wod.work_order_id = wo.work_order_id) actual_total_cost \n\r" +
        
        " FROM CLW_WORK_ORDER wo \n\r" +
        
        " JOIN CLW_BUS_ENTITY be \n\r" +
        "   ON be.BUS_ENTITY_ID IN (" + serviceProviderIdStr +  ") \n\r" +
        " JOIN CLW_BUS_ENTITY_ASSOC bea \n\r" +
        "   ON  be.BUS_ENTITY_ID = bea.BUS_ENTITY1_ID \n\r" +
        "   AND bea.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE + "' \n\r" +
        "   AND bea.BUS_ENTITY2_ID = wo.BUS_ENTITY_ID \n\r" +
        " JOIN CLW_BUS_ENTITY_ASSOC bea1 \n\r" +
        "   ON  be.BUS_ENTITY_ID = bea1.BUS_ENTITY1_ID \n\r" +
        "   AND bea1.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE + "' \n\r" +
        "   AND bea1.BUS_ENTITY2_ID = " + storeIdStr + " \n\r" +

        " JOIN CLW_WORK_ORDER_ASSOC woa \n\r" +
        "   ON  woa.WORK_ORDER_ID = wo.WORK_ORDER_ID \n\r" +
        "   AND woa.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n\r" +
        "   AND wo.WORK_ORDER_ID IN (SELECT DISTINCT work_order_id FROM clw_work_order_status_hist WHERE status_cd = '" + 
                                                                    RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER + "' " +
        "   AND clw_work_order_status_hist.work_order_id = wo.WORK_ORDER_ID ) \n\r" +
        "   AND wo.WORK_ORDER_ID NOT IN ( SELECT DISTINCT wos_hist2.work_order_id FROM \n\r" + 
        "                                                  clw_work_order_status_hist wos_hist1,\n\r" +
        "                                                  clw_work_order_status_hist wos_hist2 \n\r" +
        "                                                 WHERE   wos_hist1.work_order_id = wos_hist2.work_order_id AND \n\r" +
        "                                                         wos_hist1.work_order_id = wo.WORK_ORDER_ID AND \n\r" +
        "                                                         wos_hist2.work_order_id = wo.WORK_ORDER_ID AND \n\r" +
        "                                                         wos_hist1.status_cd = '" +
                                                                  RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED + "' AND \n\r" +
        "                                                         wos_hist2.status_cd = '" +
                                                                  RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER + "' AND \n\r" +
        "                                                         wos_hist2.mod_date > wos_hist1.mod_date) \n\r" +
        " LEFT JOIN clw_bus_entity wosite \n\r" +
        "  ON wosite.bus_entity_id = wo.bus_entity_id \n\r" +
        " LEFT JOIN clw_bus_entity_assoc bea2 \n\r" +
        "  ON wosite.bus_entity_id = bea2.bus_entity1_id \n\r" +
        "  AND bea2.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n\r" +
        " LEFT JOIN clw_bus_entity woacct \n\r" +
        "  ON woacct.bus_entity_id = bea2.bus_entity2_id \n\r" +
           
        " LEFT JOIN clw_bus_entity prov \n\r" +
        "    ON prov.bus_entity_id = woa.bus_entity_id \n\r" +
        " LEFT JOIN clw_work_order_item woi \n\r" +
        "   ON woi.work_order_id = wo.work_order_id \n\r" +
        " LEFT JOIN clw_work_order_asset woasset \n\r" +
        "   ON woasset.work_order_item_id = woi.work_order_item_id \n\r" +
        " LEFT JOIN clw_asset asset \n\r" +
        "   ON asset.asset_id = woasset.asset_id \n\r" +
        "WHERE 1=1  \n\r" +
        requestedServiceCond +
        workOrderNumCond +
        statusCond +
        typeCond +
        priorityCond +        
        providerCond +
        begDateActualCond +
        endDateActualCond +
        begDateEstimateCond +
        endDateEstimateCond +
        "ORDER BY account_name, site_name, DECODE(priority,'High',0,'Medium',1,'Low',2)";

        pstmt = conn.prepareStatement(workOrderTotalsReq);

        rs = pstmt.executeQuery();
        ll = new LinkedList();
        while (rs.next()){
            WorkOrderTotals wot = new WorkOrderTotals();
            ll.add(wot);
            wot.workOrderNum = rs.getString("WORK_ORDER_NUM");
            wot.poNum = rs.getString("PO_NUMBER");
            wot.addDate = rs.getDate("add_date");
            wot.addTime = rs.getString("add_time");
            wot.acctName = rs.getString("account_name");
            wot.siteName = rs.getString("site_name");
            wot.requestedService = rs.getString("requested_service");
            wot.action = rs.getString("action");
            wot.type = rs.getString("TYPE");
            wot.priority = rs.getString("priority");
            wot.status = rs.getString("status");
            wot.poClosedOrOpen = rs.getString("po_closed_or_open");
            wot.providerId = rs.getInt("provider_id");
            wot.serviceProvider = rs.getString("service_provider");
            wot.actualStartDate = rs.getDate("actual_start_date");
            wot.actualFinishDate = rs.getDate("actual_finish_date");
            wot.quotedStartDate = rs.getDate("Quoted_Start_Date");
            wot.quotedFinishDate = rs.getDate("Quoted_Finish_Date");
            wot.assetId = rs.getInt("asset_id");
            wot.assetName = rs.getString("asset_name");
            wot.assetModel = rs.getString("asset_model");
            wot.assetSerialNumber = rs.getString("asset_serial_number");
            wot.actualTotalCost = rs.getBigDecimal("actual_total_cost");
        }
        rs.close();
        pstmt.close();
        

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getWOTReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Work Order Totals");
        result.setTitle(getReportTitle("Work Order Totals", pParams));
        ArrayList table = new ArrayList();
        result.setTable(table);
        result.setUserStyle(createReportStyleWOTDescriptor());
        result.setFreezePositionColumn(6);
        result.setFreezePositionRow(4);

        for(Iterator iter = ll.iterator(); iter.hasNext();) {
            WorkOrderTotals wot = (WorkOrderTotals) iter.next();
            ArrayList line = new ArrayList();
            table.add(line);
            line.add(wot.workOrderNum);
            line.add(wot.poNum);
            line.add(wot.addDate);
            line.add(wot.addTime);
            line.add(wot.acctName);
            line.add(wot.siteName);
            line.add(wot.requestedService);
            line.add(wot.action);
            line.add(wot.type);
            line.add(wot.priority);
            line.add(wot.status);
            line.add(wot.poClosedOrOpen);
            line.add(wot.serviceProvider);
            line.add(wot.actualStartDate);
            line.add(wot.actualFinishDate);
            line.add(wot.quotedStartDate);
            line.add(wot.quotedFinishDate);
            line.add(wot.assetName);
            line.add(wot.assetModel);
            line.add(wot.assetSerialNumber);
            line.add(wot.actualTotalCost);
        }

        resultV.add(result);
        
        return resultV;
    }
    
    private GenericReportColumnViewVector getWOTReportHeader() {
        
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Work Order Number",0,0,null,WOT_CW[0],false,null,"WORK_ORDER_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Number",0,0,null,WOT_CW[1],false,null,"PO_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Add Date",0,0,null,WOT_CW[2],false,null,"ADD_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Add Time",0,0,null,WOT_CW[3],false,null,"ADD_TIME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,0,null,WOT_CW[4],false,null,"ACCOUNT_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,0,null,WOT_CW[5],false,null,"SITE_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Requested Service",0,0,null,WOT_CW[6],false,null,"REQUESTED_SERVICE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action",0,0,null,WOT_CW[7],false,null,"ACTION",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Type",0,0,null,WOT_CW[8],false,null,"SERVICE_TYPE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Priority",0,0,null,WOT_CW[9],false,null,"PRIORITY",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Status",0,0,null,WOT_CW[10],false,null,"STATUS",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Closed or Open?",0,0,null,WOT_CW[11],false,null,"PO_CLOSED_OPEN",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Provider",0,0,null,WOT_CW[12],false,null,"SERVICE_PROVIDER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Actual Start Date",0,0,null,WOT_CW[13],false,null,"ACTUAL_START_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Actual Finish Date",0,0,null,WOT_CW[14],false,null,"ACTUAL_FINISH_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Quoted Start Date",0,0,null,WOT_CW[15],false,null,"QUOTED_START_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Quoted Finish Date",0,0,null,WOT_CW[16],false,null,"QUOTED_FINISH_DATE",null)); 
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Name",0,0,null,WOT_CW[17],false,null,"ASSET_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Model",0,0,null,WOT_CW[18],false,null,"ASSET_MODEL",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Serial Number",0,0,null,WOT_CW[19],false,null,"ASSET_SERIAL_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator","Actual Total Price",0,0,null,WOT_CW[20],false,null,"ASSET_TOTAL_PRICE",null));
        
        return header;
    }
    
    protected HashMap createReportStyleWOTDescriptor() {
          GenericReportStyleView colWorkOrderNumber = new GenericReportStyleView("WORK_ORDER_NUMBER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[0],0);
          GenericReportStyleView colPONumber = new GenericReportStyleView("PO_NUMBER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[1],0);
          GenericReportStyleView colAddDate = new GenericReportStyleView("ADD_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[2],0);
          GenericReportStyleView colAddTime = new GenericReportStyleView("ADD_TIME",ReportingUtils.DATA_CATEGORY.TIME,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[3],0);
          GenericReportStyleView colAccountName = new GenericReportStyleView("ACCOUNT_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[4],0);
          GenericReportStyleView colSiteName = new GenericReportStyleView("SITE_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[5],0);
          GenericReportStyleView colRequestedService = new GenericReportStyleView("REQUESTED_SERVICE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[6],0);
          GenericReportStyleView colAction = new GenericReportStyleView("ACTION","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOT_CW[7],0);
          GenericReportStyleView colServiceType = new GenericReportStyleView("SERVICE_TYPE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[8],0);
          GenericReportStyleView colPriority = new GenericReportStyleView("PRIORITY",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[9],0);
          GenericReportStyleView colStatus = new GenericReportStyleView("STATUS",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[10],0);
          GenericReportStyleView colPOClosedOpen = new GenericReportStyleView("PO_CLOSED_OPEN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[11],0);
          GenericReportStyleView colServiceProvider = new GenericReportStyleView("SERVICE_PROVIDER",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[12],0);
          GenericReportStyleView colActualStartDate = new GenericReportStyleView("ACTUAL_START_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[13],0);
          GenericReportStyleView colActualFinishDate = new GenericReportStyleView("ACTUAL_FINISH_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[14],0);
          GenericReportStyleView colQuotedStartDate = new GenericReportStyleView("QUOTED_START_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[15],0);
          GenericReportStyleView colQuotedFinishDate = new GenericReportStyleView("QUOTED_FINISH_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[16],0);
          GenericReportStyleView colAssetName = new GenericReportStyleView("ASSET_NAME",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[17],0);
          GenericReportStyleView colAssetModel = new GenericReportStyleView("ASSET_MODEL",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[18],0);      
          GenericReportStyleView colAssetSerialNumber = new GenericReportStyleView("ASSET_SERIAL_NUMBER",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[19],0);
          GenericReportStyleView colAssetTotalPrice = new GenericReportStyleView("ASSET_TOTAL_PRICE",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOT_CW[20],0);

          GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
          reportDesc.setUserStyle(colWorkOrderNumber.getStyleName(), colWorkOrderNumber);
          reportDesc.setUserStyle(colPONumber.getStyleName(), colPONumber);
          reportDesc.setUserStyle(colAddDate.getStyleName(), colAddDate);
          reportDesc.setUserStyle(colAddTime.getStyleName(), colAddTime);
          reportDesc.setUserStyle(colAccountName.getStyleName(), colAccountName);
          reportDesc.setUserStyle(colSiteName.getStyleName(), colSiteName);
          reportDesc.setUserStyle(colRequestedService.getStyleName(), colRequestedService);
          reportDesc.setUserStyle(colAction.getStyleName(), colAction);
          reportDesc.setUserStyle(colServiceType.getStyleName(), colServiceType);
          reportDesc.setUserStyle(colPriority.getStyleName(), colPriority);
          reportDesc.setUserStyle(colStatus.getStyleName(), colStatus);
          reportDesc.setUserStyle(colPOClosedOpen.getStyleName(), colPOClosedOpen);
          reportDesc.setUserStyle(colServiceProvider.getStyleName(), colServiceProvider);
          reportDesc.setUserStyle(colActualStartDate.getStyleName(), colActualStartDate);
          reportDesc.setUserStyle(colActualFinishDate.getStyleName(), colActualFinishDate);
          reportDesc.setUserStyle(colQuotedStartDate.getStyleName(), colQuotedStartDate);
          reportDesc.setUserStyle(colQuotedFinishDate.getStyleName(), colQuotedFinishDate);
          reportDesc.setUserStyle(colAssetName.getStyleName(), colAssetName);
          reportDesc.setUserStyle(colAssetModel.getStyleName(), colAssetModel);
          reportDesc.setUserStyle(colAssetSerialNumber.getStyleName(), colAssetSerialNumber);
          reportDesc.setUserStyle(colAssetTotalPrice.getStyleName(), colAssetTotalPrice);

          HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
      return styleDesc;
  }
  
    protected GenericReportColumnViewVector getReportTitle(String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      
      title.add(ReportingUtils.createGenericReportColumnView("Report Name: " + pTitle, ReportingUtils.PAGE_TITLE, null, "10"));

      if (Utility.isSet((String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ACTUAL)) &&
          Utility.isSet((String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ACTUAL))) {
           
           title.add(ReportingUtils.createGenericReportColumnView("Date period selected (actual): " + 
            (String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ACTUAL) + 
            " - " + 
            (String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ACTUAL), ReportingUtils.PAGE_TITLE, null, "10"));
      } else {
            title.add(ReportingUtils.createGenericReportColumnView("Date period selected (actual): ()", ReportingUtils.PAGE_TITLE, null, "10"));
      }
      if (Utility.isSet((String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ESTIMATE)) &&
          Utility.isSet((String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ESTIMATE))) {
          
            title.add(ReportingUtils.createGenericReportColumnView("Date period selected (estimate): " + 
            (String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ESTIMATE) + 
            " - " + 
            (String) getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ESTIMATE), ReportingUtils.PAGE_TITLE, null, "10"));
      } else {
            title.add(ReportingUtils.createGenericReportColumnView("Date period selected (estimate): ()", ReportingUtils.PAGE_TITLE, null, "10"));
      }
        return title;
    }
    
    private GenericReportColumnViewVector getWOTSpecificReportHeader(String columnName) {
        
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Work Order Number",0,0,null,WOTS_CW[0],false,null,"WORK_ORDER_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Number",0,0,null,WOTS_CW[1],false,null,"PO_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Add Date",0,0,null,WOTS_CW[2],false,null,"ADD_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Add Time",0,0,null,WOTS_CW[3],false,null,"ADD_TIME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,0,null,WOTS_CW[4],false,null,"ACCOUNT_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,0,null,WOTS_CW[5],false,null,"SITE_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Requested Service",0,0,null,WOTS_CW[6],false,null,"REQUESTED_SERVICE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action",0,0,null,WOTS_CW[7],false,null,"ACTION",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Type",0,0,null,WOTS_CW[8],false,null,"SERVICE_TYPE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Priority",0,0,null,WOTS_CW[9],false,null,"PRIORITY",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Status",0,0,null,WOTS_CW[10],false,null,"STATUS",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Closed or Open?",0,0,null,WOTS_CW[11],false,null,"PO_CLOSED_OPEN",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Provider",0,0,null,WOTS_CW[12],false,null,"SERVICE_PROVIDER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Actual Start Date",0,0,null,WOTS_CW[13],false,null,"ACTUAL_START_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Actual Finish Date",0,0,null,WOTS_CW[14],false,null,"ACTUAL_FINISH_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Quoted Start Date",0,0,null,WOTS_CW[15],false,null,"QUOTED_START_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Quoted Finish Date",0,0,null,WOTS_CW[16],false,null,"QUOTED_FINISH_DATE",null)); 
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Name",0,0,null,WOTS_CW[17],false,null,"ASSET_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Model",0,0,null,WOTS_CW[18],false,null,"ASSET_MODEL",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Serial Number",0,0,null,WOTS_CW[19],false,null,"ASSET_SERIAL_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",columnName,0,0,null,WOTS_CW[20],false,null,"TOTAL",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","User Name",0,0,null,WOTS_CW[19],false,null,"USER_NAME",null));
        
        return header;
    }
    
    protected HashMap createReportStyleWOTSpecificDescriptor() {
          GenericReportStyleView colWorkOrderNumber = new GenericReportStyleView("WORK_ORDER_NUMBER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[0],0);
          GenericReportStyleView colPONumber = new GenericReportStyleView("PO_NUMBER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[1],0);
          GenericReportStyleView colAddDate = new GenericReportStyleView("ADD_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[2],0);
          GenericReportStyleView colAddTime = new GenericReportStyleView("ADD_TIME",ReportingUtils.DATA_CATEGORY.TIME,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[3],0);
          GenericReportStyleView colAccountName = new GenericReportStyleView("ACCOUNT_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[4],0);
          GenericReportStyleView colSiteName = new GenericReportStyleView("SITE_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[5],0);
          GenericReportStyleView colRequestedService = new GenericReportStyleView("REQUESTED_SERVICE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[6],0);
          GenericReportStyleView colAction = new GenericReportStyleView("ACTION","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOTS_CW[7],0);
          GenericReportStyleView colServiceType = new GenericReportStyleView("SERVICE_TYPE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[8],0);
          GenericReportStyleView colPriority = new GenericReportStyleView("PRIORITY",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[9],0);
          GenericReportStyleView colStatus = new GenericReportStyleView("STATUS",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[10],0);
          GenericReportStyleView colPOClosedOpen = new GenericReportStyleView("PO_CLOSED_OPEN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[11],0);
          GenericReportStyleView colServiceProvider = new GenericReportStyleView("SERVICE_PROVIDER",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[12],0);
          GenericReportStyleView colActualStartDate = new GenericReportStyleView("ACTUAL_START_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[13],0);
          GenericReportStyleView colActualFinishDate = new GenericReportStyleView("ACTUAL_FINISH_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[14],0);
          GenericReportStyleView colQuotedStartDate = new GenericReportStyleView("QUOTED_START_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[15],0);
          GenericReportStyleView colQuotedFinishDate = new GenericReportStyleView("QUOTED_FINISH_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[16],0);
          GenericReportStyleView colAssetName = new GenericReportStyleView("ASSET_NAME",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[17],0);
          GenericReportStyleView colAssetModel = new GenericReportStyleView("ASSET_MODEL",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[18],0);      
          GenericReportStyleView colAssetSerialNumber = new GenericReportStyleView("ASSET_SERIAL_NUMBER",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[19],0);
          GenericReportStyleView colTotal = new GenericReportStyleView("TOTAL",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[20],0);
          GenericReportStyleView colUserName = new GenericReportStyleView("USER_NAME",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOTS_CW[21],0);

          GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
          reportDesc.setUserStyle(colWorkOrderNumber.getStyleName(), colWorkOrderNumber);
          reportDesc.setUserStyle(colPONumber.getStyleName(), colPONumber);
          reportDesc.setUserStyle(colAddDate.getStyleName(), colAddDate);
          reportDesc.setUserStyle(colAddTime.getStyleName(), colAddTime);
          reportDesc.setUserStyle(colAccountName.getStyleName(), colAccountName);
          reportDesc.setUserStyle(colSiteName.getStyleName(), colSiteName);
          reportDesc.setUserStyle(colRequestedService.getStyleName(), colRequestedService);
          reportDesc.setUserStyle(colAction.getStyleName(), colAction);
          reportDesc.setUserStyle(colServiceType.getStyleName(), colServiceType);
          reportDesc.setUserStyle(colPriority.getStyleName(), colPriority);
          reportDesc.setUserStyle(colStatus.getStyleName(), colStatus);
          reportDesc.setUserStyle(colPOClosedOpen.getStyleName(), colPOClosedOpen);
          reportDesc.setUserStyle(colServiceProvider.getStyleName(), colServiceProvider);
          reportDesc.setUserStyle(colActualStartDate.getStyleName(), colActualStartDate);
          reportDesc.setUserStyle(colActualFinishDate.getStyleName(), colActualFinishDate);
          reportDesc.setUserStyle(colQuotedStartDate.getStyleName(), colQuotedStartDate);
          reportDesc.setUserStyle(colQuotedFinishDate.getStyleName(), colQuotedFinishDate);
          reportDesc.setUserStyle(colAssetName.getStyleName(), colAssetName);
          reportDesc.setUserStyle(colAssetModel.getStyleName(), colAssetModel);
          reportDesc.setUserStyle(colAssetSerialNumber.getStyleName(), colAssetSerialNumber);
          reportDesc.setUserStyle(colTotal.getStyleName(), colTotal);
          reportDesc.setUserStyle(colUserName.getStyleName(), colUserName);

          HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
      return styleDesc;
  }
    
    private GenericReportColumnViewVector getWODReportHeader() {
        
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Work Order Number",0,0,null,WOD_CW[0],false,null,"WORK_ORDER_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Number",0,0,null,WOD_CW[1],false,null,"PO_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Add Date",0,0,null,WOD_CW[2],false,null,"ADD_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Add Time",0,0,null,WOD_CW[3],false,null,"ADD_TIME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,0,null,WOD_CW[4],false,null,"ACCOUNT_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,0,null,WOD_CW[5],false,null,"SITE_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Type",0,0,null,WOD_CW[6],false,null,"SERVICE_TYPE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Requested Service",0,0,null,WOD_CW[7],false,null,"REQUESTED_SERVICE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action",0,0,null,WOD_CW[8],false,null,"ACTION",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Priority",0,0,null,WOD_CW[9],false,null,"PRIORITY",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Status",0,0,null,WOD_CW[10],false,null,"STATUS",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Closed or Open?",0,0,null,WOD_CW[11],false,null,"PO_CLOSED_OPEN",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Provider",0,0,null,WOD_CW[12],false,null,"SERVICE_PROVIDER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Actual Start Date",0,0,null,WOD_CW[13],false,null,"ACTUAL_START_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Actual Finish Date",0,0,null,WOD_CW[14],false,null,"ACTUAL_FINISH_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Quoted Start Date",0,0,null,WOD_CW[15],false,null,"QUOTED_START_DATE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Quoted Finish Date",0,0,null,WOD_CW[16],false,null,"QUOTED_FINISH_DATE",null)); 
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Part Num",0,0,null,WOD_CW[17],false,null,"PART_NUM",null));	
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Description",0,0,null,WOD_CW[18],false,null,"DESCRIPTION",null));	
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Quantity",0,0,null,WOD_CW[19],false,null,"QANTITY",null));	
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Price Each",0,0,null,WOD_CW[20],false,null,"PRICE_EACH",null));	
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Parts Price, Extended",0,0,null,WOD_CW[21],false,null,"PRICE_EACH_EXT",null));	
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Labor",0,0,null,WOD_CW[22],false,null,"LABOR",null));		
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Travel",0,0,null,WOD_CW[23],false,null,"TRAVEL",null));		
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Total Price",0,0,null,WOD_CW[24],false,null,"TOTAL_PRICE",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Name",0,0,null,WOD_CW[25],false,null,"ASSET_NAME",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Model",0,0,null,WOD_CW[26],false,null,"ASSET_MODEL",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Asset Serial Number",0,0,null,WOD_CW[27],false,null,"ASSET_SERIAL_NUMBER",null));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","User Name",0,0,null,WOD_CW[28],false,null,"USER_NAME",null));
        
        return header;
    }
    
    protected HashMap createReportStyleWODDescriptor() {
          GenericReportStyleView colWorkOrderNumber = new GenericReportStyleView("WORK_ORDER_NUMBER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[0],0);
          GenericReportStyleView colPONumber = new GenericReportStyleView("PO_NUMBER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[1],0);
          GenericReportStyleView colAddDate = new GenericReportStyleView("ADD_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[2],0);
          GenericReportStyleView colAddTime = new GenericReportStyleView("ADD_TIME",ReportingUtils.DATA_CATEGORY.TIME,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[3],0);
          GenericReportStyleView colAccountName = new GenericReportStyleView("ACCOUNT_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[4],0);
          GenericReportStyleView colSiteName = new GenericReportStyleView("SITE_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[5],0);
          GenericReportStyleView colServiceType = new GenericReportStyleView("SERVICE_TYPE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[6],0);
          GenericReportStyleView colRequestedService = new GenericReportStyleView("REQUESTED_SERVICE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[7],0);
          GenericReportStyleView colAction = new GenericReportStyleView("ACTION","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[8],0);
          GenericReportStyleView colPriority = new GenericReportStyleView("PRIORITY",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[9],0);
          GenericReportStyleView colStatus = new GenericReportStyleView("STATUS",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[10],0);
          GenericReportStyleView colPOClosedOpen = new GenericReportStyleView("PO_CLOSED_OPEN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[11],0);
          GenericReportStyleView colServiceProvider = new GenericReportStyleView("SERVICE_PROVIDER",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[12],0);
          GenericReportStyleView colActualStartDate = new GenericReportStyleView("ACTUAL_START_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[13],0);
          GenericReportStyleView colActualFinishDate = new GenericReportStyleView("ACTUAL_FINISH_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[14],0);
          GenericReportStyleView colQuotedStartDate = new GenericReportStyleView("QUOTED_START_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[15],0);
          GenericReportStyleView colQuotedFinishDate = new GenericReportStyleView("QUOTED_FINISH_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[16],0);
          GenericReportStyleView colPartNum = new GenericReportStyleView("PART_NUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[17],0);
          GenericReportStyleView colDescription = new GenericReportStyleView("DESCRIPTION","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,WOD_CW[18],0);
          GenericReportStyleView colQuantity = new GenericReportStyleView("QUANTITY",ReportingUtils.DATA_CATEGORY.INTEGER,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[19],0);
          GenericReportStyleView colPriceEach = new GenericReportStyleView("PRICE_EACH",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[20],0);
          GenericReportStyleView colPriceEachExt = new GenericReportStyleView("PRICE_EACH_EXT",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[21],0);
          GenericReportStyleView colLabor = new GenericReportStyleView("LABOR",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[22],0);
          GenericReportStyleView colTavel = new GenericReportStyleView("TRAVEL",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[23],0);
          GenericReportStyleView colTotalPrice = new GenericReportStyleView("TOTAL_PRICE",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[24],0);
          GenericReportStyleView colAssetName = new GenericReportStyleView("ASSET_NAME",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[25],0);
          GenericReportStyleView colAssetModel = new GenericReportStyleView("ASSET_MODEL",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[26],0);      
          GenericReportStyleView colAssetSerialNumber = new GenericReportStyleView("ASSET_SERIAL_NUMBER",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[27],0);
          GenericReportStyleView colUserName = new GenericReportStyleView("USER_NAME",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,WOD_CW[28],0);

          GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
          reportDesc.setUserStyle(colWorkOrderNumber.getStyleName(), colWorkOrderNumber);
          reportDesc.setUserStyle(colPONumber.getStyleName(), colPONumber);
          reportDesc.setUserStyle(colAddDate.getStyleName(), colAddDate);
          reportDesc.setUserStyle(colAddTime.getStyleName(), colAddTime);
          reportDesc.setUserStyle(colAccountName.getStyleName(), colAccountName);
          reportDesc.setUserStyle(colSiteName.getStyleName(), colSiteName);
          reportDesc.setUserStyle(colServiceType.getStyleName(), colServiceType);
          reportDesc.setUserStyle(colRequestedService.getStyleName(), colRequestedService);
          reportDesc.setUserStyle(colAction.getStyleName(), colAction);
          reportDesc.setUserStyle(colPriority.getStyleName(), colPriority);
          reportDesc.setUserStyle(colStatus.getStyleName(), colStatus);
          reportDesc.setUserStyle(colPOClosedOpen.getStyleName(), colPOClosedOpen);
          reportDesc.setUserStyle(colServiceProvider.getStyleName(), colServiceProvider);
          reportDesc.setUserStyle(colActualStartDate.getStyleName(), colActualStartDate);
          reportDesc.setUserStyle(colActualFinishDate.getStyleName(), colActualFinishDate);
          reportDesc.setUserStyle(colQuotedStartDate.getStyleName(), colQuotedStartDate);
          reportDesc.setUserStyle(colQuotedFinishDate.getStyleName(), colQuotedFinishDate);
          reportDesc.setUserStyle(colPartNum.getStyleName(), colPartNum);
          reportDesc.setUserStyle(colDescription.getStyleName(), colDescription);
          reportDesc.setUserStyle(colQuantity.getStyleName(), colQuantity);
          reportDesc.setUserStyle(colPriceEach.getStyleName(), colPriceEach);
          reportDesc.setUserStyle(colPriceEachExt.getStyleName(), colPriceEachExt);
          reportDesc.setUserStyle(colLabor.getStyleName(), colLabor);
          reportDesc.setUserStyle(colTavel.getStyleName(), colTavel);
          reportDesc.setUserStyle(colTotalPrice.getStyleName(), colTotalPrice);
          reportDesc.setUserStyle(colAssetName.getStyleName(), colAssetName);
          reportDesc.setUserStyle(colAssetModel.getStyleName(), colAssetModel);
          reportDesc.setUserStyle(colAssetSerialNumber.getStyleName(), colAssetSerialNumber);
          reportDesc.setUserStyle(colUserName.getStyleName(), colUserName);

          HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
      return styleDesc;
    }
    
    private Object getParamValue(Map pParams, String name) {
        if (pParams.containsKey(name)) {
            return pParams.get(name);
        } else if (pParams.containsKey(name + "_OPT")) {
            return pParams.get(name + "_OPT");
        } else {
            return null;
        }
    }



    private class WorkOrderTotals {
        String workOrderNum;
        String poNum;
        Date addDate;
        String addTime;
        String acctName;
        String siteName;
        String requestedService;
        String action;
        String type;
        String priority;
        String status;
        String poClosedOrOpen;
        int providerId;
        String serviceProvider;
        Date actualStartDate;
        Date actualFinishDate;
        Date quotedStartDate;
        Date quotedFinishDate;
        int assetId;
        String assetName;
        String assetModel;
        String assetSerialNumber;
        BigDecimal actualTotalCost;
        
    }
    private class WorkOrderSpecTotals {
        String workOrderNum;
        String poNum;
        Date addDate;
        String addTime;
        String acctName;
        String siteName;
        String serviceType;
        String requestedService;
        String action;
        String priority;
        String status;
        String poClosedOrOpen;
        int providerId;
        String serviceProvider;
        Date actualStartDate;
        Date actualFinishDate;
        Date quotedStartDate;
        Date quotedFinishDate;
        int assetId;
        String assetName;
        String assetModel;
        String assetSerialNumber;
        BigDecimal cost;
        String userName;
    }
    
    private class WorkOrderDetails {
        String workOrderNum;
        String poNum;
        Date addDate;
        String addTime;
        String acctName;
        String siteName;
        String serviceType;
        String requestedService;
        String action;
        String priority;
        String status;
        String poClosedOrOpen;
        int providerId;
        String serviceProvider;
        Date actualStartDate;
        Date actualFinishDate;
        Date quotedStartDate;
        Date quotedFinishDate;
        
        String partNum;
        String partDesc;
        int quantity;
        BigDecimal priceEach;
        BigDecimal partsPriceExtended;
        BigDecimal labor;
        BigDecimal travel;
        BigDecimal totalCost;
        
        int assetId;
        String assetName;
        String assetModel;
        String assetSerialNumber;
        String userName;
    }

    public class GenericReportUserStyleDesc extends ValueObject {
       HashMap mReportUserStyle;

       public GenericReportUserStyleDesc () {
         mReportUserStyle = new HashMap();
       }
       
       public void setUserStyle ( String pUserStyleType, GenericReportStyleView pPageTitleStyle) {
            if (mReportUserStyle == null){
                mReportUserStyle = new HashMap();
            }
            mReportUserStyle.put(pUserStyleType, pPageTitleStyle);
       }
   
        public GenericReportStyleView getUserStyle(String pUserStyleType){
            return ((mReportUserStyle != null) ? (GenericReportStyleView)mReportUserStyle.get(pUserStyleType) : null);
        }
   
        public HashMap getGenericReportUserStyleDesc(){
            return mReportUserStyle;
        }

       public void setGenericReportUserStyleDesc (HashMap pReportUserStyle){
         mReportUserStyle = pReportUserStyle;
       }
  }
    
}
