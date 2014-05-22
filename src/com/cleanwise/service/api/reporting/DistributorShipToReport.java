/*
 * ShipToReport.java
 *
 * Created on February 2, 2003, 6:05 PM
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
 *Picks up all contract items and adds year to date trade infrmation.
 *Keys used: DistId, AccountId, StateCd, CountyCd
 *Adapted from the ReportOrderBean (getShipTo method) to the new GenericReport Framework.
 * @author  bstevens
 */
public class DistributorShipToReport implements GenericReport {
    
    /** Creates a new instance of ShipToReport */
    public DistributorShipToReport() {
    }
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection pCon = pCons.getDefaultConnection();
        String errorMess = "No error";
        ArrayList shipTos = new  ArrayList();
        GenericReportResultView ret = GenericReportResultView.createValue();
        try {
            String sql =
            " select "+
            " dist.bus_entity_id dist_id, dist.erp_num dist_erp, "+
            " dist.short_desc dist_name,  acct.bus_entity_id acct_id, "+
            " acct.erp_num acct_erp,  acct.short_desc acct_name, "+
            " site.bus_entity_id site_id, site.erp_num site_erp, "+
            " site.short_desc site_name,  sitea.name1, sitea.name2, "+
            " sitea.address1, sitea.address2, "+
            " sitea.address3, sitea.address4,  sitea.city, "+
            " sitea.state_province_cd, sitea.country_cd, "+
            " sitea.county_cd,  sitea.postal_code , site.bus_entity_status_cd "+
            " from clw_address sitea, "+
            " (select distinct catalog_id, bus_entity_id from clw_catalog_structure) catst, "+
            " clw_bus_entity dist, "+
            " clw_bus_entity acct, "+
            " clw_bus_entity site, "+
            " clw_bus_entity_assoc bea, "+
            " clw_catalog_assoc cas "+
            " where cas.catalog_assoc_cd = 'CATALOG_SITE' "+
            " and cas.catalog_id = catst.catalog_id "+
            " and site.bus_entity_id = cas.bus_entity_id  and site.bus_entity_type_cd = 'SITE' "+
            " and acct.bus_entity_id = bea.bus_entity2_id  and acct.bus_entity_type_cd = 'ACCOUNT' "+
            " and dist.bus_entity_id = catst.bus_entity_id  and dist.bus_entity_type_cd = 'DISTRIBUTOR' "+
            " and sitea.bus_entity_id = cas.bus_entity_id "+
            " and sitea.address_type_cd = 'SHIPPING' "+
            " and bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' "+
            " and bea.bus_entity1_id = cas.bus_entity_id "+
            " and catst.catalog_id = cas.catalog_id ";
            
            if(pParams.containsKey("ACCOUNT_OPT") && Utility.isSet((String)pParams.get("ACCOUNT_OPT"))) {
                sql += " and bea.bus_entity2_id  = ? ";
            }
            if(pParams.containsKey("DISTRIBUTOR") && Utility.isSet((String)pParams.get("DISTRIBUTOR"))) {
                sql += " and catst.bus_entity_id = ?";
            }
            if(pParams.containsKey("STATE_OPT") && Utility.isSet((String)pParams.get("STATE_OPT"))) {
                sql += " and UPPER(sitea.state_province_cd) = upper(?)";
            }
            if(pParams.containsKey("COUNTY_OPT") && Utility.isSet((String)pParams.get("COUNTY_OPT"))) {
                sql += " and UPPER(sitea.county_cd) like upper(?) ";
            }
            if(pParams.containsKey("STORE_OPT") && Utility.isSet((String)pParams.get("STORE_OPT"))) {
                sql += " and acct.bus_entity_id in ( "+
                       " select bus_entity1_id "+
                       " from clw_bus_entity_assoc beas "+
                       " where beas.bus_entity2_id = ? "+
                       " and beas.bus_entity1_id = acct.bus_entity_id "+  
                       " and beas.bus_entity_assoc_cd = 'ACCOUNT OF STORE')";
            }
            sql += " order by dist_name, acct.short_desc, site.short_desc ";

            PreparedStatement stmt = pCon.prepareStatement(sql);

            int i = 1;
            if(pParams.containsKey("ACCOUNT_OPT") && Utility.isSet((String)pParams.get("ACCOUNT_OPT"))) {
                stmt.setInt(i++, Integer.parseInt((String)pParams.get("ACCOUNT_OPT")));
            }
            if(pParams.containsKey("DISTRIBUTOR") && Utility.isSet((String)pParams.get("DISTRIBUTOR"))) {
                stmt.setInt(i++, Integer.parseInt((String)pParams.get("DISTRIBUTOR")));
            }
            if(pParams.containsKey("STATE_OPT") && Utility.isSet((String)pParams.get("STATE_OPT"))) {
                stmt.setString(i++, ((String)pParams.get("STATE_OPT")));
            }
            if(pParams.containsKey("COUNTY_OPT") && Utility.isSet((String)pParams.get("COUNTY_OPT"))) {
                stmt.setString(i++, "%" + pParams.get("COUNTY_OPT") + "%");
            }
            if(pParams.containsKey("STORE_OPT") && Utility.isSet((String)pParams.get("STORE_OPT"))) {
                stmt.setInt(i++, Integer.parseInt((String)pParams.get("STORE_OPT")));
            }
            ResultSet rs = stmt.executeQuery();
            Utility.parseResultSetDataForReport(rs, ret);
            /*while (rs.next() ) {
                List row = new ArrayList();
                row.add(new Integer(rs.getInt("dist_id")));
                row.add(rs.getString("dist_erp"));
                row.add(rs.getString("dist_name"));
                row.add(new Integer(rs.getInt("acct_id")));
                row.add(rs.getString("acct_erp"));
                row.add(rs.getString("acct_name"));
                row.add(new Integer(rs.getInt("site_id")));
                row.add(rs.getString("site_erp"));
                row.add(rs.getString("site_name"));
                row.add(rs.getString("name1"));
                row.add(rs.getString("name2"));
                row.add(rs.getString("address1"));
                row.add(rs.getString("address2"));
                row.add(rs.getString("address3"));
                row.add(rs.getString("address4"));
                row.add(rs.getString("city"));
                row.add(rs.getString("state_province_cd"));
                row.add(rs.getString("country_cd"));
                row.add(rs.getString("county_cd"));
                row.add(rs.getString("postal_code"));
                shipTos.add(row);
            }*/
        }
        catch (SQLException exc) {
            errorMess = "Error. Report.getShipTos() SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
            errorMess = "Error. Report.getShipTos() Api Service Access Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        
        //ret.setTable(shipTos);
        return ret;
    }
    
    /*private GenericReportColumnView createGenericReportColumnView(
    String pColumnClass, String pColumnName, int pColumnPrecision, int pColumnScale,String pColumnType){
        GenericReportColumnView vw = GenericReportColumnView.createValue();
        vw.setColumnClass(pColumnClass);
        vw.setColumnName(pColumnName);
        vw.setColumnPrecision(pColumnPrecision);
        vw.setColumnScale(pColumnScale);
        vw.setColumnType(pColumnType);
        return vw;
    }
    
    private GenericReportColumnViewVector getReportHeader(){
       GenericReportColumnViewVector ret = new GenericReportColumnViewVector();
       ret.add(createGenericReportColumnView("java.lang.Integer","Dist Id",0,32,"NUMBER"));
       ret.add(createGenericReportColumnView("java.lang.String", "Dist Name", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String", "Dist Erp", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String", "Account Id", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Account Name", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Account Erp", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.Integer","Site Id",0,32,"NUMBER"));
       ret.add(createGenericReportColumnView("java.lang.String","Site Name", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Site Erp", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Address1", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Address2", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Address3", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Address4", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","City", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","County", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","State", 0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Postal Code",0,255, "VARCHAR2"));
       ret.add(createGenericReportColumnView("java.lang.String","Country", 0,255, "VARCHAR2"));
       return ret;
    }*/
}
