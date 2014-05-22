/*
 * ManifestReconcilliation.java
 *
 * Created on September 27, 2004, 2:23 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.text.SimpleDateFormat;
/**
 * Tries to reconcile the manifests to the actual purchase order data.
 * @author  bstevens
 */
public class ManifestReconcilliationReport implements GenericReport {
    List records = new ArrayList();
     
    /**
     *Main entrance method.  Creates a GenericReportResultView object with the parcel direct manifest reconcilliation
     *data.  Uses a "best guess" algorithim to determine if a given parcel was shipped without authorization from the
     *system.  Currently only supports parcel direct.  The algorithim is first to see if it can find a tracking number that
     *has been referenced in the clw_invoice_dist.tracking_num and second to use the destination zipcode and a "fudge factor"
     *of -30 days, + 3 days on either side of the po date to see if it can find a valid purchase order going to the zipcode
     *that this parcel was shipped to.
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception {
        //format the dates from one string type to anouther
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String sd = (String) pParams.get("BEG_DATE");
        String ed = (String) pParams.get("END_DATE");
        if(!Utility.isSet(sd)){
            sd = (String) pParams.get("BEG_DATE_OPT");
        }
        if(!Utility.isSet(ed)){
            ed = (String) pParams.get("END_DATE_OPT");
        }
        
        java.util.Date startDate = null;
        java.util.Date endDate = null;
        if(Utility.isSet(sd)){
            startDate = df.parse(sd);
        }
        if(Utility.isSet(ed)){
            endDate = df.parse(ed);
        }
        
        
        Connection con = pCons.getDefaultConnection();
        
        
        DBCriteria crit = new DBCriteria();
        if(startDate != null){
            crit.addGreaterOrEqual(PdManifestDataAccess.EST_SHIP_DATE,startDate);
        }
        if(endDate != null){
            crit.addLessOrEqual(PdManifestDataAccess.EST_SHIP_DATE,endDate);
        }
        
        
        PdManifestDataVector pdMDv = PdManifestDataAccess.select(con,crit);
        Iterator it = pdMDv.iterator();
        
        while(it.hasNext()){
            PdManifestData data = (PdManifestData)it.next();
            RecordHolder rh = new RecordHolder();
            rh.pdManifestData = data;
            
            records.add(rh);
        }
        return createReport();
    }
    
    
    
    /**
     *Does the work of generating the rows and createing the header and putting that into the GenericReportResultView
     */
    private GenericReportResultView createReport(){
        GenericReportResultView results = GenericReportResultView.createValue();
        //convert the items vector into a list in the proper order according to the columns
        ArrayList items = new ArrayList();
        for(int i=0;i<records.size();i++){
            RecordHolder rowHolder = (RecordHolder) records.get(i);
            items.add(rowHolder.toRow());
        }
        results.setTable(items);
        results.setHeader(getReportHeader());
        results.setColumnCount(results.getHeader().size());
        return results;
    }
    
    /**
     *Creates the report header
     */
     private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","CUST_MNFST_ID",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MNFST_GRP_TXT",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","CUST_PRCL_ID",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","PRCL_ID",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","DEST_SRT_CD",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ZN_CD",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","SBCL_CD",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PHYSCL_FLG",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","EXPCTD_FLG",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","EST_WT",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ACTL_WT",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PRCL_CHRG",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PRCS_CAT_CD",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","DC",0,255,"VARCHAR2"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PRCL_SIZE",0,255,"VARCHAR2"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","GRP_ID",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Estimated Ship Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Estimated Ship Date Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Match Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Matching Records",0,32,"NUMBER"));
        return header;
     }
    
    /**
     *Inner class to hold the data used for each row
     */
    private class RecordHolder{
        PdManifestData pdManifestData;
        ArrayList toRow(){
            ArrayList aRow = new ArrayList();
            aRow.add(pdManifestData.getCustManifestId());
            aRow.add(pdManifestData.getManifestGrpTxt());
            aRow.add(pdManifestData.getCustPrclId());
            aRow.add(new Integer(pdManifestData.getPdManifestId()));
            aRow.add(pdManifestData.getDestSrcCd());
            aRow.add(pdManifestData.getZoneCd());
            aRow.add(pdManifestData.getSblCd());
            aRow.add(pdManifestData.getPhysicalFlag());
            aRow.add(pdManifestData.getExpectedFlag());
            aRow.add(pdManifestData.getEstWeight());
            aRow.add(pdManifestData.getActualWeight());
            aRow.add(pdManifestData.getPrclChrg());
            aRow.add(pdManifestData.getPrcsCatCd());
            aRow.add(pdManifestData.getDestSrcCd());
            //aRow.add(pdManifestData.get
            //aRow.add(pdManifestData.get
            aRow.add(pdManifestData.getEstShipDate());
            aRow.add(pdManifestData.getEstShipDateType());
            //aRow.add(matchType);
            //aRow.add(new Integer(matchingRecords));
            return aRow;
        }
    }
}
