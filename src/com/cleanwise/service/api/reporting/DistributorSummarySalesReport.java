package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.value.*;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * @author bstevens
 */
public class DistributorSummarySalesReport implements GenericReportMulti {
    private static final Logger log = Logger.getLogger(DistributorSummarySalesReport.class);
    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

    boolean mDisplaySalesTaxInfo;
    boolean mDisplayDistInfo;
    HashMap storeMap = new HashMap();
    
    HashMap masterItemCategoryMap = new HashMap();
    Connection con;
    private boolean showPrice;

    public GenericReportResultViewVector process(ConnectionContainer pCons,
         GenericReportData pReportData,
         java.util.Map pParams)
            throws Exception {
        TimeZone tzSource = DEFAULT_TIMEZONE;

        APIAccess factory = new APIAccess();

        con = pCons.getDefaultConnection();
            GenericReportResultViewVector resultV =
                new GenericReportResultViewVector();

        //Lists of report results
        ArrayList summaryByAccounts = new ArrayList();
        ArrayList summaryByItem = new ArrayList();
        ArrayList details = new ArrayList();

        String begDateS = (String) ReportingUtils.getParam(pParams, "BEG_DATE");
        String endDateS = (String) ReportingUtils.getParam(pParams, "END_DATE");
        String dateFmt = (String) ReportingUtils.getParam(pParams, "DATE_FMT");
        String accountIdS = (String)ReportingUtils.getParam(pParams, "LOCATE_ACCOUNT_MULTI");

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }

        if (Utility.isSet(begDateS) && !ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (Utility.isSet(endDateS) && !ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (!Utility.isSet(accountIdS)) {
            String mess = "^clw^Account is not set^clw^";
            throw new Exception(mess);
        }

        // check account for store
        String storeIdS =  (String) ReportingUtils.getParam(pParams, "STORE");
        int storeId = 0;
        try {
            storeId = Integer.parseInt(storeIdS, 10);
        } catch (Exception e) {
            String mess = "^clw^Invalid store " + storeIdS + "^clw^";
            throw new Exception(mess);
        }
        // get account names
        StringBuffer accountNames = new StringBuffer();
        String accSql =
           "select short_desc from clw_bus_entity where bus_entity_id in (" + accountIdS + ")" +
             "and bus_entity_id in (\n" +
                  "select ba.bus_entity1_id from clw_bus_entity_assoc ba " +
                  "   where ba.bus_entity2_id = ? and " +
                  "       ba.bus_entity_assoc_cd = '"+ RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"'\n" +
                  ")";
        PreparedStatement stmt1 = con.prepareStatement(accSql);
        stmt1.setInt(1, storeId);
        ResultSet rsd = stmt1.executeQuery();
        while (rsd.next()) {
            if (accountNames.length() > 0) {
                accountNames.append(",");
            }
            accountNames.append(rsd.getString(1));
        }
        rsd.close();
        stmt1.close();

        if (accountNames.length() == 0) {
            String mess = "^clw^No Such Account(s) Found^clw^";
            throw new Exception(mess);
        }


        String dateWhere = getDateWhere(begDateS, endDateS, dateFmt, tzSource, DEFAULT_TIMEZONE);

        String distribIdS = (String)ReportingUtils.getParam(pParams, "DISTRIBUTOR_MULTI_OPT");
        IdVector distribs = null;
        if (Utility.isSet(distribIdS)) {
           distribs = Utility.parseIdStringToVector(distribIdS, ",");
           if (distribs.size() == 0) {
                String mess = "^clw^\"" + distribIdS + "\" is not a valid distributor identifier(s)^clw^";
                throw new Exception(mess);
           }
        }


        // title
        StringBuffer distribNames = new StringBuffer();
        String reportName = "Distributor Summary Sales Report";



        // get distributor names
        if (distribs != null && distribs.size() > 0) {
            String distribIdStr = Utility.toCommaSting(distribs);
            String distSql = "select short_desc from clw_bus_entity where bus_entity_id in (" + distribIdStr + ")";
            stmt1 = con.prepareStatement(distSql);
            rsd = stmt1.executeQuery();
            while (rsd.next()) {
                if (distribNames.length() > 0) {
                    distribNames.append(",");
                }
                distribNames.append(rsd.getString(1));
            }
            rsd.close();
            stmt1.close();
		}


        // 1 summary by Accounts

        String summaryByAccountsSql =
            "select\n" +
                    "dist.short_desc as dist_name, \n" +
                    "account.short_desc as account_name,\n" +
                    "to_char(trunc(NVL(o.revised_order_date,o.original_order_date), 'MM'), 'Month yyyy') as month_of_order,\n" +
                    "sum(oi.total_quantity_ordered) as total_qty,\n" +
                    "sum(oi.cust_contract_price * oi.total_quantity_ordered) as total_price \n" +
            "from\n" +
                    "clw_item_mapping im,   clw_order  o,  clw_bus_entity account, clw_order_item oi, \n" +
                    "clw_bus_entity dist " +
            "where \n" +
                    "dist.bus_entity_id = im.bus_entity_id \n" +
                    "and im.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    "and o.account_id in (" + accountIdS  + ")\n" +
                    "and account.bus_entity_id = o.account_id\n" +
                    "and o.order_id = oi.order_id\n" +
                    "and oi.item_id = im.item_id\n" +
                    "and oi.dist_erp_num = dist.erp_num\n" +
                    "and o.order_status_cd in ('ERP Released', 'Invoiced')\n" +
                    dateWhere  +
                    "and oi.order_item_status_cd != 'CANCELLED'\n";

           if (Utility.isSet(distribIdS)) {
               summaryByAccountsSql += " and dist.bus_entity_id in (" + distribIdS + ")\n";
           }

            summaryByAccountsSql +=
                    "group by dist.short_desc, account.short_desc, trunc(NVL(o.revised_order_date,o.original_order_date), 'MM')\n" +
                    "order by dist.short_desc,account.short_desc, trunc(NVL(o.revised_order_date,o.original_order_date), 'MM') \n";


            log.info("summaryBySitesSql=" + summaryByAccountsSql);

            PreparedStatement stmt = con.prepareStatement(summaryByAccountsSql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SummaryByAccounts record = new SummaryByAccounts();
                record.distribName = rs.getString("dist_name");
                record.accountName = rs.getString("account_name");
                record.monthOfOrder = rs.getString("month_of_order");
                record.totalQty = rs.getInt("total_qty");
                record.totalPrice = rs.getBigDecimal("total_price");

                summaryByAccounts.add(record);
            }
            rs.close();
            stmt.close();

// 2 summary by item
            String summaryByItemSql =
                 "select\n" +
                            "dist.short_desc as dist_name, \n" +
                            "to_char(trunc(NVL(o.revised_order_date,o.original_order_date), 'MM'), 'Month yyyy') as month_of_order,\n" +
                            "im_manuf.item_num as manuf_sku, i.short_desc as item_desc,\n" +
                            "uom.clw_value as uom, pack.clw_value as pack,\n" +
                            "sum(oi.total_quantity_ordered) as total_qty,\n" +
                            "sum(oi.cust_contract_price * oi.total_quantity_ordered) as total_price\n" +
                 "from\n" +
                            "clw_item i, (select * from clw_item_meta where name_value = 'UOM') uom, \n" +
                            "(select * from clw_item_meta where name_value = 'PACK') pack,\n" +
                            "clw_item_mapping im,   clw_order  o,  clw_order_item oi, \n" +
                            "clw_bus_entity dist,  clw_item_mapping im_manuf  \n" +

                  "where \n" +
                            "i.item_id = uom.item_id (+) and\n" +
                            "i.item_id = pack.item_id (+) and\n" +
                            "i.item_id = im.item_id\n" +
                            "and dist.bus_entity_id = im.bus_entity_id \n" +
                            "and im.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                            "and i.item_id = im_manuf.item_id\n" +
                            "and im_manuf.item_mapping_cd = '"+ RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'\n" +
                            "and o.account_id in (" + accountIdS  + ")\n" +
                            "and o.order_id = oi.order_id\n" +
                            "and oi.item_id = im.item_id\n" +
                            "and oi.dist_erp_num = dist.erp_num\n" +
                            "and o.order_status_cd in ('ERP Released', 'Invoiced')\n" +
                            dateWhere  +
                            "and oi.order_item_status_cd != 'CANCELLED'\n";

            if (Utility.isSet(distribIdS)) {
                summaryByItemSql += " and dist.bus_entity_id in (" + distribIdS + ")\n";
            }


            summaryByItemSql +=
                "group by dist.short_desc, im_manuf.item_num, i.short_desc, " +
                    "trunc(NVL(o.revised_order_date,o.original_order_date), 'MM'), uom.clw_value, pack.clw_value\n" +
                "order by dist.short_desc, trunc(NVL(o.revised_order_date,o.original_order_date), 'MM')\n" +
                    ",im_manuf.item_num , i.short_desc";


            log.info("summaryByItemSql=" + summaryByItemSql);

            stmt = con.prepareStatement(summaryByItemSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DistributorSummarySalesReport.SummaryByItem record = new DistributorSummarySalesReport.SummaryByItem();
                record.distribName = rs.getString("dist_name");
                record.monthOfOrder = rs.getString("month_of_order");
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
            "select\n" +
                    "dist.short_desc as dist_name, \n" +
                    "account.short_desc as account_name,\n" +
                    "site.short_desc as site_name, oa.address1, oa.city, oa.state_province_cd, oa.postal_code,\n" +
                    "to_char(trunc(NVL(o.revised_order_date,o.original_order_date), 'MM'), 'Month yyyy') as month_of_order,\n" +
                    "im_manuf.item_num as manuf_sku, i.short_desc as item_desc,\n" +
                    "uom.clw_value as uom, pack.clw_value as pack,\n" +
                    "sum(oi.total_quantity_ordered) as total_qty,\n" +
                    "sum(oi.cust_contract_price * oi.total_quantity_ordered) as total_price\n" +
            "from\n" +
                    "clw_item i, (select * from clw_item_meta where name_value = 'UOM') uom, \n" +
                    "(select * from clw_item_meta where name_value = 'PACK') pack,\n" +
                    "clw_item_mapping im,   clw_order  o, clw_Order_address oa, clw_bus_entity account, clw_order_item oi, \n" +
                    "clw_bus_entity dist,  clw_item_mapping im_manuf, clw_bus_entity site  \n" +
            "where \n" +
                    "oa.address_type_cd = 'SHIPPING' and oa.order_id = o.order_id and\n" +
                    "i.item_id = uom.item_id (+) and\n" +
                    "i.item_id = pack.item_id (+) and\n" +
                    "i.item_id = im.item_id\n" +
                    "and dist.bus_entity_id = im.bus_entity_id \n" +
                    "and im.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "'\n" +
                    "and i.item_id = im_manuf.item_id\n" +
                    "and im_manuf.item_mapping_cd = '"+ RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'\n" +
                    "and o.account_id in (" + accountIdS  + ")\n" +
                    "and account.bus_entity_id = o.account_id\n" +
                    "and site.bus_entity_id = o.site_id\n" +
                    "and o.order_id = oi.order_id\n" +
                    "and oi.item_id = im.item_id\n" +
                    "and oi.dist_erp_num = dist.erp_num\n" +
                    "and o.order_status_cd in ('ERP Released', 'Invoiced')\n" +
                    dateWhere  +
                    "and oi.order_item_status_cd != 'CANCELLED'\n";

            if (Utility.isSet(distribIdS)) {
                detailsSql += " and dist.bus_entity_id in (" + distribIdS + ")\n";
            }


            detailsSql +=
                    "group by dist.short_desc, account.short_desc, site.short_desc, " +
                        "oa.address1, oa.city, oa.state_province_cd, oa.postal_code,\n" +
                        "trunc(NVL(o.revised_order_date,o.original_order_date), 'MM'), " +
                        "im_manuf.item_num , i.short_desc, \n" +
                        "uom.clw_value, pack.clw_value\n" +
                    "order by dist.short_desc,account.short_desc, site.short_desc," +
                            "trunc(NVL(o.revised_order_date,o.original_order_date), 'MM')\n" +
                        ",im_manuf.item_num , i.short_desc";


            log.info("detailsSql=" + detailsSql);

            stmt = con.prepareStatement(detailsSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Details record = new Details();
                record.distribName= rs.getString("dist_name");
                record.accountName = rs.getString("account_name");
                record.siteName = rs.getString("site_name");
                record.address1 = rs.getString("address1");
                record.city = rs.getString("city");
                record.state = rs.getString("state_province_cd");
                record.postalCode = rs.getString("postal_code");
                record.monthOfOrder = rs.getString("month_of_order");
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
        generateResults(resultV, con, summaryByAccounts,
                    summaryByItem, details, tzSource.getID());
        GenericReportColumnViewVector title = getReportTitle(reportName, accountNames.toString(),
            distribNames.toString(), begDateS, endDateS, dateFmt);

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
        processList(summaryBySites, resultV, "Summary By Accounts", getSummaryByAccountsReportHeader(), true, timeZone);
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
                result.getTable().add(((DistributorSummarySalesReport.aRecord) it.next()).toList());
            }
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            result.setName(name);
            resultV.add(result);
        }
    }


    private GenericReportColumnViewVector getSummaryByAccountsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Month Of Order", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Total Quantity Ordered", 0, 20, "NUMBER", "4", false));
        //if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Price", 2, 20, "NUMBER", "8", false));
        //}
        return header;
    }

    private GenericReportColumnViewVector getSummaryByItemReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Month Of Order", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manuf Sku", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Desc", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "4", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Total Quantity Ordered", 0, 20, "NUMBER", "4", false));
        //if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Price", 2, 20, "NUMBER", "8", false));
        //}
        return header;
    }

    private GenericReportColumnViewVector getDetailReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "20", false));
   		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 1", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Postal Code", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Month Of Order", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manuf Sku", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Desc", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "4", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Total Quantity Ordered", 0, 20, "NUMBER", "4", false));
        //if (showPrice) {
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Price", 2, 20, "NUMBER", "8", false));
        //}
        return header;
    }


    private  GenericReportColumnViewVector getReportTitle(String pReportName, String pAccountNames, String pDistribNames,
                                                          String pBegDate, String pEndDate, String pDateFmt) {
        GenericReportColumnViewVector title = new GenericReportColumnViewVector();
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", pReportName, 0, 255, "VARCHAR2", "20", false));
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account(s): " + pAccountNames, 0, 255, "VARCHAR2", "20", false));
        if (Utility.isSet(pDistribNames)) {
            title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor(s): " + pDistribNames, 0, 255, "VARCHAR2", "20", false));
        }
        // date period
        if (Utility.isSet(pBegDate)) {
            if (!Utility.isSet(pEndDate)) {
                SimpleDateFormat sdf = new SimpleDateFormat(pDateFmt);
                pEndDate = sdf.format(new Date());
            }
            String datePeriod = pBegDate + " - " + pEndDate;
            title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Date Period Selected: " + datePeriod, 0, 255, "VARCHAR2", "20", false));
        } else {
            if (Utility.isSet(pEndDate)) {
                title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "End Date: " + pEndDate, 0, 255, "VARCHAR2", "20", false));
            }
        }
        return title;
    }


    private interface aRecord {
        ArrayList toList();
    }

    protected class SummaryByAccounts implements aRecord {
        String distribName;
        String accountName;
        String monthOfOrder;
        int totalQty;
        BigDecimal totalPrice;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
       		list.add(distribName);
            list.add(accountName);
            list.add(monthOfOrder);
            list.add(totalQty);
            //if (showPrice) {
                list.add(totalPrice);
            //}
            return list;
        }
    }


    protected class SummaryByItem implements aRecord {
        String distribName;
        String monthOfOrder;
        String manufSku;
        String itemDesc;
        String UOM;
        String pack;
        int totalQty;
        BigDecimal totalPrice;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
       		list.add(distribName);
            list.add(monthOfOrder);
            list.add(manufSku);
            list.add(itemDesc);
            list.add(UOM);
            list.add(pack);
            list.add(totalQty);
            //if (showPrice) {
                list.add(totalPrice);
            //}
            return list;
        }
    }


    protected class Details implements aRecord {
        String distribName;
        String accountName;
        String siteName;
        String address1;
        String city;
        String state;
        String postalCode;
        String monthOfOrder;
        String manufSku;
        String itemDesc;
        String UOM;
        String pack;
        int totalQty;
        BigDecimal totalPrice;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
       		list.add(distribName);
            list.add(accountName);
            list.add(siteName);
            list.add(address1);
            list.add(city);
            list.add(state);
            list.add(postalCode);
            list.add(monthOfOrder);
            list.add(manufSku);
            list.add(itemDesc);
            list.add(UOM);
            list.add(pack);
            list.add(totalQty);
            //if (showPrice) {
                list.add(totalPrice);
            //}
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


    public final static Date convertDate(Date source,
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
        return new Date(timeInMillis + (delta2 - delta1));
    }

    public final static String getDateWhere(String begDateS, String endDateS,
            String dateFmt, TimeZone tzSource, TimeZone tzTarget)
            throws Exception {
        StringBuffer result = new StringBuffer();

        if (!Utility.isSet(begDateS) && !Utility.isSet(endDateS)) return result.toString();

        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(PATTERN);

        String startDateTargetS = null;
        if (Utility.isSet(begDateS)) {
            Date startDate = sdf.parse(begDateS);
            //Date startDateTarget = convertDate(startDate, tzSource, tzTarget);
            startDateTargetS = sdfTarget.format(startDate); //sdfTarget.format(startDateTarget);
        }
        String endDateTargetS = null;
        if (Utility.isSet(endDateS)) {
            Date endDate = sdf.parse(endDateS);
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
            //Date endDate = addDays(endDateOrig,1);
            //Date endDateTarget = convertDate(endDate, tzSource, tzTarget);
            endDateTargetS = sdfTarget.format(endDate); //sdfTarget.format(endDateTarget);
        }

        result.append("and TO_DATE(TO_CHAR(NVL(o.revised_order_date,o.original_order_date), 'MM/dd/yyyy') || \n" +
                          "TO_CHAR(NVL(o.revised_order_date,o.original_order_date), 'HH24:MI'), 'MM/dd/yyyyHH24:MI')\n");

        if (Utility.isSet(startDateTargetS) && Utility.isSet(endDateTargetS)) {
            result.append(" BETWEEN TO_DATE('").append(startDateTargetS).
                   append("','MM/dd/yyyy HH24:MI') AND TO_DATE('").append(endDateTargetS).
                   append("','MM/dd/yyyy HH24:MI') \n");
        } else {
            if (Utility.isSet(startDateTargetS)) {
                result.append(">= TO_DATE('").append(startDateTargetS).
                       append("','MM/dd/yyyy HH24:MI')\n");
            } else {
                result.append("<= TO_DATE('").append(endDateTargetS).
                       append("','MM/dd/yyyy HH24:MI')\n");
            }
        }

        return result.toString();
    }


}
