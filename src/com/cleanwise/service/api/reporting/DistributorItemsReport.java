/*
 * DistributorItemsReport.java
 *
 * Created on November 21, 2003, 3:03 PM
 */

package com.cleanwise.service.api.reporting;
import java.sql.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.ReportExchangeLogDataAccess;

/**
 *
 * @author  bstevens
 */
public class DistributorItemsReport implements GenericReport{
    
    private static final String EMPTY = "";
    private static String itemSql =
    "SELECT  DISTINCT "+
"mim.item_num man_sku, mim.bus_entity_id AS man_id, dim.bus_entity_id AS dist_id, "+
"i.sku_num AS our_sku, dim.item_num AS distributor_sku, dim.item_uom, dim.item_pack,  "+
"NVL(dim.short_desc,i.short_desc) AS short_desc, dim.mod_date AS dimmoddate, mim.mod_date AS mimmoddate,dim.add_date AS dimadddate,  "+
"mim.add_date AS mimadddate, i.mod_date AS imoddate, i.add_date AS iadddate  "+
"FROM CLW_ITEM i, CLW_ITEM_MAPPING dim, CLW_ITEM_MAPPING mim  "+
"WHERE  "+
"i.item_id = mim.item_id AND  "+
"i.item_id = dim.item_id AND   "+
"dim.item_mapping_cd = 'ITEM_DISTRIBUTOR' AND "+
"mim.item_mapping_cd = 'ITEM_MANUFACTURER'  "+
"AND   "+
"dim.item_num NOT LIKE 'CW%' AND dim.item_num IS NOT NULL ";
    
    private static String distRefSql =
    "SELECT bus_entity_id, clw_value FROM clw_property WHERE "+
    "property_status_cd != 'INACTIVE' AND short_desc = 'DISTRIBUTORS_COMPANY_CODE'";
    
    private static String distNameSql =
    "SELECT bus_entity_id, short_desc FROM clw_bus_entity WHERE bus_entity_type_cd = 'DISTRIBUTOR' ";
    
    
    
    public com.cleanwise.service.api.value.GenericReportResultView process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        java.util.Date theDate = null;
        String begDate = (String)pParams.get("BEG_DATE");
        if(!Utility.isSet(begDate)){
            begDate = (String)pParams.get("BEG_DATE_OPT");
        }
        String distId = (String)pParams.get("DISTRIBUTOR");
        int distIdI = 0;
        if(!Utility.isSet(distId)){
            distId = (String)pParams.get("DISTRIBUTOR_OPT");
            try {
                distIdI = Integer.parseInt(distId);
            } catch (Exception e) {
                distIdI = 0;
            }
        }
        int groupIdI = 0;
        String groupId = (String)pParams.get("GROUP");
        if(!Utility.isSet(groupId)){
            groupId = (String)pParams.get("GROUP_OPT");
            try {
                groupIdI = Integer.parseInt(groupId);
            } catch (Exception e) {
                distIdI = 0;
            }
        }
        
        String updateLogTableStr = (String)pParams.get("ALLOW_UPDATES");
        boolean updateLogTable = false;
        if(Utility.isTrue(updateLogTableStr)){
            updateLogTable = true;
        }
        
        
        
        StringBuffer itemSqlBuf = new StringBuffer(itemSql);
        StringBuffer distRefSqlBuf = new StringBuffer(distRefSql);
        StringBuffer distNameSqlBuf = new StringBuffer(distNameSql);
        if(Utility.isSet(begDate)) {
            theDate = ReportingUtils.parseDate(begDate);
        }
        if(Utility.isSet(distId)) {
            /*itemSqlBuf.append("AND dim.bus_entity_id="+distId);
            distRefSqlBuf.append("AND bus_entity_id="+distId);
            distNameSqlBuf.append("AND bus_entity_id="+distId);*/
            itemSqlBuf.append("AND dim.bus_entity_id=?");
            distRefSqlBuf.append("AND bus_entity_id=?");
            distNameSqlBuf.append("AND bus_entity_id=?");
        }
        if(Utility.isSet(groupId)) {
            itemSqlBuf.append("AND dim.bus_entity_id IN (");
            String sql = "SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(groupId));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                itemSqlBuf.append(rs.getInt("bus_entity_id"));
            }
            while(rs.next()){
                itemSqlBuf.append(',');
                itemSqlBuf.append(rs.getInt("bus_entity_id"));
            }
            itemSqlBuf.append(')');
            //distRefSqlBuf.append("AND bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = "+groupId+")");
            //distNameSqlBuf.append("AND bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = "+groupId+")");
            distRefSqlBuf.append("AND bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = ?)");
            distNameSqlBuf.append("AND bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = ?)");
        }
        PreparedStatement stmt = con.prepareStatement(distRefSqlBuf.toString());
        int i = 1;
        if(Utility.isSet(distId)) {
            stmt.setInt(i++, distIdI);
        }
        if(Utility.isSet(groupId)) {
            stmt.setInt(i++, groupIdI);
        }
        ResultSet rs = stmt.executeQuery();
        HashMap distRefMap = new HashMap();
        while (rs.next() ) {
            Integer key = new Integer(rs.getInt("bus_entity_id"));
            distRefMap.put(key, rs.getString("clw_value"));
        }
        rs.close();
        stmt.close();
        
        
        stmt = con.prepareStatement(distNameSqlBuf.toString());
        i = 1;
        if(Utility.isSet(distId)) {
            stmt.setInt(i++, distIdI);
        }
        if(Utility.isSet(groupId)) {
            stmt.setInt(i++, groupIdI);
        }

        rs = stmt.executeQuery();
        HashMap distNameMap = new HashMap();
        while (rs.next() ) {
            Integer key = new Integer(rs.getInt("bus_entity_id"));
            distNameMap.put(key, rs.getString("short_desc"));
        }
        rs.close();
        stmt.close();
        
        
        stmt = con.prepareStatement(itemSqlBuf.toString());
        if(Utility.isSet(distId)) {
            stmt.setInt(1, distIdI);
        }
        rs = stmt.executeQuery();
        
        HashMap manNameMap = new HashMap();
        ArrayList distItems = new ArrayList();
        while (rs.next() ) {
            List row = new ArrayList();
            
            //add null temporarily we will update this later
            row.add(null);
            
            row.add(rs.getString("MAN_SKU"));
            Integer currManId = new Integer(rs.getInt("man_id"));
            Integer currDistId = new Integer(rs.getInt("dist_id"));
            String manufacturer = "unknown";
            String distributor = (String) distNameMap.get(currDistId);
            if(manNameMap.containsKey(currManId)){
                manufacturer = (String) manNameMap.get(currManId);
            }else{
                String manSql = "Select short_desc from clw_bus_entity where bus_entity_id = "+currManId;
                Statement manStmt = con.createStatement();
                ResultSet manRs = manStmt.executeQuery(manSql);
                if(manRs.next()){
                    manufacturer = manRs.getString("Short_desc");
                }
                manRs.close();
                manStmt.close();
                manNameMap.put(currManId,manufacturer);
            }
            row.add(manufacturer);
            row.add(distributor);
            
            Object distsCompanyCode = distRefMap.get(currDistId);
            if(distsCompanyCode == null){
                row.add(EMPTY);
            }else{
                row.add(distsCompanyCode);
            }
            Integer pOurSku = new Integer(rs.getInt("OUR_SKU"));
            row.add(pOurSku);
            String distSku = rs.getString("DISTRIBUTOR_SKU");
            row.add(distSku);
            row.add(rs.getString("ITEM_UOM"));
            row.add(rs.getString("ITEM_PACK"));
            row.add(rs.getString("SHORT_DESC"));
            
            RowHolder holder = new RowHolder(row, currDistId, distSku,pOurSku);
            distItems.add(holder);
        }
        
        stmt.close();
        rs.close();
        
        java.util.Date now = new java.util.Date();
        ReportExchangeLogDataVector logUpdates = new ReportExchangeLogDataVector();
        
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(ReportExchangeLogDataAccess.GENERIC_REPORT_ID,pReportData.getGenericReportId());
        ReportExchangeLogDataVector logV = ReportExchangeLogDataAccess.select(con,crit);
        Iterator repIt = distItems.iterator();
        while(repIt.hasNext()){
            RowHolder row = (RowHolder) repIt.next();
            boolean found = false;
            Iterator it = logV.iterator();
            String key = row.distId+"::"+row.distSku+"::"+row.ourSku;
            String val = rowToString(row.row);
            while(it.hasNext()){
                ReportExchangeLogData log = (ReportExchangeLogData) it.next();
                if(key.equals(log.getRecordKey())){
                    found = true;
                    if(val.equals(log.getValue())){
                        if(updateLogTable){
                            repIt.remove();
                        }else{
                            row.row.set(0," ");
                        }
                    }else{
                        row.row.set(0,"C");
                        log.setSentDate(now);
                        log.setValue(val);
                        logUpdates.add(log);
                    }
                }
            }
            if(!found){
                row.row.set(0,"A");
                ReportExchangeLogData newEntry = ReportExchangeLogData.createValue();
                newEntry.setGenericReportId(pReportData.getGenericReportId());
                newEntry.setRecordKey(key);
                newEntry.setSentDate(now);
                newEntry.setValue(val);
                logUpdates.add(newEntry);
            }
        }
        if(updateLogTable){
            Iterator it = logUpdates.iterator();
            while(it.hasNext()){
                ReportExchangeLogData log = (ReportExchangeLogData) it.next();
                if(log.getReportExchangeLogId() == 0){
                    ReportExchangeLogDataAccess.insert(con, log);
                }else{
                    ReportExchangeLogDataAccess.update(con, log);
                }
            }
        }
        
        
        
        
        
        GenericReportResultView ret = GenericReportResultView.createValue();
        ret.setTable(toRow(distItems));
        ret.setHeader(getReportHeader());
        ret.setColumnCount(ret.getHeader().size());
        
        return ret;
    }
    
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer_Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributors_Company_Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Our_Sku",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor_Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item_Description",0,255,"VARCHAR2"));
        return header;
    }
    
    private static char SEPERATOR = '\t';
    private String rowToString(List pRow){
        ArrayList row = new ArrayList();
        row.addAll(pRow);
        StringBuffer retVal = new StringBuffer();
        Iterator it = row.iterator();
        it.next(); //skip the first as it is the "code" value.
        it.next(); //skip manu info
        it.next(); //...
        it.next(); //skip dist info
        it.next(); //...
        while(it.hasNext()){
            Object o = it.next();
            retVal.append(SEPERATOR);
            if(o != null){
                retVal.append(o.toString());
            }
        }
        return retVal.toString();
    }
    
    private ArrayList toRow(List rowHolderList){
        Iterator it = rowHolderList.iterator();
        ArrayList newList = new ArrayList();
        while(it.hasNext()){
            newList.add(((RowHolder)it.next()).row);
        }
        return newList;
    }
    
    private class RowHolder{
        List row;
        Integer distId;
        String distSku;
        Integer ourSku;
        RowHolder(List pRow, Integer pDistId, String pDistSku, Integer pOurSku){
            row = pRow;
            distId = pDistId;
            distSku = pDistSku;
            ourSku = pOurSku;
        }
    }
}
