/*
 * ReSaleInvoiceByProjectCodeReport.java
 *
 * Created on December 18, 2003, 11:08 AM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import com.cleanwise.service.api.util.ConnectionContainer;
import java.sql.*;
import java.math.BigDecimal;

/**
 *Report produces and Invoice by Site and Project Code.  Both a summary and a detail are produced.
 * @author  bstevens
 */
public class ReSaleInvoiceByProjectCodeReport  implements GenericReportMulti {
    
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        
        //ArrayList summary = new ArrayList();
        ArrayList detail = new ArrayList();
        HashMap summaryMap = new HashMap();
        
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        if(!ReportingUtils.isValidDate(begDateS)){
            String mess = "^clw^\""+begDateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }
        if(!ReportingUtils.isValidDate(endDateS)){
            String mess = "^clw^\""+endDateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }
        
        String accountIdS = (String) pParams.get("ACCOUNT");
        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserSitesDesciption userDesc = repUtil.getUserSitesDesciption(pParams,con);
        
        String sql =
        "SELECT site.bus_entity_id as site_id, site.short_desc AS site_name, pcode.clw_value as budgetRef, NVL(oi.cust_item_sku_num,oi.item_sku_num) AS sku, "+
        "NVL(oi.cust_item_short_desc,icd.item_short_desc) AS item_desc, "+
        "NVL(oi.cust_item_uom,icd.item_uom) AS uom, icd.cust_contract_price, icd.item_quantity,  icd.line_total  "+
        "FROM  "+
        "clw_invoice_cust ic,clw_invoice_cust_detail icd,  clw_order_item oi, clw_order o, "+
        "(select * from clw_property where property_type_cd = 'EXTRA' and property_status_cd = 'ACTIVE' AND short_desc = 'SITE_REFERENCE_NUMBER' ) pcode, "+
        "clw_bus_entity site "+
        "WHERE  "+
        "ic.invoice_cust_id = icd.invoice_cust_id  "+
        "AND o.order_id = oi.order_id AND icd.order_item_id = oi.order_item_id "+
        "AND oi.sale_type_cd = 'RE_SALE' AND ic.account_id = "+accountIdS+" "+
        "AND pcode.bus_entity_id (+)= o.site_id "+
        "AND site.bus_entity_id = o.site_id "+
        ((userDesc.isSeesAllSites())?"": "and o.site_id in ("+userDesc.getAuthorizedSitesSql()+") ")+
        "AND ic.invoice_date between to_date('"+begDateS+"','mm/dd/yyyy') AND to_date('"+endDateS+"','mm/dd/yyyy') "+
        "ORDER BY site.short_desc";
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            Detail record = new Detail();
            record.siteName = rs.getString("site_name");
            record.budgetRef = rs.getString("budgetRef");
            record.sku = rs.getString("sku");
            record.itemDesc = rs.getString("item_desc");
            record.uom = rs.getString("uom");
            record.quantity = rs.getInt("item_quantity");
            record.lineTotal = rs.getBigDecimal("line_total");
            record.price = rs.getBigDecimal("cust_contract_price");
            record.siteId = rs.getInt("site_id");
            detail.add(record);
            
            //create summary records
            Integer key = new Integer(record.siteId);
            if(summaryMap.containsKey(key)){
                Summary sum = (Summary) summaryMap.get(key);
                sum.siteName=record.siteName;
                sum.budgetRef=record.budgetRef;
                sum.total = sum.total.add(record.lineTotal);
            }else{
                Summary sum = new Summary();
                sum.total = record.lineTotal;
                sum.siteName=record.siteName;
                sum.budgetRef=record.budgetRef;
                summaryMap.put(key, sum);
            }
        }
        rs.close();
        stmt.close();
        
        processList(detail, resultV, "Details",getDetailReportHeader());
        
        {//render detail
            Iterator it = detail.iterator();
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            BigDecimal runningTotal = new BigDecimal(0);
            runningTotal.setScale(2);
            Detail lastDet = new Detail();
            lastDet.siteId = 0;
            while(it.hasNext()) {
                Detail det = (Detail) it.next();
                if(lastDet.siteId > 0 && !det.siteName.equals(lastDet.siteName)){
                    ArrayList tmp = lastDet.toEmptyList();
                    tmp.add(runningTotal);
                    result.getTable().add(tmp);
                    runningTotal = det.lineTotal;
                }else{
                    runningTotal = runningTotal.add(det.lineTotal);
                }
                result.getTable().add(det.toList());
                lastDet = det;
            }
            if(lastDet != null){
                ArrayList tmp = lastDet.toEmptyList();
                tmp.add(runningTotal);
                result.getTable().add(tmp);
            }
            result.setColumnCount(getDetailReportHeader().size());
            result.setHeader(getDetailReportHeader());
            result.setName("Details");
            resultV.add(result);
        }
        
        //the summary list is unsorted due to its entry in the hashmap
        ArrayList summaryList = new ArrayList();
        Collection summaryCol = summaryMap.values();
        summaryList.addAll(summaryCol);
        Collections.sort(summaryList,SUMMARY_COMPARE);
        
        {//render summary
            Iterator it = summaryList.iterator();
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while(it.hasNext()) {
                result.getTable().add(((Summary) it.next()).toList());
            }
            result.setColumnCount(getSummaryReportHeader().size());
            result.setHeader(getSummaryReportHeader());
            result.setName("Summary");
            resultV.add(result);
        }
        
        return resultV;
    }
    
    
    private void processList(Collection toProcess, GenericReportResultViewVector resultV, String name, GenericReportColumnViewVector header){
        
    }
    
    

    
    private GenericReportColumnViewVector getSummaryReportHeader(){
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Project Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Amount$",2,20,"NUMBER","*",true));
        return header;
    }
    
    private GenericReportColumnViewVector getDetailReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Project Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Description",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Price$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Amount$",2,20,"NUMBER","*",true));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Sub Total$",2,20,"NUMBER","*",true));
        return header;
    }
    
    
    private class Detail{
        int siteId;
        String siteName;
        String budgetRef;
        String sku;
        String itemDesc;
        String uom;
        BigDecimal price;
        int quantity;
        BigDecimal lineTotal;
        public ArrayList toEmptyList() {
            ArrayList list = new ArrayList();
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            return list;
        }
        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(siteName);
            list.add(budgetRef);
            list.add(sku);
            list.add(itemDesc);
            list.add(uom);
            list.add(price);
            list.add(new Integer(quantity));
            list.add(lineTotal);
            return list;
        }
        
    }
    
    private class Summary{
        String siteName;
        String budgetRef;
        BigDecimal total;
        
        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(siteName);
            list.add(budgetRef);
            list.add(total);
            return list;
        }
    }
    
    static final Comparator SUMMARY_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		Summary s1 = (Summary)o1;
		Summary s2 = (Summary)o2;
                return s1.siteName.compareToIgnoreCase(s2.siteName);
            }
    };

}
