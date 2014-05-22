package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.Java2ReportItemTransformer;
import com.cleanwise.service.api.util.ConnectionContainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Picks up all catalog items and adds year to date trade infrmation Adapted
 * from the ReportOrderBean to the new GenericReport Framework
 */
public class DistrInvoiceXmlReport implements DomUniversalReport {
    public final static String INVOICE_NUM = "INVOICE_NUM";
    public final static String DISTRIBUTOR = "DISTRIBUTOR";

    public ReportItem process(ConnectionContainer pCons,
            GenericReportData pReportData, Map pParams) throws Exception {
        ReportItem tradingPartnerItem = null;
        String pInvoiceNum = (String) pParams.get(INVOICE_NUM);
        if(pInvoiceNum==null) {
            String errorMess = "^clw^No Invoice Number found^clw^";
            throw new Exception(errorMess);
        }
        String pDistId = (String) pParams.get(DISTRIBUTOR);
        if(pDistId==null) {
            String errorMess = "^clw^No Distributor found^clw^";
            throw new Exception(errorMess);
        }

        Connection con = pCons.getDefaultConnection();

        String invoiceSql =
           "select "+
           " id.INVOICE_NUM, "+
           " id.INVOICE_DATE, "+
           " o.request_po_num po_number, "+
           " o.order_num, "+
           " id.ERP_PO_NUM dist_po_number, "+
           " id.SUB_TOTAL, "+
           " id.FREIGHT, "+
           " id.MISC_CHARGES, "+
           " id.SALES_TAX, "+
           " pr.CLW_VALUE as JOB_NUMBER, "+
           " id.invoice_dist_id, "+
           " id.invoice_status_cd "+
           "  from clw_invoice_dist id, clw_order o, clw_bus_entity be, clw_property pr "+
           " where id.invoice_num = ? "+
           " and o.order_id = id.order_id "+
           " and id.bus_entity_id = "+pDistId +
           " and id.bus_entity_id = be.bus_entity_id "+
           " and pr.bus_entity_id(+) = o.site_id " +
           " and lower(trim(pr.short_desc(+))) = 'job number:' ";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        PreparedStatement stmt = con.prepareStatement(invoiceSql);
        stmt.setString(1, pInvoiceNum);
        ResultSet  rs = stmt.executeQuery();
        ReportItem rootRI = ReportItem.createValue("root");
        ReportItem invoiceRI = ReportItem.createValue("Invoice");
        rootRI.addChild(invoiceRI);
        int invId = 0;
        ArrayList statusAL = new ArrayList();
        while (rs.next()) {
            String status = rs.getString("invoice_status_cd");
            if(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED.equals(status)) {
                invId = rs.getInt("invoice_dist_id");
            } else {
                statusAL.add(status);
                continue;
            }

            String invNum = rs.getString("INVOICE_NUM");
            ReportItem invNumRI = ReportItem.createValue("INVOICE_NUM",invNum);
            invoiceRI.addChild(invNumRI);

            Date invDate = rs.getDate("INVOICE_DATE");
            ReportItem invDateRI = ReportItem.createValue("INVOICE_DATE",sdf.format(invDate));
            invoiceRI.addChild(invDateRI);

            String poNum = rs.getString("PO_NUMBER");
            ReportItem poNumRI = ReportItem.createValue("PO_NUMBER",poNum);
            invoiceRI.addChild(poNumRI);

            String orderNum = rs.getString("ORDER_NUM");
            ReportItem orderNumRI = ReportItem.createValue("ORDER_NUM",orderNum);
            invoiceRI.addChild(orderNumRI);

            String distPoNum = rs.getString("DIST_PO_NUMBER");
            ReportItem distPoNumRI = ReportItem.createValue("DIST_PO_NUMBER",distPoNum);
            invoiceRI.addChild(distPoNumRI);

            BigDecimal subTotal = rs.getBigDecimal("SUB_TOTAL");
            if(subTotal!=null) subTotal = subTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
            String subTotalS = (subTotal==null)? null:subTotal.toString();
            ReportItem subTotalRI = ReportItem.createValue("SUB_TOTAL",subTotalS);
            invoiceRI.addChild(subTotalRI);

            BigDecimal freight = rs.getBigDecimal("FREIGHT");
            if(freight!=null) freight = freight.setScale(2,BigDecimal.ROUND_HALF_UP);
            String freightS = (freight==null)? null:freight.toString();
            ReportItem freightRI = ReportItem.createValue("FREIGHT",freightS);
            invoiceRI.addChild(freightRI);

            BigDecimal miscCharges = rs.getBigDecimal("MISC_CHARGES");
            if(miscCharges!=null) miscCharges = miscCharges.setScale(2,BigDecimal.ROUND_HALF_UP);
            String miscChargesS = (miscCharges==null)? null:miscCharges.toString();
            ReportItem miscChargesRI = ReportItem.createValue("HANDLING",miscChargesS);
            invoiceRI.addChild(miscChargesRI);

            BigDecimal tax = rs.getBigDecimal("SALES_TAX");
            if(tax!=null) tax = tax.setScale(2,BigDecimal.ROUND_HALF_UP);
            String taxS = (tax==null)? null:tax.toString();
            ReportItem taxRI = ReportItem.createValue("SALES_TAX",taxS);
            invoiceRI.addChild(taxRI);

            String jobNumber = rs.getString("JOB_NUMBER");
            ReportItem jobNumberRI = ReportItem.createValue("JOB_NUMBER",jobNumber);
            invoiceRI.addChild(jobNumberRI);

            break;
        }
        rs.close();
        stmt.close();

        if(invId==0) {
            if(statusAL.isEmpty()) {
                String errorMess = "^clw^No Invoice found. Inv number = "+pInvoiceNum+"^clw^";
                throw new Exception(errorMess);
            } else {
                String errorMess = "^clw^No processed invoice found. Inv number = "+pInvoiceNum+
                        " Found invoices with status: ";
                for(int ii=0; ii<statusAL.size(); ii++) {
                    if(ii>0) errorMess += ", ";
                    errorMess += statusAL.get(ii);
                }
                errorMess +="^clw^";
                throw new Exception(errorMess);
            }
        }

        String invoiceLineSql =
           "select " +
           " dist_line_number LINE_NUMBER, "+
           " dist_item_sku_num SKU_NUM, "+
           " dist_item_short_desc ITEM_NAME, "+
           " dist_item_uom UOM, "+
           " dist_item_pack PACK, "+
           " dist_item_quantity QTY, "+
           " adjusted_cost COST, "+
           " dist_item_quantity*adjusted_cost LINE_TOTAL "+
           " from clw_invoice_dist_detail idd "+
           " where idd.invoice_dist_id =  "+invId;

        stmt = con.prepareStatement(invoiceLineSql);
        rs = stmt.executeQuery();

        ReportItem itemsRI = ReportItem.createValue("Items");
        invoiceRI.addChild(itemsRI);

        while (rs.next()) {
            ReportItem itemsLineRI = ReportItem.createValue("Items_line");
            itemsRI.addChild(itemsLineRI);

            int lineNum = rs.getInt("LINE_NUMBER");
            ReportItem lineNumRI = ReportItem.createValue("LINE_NUMBER",lineNum);
            itemsLineRI.addChild(lineNumRI);

            String skuNum = rs.getString("SKU_NUM");
            ReportItem skuNumRI = ReportItem.createValue("SKU_NUM",skuNum);
            itemsLineRI.addChild(skuNumRI);

            String itemName = rs.getString("ITEM_NAME");
            ReportItem itemNameRI = ReportItem.createValue("ITEM_NAME",itemName);
            itemsLineRI.addChild(itemNameRI);

            String uom = rs.getString("UOM");
            ReportItem uomRI = ReportItem.createValue("UOM",uom);
            itemsLineRI.addChild(uomRI);

            String pack = rs.getString("PACK");
            ReportItem packRI = ReportItem.createValue("PACK",pack);
            itemsLineRI.addChild(packRI);

            int qty = rs.getInt("QTY");
            ReportItem qtyRI = ReportItem.createValue("QTY",qty);
            itemsLineRI.addChild(qtyRI);

            BigDecimal cost = rs.getBigDecimal("COST");
            if(cost!=null) cost = cost.setScale(2,BigDecimal.ROUND_HALF_UP);
            String costS = (cost==null)? null:cost.toString();
            ReportItem costRI = ReportItem.createValue("COST",costS);
            itemsLineRI.addChild(costRI);

            BigDecimal lineTotal = rs.getBigDecimal("LINE_TOTAL");
            if(lineTotal!=null) lineTotal = lineTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
            String lineTotalS = (lineTotal==null)? null:lineTotal.toString();
            ReportItem lineTotalRI = ReportItem.createValue("LINE_TOTAL",lineTotalS);
            itemsLineRI.addChild(lineTotalRI);
        }
        rs.close();
        stmt.close();

        return rootRI;

    }
}
