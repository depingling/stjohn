/*
 * OpenLinesDistributorReport.java
 *
 * Created on January 21, 2005, 1:37 PM
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.OpenLinesResultView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *A limited view of the open lines report customized for distributors
 * @author bstevens
 */
public class OpenLinesDistributorReport extends OpenLinesReport{
    static final String FALSE_SQL = " AND 1=2 ";
    
    /**
     *Here we will add our criteria to restrict by distributor
     */
    protected String getAdditionalWhereConds() throws SQLException{
        String userIdS = (String) getParamMap().get("CUSTOMER");
        int userId = Integer.parseInt(userIdS);
        DBCriteria crit = new DBCriteria();
        crit.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,
                    UserAssocDataAccess.CLW_USER_ASSOC,UserAssocDataAccess.BUS_ENTITY_ID);
        crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC,UserAssocDataAccess.USER_ID, userId);
        crit.addDataAccessForJoin(new BusEntityDataAccess());
        List twoD = JoinDataAccess.select(getConnectionContainer().getMainConnection(),crit);
        if(twoD == null){
            //nothing should be selected
            return FALSE_SQL;
        }
        ArrayList distErps = new ArrayList();
        Iterator it = twoD.iterator();
        while (it.hasNext()){
            List oneD = (List) it.next();
            distErps.add(((BusEntityData)oneD.get(0)).getErpNum());
        }
        if(distErps.isEmpty()){
            return FALSE_SQL;
        }
        String inClause = Utility.toCommaSting(distErps,new Character('\''));
        
        return " AND LN.vendor IN ("+inClause+")  ";
    }
    
    /**
     *writes out the line data
     */
     protected void processResultLine(OpenLinesResultView olrVw,ArrayList lines,ArrayList rankedLines){
        if(!Utility.isSet(olrVw.getOrderStatusCd()) ||
            !Utility.isSet(olrVw.getOrderItemStatusCd()) ||
            Utility.isTrue(olrVw.getBillingOnlyOrder())){
            return;
        }
            
        ArrayList row = new ArrayList();
        row.add(olrVw.getVendorName());
        row.add(olrVw.getPoDate());
        String item = olrVw.getItem();
        row.add(item);
        BigDecimal bd = olrVw.getDistUomConvCost();
        bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        row.add(bd);
        row.add(olrVw.getPoNumber());
        row.add(new Integer(olrVw.getLineNumber()));
        //row.add(new Integer(olrVw.getOrderNumber()));
        row.add(olrVw.getVendorItem());
        row.add(olrVw.getDescritption());
        row.add(olrVw.getShipName());
        row.add(olrVw.getState());
        row.add(olrVw.getZipCode());
        row.add(new Integer(olrVw.getDistUomConvQty()));
        row.add(new Integer(olrVw.getDistUomConvOpenQty()));
        bd = olrVw.getOpenCost();
        bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        row.add(bd);
        row.add(new Integer(olrVw.getSiteId()));
        row.add(olrVw.getFreightHandler());
        row.add(olrVw.getAccountName());
        if(olrVw.getExsistingVendorInvoicesAgainstPo() != null){
            Iterator it = olrVw.getExsistingVendorInvoicesAgainstPo().iterator();
            StringBuffer buf = new StringBuffer();
            while(it.hasNext()){
                buf.append((String) it.next());
                if(it.hasNext()){
                    buf.append(",");
                }
            }
            row.add(buf.toString());
        }
        lines.add(row);
    }
     
     /**
      *writes out the header
      */
    public com.cleanwise.service.api.value.GenericReportColumnViewVector getReportHeader() {
      GenericReportColumnViewVector header = new GenericReportColumnViewVector();
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor Name",0,255,"VARCHAR2")); 
       header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Po Date",0,0,"DATE"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Unit Cost$",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Number",0,255,"VARCHAR2")); 
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Line Number",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor Item",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Descritption",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship Name",0,255,"VARCHAR2")); 
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Zip Code",0,255,"VARCHAR2")); 
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Open Quantity",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Open Cost$",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Site Id",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Freight Handler",0,255,"VARCHAR2")); 
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2")); 
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Existing Vendor Invoices",0,255,"VARCHAR2")); 
       return header;
    }
    
}
