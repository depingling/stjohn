/*
 * SaleTypeProjectCodeOrderSummaryReportSuper.java
 *
 * Created on November 5, 2003, 9:57 AM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.sql.*;
import com.cleanwise.service.api.util.ConnectionContainer;
import java.math.BigDecimal;

/**
 *Sub classes should provide some method of supplying the sale type through the getSaleType method
 * @author  bstevens
 */
abstract public class SaleTypeProjectCodeOrderSummaryReportSuper implements GenericReportMulti{
    
    abstract String getSaleType(Map pParams);
    
    public GenericReportResultViewVector process(ConnectionContainer pCons,GenericReportData pReportData,Map pParams)
    throws Exception{
        try{
        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        //Lists of report results
        ArrayList summary = new ArrayList();
        ArrayList details = new ArrayList();
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
        
        String detailSql =
        "SELECT  "+
        "p.clw_value AS project_code, "+
        "oi.item_sku_num, MAX(oi.item_short_desc) as short_desc, oi.item_uom,oi.cust_contract_price, SUM(oi.total_quantity_ordered) qty, SUM(oi.cust_contract_price *oi.total_quantity_ordered) as total "+
        "FROM clw_order o, clw_order_item oi, "+
        "(SELECT * FROM clw_property WHERE short_desc = 'SITE_REFERENCE_NUMBER' AND property_type_cd='EXTRA' AND property_status_cd='ACTIVE') p " +
        "WHERE o.original_order_date between to_date('"+begDateS+"','mm/dd/yyyy') AND to_date('"+endDateS+"','mm/dd/yyyy') AND "+
        ReportingUtils.getValidOrdersSQL("o") + " AND "+
        "o.account_id = "+accountIdS+" "+
        ((userDesc.isSeesAllSites())?"": "and site_id in ("+userDesc.getAuthorizedSitesSql()+") ")+
        "AND oi.order_id = o.order_id AND oi.sale_type_cd = '"+getSaleType(pParams)+"' "+
        "AND o.site_id = p.bus_entity_id (+) "+
        "GROUP BY "+
        "clw_value,item_sku_num,oi.item_uom,oi.cust_contract_price";
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(detailSql);
        while(rs.next()){
            Detail record = new Detail();
            record.projectCode = rs.getString("project_code");
            if(record.projectCode == null){
                record.projectCode = "";
            }
            record.sku = rs.getString("item_sku_num");
            record.itmShortDesc = rs.getString("short_desc");
            record.uom = rs.getString("item_uom");
            record.price = rs.getBigDecimal("cust_contract_price");
            record.qty = rs.getInt("qty");
            record.total = rs.getBigDecimal("total");
            details.add(record);
        }
        rs.close();
        stmt.close();
        
        
        //roll up the project codes into the summary report
        Iterator it = details.iterator();
        HashMap projMap = new HashMap();
        while(it.hasNext()){
            Detail detail = (Detail) it.next();
            if(projMap.containsKey(detail.projectCode)){
                ArrayList l=(ArrayList) projMap.get(detail.projectCode);
                l.add(detail);
            }else{
                ArrayList l=new ArrayList();
                l.add(detail);
                projMap.put(detail.projectCode,l);
            }
        }
        
        Set codes = projMap.keySet();
        ArrayList codesList = new ArrayList(codes);
        Collections.sort(codesList);
        it=codesList.iterator();
        while(it.hasNext()){
            String projectCode = (String) it.next();
            ArrayList l = (ArrayList) projMap.get(projectCode);
            Summary sum = new Summary();
            sum.projectCode = projectCode;
            sum.total = new BigDecimal(0);
            Iterator subIt = l.iterator();
            while(subIt.hasNext()){
                Detail det = (Detail) subIt.next();
                sum.total = sum.total.add(det.total);
            }
            summary.add(sum);
        }
        
        
        //add the detail to the report
        {
            it = details.iterator();
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while(it.hasNext()) {
                Detail detail = (Detail) it.next();
                result.getTable().add(detail.toList());
            }
            GenericReportColumnViewVector header = getDetailHeader();
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setName("Detail");
            resultV.add(result);
        }
        
        //add the summary to the report
        {
            it = summary.iterator();
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while(it.hasNext()) {
                Summary summaryRec = (Summary) it.next();
                result.getTable().add(summaryRec.toList());
            }
            GenericReportColumnViewVector header = getSummaryHeader();
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setName("Summary");
            resultV.add(result);
        }
        
        return resultV;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    private GenericReportColumnViewVector getDetailHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Project Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Description",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Amount$",2,20,"NUMBER","*",true));
        return header;
    }
    
    private GenericReportColumnViewVector getSummaryHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Project Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Amount$",2,20,"NUMBER","*",true));
        return header;
    }
    
    private class Detail{
        String projectCode;
        String sku;
        String itmShortDesc;
        String uom;
        BigDecimal price;
        int qty;
        BigDecimal total;
        
        public ArrayList toList(){
            ArrayList list = new ArrayList();
            list.add(projectCode);
            list.add(sku);
            list.add(itmShortDesc);
            list.add(uom);
            list.add(price);
            list.add(new Integer(qty));
            list.add(total);
            return list;
        }
    }
    
    private class Summary{
        String projectCode;
        BigDecimal total;
        
        public ArrayList toList(){
            ArrayList list = new ArrayList();
            list.add(projectCode);
            list.add(total);
            return list;
        }
    }
}
