package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.BusEntityDAO;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *Kind of a dash board one glance report of cost centers all grouped by BSC and cost center.
 */
public class DateComparisonSpendReportByBSC implements GenericReport {
    private static final BigDecimal ZERO = new BigDecimal(0);
    
    
    private static final String MAIN_SQL1 =
    "SELECT " +
    "oi.order_item_id, o.order_id, max(oi.cost_center_id) as cost_center_id, "+
    "max(bsca.bus_entity1_id) as bsc_id, max(l.budget_period) as budget_period, max(l.budget_year) as budget_year, " +
    "max(total_freight_cost + total_misc_cost) as frt, " +
    "max(Total_quantity_ordered * cust_contract_price) as line_total " +
    "FROM  "+
    "clw_site_ledger l, clw_order o, clw_order_item oi, clw_bus_entity_assoc bsca "+
    "WHERE  "+
    "bsca.bus_entity_assoc_cd = 'BSC FOR SITE' AND bsca.bus_entity2_id = o.site_id "+
    "AND l.order_id = o.order_id "+
    "AND o.order_id = oi.order_id "+
    "AND o.order_status_cd in ('Ordered','Invoiced','Process ERP PO','ERP Released') "+
    "AND oi.order_item_status_cd in ('PENDING_ERP_PO','PENDING_FULFILLMENT','SENT_TO_DISTRIBUTOR','PO_ACK_SUCCESS','PO_ACK_ERROR','PO_ACK_REJECT','SENT_TO_DISTRIBUTOR_FAILED','INVOICED') "+
    "AND o.account_id IN (";
    
    private static final String MAIN_SQL2 =
    ") AND budget_period <= ";
    
    private static final String MAIN_SQL3 =
    " AND budget_year IN (";
    
    private static final String MAIN_SQL4 =
    ") GROUP BY oi.order_item_id, o.order_id ORDER BY o.order_id ";
    
    
    private static final String COST_CENTER_SQL =
    "Select short_desc from clw_cost_center where cost_center_id = ?";
    
    private static final String BSC_SQL =
    "Select short_desc from clw_bus_entity where bus_entity_id = ?";
    
    private HashMap map = new HashMap();
    private HashMap bscMap = new HashMap();
    private HashMap cstCntrMap = new HashMap();
    private ArrayList results = new ArrayList();
    private int yBud;
    private int periodBud;
    private Connection con;
    private String accountS;
    private PreparedStatement bscStmt;
    private HashSet orderItemsProcessed = new HashSet();
    
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        try{
            con = pCons.getDefaultConnection();
            accountS = (String) pParams.get("ACCOUNT_MULTI");
            if(!Utility.isSet(accountS)){
                accountS = (String) pParams.get("ACCOUNT_MULTI_OPT");
            }
            String dateS = (String) pParams.get("A_DATE");
            if(!Utility.isSet(dateS)){
                dateS = (String) pParams.get("A_DATE_OPT");
            }
            java.util.Date date = null;
            if(Utility.isSet(dateS)){
                try{
                    SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                    sdf.applyPattern("MM/dd/yyyy");
                    sdf.setLenient(true);
                    date = sdf.parse(dateS);
                }catch(Exception e){
                    throw new Exception("^clw^\""+dateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^");
                }
            }
            
            String yBudS = (String) pParams.get("BUDGET_YEAR_OPT");
            String periodBudS = (String) pParams.get("BUDGET_PERIOD_OPT");
            if(date == null && (!Utility.isSet(yBudS) || !Utility.isSet(periodBudS))){
                throw new Exception("^clw^Not enough criteria set.^clw^");
            }
            
            
            //figure out budget period and year using the date
            if(date != null){
                int accountId;
                if(accountS.indexOf(',')>0){
                    throw new Exception("^clw^Multiple accounts using date as parameter not implemented.^clw^");
                }else{
                    try{
                        accountId = Integer.parseInt(accountS);
                    }catch(NumberFormatException e){
                        throw new Exception("^clw^Account ("+accountS+") is not a valid number.^clw^");
                    }
                }
                BusEntityDAO bedao = new BusEntityDAO();
                FiscalPeriodView fpv = bedao.getFiscalInfo(con, accountId);
                yBud = fpv.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
                yBudS = Integer.toString(yBud);
                periodBud = fpv.getCurrentFiscalPeriod();
                periodBudS = Integer.toString(periodBud);
            }else{
                //only need to validate if it is being parsed from strings in param map
                try{
                    yBud = Integer.parseInt(yBudS);
                }catch(Exception e){
                    throw new Exception("^clw^\""+yBudS+"\" is not a valid number^clw^");
                }
                try{
                    periodBud = Integer.parseInt(periodBudS);
                }catch(Exception e){
                    throw new Exception("^clw^\""+periodBudS+"\" is not a valid number^clw^");
                }
            }
            
            String yBudInS = yBud + "," + (yBud - 1);
            
            ArrayList containerList = new ArrayList();
            String sql = MAIN_SQL1 + accountS + MAIN_SQL2 + periodBudS + MAIN_SQL3 + yBudInS + MAIN_SQL4;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Container c = new Container();
                c.populate(rs);
                containerList.add(c);
            }
            rs.close();
            stmt.close();
            
            //now pick up data that was not in the query but can be cached and do the grouping
            PreparedStatement cstCntrStmt = con.prepareStatement(COST_CENTER_SQL);
            
            Iterator it = containerList.iterator();
            int lastOrderId = -1;
            
            while(it.hasNext()){
                Container c = (Container) it.next();
                Integer orderItemIdI = new Integer(c.orderItemId);
                //this check is necessary if we remove grouping from sql
                //it seems faster to do the grouping at the database for the moment however,
                //as we continue to get more data this might change
                if(!orderItemsProcessed.contains(orderItemIdI)){ 
                    orderItemsProcessed.add(orderItemIdI);
                    String key = c.costCenterId + "::" + c.getBsc();
                    createRecord(key, c, cstCntrStmt,false,false);
                    key = c.getBsc();
                    createRecord(key, c, cstCntrStmt,false,true);
                    createRecord("total", c, cstCntrStmt,false,true);
                    if(lastOrderId != c.orderId){
                        key = "frt::" + c.getBsc();
                        createRecord(key, c, cstCntrStmt,true,false);
                        key = c.getBsc();
                        createRecord(key, c, cstCntrStmt,true,true);
                        createRecord("total", c, cstCntrStmt,true,true);
                        lastOrderId = c.orderId;
                    }
                }
            }
            
            
            //now render the report
            ArrayList headerDef = new ArrayList();
            headerDef.add(new SimpleHeaderDef("BSC$","String"));
            headerDef.add(new SimpleHeaderDef("Cost Center$","String"));
            headerDef.add(new SimpleHeaderDef("Current FY MTD$","BigDecimal"));
            headerDef.add(new SimpleHeaderDef("Last FY MTD$","BigDecimal"));
            headerDef.add(new SimpleHeaderDef("Difference$","BigDecimal"));
            headerDef.add(new SimpleHeaderDef("","String"));
            headerDef.add(new SimpleHeaderDef("Current FY YTD$","BigDecimal"));
            headerDef.add(new SimpleHeaderDef("Last FY YTD$","BigDecimal"));
            headerDef.add(new SimpleHeaderDef("Difference$","BigDecimal"));
            //use linked list as we are arbitrarily inserting records all over the place
            List list = new ArrayList();
            ARecord totalRec = (ARecord) map.get("total");
            map.remove("total");
            list.addAll(map.values());
            Collections.sort(list,RECORD_SORT);
            ArrayList newList = new ArrayList();
            newList.add(getInfoRow1());
            newList.add(getInfoRow2());
            newList.add(getSpacerRow());
            //insert spacers and total rows
            it = list.iterator();
            ARecord lastRec = null;
            
            while(it.hasNext()){
                Object o = it.next();
                ARecord rec = (ARecord) o;
                if(lastRec == null){
                    lastRec = rec;
                }else{
                    boolean currRecIsTotal = false;
                    boolean lastRecWasTotal = false;
                    if("TOTAL".equals(rec.costCenter)){
                        currRecIsTotal = true;
                    }
                    if("TOTAL".equals(lastRec.costCenter)){
                        lastRecWasTotal = true;
                    }
                    if(!currRecIsTotal && lastRecWasTotal){
                        totalRec.bsc = "GRAND TOTAL:";
                        totalRec.costCenter = "";
                        newList.add(totalRec);
                        newList.add(getSpacerRow());
                    }
                    if(!lastRec.bsc.equals(rec.bsc)){
                        if(!"TOTAL".equals(lastRec.costCenter)){
                            //newList.add(getTotalRow("Sub Total", new BigDecimal(666.66)));
                            newList.add(getSpacerRow());
                        }
                        lastRec = rec;
                        
                    }
                }
                newList.add(o);
            }
            return ReportingUtils.createReport(newList,headerDef,"",null);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    
    
    /*
     *Creates a record and adds it to the result list.
     */
    private void createRecord(String key, Container c,
    PreparedStatement cstCntrStmt, boolean doingFrt, boolean doingBSCSummary)
    throws SQLException{
        ARecord rec;
        ResultSet rs;
        if(map.containsKey(key)){
            rec = (ARecord) map.get(key);
        }else{
            rec = new ARecord();
            rec.bscId = c.bscId;
            rec.bsc = c.getBsc();
            rec.costCenterId = c.costCenterId;
            if(doingFrt){
                rec.costCenter = "Freight";
            }else{
                Integer costCenterIdInt = new Integer(rec.costCenterId);
                if(cstCntrMap.containsKey(costCenterIdInt)){
                    rec.costCenter = (String) cstCntrMap.get(costCenterIdInt);
                }else{
                    cstCntrStmt.setInt(1, rec.costCenterId);
                    rs = cstCntrStmt.executeQuery();
                    if(rs.next()){
                        rec.costCenter = rs.getString("short_desc");
                    }
                    rs.close();
                    if(rec.costCenter == null){
                        rec.costCenter = "Other";
                    }
                    cstCntrMap.put(costCenterIdInt,rec.costCenter);
                }
            }
            
            if(doingBSCSummary){
                rec.costCenter = "TOTAL";
            }
            map.put(key, rec);
        }
        
        BigDecimal toAdd;
        if(doingFrt){
            toAdd = c.frt;
        }else{
            toAdd = c.lineTotal;
        }
        if(c.budgetYear == yBud){
            if(c.budgetPeriod == periodBud){
                rec.currentFYMTD = rec.currentFYMTD.add(toAdd);
            }
            rec.currentFYYTD = rec.currentFYYTD.add(toAdd);
        }else{
            if(c.budgetPeriod == periodBud){
                rec.lastFYMTD = rec.lastFYMTD.add(toAdd);
            }
            rec.lastFYYTD = rec.lastFYYTD.add(toAdd);
        }
    }
    
    private class Container{
        int costCenterId;
        int bscId;
        BigDecimal lineTotal;
        int budgetPeriod;
        int budgetYear;
        BigDecimal frt;
        int orderId;
        int orderItemId;
        String bsc;
        
        String getBsc() throws SQLException{
            if(bsc == null){
                
                if(bscStmt == null){
                    bscStmt = con.prepareStatement(BSC_SQL);
                }
                Integer bscIdInt = new Integer(bscId);
                if(bscMap.containsKey(bscIdInt)){
                    bsc = (String) bscMap.get(bscIdInt);
                }else{
                    bscStmt.setInt(1, bscId);
                    ResultSet rs = bscStmt.executeQuery();
                    if(rs.next()){
                        bsc = rs.getString("short_desc");
                    }
                    rs.close();
                    if(bsc == null){
                        bsc = "";
                    }
                    //tokenize bsc name
                    if(bsc.indexOf('-')>0){
                        bsc = bsc.substring(0,bsc.indexOf('-')).trim();
                    }
                    bscMap.put(bscIdInt, bsc);
                }
            }
            return bsc;
        }
        
        public void populate(ResultSet rs) throws SQLException{
            costCenterId = rs.getInt("cost_center_id");
            bscId = rs.getInt("bsc_id");
            lineTotal = rs.getBigDecimal("line_total");
            budgetPeriod = rs.getInt("budget_period");
            budgetYear = rs.getInt("budget_year");
            frt = rs.getBigDecimal("frt");
            orderId = rs.getInt("order_id");
            orderItemId = rs.getInt("order_item_id");
        }
    }
    
    class ARecord implements Record{
        int costCenterId;
        int bscId;
        String costCenter;
        String bsc;
        BigDecimal currentFYMTD = ZERO;
        BigDecimal currentFYYTD = ZERO;
        BigDecimal lastFYMTD = ZERO;
        BigDecimal lastFYYTD = ZERO;
        
        
        public List toList(){
            ArrayList l = new ArrayList();
            l.add(bsc);
            l.add(costCenter);
            l.add(currentFYMTD);
            l.add(lastFYMTD);
            l.add(currentFYMTD.subtract(lastFYMTD));
            l.add("");
            l.add(currentFYYTD);
            l.add(lastFYYTD);
            l.add(currentFYYTD.subtract(lastFYYTD));
            return l;
        }
        
    }
    
    private List getSpacerRow(){
        ArrayList l = new ArrayList();
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        return l;
    }
    
    private List getTotalRow(String text, BigDecimal amt){
        ArrayList l = new ArrayList();
        l.add(text);
        l.add(amt);
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        return l;
    }
    
    private List getInfoRow1(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        ArrayList l = new ArrayList();
        l.add("As Of Date:");
        l.add(dateFormat.format(new java.util.Date()));
        l.add("");
        if(accountS.indexOf(',') > 0){
            l.add("Accounts");
        }else{
            l.add("Account");
        }
        l.add(accountS);
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        return l;
    }
    
    private List getInfoRow2(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        ArrayList l = new ArrayList();
        l.add("Budget Year:");
        l.add(Integer.toString(yBud));
        l.add("");
        l.add("Budget Period:");
        l.add(Integer.toString(periodBud));
        l.add("");
        l.add("");
        l.add("");
        l.add("");
        return l;
    }
    
    static final Comparator RECORD_SORT = new Comparator() {
        private String normalize(String s){
            if(s == null){return "";}
            s = s.trim();
            return s;
        }
        private int rankCostCenter(String costCenter){
            //Paper, Chemicals, Liners, Janitorial Supplies,Marble Care,Freight,Other
            if(costCenter.equals("Paper")){
                return 1;
            }
            if(costCenter.equals("Chemicals")){
                return 2;
            }
            if(costCenter.equals("Liners")){
                return 3;
            }
            if(costCenter.equals("Janitorial Supplies")){
                return 4;
            }
            if(costCenter.equals("Marble Care")){
                return 5;
            }
            if(costCenter.equals("Other")){
                return 6;
            }
            if(costCenter.equals("Freight")){
                return 7;
            }
            
            return -1;
        }
        public int compare(Object o1, Object o2) {
            ARecord r1 = (ARecord)o1;
            ARecord r2 = (ARecord)o2;
            String s1 = r1.bsc;
            String s2 = r2.bsc;
            s1 = normalize(s1);
            s2 = normalize(s2);
            String cc1 = normalize(r1.costCenter);
            String cc2 = normalize(r2.costCenter);
            
            //put the totals at the end
            if(cc1.equals("TOTAL") && cc2.equals("TOTAL")){
                return s1.compareTo(s2);
            }else if(cc1.equals("TOTAL")){
                return -1;
            }else  if(cc2.equals("TOTAL")){
                return 1;
            }else{
                //all values populated
                int val = s1.compareTo(s2);
                if(val == 0){
                    //we want to sort the cost centers in a strange way:
                    //Paper, Chemicals, Liners, Janitorial Supplies,Marble Care,Freight,Other
                    int cint1 = rankCostCenter(cc1);
                    int cint2 = rankCostCenter(cc2);
                    //deal with cases where a value could not be determined
                    if(cint1 < 0 && cint2 < 0){
                        return cc1.compareTo(cc2);
                    }else if(cint1 < 0){
                        return 1;
                    }else if(cint2 < 0){
                        return -1;
                    }
                    return cint1 - cint2;
                }
                return val;
            }
        }
    };
}
