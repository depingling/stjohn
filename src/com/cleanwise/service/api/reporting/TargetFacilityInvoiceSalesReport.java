package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import java.sql.*;

/**
 *
 * @author  bstevens
 */
public class TargetFacilityInvoiceSalesReport implements GenericReportMulti {
    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final String JAN = "01";
    private static final String FEB = "02";
    private static final String MAR = "03";
    private static final String APR = "04";
    private static final String MAY = "05";
    private static final String JUN = "06";
    private static final String JUL = "07";
    private static final String AUG = "08";
    private static final String SEP = "09";
    private static final String OCT = "10";
    private static final String NOV = "11";
    private static final String DEC = "12";
    
    private static final String MAIN_SQL1 =
    "SELECT "+
    "site.bus_entity_id AS site_id,"+
    "site.short_desc AS site_name, "+
    "addr.address1,"+
    "addr.address2,"+
    "addr.address3,"+
    "addr.address4,"+
    "addr.city,"+
    "addr.state_province_cd,"+
    "addr.postal_code,"+
    "cat.short_desc AS catalog_name "+
    "FROM "+
    "CLW_BUS_ENTITY_ASSOC sa, CLW_BUS_ENTITY site, CLW_PROPERTY tar, CLW_ADDRESS addr, CLW_CATALOG cat, CLW_CATALOG_ASSOC ca "+
    "WHERE "+
    "site.bus_entity_id = tar.bus_entity_id AND addr.bus_entity_id = site.bus_entity_id "+
    "AND tar.property_type_cd = 'TARGET_FACILITY_RANK' AND tar.clw_value in (";
    private static final String MAIN_SQL2 = 
    ")  "+
    "AND ca.catalog_id = cat.catalog_id AND ca.bus_entity_id = site.bus_entity_id AND cat.catalog_status_cd = 'ACTIVE' "+
    "AND sa.bus_entity_assoc_cd = 'SITE OF ACCOUNT' and sa.bus_entity2_id IN (";
    private static final String MAIN_SQL3 = 
    ") AND sa.bus_entity1_id = site.bus_entity_id "+
    "GROUP BY  "+
    "site.bus_entity_id, "+
    "site.short_desc,  "+
    "addr.address1, "+
    "addr.address2, "+
    "addr.address3, "+
    "addr.address4, "+
    "addr.city, "+
    "addr.state_province_cd, "+
    "addr.postal_code, "+
    "cat.short_desc";
    
    private static final String INVOICE_SQL =
    "SELECT "+
    "sum(icd.item_quantity * icd.cust_contract_price) AS sub_total, "+
    "TO_CHAR(TRUNC(ic.invoice_date,'MM'),'MM') AS month, "+
    "to_char(TRUNC(ic.invoice_date,'MM'),'YYYY') AS year, "+
    "oi.dist_erp_num "+
    "FROM CLW_INVOICE_CUST_DETAIL icd, CLW_INVOICE_CUST ic, CLW_ORDER_ITEM oi "+
    "WHERE icd.invoice_cust_id = ic.invoice_cust_id AND icd.order_item_id = oi.order_item_id AND ic.site_id = ? "+
    "AND (ic.invoice_date > trunc(sysdate,'YYYY') or ic.invoice_date between trunc(add_months(sysdate,-12),'YYYY') and add_months(trunc(sysdate),-12)) "+
    "GROUP BY  "+
    "TRUNC(ic.invoice_date,'MM'), "+
    "oi.dist_erp_num "+
    "Order by TRUNC(ic.invoice_date,'MM')";
    
    
    private static final String ORDER_SQL =
    "SELECT "+
    "to_char(TRUNC(NVL(o.revised_order_date, o.original_order_date),'YYYY'),'YYYY') AS year, "+
    "SUM(oi.cust_contract_price * oi.total_quantity_ordered) AS sub_total, "+
    "oi.dist_erp_num "+
    "FROM  "+
    "clw_Order o, clw_order_item oi "+
    "WHERE  "+
    "oi.order_id = o.order_id AND oi.order_item_status_cd != 'CANCELLED' AND "+
    "o.order_status_cd IN ('Ordered','Invoiced','Process ERP PO','ERP Released','ERP Rejected','ERP Released PO Error','Sending TO Erp')AND "+
    "o.site_id = ? AND  "+
    "(NVL(o.revised_order_date, o.original_order_date) > TRUNC(SYSDATE,'YYYY') OR NVL(o.revised_order_date, o.original_order_date) BETWEEN TRUNC(ADD_MONTHS(TRUNC(SYSDATE),-12),'YYYY') AND ADD_MONTHS(SYSDATE,-12))  "+
    "GROUP BY  "+
    "TRUNC(NVL(o.revised_order_date, o.original_order_date),'YYYY'), oi.dist_erp_num "+
    "ORDER BY year";
    

    private static final String DIST_SQL =
    "select short_desc from clw_bus_entity where bus_entity_type_cd = 'DISTRIBUTOR' and erp_num = ?";
    
    
    private static final String MARGIN_SQL =
    "";
    
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        try{
            Connection con = pCons.getDefaultConnection();
            GenericReportResultViewVector resultV = new GenericReportResultViewVector();
            //Lists of report results
            HashMap byTarget = new HashMap();
            HashMap byTargetAndDistMap = new HashMap();
            
            String targetS = (String) pParams.get("TARGET_FACILITY_RANK_MULTI");
            String accountS = (String) pParams.get("ACCOUNT_MULTI");
            if(!Utility.isSet(accountS)){
                accountS = (String) pParams.get("ACCOUNT_MULTI_OPT");
            }
            
            ArrayList targetList = new ArrayList();
            HashMap distMap = new HashMap();
            
            Calendar now = Calendar.getInstance();
            now.setTime(new java.util.Date());
            int thisYear = now.get(Calendar.YEAR);
            
            //first get a list of all the target facilities
            StringTokenizer tok = new StringTokenizer(targetS, ",");
            StringBuffer targetBuf = new StringBuffer();
            while(tok.hasMoreTokens()){
                targetBuf.append('\'');
                targetBuf.append(tok.nextToken());
                targetBuf.append('\'');
                if(tok.hasMoreTokens()){
                    targetBuf.append(',');
                }
            }
            String sql = MAIN_SQL1 + targetBuf.toString() + MAIN_SQL2 + accountS + MAIN_SQL3;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Record r = new Record(rs);
                targetList.add(r);
            }
            rs.close();
            stmt.close();
            
            //now get the other information that we need and do the grouping
            PreparedStatement distStmt = con.prepareStatement(DIST_SQL);
            PreparedStatement pstmt = con.prepareStatement(INVOICE_SQL);
            PreparedStatement ordStmt = con.prepareStatement(ORDER_SQL);
            Iterator it = targetList.iterator();
            while(it.hasNext()){
                Record r = (Record) it.next();
                
                HashMap distOrderTotals = new HashMap();
                HashMap distLastYearOrderTotals = new HashMap();
                
                ordStmt.setInt(1,r.siteId);
                rs = ordStmt.executeQuery();
                r.orderSubTotal = ZERO;
                r.lastYearOrderSubTotal = ZERO;
                while(rs.next()){
                    int recYear = rs.getInt("YEAR");
                    String dist2 = rs.getString("DIST_ERP_NUM");
                    BigDecimal amount = rs.getBigDecimal("SUB_TOTAL");
                    if(recYear == thisYear){
                        r.orderSubTotal = r.orderSubTotal.add(amount);
                        distOrderTotals.put(dist2, amount);
                    }else{
                        r.lastYearOrderSubTotal = r.lastYearOrderSubTotal.add(amount);
                        distLastYearOrderTotals.put(dist2, amount);
                    }
                }
                rs.close();
                
                pstmt.setInt(1,r.siteId);
                rs = pstmt.executeQuery();
                boolean foundSale = false;
                while(rs.next()){
                    foundSale = true;
                    
                    String erpNum = rs.getString("DIST_ERP_NUM");
                    String dist;
                    if(distMap.containsKey(erpNum)){
                        dist = (String) distMap.get(erpNum);
                    }else{
                        distStmt.setString(1,erpNum);
                        ResultSet distRs = distStmt.executeQuery();
                        if(distRs.next()){
                            dist = distRs.getString("SHORT_DESC");
                        }else{
                            dist = erpNum;
                        }
                        distRs.close();
                        distMap.put(erpNum,dist);
                    }
                    
                    
                   
                    
                    int recYear = rs.getInt("YEAR");
                    String month = rs.getString("MONTH");
                    BigDecimal subTotal = rs.getBigDecimal("SUB_TOTAL");
                    String key = r.siteId + month;
                    String distKey = r.siteId + "::" + dist + "::" + month;
                    if (thisYear == recYear){
                        Record r2;
                        if(byTargetAndDistMap.containsKey(distKey)){
                            r2 = (Record) byTargetAndDistMap.get(distKey);
                        }else{
                            r2 = r.copyBase();
                        }
                        BigDecimal ordTot = (BigDecimal) distOrderTotals.get(erpNum);
                        r2.orderSubTotal = ordTot;
                        r2.dist = dist;
                        r2.subTotal = subTotal;
                        r2.month = month;
                        byTargetAndDistMap.put(distKey,r2);
                        if(byTarget.containsKey(key)){
                            Record r3 = (Record) byTarget.get(key);
                            r3.subTotal = r3.subTotal.add(r2.subTotal);
                            r3.orderSubTotal = r.orderSubTotal;
                            r3.lastYearOrderSubTotal = r.lastYearOrderSubTotal;
                            r3.month = r2.month;
                        }else{
                            Record r3 = r.copyBase();
                            r3.subTotal = r2.subTotal;
                            r3.month = r2.month;
                            r3.orderSubTotal = r.orderSubTotal;
                            r3.lastYearOrderSubTotal = r.lastYearOrderSubTotal;
                            byTarget.put(key,r3);
                        }
                    }else{
                        if(byTarget.containsKey(key)){
                            Record r3 = (Record) byTarget.get(key);
                            if(r3.lastYearSubTotal == null){
                                r3.lastYearSubTotal = subTotal;
                            }else{
                                r3.lastYearSubTotal = r3.lastYearSubTotal.add(subTotal);
                            }
                        }else{
                            Record r3 = r.copyBase();
                            r3.subTotal = ZERO;
                            r3.month = month;
                            r3.lastYearSubTotal = subTotal;
                            r3.orderSubTotal = r.lastYearOrderSubTotal;
                            byTarget.put(key,r3);
                        }
                        
                        if(byTargetAndDistMap.containsKey(distKey)){
                            Record r2 = (Record) byTargetAndDistMap.get(distKey);
                            if(r2.lastYearSubTotal == null){
                                r2.lastYearSubTotal = subTotal;
                            }else{
                                r2.lastYearSubTotal = r2.lastYearSubTotal.add(subTotal);
                            }
                            BigDecimal ordTot = (BigDecimal) distLastYearOrderTotals.get(erpNum);
                            r2.lastYearOrderSubTotal = ordTot;
                        }else{
                            Record r2 = r.copyBase();
                            r2.dist = dist;
                            r2.subTotal = ZERO;
                            r2.lastYearSubTotal = subTotal;
                            r2.month = month;
                            BigDecimal ordTot = (BigDecimal) distLastYearOrderTotals.get(erpNum);
                            r2.lastYearOrderSubTotal = ordTot;
                            byTargetAndDistMap.put(distKey,r2);
                        }
                    }
                }
                rs.close();
                if(!foundSale){
                    String key = Integer.toString(r.siteId);
                    r.month = "-";
                    byTarget.put(key,r);
                }
            }
            pstmt.close();
            ordStmt.close();
            distStmt.close();
            
            Collection byTargetAndDist = byTargetAndDistMap.values();
            
            //now we have all the data do the grouping with the months such that we have only one record
            //and the months broken out
            ArrayList byTargetLst = new ArrayList();
            byTargetLst.addAll(byTarget.values());
            ArrayList byTargetAndDistLst = new ArrayList();
            byTargetAndDistLst.addAll(byTargetAndDist);
            groupRecordsIntoMonthView(byTargetLst);
            groupRecordsIntoMonthView(byTargetAndDistLst);

            //sort the results
            Collections.sort(byTargetLst,RECORD_SORT);
            Collections.sort(byTargetAndDistLst,RECORD_SORT);
            
            //generate the results
            {
                it = byTargetAndDistLst.iterator();
                GenericReportResultView result = GenericReportResultView.createValue();
                result.setTable(new ArrayList());
                while(it.hasNext()) {
                    Record r = (Record) it.next();
                    result.getTable().add(r.toList(true));
                }
                GenericReportColumnViewVector header = getByTargetAndDistHeader();
                result.setColumnCount(header.size());
                result.setHeader(header);
                result.setName("By Site Month And Distributor");
                resultV.add(result);
            }
            
            {
                it = byTargetLst.iterator();
                GenericReportResultView result = GenericReportResultView.createValue();
                result.setTable(new ArrayList());
                while(it.hasNext()) {
                    Record r = (Record) it.next();
                    result.getTable().add(r.toList(false));
                }
                GenericReportColumnViewVector header = getByTargetHeader();
                result.setColumnCount(header.size());
                result.setHeader(header);
                result.setName("By Site And Month");
                resultV.add(result);
            }
            
            return resultV;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    
    private void groupRecordsIntoMonthView(Collection list){
        Iterator it = list.iterator();
        HashMap grp = new HashMap();
        while (it.hasNext()){
            Record r = (Record) it.next();
            String key = r.siteId + "::" + r.dist;
            Record exR;
            
            BigDecimal subTotal = r.subTotal;
            BigDecimal lastYearSubTotal = r.lastYearSubTotal;
            
            if(grp.containsKey(key)){
                exR = (Record) grp.get(key);
            }else{
                exR = r;
                exR.subTotal = ZERO;
                exR.lastYearSubTotal = ZERO;
                //XXX zero out order subtotals???
                grp.put(key,exR);
            }
            if(JAN.equals(r.month)){
                exR.janSubTotal = subTotal;
                exR.lYearJanSubTotal = lastYearSubTotal;
            }else if(FEB.equals(r.month)){
                exR.febSubTotal = subTotal;
                exR.lYearFebSubTotal = lastYearSubTotal;
            }else if(MAR.equals(r.month)){
                exR.marSubTotal = subTotal;
                exR.lYearMarSubTotal = lastYearSubTotal;
            }else if(APR.equals(r.month)){
                exR.aprSubTotal = subTotal;
                exR.lYearAprSubTotal = lastYearSubTotal;
            }else if(MAY.equals(r.month)){
                exR.maySubTotal = subTotal;
                exR.lYearMaySubTotal = lastYearSubTotal;
            }else if(JUN.equals(r.month)){
                exR.junSubTotal = subTotal;
                exR.lYearJunSubTotal = lastYearSubTotal;
            }else if(JUL.equals(r.month)){
                exR.julSubTotal = subTotal;
                exR.lYearJulSubTotal = lastYearSubTotal;
            }else if(AUG.equals(r.month)){
                exR.augSubTotal = subTotal;
                exR.lYearAugSubTotal = lastYearSubTotal;
            }else if(SEP.equals(r.month)){
                exR.sepSubTotal = subTotal;
                exR.lYearSepSubTotal = lastYearSubTotal;
            }else if(OCT.equals(r.month)){
                exR.octSubTotal = subTotal;
                exR.lYearOctSubTotal = lastYearSubTotal;
            }else if(NOV.equals(r.month)){
                exR.novSubTotal = subTotal;
                exR.lYearNovSubTotal = lastYearSubTotal;
            }else if(DEC.equals(r.month)){
                exR.decSubTotal = subTotal;
                exR.lYearDecSubTotal = lastYearSubTotal;
            }
            if(exR.lastYearSubTotal == null){
                exR.lastYearSubTotal = lastYearSubTotal;
            }else{
                if(lastYearSubTotal != null){
                    exR.lastYearSubTotal = exR.lastYearSubTotal.add(lastYearSubTotal);
                }
            }
            
            if(exR.subTotal == null){
                exR.subTotal = subTotal;
            }else{
                if(subTotal!= null){
                    exR.subTotal = exR.subTotal.add(subTotal);
                }
            }
        }
        list.clear();
        list.addAll(grp.values());
    }
    
    private GenericReportColumnViewVector getByTargetHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address1",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address2",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address3",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address4",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Postal Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Catalog Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","January",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Febuary",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","March",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","April",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","May",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","June",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","July",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","August",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","September",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","October",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","November",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","December",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD Invoice",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Last YTD Invoice",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD Orders",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Last YTD Orders",2,20,"NUMBER"));
        return header;
    }
    
    private GenericReportColumnViewVector getByTargetAndDistHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address1",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address2",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address3",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address4",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Postal Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Catalog Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","January",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Febuary",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","March",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","April",2,20,"NUMBER"));       
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","May",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","June",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","July",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","August",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","September",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","October",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","November",2,20,"NUMBER"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","December",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD Invoice",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Last YTD Invoice",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD Orders",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Last YTD Orders",2,20,"NUMBER"));
        
        return header;
    }
    
    static final Comparator RECORD_SORT = new Comparator() {
        public int compare(Object o1, Object o2) {
            Record r1 = (Record)o1;
            Record r2 = (Record)o2;
            fillNulls(r1);
            fillNulls(r2);
            int val = r1.siteName.compareTo(r2.siteName);
            if(val == 0){
                val = r1.dist.compareTo(r2.dist);
            }
            return val;
        }
        
        private void fillNulls(Record r){
            if(r.month == null){
                r.month = "";
            }
            if(r.siteName == null){
                r.siteName = "";
            }
            if(r.dist == null){
                r.dist = "";
            }
        }
    };
    
    private class Record{
        int siteId;
        String siteName;
        String addr1;
        String addr2;
        String addr3;
        String addr4;
        String city;
        String state;
        String zip;
        String catName;
        BigDecimal subTotal;
        BigDecimal lastYearSubTotal;
        
        BigDecimal lastYearOrderSubTotal;
        BigDecimal orderSubTotal;
        
        //BigDecimal frt;
        //BigDecimal tax;
        String month;
        BigDecimal janSubTotal;
        BigDecimal febSubTotal;
        BigDecimal marSubTotal;
        BigDecimal aprSubTotal;
        BigDecimal maySubTotal;
        BigDecimal junSubTotal;
        BigDecimal julSubTotal;
        BigDecimal augSubTotal;
        BigDecimal sepSubTotal;
        BigDecimal octSubTotal;
        BigDecimal novSubTotal;
        BigDecimal decSubTotal;
        BigDecimal lYearJanSubTotal;
        BigDecimal lYearFebSubTotal;
        BigDecimal lYearMarSubTotal;
        BigDecimal lYearAprSubTotal;
        BigDecimal lYearMaySubTotal;
        BigDecimal lYearJunSubTotal;
        BigDecimal lYearJulSubTotal;
        BigDecimal lYearAugSubTotal;
        BigDecimal lYearSepSubTotal;
        BigDecimal lYearOctSubTotal;
        BigDecimal lYearNovSubTotal;
        BigDecimal lYearDecSubTotal;
        
        
        String dist;
        private ArrayList toList(boolean withDist){
            ArrayList l = new ArrayList();
            l.add(siteName);
            l.add(addr1);
            l.add(addr2);
            l.add(addr3);
            l.add(addr4);
            l.add(city);
            l.add(state);
            l.add(zip);
            l.add(catName);
            if(withDist){
                l.add(dist);
            }
            l.add(janSubTotal);
            l.add(febSubTotal);            
            l.add(marSubTotal);            
            l.add(aprSubTotal);            
            l.add(maySubTotal);            
            l.add(junSubTotal);            
            l.add(julSubTotal);           
            l.add(augSubTotal);           
            l.add(sepSubTotal);           
            l.add(octSubTotal);           
            l.add(novSubTotal);           
            l.add(decSubTotal);
            l.add(subTotal);
            l.add(lastYearSubTotal);
            l.add(orderSubTotal);
            l.add(lastYearOrderSubTotal);
            return l;
        }
        
        private Record(ResultSet rs) throws SQLException{
            siteId = rs.getInt("SITE_ID");
            siteName = rs.getString("SITE_NAME");
            addr1 = rs.getString("ADDRESS1");
            addr2 = rs.getString("ADDRESS2");
            addr3 = rs.getString("ADDRESS3");
            addr4 = rs.getString("ADDRESS4");
            city = rs.getString("CITY");
            state = rs.getString("state_province_cd");
            zip = rs.getString("POSTAL_CODE");
            catName = rs.getString("catalog_name");
        }
        private Record(){
        }
        
        private Record copyBase(){
            Record clone = new Record();
            clone.siteId = this.siteId;
            clone.siteName = this.siteName;
            clone.addr1 = this.addr1;
            clone.addr2 = this.addr2;
            clone.addr3 = this.addr3;
            clone.addr4 = this.addr4;
            clone.city = this.city;
            clone.state = this.state;
            clone.zip = this.zip;
            clone.catName = this.catName;
            return clone;
        }
    }
}
