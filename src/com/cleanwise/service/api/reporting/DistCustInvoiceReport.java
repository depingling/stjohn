/*
 * InvoiceProfitReport.java
 *
 * Created on February 3, 2003, 10:12 PM
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
import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
/**
 * Picks up distributor and customer invoices and agreates it on order level based on customer invoices
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class DistCustInvoiceReport implements GenericReportMulti {
    Map orderTotalInfo = new java.util.HashMap();
    /** Creates a new instance of InvoiceProfitReport */
    public DistCustInvoiceReport() {
    }
    
    String vendorNums = ""    , startDateS = "",
     endDateS = "";
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getMainConnection();
        //Connection lawCon = pCons.getLawsonConnection();
        GenericReportResultView result = GenericReportResultView.createValue();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);
        
        //format the dates from one string type to anouther
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date pStartDate = df.parse((String) pParams.get("BEG_DATE"));
        Date pEndDate = df.parse((String) pParams.get("END_DATE"));
        df = new SimpleDateFormat("dd-MMM,yyyy");
        startDateS = df.format(pStartDate);
        endDateS = df.format(pEndDate);
        
        DistCustInvoiceViewVector invoices = new DistCustInvoiceViewVector();
        try {
            //Get all Customer Invoices
            String allCustInvSql =
            "select "+
            "nvl(co.order_nbr,0) order_nbr,"+
            "co.order_date,"+
            "oei.customer,"+
            "oei.invc_prefix,"+
            "oei.invc_number,"+
            "invoice_date,"+
            "invc_type,"+
            "invc_source,"+
            "non_inv_gds_b+misc_tot_base+frt_charge_b total_price,"+
            "non_inv_gds_b goods,"+
            "misc_tot_base+frt_charge_b misc,"+
            "trim(oeil.item) as item,"+
            "nvl(oeil.quantity,0) qty,"+
            "tax_total_bse tax_charge, "+
            "oeil.unit_price item_price, "+
            "oeil.line_grs_base line_amt, "+
            "oei.ship_to, "+
            "trim(nvl(co.ship_to_addr1,st.addr1))||' '||trim(nvl(co.ship_to_addr2,st.addr2)) as ship_to_addr12 "+
            "from law_oeinvoice oei, law_oeinvcline oeil, law_custorder co, law_shipto st "+
            "where oei.r_status!=8 "+
            "and oei.invc_prefix = 'IN' "+ // !!!!!!!!! Paula's request        
            "and oeil.invc_number(+) = oei.invc_number "+
            "and oeil.invc_prefix(+) = oei.invc_prefix "+
            "and co.order_nbr(+) = oei.order_nbr "+
            "and co.order_nbr(+) != 0 "+
            "and st.customer(+) = oei.customer "+
            "and st.ship_to(+) = oei.ship_to "+
            "and oei.order_nbr in ("+
            "select order_nbr from law_oeinvoice where "+
            " invoice_date between '"+startDateS+"' and '"+endDateS+"' )"+
            "order by order_nbr, invoice_date, invc_prefix, invc_number,item";
            
            Statement stmt = con.createStatement();
            ResultSet custInvcRS = stmt.executeQuery(allCustInvSql);
            //Create customer invoice data
            int count1 = 0;
            CustInvoiceItemViewVector ciiV = new CustInvoiceItemViewVector();
            DistCustInvoiceViewVector custDcis = new DistCustInvoiceViewVector();
            DistCustInvoiceView dci = null;
            String prevPrefix = "";
            String curPrefix = null;
            int prevOrder =-1;
            int prevInvcNum = 0;
            int curInvcNum = 0;
            String custItems = "";
            int custInd = -1;
            while ( custInvcRS.next() ) {
                custInd++;
                CustInvoiceItemView cii = CustInvoiceItemView.createValue();
                cii.setCustomer(custInvcRS.getString("Customer"));
                cii.setOrderNbr(custInvcRS.getInt("Order_Nbr"));
                cii.setCustInvoicePrefix(custInvcRS.getString("Invc_Prefix"));
                cii.setCustInvoiceNum(custInvcRS.getBigDecimal("invc_number"));
                cii.setItem(custInvcRS.getString("Item"));
                cii.setLineAmt(custInvcRS.getBigDecimal("line_amt"));
                cii.setItemPrice(custInvcRS.getBigDecimal("item_price"));
                cii.setQty(custInvcRS.getInt("QTY"));
                cii.setResidual(custInvcRS.getInt("QTY"));
                int shipTo = custInvcRS.getInt("ship_to");
                String shipToAddr = custInvcRS.getString("ship_to_addr12");
                ciiV.add(cii);
                
                if(!prevPrefix.equals(cii.getCustInvoicePrefix()) ||
                prevInvcNum != cii.getCustInvoiceNum().intValue()) {
                    prevPrefix = cii.getCustInvoicePrefix();
                    prevInvcNum = cii.getCustInvoiceNum().intValue();
                    if(dci!=null) {
                        dci.setCustItems(custItems);
                        dci.setCustItemInd2(custInd-1);
                        custDcis.add(dci);
                    }
                    custItems = new String("");
                    dci = DistCustInvoiceView.createValue();
                    dci.setCustomer(cii.getCustomer());
                    dci.setOrderNbr(cii.getOrderNbr());
                    dci.setOrderDate(custInvcRS.getDate("Order_Date"));
                    dci.setCustInvoicePrefix(cii.getCustInvoicePrefix());
                    dci.setCustInvoiceNum(cii.getCustInvoiceNum());
                    dci.setCustTotalPrice(custInvcRS.getBigDecimal("total_price"));
                    dci.setCustGoods(custInvcRS.getBigDecimal("Goods"));
                    dci.setCustMisc(custInvcRS.getBigDecimal("Misc"));
                    dci.setCustInvoiceDate(custInvcRS.getDate("invoice_date"));
                    dci.setCustInvoiceType(custInvcRS.getString("invc_type"));
                    dci.setCustInvoiceSource(custInvcRS.getString("invc_source"));
                    dci.setShipTo(shipTo);
                    dci.setShipToAddr(shipToAddr);
                    dci.setCustItemInd1(custInd);
                }
                String item = cii.getItem();
                if(item != null) {
                    if(custItems.length()>0) custItems+=", ";
                    custItems += cii.getItem()+"("+cii.getQty()+")";
                }
            }
            if(dci == null){
                dci = DistCustInvoiceView.createValue();
            }
            dci.setCustItems(custItems);
            dci.setCustItemInd2(custInd);
            custDcis.add(dci);
            stmt.close();
            
            //Get vendor invoices
            String tiedVenInvSql =
            "select"+
            "  poa.vendor, poa.invoice, poa.po_number, poa.item, poa.approve_qty, a.order_nbr, "+
            "  api.invoice_dte,api.tran_inv_amt, api.tran_tot_tax, (poa.approve_qty * poa.ent_unit_cst) AS line_amt, "+
            "  ent_unit_cst item_cost "+
            "from "+
            "(select distinct order_nbr,po_number from law_poline where order_nbr in ("+
            "select distinct order_nbr "+
            "from law_oeinvoice"+
            " where r_status!=8"+
            "   and order_nbr!=0"+
            "   and invc_prefix = 'IN'"+  //!!!!!!!!!! Paula's request  
            "   and invoice_date between '"+startDateS+"' and '"+endDateS+"')) a,"+
            "   law_apinvoice api,law_poapprove poa "+
            " where a.po_number = poa.po_number "+
            "  and api.vendor = poa.vendor "+
            "  and api.invoice = poa.invoice "+
            "  and api.base_inv_amt >= 0 " +  //!!!!!!!!!! Paula's request         
            "order by a.order_nbr,api.invoice_dte,vendor,invoice,item ";
            
            stmt = con.createStatement();
            ResultSet tiedVenInvcRS = stmt.executeQuery(tiedVenInvSql);
            
            String prevVendor = "000";
            String prevVenInvoice = "";
            dci = null;
            BigDecimal venGoods = null;
            String venItems = null;
            DistCustInvoiceViewVector venDcis = new DistCustInvoiceViewVector();
            VenInvoiceItemViewVector viiV = new VenInvoiceItemViewVector();
            int venInd = -1;
            while ( tiedVenInvcRS.next() ) {
                venInd++;
                String vendor =   tiedVenInvcRS.getString("vendor").trim();
                String venInvoice = tiedVenInvcRS.getString("invoice");
                String poNumber = tiedVenInvcRS.getString("po_number");
                String venItem = tiedVenInvcRS.getString("item").trim();
                int venItemQty = tiedVenInvcRS.getInt("approve_qty");
                int order = tiedVenInvcRS.getInt("order_nbr");
                BigDecimal venTotal = tiedVenInvcRS.getBigDecimal("tran_inv_amt");
                BigDecimal venTax = tiedVenInvcRS.getBigDecimal("tran_tot_tax");
                BigDecimal venLineAmt = tiedVenInvcRS.getBigDecimal("line_amt");
                BigDecimal venItemCost = tiedVenInvcRS.getBigDecimal("item_cost");
                Date venInvoiceDate = tiedVenInvcRS.getDate("invoice_dte");
                VenInvoiceItemView vii = VenInvoiceItemView.createValue();
                vii.setOrderNbr(order);
                vii.setVendor(vendor);
                vii.setVenInvoiceNum(venInvoice);
                vii.setItem(venItem);
                vii.setQty(venItemQty);
                vii.setResidual(venItemQty);
                vii.setLineAmt(venLineAmt);
                vii.setItemCost(venItemCost);
                viiV.add(vii);
                if(!prevVendor.equals(vendor) || 
                		!prevVenInvoice.equals(venInvoice)){
                    prevVendor = vendor;
                    prevVenInvoice = venInvoice;
                    if(dci!=null) {
                        dci.setVenGoodsCost(venGoods);
                        dci.setVenAdditionalCharges(dci.getVenTotalCost().subtract(venGoods));
                        dci.setVenItems(venItems);
                        dci.setVenItemInd2(venInd-1);
                        venDcis.add(dci);
                    }
                    venGoods = new BigDecimal(0).add(venLineAmt);
                    venItems = new String(venItem) + "("+venItemQty+")";
                    dci = DistCustInvoiceView.createValue();
                    dci.setOrderNbr(order);
                    dci.setVendor(vendor);
                    dci.setVenInvoiceNum(venInvoice);
                    dci.setVenInvoiceDate(venInvoiceDate);
                    dci.setPoNumber(poNumber);
                    dci.setVenTotalCost(venTotal);
                    dci.setVenTax(venTax);
                    dci.setVenItemInd1(venInd);
                } else {
                    venGoods = venGoods.add(venLineAmt);
                    venItems += ", "+venItem + "("+venItemQty+")";
                }
            }
            if(dci!=null){
                dci.setVenGoodsCost(venGoods);
                if(dci.getVenTotalCost() != null){
                    dci.setVenAdditionalCharges(dci.getVenTotalCost().subtract(venGoods));
                }
                dci.setVenItemInd2(venInd);
                dci.setVenItems(venItems);
                venDcis.add(dci);
            }
            stmt.close();
            
            prevOrder = -1;
            //Now the main thing: match invoices
            DistCustInvoiceViewVector orderCustDci = null;
            DistCustInvoiceViewVector orderVenDci = null;
            ReportingUtils rutil = new ReportingUtils();
            for(int ii=0, jj=0; ii<custDcis.size(); ii++) {
                DistCustInvoiceView custDci = (DistCustInvoiceView) custDcis.get(ii);
                int custOrder = custDci.getOrderNbr();
                if(custOrder==0) {
                    
                    if(custDci.getCustInvoiceDate()!=null && !custDci.getCustInvoiceDate().before(pStartDate) &&
                    !custDci.getCustInvoiceDate().after(pEndDate)) {
                        invoices.add(custDci);
                    }
                    continue;
                }
                if(prevOrder!=custOrder) {
                    if(prevOrder>0) {
                        DistCustInvoiceViewVector orderDci =
                        rutil.invoicesMatch(orderCustDci,ciiV, orderVenDci,viiV,pStartDate,pEndDate);
                        invoices.addAll(orderDci);
                        
                    }
                    orderCustDci = new DistCustInvoiceViewVector();
                    orderVenDci =  new DistCustInvoiceViewVector();
                    prevOrder = custOrder;
                }
                orderCustDci.add(custDci);
                for(; jj<venDcis.size(); jj++) {
                    DistCustInvoiceView venDci = (DistCustInvoiceView) venDcis.get(jj);
                    int venOrder = venDci.getOrderNbr();
                    if(custOrder<venOrder) {
                        break;
                    }
                    if(custOrder>venOrder) {
                        continue; //We should not have any vendor invoice if it does not mathch to custormer invoice
                    }
                    orderVenDci.add(venDci);
                }
            }
            if(orderCustDci==null) orderCustDci = new DistCustInvoiceViewVector();
            if(orderVenDci==null) orderVenDci = new DistCustInvoiceViewVector();
            ReportingUtils util = new ReportingUtils();
            DistCustInvoiceViewVector orderDci =
            util.invoicesMatch(orderCustDci,ciiV, orderVenDci,viiV,pStartDate, pEndDate);
            invoices.addAll(orderDci);
            
            DistCustInvoiceViewVector glExtraVector = new DistCustInvoiceViewVector();
            //Accout 5030
            /*
            String  sql =
            " select gl_date, invc_prefix, invc_number, account, sum(amount_base) amount_base "+
            " from OEDIST oed "+
            " where oed.account = 5030  "+
            " and oed.gl_date between '"+startDateS+"' and '"+endDateS+"' "+
            " group by gl_date, invc_prefix, invc_number, account ";


            stmt = lawCon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next() ) {
                Date glDate = rs.getDate("gl_date");
                String invcPrefix = rs.getString("invc_prefix");
                int invcNumber = rs.getInt("invc_number");
                BigDecimal amt5030 = rs.getBigDecimal("amount_base");
                int ii=0;
                for(; ii<invoices.size(); ii++) {
                    DistCustInvoiceView dciV = (DistCustInvoiceView) invoices.get(ii);
                    String prf = dciV.getCustInvoicePrefix();
                    BigDecimal bd = dciV.getCustInvoiceNum();
                    if(prf!=null && bd!=null) {
                        int num = bd.intValue();
                        if(invcNumber==num && invcPrefix.equals(prf)) {
                            dciV.setAmt5030(amt5030);
                            break;
                        }
                    }
                }
                if(ii==invoices.size()) {
                    DistCustInvoiceView dciV = DistCustInvoiceView.createValue();
                    dciV.setCustInvoicePrefix(invcPrefix);
                    dciV.setCustInvoiceNum(new BigDecimal(invcNumber));
                    dciV.setAmt5030(amt5030);
                    glExtraVector.add(dciV);
                }
            }
            rs.close();
            stmt.close();
            
            */
            //Unmatched vendor invoices
            
            //Get active vendors
            String sql =
            "select distinct vendor from law_purchorder where trim(vendor) is not null";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next() ) {
                if(vendorNums.length()>0) vendorNums += ",";
                vendorNums += "'"+rs.getString(1)+"'";
            }
            
            //No Po invoices (
            sql=
            "select api.vendor, api.invoice, api.invoice_dte,"+
            " tran_inv_amt,"+
            " sum(poa.approve_qty * poa.ent_unit_cst) goods_cost,"+
            " api.tran_inv_amt-sum(poa.approve_qty * poa.ent_unit_cst)-sum(poa.tax_amount) additional_charges,"+
            " sum(poa.tax_amount) tax "+
            "  from law_apinvoice api, law_poapprove poa "+
            " where  "+
            "    trim(api.po_num) is  null "+
            "and api.vendor = poa.vendor(+) "+
            "and api.invoice = poa.invoice(+) "+
            "and api.base_inv_amt >= 0 " +  //!!!!!!!!!! Paula's request                             
            "and api.vendor in ("+vendorNums+") "+
            "and invoice_dte between '"+startDateS+"' and '"+endDateS+"' "+
            "group by api.vendor, api.invoice, api.invoice_dte, api.tran_inv_amt ";


            rs = stmt.executeQuery(sql);
            while (rs.next() ) {
                dci = DistCustInvoiceView.createValue();
                dci.setVendor(rs.getString("vendor"));
                dci.setVenInvoiceNum(rs.getString("invoice"));
                dci.setVenInvoiceDate(rs.getDate("invoice_dte"));
                dci.setCustInvoiceDate(rs.getDate("invoice_dte"));
                dci.setVenTotalCost(rs.getBigDecimal("tran_inv_amt"));
                dci.setVenGoodsCost(rs.getBigDecimal("goods_cost"));
                dci.setVenAdditionalCharges(rs.getBigDecimal("additional_charges"));
                dci.setVenTax(rs.getBigDecimal("tax"));
                invoices.add(dci);
            }
            
            //Bogus po invoices
            sql =
            "select a.vendor, a.invoice, a.invoice_dte, a.po_num,"+
            " a.tran_inv_amt,"+
            " sum(poa.approve_qty * poa.ent_unit_cst) goods_cost,"+
            " a.tran_inv_amt-sum(poa.approve_qty * poa.ent_unit_cst)-sum(poa.tax_amount) additional_charges,"+
            " sum(poa.tax_amount) tax "+
            "from ("+
            " select api.*, pol.po_number"+
            " from law_apinvoice api, law_poline pol "+
            " where "+
            "    trim(api.po_num) is not null "+
            "  and api.base_inv_amt >= 0 " +  //!!!!!!!!!! Paula's request           
            "    and api.po_num = pol.po_number(+) "+
            "	and api.vendor in ("+vendorNums+") "+
            ") a, law_poapprove poa "+
            "where a.po_number is null "+
            "   and a.vendor = poa.vendor(+) "+
            "   and a.invoice = poa.invoice(+) "+
            "   and invoice_dte between '"+startDateS+"' and '"+endDateS+"' "+
            "   group by a.vendor, a.invoice, a.tran_inv_amt, a.invoice_dte, a.po_num";
            
            rs = stmt.executeQuery(sql);
            while (rs.next() ) {
                dci = DistCustInvoiceView.createValue();
                dci.setVendor(rs.getString("vendor"));
                dci.setPoNumber(rs.getString("po_num"));
                dci.setVenInvoiceNum(rs.getString("invoice"));
                dci.setVenInvoiceDate(rs.getDate("invoice_dte"));
                dci.setCustInvoiceDate(rs.getDate("invoice_dte"));
                dci.setVenTotalCost(rs.getBigDecimal("tran_inv_amt"));
                dci.setVenGoodsCost(rs.getBigDecimal("goods_cost"));
                dci.setVenAdditionalCharges(rs.getBigDecimal("additional_charges"));
                dci.setVenTax(rs.getBigDecimal("tax"));
                invoices.add(dci);
            }
            
            //Vendor invoices with 0 order number
            sql =
            "select a.vendor, a.invoice, a.invoice_dte, a.po_num,"+
            " tran_inv_amt,"+
            " sum(poa.approve_qty * poa.ent_unit_cst) goods_cost,"+
            " a.tran_inv_amt-sum(poa.approve_qty * poa.ent_unit_cst)-sum(poa.tax_amount) additional_charges,"+
            " sum(poa.tax_amount) tax "+
            "from "+
            "(select distinct api.vendor, api.po_num, invoice, invoice_dte, api.tran_inv_amt "+
            "  from law_apinvoice api, law_poline pol "+
            " where "+
            "    trim(api.po_num) is not null "+
            "  and api.base_inv_amt >= 0 " +  //!!!!!!!!!! Paula's request                             
            "    and api.po_num = pol.po_number "+
            "	and nvl(pol.order_nbr,0) = 0 "+
            "	and pol.cancelled_fl != 'Y' "+
            "	and api.vendor in ("+vendorNums+")) a, law_poapprove poa "+
            "where a.vendor = poa.vendor(+) "+
            "  and a.invoice = poa.invoice(+) "+
            "  and invoice_dte between '"+startDateS+"' and '"+endDateS+"' "+
            "  group by a.vendor, a.invoice, tran_inv_amt, invoice_dte, a.po_num ";
            
            rs = stmt.executeQuery(sql);
            while (rs.next() ) {
                dci = DistCustInvoiceView.createValue();
                dci.setVendor(rs.getString("vendor"));
                dci.setPoNumber(rs.getString("po_num"));
                dci.setVenInvoiceNum(rs.getString("invoice"));
                dci.setVenInvoiceDate(rs.getDate("invoice_dte"));
                dci.setCustInvoiceDate(rs.getDate("invoice_dte"));
                dci.setVenTotalCost(rs.getBigDecimal("tran_inv_amt"));
                dci.setVenGoodsCost(rs.getBigDecimal("goods_cost"));
                dci.setVenAdditionalCharges(rs.getBigDecimal("additional_charges"));
                dci.setVenTax(rs.getBigDecimal("tax"));
                invoices.add(dci);
            }
            //Add extra 5030 amounts
            invoices.addAll(glExtraVector);
            
        }
        catch (SQLException exc) {
            errorMess = "Error. Report.invoiceProfit() SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }


        //***********FROM LOGIC SERVLET*************************************
        ArrayList custNumAL = new ArrayList();
        ArrayList custNameAL = new ArrayList();
        //int prevOrderNbr = -1;
        BigDecimal orderCustTotal = new BigDecimal(0);
        BigDecimal orderVenTotal = new BigDecimal(0);
        int orderNbr = 0;
        String prevCustomer = "";
        String custName = "";
        String prevVendor = "";
        String venName = "";
        int workingOrderNbr = -1;
        for(int ii=invoices.size()-1; ii>=0; ii--) {
            
            DistCustInvoiceView  orderProfit = (DistCustInvoiceView) invoices.get(ii);
            orderNbr = orderProfit.getOrderNbr();
            if(orderNbr>0 && workingOrderNbr != orderNbr) {
                BigDecimal orderProfitAmt = orderCustTotal.subtract(orderVenTotal);
                BigDecimal orderPercentProfitAmt = null;
                if(orderCustTotal.compareTo(new BigDecimal(0.001))>0) {
                    orderPercentProfitAmt =
                    orderProfitAmt.divide(orderCustTotal,4,BigDecimal.ROUND_HALF_UP);
                }
                Object[] array = {orderCustTotal,orderVenTotal,orderProfitAmt,orderPercentProfitAmt};
                orderTotalInfo.put(new Integer(workingOrderNbr),array);
                orderVenTotal = zeroAmt;
                orderCustTotal = zeroAmt;
                workingOrderNbr = orderNbr;
            }
            BigDecimal ctp = orderProfit.getCustTotalPrice();
            if(ctp!=null) {
                orderCustTotal = orderCustTotal.add(ctp);
            }
            BigDecimal vtc = orderProfit.getVenTotalCost();
            if(vtc!=null) {
                orderVenTotal = orderVenTotal.add(vtc);
            }
            String customer = orderProfit.getCustomer();
            if(customer!=null) customer = customer.trim(); else customer = "";
            if(!prevCustomer.equals(customer) || ii==invoices.size()-1) {
                custName = getName(con, customer);
                prevCustomer = customer;
                int jj=0;
                for(; jj<custNumAL.size(); jj++) {
                  String ss = (String) custNumAL.get(jj);
                  if(ss.equals(customer)) break;
                }
                if(jj==custNumAL.size()) {
                  custNumAL.add(customer);
                  custNameAL.add(custName);
                }
            }
            
            BigDecimal amt5030 = orderProfit.getAmt5030();
            
            String vendor = orderProfit.getVendor();
            if(vendor!=null) vendor = vendor.trim();
            if(!prevVendor.equals(vendor)) {
                venName = getName(con, vendor);
                prevVendor = vendor;
            }
            
            
            orderProfit.setVenName(getName(con, vendor));
            orderProfit.setCustName(getName(con, customer));
            
            //partial amounts
            BigDecimal custCommonPrice = orderProfit.getCustCommonPrice();
            BigDecimal venCommonCost = orderProfit.getVenCommonCost();
            if(custCommonPrice!=null && venCommonCost!=null) {
                BigDecimal commDiff = custCommonPrice.subtract(venCommonCost);
                orderProfit.setPartialDiff(commDiff);
                if(custCommonPrice.compareTo(new BigDecimal(0.001))>0) {
                    orderProfit.setPartialDiffPr(commDiff.divide(custCommonPrice,4,BigDecimal.ROUND_HALF_UP));
                }
            }
            
        }

        if(orderNbr>0) {
            BigDecimal orderProfitAmt = orderCustTotal.subtract(orderVenTotal);
            BigDecimal orderPercentProfitAmt = null;
            if(orderCustTotal.compareTo(new BigDecimal(0.001))>0) {
                orderPercentProfitAmt =
                orderProfitAmt.divide(orderCustTotal,4,BigDecimal.ROUND_HALF_UP);
            }
            Object[] array = {orderCustTotal,orderVenTotal,orderProfitAmt,orderPercentProfitAmt};
            orderTotalInfo.put(new Integer(workingOrderNbr),array);
            orderVenTotal = zeroAmt;
            orderCustTotal = zeroAmt;
            workingOrderNbr = orderNbr;
        }
        //****************************END FROM LOGIC REPORT******************
        
        
        int custQty = custNumAL.size();
        String[] custNumA = new String[custQty];
        String[] custNameA = new String[custQty];
        BigDecimal[] custTotalPriceA = new BigDecimal[custQty];	
        BigDecimal[] custGoodsA = new BigDecimal[custQty];	
        BigDecimal[] custMiscA = new BigDecimal[custQty];	
        BigDecimal[] amt5030A = new BigDecimal[custQty];	
        BigDecimal[] venGoodsCostA = new BigDecimal[custQty];	
        BigDecimal[] venAdditionalChargesA = new BigDecimal[custQty];	
        BigDecimal custTotalPriceTot = new BigDecimal(0);	
        BigDecimal custGoodsTot = new BigDecimal(0);
        BigDecimal custMiscTot = new BigDecimal(0);
        BigDecimal amt5030Tot = new BigDecimal(0);
        BigDecimal venGoodsCostTot = new BigDecimal(0);
        BigDecimal venAdditionalChargesTot = new BigDecimal(0);
        
        for(int ii=0; ii<custQty; ii++) {
          custNumA[ii] = (String) custNumAL.get(ii);
          custNameA[ii] = (String) custNameAL.get(ii);
          custTotalPriceA[ii] = new BigDecimal(0);	
          custGoodsA[ii] = new BigDecimal(0);		
          custMiscA[ii] = new BigDecimal(0);	
          amt5030A[ii] = new BigDecimal(0);		
          venGoodsCostA[ii] = new BigDecimal(0);		
          venAdditionalChargesA[ii] = new BigDecimal(0);		
        }
        workingOrderNbr = -1;
       
        //Add site data
        HashMap acctSiteHM = new HashMap();
        HashSet acctHS = new HashSet();
        HashSet siteHS = new HashSet();
        ArrayList accts = new ArrayList();
        ArrayList sites = new ArrayList();
        int acctCount = 0;
        int siteCount = 0;
        String acctStr = null;
        String siteStr = null;
        for(Iterator iter=invoices.iterator(); iter.hasNext();) {
            DistCustInvoiceView invoice = (DistCustInvoiceView) iter.next();
            String acctNum = invoice.getCustomer();
            String siteNum = ""+invoice.getShipTo();
            String cs = acctNum+"@"+siteNum;
            LinkedList ll = (LinkedList) acctSiteHM.get(cs);
            if(ll==null) {
                ll = new LinkedList(); 
                acctSiteHM.put(cs,ll);
                if(!acctHS.contains(acctNum)) {
                    acctHS.add(acctNum);
                    if(acctCount>=1000) {
                        accts.add(acctStr);
                        acctCount=0;
                        acctStr = null;
                    }
                    if(acctStr!=null) {
                        acctStr +=", '"+acctNum+"'";
                    }else{
                        acctStr = "'"+acctNum+"'";
                    }
                }
                if(!siteHS.contains(siteNum)) {
                    siteHS.add(siteNum);
                    if(siteCount>=999) {
                        sites.add(siteStr);
                        siteCount=0;
                        siteStr = null;
                    }
                    if(siteStr!=null) {
                        siteStr +=", '" + siteNum + "'";
                    }else{
                        siteStr = "'" + siteNum + "'";
                    }
                    siteCount++;
                }                
            }
            ll.add(invoice);
        }

        if(acctStr!=null) accts.add(acctStr);
        if(siteStr!=null) sites.add(siteStr);
        for(Iterator acctIter = accts.iterator(); acctIter.hasNext();) {
            acctStr = (String) acctIter.next();
            for(Iterator siteIter = sites.iterator(); siteIter.hasNext();) {
               String siteAcctSql = 
                   "select " +
                   " a.bus_entity_id as acct_id, " +
                   " a.short_desc as acct_name, " +
                   " a.erp_num as acct_erp_num, " +
                   " s.bus_entity_id as site_id, " +
                   " s.short_desc as site_name, " +
                   " s.erp_num as site_erp_num, " +
                   " addr.city, "+
                   " addr.state_province_cd "+
                   " from " +
                   "clw_bus_entity s, " +
                   "clw_bus_entity_assoc bea, " +
                   "clw_address addr, "+
                   "clw_bus_entity a"+
                   " where 1=1 "+
                   " and s.bus_entity_id = bea.bus_entity1_id"+
                   " and bea.bus_entity_assoc_cd = '"+
                         RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"'"+
                   " and bea.bus_entity2_id = a.bus_entity_id "+
                   " and s.erp_num in ("+((String) siteIter.next())+")"+
                   " and a.erp_num in ("+acctStr+")"+
                   " and addr.bus_entity_id(+) = s.bus_entity_id ";

                Statement stmt = con.createStatement();
                ResultSet siteAcctRS = stmt.executeQuery(siteAcctSql);
                //Create customer invoice data
                while ( siteAcctRS.next() ) {
                    int acctId = siteAcctRS.getInt("acct_id");
                    String acctName = siteAcctRS.getString("acct_name");
                    String acctErpNum = siteAcctRS.getString("acct_erp_num");
                    int siteId = siteAcctRS.getInt("site_id");
                    String siteName = siteAcctRS.getString("site_name");
                    String siteErpNum = siteAcctRS.getString("site_erp_num");
                    String city = siteAcctRS.getString("city");
                    String state = siteAcctRS.getString("state_province_cd");
                    LinkedList ll = (LinkedList) acctSiteHM.get(acctErpNum+"@"+siteErpNum);
                    if(ll!=null) {
                        for(Iterator iter = ll.iterator(); iter.hasNext();) {
                            DistCustInvoiceView invoice = 
                                (DistCustInvoiceView) iter.next();
                            invoice.setAccountId(acctId);
                            invoice.setAccountName(acctName);
                            invoice.setSiteId(siteId);
                            invoice.setSiteName(siteName);
                            invoice.setCity(city);
                            invoice.setState(state);
                        }
                    }
                }
                stmt.close();
            }
        }
            
        
        
        
        result.setTable(new ArrayList());
        for(int i=0;i<invoices.size();i++){
            ArrayList row = new ArrayList();
            DistCustInvoiceView invoice = (DistCustInvoiceView) invoices.get(i);
            String customer = invoice.getCustomer();
            if(customer!=null) customer = customer.trim();
            int custIndex = -1;
            for(int ii=0; ii<custQty; ii++) {
                if(customer.equals(custNumA[ii])) {
                custIndex = ii;
                break;
              }
            }
            row.add(new Integer(invoice.getAccountId()));
            row.add(customer);
            row.add(invoice.getAccountName());
            row.add(new Integer(invoice.getSiteId()));
            row.add(new Integer(invoice.getShipTo()));
            row.add(invoice.getSiteName());
            row.add(invoice.getShipToAddr());
            row.add(invoice.getCity());
            row.add(invoice.getState());
            row.add(new Integer(invoice.getOrderNbr()));
            row.add(invoice.getOrderDate());
            row.add(invoice.getCustInvoicePrefix());
            row.add(invoice.getCustInvoiceNum());
            row.add(invoice.getCustInvoiceDate());

            BigDecimal custTotalPrice = invoice.getCustTotalPrice();
            if(custTotalPrice==null) custTotalPrice = new BigDecimal(0);
            row.add(custTotalPrice);
            custTotalPriceTot = custTotalPriceTot.add(custTotalPrice);	
            custTotalPriceA[custIndex] = custTotalPriceA[custIndex].add(custTotalPrice);

            BigDecimal custGoods = invoice.getCustGoods();
            if(custGoods==null) custGoods = new BigDecimal(0);
            row.add(custGoods);
            custGoodsTot = custGoodsTot.add(custGoods);	
            custGoodsA[custIndex] = custGoodsA[custIndex].add(custGoods);

            BigDecimal custMisc = invoice.getCustMisc();
            if(custMisc==null) custMisc = new BigDecimal(0);
            row.add(custMisc);
            custMiscTot = custMiscTot.add(custMisc);	
            custMiscA[custIndex] = custMiscA[custIndex].add(custMisc);

            BigDecimal amt5030 = invoice.getAmt5030();
            if(amt5030==null) amt5030 = new BigDecimal(0);
            row.add(amt5030);
            amt5030Tot = amt5030Tot.add(amt5030);	
            amt5030A[custIndex] = amt5030A[custIndex].add(amt5030);

            row.add(invoice.getVendor());
            row.add(invoice.getVenName());
            row.add(invoice.getPoNumber());
            row.add(invoice.getVenInvoiceNum());
            row.add(invoice.getVenInvoiceDate());
            row.add(invoice.getVenTotalCost());

            BigDecimal venGoodsCost = invoice.getVenGoodsCost();
            if(venGoodsCost==null) venGoodsCost = new BigDecimal(0);
            row.add(venGoodsCost);
            venGoodsCostTot = venGoodsCostTot.add(venGoodsCost);	
            venGoodsCostA[custIndex] = venGoodsCostA[custIndex].add(venGoodsCost);

            BigDecimal venAdditionalCharges = invoice.getVenAdditionalCharges();
            if(venAdditionalCharges==null) venAdditionalCharges = new BigDecimal(0);
            row.add(venAdditionalCharges);
            venAdditionalChargesTot = 
                   venAdditionalChargesTot.add(venAdditionalCharges);	
            venAdditionalChargesA[custIndex] = 
                 venAdditionalChargesA[custIndex].add(venAdditionalCharges);

            row.add(invoice.getVenTax());
            row.add(invoice.getCustItems());
            row.add(invoice.getVenItems());
            row.add(invoice.getCustCommonPrice());
            row.add(invoice.getVenCommonCost());
            row.add(invoice.getPartialDiff());
            row.add(invoice.getPartialDiffPr());
            if(workingOrderNbr != invoice.getOrderNbr()){
                Object[] array = (Object[]) orderTotalInfo.get(new Integer(invoice.getOrderNbr()));
                if(array != null){
                    //row.add(invoice.getOrderCustTotal());
                    //row.add(invoice.getOrderVenTotal());
                    //row.add(invoice.getOrderGM());
                    //row.add(invoice.getOrderGMPr());
                    row.add(array[0]);
                    row.add(array[1]);
                    row.add(array[2]);
                    row.add(array[3]);
                }else{
                    row.add(null);
                    row.add(null);
                    row.add(null);
                    row.add(null);
                }
                workingOrderNbr = invoice.getOrderNbr();
            }else{
                row.add(null);
                row.add(null);
                row.add(null);
                row.add(null);
            }
            row.add(invoice.getCustInvoiceType());
            row.add(invoice.getCustInvoiceSource());
            result.getTable().add(row);
        }
	
        GenericReportColumnViewVector rhdr =getReportHeader();
	result.setHeader(rhdr);
        result.setColumnCount(rhdr.size());
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        resultV.add(result);

        //Create summary page
        GenericReportResultView resultSum = GenericReportResultView.createValue();
	// Per account summary.
        GenericReportColumnViewVector headerSum = getReportHeaderSum(false);
        resultSum.setHeader(headerSum);
        resultSum.setColumnCount(headerSum.size());
        ArrayList rows = new ArrayList();
        for(int ii=0; ii<custQty; ii++){
          ArrayList row = new ArrayList();
          row.add(custNumA[ii]);
          row.add(custNameA[ii]);
          row.add(custTotalPriceA[ii]);
          row.add(custGoodsA[ii]);
          row.add(custMiscA[ii]);
          row.add(amt5030A[ii]);
          row.add(venGoodsCostA[ii]);
          row.add(venAdditionalChargesA[ii]);
	  row.add(venAccountCredits(con,custNumA[ii],startDateS, endDateS));

          rows.add(row); 

        }

        ArrayList rowTot = new ArrayList();
        rowTot.add("Total");
        rowTot.add("");
        rowTot.add(custTotalPriceTot);
        rowTot.add(custGoodsTot);
        rowTot.add(custMiscTot);
        rowTot.add(amt5030Tot);
        rowTot.add(venGoodsCostTot);
        rowTot.add(venAdditionalChargesTot);

        rows.add(rowTot); 
        resultSum.setTable(rows);
        resultV.add(resultSum);

	// now add the vendor summary results
	resultV = calculateVendorTotals(con, invoices, resultV);
        return resultV;
    }



    private class RepLine {
	public    String vendor, vendorName;
	public BigDecimal      custTotalPrice = new BigDecimal(0);	
	public BigDecimal      custGoods = new BigDecimal(0);		
	public BigDecimal      custMisc = new BigDecimal(0);	
	public BigDecimal      amt5030 = new BigDecimal(0);		
	public BigDecimal      venGoodsCost = new BigDecimal(0);		
	public BigDecimal      venAdditionalCharges = new BigDecimal(0);
    };
    
    private GenericReportResultViewVector calculateVendorTotals
	(
	 Connection con,
	 DistCustInvoiceViewVector invoices,
	 GenericReportResultViewVector resultV 
	 ) throws Exception {
	
        GenericReportResultView resultSum = 
	    GenericReportResultView.createValue();
        GenericReportColumnViewVector headerSum = getReportHeaderSum(true);
        resultSum.setHeader(headerSum);
        resultSum.setColumnCount(headerSum.size());

	java.util.Hashtable distRepLines = new java.util.Hashtable(); 
        for(int i=0;i<invoices.size();i++){
            DistCustInvoiceView invoice = (DistCustInvoiceView) invoices.get(i);
            String vendor = invoice.getVendor();

	    RepLine rp = null;
	    if ( !distRepLines.containsKey(vendor) ) {
		rp = new RepLine();
		distRepLines.put(vendor, rp);
	    }

	    rp = (RepLine)distRepLines.get(vendor);
	    rp.vendor = invoice.getVendor();
	    rp.vendorName = invoice.getVenName();

            BigDecimal custTotalPrice = invoice.getCustTotalPrice();
            if(custTotalPrice==null) custTotalPrice = new BigDecimal(0);
	    rp.custTotalPrice =
		rp.custTotalPrice.add(custTotalPrice);

            BigDecimal custGoods = invoice.getCustGoods();
            if(custGoods==null) custGoods = new BigDecimal(0);
	    rp.custGoods = rp.custGoods.add(custGoods);

            BigDecimal custMisc = invoice.getCustMisc();
            if(custMisc==null) custMisc = new BigDecimal(0);
            rp.custMisc = rp.custMisc.add(custMisc);

            BigDecimal amt5030 = invoice.getAmt5030();
            if(amt5030==null) amt5030 = new BigDecimal(0);
            rp.amt5030 = rp.amt5030.add(amt5030);

            BigDecimal venGoodsCost = invoice.getVenGoodsCost();
            if(venGoodsCost==null) venGoodsCost = new BigDecimal(0);
            rp.venGoodsCost = rp.venGoodsCost.add(venGoodsCost);

            BigDecimal venAdditionalCharges = invoice.getVenAdditionalCharges();
            if(venAdditionalCharges==null) 
		venAdditionalCharges = new BigDecimal(0);
            rp.venAdditionalCharges = rp.venAdditionalCharges.add(venAdditionalCharges);
        }


	java.util.Enumeration e = distRepLines.keys();
        ArrayList rows = new ArrayList();
	while (e.hasMoreElements() ) {
	    String k = (String)e.nextElement();
	    if ( k.trim().length() <= 0 ) continue;
	    RepLine res = (RepLine)distRepLines.get(k);
	    ArrayList row = new ArrayList();
	    row.add(res.vendor);
	    row.add(res.vendorName);
	    row.add(res.custTotalPrice);
	    row.add(res.custGoods);
	    row.add(res.custMisc);
	    row.add(res.amt5030);
	    row.add(res.venGoodsCost);
	    row.add(res.venAdditionalCharges);
	    row.add(venCredits(con, res.vendor,startDateS, endDateS));
	    rows.add(row); 
        }
        resultSum.setTable(rows);

        resultV.add(resultSum);

	return resultV;
    }
    
    //We can't just use the meta data here because of all the various sub-queries that
    //take place.
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
	String col1 = "Customer Num", col2 = "Cust Name";

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Customer Id",38,0,"Integer"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",col1,0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",col2,0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Site Id",38,0,"Integer"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Erp Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Addr",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Erp Order Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Erp Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Invoice Prefix",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Invoice Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Cust Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Total Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Goods",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Misc",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount 5030",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Po Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Invoice Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Ven Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Total Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Goods Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Additional Charges",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Tax",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Part Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Part Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Partial Diff $",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Partial Diff %",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order Cust Total",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order Ven Total",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order GM $",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order GM %",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Invoice Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Invoice Source",0,255,"VARCHAR2"));
        return header;
    }

    
    private GenericReportColumnViewVector getReportHeaderSum(boolean pForDist) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
	String col1 = "Customer Num", col2 = "Cust Name";
	if ( pForDist ) {
	    col1 = "Vendor Num"; col2 = "Vendor Name";
	}

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",col1,0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",col2,0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Total Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Goods",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Misc",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount 5030",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Goods Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Additional Charges",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Credits",2,20,"NUMBER"));

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


    private BigDecimal venCredits
	(java.sql.Connection con,
	 String vendorNum, String startDateS, String endDateS)
	throws Exception {
	
	// pick out any vendor credits
	String credsql = "select sum(api.base_inv_amt) sum_credits"
	    + " from law_apinvoice api " + " where " + "   api.vendor = ? "
	    + " and api.base_inv_amt < 0 "
	    + "  and create_date between '" + startDateS + "' and '"
	    + endDateS + "' " ;
	
	PreparedStatement stmt = con.prepareStatement(credsql);

	stmt.setString(1, vendorNum);

	java.sql.ResultSet rs = stmt.executeQuery();
	BigDecimal credamt = null;
    
	while (rs.next()) {
	    credamt = rs.getBigDecimal("sum_credits");	    
	}
	
	rs.close();
	stmt.close();

	if (null == credamt) credamt = new BigDecimal(0);
	return credamt;
    }


    private BigDecimal venAccountCredits
	(java.sql.Connection con,
	 String accountNum, String startDateS, String endDateS)
	throws Exception {
	
    	
    	if ( null == accountNum || accountNum.trim().length() <= 0 ) {
    		return new BigDecimal(0);
    	}
	// pick out any vendor credits
	String credsql = "select sum(api.base_inv_amt) sum_credits"
	    + " from law_apinvoice api, law_custorder co  " + " where " + 
	    "  co.customer in ("
	    + accountNum + ") " + " and api.base_inv_amt < 0 "
	    + " and co.order_nbr = api.order_nbr "
	    + "  and api.create_date between '" + startDateS + "' and '"
	    + endDateS + "' " ;
	
	java.sql.Statement stmt = con.createStatement();
	java.sql.ResultSet rs = stmt.executeQuery(credsql);
	BigDecimal credamt = null;
    
	while (rs.next()) {
	    credamt = rs.getBigDecimal("sum_credits");	    
	}
	
	rs.close();
	stmt.close();

	if (null == credamt) credamt = new BigDecimal(0);
	return credamt;
    }

}
