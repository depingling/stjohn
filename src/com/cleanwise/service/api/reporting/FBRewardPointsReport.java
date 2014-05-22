/*
 * VendorReconciliationReport.java
 *
 *
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class FBRewardPointsReport implements GenericReportMulti {

    private static final Logger log = Logger.getLogger(FBRewardPointsReport.class);


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        Statement stmt;
        ResultSet rs;

		
        String accountIdS = (String) pParams.get("ACCOUNT_MULTI_OPT");		
        String customerS = (String)ReportingUtils.getParam(pParams,"CUSTOMER");
        String begDateS = (String) pParams.get("BEG_DATE");		
        String endDateS = (String) pParams.get("END_DATE");		
        String multiplierS = (String) pParams.get("MULTIPLIER_OPT");		
        int multiplier = 1;
        if(Utility.isSet(multiplierS)) {
            try {
                multiplier = Integer.parseInt(multiplierS);
            } catch (Exception exc) {
                String errorMess = "^clw^Invalid multiplier format: "+multiplierS+"^clw^";
                throw new Exception(errorMess);
            }
        }
        int userId = 0;		

        try{
            userId = Integer.parseInt(customerS);
        }catch(Exception e){
            String errorMess = "^clw^User id must be an integer value^clw^";
            throw new Exception(errorMess);
        }
        
        DBCriteria dbc = new DBCriteria();
        IdVector accountIdV = null;
        if(Utility.isSet(accountIdS)) {
            accountIdV = Utility.parseIdStringToVector(accountIdS,",");
            if(accountIdV!=null && !accountIdV.isEmpty()) {
                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountIdV);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                accountIdV = BusEntityDataAccess.selectIdOnly(con,BusEntityDataAccess.BUS_ENTITY_ID,dbc);
            }
            if(accountIdV==null || accountIdV.isEmpty()) {
                String errorMess = "^clw^No accounts found. Accounts requested: +" +
                        accountIdS+"^clw^";
                throw new Exception(errorMess);
            }
        }
        
        

	UserData uD = null;
        try {
            uD = UserDataAccess.select(con,userId); 
        } catch (Exception exc) {
            String errorMess = "^clw^Unknow user. User id: "+customerS+"^clw^";
            throw new Exception(errorMess);            
        }

        String userTypeCd = uD.getUserTypeCd();
        if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeCd) ||
           RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd)) {               
            if(accountIdS==null) {
                String errorMess = "^clw^Administrator must pick some accounts^clw^";
                throw new Exception(errorMess);            
            }
        }
        
        IdVector storeIdV = null;
        if(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd) ||
           RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)) {           
            dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID,userId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                    RefCodeNames.USER_ASSOC_CD.STORE);
            storeIdV = UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID,dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeIdV);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            if(accountIdV!=null) {
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,accountIdV);
            }            
            accountIdV = BusEntityAssocDataAccess.selectIdOnly(con,BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);
        }
        
        if(RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)) {           
            dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID,userId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                    RefCodeNames.USER_ASSOC_CD.ACCOUNT);
            if(accountIdV!=null) {
                dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,accountIdV);
            }
            accountIdV = UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID, dbc);                
        }
        if(accountIdV!=null) {
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountIdV);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            accountIdV = BusEntityDataAccess.selectIdOnly(con,BusEntityDataAccess.BUS_ENTITY_ID,dbc);
        }
        if(accountIdV==null || accountIdV.isEmpty()) {
            String errorMess = "^clw^No active accounts found. Accounts requested: +" +
                    accountIdS+"^clw^";
            throw new Exception(errorMess);
        }
        
        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, accountIdV);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        
       
        IdVector siteIdV = null;        
        if(RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) ||                
           RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd)) {           
            
            dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, userId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);
            siteIdV = UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID,dbc);
        
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountIdV);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,siteIdV);
            siteIdV = BusEntityAssocDataAccess.selectIdOnly(con,BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);
        }

        
        
        
        String sql = null;
        if(siteIdV==null) {                        
            sql =         
            " SELECT acct.bus_entity_id account_id, acct.short_desc account, "+
            " site.bus_entity_id site_id, site.short_desc site  "+
            " FROM clw_bus_entity acct, clw_bus_entity site, clw_bus_entity_assoc bea "+
            " WHERE acct.bus_entity_id = bea.bus_entity2_id "+
            "   AND site.bus_entity_id = bea.bus_entity1_id "+
            "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' "+
            "   AND site.bus_entity_status_cd = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' "+
            "   AND acct.bus_entity_id IN ("+IdVector.toCommaString(accountIdV)+") "+
            "   ORDER BY account, site ";
        } else {
            String siteCond = (siteIdV.size()>0)?
                IdVector.toCommaString(siteIdV):"-1";
            sql =         
            " SELECT acct.bus_entity_id account_id, acct.short_desc account, "+
            " site.bus_entity_id site_id, site.short_desc site  "+
            " FROM clw_bus_entity acct, clw_bus_entity site, clw_bus_entity_assoc bea "+
            " WHERE acct.bus_entity_id = bea.bus_entity2_id "+
            "   AND site.bus_entity_id = bea.bus_entity1_id "+
            "   AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' "+
            "   AND site.bus_entity_status_cd = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' "+
            "   AND site.bus_entity_id IN ("+siteCond+") "+
            "   ORDER BY account, site ";
        }
        log.info("FBRewardPointsReport sql: "+sql);
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        LinkedList acctSiteLL = new LinkedList();
        while(rs.next()) {
            AcctSite as = new AcctSite();             
            as.acctId = rs.getInt("account_id");
            as.acctName = rs.getString("account");
            as.siteId = rs.getInt("site_id");
            as.siteName = rs.getString("site");
            acctSiteLL.add(as);        
        }
        rs.close();
        stmt.close();
        
        sql = null;
        if(siteIdV==null) {                        
            sql =         
              " SELECT account_id, site_id, SUM(total_price)*"+multiplier+" points  "+
              " FROM clw_order o "+
              " WHERE o.account_id IN ("+IdVector.toCommaString(accountIdV)+") "+
              " AND NVL(revised_order_date, original_order_date)  "+
              "     BETWEEN TO_DATE('"+begDateS+"','mm/dd/yyyy') AND TO_DATE('"+endDateS+"','mm/dd/yyyy') "+
              " AND o.order_status_cd NOT IN ('Cancelled','Duplicated','ERP Cancelled','REFERENCE ONLY','Rejected') "+
              " GROUP BY account_id, site_id";

        } else {
            String siteCond = (siteIdV.size()>0)?
                IdVector.toCommaString(siteIdV):"-1";
            sql =         
              " SELECT account_id, site_id, SUM(total_price)*"+multiplier+" points  "+
              " FROM clw_order o "+
              " WHERE o.site_id IN ("+siteCond+") "+
              " AND NVL(revised_order_date, original_order_date)  "+
              "     BETWEEN TO_DATE('"+begDateS+"','mm/dd/yyyy') AND TO_DATE('"+endDateS+"','mm/dd/yyyy') "+
              " AND o.order_status_cd NOT IN ('Cancelled','Duplicated','ERP Cancelled','REFERENCE ONLY','Rejected') "+
              " GROUP BY account_id, site_id";
            
        }
		
        log.info("FBRewardPointsReport sql: "+sql);
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        HashMap sitePointsHM = new HashMap();
        while(rs.next()) {
            RewardPoints rp = new RewardPoints();             
            rp.acctId = rs.getInt("account_id");
            rp.siteId = rs.getInt("site_id");
            rp.points = rs.getBigDecimal("points");
            String key = ""+rp.acctId+"|"+rp.siteId;
            sitePointsHM.put(key,rp);
        }
        rs.close();
        stmt.close();
        
        GenericReportResultView detailSheet = GenericReportResultView.createValue();
        resultV.add(detailSheet);
        GenericReportColumnViewVector detailHeader = getDetailHeader();
        detailSheet.setColumnCount(detailHeader.size());
        detailSheet.setHeader(detailHeader);
        detailSheet.setName("Sites");
        ArrayList sitePointsAL = new ArrayList();
        detailSheet.setTable(sitePointsAL);
        for(Iterator iter = acctSiteLL.iterator(); iter.hasNext();) {
            AcctSite as = (AcctSite) iter.next();
            String key = as.acctId+"|"+as.siteId;
            RewardPoints rp = (RewardPoints) sitePointsHM.get(key);
            if(rp==null) {
                rp = new RewardPoints();
                rp.acctId = as.acctId;
                rp.siteId = as.siteId;
                rp.points = new BigDecimal(0);
            }
            rp.acctName = as.acctName;
            rp.siteName = as.siteName;
            LinkedList row = new LinkedList();
            row.add(rp.acctName);
            row.add(rp.siteName);
            row.add(rp.points);
            sitePointsAL.add(row);
        }


        GenericReportResultView sumSheet = GenericReportResultView.createValue();
        resultV.add(sumSheet);
        GenericReportColumnViewVector sumHeader = getSummaryHeader();
        sumSheet.setColumnCount(sumHeader.size());
        sumSheet.setHeader(sumHeader);
        sumSheet.setName("Accounts");
        ArrayList acctPointsAL = new ArrayList();
        sumSheet.setTable(acctPointsAL);
        BigDecimal points = new BigDecimal(0);
        RewardPoints rpWrk = null;
        LinkedList row = null;
        for(Iterator iter = acctSiteLL.iterator(); iter.hasNext();) {
            AcctSite as = (AcctSite) iter.next();
            if(rpWrk==null || as.acctId != rpWrk.acctId) {
                if(rpWrk!=null) {
                   row = new LinkedList();
                   acctPointsAL.add(row);
                   row.add(rpWrk.acctName);
                   row.add(rpWrk.points);
                }
                rpWrk = new RewardPoints();           
                rpWrk.acctId = as.acctId;
                rpWrk.acctName = as.acctName;
                rpWrk.points = new BigDecimal(0);
            }
            String key = as.acctId+"|"+as.siteId;
            RewardPoints rp = (RewardPoints) sitePointsHM.get(key);
            if(rp!=null) {
                rpWrk.points = rpWrk.points.add(rp.points);
            }
        }
        if(rpWrk!=null) {
           row = new LinkedList();
           acctPointsAL.add(row);
           row.add(rpWrk.acctName);
           row.add(rpWrk.points);
        }

        return resultV;

    }


    private GenericReportColumnViewVector getDetailHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Points",2,20,"NUMBER"));
        return header;
    }

    private GenericReportColumnViewVector getSummaryHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Points",2,20,"NUMBER"));
        return header;
    }
    

    private class AcctSite {
        int acctId;
        int siteId;
        String acctName;
        String siteName;
    }

    private class RewardPoints {
        int acctId;
        int siteId;
        String acctName;
        String siteName;
        BigDecimal points;        
    }



}
