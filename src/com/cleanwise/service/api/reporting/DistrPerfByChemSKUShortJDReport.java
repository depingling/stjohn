package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class DistrPerfByChemSKUShortJDReport implements GenericReport, java.io.Serializable {

    private static final Logger log = Logger.getLogger(DistrPerfByChemSKUShortJDReport.class);

    private static final String BEG_DATE = "BEG_DATE";
    private static final String END_DATE = "END_DATE";
    private static final String STORE_ID = "STORE";

    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        String begDateStr = (String) pParams.get(BEG_DATE);
        String endDateStr = (String) pParams.get(END_DATE);
        String storeIdStr = (String) pParams.get(STORE_ID);
        int storeId;

        try {
            storeId = Integer.parseInt(storeIdStr);
        } catch (NumberFormatException e) {
            log.info(e.getMessage(), e);
            String mess = "^clw^Store identifier is not a valid value^clw^";
            throw new Exception(mess);
        }

        if (!ReportingUtils.isValidDate(begDateStr)) {
            String mess = "^clw^\"" + begDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        if (!ReportingUtils.isValidDate(endDateStr)) {
            String mess = "^clw^\"" + endDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        GregorianCalendar currBegCalendar = new GregorianCalendar();
        currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

        GregorianCalendar priorBegCalendar = (GregorianCalendar) currBegCalendar.clone();
        priorBegCalendar.add(GregorianCalendar.YEAR, -1);

        GregorianCalendar currEndCalendar = new GregorianCalendar();
        currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

        GregorianCalendar priorEndCalendar = (GregorianCalendar) currEndCalendar.clone();
        priorEndCalendar.add(GregorianCalendar.YEAR, -1);


        String sql = "SELECT RES.SHORT_DESC,RES.ENTERPRISE_SHORT_DESC,RES.ENTERPRISE_MANUF_NUM,RES.SUPPLIER_NAME,RES.SUBCATEGORY," +
                "RES.BUS_ENTITY_ID,RES.DIST_ITEM_SKU_NUM,RES.DIST_ITEM_QTY_RECEIVED_1,RES.ITEM_RECEIVED_COST_1,RES.DIST_INTO_STOCK_COST_1," +
                "TO_NUMBER(DECODE(SP.SUM_PRICES,0,NULL,(RES.ITEM_RECEIVED_COST_1/SP.SUM_PRICES)))AS PER_OF_TOTAL," +
                "DECODE(RES.ITEM_RECEIVED_COST_2,0,DECODE(RES.ITEM_RECEIVED_COST_1,0,0,(SIGN(RES.ITEM_RECEIVED_COST_1))),((RES.ITEM_RECEIVED_COST_1-RES.ITEM_RECEIVED_COST_2)/RES.ITEM_RECEIVED_COST_2)) AS SALES_GROWTH,\n" +
                "DECODE(RES.DIST_ITEM_QTY_RECEIVED_2,0,DECODE(RES.DIST_ITEM_QTY_RECEIVED_1,0,0,(SIGN(RES.DIST_ITEM_QTY_RECEIVED_1))),(( RES.DIST_ITEM_QTY_RECEIVED_1- RES.DIST_ITEM_QTY_RECEIVED_2)/ RES.DIST_ITEM_QTY_RECEIVED_2)) AS UNITS_GROWTH,\n" +
                "(ITEM_RECEIVED_COST_1- RES.DIST_INTO_STOCK_COST_1) AS DEC_MARGIN,\n" +
                "TO_NUMBER(DECODE(ITEM_RECEIVED_COST_1,0,NULL,((ITEM_RECEIVED_COST_1- RES.DIST_INTO_STOCK_COST_1)/ITEM_RECEIVED_COST_1))) AS PER_MARGIN,\n" +
                "RES.DIST_ITEM_QTY_RECEIVED_2,RES.ITEM_RECEIVED_COST_2,RES.DIST_INTO_STOCK_COST_2  FROM (SELECT   II.*,  EI.ENTERPRISE_SHORT_DESC,  EI.ENTERPRISE_MANUF_NUM,  EI.SUPPLIER_NAME, \n" +
                "EI.SUBCATEGORY FROM  (SELECT T1.BUS_ENTITY_ID,  T1.DIST_ITEM_SKU_NUM, T1.SHORT_DESC,\n" +
                "NVL(T1.DIST_ITEM_QTY_RECEIVED_1,0) AS DIST_ITEM_QTY_RECEIVED_1, NVL(T1.ITEM_RECEIVED_COST_1,0) AS ITEM_RECEIVED_COST_1,  NVL(T1.DIST_INTO_STOCK_COST_1,0) AS DIST_INTO_STOCK_COST_1, \n" +
                "NVL(T2.DIST_ITEM_QTY_RECEIVED_2,0) AS DIST_ITEM_QTY_RECEIVED_2,  NVL(T2.ITEM_RECEIVED_COST_2,0) AS ITEM_RECEIVED_COST_2,  NVL(T2.DIST_INTO_STOCK_COST_2,0) AS DIST_INTO_STOCK_COST_2\n" +
                "  FROM (SELECT ID.BUS_ENTITY_ID,  IDD.DIST_ITEM_SKU_NUM, IDD.DIST_ITEM_SHORT_DESC as SHORT_DESC, SUM(DIST_ITEM_QTY_RECEIVED) AS DIST_ITEM_QTY_RECEIVED_1,  SUM(DIST_ITEM_QTY_RECEIVED*IDD.ITEM_RECEIVED_COST) AS ITEM_RECEIVED_COST_1,  SUM(IDD.DIST_INTO_STOCK_COST) AS DIST_INTO_STOCK_COST_1    FROM CLW_INVOICE_DIST ID,  CLW_INVOICE_DIST_DETAIL IDD\n" +
                "WHERE  ID.INVOICE_DATE BETWEEN " + ReportingUtils.toSQLDate(currBegCalendar.getTime()) + "\n" +
                "  AND " + ReportingUtils.toSQLDate(currEndCalendar.getTime()) + "\n" +
                "  AND IDD.INVOICE_DIST_ID = ID.INVOICE_DIST_ID  \n" +
                "  AND ID.SITE_ID IN" + "(" + getSiteIdsSql(storeId) + ")" + " GROUP BY  ID.BUS_ENTITY_ID,IDD.DIST_ITEM_SKU_NUM,IDD.DIST_ITEM_SHORT_DESC)  T1 \n" +
                "  \n" +
                "  LEFT JOIN \n" +
                "  \n" +
                "(SELECT ID.BUS_ENTITY_ID,IDD.DIST_ITEM_SKU_NUM,SUM(DIST_ITEM_QTY_RECEIVED) AS DIST_ITEM_QTY_RECEIVED_2,SUM(DIST_ITEM_QTY_RECEIVED*IDD.ITEM_RECEIVED_COST) AS ITEM_RECEIVED_COST_2,SUM(IDD.DIST_INTO_STOCK_COST) AS DIST_INTO_STOCK_COST_2  FROM CLW_INVOICE_DIST ID,CLW_INVOICE_DIST_DETAIL IDD    \n" +
                " WHERE  ID.INVOICE_DATE  BETWEEN " + ReportingUtils.toSQLDate(priorBegCalendar.getTime()) + "\n" +
                "  AND " + ReportingUtils.toSQLDate(priorEndCalendar.getTime()) + "\n" +
                "  AND IDD.INVOICE_DIST_ID = ID.INVOICE_DIST_ID   \n" +
                " AND ID.SITE_ID IN" + "(" + getSiteIdsSql(storeId) + ")" + " GROUP BY  ID.BUS_ENTITY_ID,IDD.DIST_ITEM_SKU_NUM) T2 ON T1.BUS_ENTITY_ID = T2.BUS_ENTITY_ID AND T1.DIST_ITEM_SKU_NUM=T2.DIST_ITEM_SKU_NUM) II\n" +
                " \n" +
                "  LEFT JOIN\n" +
                "  \n" +
                " (SELECT IM.ITEM_ID,EI.ITEM_ID AS ENTERPRISE_ITEM_ID,EI.SHORT_DESC AS ENTERPRISE_SHORT_DESC,EIM.ITEM_NUM AS ENTERPRISE_MANUF_NUM,BE.SHORT_DESC AS SUPPLIER_NAME,EC.SHORT_DESC AS SUBCATEGORY, IM.BUS_ENTITY_ID,IM.ITEM_NUM   FROM CLW_ITEM_MAPPING IM,CLW_ITEM_ASSOC IA,CLW_ITEM EI,CLW_ITEM_MAPPING EIM,CLW_BUS_ENTITY BE ,CLW_ITEM_ASSOC EIA,CLW_ITEM EC \n" +
                "    WHERE (IM.BUS_ENTITY_ID,IM.ITEM_NUM)  IN \n" +
                "\t( SELECT ID.BUS_ENTITY_ID,IDD.DIST_ITEM_SKU_NUM FROM CLW_INVOICE_DIST ID,CLW_INVOICE_DIST_DETAIL IDD  \n" +
                " WHERE  ID.INVOICE_DATE BETWEEN " + ReportingUtils.toSQLDate(currBegCalendar.getTime()) + "\n" +
                "  AND " + ReportingUtils.toSQLDate(currEndCalendar.getTime()) + "\n" +
                "  AND ID.SITE_ID IN" + "(" + getSiteIdsSql(storeId) + ")" +
                "  AND IDD.INVOICE_DIST_ID = ID.INVOICE_DIST_ID GROUP BY  ID.BUS_ENTITY_ID,IDD.DIST_ITEM_SKU_NUM)\n" +
                "  AND IM.ITEM_MAPPING_CD='" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "' \n" +
                "  AND IM.ITEM_ID = IA.ITEM1_ID \n" +
                "  AND IA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.CROSS_STORE_ITEM_LINK + "' \n" +
                "  AND IA.CATALOG_ID =13307 \n" +
				
                "  AND EI.ITEM_ID=IA.ITEM2_ID \n" +
                "  AND EI.ITEM_TYPE_CD='" + RefCodeNames.ITEM_TYPE_CD.PRODUCT + "'\n" +
                "  AND EIM.ITEM_ID=EI.ITEM_ID \n" +
                "  AND EIM.ITEM_MAPPING_CD='" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' \n" +
                "  AND BE.BUS_ENTITY_ID = EIM.BUS_ENTITY_ID  \n" +
                "  AND BE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER + "' \n" +
                "  AND EIA.ITEM1_ID=EI.ITEM_ID  \n" +
                "  AND EIA.ITEM_ASSOC_CD='" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' \n" +
                "  AND EC.ITEM_ID=EIA.ITEM2_ID  \n" +
                "  AND EC.ITEM_TYPE_CD='" + RefCodeNames.ITEM_TYPE_CD.CATEGORY + "') EI \n" +
                "ON II.BUS_ENTITY_ID=EI.BUS_ENTITY_ID AND II.DIST_ITEM_SKU_NUM = EI.ITEM_NUM) RES \n" +
                " \n" +
                " LEFT JOIN \n" +
                " \n" +
                "(SELECT ID.BUS_ENTITY_ID, SUM(DIST_ITEM_QTY_RECEIVED*ITEM_RECEIVED_COST) AS SUM_PRICES   FROM CLW_INVOICE_DIST ID, CLW_INVOICE_DIST_DETAIL IDD\n" +
                " WHERE  ID.INVOICE_DATE BETWEEN " + ReportingUtils.toSQLDate(currBegCalendar.getTime()) + "\n" +
                "  AND " + ReportingUtils.toSQLDate(currEndCalendar.getTime()) + "\n" +
                "  AND IDD.INVOICE_DIST_ID = ID.INVOICE_DIST_ID  \n" +
                "  AND ID.SITE_ID IN" + "(" + getSiteIdsSql(storeId) + ")" + " GROUP BY ID.BUS_ENTITY_ID) SP\n" +
                " ON RES.BUS_ENTITY_ID = SP.BUS_ENTITY_ID";

        log.info("process sql:" + sql);

        Statement stmt = pCons.getMainConnection().createStatement();

        ArrayList records = new ArrayList();

        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            aRecord record = new aRecord();

            record.setProductName(rs.getString(1));
            record.setEnterpriseProductName(rs.getString(2));
            record.setManufacturerSku(rs.getString(3));
            record.setManufacturer(rs.getString(4));
            record.setCategory(rs.getString(5));
            record.setCurrProductUnits(rs.getInt(8));
            record.setCurrProductSales(rs.getBigDecimal(9));
            record.setCurrProductCost(rs.getBigDecimal(10));
            record.setPerOfTotal(rs.getBigDecimal(11));
            record.setSalesGrowth(rs.getBigDecimal(12));
            record.setUnitsGrowth(rs.getBigDecimal(13));
            record.setDecMargin(rs.getBigDecimal(14));
            record.setPerMargin(rs.getBigDecimal(15));
            record.setPriorProductUnits(rs.getInt(16));
            record.setPriorProductSales(rs.getBigDecimal(17));
            record.setPriorProductCost(rs.getBigDecimal(18));


            records.add(record.toList());
        }

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader(currBegCalendar.get(GregorianCalendar.YEAR));
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Chem SKU Short");
        result.setTable(records);

        return result;
    }

    private GenericReportColumnViewVector getReportHeader(int year) {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SKU", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Product", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Subcategory", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Supplier Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", (year - 1) + " Sales $$", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", (year) + " Sales $$", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "% of Total %", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", (year) + " Cost $$", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Sales Growth %", 2, 20, "NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", (year - 1) + " Units", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", (year) + " Units", 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Units Growth %", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", (year) + " Margin $$", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", (year) + " Margin %", 4, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", year + " Avg Inv $$", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Inv Turns", 0, 255, "VARCHAR2"));

        return header;
    }

    public String getSiteIdsSql(int pStoreId){
        return "SELECT SITE.BUS_ENTITY_ID FROM CLW_BUS_ENTITY ACCOUNT,CLW_BUS_ENTITY SITE,CLW_BUS_ENTITY_ASSOC BEA1,CLW_BUS_ENTITY_ASSOC BEA2\n" +
                "     WHERE BEA1.BUS_ENTITY2_ID = "+pStoreId+"\n" +
                "         AND ACCOUNT.BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n" +
                "         AND ACCOUNT.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT + "' \n" +
                "         AND SITE.BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n" +
                "         AND SITE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'  \n" +
                "         AND BEA1.BUS_ENTITY1_ID = ACCOUNT.BUS_ENTITY_ID\n" +
                "         AND BEA1.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "' \n" +
                "         AND BEA2.BUS_ENTITY1_ID = SITE.BUS_ENTITY_ID\n" +
                "         AND BEA2.BUS_ENTITY2_ID = BEA1.BUS_ENTITY1_ID\n" +
                "         AND BEA2.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'";
    }
    
    private class aRecord implements Record, java.io.Serializable {

        private String productName;
        private String enterpriseProductName;
        private String manufacturerSku;
        private String manufacturer;
        private String category;
        private Integer currProductUnits;
        private BigDecimal currProductSales;
        private BigDecimal currProductCost;
        private BigDecimal perOfTotal;
        private BigDecimal salesGrowth;
        private BigDecimal unitsGrowth;
        private BigDecimal decMargin;
        private BigDecimal perMargin;
        private Integer priorProductUnits;
        private BigDecimal priorProductSales;
        private BigDecimal priorProductCost;

        public List toList() {
            ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(manufacturerSku);
            list.add(enterpriseProductName == null ? productName : enterpriseProductName);
            list.add(category);
            list.add(manufacturer);
            list.add(priorProductSales);
            list.add(currProductSales);
            list.add(perOfTotal);
            list.add(currProductCost);
            list.add(salesGrowth);
            list.add(priorProductUnits);
            list.add(currProductUnits);
            list.add(unitsGrowth);
            list.add(decMargin);
            list.add(perMargin);
            list.add(null);
            list.add(null);

            return list;
        }


        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getCurrProductUnits() {
            return currProductUnits;
        }

        public void setCurrProductUnits(Integer currProductUnits) {
            this.currProductUnits = currProductUnits;
        }

        public BigDecimal getCurrProductSales() {
            return currProductSales;
        }

        public void setCurrProductSales(BigDecimal currProductSales) {
            this.currProductSales = currProductSales;
        }

        public BigDecimal getPerOfTotal() {
            return perOfTotal;
        }

        public void setPerOfTotal(BigDecimal perOfTotal) {
            this.perOfTotal = perOfTotal;
        }

        public BigDecimal getSalesGrowth() {
            return salesGrowth;
        }

        public void setSalesGrowth(BigDecimal salesGrowth) {
            this.salesGrowth = salesGrowth;
        }

        public BigDecimal getUnitsGrowth() {
            return unitsGrowth;
        }

        public void setUnitsGrowth(BigDecimal unitsGrowth) {
            this.unitsGrowth = unitsGrowth;
        }

        public BigDecimal getDecMargin() {
            return decMargin;
        }

        public void setDecMargin(BigDecimal decMargin) {
            this.decMargin = decMargin;
        }

        public BigDecimal getPerMargin() {
            return perMargin;
        }

        public void setPerMargin(BigDecimal perMargin) {
            this.perMargin = perMargin;
        }

        public Integer getPriorProductUnits() {
            return priorProductUnits;
        }

        public void setPriorProductUnits(Integer priorProductUnits) {
            this.priorProductUnits = priorProductUnits;
        }

        public BigDecimal getPriorProductSales() {
            return priorProductSales;
        }

        public void setPriorProductSales(BigDecimal priorProductSales) {
            this.priorProductSales = priorProductSales;
        }

        public String getEnterpriseProductName() {
            return enterpriseProductName;
        }

        public void setEnterpriseProductName(String enterpriseProductName) {
            this.enterpriseProductName = enterpriseProductName;
        }


        public String getManufacturerSku() {
            return manufacturerSku;
        }

        public void setManufacturerSku(String manufacturerSku) {
            this.manufacturerSku = manufacturerSku;
        }

        public BigDecimal getCurrProductCost() {
            return currProductCost;
        }

        public void setCurrProductCost(BigDecimal currProductCost) {
            this.currProductCost = currProductCost;
        }


        public BigDecimal getPriorProductCost() {
            return priorProductCost;
        }

        public void setPriorProductCost(BigDecimal priorProducCost) {
            this.priorProductCost = priorProducCost;
        }
    }
}
