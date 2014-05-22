/*
 * VendorReconciliationReport.java
 *
 *
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.io.PrintWriter;

public class VendorReconciliationReport implements GenericReportMulti {

    private static String className="VendorReconciliationReport";
    private static final String INVOICE_SEPARATOR = ",";


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        Statement stmt;
        ResultSet rs;

        String begDateS = (String) pParams.get("B_DATE");
        String endDateS = (String) pParams.get("E_DATE");
        String userIdsStr = (String) pParams.get("CUSTOMER");
        String invoiceNums=(String)pParams.get("INVOICE_MULTI_OPT");

        if(!Utility.isSet(invoiceNums)) {
            if (!Utility.isSet(begDateS)) {
                String mess = "^clw^Begin Date is not set^clw^";
                throw new Exception(mess);
            }
            if (!Utility.isSet(endDateS)) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                endDateS = sdf.format(new java.util.Date());
            }
        }
        if (userIdsStr != null) {
            try {
                IdVector userIds = Utility.parseIdStringToVector(userIdsStr, ",");
            }
            catch (Exception e) {
                String mess = "^clw^" + "Wrong user ids format" + "^clw^";
                throw new Exception(mess);
            }

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

        if(userIdsStr!=null && userIdsStr.trim().length()==0)
        {
         userIdsStr=null;   
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

        sql = "select "+
                " o.order_id, "+
                " po.erp_po_num, "+
				" o.request_po_num, "+
                " po.po_date, "+
                " dist.short_desc as vendor_name, "+
                " acc.short_desc as account_name, "+
                " site.short_desc as site_name, "+
                " oa.address1, "+
				" oa.address2, "+
                " oa.city, "+
				" oa.state_province_cd, "+
                " oa.postal_code, "+
                " oi.item_sku_num as system_sku, "+
                " oi.dist_item_sku_num as po_vendor_sku, "+
                " idd.dist_item_sku_num as inv_vendor_sku, "+
                " oi.dist_item_uom, "+
				" oi.dist_item_pack, "+
                " oi.item_short_desc, "+
                " oi.total_quantity_ordered, "+
                " oi.dist_uom_conv_multiplier, "+
                " idd.dist_item_qty_received, "+
                " id.invoice_num, "+
                " id.invoice_status_cd "+
                "  from  "+
            " clw_purchase_order po, "+
            " clw_invoice_dist id, "+
            " clw_invoice_dist_detail idd, "+
            " clw_bus_entity dist, "+
            " clw_user_assoc ua, "+
            " clw_bus_entity acc, "+
            " clw_bus_entity site, "+
            " clw_order o, "+
            " clw_order_item oi, "+
			" clw_order_address oa "+
                "  where 1=1  "+
             ((invoiceNumsList==null)? 
                (" and po.po_date between  "+
                     " to_date('"+begDateS+"' ,'mm/dd/yyyy') and to_date('"+endDateS+"' ,'mm/dd/yyyy') "):
                (" and po.erp_po_num in ("+poNumList+")"))+
             " and po.purchase_order_id = oi.purchase_order_id  "+
             " and po.purchase_order_status_cd not in ('"+RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED+"')"+
             " and oi.order_item_status_cd not in ('"+RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED+"')"+
             " and oi.dist_erp_num = dist.erp_num "+
			 " and o.order_id = oa.order_id "+
			 " and oa.address_type_cd = '"+RefCodeNames.ADDRESS_TYPE_CD.SHIPPING+"' "+
             " and ua.bus_entity_id = dist.bus_entity_id  "+
             " and ua.user_id = "+userIdsStr+            
             " and o.order_id = po.order_id  "+
             " and o.site_id = site.bus_entity_id  "+
             " and o.account_id = acc.bus_entity_id  "+
             " and oi.order_item_id = idd.order_item_id (+)  "+
             " and idd.invoice_dist_id = id.invoice_dist_id(+)  "+
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
                    poLine.mInvoices += ", "+rs.getString("invoice_num");
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
                poLine.mSiteName=rs.getString("site_name");
                poLine.mAddress1 =rs.getString("address1");
                poLine.mAddress2 =rs.getString("address2");
                poLine.mCity=rs.getString("city");
                poLine.mState=rs.getString("state_province_cd");
                poLine.mZip=rs.getString("postal_code");
                poLine.mSystemSKU=rs.getInt("system_sku");
                poLine.mPoVendorSKU=rs.getString("po_vendor_sku");
                poLine.mInvVendorSKU=rs.getString("inv_vendor_sku");
                poLine.mVendorUOM=rs.getString("dist_item_uom");
                poLine.mVendorPack=rs.getString("dist_item_pack");
                poLine.mItemShortDesc=rs.getString("item_short_desc");
                poLine.mQtyOrdered=rs.getInt("total_quantity_ordered");
                BigDecimal mult = rs.getBigDecimal("dist_uom_conv_multiplier");
                int qtyReceived = rs.getInt("dist_item_qty_received");
                if(mult!=null && qtyReceived!=0) {
                    int multDb = (int) mult.doubleValue();
                    qtyReceived /= multDb;
                }
                poLine.mQtyOpen=poLine.mQtyOrdered - qtyReceived;
                poLine.mInvoices = rs.getString("invoice_num");
            }
        }
        rs.close();
        stmt.close();

        if(openPoFl || poLine!=null && poLine.mQtyOpen!=0) {
            allPoLineLL.addAll(poLineLL);
        }


       ArrayList invoiceLines = new ArrayList();
       HashMap oplMap = new HashMap();

       sql = "Select " +
           "o.order_id, " +
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
           "id.ship_to_state, " +
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
           "oi.dist_uom_conv_multiplier "+
        "from clw_invoice_dist id, " +
           " clw_bus_entity dist, " +
           " clw_order o, " +
           " clw_order_item oi, "+
           " clw_bus_entity site, " +
           " clw_bus_entity acc,"+
           " clw_invoice_dist_detail idd, " +
           " clw_user_assoc ua"+
        " where 1=1"+
          ((invoiceIdList==null)?
             (" and id.invoice_date between "+
                " to_date('"+begDateS+"' ,'mm/dd/yyyy') and to_date('"+endDateS+"' ,'mm/dd/yyyy') "):
             (" and id.invoice_dist_id in ("+invoiceIdList+")"))+
          " and id.bus_entity_id = dist.bus_entity_id"+
          " and o.order_id = id.order_id"+
          " and idd.order_item_id = oi.order_item_id"+
          " and o.site_id = site.bus_entity_id"+
          " and o.account_id = acc.bus_entity_id"+
          " and id.invoice_dist_id = idd.invoice_dist_id(+)"+
          " and ua.user_assoc_cd = 'DISTRIBUTOR'"+
          " and ua.bus_entity_id = id.bus_entity_id"+
          " and ua.user_id = "+ userIdsStr+
         " order by o.order_id, id.invoice_dist_id, idd.dist_line_number ";

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
                invoice.mInvoiceDistId = invoiceDistId;
                invoice.mOrderId = rs.getInt("order_id");
                invoice.mInvoice = rs.getString("invoice_num");
                invoice.mInvoiceDate = rs.getDate("invoice_date");
				invoice.mErpPoNum = rs.getString("erp_po_num");
				invoice.mPurchaseOrderNumber = rs.getString("request_po_num");
                invoice.mVendorName = rs.getString("vendor");
                invoice.mAccountName = rs.getString("account");
                invoice.mSiteName = rs.getString("site");
                invoice.mAddress1 = rs.getString("ship_to_address_1");
                invoice.mAddress2 = rs.getString("ship_to_address_2");
                invoice.mCity = rs.getString("ship_to_city");
                invoice.mState = rs.getString("ship_to_state");
                invoice.mZip = rs.getString("ship_to_postal_code");
                invoice.mSystemSKU = rs.getInt("item_sku_num");
                invoice.mPoVendorSKU = rs.getString("po_dist_item_sku_num");
                invoice.mInvVendorSKU = rs.getString("inv_dist_item_sku_num");
                invoice.mVendorUOM = rs.getString("dist_item_uom");
                invoice.mVendorPack = rs.getString("dist_item_pack");
                invoice.mItemShortDesc = rs.getString("dist_item_short_desc");
                invoice.mStatusCd = 
                    translateInvoiceStatusCd(rs.getString("invoice_status_cd"), 
                                 invoiceTableStatusCds);
                BigDecimal invCost = rs.getBigDecimal("item_received_cost");
                invoice.mLineCostRequested = invCost;
                BigDecimal stjCost = rs.getBigDecimal("adjusted_cost");
                invoice.mLineCostAccepted = stjCost;
                int invQty = rs.getInt("dist_item_qty_received");
                int stjQty = rs.getInt("dist_item_quantity");    
                BigDecimal mult = rs.getBigDecimal("dist_uom_conv_multiplier");
                invoice.mConvMult = (mult==null)?1:mult.intValue();
                if(invoice.mConvMult==0) poLine.mConvMult = 1;
                int qtyReceived = rs.getInt("dist_item_qty_received");
                invoice.mQtyAccepted = stjQty;
                invoice.mQtyRequested = invQty;
                BigDecimal invAmount = null;
                if(invCost!=null) {
                    invAmount = invCost.multiply(new BigDecimal(invQty));
                    invAmount = invAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                
                BigDecimal stjAmount = null;
                if(stjCost!=null) {
                    stjAmount = stjCost.multiply(new BigDecimal(stjQty));
                    if(invoice.mConvMult!=1) {
                       stjAmount = 
                           stjAmount.multiply(new BigDecimal(invoice.mConvMult));
                    }
                    stjAmount = stjAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                
                invoice.mLineAmountAccepted = stjAmount;
                invoice.mLineAmountRequested = invAmount; 
                
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
                }
            }



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
            GenericReportColumnViewVector invoiceHeader = getInvoiceLineReportHeader();
            result.setColumnCount(invoiceHeader.size());
            result.setHeader(invoiceHeader);
            result.setName("Invoice Lines");
            resultV.add(result);

        }
        /////////////////////////////////////POLines////////////////////////////////////////////
       // multiplyInvoice(poLines,poLinesMap,invoiceMap);
        {
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            Iterator iter=allPoLineLL.iterator();
            while(iter.hasNext())
            {
            POLine po = (POLine) iter.next();
            result.getTable().add(po.toPOList());
            }
            GenericReportColumnViewVector poLineHeader = getPOLineReportHeader();
            result.setColumnCount(poLineHeader.size());
            result.setHeader(poLineHeader);
            result.setName("Open Po Lines");
            resultV.add(result);
        }

        return resultV;
    }

    private String translateInvoiceStatusCd(String string, Hashtable translateTable) {
        if(translateTable==null)   return string;
        if(!translateTable.containsKey(string))  return string;
        return (String)translateTable.get(string);

    }

    private GenericReportColumnViewVector getPOLineReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "System PO Number", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Customer PO Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "PO Date", 0, 0, "DATE"));
        header.addAll(getVendorLineHeader());
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Qty Ordered",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Qty Open",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Invoices", 0, 255, "VARCHAR2"));
        return header;
    }

    private GenericReportColumnViewVector getInvoiceLineReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        //
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Invoice Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Invoice Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "System PO Number", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Customer PO Number", 0, 255, "VARCHAR2"));
        header.addAll(getVendorLineHeader());
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Line Amount Accepted", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Line Amount Requested", 2, 20, "NUMBER", "*", false));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Qty Accepted",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Qty Requested",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Conversion",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Last Activity Date", 0, 0, "DATE"));
        header.addAll(getOrderPropertyHeader());
        return header;
    }

    private GenericReportColumnViewVector getOrderPropertyHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Tax Accepted", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Tax Requested", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Misc Charges Accepted", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Misc Charges Paid", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Freight Charges Accepted", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Freight Charges Paid;", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total Varience", 2, 20, "NUMBER", "*", false));
        return header;
    }

    private GenericReportColumnViewVector getVendorLineHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Vendor Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 1", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Zip", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "System SKU", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Vendor SKU", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Inv Vendor SKU", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Vendor UOM", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Vendor Pack", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Short Desc", 0, 255, "VARCHAR2"));
        return header;
    }


    private class BaseLine {
        String mVendorName;
        String mAccountName;
        String mSiteName;
        String mAddress1;
        String mAddress2;
        String mCity;
        String mState;
        String mZip;
        int mSystemSKU;
        String mPoVendorSKU;
        String mInvVendorSKU;
        String mVendorUOM;
        String mVendorPack;
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

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(mVendorName);
            list.add(mAccountName);
            list.add(mSiteName);
            list.add(mAddress1);
            list.add(mCity);
            list.add(mState);
            list.add(mZip);
            list.add(new Integer(mSystemSKU));
            list.add(mPoVendorSKU);
            list.add(mInvVendorSKU);
            list.add(mVendorUOM);
            list.add(mVendorPack);
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
        String mInvoices;

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
            list.addAll(this.toList());
            list.add(new Integer(mQtyOrdered));
            list.add(new Integer(mQtyOpen));
            list.add(mInvoices);
            return list;
        }
/*
        public String getUniqueKeyMap() {
            return buildUniqueKeyMap(mPONumber,mSystemSKU,mVendorSKU);
        }
        public  String buildUniqueKeyMap(String po,int sysSku,String venSku)
        {
             return po+"@"+sysSku+"@"+venSku;
        }
 */
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
            list.addAll(this.toList());
            list.add(mStatusCd);
            list.add(mLineAmountAccepted);
            list.add(mLineAmountRequested);
            list.add(new Integer(mQtyAccepted));
            list.add(new Integer(mQtyRequested));
            list.add(new Integer(mConvMult));
            list.add(mLastActivityDate);
            list.add(mTaxAccepted);
            list.add(mTaxRequested);
            list.add(mMiscChargesAccepted);
            list.add(mMiscChargesPaid);
            list.add(mFreightChargesAccepted);
            list.add(mFreightChargesPaid);
            list.add(mTotalVarience);
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


}
