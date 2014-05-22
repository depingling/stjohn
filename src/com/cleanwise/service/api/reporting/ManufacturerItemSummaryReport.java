package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * @author bstevens
 */
public class ManufacturerItemSummaryReport implements GenericReportMulti {
    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

    boolean mDisplaySalesTaxInfo;
    boolean mDisplayDistInfo;
    HashMap storeMap = new HashMap();
    HashMap masterItemCategoryMap = new HashMap();
    Connection con;
    private boolean showPrice;

    public com.cleanwise.service.api.value.GenericReportResultViewVector process(com.cleanwise.service.api.util.ConnectionContainer pCons,
         com.cleanwise.service.api.value.GenericReportData pReportData,
         java.util.Map pParams)
            throws Exception {
        TimeZone tzSource = DEFAULT_TIMEZONE;
        APIAccess factory = new APIAccess();

        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV =
                new GenericReportResultViewVector();
        //Lists of report results
        ArrayList summaryBySites = new ArrayList();
        ArrayList summaryByItem = new ArrayList();
        ArrayList details = new ArrayList();
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        String dateFmt = (String) pParams.get("DATE_FMT");

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }
        if(!Utility.isSet(begDateS) && !Utility.isSet(endDateS)) {
            String mess = "^clw^No date interval is set^clw^";
            throw new Exception(mess);
		}
        if(!Utility.isSet(begDateS)) {
            String mess = "^clw^Begin date is not set^clw^";
            throw new Exception(mess);
		}
        if(!Utility.isSet(endDateS)) {
            String mess = "^clw^End date is not set^clw^";
            throw new Exception(mess);
		}
        if (!ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (!ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }


        String dateWhere = getDateWhere(begDateS, endDateS, dateFmt, tzSource, DEFAULT_TIMEZONE);

        String manufIdS = (String)ReportingUtils.getParam(pParams, "MANUFACTURER");
        String itemIdS = (String)ReportingUtils.getParam(pParams, "LOCATE_ITEM_MULTI");
        String siteIdS = (String)ReportingUtils.getParam(pParams, "LOCATE_SITE_MULTI");

        String showInactiveSitesS = (String) pParams.get("showInactiveSites");
        boolean showInactiveSites = Utility.isOn(showInactiveSitesS);

        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc = repUtil.getUserAccessDescription(pParams, con);

        UserRightsTool urt = new UserRightsTool(userDesc.getUserData());
        showPrice = urt.getShowPrice();

        // title
        String manufName = "";
        String siteName = "";
        String itemName = "";
        String reportName = "Manufacturer Item Summary Report";


        String siteListSql = " ( select bus_entity_id as site_id from clw_user_assoc ua, \n" +
                            "  clw_user u where u.user_id = ua.user_id \n" +
                            " and  u.user_id = " + userDesc.getUserData().getUserId() + "\n"+
                            " and user_assoc_cd = 'SITE')  site_list   \n";


        String manufSql = "";
        // get all manufactures for store
        BusEntityDataVector mans = factory.getManufacturerAPI().getManufacturerByUserId(userDesc.getUserData().getUserId()+"");
        if (Utility.isSet(manufIdS)) {
            manufSql = " (select * from clw_bus_entity where bus_entity_id = " + manufIdS + " ) manu, \n";
            int manufId = 0;
            try {
                manufId = Integer.parseInt(manufIdS);
            } catch (Exception e) {
                manufId = 0;
            }
            if (manufId > 0) {
                for (Iterator iter=mans.iterator(); iter.hasNext(); ) {
                    BusEntityData manufD = (BusEntityData) iter.next();
                    if (manufD.getBusEntityId() == manufId) {
                        manufName = manufD.getShortDesc();
                        break;
                    }
                }
            }
		} else {
            manufSql = " (select * from clw_bus_entity where bus_entity_type_cd = 'MANUFACTURER' ) manu, \n";
		}

        // get site name
        if (Utility.isSet(siteIdS) && siteIdS.indexOf(",") == -1) {
            try {
                int siteId = (new Integer(siteIdS)).intValue();
                BusEntityData site = BusEntityDataAccess.select(con, siteId);
                siteName = site.getShortDesc();
            } catch (Exception e) {
            }
        }

        // get item name
        if (Utility.isSet(itemIdS) && itemIdS.indexOf(",") == -1) {
            try {
                int itemId = (new Integer(itemIdS)).intValue();
                ItemData item = ItemDataAccess.select(con, itemId);
                itemName = item.getShortDesc();
            } catch (Exception e) {
            }
        }


// 1 summary by sites

        String summaryBySitesSql =
            "SELECT  \n" +
                    " manu.short_desc as manuf, \n" +
                    " oa.short_desc as site_name, \n" +
                    " oa.address1 as addr, \n" +
                    " oa.city as city, \n" +
                    " oa.state_province_cd as state, \n" +
                    " sum(oi.total_quantity_ordered) as total_qty, \n" +
                    " sum(oi.cust_contract_price * oi.total_quantity_ordered) as total_price \n" +
            "FROM  \n" +
                    " clw_item i, \n" +
                    " clw_item_mapping im,  \n" +
                    " clw_order  o, \n" +
                    " clw_Order_address oa, \n" +
                    " clw_order_item oi,   \n" +
                    " CLW_BUS_ENTITY site, \n" +

                    manufSql +

                    siteListSql +

            "WHERE   " +
                    "oa.address_type_cd = 'SHIPPING' and \n" +
                    "oa.order_id = o.order_id and  \n" +
                    "i.item_id = im.item_id  and \n" +
                    "manu.bus_entity_id = im.bus_entity_id   and \n" +
                    "im.item_mapping_cd = 'ITEM_MANUFACTURER'  and \n" +
                    "site_list.site_id = o.site_id  and \n" +
                    "o.site_id = site.bus_entity_id and \n" +
                    "o.order_id = oi.order_id  and \n" +
                    "oi.item_id = im.item_id  and \n" +
                    "o.order_status_cd in ('ERP Released', 'Invoiced')  and \n" +
                    dateWhere + " and \n" +
                    "oi.order_item_status_cd != 'CANCELLED'  \n";


            if (null != siteIdS && siteIdS.trim().length() > 0) {
                summaryBySitesSql += " AND o.site_id in (" + siteIdS + ") \n";
            }
            if (siteIdS == null || siteIdS.trim().length() == 0) {
                if (!showInactiveSites) {
                    summaryBySitesSql += " and site.bus_entity_status_cd = 'ACTIVE' \n";
                }
            }


            if (itemIdS != null && itemIdS.length() > 0) {
                summaryBySitesSql += " AND i.item_id in (" + itemIdS + ") \n";
            }

            summaryBySitesSql +=
                "GROUP BY \n" +
                       "manu.short_desc, oa.short_desc, oa.address1, oa.city,  oa.state_province_cd, oa.postal_code \n" +
                "ORDER BY  manu.short_desc, oa.short_desc\n";



            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(summaryBySitesSql);
            while (rs.next()) {
                ManufacturerItemSummaryReport.SummaryBySites record = new ManufacturerItemSummaryReport.SummaryBySites();
                record.manuf = rs.getString("manuf");
                record.siteName = rs.getString("site_name");
                record.address1 = rs.getString("addr");
                record.city = rs.getString("city");
                record.state = rs.getString("state");
                record.totalQty = rs.getInt("total_qty");
                record.totalPrice = rs.getBigDecimal("total_price");

                summaryBySites.add(record);
            }
            rs.close();
            stmt.close();

// 2 summary by item
            String summaryByItemSql =
                    "SELECT " +
                            " manu.short_desc as manuf, \n" +
                            " im.item_num as manuf_sku, \n" +
                            " i.short_desc as item_desc, \n" +
                            " uom.clw_value as uom, \n" +
                            " pack.clw_value as pack, \n" +
                            " sum(oi.total_quantity_ordered) as total_qty, \n" +
                            " sum(oi.cust_contract_price * oi.total_quantity_ordered) as total_price\n" +
                     " FROM \n" +
                            " clw_item i, (select * from clw_item_meta where name_value = 'UOM') uom, \n" +
                            " (select * from clw_item_meta where name_value = 'PACK') pack, \n" +
                            " clw_item_mapping im,   clw_order  o,  clw_bus_entity account, clw_order_item oi, \n" +
                            " clw_Order_address oa, \n" +
                            "CLW_BUS_ENTITY site, \n"  +

                            manufSql +

                            siteListSql +

                      " WHERE \n" +
                            " i.item_id = uom.item_id (+) and \n" +
                            " i.item_id = pack.item_id (+) and \n" +
                            " i.item_id = im.item_id \n" +
                            " and manu.bus_entity_id = im.bus_entity_id \n" +
                            " and im.item_mapping_cd = 'ITEM_MANUFACTURER' \n" +
                            " and site_list.site_id = o.site_id \n" +
                            " and account.bus_entity_id = o.account_id \n" +
                            " and o.order_id = oi.order_id \n" +
                            " and oa.address_type_cd = 'SHIPPING' \n" +
                            " and oa.order_id = o.order_id \n" +

                            " and o.site_id = site.bus_entity_id  \n" +

                            " and oi.item_id = im.item_id \n" +
                            " and o.order_status_cd in ('ERP Released', 'Invoiced') \n" +
                            " and " + dateWhere +"\n"+ 
                            " and oi.order_item_status_cd != 'CANCELLED' \n";


            if (null != siteIdS && siteIdS.length() > 0) {
                summaryByItemSql += " AND o.site_id in (" + siteIdS + ") \n";
            }

            if (siteIdS == null || siteIdS.trim().length() == 0) {
                if (!showInactiveSites) {
                    summaryByItemSql += " and site.bus_entity_status_cd = 'ACTIVE' \n";
                }
            }


            if (itemIdS != null && itemIdS.length() > 0) {
                summaryByItemSql += " AND i.item_id in (" + itemIdS + ") \n";
            }


            summaryByItemSql += " GROUP BY manu.short_desc, im.item_num , i.short_desc, uom.clw_value, pack.clw_value \n";
            summaryByItemSql += " ORDER BY manu.short_desc, im.item_num, i.short_desc\n";


            stmt = con.createStatement();
            rs = stmt.executeQuery(summaryByItemSql);
            while (rs.next()) {
                ManufacturerItemSummaryReport.SummaryByItem record = new ManufacturerItemSummaryReport.SummaryByItem();
                record.manuf = rs.getString("manuf");
                record.manufSku = rs.getString("manuf_sku");
                record.itemDesc = rs.getString("item_desc");
                record.UOM = rs.getString("uom");
                record.pack = rs.getString("pack");
                record.totalQty = rs.getInt("total_qty");
                record.totalPrice = rs.getBigDecimal("total_price");

                summaryByItem.add(record);
            }
            rs.close();
            stmt.close();

    // 3 details
        String detailsSql =
            "SELECT  \n" +
                    " manu.short_desc as manuf, \n" +
                    " oa.short_desc as site_name, \n" +
                    " oa.address1 as addr, \n" +
                    " oa.city as city, \n" +
                    " oa.state_province_cd as state, \n" +
                    " im.item_num as manuf_sku, \n" +
                    " i.short_desc as item_desc, \n" +
                    " uom.clw_value as uom, \n" +
                    " pack.clw_value as pack, \n" +
                    " sum(oi.total_quantity_ordered) as total_qty, \n" +
                    " sum(oi.cust_contract_price * oi.total_quantity_ordered) as total_price \n" +
            "FROM  \n" +
                    " clw_item i, (select * from clw_item_meta where name_value = 'UOM') uom, \n" +
                    " (select * from clw_item_meta where name_value = 'PACK') pack, \n" +
                    " clw_item_mapping im,  \n" +
                    " clw_order  o, \n" +
                    " clw_Order_address oa, \n" +
                    " clw_order_item oi,   \n" +
                    " CLW_BUS_ENTITY site, \n" +

                    manufSql +

                    siteListSql +

            "WHERE   \n" +
                    "oa.address_type_cd = 'SHIPPING' and \n" +
                    "oa.order_id = o.order_id and  \n" +
                    " i.item_id = uom.item_id (+) and \n" +
                    " i.item_id = pack.item_id (+) and \n" +
                    " i.item_id = im.item_id and \n" +
                    "manu.bus_entity_id = im.bus_entity_id   and \n" +
                    "im.item_mapping_cd = 'ITEM_MANUFACTURER'  and \n" +
                    "site_list.site_id = o.site_id  and \n" +
                    "o.order_id = oi.order_id  and \n" +
                    "o.site_id = site.bus_entity_id AND \n" +
                    "oi.item_id = im.item_id  and \n" +
                    "o.order_status_cd in ('ERP Released', 'Invoiced')  and \n" +
                    dateWhere + " and \n" +
                    "oi.order_item_status_cd != 'CANCELLED'  \n";


            if (null != siteIdS && siteIdS.trim().length() > 0) {
                detailsSql += " AND o.site_id in (" + siteIdS + ") \n";
            }

            if (siteIdS == null || siteIdS.trim().length() == 0) {
                if (!showInactiveSites) {
                    detailsSql += " and site.bus_entity_status_cd = 'ACTIVE' \n";
                }
            }

            if (itemIdS != null && itemIdS.length() > 0) {
                detailsSql += " AND i.item_id in (" + itemIdS + ") \n";
            }

            detailsSql +=
                "GROUP BY \n" +
                       "manu.short_desc, oa.short_desc, oa.address1, oa.city, oa.state_province_cd, \n" +
                       "im.item_num , i.short_desc, uom.clw_value, pack.clw_value \n" +
                "ORDER BY  manu.short_desc, oa.short_desc\n";



            stmt = con.createStatement();
            rs = stmt.executeQuery(detailsSql);
            while (rs.next()) {
                ManufacturerItemSummaryReport.Details record = new ManufacturerItemSummaryReport.Details();
                record.manuf = rs.getString("manuf");
                record.siteName = rs.getString("site_name");
                record.address1 = rs.getString("addr");
                record.city = rs.getString("city");
                record.state = rs.getString("state");
                record.manufSku = rs.getString("manuf_sku");
                record.itemDesc = rs.getString("item_desc");
                record.UOM = rs.getString("uom");
                record.pack = rs.getString("pack");
                record.totalQty = rs.getInt("total_qty");
                record.totalPrice = rs.getBigDecimal("total_price");

                details.add(record);
            }
            rs.close();
            stmt.close();



    // all results
        generateResults(resultV, con, summaryBySites,
                    summaryByItem, details, tzSource.getID());
        String datePeriod = begDateS + " - " + endDateS;
        GenericReportColumnViewVector title = getReportTitle(reportName, manufName, siteName, itemName, datePeriod);

        int headerPos = title.size() + 2;

        for (int i = 0; i < resultV.size(); i++) {
            GenericReportResultView report = (GenericReportResultView) resultV.get(i);
            report.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            report.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            report.setFreezePositionRow(headerPos);
            report.setTitle(title);
        }
        return resultV;
    }


    protected void generateResults
            (GenericReportResultViewVector resultV, Connection con,
             List summaryBySites, List summaryByItem,
             List details, String timeZone) throws Exception {

        processList(details, resultV, "Detail", getDetailReportHeader(), false, timeZone);
        processList(summaryByItem, resultV, "Summary By Item", getSummaryByItemReportHeader(), false, timeZone);
        processList(summaryBySites, resultV, "Summary By Sites", getSummaryBySitesReportHeader(), true, timeZone);
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
                result.getTable().add(((ManufacturerItemSummaryReport.aRecord) it.next()).toList());
            }
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            result.setName(name);
            resultV.add(result);
        }
    }


    private GenericReportColumnViewVector getSummaryBySitesReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 1", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Total Quantity Ordered", 0, 20, "NUMBER", "4", false));
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Amount", 2, 20, "NUMBER", "8", false));
        }
        return header;
    }

    private GenericReportColumnViewVector getSummaryByItemReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer Sku", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Description", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "4", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Total Quantity Ordered", 0, 20, "NUMBER", "4", false));
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Amount", 2, 20, "NUMBER", "8", false));
        }
        return header;
    }

    private GenericReportColumnViewVector getDetailReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 1", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer Sku", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Description", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "4", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Total Quantity Ordered", 0, 20, "NUMBER", "4", false));
        if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Amount", 2, 20, "NUMBER", "8", false));
        }
        return header;
    }


    private  GenericReportColumnViewVector getReportTitle(String pReportName, String pManufName, String pSiteName,
                                                          String pItemName, String pDatePeriod) {
        GenericReportColumnViewVector title = new GenericReportColumnViewVector();
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", pReportName, 0, 255, "VARCHAR2", "20", false));
        if (Utility.isSet(pManufName)) {
            title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer Name: " + pManufName, 0, 255, "VARCHAR2", "20", false));
        }
        if (Utility.isSet(pItemName)) {
            title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Name(s): " + pItemName, 0, 255, "VARCHAR2", "20", false));
        }
        if (Utility.isSet(pSiteName)) {
            title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name(s): " + pSiteName, 0, 255, "VARCHAR2", "20", false));
        }
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Date Period Selected: " + pDatePeriod, 0, 255, "VARCHAR2", "20", false));
        return title;
    }


    private interface aRecord {
        ArrayList toList();
    }

    protected class SummaryBySites implements ManufacturerItemSummaryReport.aRecord {
        String manuf;
        String siteName;
        String address1;
        String city;
        String state;
        int totalQty;
        BigDecimal totalPrice;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
       		list.add(manuf);
            list.add(siteName);
            list.add(address1);
            list.add(city);
            list.add(state);
            list.add(totalQty);
            if (showPrice) {
                list.add(totalPrice);
            }
            return list;
        }
    }


    protected class SummaryByItem implements ManufacturerItemSummaryReport.aRecord {
        String manuf;
        String manufSku;
        String itemDesc;
        String UOM;
        String pack;
        int totalQty;
        BigDecimal totalPrice;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
       		list.add(manuf);
            list.add(manufSku);
            list.add(itemDesc);
            list.add(UOM);
            list.add(pack);
            list.add(totalQty);
            if (showPrice) {
                list.add(totalPrice);
            }
            return list;
        }
    }


    protected class Details implements ManufacturerItemSummaryReport.aRecord {
        String manuf;
        String siteName;
        String address1;
        String city;
        String state;
        String manufSku;
        String itemDesc;
        String UOM;
        String pack;
        int totalQty;
        BigDecimal totalPrice;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
       		list.add(manuf);
            list.add(siteName);
            list.add(address1);
            list.add(city);
            list.add(state);
            list.add(manufSku);
            list.add(itemDesc);
            list.add(UOM);
            list.add(pack);
            list.add(totalQty);
            if (showPrice) {
                list.add(totalPrice);
            }
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


    private final static int ONE_SECOND = 1000;

    private final static int ONE_MINUTE = ONE_SECOND * 60;

    private final static int ONE_HOUR = ONE_MINUTE * 60;

    private final static SimpleDateFormat SDF_DATE = new SimpleDateFormat("MM/dd/yyyy");
    private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
    private final static SimpleDateFormat SDF_FULL = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private final static Date getDate(Date date,
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
    public static Date addDays(Date date, int nDays)
    {
    	if (date == null)
        throw new IllegalArgumentException("Date is null in ManufacturerItemSummaryReport.addDays()");
      // Create a calendar based on given date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      // Add/subtract the specified number of days
      calendar.add(Calendar.DAY_OF_MONTH, nDays);
      return calendar.getTime();
    }


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
        return " TO_DATE(TO_CHAR(o.original_order_date, 'MM/dd/yyyy') || TO_CHAR(o.original_order_time, 'HH24:MI'), 'MM/dd/yyyyHH24:MI') "
                + " BETWEEN TO_DATE('"
                + startDateTargetS
                + "','MM/dd/yyyy HH24:MI') AND TO_DATE('"
                + endDateTargetS
                + "','MM/dd/yyyy HH24:MI') ";
    }


}
