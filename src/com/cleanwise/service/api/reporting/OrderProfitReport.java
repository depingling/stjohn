/*
 * OrderProfitReport.java
 *
 * Created on February 3, 2003, 10:26 PM
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
 * Picks up distributor and customer invoices and agreates it on order level based on orders
 * @param pBeginDate start of the period, pEndDate  end of the period
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class OrderProfitReport implements GenericReport {
    Map orderTotalInfo = new java.util.HashMap();
    /** Creates a new instance of OrderProfitReport */
    public OrderProfitReport() {
    }
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        GenericReportResultView result = GenericReportResultView.createValue();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);
        
        //format the dates from one string type to anouther
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date pStartDate = df.parse((String) pParams.get("BEG_DATE"));
        Date pEndDate = df.parse((String) pParams.get("END_DATE"));
        df = new SimpleDateFormat("dd-MMM,yyyy");
        String startDateS = df.format(pStartDate);
        String endDateS = df.format(pEndDate);
        
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
            "non_inv_gds_b+misc_tot_base+frt_charge_b total_price,"+
            "non_inv_gds_b goods,"+
            "misc_tot_base+frt_charge_b misc,"+
            "trim(oeil.item) as item,"+
            "nvl(oeil.quantity,0) qty,"+
            "tax_total_bse tax_charge, "+
            "oeil.sell_unit_prc item_price, "+
            "oeil.line_grs_base line_amt "+
            "from law_oeinvoice oei, law_oeinvcline oeil,law_custorder co "+
            "where oei.r_status!=8 "+
            "and oeil.invc_number(+) = oei.invc_number "+
            "and oeil.invc_prefix(+) = oei.invc_prefix "+
            "and co.order_nbr = oei.order_nbr "+
            "and co.order_nbr != 0 "+
            "and co.order_date between '"+startDateS+"' and '"+endDateS+"' "+
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
                    dci.setCustItemInd1(custInd);
                }
                String item = cii.getItem();
                if(item != null) {
                    if(custItems.length()>0) custItems+=", ";
                    custItems += cii.getItem()+"("+cii.getQty()+")";
                }
            }
            if(dci==null){
                dci = DistCustInvoiceView.createValue();
            }
            dci.setCustItems(custItems);
            dci.setCustItemInd2(custInd-1);
            custDcis.add(dci);
            stmt.close();
            
            //Get vendor invoices
            String tiedVenInvSql =
            "select"+
            "  poa.vendor, poa.invoice, poa.po_number, poa.item, poa.approve_qty, a.order_nbr, co.order_date, "+
            "  api.invoice_dte,api.base_tot_dist, api.tran_tot_tax, poa.tot_base_amt line_amt, "+
            "  ent_unit_cst item_cost "+
            "from "+
            "(select distinct order_nbr,po_number from law_poline where order_nbr in ("+
            "select distinct order_nbr "+
            "from law_custorder"+
            " where r_status!=8"+
            "   and order_nbr!=0"+
            "   and order_date between '"+startDateS+"' and '"+endDateS+"')) a,"+
            "   law_apinvoice api,law_poapprove poa, law_custorder co "+
            " where a.po_number = poa.po_number "+
            "  and api.vendor = poa.vendor "+
            "  and api.invoice = poa.invoice "+
            "  and api.cancel_seq = 0 "+
            "  and co.order_nbr = a.order_nbr "+
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
                Date orderDate = tiedVenInvcRS.getDate("order_date");
                BigDecimal venTotal = tiedVenInvcRS.getBigDecimal("base_tot_dist");
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
                if(!prevVendor.equals(vendor) || !prevVenInvoice.equals(venInvoice)){
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
                    dci.setOrderDate(orderDate);
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
            if(dci==null){
                dci = DistCustInvoiceView.createValue();
            }
            dci.setVenItemInd2(venInd-1);
            venDcis.add(dci);
            stmt.close();
            
            prevOrder = -1;
            //Now the main thing: match invoices
            DistCustInvoiceViewVector orderCustDci = null;
            DistCustInvoiceViewVector orderVenDci = null;
            ReportingUtils util = new ReportingUtils();
            for(int ii=0, jj=0; ii<custDcis.size()||jj<venDcis.size();) {
                DistCustInvoiceView custDci = null;
                int custOrder = -1;
                if(ii<custDcis.size()) {
                    custDci = (DistCustInvoiceView) custDcis.get(ii);
                    custOrder = custDci.getOrderNbr();
                }
                DistCustInvoiceView venDci = null;
                int venOrder = -1;
                if(jj<venDcis.size()) {
                    venDci = (DistCustInvoiceView) venDcis.get(jj);
                    venOrder = venDci.getOrderNbr();
                }
                orderCustDci = new DistCustInvoiceViewVector();
                orderVenDci = new DistCustInvoiceViewVector();
                if(custOrder>0 && custOrder<=venOrder) {
                    prevOrder = custOrder;
                    orderCustDci.add(custDci);
                    for(ii++;ii<custDcis.size();ii++) {
                        custDci = (DistCustInvoiceView) custDcis.get(ii);
                        custOrder = custDci.getOrderNbr();
                        if(custOrder!=prevOrder) {
                            break;
                        }
                        orderCustDci.add(custDci);
                    }
                }
                if(venOrder>0 && venOrder<=custOrder) {
                    prevOrder = venOrder;
                    orderVenDci.add(venDci);
                    for(jj++;jj<venDcis.size();jj++) {
                        venDci = (DistCustInvoiceView) venDcis.get(jj);
                        venOrder = venDci.getOrderNbr();
                        if(venOrder!=prevOrder) {
                            break;
                        }
                        orderVenDci.add(venDci);
                    }
                }
                DistCustInvoiceViewVector orderDci =
                util.invoicesMatch(orderCustDci,ciiV, orderVenDci,viiV);
                invoices.addAll(orderDci);
            }
            if(orderCustDci==null) orderCustDci = new DistCustInvoiceViewVector();
            if(orderVenDci==null) orderVenDci = new DistCustInvoiceViewVector();
            DistCustInvoiceViewVector orderDci =
            util.invoicesMatch(orderCustDci,ciiV, orderVenDci,viiV,pStartDate, pEndDate);
            invoices.addAll(orderDci);
        }
        catch (SQLException exc) {
            errorMess = "Error. SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        
        //***********************taken from the logic servlet
        int prevOrderNbr = -1;
        BigDecimal orderCustTotal = new BigDecimal(0);
        BigDecimal orderVenTotal = new BigDecimal(0);
        int orderNbr = 0;
        String prevCustomer = "";
        String custName = "";
        String prevVendor = "";
        String venName = "";
        for(int ii=invoices.size()-1; ii>=0; ii--) {
            DistCustInvoiceView  orderProfit = (DistCustInvoiceView) invoices.get(ii);
            orderNbr = orderProfit.getOrderNbr();
            if(orderNbr!=prevOrderNbr && prevOrderNbr>0) {
                BigDecimal orderProfitAmt = orderCustTotal.subtract(orderVenTotal);
                BigDecimal orderPercentProfitAmt = null;
                if(orderCustTotal.compareTo(new BigDecimal(0.001))>0 && !orderProfitAmt.equals(new BigDecimal(0))) {
                    orderPercentProfitAmt =
                    orderProfitAmt.divide(orderCustTotal,4,BigDecimal.ROUND_HALF_UP);
                }
                Object[] array = {orderCustTotal,orderVenTotal,orderProfitAmt,orderPercentProfitAmt};
                orderTotalInfo.put(new Integer(prevOrderNbr),array);
                orderVenTotal = zeroAmt;
                orderCustTotal = zeroAmt;
                prevOrderNbr = orderNbr;
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
            if(customer!=null) customer = customer.trim();
            if(!prevCustomer.equals(customer)) {
                custName = getName(con, customer);
                prevCustomer = customer;
            }
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
        }
        if(orderNbr>0) {
            BigDecimal orderProfitAmt = orderCustTotal.subtract(orderVenTotal);
            BigDecimal orderPercentProfitAmt = null;
            if(!orderCustTotal.equals(new BigDecimal(0)) && !orderProfitAmt.equals(new BigDecimal(0))) {
                orderPercentProfitAmt =
                orderProfitAmt.divide(orderCustTotal,4,BigDecimal.ROUND_HALF_UP);
            }
            Object[] array = {orderCustTotal,orderVenTotal,orderProfitAmt,orderPercentProfitAmt};
            orderTotalInfo.put(new Integer(prevOrderNbr),array);
            orderVenTotal = zeroAmt;
            orderCustTotal = zeroAmt;
        }
        
        //*********************End from logic servlet
        
        
        
        //return invoices;
        int workingOrderNbr = 0;
        result.setTable(new ArrayList());
        for(int i=0;i<invoices.size();i++){
            DistCustInvoiceView invoice = (DistCustInvoiceView) invoices.get(i);
            ArrayList row = new ArrayList();
            row.add(invoice.getCustomer());
            row.add(invoice.getCustName());
            row.add(new Integer(invoice.getOrderNbr()));
            row.add(invoice.getOrderDate());
            row.add(invoice.getCustInvoicePrefix());
            row.add(invoice.getCustInvoiceNum());
            row.add(invoice.getCustInvoiceDate());
            row.add(invoice.getCustTotalPrice());
            row.add(invoice.getCustGoods());
            row.add(invoice.getCustMisc());
            row.add(invoice.getVendor());
            row.add(invoice.getVenName());
            row.add(invoice.getPoNumber());
            row.add(invoice.getVenInvoiceNum());
            row.add(invoice.getVenInvoiceDate());
            row.add(invoice.getVenTotalCost());
            row.add(invoice.getVenGoodsCost());
            row.add(invoice.getVenAdditionalCharges());
            row.add(invoice.getVenTax());
            row.add(invoice.getCustItems());
            row.add(invoice.getVenItems());
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
            result.getTable().add(row);
        }
        result.setHeader(getReportHeader());
        result.setColumnCount(getReportHeader().size());
        return result;
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

    
    //We can't just use the meta data here because of all the various sub-queries that
    //take place.
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Invoice Prefix",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Invoice Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Cust Invoice Date",0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Total Price", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Goods",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Cust Misc", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Po Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Invoice Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Ven Invoice Date",0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Total Cost", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Goods Cost", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Additional Charges", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Ven Tax", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ven Items",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order Cust Total", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order Ven Total", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order GM $", 2, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order GM %", 2, 20, "NUMBER"));
        return header;
    }
}
