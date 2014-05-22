package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.SelfServiceErp;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;


import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.*;

/**
 */
public class ErpSystemsCompareReport implements GenericReport, Serializable {


    private static String className = "ErpSystemsCompareReport";
    private static final Logger log = Logger.getLogger(ErpSystemsCompareReport.class);
    private static final String GET = "get";

    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        ArrayList<List> records = new ArrayList<List>();

        String bDate = (String) getParamValue(pParams, "B_DATE");
        String eDate = (String) getParamValue(pParams, "E_DATE");
        boolean debug = Utility.isTrue((String) getParamValue(pParams, "DEBUG"));
        boolean addNotes = Utility.isTrue((String) getParamValue(pParams, "NOTES"),true);

        if (!ReportingUtils.isValidDate(bDate)) {
            String mess = "^clw^\"" + bDate + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        if (!ReportingUtils.isValidDate(eDate)) {
            String mess = "^clw^\"" + eDate + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        SelfServiceErp selfService = APIAccess.getAPIAccess().getSelfServiceErpAPI();

        PairViewVector invPairs = getInvoicePairsForReport(pCons.getMainConnection(), bDate, eDate);

        for (Object oInvPair : invPairs) {

            PairView invPair = (PairView) oInvPair;

            Integer invCustId = (Integer) invPair.getObject1();
            Integer invDistId = (Integer) invPair.getObject2();

            InvoiceCustDescData emulatedCustomerInvoice = selfService.service(invDistId, true);
            InvoiceCustDescData realCustomerInvoice = getInvoiceCust(invCustId);

            for (Object oRealCustDetail : realCustomerInvoice.getInvoiceCustDetailList()) {

                InvoiceCustDetailData realCustDetail = (InvoiceCustDetailData) oRealCustDetail;

                InvoiceCustDetailData emCustDetail = InvoiceCustDetailData.createValue();
                for (Object oEmCustDetail : emulatedCustomerInvoice.getInvoiceCustDetailList()) {
                    emCustDetail = (InvoiceCustDetailData) oEmCustDetail;
                    if (emCustDetail.getOrderItemId() == realCustDetail.getOrderItemId()) {
                        break;
                    }
                }

                MatchResult matchRes = match(emulatedCustomerInvoice.getInvoiceCust(), emCustDetail, realCustomerInvoice.getInvoiceCust(), realCustDetail, debug);

                if (matchRes != null) {

                    aRecord record = new aRecord();

                    record.setEmCustInvoiceId(emulatedCustomerInvoice.getInvoiceCust().getInvoiceCustId());
                    record.setRealCustInvoiceId(realCustomerInvoice.getInvoiceCust().getInvoiceCustId());

                    record.setEmCustInvoiceNum(emulatedCustomerInvoice.getInvoiceCust().getInvoiceNum());
                    record.setRealCustInvoiceNum(realCustomerInvoice.getInvoiceCust().getInvoiceNum());

                    record.setEmCustInvoiceDate(emulatedCustomerInvoice.getInvoiceCust().getInvoiceDate());
                    record.setRealCustInvoiceDate(realCustomerInvoice.getInvoiceCust().getInvoiceDate());

                    record.setEmCustOrderId(emulatedCustomerInvoice.getInvoiceCust().getOrderId());
                    record.setRealCustOrderId(realCustomerInvoice.getInvoiceCust().getOrderId());

                    record.setEmCustInvoiceDetailId(emCustDetail.getInvoiceCustDetailId());
                    record.setRealCustInvoiceDetailId(realCustDetail.getInvoiceCustDetailId());

                    record.setEmCustOrderItemId(emCustDetail.getOrderItemId());
                    record.setRealCustOrderItemId(realCustDetail.getOrderItemId());

                    record.setEmLineNumber(emCustDetail.getLineNumber());
                    record.setRealLineNumber(realCustDetail.getLineNumber());

                    record.setEmItemSkuNum(emCustDetail.getItemSkuNum());
                    record.setRealItemSkuNum(realCustDetail.getItemSkuNum());

                    record.setEmCustItemShosrDesc(emCustDetail.getItemShortDesc());
                    record.setRealCustItemShosrDesc(realCustDetail.getItemShortDesc());

                    record.setEmCustItemUom(emCustDetail.getItemUom());
                    record.setRealCustItemUom(realCustDetail.getItemUom());

                    record.setEmCustItemPack(emCustDetail.getItemPack());
                    record.setRealCustItemPack(realCustDetail.getItemPack());

                    record.setEmCustItemQty(emCustDetail.getItemQuantity());
                    record.setRealCustItemQty(realCustDetail.getItemQuantity());

                    record.setEmCustItemContractPrice(emCustDetail.getCustContractPrice());
                    record.setRealCustItemContractPrice(realCustDetail.getCustContractPrice());

                    record.setEmCustItemLineTotal(emCustDetail.getLineTotal());
                    record.setRealCustItemLineTotal(realCustDetail.getLineTotal());

                    record.setEmCustNetDue(emulatedCustomerInvoice.getInvoiceCust().getNetDue());
                    record.setRealCustNetDue(realCustomerInvoice.getInvoiceCust().getNetDue());

                    record.setEmCustSubTotal(emulatedCustomerInvoice.getInvoiceCust().getSubTotal());
                    record.setRealCustSubTotal(realCustomerInvoice.getInvoiceCust().getSubTotal());

                    record.setEmCustSubTotal(emulatedCustomerInvoice.getInvoiceCust().getSubTotal());
                    record.setRealCustSubTotal(realCustomerInvoice.getInvoiceCust().getSubTotal());

                    record.setEmCustFreight(emulatedCustomerInvoice.getInvoiceCust().getFreight());
                    record.setRealCustFreight(realCustomerInvoice.getInvoiceCust().getFreight());

                    record.setEmCustSalesTax(emulatedCustomerInvoice.getInvoiceCust().getSalesTax());
                    record.setRealCustSalesTax(realCustomerInvoice.getInvoiceCust().getSalesTax());

                    record.setEmCustMiscCharges(emulatedCustomerInvoice.getInvoiceCust().getMiscCharges());
                    record.setRealCustMiscCharges(realCustomerInvoice.getInvoiceCust().getMiscCharges());

                    record.setEmCustFuelSurcharge(emulatedCustomerInvoice.getInvoiceCust().getFuelSurcharge());
                    record.setRealCustFuelSurcharge(realCustomerInvoice.getInvoiceCust().getFuelSurcharge());

                    record.setEmCustDiscounts(emulatedCustomerInvoice.getInvoiceCust().getDiscounts());
                    record.setRealCustDiscounts(realCustomerInvoice.getInvoiceCust().getDiscounts());

                    setEmptySalesTax(emulatedCustomerInvoice,realCustomerInvoice);
                    MatchResult matchResWithEmptyTax = match(emulatedCustomerInvoice.getInvoiceCust(), emCustDetail, realCustomerInvoice.getInvoiceCust(), realCustDetail, debug);
                    record.setMatchStatusWithEmptyTax(matchResWithEmptyTax.getResult() ? "OK" : "FAILED");

                    record.setMatchStatus(matchRes.getResult() ? "OK" : "FAILED");
                    if(addNotes && !matchRes.getMatchNotes().isEmpty()){
                        record.setMatchNotes(matchRes.getMatchNotes().toString());
                    }

                    records.add(record.toList());
                }

            }
        }

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Sheet_1");
        result.setTable(records);

        return result;
    }

    private void setEmptySalesTax(InvoiceCustDescData invoiceCust1, InvoiceCustDescData invoiceCust2) {

        BigDecimal currentTax1 = invoiceCust1.getInvoiceCust().getSalesTax();
        BigDecimal currentTax2 = invoiceCust2.getInvoiceCust().getSalesTax();

        if (currentTax1 != null) {
            BigDecimal netDue = invoiceCust1.getInvoiceCust().getNetDue();
            invoiceCust1.getInvoiceCust().setNetDue(Utility.subtractAmt(netDue, currentTax1));
            invoiceCust1.getInvoiceCust().setSalesTax(new BigDecimal(0));
        } else {
            invoiceCust1.getInvoiceCust().setSalesTax(new BigDecimal(0));
        }

        if (currentTax2 != null) {
            BigDecimal netDue = invoiceCust2.getInvoiceCust().getNetDue();
            invoiceCust2.getInvoiceCust().setNetDue(Utility.subtractAmt(netDue, currentTax2));
            invoiceCust2.getInvoiceCust().setSalesTax(new BigDecimal(0));
        } else {
            invoiceCust2.getInvoiceCust().setSalesTax(new BigDecimal(0));
        }

    }

    private InvoiceCustDescData getInvoiceCust(int invoiceCustId) throws Exception {

        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
        InvoiceCustDescData result = new InvoiceCustDescData();

        result.setInvoiceCust(orderEjb.getInvoiceCust(invoiceCustId));
        result.setInvoiceCustDetailList(orderEjb.getInvoiceCustDetailCollection(invoiceCustId));

        return result;

    }

    private PairViewVector getInvoicePairsForReport(Connection conn, String bDate, String eDate) throws SQLException {

        PairViewVector result = new PairViewVector();

        String sql = "SELECT \n" +
                " IC.INVOICE_CUST_ID,ID.INVOICE_DIST_ID\n" +
                "FROM \n" +
                "  CLW_INVOICE_CUST IC ,\n" +
                "  CLW_INVOICE_DIST ID ,\n" +
                "  CLW_INVOICE_CUST_DETAIL ICD ,\n" +
                "  CLW_INVOICE_DIST_DETAIL IDD ,\n" +
                "  (SELECT INVOICE_DIST_ID, COUNT(INVOICE_DIST_ID) ITEM_COUNT FROM CLW_INVOICE_DIST_DETAIL GROUP BY INVOICE_DIST_ID) IDD_COUNT ,\n" +
                "  (SELECT INVOICE_CUST_ID, COUNT(INVOICE_CUST_ID) ITEM_COUNT FROM CLW_INVOICE_CUST_DETAIL WHERE INVOICE_DETAIL_STATUS_CD = 'CUST_INVOICED' GROUP BY INVOICE_CUST_ID) ICD_COUNT\n" +
                "WHERE ID.ORDER_ID = IC.ORDER_ID \n" +
                "   AND ID.ERP_SYSTEM_CD='LAWSON'   \n" +
                "   AND IC.ERP_SYSTEM_CD='LAWSON'\n" +
                "   AND IC.INVOICE_CUST_ID = ICD_COUNT.INVOICE_CUST_ID  \n" +
                "   AND ID.INVOICE_DIST_ID = IDD_COUNT.INVOICE_DIST_ID \n" +
                "   AND ICD_COUNT.ITEM_COUNT = IDD_COUNT.ITEM_COUNT\n" +
                "   AND ICD.INVOICE_CUST_ID = ICD_COUNT.INVOICE_CUST_ID \n" +
                "   AND IDD.INVOICE_DIST_ID = IDD_COUNT.INVOICE_DIST_ID  \n" +
                "   AND IDD.ORDER_ITEM_ID = ICD.ORDER_ITEM_ID  \n" +
                "   AND ID.INVOICE_DATE BETWEEN " + ReportingUtils.toSQLDate(bDate) + " AND " + ReportingUtils.toSQLDate(eDate) +
                "   AND ID.INVOICE_STATUS_CD IN('PROCESS_ERP')\n" +
                "   AND IC.INVOICE_STATUS_CD IN ('CUST_INVOICED') \n" +
                "   AND ICD.INVOICE_DETAIL_STATUS_CD IN ('CUST_INVOICED')  GROUP BY IC.INVOICE_CUST_ID,ID.INVOICE_DIST_ID";

        log.info("getInvoicesForReport => sql:" + sql);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int cid = rs.getInt(1);
            int did = rs.getInt(2);
            result.add(new PairView(cid, did));
        }

        rs.close();
        stmt.close();

        log.info("getInvoicesForReport => result size:" + result.size());

        return result;
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

    private GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Customer Invoice Id", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Customer Invoice Id", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Emulated Customer Invoice Num", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Real Customer Invoice Num", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Emulated Invoice Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Real Invoice Date", 0, 0, "DATE"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Order Id", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Order Id", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Customer Invoice Detail Id ", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Customer Invoice Detail Id", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Order Item Id ", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Order Item Id", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Line Number", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Line Number", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Item Sku Num", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Item Sku Num", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Emulated Item Short Desc", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Real Item Short Desc", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Emulated Item Uom", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Real Item Uom", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Emulated Item Pack", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Real Item Pack", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Emulated Item Qty", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Real Item Qty", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Item Contract Price", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Item Contract Price", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Item Line Total", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Item Line Total", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated NetDue", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real NetDue", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated SubTotal", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real SubTotal", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Freight", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Freight", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Sales Tax", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Sales Tax", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Misc Charges", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Misc Charges", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Fuel Surcharge", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Fuel Surcharge", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Emulated Discounts", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Real Discounts", 2, 20, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Tax Exempt Status", 0, 255, "VARCHAR2"));          
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Notes", 0, 255, "VARCHAR2"));


        return header;
    }


    private class aRecord implements Record, Serializable {

        Integer emCustInvoiceId;
        String emCustInvoiceNum;
        Date emCustInvoiceDate;
        Integer emCustOrderId;

        Integer emCustInvoiceDetailId;
        Integer emCustOrderItemId;
        Integer emLineNumber;
        Integer emItemSkuNum;
        String emCustItemShosrDesc;
        String emCustItemUom;
        String emCustItemPack;
        Integer emCustItemQty;
        BigDecimal emCustItemContractPrice;
        BigDecimal emCustItemLineTotal;
        BigDecimal emCustNetDue;
        BigDecimal emCustSubTotal;
        BigDecimal emCustFreight;
        BigDecimal emCustSalesTax;
        BigDecimal emCustMiscCharges;
        BigDecimal emCustFuelSurcharge;
        BigDecimal emCustDiscounts;

        Integer realCustInvoiceId;
        String realCustInvoiceNum;
        Date realCustInvoiceDate;
        Integer realCustOrderId;

        Integer realCustInvoiceDetailId;
        Integer realCustOrderItemId;
        Integer realLineNumber;
        Integer realItemSkuNum;
        String realCustItemShosrDesc;
        String realCustItemUom;
        String realCustItemPack;
        Integer realCustItemQty;
        BigDecimal realCustItemContractPrice;
        BigDecimal realCustItemLineTotal;
        BigDecimal realCustNetDue;
        BigDecimal realCustSubTotal;
        BigDecimal realCustFreight;
        BigDecimal realCustSalesTax;
        BigDecimal realCustMiscCharges;
        BigDecimal realCustFuelSurcharge;
        BigDecimal realCustDiscounts;

        String matchStatus;
        private String matchNotes;
        private String matchStatusWithEmptyTax;

        public List toList() {
            ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(emCustInvoiceId);
            list.add(realCustInvoiceId);

            list.add(emCustInvoiceNum);
            list.add(realCustInvoiceNum);

            list.add(emCustInvoiceDate);
            list.add(realCustInvoiceDate);

            list.add(emCustOrderId);
            list.add(realCustOrderId);

            list.add(emCustInvoiceDetailId);
            list.add(realCustInvoiceDetailId);

            list.add(emCustOrderItemId);
            list.add(realCustOrderItemId);

            list.add(emLineNumber);
            list.add(realLineNumber);

            list.add(emItemSkuNum);
            list.add(realItemSkuNum);

            list.add(emCustItemShosrDesc);
            list.add(realCustItemShosrDesc);

            list.add(emCustItemUom);
            list.add(realCustItemUom);

            list.add(emCustItemPack);
            list.add(realCustItemPack);

            list.add(emCustItemQty);
            list.add(realCustItemQty);

            list.add(emCustItemContractPrice);
            list.add(realCustItemContractPrice);

            list.add(emCustItemLineTotal);
            list.add(realCustItemLineTotal);

            list.add(emCustNetDue);
            list.add(realCustNetDue);

            list.add(emCustSubTotal);
            list.add(realCustSubTotal);

            list.add(emCustFreight);
            list.add(realCustFreight);

            list.add(emCustSalesTax);
            list.add(realCustSalesTax);

            list.add(emCustMiscCharges);
            list.add(realCustMiscCharges);

            list.add(emCustFuelSurcharge);
            list.add(realCustFuelSurcharge);

            list.add(emCustDiscounts);
            list.add(realCustDiscounts);

            list.add(matchStatusWithEmptyTax);
            list.add(matchStatus);
            list.add(matchNotes);

            return list;
        }

        public BigDecimal getEmCustNetDue() {
            return emCustNetDue;
        }

        public void setEmCustNetDue(BigDecimal emCustNetDue) {
            this.emCustNetDue = emCustNetDue;
        }

        public BigDecimal getRealCustNetDue() {
            return realCustNetDue;
        }

        public void setRealCustNetDue(BigDecimal realCustNetDue) {
            this.realCustNetDue = realCustNetDue;
        }

        public Integer getEmCustInvoiceId() {
            return emCustInvoiceId;
        }

        public void setEmCustInvoiceId(Integer emCustInvoiceId) {
            this.emCustInvoiceId = emCustInvoiceId;
        }

        public String getEmCustInvoiceNum() {
            return emCustInvoiceNum;
        }

        public void setEmCustInvoiceNum(String emCustInvoiceNum) {
            this.emCustInvoiceNum = emCustInvoiceNum;
        }

        public String getEmCustItemUom() {
            return emCustItemUom;
        }

        public void setEmCustItemUom(String emCustItemUom) {
            this.emCustItemUom = emCustItemUom;
        }

        public String getEmCustItemShosrDesc() {
            return emCustItemShosrDesc;
        }

        public void setEmCustItemShosrDesc(String emCustItemShosrDesc) {
            this.emCustItemShosrDesc = emCustItemShosrDesc;
        }

        public Integer getEmItemSkuNum() {
            return emItemSkuNum;
        }

        public void setEmItemSkuNum(Integer emItemSkuNum) {
            this.emItemSkuNum = emItemSkuNum;
        }

        public Integer getEmLineNumber() {
            return emLineNumber;
        }

        public void setEmLineNumber(Integer emLineNumber) {
            this.emLineNumber = emLineNumber;
        }

        public Integer getEmCustOrderItemId() {
            return emCustOrderItemId;
        }

        public void setEmCustOrderItemId(Integer emCustOrderItemId) {
            this.emCustOrderItemId = emCustOrderItemId;
        }

        public Integer getEmCustInvoiceDetailId() {
            return emCustInvoiceDetailId;
        }

        public void setEmCustInvoiceDetailId(Integer emCustInvoiceDetailId) {
            this.emCustInvoiceDetailId = emCustInvoiceDetailId;
        }

        public Integer getEmCustOrderId() {
            return emCustOrderId;
        }

        public void setEmCustOrderId(Integer emCustOrderId) {
            this.emCustOrderId = emCustOrderId;
        }

        public Date getEmCustInvoiceDate() {
            return emCustInvoiceDate;
        }

        public void setEmCustInvoiceDate(Date emCustInvoiceDate) {
            this.emCustInvoiceDate = emCustInvoiceDate;
        }

        public BigDecimal getRealCustDiscounts() {
            return realCustDiscounts;
        }

        public void setRealCustDiscounts(BigDecimal realCustDiscounts) {
            this.realCustDiscounts = realCustDiscounts;
        }

        public BigDecimal getRealCustFuelSurcharge() {
            return realCustFuelSurcharge;
        }

        public void setRealCustFuelSurcharge(BigDecimal realCustFuelSurcharge) {
            this.realCustFuelSurcharge = realCustFuelSurcharge;
        }

        public BigDecimal getRealCustMiscCharges() {
            return realCustMiscCharges;
        }

        public void setRealCustMiscCharges(BigDecimal realCustMiscCharges) {
            this.realCustMiscCharges = realCustMiscCharges;
        }

        public BigDecimal getRealCustSalesTax() {
            return realCustSalesTax;
        }

        public void setRealCustSalesTax(BigDecimal realCustSalesTax) {
            this.realCustSalesTax = realCustSalesTax;
        }

        public BigDecimal getRealCustFreight() {
            return realCustFreight;
        }

        public void setRealCustFreight(BigDecimal realCustFreight) {
            this.realCustFreight = realCustFreight;
        }

        public BigDecimal getRealCustSubTotal() {
            return realCustSubTotal;
        }

        public void setRealCustSubTotal(BigDecimal realCustSubTotal) {
            this.realCustSubTotal = realCustSubTotal;
        }

        public BigDecimal getRealCustItemLineTotal() {
            return realCustItemLineTotal;
        }

        public void setRealCustItemLineTotal(BigDecimal realCustItemLineTotal) {
            this.realCustItemLineTotal = realCustItemLineTotal;
        }

        public BigDecimal getRealCustItemContractPrice() {
            return realCustItemContractPrice;
        }

        public void setRealCustItemContractPrice(BigDecimal realCustItemContractPrice) {
            this.realCustItemContractPrice = realCustItemContractPrice;
        }

        public Integer getRealCustItemQty() {
            return realCustItemQty;
        }

        public void setRealCustItemQty(Integer realCustItemQty) {
            this.realCustItemQty = realCustItemQty;
        }

        public String getRealCustItemPack() {
            return realCustItemPack;
        }

        public void setRealCustItemPack(String realCustItemPack) {
            this.realCustItemPack = realCustItemPack;
        }

        public String getRealCustItemShosrDesc() {
            return realCustItemShosrDesc;
        }

        public void setRealCustItemShosrDesc(String realCustItemShosrDesc) {
            this.realCustItemShosrDesc = realCustItemShosrDesc;
        }

        public String getRealCustItemUom() {
            return realCustItemUom;
        }

        public void setRealCustItemUom(String realCustItemUom) {
            this.realCustItemUom = realCustItemUom;
        }

        public Integer getRealItemSkuNum() {
            return realItemSkuNum;
        }

        public void setRealItemSkuNum(Integer realItemSkuNum) {
            this.realItemSkuNum = realItemSkuNum;
        }

        public Integer getRealLineNumber() {
            return realLineNumber;
        }

        public void setRealLineNumber(Integer realLineNumber) {
            this.realLineNumber = realLineNumber;
        }

        public Integer getRealCustOrderItemId() {
            return realCustOrderItemId;
        }

        public void setRealCustOrderItemId(Integer realCustOrderItemId) {
            this.realCustOrderItemId = realCustOrderItemId;
        }

        public Integer getRealCustInvoiceDetailId() {
            return realCustInvoiceDetailId;
        }

        public void setRealCustInvoiceDetailId(Integer realCustInvoiceDetailId) {
            this.realCustInvoiceDetailId = realCustInvoiceDetailId;
        }

        public Integer getRealCustOrderId() {
            return realCustOrderId;
        }

        public void setRealCustOrderId(Integer realCustOrderId) {
            this.realCustOrderId = realCustOrderId;
        }

        public String getRealCustInvoiceNum() {
            return realCustInvoiceNum;
        }

        public void setRealCustInvoiceNum(String realCustInvoiceNum) {
            this.realCustInvoiceNum = realCustInvoiceNum;
        }

        public Date getRealCustInvoiceDate() {
            return realCustInvoiceDate;
        }

        public void setRealCustInvoiceDate(Date realCustInvoiceDate) {
            this.realCustInvoiceDate = realCustInvoiceDate;
        }

        public Integer getRealCustInvoiceId() {
            return realCustInvoiceId;
        }

        public void setRealCustInvoiceId(Integer realCustInvoiceId) {
            this.realCustInvoiceId = realCustInvoiceId;
        }

        public BigDecimal getEmCustFuelSurcharge() {
            return emCustFuelSurcharge;
        }

        public void setEmCustFuelSurcharge(BigDecimal emCustFuelSurcharge) {
            this.emCustFuelSurcharge = emCustFuelSurcharge;
        }

        public BigDecimal getEmCustDiscounts() {
            return emCustDiscounts;
        }

        public void setEmCustDiscounts(BigDecimal emCustDiscounts) {
            this.emCustDiscounts = emCustDiscounts;
        }

        public BigDecimal getEmCustMiscCharges() {
            return emCustMiscCharges;
        }

        public void setEmCustMiscCharges(BigDecimal emCustMiscCharges) {
            this.emCustMiscCharges = emCustMiscCharges;
        }

        public BigDecimal getEmCustSalesTax() {
            return emCustSalesTax;
        }

        public void setEmCustSalesTax(BigDecimal emCustSalesTax) {
            this.emCustSalesTax = emCustSalesTax;
        }

        public BigDecimal getEmCustFreight() {
            return emCustFreight;
        }

        public void setEmCustFreight(BigDecimal emCustFreight) {
            this.emCustFreight = emCustFreight;
        }

        public BigDecimal getEmCustSubTotal() {
            return emCustSubTotal;
        }

        public void setEmCustSubTotal(BigDecimal emCustSubTotal) {
            this.emCustSubTotal = emCustSubTotal;
        }

        public BigDecimal getEmCustItemLineTotal() {
            return emCustItemLineTotal;
        }

        public void setEmCustItemLineTotal(BigDecimal emCustItemLineTotal) {
            this.emCustItemLineTotal = emCustItemLineTotal;
        }

        public BigDecimal getEmCustItemContractPrice() {
            return emCustItemContractPrice;
        }

        public void setEmCustItemContractPrice(BigDecimal emCustItemContractPrice) {
            this.emCustItemContractPrice = emCustItemContractPrice;
        }

        public Integer getEmCustItemQty() {
            return emCustItemQty;
        }

        public void setEmCustItemQty(Integer emCustItemQty) {
            this.emCustItemQty = emCustItemQty;
        }

        public String getEmCustItemPack() {
            return emCustItemPack;
        }

        public void setEmCustItemPack(String emCustItemPack) {
            this.emCustItemPack = emCustItemPack;
        }

        public String getMatchStatus() {
            return matchStatus;
        }

        public void setMatchStatus(String matchStatus) {
            this.matchStatus = matchStatus;
        }

        public void setMatchNotes(String matchNotes) {
            this.matchNotes = matchNotes;
        }

        public String getMatchNotes() {
            return matchNotes;
        }

        public void setMatchStatusWithEmptyTax(String matchStatusWithEmptyTax) {
            this.matchStatusWithEmptyTax = matchStatusWithEmptyTax;
        }

        public String getMatchStatusWithEmptyTax() {
            return matchStatusWithEmptyTax;
        }
    }

    private MatchResult matchFields(Object o1, Object o2, List<String> excFieldNames) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        MatchResult matchResult = new MatchResult();
        matchResult.setResult(true);

        HashSet<String> excFieldNamesSet = new HashSet<String>(excFieldNames);

        Field[] invoiceCustFields1 = o1.getClass().getDeclaredFields();
        Field[] invoiceCustFields2 = o2.getClass().getDeclaredFields();

        HashMap<String, Field> invoiceCustFieldsMap1 = toMap(invoiceCustFields1);
        HashMap<String, Field> invoiceCustFieldsMap2 = toMap(invoiceCustFields2);

        Set<String> invoiceCustFieldNames1 = invoiceCustFieldsMap1.keySet();
        Set<String> invoiceCustFieldNames2 = invoiceCustFieldsMap2.keySet();

        invoiceCustFieldNames1.removeAll(excFieldNamesSet);
        invoiceCustFieldNames2.removeAll(excFieldNamesSet);

        for (String fieldName : invoiceCustFieldNames1) {

            Field field1 = invoiceCustFieldsMap1.get(fieldName);
            Field field2 = invoiceCustFieldsMap2.get(fieldName);
            if (field1.getName().equals(field2.getName())) {

                if (field1.getType() == Integer.TYPE && field2.getType() == Integer.TYPE) {
                    int val1 = (Integer) invokeGetter(o1, fieldName.substring(1));
                    int val2 = (Integer) invokeGetter(o2, fieldName.substring(1));
                    if (val1 != val2) {
                        matchResult.setResult(false);
                        matchResult.addMatchNotes(generateDoesNotMatchNote(fieldName, val1, val2));
                    }
                } else if (field1.getType() == BigDecimal.class && field2.getType() == BigDecimal.class) {
                    BigDecimal val1 = (BigDecimal) invokeGetter(o1, fieldName.substring(1));
                    BigDecimal val2 = (BigDecimal) invokeGetter(o2, fieldName.substring(1));
                    if (!bdEqual(val1, val2, 2)) {
                        matchResult.setResult(false);
                        matchResult.addMatchNotes(generateDoesNotMatchNote(fieldName, val1, val2));
                    }
                } else if (field1.getType() == String.class && field2.getType() == String.class) {
                    String val1 = Utility.trimRight((String) invokeGetter(o1, fieldName.substring(1)), " ");
                    String val2 = Utility.trimRight((String) invokeGetter(o2, fieldName.substring(1)), " ");
                    if (val1 != null || val2 != null) {
                        if (val1 == null || val2 == null || !val1.equals(val2)) {
                            matchResult.setResult(false);
                            matchResult.addMatchNotes(generateDoesNotMatchNote(fieldName, val1, val2));
                        }
                    }
                } else {
                    Object val1 = invokeGetter(o1, fieldName.substring(1));
                    Object val2 = invokeGetter(o2, fieldName.substring(1));
                    if (val1 != null || val2 != null) {
                        if (val1 == null || val2 == null || !val1.equals(val2)) {
                            matchResult.setResult(false);
                            matchResult.addMatchNotes(generateDoesNotMatchNote(fieldName, val1, val2));
                        }
                    }
                }
            }
        }
        return matchResult;
    }

    private Object invokeGetter(Object aClass, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends Object> invClass = aClass.getClass();
        Method method = invClass.getMethod(GET + fieldName);
        return method.invoke(aClass);
    }

    private HashMap<String, Field> toMap(Field[] invoiceCustFields) {
        HashMap<String, Field> map = new HashMap<String, Field>();
        for (Field oInvCustField : invoiceCustFields) {
            map.put(oInvCustField.getName(), oInvCustField);
        }
        return map;
    }

    public MatchResult match(InvoiceCustData invoiceCust1,
                             InvoiceCustDetailData invoiceCustDet1,
                             InvoiceCustData invoiceCust2,
                             InvoiceCustDetailData invoiceCustDet2,
                             boolean detailedMatch) throws Exception {

        MatchResult result = null;

        if (canMatch(invoiceCust1, invoiceCust1) && canMatch(invoiceCustDet1, invoiceCustDet2)) {

            if (detailedMatch) {

                ArrayList<String> excludeFieldNames = new ArrayList<String>();
                excludeFieldNames.add("mAddDate");
                excludeFieldNames.add("mAddBy");
                excludeFieldNames.add("mModDate");
                excludeFieldNames.add("mModBy");
                excludeFieldNames.add("serialVersionUID");

                ArrayList<String> excludeDetFieldNames = new ArrayList<String>();
                excludeDetFieldNames.add("mAddDate");
                excludeDetFieldNames.add("mAddBy");
                excludeDetFieldNames.add("serialVersionUID");
                excludeDetFieldNames.add("mModDate");
                excludeDetFieldNames.add("mModBy");

                result = new MatchResult();

                MatchResult invCustMatchResult = matchFields(invoiceCust1, invoiceCust2, excludeFieldNames);
                MatchResult invCustDetailMatchResult = matchFields(invoiceCustDet1, invoiceCustDet2, excludeDetFieldNames);

                result.setResult(invCustMatchResult.getResult() && invCustDetailMatchResult.getResult());
                result.getMatchNotes().addAll(invCustMatchResult.getMatchNotes());
                result.getMatchNotes().addAll(invCustDetailMatchResult.getMatchNotes());

                return result;

            } else {
                result = new MatchResult();

                MatchResult invCustMatchResult = matchInvoiceCustFields(invoiceCust1, invoiceCust2);
                MatchResult invCustDetailMatchResult = matchInvoiceCustDetailFields(invoiceCustDet1, invoiceCustDet2);

                result.setResult(invCustMatchResult.getResult() && invCustDetailMatchResult.getResult());
                result.getMatchNotes().addAll(invCustMatchResult.getMatchNotes());
                result.getMatchNotes().addAll(invCustDetailMatchResult.getMatchNotes());
            }
        }

        return result;
    }


    private MatchResult matchInvoiceCustDetailFields(InvoiceCustDetailData invoiceCustDet1, InvoiceCustDetailData invoiceCustDet2) {

        MatchResult result = new MatchResult();
        result.setResult(true);
        
        if (invoiceCustDet1.getOrderItemId() != invoiceCustDet2.getOrderItemId()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Order Item Id", invoiceCustDet1.getOrderItemId(), invoiceCustDet2.getOrderItemId()));
        }

        if (!bdEqual(invoiceCustDet1.getLineTotal(), invoiceCustDet2.getLineTotal(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Line Total", invoiceCustDet1.getLineTotal(), invoiceCustDet2.getLineTotal()));
        }

        if (!bdEqual(invoiceCustDet1.getCustContractPrice(), invoiceCustDet2.getCustContractPrice(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Contract Price", invoiceCustDet1.getCustContractPrice(), invoiceCustDet2.getCustContractPrice()));
        }

        if (invoiceCustDet1.getItemQuantity() != invoiceCustDet2.getItemQuantity()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Item Quantity", invoiceCustDet1.getItemQuantity(), invoiceCustDet2.getItemQuantity()));
        }

        if (invoiceCustDet1.getLineNumber() != invoiceCustDet2.getLineNumber()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Line Number", invoiceCustDet1.getLineNumber(), invoiceCustDet2.getLineNumber()));
        }

        return result;

    }

    private MatchResult matchInvoiceCustFields(InvoiceCustData selfServiceInvoice, InvoiceCustData lawsonInvoice) {

        MatchResult result = new MatchResult();
        result.setResult(true);

        if (!bdEqual(selfServiceInvoice.getSubTotal(), lawsonInvoice.getSubTotal(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("SubTotal", selfServiceInvoice.getSubTotal(), lawsonInvoice.getSubTotal()));
        }

        if (!bdEqual(selfServiceInvoice.getSalesTax(), lawsonInvoice.getSalesTax(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Sales Tax", selfServiceInvoice.getSalesTax(), lawsonInvoice.getSalesTax()));
        }

        if (!bdEqual(selfServiceInvoice.getNetDue(), lawsonInvoice.getNetDue(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Net Due", selfServiceInvoice.getNetDue(), lawsonInvoice.getNetDue()));
        }

        if (!bdEqual(selfServiceInvoice.getFuelSurcharge(), lawsonInvoice.getFuelSurcharge(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Fuel Surcharge", selfServiceInvoice.getFuelSurcharge(), lawsonInvoice.getFuelSurcharge()));
        }

        if (!bdEqual(selfServiceInvoice.getDiscounts(), lawsonInvoice.getDiscounts(), 2)) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Discounts", selfServiceInvoice.getDiscounts(), lawsonInvoice.getDiscounts()));
        }

        if (selfServiceInvoice.getStoreId() != lawsonInvoice.getStoreId()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Store Id", selfServiceInvoice.getStoreId(), lawsonInvoice.getStoreId()));
        }

        if (selfServiceInvoice.getAccountId() != lawsonInvoice.getAccountId()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Account Id", selfServiceInvoice.getAccountId(), lawsonInvoice.getAccountId()));
        }

        if (selfServiceInvoice.getSiteId() != lawsonInvoice.getSiteId()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Site Id", selfServiceInvoice.getSiteId(), lawsonInvoice.getSiteId()));
        }

        if (selfServiceInvoice.getOrderId() != lawsonInvoice.getOrderId()) {
            result.setResult(false);
            result.addMatchNotes(generateDoesNotMatchNote("Order Id", selfServiceInvoice.getOrderId(), lawsonInvoice.getOrderId()));
        }

        return result;
    }

    private boolean bdEqual(BigDecimal bd1, BigDecimal bd2, int scale) {
        if (bd1 == null) {
            return bd2 == null;
        } else {
            return bd2 != null && bd1.setScale(scale, BigDecimal.ROUND_HALF_UP).equals(bd2.setScale(scale, BigDecimal.ROUND_HALF_UP));
        }
    }

    private String generateDoesNotMatchNote(String field, Object val1, Object val2) {
        return MessageFormat.format("{0}:{1} != {2}", field, val1, val2);
    }

    private boolean canMatch(InvoiceCustData i1, InvoiceCustData i2) throws Exception {
        return i1 != null && i2 != null;
    }

    private boolean canMatch(InvoiceCustDetailData id1, InvoiceCustDetailData id2) throws Exception {
        return id1 != null && id2 != null;
    }

    private class MatchResult {

        boolean result;
        List<String> matchNotes;

        public MatchResult() {
            matchNotes = new ArrayList<String>();
            result = false;
        }

        public boolean getResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public List<String> getMatchNotes() {
            return matchNotes;
        }

        public void setMatchNotes(List<String> matchNotes) {
            this.matchNotes = matchNotes;
        }

        public void addMatchNotes(String note) {
            this.matchNotes.add(note);
        }
    }


}
