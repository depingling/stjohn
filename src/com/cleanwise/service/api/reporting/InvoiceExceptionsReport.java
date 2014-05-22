package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.view.utils.Constants;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.math.BigDecimal;

public class InvoiceExceptionsReport implements GenericReportMulti, ReportShowProperty {


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        ReportingUtils repUtil = new ReportingUtils();
        //String accountIdS = (String)repUtil.getParam(pParams, "LOCATE_ACCOUNT_MULTI");
        String accountIdS = (String) pParams.get("ACCOUNT_MULTI_OPT");
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

        /*ReportingUtils.UserAccessDescription userDesc =
            repUtil.getUserAccessDescription(pParams,con);

        String accessControlCond = "";
        if (userDesc.hasStoreAccess()) {
          accessControlCond += " and inv.store_id in (" + userDesc.getAuthorizedSql() + ") \n";
  	    }else if(userDesc.hasAccountAccess()) {
          accessControlCond =" and inv.account_id in ("+
                    userDesc.getAuthorizedSql()+") \n";
        } else if(userDesc.hasSiteAccess()) {
          accessControlCond = " and inv.site_id in ("+
                    userDesc.getAuthorizedSql()+") \n";
        }
          */

        String storeIdStr = (String) pParams.get("STORE");
        String storeCondSql = "";
        if (Utility.isSet(storeIdStr)) {
            storeCondSql = " and inv.store_id = " + storeIdStr + "\n";
        }

        String accountCond = "";
        if (null != accountIdS && accountIdS.trim().length() > 0) {
                accountCond += " AND inv.account_id in (" + accountIdS + ") \n";
        }

      String sql =
       "select distinct \n" +
               "inv.invoice_dist_id as invoice_id, \n" +
               "inv.invoice_num, \n" +
               "inv.invoice_date, \n" +
               "o.order_num,\n" +
               "o.order_id,\n" +
               "o.original_order_date as order_date,\n" +
               "inv.erp_po_num as po_num,\n" +
               "invd.dist_item_sku_num as dist_sku,\n" +
               "invd.order_item_id, \n" +
               "(select oi.dist_item_cost from clw_order_item oi where oi.order_item_id = invd.order_item_id) as order_cost,\n" +
               "invd.item_received_cost as invoice_cost, \n" +
               "inv.sub_total as invoice_total,\n" +
               "acc.short_desc as account_name,\n" +
               "site.short_desc as site_name, \n" +
               "sp.clw_value as site_ref_num,\n" +
               "op.clw_value as err_message,\n" +
               "op.message_key, \n" +
               "op.arg0, op.arg0_type_cd,\n" +
               "op.arg1, op.arg1_type_cd,\n" +
               "op.arg2, op.arg2_type_cd,\n" +
               "op.arg3, op.arg3_type_cd\n" +
               "\n" +
        "from \n" +
               "clw_invoice_dist_detail invd, clw_invoice_dist inv,\n" +
               "clw_bus_entity site, clw_bus_entity acc,\n" +
               "clw_order o, \n" +
               "clw_order_item oi,\n" +
               "clw_property sp, \n" +
               "clw_order_property op \n" +
               "\n" +
        "where inv.invoice_dist_id = invd.invoice_dist_id\n" +
               "and site.bus_entity_id = inv.site_id\n" +
               "and acc.bus_entity_id = inv.account_id\n" +
               "and o.order_id = inv.order_id\n" +
               "and oi.order_id = o.order_id\n" +
               "and (oi.order_item_id = invd.order_item_id or invd.order_item_id = 0)\n" +
               "and inv.invoice_status_cd = '"+ RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW+ "' \n" +
               storeCondSql +
               //accessControlCond +
               "and sp.bus_entity_id = inv.site_id\n" +
               "and sp.short_desc(+) = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "' \n" +
               "and op.message_key in ('pipeline.message.priceErrFoundInvoiceDist','pipeline.message.noItemFoundInvoiceDist') \n" +
               "and op.invoice_dist_detail_id = invd.invoice_dist_detail_id \n" +
               "and op.order_id = o.order_id \n" +
               "and op.short_desc = 'Invoice Error' \n" +
               accountCond;


        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        LinkedList invoiceDetLL = new LinkedList();
        while(rs.next()){
            InvoiceExceptionsReport.InvoiceDetail invd = new InvoiceExceptionsReport.InvoiceDetail();
            invd.invoiceId = rs.getInt("invoice_id");
            invd.invoiceNum = rs.getString("invoice_num");
            invd.invoiceDate = rs.getDate("invoice_date");
            invd.orderNum = rs.getString("order_num");
            invd.orderDate = rs.getDate("order_date");
            invd.purchaseOrderNum = rs.getString("po_num");
            invd.distSku = rs.getString("dist_sku");
            invd.orderCost = rs.getBigDecimal("order_cost");
            invd.invoiceCost = rs.getBigDecimal("invoice_cost");
            invd.invoiceTotal = rs.getBigDecimal("invoice_total");
            invd.accountName = rs.getString("account_name");
            invd.siteName = rs.getString("site_name");
            invd.siteRefNum = rs.getString("site_ref_num");
            invd.order_id = rs.getInt("order_id");

            invd.errMessage = rs.getString("err_message");
            invd.noteCode = rs.getString("message_key");

            invd.arg0 = rs.getString("arg0");
            invd.arg0TypeCd = rs.getString("arg0_type_cd");
            invd.arg1 = rs.getString("arg1");
            invd.arg1TypeCd = rs.getString("arg1_type_cd");
            invd.arg2 = rs.getString("arg2");
            invd.arg2TypeCd = rs.getString("arg2_type_cd");
            invd.arg1 = rs.getString("arg3");
            invd.arg3TypeCd = rs.getString("arg3_type_cd");

            invoiceDetLL.add(invd);

        }
        rs.close();
        stmt.close();

        processList(invoiceDetLL, resultV, "Invoice Exception Report",
                        getReportHeader());

        return resultV;
    }

    private void processList(List toProcess, GenericReportResultViewVector resultV, String name,
                            GenericReportColumnViewVector header) {
        GenericReportResultView result = GenericReportResultView.createValue();

        result.setTable(new ArrayList());
        result.setColumnCount(header.size());
        result.setHeader(header);
        result.setName(name);

        Iterator it = toProcess.iterator();
        while(it.hasNext()) {
            result.getTable().add(((InvoiceDetail) it.next()).toList());
        }

        resultV.add(result);
    }


    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Num",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Purchase Order Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order Cost",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Invoice Cost",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Invoice Total",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Ref Number",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Note",0,255,"VARCHAR2"));

        return header;
    }


    private class InvoiceDetail {
        int invoiceId;
        String invoiceNum;
        java.sql.Date invoiceDate;
        String orderNum;
        java.sql.Date orderDate;
        String purchaseOrderNum;
        String distSku;
        BigDecimal orderCost;
        BigDecimal invoiceCost;
        BigDecimal invoiceTotal;
        String accountName;
        String siteName;
        String siteRefNum;

        String errMessage;
        String noteCode;
        String arg0;
        String arg0TypeCd;
        String arg1;
        String arg1TypeCd;
        String arg2;
        String arg2TypeCd;
        String arg3;
        String arg3TypeCd;

        int order_id;

        public ArrayList toList(){

            ArrayList list = new ArrayList();
            list.add(invoiceNum);
            list.add(invoiceDate);
            list.add(orderNum);
            list.add(orderDate);
            list.add(purchaseOrderNum);
            list.add(distSku);
            list.add(orderCost);
            list.add(invoiceCost);
            list.add(invoiceTotal);
            list.add(accountName);
            list.add(siteName);
            list.add(siteRefNum);
            list.add(errMessage);

            return list;
        }
    }


    public boolean showOnlyDownloadReportButton() {
       return true;
    }


}
