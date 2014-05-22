/*
 * FreightDetailReport.java
 *
 * Created on March 11, 2005, 2:50 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.ShoppingServices;
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
 *
 * @author YKupershmidt
 */
public class FreightDetailReport implements GenericReportMulti {
 
 /** Creates a new instance of FreightDetailReport */
 public FreightDetailReport() {
 }
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception 
    {
        Connection con = pCons.getMainConnection();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);
        com.cleanwise.service.api.APIAccess apiAccess = 
                                         new com.cleanwise.service.api.APIAccess();
        ShoppingServices shoppingServEjb = apiAccess.getShoppingServicesAPI();
        
        //format the dates from one string type to anouther
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date pStartDate = df.parse((String) pParams.get("BEG_DATE"));
        
        Date pEndDate = df.parse((String) pParams.get("END_DATE"));
        
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
///////////////
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
           
           String orderStatusString = ""+
             "'"+RefCodeNames.ORDER_STATUS_CD.CANCELLED+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.DUPLICATED+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.REJECTED+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW+"',"+
             "'"+RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW+"'";

           //Get customer invoice items 
           String sql = 
            "select "+
            " oei.INVC_PREFIX, "+
            " oei.INVC_NUMBER, "+
            " oi.order_line_num LINE_NBR, "+
            " oi.item_sku_num item, "+
            " oi.total_quantity_ordered QUANTITY, "+
            " oi.cust_contract_price UNIT_PRICE, "+
            " oi.dist_item_cost UNIT_COST, "+
            " oei.CUSTOMER, "+
            " oei.INVC_TYPE, "+
            " oei.INVC_SOURCE, "+
            " oei.INVOICE_DATE, "+
            " oei.ORIG_INVC_PRE, "+
            " oei.ORIG_INVC_NBR, "+
            " oei.ORDER_NBR, "+
            " oei.NON_INV_GDS_B goods_base, "+   
            " oei.misc_tot_base, "+
            " oi.item_id, "+
            " be.bus_entity_id account_id, "+
            " be.short_desc account_name, "+
            " o.site_id, "+
            " o.order_site_name, "+
            " o.contract_id, "+
            " o.order_source_cd, "+
            " o.total_rush_charge, "+
            " o.add_by  "+
            " from law_oeinvoice oei,  "+
            " clw_bus_entity be, clw_order o, clw_order_item oi "+
            " where 1=1 "+
            " and oei.r_status!=8 "+
            " and "+acctErpCond+
            " and be.erp_num = trim(oei.customer) "+
            " and oi.order_id = o.order_id "+
            " and oi.order_item_status_cd != '"
                   +RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED+"' "+
            " and be.bus_entity_type_cd = 'ACCOUNT' " +
            " and o.erp_order_num = to_char(oei.order_nbr) "+
            " and o.order_status_cd not in ("+orderStatusString+")" +
            " and invoice_date between '"+startDateS+"' and '"+endDateS+"' "+
             "and oei.misc_tot_base !=0 "+ 
            " order by oei.customer, oei.ORDER_NBR, oi.order_line_num ";


           Statement stmt = con.createStatement();
           ResultSet custInvcRS = stmt.executeQuery(sql);
           ArrayList freightAL = new ArrayList();           
           while ( custInvcRS.next() ) {
             
             FreightWrk fwrk = new FreightWrk();
             freightAL.add(fwrk);
             fwrk.invoicePerfix = custInvcRS.getString("INVC_PREFIX");
             fwrk.invoiceNumber =custInvcRS.getInt("INVC_NUMBER");
             fwrk.skuNum = custInvcRS.getString("item");
             fwrk.qty = custInvcRS.getInt("QUANTITY");
             fwrk.price = custInvcRS.getBigDecimal("UNIT_PRICE");
             fwrk.cost = custInvcRS.getBigDecimal("UNIT_COST");
             fwrk.accountErpNum = custInvcRS.getString("CUSTOMER");
             fwrk.invoiceType = custInvcRS.getString("INVC_TYPE");
             fwrk.invoiceSource = custInvcRS.getString("INVC_SOURCE");
             fwrk.invoiceDate = custInvcRS.getDate("INVOICE_DATE");
             fwrk.erpOrderNum = custInvcRS.getInt("ORDER_NBR");
             fwrk.itemId = custInvcRS.getInt("item_id");
             fwrk.accountId = custInvcRS.getInt("account_id");
             fwrk.siteId = custInvcRS.getInt("site_id");             
             fwrk.contractId = custInvcRS.getInt("contract_id");
             fwrk.accountName = custInvcRS.getString("account_name");
             fwrk.siteName = custInvcRS.getString("order_site_name");
             fwrk.origInviceNum = custInvcRS.getInt("orig_invc_nbr");
             fwrk.invoiceFreight = custInvcRS.getBigDecimal("misc_tot_base");
             fwrk.invoiceGoods = custInvcRS.getBigDecimal("goods_base");
             fwrk.orderSourceCd = custInvcRS.getString("order_source_cd");
             fwrk.totalRushCharge = custInvcRS.getBigDecimal("total_rush_charge");
             fwrk.addBy = custInvcRS.getString("add_by");
           }
           custInvcRS.close();
           stmt.close();
           
           //Calculate handling charges
           int prevErpOrderNum = -1; 
           OrderHandlingView frOrder = null;
           OrderHandlingItemViewVector frItems = null;
           ArrayList itemAL = null;
           ArrayList freightResAL = new ArrayList();
           FreightRes freightRes = null;
           ArrayList freightColumnAL = new ArrayList();

           for(Iterator iter=freightAL.iterator(); iter.hasNext();) {             
             FreightWrk fwrk = (FreightWrk) iter.next();
             
             if(fwrk.erpOrderNum!=prevErpOrderNum) {               
               if(prevErpOrderNum != -1) {
                 frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
                 freightRes.frOrder = frOrder;
                 OrderHandlingDetailViewVector ohdVwV = frOrder.getDetail();
                 for(Iterator iter1=ohdVwV.iterator(); iter1.hasNext();) {
                   OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter1.next();
                   if(RefCodeNames.CHARGE_TYPE_CD.TOTAL_AMOUNT_FREIGTH.
                           equals(ohdVw.getChargeTypeCd())) {
                     freightRes.baseFreight = ohdVw.getAmount();
                   } else if(RefCodeNames.CHARGE_TYPE_CD.SPECIAL_FREIGHT_RULE.
                           equals(ohdVw.getChargeTypeCd())) {
                     String ruleName = ohdVw.getRuleShortDesc()+
                             " ("+ohdVw.getPriceRuleId()+")";
                     if(!freightColumnAL.contains(ruleName)) {
                       freightColumnAL.add(ruleName);
                     }
                   }                 
                 }
               }
               frOrder = OrderHandlingView.createValue();
               frOrder.setTotalHandling(new BigDecimal(0));
               frOrder.setTotalFreight(new BigDecimal(0));
               frOrder.setContractId(fwrk.contractId );
               frOrder.setAccountId(fwrk.accountId );
               frOrder.setSiteId(fwrk.siteId);
               frOrder.setAmount(new BigDecimal(0));
               frOrder.setWeight(new BigDecimal(0));
               frItems = new OrderHandlingItemViewVector();
               frOrder.setItems(frItems);

               freightRes = new FreightRes();
               freightResAL.add(freightRes);
               freightRes.invoicePerfix = fwrk.invoicePerfix;
               freightRes.invoiceNumber = fwrk.invoiceNumber;
               freightRes.accountErpNum = fwrk.accountErpNum;
               freightRes.invoiceType = fwrk.invoiceType;
               freightRes.invoiceSource = fwrk.invoiceSource;
               freightRes.invoiceDate = fwrk.invoiceDate;
               freightRes.erpOrderNum = fwrk.erpOrderNum;
               freightRes.accountId = fwrk.accountId;
               freightRes.siteId = fwrk.siteId;
               freightRes.contractId = fwrk.contractId;
               freightRes.accountName = fwrk.accountName;
               freightRes.siteName = fwrk.siteName;
               freightRes.origInviceNum = fwrk.origInviceNum;
               freightRes.invoiceFreight = fwrk.invoiceFreight;
               freightRes.invoiceGoods = fwrk.invoiceGoods;
               freightRes.orderSourceCd = fwrk.orderSourceCd;
               freightRes.totalOrderGoods = new BigDecimal(0);
               freightRes.totalRushCharge = fwrk.totalRushCharge;
               freightRes.addBy = fwrk.addBy;
               itemAL = new ArrayList();
               freightRes.itemAL = itemAL;
            
               prevErpOrderNum = fwrk.erpOrderNum;
             }
             BigDecimal amount = frOrder.getAmount();
             amount = amount.add(fwrk.price.multiply(new BigDecimal(fwrk.qty)));
             frOrder.setAmount(amount);
             freightRes.totalOrderGoods = amount;
             OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
             frItems.add(frItem);
             frItem.setItemId(fwrk.itemId);
             frItem.setPrice(fwrk.price);
             frItem.setQty(fwrk.qty);
             frItem.setWeight(new BigDecimal(0));
             ItemSkuQty isq = new ItemSkuQty();
             itemAL.add(isq);
             isq.itemId = fwrk.itemId;
             isq.skuNum = fwrk.skuNum;
             isq.qty = fwrk.qty;
             isq.price = fwrk.price;
           }

           frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
           freightRes.frOrder = frOrder;
           OrderHandlingDetailViewVector ohdVwV = frOrder.getDetail();
           for(Iterator iter1=ohdVwV.iterator(); iter1.hasNext();) {
             OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter1.next();
             if(RefCodeNames.CHARGE_TYPE_CD.TOTAL_AMOUNT_FREIGTH.
                     equals(ohdVw.getChargeTypeCd())) {
               freightRes.baseFreight = ohdVw.getAmount();
             } else if(RefCodeNames.CHARGE_TYPE_CD.SPECIAL_FREIGHT_RULE.
                     equals(ohdVw.getChargeTypeCd())) {
               String ruleName = ohdVw.getRuleShortDesc()+
                       " ("+ohdVw.getPriceRuleId()+")";
               if(!freightColumnAL.contains(ruleName)) {
                 freightColumnAL.add(ruleName);
               }
             }                 
           }
        

        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader(customerVersionFl,freightColumnAL);
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("List");
        result.setTable(new ArrayList());
        resultV.add(result);
        
        for(Iterator iter=freightResAL.iterator(); iter.hasNext(); ) {
          FreightRes fr = (FreightRes) iter.next();
          ArrayList row = new ArrayList();
          result.getTable().add(row);
          row.add(fr.invoicePerfix);
          row.add(new Integer(fr.invoiceNumber));
          row.add(fr.accountErpNum);
          row.add(fr.invoiceType);
          row.add(fr.invoiceSource);
          row.add(fr.invoiceDate);
          row.add(new Integer(fr.erpOrderNum));
          row.add(new Integer(fr.accountId));
          row.add(new Integer(fr.siteId));
          row.add(new Integer(fr.contractId));
          row.add(fr.accountName);
          row.add(fr.siteName);
          row.add(new Integer(fr.origInviceNum));          
          row.add(fr.invoiceGoods);
          row.add(fr.totalOrderGoods);
          row.add(fr.invoiceFreight);

          itemAL = fr.itemAL;
          String itemStr = "";
          for(Iterator iter1=itemAL.iterator(); iter1.hasNext();){
            ItemSkuQty isq = (ItemSkuQty) iter1.next();
            if(itemStr.length()!=0) itemStr += ", ";
            itemStr += isq.skuNum + "("+isq.qty+")";
          }
          row.add(itemStr);
          
          row.add(fr.orderSourceCd);
          row.add(fr.totalRushCharge);
          row.add(fr.baseFreight);
          OrderHandlingView ohVw = fr.frOrder;
          ohdVwV = ohVw.getDetail();
          String excludeSkuS = "";
          BigDecimal sumExcludePrice = null;
          for(Iterator iter2=ohdVwV.iterator(); iter2.hasNext();) {
            OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter2.next();
            if(RefCodeNames.CHARGE_TYPE_CD.TOTAL_AMOUNT_FREIGTH.
                        equals(ohdVw.getChargeTypeCd())) {
              ArrayList excludeItemAL = ohdVw.getItemIdVector();
              for(Iterator iter3=excludeItemAL.iterator(); iter3.hasNext();) {
                Integer itemIdI = (Integer) iter3.next();
                int itemId = itemIdI.intValue();
                for(Iterator iter4=itemAL.iterator(); iter4.hasNext();) {
                  ItemSkuQty isq = (ItemSkuQty) iter4.next();
                  if(isq.itemId==itemId) {
                    if(excludeSkuS.length()!=0) excludeSkuS += ", ";
                    excludeSkuS += isq.skuNum+"("+isq.qty+")";
                    if(sumExcludePrice==null) sumExcludePrice = new BigDecimal(0);
                    sumExcludePrice = sumExcludePrice.add(isq.price.multiply(new BigDecimal(isq.qty)));
                    break;
                  }
                }                   
              }
            }
          }
          row.add(excludeSkuS);
          row.add(sumExcludePrice);

          for(Iterator iter1=freightColumnAL.iterator(); iter1.hasNext();) {
             String frColumnName = (String) iter1.next();
             boolean foundFl = false;
             for(Iterator iter2=ohdVwV.iterator(); iter2.hasNext();) {
               OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter2.next();
               ArrayList ruleItemAL = ohdVw.getItemIdVector();
               if(RefCodeNames.CHARGE_TYPE_CD.SPECIAL_FREIGHT_RULE.
                           equals(ohdVw.getChargeTypeCd())) {
                 String ruleName = ohdVw.getRuleShortDesc()+
                             " ("+ohdVw.getPriceRuleId()+")";
                 if(frColumnName.equals(ruleName)) {
                   foundFl = true;
                   row.add(ohdVw.getAmount());
                   String ruleSkuS = "";
                   for(Iterator iter3=ruleItemAL.iterator(); iter3.hasNext();) {
                     Integer itemIdI = (Integer) iter3.next();
                     int itemId = itemIdI.intValue();
                     for(Iterator iter4=itemAL.iterator(); iter4.hasNext();) {
                       ItemSkuQty isq = (ItemSkuQty) iter4.next();
                       if(isq.itemId==itemId) {
                         if(ruleSkuS.length()!=0) ruleSkuS += ", ";
                         ruleSkuS += isq.skuNum+"("+isq.qty+")";
                         break;
                       }
                     }                   
                   }
                   row.add(ruleSkuS);
                   break;
                 }
               }
             }
             if(!foundFl) {
               row.add(null);
               row.add(null);
             }
          }
          row.add(fr.addBy);
        
        }

        
        return resultV;
        }
        catch (SQLException exc) {
            errorMess = "Error. SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
    }
    
    private GenericReportColumnViewVector getReportHeader(boolean pCustomerVersionFl, 
            ArrayList pFreightColumns) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Prefix",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Invoice Number",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Erp Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Source",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Erp Order Num",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Account Id",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Site Id",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Contract Id",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Original Invoice Num",15,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Invoice Goods",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order Goods",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Invoice Freight",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Source",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Rush Charge",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Base Freight",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Exclude Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Exclude Items Price",0,255,"VARCHAR2"));
        for(Iterator iter=pFreightColumns.iterator(); iter.hasNext();) {
          String frColumnName = (String) iter.next();
          header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",frColumnName,2,20,"NUMBER"));
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",frColumnName+" Items",0,255,"VARCHAR2"));
        }        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Added By",0,255,"VARCHAR2"));
        return header;
    }


    
    public class FreightWrk {
     String invoicePerfix;
     int invoiceNumber;
     String skuNum;
     int qty;
     BigDecimal price;
     BigDecimal cost;
     String accountErpNum;
     String invoiceType;
     String invoiceSource;
     Date invoiceDate;
     int erpOrderNum;
     int itemId;
     int accountId;
     int siteId;
     int contractId;
     String accountName;
     String siteName;
     int origInviceNum;     
     BigDecimal invoiceGoods;
     BigDecimal invoiceFreight;
     String orderSourceCd;
     BigDecimal totalRushCharge;
     String addBy;
    }
    
    public class FreightRes {
     String invoicePerfix;
     int invoiceNumber;
     String accountErpNum;
     String invoiceType;
     String invoiceSource;
     Date invoiceDate;
     int erpOrderNum;
     int accountId;
     int siteId;
     int contractId;
     String accountName;
     String siteName;
     int origInviceNum;
     BigDecimal invoiceGoods;
     BigDecimal invoiceFreight;
     String orderSourceCd;
     BigDecimal totalOrderGoods;
     BigDecimal totalRushCharge;
     BigDecimal baseFreight;
     OrderHandlingView frOrder;
     ArrayList itemAL;
     String addBy;
    }
    
    public class ItemSkuQty {
      int itemId;
      String skuNum;
      int qty;
      BigDecimal price;
     
    }
}


