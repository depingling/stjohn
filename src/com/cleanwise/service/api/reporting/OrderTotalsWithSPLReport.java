package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.value.GenericReportColumnViewVector;

/**
 * User: Veronika
 * Date: Oct 14, 2010
 * Time: 5:09:47 PM
 */

public class OrderTotalsWithSPLReport extends ReSaleOrderReport {

    protected boolean isShowSPL() {
        return true;
    }


    protected GenericReportColumnViewVector getOrderTotalsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(isUseMultiAcctsFlag()){
        	if (multiAccounts) {
        		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2", "10", false));
        	}
        }else{
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2", "10", false));
        }
        if(isShowPrimaryContact()){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact First Name",0,255,"VARCHAR2","10", false));
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Primary Contact Last Name",0,255,"VARCHAR2","10", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "10", false));
        if (includeState) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "5", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Budget_Ref", 0, 255, "VARCHAR2", "15", false));
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


}
