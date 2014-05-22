/*
 * JciRebateReport.java
 *
 * Created on March 2, 2004
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 * Picks up customer invoices and calculates rebate amount using item base price.
 * Uses vendor cost (minus 10%) if there is no base price for the item
 * @param pBeginDate start of the period. 
 * @param pEndDate  end of the period
 * @param pAccount list of Account ids (separated by comma)  
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class JciRebateReport implements GenericReportMulti {
    Map orderTotalInfo = new java.util.HashMap();
    /** Creates a new instance of InvoiceProfitReport */
    public JciRebateReport() {
    }
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception 
    {
        Connection con = pCons.getMainConnection();
        Connection lawCon = pCons.getLawsonConnection();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);
        
        //format the dates from one string type to anouther
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date pStartDate = df.parse((String) pParams.get("BEG_DATE"));
        Date earliestReportDate = df.parse("03/01/2004");
        boolean beforeAgeFl = pStartDate.before(earliestReportDate);
        if(beforeAgeFl) {
          String mess = "^clw^Begin Date can't be earlier 03/01/2004^clw^";
          throw new Exception(mess);
        }
        
        Date pEndDate = df.parse((String) pParams.get("END_DATE"));
        String revDateS = (String) pParams.get("AS_OF_DATE(date,false,As Of Date)");
        Date revDate = (revDateS==null || revDateS.trim().length()==0)? 
             null:df.parse(revDateS);
        
        //User
        String userIdS = (String) pParams.get("CUSTOMER");
        if(userIdS==null || userIdS.trim().length()==0){
          String mess = "^clw^No user provided^clw^";
          throw new Exception(mess);
        }
        int userId = 0;
        try {
          userId = Integer.parseInt(userIdS);
        }
        catch (Exception exc1) {
          String mess = "^clw^Wrong user id format^clw^";
          throw new Exception(mess);
        }
        //        
        df = new SimpleDateFormat("dd-MMM,yyyy");
        String startDateS = df.format(pStartDate);
        String endDateS = df.format(pEndDate);
        String accountListS = (String) pParams.get("ACCOUNT_MULTI_OPT");
        String runForAccounts  = (String) pParams.get("runForAccounts");
        if(runForAccounts!=null) {
          if(accountListS==null){
            accountListS = runForAccounts;
          } else {
            accountListS += ","+runForAccounts;
          }
        }
        IdVector accountIdV = new IdVector();
        DBCriteria dbc = null;
        try {
           if(accountListS==null || accountListS.trim().length()==0) {
             dbc = new DBCriteria();
             dbc.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,
                                           RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
             dbc.addEqualTo(GroupDataAccess.GROUP_STATUS_CD, 
                               RefCodeNames.GROUP_STATUS_CD.ACTIVE);
             dbc.addBeginsWithIgnoreCase(GroupDataAccess.SHORT_DESC,"Item Base Cost JCI");
             IdVector groupIdV = GroupDataAccess.selectIdOnly(con,dbc);

             dbc = new DBCriteria();
             dbc.addOneOf(GroupAssocDataAccess.GROUP_ID,groupIdV);
             dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, 
                  RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
             accountIdV = GroupAssocDataAccess.selectIdOnly(con,
                                      GroupAssocDataAccess.BUS_ENTITY_ID,dbc);
             
             dbc = new DBCriteria();
             dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountIdV);
             dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, 
                             RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
             accountIdV = BusEntityDataAccess.selectIdOnly(con,dbc);
              //String mess = "^clw^No accounts provided^clw^";
              //throw new Exception(mess);
           } else {
             StringTokenizer tok = new StringTokenizer(accountListS,",");
             //int[] accountIdA =new int [tok.countTokens()];
             while(tok.hasMoreTokens()){
               String acctS = tok.nextToken();
               if(acctS!=null && acctS.trim().length()>0) {
                 acctS = acctS.trim();
                 int acctId = 0;
                 try {
                   acctId = Integer.parseInt(acctS);
                 } catch(Exception exc) {
                    String mess = "^clw^Wrong account id: "+acctS+"^clw^";
                    throw new Exception(mess);
                 }
                 accountIdV.add(new Integer(acctId));
               }
             }
           }
           if(accountIdV.size()==0) {
              String mess = "^clw^No accounts provided^clw^";
              throw new Exception(mess);
           }

           DistCustInvoiceViewVector invoices = new DistCustInvoiceViewVector();
           dbc = new DBCriteria();
           //Get user accounts
           UserData userD = null;
           try {
             userD = UserDataAccess.select(con,userId);
           } catch(Exception exc) {
             String mess = "^clw^No user information found. User id: "+userIdS+"^clw^";
             throw new Exception(mess);
           }
           boolean checkAccountsFl = true;
           boolean customerVersionFl = true;
           IdVector userAccountIdV = new IdVector();
           if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userD.getUserTypeCd()) ||
              RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userD.getUserTypeCd())){
              checkAccountsFl = false;
              customerVersionFl = false;
           } else {
             //
             dbc = new DBCriteria();
             dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                                               RefCodeNames.USER_ASSOC_CD.ACCOUNT);
             dbc.addEqualTo(UserAssocDataAccess.USER_ID, userId);
             userAccountIdV = 
               UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID,dbc);
             
           }
          
           //Match accounts
           if(checkAccountsFl) {
             for(Iterator iter = accountIdV.iterator(); iter.hasNext();){
                Integer acctIdI = (Integer) iter.next();
                if(!userAccountIdV.contains(acctIdI)) {
                  iter.remove();
                }
              }
           }
           if(accountIdV.size()==0) {
              String mess = "^clw^User is not authorized to access account(s)^clw^";
              throw new Exception(mess);
            
           }
           
           //Get account erp_numbers
           dbc = new DBCriteria();
           dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountIdV);
           BusEntityDataVector accountDV = BusEntityDataAccess.select(con,dbc);
           String acctErpListS = "";
           for(int ii=0; ii<accountDV.size(); ii++) {
             BusEntityData beD = (BusEntityData) accountDV.get(ii);
             String erpNum = beD.getErpNum();
             if(acctErpListS.length()>0) acctErpListS += ",";
             acctErpListS += erpNum;
           }
           String acctErpCond = (acctErpListS.length()>0)?
                                   ("oei.customer in ("+acctErpListS+")"):("1=2");
                                   
           //Get group ids 
           dbc = new DBCriteria();
           dbc.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,
                                         RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
           dbc.addEqualTo(GroupDataAccess.GROUP_STATUS_CD,
                                         RefCodeNames.GROUP_STATUS_CD.ACTIVE);
           dbc.addBeginsWithIgnoreCase(GroupDataAccess.SHORT_DESC, 
                                                      "Item Base Cost JCI");
           IdVector groupIdV = 
              GroupDataAccess.selectIdOnly(con,GroupDataAccess.GROUP_ID,dbc);
           if(groupIdV.size()==0) {
              String mess = "^clw^No Base Cost groups found^clw^";
              throw new Exception(mess);
           }
           String groupListS = "";
           for(int ii=0; ii<groupIdV.size(); ii++) {
             Integer grIdI = (Integer) groupIdV.get(ii);
             if(ii>0) groupListS += ",";
             groupListS += grIdI;
           }

           //Account base cost
           String sql = 
            "select "+
            " prop.clw_value rebate_percent, ga.bus_entity_id, ga.group_id "+ 
            " from clw_group gr, clw_group_assoc ga, clw_property prop "+
            " where ga.group_id = gr.group_id "+
            "   and gr.short_desc like 'Item Base Cost JCI%' "+
            "   and ga.bus_entity_id = prop.bus_entity_id(+) "+
            "   and prop.short_desc(+) = 'Rebate Percent'  "+
            " and prop.property_type_cd(+) = '"+
                     RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD+"' "+
            " and prop.property_status_cd(+) = '"+
                     RefCodeNames.PROPERTY_STATUS_CD.ACTIVE+"' "+
            " and ga.group_assoc_cd = '"+
                     RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP+"' ";
           
//////////////////////////////
           Statement stmt = con.createStatement();
           ResultSet propRS = stmt.executeQuery(sql);
           HashMap rebatePercentHM = new HashMap();
           HashMap accountGroupHM = new HashMap();
           String busEntityListS = null;
           while ( propRS.next() ) {
             String rebatePercentS = propRS.getString("rebate_percent");
             int busEntityId = propRS.getInt("bus_entity_id");
             int groupId = propRS.getInt("group_id");
             accountGroupHM.put(new Integer(busEntityId),new Integer(groupId));
             if(busEntityListS==null) {
               busEntityListS = "" + busEntityId;               
             } else {
               busEntityListS += ", "+busEntityId;
             }
             try {
               double rebatePercentDb = Double.parseDouble(rebatePercentS);
               if(rebatePercentDb>0.00001) {
                 BigDecimal rebatePercentBD = new BigDecimal(rebatePercentS);
                 rebatePercentHM.put(new Integer(busEntityId), rebatePercentBD);
               }
             } catch (Exception exc) {
             }
           }
           propRS.close();
           stmt.close();
///////////////////////////////           
           //Get customer invoice items 
           sql = 
            "select "+
            " oei.INVC_PREFIX, "+
            " oei.INVC_NUMBER, "+
            " oeil.LINE_NBR, "+
            " trim(oeil.ITEM) item, "+
            " oeil.DESCRIPTION, "+
            " oeil.QUANTITY, "+
            " oeil.UNIT_PRICE, "+
            " oeil.UNIT_COST, "+
            " oei.CUSTOMER, "+
            " oei.INVC_TYPE, "+
            " oei.INVC_SOURCE, "+
            " oei.INVOICE_DATE, "+
            " oei.ORIG_INVC_PRE, "+
            " oei.ORIG_INVC_NBR, "+
            " co.ORDER_DATE, "+
            " co.ORDER_NBR, "+
            " i.item_id, "+
            " be.bus_entity_id, "+
            " be.short_desc, "+
            " ga.group_id "+
            " from law_oeinvoice oei, law_oeinvcline oeil, law_custorder co, "+
            "  clw_item i, clw_bus_entity be, clw_group_assoc ga "+
            " where 1=1 "+
            " and oei.invc_prefix = oeil.invc_prefix "+
            " and oei.invc_number = oeil.invc_number "+
            " and oei.r_status!=8 "+
            " and "+acctErpCond+
            " and trim(oeil.item) is not null "+
            " and i.sku_num(+) = trim(oeil.item) "+
            " and be.erp_num = trim(oei.customer) "+
            " and be.bus_entity_type_cd = 'ACCOUNT' " +
            " and invoice_date between '"+startDateS+"' and '"+endDateS+"' "+
            " and co.order_nbr = oei.order_nbr "+
            " and co.r_status != 8 "+
            " and ga.bus_entity_id = be.bus_entity_id "+
            " and ga.group_id in ("+groupListS+") " +
            " and ga.group_assoc_cd = '"+
                     RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP+"' "+
          //  " order by ga.group_id, i.item_id, oei.invoice_date";
            " order by ga.group_id, i.item_id, co.ORDER_DATE";

           stmt = con.createStatement();
           ResultSet custInvcRS = stmt.executeQuery(sql);
           JciRebateViewVector rebateVwV = new JciRebateViewVector();
           while ( custInvcRS.next() ) {
             JciRebateView rVw = JciRebateView.createValue();
             rebateVwV.add(rVw);
             rVw.setInvoicePrefix(custInvcRS.getString("INVC_PREFIX"));
             rVw.setInvoiceNum(custInvcRS.getInt("INVC_NUMBER"));
             rVw.setSku(custInvcRS.getString("item"));
             rVw.setItemDesc(custInvcRS.getString("DESCRIPTION"));
             rVw.setQty(custInvcRS.getInt("QUANTITY"));
             rVw.setCustomerPrice(custInvcRS.getBigDecimal("UNIT_PRICE"));
             rVw.setDistCost(custInvcRS.getBigDecimal("UNIT_COST"));
             rVw.setAccountErpNum(custInvcRS.getString("CUSTOMER"));
             rVw.setInvoiceType(custInvcRS.getString("INVC_TYPE"));
             rVw.setInvoiceSource(custInvcRS.getString("INVC_SOURCE"));
             rVw.setInvoiceDate(custInvcRS.getDate("INVOICE_DATE"));
             rVw.setOrderDate(custInvcRS.getDate("ORDER_DATE"));
             rVw.setErpOrderNum(custInvcRS.getInt("ORDER_NBR"));
             rVw.setItemId(custInvcRS.getInt("item_id"));
             int accountId = custInvcRS.getInt("bus_entity_id");
             rVw.setAccountId(accountId);
             rVw.setAccountName(custInvcRS.getString("short_desc"));
             rVw.setOrigInvoicePrefix(custInvcRS.getString("orig_invc_pre"));
             rVw.setOrigInvoiceNum(custInvcRS.getInt("orig_invc_nbr"));
             rVw.setGroupId(custInvcRS.getInt("group_id"));
             BigDecimal rebatePercentBD = (BigDecimal) rebatePercentHM.get(new Integer(accountId));
             rVw.setRebatePercent(rebatePercentBD);
           }
           custInvcRS.close();
           stmt.close();
           
           
           
           //Pick Up Base Cost
           SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
           Date minDate = sdf.parse("01/01/1900");
           Date maxDate = sdf.parse("01/01/3000");
           Date startDate = sdf.parse("03/01/2004");
           BaseCostDataVector baseCostDV = null;
           if(revDate == null ) {
             dbc = new DBCriteria();
             dbc.addOneOf(BaseCostDataAccess.GROUP_ID,groupIdV);
             dbc.addIsNull(BaseCostDataAccess.REV_DATE);
             dbc.addOrderBy(BaseCostDataAccess.GROUP_ID);
             dbc.addOrderBy(BaseCostDataAccess.ITEM_ID);
             dbc.addOrderBy(BaseCostDataAccess.DISTRIBUTOR_ID);
             dbc.addOrderBy(BaseCostDataAccess.EFF_DATE);
             baseCostDV = BaseCostDataAccess.select(con,dbc);
           }else{
             SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
             String bcSql = 
               "select " +
               "bc.BASE_COST_ID "+
               " from clw_base_cost bc, "+
               " ( "+
               " select group_id, item_id, eff_date, exp_date, min(rev_date) rev_date "+
               " from clw_base_cost bc "+
               " where nvl(rev_date,'1 jan 3000') > '"+sdf1.format(revDate)+"' "+
               " group by group_id, item_id, eff_date, exp_date "+
               " ) bcact "+
               " where bc.group_id = bcact.group_id "+
               " and bc.item_id = bcact.item_id "+
               " and nvl(bc.eff_date,'1 mar 2004') =  nvl(bcact.eff_date,'1 mar 2004')  "+
               " and nvl(bc.exp_date,'1 jan 3000') =  nvl(bcact.exp_date,'1 jan 3000')  "+
               " and nvl(bc.rev_date,'1 jan 3000') =  nvl(bcact.rev_date,'1 jan 3000')  ";
             
             Statement bcStmt = con.createStatement();
             ResultSet bcRS = bcStmt.executeQuery(bcSql);
             IdVector bcIdV = new IdVector();
             while (bcRS.next() ) {
               int bcId = bcRS.getInt("BASE_COST_ID");
               bcIdV.add(new Integer(bcId));
             }
             bcRS.close();
             bcStmt.close();

             dbc = new DBCriteria();
             dbc.addOneOf(BaseCostDataAccess.BASE_COST_ID,bcIdV);
             dbc.addOrderBy(BaseCostDataAccess.GROUP_ID);
             dbc.addOrderBy(BaseCostDataAccess.ITEM_ID);
             dbc.addOrderBy(BaseCostDataAccess.DISTRIBUTOR_ID);
             dbc.addOrderBy(BaseCostDataAccess.EFF_DATE);
             baseCostDV = BaseCostDataAccess.select(con,dbc);
           }
           
           BaseCostDataVector distBaseCostDV = new BaseCostDataVector();
           for(Iterator iter=baseCostDV.iterator();iter.hasNext();) {
             BaseCostData bcD = (BaseCostData) iter.next();
             int distId = bcD.getDistributorId();
             if(distId>0){
               distBaseCostDV.add(bcD);
             }
           }    

           
           JciRebateViewVector needDistRebateVwV = new JciRebateViewVector();
           JciRebateViewVector needOrderNumRebateVwV = new JciRebateViewVector();
           JciRebateViewVector problemInvoices = new JciRebateViewVector();
           //
           boolean baseCostFl = false;
           BaseCostData bcD = null;
           int prevGroupId = -1;
           int prevItemId = -1;
           int prevDistId = -1;
           boolean firstIntervalFl = false;
           for(Iterator iter = rebateVwV.iterator(),iter1=baseCostDV.iterator();iter.hasNext();) {
             baseCostFl = false;
             JciRebateView rVw = (JciRebateView) iter.next();
             int groupId = rVw.getGroupId();
             int itemId = rVw.getItemId();
             int distId = rVw.getDistributorId();
             Date orderDate = rVw.getOrderDate();
             while(true){
               if(bcD==null && iter1.hasNext()){
                 bcD = (BaseCostData) iter1.next();
               }
               if(bcD==null) {
                 break;
               }
               int itemId1 = bcD.getItemId();
               int groupId1 = bcD.getGroupId();
               int distId1 = bcD.getDistributorId();
               if(groupId1!=prevGroupId || itemId1!=prevItemId || distId1!=prevDistId) {
                 prevGroupId = groupId1;
                 prevItemId = itemId1;
                 prevDistId = distId1;
                 firstIntervalFl = true;
               }
               if(groupId>groupId1) {
                 bcD = null;
                 continue;
               }
               if(groupId<groupId1) {
                 break;
               }
               if(itemId>itemId1) {
                 bcD = null;
                 continue;
               }
               if(itemId<itemId1) {
                 break;
               }
               if(distId1>0){
                 needDistRebateVwV.add(rVw);                 
                 if(rVw.getErpOrderNum()==0){
                   if(rVw.getOrigInvoiceNum()>0 && 
                      "IN".equals(rVw.getOrigInvoicePrefix())){
                     needOrderNumRebateVwV.add(rVw);
                   } else {
                     problemInvoices.add(rVw);
                   }
                 }
                 baseCostFl = true;
                 break;
               }
               Date effDate = bcD.getEffDate();
               Date expDate = bcD.getExpDate();
               if(expDate!=null && !expDate.after(orderDate)) {
                 bcD = null;
                 continue;
               }
               if(firstIntervalFl && 
                  orderDate.before(effDate) && !startDate.equals(effDate)) {
                 break;
               }
               if(startDate.before(orderDate) && !firstIntervalFl){
                 bcD = null;
                 continue;
               }
               if(firstIntervalFl && effDate.after(orderDate)) {
                 //Orders before the 1 of march, 2004
                 BigDecimal baseCost = bcD.getBaseCost();
                 rVw.setBaseCost(baseCost);
                 rVw.setBaseCostEffDate(minDate);
                 rVw.setBaseCostExpDate(effDate);
                 break;
               }
               if(!effDate.after(orderDate)) {
                 BigDecimal baseCost = bcD.getBaseCost();
                 rVw.setBaseCost(baseCost);
                 rVw.setBaseCostEffDate(effDate);
                 rVw.setBaseCostExpDate(expDate);
                 break;
               }
             }
           }
           if(needOrderNumRebateVwV.size()>1000){
             errorMess = "^clw^Sorry. Report exceeded its limitations."+
                " Please reduce date interval or contact customer support^clw^";
             throw new Exception(errorMess); 
           }

           //Get order numbers 
           String origInvStr = "";
           int count = 0;
           for(Iterator iter = needOrderNumRebateVwV.iterator(); iter.hasNext();) {
             JciRebateView rVw = (JciRebateView) iter.next();
             if(count>0){
               origInvStr += ",";
             }
             count++;
           }
           if(count>0) {
             sql = 
               "select invc_number, order_nbr "+
               " from law_oeinvoice oei "+
               " where invc_prefix = 'IN' "+
               " and invc_number in ("+origInvStr+")";

             stmt = con.createStatement();
             ResultSet origInvcRS = stmt.executeQuery(sql);
             HashMap invOrderNbrHM = new HashMap();
             while (origInvcRS.next() ) {
               int origInvcNum =  origInvcRS.getInt("INVC_NUMBER");
               int orderNbr = origInvcRS.getInt("ORDER_NBR");
               invOrderNbrHM.put(new Integer(origInvcNum), new Integer(orderNbr));
             }
             origInvcRS.close();
             stmt.close();
             for(Iterator iter = needOrderNumRebateVwV.iterator(); iter.hasNext();) {
               JciRebateView rVw = (JciRebateView) iter.next();
               Integer origInvcNumI = new Integer(rVw.getOrigInvoiceNum());
               Integer orderNumI = (Integer) invOrderNbrHM.get(origInvcNumI);
               if(orderNumI != null) rVw.setErpOrderNum(orderNumI.intValue());
             }
           }
           
           //Get distributor dependent base cost
           if(needDistRebateVwV.size()>0) {
             count = 0;
             Object[] needDistRebateVwA = sortByOrderSku(needDistRebateVwV.toArray());
             LinkedList orderNumLL = new LinkedList();
             String orderNumS = "";
             int prevOrderNum = -1;
             for(int ii=0; ii<needDistRebateVwA.length; ii++){
               JciRebateView rVw = (JciRebateView) needDistRebateVwA[ii];
               int orderNum = rVw.getErpOrderNum();
               if(orderNum!=prevOrderNum){
                 prevOrderNum = orderNum;
                 count++;
                 if(count==1000) {
                   orderNumLL.add(orderNumS);
                   count = 0;
                   orderNumS = "";
                 }
                 if(orderNumS.length()>0) {
                   orderNumS += ",";
                 }
                 orderNumS += orderNum;
               }
             }
             orderNumLL.add(orderNumS);
             String poLineSql = 
               "select order_nbr, item, bus_entity_id dist_id "+
               " from law_poline pol, clw_bus_entity be "+
               "where pol.cancelled_fl = 'N' "+
               "  and be.erp_num = pol.vendor "+
               "  and be.bus_entity_type_cd = 'DISTRIBUTOR'  "+
               "  and ( ";
               count = 0;
             for(Iterator iter = orderNumLL.iterator();iter.hasNext();) {
               if(count>0) poLineSql += " OR ";
               poLineSql += "order_nbr in ("+iter.next()+")"; 
             }
             poLineSql += ")";
             poLineSql += " order by order_nbr, item ";

             Statement poStmt = con.createStatement();
             ResultSet poRS = poStmt.executeQuery(poLineSql);
             int index = 0;
             while (poRS.next() ) {
               int orderNum = poRS.getInt("ORDER_NBR");
               String item = poRS.getString("ITEM");
               int distId = poRS.getInt("DIST_ID");
               while(index<needDistRebateVwA.length){
                 JciRebateView rVw = (JciRebateView) needDistRebateVwA[index];
                 int on = rVw.getErpOrderNum();
                 if(orderNum>on) {
                   index++;
                   continue;
                 }
                 if(orderNum<on) {
                   break;
                 }
                 int comp = item.compareTo(rVw.getSku());
                 if(comp>0) {
                   index++;
                   continue;
                 }
                 if(comp<0) {
                   break;
                 }
                 rVw.setDistributorId(distId);
                 index++;
               }
             }
             poRS.close();
             poStmt.close();           

             //Assign Distr Base Cost
             bcD = null;
             needDistRebateVwA = sortByGroupItemDistDate(needDistRebateVwV.toArray());
             prevGroupId = -1;
             prevItemId = -1;
             prevDistId = -1;
             firstIntervalFl = false;
             Iterator iterBc = distBaseCostDV.iterator();
             for(int ii=0;  ii<needDistRebateVwA.length;ii++) {
               JciRebateView rVw = (JciRebateView) needDistRebateVwA[ii];
               int groupId = rVw.getGroupId();
               int itemId = rVw.getItemId();
               int distId = rVw.getDistributorId();
               if(distId==0) {
                  continue;
               }
               Date orderDate = rVw.getOrderDate();
               while(true){
                 if(bcD==null && iterBc.hasNext()){
                   bcD = (BaseCostData) iterBc.next();
                 }
                 if(bcD==null) {
                   break;
                 }
                 int groupId1 = bcD.getGroupId();
                 int itemId1 = bcD.getItemId();
                 int distId1 = bcD.getDistributorId();
                 if(groupId1!=prevGroupId || itemId1!=prevItemId || distId1!=prevDistId) {
                   prevGroupId = groupId1;
                   prevItemId = itemId1;
                   prevDistId = distId1;
                   firstIntervalFl = true;
                 }
                 if(groupId>groupId1) {
                   bcD = null;
                   continue;
                 }
                 if(groupId<groupId1) {
                   break;
                 }
                 if(itemId>itemId1) {
                   bcD = null;
                   continue;
                 }
                 if(itemId<itemId1) {
                   break;
                 }
                 if(distId>distId1){
                   bcD = null;
                   continue;
                 }
                 if(distId<distId1) {
                   break;
                 }
                 Date effDate = bcD.getEffDate();
                 Date expDate = bcD.getExpDate();
                 if(expDate!=null && !expDate.after(orderDate)) {
                   bcD = null;
                   continue;
                 }
                 if(firstIntervalFl && orderDate.before(effDate) && !startDate.equals(effDate)) {
                   break;
                 }
                 if(startDate.before(orderDate) && !firstIntervalFl){
                   bcD = null;
                   continue;
                 }
                 if(firstIntervalFl && effDate.after(orderDate)) {
                   //Orders before the 1 of march, 2004
                   BigDecimal baseCost = bcD.getBaseCost();
                   rVw.setBaseCost(baseCost);
                   rVw.setBaseCostEffDate(minDate);
                   rVw.setBaseCostExpDate(effDate);
                   break;
                 }
                 if(!effDate.after(orderDate)) {
                   BigDecimal baseCost = bcD.getBaseCost();
                   rVw.setBaseCost(baseCost);
                   rVw.setBaseCostEffDate(effDate);
                   rVw.setBaseCostExpDate(expDate);
                   break;
                 }
               }
             }
           }

           
           
           //Res sort by account item
           Object[] rebateA = rebateVwV.toArray();
           rebateA = sortByAccountItem(rebateA);
           
           JciRebateViewVector noCostInvoices = new JciRebateViewVector();
           int invcCount = 0;
           origInvStr = "";
           String itemStr = "";
           //Try to resolve 0 cost 
           for(int ii=0; ii<rebateA.length; ii++) {
             JciRebateView rVw = (JciRebateView) rebateA[ii];
             BigDecimal costBD = rVw.getDistCost();
             if(costBD==null || costBD.abs().doubleValue()<.00001) {
               noCostInvoices.add(rVw);
               String origPref = rVw.getOrigInvoicePrefix();
               int origNum = rVw.getOrigInvoiceNum();
               if("IN".equals(origPref) && invcCount<1000) {
                 if(invcCount>0) {
                   origInvStr += ",";
                   itemStr += ",";
                 }
                 origInvStr += origNum;
                 itemStr += "'"+rVw.getSku()+"'";
                 invcCount++;
               }
             }
           }
           if(invcCount>0) {
             Object[] noCostInvoicesA = noCostInvoices.toArray();
             noCostInvoicesA = sortByOrigInvoiceNum(noCostInvoicesA);
             sql = 
               "select invc_number, trim(item) item, unit_cost, UNIT_PRICE "+
               " from law_oeinvcline oeil "+
               " where invc_prefix = 'IN' "+
               " and invc_number in ("+origInvStr+")"+
               "order by invc_number";
             stmt = con.createStatement();
             ResultSet origInvcRS = stmt.executeQuery(sql);
             int iBase = 0;
             while ( origInvcRS.next() ) {
               int origInvcNum =  origInvcRS.getInt("INVC_NUMBER");
               String skuNum =  origInvcRS.getString("item");
               if(skuNum==null) continue;
               for(int ii=iBase; ii<noCostInvoicesA.length; ii++) {
                  JciRebateView jrVw = (JciRebateView) noCostInvoicesA[ii];
                  int invcNum = jrVw.getOrigInvoiceNum();
                  if(origInvcNum==invcNum) {
                    if(skuNum.equals(jrVw.getSku())) {
                      BigDecimal price = jrVw.getCustomerPrice();
                      BigDecimal origPrice = origInvcRS.getBigDecimal("UNIT_PRICE");
                      if(origPrice!=null && price!=null &&
                         origPrice.abs().subtract(price.abs()).abs().doubleValue()>0.0001) {
                          problemInvoices.add(jrVw);
                      }
                      jrVw.setDistCost(origInvcRS.getBigDecimal("UNIT_COST"));
                    }
                    continue;
                  } else if (origInvcNum>invcNum) {
                    iBase++;
                    continue;
                  } else {
                    break;
                  }
               }
             }
             origInvcRS.close();
             stmt.close();
           }
           
           //Group 
           int accountIdPrev = -1;
           int itemIdPrev = -1;
           int baseCostIndPrev = -1;
           BigDecimal baseCostPrevBD = null;
           Date effDatePrev = minDate;
           Date minOrderDate = maxDate;
           Date maxOrderDate = minDate;
           JciRebateSumViewVector rebateSumVwV = new JciRebateSumViewVector();
           JciRebateSumView rebateSumVw = null;
           double rebateBillSum = 0;
           double rebateCreditSum = 0;
           double rebateBaseBillSum = 0;
           double rebateBaseCreditSum = 0;
           double priceMin = -1.00;
           double priceMax = -1.00;
           double costMin = -1.00;
           double costMax = -1.00;
           int qtyBillSum = 0;
           int qtyCreditSum = 0;
           double  costBillSum = 0;
           double  costCreditSum = 0;
           double priceBillSum = 0;
           double priceCreditSum = 0;
           for(int ii=0; ii<rebateA.length; ii++) {
             JciRebateView rVw = (JciRebateView) rebateA[ii];
             int accountId = rVw.getAccountId();
             BigDecimal priceBD = rVw.getCustomerPrice();
             Date orderDate = rVw.getOrderDate();
             Date effDate = rVw.getBaseCostEffDate();
             if(effDate==null) effDate = minDate;
             double price = (priceBD==null)?0:priceBD.doubleValue();
             BigDecimal costBD = rVw.getDistCost();
             double cost = (costBD==null)?0:costBD.doubleValue();
             int itemId = rVw.getItemId();
             BigDecimal rebatePercentBD = Utility.bdNN(rVw.getRebatePercent());
             //if(rebatePercentBD==null) {
             //  problemInvoices.add(rVw);
             //  continue;
             //}
             double rebatePercent = rebatePercentBD.doubleValue();
             int qty = rVw.getQty();
             BigDecimal baseCostBD = rVw.getBaseCost();
             double baseCost = (baseCostBD==null)?0:baseCostBD.doubleValue();
             int baseCostInd = (baseCostBD==null)?0:1;
             if(price>0.0001 && cost<0.0001 && baseCostInd==0) {
               //can't calculate rebate
               problemInvoices.add(rVw);
               continue;
             }
             boolean creditFl = ("CR".endsWith(rVw.getInvoicePrefix()))?true:false;
             if(accountId != accountIdPrev ||
                itemId != itemIdPrev ||
                baseCostInd != baseCostIndPrev ||
                (baseCostInd==baseCostIndPrev && 
                 baseCostBD!=null && baseCostPrevBD!=null && 
                 baseCostBD.subtract(baseCostPrevBD).abs().doubleValue()>0.001) || 
                effDate.compareTo(effDatePrev)!=0) {
               accountIdPrev = accountId;
               itemIdPrev = itemId;
               effDatePrev = effDate;
               baseCostIndPrev = baseCostInd;
               baseCostPrevBD = baseCostBD;
               if(ii>0) {
                rebateSumVw.setMinOrderDate(minOrderDate);
                rebateSumVw.setMaxOrderDate(maxOrderDate);
                rebateSumVw.setBillQty(qtyBillSum);
                rebateSumVw.setCreditQty(qtyCreditSum);
                rebateSumVw.setQty(qtyBillSum+qtyCreditSum);
                if(priceMin>=0) rebateSumVw.setMinCustomerPrice(new BigDecimal(priceMin));
                if(priceMax>=0) rebateSumVw.setMaxCustomerPrice(new BigDecimal(priceMax));
                rebateSumVw.setTotalBillPrice(new BigDecimal(priceBillSum));
                rebateSumVw.setTotalCreditPrice(new BigDecimal(priceCreditSum));
                rebateSumVw.setTotalCustomerPrice(new BigDecimal(priceBillSum+priceCreditSum));
                rebateSumVw.setMinDistCost(new BigDecimal(costMin));
                rebateSumVw.setMaxDistCost(new BigDecimal(costMax));
                rebateSumVw.setTotalBillCost(new BigDecimal(costBillSum));
                rebateSumVw.setTotalCreditCost(new BigDecimal(costCreditSum));
                rebateSumVw.setTotalDistCost(new BigDecimal(costBillSum+costCreditSum));
                rebateSumVw.setBillRebate(new BigDecimal(rebateBillSum));
                rebateSumVw.setCreditRebate(new BigDecimal(rebateCreditSum));
                rebateSumVw.setRebate(new BigDecimal(rebateBillSum+rebateCreditSum));
                rebateSumVw.setRebateBillBase(new BigDecimal(rebateBaseBillSum));
                rebateSumVw.setRebateCreditBase(new BigDecimal(rebateBaseCreditSum));
                rebateSumVw.setRebateBase(new BigDecimal(rebateBaseBillSum+rebateBaseCreditSum));
                
               }
               rebateSumVw = JciRebateSumView.createValue();
               rebateSumVwV.add(rebateSumVw);

               rebateSumVw.setGroupId(rVw.getGroupId());
               rebateSumVw.setAccountErpNum(rVw.getAccountErpNum());
               rebateSumVw.setAccountId(rVw.getAccountId());
               rebateSumVw.setAccountName(rVw.getAccountName());
               rebateSumVw.setSku(rVw.getSku());
               rebateSumVw.setItemId(rVw.getItemId());
               rebateSumVw.setItemDesc(rVw.getItemDesc());
               rebateSumVw.setBaseCost(rVw.getBaseCost());
               rebateSumVw.setRebatePercent(rVw.getRebatePercent());
               rebateSumVw.setBaseCostEffDate(effDate);
               rebateSumVw.setBaseCostExpDate(rVw.getBaseCostExpDate());
               
               priceMin = -1;
               priceMax = -1;
               priceBillSum = 0;
               priceCreditSum = 0;

               costMin = -1;
               costMax = -1;
               costBillSum = 0;
               costCreditSum = 0;
               
               qtyBillSum = 0;
               qtyCreditSum = 0;

               rebateBillSum = 0;
               rebateCreditSum = 0;
               rebateBaseBillSum = 0;
               rebateBaseCreditSum = 0;
               minOrderDate = maxDate;
               maxOrderDate = minDate;
             } 
             if(creditFl){
               qtyCreditSum += qty;
               priceCreditSum += price*qty;
               costCreditSum += cost*qty;
             } else {
               qtyBillSum += qty;
               priceBillSum += price*qty;
               costBillSum += cost*qty;
             }
             if(minOrderDate.after(orderDate)) minOrderDate = orderDate;
             if(maxOrderDate.before(orderDate)) maxOrderDate = orderDate;
             if(priceMin>price || priceMin<0) priceMin = price;
             if(costMin>cost || costMin<0) costMin = cost;
             if(priceMax<price || priceMax<0) priceMax = price;
             if(costMax<cost || costMax<0) costMax = cost;

             if(!(price>-0.0001 && price<0.0001)) {
               if(baseCostInd==1) {
                 double diff = baseCost-price;
                 if(!(diff>-0.0001 && diff<0.0001)) {
                   if(creditFl) {
                     if(rebatePercent>0.000001) {
                       rebateCreditSum += (price-baseCost/(1-rebatePercent/100))*qty;
                       rebateBaseCreditSum += baseCost*qty;
                     }
                   } else {
                     if(rebatePercent>0.000001) {
                       rebateBillSum += (price-baseCost/(1-rebatePercent/100))*qty;
                       rebateBaseBillSum += baseCost*qty;
                     }
                   }
                 }
               } else {
                 double bc = cost*(0.9);
                 if(creditFl) {
                   if(rebatePercent>0.000001) {
                      rebateCreditSum += (price-bc/(1-rebatePercent/100))*qty;
                      rebateBaseCreditSum += bc*qty;
                   }
                 } else {
                   if(rebatePercent>0.000001) {
                     rebateBillSum += (price-bc/(1-rebatePercent/100))*qty;
                     rebateBaseBillSum += bc*qty;
                   }
                 }
               }
             }
           } //end of cycle
           if(rebateSumVw==null) {
             errorMess = "^clw^No Data for the Report^clw^";
             throw new Exception(errorMess);
           }
           rebateSumVw.setMinOrderDate(minOrderDate);
           rebateSumVw.setMaxOrderDate(maxOrderDate);
           rebateSumVw.setBillQty(qtyBillSum);
           rebateSumVw.setCreditQty(qtyCreditSum);
           rebateSumVw.setQty(qtyBillSum+qtyCreditSum);
           if(priceMin>=0) rebateSumVw.setMinCustomerPrice(new BigDecimal(priceMin));
           if(priceMax>=0) rebateSumVw.setMaxCustomerPrice(new BigDecimal(priceMax));
           rebateSumVw.setTotalBillPrice(new BigDecimal(priceBillSum));
           rebateSumVw.setTotalCreditPrice(new BigDecimal(priceCreditSum));
           rebateSumVw.setTotalCustomerPrice(new BigDecimal(priceBillSum+priceCreditSum));
           rebateSumVw.setMinDistCost(new BigDecimal(costMin));
           rebateSumVw.setMaxDistCost(new BigDecimal(costMax));
           rebateSumVw.setTotalBillCost(new BigDecimal(costBillSum));
           rebateSumVw.setTotalCreditCost(new BigDecimal(costCreditSum));
           rebateSumVw.setTotalDistCost(new BigDecimal(costBillSum+costCreditSum));
           rebateSumVw.setBillRebate(new BigDecimal(rebateBillSum));
           rebateSumVw.setCreditRebate(new BigDecimal(rebateCreditSum));
           rebateSumVw.setRebate(new BigDecimal(rebateBillSum+rebateCreditSum));
           rebateSumVw.setRebateBillBase(new BigDecimal(rebateBaseBillSum));
           rebateSumVw.setRebateCreditBase(new BigDecimal(rebateBaseCreditSum));
           rebateSumVw.setRebateBase(new BigDecimal(rebateBaseBillSum+rebateBaseCreditSum));
           
          //Get manufacturer info
          IdVector itemIdV = new IdVector();
          for(Iterator iter = rebateSumVwV.iterator(); iter.hasNext();){
            JciRebateSumView jrsVw = (JciRebateSumView) iter.next();
            int itemId = jrsVw.getItemId();
            itemIdV.add(new Integer(itemId));
          }
          
          dbc = new DBCriteria();
          dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,itemIdV);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                              RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
          dbc.addOrderBy(ItemMappingDataAccess.BUS_ENTITY_ID);
          ItemMappingDataVector manufItemMappingDV = 
                                       ItemMappingDataAccess.select(con,dbc);
          IdVector manufIdV = new IdVector();
          for(Iterator iter=manufItemMappingDV.iterator(); iter.hasNext();) {
            ItemMappingData imD = (ItemMappingData) iter.next();
            int busEntityId = imD.getBusEntityId();
            manufIdV.add(new Integer(busEntityId));
          }
          dbc = new DBCriteria();
          dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,manufIdV);
          dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
          BusEntityDataVector manufDV = BusEntityDataAccess.select(con,dbc);
          
          HashMap itemManufHM = new HashMap();
          BusEntityData manufD = null;
          for(Iterator iter=manufItemMappingDV.iterator(),iter1=manufDV.iterator();
                       iter.hasNext();) {
            ItemMappingData imD = (ItemMappingData) iter.next();
            int itemId = imD.getItemId();
            int manufId = imD.getBusEntityId();
            while(manufD!=null || iter1.hasNext()) {
              if(manufD==null) manufD = (BusEntityData) iter1.next();
              if(manufId<manufD.getBusEntityId()) {
                break;
              }
              if(manufId>manufD.getBusEntityId()) {
                manufD = null;
                continue;
              }
              itemManufHM.put(new Integer(itemId),manufD);
              break;
            }
          }

          for(Iterator iter = rebateSumVwV.iterator(); iter.hasNext();){
            JciRebateSumView jrsVw = (JciRebateSumView) iter.next();
            int itemId = jrsVw.getItemId();
            manufD = (BusEntityData) itemManufHM.get(new Integer(itemId));
            if(manufD!=null) {
              jrsVw.setManufId(manufD.getBusEntityId());
              jrsVw.setManufDesc(manufD.getShortDesc());
              jrsVw.setManufErp(manufD.getErpNum());
            } else {
              jrsVw.setManufId(0);
              jrsVw.setManufDesc("NA");
              jrsVw.setManufErp("");
            }
          }            
            
          //Break by accounts
          accountIdPrev = -1;
          ArrayList accountRebateAL = new ArrayList();
          JciRebateSumViewVector jrsVwV = null;
          for(Iterator iter = rebateSumVwV.iterator(); iter.hasNext();){
            JciRebateSumView jrsVw = (JciRebateSumView) iter.next();
            int accountId = jrsVw.getAccountId();
            if(accountId!=accountIdPrev) {
               accountIdPrev = accountId;
               jrsVwV = new JciRebateSumViewVector();
               accountRebateAL.add(jrsVwV);
            }
            jrsVwV.add(jrsVw);
          }          
          
          ArrayList noCategoryItems = new ArrayList();
          //Assign categories
          ArrayList catAL = null;
          for(int ii=0; ii<accountRebateAL.size(); ii++) {
            jrsVwV = (JciRebateSumViewVector) accountRebateAL.get(ii);
            for(int jj=0, kk=0; jj<jrsVwV.size(); jj++) {
              JciRebateSumView jrsVw = (JciRebateSumView) jrsVwV.get(jj);
              int itemId = jrsVw.getItemId();
              if(jj==0) {
                 //Get item category info
                int accountId = jrsVw.getAccountId();
                catAL = getItemCategory(con,accountId);
              }
              while(kk<catAL.size()) {               
                ItemCategory ic = (ItemCategory) catAL.get(kk);
                if(itemId==ic.itemId) {
                   jrsVw.setCategoryId(ic.categoryId);
                   jrsVw.setCategory(ic.category);
                   jrsVw.setMajorCategory(ic.majorCategory);
                   if(ic.majorCategory==null) {
                     noCategoryItems.add(jrsVw);
                   }
                   break;
                } else if(itemId>ic.itemId) {
                   kk++;
                } else {
                   noCategoryItems.add(jrsVw);
                   break;
                }
              }
            }
          }
          
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        //Create error page
        if(problemInvoices.size()>0 && !customerVersionFl){
          GenericReportResultView errorPage = GenericReportResultView.createValue();
          errorPage.setTable(new ArrayList());
          for(int ii=0; ii<problemInvoices.size(); ii++) {
            ArrayList row = new ArrayList();
            JciRebateView rVw = (JciRebateView) problemInvoices.get(ii);
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","AccountId",15,15,"NUMBER"));
              row.add(new Integer(rVw.getAccountId()));
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","AccountErpNum",0,255,"VARCHAR2"));
              row.add(rVw.getAccountErpNum());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","AccountName",0,255,"VARCHAR2"));
              row.add(rVw.getAccountName());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","InvoicePrefix",0,255,"VARCHAR2"));
              row.add(rVw.getInvoicePrefix());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","InvoiceNum",15,15,"NUMBER"));
              row.add(new Integer(rVw.getInvoiceNum()));
  //        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","InvoiceDate",0,0,"DATE"));
              row.add(rVw.getInvoiceDate());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","InvoiceType",0,255,"VARCHAR2"));
              row.add(rVw.getInvoiceType());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","InvoiceSource",0,255,"VARCHAR2"));
              row.add(rVw.getInvoiceSource());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
              row.add(rVw.getSku());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","ItemId",15,15,"NUMBER"));
              row.add(new Integer(rVw.getItemId()));
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ItemDesc",0,255,"VARCHAR2"));
              row.add(rVw.getItemDesc());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","CustomerPrice",2,20,"NUMBER"));
              row.add(rVw.getCustomerPrice());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","DistCost",2,20,"NUMBER"));
              row.add(rVw.getDistCost());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","BaseCost",2,20,"NUMBER"));
              row.add(rVw.getBaseCost());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","RebatePercent",2,20,"NUMBER"));
              row.add(rVw.getRebatePercent());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Qty",15,15,"NUMBER"));
              row.add(new Integer(rVw.getQty()));
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
              row.add(rVw.getCategory());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MajorCategory",0,255,"VARCHAR2"));//
              row.add(rVw.getMajorCategory());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","OrigInvoicePrefix",0,255,"VARCHAR2"));
              row.add(rVw.getOrigInvoicePrefix());
  //        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","OrigInvoiceNum",15,15,"NUMBER"));
              row.add(new Integer(rVw.getOrigInvoiceNum()));
              errorPage.getTable().add(row);
          }
          errorPage.setHeader(getProblemHeader());
          errorPage.setColumnCount(getProblemHeader().size());
          errorPage.setName("Problem Invoices");
          resultV.add(errorPage);
        }

        //Create no category item page
        if(noCategoryItems.size()>0 && !customerVersionFl) {
          GenericReportResultView noCategoryPage = GenericReportResultView.createValue();
          noCategoryPage.setTable(new ArrayList());
          for(int ii=0; ii<noCategoryItems.size(); ii++) {
            ArrayList row = new ArrayList();
            JciRebateSumView rVw = (JciRebateSumView) noCategoryItems.get(ii);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","AccountId",15,15,"NUMBER"));
            row.add(new Integer(rVw.getAccountId()));
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","AccountName",0,255,"VARCHAR2"));
            row.add(rVw.getAccountName());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
            row.add(rVw.getSku());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","ItemId",15,15,"NUMBER"));
            row.add(new Integer(rVw.getItemId()));
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ItemDesc",0,255,"VARCHAR2"));
            row.add(rVw.getItemDesc());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
            row.add(rVw.getCategory());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MajorCategory",0,255,"VARCHAR2"));
            row.add(rVw.getMajorCategory());
            noCategoryPage.getTable().add(row);
          }
          noCategoryPage.setHeader(getNoCategoryHeader());
          noCategoryPage.setColumnCount(getNoCategoryHeader().size());
          noCategoryPage.setName("No Category Items");
          resultV.add(noCategoryPage);
        }        
        
          //Create xls
          GenericReportResultView allAccounts = GenericReportResultView.createValue();
          GenericReportColumnViewVector allAccountsHeader = getReportHeader(customerVersionFl);
          allAccounts.setHeader(allAccountsHeader);
          allAccounts.setColumnCount(allAccountsHeader.size());
          allAccounts.setName("All Accounts");
          allAccounts.setTable(new ArrayList());
          GenericReportResultView result = null;
          for(int ii=0; ii<accountRebateAL.size(); ii++) {
            jrsVwV = (JciRebateSumViewVector) accountRebateAL.get(ii);
            for(int jj=0, kk=0; jj<jrsVwV.size(); jj++) {
              JciRebateSumView jrsVw = (JciRebateSumView) jrsVwV.get(jj);
              String accountName = jrsVw.getAccountName();
              if(jj==0) {
                result = GenericReportResultView.createValue();
                GenericReportColumnViewVector header = getReportHeader(customerVersionFl);
                result.setHeader(header);
                result.setColumnCount(header.size());
                result.setName(accountName);
                result.setTable(new ArrayList());
                resultV.add(result);
              }
              ArrayList row = new ArrayList();
              String accountErpNum = jrsVw.getAccountErpNum();
/////////////////////////////////////////////////////////////////
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
              row.add(accountErpNum);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Name",0,255,"VARCHAR2"));
              row.add(jrsVw.getAccountName());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Major Category",0,255,"VARCHAR2"));
              row.add(jrsVw.getMajorCategory());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
              row.add(jrsVw.getCategory());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item",0,255,"VARCHAR2"));
              row.add(jrsVw.getSku());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Name",0,255,"VARCHAR2"));
              row.add(jrsVw.getItemDesc());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufactrurer",0,255,"VARCHAR2"));
              row.add(jrsVw.getManufDesc());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Billing Qty",15,15,"NUMBER"));
              row.add(new Integer(jrsVw.getBillQty()));
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Credit Qty",15,15,"NUMBER"));
              row.add(new Integer(jrsVw.getCreditQty()));
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Total Qty",15,15,"NUMBER"));
              row.add(new Integer(jrsVw.getQty()));
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Table Base Cost",2,20,"NUMBER"));
              row.add(jrsVw.getBaseCost());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Calc Base Cost",2,20,"NUMBER"));
              int sumQty = jrsVw.getQty();
              BigDecimal rb = null;
              if(sumQty!=0) {
                rb = jrsVw.getRebateBase();
                if(rb!=null) {
                  rb = rb.divide(new BigDecimal(sumQty),2,BigDecimal.ROUND_HALF_UP);
                }
              }
              row.add(rb);
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate Percent%",2,20,"NUMBER"));
              BigDecimal rebatePersent = Utility.bdNN(jrsVw.getRebatePercent()).multiply(new BigDecimal(.01));
              row.add(rebatePersent);
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Rebate Base",2,20,"NUMBER"));
              row.add(jrsVw.getRebateBase());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Rebate Cost",2,20,"NUMBER"));
              BigDecimal rebateCost = jrsVw.getRebateBase();
              if(rebateCost!=null){
                double rcD = rebateCost.doubleValue();
                double rpD = rebatePersent.doubleValue();
                rebateCost = new BigDecimal(rcD/(1-rpD));
              }
              row.add(rebateCost);
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Price",2,20,"NUMBER"));
              row.add(jrsVw.getTotalCustomerPrice());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate",2,20,"NUMBER"));
              row.add(jrsVw.getRebate());
              if(!customerVersionFl){
                //  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Min Cost",2,20,"NUMBER"));
                row.add(jrsVw.getMinDistCost());
                //  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Max Cost",2,20,"NUMBER"));
                row.add(jrsVw.getMaxDistCost());
                // header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Billing Cost",2,20,"NUMBER"));
                row.add(jrsVw.getTotalBillCost());
                // header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Credit Cost",2,20,"NUMBER"));
                row.add(jrsVw.getTotalCreditCost());
                // header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Cost",2,20,"NUMBER"));
                row.add(jrsVw.getTotalDistCost());
              }
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Min Price",2,20,"NUMBER"));
              row.add(jrsVw.getMinCustomerPrice());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Max Price",2,20,"NUMBER"));
              row.add(jrsVw.getMaxCustomerPrice());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Billing Price",2,20,"NUMBER"));
              row.add(jrsVw.getTotalBillPrice());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Credit Price",2,20,"NUMBER"));
              row.add(jrsVw.getTotalCreditPrice());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate Billing",2,20,"NUMBER"));
              row.add(jrsVw.getBillRebate());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate Credit",2,20,"NUMBER"));
              row.add(jrsVw.getCreditRebate());
              Date effDate = jrsVw.getBaseCostEffDate();
              if(!customerVersionFl) {
                // header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Rebate Source",0,255,"VARCHAR2"));
                String rebateSource = "Contract Cost";
                if(jrsVw.getBaseCost()!=null) {
                  if(effDate.compareTo(minDate)!=0) {
                    rebateSource = "Base Cost";
                  } else {
                    rebateSource = "Estimated Base Cost";
                  }
                }
                row.add(rebateSource);
              }
//        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Base Cost Eff Date",0,0,"DATE"));
//        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Base Cost Exp Date",0,0,"DATE"));
              if(jrsVw.getBaseCost()==null || effDate==null){
                row.add(null); //eff date
                row.add(null); //exp date
              } else {
                if(effDate.compareTo(minDate)==0) {
                  row.add(jrsVw.getMinOrderDate());
                  row.add(startDate); //exp date
                } else {                 
                  row.add(effDate);
                  row.add(jrsVw.getBaseCostExpDate());
                }
              }
              if(!customerVersionFl) {
                // header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Item Id",15,15,"VARCHAR2"));
                row.add(new Integer(jrsVw.getItemId()));
              }
              result.getTable().add(row);
              allAccounts.getTable().add(row);
//        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Min Order Date",0,0,"DATE"));
              row.add(jrsVw.getMinOrderDate());
//        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Max Order Date",0,0,"DATE"));
              row.add(jrsVw.getMaxOrderDate());
            }
        }

        // United page
        resultV.add(allAccounts);
          
          
        //Create summary page
        JciRebateSumViewVector summaryRebate = new   JciRebateSumViewVector();
        for(int ii=0; ii<accountRebateAL.size(); ii++) {
          jrsVwV = (JciRebateSumViewVector) accountRebateAL.get(ii);
          Object[] accountRebA = jrsVwV.toArray();
          accountRebA = sortByMajorCategory(accountRebA);
          String majorCatPrev = "";
          double rSum = 0;
          double prSum = 0;
          JciRebateSumView jrsTotVw = null;
          for(int jj=0; jj<accountRebA.length; jj++) {
            JciRebateSumView jrsVw = (JciRebateSumView) accountRebA[jj];
            String majorCat = jrsVw.getMajorCategory();
            if(majorCat==null) majorCat = "";
            if(jj==0 || !majorCat.equals(majorCatPrev)) {
              if(jj>0) {
                jrsTotVw.setRebate(new BigDecimal(rSum));
                jrsTotVw.setTotalCustomerPrice(new BigDecimal(prSum));
              }
              majorCatPrev = majorCat;
              jrsTotVw = jrsVw.copy();
              summaryRebate.add(jrsTotVw);
              rSum = 0;
              prSum = 0;
            } 
            BigDecimal rebateBD = jrsVw.getRebate();
            if(rebateBD!=null) {
              rSum += rebateBD.doubleValue();
            }
            BigDecimal sumPriceBD = jrsVw.getTotalCustomerPrice();
            if(sumPriceBD!=null) {
              prSum += sumPriceBD.doubleValue();
            }
          }
          jrsTotVw.setRebate(new BigDecimal(rSum));
          jrsTotVw.setTotalCustomerPrice(new BigDecimal(prSum));
        }
        
        GenericReportResultView resultSum = GenericReportResultView.createValue();
        GenericReportColumnViewVector headerSum = getReportHeaderSum();
        resultSum.setHeader(headerSum);
        resultSum.setName("Summary");
        resultSum.setColumnCount(headerSum.size());
        ArrayList rows = new ArrayList();
        for(int ii=0; ii<summaryRebate.size(); ii++){
          JciRebateSumView jrsVw = (JciRebateSumView)summaryRebate.get(ii);
          ArrayList row = new ArrayList();
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
          row.add(jrsVw.getAccountErpNum());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Name",0,255,"VARCHAR2"));
          row.add(jrsVw.getAccountName());
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Major Category",0,255,"VARCHAR2"));
          row.add(jrsVw.getMajorCategory());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate Percent%",2,20,"NUMBER"));
          BigDecimal rebPrBD = jrsVw.getRebatePercent();
          if(rebPrBD!=null) rebPrBD = rebPrBD.multiply(new BigDecimal(.01)); 
          row.add(rebPrBD);
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Price",2,20,"NUMBER"));
          row.add(jrsVw.getTotalCustomerPrice());
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate",2,20,"NUMBER"));
          row.add(jrsVw.getRebate());
          rows.add(row); 
        }
        resultSum.setTable(rows);
        resultV.add(resultSum);
        return resultV;
        }
        catch (SQLException exc) {
            errorMess = "Error. SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
    }
    
    //We can't just use the meta data here because of all the various sub-queries that
    //take place.
    private GenericReportColumnViewVector getReportHeader(boolean pCustomerVersionFl) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Major Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufactrurer",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Billing Qty",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Credit Qty",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Total Qty",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Table Base Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Applied Base Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate Percent%",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Rebate Base",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Rebate Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate",2,20,"NUMBER"));
        if(!pCustomerVersionFl) {
          header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Min Cost",2,20,"NUMBER"));
          header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Max Cost",2,20,"NUMBER"));
          header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Billing Cost",2,20,"NUMBER"));
          header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Credit Cost",2,20,"NUMBER"));
          header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Cost",2,20,"NUMBER"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Min Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Max Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Billing Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Credit Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Billing Rebate",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Credit Rebate",2,20,"NUMBER"));
        if(!pCustomerVersionFl) {
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Rebate Source",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Base Cost Eff Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Base Cost Exp Date",0,0,"DATE"));
        if(!pCustomerVersionFl) {
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Item Id",15,15,"NUMBER"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","First Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Last Order Date",0,0,"DATE"));
        return header;
    }

    private GenericReportColumnViewVector getProblemHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","AccountId",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","AccountErpNum",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","AccountName",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","InvoicePrefix",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","InvoiceNum",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","InvoiceDate",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","InvoiceType",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","InvoiceSource",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","ItemId",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ItemDesc",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","CustomerPrice",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","DistCost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","BaseCost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","RebatePercent",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Qty",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MajorCategory",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","OrigInvoicePrefix",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","OrigInvoiceNum",15,15,"NUMBER"));
        return header;
    }

    private GenericReportColumnViewVector getNoCategoryHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","AccountId",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","AccountName",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","ItemId",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ItemDesc",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MajorCategory",0,255,"VARCHAR2"));
        return header;
    }
    
    private GenericReportColumnViewVector getReportHeaderSum() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Major Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate Percent%",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rebate",2,20,"NUMBER"));
        return header;
    }
    
    private Map mBusEntMap;
    private String getName(Connection pCon, String pId) throws SQLException{
        if(mBusEntMap == null){
            DBCriteria crit = new DBCriteria();
            List l = new ArrayList();
            l.add(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            l.add(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,l);
            BusEntityDataVector v = BusEntityDataAccess.select(pCon,crit);
            mBusEntMap = new java.util.HashMap();
            for(int i=0;i<v.size();i++){
                BusEntityData d = (BusEntityData) v.get(i);
                String erp = d.getErpNum();
                String desc = d.getShortDesc();
                if(erp == null){
                    erp = "0";
                }else{
                    erp = erp.trim();
                }
                if(desc == null){
                    desc = "";
                }else{
                    desc = desc.trim();
                }
                mBusEntMap.put(erp,desc);
            }
        }
        return (String) mBusEntMap.get(pId);
    }

    private Object[] sortByOrderSku(Object[] pRebates)
    {
        //Res sort by account item
      if(pRebates==null || pRebates.length<=1) {
        return pRebates;
      }
      for(int ii=0; ii<pRebates.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<pRebates.length-ii-1; jj++) {
          JciRebateView rVw1 = (JciRebateView) pRebates[jj];
          JciRebateView rVw2 = (JciRebateView) pRebates[jj+1];
          int orderNum1 = rVw1.getErpOrderNum();
          int orderNum2 = rVw2.getErpOrderNum();
          if(orderNum1>orderNum2) {
             pRebates[jj] = rVw2;
             pRebates[jj+1] = rVw1;
             exitFl = false;
          } else if(orderNum1==orderNum2) {
            String sku1 = rVw1.getSku(); 
            String sku2 = rVw2.getSku(); 
            int comp = sku1.compareTo(sku2);
            if(comp>0) {
               pRebates[jj] = rVw2;
               pRebates[jj+1] = rVw1;
               exitFl = false;
            }
          }
        }
        if(exitFl) {
          break;
        }
      }
      return pRebates;
     
    }

    private Object[] sortByGroupItemDistDate(Object[] pRebates)
    {
        //Res sort by account item
      if(pRebates==null || pRebates.length<=1) {
        return pRebates;
      }
      for(int ii=0; ii<pRebates.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<pRebates.length-ii-1; jj++) {
          JciRebateView rVw1 = (JciRebateView) pRebates[jj];
          JciRebateView rVw2 = (JciRebateView) pRebates[jj+1];
          int groupId1 = rVw1.getGroupId();
          int groupId2 = rVw2.getGroupId();
          if(groupId1>groupId2) {
             pRebates[jj] = rVw2;
             pRebates[jj+1] = rVw1;
             exitFl = false;
          } else if(groupId1==groupId2) {
            int itemId1 = rVw1.getItemId(); 
            int itemId2 = rVw2.getItemId(); 
            if(itemId1>itemId2) {
               pRebates[jj] = rVw2;
               pRebates[jj+1] = rVw1;
               exitFl = false;
            } else if(itemId1==itemId2) {
              int distId1 = rVw1.getDistributorId(); 
              int distId2 = rVw2.getDistributorId(); 
              if(distId1>distId2) {
                 pRebates[jj] = rVw2;
                 pRebates[jj+1] = rVw1;
                 exitFl = false;
              } else if(distId1==distId2) {
                Date orderDate1 = rVw1.getOrderDate();
                Date orderDate2 = rVw2.getOrderDate();
                int comp = orderDate1.compareTo(orderDate2);
                if(comp==1) {
                  pRebates[jj] = rVw2;
                  pRebates[jj+1] = rVw1;
                  exitFl = false;
                }
              }
            }
          }
        }
        if(exitFl) {
          break;
        }
      }
      return pRebates;
     
    }
    
    
    private Object[] sortByAccountItem(Object[] pRebates)
    {
        //Res sort by account item
      if(pRebates==null || pRebates.length<=1) {
        return pRebates;
      }
      for(int ii=0; ii<pRebates.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<pRebates.length-ii-1; jj++) {
          JciRebateView rVw1 = (JciRebateView) pRebates[jj];
          JciRebateView rVw2 = (JciRebateView) pRebates[jj+1];
          int accountId1 = rVw1.getAccountId();
          int accountId2 = rVw2.getAccountId();
          if(accountId1>accountId2) {
             pRebates[jj] = rVw2;
             pRebates[jj+1] = rVw1;
             exitFl = false;
          } else if(accountId1==accountId2) {
            int itemId1 = rVw1.getItemId(); 
            int itemId2 = rVw2.getItemId(); 
            if(itemId1>itemId2) {
               pRebates[jj] = rVw2;
               pRebates[jj+1] = rVw1;
               exitFl = false;
            } 
            else if(itemId1==itemId2) {
               Date orderDate1 = rVw1.getOrderDate();
               Date orderDate2 = rVw2.getOrderDate();
               int comp = orderDate1.compareTo(orderDate2);
               if(comp==1) {
                 pRebates[jj] = rVw2;
                 pRebates[jj+1] = rVw1;
                 exitFl = false;
               }
            }
          }
        }
        if(exitFl) {
          break;
        }
      }
      return pRebates;
    }
             

    
    private Object[] sortByOrigInvoiceNum(Object[] pRebates)
    {
        //Res sort by account item
      if(pRebates==null || pRebates.length<=1) {
        return pRebates;
      }
      for(int ii=0; ii<pRebates.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<pRebates.length-ii-1; jj++) {
          JciRebateView rVw1 = (JciRebateView) pRebates[jj];
          JciRebateView rVw2 = (JciRebateView) pRebates[jj+1];
          int invcNum1 = rVw1.getOrigInvoiceNum();
          int invcNum2 = rVw2.getOrigInvoiceNum();
          if(invcNum1>invcNum2) {
             pRebates[jj] = rVw2;
             pRebates[jj+1] = rVw1;
             exitFl = false;
          } 
        }
        if(exitFl) {
          break;
        }
      }
      return pRebates;
    }
    
    private Object[] sortByMajorCategory(Object[] pRebates)
    {
        //Res sort by account item
      if(pRebates==null || pRebates.length<=1) {
        return pRebates;
      }
      for(int ii=0; ii<pRebates.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<pRebates.length-ii-1; jj++) {
          JciRebateSumView rVw1 = (JciRebateSumView) pRebates[jj];
          JciRebateSumView rVw2 = (JciRebateSumView) pRebates[jj+1];
          String majorCat1 = rVw1.getMajorCategory();
          if(majorCat1==null) majorCat1 = "";
          String majorCat2 = rVw2.getMajorCategory();
          if(majorCat2==null) majorCat2 = "";
          int compRes = majorCat1.compareTo(majorCat2);
          if(compRes>0) {
             pRebates[jj] = rVw2;
             pRebates[jj+1] = rVw1;
             exitFl = false;
          }
        }
        if(exitFl) {
          break;
        }
      }
      return pRebates;
    }

    private ArrayList getItemCategory(Connection pCon, int pAccountId) 
    throws Exception
    {
      ArrayList categoryAL = new ArrayList();
      String sql = 
       "select cata.bus_entity_id, "+
       " i.item_id,  "+
       " i.sku_num,  "+
       " c.item_id cat_id, "+
       " c.short_desc category,  "+
       " c1.short_desc major_category  "+
       " from clw_item i, clw_item_assoc ia, clw_item c, clw_catalog_assoc cata, clw_catalog cat, "+
       " clw_item_assoc ia1, clw_item c1 "+
       " where i.item_id = ia.item1_id "+
       " and ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' "+
       " and ia.catalog_id = cata.catalog_id "+
       " and cata.bus_entity_id = "+pAccountId +" "+
       " and cata.catalog_assoc_cd = 'CATALOG_ACCOUNT' "+
       " and cat.catalog_id = cata.catalog_id "+
       " and cat.catalog_type_cd = 'ACCOUNT' "+
       " and cat.catalog_status_cd = 'ACTIVE' "+
       " and c.item_id = ia.item2_id "+
       " and c.item_id = ia1.item1_id(+) "+
       " and ia1.item_assoc_cd(+) = 'CATEGORY_MAJOR_CATEGORY' "+
       " and ia1.item2_id = c1.item_id(+) "+
       " order by cata.bus_entity_id, i.item_id ";

      Statement stmt = pCon.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while ( rs.next() ) {
        int itemId = rs.getInt("item_id");
        int skuNum = rs.getInt("sku_num");
        int catId = rs.getInt("cat_id");
        String cat = rs.getString("category");
        String majorCat = rs.getString("major_category");
        ItemCategory ic = new ItemCategory(pAccountId, itemId, skuNum, catId, cat, majorCat);
        categoryAL.add(ic);
      }
      
      return categoryAL;
    }
    
    public class ItemCategory {
      public int accountId;
      public int itemId;
      public int skuNum;
      public int categoryId;
      public String category;
      public String majorCategory;
      ItemCategory(int pAccountId, int pItemId, int pSkuNum, int pCategoryId, String pCategory, String pMajorCategory) {
        accountId = pAccountId;
        itemId = pItemId;
        skuNum = pSkuNum;
        categoryId = pCategoryId;
        category = pCategory;
        majorCategory = pMajorCategory;
      }
    }
    
    
}
