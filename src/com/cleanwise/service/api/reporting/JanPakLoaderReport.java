package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import org.apache.log4j.Logger;

import org.jboss.security.auth.login.PolicyConfig;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;


public class JanPakLoaderReport implements GenericReportMulti, java.io.Serializable {

    private static final Logger log = Logger.getLogger(JanPakLoaderReport.class);

    private static final String SITE_TABLE    = "SITE_TABLE_OPT";
    private static final String ITEM_TABLE    = "ITEM_TABLE_OPT";
    private static final String INVOICE_TABLE = "INVOICE_TABLE_OPT";

    private static final String NO_BILL_TO_ADD1    = "NO NO_BILL_TO_ADD1";
    private static final String NO_BILL_TO_NAME    = "NO_BILL_TO_NAME";
    private static final String NO_SHIP_TO_NUM     = "NO_SHIP_TO_NUM";
    private static final String NO_SHIP_TO_NAME    = "NO_SHIP_TO_NAME";
    private static final String SHIP_TO_ZIP_LENGTH = "SHIP_TO_ZIP_LENGTH>15";
    private static final String INVOICE_EXISTS     = " INVOICE_EXISTS";
    private static final String ACCOUNT_NOT_FOUND  = "ACCOUNT_NOT_FOUND ";
    private static final String SITE_NOT_FOUND     = "SITE_NOT_FOUND";

    private static final String IGNORE_CD = "I";
    private static final String ERROR_CD  = "E";

    private static final int MAX_ROWS = 65000;


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        GenericReportResultViewVector results = new GenericReportResultViewVector();

        String pSiteTable = (String) pParams.get(SITE_TABLE);
        String pItemTable = (String) pParams.get(ITEM_TABLE);
        String pInvoiceTable = (String) pParams.get(INVOICE_TABLE);

        if (Utility.isSet(pInvoiceTable)) {

            GenericReportResultView resultInvoice = GenericReportResultView.createValue();
            GenericReportColumnViewVector headerInvoice = getReportHeaderInvoice();
            resultInvoice.setHeader(headerInvoice);
            resultInvoice.setColumnCount(headerInvoice.size());
            resultInvoice.setName("Invoices");
            resultInvoice.setTable(getReportTableInvoice(pCons, pInvoiceTable));
            results.add(resultInvoice);

        }


        if (Utility.isSet(pItemTable)) {

            GenericReportResultView resultItem = GenericReportResultView.createValue();
            GenericReportColumnViewVector headerItem = getReportHeaderItem();
            resultItem.setHeader(headerItem);
            resultItem.setColumnCount(headerItem.size());
            resultItem.setName("Items");
            resultItem.setTable(getReportTableItem(pCons, pItemTable));
            results.add(resultItem);

        }

        if (Utility.isSet(pSiteTable)) {

            GenericReportResultView resultBill = GenericReportResultView.createValue();
            GenericReportColumnViewVector headerBill = getReportHeaderBill();
            resultBill.setHeader(headerBill);
            resultBill.setColumnCount(headerBill.size());
            resultBill.setName("Accounts");
            resultBill.setTable(getReportTableBill(pCons, pSiteTable));
            results.add(resultBill);

            GenericReportResultView resultShip = GenericReportResultView.createValue();
            GenericReportColumnViewVector headerShip = getReportHeaderShip();
            resultShip.setHeader(headerShip);
            resultShip.setColumnCount(headerShip.size());
            resultShip.setName("Sites");
            resultShip.setTable(getReportTableShip(pCons, pSiteTable));
            results.add(resultShip);

        }

        return results;
    }

    private GenericReportColumnViewVector getReportHeaderItem() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "ITEM_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_DESC", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_LONG_DESC", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PRICE_LINE", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "CATALOG_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "MANUFACTURER", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "MANUFACTURER_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PACK", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "JD_UOM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "CATEGORY", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "CATEGORY_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_CATEGORY_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_META_UOM_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_META_PACK_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_DISTRIBUTOR_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_MANUFACTURER_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ITEM_CATALOG_ACTION_CD", 0, 255, "VARCHAR2"));

        return header;
    }

    private ArrayList<List> getReportTableBill(ConnectionContainer pCons, String pSiteTable) throws SQLException {

        ArrayList<aAccountRecord> bills = new ArrayList<aAccountRecord>();

        String sql = "SELECT * FROM (SELECT BILL_TO_NUM,BILL_TO_NAME,ACCOUNT_ID,ACCOUNT_ACTION_CD,ACCOUNT_ADDRESS_ACTION_CD,CUS_SEL_CODE_ACTION_CD FROM " + pSiteTable + "_BILL\n" +
                "   UNION \n" +
                "   SELECT BILL_TO_NUM,BILL_TO_NAME,null,'" + IGNORE_CD + "(" + NO_BILL_TO_ADD1 + ")','" + IGNORE_CD + "(" + NO_BILL_TO_ADD1 + ")','" + IGNORE_CD + "(" + NO_BILL_TO_ADD1 + ")' FROM " + pSiteTable + " where TRIM(BILL_TO_ADD1) IS NULL) ORDER BY  ACCOUNT_ACTION_CD DESC,ACCOUNT_ADDRESS_ACTION_CD DESC,CUS_SEL_CODE_ACTION_CD DESC";

        log.info("process sql:" + sql);

        Statement stmt = pCons.getMainConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int i = 0;
        while (rs.next()) {

            if (i == MAX_ROWS) {
                break;
            }

            aAccountRecord accountRecord = new aAccountRecord();

            accountRecord.setBillToNum(rs.getString(1));
            accountRecord.setBillToName(rs.getString(2));
            accountRecord.setAccountId(rs.getInt(3));
            accountRecord.setActionCd(rs.getString(4));
            accountRecord.setAddressActionCd(rs.getString(5));
            accountRecord.setCusSelCodeActionCd(rs.getString(6));

            bills.add(accountRecord);
            i++;
        }

        ArrayList<List> reportTableBill = new ArrayList<List>();
        for (aAccountRecord accountRecord : bills) {
            reportTableBill.add(accountRecord.toList());
        }

        return reportTableBill;
    }


    public ArrayList getReportTableItem(ConnectionContainer pCons, String pTable) throws SQLException {

        ArrayList<aItemRecord> items = new ArrayList<aItemRecord>();

        String sql = " SELECT ITEM_NUM,ITEM_DESC,ITEM_ID,ITEM_LONG_DESC,PRICE_LINE,CATALOG_NUM,MANUFACTURER,MANUFACTURER_ID,\n" +
                " UOM,PACK,JD_UOM,CATEGORY,CATEGORY_ID,ITEM_ACTION_CD,ITEM_CATEGORY_ACTION_CD,ITEM_META_UOM_ACTION_CD,\n" +
                " ITEM_META_PACK_ACTION_CD, ITEM_DISTRIBUTOR_ACTION_CD, ITEM_MANUFACTURER_ACTION_CD, ITEM_CATALOG_ACTION_CD  \n" +
                " FROM "+pTable+"_ITEM ORDER BY  ITEM_ACTION_CD DESC, ITEM_CATEGORY_ACTION_CD DESC, ITEM_META_UOM_ACTION_CD DESC, ITEM_META_PACK_ACTION_CD DESC, ITEM_DISTRIBUTOR_ACTION_CD DESC, ITEM_MANUFACTURER_ACTION_CD DESC,ITEM_LONG_DESC DESC";


        Statement stmt = pCons.getMainConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int i = 0;

        while (rs.next()) {

            if (i == MAX_ROWS) {
                break;
            }

            aItemRecord itemRecord = new aItemRecord();
            itemRecord.setItemNum(rs.getString(1));
            itemRecord.setItemDesc(rs.getString(2));
            itemRecord.setItemId(rs.getInt(3));
            itemRecord.setItemLongDesc(rs.getString(4));
            itemRecord.setPriceLine(rs.getString(5));
            itemRecord.setCatalogNum(rs.getString(6));
            itemRecord.setManufacturer(rs.getString(7));
            itemRecord.setManufacturerId(rs.getInt(8));
            itemRecord.setUom(rs.getString(9));
            itemRecord.setPack(rs.getString(10));
            itemRecord.setJdUom(rs.getString(11));
            itemRecord.setCategory(rs.getString(12));
            itemRecord.setCategoryId(rs.getInt(13));
            itemRecord.setItemActionCd(rs.getString(14));
            itemRecord.setItemCategoryActionCd(rs.getString(15));
            itemRecord.setItemMetaUomActionCd(rs.getString(16));
            itemRecord.setItemMetaPackActionCd(rs.getString(17));
            itemRecord.setItemDistributorActionCd(rs.getString(18));
            itemRecord.setItemManufacturerActionCd(rs.getString(19));
            itemRecord.setItemCatalogActionCd(rs.getString(20));
            items.add(itemRecord);
            i++;
        }

        ArrayList<List> reportTableItems = new ArrayList<List>();
        for (aItemRecord itemRecord : items) {
            reportTableItems.add(itemRecord.toList());
        }

        return reportTableItems;
    }

    private GenericReportColumnViewVector getReportHeaderInvoice() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","ACCOUNT_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","SITE_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","INVOICE_DIST_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "INVOICE_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "INVOICE_DATE", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", ",BILL_TO_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SHIP_TO_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SHIP_TO_NAME", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","SUB_TOTAL", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BRANCH", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "REP_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "REP_NAME", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "CUSTOMER_PO", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","INVENTORY_ACTION_CD", 0, 32, "NUMBER"));


        return header;
    }


    public ArrayList getReportTableInvoice(ConnectionContainer pCons, String pTable) throws SQLException {

        ArrayList<aInvoiceRecord> invoices = new ArrayList<aInvoiceRecord>();

        String sql = "SELECT  ACCOUNT_ID,SITE_ID,INVOICE_DIST_ID,INVOICE_NUM,INVOICE_DATE,BILL_TO,SHIP_TO,SHIP_TO_NAME,SUB_TOTAL,BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO,INV_ACTION_CD FROM \n" +
                "  (\n" +
                " SELECT ACCOUNT_ID,SITE_ID,INVOICE_DIST_ID,INVOICE_NUM,INVOICE_DATE,BILL_TO,SHIP_TO,SHIP_TO_NAME,SUB_TOTAL,BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO,NVL(INV_ACTION_CD,'" + IGNORE_CD + "(" + INVOICE_EXISTS + ")') AS INV_ACTION_CD FROM " + pTable + "_INV WHERE SITE_ID>0 AND ACCOUNT_ID>0\n" +
                " UNION \n" +
                " SELECT ACCOUNT_ID,SITE_ID,INVOICE_DIST_ID,INVOICE_NUM,INVOICE_DATE,BILL_TO,SHIP_TO,SHIP_TO_NAME,SUB_TOTAL,BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO,'" + IGNORE_CD + "(" + ACCOUNT_NOT_FOUND + ")' FROM " + pTable + "_INV WHERE SITE_ID IS NULL AND ACCOUNT_ID IS NULL\n" +
                " UNION \n" +
                " SELECT ACCOUNT_ID,SITE_ID,INVOICE_DIST_ID,INVOICE_NUM,INVOICE_DATE,BILL_TO,SHIP_TO,SHIP_TO_NAME,SUB_TOTAL,BRANCH,REP_NUM,REP_NAME,CUSTOMER_PO,'" + IGNORE_CD + "(" + SITE_NOT_FOUND + ")' FROM " + pTable + "_INV WHERE SITE_ID IS NULL AND ACCOUNT_ID IS NOT NULL\n" +
                ") ORDER BY INV_ACTION_CD DESC";


        Statement stmt = pCons.getMainConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int i = 0;

        while (rs.next()) {

            if (i == MAX_ROWS) {
                break;
            }

            aInvoiceRecord ainvoiceRecord = new aInvoiceRecord();

            ainvoiceRecord.setAccountId(rs.getInt(1));
            ainvoiceRecord.setSiteId(rs.getInt(2));
            ainvoiceRecord.setInvoiceDistId(rs.getInt(3));
            ainvoiceRecord.setInvoiceNum(rs.getString(4));
            ainvoiceRecord.setInvoiceDate(rs.getDate(5));
            ainvoiceRecord.setBillTo(rs.getString(6));
            ainvoiceRecord.setShipTo(rs.getString(7));
            ainvoiceRecord.setShipToName(rs.getString(8));
            ainvoiceRecord.setSubTotal(rs.getBigDecimal(9));
            ainvoiceRecord.setBranch(rs.getString(10));
            ainvoiceRecord.setRepNum(rs.getString(11));
            ainvoiceRecord.setRepName(rs.getString(12));
            ainvoiceRecord.setCustomerPo(rs.getString(13));
            ainvoiceRecord.setInvActionCd(rs.getString(14));

            invoices.add(ainvoiceRecord);

            i++;
        }

        ArrayList<List> reportTableInvoice = new ArrayList<List>();
        for (aInvoiceRecord invoiceRecord : invoices) {
            reportTableInvoice.add(invoiceRecord.toList());
        }

        return reportTableInvoice;
    }




    private ArrayList<List> getReportTableShip(ConnectionContainer pCons, String pSiteTable) throws SQLException {

        ArrayList<aSiteRecord> ships = new ArrayList<aSiteRecord>();

        String sql = "SELECT * FROM (SELECT BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,ACCOUNT_ID,SITE_ID,SITE_ACTION_CD,SITE_ADDRESS_ACTION_CD FROM " + pSiteTable + "_SHIP\n" +
                "   UNION \n" +
                "   SELECT BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,NULL,NULL,'" + IGNORE_CD + "(" + NO_BILL_TO_ADD1 + ")','" + IGNORE_CD + "(" + NO_BILL_TO_ADD1 + ")' FROM " + pSiteTable + " WHERE TRIM(BILL_TO_ADD1) IS NULL\n" +
                "   UNION \n" +
                "   SELECT BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,NULL,NULL,'" + IGNORE_CD + "(" + NO_BILL_TO_NAME + ")','" + IGNORE_CD + "(" + NO_BILL_TO_NAME + ")' FROM " + pSiteTable + " WHERE TRIM(BILL_TO_NAME) IS NULL\n" +
                "   UNION \n" +
                "   SELECT BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,NULL,NULL,'" + ERROR_CD + "(" + NO_SHIP_TO_NUM + ")','" + ERROR_CD + "(" + NO_SHIP_TO_NUM + ")' FROM " + pSiteTable + " WHERE TRIM(SHIP_TO_NUM) IS  NULL \n" +
                "   UNION \n" +
                "   SELECT BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,NULL,NULL,'" + ERROR_CD + "(" + NO_SHIP_TO_NAME + ")','" + ERROR_CD + "(" + NO_SHIP_TO_NAME + ")' FROM " + pSiteTable + " WHERE TRIM(SHIP_TO_NAME) IS  NULL \n" +
                "   UNION \n" +
                "   SELECT BILL_TO_NUM,SHIP_TO_NUM,SHIP_TO_NAME,NULL,NULL,'" + ERROR_CD + "(" + SHIP_TO_ZIP_LENGTH + ")','" + ERROR_CD + "(" + SHIP_TO_ZIP_LENGTH + ")' FROM " + pSiteTable + " WHERE LENGTH(SHIP_TO_ZIP)>15) ORDER BY  SITE_ACTION_CD DESC,SITE_ADDRESS_ACTION_CD DESC";

        log.info("process sql:" + sql);

        Statement stmt = pCons.getMainConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int i = 0;

        while (rs.next()) {

            if (i == MAX_ROWS) {
                break;
            }

            aSiteRecord asiteRecord = new aSiteRecord();
            asiteRecord.setBillToNum(rs.getString(1));
            asiteRecord.setShipToNum(rs.getString(2));
            asiteRecord.setShipToName(rs.getString(3));
            asiteRecord.setAccountId(rs.getInt(4));
            asiteRecord.setSiteId(rs.getInt(5));
            asiteRecord.setSiteActionCd(rs.getString(6));
            asiteRecord.setSiteAddressActionCd(rs.getString(7));

            ships.add(asiteRecord);

            i++;
        }

        ArrayList<List> reportTableShip = new ArrayList<List>();
        for (aSiteRecord siteRecord : ships) {
            reportTableShip.add(siteRecord.toList());
        }
        return reportTableShip;
    }


    private GenericReportColumnViewVector getReportHeaderShip() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BILL_TO_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SHIP_TO_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SHIP_TO_NAME", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "ACCOUNT_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "SITE_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SITE_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SITE_ADDRESS_ACTION_CD", 0, 255, "VARCHAR2"));

        return header;

    }

    private GenericReportColumnViewVector getReportHeaderBill() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BILL_TO_NUM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BILL_TO_NAME", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "ACCOUNT_ID", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ACCOUNT_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ADDRESS_ACTION_CD", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "CUS_SEL_CODE_ACTION_CD", 0, 255, "VARCHAR2"));

        return header;
    }

    private class aAccountRecord implements Record, java.io.Serializable {

         private String billToNum;
         private String actionCd;
         private int accountId;
        private String billToName;
        private String addressActionCd;
        private String cusSelCodeActionCd;

        public void setBillToName(String billToName) {
            this.billToName = billToName;
        }

        public String getBillToName() {
            return billToName;
        }

        public String getActionCd() {
            return actionCd;
        }

        public void setActionCd(String actionCd) {
            this.actionCd = actionCd;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getBillToNum() {
            return billToNum;
        }

        public void setBillToNum(String billToNum) {
            this.billToNum = billToNum;
        }
               public String getAddressActionCd() {
            return addressActionCd;
        }

        public String getCusSelCodeActionCd() {
            return cusSelCodeActionCd;
        }

        public void setAddressActionCd(String addressActionCd) {
            this.addressActionCd = addressActionCd;
        }

        public void setCusSelCodeActionCd(String cusSelCodeActionCd) {
            this.cusSelCodeActionCd = cusSelCodeActionCd;
        }
        public List toList() {
            ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(this.billToNum);
            list.add(this.billToName);
            list.add(this.accountId);
            list.add(this.actionCd);
            list.add(this.addressActionCd);
            list.add(this.cusSelCodeActionCd);

            return list;
        }
    }

     private class aSiteRecord implements Record, java.io.Serializable {

         private String billToNum;
         private String shipToNum;
         private String shipToName;
         private int accountId;
         private int siteId;
         private String siteActionCd;
         private String siteAddressActionCd;

         public String getBillToNum() {
             return billToNum;
         }

         public void setBillToNum(String billToNum) {
             this.billToNum = billToNum;
         }

         public String getShipToNum() {
             return shipToNum;
         }

         public void setShipToNum(String shipToNum) {
             this.shipToNum = shipToNum;
         }

         public String getShipToName() {
             return shipToName;
         }

         public void setShipToName(String shipToName) {
             this.shipToName = shipToName;
         }

         public int getAccountId() {
             return accountId;
         }

         public void setAccountId(int accountid) {
             this.accountId = accountid;
         }

         public int getSiteId() {
             return siteId;
         }

         public void setSiteId(int siteId) {
             this.siteId = siteId;
         }

         public String getSiteActionCd() {
             return siteActionCd;
         }

         public void setSiteActionCd(String siteActionCd) {
             this.siteActionCd = siteActionCd;
         }

         public String getSiteAddressActionCd() {
             return siteAddressActionCd;
         }

         public void setSiteAddressActionCd(String siteAddressActionCd) {
             this.siteAddressActionCd = siteAddressActionCd;
         }

         public List toList() {
             ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(this.billToNum);
            list.add(this.shipToNum);
            list.add(this.shipToName);
            list.add(this.accountId);
            list.add(this.siteId);
            list.add(this.siteActionCd);
            list.add(this.siteAddressActionCd);

            return list;
         }
     }

     private class aItemRecord implements Record, java.io.Serializable {

         private String  itemNum;
         private String itemDesc;
         private int itemId;
         private String itemLongDesc;
         private String  priceLine;
         private String catalogNum;
         private String  manufacturer;
         private int manufacturerId;
         private String uom;
         private String pack;
         private String jdUom;
         private String category;
         private int categoryId;
         private String itemActionCd;
         private String itemCategoryActionCd;
         private String itemMetaUomActionCd;
         private String  itemMetaPackActionCd;
         private String itemDistributorActionCd;
         private String itemManufacturerActionCd;
         private String itemCatalogActionCd;

         public List toList() {

             ArrayList<Serializable> list = new ArrayList<Serializable>();

             list.add(this.itemNum);
             list.add(this.itemId);
             list.add(this.itemDesc);
             list.add(this.itemLongDesc);
             list.add(this.priceLine);
             list.add(this.catalogNum);
             list.add(this.manufacturer);
             list.add(this.manufacturerId);
             list.add(this.uom);
             list.add(this.pack);
             list.add(this.jdUom);
             list.add(this.category);
             list.add(this.categoryId);
             list.add(this.itemActionCd);
             list.add(this.itemCategoryActionCd);
             list.add(this.itemMetaUomActionCd);
             list.add(this.itemMetaPackActionCd);
             list.add(this.itemDistributorActionCd);
             list.add(this.itemManufacturerActionCd);
             list.add(this.itemCatalogActionCd);

             return list;
         }


         public String getItemNum() {
             return itemNum;
         }

         public void setItemNum(String itemNum) {
             this.itemNum = itemNum;
         }

         public String getItemDesc() {
             return itemDesc;
         }

         public void setItemDesc(String itemDesc) {
             this.itemDesc = itemDesc;
         }

         public int getItemId() {
             return itemId;
         }

         public void setItemId(int itemId) {
             this.itemId = itemId;
         }

         public String getItemLongDesc() {
             return itemLongDesc;
         }

         public void setItemLongDesc(String itemLongDesc) {
             this.itemLongDesc = itemLongDesc;
         }

         public String getPriceLine() {
             return priceLine;
         }

         public void setPriceLine(String priceLine) {
             this.priceLine = priceLine;
         }

         public String getCatalogNum() {
             return catalogNum;
         }

         public void setCatalogNum(String catalogNum) {
             this.catalogNum = catalogNum;
         }

         public String getManufacturer() {
             return manufacturer;
         }

         public void setManufacturer(String manufacturer) {
             this.manufacturer = manufacturer;
         }

         public String getUom() {
             return uom;
         }

         public void setUom(String uom) {
             this.uom = uom;
         }

         public int getManufacturerId() {
             return manufacturerId;
         }

         public void setManufacturerId(int manufacturerId) {
             this.manufacturerId = manufacturerId;
         }

         public String getPack() {
             return pack;
         }

         public void setPack(String pack) {
             this.pack = pack;
         }

         public String getJdUom() {
             return jdUom;
         }

         public void setJdUom(String jdUom) {
             this.jdUom = jdUom;
         }

         public String getCategory() {
             return category;
         }

         public void setCategory(String category) {
             this.category = category;
         }

         public int getCategoryId() {
             return categoryId;
         }

         public void setCategoryId(int categoryId) {
             this.categoryId = categoryId;
         }

         public String getItemActionCd() {
             return itemActionCd;
         }

         public void setItemActionCd(String itemActionCd) {
             this.itemActionCd = itemActionCd;
         }

         public String getItemCategoryActionCd() {
             return itemCategoryActionCd;
         }

         public void setItemCategoryActionCd(String itemCategoryActionCd) {
             this.itemCategoryActionCd = itemCategoryActionCd;
         }

         public String getItemMetaUomActionCd() {
             return itemMetaUomActionCd;
         }

         public void setItemMetaUomActionCd(String itemMetaUomActionCd) {
             this.itemMetaUomActionCd = itemMetaUomActionCd;
         }

         public String getItemMetaPackActionCd() {
             return itemMetaPackActionCd;
         }

         public void setItemMetaPackActionCd(String itemMetaPackActionCd) {
             this.itemMetaPackActionCd = itemMetaPackActionCd;
         }

         public String getItemDistributorActionCd() {
             return itemDistributorActionCd;
         }

         public void setItemDistributorActionCd(String itemDistributorActionCd) {
             this.itemDistributorActionCd = itemDistributorActionCd;
         }

         public String getItemManufacturerActionCd() {
             return itemManufacturerActionCd;
         }

         public void setItemManufacturerActionCd(String itemManufacturerActionCd) {
             this.itemManufacturerActionCd = itemManufacturerActionCd;
         }

         public String getItemCatalogActionCd() {
             return itemCatalogActionCd;
         }

         public void setItemCatalogActionCd(String itemCatalogActionCd) {
             this.itemCatalogActionCd = itemCatalogActionCd;
         }
     }

    private class aInvoiceRecord implements Record, java.io.Serializable {

        private int accountId;
        private int siteId;
        private int invoiceDistId;
        private String invoiceNum;
        private Date invoiceDate;
        private String billTo;
        private String shipTo;
        private String shipToName;
        private BigDecimal subTotal;
        private String branch;
        private String repNum;
        private String repName;
        private String customerPo;
        private  String invActionCd;


        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public int getInvoiceDistId() {
            return invoiceDistId;
        }

        public void setInvoiceDistId(int invoiceDistId) {
            this.invoiceDistId = invoiceDistId;
        }

        public String getInvoiceNum() {
            return invoiceNum;
        }

        public void setInvoiceNum(String invoiceNum) {
            this.invoiceNum = invoiceNum;
        }

        public String getBillTo() {
            return billTo;
        }

        public void setBillTo(String billTo) {
            this.billTo = billTo;
        }

        public Date getInvoiceDate() {
            return invoiceDate;
        }

        public void setInvoiceDate(Date invoiceDate) {
            this.invoiceDate = invoiceDate;
        }

        public String getShipTo() {
            return shipTo;
        }

        public void setShipTo(String shipTo) {
            this.shipTo = shipTo;
        }

        public String getShipToName() {
            return shipToName;
        }

        public void setShipToName(String shipToName) {
            this.shipToName = shipToName;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public BigDecimal getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
        }

        public String getRepNum() {
            return repNum;
        }

        public void setRepNum(String repNum) {
            this.repNum = repNum;
        }

        public String getRepName() {
            return repName;
        }

        public void setRepName(String repName) {
            this.repName = repName;
        }

        public String getCustomerPo() {
            return customerPo;
        }

        public void setCustomerPo(String customerPo) {
            this.customerPo = customerPo;
        }

        public String getInvActionCd() {
            return invActionCd;
        }

        public void setInvActionCd(String invActionCd) {
            this.invActionCd = invActionCd;
        }

        public List toList() {

            ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(this.accountId);
            list.add(this.siteId);
            list.add(this.invoiceDistId);
            list.add(this.invoiceNum);
            list.add(this.invoiceDate);
            list.add(this.billTo);
            list.add(this.shipTo);
            list.add(this.shipToName);
            list.add(this.subTotal);
            list.add(this.branch);
            list.add(this.repNum);
            list.add(this.repName);
            list.add(this.customerPo);
            list.add(this.invActionCd);

            return list;
        }
    }



}
