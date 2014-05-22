/*
 * JciCustomerInvoiceReport.java
 *
 * Created on August 7, 2003
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.ScheduleProc;
import java.util.Map;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;

/**
 * Generates xls wiht JCI customer invoice date
 * accepts parameters: Min IN Invoice Number, Max IN Invoice Number, 
 * Min CR Invoice Number, Max CR Invoice Number
 */
public class JciCustomerInvoiceReport implements GenericReport {
    protected static final int DETAIL_LEVEL = 1;
    protected static final int HEADER_LEVEL = 2;
    protected int reportDetailLevel = HEADER_LEVEL;
    
    private static final BigDecimal ZERO = new BigDecimal(0);

    /** Creates a new instance of JciCustomerInvoiceReport */
    public JciCustomerInvoiceReport() {
    }
    
    private JciCustInvoiceViewVector getLawsonInvoiceData(Connection pLawCon, String pInvType, int pMinNum, int pMaxNum) 
    throws Exception
    {
      JciCustInvoiceViewVector invoiceVwV = new JciCustInvoiceViewVector();  
      String customerErp = "10051";
      String sql = 
       " SELECT "+
       " TO_CHAR(inv.invc_number) as Invoice_Num, "+
       " inv.invc_prefix as Invoice_Type, "+
       " inv.invoice_date as Invoice_Date, "+
       " invln.line_nbr as Line_Num, "+ 
       " invln.line_grs_curr as Line_Amount, "+ 
       " invln.quantity as Qty, "+ 
       " trim(REPLACE(invln.description,',',' ')) as Item_Name, "+ 
       " trim(invln.item) as Item_Sku, "+ 
       " (inv.non_inv_gds_b + inv.tax_total + inv.misc_total) as Invoice_Amount, "+ 
       " tax_total as Tax, "+ 
       " misc_total as Freight "+
       " FROM oeinvoice inv, oeinvcline invln "+
       " WHERE inv.company=100 AND inv.invc_prefix = '"+pInvType+"' "+
       " AND inv.invc_number BETWEEN "+pMinNum+" AND "+pMaxNum+ 
       " AND inv.customer="+customerErp+
       " AND invln.company = inv.company "+
       " AND invln.invc_number= inv.invc_number "+
       " AND invln.invc_prefix = inv.invc_prefix "+
       " ORDER BY Invoice_Num,Line_Num";

        Statement stmt = pLawCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next() ) {
          JciCustInvoiceView invoice = JciCustInvoiceView.createValue();
          invoice.setInvoiceNum(rs.getString("Invoice_Num"));
          invoice.setInvoiceType(rs.getString("Invoice_Type"));
          invoice.setInvoiceDate(rs.getDate("Invoice_Date"));
          invoice.setLineNum(rs.getInt("Line_Num")); 
          invoice.setLineAmount(rs.getBigDecimal("Line_Amount"));
          invoice.setQty(rs.getInt("Qty"));
          invoice.setItemName(rs.getString("Item_Name"));
          invoice.setItemSku(rs.getString("Item_Sku"));
          invoice.setInvoiceAmount(rs.getBigDecimal("Invoice_Amount"));
          invoice.setTax(rs.getBigDecimal("Tax"));
          invoice.setFreight(rs.getBigDecimal("Freight"));
          invoiceVwV.add(invoice);
        }
        rs.close();
        rs = null;
        stmt.close();
        stmt = null;
        
 
      return invoiceVwV;
      
    }
    private JciCustInvoiceViewVector getStjohnInvoiceData(Connection pCon, int pStoreId, String pInvType, int pMinNum, int pMaxNum) 
    throws Exception
    {
      JciCustInvoiceViewVector invoiceVwV = new JciCustInvoiceViewVector();  
      int accountId = 99;
      String sql = 
        "SELECT  "+
        " ic.invoice_num as Invoice_Num, "+
        " ic.invoice_type as  Invoice_Type, "+
        " ic.INVOICE_DATE as Invoice_Date, "+ 
        " '13703-003' as Company, "+
        " 'J'||  "+
        " (SELECT DISTINCT (SUBSTR(prop.clw_value,0,4) || '-' || SUBSTR(prop.clw_value,5)) "+ 
        " FROM CLW_PROPERTY prop  "+
        " WHERE prop.bus_entity_id = ic.site_id AND prop.short_desc = 'SITE_REFERENCE_NUMBER') "+ 
        " as Location, "+ 
        " (SELECT DISTINCT REPLACE(REPLACE(ADDRESS3,' ',''),'WORKORDER#','')  "+
        " FROM CLW_ADDRESS addr  "+
        " WHERE addr.bus_entity_id = ic.site_id AND addr.ADDRESS_TYPE_CD = 'SHIPPING') "+ 
        " as Wo_Number, "+
        " 'Materials' as Expense_Type, "+
        " '' as Comp_Wo_Flag, "+
        " null as Comp_Wo_Date, "+
        " 'Janitorial Minor' as Service_Type, "+
        " icd.line_number as Line_Num, "+ 
        " icd.LINE_TOTAL as Line_Amount, "+ 
        " icd.ITEM_QUANTITY as Qty, "+
        " trim(REPLACE(icd.ITEM_SHORT_DESC,',',' ')) as Item_Name, "+ 
        " trim(icd.ITEM_SKU_NUM) as Item_Sku, "+
        " ic.NET_DUE as Invoice_Amount, "+
        " ic.SALES_TAX as Tax, "+
        " ic.FREIGHT+ic.MISC_CHARGES as Freight "+
        " FROM  "+
        " clw_invoice_cust ic, clw_invoice_cust_detail icd "+ 
        " WHERE to_number(translate(ic.invoice_num,'#',' '))  BETWEEN "+pMinNum+" AND "+pMaxNum+ 
        " AND ic.account_id = "+accountId+ 
        " AND icd.invoice_cust_id = ic.invoice_cust_id "+ 
        " AND ic.invoice_type = '"+pInvType+"'  "+
        " ORDER BY ic.invoice_num, icd.line_number ";

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next() ) {
          JciCustInvoiceView invoice = JciCustInvoiceView.createValue();
          invoice.setInvoiceNum(rs.getString("Invoice_Num"));
          invoice.setInvoiceType(rs.getString("Invoice_Type"));
          invoice.setInvoiceDate(rs.getDate("Invoice_Date"));
          invoice.setCompany(rs.getString("Company"));
          invoice.setLocation(rs.getString("Location")); 
          invoice.setWoNumber(rs.getString("Wo_Number"));
          invoice.setExpenseType(rs.getString("Expense_Type"));
          invoice.setCompWoFlag(rs.getString("Comp_Wo_Flag"));
          invoice.setCompWoDate(rs.getDate("Comp_Wo_Date"));
          invoice.setServiceType(rs.getString("Service_Type"));
          invoice.setLineNum(rs.getInt("Line_Num")); 
          invoice.setLineAmount(rs.getBigDecimal("Line_Amount"));
          invoice.setQty(rs.getInt("Qty"));
          invoice.setItemName(rs.getString("Item_Name"));
          invoice.setItemSku(rs.getString("Item_Sku"));
          invoice.setInvoiceAmount(rs.getBigDecimal("Invoice_Amount"));
          invoice.setTax(rs.getBigDecimal("Tax"));
          invoice.setFreight(rs.getBigDecimal("Freight"));
          invoiceVwV.add(invoice);
        }
        rs.close();
        rs = null;
        stmt.close();
        stmt = null;
      return invoiceVwV;
      
    }
    
    private ArrayList validityCheck(JciCustInvoiceViewVector pInvVwV, JciCustInvoiceViewVector pLawInvVwV) {
      ArrayList errors = new ArrayList();
      if((pInvVwV==null || pInvVwV.size()==0) && (pLawInvVwV==null || pLawInvVwV.size()==0)) return errors;
      for(int ii=0; ii<pInvVwV.size(); ii++) {
        JciCustInvoiceView invVw = (JciCustInvoiceView) pInvVwV.get(ii);
        //check workorder number
        String workorderNum = (String) invVw.getWoNumber();
        String location = (String) invVw.getLocation();
        if(location!=null) location = location.trim();
        if(workorderNum==null || workorderNum.trim().length()!=10) {
          String error = " [FAIL] BAD WORKORDER NUMBER FOR STORE: "+location;
          addError(errors,error);
        }
        //check store number start with J then is 4 digits plus a check digit or HOM
        if(location==null || location.length()==0) {
          String error = " [FAIL] MISSING STORE NUMBER FOR STORE: "+location;
          addError(errors,error);
        } else if(!"J".equals(location.substring(0,1))) {
          String error = " [FAIL] BAD STORE NUMBER FOR STORE: "+location;
          addError(errors,error);
        } else if(!(location.length()==7||location.length()==9)) {
          String error = " [FAIL] BAD STORE NUMBER, WRONG LENGTH FOR STORE: "+location;
          addError(errors,error);
        }
      }
      String lawInvNum = null;
      String stjInvNum = null;
      int lawLineNum = 0;
      int stjLineNum = 0;
      String lawItemSku = null;
      String stjItemSku = null;
      int lawQty = 0;
      int stjQty = 0;
      BigDecimal lawLineAmt = null;
      BigDecimal stjLineAmt = null;
      BigDecimal lawTax = null;
      BigDecimal stjTax = null;
      BigDecimal lawFreight = null;
      BigDecimal stjFreight = null;
      BigDecimal lawInvAmt = null;
      BigDecimal stjInvAmt = null;
      if(pLawInvVwV!=null) {//check against Lawson invoice
        for(int ii=0,jj=0; ii<pLawInvVwV.size()||jj<pInvVwV.size();){
          if(ii<pLawInvVwV.size()){
            JciCustInvoiceView lawInvVw = (JciCustInvoiceView) pLawInvVwV.get(ii);
            lawInvNum = lawInvVw.getInvoiceNum();
            lawLineNum = lawInvVw.getLineNum();
            lawItemSku = lawInvVw.getItemSku();
            if(lawItemSku==null) lawItemSku="";
            lawItemSku = lawItemSku.trim();
            lawQty = lawInvVw.getQty();
            lawLineAmt = lawInvVw.getLineAmount();
            if(lawLineAmt==null) lawLineAmt = new BigDecimal(0);
            lawLineAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
            lawTax = lawInvVw.getTax();
            if(lawTax==null) lawTax = new BigDecimal(0);
            lawTax.setScale(2,BigDecimal.ROUND_HALF_UP);
            lawFreight = lawInvVw.getFreight();
            if(lawFreight==null) lawFreight = new BigDecimal(0);
            lawFreight.setScale(2,BigDecimal.ROUND_HALF_UP);
            lawInvAmt = lawInvVw.getInvoiceAmount();
            if(lawInvAmt==null) lawInvAmt = new BigDecimal(0);
            lawInvAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
          }
          if(jj<pInvVwV.size()){
            JciCustInvoiceView stjInvVw = (JciCustInvoiceView) pInvVwV.get(jj);
            stjInvNum = stjInvVw.getInvoiceNum();
            stjLineNum = stjInvVw.getLineNum();
            stjItemSku = stjInvVw.getItemSku();
            if(stjItemSku==null) stjItemSku="";
            stjItemSku = stjItemSku.trim();
            stjQty = stjInvVw.getQty();
            stjLineAmt = stjInvVw.getLineAmount();
            if(stjLineAmt==null) stjLineAmt = new BigDecimal(0);
            stjLineAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
            stjTax = stjInvVw.getTax();
            if(stjTax==null) stjTax = new BigDecimal(0);
            stjTax.setScale(2,BigDecimal.ROUND_HALF_UP);
            stjFreight = stjInvVw.getFreight();
            if(stjFreight==null) stjFreight = new BigDecimal(0);
            stjFreight.setScale(2,BigDecimal.ROUND_HALF_UP);
            stjInvAmt = stjInvVw.getInvoiceAmount();
            if(stjInvAmt==null) stjInvAmt = new BigDecimal(0);
            stjInvAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
          }
          int comp=2;
          if(lawInvNum!=null && stjInvNum!=null){
            comp = lawInvNum.compareTo(stjInvNum);  
          } 
          if(comp!=0 && ii>=pLawInvVwV.size()) {
            comp=1;
          }else if(comp!=0 && jj>=pInvVwV.size()) {
            comp=-1;
          }
          if(comp==0 && lawLineNum==stjLineNum) {
            ii++;
            jj++;
            if(!lawItemSku.equals(stjItemSku)) {
               String error = " [FAIL] Lawson - Stjohn sku mismatch. Invoice="+lawInvNum+", line="+lawLineNum+
                              ". Lawson sku="+lawItemSku+" Stjohn sku="+stjItemSku;
               addError(errors,error);
            }
            if(lawQty!=stjQty){
               String error = " [FAIL] Lawson - Stjohn quantity mismatch. Invoice="+lawInvNum+", line="+lawLineNum+
                              ". Lawson qty="+lawQty+" Stjohn qty="+stjQty;
               addError(errors,error);
            }
            if(lawLineAmt.compareTo(stjLineAmt)!=0){
               String error = " [FAIL] Lawson - Stjohn line amount mismatch. Invoice="+lawInvNum+", line="+lawLineNum+
                              ". Lawson amt="+lawLineAmt+" Stjohn amt="+stjLineAmt;
               addError(errors,error);
            }
            if(lawTax.compareTo(stjTax)!=0){
               String error = " [FAIL] Lawson - Stjohn tax mismatch. Invoice="+lawInvNum+", line="+lawLineNum+
                              ". Lawson tax="+lawTax+" Stjohn tax="+stjTax;
               addError(errors,error);
            }
            if(lawFreight.compareTo(stjFreight)!=0){
               String error = " [FAIL] Lawson - Stjohn freight mismatch. Invoice="+lawInvNum+", line="+lawLineNum+
                              ". Lawson freight="+lawFreight+" Stjohn freight="+stjFreight;
               addError(errors,error);
            }
            if(lawInvAmt.compareTo(stjInvAmt)!=0){
               String error = " [FAIL] Lawson - Stjohn invoice amount mismatch. Invoice="+lawInvNum+", line="+lawLineNum+
                              ". Lawson amt="+lawInvAmt+" Stjohn amt="+stjInvAmt;
               addError(errors,error);
            }
          }else if(comp==0 && (lawLineNum<stjLineNum && ii<pLawInvVwV.size() || jj>=pInvVwV.size())) {
            ii++;
            String error = " [FAIL] Lawson - Stjohn invoice line mismatch. Invoice="+lawInvNum+", Lawson invoice line="+lawLineNum+
                              " is missing in Stjohn invoice";
            addError(errors,error);
          }else if(comp==0 && (lawLineNum>stjLineNum && jj<pInvVwV.size() || ii>=pLawInvVwV.size())) {
            jj++;
            String error = " [FAIL] Lawson - Stjohn invoice line mismatch. Invoice="+lawInvNum+", Stjohn invoice line="+stjLineNum+
                              " is missing in Lawson invoice";
            addError(errors,error);
          }else if(comp<0) {
            ii++;
            String error = " [FAIL] Lawson - Stjohn invoice mismatch. Lawson invoice="+lawInvNum+
                              " is missing in Stjohn";
            addError(errors,error);
          }else if(comp>0) {
            jj++;
            String error = " [FAIL] Lawson - Stjohn invoice mismatch. Stjohn invoice="+stjInvNum+
                              " is missing in Lawson";
            addError(errors,error);
          }

        }
      }
      return errors;
    }
    private void addError(ArrayList pErrors, String pError) {
      for(int ii=0; ii<pErrors.size(); ii++) {
        String ee = (String) pErrors.get(ii);
        if(ee.equals(pError)) return;
      }
      pErrors.add(pError);
      return;
    }
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception 
    {
    GenericReportResultView result = GenericReportResultView.createValue();
    Connection con = pCons.getMainConnection();
    Connection lawCon = pCons.getLawsonConnection();
    Statement stmt = null;
    ResultSet rs = null;
    JciCustInvoiceViewVector invVwV = new JciCustInvoiceViewVector();
    JciCustInvoiceViewVector lawInvVwV = null;
    JciCustInvoiceViewVector invCrVwV = null;
    JciCustInvoiceViewVector lawCrInvVwV = null;
    ArrayList errors=new ArrayList();
    
    try {
     String minInInvNumS = (String) pParams.get("Min IN Invoice Num");
     String maxInInvNumS = (String) pParams.get("Max IN Invoice Num");
     String minCrInvNumS = (String) pParams.get("Min CR Invoice Num");
     String maxCrInvNumS = (String) pParams.get("Max CR Invoice Num");
     if(minInInvNumS!=null && minInInvNumS.trim().length()>0 &&
        maxInInvNumS!=null && maxInInvNumS.trim().length()>0) {
       int minNum = 0;
       try {
         minNum = Integer.parseInt(minInInvNumS);
       } catch(Exception exc) {
         String errorMess = "Wrong Min IN Invoice Num format: "+minInInvNumS;
         throw new Exception(errorMess);
       }
       int maxNum = 0;
       try {
         maxNum = Integer.parseInt(maxInInvNumS);
       } catch(Exception exc) {
         String errorMess = "Wrong Max IN Invoice Num format: "+maxInInvNumS;
         throw new Exception(errorMess);
       }
       if(minNum!=0 && maxNum!=0) {
         lawInvVwV = getLawsonInvoiceData(lawCon, "IN", minNum, maxNum);
         invVwV = getStjohnInvoiceData(con, 1, "IN", minNum, maxNum);
         errors.addAll(validityCheck(invVwV, lawInvVwV));         
       }
     } else {
         if((minInInvNumS==null || minInInvNumS.trim().length()==0) &&
           (maxInInvNumS!=null && maxInInvNumS.trim().length()>0)) {
           String errorMess = "No Min IN Invoice Num, when Max IN Invoice Num = "+maxInInvNumS;
           throw new Exception(errorMess);
         }
         if((maxInInvNumS==null || maxInInvNumS.trim().length()==0) &&
           (minInInvNumS!=null && minInInvNumS.trim().length()>0)) {
           String errorMess = "No Max IN Invoice Num, when Min IN Invoice Num = "+minInInvNumS;
           throw new Exception(errorMess);
         }
     }
     if(minCrInvNumS!=null && minCrInvNumS.trim().length()>0 &&
        maxCrInvNumS!=null && maxCrInvNumS.trim().length()>0) {
       int minNum = 0;
       try {
         minNum = Integer.parseInt(minCrInvNumS);
       } catch(Exception exc) {
         String errorMess = "Wrong Min IN Invoice Num format: "+minCrInvNumS;
         throw new Exception(errorMess);
       }
       int maxNum = 0;
       try {
         maxNum = Integer.parseInt(maxCrInvNumS);
       } catch(Exception exc) {
         String errorMess = "Wrong Max IN Invoice Num format: "+maxCrInvNumS;
         throw new Exception(errorMess);
       }
       if(minNum!=0 && maxNum!=0) {
         lawCrInvVwV = getLawsonInvoiceData(lawCon, "CR", minNum, maxNum);
         invCrVwV = getStjohnInvoiceData(con, 1, "CR", minNum, maxNum);
         errors.addAll(validityCheck(invCrVwV, lawCrInvVwV));         
       }
     } else {
         if((minCrInvNumS==null || minCrInvNumS.trim().length()==0) &&
           (maxCrInvNumS!=null && maxCrInvNumS.trim().length()>0)) {
           String errorMess = "No Min IN Invoice Num, when Max IN Invoice Num = "+maxCrInvNumS;
           throw new Exception(errorMess);
         }
         if((maxCrInvNumS==null || maxCrInvNumS.trim().length()==0) &&
           (minCrInvNumS!=null && minCrInvNumS.trim().length()>0)) {
           String errorMess = "No Max IN Invoice Num, when Min IN Invoice Num = "+minCrInvNumS;
           throw new Exception(errorMess);
         }
     }
     if(invCrVwV!=null) {
       invVwV.addAll(invCrVwV);
     }
     //Make reprot    
     BigDecimal bd = null;
     BigDecimal freight = new BigDecimal(0);
     BigDecimal tax = new BigDecimal(0);
     BigDecimal lineTotal = new BigDecimal(0);
     ArrayList lines = new ArrayList();
     String invNumOld = "";
     ArrayList row = null;
     int lineNum = 0;
     for(int ii=0; ii<invVwV.size(); ii++) {  
       JciCustInvoiceView invVw = (JciCustInvoiceView) invVwV.get(ii);
       String invNum = invVw.getInvoiceNum();
       if(invVw.getLineAmount().compareTo(ZERO) < 0 || invVw.getInvoiceAmount().compareTo(ZERO) < 0){
        invNum = invNum + "CM"; //append CM so that JCI does not see as a duplicate invoice
       }
       if(ii>0 && !invNumOld.equals(invNum)) {
           
         if(reportDetailLevel == HEADER_LEVEL && lineTotal!=null && lineTotal.abs().doubleValue()>0.001){
           lineNum = 1;
           ArrayList rowItem = makeTaxFreightRow(row, "Item", lineTotal, new Integer(lineNum));     
           lines.add(rowItem);
         }
         if(tax!=null && tax.abs().doubleValue()>0.001) { 
           //lineNum++;
           ArrayList rowTax = makeTaxFreightRow(row, "Sales Tax", tax, new Integer(lineNum+1));     
           lines.add(rowTax);
         }
         if(freight!=null && freight.abs().doubleValue()>0.001) { 
           //lineNum++;
           ArrayList rowFreight = makeTaxFreightRow(row, "Freight", freight, new Integer(lineNum+2));     
           lines.add(rowFreight);
         }
         invNumOld = invNum;
         lineTotal = ZERO;
       }
       
       
       row = new ArrayList();
       row.add(invNum);
       Date dd = invVw.getInvoiceDate();
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
       row.add(sdf.format(dd));
       row.add(invVw.getCompany());
       row.add(invVw.getLocation());
       row.add(invVw.getWoNumber());
       row.add(invVw.getExpenseType());
       row.add(invVw.getCompWoFlag());
       row.add(invVw.getCompWoDate());
       row.add(invVw.getServiceType());
       if(reportDetailLevel == DETAIL_LEVEL){
        lineNum = invVw.getLineNum();
       }
       row.add(new Integer(lineNum));
       row.add(invVw.getLineAmount());
       row.add(new Integer(invVw.getQty()));
       row.add(invVw.getItemName());
       row.add(invVw.getItemSku());
       row.add(invVw.getInvoiceAmount());
       

       tax = invVw.getTax();
       lineTotal = lineTotal.add(invVw.getLineAmount());
       freight = invVw.getFreight();
       if(reportDetailLevel == DETAIL_LEVEL){
        lines.add(row);
       }
     }
     if(tax!=null && tax.abs().doubleValue()>0.001) { 
       //lineNum++;  
       ArrayList rowTax = makeTaxFreightRow(row, "Sales Tax", tax, new Integer(lineNum+1));     
       lines.add(rowTax);
     }
     if(freight!=null && freight.abs().doubleValue()>0.001) { 
       //lineNum++;  
       ArrayList rowFreight = makeTaxFreightRow(row, "Freight", freight, new Integer(lineNum+2));     
       lines.add(rowFreight);
     }
     if(reportDetailLevel == HEADER_LEVEL && lineTotal!=null && lineTotal.abs().doubleValue()>0.001){
       ArrayList rowItem = makeTaxFreightRow(row, "Item", lineTotal, new Integer(lineNum+3));     
       lines.add(rowItem);
     }
     //Add empty row
     row = new ArrayList();
     lines.add(row);
     //Add errors
     for(int ii=0; ii<errors.size(); ii++) {
       row = new ArrayList();
       row.add(errors.get(ii));
       lines.add(row);
     }
     result.setTable(lines);
     result.setHeader(getReportHeader());
     result.setColumnCount(result.getHeader().size());
    } catch (Exception exc) {
       exc.printStackTrace();
       throw exc;
     }finally{
       try{
         con.close();
         //repCon.close();
         if(rs!=null) rs.close();
         if(stmt!=null) stmt.close();
       }catch(Exception exc) {
         exc.printStackTrace();
         throw exc;
       }
     }
      return result;
    }
   private ArrayList makeTaxFreightRow(ArrayList pRow, String pFootText, BigDecimal pAmt, Integer pLineNum) {    
     ArrayList rowFoot = new ArrayList();
     rowFoot.add(pRow.get(0)); // Invoice
     rowFoot.add(pRow.get(1)); // Invoice date
     rowFoot.add(pRow.get(2)); // Company
     rowFoot.add(pRow.get(3)); // Location
     rowFoot.add(pRow.get(4)); // WoNumber
     rowFoot.add(pFootText); //Expense Type
     rowFoot.add(pRow.get(6)); // Comp Wo Flag
     rowFoot.add(pRow.get(7)); // Comp Wo Date
     rowFoot.add(pRow.get(8)); // Service Type
     rowFoot.add(pLineNum);
     rowFoot.add(pAmt); // Line Amount
     int qty = (pAmt.doubleValue()<0)?-1:1;
     rowFoot.add(new Integer(qty)); //Line Qty
     rowFoot.add(pFootText); //Item Name
     rowFoot.add(null); //Item Sku
     rowFoot.add(pRow.get(14)); //InvoiceAmount
     return rowFoot;
   }
    private GenericReportColumnViewVector getReportHeader() {
      GenericReportColumnViewVector header = new GenericReportColumnViewVector();
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Number",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice Date",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Company",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Location",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Wo Number",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Expense Type",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Comp Wo Flag",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Comp Wo Date",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Service Type",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Line Number",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Line Amount",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Qty",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Sku",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Invoice_Amount",2,20,"NUMBER"));
       return header;
    }
}
