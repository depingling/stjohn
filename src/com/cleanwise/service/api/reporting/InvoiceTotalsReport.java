/*
 * InvoiceTotalsReport.java
 *
 * Created on September 21, 2004
 *
 * Author Y.Kupershmidt
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.sql.*;
import java.math.BigDecimal;
public class InvoiceTotalsReport  implements GenericReportMulti {

    public com.cleanwise.service.api.value.GenericReportResultViewVector process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        //Lists of report results
        ArrayList catTotals = new ArrayList();
        ArrayList orderTotals = new ArrayList();
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

        String accountIdS = (String) pParams.get("ACCOUNT_MULTI");

        if(accountIdS==null) {
          accountIdS = (String) pParams.get("ACCOUNT");
        }
        String runForAccounts  = (String) pParams.get("runForAccounts");
        if(runForAccounts!=null) {
          if(accountIdS==null){
            accountIdS = runForAccounts;
          } else {
            accountIdS += ","+runForAccounts;
          }
        }
        StringTokenizer token = new StringTokenizer(accountIdS,",");
        IdVector accountIdV = new IdVector();
        while(token.hasMoreTokens()) {
           String acctS = token.nextToken();
           if(acctS.trim().length()>0)
           try {
             int acct = Integer.parseInt(acctS.trim());
             accountIdV.add(new Integer(acct));
           } catch(Exception exc){
             String mess = "^clw^"+acctS+" is not a valid account identifier ^clw^";
             throw new Exception(mess);
           }

        }
        // Check that all accounts from Cleanwise.com
        DBCriteria cr1 = new DBCriteria();
        cr1.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, accountIdV);
        String storeIdsSQL = "SELECT "
                + BusEntityAssocDataAccess.BUS_ENTITY2_ID + " FROM "
                + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " WHERE "
                + cr1.getWhereClause();
        DBCriteria cr = new DBCriteria();
        cr.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, 1);
        cr.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        cr.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeIdsSQL);
        BusEntityDataVector stores = BusEntityDataAccess.select(con, cr);
        if (stores != null && stores.size() > 0) {
            List<String> storeNames = new ArrayList<String>();
            for (int i = 0; i < stores.size(); i++) {
                BusEntityData item = (BusEntityData) stores.get(i);
                storeNames.add(item.getShortDesc());
            }
            String mess = "^clw^The report is not designed for " + storeNames
                    + (storeNames.size() == 1 ? " store" : " stores")
                    + ".^clw^";
            throw new Exception(mess);
        }
        /*
        if(accountIdV.size()==0) {
          String mess = "^clw^No accounts provided^clw^";
          throw new Exception(mess);
        }
         */
        BusEntityDataVector acctBusEntityDV = new BusEntityDataVector();
        String acctErpNumCond = null;
        List<String> acctErpNumList = new ArrayList<String>();
        for(Iterator iter=accountIdV.iterator(); iter.hasNext();){
          Integer acctIdI = (Integer) iter.next();
          try {
            BusEntityData beD = BusEntityDataAccess.select(con,acctIdI.intValue());
            acctBusEntityDV.add(beD);
            String acctErpNum = beD.getErpNum();
            if(acctErpNum==null ||acctErpNum.trim().length()==0) {
              String mess = "^clw^Account "+acctIdI+" does not have erp number ^clw^";
              throw new Exception(mess);
            }
            acctErpNumList.add(acctErpNum);
//            if(acctErpNumCond==null) {
//              acctErpNumCond = acctErpNum;
//            } else {
//              acctErpNumCond += ","+acctErpNum;
//            }
          } catch(Exception exc) {
            String mess = "^clw^"+acctIdI+" is not a valid account identifier ^clw^";
            throw new Exception(mess);
          }
        }
        if (acctErpNumList.size() > 0) {
            DBCriteria crAccErp = new DBCriteria();
            crAccErp.addOneOf("and oei.customer", acctErpNumList);
            acctErpNumCond = crAccErp.getWhereClause();
        }
        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc =
            repUtil.getUserAccessDescription(pParams,con);


        String badOrderStatusCd =
          "'"+RefCodeNames.ORDER_STATUS_CD.CANCELLED+"'"+
          ",'"+RefCodeNames.ORDER_STATUS_CD.DUPLICATED+"'"+
          ",'"+RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED+"'"+
          ",'"+RefCodeNames.ORDER_STATUS_CD.REJECTED+"'";

        String accessControlCond = null;
        if (userDesc.hasStoreAccess()) {
          accessControlCond += " and o.store_id in (" + userDesc.getAuthorizedSql() + ") ";
  	    }else if(userDesc.hasAccountAccess()) {
          accessControlCond =" and o.account_id in ("+
                    userDesc.getAuthorizedSql()+") ";
        } else if(userDesc.hasSiteAccess()) {
          accessControlCond = " and o.site_id in ("+
                    userDesc.getAuthorizedSql()+") ";
        }

      String sql =
       "select  "+
       " oei.INVC_PREFIX, "+
       " oei.INVC_NUMBER, "+
       " oeil.LINE_NBR, "+
       " to_number(trim(oeil.ITEM)) sku_num, "+
       " oeil.QUANTITY, "+
       " oeil.UNIT_PRICE, "+
       " oeil.LINE_GRS_BASE, "+
       " oei.CUSTOMER, "+
       " oei.INVC_TYPE, "+
       " oei.INVOICE_DATE, "+
       " o.REQUEST_PO_NUM, "+
       " o.ORIGINAL_ORDER_DATE, "+
       " o.ORDER_NUM, "+
       " o.ORDER_ID, "+
       " o.ACCOUNT_ID, "+
       " o.SITE_ID, "+
       " o.ORDER_SITE_NAME "+
       " from law_oeinvoice oei, law_oeinvcline oeil, clw_order o"+
       " where oei.invc_prefix = oeil.invc_prefix   "+
       " and oei.invc_number = oeil.invc_number  "+
       " and oei.r_status!=8 "+
       " and trim(translate(oeil.item,'1234567890','          ')) is null "+
       " and o.ERP_ORDER_NUM = oei.order_nbr  " +
       " and o.ORDER_STATUS_CD not in ("+badOrderStatusCd+")"+
       ((accessControlCond==null)?"":accessControlCond)+
       ((acctErpNumCond==null)?"":" " + acctErpNumCond) +
       " and invoice_date between to_date('"+begDateS+"','mm/dd/yyyy') "+
       " and to_date('"+endDateS+"','mm/dd/yyyy') "+
       " and oei.ORDER_NBR !=0 "+
       " union all "+
       "select  "+
       " oei.INVC_PREFIX, "+
       " oei.INVC_NUMBER, "+
       " oeil.LINE_NBR, "+
       " to_number(trim(oeil.ITEM)) sku_num, "+
       " oeil.QUANTITY, "+
       " oeil.UNIT_PRICE, "+
       " oeil.LINE_GRS_BASE, "+
       " oei.CUSTOMER, "+
       " oei.INVC_TYPE, "+
       " oei.INVOICE_DATE, "+
       " o.REQUEST_PO_NUM, "+
       " o.ORIGINAL_ORDER_DATE, "+
       " o.ORDER_NUM, "+
       " o.ORDER_ID, "+
       " o.ACCOUNT_ID, "+
       " o.SITE_ID, "+
       " o.ORDER_SITE_NAME "+
       " from law_oeinvcline oeil, law_oeinvoice origoei, "+
       "   clw_order o, law_oeinvoice oei "+
       " where oei.invc_prefix = oeil.invc_prefix   "+
       " and oei.invc_number = oeil.invc_number  "+
       " and oei.orig_invc_pre = origoei.invc_prefix  " +
       " and oei.ORIG_INVC_NBR = origoei.invc_number "+
       " and oei.r_status!=8 "+
       " and origoei.r_status!=8 "+
       " and origoei.ORDER_NBR !=0 " +
       " and trim(translate(oeil.item,'1234567890','          ')) is null "+
       " and o.ERP_ORDER_NUM = origoei.order_nbr  " +
       " and o.ORDER_STATUS_CD not in ("+badOrderStatusCd+")"+
       ((accessControlCond==null)?"":accessControlCond)+
       ((acctErpNumCond==null)?"":" " + acctErpNumCond)+
       " and oei.invoice_date between to_date('"+begDateS+"','mm/dd/yyyy') "+
       " and to_date('"+endDateS+"','mm/dd/yyyy') "+
       " and oei.ORDER_NBR =0 "+
       " order by sku_num, account_id ";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        LinkedList invoiceDetLL = new LinkedList();
        while(rs.next()){
          InvoiceDetail invd = new InvoiceDetail();
          invoiceDetLL.add(invd);
          invd.accountId = rs.getInt("account_id");
          invd.siteId = rs.getInt("site_id");
          invd.orderId = rs.getInt("order_id");
          invd.itemId = 0;
          invd.skuNum = rs.getInt("sku_num");
          invd.invoicePref = rs.getString("invc_prefix");
          invd.invoiceNum = rs.getInt("invc_number");
          invd.invoiceLineNum = rs.getInt("line_nbr");
          invd.invoiceDate = rs.getDate("invoice_date");
          invd.custPoNum = rs.getString("request_po_num");
          invd.orderNum = rs.getString("order_num");
          invd.orderDate = rs.getDate("original_order_date");
          invd.siteName = rs.getString("order_site_name");
          invd.accountName = "";
          invd.category = "";
          invd.qty = rs.getInt("quantity");
          invd.unitPrice = rs.getBigDecimal("unit_price");
          invd.lineAmount = rs.getBigDecimal("line_grs_base");
          invd.uom = "";
          invd.pack = "";
          invd.itemName ="";
        }
        rs.close();
        stmt.close();


        //Get items
        Object[] invoiceDetA = invoiceDetLL.toArray();
        IdVector catAccountIdV = new IdVector();
        int prevSkuNum = -1;
        int prevAcctId = -1;
        int counter = 0;
        LinkedList skuNumCondLL = new LinkedList();
        String skuNumCond = null;
        for(int ii=0; ii<invoiceDetA.length; ii++) {
          InvoiceDetail invd = (InvoiceDetail) invoiceDetA[ii];
          if(prevSkuNum!=invd.skuNum) {
            prevSkuNum = invd.skuNum;
            if(counter==0) {
              skuNumCond = ""+invd.skuNum;
            } else {
              skuNumCond += ","+invd.skuNum;
            }
            if(counter==999) {
              skuNumCondLL.add(skuNumCond);
              counter = 0;
              skuNumCond = null;
            }
            counter++;
          }
          if(prevAcctId!=invd.accountId) {
            prevAcctId = invd.accountId;
            Integer accountIdI = new Integer(invd.accountId);
            if(!catAccountIdV.contains(accountIdI)) {
               catAccountIdV.add(accountIdI);
            }
          }
        }
        if(skuNumCond!=null) {
          skuNumCondLL.add(skuNumCond);
        }
        //Get account catalog ids
        String accountCond = null;
        for(Iterator iter=catAccountIdV.iterator(); iter.hasNext();) {
           Integer accountIdI = (Integer) iter.next();
           if(accountCond==null) {
             accountCond = ""+accountIdI;
           } else {
             accountCond += ","+accountIdI;
           }
        }

        String resultSkuNumCond = "";
        if (skuNumCondLL != null && skuNumCondLL.size() > 0) {
        for (int ii=0; ii<skuNumCondLL.size();ii++) {
          String ss = (String)skuNumCondLL.get(0);
          if(ii==0) {
            resultSkuNumCond = "( i.sku_num in ("+ss+") ";
          } else {
            resultSkuNumCond += " OR i.sku_num in ("+ss+") ";
          }
        }
        resultSkuNumCond += ")";
        } else {
            resultSkuNumCond = "1=2";
        }
        //Get item categories
        sql = null;
        for(Iterator iter=catAccountIdV.iterator(); iter.hasNext();) {
           Integer accountIdI = (Integer) iter.next();
         if(sql==null) {
           sql = "";
         } else {
           sql += " union all ";
         }
         sql +=
         "select " +
         " ic.short_desc as category, cata.bus_entity_id as account_id, " +
         " i.sku_num" +
         " from clw_item ic, clw_item_assoc ia, clw_item i, " +
         "  clw_catalog cat, clw_catalog_assoc cata  " +
         " where 1=1 " +
         " and ic.item_id = ia.item2_id "+
         " and ia.item_assoc_cd = '"+RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+"' "+
         " and ia.item1_id = i.item_id "+
         " and "+resultSkuNumCond+" "+
         " and ia.catalog_id = cata.catalog_id "+
         " and cat.catalog_type_cd = '"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
         " and cat.catalog_id = cata.catalog_id "+
         " and cata.catalog_assoc_cd = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"'"+
         " and cata.bus_entity_id = "+accountIdI+" ";
        }
        if(sql != null){
	        sql += " order by sku_num, account_id ";

	        stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        int ind = 0;
	        while(rs.next()){
	          int skuNum = rs.getInt("sku_num");
	          int accountId = rs.getInt("account_id");
	          String category = rs.getString("category");
	          for(; ind<invoiceDetA.length; ind++) {
	            InvoiceDetail invcd = (InvoiceDetail) invoiceDetA[ind];
	            if(invcd.skuNum==skuNum && invcd.accountId==accountId) {
	              invcd.category = category;
	              continue;
	            }
	            if(invcd.skuNum>skuNum ||
	              (invcd.skuNum==skuNum && invcd.accountId>accountId )) {
	              break;
	            }
	          }
	        }
	        rs.close();
	        stmt.close();
        }

        //Get item properties
        sql =
         "select " +
         " i.short_desc, i.sku_num, i.item_id, uom.clw_value uom, pack.clw_value pack " +
         " from clw_item i, clw_item_meta uom, clw_item_meta pack " +
         " where 1=1 " +
         " and i.item_id = uom.item_id(+) " +
         " and uom.name_value(+) = 'UOM' " +
         " and i.item_id = pack.item_id(+) " +
         " and pack.name_value(+) = 'PACK' " +
         " and "+resultSkuNumCond+" "+
         " order by i.sku_num ";

        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        int ind = 0;
        while(rs.next()){
          int skuNum = rs.getInt("sku_num");
          int itemId = rs.getInt("item_id");
          String itemName = rs.getString("short_desc");
          String uom = rs.getString("uom");
          String pack = rs.getString("pack");
          for(; ind<invoiceDetA.length; ind++) {
            InvoiceDetail invcd = (InvoiceDetail) invoiceDetA[ind];
            if(invcd.skuNum==skuNum) {
              invcd.itemId = itemId;
              invcd.itemName = itemName;
              invcd.uom = uom;
              invcd.pack = pack;
              continue;
            }
            if(invcd.skuNum>skuNum) {
              break;
            }
          }
        }
        rs.close();
        stmt.close();
        //Reorder by Account, Invoice date, Invoice line number
        for(int ii=0; ii<invoiceDetA.length-1; ii++ ){
          boolean exitFl = true;
          for(int jj=0; jj<invoiceDetA.length-ii-1; jj++) {
            InvoiceDetail invcd1 = (InvoiceDetail) invoiceDetA[jj];
            InvoiceDetail invcd2 = (InvoiceDetail) invoiceDetA[jj+1];
            int accountId1 = invcd1.accountId;
            int accountId2 = invcd2.accountId;
            if(accountId1>accountId2) {
              invoiceDetA[jj] = invcd2;
              invoiceDetA[jj+1] = invcd1;
              exitFl = false;
            }
            else if(accountId1==accountId2) {
              Date date1 = invcd1.invoiceDate;
              Date date2 = invcd2.invoiceDate;
              int comp = date1.compareTo(date2);
              if(comp>0) {
                invoiceDetA[jj] = invcd2;
                invoiceDetA[jj+1] = invcd1;
                exitFl = false;
              } else if (comp==0) {
                String invoicePref1 = invcd1.invoicePref;
                String invoicePref2 = invcd2.invoicePref;
                int prefComp = invoicePref1.compareTo(invoicePref2);
                if(prefComp>0) {
                  invoiceDetA[jj] = invcd2;
                  invoiceDetA[jj+1] = invcd1;
                  exitFl = false;
                } else if (prefComp==0) {
                  int invNum1 = invcd1.invoiceNum;
                  int invNum2 = invcd2.invoiceNum;
                  if(invNum1>invNum2){
                    invoiceDetA[jj] = invcd2;
                    invoiceDetA[jj+1] = invcd1;
                    exitFl = false;
                  } else if (invNum1==invNum2) {
                    int lineNbr1 = invcd1.invoiceLineNum;
                    int lineNbr2 = invcd2.invoiceLineNum;
                    if(lineNbr1>lineNbr2) {
                      invoiceDetA[jj] = invcd2;
                      invoiceDetA[jj+1] = invcd1;
                      exitFl = false;
                    }
                  }
                }
              }
            }
          }
          if(exitFl) {
            break;
          }
        }

        //Get account name
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,catAccountIdV);
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
        BusEntityDataVector allAcctBusEntityDV =
                             BusEntityDataAccess.select(con,dbc);


        //Break invoices by Accounts
        LinkedList[] accountDetailPageA = new LinkedList[allAcctBusEntityDV.size()];
        Rebate[][] rebatePersentA =
              new Rebate[allAcctBusEntityDV.size()][];
        prevAcctId = -1;
        String accountName = null;
        LinkedList accountPageLL = null;
        for(int ii=0; ii<invoiceDetA.length; ii++) {
          InvoiceDetail invcd = (InvoiceDetail)invoiceDetA[ii];
          if(invcd.accountId!=prevAcctId) {
            prevAcctId = invcd.accountId;
            accountPageLL = new LinkedList();
            for(int jj=0; jj<allAcctBusEntityDV.size();jj++ ) {
              BusEntityData beD = (BusEntityData) allAcctBusEntityDV.get(jj);
              int aId = beD.getBusEntityId();
              if(aId==invcd.accountId) {
                accountName = beD.getShortDesc();
                accountDetailPageA[jj] = accountPageLL;
                // get rebate rate
                dbc = new DBCriteria();
                dbc.addEqualTo(BusEntityParameterDataAccess.BUS_ENTITY_ID,aId);
                dbc.addEqualTo(BusEntityParameterDataAccess.NAME,"Rebate Persent");
                dbc.addOrderBy(BusEntityParameterDataAccess.EFF_DATE);
                BusEntityParameterDataVector rebatePersentV =
                  BusEntityParameterDataAccess.select(con,dbc);
                if(rebatePersentV.size()>0) {
                  Rebate[] rebateA = new Rebate[rebatePersentV.size()];
                  int kk=0;
                  for(Iterator iter=rebatePersentV.iterator();iter.hasNext();) {
                    BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
                    Rebate rr = new Rebate();
                    rebateA[kk++] = rr;
                    rr.rebate = 0;
                    java.util.Date effDate = bepD.getEffDate();
                    if(effDate==null) effDate = Utility.MIN_DATE;
                    rr.effDate = effDate;
                    String rebateS = bepD.getValue();
                    try {
                      double rebate = Double.parseDouble(rebateS);
                      rr.rebate = rebate;
                    } catch (Exception exc) {}
                  }
                  rebatePersentA[jj] = rebateA;
                }
                break;
              }
            }
          }
          invcd.accountName = accountName;
          accountPageLL.add(invcd);
        }
        //Cretate detail pages
        if (accountDetailPageA.length == 0){
          for(Iterator iter=acctBusEntityDV.iterator(); iter.hasNext();){
            BusEntityData beD = (BusEntityData) iter.next();
            String pageName = beD.getShortDesc() + " Detail";
            processList(new LinkedList(), resultV, pageName,
                        getInvoiceReportHeader(false), true, null);
          }
         } else {
           for (int ii = accountDetailPageA.length - 1; ii >= 0; ii--) {
             BusEntityData beD = (BusEntityData) allAcctBusEntityDV.get(ii);
             String pageName = beD.getShortDesc() + " Detail";
             boolean rebateFl = (rebatePersentA[ii] == null) ? false : true;
             processList(accountDetailPageA[ii], resultV, pageName,
                         getInvoiceReportHeader(rebateFl),
                         true, rebatePersentA[ii]);
           }
         }
        //Cretate total pages
        if (accountDetailPageA.length == 0){
          for(Iterator iter=acctBusEntityDV.iterator(); iter.hasNext();){
            BusEntityData beD = (BusEntityData) iter.next();
            String pageName = beD.getShortDesc() + " Total";
            processList(new LinkedList(), resultV, pageName,
                        getInvoiceTotalReportHeader(false), true, null);
          }
        } else {
          for (int ii = accountDetailPageA.length - 1; ii >= 0; ii--) {
            BusEntityData beD = (BusEntityData) allAcctBusEntityDV.get(ii);
            String pageName = beD.getShortDesc() + " Total";
            LinkedList acctInvDetLL = accountDetailPageA[ii];
            LinkedList acctInvTotalLL = new LinkedList();
            String prevInvPref = "";
            int prevInvNum = -1;
            InvoiceTotal invct = null;
            for (Iterator iter = acctInvDetLL.iterator(); iter.hasNext(); ) {
              InvoiceDetail invcd = (InvoiceDetail) iter.next();
              if (!prevInvPref.equals(invcd.invoicePref) ||
                  prevInvNum != invcd.invoiceNum) {
                prevInvPref = invcd.invoicePref;
                prevInvNum = invcd.invoiceNum;
                invct = new InvoiceTotal();
                acctInvTotalLL.add(invct);
                invct.accountId = invcd.accountId;
                invct.siteId = invcd.siteId;
                invct.orderId = invcd.orderId;
                invct.invoicePref = invcd.invoicePref;
                invct.invoiceNum = invcd.invoiceNum;
                invct.invoiceDate = invcd.invoiceDate;
                invct.custPoNum = invcd.custPoNum;
                invct.orderNum = invcd.orderNum;
                invct.orderDate = invcd.orderDate;
                invct.siteName = invcd.siteName;
                invct.accountName = invcd.accountName;
                invct.itemAmount = invcd.lineAmount;
              }
              else {
                invct.itemAmount = invct.itemAmount.add(invcd.lineAmount);
              }

            }
            boolean rebateFl = (rebatePersentA[ii] == null) ? false : true;
            processList(acctInvTotalLL, resultV, pageName,
                        getInvoiceTotalReportHeader(rebateFl), true,
                        rebatePersentA[ii]);
          }
        }

        return resultV;
    }

    private void processList(List toProcess, GenericReportResultViewVector resultV, String name,
                            GenericReportColumnViewVector header, boolean alwaysCreate,
                            Rebate[] pRebateA)
                            {
        Iterator it = toProcess.iterator();
        if(alwaysCreate || it.hasNext()) {
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while(it.hasNext()) {
                result.getTable().add(((aRecord) it.next()).toList(pRebateA));
            }
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setName(name);
            resultV.add(result);
        }
    }

    /*
    int getIndex(Object[] pReplacedOrderA, int pOrderId, int pBegInd, int pEndInd)
    {
       int ind = (pEndInd + pBegInd)/2;
       ConsolidatedDetail cd = (ConsolidatedDetail) pReplacedOrderA[ind];
       if(cd.orderId==pOrderId) {
         for(; ind>=0;ind--) {
           cd = (ConsolidatedDetail) pReplacedOrderA[ind];
           if(cd.orderId!=pOrderId){
              return (ind+1);
           }
         }
         return 0;
       }
       if(pBegInd==pEndInd) {
         return -1;
       }
       if(cd.orderId>pOrderId) {
         return getIndex(pReplacedOrderA, pOrderId, pBegInd,ind-1);
       }
       return getIndex(pReplacedOrderA, pOrderId, ind+1, pEndInd);
    }
    */



    private interface aRecord{
        ArrayList toList(Rebate[] pRebateA);
    }

    private GenericReportColumnViewVector getInvoiceTotalReportHeader(boolean pRebateFl) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Invoice Num",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Po Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Confirm Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Item Total$",2,20,"NUMBER","*",false));
        if(pRebateFl) {
         header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Invoice Rebate$",2,20,"NUMBER","*",false));
        }
        return header;
    }

    private GenericReportColumnViewVector getInvoiceReportHeader(boolean pRebateFl) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Invoice Num",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Po Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Confirm Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Qty",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Line Total$",2,20,"NUMBER","*",false));
        if(pRebateFl) {
         header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Line Rebate$",2,20,"NUMBER","*",false));
        }
        return header;
    }

    private class InvoiceDetail implements aRecord{
        int accountId;
        int siteId;
        int orderId;
        int itemId;
        int skuNum;
        String invoicePref;
        int invoiceNum;
        int invoiceLineNum;
        Date invoiceDate;
        String custPoNum;
        String orderNum;
        Date orderDate;
        String siteName;
        String accountName;
        String category;
        int qty;
        BigDecimal lineAmount;
        BigDecimal unitPrice;
        String uom;
        String pack;
        String itemName;
        public ArrayList toList(Rebate[] pRebateA){
            ArrayList list = new ArrayList();
            list.add(siteName);
            list.add(invoicePref);
            list.add(new Integer(invoiceNum));
            list.add(invoiceDate);
            list.add(custPoNum);
            list.add(orderNum);
            list.add(orderDate);
            list.add(category);
            list.add(new Integer(skuNum));
            list.add(uom);
            list.add(pack);
            list.add(itemName);
            list.add(new Integer(qty));
            list.add(lineAmount);
            if(pRebateA!=null) {
              double rebate = 0;
              for(int ii=0; ii<pRebateA.length; ii++ ) {
                Rebate rebateD = pRebateA[ii];
                if(orderDate!=null && !orderDate.before(rebateD.effDate) &&
                 (rebateD.expDate==null || rebateD.expDate.after(orderDate))) {
                  rebate = rebateD.rebate*lineAmount.doubleValue()/100;
                  break;
                }
              }
              list.add(new BigDecimal(rebate));
            }
            return list;
        }
    }

    private class InvoiceTotal implements aRecord{
        int accountId;
        int siteId;
        int orderId;
        String invoicePref;
        int invoiceNum;
        Date invoiceDate;
        String custPoNum;
        String orderNum;
        Date orderDate;
        String siteName;
        String accountName;
        BigDecimal itemAmount;
        public ArrayList toList(Rebate[] pRebateA){
            ArrayList list = new ArrayList();
            list.add(siteName);
            list.add(invoicePref);
            list.add(new Integer(invoiceNum));
            list.add(invoiceDate);
            list.add(custPoNum);
            list.add(orderNum);
            list.add(orderDate);
            list.add(itemAmount);
            if(pRebateA!=null) {
              double rebate = 0;
              for(int ii=0; ii<pRebateA.length; ii++ ) {
                Rebate rebateD = pRebateA[ii];
                if(orderDate!=null && !orderDate.before(rebateD.effDate) &&
                 (rebateD.expDate==null || rebateD.expDate.after(orderDate))) {
                  rebate = rebateD.rebate*itemAmount.doubleValue()/100;
                  break;
                }
              }
              list.add(new BigDecimal(rebate));
            }
            //if(pRebatePersent!=null && pRebatePersent.doubleValue()>0.01) {
            //  double rebate = pRebatePersent.doubleValue()*itemAmount.doubleValue()/100;
            //  list.add(new BigDecimal(rebate));
            //}
            return list;
        }

    }

    private class Rebate {
      double rebate;
      java.util.Date effDate;
      java.util.Date expDate;
    }
}
