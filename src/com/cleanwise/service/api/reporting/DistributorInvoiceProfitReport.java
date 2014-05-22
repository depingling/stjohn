/*
 * DistributorInvoiceProfitReport.java
 *
 * Created on February 3, 2003, 4:43 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;

import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;

/**
 * This report seems to have been removed from the view layer, thus this class was not added to 
 * the view layer
 * Picks up distributor a invoices and agreates it on invoice level
 * @param pBeginDate start of the period.
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class DistributorInvoiceProfitReport implements GenericReport {
    
    /** Creates a new instance of DistributorInvoiceProfitReport */
    public DistributorInvoiceProfitReport() {
    }
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);

        //format the dates from one string type to anouther
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date bDte = df.parse((String) pParams.get("BEG_DATE"));
        Date eDte = df.parse((String) pParams.get("END_DATE"));
        df = new SimpleDateFormat("dd-MMM,yyyy");
        String startDateS = df.format(bDte);
        String endDateS = df.format(eDte);
        
        DistInvoiceViewVector invoices = new DistInvoiceViewVector();
        GenericReportResultView result = GenericReportResultView.createValue();
        try {
            //Get active vendors
            String  sql =
            "select distinct vendor from law_purchorder where trim(vendor) is not null";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String vendorNums = "";
            ArrayList vendorNumList = new ArrayList();
            while (rs.next() ) {
                if(vendorNums.length()>0) vendorNums += ",";
                vendorNums += "?";
                vendorNumList.add(rs.getString(1));
                //vendorNums += "'"+rs.getString(1)+"'";
            }
            stmt.close();
            
            //Get vendor invoices
            String venInvSql =
            "select "+
            "co.customer, co.ship_to, "+
            "trim(co.ship_to_addr1)||' '||trim(co.ship_to_addr2) as ship_to_addr12, "+
            "api.vendor, api.invoice, api.invoice_dte, "+
            "api.base_tot_dist, api.tran_tot_tax, "+
            "api.po_num,poa.item, poa.tot_base_amt line_amt, poa.approve_qty, "+
            "col.sell_prc_curr item_price, col.unit_cost item_cost, "+
            "pol.order_nbr, co.order_date "+
            "from law_apinvoice api, law_poapprove poa, law_poline pol, "+
            "law_coline col, law_custorder co "+
            "where poa.invoice(+) = api.invoice "+
            "and poa.vendor(+) = api.vendor "+
            "and api.cancel_seq = 0 "+
            "and poa.r_status(+)!=8 "+
            "and poa.po_number = pol.po_number(+) "+
            "and poa.item = pol.item(+) "+
            "and pol.cancelled_fl(+)='N' "+
            "and col.item(+) = pol.item "+
            "and col.order_nbr(+) = pol.order_nbr "+
            "and col.r_status(+)!=8 "+
            "and co.order_nbr(+) = pol.order_nbr "+
            "and api.invoice_dte between '"+startDateS+"' and '"+endDateS+"' "+
            "and api.vendor in ("+vendorNums+") "+
            "order by -sign(nvl(order_nbr,0)), invoice_dte,vendor,invoice,item ";

            PreparedStatement pstmt = con.prepareStatement(venInvSql);
            for (int k=0; k<vendorNumList.size(); k++) {
                int c = k+1;
                pstmt.setString(c, (String)vendorNumList.get(k));
            }

            ResultSet venInvcRS = pstmt.executeQuery();
            String prevVendor = "000";
            String prevVenInvoice = "";
            BigDecimal goodsCost = new BigDecimal(0);
            BigDecimal goodsPrice = new BigDecimal(0);
            String itemList = "";
            DistInvoiceView div = null;
            int venInd = -1;
            while (venInvcRS.next() ) {
                venInd++;
                String vendor =   venInvcRS.getString("vendor").trim();
                String venInvoice = venInvcRS.getString("invoice");
                if(!prevVendor.equals(vendor) || !prevVenInvoice.equals(venInvoice)){
                    if(div!=null) {
                        div.setVenGoodsCost(goodsCost);
                        if(goodsCost!=null) {
                            div.setVenAdditionalCharges(div.getVenTotalCost().subtract(goodsCost));
                        } else {
                            div.setVenAdditionalCharges(null);
                        }
                        div.setOrderGoodsPrice(goodsPrice);
                        div.setMarginPr(null);
                        div.setMargin(null);
                        if(goodsCost!=null && goodsPrice!=null) {
                            BigDecimal margin = goodsPrice.subtract(goodsCost);
                            div.setMargin(goodsPrice.subtract(goodsCost));
                            if(goodsPrice.abs().compareTo(new BigDecimal(0.0001))>0){
                                div.setMarginPr(margin.divide(goodsPrice,4,BigDecimal.ROUND_HALF_UP));
                            }
                        }
                        div.setVenItems(itemList);
                        invoices.add(div);
                        goodsCost = new BigDecimal(0);
                        goodsPrice = new BigDecimal(0);
                        itemList = "";
                    }
                    div = DistInvoiceView.createValue();
                    String customer = venInvcRS.getString("customer");
                    int shipTo = venInvcRS.getInt("ship_to");
                    String shipToAdr = venInvcRS.getString("ship_to_addr12");
                    String poNumber = venInvcRS.getString("po_num");
                    int orderNbr = venInvcRS.getInt("order_nbr");
                    Date orderDate = venInvcRS.getDate("order_date");
                    BigDecimal venTotal = venInvcRS.getBigDecimal("base_tot_dist");
                    BigDecimal venTax = venInvcRS.getBigDecimal("tran_tot_tax");
                    Date venInvoiceDate = venInvcRS.getDate("invoice_dte");
                    prevVendor = vendor;
                    prevVenInvoice = venInvoice;
                    div.setVenInvoiceNum(venInvoice);
                    div.setVenInvoiceDate(venInvoiceDate);
                    div.setVenTotalCost(venTotal);
                    div.setVenTax(venTax);
                    div.setShipTo(shipTo);
                    div.setShipToAddr(shipToAdr);
                    div.setPoNumber(poNumber);
                    div.setOrderNbr(orderNbr);
                    div.setCustomer(customer);
                    div.setVendor(vendor);
                    div.setOrderDate(orderDate);
                }
                String venItem = venInvcRS.getString("item");
                if(venItem!=null) venItem = venItem.trim();
                int venItemQty = venInvcRS.getInt("approve_qty");
                BigDecimal venItemCost = venInvcRS.getBigDecimal("item_cost");
                BigDecimal orderItemPrice = venInvcRS.getBigDecimal("item_price");
                BigDecimal venLineAmt = venInvcRS.getBigDecimal("line_amt");
                if(venItem!=null) {
                    if(itemList.length()>0) {
                        itemList += ", ";
                    }
                    itemList += venItem +"("+venItemQty+")";
                }
                if(venLineAmt!=null && goodsCost!=null) {
                    goodsCost = goodsCost.add(venLineAmt);
                } else {
                    goodsCost = null;
                }
                if(orderItemPrice!=null && goodsPrice!=null) {
                    goodsPrice = goodsPrice.add(orderItemPrice.multiply(new BigDecimal(venItemQty)));
                }
            }
            stmt.close();
            if(div!=null) {
                div.setVenGoodsCost(goodsCost);
                if(goodsCost!=null) {
                    div.setVenAdditionalCharges(div.getVenTotalCost().subtract(goodsCost));
                } else {
                    div.setVenAdditionalCharges(null);
                }
                div.setOrderGoodsPrice(goodsPrice);
                div.setMarginPr(null);
                div.setMargin(null);
                if(goodsCost!=null && goodsPrice!=null) {
                    BigDecimal margin = goodsPrice.subtract(goodsCost);
                    div.setMargin(goodsPrice.subtract(goodsCost));
                    if(goodsPrice.abs().compareTo(new BigDecimal(0.0001))>0){
                        div.setMarginPr(margin.divide(goodsPrice,4,BigDecimal.ROUND_HALF_UP));
                    }
                }
                div.setVenItems(itemList);
                invoices.add(div);
            }
        }
        catch (SQLException exc) {
            errorMess = "Error. Report.distInvoiceProfit() SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
            errorMess = "Error. Report.distInvoiceProfit() Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        //convert the invoices to and ArrayList (Necessary for the conversion to the
        //generic reporting framwork)
        result.setTable(new ArrayList());
        for(int i=0;i<invoices.size();i++){
            ArrayList row = new ArrayList();
            DistInvoiceView invoice = (DistInvoiceView) invoices.get(i);
            row.add(invoice.getCustomer());
            row.add(invoice.getCustName());
            row.add(new Integer(invoice.getShipTo()));
            row.add(invoice.getShipToAddr());
            row.add(new Integer(invoice.getOrderNbr()));
            row.add(invoice.getOrderDate());
            row.add(invoice.getVendor());
            row.add(invoice.getVenName());
            row.add(invoice.getPoNumber());
            row.add(invoice.getVenInvoiceNum());
            row.add(invoice.getVenInvoiceDate());
            row.add(invoice.getVenTotalCost());
            row.add(invoice.getVenAdditionalCharges());
            row.add(invoice.getVenTax());
            row.add(invoice.getVenItems());
            row.add(invoice.getVenGoodsCost());
            row.add(invoice.getOrderGoodsPrice());
            row.add(invoice.getMargin());
            row.add(invoice.getMarginPr());
            result.getTable().add(row);
        }
        result.setHeader(getReportHeader());
        result.setColumnCount(getReportHeader().size());
        return result;
    }
    
    //We can't just use the meta data here because of all the various sub-queries that
    //take place.
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship To",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship To Addr",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Po Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Invoice Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Ven Invoice Date",0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Total Cost", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Additional Charges", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Tax", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Goods Cost", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Goods Price", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Goods Margin $", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Goods Margin %", 2, 20, "NUMBER"));
        return header;
    }
}
