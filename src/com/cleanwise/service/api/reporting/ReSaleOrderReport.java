/*
 * ReSaleOrder.java
 *
 * Created on October 23, 2003, 2:23 PM
 */

package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.ItemData;

/**
 * @author bstevens
 */
public class ReSaleOrderReport implements GenericReportMulti {
    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();
//   private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("America/New_York");


    protected boolean isShowPrimaryContact(){
    	return false;
    }
    protected boolean isShowCost(){
    	return false;
    }
    
    protected boolean isUseMultiAcctsFlag(){
    	return true;
    }
    
    protected boolean isShowDeliveryNoteNum() {
    	return false;
    }

    protected boolean isShowSPL() {
        return false;
    }
    
    protected boolean mDisplaySalesTaxInfo;
    protected boolean mDisplayDistInfo;
    protected HashMap storeMap = new HashMap();
    protected HashMap masterItemCategoryMap = new HashMap();
    Connection con;
    protected boolean multiAccounts = false;
    protected boolean includeDist = false;
    protected HashMap accountMap = new HashMap();
    protected boolean showPrice;
    protected boolean displayPONum = false;
    protected boolean includeState = false;

    public com.cleanwise.service.api.value.GenericReportResultViewVector process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams)
            throws Exception {
        TimeZone tzSource = DEFAULT_TIMEZONE;
        APIAccess factory = new APIAccess();
        Distributor distEjb = factory.getDistributorAPI();

        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV =
                new GenericReportResultViewVector();
        //Lists of report results
        HashSet storeIdsInReport = new HashSet();
        ArrayList catTotals = new ArrayList();
        ArrayList orderTotals = new ArrayList();
        ArrayList details = new ArrayList();
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        String dateFmt = (String) pParams.get("DATE_FMT");

        String distributorOpt = (String)ReportingUtils.getParam(pParams,"DISTRIBUTOR_MULTI");
		if(distributorOpt == null){
			distributorOpt = (String)ReportingUtils.getParam(pParams,"DISTRIBUTOR");
		}
        //String siteStateOpt = (String) pParams.get("STATE_OPT");
        String siteStateOpt = (String)ReportingUtils.getParam(pParams,"STATE");

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }

        if (!ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (!ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        Date begDateD = sdf.parse(begDateS);
        Date endDateD = sdf.parse(endDateS);
        if (endDateD != null && begDateD != null && endDateD.compareTo(begDateD) < 0) {
            // TODO if it's possible then should use
            // shop.errors.endDateBeforeStartDate=End date: {0} cannot be before
            // start date: {1}
            String mess = "^clw^End date: \"" + endDateS
                    + "\" cannot be before start date: \"" + begDateS
                    + "\"^clw^";
            throw new Exception(mess);
        }

        String dateWhere = getDateWhere(begDateS, endDateS, dateFmt, tzSource, DEFAULT_TIMEZONE);
        String accountIdS = (String) pParams.get("ACCOUNT_MULTI_OPT");
        String accountIdS2 = (String) pParams.get("runForAccounts");
        String acctStr = (String)ReportingUtils.getParam(pParams, "ACCOUNT");
        String siteIdS = (String)ReportingUtils.getParam(pParams, "LOCATE_SITE_MULTI");

        String showInactiveSitesS = (String) pParams.get("showInactiveSites");
        boolean showInactiveSites = Utility.isOn(showInactiveSitesS);


        if (accountIdS2 == null) {
            accountIdS2 = accountIdS;
        } else {
            accountIdS2 += "," + accountIdS;
        }
        if(Utility.isSet(acctStr) && Utility.parseInt(acctStr) > 0){
        	if (accountIdS2 == null) {
        		accountIdS2 = acctStr;
        	}else{
        		accountIdS2 += "," + accountIdS;
        	}
        }

        int acctCt = 0;
        multiAccounts = false;
        if(accountIdS2 == null){
        	accountIdS2="";
        }
        StringTokenizer token = new StringTokenizer(accountIdS2, ",");
        while (token.hasMoreTokens()) {
            String acctS = token.nextToken();
            acctCt++;
            acctS = acctS.trim();
            if (acctS.length() > 0)
                try {
                    int acctId = Integer.parseInt(acctS);
                    //just use first account for timezone offset
                    if(acctCt == 1){
	                    BusEntityData accountItem = BusEntityDataAccess.select(con,acctId);
	        	        if (accountItem != null && accountItem.getTimeZoneCd() != null) {
	        	            TimeZone tzBuffer = TimeZone.getTimeZone(accountItem.getTimeZoneCd());
	        	            if (tzBuffer != null && tzBuffer.getID().equals(accountItem.getTimeZoneCd())) {
	        	                tzSource = tzBuffer;
	        	            }
	        	        }
                    }
                } catch (Exception exc) {
                    String mess = "^clw^" + acctS + " is not a valid account identifier ^clw^";
                    throw new Exception(mess);
                }
        }
        if (acctCt != 1) {
            multiAccounts = true;
        }

        Iterator it3 = pParams.keySet().iterator();
        String repParams = "";
        while (it3.hasNext()) {
            Object o = it3.next();
            repParams += " " + o + "::" + pParams.get(o);
        }

        if (siteIdS != null) {
            token = new StringTokenizer(siteIdS, ",");
            while (token.hasMoreTokens()) {
                String siteS = token.nextToken();
                if (siteS.trim().length() > 0)
                    try {
                        Integer.parseInt(siteS.trim());
                    } catch (Exception exc) {
                        String mess = "^clw^" + siteS + " is not a valid site identifier ^clw^";
                        throw new Exception(mess);
                    }

            }
        }

        BusEntityDataVector distCollection = null;
        String distErpNumsStr = "";
        String stateCriteria = "";

        if (Utility.isSet(distributorOpt)) {

            try {

                BusEntitySearchCriteria crit = new BusEntitySearchCriteria();

                if (Utility.isSet(distributorOpt)) {
                    crit.setDistributorBusEntityIds(Utility.parseIdStringToVector(distributorOpt, ","));
                }
                distCollection = distEjb.getDistributrBusEntitiesByCriteria(crit);
                distErpNumsStr = "";
                int i = 0;
                Iterator it = distCollection.iterator();
                while (it.hasNext()) {
                    BusEntityData dist = (BusEntityData) it.next();
                    if (i != 0) {
                        distErpNumsStr += ",'" + dist.getErpNum() + "'";
                    } else {
                        distErpNumsStr += "'" + dist.getErpNum() + "'";
                    }
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }

        if (!Utility.isSet(distributorOpt) || Utility.isSet(distErpNumsStr)) {

            includeDist = Utility.isSet(distributorOpt);
            includeState = Utility.isSet(siteStateOpt);

            ReportingUtils repUtil = new ReportingUtils();
            ReportingUtils.UserAccessDescription userDesc = repUtil.getUserAccessDescription(pParams, con);

            UserRightsTool urt = new UserRightsTool(userDesc.getUserData());
            showPrice = urt.getShowPrice();

            if (includeState) {
                stateCriteria = "Select be.bus_entity_id,a.state_province_cd as state from clw_address a,clw_bus_entity be " +
                        "where address_type_cd='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'" +
                        " and a.bus_entity_id = be.bus_entity_id " +
                        " and be.bus_entity_type_cd = '" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'" +
                        //" and state_province_cd like '" + siteStateOpt + "%'";
                        " and state_province_cd like ? ";
            }

            String orderTotalSql =
                    "SELECT " +
                            " name1, name2, site_name," + (includeState ? "site_ship_state," : "") +
                            " (SELECT max(clw_value) FROM CLW_PROPERTY p WHERE p.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "' AND p.property_type_cd = '" + RefCodeNames.PROPERTY_TYPE_CD.EXTRA + "' AND p.bus_entity_id = grp.site_id AND p.property_status_cd='" + RefCodeNames.PHONE_STATUS_CD.ACTIVE + "') AS projectCode, " +
                            "order_num, request_po_num, sale_type_cd," +
                            " (select op.clw_value from clw_order_property op where op.order_id = grp.order_id and op.order_property_type_cd = '" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER + "') as rebill_order, " +
                            (!includeDist ? "" : "dist_name,") + " total_price, revised_order_date, revised_order_time, original_order_date, original_order_time, total_tax_cost,total_misc_cost,total_freight_cost,store_id,account_id, " +
                            "(SELECT clw_VALUE FROM CLW_ORDER_META om " +
                                " WHERE om.order_id=grp.order_id AND om.name='" + RefCodeNames.CHARGE_CD.DISCOUNT + "' AND ROWNUM = 1) as discount, " +
                            "CURRENCY_CD " +
                            "FROM ( " +
                            "SELECT addr.name1 as name1, addr.name2 as name2, ord.order_id, ord.store_id, ord.site_id, " +
                            "site.short_desc AS site_name," +
                            (includeState ? "sa.state as site_ship_state," : "")
                            + " ord.account_id, "
                            + " ord.order_num, request_po_num, "
                            + " ord.total_tax_cost,ord.total_misc_cost,ord.total_freight_cost, " +
                            "oi.sale_type_cd," + (!includeDist ? "" : "oi.dist_item_short_desc as dist_name,") + "SUM(total_quantity_ordered * cust_contract_price) as total_price, ord.revised_order_date, ord.revised_order_time, ord.original_order_date, ord.original_order_time, ord.currency_cd " +
                            "FROM  " +
                            "CLW_ORDER ord, CLW_BUS_ENTITY site, CLW_ORDER_ITEM oi, clw_address addr " +
                            (includeState ? ",(" + stateCriteria + ") sa " : "") +
                            "WHERE  " +
                            ReportingUtils.getValidOrdersSQL("ord") + " AND " +
                            dateWhere
//                            "ord.original_order_date between to_date('" + begDateS + "','"
//                            + dateFmt + "') AND to_date('" + endDateS + "','" + dateFmt + "') AND " +
                            + " AND (oi.order_item_status_cd NOT IN ('CANCELLED') OR oi.order_item_status_cd IS NULL) AND  " +
                            "ord.site_id = site.bus_entity_id AND ord.order_id = oi.order_id "+
                            " AND ord.account_id = addr.bus_entity_id AND addr.address_type_cd='"+RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT+"' ";


            if (null != accountIdS && accountIdS.trim().length() > 0) {
                orderTotalSql += " AND ord.account_id in (" + accountIdS + ") ";
            }

            if (includeState) {
                orderTotalSql += " AND site.bus_entity_id = sa.bus_entity_id ";
            }


            if (includeDist) {
                orderTotalSql += " AND oi.dist_erp_num in (" + distErpNumsStr + ") ";
            }

            if (null != siteIdS && siteIdS.trim().length() > 0) {
                orderTotalSql += " AND ord.site_id in (" + siteIdS + ") ";
            }
            if (siteIdS == null || siteIdS.trim().length() == 0) {
                if (!showInactiveSites) {
                    orderTotalSql += " and site.bus_entity_status_cd = 'ACTIVE' ";
                }
            }


            if (!userDesc.hasAccessToAll()) {
            	if (userDesc.hasStoreAccess()) {
            	    orderTotalSql += " and store_id in (" + userDesc.getAuthorizedSql() + ") ";            		  
            	} else if (userDesc.hasAccountAccess()) {
                    orderTotalSql += " and account_id in (" + userDesc.getAuthorizedSql() + ") ";
                } else if (userDesc.hasSiteAccess()) {
                    orderTotalSql += " and site_id in (" + userDesc.getAuthorizedSql() + ") ";
                }
            }

            String foraccounts = (String) pParams.get("runForAccounts");
            if (foraccounts != null && foraccounts.trim().length() > 0) {
                orderTotalSql += " and ord.account_id in (" + foraccounts + "  ) ";
            }

            orderTotalSql += "GROUP BY  " +
                    "ord.order_id, ord.site_id, site.short_desc, " + (includeState ? "sa.state," : "") + " ord.account_id, ord.order_num , request_po_num, ord.currency_cd, " +
                    "oi.sale_type_cd," + (!includeDist ? "" : "oi.dist_item_short_desc,") + " ord.total_price, ord.total_tax_cost,ord.total_misc_cost,ord.total_freight_cost, ord.revised_order_date, ord.revised_order_time, ord.original_order_date, ord.original_order_time, ord.store_id, " +
                    "addr.name1, addr.name2 ) grp " +
                    "ORDER BY site_name,order_num";

            PreparedStatement stmt = con.prepareStatement(orderTotalSql);
            if (includeState) {
                stmt.setString(1, siteStateOpt + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderTotal record = new OrderTotal();
                
                record.primaryContactFName = rs.getString("name1");
                record.primaryContactLName = rs.getString("name2");
                record.siteName = rs.getString("site_name");
                if (includeState) {
                    record.siteShippingState = rs.getString("site_ship_state");
                }
                record.projectCode = rs.getString("projectCode");
                record.confirmNum = rs.getString("order_num");
                record.poNum = rs.getString("request_po_num");
                record.saleType = rs.getString("sale_type_cd");
                String rebildOrder = rs.getString("rebill_order");
                record.orderType = "STANDARD";
                if (Utility.isTrue(rebildOrder)) {
                    //record.saleType = "REBILL";
                	record.orderType = "REBILL";
                }
                if (includeDist) {
                    record.distName = rs.getString("dist_name");
                }
                record.orderTotal = rs.getBigDecimal("total_price");
                record.taxTotal = rs.getBigDecimal("total_tax_cost");
                record.totalMiscCost = rs.getBigDecimal("total_misc_cost");
                record.totalFreightCost = rs.getBigDecimal("total_freight_cost");
                if (record.totalMiscCost == null) {
                    record.totalMiscCost = new BigDecimal(0);
                }
                if (record.totalFreightCost == null) {
                    record.totalFreightCost = new BigDecimal(0);
                }
                String discount = rs.getString("discount");
                if (discount != null && discount.length() > 0) {
                    try {
                        BigDecimal discBD = new BigDecimal(discount);
                        if (discBD.doubleValue() > 0) {
                            discBD.negate();
                        }
                        record.discount = discBD;
                    } catch (Exception e) {
                        record.discount = new BigDecimal(0);
                    }
                } else {
                    record.discount = new BigDecimal(0);
                }
                
                java.sql.Date orderDate = rs.getDate("revised_order_date") == null ? rs.getDate("original_order_date") : rs.getDate("revised_order_date");
                java.sql.Timestamp orderTime = rs.getTimestamp("revised_order_time") == null ? rs.getTimestamp("original_order_time") : rs.getTimestamp("revised_order_time");
                java.util.Date buffer = getDate(orderDate, orderTime);
                buffer = convertDate(buffer, DEFAULT_TIMEZONE, tzSource);
                record.orderDate = new Date(buffer.getTime());

                storeIdsInReport.add(new Integer(rs.getInt("store_id")));

                Integer acctInt = new Integer(rs.getInt("account_id"));
                if (accountMap.containsKey(acctInt)) {
                    record.account = (String) accountMap.get(acctInt);
                } else {
                    try {
                        BusEntityData acct = BusEntityDataAccess.select(con, acctInt.intValue());
                        accountMap.put(acctInt, acct.getShortDesc());
                        record.account = acct.getShortDesc();
                    } catch (DataNotFoundException e) {
                        accountMap.put(acctInt, "");
                        record.account = "";
                    }
                }

                record.orderCurrencyCd = rs.getString("currency_cd");
                orderTotals.add(record);
            }
            rs.close();
            stmt.close();

            String splSelSql = "";
            String splTableSql = "";
            String splCondSql = "";
            if (isShowSPL()) {
            	splSelSql = ", (select UPPER(max(oim.clw_value)) from clw_order_item_meta oim " +
            			"where oi.order_item_id = oim.order_item_id(+) " +
            			"and oim.name(+) = 'STANDARD_PRODUCT_LIST') as spl";                
            }
            String detailSql =
                    "SELECT " +
                            "addr.name1, addr.name2, ord.account_id, ord.store_id, site.short_desc," + (includeState ? "sa.state as site_ship_state," : "") + " ord.order_num, ord.request_po_num, ord.order_type_cd, ord.order_id, " +
                            "oi.dist_item_cost, oi.cust_contract_price,NVL(oi.service_fee,0) as service_fee, " +
                            "(select max(oim.clw_value) from clw_order_item_meta oim " +
                            "where oi.order_item_id = oim.order_item_id(+) " +
                            "and oim.name(+) = 'CATEGORY_NAME') categoryName, " +
                            "(select max(category.short_desc) from clw_catalog c, clw_catalog_assoc ca, clw_item category, clw_item_assoc  itmassoc " +
                            "where c.catalog_id = ca.catalog_id " +
                            "and c.catalog_type_cd = 'STORE' " +
                            "and c.catalog_status_cd = 'ACTIVE' " +
                            "and ca.bus_entity_id =ord.store_id " +
                            "and c.catalog_id = itmassoc.catalog_id " +
                            "and itmassoc.item1_id = oi.item_id " +
                            "and category.item_id =itmassoc.item2_id " +
                            "and  category.item_type_cd = 'CATEGORY') categoryName2, " +
                            "NVL(oi.cust_item_sku_num,item_sku_num) as cust_item_sku_num, NVL(oi.cust_item_uom,item_uom) as cust_item_uom, NVL(oi.cust_item_pack,item_pack) as cust_item_pack, oi.item_size, " +
                            "ci.short_desc as cust_item_short_desc,(oi.total_quantity_ordered * oi.cust_contract_price) AS line_tot,oi.sale_type_cd, " +
                            "(select op.clw_value from clw_order_property op where op.order_id = ord.order_id and op.order_property_type_cd = '" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER + "') as rebill_order, " +
                            "oi.total_quantity_ordered, ord.revised_order_date, ord.revised_order_time, " +
                            "ord.original_order_date, ord.original_order_time, oi.manu_item_sku_num, oi.manu_item_short_desc, oi.dist_item_short_desc, oi.dist_item_sku_num, oi.item_id, ord.currency_cd, " +
                            " oi.dist_item_cost, " +
                            "( select max(im.item_mapping_id) " +
                            " from clw_item_mapping im " +
                            " where im.item_id = oi.item_id " +
                            " AND im.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY +"' ) as green_flag, " +
                            " ( select max(oia.comments)  from clw_order_item_action oia " +
                            " where oia.order_item_id = oi.order_item_id " +
                            " and oia.action_cd = 'Delivery Ref Number' " +
                            " and oia.action_date = ( " +
                            " select max(oia2.action_date)  " +
                            " from clw_order_item_action oia2 " +
                            " where oia2.order_item_id = oi.order_item_id " +
                            " and oia2.action_cd = 'Delivery Ref Number' )" +
                            " )as delivery_note_num "+
                            splSelSql + "\n" +
                            " FROM  " +
                            "CLW_ORDER ord, CLW_BUS_ENTITY site, CLW_ORDER_ITEM oi,CLW_ITEM ci, clw_address addr " +
                            (includeState ? ",(" + stateCriteria + ") sa " : "") +
                            splTableSql + "\n" +
                            "WHERE " +
                            ReportingUtils.getValidOrdersSQL("ord") + " AND " +
                            dateWhere +
//                            "ord.original_order_date between to_date('" + begDateS + "','" + dateFmt + "') AND to_date('" + endDateS + "','" + dateFmt + "') AND " +
                            " AND (oi.order_item_status_cd NOT IN ('CANCELLED') OR oi.order_item_status_cd IS NULL) AND  " +
                            "ord.site_id = site.bus_entity_id AND ord.order_id = oi.order_id AND ci.item_id = oi.item_id " +
                            splCondSql +
                            " AND ord.account_id = addr.bus_entity_id AND addr.address_type_cd='"+RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT+"' ";

            if (null != accountIdS && accountIdS.length() > 0) {
                detailSql += " AND ord.account_id in (" + accountIdS + ") ";
            }

            if (null != siteIdS && siteIdS.length() > 0) {
                detailSql += " AND ord.site_id in (" + siteIdS + ") ";
            }

            if (includeDist) {
                detailSql += " AND oi.dist_erp_num in (" + distErpNumsStr + ") ";
            }

            if (includeState) {
                detailSql += " AND site.bus_entity_id = sa.bus_entity_id ";
            }

            if (siteIdS == null || siteIdS.trim().length() == 0) {
                if (!showInactiveSites) {
                	detailSql += " and site.bus_entity_status_cd = 'ACTIVE' ";
                }
            }

            if (userDesc.hasAccessToAll() == false) {
              if (userDesc.hasStoreAccess()) {
            		detailSql += " and store_id in (" + userDesc.getAuthorizedSql() + ") ";            		  
          	  } else if (userDesc.hasAccountAccess()) {
                    detailSql += " and account_id in (" +
                            userDesc.getAuthorizedSql() + ") ";
                } else if (userDesc.hasSiteAccess()) {
                    detailSql += " and site_id in (" +
                            userDesc.getAuthorizedSql() + ") ";
                }
            }
            if (foraccounts != null && foraccounts.length() > 0) {
                detailSql += " and ord.account_id in (" + foraccounts + "  ) ";
            }

            detailSql += " ORDER BY site.short_desc, ord.order_num";

            HashMap catTotalsMap = new HashMap();
            stmt = con.prepareStatement(detailSql);
            if (includeState) {
                stmt.setString(1, siteStateOpt + "%");
            }
            rs = stmt.executeQuery();
            int prevOrderId = -1;
            LinkedList consolidated = new LinkedList();
            while (rs.next()) {
                Detail record = new Detail();
                record.storeId = rs.getInt("store_id");
                record.siteName = rs.getString("short_desc");
                if (includeState) {
                    record.siteShippingState = rs.getString("site_ship_state");
                }
                record.confirmNum = rs.getString("order_num");
                record.poNum = rs.getString("request_po_num");
                if (Utility.isSetForDisplay("request_po_num")) {
                    //if(record.poNum != null){
                    displayPONum = true;
                }
                int orderId = rs.getInt("order_id");                
                String categoryName = rs.getString("categoryName");
    			if (!Utility.isSet(categoryName)){
    				categoryName = rs.getString("categoryName2");
    				if (!Utility.isSet(categoryName)){
    					throw new Exception ("Failed to get category name for item_id="+rs.getInt("item_id") + " and order_id=" + orderId);
    				}
    			}
                record.myCategory = categoryName;
                record.sku = rs.getString("cust_item_sku_num");
                record.distSku = rs.getString("dist_item_sku_num");
                record.UOM = rs.getString("cust_item_uom");
                record.pack = rs.getString("cust_item_pack");
                record.itemSize = rs.getString("item_size");
                record.name = rs.getString("cust_item_short_desc");
                record.manuItemSkuNum = rs.getString("manu_item_sku_num");
                record.manuShortDesc = rs.getString("manu_item_short_desc");
                record.distShortDesc = rs.getString("dist_item_short_desc");
                record.lineTotal = rs.getBigDecimal("line_tot");
                record.saleType = rs.getString("sale_type_cd");
                String rebildOrder = rs.getString("rebill_order");
                record.orderType = "STANDARD";
                if (Utility.isTrue(rebildOrder)) {
                    //record.saleType = "REBILL";
                	record.orderType = "REBILL";
                } else {
                	
                }
                record.qty = rs.getInt("total_quantity_ordered");
                java.sql.Date orderDate = rs.getDate("revised_order_date") == null ? rs.getDate("original_order_date") : rs.getDate("revised_order_date");
                java.sql.Timestamp orderTime = rs.getTimestamp("revised_order_time") == null ? rs.getTimestamp("original_order_time") : rs.getTimestamp("revised_order_time");
                java.util.Date buffer = getDate(orderDate, orderTime);                
                buffer = convertDate(buffer, DEFAULT_TIMEZONE, tzSource);
                record.orderDate = new Date(buffer.getTime());
                record.orderId = orderId;
                record.itemPrice = rs.getBigDecimal("cust_contract_price");
                record.distCost = rs.getBigDecimal("dist_item_cost");
                record.itemId = rs.getInt("item_id");
                Integer acctInt = new Integer(rs.getInt("account_id"));
                
                /*
                 * Subtracting service fee -xpedx
                 */
                BigDecimal sfee = rs.getBigDecimal("service_fee");
                if(sfee!=null && sfee.compareTo(new BigDecimal(0))>0){
                	BigDecimal totSFee = sfee.multiply(new BigDecimal(record.qty));
                	if(record.lineTotal != null){
                		record.lineTotal = (record.lineTotal).subtract(totSFee);
                	}
                }
                     	
                if (accountMap.containsKey(acctInt)) {
                    record.account = (String) accountMap.get(acctInt);
                } else {
                    try {
                        BusEntityData acct = BusEntityDataAccess.select(con, acctInt.intValue());
                        accountMap.put(acctInt, acct.getShortDesc());
                        record.account = acct.getShortDesc();
                    } catch (DataNotFoundException e) {
                        accountMap.put(acctInt, "");
                        record.account = "";
                    }
                }
                record.primaryContactFName = rs.getString("name1");
                record.primaryContactLName = rs.getString("name2");
                record.orderCurrencyCd = rs.getString("currency_cd");
                if(rs.getInt("green_flag") > 0){
                	record.greenFl = "TRUE";
                }else{
                	record.greenFl = "FALSE";
                }
                if (isShowSPL()) {
                    record.spl = rs.getString("spl");
                }

                details.add(record);
                
                record.deliveryNoteNum = rs.getString("delivery_note_num");
                String orderTypeCd = rs.getString("order_type_cd");
                boolean cosolidatedFl =
                        RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderTypeCd);
                if (cosolidatedFl) {
                    consolidated.add(record);
                }

                //now group the category totals
                String key = record.account + "::" + record.siteName + "::" + record.confirmNum + "::" + record.getCategory() + "::" + record.orderCurrencyCd;

                if (catTotalsMap.containsKey(key)) {
                    ArrayList currDetail = (ArrayList) catTotalsMap.get(key);
                    currDetail.add(record);
                } else {
                    ArrayList newDetail = new ArrayList();
                    newDetail.add(record);
                    catTotalsMap.put(key, newDetail);
                }
            }
            rs.close();
            stmt.close();

            //now get the category totals from the map
            Collection groupedDetail = catTotalsMap.values();
            Iterator it = groupedDetail.iterator();
            while (it.hasNext()) {
                ArrayList grouping = (ArrayList) it.next();
                Iterator gIt = grouping.iterator();
                BigDecimal grpTotal = null;
                Detail det = null;
                while (gIt.hasNext()) {
                    det = (Detail) gIt.next();
                    if (grpTotal == null) {
                        grpTotal = det.lineTotal;
                    } else {
                        grpTotal = grpTotal.add(det.lineTotal);
                    }
                }
                if (det != null) {
                    CategoryTotal record = new CategoryTotal();
                    record.account = det.account;
                    record.primaryContactFName = det.primaryContactFName;
                    record.primaryContactLName = det.primaryContactLName;
                    record.category = det.getCategory();
                    record.confirmNum = det.confirmNum;
                    record.poNum = det.poNum;
                    record.siteName = det.siteName;
                    record.siteShippingState = det.siteShippingState;
                    record.categoryTotal = grpTotal;
                    record.orderDate = det.orderDate;
                    record.orderCurrencyCd = det.orderCurrencyCd;
                    catTotals.add(record);
                }
            }
            Collections.sort(catTotals, CATEGORY_TOTAL_COMPARE);

            //    	Create consolidate order page
            prevOrderId = -1;

            LinkedList consolidatedResult = new LinkedList();
            if (consolidated.size() > 0) {
                int counter = 0;
                String orderIdS = null;
                LinkedList wrkOrders = new LinkedList();
                for (Iterator iter = consolidated.iterator(); iter.hasNext();) {
                    Detail wrkOrder = (Detail) iter.next();
                    int orderId = wrkOrder.orderId;
                    if (prevOrderId != orderId) {
                        if (counter >= 999) {
                            LinkedList ll = getConsolidatedOrders(con, orderIdS, wrkOrders, tzSource);
                            consolidatedResult.addAll(ll);
                            counter = 0;
                            orderIdS = null;
                        }
                        prevOrderId = orderId;
                        counter++;
                        if (counter == 1) {
                            orderIdS = "" + wrkOrder.orderId;
                        } else {
                            orderIdS += "," + wrkOrder.orderId;
                        }
                    }
                    wrkOrders.add(wrkOrder);
                }
                if (orderIdS != null) {
                    LinkedList ll = getConsolidatedOrders(con, orderIdS, wrkOrders, tzSource);
                    consolidatedResult.addAll(ll);
                }

            }

            //find if any of the stores represented in this report have the sales tax
            //feature turned on.
            it = storeIdsInReport.iterator();
            mDisplaySalesTaxInfo = false;
            mDisplayDistInfo = false;
            while (it.hasNext()) {
                int storeId = ((Integer) it.next()).intValue();
                if (storeId > 0) {
                    StoreInfo si = getStoreInfo(storeId);
                    if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(si.getStoreType())) {
                        mDisplayDistInfo = true;
                    }
                    if (si.isTaxable()) {
                        mDisplaySalesTaxInfo = true;
                    }
                }
            }

            if (accountMap.keySet().size() > 1) {
                multiAccounts = true;
            } else {
                multiAccounts = false;
            }

            generateResults(resultV, con, consolidatedResult, details,
                    orderTotals, catTotals, tzSource.getID());
        } else {  // end of if (!Utility.isSet(distributorOpt) || Utility.isSet(distErpNumsStr)) 
            generateResults(resultV, con, new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), tzSource.getID());
        }

        for (int i = 0; i < resultV.size(); i++) {
            GenericReportResultView report = (GenericReportResultView) resultV.get(i);
            report.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            report.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            report.setFreezePositionRow(1);
        }
        return resultV;
    }


    private StoreInfo getStoreInfo(int storeId) throws Exception {
        if (storeId == 0) {
            return null;
        }
        PropertyUtil pru = new PropertyUtil(con);
        StoreInfo si = (StoreInfo) storeMap.get(new Integer(storeId));
        if (si == null) {
            boolean taxable = Utility.isTrue(pru.fetchValueIgnoreMissing(0, storeId, RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR));
            String storeTypeCd = pru.fetchValueIgnoreMissing(0, storeId, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
            int storeCatalogId = BusEntityDAO.getStoreCatalogId(con, storeId);
            si = new StoreInfo(storeId, storeTypeCd, storeCatalogId, taxable);
            storeMap.put(new Integer(storeId), si);
        }
        return si;
    }


    protected void generateResults
            (GenericReportResultViewVector resultV, Connection con,
             List consolidatedResult, List details, List orderTotals,
             List catTotals, String timeZone) throws Exception {

        if (consolidatedResult.size() > 0) {
            processList(consolidatedResult, resultV, "Consolidated_Details", getDetailConsolidatedReportHeader(), false, timeZone);
        }

        processList(details, resultV, "Details", getDetailReportHeader(), true, timeZone);

        processList(orderTotals, resultV, "Order_Totals", getOrderTotalsReportHeader(), false, timeZone);
        processList(catTotals, resultV, "Category_Totals", getCategoryTotalsReportHeader(), false, timeZone);
    }

    protected void processList
            (List toProcess,
             GenericReportResultViewVector resultV,
             String name, GenericReportColumnViewVector header,
             boolean alwaysCreate, String timeZone) {

        Iterator it = toProcess.iterator();
        if (alwaysCreate || it.hasNext()) {
            GenericReportResultView result =
                    GenericReportResultView.createValue();
            result.setTimeZone(timeZone);
            result.setTable(new ArrayList());
            while (it.hasNext()) {
                result.getTable().add(((aRecord) it.next()).toList());
            }
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            result.setName(name);
            resultV.add(result);
        }
    }

    private LinkedList getConsolidatedOrders(Connection pCon, String pOrderIdS, LinkedList pConsolidatedOrders, TimeZone tzSource)
            throws Exception {

        LinkedList replacedOrderLL = new LinkedList();

        String sql =
                " SELECT addr.name1, addr.name2,oa.order2_id, o.order_id,o.request_po_num, o.original_order_date, o.original_order_time, o.order_num, o.order_site_name, " +
                        " NVL(oi.cust_item_sku_num,item_sku_num) as cust_item_sku_num, " +
                        " oi.total_quantity_ordered, oi.order_item_status_cd, o.currency_cd " +
                        " FROM clw_order o, clw_order_item oi, clw_order_assoc oa, clw_address addr " +
                        " WHERE order2_id in (" + pOrderIdS + ")" +
                        " AND order1_id = o.order_id " +
                        " AND order_assoc_cd = '" + RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED + "' " +
                        " AND order_assoc_status_cd = '" + RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE + "' " +
                        " AND o.order_id = oi.order_id " +
                        " AND nvl(oi.order_item_status_cd,' ') != '" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED + "' " +
                        " AND oi.total_quantity_ordered > 0 " +
                        " and o.account_id = addr.bus_entity_id and addr.address_type_cd='"+RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT+"' "+
                        " ORDER BY oa.order2_id ";

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ConsolidatedDetail consDet = new ConsolidatedDetail();
            replacedOrderLL.add(consDet);
            consDet.orderId = rs.getInt("order2_id");
            consDet.replOrderId = rs.getInt("order2_id");
            consDet.replSiteName = rs.getString("order_site_name");
            consDet.replOrderNum = rs.getString("order_num");
            consDet.poNum = rs.getString("request_po_num");
            consDet.sku = rs.getString("cust_item_sku_num");
            consDet.replQty = rs.getInt("total_quantity_ordered");
            java.util.Date buffer = getDate(rs
                    .getDate("original_order_date"), rs
                    .getTimestamp("original_order_time"));
            buffer = convertDate(buffer, DEFAULT_TIMEZONE, tzSource);
            consDet.replOrderDate = new Date(buffer.getTime());
            consDet.orderCurrencyCd = rs.getString("currency_cd");
        }
        rs.close();
        stmt.close();

        Object[] replacedOrderA = replacedOrderLL.toArray();
        LinkedList consolidatedOrders = new LinkedList();
        int prevOrderId = -1;
        int replOrderInd = 0;
        for (Iterator iter = pConsolidatedOrders.iterator(); iter.hasNext();) {
            Detail det = (Detail) iter.next();
            String sku = det.sku;
            int orderId = det.orderId;
            if (prevOrderId != orderId) {
                prevOrderId = orderId;
                replOrderInd = getIndex(replacedOrderA, orderId, 0, replacedOrderA.length);
            }
            ConsolidatedDetail consolidatedOrder = null;
            if (replOrderInd >= 0) {
                for (int ii = replOrderInd; ii < replacedOrderA.length; ii++) {
                    ConsolidatedDetail cd = (ConsolidatedDetail) replacedOrderA[ii];
                    if (orderId != cd.orderId) {
                        break;
                    }
                    if (sku.equals(cd.sku)) {
                        consolidatedOrder = cd;
                        consolidatedOrders.add(consolidatedOrder);
                        consolidatedOrder.account = det.account;
                        consolidatedOrder.primaryContactFName = det.primaryContactFName;
                        consolidatedOrder.primaryContactLName = det.primaryContactLName;
                        consolidatedOrder.siteName = det.siteName;
                        consolidatedOrder.confirmNum = det.confirmNum;
                        consolidatedOrder.poNum = det.poNum;
                        consolidatedOrder.myCategory = det.getCategory();
                        consolidatedOrder.UOM = det.UOM;
                        consolidatedOrder.pack = det.pack;
                        consolidatedOrder.itemSize = det.itemSize;
                        consolidatedOrder.name = det.name;
                        consolidatedOrder.saleType = det.saleType;
                        consolidatedOrder.qty = det.qty;
                        consolidatedOrder.lineTotal = det.lineTotal;
                        consolidatedOrder.orderDate = det.orderDate;
                        consolidatedOrder.itemPrice = det.itemPrice;
                        consolidatedOrder.distCost = det.distCost;
                        consolidatedOrder.orderId = det.orderId;
                        consolidatedOrder.storeId = det.storeId;
                        consolidatedOrder.distShortDesc = det.distShortDesc;
                        consolidatedOrder.siteShippingState = det.siteShippingState;
                        consolidatedOrder.distSku = det.distSku;
                        consolidatedOrder.manuShortDesc = det.manuShortDesc;
                        consolidatedOrder.manuItemSkuNum = det.manuItemSkuNum;
                        consolidatedOrder.orderCurrencyCd = det.orderCurrencyCd;
                    }
                }
            }
            if (consolidatedOrder == null) {
                consolidatedOrder = new ConsolidatedDetail();
                consolidatedOrders.add(consolidatedOrder);
                consolidatedOrder.account = det.account;
                consolidatedOrder.primaryContactFName = det.primaryContactFName;
                consolidatedOrder.primaryContactLName = det.primaryContactLName;
                consolidatedOrder.siteName = det.siteName;
                consolidatedOrder.confirmNum = det.confirmNum;
                consolidatedOrder.poNum = det.poNum;
                consolidatedOrder.myCategory = det.getCategory();
                consolidatedOrder.UOM = det.UOM;
                consolidatedOrder.pack = det.pack;
                consolidatedOrder.itemSize = det.itemSize;
                consolidatedOrder.name = det.name;
                consolidatedOrder.saleType = det.saleType;
                consolidatedOrder.qty = det.qty;
                consolidatedOrder.lineTotal = det.lineTotal;
                consolidatedOrder.orderDate = det.orderDate;
                consolidatedOrder.itemPrice = det.itemPrice;
                consolidatedOrder.distCost = det.distCost;
                consolidatedOrder.orderId = det.orderId;
                consolidatedOrder.storeId = det.storeId;
                consolidatedOrder.distShortDesc = det.distShortDesc;
                consolidatedOrder.siteShippingState = det.siteShippingState;
                consolidatedOrder.distSku = det.distSku;
                consolidatedOrder.manuShortDesc = det.manuShortDesc;
                consolidatedOrder.manuItemSkuNum = det.manuItemSkuNum;
                consolidatedOrder.orderCurrencyCd = det.orderCurrencyCd;
            }
        }
        return consolidatedOrders;

    }

    int getIndex(Object[] pReplacedOrderA, int pOrderId, int pBegInd, int pEndInd) {
        int ind = (pEndInd + pBegInd) / 2;
        ConsolidatedDetail cd = (ConsolidatedDetail) pReplacedOrderA[ind];
        if (cd.orderId == pOrderId) {
            for (; ind >= 0; ind--) {
                cd = (ConsolidatedDetail) pReplacedOrderA[ind];
                if (cd.orderId != pOrderId) {
                    return (ind + 1);
                }
            }
            return 0;
        }
        if (pBegInd == pEndInd) {
            return -1;
        }
        if (cd.orderId > pOrderId) {
            return getIndex(pReplacedOrderA, pOrderId, pBegInd, ind - 1);
        }
        return getIndex(pReplacedOrderA, pOrderId, ind + 1, pEndInd);
    }

    private GenericReportColumnViewVector getCategoryTotalsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        if(isUseMultiAcctsFlag()){
        	if (multiAccounts) {
        		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "20", false));
        	}
        }else{
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "20", false));
        }       
        if(isShowPrimaryContact()){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact First Name",0,255,"VARCHAR2","10", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact Last Name",0,255,"VARCHAR2","10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "20", false));
        if (includeState) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "5", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "7", false));
        if (displayPONum) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "7", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2", "12", false));
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Category_Total$", 2, 20, "NUMBER", "10", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "rowInfo_Currency Code", 0, 255, "VARCHAR2", "6", false));
        }
        return header;
    }

    protected GenericReportColumnViewVector getOrderTotalsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(isUseMultiAcctsFlag()){
        	if (multiAccounts) {
        		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        	}
        }else{
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        }       
        if(isShowPrimaryContact()){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact First Name",0,255,"VARCHAR2","10", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact Last Name",0,255,"VARCHAR2","10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "10", false));
        if (includeState) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "5", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Budget Ref", 0, 255, "VARCHAR2", "15", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sale_Type", 0, 255, "VARCHAR2", "8", false));
        if (includeDist) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "10", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order_Sub_Total$", 2, 20, "NUMBER", "10", false));
        }
        if (mDisplaySalesTaxInfo && showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Tax$", 2, 20, "NUMBER", "8", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Freight$", 2, 20, "NUMBER", "8", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Handling$", 2, 20, "NUMBER", "10", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Discount$", 2, 20, "NUMBER", "10", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "rowInfo_Currency Code", 0, 255, "VARCHAR2", "8", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Type", 0, 2000, "VARCHAR2", "10", false));
        return header;
    }

    protected GenericReportColumnViewVector getDetailReportHeader() {
    	GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(isUseMultiAcctsFlag()){
        	if (multiAccounts) {
        		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        	}
        }else{
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        }       
        if(isShowPrimaryContact()){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact First Name",0,255,"VARCHAR2","10", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact Last Name",0,255,"VARCHAR2","10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "10", false));
        if (includeState) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "5", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "7", false));
        if (isShowDeliveryNoteNum()) {
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Delivery_Note_Num", 0, 255, "VARCHAR2", "10", false));
        }
        if (displayPONum) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "7", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "8", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2", "12", false));
        if (mDisplayDistInfo) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sys_Sku", 0, 255, "VARCHAR2", "10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sku", 0, 255, "VARCHAR2", "6", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "4", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item_Size", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Man_Sku", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "6", false));
        if (includeDist || mDisplayDistInfo) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "6", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Name", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Green_Item", 0, 255, "VARCHAR2", "8", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sale_Type", 0, 255, "VARCHAR2", "8", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Qty", 0, 20, "NUMBER", "4", false));
        
        if(isShowCost() && showPrice){
        	header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Dist Cost", 2, 20, "NUMBER", "8", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Cust Price", 2, 20, "NUMBER", "8", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Line_Total$", 2, 20, "NUMBER", "8", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "rowInfo_Currency Code", 0, 255, "VARCHAR2", "6", false));
        }
        if (isShowSPL()) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SPL", 0, 255, "VARCHAR2", "6", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Type", 0, 2000, "VARCHAR2", "10", false));
        return header;
    }

    protected interface aRecord {
        ArrayList toList();
    }

    private GenericReportColumnViewVector getDetailConsolidatedReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(isUseMultiAcctsFlag()){
        	if (multiAccounts) {
        		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        	}
        }else{
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        }       
        if(isShowPrimaryContact()){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact First Name",0,255,"VARCHAR2","10", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact Last Name",0,255,"VARCHAR2","10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "20", false));
        if (includeState) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "5", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "12", false));
        if (displayPONum) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "15", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2", "20", false));
        if (mDisplayDistInfo) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sys_Sku", 0, 255, "VARCHAR2", "10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sku", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "7", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item_Size", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Man_Sku", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "20", false));
        if (includeDist || mDisplayDistInfo) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "20", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Name", 0, 255, "VARCHAR2", "15", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sale_Type", 0, 255, "VARCHAR2", "8", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Sum_Qty", 0, 20, "NUMBER", "8", false));
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Sum_Line_Total$", 2, 20, "NUMBER", "8", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Original_Site_Name", 0, 20, "NUMBER", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Original_Confirm_Num", 0, 20, "NUMBER", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Original_Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Original_Qty", 0, 20, "NUMBER", "20", false));
        
        if(isShowCost() && showPrice){
        	header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Dist Cost", 2, 20, "NUMBER", "8", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Cust Price", 2, 20, "NUMBER", "8", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Original_Line_Total$", 2, 20, "NUMBER", "8", false));
        }
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "rowInfo_Currency Code", 0, 255, "VARCHAR2", "12", false));
        }
        if (isShowSPL()) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SPL", 0, 255, "VARCHAR2", "6", false));
        }
        return header;
    }

    protected class CategoryTotal implements aRecord {
        String siteName;
        String confirmNum;
        String poNum;
        String category;
        String account;
        String primaryContactFName;
        String primaryContactLName;
        String orderCurrencyCd;
        BigDecimal categoryTotal;
        Date orderDate;
        String siteShippingState;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            if(isUseMultiAcctsFlag()){
            	if (multiAccounts) {
            		list.add(account);
            	}
            }else{
            	list.add(account);
            }
            if(isShowPrimaryContact()){
            	list.add(primaryContactFName);
            	list.add(primaryContactLName);
            }
            list.add(siteName);
            if (includeState) {
                list.add(siteShippingState);
            }
            list.add(confirmNum);
            if (displayPONum) {
                list.add(poNum);
            }
            list.add(orderDate);
            list.add(category);
            if (showPrice) {
                list.add(categoryTotal);
            }
            // Add row formating information
            if (showPrice) {
                list.add("rowInfo_currencyCd=" + orderCurrencyCd);
            }
            return list;
        }
    }

    protected class OrderTotal implements aRecord {
        String siteName;
        String projectCode;
        String confirmNum;
        String poNum;
        String saleType;
        BigDecimal orderTotal;
        BigDecimal taxTotal;
        BigDecimal totalFreightCost;
        BigDecimal totalMiscCost;
        BigDecimal discount;
        Date orderDate;
        String account;
        String primaryContactFName;
        String primaryContactLName;
        String orderCurrencyCd;
        public String distName;
        public String siteShippingState;
        String orderType;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            if(isUseMultiAcctsFlag()){
            	if (multiAccounts) {
            		list.add(account);
            	}
            }else{
            	list.add(account);
            }
            if(isShowPrimaryContact()){
            	list.add(primaryContactFName);
            	list.add(primaryContactLName);
            }
            list.add(siteName);
            if (includeState) {
                list.add(siteShippingState);
            }
            list.add(projectCode);
            list.add(confirmNum);
            list.add(orderDate);
            list.add(poNum);
            list.add(saleType);

            if (includeDist) {
                list.add(distName);
            }

            if (showPrice) {
                list.add(orderTotal);
            }

            if (mDisplaySalesTaxInfo && showPrice) {
                list.add(taxTotal);
            }
            if (showPrice) {
                list.add(totalFreightCost);
            }
            if (showPrice) {
                list.add(totalMiscCost);
            }
            if (showPrice) {
                list.add(discount);
            }
            // Add row formating information
            if (showPrice) {
                list.add("rowInfo_currencyCd=" + orderCurrencyCd);
            }
            list.add(orderType);
            return list;
        }
    }

    protected class Detail implements aRecord {
        int storeId = 0;
        int itemId = 0;
        String siteName;
        String confirmNum;
        String poNum;
        String myCategory;
        String sku;
        String distSku;
        String UOM;
        String pack;
        String itemSize;
        String name;
        String saleType;
        int qty;
        BigDecimal lineTotal;
        Date orderDate;
        BigDecimal itemPrice;
        BigDecimal distCost;
        int orderId;
        String manuItemSkuNum;
        String manuShortDesc;
        String distShortDesc;
        String account;
        String primaryContactFName;
        String primaryContactLName;
        String orderCurrencyCd;
        String greenFl;
        public String siteShippingState;
        String deliveryNoteNum;
        String spl;
        String orderType;

        protected String getCategory() {

            if (myCategory == null) {
                if (storeId > 0 && itemId > 0) {
                    StoreInfo si = null;
                    try {
                        si = getStoreInfo(storeId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (si != null && si.getStoreCatalogId() != 0) {
                        Object key = storeId + "::" + itemId;
                        myCategory = (String) masterItemCategoryMap.get(key);
                        if (myCategory == null) {
                            DBCriteria crit = new DBCriteria();
                            String ia = ItemAssocDataAccess.CLW_ITEM_ASSOC;
                            String category = ItemDataAccess.CLW_ITEM;

                            crit.addJoinCondition(ia, ItemAssocDataAccess.ITEM2_ID, category, ItemDataAccess.ITEM_ID);
                            crit.addJoinTableEqualTo(ia, ItemAssocDataAccess.ITEM1_ID, itemId);
                            crit.addJoinTableEqualTo(ia, ItemAssocDataAccess.CATALOG_ID, si.getStoreCatalogId());
                            crit.addDataAccessForJoin(new ItemDataAccess());
                            try {
                                List categoryVec = JoinDataAccess.select(con, crit);
                                if (categoryVec != null && !categoryVec.isEmpty()) {
                                    myCategory = ((ItemData) ((List) categoryVec.get(0)).get(0)).getShortDesc();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();

                            }

                            if (myCategory == null) {
                                myCategory = "Other";
                            }
                            masterItemCategoryMap.put(key, myCategory);
                        }
                    }
                }
            }
            if (myCategory == null) {
                myCategory = "Other";
            }
            return myCategory;
        }

        /**
         * Figures out wheather to display the dist info for this line
         */
        protected boolean displayDistInfo() {
            try {
                StoreInfo si = getStoreInfo(storeId);
                if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(si.getStoreType())) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            if(isUseMultiAcctsFlag()){
            	if (multiAccounts) {
            		list.add(account);
            	}
            }else{
            	list.add(account);
            }
            if(isShowPrimaryContact()){
            	list.add(primaryContactFName);
            	list.add(primaryContactLName);
            }
            list.add(siteName);
            if (includeState) {
                list.add(siteShippingState);
            }
            list.add(confirmNum);
            if(isShowDeliveryNoteNum()){
            	list.add(deliveryNoteNum);
            }
            if (displayPONum) {
                list.add(poNum);
            }
            list.add(orderDate);
            list.add(getCategory());
            list.add(sku);
            if (mDisplayDistInfo) {
                if (!displayDistInfo() || distSku == null) {
                    distSku = "";
                }
                list.add(distSku);
            }
            list.add(UOM);
            list.add(pack);
            list.add(itemSize);
            if (manuItemSkuNum == null) {
                list.add("");
            } else {
                list.add(manuItemSkuNum);
            }
            if (manuShortDesc == null) {
                list.add("");
            } else {
                list.add(manuShortDesc);
            }
            if (mDisplayDistInfo || includeDist) {
                if ((!includeDist && !displayDistInfo()) || distSku == null) {
                    distShortDesc = "";
                }
                list.add(distShortDesc);
            }
            list.add(name);
            list.add(greenFl);
            list.add(saleType);
            list.add(new Integer(qty));
            
            if(isShowCost() && showPrice){
            	//Bug # 5111
            	list.add(distCost);
            	list.add(itemPrice);
            	
            }
            if (showPrice) {
                list.add(lineTotal);
            }
            // Add row formating information
            if (showPrice) {
                list.add("rowInfo_currencyCd=" + orderCurrencyCd);
            }
            if (isShowSPL()) {
                list.add(spl);
            }
            list.add(orderType);
            return list;
        }
    }

    /**
     * cached instance of store data.  Lightweight version of the @see StoreData object
     */
    private class StoreInfo {
        private String storeTypeCd;
        private int storeId;
        private int storeCatalogId;
        private boolean taxable;


        private int getStoreId() {
            return storeId;
        }

        private String getStoreType() {
            return storeTypeCd;
        }

        private int getStoreCatalogId() {
            return storeCatalogId;
        }

        private boolean isTaxable() {
            return taxable;
        }

        private StoreInfo(int pStoreId, String pStoreTypeCd, int pStoreCatalogId, boolean pTaxable) {
            storeId = pStoreId;
            storeTypeCd = pStoreTypeCd;
            storeCatalogId = pStoreCatalogId;
            taxable = pTaxable;
        }

        public String toString() {
            return "storeId = [" + storeId + "]::storeCatalogId = [" + storeCatalogId + "]::storeTypeCd = [" + storeTypeCd + "]";
        }
    }

    protected class ConsolidatedDetail extends Detail implements aRecord {
        int replOrderId;
        String replSiteName;
        String replOrderNum;
        String orderCurrencyCd;
        int replQty;
        Date replOrderDate;

        public ArrayList toList() {
           // ArrayList list = super.toList();
//////////////
            ArrayList list = new ArrayList();
            if(isUseMultiAcctsFlag()){
            	if (multiAccounts) {
            		list.add(account);
            	}
            }else{
            	list.add(account);
            }
            if(isShowPrimaryContact()){
            	list.add(primaryContactFName);
            	list.add(primaryContactLName);
            }
            list.add(siteName);
            if (includeState) {
                list.add(siteShippingState);
            }
            list.add(confirmNum);
            if (displayPONum) {
                list.add(poNum);
            }
            list.add(orderDate);
            list.add(getCategory());
            list.add(sku);
            if (mDisplayDistInfo) {
                if (!super.displayDistInfo() || distSku == null) {
                    distSku = "";
                }
                list.add(distSku);
            }
            list.add(UOM);
            list.add(pack);
            list.add(itemSize);
            if (manuItemSkuNum == null) {
                list.add("");
            } else {
                list.add(manuItemSkuNum);
            }
            if (manuShortDesc == null) {
                list.add("");
            } else {
                list.add(manuShortDesc);
            }
            if (mDisplayDistInfo || includeDist) {
                if ((!includeDist && !super.displayDistInfo()) || distSku == null) {
                    distShortDesc = "";
                }
                list.add(distShortDesc);
            }
            list.add(name);
            list.add(saleType);
            list.add(new Integer(qty));
            
            if (showPrice) {
                list.add(lineTotal);
            }
////////////////		   
            list.add(replSiteName);
            list.add(replOrderNum);
            list.add(replOrderDate);
            list.add(new Integer(replQty));
            if(isShowCost() && showPrice){
             	list.add(itemPrice);
            	list.add(distCost);
            }
            BigDecimal replTotalPrice =
                    (itemPrice == null) ?
                            new BigDecimal(0) :
                            itemPrice.multiply(new BigDecimal(replQty));
            if (showPrice) {
                list.add(replTotalPrice);
            }
            // Add row formating information
            if (showPrice) {
                list.add("rowInfo_currencyCd=" + orderCurrencyCd);
            }
            if (isShowSPL()) {
                list.add(spl);
            }
          return list;
        }
    }

    static final Comparator CATEGORY_TOTAL_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            CategoryTotal c1 = (CategoryTotal) o1;
            CategoryTotal c2 = (CategoryTotal) o2;
            if (c1.siteName == null) {
                c1.siteName = "";
            }
            if (c2.siteName == null) {
                c2.siteName = "";
            }
            if (c1.confirmNum == null) {
                c1.confirmNum = "";
            }
            if (c2.confirmNum == null) {
                c2.confirmNum = "";
            }
            if (c1.category == null) {
                c1.category = "";
            }
            if (c2.category == null) {
                c2.category = "";
            }
            int val = c1.siteName.compareToIgnoreCase(c2.siteName);
            if (val != 0) {
                return val;
            }
            val = c1.confirmNum.compareToIgnoreCase(c2.confirmNum);
            if (val != 0) {
                return val;
            }
            val = c1.category.compareToIgnoreCase(c2.category);
            return val;
        }
    };

    public final static String getDateWhere(String begDateS, String endDateS,
            String dateFmt, TimeZone tzSource, TimeZone tzTarget)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        java.util.Date startDate = sdf.parse(begDateS);
        java.util.Date endDateOrig = sdf.parse(endDateS);
        java.util.Date endDate = addDays(endDateOrig,1);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(PATTERN);
        java.util.Date startDateTarget = convertDate(startDate, tzSource,
                tzTarget);
        java.util.Date endDateTarget = convertDate(endDate, tzSource,
                tzTarget);
        String startDateTargetS = sdfTarget.format(startDateTarget);
        String endDateTargetS = sdfTarget.format(endDateTarget);
        String sqlBetween = " BETWEEN TO_DATE('"
            + startDateTargetS
            + "','MM/dd/yyyy HH24:MI') AND TO_DATE('"
            + endDateTargetS
            + "','MM/dd/yyyy HH24:MI') ";
        return " (" 
        		+ "(ord.revised_order_date is null AND TO_DATE(TO_CHAR(ord.original_order_date, 'MM/dd/yyyy') || TO_CHAR(ord.original_order_time, 'HH24:MI'), 'MM/dd/yyyyHH24:MI')"
                + sqlBetween 
                + ") or (ord.revised_order_date is not null and TO_DATE((TO_CHAR(ord.revised_order_date, 'MM/dd/yyyy') || ' 00:01'),'MM/dd/yyyy HH24:MI')"
                + sqlBetween 
                + ")) ";
    }

    private final static int ONE_SECOND = 1000;

    private final static int ONE_MINUTE = ONE_SECOND * 60;

    private final static int ONE_HOUR = ONE_MINUTE * 60;

    public final static java.util.Date convertDate(java.util.Date source,
            TimeZone tzSource, TimeZone tzTarget) {
        long timeInMillis = source.getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(source);
        int millis = c.get(Calendar.HOUR_OF_DAY) * ONE_HOUR
                + c.get(Calendar.MINUTE) * ONE_MINUTE;
        long delta1 = tzSource.getOffset(c.get(Calendar.ERA), c
                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
                millis);
        int delta2 = tzTarget.getOffset(c.get(Calendar.ERA), c
                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
                millis);
        return new java.util.Date(timeInMillis + (delta2 - delta1));
    }

    private final static SimpleDateFormat SDF_DATE = new SimpleDateFormat("MM/dd/yyyy");
    private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
    private final static SimpleDateFormat SDF_FULL = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private final static java.util.Date getDate(java.util.Date date,
            Timestamp time) throws Exception {
    	try{
    		return SDF_FULL.parse(SDF_DATE.format(date) + " " + SDF_TIME.format(time));
    	}catch(Exception e){
    		return date;
    	}
    }
    
    /**
     * Add nDays to input date.
     *
     * @param nDays  Number of days to add. May be negative.
     * @return  Date as requested.
     */
    public static java.util.Date addDays(java.util.Date date, int nDays)
    {
    	if (date == null)
        throw new IllegalArgumentException("Date is null in ReSaleOrderReport.addDays()");
      // Create a calendar based on given date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      // Add/subtract the specified number of days
      calendar.add(Calendar.DAY_OF_MONTH, nDays);
      return calendar.getTime();
    }




}
