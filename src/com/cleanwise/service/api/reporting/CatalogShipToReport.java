/*
 * CatalogShipToReport.java
 *
 * Created on February 3, 2003, 4:15 PM
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
 *Picks up catalog sites
 * @param pConditions. Keys: CatalogId, DistId, AccountId, StateCd, CountyCd
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class CatalogShipToReport implements GenericReport {
    private static final Integer ZERO = new Integer(0);
    
    /** Creates a new instance of CatalogShipToReport */
    public CatalogShipToReport() {
    }
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        GenericReportResultView result = GenericReportResultView.createValue();
        String errorMess = "No error";
        ShipToViewVector shipTos = new  ShipToViewVector();
        String lDistributorIdS = (String) pParams.get("DISTRIBUTOR_OPT");
        String lCatalogIdS = (String) pParams.get("CATALOG");
        String lAccountIdS = (String) pParams.get("ACCOUNT_OPT");
        Integer lDistributorId = null;
        Integer lCatalogId = null;
        Integer lAccountId = null;
        if(Utility.isSet(lDistributorIdS))
            lDistributorId = new Integer(lDistributorIdS);
        if(Utility.isSet(lCatalogIdS))
            lCatalogId = new Integer(lCatalogIdS);
        if(Utility.isSet(lAccountIdS))
            lAccountId = new Integer(lAccountIdS);
        String lState = (String) pParams.get("STATE_OPT");
        String lCounty = (String) pParams.get("COUNTY_OPT");
        if(ZERO.equals(lDistributorId)){
            lDistributorId = null;
        }
        if(ZERO.equals(lCatalogId)){
            lCatalogId = null;
        }
        if(ZERO.equals(lAccountId)){
            lAccountId = null;
        }
        try {
            String sql =
            "select "+
            " cat.catalog_id, "+
            " cat.short_desc catalog_name, ";
            if(lDistributorId != null) {
                sql += " dist.bus_entity_id dist_id, dist.erp_num dist_erp, "+
                " dist.short_desc dist_name, ";
            }
            sql += " acct.bus_entity_id acct_id,  "+
            " acct.erp_num acct_erp,  acct.short_desc acct_name, "+
            " site.bus_entity_id site_id, site.erp_num site_erp, "+
            " site.short_desc site_name,  sitea.name1, sitea.name2, "+
            " sitea.address1, sitea.address2, "+
            " sitea.address3, sitea.address4,  sitea.city, "+
            " sitea.state_province_cd, sitea.country_cd, "+
            " sitea.county_cd,  sitea.postal_code "+
		", (SELECT MAX(CLW_VALUE) FROM CLW_PROPERTY P " +
		" WHERE P.PROPERTY_TYPE_CD = 'TARGET_FACILITY_RANK' " +
		" AND P.BUS_ENTITY_ID = site.bus_entity_id) RANK " +
		" , site.bus_entity_status_cd site_status " +
            " from clw_address sitea, ";
            if(lDistributorId != null) {
                sql += " (select distinct catalog_id, bus_entity_id from clw_catalog_structure) catst, "+
                " clw_bus_entity dist, ";
            }
            sql += " clw_bus_entity acct, "+
            " clw_bus_entity site, "+
            " clw_bus_entity_assoc bea, "+
            " clw_catalog_assoc cas, "+
            " clw_catalog cat "+
            " where cas.catalog_assoc_cd = 'CATALOG_SITE' ";
            if(lDistributorId != null) {
                sql += " and cas.catalog_id = catst.catalog_id "+
                " and dist.bus_entity_id = catst.bus_entity_id  and dist.bus_entity_type_cd = 'DISTRIBUTOR' "+
                " and catst.catalog_id = cas.catalog_id ";
            }
            sql += " and site.bus_entity_id = cas.bus_entity_id  and site.bus_entity_type_cd = 'SITE' "+
            " and acct.bus_entity_id = bea.bus_entity2_id  and acct.bus_entity_type_cd = 'ACCOUNT' "+
            " and sitea.bus_entity_id = cas.bus_entity_id "+
            " and sitea.address_type_cd = 'SHIPPING' "+
            " and bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' "+
            " and bea.bus_entity1_id = cas.bus_entity_id "+
            " and cas.catalog_id = cat.catalog_id ";
            
            
            if(lCatalogId != null) {
                sql += " and cat.catalog_id  = "+lCatalogId;
            }
            if(lAccountId != null) {
                sql += " and bea.bus_entity2_id  = "+lAccountId;
            }
            if(lDistributorId != null) {
                sql += " and catst.bus_entity_id = "+lDistributorId;
            }
            if(Utility.isSet(lState)) {
                sql += " and UPPER(sitea.state_province_cd) = upper(?) ";
            }
            if(Utility.isSet(lCounty)) {
                sql += " and UPPER(sitea.county_cd) like upper(?) ";
            }
            sql += " order by acct.short_desc, site.short_desc ";

            PreparedStatement stmt = con.prepareStatement(sql);
            int i = 1;
            if(Utility.isSet(lState)) {
                stmt.setString(i++, lState);
            }
            if(Utility.isSet(lCounty)) {
                stmt.setString(i++, "%" + lCounty + "%");
            }
            ResultSet rs = stmt.executeQuery();
            Utility.parseResultSetDataForReport(rs, result);
            /*while (rs.next() ) {
                result.getTable().add();
                ShipToView stV = ShipToView.createValue();
                stV.setCatalogId(rs.getInt("catalog_id"));
                stV.setCatalogName(rs.getString("catalog_name"));
                if(lDistributorId != null) {
                    stV.setDistId(rs.getInt("dist_id"));
                    stV.setDistErp(rs.getString("dist_erp"));
                    stV.setDistName(rs.getString("dist_name"));
                }
                stV.setAcctId(rs.getInt("acct_id"));
                stV.setAcctErp(rs.getString("acct_erp"));
                stV.setAcctName(rs.getString("acct_name"));
                stV.setSiteId(rs.getInt("site_id"));
                stV.setSiteErp(rs.getString("site_erp"));
                stV.setSiteName(rs.getString("site_name"));
                stV.setName1(rs.getString("name1"));
                stV.setName2(rs.getString("name2"));
                stV.setAddress1(rs.getString("address1"));
                stV.setAddress2(rs.getString("address2"));
                stV.setAddress3(rs.getString("address3"));
                stV.setAddress4(rs.getString("address4"));
                stV.setCity(rs.getString("city"));
                stV.setStateProvinceCd(rs.getString("state_province_cd"));
                stV.setCountryCd(rs.getString("country_cd"));
                stV.setCountyCd(rs.getString("county_cd"));
                stV.setPostalCode(rs.getString("postal_code"));
                shipTos.add(stV);
            }*/
        }
        catch (SQLException exc) {
            errorMess = "Error. CatalogShipToReport SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
            errorMess = exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        return result;
    }
}
