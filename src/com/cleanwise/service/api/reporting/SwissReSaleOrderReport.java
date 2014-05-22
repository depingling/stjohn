/**
 * ISSReSaleOrderReport.java
 */
package com.cleanwise.service.api.reporting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;

/**
 * @author ssharma
 *
 */
public class SwissReSaleOrderReport extends ReSaleOrderReport{
	
	public SwissReSaleOrderReport(){
		displayPONum = true;
		mDisplayDistInfo = true;
		showPrice = true;
	}
	/**
	 * Custom report for JD Swiss. Extra columns - Primary contact name, dist cost, cust price
	 */
	protected GenericReportColumnViewVector getOrderTotalsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(isUseMultiAcctsFlag()){
        	if (multiAccounts) {
        		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        	}
        }else{
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "10", false));
        }       
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order_Sub_Total$", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "rowInfo_Currency Code", 0, 255, "VARCHAR2", "8", false));
        
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
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "7", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "7", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "8", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sku", 0, 255, "VARCHAR2", "6", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item_Size", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Name", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Qty", 0, 20, "NUMBER", "4", false));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Line_Total$", 2, 20, "NUMBER", "8", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "rowInfo_Currency Code", 0, 255, "VARCHAR2", "6", false));
        
        return header;
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
				result.getTable().add(toList((aRecord)it.next()));
			}
			result.setColumnCount(header.size());
			result.setHeader(header);
			result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
			result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
			result.setName(name);
			resultV.add(result);
		}
	}
	protected ArrayList toList(aRecord record) {
            ArrayList list = new ArrayList();
            if (record instanceof OrderTotal){
            	OrderTotal orderTotal = (OrderTotal) record;
            	if(isUseMultiAcctsFlag()){
                	if (multiAccounts) {
                		list.add(orderTotal.account);
                	}
                }else{
                	list.add(orderTotal.account);
                }
                list.add(orderTotal.siteName);
                list.add(orderTotal.confirmNum);
                list.add(orderTotal.orderDate);
                list.add(orderTotal.poNum);
                list.add(orderTotal.orderTotal);
                list.add("rowInfo_currencyCd=" + orderTotal.orderCurrencyCd);
            }else if (record instanceof Detail){
            	Detail detail = (Detail) record;
                if(isUseMultiAcctsFlag()){
                	if (multiAccounts) {
                		list.add(detail.account);
                	}
                }else{
                	list.add(detail.account);
                }
                list.add(detail.siteName);
                list.add(detail.confirmNum);
                list.add(detail.poNum);                
                list.add(detail.orderDate);
                list.add(detail.getCategory());
                if (detail.distSku == null) {
                	list.add("");                
                }else{
                	list.add(detail.distSku);
                }
                list.add(detail.itemSize);
                list.add(detail.name);
                list.add(new Integer(detail.qty));
                list.add(detail.lineTotal);
                list.add("rowInfo_currencyCd=" + detail.orderCurrencyCd);
            } else {
            	return record.toList();
            }
            
            return list;

    }
}
