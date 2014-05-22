/*
 * DetroitCustomerInvoiceReport.java
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
public class DetroitCustomerInvoiceReport implements GenericReport {
    /** Creates a new instance of DetroitCustomerInvoiceReport */
    public DetroitCustomerInvoiceReport() {
    }

    
    
 private JciCustInvoiceViewVector getStjohnInvoiceData(Connection pCon, int pStoreId, String pInvType, int pMinNum, int pMaxNum) 
    throws Exception
    {
      JciCustInvoiceViewVector invoiceVwV = new JciCustInvoiceViewVector();  
      int accountId = 88278;
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
    
    private ArrayList validityCheck(JciCustInvoiceViewVector pInvVwV) {
      ArrayList errors = new ArrayList();
      if(pInvVwV==null || pInvVwV.size()==0) return errors;
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
    Statement stmt = null;
    ResultSet rs = null;
    JciCustInvoiceViewVector invVwV = new JciCustInvoiceViewVector();
    JciCustInvoiceViewVector invCrVwV = null;
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
         invVwV = getStjohnInvoiceData(con, 88277, "IN", minNum, maxNum);
         errors.addAll(validityCheck(invVwV));         
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
         invCrVwV = getStjohnInvoiceData(con, 88277, "CR", minNum, maxNum);
         errors.addAll(validityCheck(invCrVwV));         
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
     ArrayList lines = new ArrayList();
     String invNumOld = "";
     ArrayList row = null;
     int lineNum = 0;
     for(int ii=0; ii<invVwV.size(); ii++) {  
       JciCustInvoiceView invVw = (JciCustInvoiceView) invVwV.get(ii);
       String invNum = invVw.getInvoiceNum();
       if(ii>0 && !invNumOld.equals(invNum)) {
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
       lineNum = invVw.getLineNum();
       row.add(new Integer(lineNum));
       row.add(invVw.getLineAmount());
       row.add(new Integer(invVw.getQty()));
       row.add(invVw.getItemName());
       row.add(invVw.getItemSku());
       row.add(invVw.getInvoiceAmount());

       tax = invVw.getTax();
       freight = invVw.getFreight();
       lines.add(row);
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
