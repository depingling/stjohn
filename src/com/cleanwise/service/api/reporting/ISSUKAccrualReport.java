/*
 * ISSUKAccrualReport.java
 *
 * Based off VendorReconciliationReport
 * Differences include 
 * - ran by user_type -StoreAdmin
 * - does not show lines which have qty-open = 0 (lines already invoiced)
 * - some additional columns 
 * 
 * @author ssharma 
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.reporting.BaseJDReportDW.GenericReportUserStyleDesc;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.DBCriteria;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.io.PrintWriter;

public class ISSUKAccrualReport implements GenericReportMulti {

    private static String className="ISSUKAccrualReport";
    private static final String INVOICE_SEPARATOR = ",";
    public static final String TABLE_WIDTH="748";
    //public static final String TABLE_WIDTH="772";
    private static String storeName="";
    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;
    protected static final String[] PO_COL_WIDTH = new String[]{"10","10","10","25","27","11","20","18","14","9","10","14","10","12",
    	"30","12","32","11","11","18"};
    protected static final String[] INV_COL_WIDTH = new String[]{"15","10","16","12","25","27","11","20","18","14","9","10","14","14",
    	"10","12","30","12","32","14","20","21","12","14","10","12","14","22","18","24","24","14","12"};

    protected static final String BOLD_STYLE = "BOLD";

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        Statement stmt;
        ResultSet rs;

        String begDateS = (String) pParams.get("BEG_DATE_OPT");
        String endDateS = (String) pParams.get("END_DATE_OPT");
        String storeIdStr = (String) pParams.get("STORE");
        String invoiceNums=(String)pParams.get("INVOICE_MULTI_OPT");
        String invoiceStatus=(String)pParams.get("INVOICE_STATUS_SELECT_OPT");
        

        try {
            Integer.parseInt(storeIdStr);
        } catch (NumberFormatException e) {
            String mess = "^clw^Store identifier is not a valid value^clw^";
            throw new Exception(mess);
        }
        
        String invoiceNumsList=null;
        if(Utility.isSet(invoiceNums)){
           StringTokenizer tok = new StringTokenizer(invoiceNums.trim(),",");
           while(tok.hasMoreTokens()){
             String num=tok.nextToken().trim();

             if(invoiceNumsList!=null) {
               invoiceNumsList += ",'"+num+"'";
             } else {
               invoiceNumsList = "'"+num+"'";
             }
           }
         }

        Hashtable invoiceTableStatusCds=new Hashtable();
        invoiceTableStatusCds.put("CANCELLED","Inv Rejected by Client");
        invoiceTableStatusCds.put("REJECTED","Inv Rejected by Client" );
        invoiceTableStatusCds.put("DIST_SHIPPED","Inv Pending Clients Approval");
        invoiceTableStatusCds.put("PENDING_REVIEW","Inv Pending Clients Approval");
        invoiceTableStatusCds.put("DUPLICATE" ,"Duplicate");
        invoiceTableStatusCds.put("PROCESS_ERP","PROCESS_ERP");
        invoiceTableStatusCds.put("ERP_REJECTED","ERP_REJECTED");
        invoiceTableStatusCds.put("ERP_GENERATED","ERP_GENERATED");
        invoiceTableStatusCds.put("ERP_GENERATED_ERROR","ERP_GENERATED_ERROR");
        invoiceTableStatusCds.put("ERP_RELEASED","Sent to Client");
        invoiceTableStatusCds.put("ERP_RELEASED_ERROR","Sent to Client Error");
        invoiceTableStatusCds.put("MANUAL_INVOICE_RELEASE","Sent to Client");
        invoiceTableStatusCds.put("CUST_INVOICED","CUST_INVOICED");
        invoiceTableStatusCds.put("CUST_INVOICED_FAILED","CUST_INVOICED_FAILED");
        invoiceTableStatusCds.put("CLW_ERP_PROCESSED","Ready to Send to Client");
        invoiceTableStatusCds.put("PENDING", 	"Pending Further Action");
        
        String sql = "";
        String invoiceIdList = null;
        String poNumList = null;
        if(invoiceNumsList!=null) {
            sql = "select invoice_dist_id, erp_po_num from clw_invoice_dist id where invoice_num in ("+
                invoiceNumsList+")";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt("invoice_dist_id");
                String poNum = rs.getString("erp_po_num");
                if(invoiceIdList==null) {
                    invoiceIdList = ""+id;
                    poNumList = "'"+poNum+"'";
                } else {
                    invoiceIdList += ", "+id;
                    poNumList += ", '"+poNum+"'";
                }                
            }            
            if(poNumList==null) {
                String mess = "^clw^No Requested Invoices Found^clw^";
                throw new Exception(mess);
            }
        }
        
        if(storeIdStr!=null){

            String storeSql = "select short_desc from clw_bus_entity where "+
            					"bus_entity_id = "+storeIdStr;
            stmt = con.createStatement();
            rs = stmt.executeQuery(storeSql);
            while(rs.next()) {
            	storeName = rs.getString("short_desc");
            }
            if(storeName==null) {
                String mess = "^clw^No Store Found^clw^";
                throw new Exception(mess);
            }
        }

        sql = "select "+
                " o.order_id, "+
                " order_amount.tot_amt as order_amt, "+
                " po.erp_po_num, "+
				" o.request_po_num, "+
                " po.po_date, "+
                " dist.short_desc as vendor_name, "+
                " acc.short_desc as account_name, "+
                " site.short_desc as site_name, "+
                " oa.address1, "+
				" oa.address2, "+
                " oa.city, "+
                " oa.postal_code, "+
                " oi.item_sku_num as system_sku, "+
                " oi.dist_item_sku_num as po_vendor_sku, "+
                " oi.dist_item_uom, "+
				" oi.dist_item_pack, "+
                " oi.item_short_desc, "+
                " oi.total_quantity_ordered, "+
                " oi.cust_contract_price as line_price, "+
                " oi.dist_uom_conv_multiplier, "+
                " idd.dist_item_qty_received, "+
                " id.invoice_status_cd, "+
                " p.clw_value as cost_centre, "+
                " c.short_desc as line_cost_centre,"+
                " c.cost_center_code as line_cost_centre_cd"+
                "  from  "+
            " clw_purchase_order po, "+
            " clw_invoice_dist id, "+
            " clw_invoice_dist_detail idd, "+
            " clw_bus_entity dist, "+
            " clw_bus_entity acc, "+
            " clw_bus_entity site, "+
            " clw_order o, "+
            " clw_order_item oi, "+
			" clw_order_address oa, "+
			" clw_property p, clw_cost_center c, "+
			" (select order_id, sum(cust_contract_price*total_quantity_ordered) tot_amt " +
			" from clw_order_item group by order_id ) order_amount "+
                "  where 1=1 "+
             ((invoiceNumsList==null)? 
                (""):
                (" and po.erp_po_num in ("+poNumList+") "))+
                getDateCriteria("po_date",begDateS, endDateS)+
             " and po.purchase_order_id = oi.purchase_order_id  "+
             " and po.purchase_order_status_cd not in ('"+RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED+"')"+
             " and oi.order_item_status_cd not in ('"+RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED+"')"+
             " and oi.dist_erp_num = dist.erp_num "+
			 " and o.order_id = oa.order_id "+
			 " and o.store_id="+ storeIdStr+
			 " and oa.address_type_cd = '"+RefCodeNames.ADDRESS_TYPE_CD.SHIPPING+"' "+
             " and o.order_id = po.order_id  "+
             " and o.site_id = site.bus_entity_id  "+
             " and o.account_id = acc.bus_entity_id  "+
             " and o.order_id = order_amount.order_id "+
             " and c.cost_center_id = oi.cost_center_id "+
             " and oi.order_item_id = idd.order_item_id (+)  "+
             " and idd.invoice_dist_id = id.invoice_dist_id(+)  "+
             " and p.bus_entity_id = site.bus_entity_id "+
             " and p.short_desc='"+RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"'"+
             " order by o.order_id, oi.erp_po_num, system_sku, id.invoice_num ";


        LinkedList allPoLineLL = new LinkedList();
        HashMap poLinesMap=new HashMap();
        HashMap invoiceMap=new HashMap();
        ArrayList invNums=new ArrayList();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        String prevPoNum = null;
        int prevSkuNum = -1;
        LinkedList poLineLL = new LinkedList();
        boolean openPoFl = false;
        POLine poLine = null;
       
        while(rs.next()){
        	
        	//BigDecimal orderAmt = rs.getBigDecimal("order_amt");
        	BigDecimal lineItemPrice = rs.getBigDecimal("line_price");
            String invStatusCd = rs.getString("invoice_status_cd");
            if(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE.equals(invStatusCd) ||
               RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED.equals(invStatusCd)) {
                continue;
            }
            String poNum = rs.getString("erp_po_num");        
            int skuNum = rs.getInt("system_sku");
       
            if(prevPoNum==null || !prevPoNum.equals(poNum)) {
                //Next po
                if(poLine!=null && poLine.mQtyOpen!=0) {
                    openPoFl = true;
                }
                if(openPoFl) {
                    allPoLineLL.addAll(poLineLL);
                    openPoFl = false;
                }
                poLineLL.clear();          
                poLine = null;
                prevPoNum = poNum;
            } 
            if(poLine!=null) {
                if(prevSkuNum==skuNum) {
                    // next invoice for the po item
                    BigDecimal mult = rs.getBigDecimal("dist_uom_conv_multiplier");
                    poLine.mConvMult = (mult==null)?1:mult.intValue();
                    if(poLine.mConvMult==0) poLine.mConvMult = 1;
                    int qtyReceived = rs.getInt("dist_item_qty_received");
                    if(mult!=null && qtyReceived!=0) {
                        qtyReceived /= poLine.mConvMult;
                    }
                    poLine.mQtyOpen -= qtyReceived;

                } else {
                    // next item of the po
                    if(poLine.mQtyOpen!=0) {
                        openPoFl = true;
                    }
                    poLine = null;
                }
            }
            if(poLine==null) { 
                prevSkuNum = skuNum;
                poLine = new POLine();
                poLineLL.add(poLine);

                poLine.mPONumber = poNum;
				poLine.mPurchaseOrderNumber = rs.getString("request_po_num");
                poLine.mSystemSKU = skuNum;
                poLine.mPODate=rs.getDate("po_date");
                poLine.mVendorName=rs.getString("vendor_name");
                poLine.mAccountName=rs.getString("account_name");
                poLine.mCostCentre=rs.getString("cost_centre");
                poLine.mSiteName=rs.getString("site_name");
                poLine.mAddress1 =rs.getString("address1");
                poLine.mAddress2 =rs.getString("address2");
                poLine.mCity=rs.getString("city");
                poLine.mZip=rs.getString("postal_code");
                poLine.mSystemSKU=rs.getInt("system_sku");
                poLine.mPoVendorSKU=rs.getString("po_vendor_sku");
                poLine.mVendorUOM=rs.getString("dist_item_uom");
                poLine.mVendorPack=rs.getString("dist_item_pack");
                poLine.mExpenseCodeName=rs.getString("line_cost_centre");
                poLine.mExpenseCode=rs.getString("line_cost_centre_cd");
                poLine.mItemShortDesc=rs.getString("item_short_desc");
                poLine.mQtyOrdered=rs.getInt("total_quantity_ordered");
                poLine.mOrderAmt=rs.getBigDecimal("order_amt");
                BigDecimal mult = rs.getBigDecimal("dist_uom_conv_multiplier");
                int qtyReceived = rs.getInt("dist_item_qty_received");
                if(mult!=null && qtyReceived!=0) {
                    int multDb = (int) mult.doubleValue();
                    qtyReceived /= multDb;
                }
                poLine.mQtyOpen=poLine.mQtyOrdered - qtyReceived;

            }
            //poLine.mOrderAmt = orderAmt;
            
            poLine.mOrderAmt = lineItemPrice.multiply(new BigDecimal(poLine.mQtyOpen));

        }
        rs.close();
        stmt.close();

        if(openPoFl || poLine!=null && poLine.mQtyOpen>0) {
            allPoLineLL.addAll(poLineLL);
        }


       ArrayList invoiceLines = new ArrayList();
       HashMap oplMap = new HashMap();

       sql = "Select " +
           "o.order_id, " +
           " order_amount.tot_amt as order_amt, "+
           "id.erp_po_num, " +
           "o.request_po_num, " +
           "id.invoice_dist_id, "+
           "id.invoice_num, " +
           "id.invoice_date, " +
           "dist.short_desc vendor, "+
           "acc.short_desc account, " +
           "site.short_desc site, "+
           "id.ship_to_address_1, " +
           "id.ship_to_address_2, " +
           "id.ship_to_city, " +
           "id.ship_to_postal_code, "+
           "idd.item_sku_num, " +
           "oi.dist_item_sku_num as po_dist_item_sku_num, " +
           "idd.dist_item_sku_num as inv_dist_item_sku_num, " +
           "idd.dist_item_uom, " +
           "idd.dist_item_pack, " +
           "idd.dist_item_short_desc, "+
           "id.invoice_status_cd,"+
           "idd.adjusted_cost, " +
           "idd.dist_item_quantity, " +
           "idd.item_received_cost, " +
           "dist_item_qty_received, "+
           "id.mod_date, " +
           "idd.mod_date mod_date1,"+
           "id.freight, " +
           "id.misc_charges, " +
           "id.sales_tax, "+
           "oi.dist_uom_conv_multiplier, "+
           " p.clw_value as cost_centre, "+
           " c.short_desc as line_cost_centre, "+
           " c.cost_center_code as line_cost_centre_cd, "+
           "' ' as invoice_note " + // new stmt
           "from clw_invoice_dist id, " +
           " clw_bus_entity dist, " +
           " clw_order o, " +
           " clw_order_item oi, "+
           " clw_bus_entity site, " +
           " clw_bus_entity acc,"+
           " clw_invoice_dist_detail idd, " +
           " clw_property p, clw_cost_center c, "+
           " (select order_id, sum(cust_contract_price*total_quantity_ordered) tot_amt " +
			" from clw_order_item group by order_id ) order_amount "+
        " where 1=1 "+
          ((invoiceIdList==null)? " ":
             (" and id.invoice_dist_id in ("+invoiceIdList+") "))+
             getDateCriteria("id.invoice_date",begDateS,endDateS);
       
        if (invoiceStatus != null && !invoiceStatus.trim().equals("")) {
        	sql = sql + " and id.invoice_status_cd='"+invoiceStatus+"'";
        }

        sql = sql +
          " and id.bus_entity_id = dist.bus_entity_id"+
          " and o.order_id = id.order_id"+
          " and o.store_id="+ storeIdStr+
          " and idd.order_item_id = oi.order_item_id"+
          " and c.cost_center_id = oi.cost_center_id "+
          " and o.site_id = site.bus_entity_id"+
          " and o.account_id = acc.bus_entity_id"+
          " and o.order_id = order_amount.order_id "+
          " and id.invoice_dist_id = idd.invoice_dist_id(+)"+
          " and p.bus_entity_id = site.bus_entity_id "+
          " and p.short_desc='"+RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"'";
        
        //Get invoices for orders placed outside of Connexion.
      	sql = sql + " \nUNION\n " +
      			" Select null as order_id, " +
      			" null as order_amt, " +
      			" id.erp_po_num, " +
      			" null as request_po_num, " +
      			" id.invoice_dist_id, " +
      			" id.invoice_num, " +
      			" id.invoice_date, " +
      			" dist.short_desc vendor, " +
      			" null as account, " +
      			" null as site, " +
      			" id.ship_to_address_1, " +
      			" id.ship_to_address_2, " +
      			" id.ship_to_city, " +
      			" id.ship_to_postal_code, " +
      			" idd.item_sku_num, " +
      			" null as po_dist_item_sku_num, " +
      			" idd.dist_item_sku_num as inv_dist_item_sku_num, " +
      			" idd.dist_item_uom, idd.dist_item_pack, " +
      			" idd.dist_item_short_desc, " +
      			" id.invoice_status_cd, " +
      			" idd.adjusted_cost, " +
      			" idd.dist_item_quantity, " +
      			" idd.item_received_cost, " +
      			" dist_item_qty_received, " +
      			" id.mod_date, " +
      			" idd.mod_date mod_date1, " +
      			" id.freight, " +
      			" id.misc_charges, " +
      			" id.sales_tax, null as dist_uom_conv_multiplier, " +
      			" null as cost_centre, " +
      			" null as line_cost_centre, " +
      			" null as line_cost_centre_cd, " +
      			" ' ' as invoice_note  " +
      			" from clw_invoice_dist id, " +
      			" clw_bus_entity dist, " +
      			" clw_invoice_dist_detail idd " +
      			" where 1=1 " +
      			" and id.order_id is null "+
            ((invoiceIdList==null)? " ":
               (" and id.invoice_dist_id in ("+invoiceIdList+") "))+
               getDateCriteria("id.invoice_date",begDateS,endDateS);
         
          	if (invoiceStatus != null && !invoiceStatus.trim().equals("")) {
          		sql = sql + " and id.invoice_status_cd='"+invoiceStatus+"'";
          	}
	          sql = sql +
	      			" and id.bus_entity_id = dist.bus_entity_id  " +
	      			" and id.store_id="+ storeIdStr+
	      			" and id.invoice_dist_id = idd.invoice_dist_id " +
	      		//	" order by id.invoice_dist_id, idd.dist_line_number ; ";
	      			" order by 1, 5 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            int prevOrderId = -1;
            int prevInvoiceDistId = -1;
            IdVector invDistIdV = new IdVector();
            LinkedList invoiceLL = new LinkedList();
            boolean nextInvFl = false;
            while (rs.next()) {
                InvoiceLine invoice = new InvoiceLine();
                invoiceLL.add(invoice);
                int invoiceDistId = rs.getInt("invoice_dist_id");
                if(prevInvoiceDistId!=invoiceDistId) {
                    nextInvFl = true;
                    prevInvoiceDistId = invoiceDistId;
                    invDistIdV.add(new Integer(invoiceDistId));
                } else {
                    nextInvFl = false;
                }
                BigDecimal orderAmt = rs.getBigDecimal("order_amt");
                invoice.mInvoiceDistId = invoiceDistId;
                invoice.mOrderId = rs.getInt("order_id");
                invoice.mInvoice = rs.getString("invoice_num");
                invoice.mInvoiceDate = rs.getDate("invoice_date");
				invoice.mErpPoNum = rs.getString("erp_po_num");
				invoice.mPurchaseOrderNumber = rs.getString("request_po_num");
                invoice.mVendorName = rs.getString("vendor");
                invoice.mAccountName = rs.getString("account");
                invoice.mCostCentre = rs.getString("cost_centre");
                invoice.mSiteName = rs.getString("site");
                invoice.mAddress1 = rs.getString("ship_to_address_1");
                invoice.mAddress2 = rs.getString("ship_to_address_2");
                invoice.mCity = rs.getString("ship_to_city");
                invoice.mZip = rs.getString("ship_to_postal_code");
                invoice.mSystemSKU = rs.getInt("item_sku_num");
                invoice.mPoVendorSKU = rs.getString("po_dist_item_sku_num");
                invoice.mInvVendorSKU = rs.getString("inv_dist_item_sku_num");
                invoice.mVendorUOM = rs.getString("dist_item_uom");
                invoice.mVendorPack = rs.getString("dist_item_pack");
                invoice.mExpenseCodeName=rs.getString("line_cost_centre");
                invoice.mExpenseCode=rs.getString("line_cost_centre_cd");
                invoice.mNote = rs.getString("invoice_note"); // new stmt
                invoice.mItemShortDesc = rs.getString("dist_item_short_desc");
                invoice.mStatusCd = 
                    translateInvoiceStatusCd(rs.getString("invoice_status_cd"), 
                                 invoiceTableStatusCds);
                BigDecimal itemRecCost = rs.getBigDecimal("item_received_cost");
                invoice.mLineCostRequested = itemRecCost;
                BigDecimal adjustedCost = rs.getBigDecimal("adjusted_cost");
                invoice.mLineCostAccepted = adjustedCost;
                int distItemQtyRec = rs.getInt("dist_item_qty_received");
                //int distItemQtyRec = rs.getInt("dist_item_quantity");
                int distItemQty = rs.getInt("dist_item_quantity"); 
                if(distItemQtyRec ==0 ){
                	distItemQtyRec = distItemQty;
                }
                BigDecimal mult = rs.getBigDecimal("dist_uom_conv_multiplier");
                int mConvMult = 0;
                
                mConvMult = (mult==null)?1:mult.intValue();
                if(mConvMult==0) poLine.mConvMult = 1;
                int qtyReceived = rs.getInt("dist_item_qty_received");
                invoice.mQtyAccepted = distItemQty;
                invoice.mQtyRequested = distItemQtyRec;
                BigDecimal lineAmtReq = null;
                if(itemRecCost!=null) {
                    lineAmtReq = itemRecCost.multiply(new BigDecimal(distItemQtyRec));
                    lineAmtReq = lineAmtReq.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                
                BigDecimal lineAmtAccepted = null;
                if(adjustedCost == null){
                	adjustedCost = itemRecCost;
                }
                if(adjustedCost!=null) {
                    lineAmtAccepted = adjustedCost.multiply(new BigDecimal(distItemQty));
                    
                    if(mConvMult!=1){
                       lineAmtAccepted = 
                           lineAmtAccepted.multiply(new BigDecimal(mConvMult));
                    }
                    lineAmtAccepted = lineAmtAccepted.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                
                invoice.mLineAmountAccepted = lineAmtAccepted;
                invoice.mLineAmountRequested = lineAmtReq; 
                
                Date dd = rs.getDate("mod_date");
                invoice.mLastActivityDate = dd;
                long tt = (dd!=null)?dd.getTime():0;
                Date d2 = rs.getDate("mod_date1");
                if(d2!=null && tt<d2.getTime()) {
                     invoice.mLastActivityDate = d2;
                }
                if(nextInvFl) {
                    invoice.mMiscChargesAccepted = rs.getBigDecimal("misc_charges");
                    invoice.mFreightChargesAccepted = rs.getBigDecimal("freight");
                    invoice.mTaxAccepted = rs.getBigDecimal("sales_tax");
                }
                invoice.mOrderAmt = orderAmt;
                //invoice.mNote ="duplicate invoice";
            }
            rs.close();
            stmt.close();
            
            DBCriteria dbc = new DBCriteria();            
            dbc.addOneOf(OrderPropertyDataAccess.INVOICE_DIST_ID,invDistIdV);
            LinkedList venorChargeTypeLL = new LinkedList();
            venorChargeTypeLL.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_FREIGHT);
            venorChargeTypeLL.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_MISC_CHARGE);
            venorChargeTypeLL.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TAX);
            dbc.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,venorChargeTypeLL);
            dbc.addOrderBy(OrderPropertyDataAccess.ORDER_ID);
            dbc.addOrderBy(OrderPropertyDataAccess.INVOICE_DIST_ID);
            OrderPropertyDataVector orderPropertyDV = 
                OrderPropertyDataAccess.select(con,dbc);
            
            /*** Fetch Invoice Note (new report column) for the Invoice which INVOICE_DIST_ID = invDistIdV ***/
            //log("01012010_1: invDistIdV = " + invDistIdV);
            dbc = new DBCriteria();           
            dbc.addOneOf(OrderPropertyDataAccess.INVOICE_DIST_ID, invDistIdV); 
            dbc.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, "Invoice Matching Note");
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            dbc.addOrderBy(OrderPropertyDataAccess.INVOICE_DIST_ID);
            dbc.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID);
            OrderPropertyDataVector orderPropertyDVNote = 
                OrderPropertyDataAccess.select(con,dbc);
            /*****************************************************************************/
            
            prevInvoiceDistId = -1;
            InvoiceLine firstLine = null;
            nextInvFl = false;
            OrderPropertyData wrkOrderPropD = null;
            for(Iterator iter=invoiceLL.iterator(),
                         iter1=orderPropertyDV.iterator();  iter.hasNext();) {
                InvoiceLine invLine = (InvoiceLine) iter.next();
                int invoiceDistId = invLine.mInvoiceDistId;
                if(prevInvoiceDistId==invoiceDistId) {
                    if(firstLine.mTotalVarience==null ||
                       invLine.mLineAmountAccepted==null || 
                       invLine.mLineAmountRequested==null){
                     //   firstLine.mTotalVarience = null;
                    } else {
                        firstLine.mTotalVarience = 
                            firstLine.mTotalVarience.add(invLine.mLineAmountRequested);
                        firstLine.mTotalVarience = 
                            firstLine.mTotalVarience.subtract(invLine.mLineAmountAccepted);
                    }
                    continue;
                }
                firstLine = invLine;
                firstLine.mTotalVarience = firstLine.mLineAmountRequested;
                if(firstLine.mTotalVarience!=null && firstLine.mLineAmountAccepted!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.subtract(firstLine.mLineAmountAccepted);
                }
                if(firstLine.mTotalVarience!=null && firstLine.mFreightChargesPaid!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.add(firstLine.mFreightChargesPaid);
                }
                if(firstLine.mTotalVarience!=null && firstLine.mFreightChargesAccepted!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.subtract(firstLine.mFreightChargesAccepted);
                }
                if(firstLine.mTotalVarience!=null && firstLine.mMiscChargesPaid!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.add(firstLine.mMiscChargesPaid);
                }
                if(firstLine.mTotalVarience!=null && firstLine.mMiscChargesAccepted!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.subtract(firstLine.mMiscChargesAccepted);
                }
                if(firstLine.mTotalVarience!=null && firstLine.mTaxRequested!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.add(firstLine.mTaxRequested);
                }
                if(firstLine.mTotalVarience!=null && firstLine.mTaxAccepted!=null) {
                    firstLine.mTotalVarience = 
                        firstLine.mTotalVarience.subtract(firstLine.mTaxAccepted);
                }
                prevInvoiceDistId = invoiceDistId;
                int orderId = invLine.mOrderId;
                while(wrkOrderPropD!=null || iter1.hasNext()) {
                    if(wrkOrderPropD==null) {
                        wrkOrderPropD = (OrderPropertyData) iter1.next();
                    }
                    int propOrderId = wrkOrderPropD.getOrderId();
                    int propInvDistId = wrkOrderPropD.getInvoiceDistId();
                    if(orderId==propOrderId && invoiceDistId == propInvDistId) {
                        String type = wrkOrderPropD.getOrderPropertyTypeCd();
                        BigDecimal valBD = null;
                        String valS = wrkOrderPropD.getValue();
                        try {
                            double valDb = Double.parseDouble(valS);
                            valBD = new BigDecimal(valDb).setScale(2,BigDecimal.ROUND_HALF_UP);
                        } catch (Exception exc) {}
                        if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TAX
                                              .equals(type)) {
                            invLine.mTaxRequested = valBD;
                        } else if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_MISC_CHARGE
                                              .equals(type)) {
                            invLine.mMiscChargesPaid = valBD;
                        } else if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_FREIGHT
                                              .equals(type)) {
                            invLine.mFreightChargesPaid = valBD;                            
                        }
                        if(valBD!=null) {
                            invLine.mTotalVarience = invLine.mTotalVarience.add(valBD);
                        }
                        wrkOrderPropD = null;
                        continue;
                    } 
                    if(orderId > propOrderId) {
                        wrkOrderPropD = null;
                        continue;
                    }
                    if(orderId < propOrderId) {
                        break;
                    }  
                    if(invoiceDistId > propInvDistId) {
                        wrkOrderPropD = null;
                        continue;
                    }
                    break;                       
                } //end while  
            } //end for  
            
            /*** fill out the "Note" element in the invoiceLL  ***/
            OrderPropertyData wrk2OrderPropD = null;
            for(Iterator iter2=invoiceLL.iterator(); iter2.hasNext();) {
                InvoiceLine invLine1 = (InvoiceLine) iter2.next();
                int invoiceDistId1 = invLine1.mInvoiceDistId;
                //loop through all elements of the orderPropertyDVNote ArrayList, which stores the 
                //invDistIds with the Notes
                for(Iterator iter3=orderPropertyDVNote.iterator();  iter3.hasNext();) {
                    	wrk2OrderPropD = (OrderPropertyData) iter3.next();
                    	if (invoiceDistId1 == wrk2OrderPropD.getInvoiceDistId()) {
                    		 // "Note" report element will always show the latest(last)
                    		 // Note entered for the invoice
                    		 invLine1.mNote = wrk2OrderPropD.getValue();
                    	}
                } //end for                
            } //end for
            


        /////////////////////////////////InvoiceLines//////////////////////////////////////////
        {
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            Iterator iter=invoiceLines.iterator();
            HashSet hs=new HashSet();
            for (Iterator iter1=invoiceLL.iterator(); iter1.hasNext();) {
            	InvoiceLine invoice = (InvoiceLine) iter1.next();
            	result.getTable().add(invoice.toInvoiceList());
            }
            result.setWidth(TABLE_WIDTH);
            //log("invoice report) table width = " + result.getWidth());
        	result.setTitle( getReportTitle(con, pReportData.getName(), pParams));
        	result.setHeader(getInvLineReportHeader());
        	//log("invoice report header = " + result.getHeader());
        	result.setColumnCount(getInvLineReportHeader().size());
        	//log("invoice report column count = " + result.getColumnCount());
        	result.setName("Invoice Lines"); // length - maximum 30 letters
        	result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        	result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        	HashMap userStyles = createInvReportStyleDescriptor();
            result.setUserStyle(userStyles);
        	result.setFreezePositionRow(result.getTitle().size()+2);
        	resultV.add(result);

        }
        /////////////////////////////////////POLines////////////////////////////////////////////
      
        {
        	GenericReportResultView result = GenericReportResultView.createValue();
        	result.setTable(new ArrayList());
        	Iterator iter=allPoLineLL.iterator();
        	while(iter.hasNext()){
        		POLine po = (POLine) iter.next();
        		if(po.mQtyOpen > 0){
        			result.getTable().add(po.toPOList());
        		}
        	}
        	result.setWidth(TABLE_WIDTH);
        	result.setTitle( getReportTitle(con, pReportData.getName(), pParams));
        	result.setHeader(getPOLineReportHeader());
        	result.setColumnCount(getPOLineReportHeader().size());
        	result.setName("Open Po Lines"); // length - maximum 30 letters
        	result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        	result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        	HashMap userStyles = createPOReportStyleDescriptor();
            result.setUserStyle(userStyles);
        	result.setFreezePositionRow(result.getTitle().size()+2);
        	resultV.add(result);
        }
        
        return resultV;
    }

    private String translateInvoiceStatusCd(String string, Hashtable translateTable) {
        if(translateTable==null)   return string;
        if(!translateTable.containsKey(string))  return string;
        return (String)translateTable.get(string);

    }
    
    protected HashMap createPOReportStyleDescriptor(){

          GenericReportStyleView colSysPONum = new GenericReportStyleView("COL_SYSPONUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[0],0 );
          GenericReportStyleView colCustPONum = new GenericReportStyleView("COL_CUSTPONUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[1],0 );
          GenericReportStyleView colPODate = new GenericReportStyleView("COL_PODATE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[2],0 );
          GenericReportStyleView colVendorName = new GenericReportStyleView("COL_VENDORNAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[3],0 );
          GenericReportStyleView colAcctName = new GenericReportStyleView("COL_ACCTNAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[4],0 );
          GenericReportStyleView colCC = new GenericReportStyleView("COL_CC","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[5],0 );
          GenericReportStyleView colSiteName = new GenericReportStyleView("COL_SITENAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[6],0 );
          GenericReportStyleView colAddr = new GenericReportStyleView("COL_ADDR","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[7],0 );
          GenericReportStyleView colCity = new GenericReportStyleView("COL_CITY","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[8],0 );
          GenericReportStyleView colZip = new GenericReportStyleView("COL_ZIP","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[9],0 );
          GenericReportStyleView colSysSku = new GenericReportStyleView("COL_SYSSKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[10],0 );
          GenericReportStyleView colPOVendorSku = new GenericReportStyleView("COL_POVENDORSKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[11],0 );
          GenericReportStyleView colUom = new GenericReportStyleView("COL_UOM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[12],0 );
          GenericReportStyleView colPack = new GenericReportStyleView("COL_PACK","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[13],0 );
          GenericReportStyleView colExpCodeName = new GenericReportStyleView("COL_EXPCODE_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[14],0 );
          GenericReportStyleView colExpCode = new GenericReportStyleView("COL_EXPCODE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[15],0 );
          GenericReportStyleView colItemDesc = new GenericReportStyleView("COL_ITEMDESC","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[16],0 );
          GenericReportStyleView colQtyOrd = new GenericReportStyleView("COL_QTYORD",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[17],0 );
          GenericReportStyleView colQtyOpen = new GenericReportStyleView("COL_QTYOPEN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[18],0 );
          GenericReportStyleView colOrdAmt = new GenericReportStyleView("COL_ORDAMT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,PO_COL_WIDTH[19],0 );
          
          GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
          reportDesc.setUserStyle(colSysPONum.getStyleName(), colSysPONum);
          reportDesc.setUserStyle(colCustPONum.getStyleName(), colCustPONum);
          reportDesc.setUserStyle(colPODate.getStyleName(), colPODate);
          reportDesc.setUserStyle(colVendorName.getStyleName(), colVendorName);
          reportDesc.setUserStyle(colAcctName.getStyleName(), colAcctName);
          reportDesc.setUserStyle(colCC.getStyleName(), colCC);
          reportDesc.setUserStyle(colSiteName.getStyleName(), colSiteName);
          reportDesc.setUserStyle(colAddr.getStyleName(), colAddr);

          reportDesc.setUserStyle(colCity.getStyleName(), colCity);
          reportDesc.setUserStyle(colZip.getStyleName(), colZip);
          reportDesc.setUserStyle(colSysSku.getStyleName(), colSysSku);
          reportDesc.setUserStyle(colPOVendorSku.getStyleName(), colPOVendorSku);
          reportDesc.setUserStyle(colUom.getStyleName(), colUom);
          reportDesc.setUserStyle(colPack.getStyleName(), colPack);
          reportDesc.setUserStyle(colExpCodeName.getStyleName(), colExpCodeName);
          reportDesc.setUserStyle(colExpCode.getStyleName(), colExpCode);
          reportDesc.setUserStyle(colItemDesc.getStyleName(), colItemDesc);

          reportDesc.setUserStyle(colQtyOrd.getStyleName(), colQtyOrd);
          reportDesc.setUserStyle(colQtyOpen.getStyleName(), colQtyOpen);
          reportDesc.setUserStyle(colOrdAmt.getStyleName(), colOrdAmt);

          HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
          return styleDesc;
      }
    
    private GenericReportColumnViewVector getPOLineReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("System PO Number", "COL_SYSPONUM", null,PO_COL_WIDTH[0]));
        header.add(ReportingUtils.createGenericReportColumnView("Customer PO Number", "COL_CUSTPONUM", null,PO_COL_WIDTH[1]));
        //header.add(ReportingUtils.createGenericReportColumnView("PO Date", "COL_PODATE", null,PO_COL_WIDTH[2]));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "PO Date", 0, 0, "DATE","20",false));
        header.add(ReportingUtils.createGenericReportColumnView("Vendor Name", "COL_VENDORNAME", null,PO_COL_WIDTH[3]));
        header.add(ReportingUtils.createGenericReportColumnView("Account Name", "COL_ACCTNAME", null,PO_COL_WIDTH[4]));
        header.add(ReportingUtils.createGenericReportColumnView("Cost Centre", "COL_CC", null,PO_COL_WIDTH[5]));
        header.add(ReportingUtils.createGenericReportColumnView("Site Name", "COL_SITENAME", null,PO_COL_WIDTH[6]));
        header.add(ReportingUtils.createGenericReportColumnView("Address 1", "COL_ADDR", null,PO_COL_WIDTH[7]));
        header.add(ReportingUtils.createGenericReportColumnView("City", "COL_CITY", null,PO_COL_WIDTH[8]));
        header.add(ReportingUtils.createGenericReportColumnView("Zip", "COL_ZIP", null,PO_COL_WIDTH[9]));
        header.add(ReportingUtils.createGenericReportColumnView("System Sku", "COL_SYSSKU", null,PO_COL_WIDTH[10]));
        header.add(ReportingUtils.createGenericReportColumnView("PO Vendor Sku", "COL_POVENDORSKU", null,PO_COL_WIDTH[11]));
        header.add(ReportingUtils.createGenericReportColumnView("Vendor UOM", "COL_UOM", null,PO_COL_WIDTH[12]));
        header.add(ReportingUtils.createGenericReportColumnView("Vendor Pack", "COL_PACK", null,PO_COL_WIDTH[13]));
        header.add(ReportingUtils.createGenericReportColumnView("Expense Code Name", "COL_EXPCODE_NAME", null,PO_COL_WIDTH[14]));
        header.add(ReportingUtils.createGenericReportColumnView("Expense Code", "COL_EXPCODE", null,PO_COL_WIDTH[15]));
        header.add(ReportingUtils.createGenericReportColumnView("Item Short Desc", "COL_ITEMDESC", null,PO_COL_WIDTH[16]));
        header.add(ReportingUtils.createGenericReportColumnView("Qty Ordered", "COL_QTYORD", null,PO_COL_WIDTH[17]));
        header.add(ReportingUtils.createGenericReportColumnView("Qty Open", "COL_QTYOPEN", null,PO_COL_WIDTH[18]));
        header.add(ReportingUtils.createGenericReportColumnView("Open Line Amount", "COL_ORDERAMT", null,PO_COL_WIDTH[19]));
        return header;
    }
    
    protected HashMap createInvReportStyleDescriptor(){

    	GenericReportStyleView colInvNum = new GenericReportStyleView("COL_INVNUM",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[0],0 );
    	GenericReportStyleView colInvDate = new GenericReportStyleView("COL_INVDATE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[1],0 );
    	GenericReportStyleView colSysPONum = new GenericReportStyleView("COL_SYSPONUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[2],0 );
    	GenericReportStyleView colCustPONum = new GenericReportStyleView("COL_CUSTPONUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[3],0 );
    	GenericReportStyleView colVendorName = new GenericReportStyleView("COL_VENDORNAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[4],0 );
        GenericReportStyleView colAcctName = new GenericReportStyleView("COL_ACCTNAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[5],0 );
        GenericReportStyleView colCC = new GenericReportStyleView("COL_CC","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[6],0 );
        GenericReportStyleView colSiteName = new GenericReportStyleView("COL_SITENAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[7],0 );
        GenericReportStyleView colAddr = new GenericReportStyleView("COL_ADDR","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[8],0 );
        GenericReportStyleView colCity = new GenericReportStyleView("COL_CITY","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[9],0 );
        GenericReportStyleView colZip = new GenericReportStyleView("COL_ZIP","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[10],0 );
        GenericReportStyleView colSysSku = new GenericReportStyleView("COL_SYSSKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[11],0 );
        GenericReportStyleView colPOVendorSku = new GenericReportStyleView("COL_POVENDORSKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[12],0 );
        GenericReportStyleView colInvVendorSku = new GenericReportStyleView("COL_INVVENDORSKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[13],0 );
        GenericReportStyleView colUom = new GenericReportStyleView("COL_UOM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[14],0 );
        GenericReportStyleView colPack = new GenericReportStyleView("COL_PACK","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[15],0 );
        GenericReportStyleView colExpCodeName = new GenericReportStyleView("COL_EXPCODE_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[16],0 );
        GenericReportStyleView colExpCode = new GenericReportStyleView("COL_EXPCODE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[17],0 );
        GenericReportStyleView colItemDesc = new GenericReportStyleView("COL_ITEMDESC","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[18],0 );
        GenericReportStyleView colStatus = new GenericReportStyleView("COL_STATUS","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[19],0 );
        GenericReportStyleView colLineAmtAccepted = new GenericReportStyleView("COL_LINEAMTACCEPTED",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[20],0 );
        GenericReportStyleView colLineAmtReq = new GenericReportStyleView("COL_LINEAMTREQ",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[21],0 );
        GenericReportStyleView colQtyAccepted = new GenericReportStyleView("COL_QTYACCEPTED",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[22],0 );
        GenericReportStyleView colQtyReq = new GenericReportStyleView("COL_QTYREQ",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[23],0 );
        GenericReportStyleView colLastActDate = new GenericReportStyleView("COL_LASTACTDATE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[24],0 );
        GenericReportStyleView colTaxAccepted = new GenericReportStyleView("COL_TAXACCEPTED",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[25],0 );
        GenericReportStyleView colTaxReq = new GenericReportStyleView("COL_TAXREQ",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[26],0 );
        GenericReportStyleView colOCAccepted = new GenericReportStyleView("COL_OCACCEPTED",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[27],0 );
        GenericReportStyleView colOCPaid = new GenericReportStyleView("COL_OCPAID",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[28],0 );
        GenericReportStyleView colFCAccepted = new GenericReportStyleView("COL_FCACCEPTED",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[29],0 );
        GenericReportStyleView colFCPaid = new GenericReportStyleView("COL_FCPAID",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[30],0 );
        GenericReportStyleView colTotVar = new GenericReportStyleView("COL_TOTVAR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[31],0 );
        GenericReportStyleView colOrderAmt = new GenericReportStyleView("COL_ORDERAMT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[32],0 );
        GenericReportStyleView colNote = new GenericReportStyleView("COL_NOTE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,INV_COL_WIDTH[30],0 );           
        
        GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
        reportDesc.setUserStyle(colInvNum.getStyleName(), colInvNum);
        reportDesc.setUserStyle(colInvDate.getStyleName(), colInvDate);
        reportDesc.setUserStyle(colSysPONum.getStyleName(), colSysPONum);
        reportDesc.setUserStyle(colCustPONum.getStyleName(), colCustPONum);
        reportDesc.setUserStyle(colVendorName.getStyleName(), colVendorName);
        reportDesc.setUserStyle(colAcctName.getStyleName(), colAcctName);
        reportDesc.setUserStyle(colCC.getStyleName(), colCC);
        reportDesc.setUserStyle(colSiteName.getStyleName(), colSiteName);
        reportDesc.setUserStyle(colAddr.getStyleName(), colAddr);
        reportDesc.setUserStyle(colCity.getStyleName(), colCity);
        reportDesc.setUserStyle(colZip.getStyleName(), colZip);     
        reportDesc.setUserStyle(colSysSku.getStyleName(), colSysSku);
        reportDesc.setUserStyle(colPOVendorSku.getStyleName(), colPOVendorSku); 
        //reportDesc.setUserStyle(colInvVendorSku.getStyleName(), colInvVendorSku); // SVC: new stmt
        reportDesc.setUserStyle(colUom.getStyleName(), colUom);
        reportDesc.setUserStyle(colPack.getStyleName(), colPack);
        reportDesc.setUserStyle(colExpCodeName.getStyleName(), colExpCodeName);
        reportDesc.setUserStyle(colExpCode.getStyleName(), colExpCode);
        reportDesc.setUserStyle(colItemDesc.getStyleName(), colItemDesc);     
        reportDesc.setUserStyle(colStatus.getStyleName(), colStatus);
        reportDesc.setUserStyle(colLineAmtAccepted.getStyleName(), colLineAmtAccepted);
        reportDesc.setUserStyle(colLineAmtReq.getStyleName(), colLineAmtReq);
        reportDesc.setUserStyle(colQtyAccepted.getStyleName(), colQtyAccepted);
        reportDesc.setUserStyle(colQtyReq.getStyleName(), colQtyReq);
        reportDesc.setUserStyle(colLastActDate.getStyleName(), colLastActDate);      
        reportDesc.setUserStyle(colTaxAccepted.getStyleName(), colTaxAccepted);
        reportDesc.setUserStyle(colTaxReq.getStyleName(), colTaxReq);
        reportDesc.setUserStyle(colOCAccepted.getStyleName(), colOCAccepted);    
        reportDesc.setUserStyle(colOCPaid.getStyleName(), colOCPaid);
        reportDesc.setUserStyle(colFCAccepted.getStyleName(), colFCAccepted);
        reportDesc.setUserStyle(colFCPaid.getStyleName(), colFCPaid);    
        reportDesc.setUserStyle(colTotVar.getStyleName(), colTotVar);
        reportDesc.setUserStyle(colOrderAmt.getStyleName(), colOrderAmt);
        reportDesc.setUserStyle(colNote.getStyleName(), colNote);
        
        HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
        return styleDesc;
    }
  
  private GenericReportColumnViewVector getInvLineReportHeader() {
      GenericReportColumnViewVector header = new GenericReportColumnViewVector();
      
      header.add(ReportingUtils.createGenericReportColumnView("Invoice Number", "COL_INVNUM", null,INV_COL_WIDTH[0]));
      //header.add(ReportingUtils.createGenericReportColumnView("Invoice Date", "COL_INVDATE", null,INV_COL_WIDTH[1]));
      header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Invoice Date", 0, 0, "DATE","30",false));
      header.add(ReportingUtils.createGenericReportColumnView("System PO Number", "COL_SYSPONUM", null,INV_COL_WIDTH[2]));
      header.add(ReportingUtils.createGenericReportColumnView("Customer PO Number", "COL_CUSTPONUM", null,INV_COL_WIDTH[3]));
      header.add(ReportingUtils.createGenericReportColumnView("Vendor Name", "COL_VENDORNAME", null,INV_COL_WIDTH[4]));
      header.add(ReportingUtils.createGenericReportColumnView("Account Name", "COL_ACCTNAME", null,INV_COL_WIDTH[5]));
      header.add(ReportingUtils.createGenericReportColumnView("Cost Centre", "COL_CC", null,INV_COL_WIDTH[6]));
      header.add(ReportingUtils.createGenericReportColumnView("Site Name", "COL_SITENAME", null,INV_COL_WIDTH[7]));
      header.add(ReportingUtils.createGenericReportColumnView("Address 1", "COL_ADDR", null,INV_COL_WIDTH[8]));
      header.add(ReportingUtils.createGenericReportColumnView("City", "COL_CITY", null,INV_COL_WIDTH[9]));
      header.add(ReportingUtils.createGenericReportColumnView("Zip", "COL_ZIP", null,INV_COL_WIDTH[10]));
      header.add(ReportingUtils.createGenericReportColumnView("System Sku", "COL_SYSSKU", null,INV_COL_WIDTH[11]));
      header.add(ReportingUtils.createGenericReportColumnView("PO Vendor Sku", "COL_POVENDORSKU", null,INV_COL_WIDTH[12]));
      header.add(ReportingUtils.createGenericReportColumnView("Inv Vendor Sku", "COL_INVVENDORSKU", null,INV_COL_WIDTH[13]));
      header.add(ReportingUtils.createGenericReportColumnView("Vendor UOM", "COL_UOM", null,INV_COL_WIDTH[14]));
      header.add(ReportingUtils.createGenericReportColumnView("Vendor Pack", "COL_PACK", null,INV_COL_WIDTH[15]));
      header.add(ReportingUtils.createGenericReportColumnView("Expense Code Name", "COL_EXPCODE_NAME", null,INV_COL_WIDTH[16]));
      header.add(ReportingUtils.createGenericReportColumnView("Expense Code", "COL_EXPCODE", null,INV_COL_WIDTH[17]));
      header.add(ReportingUtils.createGenericReportColumnView("Item Short Desc", "COL_ITEMDESC", null,INV_COL_WIDTH[18]));
      
      header.add(ReportingUtils.createGenericReportColumnView("Status", "COL_STATUS", null,INV_COL_WIDTH[19]));
      header.add(ReportingUtils.createGenericReportColumnView("Line Amount Accepted", "COL_LINEAMTACCEPTED", null,INV_COL_WIDTH[20]));
      header.add(ReportingUtils.createGenericReportColumnView("Line Amount Requested", "COL_LINEAMTREQ", null,INV_COL_WIDTH[21]));
      header.add(ReportingUtils.createGenericReportColumnView("Qty Accepted", "COL_QTYACCEPTED", null,INV_COL_WIDTH[22]));
      
      header.add(ReportingUtils.createGenericReportColumnView("Qty Requested", "COL_QTYREQ", null,INV_COL_WIDTH[23]));
      //header.add(ReportingUtils.createGenericReportColumnView("Last Activity Date", "COL_LASTACTDATE", null,INV_COL_WIDTH[23]));
      header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Last Activity Date", 0, 0, "DATE","20",false));
      header.add(ReportingUtils.createGenericReportColumnView("Tax Accepted", "COL_TAXACCEPTED", null,INV_COL_WIDTH[25]));
      header.add(ReportingUtils.createGenericReportColumnView("Tax Requested", "COL_TAXREQ", null,INV_COL_WIDTH[26]));
      
      header.add(ReportingUtils.createGenericReportColumnView("Other Charges Accepted", "COL_OCACCEPTED", null,INV_COL_WIDTH[27]));
      header.add(ReportingUtils.createGenericReportColumnView("Other Charges Paid", "COL_OCPAID", null,INV_COL_WIDTH[28]));
      header.add(ReportingUtils.createGenericReportColumnView("Freight Charges Accepted", "COL_FCACCEPTED", null,INV_COL_WIDTH[29]));
      header.add(ReportingUtils.createGenericReportColumnView("Freight Charges Paid", "COL_FCPAID", null,INV_COL_WIDTH[30]));
      
      header.add(ReportingUtils.createGenericReportColumnView("Total Variance", "COL_TOTVAR", null,INV_COL_WIDTH[31]));
      header.add(ReportingUtils.createGenericReportColumnView("Order Amount", "COL_ORDERAMT", null,INV_COL_WIDTH[32]));
      header.add(ReportingUtils.createGenericReportColumnView("Note", "COL_NOTE", null,INV_COL_WIDTH[30]));
      
      return header;
  }
  
    private class BaseLine {
        String mVendorName;
        String mAccountName;
        String mCostCentre;
        String mSiteName;
        String mAddress1;
        String mAddress2;
        String mCity;
        String mZip;
        int mSystemSKU;
        String mPoVendorSKU;
        String mInvVendorSKU;
        String mVendorUOM;
        String mVendorPack;
        String mExpenseCodeName;
        String mExpenseCode;
        String mItemShortDesc;
        int mInvoiceDistId;
        int mOrderId;
        int mConvMult;

        public ArrayList toEmptyList() {
            ArrayList list = new ArrayList();
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            return list;
        }

        public ArrayList toList(boolean isPOLines) {
            ArrayList list = new ArrayList();
            list.add(mVendorName);
            list.add(mAccountName);
            list.add(mCostCentre);
            list.add(mSiteName);
            list.add(mAddress1);
            list.add(mCity);
            list.add(mZip);
            list.add(new Integer(mSystemSKU));
            list.add(mPoVendorSKU);
            if(!isPOLines){
            	list.add(mInvVendorSKU);
            }
            list.add(mVendorUOM);
            list.add(mVendorPack);
            list.add(mExpenseCodeName);
            list.add(mExpenseCode);
            list.add(mItemShortDesc);
            return list;
        }
    }

    private class POLine extends BaseLine {
        String mPONumber;
		String mPurchaseOrderNumber;
        Date mPODate;
        int mQtyOrdered;
        int mQtyOpen;
        BigDecimal mOrderAmt;

        public ArrayList toEmptyPOList() {
            ArrayList list = new ArrayList();
            list.add(null);
            list.add(null);
            list.addAll(this.toEmptyList());
            list.add(null);
            list.add(null);
            list.add(null);
            return list;
        }

        public ArrayList toPOList() {
            ArrayList list = new ArrayList();
            list.add(mPONumber);
			list.add(mPurchaseOrderNumber);
            list.add(mPODate);
            list.addAll(this.toList(true));
            list.add(new Integer(mQtyOrdered));
            list.add(new Integer(mQtyOpen));
            list.add(mOrderAmt);
            return list;
        }

    }

    private class InvoiceLine extends BaseLine {
        String mStatusCd;
        String mErpPoNum;
		String mPurchaseOrderNumber;
        BigDecimal mLineCostAccepted;
        BigDecimal mLineCostRequested;
        BigDecimal mLineAmountAccepted;
        BigDecimal mLineAmountRequested;
        int mQtyRequested;
        int mQtyAccepted;
        Date mLastActivityDate;
        String mInvoice;
        Date mInvoiceDate;
        BigDecimal mTaxAccepted;
        BigDecimal mTaxRequested;
        BigDecimal mMiscChargesAccepted;
        BigDecimal mMiscChargesPaid;
        BigDecimal mFreightChargesAccepted;
        BigDecimal mFreightChargesPaid;
        BigDecimal mTotalVarience;
        BigDecimal mOrderAmt;
        
        String mNote; //SVC: new stmt
        

        public ArrayList toEmptyInvoiceList() {
            ArrayList list = new ArrayList();

            list.addAll(this.toEmptyList());
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            return list;
        }

        public ArrayList toInvoiceList() {
            ArrayList list = new ArrayList();
            list.add(mInvoice);
            list.add(mInvoiceDate);
            list.add(mErpPoNum);
			list.add(mPurchaseOrderNumber);
            list.addAll(this.toList(false));
            list.add(mStatusCd);
            list.add(mLineAmountAccepted);
            list.add(mLineAmountRequested);
            list.add(new Integer(mQtyAccepted));
            list.add(new Integer(mQtyRequested));
            list.add(mLastActivityDate);
            list.add(mTaxAccepted);
            list.add(mTaxRequested);
            list.add(mMiscChargesAccepted);
            list.add(mMiscChargesPaid);
            list.add(mFreightChargesAccepted);
            list.add(mFreightChargesPaid);
            list.add(mTotalVarience);
            list.add(mOrderAmt);
            list.add(mNote); // new stmt
            
            return list;
        }

        public void addOrderPropertyLine(OrderPropertyLine opl) {
        mTaxAccepted=opl.mTaxAccepted;
        mTaxRequested=opl.mTaxRequested;
        mMiscChargesAccepted=opl.mMiscChargesAccepted;
        mMiscChargesPaid=opl.mMiscChargesPaid;
        mFreightChargesAccepted=opl.mFreightChargesAccepted;
        mFreightChargesPaid=opl.mFreightChargesPaid;
        mTotalVarience=opl.calculateTotalVariance();
        }
        }

    private class OrderPropertyLine
    {
        String mInvoiceNum;
        BigDecimal mTaxAccepted;
        BigDecimal mTaxRequested;
        BigDecimal mMiscChargesAccepted;
        BigDecimal mMiscChargesPaid;
        BigDecimal mFreightChargesAccepted;
        BigDecimal mFreightChargesPaid;
        BigDecimal mTotalVarience;

        public ArrayList toOrderPropertyList() {
            ArrayList list = new ArrayList();
            list.add(mTaxAccepted);
            list.add(mTaxRequested);
            list.add(mMiscChargesAccepted);
            list.add(mMiscChargesPaid);
            list.add(mFreightChargesAccepted);
            list.add(mFreightChargesPaid);
            list.add(mTotalVarience);
            return list;
        }

        public ArrayList toEmptyOrderPropertyList() {
            ArrayList list = new ArrayList();
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            return list;
        }

        public BigDecimal calculateTotalVariance() {
        
            return (mTaxAccepted.add(mMiscChargesAccepted.add(mFreightChargesAccepted)))
                    .subtract(mTaxRequested.add(mMiscChargesPaid.add(mFreightChargesPaid)));

        }
    }

    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
    	GenericReportColumnViewVector title = new GenericReportColumnViewVector();
    	title.add(ReportingUtils.createGenericReportColumnView(pTitle,ReportingUtils.PAGE_TITLE, null,"10"));
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store : " + storeName,0,255,"VARCHAR2"));
        String controlName = null;
        if (pParams.containsKey("BEG_DATE_OPT") && Utility.isSet((String) pParams.get("BEG_DATE_OPT")) &&
            pParams.containsKey("END_DATE_OPT") && Utility.isSet((String) pParams.get("END_DATE_OPT"))){
        	title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get("BEG_DATE_OPT") + " - " + (String) pParams.get("END_DATE_OPT"),ReportingUtils.PAGE_TITLE, null,"10"));
        } 
        return title;
    }
    
    
    private String getDateCriteria(String crit, String begDateStr, String endDateStr) {
        if (Utility.isSet(begDateStr) && Utility.isSet(endDateStr)) {
            return " AND "+crit+" BETWEEN " + ReportingUtils.toSQLDate(begDateStr) + " AND " + ReportingUtils.toSQLDate(endDateStr);
        } else if (Utility.isSet(begDateStr)) {
            return " AND "+crit+" >= " + ReportingUtils.toSQLDate(begDateStr);
        } else if (Utility.isSet(endDateStr)) {
            return " AND "+crit+" <= " + ReportingUtils.toSQLDate(endDateStr);
        } else {
            return "";
        }
    }


    public class GenericReportUserStyleDesc extends ValueObject {
    	   HashMap mReportUserStyle;

    	   public GenericReportUserStyleDesc ()
    	   {
    	     mReportUserStyle = new HashMap();
    	   }

    	   public void setUserStyle ( String pUserStyleType, GenericReportStyleView pPageTitleStyle){
    	     if (mReportUserStyle == null){
    	       mReportUserStyle = new HashMap();
    	     }
    	     mReportUserStyle.put(pUserStyleType, pPageTitleStyle);
    	   }
    	   public GenericReportStyleView getUserStyle(String pUserStyleType){
    	      return ((mReportUserStyle != null) ? (GenericReportStyleView)mReportUserStyle.get(pUserStyleType) : null);
    	   }
    	   public HashMap getGenericReportUserStyleDesc(){
    	     return mReportUserStyle;
    	   }

    	   public void setGenericReportUserStyleDesc (HashMap pReportUserStyle){
    	     mReportUserStyle = pReportUserStyle;
    	   }
    	  }
}
