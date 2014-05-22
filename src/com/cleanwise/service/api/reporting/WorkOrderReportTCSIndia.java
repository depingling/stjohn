package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import com.cleanwise.service.api.session.Service;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.GenericReportControlFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;


/**
 */
public class WorkOrderReportTCSIndia implements GenericReportMulti {
	private static final Logger log = Logger.getLogger(WorkOrderReportTCSIndia.class);

    private static String className = "WorkOrderReportTCSIndia";
	private boolean isDisplayDistAcctRefNum;
	private boolean isDisplayDistSiteRefNum;

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        log("process => begin");
        log("process => params: " + pParams);
        Connection conn = pCons.getMainConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();


        APIAccess factory = new APIAccess();
        WorkOrder wrkOrderEjb = factory.getWorkOrderAPI();
        Service serviceEjb = factory.getServiceAPI();
        PropertyService propertyServiceEjb = factory.getPropertyServiceAPI();
        WorkOrderSimpleSearchCriteria criteria = new WorkOrderSimpleSearchCriteria();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Object woSearchType = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_SEARCH_TYPE);
        Object requestedService = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.REQUESTED_SERVICE);
        Object woNumber = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_NUMBER);
        Object woDistAcctRefNum = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_ACCT_REF_NUM);
        Object woDistSiteRefNum = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_SITE_REF_NUM);
        Object serviceProvider = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.SERVICE_PROVIDER);
        Object woStatus = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_STATUS);
        Object woType = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_TYPE);
        Object woPriority = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_PRIORITY);
        //Object woDateType = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DATE_TYPE);
        Object woBegDateActual = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ACTUAL);
        Object woEndDateActual = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ACTUAL);
        Object woBegDateEstimate = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.BEG_DATE_ESTIMATE);
        Object woEndDateEstimate = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.END_DATE_ESTIMATE);
        Object woReceivedDateFrom = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.RECEIVED_DATE_FROM);
        Object woReceivedDateTo = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.RECEIVED_DATE_TO);


        Object woAssetModel = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.ASSET_MODEL);
        Object woSerialNum = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.SERIAL_NUMBER);

//        Object storeIdStr = getParamValue(pParams, "STORE");
        Object storeIdStr = getParamValue(pParams, "STORE_SELECT");
        //String userIdStr = (String)getParamValue(pParams, "USER_ID");
        String userIdStr = (String)getParamValue(pParams, "CUSTOMER");
        String userType = (String)getParamValue(pParams, "USER_TYPE");
        String userWOadmin = (String)getParamValue(pParams, "USER_WO_ADMIN");

        String accountFilter = (String)ReportingUtils.getParam(pParams,"LOCATE_ACCOUNT_MULTI_OPT");
        String siteFilter = (String)ReportingUtils.getParam(pParams,"LOCATE_SITE_MULTI_OPT");

        String daysOpenStr = (String)ReportingUtils.getParam(pParams,GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.DAYS_OPEN);
        int daysOpen = -1;
        if (Utility.isSet(daysOpenStr)) {
          try {
            daysOpen = Integer.parseInt(daysOpenStr);
            if (daysOpen < 0){
              throw new RemoteException("^clwKey^report.text.daysOpenParameterInvalid^clwKey^");
            }
          }
          catch (Exception e) {
            throw new RemoteException("^clwKey^report.text.daysOpenParameterInvalid^clwKey^");
          }
        }
        boolean isAuthorizedAdmin = false;
        if (userWOadmin != null) {
            isAuthorizedAdmin = Boolean.parseBoolean(userWOadmin);
        }
        int userId = 0;
        if (userIdStr != null) {
            userId = Integer.parseInt(userIdStr);
        }

/*
        if (Utility.isSet((String) woEndDate)) {
            try {
                Date date = df.parse((String) woEndDate);
                if (Utility.isSet((String) woDateType)) {
                    if (RefCodeNames.WORK_ORDER_DATE_TYPE.ACTUAL.equals(woDateType)) {
                        criteria.setActualFinishDate(date);
                    } else if (RefCodeNames.WORK_ORDER_DATE_TYPE.ESTIMATE.equals(woDateType)) {
                        criteria.setEstimateFinishDate(date);
                    } else { // default
                        criteria.setActualFinishDate(date);
                    }
                }
            } catch (ParseException e) {
                error(e.getMessage(), e);
                throw new Exception(e.getMessage());
            }
        }
*/

        
        String datePattern = (String)getParamValue(pParams, "DATE_FMT");
        if (!Utility.isSet(datePattern))
        	datePattern = Constants.SIMPLE_DATE_PATTERN;

        String begDateActualCond = "";
        if(Utility.isSet((String) woBegDateActual)) {
            begDateActualCond =
            " AND wo.actual_start_date >= To_Date ('"+((String) woBegDateActual).trim()+"','" + datePattern + "') \n\r";
        }
        String endDateActualCond = "";
        if(Utility.isSet((String) woEndDateActual)) {
            endDateActualCond =
            " AND wo.actual_finish_date <= To_Date ('"+((String) woEndDateActual).trim()+"','" + datePattern + "') \n\r";
        }

        String begDateEstimateCond = "";
        if(Utility.isSet((String) woBegDateEstimate)) {
            begDateEstimateCond =
            " AND wo.estimate_start_date >= To_Date ('"+((String) woBegDateEstimate).trim()+"','" + datePattern + "') \n\r";
        }
        String endDateEstimateCond = "";
        if(Utility.isSet((String) woEndDateEstimate)) {
            endDateEstimateCond =
            " AND wo.estimate_finish_date <= To_Date ('"+((String) woEndDateEstimate).trim()+"','" + datePattern + "') \n\r";
        }

        String receivedDateFromCond = "";
        if(Utility.isSet((String) woReceivedDateFrom)) {
        	receivedDateFromCond =
            " AND wo.RECEIVED_DATE >= To_Date ('"+((String) woReceivedDateFrom).trim()+"','" + datePattern + "') \n\r";
        }
        String receivedDateToCond  = "";
        if(Utility.isSet((String) woReceivedDateTo)) {
        	receivedDateToCond =
            " AND wo.RECEIVED_DATE <= To_Date ('"+((String) woReceivedDateTo).trim()+"','" + datePattern + "') \n\r";
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

        String filterCondition = "";
        if (userId > 0) {
            if (userType == null || userType.length() <=0 ) {
                UserData userD = UserDataAccess.select(conn, userId);
                userType = userD.getUserTypeCd();
            }
            if (isAuthorizedAdmin) {
                filterCondition = "";
            } else if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userType) ||
                       RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userType)) {
                filterCondition = " JOIN clw_user_assoc ua \n\r" +
                                      "ON  ua.user_id = " + userId + " \n\r" +
                                      "AND ua.user_assoc_cd = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "' \n\r" +
                                  " JOIN clw_bus_entity_assoc bea_site_account \n\r" +
                                      "ON  bea_site_account.bus_entity1_id = wo.bus_entity_id \n\r" +
                                      "AND ua.bus_entity_id = bea_site_account.bus_entity2_id \n\r" +
                                      "AND bea_site_account.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n\r";

            } else {
                filterCondition = " JOIN clw_user_assoc ua \n\r" +
                                        "ON  ua.bus_entity_id = wo.bus_entity_id \n\r" +
                                        "AND ua.user_id = " + userId + " \n\r" +
                                        "AND ua.user_assoc_cd = '" + RefCodeNames.USER_ASSOC_CD.SITE + "' \n\r";
            }
        }

        int storeId = (storeIdStr != null && storeIdStr instanceof String ? Integer.parseInt((String)storeIdStr) : 0);
        // check child stores
        String storeFilter = "=" + storeId;
log.info("********** userType= " + userType);
        if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userType) ||
            RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) ||
            RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType) ||
            RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType) ||
            RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userType)) {
          if (storeId > 0) {
            boolean isParentStore;
            try {
              isParentStore = Boolean.parseBoolean(propertyServiceEjb.
                  getBusEntityProperty(storeId,
                                       RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE));
            }
            catch (Exception e) {
              isParentStore = false;
            }
            if (isParentStore) {
              Store storeEjb = factory.getStoreAPI();
              BusEntityDataVector childStores = storeEjb.getChildStores(storeId);
              if (childStores.size() > 0) {
                StringBuffer sb = new StringBuffer(" in (").append(storeId);
                Iterator i = childStores.iterator();
                while (i.hasNext()) {
                  sb.append(",");
                  sb.append( ( (BusEntityData) i.next()).getBusEntityId());
                }
                sb.append(") ");
                storeFilter = sb.toString();
              }
            }
          }
        }
        try {
            isDisplayDistAcctRefNum = Boolean.parseBoolean(propertyServiceEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM));
            isDisplayDistSiteRefNum = Boolean.parseBoolean(propertyServiceEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM));
        } catch (Exception e) {
            isDisplayDistAcctRefNum = false;
            isDisplayDistSiteRefNum = false;
        }
        final String distAcctRefNumColumnAlias = "dist_acct_ref_num";
		String distAcctRefNumColumn = "acct_prop.clw_value AS " + distAcctRefNumColumnAlias;
        final String distSiteRefNumColumnAlias = "dist_site_ref_num";
		String distSiteRefNumColumn = "site_prop.clw_value AS " + distSiteRefNumColumnAlias;

        String propertyJoins = "";
        if(isDisplayDistAcctRefNum)
        {
        	propertyJoins += " LEFT JOIN clw_property acct_prop \n\r" +
              "ON acct_prop.bus_entity_id = woacct.bus_entity_id \n\r" +
                 "AND acct_prop.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "' \n\r";
        }
        if(isDisplayDistSiteRefNum)
        {
        	propertyJoins += " LEFT JOIN clw_property site_prop \n\r" +
              "ON site_prop.bus_entity_id = wosite.bus_entity_id \n\r" +
                 "AND site_prop.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "' \n\r";
        }

        String workOrderDistAcctRefNumCond = "";
        if(isDisplayDistAcctRefNum && Utility.isSet((String) woDistAcctRefNum)) {
        	workOrderDistAcctRefNumCond =
        		"AND wo." + WorkOrderDataAccess.BUS_ENTITY_ID + " IN (" +
            			String.format(WorkOrderUtil.SUB_QUERY_FOR_SITES_WITH_DIST_ACCT_REF_NUM,
            					new Object[] {woDistAcctRefNum}) +	")";
        }
        String workOrderDistSiteRefNumCond = "";
        if(isDisplayDistSiteRefNum && Utility.isSet((String) woDistSiteRefNum)) {
        	workOrderDistSiteRefNumCond =
        		"AND wo." + WorkOrderDataAccess.BUS_ENTITY_ID + " IN (" +
            			String.format(WorkOrderUtil.SUB_QUERY_FOR_SITES_WITH_DIST_SITE_REF_NUM,
            					new Object[] {woDistSiteRefNum}) +	")";
        }

        String webOrderTotalsLeftJoin =
        	" LEFT JOIN " +
			"(SELECT   clw_work_order_item.work_order_id, " +
				"clw_order.order_id, " +
				"clw_order.order_num, " +

				"nvl(clw_order.total_price,0) " +
				    "+ nvl(clw_order.total_freight_cost,0) " +
				    "+ nvl(clw_order.total_misc_cost,0) " +
				    "+ nvl(clw_order.TOTAL_RUSH_CHARGE,0) " +
				    "+ nvl(clw_order.total_tax_cost,0) " +
				    "+ nvl(meta.discount,0) " +
				    "+ nvl(meta.small_order_fee,0) " +
				    "+ nvl(meta.fuel_surcharge,0) as web_order_total " +

			"FROM  clw_work_order_item, " +
            	"clw_order_assoc, " +
            	"clw_order, " +
            	"( " +
                	"SELECT   order_id, " +
	                    "MAX (CASE WHEN name = 'DISCOUNT' THEN clw_value ELSE NULL END) AS discount, " +
	                    "MAX (CASE WHEN name = 'SMALL_ORDER_FEE' THEN clw_value ELSE NULL END) AS small_order_fee, " +
	                    "MAX (CASE WHEN name = 'FUEL_SURCHARGE' THEN clw_value ELSE NULL END) AS fuel_surcharge " +
                    "FROM   clw_order_meta " +
                    "GROUP BY   order_id " +
                ") meta " +
            "WHERE   (clw_work_order_item.work_order_item_id = clw_order_assoc.work_order_item_id) " +
	            "AND (clw_order.order_id = clw_order_assoc.order2_id) " +
	            "AND (clw_order.order_id = meta.order_id(+)) " +
            ") web_order " +
            "ON wo.work_order_id = web_order.work_order_id";


        // account filter
        if (Utility.isSet(accountFilter)) {
            accountFilter = "AND woacct.bus_entity_id in ( " + accountFilter + ") ";
        } else {
            accountFilter = "";
        }

        // site filter
        if (Utility.isSet(siteFilter)) {
            siteFilter = "AND wosite.bus_entity_id in ( " + siteFilter + ") ";
        } else {
            siteFilter = "";
        }

        // days open condition
/*        String daysOpenJoins =
            " LEFT JOIN \n" +
            " (SELECT * FROM\n" +
            "   (select work_order_id, max(days_open) days_open  from \n" +
            "     (select wh.work_order_id, wh.status_cd,\n" +
            "      (case\n" +
            "       when wh.status_cd in ('" + RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED+ "'," +
                                         "'" + RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED+ "') \n" +
            "       then round(max(wh.status_date) - min(wh.status_date)) \n" +
            "       else round(sysdate - min(wh.status_date))\n" +
            "       end ) as days_open \n" +
            "    from clw_work_order_status_hist wh \n" +
            "    where wh.status_cd in ('" + RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST + "'," +
                                       "'" + RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER + "'," +
                                       "'" + RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL + "'," +
                                       "'" + RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER + "'," +
                                       "'" + RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER + "'," +
                                       "'" + RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED + "') \n" +
            "    group by wh.work_order_id , wh.status_cd )\n" +
            "  group by work_order_id )\n" +
            " )\n" +
            " wsh \n" +
            " on wsh.work_order_id = wo.work_order_id \r\n";
*/
        String daysOpenJoins =
            " join \n" +
            " (select work_order_id, -(trunc(to_date(date1)) - trunc(nvl(date2,sysdate))) days_open \n"+
            "  from \n" +
            "   (select work_order_id, received_time date1, actual_finish_time date2  from clw_work_order) \n" +
            " ) wsh \n" +
            " on wsh.work_order_id = wo.work_order_id  \r\n";


 //           " ( SELECT * FROM\n" +
 //               " (select wh.work_order_id, round(max(wh.status_date) - min(wh.status_date)) as days_open \n" +
 //                   " from clw_work_order_status_hist wh \n\r" +
 //                   " where wh.status_cd in ('" + RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST + "'," +
 //                                           "'" + RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER + "'," +
 //                                           "'" + RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL + "'," +
 //                                           "'" + RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER + "'," +
 //                                           "'" + RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER + "'," +
 //                                           "'" + RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED + "') \n" +
 //                   "       group by wh.work_order_id \n" +
 //                   "       )\n" +
 //              " )\n" +
 //              " wsh \n" +
 //              " on wsh.work_order_id = wo.work_order_id \r\n";


        // days open filter
        String daysOpenFilter = "";
        String workOrderStatusFilter = "";
        if (daysOpen >= 0) {
            daysOpenFilter = " and wsh.days_open <= " + daysOpen + "\n";
            workOrderStatusFilter = " and wo.status_cd in ('" + RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST + "'," +
                                                   "'" + RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER + "'," +
                                                   "'" + RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL + "'," +
                                                   "'" + RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER + "'," +
                                                   "'" + RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER + "'," +
                                                   "'" + RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED + "') \n"  ;
        }
       String assetModelFilter= (Utility.isSet((String)woAssetModel)?"AND Lower(asset.model_number) LIKE '%"+((String)woAssetModel).trim().toLowerCase()+"%' \n\r":"");
       String serialNumFilter=  (Utility.isSet((String)woSerialNum)?"AND Lower(asset.serial_num) LIKE '%"+((String)woSerialNum).trim().toLowerCase()+"%' \n\r":"");
//================================================================================
// Work Order Detail



        String workOrderDetailReq =
        "SELECT \n\r"+
        " wo.WORK_ORDER_NUM, \n\r"+
        " wo.PO_NUMBER, \n\r"+
        " To_Date(To_Char(wo.add_date,'mm/dd/yyyy'),'mm/dd/yyyy') AS add_date, \n\r"+
//        " To_Char(wo.add_date,'hh:mi pm') AS add_time, \n\r"+
        " To_Char(wo.add_date,'HH24:mi') AS add_time, \n\r"+
        " woacct.short_desc AS account_name, \n\r"+

        (isDisplayDistAcctRefNum ? distAcctRefNumColumn + ", \n\r" : "")  +
        " wosite.short_desc AS site_name, \n\r"+
        (isDisplayDistSiteRefNum ? distSiteRefNumColumn + ", \n\r" : "")  +


        " wod.PAYMENT_TYPE_CD AS service_type, \n\r"+
        " wo.short_desc AS requested_service, \n\r"+
        " wo.action_cd AS action, \n\r"+
        " wo.type_cd AS TYPE, \n\r"+
        " wo.priority AS priority, \n\r"+
        " cc.short_desc AS cost_center, \n\r"+
        " wo.status_cd AS status, \n\r"+
        " (CASE WHEN wo.status_cd = 'Completed' \n\r"+
        "     OR wo.status_cd = 'Cancelled'  \n\r"+
        "     THEN 'Closed' ELSE 'Open' END) AS po_closed_or_open, \n\r"+
        " woa.bus_entity_id AS provider_id, \n\r"+
        " prov.short_desc AS service_provider, \n\r"+
        " wo.actual_start_date, \n\r"+
        " wo.actual_finish_date, \n\r"+
        " wo.estimate_start_date as Quoted_Start_Date, \n\r"+
        " wo.estimate_finish_date AS Quoted_Finish_Date, \n\r"+

        " wod.part_number AS Part_Num, \n\r"+
        " wod.short_desc AS Description, \n\r"+
        " wod.Quantity, \n\r"+
        " wod.part_price AS Price_Each, \n\r"+
        " wod.part_price * wod.quantity AS Parts_Price_Extended, \n\r"+
        " wod.Labor, \n\r"+
        " wod.Travel, \n\r"+
        " wod.part_price * wod.quantity + wod.labor + wod.travel AS Total_Price, \n\r"+

        " woasset.asset_id, \n\r"+
        " asset.short_desc AS asset_name, \n\r"+
        " asset.MODEL_NUMBER AS asset_model, \n\r"+
        " asset.SERIAL_NUM AS asset_serial_number, \n\r"+
        " wo.add_by AS user_name, \n\r" +
        " wsh.days_open \n\r" +

        " FROM clw_work_order wo \n\r"+
        " join clw_bus_entity wosite  \n\r"+
        "   ON wosite.bus_entity_id = wo.bus_entity_id \n\r"+
             siteFilter + "\n\r"+
        " join clw_bus_entity_assoc bea  \n\r"+
        "   ON wosite.bus_entity_id = bea.bus_entity1_id   \n\r"+
        "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n\r"+
        " join clw_bus_entity woacct  \n\r"+
        "   ON woacct.bus_entity_id = bea.bus_entity2_id \n\r"+
            accountFilter + "\n\r" +
        " join clw_bus_entity_assoc bea1  \n\r"+
        "   ON bea.bus_entity2_id = bea1.bus_entity1_id \n\r"+
        "   AND bea1.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"' \n\r"+
        "   AND bea1.bus_entity2_id " + storeFilter + " \n\r"+
        filterCondition +
        " left join clw_work_order_assoc woa  \n\r"+
        "   ON woa.work_order_id = wo.work_order_id  \n\r"+
        "   AND woa.work_order_assoc_cd = '"+RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER+"' \n\r"+
        " left join clw_bus_entity prov  \n\r"+
        "   ON prov.bus_entity_id = woa.bus_entity_id \n\r"+
        " left join clw_work_order_item woi  \n\r"+
        "   ON woi.work_order_id = wo.work_order_id \n\r"+
        " left join clw_work_order_asset woasset  \n\r"+
        "   ON woasset.work_order_item_id = woi.work_order_item_id \n\r"+
        " left join clw_asset asset  \n\r"+
        "   ON asset.asset_id = woasset.asset_id \n\r"+
        " left join clw_cost_center cc  \n\r"+
        "   ON cc.cost_center_id = wo.cost_center_id \n\r"+
        " left join clw_work_order_detail wod \n\r"+
        "   ON wod.work_order_id = wo.work_order_id AND wod.STATUS_CD = '" + RefCodeNames.STATUS_CD.ACTIVE + "' \n\r"+
        propertyJoins +
        daysOpenJoins +
        " WHERE 1=1 \n\r"+
        requestedServiceCond +
        workOrderNumCond +
        workOrderDistAcctRefNumCond +
        workOrderDistSiteRefNumCond +
        statusCond +
        typeCond +
        priorityCond +
        providerCond +
        begDateActualCond +
        endDateActualCond +
        begDateEstimateCond +
        endDateEstimateCond +
        receivedDateFromCond +
        receivedDateToCond +
        daysOpenFilter +
        workOrderStatusFilter +
        assetModelFilter +
        serialNumFilter +
//        " AND woacct.short_desc LIKE '%' \n\r"+
//        " AND wosite.short_desc LIKE '%' \n\r"+
//        " AND (CASE WHEN wo.status_cd = 'Completed' OR wo.status_cd = 'Closed' THEN 'Closed' \n\r"+
//        "           WHEN wo.status_cd = 'Cancelled' THEN 'Closed' ELSE 'Open' END) LIKE '%' \n\r"+
        " ORDER BY account_name, asset_name";




        PreparedStatement pstmt = conn.prepareStatement(workOrderDetailReq);

        log.info(getClass().getName() + " ------------> workOrderDetailReq: " + workOrderDetailReq);
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
            wod.serviceType = rs.getString("service_type");
            wod.requestedService = rs.getString("requested_service");
            wod.action = rs.getString("action");
            wod.type = rs.getString("TYPE");
            wod.priority = rs.getString("priority");
            wod.costCenter = rs.getString("cost_center");
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

//            wod.daysOpen = rs.getInt("days_open");
            wod.daysOpen = (rs.getString("days_open")!= null) ? Integer.valueOf(rs.getString("days_open")) : null;

            if (isDisplayDistAcctRefNum)
            {
            	wod.distAcctRefNum = rs.getString(distAcctRefNumColumnAlias);
            }
            if (isDisplayDistSiteRefNum)
            {
            	wod.distSiteRefNum = rs.getString(distSiteRefNumColumnAlias);
            }
        }
        rs.close();
        pstmt.close();


        GenericReportResultView result2 = GenericReportResultView.createValue();
        GenericReportColumnViewVector header2 = getWorkOrderDetailReportHeader();
        result2.setHeader(header2);
        result2.setColumnCount(header2.size());
        result2.setName("Work Order Details");
        ArrayList table2 = new ArrayList();
        result2.setTable(table2);
        result2.setFreezePositionColumn(1);
        result2.setFreezePositionRow(1);

        for(Iterator iter = ll.iterator(); iter.hasNext();) {
            WorkOrderDetails wod = (WorkOrderDetails) iter.next();
            ArrayList line = new ArrayList();
            table2.add(line);
            line.add(wod.workOrderNum);
            line.add(wod.poNum);
            line.add(wod.addDate);
            line.add(wod.addTime);
            line.add(wod.daysOpen);
            line.add(wod.acctName);
            if (isDisplayDistAcctRefNum)
            {
                line.add(wod.distAcctRefNum);
            }
            line.add(wod.siteName);
            if (isDisplayDistSiteRefNum)
            {
                line.add(wod.distSiteRefNum);
            }
            line.add(wod.serviceType);
            line.add(wod.requestedService);
            line.add(wod.action);
            line.add(wod.type);
            line.add(wod.priority);
            line.add(wod.costCenter);
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
            final boolean isNteFieldRequired = (ii != 0); // not "PM Totals"

            String workOrderSpecReq =
            "SELECT \n\r"+
            " wo.WORK_ORDER_NUM, \n\r"+
            " wo.PO_NUMBER, \n\r"+
            " To_Date(To_Char(wo.add_date,'mm/dd/yyyy'),'mm/dd/yyyy') AS add_date, \n\r"+
//            " To_Char(wo.add_date,'hh:mi pm') AS add_time, \n\r"+
            " To_Char(wo.add_date,'HH24:mi') AS add_time, \n\r"+
            " woacct.short_desc AS account_name, \n\r"+

            (isDisplayDistAcctRefNum ? distAcctRefNumColumn + ", \n\r" : "")  +
            " wosite.short_desc AS site_name, \n\r"+
            (isDisplayDistSiteRefNum ? distSiteRefNumColumn + ", \n\r" : "")  +

            "'"+costType+"' AS service_type, \n\r"+
            " wo.short_desc AS requested_service, \n\r"+
            " wo.action_cd AS action, \n\r"+
            " wo.type_cd AS TYPE, \n\r"+
            " wo.priority AS priority, \n\r"+
            " cc.short_desc AS cost_center, \n\r"+
            " wo.status_cd AS status, \n\r"+
            " (CASE WHEN wo.status_cd = 'Completed' \n\r"+
            "     OR wo.status_cd = 'Cancelled'  \n\r"+
            "     THEN 'Closed' ELSE 'Open' END) AS po_closed_or_open, \n\r"+
            " woa.bus_entity_id AS provider_id, \n\r"+
            " prov.short_desc AS service_provider, \n\r"+
            " actual_start_date, \n\r"+
            " actual_finish_date, \n\r"+
            " wo.estimate_start_date as Quoted_Start_Date, \n\r"+
            " estimate_finish_date AS Quoted_Finish_Date, \n\r"+
            " woasset.asset_id, \n\r"+
            " asset.short_desc AS asset_name, \n\r"+
            " asset.MODEL_NUMBER AS asset_model, \n\r"+
            " asset.SERIAL_NUM AS asset_serial_number, \n\r"+
            " (SELECT Sum(PART_PRICE*QUANTITY+labor+travel)  \n\r"+
            "    FROM clw_work_order_detail wod  \n\r"+
            "    WHERE wod.work_order_id = wo.work_order_id \n\r" +
            "      AND wod.STATUS_CD = '" + RefCodeNames.STATUS_CD.ACTIVE + "' \n\r" +
            "      AND wod.PAYMENT_TYPE_CD = '"+costType+"') cost, \n\r"+
            " wo.nte, \n\r" +
            " wo.add_by AS user_name, \n\r" +
            " wsh.days_open \n\r" +

            " FROM clw_work_order wo \n\r"+
            " join clw_bus_entity wosite  \n\r"+
            "   ON wosite.bus_entity_id = wo.bus_entity_id \n\r"+
                siteFilter + "\n\r" +
            " join clw_bus_entity_assoc bea  \n\r"+
            "   ON wosite.bus_entity_id = bea.bus_entity1_id   \n\r"+
            "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n\r"+
            " join clw_bus_entity woacct  \n\r"+
            "   ON woacct.bus_entity_id = bea.bus_entity2_id \n\r"+
                accountFilter + "\n\r" +
            " join clw_bus_entity_assoc bea1  \n\r"+
            "   ON bea.bus_entity2_id = bea1.bus_entity1_id \n\r"+
            "   AND bea1.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"' \n\r"+
            "   AND bea1.bus_entity2_id "+storeFilter + " \n\r"+
            filterCondition +
            " left join clw_work_order_assoc woa  \n\r"+
            "   ON woa.work_order_id = wo.work_order_id  \n\r"+
            "   AND woa.work_order_assoc_cd = '"+RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER+"' \n\r"+
            " left join clw_bus_entity prov  \n\r"+
            "   ON prov.bus_entity_id = woa.bus_entity_id \n\r"+
            " left join clw_work_order_item woi  \n\r"+
            "   ON woi.work_order_id = wo.work_order_id \n\r"+
            " left join clw_work_order_asset woasset  \n\r"+
            "   ON woasset.work_order_item_id = woi.work_order_item_id \n\r"+
            " left join clw_asset asset  \n\r"+
            "   ON asset.asset_id = woasset.asset_id \n\r"+
            " left join clw_cost_center cc  \n\r"+
            "   ON cc.cost_center_id = wo.cost_center_id \n\r"+
            propertyJoins +
            daysOpenJoins +
            " WHERE 1=1 \n\r"+
            requestedServiceCond +
            workOrderNumCond +
            workOrderDistAcctRefNumCond +
            workOrderDistSiteRefNumCond +
            statusCond +
            typeCond +
            priorityCond +
            providerCond +
            begDateActualCond +
            endDateActualCond +
            begDateEstimateCond +
            endDateEstimateCond +
            receivedDateFromCond +
            receivedDateToCond +
            daysOpenFilter +
            workOrderStatusFilter +
            assetModelFilter +
            serialNumFilter +
            " AND wo.work_order_id IN ( \n\r"+
            " SELECT work_order_id \n\r"+
            " FROM clw_work_order_detail \n\r"+
            "    WHERE PAYMENT_TYPE_CD = '"+costType+"' \n\r"+
            "      AND STATUS_CD = '" + RefCodeNames.STATUS_CD.ACTIVE + "' \n\r" +
            "      AND  PART_PRICE*QUANTITY+labor+travel > 0 \n\r"+
            " ) \n\r"+
    //        " AND woacct.short_desc LIKE '%' \n\r"+
    //        " AND wosite.short_desc LIKE '%' \n\r"+
    //        " AND (CASE WHEN wo.status_cd = 'Completed' OR wo.status_cd = 'Closed' THEN 'Closed' \n\r"+
    //        "           WHEN wo.status_cd = 'Cancelled' THEN 'Closed' ELSE 'Open' END) LIKE '%' \n\r"+
            " ORDER BY account_name, asset_name";




            pstmt = conn.prepareStatement(workOrderSpecReq);

            log.info(getClass().getName() + " ------------> workOrderSpecReq: " + workOrderSpecReq);
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
                wost.serviceType = rs.getString("service_type");
                wost.requestedService = rs.getString("requested_service");
                wost.action = rs.getString("action");
                wost.type = rs.getString("TYPE");
                wost.priority = rs.getString("priority");
                wost.costCenter = rs.getString("cost_center");
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
               	wost.nte = rs.getBigDecimal("nte");

                if (isDisplayDistAcctRefNum)
                {
                	wost.distAcctRefNum = rs.getString(distAcctRefNumColumnAlias);
                }
                if (isDisplayDistSiteRefNum)
                {
                	wost.distSiteRefNum = rs.getString(distSiteRefNumColumnAlias);
                }
//                wost.daysOpen = rs.getInt("days_open");
                wost.daysOpen = (rs.getString("days_open")!= null) ? Integer.valueOf(rs.getString("days_open")) : null;
            }

            rs.close();
            pstmt.close();


            GenericReportResultView result1 = GenericReportResultView.createValue();
			GenericReportColumnViewVector header1 = getWorkOrderTypeSpecificReportHeader(columnName, isNteFieldRequired);
            result1.setHeader(header1);
            result1.setColumnCount(header1.size());
            result1.setName(pageName);
            ArrayList table1 = new ArrayList();
            result1.setTable(table1);
            result1.setFreezePositionColumn(1);
            result1.setFreezePositionRow(1);

            for(Iterator iter = ll.iterator(); iter.hasNext();) {
                WorkOrderSpecTotals wost = (WorkOrderSpecTotals) iter.next();
                ArrayList line = new ArrayList();
                table1.add(line);
                line.add(wost.workOrderNum);
                line.add(wost.poNum);
                line.add(wost.addDate);
                line.add(wost.addTime);
                line.add(wost.daysOpen);
                line.add(wost.acctName);
                if (isDisplayDistAcctRefNum)
                {
                    line.add(wost.distAcctRefNum);
                }
                line.add(wost.siteName);
                if (isDisplayDistSiteRefNum)
                {
                    line.add(wost.distSiteRefNum);
                }
                line.add(wost.serviceType);
                line.add(wost.requestedService);
                line.add(wost.action);
                line.add(wost.type);
                line.add(wost.priority);
                line.add(wost.costCenter);
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
                if(isNteFieldRequired)
                {
                	line.add(wost.nte);
                }
            }
            resultV.add(result1);
        }
//===============================================================
        String workOrderTotalsReq =
        "SELECT \n\r"+
        " wo.WORK_ORDER_NUM, \n\r"+
        " wo.PO_NUMBER, \n\r"+
        " To_Date(To_Char(wo.add_date,'mm/dd/yyyy'),'mm/dd/yyyy') AS add_date, \n\r"+
        " To_Char(wo.add_date,'HH24:mi') AS add_time, \n\r"+
        " woacct.short_desc AS account_name, \n\r"+

        (isDisplayDistAcctRefNum ? distAcctRefNumColumn + ", \n\r" : "")  +
        " wosite.short_desc AS site_name, \n\r"+
        (isDisplayDistSiteRefNum ? distSiteRefNumColumn + ", \n\r" : "")  +

        " wo.short_desc AS requested_service, \n\r"+
        " wo.action_cd AS action, \n\r"+
        " wo.type_cd AS TYPE, \n\r"+
        " wo.priority AS priority, \n\r"+
        " cc.short_desc AS cost_center, \n\r"+
        " wo.status_cd AS status, \n\r"+
        " (CASE WHEN wo.status_cd = 'Completed' \n\r"+
        "     OR wo.status_cd = 'Cancelled'  \n\r"+
        "     THEN 'Closed' ELSE 'Open' END) AS po_closed_or_open, \n\r"+
        " woa.bus_entity_id AS provider_id, \n\r"+
        " prov.short_desc AS service_provider, \n\r"+
        " actual_start_date, \n\r"+
        " actual_finish_date, \n\r"+
        " wo.estimate_start_date as Quoted_Start_Date, \n\r"+
        " estimate_finish_date AS Quoted_Finish_Date, \n\r"+
        " woasset.asset_id, \n\r"+
        " asset.short_desc AS asset_name, \n\r"+
        " asset.MODEL_NUMBER AS asset_model, \n\r"+
        " asset.SERIAL_NUM AS asset_serial_number, \n\r"+
        " (SELECT Sum(PART_PRICE*QUANTITY+labor+travel)  \n\r"+
        "    FROM clw_work_order_detail wod  \n\r"+
        "    WHERE wod.work_order_id = wo.work_order_id" +
        "      AND wod.STATUS_CD = '" + RefCodeNames.STATUS_CD.ACTIVE + "') actual_total_cost, \n\r"+
        " web_order.order_num as web_order_num, \n\r" +
        " web_order.web_order_total, \n\r" +
        " wo.nte, \n\r" +
        " wsh.days_open, \n\r" +

        " To_Date(To_Char(wo.received_time,'mm/dd/yyyy'),'mm/dd/yyyy') AS received_date, \n\r"+
        " wo.received_time, \n\r"+
        " wo.actual_start_time, \n\r"+
        " wo.actual_finish_time, \n\r"+
        " wo.estimate_start_time AS quoted_start_time, \n\r"+
        " wo.estimate_finish_time AS quoted_finish_time \n\r"+

//         " To_Char(wo.received_time,'HH:mi') AS received_time, \n\r"+
//         " To_Char(wo.actual_start_time,'HH:mi') AS actual_start_time, \n\r"+
//         " To_Char(wo.actual_finish_time,'HH:mi') AS actual_finish_time, \n\r"+
//         " To_Char(wo.estimate_start_time,'HH:mi') AS quoted_start_time, \n\r"+
//         " To_Char(wo.estimate_finish_time,'HH:mi') AS quoted_finish_time \n\r"+

        " FROM clw_work_order wo \n\r"+
        " join clw_bus_entity wosite  \n\r"+
        "   ON wosite.bus_entity_id = wo.bus_entity_id \n\r"+
            siteFilter + "\n\r" +
        " join clw_bus_entity_assoc bea  \n\r"+
        "   ON wosite.bus_entity_id = bea.bus_entity1_id   \n\r"+
        "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n\r"+
        " join clw_bus_entity woacct  \n\r"+
        "   ON woacct.bus_entity_id = bea.bus_entity2_id \n\r"+
            accountFilter + "\n\r" +
        " join clw_bus_entity_assoc bea1  \n\r"+
        "   ON bea.bus_entity2_id = bea1.bus_entity1_id \n\r"+
        "   AND bea1.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"' \n\r"+
        "   AND bea1.bus_entity2_id " + storeFilter + " \n\r"+
        filterCondition +
        " left join clw_work_order_assoc woa  \n\r"+
        "   ON woa.work_order_id = wo.work_order_id  \n\r"+
        "   AND woa.work_order_assoc_cd = '"+RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER+"' \n\r"+
        " left join clw_bus_entity prov  \n\r"+
        "   ON prov.bus_entity_id = woa.bus_entity_id \n\r"+
        " left join clw_work_order_item woi  \n\r"+
        "   ON woi.work_order_id = wo.work_order_id \n\r"+
        " left join clw_work_order_asset woasset  \n\r"+
        "   ON woasset.work_order_item_id = woi.work_order_item_id \n\r"+
        " left join clw_asset asset  \n\r"+
        "   ON asset.asset_id = woasset.asset_id \n\r"+
        " left join clw_cost_center cc  \n\r"+
        "   ON cc.cost_center_id = wo.cost_center_id \n\r"+
        webOrderTotalsLeftJoin +

        propertyJoins +
        daysOpenJoins +
        " WHERE 1=1 \n\r"+
        requestedServiceCond +
        workOrderNumCond +
        workOrderDistAcctRefNumCond +
        workOrderDistSiteRefNumCond +
        statusCond +
        typeCond +
        priorityCond +
        providerCond +
        begDateActualCond +
        endDateActualCond +
        begDateEstimateCond +
        endDateEstimateCond +
        receivedDateFromCond +
        receivedDateToCond +
        daysOpenFilter +
        workOrderStatusFilter +
        assetModelFilter +
        serialNumFilter +

//        " AND woacct.short_desc LIKE '%' \n\r"+
//        " AND wosite.short_desc LIKE '%' \n\r"+
//        " AND (CASE WHEN wo.status_cd = 'Completed' OR wo.status_cd = 'Closed' THEN 'Closed' \n\r"+
//        "           WHEN wo.status_cd = 'Cancelled' THEN 'Closed' ELSE 'Open' END) LIKE '%' \n\r"+
        " ORDER BY account_name, asset_name";


//--AND asset.MODEL_NUMBER LIKE '%'
//--AND asset.SERIAL_NUM LIKE '%'
//--AND cc.short_desc LIKE '%'

        pstmt = conn.prepareStatement(workOrderTotalsReq);

        log.info(getClass().getName() + " ----------> workOrderTotalsReq: " + workOrderTotalsReq);
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
            wot.costCenter = rs.getString("cost_center");
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

            if (isDisplayDistAcctRefNum)
            {
            	wot.distAcctRefNum = rs.getString(distAcctRefNumColumnAlias);
            }
            if (isDisplayDistSiteRefNum)
            {
            	wot.distSiteRefNum = rs.getString(distSiteRefNumColumnAlias);
            }
            wot.webOrderNum = rs.getString("web_order_num");
            wot.webOrderTotal = rs.getBigDecimal("web_order_total");
            wot.nte = rs.getBigDecimal("nte");
//            wot.daysOpen = rs.getInt("days_open");
            wot.daysOpen = (rs.getString("days_open")!= null) ? Integer.valueOf(rs.getString("days_open")) : null;
            wot.receivedDate = rs.getDate("received_date");
            wot.receivedTime = rs.getTimestamp("received_time");
            wot.actualStartTime = rs.getTimestamp("actual_start_time");
            wot.actualFinishTime = rs.getTimestamp("actual_finish_time");
            wot.quotedStartTime = rs.getTimestamp("quoted_start_time");
            wot.quotedFinishTime = rs.getTimestamp("quoted_finish_time");

        }
        rs.close();
        pstmt.close();


        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getWorkOrderTotalsReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Work Order Totals");
        ArrayList table = new ArrayList();
        result.setTable(table);
        result.setFreezePositionColumn(1);
        result.setFreezePositionRow(1);


        for(Iterator iter = ll.iterator(); iter.hasNext();) {
            WorkOrderTotals wot = (WorkOrderTotals) iter.next();
            ArrayList line = new ArrayList();
            table.add(line);
            line.add(wot.workOrderNum);
            line.add(wot.poNum);
            line.add(wot.addDate);
            line.add(wot.addTime);
            line.add(wot.daysOpen);
            line.add(wot.acctName);
            if (isDisplayDistAcctRefNum)
            {
                line.add(wot.distAcctRefNum);
            }
            line.add(wot.siteName);
            if (isDisplayDistSiteRefNum)
            {
                line.add(wot.distSiteRefNum);
            }
            line.add(wot.requestedService);
            line.add(wot.action);
            line.add(wot.type);
            line.add(wot.priority);
            line.add(wot.costCenter);
            line.add(wot.status);
            line.add(wot.poClosedOrOpen);
            line.add(wot.serviceProvider);
            line.add(wot.receivedDate);
            line.add(formatTime(wot.receivedTime));
            line.add(wot.actualStartDate);
            line.add(formatTime(wot.actualStartTime));
            line.add(wot.actualFinishDate);
            line.add(formatTime(wot.actualFinishTime));
            log.info("process-> wot.receivedTime = " +wot.receivedTime );
            line.add((wot.receivedTime == null ) ? "" : calculateDiffTime( getTimeInMillis(wot.actualStartTime), getTimeInMillis(wot.receivedTime)));
            //actualResponseTime
            line.add(wot.quotedStartDate);
            line.add(formatTime(wot.quotedStartTime));
            line.add(wot.quotedFinishDate);
            line.add(formatTime(wot.quotedFinishTime));
            long deltaTime1 = (wot.receivedTime == null || wot.actualFinishTime == null) ? 0 :
                            (getTimeInMillis(wot.actualFinishTime) - getTimeInMillis(wot.receivedTime)) ;
            long deltaTime2 = (wot.quotedStartTime == null || wot.quotedFinishTime == null) ? 0:
                            (getTimeInMillis(wot.quotedFinishTime) - getTimeInMillis(wot.quotedStartTime));
            line.add(calculateDiffTime(deltaTime1,deltaTime2));//actualClosureTime
            line.add(wot.assetName);
            line.add(wot.assetModel);
            line.add(wot.assetSerialNumber);
            line.add(wot.actualTotalCost);
            line.add(wot.webOrderNum);
            line.add(wot.webOrderTotal);
            line.add(wot.nte);

        }

        resultV.add(result);

        log("process => end");
        return resultV;
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

    private GenericReportColumnViewVector getWorkOrderTotalsReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Work Order Number", 0, 38, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "System Add Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "System Add Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Days Open", 0, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        if (isDisplayDistAcctRefNum)
        {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Acct Number", 0, 255, "VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        if (isDisplayDistSiteRefNum)
        {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Site Number", 0, 255, "VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Requested Service", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Type", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Priority", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Cost Center", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Closed or Open?", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Service Provider", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Received Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "Received Time", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Actual Start Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "Actual Start Time", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Actual Finish Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "Actual Finish Time", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "Actual Response Time Minutes", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Quotation Start Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "Quotation Start Time", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "PO Received Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "PO Received Time", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.String", "Actual Closure Time Minutes", 0, 0, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Model", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Serial Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Actual Total Price", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Web Order Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Web Order Total", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "NTE", 2, 20, "NUMBER"));
        return header;
    }

    private GenericReportColumnViewVector getWorkOrderTypeSpecificReportHeader(String pCostName, boolean isNteFieldRequired) {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Work Order Number", 0, 38, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "System Add Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "System Add Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Days Open", 0, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));

        if (isDisplayDistAcctRefNum)
        {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Acct Number", 0, 255, "VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        if (isDisplayDistSiteRefNum)
        {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Site Number", 0, 255, "VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Service Type", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Requested Service", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Type", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Priority", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Cost Center", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Closed or Open?", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Service Provider", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Actual Start Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Actual Finish Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Quotation Start Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "PO Received Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Model", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Serial Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", pCostName, 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "User Name", 0, 255, "VARCHAR2"));
        if(isNteFieldRequired)
        {
        	header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "NTE", 2, 20, "NUMBER"));
        }

        return header;
    }

    private GenericReportColumnViewVector getWorkOrderDetailReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Work Order Number", 0, 38, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "System Add Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "System Add Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Days Open", 0, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        if (isDisplayDistAcctRefNum)
        {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Acct Number", 0, 255, "VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        if (isDisplayDistSiteRefNum)
        {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Site Number", 0, 255, "VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Service Type", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Requested Service", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Type", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Priority", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Cost Center", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Closed or Open?", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Service Provider", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Actual Start Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Actual Finish Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Quotation Start Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "PO Received Date", 0, 0, "DATE"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Part Num", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Description", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Quantity", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Price Each", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Parts Price, Extended", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Labor", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Travel", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Total Price", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Model", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Serial Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "User Name", 0, 255, "VARCHAR2"));

        return header;
    }


    /**
     * Logging
     *
     * @param message - message which will be pasted to log
     */
    private static void log(String message) {
        log.info(className + " :: " + message);
    }

    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }
    private static long getTimeInMillis(Date pTime){
      Calendar cal = Calendar.getInstance();
      long timeInMillis = 0;
      if (pTime != null) {
        cal.setTime(pTime);
        timeInMillis = cal.getTimeInMillis();
      }
      return timeInMillis;
    }

    private static String calculateDiffTime(long pTimeInMillis1 , long pTimeInMillis2) {
        log.info("calculateTime() ==> pTime1= " + pTimeInMillis1 + "; pTime2=" + pTimeInMillis2);
        String difTimeS = "";
        if (pTimeInMillis1 != 0 ){
          String sign = (pTimeInMillis1-pTimeInMillis2 > 0) ? "" :"-";
          Long min = java.lang.Math.abs((pTimeInMillis1-pTimeInMillis2)/60000);
          difTimeS = min.toString();
        }
        log.info("calculateTime() ==> difTimeS= " + difTimeS );

        return difTimeS;
    }

    private static String formatTime(Date pTime){
      String timeS = "";
      if (pTime != null) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        timeS = sdf.format(pTime);
      }
      return timeS;
    }
    private class WorkOrderTotals {
        public BigDecimal nte;
		public BigDecimal webOrderTotal;
		public String webOrderNum;
		public String distSiteRefNum;
		public String distAcctRefNum;
		String workOrderNum;
        String poNum;
        Date addDate;
        String addTime;
//        int daysOpen;
        Integer daysOpen;
        String acctName;
        String siteName;
        String requestedService;
        String action;
        String type;
        String priority;
        String costCenter;
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
        Date receivedDate;
        Date receivedTime;
        Date actualStartTime;
        Date actualFinishTime;
        Date quotedStartTime;
        Date quotedFinishTime;


    }
    private class WorkOrderSpecTotals {
        public BigDecimal nte;
		public String distSiteRefNum;
		public String distAcctRefNum;
		String workOrderNum;
        String poNum;
        Date addDate;
        String addTime;
        //        int daysOpen;
        Integer daysOpen;
        String acctName;
        String siteName;
        String serviceType;
        String requestedService;
        String action;
        String type;
        String priority;
        String costCenter;
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
		public BigDecimal webOrderTotal;
		public String webOrderNum;
		public String distSiteRefNum;
		public String distAcctRefNum;
		String workOrderNum;
        String poNum;
        Date addDate;
        String addTime;
        //        int daysOpen;
        Integer daysOpen;
      String acctName;
        String siteName;
        String serviceType;
        String requestedService;
        String action;
        String type;
        String priority;
        String costCenter;
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
}
