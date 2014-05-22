/*
 * DeliverySchedule.java
 *
 * Created on January 5, 2006, 3:27 PM
 *
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
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.util.StringTokenizer;
import java.util.GregorianCalendar;

/**
 *
 * @author Ykupershmidt
 */
public class DeliverySchedule  implements GenericReportMulti {
  
  /** Creates a new instance of DeliverySchedule */
  public DeliverySchedule() {
  }
  


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception 
    {
        com.cleanwise.service.api.APIAccess apiAccess = 
                                         new com.cleanwise.service.api.APIAccess();
        com.cleanwise.service.api.session.Site siteEjb = apiAccess.getSiteAPI();

        DBCriteria dbc = null;
        Connection con = pCons.getMainConnection();
        String errorMess = "No error";
        
        LinkedList scheduleProperties = new LinkedList();
        LinkedList distributors = new LinkedList();
        LinkedList schedules = new LinkedList();
        LinkedList siteSchedules = new LinkedList();
        
        LinkedList errors = new LinkedList();
        
       //User
        String userIdS = (String) pParams.get("CUSTOMER");
        if(userIdS==null || userIdS.trim().length()==0){
          String mess = "^clw^No user provided^clw^";
          throw new Exception(mess);
        }
        int userId = 0;
        try {
          userId = Integer.parseInt(userIdS);
        }
        catch (Exception exc1) {
          String mess = "^clw^Wrong user id format^clw^";
          throw new Exception(mess);
        }

        dbc = new DBCriteria();

        //Get user sites
        UserData userD = null;
        try {
          userD = UserDataAccess.select(con,userId);
        } catch(Exception exc) {
          String mess = "^clw^No user information found. User id: "+userIdS+"^clw^";
          throw new Exception(mess);
        }

        String sql = 
        "select u.user_id, site.bus_entity_id site_id, site.short_desc site_name, "+
        " acct.bus_entity_id account_id, acct.short_desc account_name, contr.short_desc bsc, "+
        " a.city, a.state_province_cd, a.address1, a.address2 "+        
        " from clw_user u, clw_user_assoc ua, clw_bus_entity site, clw_address a, "+
        " clw_bus_entity_assoc beasa, clw_bus_entity acct, "+
        " clw_catalog_assoc ca, clw_catalog c, "+
        " clw_bus_entity_assoc beasc, clw_bus_entity contr "+
        " where 1=1 "+
        " and u.user_id = ua.user_id "+
        " and ua.bus_entity_id = site.bus_entity_id "+
        " and ua.user_assoc_cd = 'SITE' "+
        " and site.bus_entity_type_cd = 'SITE' "+
        " and site.bus_entity_status_cd = 'ACTIVE' "+
        " and site.bus_entity_id = a.bus_entity_id (+) "+
        " and a.address_type_cd(+) = 'SHIPPING' "+
        " and a.address_status_cd(+) = 'ACTIVE' "+
        " and a.primary_ind (+) = 1 "+        
        " and beasa.bus_entity_assoc_cd = 'SITE OF ACCOUNT' "+
        " and beasa.bus_entity1_id = site.bus_entity_id "+
        " and acct.bus_entity_id = beasa.bus_entity2_id "+
        " and acct.bus_entity_type_cd = 'ACCOUNT' "+
        " and acct.bus_entity_status_cd = 'ACTIVE' "+
        " and ca.catalog_assoc_cd = 'CATALOG_SITE' "+
        " and ca.bus_entity_id = site.bus_entity_id "+
        " and c.catalog_id = ca.catalog_id "+
        " and c.catalog_status_cd = 'ACTIVE' "+
        " and site.bus_entity_id = beasc.bus_entity2_id(+) "+
        " and beasc.bus_entity_assoc_cd(+) ='BSC FOR SITE' "+
        " and contr.bus_entity_id(+) = beasc.bus_entity1_id "+
        " and u.user_id = "+userId;
        
        Statement stmt = con.createStatement();
        ResultSet propRS = stmt.executeQuery(sql);
        LinkedList schedPropLL = new LinkedList();
        while ( propRS.next() ) {
          ScheduleProp sp = new ScheduleProp();
          schedPropLL.add(sp);
          sp.siteId = propRS.getInt("site_id");
          sp.siteName = propRS.getString("site_name");
          sp.accountId = propRS.getInt("account_id");
          sp.accountName = propRS.getString("account_name");
          sp.bsc = propRS.getString("bsc");
          sp.city = propRS.getString("city");
          sp.state = propRS.getString("state_province_cd");
          sp.address1 = propRS.getString("address1");
          sp.address2 = propRS.getString("address2");
        }
        propRS.close();
        stmt.close();
        
        /*
        dbc = new DBCriteria();
        dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                                          RefCodeNames.USER_ASSOC_CD.SITE);
        dbc.addEqualTo(UserAssocDataAccess.USER_ID, userId);
        IdVector userSiteIdV = 
          UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID,dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                                          RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
        dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, userSiteIdV);
        userSiteIdV = 
          CatalogAssocDataAccess.selectIdOnly(con,CatalogAssocDataAccess.BUS_ENTITY_ID,dbc);
        
        
        
        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, userSiteIdV);
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                  RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        BusEntityDataVector siteDV = BusEntityDataAccess.select(con,dbc);
        */
        
        //Date interval        
        int beginYear = Integer.parseInt((String) pParams.get("beginYear"));
        int beginMonth = Integer.parseInt((String) pParams.get("beginMonth"))-1;
        GregorianCalendar beginDateC = new GregorianCalendar(beginYear,beginMonth,1);
        int endYear = Integer.parseInt((String) pParams.get("endYear"));
        int endMonth = Integer.parseInt((String) pParams.get("endMonth"))-1;
        GregorianCalendar endDateC = new GregorianCalendar(endYear,endMonth,1);
        
        //Gap
        int gap  = Integer.parseInt((String) pParams.get("gap"));
        
        String[] siteSchedA = {"1","2","3","4","Last"};
        String[] weekDays = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

        int count = 1;
        for(Iterator schedIter=schedPropLL.iterator(); schedIter.hasNext();) {
          ScheduleProp sp = (ScheduleProp) schedIter.next();
          int siteId = sp.siteId;
//if(count>=100) break;
          DistributorData distD = null;
          try {
             distD = siteEjb.getMajorSiteDist(siteId);
          }catch(Exception exc){
            //exc.printStackTrace();
            errorMess = "^clw^Failed to get distributor for the site. Site id: "+siteId+"^clw^";
            throw new Exception(errorMess);
          }
          

          
          ArrayList scheduleOrderDatesVector = null;
          try {
             scheduleOrderDatesVector = 
             siteEjb.getOrderScheduleDatesNoExc(siteId, sp.accountId, beginDateC.getTime(),  endDateC.getTime());
             if(scheduleOrderDatesVector!=null && scheduleOrderDatesVector.size()>0){
               Object oo = scheduleOrderDatesVector.get(0);
               if(oo instanceof String) {
                 addErrorRow(errors,sp,oo.toString());
                 continue;
               }
             }
             
          }catch(Exception exc){
            exc.printStackTrace();
            errorMess = "Failed to get site schedule (Unknown error).";
            addErrorRow(errors,sp,errorMess);
            continue;
          }
          
          scheduleProperties.add(sp);
          distributors.add(distD);
          schedules.add(scheduleOrderDatesVector);

          dbc = new DBCriteria();
          dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,siteId);
          dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, 
                  RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE);
          PropertyDataVector propertyDV =  PropertyDataAccess.select(con,dbc);
          String siteSched = "";
          for(int ii=0; ii<propertyDV.size(); ii++) {
            PropertyData pD = (PropertyData) propertyDV.get(ii);
            String val = pD.getValue();
            if(val!=null) {
              int index = val.indexOf("Monthly:");
              if(index>=0) {
                index += "Monthly:".length();
                if(val.length()>index) {
                  String weekNumS = val.substring(index);
                  try {
                    int weekNum = Integer.parseInt(weekNumS)-1;
                    if(siteSched.length()>0) siteSched += "; ";
                    siteSched += siteSchedA[weekNum];
                  } catch(Exception exc) {}
                }
              }
            }
          }
          siteSchedules.add(siteSched);
        }

        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        //Create error page
        if(errors.size()>0){
          GenericReportResultView errorPage = GenericReportResultView.createValue();
          resultV.add(errorPage);
          errorPage.setTable(new ArrayList());
          GenericReportColumnViewVector errorHeader = new GenericReportColumnViewVector();
          
          addStaticCommonHeaders(errorHeader);
          errorHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Errors",0,255,"VARCHAR2"));
          errorPage.setHeader(errorHeader);
          errorPage.setColumnCount(errorHeader.size());
          errorPage.setName("Errors");
          for(Iterator iter=errors.iterator(); iter.hasNext();) {
            errorPage.getTable().add((ArrayList)iter.next());
          }
        }
        
        
        //Number of months
        int yearDiff = endYear - beginYear+1;
        ArrayList[][] deliveryDateA = new ArrayList[yearDiff*12][scheduleProperties.size()]; 
        ArrayList[][] cutoffDateA = new ArrayList[yearDiff*12][scheduleProperties.size()];         
        
        //scedule page
        SimpleDateFormat yearDF = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthDF = new SimpleDateFormat("MM");
        
        for(int ii=0; ii<schedules.size(); ii++) {
          ArrayList scheduleOrderDatesVector = (ArrayList) schedules.get(ii);
          for(Iterator iter = scheduleOrderDatesVector.iterator(); iter.hasNext();) {
            ScheduleOrderDates orderDates = (ScheduleOrderDates) iter.next();
            Date deliveryDate = orderDates.getNextOrderDeliveryDate();
            int year = Integer.parseInt(yearDF.format(deliveryDate));
            int month = Integer.parseInt(monthDF.format(deliveryDate))-1;
            int index = (year-beginYear)*12+month;
            if(deliveryDateA[index][ii]==null) {
              deliveryDateA[index][ii] = new ArrayList();
              cutoffDateA[index][ii] = new ArrayList();
            }
            deliveryDateA[index][ii].add(deliveryDate);
            cutoffDateA[index][ii].add(orderDates.getNextOrderCutoffDate());            
          }
        }
        
        SimpleDateFormat dayMonthDF = new SimpleDateFormat("dd MMM");
        int firstIndex = beginMonth;
        int lastIndex = (endYear - beginYear)*12+endMonth;
        GenericReportResultView schedulePage = GenericReportResultView.createValue();
        resultV.add(schedulePage);
        schedulePage.setTable(new ArrayList());
        for(int ii=0; ii<schedules.size(); ii++) {
          ArrayList row = new ArrayList();
          schedulePage.getTable().add(row);
          ScheduleProp sp = (ScheduleProp) scheduleProperties.get(ii);
          row.add(sp.accountName);
          row.add(sp.siteName);
          row.add(sp.city);
          row.add(sp.state);
          row.add(sp.address1);
          row.add(sp.address2);
          row.add(sp.bsc);
          int[] weekDayWeight = new int[7];
          for(int dd=0; dd<weekDayWeight.length; dd++) weekDayWeight[dd] = 0;
          for(int jj=firstIndex; jj<=lastIndex; jj++) {
            ArrayList dateAL = deliveryDateA[jj][ii];
            ArrayList cutoffAL = cutoffDateA[jj][ii];
            String datesS = "";
            String cutoffS = "";
            String myCutoffS = "";
            if(dateAL!=null) {
              for(int kk=0; kk<dateAL.size(); kk++) {
                if(kk>0) {
                  datesS += "; ";
                  cutoffS += "; ";
                  myCutoffS += "; ";
                }
                Date deliveryDate = (Date) dateAL.get(kk);
                GregorianCalendar deliveryGC = (new GregorianCalendar());
                deliveryGC.setTime(deliveryDate);
                int weekDay = deliveryGC.get(GregorianCalendar.DAY_OF_WEEK)-1;
                weekDayWeight[weekDay]++;
                datesS += dayMonthDF.format(deliveryDate);
                Date cutoffDate = (Date) cutoffAL.get(kk);
                cutoffS += dayMonthDF.format(cutoffDate);
                if(gap>0) {
                  GregorianCalendar myCutoffGC = (new GregorianCalendar());
                  myCutoffGC.setTime(cutoffDate);
                  myCutoffGC.add(GregorianCalendar.DATE, -gap);
                  Date myCutoff = myCutoffGC.getTime();
                  myCutoffS += dayMonthDF.format(myCutoff);
                  
                }
              }
            }
            if(gap>0) {
              row.add(myCutoffS);
            }
            row.add(cutoffS);
            row.add(datesS);
          }
          String siteSched = (String) siteSchedules.get(ii);
          int maxWeight = -1;
          int maxInd = -1;
          for(int dd=0; dd<weekDayWeight.length; dd++) {
            if(weekDayWeight[dd]>maxWeight) {
              maxWeight = weekDayWeight[dd];
              maxInd = dd;
            }
          }
          if(maxInd>=0) {
            siteSched += " "+weekDays[maxInd];
          }
          row.add(siteSched);
        }
        //Generate header
        String[] monthA = {"Jan","Feb","Mar","Apr","May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; 
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        schedulePage.setHeader(header);
        schedulePage.setName("Results");
        addStaticCommonHeaders(header);
        int columnQty = 7;
        for(int ii=firstIndex; ii<=lastIndex; ii++) {
          String month = monthA[ii%12];
          if(gap>0) {
           header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",month+"My Cut Off",0,255,"VARCHAR2"));          
           columnQty++;           
          }
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",month+" Cut Off",0,255,"VARCHAR2"));          
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",month+" Delivery",0,255,"VARCHAR2"));          
          columnQty +=2;
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Delivery Week",0,255,"VARCHAR2"));
        columnQty++;
        schedulePage.setColumnCount(columnQty);
        
        

        return resultV;
    }
    
    private void addErrorRow(List errors, ScheduleProp sp, String errorMess){
        ArrayList errorRow = new ArrayList();
        errorRow.add(sp.accountName);
        errorRow.add(sp.siteName);
        errorRow.add(sp.city);
        errorRow.add(sp.state);
        errorRow.add(sp.address1);
        errorRow.add(sp.address2);
        errorRow.add(sp.bsc);
        errorRow.add(errorMess);
        errors.add(errorRow);
    }
    
    /**
     * Headers used for both error and general report
     */
    private void addStaticCommonHeaders(GenericReportColumnViewVector header){
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship To",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address1",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address2",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","BSC",0,255,"VARCHAR2"));
    }
    
    
    public class ScheduleProp {
      public String accountName;
      public int accountId;
      public String siteName;
      public int siteId;
      public String bsc;
      public String city;
      public String state;
      public String address1;
      public String address2;
    }
    
}
