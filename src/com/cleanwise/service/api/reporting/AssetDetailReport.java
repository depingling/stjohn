package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.GenericReportControlFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/**
 */
public class AssetDetailReport implements GenericReportMulti {

    private static String className = "AssetDetailReport";
	private boolean	isDisplayDistAcctRefNum = false;
	private boolean isDisplayDistSiteRefNum = false;


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {


        APIAccess factory = new APIAccess();

        Asset assetEjb       = factory.getAssetAPI();
        User  userEjb        = factory.getUserAPI();
        WorkOrder woEjb      = factory.getWorkOrderAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        PropertyService propertyServiceEjb = factory.getPropertyServiceAPI();

        Connection conn = pCons.getMainConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Object assetName     = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.ASSET_NAME);
        Object serialNumber  = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.SERIAL_NUMBER);
        Object assetModel    = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.ASSET_MODEL);
        Object assetCategory = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.ASSET_CATEGORY);
        Object userIdStr     = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.CUSTOMER);
//        Object storeIdStr    = getParamValue(pParams, "STORE");
        Object storeIdStr    = getParamValue(pParams, "STORE_SELECT");
        Object assetNum      = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.ASSET_NUMBER);
        Object userIdString  = getParamValue(pParams, "USER_ID");
        Object userType      = getParamValue(pParams, "USER_TYPE");
        Object userWOadmin   = getParamValue(pParams, "USER_WO_ADMIN");
        Object woDistAcctRefNum = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_ACCT_REF_NUM);
        Object woDistSiteRefNum = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.WORK_ORDER_DIST_SITE_REF_NUM);

        String accountFilter = (String)ReportingUtils.getParam(pParams,"LOCATE_ACCOUNT_MULTI_OPT");
        String siteFilter = (String)ReportingUtils.getParam(pParams,"LOCATE_SITE_MULTI_OPT");
        int userId = 0;
        if (Utility.isSet((String)userIdString)) {
            userId = Integer.parseInt((String)userIdString);
        } else if (Utility.isSet((String)userIdStr)) {
            userId = Integer.parseInt((String) userIdStr);
        }

        if (userId == 0) {
            throw new Exception("Asset Detail Report: Error no user defined!");
        }
        UserData userD = userEjb.getUser(userId);

        String userTypeStr = "";
        if (Utility.isSet((String)userType)) {
            userTypeStr = (String)userType;
        } else {
            userTypeStr = userD.getUserTypeCd();
        }

        RefCdDataVector refDV = userEjb.getAuthorizedFunctions(userId, userTypeStr);
        ArrayList userFunctions = new ArrayList();
        Iterator it = refDV.iterator();
        RefCdData refD;
        while (it.hasNext()) {
            refD = (RefCdData)it.next();
            userFunctions.add(refD.getValue());
        }

        boolean isAuthorizedAdmin = false;
        if (Utility.isSet((String)userWOadmin)) {
            isAuthorizedAdmin = Boolean.parseBoolean((String)userWOadmin);
        } else if (hasReportingAssignAllAcctsProperty(userD)) {
            isAuthorizedAdmin =true;
        } else {
            isAuthorizedAdmin = userFunctions.contains(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_WO_VIEW_ALL_FOR_STORE);
        }


        /*
        if (Utility.isSet((String)userIdStr)) {
            try {
                int userIdInt = Integer.parseInt((String) userIdStr);

            } catch (Exception e) {
                error(e.getMessage(), e);
                throw new Exception(e.getMessage());
            }
        }
        */

        String emptyResultCondition = " AND 1<>1 ";
        String filterCondition = "";
        if (userId > 0) {
            if (isAuthorizedAdmin) {
                emptyResultCondition = "";
                filterCondition = "";
            } else if (RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeStr) &&
                       (userFunctions.contains(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER) ||
                        userFunctions.contains(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR) ||
                        userFunctions.contains(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER))
                      ) {
                emptyResultCondition = "";
                filterCondition = " JOIN clw_user_assoc ua \n\r" +
                                      "ON ua.user_id = " + userId + " \n\r" +
                                      "AND ua.user_assoc_cd = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "' \n\r" +
                                  " JOIN clw_bus_entity_assoc bea_site_account \n\r" +
                                      "ON  ua.bus_entity_id = bea_site_account.bus_entity2_id \n\r" +
                                      "AND bea_site_account.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n\r" +
                                  " JOIN clw_asset_assoc aa_asset_site \n\r" +
                                      "ON  aa_asset_site.bus_entity_id = bea_site_account.bus_entity1_id \n\r" +
                                      "AND a.asset_id = aa_asset_site.asset_id \n\r" +
                                      "AND aa_asset_site.asset_assoc_cd = '" + RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE + "' \n\r";
            }
            //else {
            //    filterCondition = " JOIN clw_user_assoc ua \n\r" +
            //                            "ON ua.user_id = " + userId + " \n\r" +
            //                            "AND ua.user_assoc_cd = '" + RefCodeNames.USER_ASSOC_CD.SITE + "' \n\r" +
            //                      " JOIN clw_asset_assoc aa_asset_site \n\r" +
            //                            "ON  aa_asset_site.bus_entity_id = ua.bus_entity_id \n\r" +
            //                            "AND a.asset_id = aa_asset_site.asset_id \n\r" +
            //                            "AND aa_asset_site.asset_assoc_cd = '" + RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE + "' \n\r";
            //}
        }

        int storeId = (storeIdStr != null && storeIdStr instanceof String ? Integer.parseInt((String)storeIdStr) : 0);

        // check child stores
        String storeFilter = "=" + storeId;
        if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeStr) ||
            RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeStr) ||
            RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeStr) ||
            RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeStr) ||
            RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeStr)) {

        if (storeId > 0) {
            boolean isParentStore;
            try {
              isParentStore = Boolean.parseBoolean(propertyServiceEjb.
                  getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE));
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
        } catch (DataNotFoundException e) {}

        final String distAcctRefNumColumnAlias = "dist_acct_ref_num";
		String distAcctRefNumColumn = "acct_prop.clw_value AS " + distAcctRefNumColumnAlias;
        final String distSiteRefNumColumnAlias = "dist_site_ref_num";
		String distSiteRefNumColumn = "site_prop.clw_value AS " + distSiteRefNumColumnAlias;

        String propertyJoins = "";
        if(isDisplayDistAcctRefNum)
        {
        	propertyJoins += " LEFT JOIN clw_property acct_prop \n\r" +
              "ON acct_prop.bus_entity_id = acct.bus_entity_id \n\r" +
                 "AND acct_prop.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "' \n\r";
        }
        if(isDisplayDistSiteRefNum)
        {
        	propertyJoins += " LEFT JOIN clw_property site_prop \n\r" +
              "ON site_prop.bus_entity_id = asite.bus_entity_id \n\r" +
                 "AND site_prop.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "' \n\r";
        }

        String workOrderDistAcctRefNumCond = "";
        if(isDisplayDistAcctRefNum && Utility.isSet((String) woDistAcctRefNum)) {
        	workOrderDistAcctRefNumCond =
        		"AND asite.bus_entity_id IN (" +
            			String.format(WorkOrderUtil.SUB_QUERY_FOR_SITES_WITH_DIST_ACCT_REF_NUM,
            					new Object[] {woDistAcctRefNum}) +	")";
        }
        String workOrderDistSiteRefNumCond = "";
        if(isDisplayDistSiteRefNum && Utility.isSet((String) woDistSiteRefNum)) {
        	workOrderDistSiteRefNumCond =
        		"AND asite.bus_entity_id IN (" +
            			String.format(WorkOrderUtil.SUB_QUERY_FOR_SITES_WITH_DIST_SITE_REF_NUM,
            					new Object[] {woDistSiteRefNum}) +	")";
        }

        if (Utility.isSet(accountFilter)) {
//            accountFilter = "AND acct.bus_entity_id in ( " + accountFilter + ") ";
            accountFilter = "AND bea.bus_entity2_id in ( " + accountFilter + ") ";
        } else {
            accountFilter = "";
        }
        if (Utility.isSet(siteFilter)) {
//            siteFilter = "AND site.bus_entity_id in ( " + siteFilter + ") ";
            siteFilter = "AND asite.bus_entity_id in ( " + siteFilter + ") ";
        } else {
            siteFilter = "";
        }

//=============================================================
        String assetWarrantyRequest =
        " SELECT DISTINCT "+
        " a.asset_id, \n\r"+
        " aa.bus_entity_id AS store_id, \n\r"+
        " acct.short_desc AS acct_name, \n\r"+

        (isDisplayDistAcctRefNum ? distAcctRefNumColumn + ", \n\r" : "")  +
        " site.short_desc AS site_name, \n\r"+
        (isDisplayDistSiteRefNum ? distSiteRefNumColumn + ", \n\r" : "")  +

        " a.short_desc AS asset_name, \n\r"+
        " a.ASSET_NUM AS asset_number, \n\r"+
        " a.SERIAL_NUM AS serial_number, \n\r"+
        " a.MODEL_NUMBER AS model, \n\r"+
        " acat.short_desc AS category, \n\r"+
        " Nvl(manuf.short_desc, a.MANUF_NAME) AS manufacturer, \n\r"+
        " a.MANUF_SKU, \n\r"+
        " a.status_cd AS status, \n\r"+
        " w.SHORT_DESC AS warranty_name, \n\r"+
        " w.WARRANTY_NUMBER, \n\r"+
        " wp.short_desc AS warranty_provider, \n\r"+
        " To_Date (ap3.clw_value,'mm/dd/yyyy') AS effective_date, \n\r"+
        " w.DURATION||' '||w.DURATION_TYPE_CD AS warranty_period, \n\r"+
        " Add_Months(To_Date (ap3.clw_value,'mm/dd/yyyy'), \n\r"+
        "   w.duration*(CASE WHEN w.duration_type_cd='MONTHS' THEN 1 ELSE 12 END))  \n\r"+
        "      AS experation_date \n\r"+
        " FROM clw_asset a \n\r"+
        " join clw_asset_assoc aa ON a.asset_id = aa.asset_id  \n\r"+
        "  AND aa.asset_assoc_cd = '"+RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE+"' \n\r"+
        "  AND aa.bus_entity_id " + storeFilter + " \n\r"+
        filterCondition +
        " left join clw_asset_property ap ON a.asset_id = ap.asset_id  \n\r"+
        "  AND ap.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE+"' \n\r"+
        " left join clw_asset_property ap3 ON a.asset_id = ap3.asset_id \n\r"+
        "  AND ap3.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE+"' \n\r"+
        " left join clw_asset acat ON acat.asset_id = a.parent_id \n\r"+
        "  AND acat.asset_type_cd = '"+RefCodeNames.ASSET_TYPE_CD.CATEGORY+"' \n\r"+
        " left join clw_asset_assoc asite ON a.asset_id = asite.asset_id \n\r"+
        "   AND asite.asset_assoc_cd = '"+RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE+"' \n\r"+
        " left join clw_bus_entity site \n\r"+
        "   ON site.bus_entity_id = asite.bus_entity_id \n\r"+
//            siteFilter + "\n\r" +
        " left join clw_bus_entity_assoc bea \n\r"+
        "   ON asite.bus_entity_id = bea.bus_entity1_id \n\r"+
        "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n\r"+
        " left join clw_bus_entity acct \n\r"+
        "   ON acct.bus_entity_id = bea.bus_entity2_id \n\r"+
 //           accountFilter + "\n\r" +
        " left join clw_bus_entity manuf \n\r"+
        "   ON a.manuf_id = manuf.bus_entity_id \n\r"+
        " left join clw_asset_warranty aw ON a.asset_id = aw.asset_id \n\r"+
        " left join clw_warranty w ON aw.warranty_id = w.warranty_id \n\r"+
        " left join clw_warranty_assoc wa ON wa.warranty_id = w.warranty_id" +
        "   AND  wa.WARRANTY_ASSOC_CD = '"+RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_PROVIDER+"' \n\r"+
        " left join clw_bus_entity wp ON wa.bus_entity_id = wp.bus_entity_id \n\r"+
        propertyJoins +

        " WHERE a.ASSET_TYPE_CD = '"+RefCodeNames.ASSET_TYPE_CD.ASSET+"' \n\r"+
        (Utility.isSet((String) assetName)?"AND Lower(a.short_desc) LIKE '%"+((String)assetName).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) assetNum)?"AND Lower(a.asset_num) LIKE '%"+((String)assetNum).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) serialNumber)?"AND Lower(a.serial_num) LIKE '%"+((String)serialNumber).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) assetCategory)?"AND Lower(acat.short_desc) LIKE '%"+((String)assetCategory).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) assetModel)?"AND Lower(a.model_number) LIKE '%"+((String)assetModel).trim().toLowerCase()+"%' \n\r":"")+
        " AND Lower(Nvl(manuf.short_desc, a.manuf_name)) LIKE '%' \n\r"+
        siteFilter + "\n" +
        accountFilter + "\n" +
        workOrderDistAcctRefNumCond +
        workOrderDistSiteRefNumCond +
        emptyResultCondition +
        " ORDER BY acct_name, asset_name \n\r";
       // " --AND a.status_cd = '' \n\r"+
       // " --AND wosum.amount > 0 ";

        PreparedStatement pstmt = conn.prepareStatement(assetWarrantyRequest);

        ResultSet rs = pstmt.executeQuery();
        LinkedList ll = new LinkedList();
        while (rs.next()){
            AssetWarranty aw = new AssetWarranty();
            ll.add(aw);
            aw.assetId = rs.getInt("asset_id");
            aw.storeId = rs.getInt("store_id");
            aw.acctName = rs.getString("acct_name");
            aw.siteName = rs.getString("site_name");
            aw.assetName = rs.getString("asset_name");
            aw.assetNum = rs.getString("asset_number");
            aw.serialNum = rs.getString("serial_number");
            aw.modelNum = rs.getString("model");
            aw.category = rs.getString("category");
            aw.manufName = rs.getString("manufacturer");
            aw.manufSkuNum = rs.getString("MANUF_SKU");
            aw.status = rs.getString("status");

            aw.warrantyName = rs.getString("warranty_name");
            aw.warrantyNum = rs.getString("WARRANTY_NUMBER");
            aw.warrantyProvider = rs.getString("warranty_provider");
            aw.effDate = rs.getDate("effective_date");
            aw.warrantyPeriod = rs.getString("warranty_period");
            aw.expDate = rs.getDate("experation_date");

            if (isDisplayDistAcctRefNum)
            {
            	aw.distAcctRefNum = rs.getString(distAcctRefNumColumnAlias);
            }
            if (isDisplayDistSiteRefNum)
            {
            	aw.distSiteRefNum = rs.getString(distSiteRefNumColumnAlias);
            }
        }
        rs.close();
        pstmt.close();


        GenericReportResultView result1 = GenericReportResultView.createValue();
        GenericReportColumnViewVector header1 = getAssetWarrantyReportHeader();
        result1.setHeader(header1);
        result1.setColumnCount(header1.size());
        result1.setName("Asset Warranty");
        ArrayList table1 = new ArrayList();
        result1.setTable(table1);
        result1.setFreezePositionColumn(1);
        result1.setFreezePositionRow(1);

        for(Iterator iter = ll.iterator(); iter.hasNext();) {
            AssetWarranty aw = (AssetWarranty) iter.next();
            ArrayList line = new ArrayList();
            table1.add(line);
            line.add(aw.acctName);

            if (isDisplayDistAcctRefNum)
            {
                line.add(aw.distAcctRefNum);
            }
            line.add(aw.siteName);
            if (isDisplayDistSiteRefNum)
            {
                line.add(aw.distSiteRefNum);
            }

            line.add(aw.assetName);
            line.add(aw.assetNum);
            line.add(aw.serialNum);
            line.add(aw.modelNum);
            line.add(aw.category);
            line.add(aw.manufName);
            line.add(aw.manufSkuNum);
            line.add(aw.status);
            line.add(aw.warrantyName);
            line.add(aw.warrantyNum);
            line.add(aw.warrantyProvider);
            line.add(aw.effDate);
            line.add(aw.warrantyPeriod);
            line.add(aw.expDate);
        }
        resultV.add(result1);
//======================================================================

        String assetDetailRequest =
        " SELECT DISTINCT "+
        " a.asset_id, \n\r"+
        " aa.bus_entity_id AS store_id, \n\r"+
        " acct.short_desc AS acct_name, \n\r"+

        (isDisplayDistAcctRefNum ? distAcctRefNumColumn + ", \n\r" : "")  +
        " site.short_desc AS site_name, \n\r"+
        (isDisplayDistSiteRefNum ? distSiteRefNumColumn + ", \n\r" : "")  +

        " a.short_desc AS asset_name, \n\r"+
        " a.ASSET_NUM AS asset_number, \n\r"+
        " a.SERIAL_NUM AS serial_number, \n\r"+
        " a.MODEL_NUMBER AS model, \n\r"+
        " acat.short_desc AS category, \n\r"+
        " Nvl(manuf.short_desc, a.MANUF_NAME) AS manufacturer, \n\r"+
        " a.MANUF_SKU, \n\r"+
        " a.status_cd AS status, \n\r"+
        " To_Date (ap.clw_value,'mm/dd/yyyy') AS date_in_service, \n\r"+
        " ap1.clw_value AS last_meter_reading, \n\r"+
        " To_Date (ap2.clw_value,'mm/dd/yyyy') AS date_last_meter_reading, \n\r"+
        " To_Date (ap3.clw_value,'mm/dd/yyyy') AS acquisition_date, \n\r"+
        " To_Number(ap4.clw_value) AS acquisition_cost, \n\r"+
        " wosum.parts, \n\r"+
        " wosum.labor, \n\r"+
        " wosum.travel, \n\r"+
        " wosum.total AS work_order_totals \n\r"+
        " FROM clw_asset a \n\r"+
        " join clw_asset_assoc aa ON a.asset_id = aa.asset_id  \n\r"+
        "  AND aa.asset_assoc_cd = '"+RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE+"' \n\r"+
        "  AND aa.bus_entity_id " + storeFilter + " \n\r"+
        filterCondition +
        " left join clw_asset_property ap ON a.asset_id = ap.asset_id  \n\r"+
        "  AND ap.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE+"' \n\r"+
        " left join clw_asset_property ap1 ON a.asset_id = ap1.asset_id  \n\r"+
        "  AND ap1.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.LAST_HOUR_METER_READING+"' \n\r"+
        " left join clw_asset_property ap2 ON a.asset_id = ap2.asset_id \n\r"+
        "  AND ap2.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_LAST_HOUR_METER_READING+"' \n\r"+
        " left join clw_asset_property ap3 ON a.asset_id = ap3.asset_id \n\r"+
        "  AND ap3.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE+"' \n\r"+
        " left join clw_asset_property ap4 ON a.asset_id = ap4.asset_id \n\r"+
        "  AND ap4.asset_property_cd = '"+RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_COST+"' \n\r"+
        " left join clw_asset acat ON acat.asset_id = a.parent_id \n\r"+
        "  AND acat.asset_type_cd = '"+RefCodeNames.ASSET_TYPE_CD.CATEGORY+"' \n\r"+
        " left join clw_asset_assoc asite ON a.asset_id = asite.asset_id \n\r"+
        "   AND asite.asset_assoc_cd = '"+RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE+"' \n\r"+
        " left join clw_bus_entity site \n\r"+
        "   ON site.bus_entity_id = asite.bus_entity_id \n\r"+
//                siteFilter +  "\n\r" +
        " left join clw_bus_entity_assoc bea \n\r"+
        "   ON asite.bus_entity_id = bea.bus_entity1_id \n\r"+
        "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n\r"+
        " left join clw_bus_entity acct \n\r"+
        "   ON acct.bus_entity_id = bea.bus_entity2_id \n\r"+
//                accountFilter + "\n\r" +
        " left join clw_bus_entity manuf \n\r"+
        "   ON a.manuf_id = manuf.bus_entity_id \n\r"+
        " left join \n\r"+
        "  (SELECT woa.asset_id, \n\r"+
        "     Sum(wod.PART_PRICE * wod.QUANTITY) parts, \n\r"+
        "     Sum(wod.labor) labor, \n\r"+
        "     Sum (wod.travel) travel, \n\r"+
        "     Sum(wod.PART_PRICE * wod.QUANTITY + wod.labor + wod.travel) total \n\r"+
        "   FROM clw_work_order_asset woa \n\r"+
        "     join clw_work_order_item woi \n\r"+
        "       ON woi.work_order_item_id = woa.work_order_item_id \n\r"+
        "     join clw_work_order wo \n\r"+
        "       ON woi.work_order_id = wo.work_order_id \n\r"+
        "     join clw_work_order_detail wod \n\r"+
        "       ON wo.work_order_id = wod.work_order_id \n\r"+
        "     GROUP BY woa.asset_id) wosum \n\r"+
        "  ON wosum.asset_id = a.asset_id \n\r"+
        propertyJoins +
        " WHERE a.ASSET_TYPE_CD = '"+RefCodeNames.ASSET_TYPE_CD.ASSET+"' \n\r"+
        (Utility.isSet((String) assetName)?"AND Lower(a.short_desc) LIKE '%"+((String)assetName).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) assetNum)?"AND Lower(a.asset_num) LIKE '%"+((String)assetNum).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) serialNumber)?"AND Lower(a.serial_num) LIKE '%"+((String)serialNumber).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) assetCategory)?"AND Lower(acat.short_desc) LIKE '%"+((String)assetCategory).trim().toLowerCase()+"%' \n\r":"")+
        (Utility.isSet((String) assetModel)?"AND Lower(a.model_number) LIKE '%"+((String)assetModel).trim().toLowerCase()+"%' \n\r":"")+
        " AND Lower(Nvl(manuf.short_desc, a.manuf_name)) LIKE '%' \n\r"+
        siteFilter + "\n" +
        accountFilter + "\n" +
        workOrderDistAcctRefNumCond +
        workOrderDistSiteRefNumCond +
        emptyResultCondition +
        " ORDER BY acct_name, asset_name \n\r";
       // " --AND a.status_cd = '' \n\r"+
       // " --AND wosum.amount > 0 ";




        pstmt = conn.prepareStatement(assetDetailRequest);

        rs = pstmt.executeQuery();
        ll = new LinkedList();
        while (rs.next()){
            AssetDetail ad = new AssetDetail();
            ll.add(ad);
            ad.assetId = rs.getInt("asset_id");
            ad.storeId = rs.getInt("store_id");
            ad.acctName = rs.getString("acct_name");
            ad.siteName = rs.getString("site_name");
            ad.assetName = rs.getString("asset_name");
            ad.assetNum = rs.getString("asset_number");
            ad.serialNum = rs.getString("serial_number");
            ad.modelNum = rs.getString("model");
            ad.category = rs.getString("category");
            ad.manufName = rs.getString("manufacturer");
            ad.manufSkuNum = rs.getString("MANUF_SKU");
            ad.status = rs.getString("status");
            ad.dateInService = rs.getDate("date_in_service");
            ad.lastMeterReading = rs.getBigDecimal("last_meter_reading");
            ad.dateLastMeterReading = rs.getDate("date_last_meter_reading");
            ad.acquisitionDate = rs.getDate("acquisition_date");
            ad.acquisitioCost = rs.getBigDecimal("acquisition_cost");
            ad.parts = rs.getBigDecimal("parts");
            ad.labor = rs.getBigDecimal("labor");
            ad.travel = rs.getBigDecimal("travel");
            ad.workOrderTotals = rs.getBigDecimal("work_order_totals");

            if (isDisplayDistAcctRefNum)
            {
            	ad.distAcctRefNum = rs.getString(distAcctRefNumColumnAlias);
            }
            if (isDisplayDistSiteRefNum)
            {
            	ad.distSiteRefNum = rs.getString(distSiteRefNumColumnAlias);
            }
        }
        rs.close();
        pstmt.close();


        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getAssetDetailReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Asset Detail");
        ArrayList table = new ArrayList();
        result.setTable(table);
        result.setFreezePositionColumn(1);
        result.setFreezePositionRow(1);

        for(Iterator iter = ll.iterator(); iter.hasNext();) {
            AssetDetail ad = (AssetDetail) iter.next();
            ArrayList line = new ArrayList();
            table.add(line);
            line.add(ad.acctName);
            if (isDisplayDistAcctRefNum)
            {
                line.add(ad.distAcctRefNum);
            }
            line.add(ad.siteName);
            if (isDisplayDistSiteRefNum)
            {
                line.add(ad.distSiteRefNum);
            }
            line.add(ad.assetName);
            line.add(ad.assetNum);
            line.add(ad.serialNum);
            line.add(ad.modelNum);
            line.add(ad.category);
            line.add(ad.manufName);
            line.add(ad.manufSkuNum);
            line.add(ad.status);
            line.add(ad.dateInService);
            line.add(ad.lastMeterReading);
            line.add(ad.dateLastMeterReading);
            line.add(ad.acquisitionDate);
            line.add(ad.acquisitioCost);
            line.add(ad.parts);
            line.add(ad.labor);
            line.add(ad.travel);
            line.add(ad.workOrderTotals);

        }
        resultV.add(result);


        return resultV;

    }





    private GenericReportColumnViewVector getAssetDetailReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

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

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Serial Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Model", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer SKU", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Date In-Service", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Last Hour Meter Reading", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Date Last Hour Meter Reading", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Acquisition Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Acquisition Price", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Parts", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Labor", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Travel", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator", "Work Order Totals", 2, 20, "NUMBER"));
        return header;
    }

    private GenericReportColumnViewVector getAssetWarrantyReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
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
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Asset Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Serial Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Model", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer SKU", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Warranty Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Warranty Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Warranty Provider", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Effective Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Warranty Period", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Warranty Expiration Date", 0, 0, "DATE"));

        return header;
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

    private boolean hasReportingAssignAllAcctsProperty(UserData userD){
      String userRoleCd = userD.getUserRoleCd();
      boolean hasRole = Utility.isSet(userRoleCd) && userRoleCd.contains("RepOA^");
      return hasRole;
    }

    private class AssetDetail {
        String distSiteRefNum;
		String distAcctRefNum;
		int assetId;
        int storeId;
        String acctName;
        String siteName;
        String assetName;
        String assetNum;
        String serialNum;
        String modelNum;
        String category;
        String manufName;
        String manufSkuNum;
        String status;
        Date dateInService;
        BigDecimal lastMeterReading;
        Date dateLastMeterReading;
        Date acquisitionDate;
        BigDecimal acquisitioCost;
        BigDecimal parts;
        BigDecimal labor;
        BigDecimal travel;
        BigDecimal workOrderTotals;
    }


    private class AssetWarranty {
        String distSiteRefNum;
		String distAcctRefNum;
		int assetId;
        int storeId;
        String acctName;
        String siteName;
        String assetName;
        String assetNum;
        String serialNum;
        String modelNum;
        String category;
        String manufName;
        String manufSkuNum;
        String status;
        String warrantyName;
        String warrantyNum;
        String warrantyProvider;
        Date effDate;
        String warrantyPeriod;
        Date expDate;
    }
}
